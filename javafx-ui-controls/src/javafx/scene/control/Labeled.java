/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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


import com.sun.javafx.css.Selector;
import com.sun.javafx.css.SimpleSelector;
import com.sun.javafx.css.converters.BooleanConverter;
import com.sun.javafx.css.converters.EnumConverter;
import com.sun.javafx.css.converters.InsetsConverter;
import com.sun.javafx.css.converters.PaintConverter;
import com.sun.javafx.css.converters.SizeConverter;
import com.sun.javafx.css.converters.StringConverter;
import com.sun.javafx.css.parser.CSSParser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javafx.beans.DefaultProperty;
import javafx.css.CssMetaData;
import javafx.css.FontCssMetaData;
import javafx.css.StyleOrigin;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;

import javafx.css.StyleableStringProperty;


/**
 * A Labeled {@link Control} is one which has as part of its user interface
 * a textual content associated with it. For example, a {@link Button} displays
 * {@code text}, as does a {@link Label}, a {@link Tooltip}, and many
 * other controls.
 * <p>
 * Labeled is also a convenient base class from which to extend when building
 * new Controls which, as part of their UI, display read-only textual content.
 * </p>
 *  
 * <p>Example of how to place a graphic above the text:
 * <pre><code>
 *  Image image = new Image(getClass().getResourceAsStream("image.png"));
 *  ImageView imageView = new ImageView();
 *  imageView.setImage(image);
 *  Label label = new Label("text", imageView);
 *  label.setContentDisplay(ContentDisplay.TOP);
 * </code></pre>
 *
 * @see Button
 * @see Label
 * @see ToggleButton
 */
@DefaultProperty("text")
public abstract class Labeled extends Control {

    private final static String DEFAULT_ELLIPSIS_STRING = "...";


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a Label with no text and graphic
     */
    public Labeled() { }

    /**
     * Creates a Label with text
     * @param text The text for the label.
     */
    public Labeled(String text) {
        setText(text);
    }

    /**
     * Creates a Label with text and a graphic
     * @param text The text for the label.
     * @param graphic The graphic for the label.
     */
    public Labeled(String text, Node graphic) {
        setText(text);
        setGraphic(graphic);
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/
    /**
     * The text to display in the label. The text may be null.
     */
    public final StringProperty textProperty() {
        if (text == null) {
            text = new SimpleStringProperty(this, "text", "");
        }
        return text;
    }
    private StringProperty text;
    public final void setText(String value) { textProperty().setValue(value); }
    public final String getText() { return text == null ? "" : text.getValue(); }

    /**
     * Specifies how the text and graphic within the Labeled should be
     * aligned when there is empty space within the Labeled.
     */
    public final ObjectProperty<Pos> alignmentProperty() {
        if (alignment == null) {
            alignment = new StyleableObjectProperty<Pos>(Pos.CENTER_LEFT) {

                @Override public CssMetaData getCssMetaData() {
                    return StyleableProperties.ALIGNMENT;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "alignment";
                }
            };
        }
        return alignment;
    }
    private ObjectProperty<Pos> alignment;
    public final void setAlignment(Pos value) { alignmentProperty().set(value); }
    public final Pos getAlignment() { return alignment == null ? Pos.CENTER_LEFT : alignment.get(); }


    /**
     * Specifies the behavior for lines of text <em>when text is multiline</em>
     * Unlike {@link #contentDisplayProperty} which affects the graphic and text, this setting
     * only affects multiple lines of text relative to the text bounds.
     */
    public final ObjectProperty<TextAlignment> textAlignmentProperty() {
        if (textAlignment == null) {
            textAlignment = new StyleableObjectProperty<TextAlignment>(TextAlignment.LEFT) {
                
                @Override 
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.TEXT_ALIGNMENT;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "textAlignment";
                }
            };
        }
        return textAlignment;
    }
    private ObjectProperty<TextAlignment> textAlignment;
    public final void setTextAlignment(TextAlignment value) { textAlignmentProperty().setValue(value); }
    public final TextAlignment getTextAlignment() { return textAlignment == null ? TextAlignment.LEFT : textAlignment.getValue(); }

