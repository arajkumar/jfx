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

package javafx.geometry;

/**
Builder class for javafx.geometry.Rectangle2D
@see javafx.geometry.Rectangle2D
@deprecated This class is deprecated and will be removed in the next version
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class Rectangle2DBuilder<B extends javafx.geometry.Rectangle2DBuilder<B>> implements javafx.util.Builder<javafx.geometry.Rectangle2D> {
    protected Rectangle2DBuilder() {
    }
    
    /** Creates a new instance of Rectangle2DBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.geometry.Rectangle2DBuilder<?> create() {
        return new javafx.geometry.Rectangle2DBuilder();
    }
    
    private double height;
    /**
    Set the value of the {@link javafx.geometry.Rectangle2D#getHeight() height} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B height(double x) {
        this.height = x;
        return (B) this;
    }
    
    private double minX;
    /**
    Set the value of the {@link javafx.geometry.Rectangle2D#getMinX() minX} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B minX(double x) {
        this.minX = x;
        return (B) this;
    }
    
    private double minY;
    /**
    Set the value of the {@link javafx.geometry.Rectangle2D#getMinY() minY} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B minY(double x) {
        this.minY = x;
        return (B) this;
    }
    
    private double width;
    /**
    Set the value of the {@link javafx.geometry.Rectangle2D#getWidth() width} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B width(double x) {
        this.width = x;
        return (B) this;
    }
    
    /**
    Make an instance of {@link javafx.geometry.Rectangle2D} based on the properties set on this builder.
    */
    public javafx.geometry.Rectangle2D build() {
        javafx.geometry.Rectangle2D x = new javafx.geometry.Rectangle2D(this.minX, this.minY, this.width, this.height);
        return x;
    }
}
