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

#include "config.h"
#include "WebGPURenderPipelineDescriptor.h"

#if ENABLE(WEBGPU)

#include "GPUFunction.h"
#include "GPURenderPipelineColorAttachmentDescriptor.h"
#include "WebGPUFunction.h"
#include "WebGPURenderPipelineColorAttachmentDescriptor.h"

namespace WebCore {

Ref<WebGPURenderPipelineDescriptor> WebGPURenderPipelineDescriptor::create()
{
    return adoptRef(*new WebGPURenderPipelineDescriptor);
}

WebGPURenderPipelineDescriptor::WebGPURenderPipelineDescriptor() = default;

WebGPURenderPipelineDescriptor::~WebGPURenderPipelineDescriptor() = default;

WebGPUFunction* WebGPURenderPipelineDescriptor::vertexFunction() const
{
    return m_vertexFunction.get();
}

void WebGPURenderPipelineDescriptor::setVertexFunction(RefPtr<WebGPUFunction>&& newVertexFunction)
{
    // FIXME: Why can't we set this to null?
    if (!newVertexFunction)
        return;

    m_vertexFunction = WTFMove(newVertexFunction);

    m_descriptor.setVertexFunction(m_vertexFunction->function());
}

WebGPUFunction* WebGPURenderPipelineDescriptor::fragmentFunction() const
{
    return m_fragmentFunction.get();
}

void WebGPURenderPipelineDescriptor::setFragmentFunction(RefPtr<WebGPUFunction>&& newFragmentFunction)
{
    // FIXME: Why can't we set this to null?
    if (!newFragmentFunction)
        return;

    m_fragmentFunction = WTFMove(newFragmentFunction);

    m_descriptor.setFragmentFunction(m_fragmentFunction->function());
}

const Vector<RefPtr<WebGPURenderPipelineColorAttachmentDescriptor>>& WebGPURenderPipelineDescriptor::colorAttachments()
{
    if (!m_colorAttachments.size()) {
        auto attachments = m_descriptor.colorAttachments();
        m_colorAttachments.reserveInitialCapacity(attachments.size());
        for (auto& attachment : attachments)
            m_colorAttachments.uncheckedAppend(WebGPURenderPipelineColorAttachmentDescriptor::create(WTFMove(attachment)));
    }
    return m_colorAttachments;
}

unsigned WebGPURenderPipelineDescriptor::depthAttachmentPixelFormat() const
{
    return m_descriptor.depthAttachmentPixelFormat();
}

void WebGPURenderPipelineDescriptor::setDepthAttachmentPixelFormat(unsigned newPixelFormat)
{
    m_descriptor.setDepthAttachmentPixelFormat(newPixelFormat);
}

void WebGPURenderPipelineDescriptor::reset()
{
    m_vertexFunction = nullptr;
    m_fragmentFunction = nullptr;

    // FIXME: Why doesn't this clear out the functions on m_descriptor?
}

} // namespace WebCore

#endif
