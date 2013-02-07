/*
 * Copyright (c) 2009, 2013 Oracle and/or its affiliates. All rights reserved.
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

/*
 * This file was originally generated by JSLC
 * and then hand edited for performance.
 */

#include <jni.h>
#include "com_sun_scenario_effect_impl_sw_sse_SSEBoxBlurPeer.h"

JNIEXPORT void JNICALL
Java_com_sun_scenario_effect_impl_sw_sse_SSEBoxBlurPeer_filterHorizontal
    (JNIEnv *env, jclass klass,
     jintArray dstPixels_arr, jint dstw, jint dsth, jint dstscan,
     jintArray srcPixels_arr, jint srcw, jint srch, jint srcscan)
{
    jint *srcPixels = (jint *)env->GetPrimitiveArrayCritical(srcPixels_arr, 0);
    if (srcPixels == NULL) return;
    jint *dstPixels = (jint *)env->GetPrimitiveArrayCritical(dstPixels_arr, 0);
    if (dstPixels == NULL) {
        env->ReleasePrimitiveArrayCritical(srcPixels_arr, srcPixels, JNI_ABORT);
        return;
    }

    jint hsize = dstw - srcw + 1;
    jint kscale = 0x7fffffff / (hsize * 255);
    jint srcoff = 0;
    jint dstoff = 0;
    for (jint y = 0; y < dsth; y++) {
        jint suma = 0;
        jint sumr = 0;
        jint sumg = 0;
        jint sumb = 0;
        for (jint x = 0; x < dstw; x++) {
            jint rgb;
            // Un-accumulate the data for col-hsize location into the sums.
            rgb = (x >= hsize) ? srcPixels[srcoff + x - hsize] : 0;
            suma -= (rgb >> 24) & 0xff;
            sumr -= (rgb >> 16) & 0xff;
            sumg -= (rgb >>  8) & 0xff;
            sumb -= (rgb      ) & 0xff;
            // Accumulate the data for this col location into the sums.
            rgb = (x < srcw) ? srcPixels[srcoff + x] : 0;
            suma += (rgb >> 24) & 0xff;
            sumr += (rgb >> 16) & 0xff;
            sumg += (rgb >>  8) & 0xff;
            sumb += (rgb      ) & 0xff;
            dstPixels[dstoff + x] =
                (((suma * kscale) >> 23) << 24) +
                (((sumr * kscale) >> 23) << 16) +
                (((sumg * kscale) >> 23) <<  8) +
                (((sumb * kscale) >> 23)      );
        }
        srcoff += srcscan;
        dstoff += dstscan;
    }

    env->ReleasePrimitiveArrayCritical(dstPixels_arr, dstPixels, 0);
    env->ReleasePrimitiveArrayCritical(srcPixels_arr, srcPixels, JNI_ABORT);
}

