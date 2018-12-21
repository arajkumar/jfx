/*
 * Copyright (C) 2017 Apple Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY APPLE INC. ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL APPLE INC. OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#pragma once

#if ENABLE(WEBGPU)

#include "GPURenderCommandEncoder.h"
#include <wtf/Ref.h>
#include <wtf/RefCounted.h>

namespace WebCore {

class WebGPUBuffer;
class WebGPUDepthStencilState;
class WebGPURenderPipelineState;

class WebGPURenderCommandEncoder : public RefCounted<WebGPURenderCommandEncoder> {
public:
    ~WebGPURenderCommandEncoder();
    static Ref<WebGPURenderCommandEncoder> create(GPURenderCommandEncoder&&);

    void setRenderPipelineState(WebGPURenderPipelineState&);
    void setDepthStencilState(WebGPUDepthStencilState&);
    void setVertexBuffer(WebGPUBuffer&, unsigned offset, unsigned index);
    void setFragmentBuffer(WebGPUBuffer&, unsigned offset, unsigned index);
    void drawPrimitives(unsigned type, unsigned start, unsigned count);
    void endEncoding();

    GPURenderCommandEncoder& encoder() { return m_encoder; }

private:
    explicit WebGPURenderCommandEncoder(GPURenderCommandEncoder&&);

    GPURenderCommandEncoder m_encoder;
};

} // namespace WebCore

#endif
