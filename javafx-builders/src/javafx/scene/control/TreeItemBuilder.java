/* 
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javafx.scene.control;

/**
Builder class for javafx.scene.control.TreeItem
@see javafx.scene.control.TreeItem
@deprecated This class is deprecated and will be removed in the next version
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class TreeItemBuilder<T, B extends javafx.scene.control.TreeItemBuilder<T, B>> implements javafx.util.Builder<javafx.scene.control.TreeItem<T>> {
    protected TreeItemBuilder() {
    }
    
    /** Creates a new instance of TreeItemBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static <T> javafx.scene.control.TreeItemBuilder<T, ?> create() {
        return new javafx.scene.control.TreeItemBuilder();
    }
    
    private int __set;
    public void applyTo(javafx.scene.control.TreeItem<T> x) {
        int set = __set;
        if ((set & (1 << 0)) != 0) x.getChildren().addAll(this.children);
        if ((set & (1 << 1)) != 0) x.setExpanded(this.expanded);
        if ((set & (1 << 2)) != 0) x.setGraphic(this.graphic);
        if ((set & (1 << 3)) != 0) x.setValue(this.value);
    }
    
    private java.util.Collection<? extends javafx.scene.control.TreeItem<T>> children;
    /**
    Add the given items to the List of items in the {@link javafx.scene.control.TreeItem#getChildren() children} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B children(java.util.Collection<? extends javafx.scene.control.TreeItem<T>> x) {
        this.children = x;
        __set |= 1 << 0;
        return (B) this;
    }
    
    /**
    Add the given items to the List of items in the {@link javafx.scene.control.TreeItem#getChildren() children} property for the instance constructed by this builder.
    */
    public B children(javafx.scene.control.TreeItem<T>... x) {
        return children(java.util.Arrays.asList(x));
    }
    
    private boolean expanded;
    /**
    Set the value of the {@link javafx.scene.control.TreeItem#isExpanded() expanded} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B expanded(boolean x) {
        this.expanded = x;
        __set |= 1 << 1;
        return (B) this;
    }
    
    private javafx.scene.Node graphic;
    /**
    Set the value of the {@link javafx.scene.control.TreeItem#getGraphic() graphic} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B graphic(javafx.scene.Node x) {
        this.graphic = x;
        __set |= 1 << 2;
        return (B) this;
    }
    
    private T value;
    /**
    Set the value of the {@link javafx.scene.control.TreeItem#getValue() value} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B value(T x) {
        this.value = x;
        __set |= 1 << 3;
        return (B) this;
    }
    
    /**
    Make an instance of {@link javafx.scene.control.TreeItem} based on the properties set on this builder.
    */
    public javafx.scene.control.TreeItem<T> build() {
        javafx.scene.control.TreeItem<T> x = new javafx.scene.control.TreeItem<T>();
        applyTo(x);
        return x;
    }
}
