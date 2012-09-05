/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.javafx.scene.control.skin;


import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import javafx.collections.ListChangeListener;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import com.sun.javafx.scene.control.behavior.CellBehaviorBase;
import javafx.collections.WeakListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;

/**
 */
public class TableRowSkin<T> extends CellSkinBase<TableRow<T>, CellBehaviorBase<TableRow<T>>> {

    // Specifies the number of times we will call 'recreateCells()' before we blow
    // out the cellsMap structure and rebuild all cells. This helps to prevent
    // against memory leaks in certain extreme circumstances.
    private static final int DEFAULT_FULL_REFRESH_COUNTER = 100;

    /*
     * A map that maps from TableColumn to TableCell (i.e. model to view).
     * This is recreated whenever the leaf columns change, however to increase
     * efficiency we create cells for all columns, even if they aren't visible,
     * and we only create new cells if we don't already have it cached in this
     * map.
     *
     * Note that this means that it is possible for this map to therefore be
     * a memory leak if an application uses TableView and is creating and removing
     * a large number of tableColumns. This is mitigated in the recreateCells()
     * function below - refer to that to learn more.
     */
    private WeakHashMap<TableColumn, TableCell> cellsMap;

    // This observableArrayList contains the currently visible table cells for this row.
    private final List<TableCell> cells = new ArrayList<TableCell>();
    
    private int fullRefreshCounter = DEFAULT_FULL_REFRESH_COUNTER;

    private boolean showColumns = true;
    
    private boolean isDirty = false;
    private boolean updateCells = false;
    
    private ListChangeListener visibleLeafColumnsListener = new ListChangeListener() {
        @Override public void onChanged(Change c) {
            isDirty = true;
            requestLayout();
        }
    };

    public TableRowSkin(TableRow<T> tableRow) {
        super(tableRow, new CellBehaviorBase<TableRow<T>>(tableRow));

        recreateCells();
        updateCells(true);

        initBindings();

        registerChangeListener(tableRow.itemProperty(), "ITEM");
        registerChangeListener(tableRow.editingProperty(), "EDITING");
        registerChangeListener(tableRow.tableViewProperty(), "TABLE_VIEW");
    }

    @Override protected void handleControlPropertyChanged(String p) {
        // we run this before the super call because we want to update whether
        // we are showing columns or the node (if it isn't null) before the
        // parent class updates the content
        if ("TEXT".equals(p) || "GRAPHIC".equals(p) || "EDITING".equals(p)) {
            updateShowColumns();
        }

        super.handleControlPropertyChanged(p);

        if ("ITEM".equals(p)) {
            updateCells = true;
            requestLayout();
            
            // Required to fix RT-24725
            getSkinnable().layout();
        } else if ("TABLE_VIEW".equals(p)) {
            for (int i = 0; i < getChildren().size(); i++) {
                Node n = getChildren().get(i);
                if (n instanceof TableCell) {
                    ((TableCell)n).updateTableView(getSkinnable().getTableView());
                }
            }
        }
    }

    private void updateShowColumns() {
        boolean newValue = (isIgnoreText() && isIgnoreGraphic());
        if (showColumns == newValue) return;
        
        showColumns = newValue;

        requestLayout();
    }
    
    private void initBindings() {
        // watches for any change in the leaf columns observableArrayList - this will indicate
        // that the column order has changed and that we should update the row
        // such that the cells are in the new order
        if (getSkinnable() == null) {
            throw new IllegalStateException("TableRowSkin does not have a Skinnable set to a TableRow instance");
        }
        if (getSkinnable().getTableView() == null) {
            throw new IllegalStateException("TableRow not have the TableView property set");
        }
        
        getSkinnable().getTableView().getVisibleLeafColumns().addListener(
                new WeakListChangeListener(visibleLeafColumnsListener));
    }
    
    @Override protected void layoutChildren(double x, final double y,
            final double w, final double h) {
        
        if (isDirty) {
            recreateCells();
            updateCells(true);
            isDirty = false;
        } else if (updateCells) {
            updateCells(false);
            updateCells = false;
        }
        
        TableView<T> table = getSkinnable().getTableView();
        if (table == null) return;
        if (cellsMap.isEmpty()) return;
        
        if (showColumns && ! table.getVisibleLeafColumns().isEmpty()) {
            // layout the individual column cells
            double width;
            double height;
            
            Insets insets = getInsets();
            
            double verticalPadding = insets.getTop() + insets.getBottom();
            double horizontalPadding = insets.getLeft() + insets.getRight();
            
            for (int i = 0; i < getChildren().size(); i++) {
                // in most cases all children are TableCell instances, but this
                // is not always the case. For example, see RT-17694
                Node node = getChildren().get(i);

                width = snapSize(node.prefWidth(-1) - horizontalPadding);
                height = Math.max(getHeight(), node.prefHeight(-1));
                height = snapSize(height - verticalPadding);
                
                node.resize(width, height);
                node.relocate(x, insets.getTop());
                x += width;
            }
        } else {
            super.layoutChildren(x,y,w,h);
        }
    }

