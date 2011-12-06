/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.PlatformUtil;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import com.sun.javafx.scene.text.HitInfo;
import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.*;

/**
 * Text area behavior.
 */
public class TextAreaBehavior extends TextInputControlBehavior<TextArea> {
    /**************************************************************************
     *                          Setup KeyBindings                             *
     *************************************************************************/
    protected static final List<KeyBinding> TEXT_AREA_BINDINGS = new ArrayList<KeyBinding>();
    static {
        TEXT_AREA_BINDINGS.add(new KeyBinding(TAB, KEY_PRESSED, "TraverseNext").ctrl());
        TEXT_AREA_BINDINGS.add(new KeyBinding(TAB, KEY_PRESSED, "TraversePrevious").ctrl().shift());
        TEXT_AREA_BINDINGS.add(new KeyBinding(HOME, KEY_PRESSED, "LineStart")); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(END, KEY_PRESSED, "LineEnd")); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(UP, KEY_PRESSED, "PreviousLine")); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(KP_UP, KEY_PRESSED, "PreviousLine")); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(DOWN, KEY_PRESSED, "NextLine")); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(KP_DOWN, KEY_PRESSED, "NextLine")); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(PAGE_UP, KEY_PRESSED, "PreviousPage")); // new
        TEXT_AREA_BINDINGS.add(new KeyBinding(PAGE_DOWN, KEY_PRESSED, "NextPage")); // new
        TEXT_AREA_BINDINGS.add(new KeyBinding(ENTER, KEY_PRESSED, "InsertNewLine")); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(TAB, KEY_PRESSED, "InsertTab")); // changed

        TEXT_AREA_BINDINGS.add(new KeyBinding(HOME, KEY_PRESSED, "SelectLineStart").shift()); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(END, KEY_PRESSED, "SelectLineEnd").shift()); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(UP, KEY_PRESSED, "SelectPreviousLine").shift()); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(KP_UP, KEY_PRESSED, "SelectPreviousLine").shift()); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(DOWN, KEY_PRESSED, "SelectNextLine").shift()); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(KP_DOWN, KEY_PRESSED, "SelectNextLine").shift()); // changed
        TEXT_AREA_BINDINGS.add(new KeyBinding(PAGE_UP, KEY_PRESSED, "SelectPreviousPage").shift()); // new
        TEXT_AREA_BINDINGS.add(new KeyBinding(PAGE_DOWN, KEY_PRESSED, "SelectNextPage").shift()); // new
        // Platform specific settings
        if (PlatformUtil.isMac()) {
            TEXT_AREA_BINDINGS.add(new KeyBinding(LEFT, KEY_PRESSED, "LineStart").meta()); // changed
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_LEFT, KEY_PRESSED, "LineStart").meta()); // changed
            TEXT_AREA_BINDINGS.add(new KeyBinding(RIGHT, KEY_PRESSED, "LineEnd").meta()); // changed
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_RIGHT, KEY_PRESSED, "LineEnd").meta()); // changed
            TEXT_AREA_BINDINGS.add(new KeyBinding(LEFT, KEY_PRESSED, "SelectLineStart").shift().meta()); // changed
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_LEFT, KEY_PRESSED, "SelectLineStart").shift().meta()); // changed
            TEXT_AREA_BINDINGS.add(new KeyBinding(RIGHT, KEY_PRESSED, "SelectLineEnd").shift().meta()); // changed
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_RIGHT, KEY_PRESSED, "SelectLineEnd").shift().meta()); // changed

            TEXT_AREA_BINDINGS.add(new KeyBinding(UP, KEY_PRESSED, "ParagraphStart").meta());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_UP, KEY_PRESSED, "ParagraphStart").meta());
            TEXT_AREA_BINDINGS.add(new KeyBinding(DOWN, KEY_PRESSED, "ParagraphEnd").meta());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_DOWN, KEY_PRESSED, "ParagraphEnd").meta());
            TEXT_AREA_BINDINGS.add(new KeyBinding(UP, KEY_PRESSED, "SelectParagraphStart").meta().shift());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_UP, KEY_PRESSED, "SelectParagraphStart").meta().shift());
            TEXT_AREA_BINDINGS.add(new KeyBinding(DOWN, KEY_PRESSED, "SelectParagraphEnd").meta().shift());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_DOWN, KEY_PRESSED, "SelectParagraphEnd").meta().shift());
        } else {
            TEXT_AREA_BINDINGS.add(new KeyBinding(UP, KEY_PRESSED, "ParagraphStart").ctrl());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_UP, KEY_PRESSED, "ParagraphStart").ctrl());
            TEXT_AREA_BINDINGS.add(new KeyBinding(DOWN, KEY_PRESSED, "ParagraphEnd").ctrl());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_DOWN, KEY_PRESSED, "ParagraphEnd").ctrl());
            TEXT_AREA_BINDINGS.add(new KeyBinding(UP, KEY_PRESSED, "SelectParagraphStart").ctrl().shift());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_UP, KEY_PRESSED, "SelectParagraphStart").ctrl().shift());
            TEXT_AREA_BINDINGS.add(new KeyBinding(DOWN, KEY_PRESSED, "SelectParagraphEnd").ctrl().shift());
            TEXT_AREA_BINDINGS.add(new KeyBinding(KP_DOWN, KEY_PRESSED, "SelectParagraphEnd").ctrl().shift());
        }
        // Add the other standard key bindings in
        TEXT_AREA_BINDINGS.addAll(TextInputControlBindings.BINDINGS);
        // However, we want to consume other key press / release events too, for
        // things that would have been handled by the InputCharacter normally
        TEXT_AREA_BINDINGS.add(new KeyBinding(null, KEY_PRESSED, "Consume"));
    }

    private TextAreaSkin skin;

    /**************************************************************************
     * Constructors                                                           *
     *************************************************************************/

    public TextAreaBehavior(TextArea textArea) {
        super(textArea);

        // Register for change events
        textArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                TextArea textArea = getControl();
                if (textArea.isFocused()) {
                    if (!focusGainedByMouseClick) {
                        setCaretAnimating(true);
                    }
                } else {
//                    skin.hideCaret();
                    focusGainedByMouseClick = false;
                }
            }
        });

    }

    // An unholy back-reference!
    public void setTextAreaSkin(TextAreaSkin skin) {
        this.skin = skin;
    }

    /**************************************************************************
     * Key handling implementation                                            *
     *************************************************************************/

    @Override protected List<KeyBinding> createKeyBindings() {
        return TEXT_AREA_BINDINGS;
    }

    @Override public void callAction(String name) {
        final TextArea textInputControl = getControl();

        if (textInputControl.isEditable()) {
//            fnCaretAnim(false);
//            setCaretOpacity(1.0);
            if ("LineStart".equals(name)) skin.lineStart(false);
            else if ("LineEnd".equals(name)) skin.lineEnd(false);
            else if ("SelectLineStart".equals(name)) skin.lineStart(true);
            else if ("SelectLineEnd".equals(name)) skin.lineEnd(true);
            else if ("PreviousLine".equals(name)) skin.previousLine(false);
            else if ("NextLine".equals(name)) skin.nextLine(false);
            else if ("SelectPreviousLine".equals(name)) skin.previousLine(true);
            else if ("SelectNextLine".equals(name)) skin.nextLine(true);

            else if ("ParagraphStart".equals(name)) skin.paragraphStart(true, false);
            else if ("ParagraphEnd".equals(name)) skin.paragraphEnd(true, false);
            else if ("SelectParagraphStart".equals(name)) skin.paragraphStart(true, true);
            else if ("SelectParagraphEnd".equals(name)) skin.paragraphEnd(true, true);

            else if ("PreviousPage".equals(name)) skin.previousPage(false);
            else if ("NextPage".equals(name)) skin.nextPage(false);
            else if ("SelectPreviousPage".equals(name)) skin.previousPage(true);
            else if ("SelectNextPage".equals(name)) skin.nextPage(true);
            else if ("InsertNewLine".equals(name)) insertNewLine();
            else if ("InsertTab".equals(name)) insertTab();
            else super.callAction(name);
//            fnCaretAnim(true);

        } else if ("Copy".equals(name)) {
            // if the key event is for the "copy" action then we go ahead
            // and execute it, but for all other key events which occur
            // when not editable, we don't allow.
            textInputControl.copy();
        } else if (name.startsWith("Traverse")) {
            // call super.callAction() for any focus traversal actions even if
            // it's not editable
            super.callAction(name);
        }
    }

    private void insertNewLine() {
        getControl().replaceSelection("\n");
    }
    private void insertTab() {
        getControl().replaceSelection("\t");
    }

    @Override protected void deleteChar(boolean previous) {
        skin.deleteChar(previous);
    }

    protected void scrollCharacterToVisible(int index) {
        // TODO this method should be removed when TextAreaSkin
        // TODO is refactored to no longer need it.
        skin.scrollCharacterToVisible(index);
    }

    @Override protected void replaceText(int start, int end, String txt) {
        getControl().replaceText(start, end, txt);
    }

    /**
     * If the focus is gained via response to a mouse click, then we don't
     * want to select all the text even if selectOnFocus is true.
     */
    private boolean focusGainedByMouseClick = false; // TODO!!
    private boolean shiftDown = false;
    private boolean deferClick = false;

    @Override public void mousePressed(MouseEvent e) {
        TextArea textArea = getControl();
        super.mousePressed(e);
        // We never respond to events if disabled
        if (!textArea.isDisabled()) {
            // If the text field doesn't have focus, then we'll attempt to set
            // the focus and we'll indicate that we gained focus by a mouse
            // click, TODO which will then NOT honor the selectOnFocus variable
            // of the textInputControl
            if (!textArea.isFocused()) {
                focusGainedByMouseClick = true;
                textArea.requestFocus();
            }

            // stop the caret animation
            setCaretAnimating(false);
            // only if there is no selection should we see the caret
//            setCaretOpacity(if (textInputControl.dot == textInputControl.mark) then 1.0 else 0.0);

            // if the primary button was pressed
            if (e.getButton() == MouseButton.PRIMARY && !(e.isMiddleButtonDown() || e.isSecondaryButtonDown())) {
                HitInfo hit = skin.getIndex(e);
                int i = hit.getInsertionIndex();
//                 int i = skin.getInsertionPoint(e.getX(), e.getY());
                final int anchor = textArea.getAnchor();
                final int caretPosition = textArea.getCaretPosition();
                if (e.getClickCount() < 2 &&
                        anchor != caretPosition &&
                        ((i > anchor && i < caretPosition) || (i < anchor && i > caretPosition))) {
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
                      case 1: skin.positionCaret(hit, false); break;
                      case 2: mouseDoubleClick(hit); break;
                      case 3: mouseTripleClick(hit); break;
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
                    if(macOS) {
                        textArea.extendSelection(i);
                    } else {
                        skin.positionCaret(hit, true);
                    }
                }
//                 skin.setForwardBias(hit.isLeading());
//                if (textInputControl.editable)
//                    displaySoftwareKeyboard(true);
            }
        }
    }

    @Override public void mouseDragged(MouseEvent e) {
        final TextArea textArea = getControl();
        // we never respond to events if disabled, but we do notify any onXXX
        // event listeners on the control
        if (!textArea.isDisabled() && !deferClick) {
            if (e.getButton() == MouseButton.PRIMARY && !(e.isMiddleButtonDown() || e.isSecondaryButtonDown())) {
                if (!(e.isControlDown() || e.isAltDown() || e.isShiftDown() || e.isMetaDown())) {
                    skin.positionCaret(skin.getIndex(e), true);
                }
            }
        }
    }

    @Override public void mouseReleased(MouseEvent e) {
        final TextArea textArea = getControl();
        super.mouseReleased(e);
        // we never respond to events if disabled, but we do notify any onXXX
        // event listeners on the control
        if (!textArea.isDisabled()) {
            setCaretAnimating(false);
            if (deferClick) {
                deferClick = false;
                skin.positionCaret(skin.getIndex(e), shiftDown);
                shiftDown = false;
            }
            setCaretAnimating(true);
        }
    }

    @Override protected void setCaretAnimating(boolean play) {
        skin.setCaretAnimating(play);
    }

    protected void mouseDoubleClick(HitInfo hit) {
        final TextArea textArea = getControl();
        textArea.previousWord();
        textArea.selectNextWord();
    }

    protected void mouseTripleClick(HitInfo hit) {
        // select the line
        skin.paragraphStart(false, false);
        skin.paragraphEnd(false, true);
    }

    //    public function mouseWheelMove(e:MouseEvent):Void {
//        def textBox = bind skin.control as TextBox;
//        // we never respond to events if disabled, but we do notify any onXXX
//        // event listeners on the control
//        if (not textBox.disabled) {
//            var rot = Math.abs(e.wheelRotation);
//            while (rot > 0) {
//                rot--;
//                scrollText(e.wheelRotation > 0);
//            }
//        }
//    }

}
