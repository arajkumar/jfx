/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 */
package com.sun.javafx.webkit.prism;

import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.prism.Graphics;
import com.sun.prism.GraphicsPipeline;
import com.sun.prism.Image;
import com.sun.prism.PixelFormat;
import com.sun.prism.PrinterGraphics;
import com.sun.prism.RTTexture;
import com.sun.prism.ResourceFactory;
import com.sun.prism.ResourceFactoryListener;
import com.sun.prism.Texture;
import com.sun.webkit.Invoker;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 * @author Alexey.Ushakov
 */
final class RTImage extends PrismImage implements ResourceFactoryListener {
    private RTTexture txt;
    private final int width, height;
    private boolean listenerAdded = false;
    private ByteBuffer pixelBuffer;
    private float pixelScale;

    RTImage(int w, int h, float pixelScale) {
        width = w;
        height = h;
        this.pixelScale = pixelScale;
    }

    @Override
    Image getImage() {
         return Image.fromByteBgraPreData(
                getPixelBuffer(),
                getWidth(), getHeight());
    }

    @Override
    Graphics getGraphics() {
        Graphics g = getTexture().createGraphics();
        g.scale(pixelScale, pixelScale);
        return g;
    }

    private RTTexture getTexture() {
        if (txt == null) {
            ResourceFactory f = GraphicsPipeline.getDefaultResourceFactory();
            txt = f.createRTTexture(
                    (int) Math.ceil(width * pixelScale),
                    (int) Math.ceil(height * pixelScale),
                    Texture.WrapMode.CLAMP_NOT_NEEDED);
            txt.contentsUseful();
            txt.makePermanent();
            if (! listenerAdded) {
                f.addFactoryListener(this);
                listenerAdded = true;
            }
        }
        return txt;
    }

    @Override
    void draw(Graphics g,
            int dstx1, int dsty1, int dstx2, int dsty2,
            int srcx1, int srcy1, int srcx2, int srcy2)
    {
        if (txt == null) {
            return;
        }
        if (g instanceof PrinterGraphics) {
            // We're printing. Copy [txt] into a J2DTexture and draw it.
            int w = srcx2 - srcx1;
            int h = srcy2 - srcy1;
            final IntBuffer pixels = IntBuffer.allocate(w * h);

            PrismInvoker.runOnRenderThread(new Runnable() {
                public void run() {
                    getTexture().readPixels(pixels);
                }
            });
            Image img = Image.fromIntArgbPreData(pixels, w, h);
            Texture t = g.getResourceFactory().createTexture(
                    img, Texture.Usage.STATIC, Texture.WrapMode.CLAMP_NOT_NEEDED);
            g.drawTexture(t,
                    dstx1, dsty1, dstx2, dsty2,
                    0, 0, w, h);
            t.dispose();
        } else {
            g.drawTexture(getTexture(),
                    dstx1, dsty1, dstx2, dsty2,
                    srcx1 * pixelScale, srcy1 * pixelScale,
                    srcx2 * pixelScale, srcy2 * pixelScale);
        }
    }

    @Override
    void dispose() {
        PrismInvoker.invokeOnRenderThread(new Runnable() {
            public void run() {
                if (txt != null) {
                    txt.dispose();
                    txt = null;
                }
            }
        });
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public ByteBuffer getPixelBuffer() {
        boolean isNew = false;
        if (pixelBuffer == null) {
            pixelBuffer = ByteBuffer.allocateDirect(width*height*4);
            if (pixelBuffer != null) {
                pixelBuffer.order(ByteOrder.nativeOrder());
                isNew = true;
            }
        }
        if (isNew || isDirty()) {
            PrismInvoker.runOnRenderThread(new Runnable() {
                public void run() {
                    flushRQ();
                    if (txt != null && pixelBuffer != null) {
                        PixelFormat pf = txt.getPixelFormat();
                        if (pf != PixelFormat.INT_ARGB_PRE &&
                            pf != PixelFormat.BYTE_BGRA_PRE) {

                            throw new AssertionError("Unexpected pixel format: " + pf);
                        }

                        RTTexture t = txt;
                        if (pixelScale != 1.0f) {
                            // Convert [txt] to a texture the size of the image
                            ResourceFactory f = GraphicsPipeline.getDefaultResourceFactory();
                            t = f.createRTTexture(width, height, Texture.WrapMode.CLAMP_NOT_NEEDED);
                            Graphics g = t.createGraphics();
                            g.drawTexture(txt, 0, 0, width, height,
                                    0, 0, width * pixelScale, height * pixelScale);
                        }
                        
                        pixelBuffer.rewind();
                        int[] pixels = t.getPixels();
                        if (pixels != null) {
                            pixelBuffer.asIntBuffer().put(pixels);
                        } else {
                            t.readPixels(pixelBuffer);
                        }

                        if (t != txt) {
                            t.dispose();
                        }
                    }
                }
            });
        }
        return pixelBuffer;
    }

    // This method is called from native [ImageBufferData::update]
    // while lazy painting procedure
    @Override
    protected void drawPixelBuffer() {
        PrismInvoker.invokeOnRenderThread(new Runnable() {
            public void run() {
                //[g] field can be null if it is the first paint
                //from synthetic ImageData.
                Graphics g = getGraphics();
                if (g != null && pixelBuffer != null) {
                    pixelBuffer.rewind();//critical!
                    Image img = Image.fromByteBgraPreData(
                            pixelBuffer,
                            width,
                            height);
                    Texture txt = g.getResourceFactory().createTexture(img, Texture.Usage.DEFAULT, Texture.WrapMode.CLAMP_NOT_NEEDED);
                    g.clear();
                    g.drawTexture(txt, 0, 0, width, height);
                    txt.dispose();
                }
            }
        });
    }

    @Override public void factoryReset() {
        if (txt != null) {
            txt.dispose();
            txt = null;
        }
    }

    @Override public void factoryReleased() {
    }
}
