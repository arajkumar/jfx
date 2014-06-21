/* 
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
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
Builder class for javafx.scene.control.CheckBox
@see javafx.scene.control.CheckBox
@deprecated This class is deprecated and will be removed in the next version
* @since JavaFX 2.0
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class CheckBoxBuilder<B extends javafx.scene.control.CheckBoxBuilder<B>> extends javafx.scene.control.ButtonBaseBuilder<B> implements javafx.util.Builder<javafx.scene.control.CheckBox> {
    protected CheckBoxBuilder() {
    }
    
    /** Creates a new instance of CheckBoxBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.scene.control.CheckBoxBuilder<?> create() {
        return new javafx.scene.control.CheckBoxBuilder();
    }
    
    private int __set;
    public void applyTo(javafx.scene.control.CheckBox x) {
        super.applyTo(x);
        int set = __set;
        if ((set & (1 << 0)) != 0) x.setAllowIndeterminate(this.allowIndeterminate);
        if ((set & (1 << 1)) != 0) x.setIndeterminate(this.indeterminate);
        if ((set & (1 << 2)) != 0) x.setSelected(this.selected);
    }
    
    private boolean allowIndeterminate;
    /**
    Set the value of the {@link javafx.scene.control.CheckBox#isAllowIndeterminate() allowIndeterminate} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B allowIndeterminate(boolean x) {
        this.allowIndeterminate = x;
        __set |= 1 << 0;
        return (B) this;
    }
    
    private boolean indeterminate;
    /**
    Set the value of the {@link javafx.scene.control.CheckBox#isIndeterminate() indeterminate} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B indeterminate(boolean x) {
        this.indeterminate = x;
        __set |= 1 << 1;
        return (B) this;
    }
    
    private boolean selected;
    /**
    Set the value of the {@link javafx.scene.control.CheckBox#isSelected() selected} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B selected(boolean x) {
        this.selected = x;
        __set |= 1 << 2;
        return (B) this;
    }
    
    /**
    Make an instance of {@link javafx.scene.control.CheckBox} based on the properties set on this builder.
    */
    public javafx.scene.control.CheckBox build() {
        javafx.scene.control.CheckBox x = new javafx.scene.control.CheckBox();
        applyTo(x);
        return x;
    }
}