JNIEXPORT void JNICALL
Java_com_sun_scenario_effect_impl_sw_sse_SSEBoxBlurPeer_filterVertical
    (JNIEnv *env, jclass klass,
     jintArray dstPixels_arr, jint dstw, jint dsth, jint dstscan,
     jintArray srcPixels_arr, jint srcw, jint srch, jint srcscan)
{
    jint *srcPixels = (jint *)env->GetPrimitiveArrayCritical(srcPixels_arr, 0);
    if (srcPixels == NULL) return;
    jint *dstPixels = (jint *)env->GetPrimitiveArrayCritical(dstPixels_arr, 0);
    if (dstPixels == NULL) {
        env->ReleasePrimitiveArrayCritical(srcPixels_arr, srcPixels, JNI_ABORT);
        return;
    }

    jint vsize = dsth - srch + 1;
    jint kscale = 0x7fffffff / (vsize * 255);
    jint voff = vsize * srcscan;
    for (jint x = 0; x < dstw; x++) {
        jint suma = 0;
        jint sumr = 0;
        jint sumg = 0;
        jint sumb = 0;
        jint srcoff = x;
        jint dstoff = x;
        for (jint y = 0; y < dsth; y++) {
            jint rgb;
            // Un-accumulate the data for row-vsize location into the sums.
            rgb = (srcoff >= voff) ? srcPixels[srcoff - voff] : 0;
            suma -= (rgb >> 24) & 0xff;
            sumr -= (rgb >> 16) & 0xff;
            sumg -= (rgb >>  8) & 0xff;
            sumb -= (rgb      ) & 0xff;
            // Accumulate the data for this col location into the sums.
            rgb = (y < srch) ? srcPixels[srcoff] : 0;
            suma += (rgb >> 24) & 0xff;
            sumr += (rgb >> 16) & 0xff;
            sumg += (rgb >>  8) & 0xff;
            sumb += (rgb      ) & 0xff;
            dstPixels[dstoff] =
                (((suma * kscale) >> 23) << 24) +
                (((sumr * kscale) >> 23) << 16) +
                (((sumg * kscale) >> 23) <<  8) +
                (((sumb * kscale) >> 23)      );
            srcoff += srcscan;
            dstoff += dstscan;
        }
    }

    env->ReleasePrimitiveArrayCritical(dstPixels_arr, dstPixels, 0);
    env->ReleasePrimitiveArrayCritical(srcPixels_arr, srcPixels, JNI_ABORT);
}

#if 0
/*
 * This is a useful routine for some uses - it goes faster than the
 * horizontal-only and vertical-only loops, but it is hard to use it
 * in the face of multi-pass box blurs and having to adjust for even
 * blur sizes, so it is commented out for now...
 */
JNIEXPORT void JNICALL
Java_com_sun_scenario_effect_impl_sw_sse_SSEBoxBlurPeer_filterTranspose
    (JNIEnv *env, jclass klass,
     jintArray dstPixels_arr, jint dstw, jint dsth, jint dstscan,
     jintArray srcPixels_arr, jint srcw, jint srch, jint srcscan,
     jint ksize)
{
    jint *srcPixels = (jint *)env->GetPrimitiveArrayCritical(srcPixels_arr, 0);
    if (srcPixels == NULL) return;
    jint *dstPixels = (jint *)env->GetPrimitiveArrayCritical(dstPixels_arr, 0);
    if (dstPixels == NULL) {
        env->ReleasePrimitiveArrayCritical(srcPixels_arr, srcPixels, JNI_ABORT);
        return;
    }

    jint kscale = 0x7fffffff / (ksize * 255);
    jint srcoff = 0;
    for (jint y = 0; y < dstw; y++) {
        jint suma = 0;
        jint sumr = 0;
        jint sumg = 0;
        jint sumb = 0;
        jint dstoff = y;
        for (jint x = 0; x < dsth; x++) {
            jint rgb;
            // Un-accumulate the data for col-ksize location into the sums.
            rgb = (x >= ksize) ? srcPixels[srcoff + x - ksize] : 0;
            suma -= (rgb >> 24) & 0xff;
            sumr -= (rgb >> 16) & 0xff;
            sumg -= (rgb >>  8) & 0xff;
            sumb -= (rgb      ) & 0xff;
            // Accumulate the data for this col location into the sums.
            rgb = (x < srcw) ? srcPixels[srcoff + x] : 0;
            suma += (rgb >> 24) & 0xff;
            sumr += (rgb >> 16) & 0xff;
            sumg += (rgb >>  8) & 0xff;
            sumb += (rgb      ) & 0xff;
            dstPixels[dstoff] =
                (((suma * kscale) >> 23) << 24) +
                (((sumr * kscale) >> 23) << 16) +
                (((sumg * kscale) >> 23) <<  8) +
                (((sumb * kscale) >> 23)      );
            dstoff += dstscan;
        }
        srcoff += srcscan;
    }

    env->ReleasePrimitiveArrayCritical(dstPixels_arr, dstPixels, 0);
    env->ReleasePrimitiveArrayCritical(srcPixels_arr, srcPixels, JNI_ABORT);
}
#endif
