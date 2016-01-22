/*
 * Copyright (C) 2012, 2013 Apple Inc. All rights reserved.
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

#ifndef LLIntThunks_h
#define LLIntThunks_h

#include <wtf/Platform.h>

#if ENABLE(LLINT)

#include "MacroAssemblerCodeRef.h"

namespace JSC {

class VM;
struct ProtoCallFrame;

extern "C" {
    EncodedJSValue callToJavaScript(void*, VM*, ProtoCallFrame*);
    EncodedJSValue callToNativeFunction(void*, VM*, ProtoCallFrame*);
#if ENABLE(JIT)
    void handleUncaughtException();
#endif
}

namespace LLInt {

MacroAssemblerCodeRef functionForCallEntryThunkGenerator(VM*);
MacroAssemblerCodeRef functionForConstructEntryThunkGenerator(VM*);
MacroAssemblerCodeRef functionForCallArityCheckThunkGenerator(VM*);
MacroAssemblerCodeRef functionForConstructArityCheckThunkGenerator(VM*);
MacroAssemblerCodeRef evalEntryThunkGenerator(VM*);
MacroAssemblerCodeRef programEntryThunkGenerator(VM*);

} } // namespace JSC::LLInt

#endif // ENABLE(LLINT)

#endif // LLIntThunks_h
