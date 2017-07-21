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
 * THIS SOFTWARE IS PROVIDED BY APPLE INC. AND ITS CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL APPLE INC. OR ITS CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

#include "config.h"
#include "ResourceTiming.h"

#include "CachedResource.h"
#include "SecurityOrigin.h"

namespace WebCore {

static bool passesTimingAllowCheck(const ResourceResponse& response, const SecurityOrigin& initiatorSecurityOrigin)
{
    Ref<SecurityOrigin> resourceOrigin = SecurityOrigin::create(response.url());
    if (resourceOrigin->isSameSchemeHostPort(initiatorSecurityOrigin))
        return true;

    const String& timingAllowOriginString = response.httpHeaderField(HTTPHeaderName::TimingAllowOrigin);
    if (timingAllowOriginString.isEmpty() || equalLettersIgnoringASCIICase(timingAllowOriginString, "null"))
        return false;

    if (timingAllowOriginString == "*")
        return true;

    const String& securityOrigin = initiatorSecurityOrigin.toString();
    Vector<String> timingAllowOrigins;
    timingAllowOriginString.split(',', timingAllowOrigins);
    for (auto& origin : timingAllowOrigins) {
        if (origin.stripWhiteSpace() == securityOrigin)
            return true;
    }

    return false;
}

ResourceTiming ResourceTiming::fromCache(const URL& url, const String& initiator, const LoadTiming& loadTiming)
{
    return ResourceTiming(url, initiator, loadTiming);
}

ResourceTiming ResourceTiming::fromLoad(CachedResource& resource, const String& initiator, const LoadTiming& loadTiming, const SecurityOrigin& securityOrigin)
{
    return ResourceTiming(resource, initiator, loadTiming, securityOrigin);
}

ResourceTiming ResourceTiming::fromSynchronousLoad(const URL& url, const String& initiator, const LoadTiming& loadTiming, const NetworkLoadTiming& networkLoadTiming, const ResourceResponse& response, const SecurityOrigin& securityOrigin)
{
    return ResourceTiming(url, initiator, loadTiming, networkLoadTiming, response, securityOrigin);
}

ResourceTiming::ResourceTiming(const URL& url, const String& initiator, const LoadTiming& loadTiming)
    : m_url(url)
    , m_initiator(initiator)
    , m_loadTiming(loadTiming)
    , m_allowTimingDetails(true)
{
}

ResourceTiming::ResourceTiming(CachedResource& resource, const String& initiator, const LoadTiming& loadTiming, const SecurityOrigin& securityOrigin)
    : m_url(resource.resourceRequest().url())
    , m_initiator(initiator)
    , m_loadTiming(loadTiming)
    , m_networkLoadTiming(resource.response().networkLoadTiming())
    , m_allowTimingDetails(passesTimingAllowCheck(resource.response(), securityOrigin))
{
}

ResourceTiming::ResourceTiming(const URL& url, const String& initiator, const LoadTiming& loadTiming, const NetworkLoadTiming& networkLoadTiming, const ResourceResponse& response, const SecurityOrigin& securityOrigin)
    : m_url(url)
    , m_initiator(initiator)
    , m_loadTiming(loadTiming)
    , m_networkLoadTiming(networkLoadTiming)
    , m_allowTimingDetails(passesTimingAllowCheck(response, securityOrigin))
{
}

ResourceTiming ResourceTiming::isolatedCopy() const
{
    return ResourceTiming(m_url.isolatedCopy(), m_initiator.isolatedCopy(), m_loadTiming.isolatedCopy(), m_networkLoadTiming.isolatedCopy(), m_allowTimingDetails);
}

} // namespace WebCore
