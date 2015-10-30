/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javafx.scene.control.behavior;

import com.sun.javafx.scene.control.Properties;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;
import com.sun.javafx.scene.control.skin.Utils;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Window;
import com.sun.javafx.PlatformUtil;
import com.sun.javafx.geom.transform.Affine3D;

import static javafx.scene.control.skin.TextFieldSkin.TextPosInfo;
import static com.sun.javafx.PlatformUtil.isMac;
import static com.sun.javafx.PlatformUtil.isWindows;

/**
 * Text field behavior.
 */
public class TextFieldBehavior extends TextInputControlBehavior<TextField> {
    private TextFieldSkin skin;
    private TwoLevelFocusBehavior tlFocus;
    private ChangeListener<Scene> sceneListener;
    private ChangeListener<Node> focusOwnerListener;

    public TextFieldBehavior(final TextField textField) {
        super(textField);

        if (Properties.IS_TOUCH_SUPPORTED) {
            contextMenu.getStyleClass().add("text-input-context-menu");
        }

        handleFocusChange();

        // Register for change events
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            handleFocusChange();
        });

        focusOwnerListener = (observable, oldValue, newValue) -> {
            // RT-23699: The selection is now only affected when the TextField
            // gains or loses focus within the Scene, and not when the whole
            // stage becomes active or inactive.
            if (newValue == textField) {
                if (!focusGainedByMouseClick) {
                    textField.selectRange(textField.getLength(), 0);
                }
            } else {
                textField.selectRange(0, 0);
            }
        };

        final WeakChangeListener<Node> weakFocusOwnerListener =
                                new WeakChangeListener<Node>(focusOwnerListener);
        sceneListener = (observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.focusOwnerProperty().removeListener(weakFocusOwnerListener);
            }
            if (newValue != null) {
                newValue.focusOwnerProperty().addListener(weakFocusOwnerListener);
            }
        };
        textField.sceneProperty().addListener(new WeakChangeListener<Scene>(sceneListener));

        if (textField.getScene() != null) {
            textField.getScene().focusOwnerProperty().addListener(weakFocusOwnerListener);
        }

        // Only add this if we're on an embedded platform that supports 5-button navigation
        if (Utils.isTwoLevelFocus()) {
            tlFocus = new TwoLevelFocusBehavior(textField); // needs to be last.
        }
    }

    @Override public void dispose() {
        if (tlFocus != null) tlFocus.dispose();
        super.dispose();
    }

    private void handleFocusChange() {
        TextField textField = getNode();
        
        if (textField.isFocused()) {
            if (PlatformUtil.isIOS()) {
                // special handling of focus on iOS is required to allow to
                // control native keyboard, because nat. keyboard is poped-up only when native 
                // text component gets focus. When we have JFX keyboard we can remove this code
                TextInputTypes type = TextInputTypes.TEXT_FIELD;
                if (textField.getClass().equals(javafx.scene.control.PasswordField.class)) {
                    type = TextInputTypes.PASSWORD_FIELD;
                } else if (textField.getParent().getClass().equals(javafx.scene.control.ComboBox.class)) {
                    type = TextInputTypes.EDITABLE_COMBO;
                }
                final Bounds bounds = textField.getBoundsInParent();
                double w = bounds.getWidth();
                double h = bounds.getHeight();
                Affine3D trans = calculateNodeToSceneTransform(textField);
//                Insets insets = skin.getInsets();
//                w -= insets.getLeft() + insets.getRight();
//                h -= insets.getTop() + insets.getBottom();
                String text = textField.getText();

                // we need to display native text input component on the place where JFX component is drawn
                // all parameters needed to do that are passed to native impl. here
                textField.getScene().getWindow().impl_getPeer().requestInput(text, type.ordinal(), w, h, 
                        trans.getMxx(), trans.getMxy(), trans.getMxz(), trans.getMxt(),// + insets.getLeft(),
                        trans.getMyx(), trans.getMyy(), trans.getMyz(), trans.getMyt(),// + insets.getTop(),
                        trans.getMzx(), trans.getMzy(), trans.getMzz(), trans.getMzt());
            }
            if (!focusGainedByMouseClick) {
                setCaretAnimating(true);
            }
        } else {
            if (PlatformUtil.isIOS() && textField.getScene() != null) {
                // releasing the focus => we need to hide the native component and also native keyboard
                textField.getScene().getWindow().impl_getPeer().releaseInput();
            }
            focusGainedByMouseClick = false;
            setCaretAnimating(false);
        }
    }

    static Affine3D calculateNodeToSceneTransform(Node node) {
        final Affine3D transform = new Affine3D();
        do {
            transform.preConcatenate(node.impl_getLeafTransform());
            node = node.getParent();
        } while (node != null);

        return transform;
    }

    // An unholy back-reference!
    public void setTextFieldSkin(TextFieldSkin skin) {
        this.skin = skin;
    }

    @Override protected void fire(KeyEvent event) {
        TextField textField = getNode();
        EventHandler<ActionEvent> onAction = textField.getOnAction();
        ActionEvent actionEvent = new ActionEvent(textField, null);

        textField.fireEvent(actionEvent);
        textField.commitValue();

        if (onAction == null && !actionEvent.isConsumed()) {
            forwardToParent(event);
        }
    }

    @Override
    protected void cancelEdit(KeyEvent event) {
        TextField textField = getNode();
        if (textField.getTextFormatter() != null) {
            textField.cancelEdit();
            event.consume();
        } else {
            super.cancelEdit(event);
        }
    }

    @Override protected void deleteChar(boolean previous) {
        skin.deleteChar(previous);
    }

    @Override protected void replaceText(int start, int end, String txt) {
        skin.replaceText(start, end, txt);
    }

    @Override protected void deleteFromLineStart() {
        TextField textField = getNode();
        int end = textField.getCaretPosition();

        if (end > 0) {
            replaceText(0, end, "");
        }
    }

    @Override protected void setCaretAnimating(boolean play) {
        if (skin != null) {
            skin.setCaretAnimating(play);
        }
    }

    /**
     * Function which beeps. This requires a hook into the toolkit, and should
     * also be guarded by something that indicates whether we should beep
     * (as it is pretty annoying and many native controls don't do it).
     */
    private void beep() {
        // TODO
    }

    /**
     * If the focus is gained via response to a mouse click, then we don't
     * want to select all the text even if selectOnFocus is true.
     */
    private boolean focusGainedByMouseClick = false;
    private boolean shiftDown = false;
    private boolean deferClick = false;

    @Override public void mousePressed(MouseEvent e) {
        TextField textField = getNode();
        // We never respond to events if disabled
        if (!textField.isDisabled()) {
            // If the text field doesn't have focus, then we'll attempt to set
            // the focus and we'll indicate that we gained focus by a mouse
            // click, which will then NOT honor the selectOnFocus variable
            // of the textInputControl
            if (!textField.isFocused()) {
                focusGainedByMouseClick = true;
                textField.requestFocus();
            }

            // stop the caret animation
            setCaretAnimating(false);
            // only if there is no selection should we see the caret
//            setCaretOpacity(if (textInputControl.dot == textInputControl.mark) then 1.0 else 0.0);

            // if the primary button was pressed
            if (e.isPrimaryButtonDown() && !(e.isMiddleButtonDown() || e.isSecondaryButtonDown())) {
                TextPosInfo hit = skin.getIndex(e.getX(), e.getY());
                String text = textField.textProperty().getValueSafe();
                int i = Utils.getHitInsertionIndex(hit, text);
                final int anchor = textField.getAnchor();
                final int caretPosition = textField.getCaretPosition();
                if (e.getClickCount() < 2 &&
                    (Properties.IS_TOUCH_SUPPORTED ||
                     (anchor != caretPosition &&
                      ((i > anchor && i < caretPosition) || (i < anchor && i > caretPosition))))) {
                    // if there is a selection, then we will NOT handle the
                    // press now, but will defer until the release. If you
                    // select some text and then press down, we change the
                    // caret and wait to allow you to drag the text (TODO).
                    // When the drag concludes, then we handle the click

                    deferClick = true;
                    // TODO start a timer such that after some millis we
                    // switch into text dragging mode, change the cursor
                    // to indicate the text can be dragged, etc.
                } else if (!(e.isControlDown() || e.isAltDown() || e.isShiftDown() || e.isMetaDown())) {
                    switch (e.getClickCount()) {
                        case 1: mouseSingleClick(hit); break;
                        case 2: mouseDoubleClick(hit); break;
                        case 3: mouseTripleClick(hit); break;
                        default: // no-op
                    }
                } else if (e.isShiftDown() && !(e.isControlDown() || e.isAltDown() || e.isMetaDown()) && e.getClickCount() == 1) {
                    // didn't click inside the selection, so select
                    shiftDown = true;
                    // if we are on mac os, then we will accumulate the
                    // selection instead of just moving the dot. This happens
                    // by figuring out past which (dot/mark) are extending the
                    // selection, and set the mark to be the other side and
                    // the dot to be the new position.
                    // everywhere else we just move the dot.
                    if (isMac()) {
                        textField.extendSelection(i);
                    } else {
                        skin.positionCaret(hit, true);
                    }
                }
                skin.setForwardBias(hit.isLeading());
//                if (textInputControl.editable)
//                    displaySoftwareKeyboard(true);
            }
        }
        if (contextMenu.isShowing()) {
            contextMenu.hide();
        }
    }

    @Override public void mouseDragged(MouseEvent e) {
        final TextField textField = getNode();
        // we never respond to events if disabled, but we do notify any onXXX
        // event listeners on the control
        if (!textField.isDisabled() && !deferClick) {
            if (e.isPrimaryButtonDown() && !(e.isMiddleButtonDown() || e.isSecondaryButtonDown())) {
                if (!(e.isControlDown() || e.isAltDown() || e.isShiftDown() || e.isMetaDown())) {
                    skin.positionCaret(skin.getIndex(e.getX(), e.getY()), true);
                }
            }
        }
    }

    @Override public void mouseReleased(MouseEvent e) {
        final TextField textField = getNode();
        // we never respond to events if disabled, but we do notify any onXXX
        // event listeners on the control
        if (!textField.isDisabled()) {
            setCaretAnimating(false);
            if (deferClick) {
                deferClick = false;
                skin.positionCaret(skin.getIndex(e.getX(), e.getY()), shiftDown);
                shiftDown = false;
            }
            setCaretAnimating(true);
        }
    }

    @Override public void contextMenuRequested(ContextMenuEvent e) {
        final TextField textField = getNode();

        if (contextMenu.isShowing()) {
            contextMenu.hide();
        } else if (textField.getContextMenu() == null && 
                   textField.getOnContextMenuRequested() == null) {
            double screenX = e.getScreenX();
            double screenY = e.getScreenY();
            double sceneX = e.getSceneX();

            if (Properties.IS_TOUCH_SUPPORTED) {
                Point2D menuPos;
                if (textField.getSelection().getLength() == 0) {
                    skin.positionCaret(skin.getIndex(e.getX(), e.getY()), false);
                    menuPos = skin.getMenuPosition();
                } else {
                    menuPos = skin.getMenuPosition();
                    if (menuPos != null && (menuPos.getX() <= 0 || menuPos.getY() <= 0)) {
                        skin.positionCaret(skin.getIndex(e.getX(), e.getY()), false);
                        menuPos = skin.getMenuPosition();
                    }
                }

                if (menuPos != null) {
                    Point2D p = getNode().localToScene(menuPos);
                    Scene scene = getNode().getScene();
                    Window window = scene.getWindow();
                    Point2D location = new Point2D(window.getX() + scene.getX() + p.getX(),
                                                   window.getY() + scene.getY() + p.getY());
                    screenX = location.getX();
                    sceneX = p.getX();
                    screenY = location.getY();
                }
            }

            populateContextMenu();
            double menuWidth = contextMenu.prefWidth(-1);
            double menuX = screenX - (Properties.IS_TOUCH_SUPPORTED ? (menuWidth / 2) : 0);
            Screen currentScreen = com.sun.javafx.util.Utils.getScreenForPoint(screenX, 0);
            Rectangle2D bounds = currentScreen.getBounds();

            if (menuX < bounds.getMinX()) {
                getNode().getProperties().put("CONTEXT_MENU_SCREEN_X", screenX);
                getNode().getProperties().put("CONTEXT_MENU_SCENE_X", sceneX);
                contextMenu.show(getNode(), bounds.getMinX(), screenY);
            } else if (screenX + menuWidth > bounds.getMaxX()) {
                double leftOver = menuWidth - ( bounds.getMaxX() - screenX);
                getNode().getProperties().put("CONTEXT_MENU_SCREEN_X", screenX);
                getNode().getProperties().put("CONTEXT_MENU_SCENE_X", sceneX);
                contextMenu.show(getNode(), screenX - leftOver, screenY);
            } else {
                getNode().getProperties().put("CONTEXT_MENU_SCREEN_X", 0);
                getNode().getProperties().put("CONTEXT_MENU_SCENE_X", 0);
                contextMenu.show(getNode(), menuX, screenY);
            }
        }

        e.consume();
    }

    protected void mouseSingleClick(TextPosInfo hit) {
        skin.positionCaret(hit, false);
    }

    protected void mouseDoubleClick(TextPosInfo hit) {
        final TextField textField = getNode();
        textField.previousWord();
        if (isWindows()) {
            textField.selectNextWord();
        } else {
            textField.selectEndOfNextWord();
        }
    }

    protected void mouseTripleClick(TextPosInfo hit) {
        getNode().selectAll();
    }
    
    // Enumeration of all types of text input that can be simulated on 
    // touch device, such as iPad. Type is passed to native code and 
    // native text component is shown. It's used as workaround for iOS
    // devices since keyboard control is not possible without native 
    // text component being displayed
    enum TextInputTypes {
        TEXT_FIELD,
        PASSWORD_FIELD,
        EDITABLE_COMBO,
        TEXT_AREA;
    }

}