    /**
     * Specifies the behavior to use if the text of the {@code Labeled}
     * exceeds the available space for rendering the text.
     */
    public final ObjectProperty<OverrunStyle> textOverrunProperty() {
        if (textOverrun == null) {
            textOverrun = new StyleableObjectProperty<OverrunStyle>(OverrunStyle.ELLIPSIS) {
                
                @Override 
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.TEXT_OVERRUN;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "textOverrun";
                }
            };
        }
        return textOverrun;
    }
    private ObjectProperty<OverrunStyle> textOverrun;
    public final void setTextOverrun(OverrunStyle value) { textOverrunProperty().setValue(value); }
    public final OverrunStyle getTextOverrun() { return textOverrun == null ? OverrunStyle.ELLIPSIS : textOverrun.getValue(); }

    /**
     * Specifies the string to display for the ellipsis when text is truncated.
     *
     * <table border="0" cellpadding="0" cellspacing="0"><tr><th>Examples</th></tr>
     *   <tr class="altColor"><td align="right">"..."</td>        <td>- Default value for most locales</td>
     *   <tr class="rowColor"><td align="right">" . . . "</td>    <td></td>
     *   <tr class="altColor"><td align="right">" [...] "</td>    <td></td>
     *   <tr class="rowColor"><td align="right">"&#92;u2026"</td> <td>- The Unicode ellipsis character '&hellip;'</td>
     *   <tr class="altColor"><td align="right">""</td>           <td>- No ellipsis, just display the truncated string</td>
     * </table>
     *
     * <p>Note that not all fonts support all Unicode characters.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Ellipsis#Computer_representations">Wikipedia:ellipsis</a>
     * @since 2.2
     */
    public final StringProperty ellipsisStringProperty() {
        if (ellipsisString == null) {
            ellipsisString = new StyleableStringProperty(DEFAULT_ELLIPSIS_STRING) {
                @Override public Object getBean() {
                    return Labeled.this;
                }

                @Override public String getName() {
                    return "ellipsisString";
                }

                @Override public CssMetaData getCssMetaData() {
                    return StyleableProperties.ELLIPSIS_STRING;
                }                
            };
        }
        return ellipsisString;
    }
    private StringProperty ellipsisString;
    public final void setEllipsisString(String value) { ellipsisStringProperty().set((value == null) ? "" : value); }
    public final String getEllipsisString() { return ellipsisString == null ? DEFAULT_ELLIPSIS_STRING : ellipsisString.get(); }


    /**
     * If a run of text exceeds the width of the Labeled, then this variable
     * indicates whether the text should wrap onto another line.
     */
    public final BooleanProperty wrapTextProperty() {
        if (wrapText == null) {
            wrapText = new StyleableBooleanProperty() {
                
                @Override 
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.WRAP_TEXT;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "wrapText";
                }
            };
        }
        return wrapText;
    }
    private BooleanProperty wrapText;
    public final void setWrapText(boolean value) { wrapTextProperty().setValue(value); }
    public final boolean isWrapText() { return wrapText == null ? false : wrapText.getValue(); }

    /**
     * If wrapText is true, then contentBias will be HORIZONTAL, otherwise it is null.
     * @return orientation of width/height dependency or null if there is none
     */
    @Override public Orientation getContentBias() {
        return isWrapText()? Orientation.HORIZONTAL : null;
    }

    /**
     * The default font to use for text in the Labeled. If the Label's text is
     * rich text then this font may or may not be used depending on the font
     * information embedded in the rich text, but in any case where a default
     * font is required, this font will be used.
     */
    public final ObjectProperty<Font> fontProperty() {
        if (font == null) {
            font = new StyleableObjectProperty<Font>(Font.getDefault()) {
                                
                @Override
                public void set(Font value) {
                    final Font oldValue = get();
                    if (value == null ? oldValue == null : value.equals(oldValue)) {
                        return;
                    }
                    super.set(value);
                }
                
                @Override
                protected void invalidated() {
                    // RT-20727 - if font is changed by calling setFont, then
                    // css might need to be reapplied since font size affects
                    // calculated values for styles with relative values
                    if(fontSetByCss == false) {
                        Labeled.this.impl_reapplyCSS();
                    } else {
                        fontSetByCss = false;
                    }
                }
                
                @Override 
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.FONT;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "font";
                }
            };
        }
        return font;
    }
    private boolean fontSetByCss = false;
    private ObjectProperty<Font> font;
    public final void setFont(Font value) { fontProperty().setValue(value); }
    public final Font getFont() { return font == null ? Font.getDefault() : font.getValue(); }
    

    /**
     * An optional icon for the Labeled. This can be positioned relative to the
     * text by using {@link #setContentDisplay}.  The node specified for this
     * variable cannot appear elsewhere in the scene graph, otherwise
     * the {@code IllegalArgumentException} is thrown.  See the class
     * description of {@link javafx.scene.Node Node} for more detail.
     */
    public final ObjectProperty<Node> graphicProperty() {
        if (graphic == null) {
            graphic = new StyleableObjectProperty<Node>() {

                // The graphic is styleable by css, but it is the 
                // imageUrlProperty that handles the style value. 
                @Override
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.GRAPHIC;
                }
                                
                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "graphic";
                }
            };
        }
        return graphic;
    }
    private ObjectProperty<Node> graphic;
    public final void setGraphic(Node value) {
        graphicProperty().setValue(value);
    }
    public final Node getGraphic() { return graphic == null ? null : graphic.getValue(); }

    private StringProperty imageUrl = null;
    /**
     * The imageUrl property is set from CSS and then the graphic property is
     * set from the invalidated method. This ensures that the same image isn't
     * reloaded. 
     */
    private StringProperty imageUrlProperty() {
        if (imageUrl == null) {
            imageUrl = new StyleableStringProperty() {

                @Override
                public void applyStyle(StyleOrigin origin, String v) {
                    super.applyStyle(origin, v);
                    if (v == null) {
                        ((StyleableProperty)graphicProperty()).applyStyle(origin, null);
                    } else if (v.startsWith(CSSParser.SPECIAL_REGION_URL_PREFIX)) {
                        final Region region = new Region();
                        final String styleClassOrId = v.substring(CSSParser.SPECIAL_REGION_URL_PREFIX.length());
                        if (styleClassOrId.length() > 0) {
                            Selector s = Selector.createSelector(styleClassOrId);
                            if (s instanceof SimpleSelector) {
                                SimpleSelector ss = (SimpleSelector)s;
                                region.setId(ss.getId());
                                region.getStyleClass().addAll(ss.getStyleClasses());
                            }
                        }
                        ((StyleableProperty)graphicProperty()).applyStyle(origin, region);
                    } else {
                        URL url = null;
                        try {
                            url = new URL(v);
                        } catch (MalformedURLException malf) {
                            // This may be a relative URL, so try resolving it using the application classloader
                            final ClassLoader cl = Thread.currentThread().getContextClassLoader();
                            url = cl.getResource(v);
                        }
                        if (url != null) {
                            ((StyleableProperty)graphicProperty()).applyStyle(origin, new ImageView(new Image(url.toExternalForm())));
                        }
                    }
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "imageUrl";
                }

                @Override
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.GRAPHIC;
                }
                
            };
        }
        return imageUrl;
    }
    
    /**
     * Whether all text should be underlined.
     */
    public final BooleanProperty underlineProperty() {
        if (underline == null) {
            underline = new StyleableBooleanProperty(false) {

                @Override
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.UNDERLINE;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "underline";
                }
            };
        }
        return underline;
    }
    private BooleanProperty underline;
    public final void setUnderline(boolean value) { underlineProperty().setValue(value); }
    public final boolean isUnderline() { return underline == null ? false : underline.getValue(); }

    /**
     * Specifies the space in pixel between lines.
     */
    public final DoubleProperty lineSpacingProperty() {
        if (lineSpacing == null) {
            lineSpacing = new StyleableDoubleProperty(0) {

                @Override
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.LINE_SPACING;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "lineSpacing";
                }
            };
        }
        return lineSpacing;
    }
    private DoubleProperty lineSpacing;
    public final void setLineSpacing(double value) { lineSpacingProperty().setValue(value); }
    public final double getLineSpacing() { return lineSpacing == null ? 0 : lineSpacing.getValue(); }

    /**
     * Specifies the positioning of the graphic relative to the text.
     */
    public final ObjectProperty<ContentDisplay> contentDisplayProperty() {
        if (contentDisplay == null) {
            contentDisplay = new StyleableObjectProperty<ContentDisplay>(ContentDisplay.LEFT) {
                
                @Override 
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.CONTENT_DISPLAY;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "contentDisplay";
                }
            };
        }
        return contentDisplay;
    }
    private ObjectProperty<ContentDisplay> contentDisplay;
    public final void setContentDisplay(ContentDisplay value) { contentDisplayProperty().setValue(value); }
    public final ContentDisplay getContentDisplay() { return contentDisplay == null ? ContentDisplay.LEFT : contentDisplay.getValue(); }

    /**
     * The padding around the Labeled's text and graphic content.
     * By default labelPadding is Insets.EMPTY and cannot be set to null.
     * Subclasses may add nodes outside this padding and inside the Labeled's padding.
     *
     * This property can only be set from CSS.
     */
    public final ReadOnlyObjectProperty<Insets> labelPaddingProperty() {
        return labelPaddingPropertyImpl();
    }
    private ObjectProperty<Insets> labelPaddingPropertyImpl() {
        if (labelPadding == null) {
            labelPadding = new StyleableObjectProperty<Insets>(Insets.EMPTY) {
                private Insets lastValidValue = Insets.EMPTY;

                @Override
                public void invalidated() {
                    final Insets newValue = get();
                    if (newValue == null) {
                        set(lastValidValue);
                        throw new NullPointerException("cannot set labelPadding to null");
                    }
                    lastValidValue = newValue;
                    requestLayout();
                }
                
                @Override
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.LABEL_PADDING;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "labelPadding";
                }
            };
        }
        return labelPadding;
    }
    private ObjectProperty<Insets> labelPadding;
    private void setLabelPadding(Insets value) { labelPaddingPropertyImpl().set(value); }
    public final Insets getLabelPadding() { return labelPadding == null ? Insets.EMPTY : labelPadding.get(); }

    /**
     * The amount of space between the graphic and text
     */
    public final DoubleProperty graphicTextGapProperty() {
        if (graphicTextGap == null) {
            graphicTextGap = new StyleableDoubleProperty(4) {
                
                @Override
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.GRAPHIC_TEXT_GAP;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "graphicTextGap";
                }
            };
        }
        return graphicTextGap;
    }
    private DoubleProperty graphicTextGap;
    public final void setGraphicTextGap(double value) { graphicTextGapProperty().setValue(value); }
    public final double getGraphicTextGap() { return graphicTextGap == null ? 4 : graphicTextGap.getValue(); }


    /**
     * The {@link Paint} used to fill the text.
     */
    private ObjectProperty<Paint> textFill; // TODO for now change this

    public final void setTextFill(Paint value) {
        textFillProperty().set(value);
    }

    public final Paint getTextFill() {
        return textFill == null ? Color.BLACK : textFill.get();
    }

    public final ObjectProperty<Paint> textFillProperty() {
        if (textFill == null) {
            textFill = new StyleableObjectProperty<Paint>(Color.BLACK) {
                
                @Override 
                public CssMetaData getCssMetaData() {
                    return StyleableProperties.TEXT_FILL;
                }

                @Override
                public Object getBean() {
                    return Labeled.this;
                }

                @Override
                public String getName() {
                    return "textFill";
                }
            };
        }
        return textFill;
    }


    /**
     * MnemonicParsing property to enable/disable text parsing.
     * If this is set to true, then the Label text will be
     * parsed to see if it contains the mnemonic parsing character '_'.
     * When a mnemonic is detected the key combination will
     * be determined based on the succeeding character, and the mnemonic
     * added.
     * 
     * <p>
     * The default value for Labeled is false, but it
     * is enabled by default on some Controls.
     * </p>
     */
    private BooleanProperty mnemonicParsing;
    public final void setMnemonicParsing(boolean value) {
        mnemonicParsingProperty().set(value);
    }
    public final boolean isMnemonicParsing() {
        return mnemonicParsing == null ? false : mnemonicParsing.get();
    }
    public final BooleanProperty mnemonicParsingProperty() {
        if (mnemonicParsing == null) {
            mnemonicParsing = new SimpleBooleanProperty(this, "mnemonicParsing");
        }
        return mnemonicParsing;
    }

    //    /**
    //     * This is the symbol that is searched for in the text and used as
    //     * a mnemonic. You can change what symbol is used. Using the symbol
    //     * more than once will cause the symbol to be escaped. Thus, if "_"
    //     * (the default) is used, then the string "H_ello World" will use
    //     * "e" as the mnemonic. If "H__ello World" is used, then no mnemonic
    //     * will be used and the text will be rendered as "H_ello World".
    //     * TODO: Have i18n review this part of the API to confirm proper
    //     * externalization will work as expected
    //     */

    /***************************************************************************
     *                                                                         *
     * Stylesheet Handling                                                     *
     *                                                                         *
     **************************************************************************/

     /**
      * Not everything uses the default value of false for alignment. 
      * This method provides a way to have them return the correct initial value.
      * @treatAsPrivate implementation detail
      */
    @Deprecated
    protected Pos impl_cssGetAlignmentInitialValue() {
        return Pos.CENTER_LEFT;
    }
    
     /**
      * @treatAsPrivate implementation detail
      */
    private static class StyleableProperties {
        private static final FontCssMetaData<Labeled> FONT = 
            new FontCssMetaData<Labeled>("-fx-font", Font.getDefault()) {

            @Override
            public void set(Labeled styleable, Font value, StyleOrigin origin) {
                // RT-20727 - if font is changed by calling setFont, then
                // css might need to be reapplied since font size affects
                // calculated values for styles with relative values
                styleable.fontSetByCss = origin != StyleOrigin.USER;
                super.set(styleable, value, origin);                
            }

            @Override
            public boolean isSettable(Labeled n) {
                return n.font == null || !n.font.isBound();
            }

            @Override
            public StyleableProperty<Font> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.fontProperty();
            }
        };
        
        private static final CssMetaData<Labeled,Pos> ALIGNMENT = 
                new CssMetaData<Labeled,Pos>("-fx-alignment",
                new EnumConverter<Pos>(Pos.class), Pos.CENTER_LEFT ) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.alignment == null || !n.alignment.isBound();
            }

            @Override
            public StyleableProperty<Pos> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.alignmentProperty();
            }
            
            @Override
            public Pos getInitialValue(Labeled n) {
                return n.impl_cssGetAlignmentInitialValue();
            }
        };
        
        private static final CssMetaData<Labeled,TextAlignment> TEXT_ALIGNMENT = 
                new CssMetaData<Labeled,TextAlignment>("-fx-text-alignment",
                new EnumConverter<TextAlignment>(TextAlignment.class),
                TextAlignment.LEFT) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.textAlignment == null || !n.textAlignment.isBound();
            }

            @Override
            public StyleableProperty<TextAlignment> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.textAlignmentProperty();
            }
        };
        
        private static final CssMetaData<Labeled,Paint> TEXT_FILL = 
                new CssMetaData<Labeled,Paint>("-fx-text-fill",
                PaintConverter.getInstance(), Color.BLACK) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.textFill == null || !n.textFill.isBound();
            }

            @Override
            public StyleableProperty<Paint> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.textFillProperty();
            }
        };
        
        private static final CssMetaData<Labeled,OverrunStyle> TEXT_OVERRUN = 
                new CssMetaData<Labeled,OverrunStyle>("-fx-text-overrun",
                new EnumConverter<OverrunStyle>(OverrunStyle.class), 
                OverrunStyle.ELLIPSIS) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.textOverrun == null || !n.textOverrun.isBound();
            }

            @Override
            public StyleableProperty<OverrunStyle> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.textOverrunProperty();
            }
        };

        private static final CssMetaData<Labeled,String> ELLIPSIS_STRING =
                new CssMetaData<Labeled,String>("-fx-ellipsis-string",
                StringConverter.getInstance(), DEFAULT_ELLIPSIS_STRING) {

            @Override public boolean isSettable(Labeled n) {
                return n.ellipsisString == null || !n.ellipsisString.isBound();
            }

            @Override public StyleableProperty<String> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.ellipsisStringProperty();
            }
        };

        private static final CssMetaData<Labeled,Boolean> WRAP_TEXT = 
                new CssMetaData<Labeled,Boolean>("-fx-wrap-text",
                BooleanConverter.getInstance(), false) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.wrapText == null || !n.wrapText.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.wrapTextProperty();
            }
        };
        
        private static final CssMetaData<Labeled,String> GRAPHIC = 
            new CssMetaData<Labeled,String>("-fx-graphic",
                StringConverter.getInstance()) {

            @Override
            public boolean isSettable(Labeled n) {
                // Note that we care about the graphic, not imageUrl
                return n.graphic == null || !n.graphic.isBound();
            }

            @Override
            public StyleableProperty<String> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.imageUrlProperty();
            }
        };
        
        private static final CssMetaData<Labeled,Boolean> UNDERLINE = 
            new CssMetaData<Labeled,Boolean>("-fx-underline",
                BooleanConverter.getInstance(), Boolean.FALSE) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.underline == null || !n.underline.isBound();
            }

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.underlineProperty();
            }
        };
        
        private static final CssMetaData<Labeled,Number> LINE_SPACING =
            new CssMetaData<Labeled,Number>("-fx-line-spacing",
                SizeConverter.getInstance(), 0) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.lineSpacing == null || !n.lineSpacing.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.lineSpacingProperty();
            }
        };

        private static final CssMetaData<Labeled,ContentDisplay> CONTENT_DISPLAY = 
            new CssMetaData<Labeled,ContentDisplay>("-fx-content-display",
                new EnumConverter<ContentDisplay>(ContentDisplay.class), 
                ContentDisplay.LEFT) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.contentDisplay == null || !n.contentDisplay.isBound();
            }

            @Override
            public StyleableProperty<ContentDisplay> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.contentDisplayProperty();
            }
        };
        
        private static final CssMetaData<Labeled,Insets> LABEL_PADDING = 
            new CssMetaData<Labeled,Insets>("-fx-label-padding",
                InsetsConverter.getInstance(), Insets.EMPTY) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.labelPadding == null || !n.labelPadding.isBound();
            }

            @Override
            public StyleableProperty<Insets> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.labelPaddingPropertyImpl();
            }
        };
        
        private static final CssMetaData<Labeled,Number> GRAPHIC_TEXT_GAP = 
            new CssMetaData<Labeled,Number>("-fx-graphic-text-gap",
                SizeConverter.getInstance(), 4.0) {

            @Override
            public boolean isSettable(Labeled n) {
                return n.graphicTextGap == null || !n.graphicTextGap.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(Labeled n) {
                return (StyleableProperty)n.graphicTextGapProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<CssMetaData<? extends Styleable, ?>>(Control.getClassCssMetaData());
            Collections.addAll(styleables,
                FONT,
                ALIGNMENT,
                TEXT_ALIGNMENT,
                TEXT_FILL,
                TEXT_OVERRUN,
                ELLIPSIS_STRING,
                WRAP_TEXT,
                GRAPHIC,
                UNDERLINE,
                LINE_SPACING,
                CONTENT_DISPLAY,
                LABEL_PADDING,
                GRAPHIC_TEXT_GAP
            );
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    /**
     * @return The CssMetaData associated with this class, which may include the
     * CssMetaData of its super classes.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

 }
