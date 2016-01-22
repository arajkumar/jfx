/*
 * Copyright (C) 2008 Apple Inc. All rights reserved.
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
 * THIS SOFTWARE IS PROVIDED BY APPLE COMPUTER, INC. ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL APPLE COMPUTER, INC. OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#ifndef OpaqueJSString_h
#define OpaqueJSString_h

#include <atomic>
#include <wtf/ThreadSafeRefCounted.h>
#include <wtf/text/WTFString.h>

#if PLATFORM(JAVA) // tav todo temp
#include <JSExportMacros.h>
#endif

namespace JSC {
    class Identifier;
    class VM;
}

struct OpaqueJSString : public ThreadSafeRefCounted<OpaqueJSString> {
    static PassRefPtr<OpaqueJSString> create()
    {
        return adoptRef(new OpaqueJSString);
    }

    static PassRefPtr<OpaqueJSString> create(const LChar* characters, unsigned length)
    {
        return adoptRef(new OpaqueJSString(characters, length));
    }

    static PassRefPtr<OpaqueJSString> create(const UChar* characters, unsigned length)
    {
        return adoptRef(new OpaqueJSString(characters, length));
    }

    JS_EXPORT_PRIVATE static PassRefPtr<OpaqueJSString> create(const String&);

    JS_EXPORT_PRIVATE ~OpaqueJSString();

    bool is8Bit() { return this ? m_string.is8Bit() : false; }
    const LChar* characters8() { return this ? m_string.characters8() : nullptr; }
    const UChar* characters16() { return this ? m_string.characters16() : nullptr; }
    unsigned length() { return this ? m_string.length() : 0; }

    const UChar* characters();

    JS_EXPORT_PRIVATE String string() const;
    JSC::Identifier identifier(JSC::VM*) const;

    static bool equal(const OpaqueJSString*, const OpaqueJSString*);

private:
    friend class WTF::ThreadSafeRefCounted<OpaqueJSString>;

    OpaqueJSString()
        : m_characters(nullptr)
    {
    }

    OpaqueJSString(const String& string)
        : m_string(string.isolatedCopy())
        , m_characters(m_string.impl() && m_string.is8Bit() ? nullptr : const_cast<UChar*>(m_string.characters16()))
    {
    }

    OpaqueJSString(const LChar* characters, unsigned length)
        : m_string(characters, length)
        , m_characters(nullptr)
    {
    }

    OpaqueJSString(const UChar* characters, unsigned length)
        : m_string(characters, length)
        , m_characters(m_string.impl() && m_string.is8Bit() ? nullptr : const_cast<UChar*>(m_string.characters16()))
    {
    }

    String m_string;

    // This will be initialized on demand when characters() is called if the string needs up-conversion.
    std::atomic<UChar*> m_characters;
};

#endif