    private int columnCount = 0;
    
    private void recreateCells() {
        // This function is smart in the sense that we don't recreate all
        // TableCell instances every time this function is called. Instead we
        // only create TableCells for TableColumns we haven't already encountered.
        // To avoid a potential memory leak (when the TableColumns in the
        // TableView are created/inserted/removed/deleted, we have a 'refresh
        // counter' that when we reach 0 will delete all cells in this row
        // and recreate all of them.
        
        TableView<T> table = getSkinnable().getTableView();
        if (table == null) {
            clearCellsMap();
            return;
        }
        
        ObservableList<TableColumn<T,?>> columns = table.getVisibleLeafColumns();
        
        if (columns.size() != columnCount || fullRefreshCounter == 0 || cellsMap == null) {
            clearCellsMap();
            cellsMap = new WeakHashMap<TableColumn, TableCell>(columns.size());
            fullRefreshCounter = DEFAULT_FULL_REFRESH_COUNTER;
            getChildren().clear();
        }
        columnCount = columns.size();
        fullRefreshCounter--;
        
        TableRow skinnable = getSkinnable();
        
        for (TableColumn col : columns) {
            if (cellsMap.containsKey(col)) {
                continue;
            }
            
            // we must create a TableCell for each table column
            TableCell cell = (TableCell) col.getCellFactory().call(col);

            // we set it's TableColumn, TableView and TableRow
            cell.updateTableColumn(col);
            cell.updateTableView(table);
            cell.updateTableRow(skinnable);

            // and store this in our HashMap until needed
            cellsMap.put(col, cell);
        }
    }
    
    private void clearCellsMap() {
        if (cellsMap != null) cellsMap.clear();
    }

    private void updateCells(boolean resetChildren) {
        // if delete isn't called first, we can run into situations where the
        // cells aren't updated properly.
        cells.clear();

        TableRow skinnable = getSkinnable();
        int skinnableIndex = skinnable.getIndex();
        TableView<T> table = skinnable.getTableView();
        if (table != null) {
            List<TableColumn<T,?>> visibleLeafColumns = table.getVisibleLeafColumns();
            for (int i = 0, max = visibleLeafColumns.size(); i < max; i++) {
                TableColumn<T,?> col = visibleLeafColumns.get(i);
                TableCell cell = cellsMap.get(col);
                if (cell == null) continue;

                cell.updateIndex(skinnableIndex);
                cell.updateTableRow(skinnable);
                cells.add(cell);
            }
        }

        // update children of each row
        ObservableList<Node> children = getChildren();
        if (resetChildren) {
            if (showColumns) {
                if (cells.isEmpty()) {
                    children.clear();
                } else {
                    // TODO we can optimise this by only showing cells that are 
                    // visible based on the table width and the amount of horizontal
                    // scrolling.
                    children.setAll(cells);
                }
            } else {
                children.clear();

                if (!isIgnoreText() || !isIgnoreGraphic()) {
                    children.add(skinnable);
                }
            }
        }
    }
    
    @Override protected double computePrefWidth(double height) {
        if (showColumns) {
            double prefWidth = 0.0F;

            if (getSkinnable().getTableView() != null) {
                List<TableColumn<T,?>> visibleLeafColumns = getSkinnable().getTableView().getVisibleLeafColumns();
                for (int i = 0, max = visibleLeafColumns.size(); i < max; i++) {
                    TableColumn<T,?> tableColumn = visibleLeafColumns.get(i);
                    prefWidth += tableColumn.getWidth();
                }
            }

            return prefWidth;
        } else {
            return super.computePrefWidth(height);
        }
    }
    
    @Override protected double computePrefHeight(double width) {
        if (showColumns) {
            // Support for RT-18467: making it easier to specify a height for
            // cells via CSS, where the desired height is less than the height
            // of the TableCells. Essentially, -fx-cell-size is given higher
            // precedence now
            if (getCellSize() < CellSkinBase.DEFAULT_CELL_SIZE) {
                return getCellSize();
            }
            
            // FIXME according to profiling, this method is slow and should
            // be optimised
            double prefHeight = 0.0f;
            final int count = cells.size();
            for (int i=0; i<count; i++) {
                final TableCell tableCell = cells.get(i);
                prefHeight = Math.max(prefHeight, tableCell.prefHeight(-1));
            }
            return Math.max(prefHeight, Math.max(getCellSize(), getSkinnable().minHeight(-1)));
        } else {
            return super.computePrefHeight(width);
        }
    }
    
}
