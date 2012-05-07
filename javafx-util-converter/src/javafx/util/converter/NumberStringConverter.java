/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
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
package javafx.util.converter;

import com.sun.javafx.beans.annotations.NoBuilder;
import java.text.*;
import java.util.Locale;
import javafx.util.StringConverter;

/**
 * <p>{@link StringConverter} implementation for {@link Number} values.</p>
 */
@NoBuilder
public class NumberStringConverter extends FormatStringConverter<Number> {
    
    // ------------------------------------------------------ Private properties

    final Locale locale;
    final String pattern;
//    final NumberFormat numberFormat;
    
    // ------------------------------------------------------------ Constructors
    public NumberStringConverter() {
        this(Locale.getDefault());
    }
    
    public NumberStringConverter(Locale locale) {
        this(locale, null);
    }
    
    public NumberStringConverter(String pattern) {
        this(Locale.getDefault(), pattern);
    }
    
    public NumberStringConverter(Locale locale, String pattern) {
        this(locale, pattern, null);
    }
    
    public NumberStringConverter(NumberFormat numberFormat) {
        this(null, null, numberFormat);
    }
    
    NumberStringConverter(Locale locale, String pattern, NumberFormat numberFormat) {
        super(numberFormat);
        this.locale = locale;
        this.pattern = pattern;
    }

    // ------------------------------------------------------- Converter Methods

    /**
     * <p>Return a <code>NumberFormat</code> instance to use for formatting
     * and parsing in this {@link StringConverter}.</p>
     */
    @Override protected Format getFormat() {
        Locale _locale = locale == null ? Locale.getDefault() : locale;
        
        if (format != null) {
            return format;
        } else if (pattern != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(_locale);
            return new DecimalFormat(pattern, symbols);
        } else {
            return NumberFormat.getNumberInstance(_locale);
        }
    }
}
