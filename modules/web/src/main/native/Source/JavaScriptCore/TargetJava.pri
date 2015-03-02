win* {
    PRECOMPILED_HEADER = $$PWD/javascriptcorejava_pch.h
}

CONFIG += staticlib depend_includepath precompile_header

QMAKE_MACOSX_DEPLOYMENT_TARGET = 10.8

mac*|linux* {
    QMAKE_CXXFLAGS += -std=c++11
}

*clang* {
    QMAKE_CXXFLAGS += -stdlib=libc++
}

VPATH += \
    $$PWD \

INCLUDEPATH += \
    $$GENERATED_SOURCES_DIR \
    $$PWD \
    $$PWD/.. \
    $$PWD/assembler \
    $$PWD/bytecode \
    $$PWD/bytecompiler \
    $$PWD/bindings \
    $$PWD/builtins \
    $$PWD/ftl \
    $$PWD/heap \
    $$PWD/dfg \
    $$PWD/debugger \
    $$PWD/disassembler \
    $$PWD/interpreter \
    $$PWD/inspector \
    $$PWD/jit \
    $$PWD/llint \
    $$PWD/parser \
    $$PWD/profiler \
    $$PWD/runtime \
    $$PWD/tools \
    $$PWD/yarr \
    $$PWD/API \
    $$PWD/ForwardingHeaders \
    $$PWD/../WTF \
    $$PWD/../WTF/wtf \
    ../../../generated-src/headers \

win* {
    INCLUDEPATH += \
        $(WEBKIT_OUTPUTDIR)/import/include/icu
}

include(yarr/yarr.pri)
include($$PWD/../WTF/WTFJava.pri)

INSTALLDEPS += all

SOURCES += \
    API/JSBase.cpp \
    API/JSCallbackConstructor.cpp \
    API/JSCallbackFunction.cpp \
    API/JSCallbackObject.cpp \
    API/JSClassRef.cpp \
    API/JSContextRef.cpp \
    API/JSObjectRef.cpp \
    API/JSScriptRef.cpp \
    API/JSStringRef.cpp \
    API/JSValueRef.cpp \
    API/JSWeakObjectMapRefPrivate.cpp \
    API/OpaqueJSString.cpp \
    API/JSCTestRunnerUtils.cpp \
    assembler/ARMAssembler.cpp \
    assembler/ARMv7Assembler.cpp \
    assembler/LinkBuffer.cpp \
    assembler/MacroAssembler.cpp \
    assembler/MacroAssemblerARM.cpp \
    bytecode/ArrayAllocationProfile.cpp \
    bytecode/ArrayProfile.cpp \
    bytecode/BytecodeBasicBlock.cpp \
    bytecode/BytecodeLivenessAnalysis.cpp \
    bytecode/CallLinkInfo.cpp \
    bytecode/CallLinkStatus.cpp \
    bytecode/CodeType.cpp \
    bytecode/CodeBlock.cpp \
    bytecode/CodeBlockHash.cpp \
    bytecode/CodeBlockJettisoningWatchpoint.cpp \
    bytecode/CodeOrigin.cpp \
    bytecode/DFGExitProfile.cpp \
    bytecode/DeferredCompilationCallback.cpp \
    bytecode/ExecutionCounter.cpp \
    bytecode/ExitKind.cpp \
    bytecode/ExitingJITType.cpp \
    bytecode/GetByIdStatus.cpp \
    bytecode/GetByIdVariant.cpp \
    bytecode/InlineCallFrameSet.cpp \
    bytecode/JumpTable.cpp \
    bytecode/LazyOperandValueProfile.cpp \
    bytecode/MethodOfGettingAValueProfile.cpp \
    bytecode/Opcode.cpp \
    bytecode/PolymorphicPutByIdList.cpp \
    bytecode/PreciseJumpTargets.cpp \
    bytecode/ProfiledCodeBlockJettisoningWatchpoint.cpp \
    bytecode/SpeculatedType.cpp \
    bytecode/PutByIdStatus.cpp \
    bytecode/ReduceWhitespace.cpp \
    bytecode/SamplingTool.cpp \
    bytecode/SpecialPointer.cpp \
    bytecode/StructureStubInfo.cpp \
    bytecode/StructureStubClearingWatchpoint.cpp \
    bytecode/UnlinkedCodeBlock.cpp \
    bytecode/UnlinkedInstructionStream.cpp \
    bytecode/ValueRecovery.cpp \
    bytecode/Watchpoint.cpp \
    bytecompiler/BytecodeGenerator.cpp \
    bytecompiler/NodesCodegen.cpp \
    bindings/ScriptFunctionCall.cpp \
    bindings/ScriptObject.cpp \
    bindings/ScriptValue.cpp \
    builtins/BuiltinExecutables.cpp \
    heap/CodeBlockSet.cpp \
    heap/CopiedSpace.cpp \
    heap/CopyVisitor.cpp \
    heap/ConservativeRoots.cpp \
    heap/DeferGC.cpp \
    heap/Weak.cpp \
    heap/WeakBlock.cpp \
    heap/WeakHandleOwner.cpp \
    heap/WeakSet.cpp \
    heap/WriteBarrierBuffer.cpp \
    heap/HandleSet.cpp \
    heap/HandleStack.cpp \
    heap/BlockAllocator.cpp \
    heap/GCThreadSharedData.cpp \
    heap/GCThread.cpp \
    heap/Heap.cpp \
    heap/HeapStatistics.cpp \
    heap/HeapTimer.cpp \
    heap/IncrementalSweeper.cpp \
    heap/JITStubRoutineSet.cpp \
    heap/MachineStackMarker.cpp \
    heap/MarkStack.cpp \
    heap/MarkedAllocator.cpp \
    heap/MarkedBlock.cpp \
    heap/MarkedSpace.cpp \
    heap/SlotVisitor.cpp \
    heap/SuperRegion.cpp \
    heap/WriteBarrierSupport.cpp \
    debugger/DebuggerActivation.cpp \
    debugger/DebuggerCallFrame.cpp \
    debugger/Debugger.cpp \
    dfg/DFGAbstractHeap.cpp \
    dfg/DFGAbstractValue.cpp \
    dfg/DFGArgumentsSimplificationPhase.cpp \
    dfg/DFGArithMode.cpp \
    dfg/DFGArrayMode.cpp \
    dfg/DFGAtTailAbstractState.cpp \
    dfg/DFGAvailability.cpp \
    dfg/DFGBackwardsPropagationPhase.cpp \
    dfg/DFGBasicBlock.cpp \
    dfg/DFGBinarySwitch.cpp \
    dfg/DFGBlockInsertionSet.cpp \
    dfg/DFGByteCodeParser.cpp \
    dfg/DFGCFGSimplificationPhase.cpp \
    dfg/DFGCapabilities.cpp \
    dfg/DFGCFAPhase.cpp \
    dfg/DFGCPSRethreadingPhase.cpp \
    dfg/DFGClobberize.cpp \
    dfg/DFGClobberSet.cpp \
    dfg/DFGCommon.cpp \
    dfg/DFGCommonData.cpp \
    dfg/DFGCompilationKey.cpp \
    dfg/DFGCompilationMode.cpp \
    dfg/DFGConstantFoldingPhase.cpp \
    dfg/DFGCriticalEdgeBreakingPhase.cpp \
    dfg/DFGCSEPhase.cpp \
    dfg/DFGDCEPhase.cpp \
    dfg/DFGDesiredIdentifiers.cpp \
    dfg/DFGDesiredStructureChains.cpp \
    dfg/DFGDesiredTransitions.cpp \
    dfg/DFGDesiredWatchpoints.cpp \
    dfg/DFGDesiredWeakReferences.cpp \
    dfg/DFGDesiredWriteBarriers.cpp \
    dfg/DFGDisassembler.cpp \
    dfg/DFGDominators.cpp \
    dfg/DFGDriver.cpp \
    dfg/DFGEdge.cpp \
    dfg/DFGFailedFinalizer.cpp \
    dfg/DFGFinalizer.cpp \
    dfg/DFGFixupPhase.cpp \
    dfg/DFGFlushFormat.cpp \
    dfg/DFGFlushLivenessAnalysisPhase.cpp \
    dfg/DFGFlushedAt.cpp \
    dfg/DFGGraph.cpp \
    dfg/DFGGraphSafepoint.cpp \
    dfg/DFGInPlaceAbstractState.cpp \
    dfg/DFGIntegerCheckCombiningPhase.cpp \
    dfg/DFGInvalidationPointInjectionPhase.cpp \
    dfg/DFGJITCode.cpp \
    dfg/DFGJITCompiler.cpp \
    dfg/DFGJITFinalizer.cpp \
    dfg/DFGJumpReplacement.cpp \
    dfg/DFGLICMPhase.cpp \
    dfg/DFGLazyJSValue.cpp \
    dfg/DFGLivenessAnalysisPhase.cpp \
    dfg/DFGLongLivedState.cpp \
    dfg/DFGLoopPreHeaderCreationPhase.cpp \
    dfg/DFGMinifiedNode.cpp \
    dfg/DFGNaturalLoops.cpp \
    dfg/DFGNode.cpp \
    dfg/DFGNodeFlags.cpp \
    dfg/DFGOperations.cpp \
    dfg/DFGOSRAvailabilityAnalysisPhase.cpp \
    dfg/DFGOSREntry.cpp \
    dfg/DFGOSREntrypointCreationPhase.cpp \
    dfg/DFGOSRExitCompiler32_64.cpp \
    dfg/DFGOSRExitCompiler64.cpp \
    dfg/DFGOSRExitCompiler.cpp \
    dfg/DFGOSRExitCompilerCommon.cpp \
    dfg/DFGOSRExit.cpp \
    dfg/DFGOSRExitBase.cpp \
    dfg/DFGOSRExitJumpPlaceholder.cpp \
    dfg/DFGOSRExitPreparation.cpp \
    dfg/DFGPhase.cpp \
    dfg/DFGPlan.cpp \
    dfg/DFGPredictionInjectionPhase.cpp \
    dfg/DFGPredictionPropagationPhase.cpp \
    dfg/DFGResurrectionForValidationPhase.cpp \
    dfg/DFGSafepoint.cpp \
    dfg/DFGSpeculativeJIT32_64.cpp \
    dfg/DFGSpeculativeJIT64.cpp \
    dfg/DFGSpeculativeJIT.cpp \
    dfg/DFGSSAConversionPhase.cpp \
    dfg/DFGSSALoweringPhase.cpp \
    dfg/DFGStackLayoutPhase.cpp \
    dfg/DFGStoreBarrierElisionPhase.cpp \
    dfg/DFGStrengthReductionPhase.cpp \
    dfg/DFGTierUpCheckInjectionPhase.cpp \
    dfg/DFGThreadData.cpp \
    dfg/DFGThunks.cpp \
    dfg/DFGToFTLDeferredCompilationCallback.cpp \
    dfg/DFGToFTLForOSREntryDeferredCompilationCallback.cpp \
    dfg/DFGTypeCheckHoistingPhase.cpp \
    dfg/DFGUnificationPhase.cpp \
    dfg/DFGUseKind.cpp \
    dfg/DFGValidate.cpp \
    dfg/DFGValueSource.cpp \
    dfg/DFGVariableAccessDataDump.cpp \
    dfg/DFGVariableEvent.cpp \
    dfg/DFGVariableEventStream.cpp \
    dfg/DFGVirtualRegisterAllocationPhase.cpp \
    dfg/DFGWatchpointCollectionPhase.cpp \
    dfg/DFGWorklist.cpp \
    disassembler/ARM64Disassembler.cpp \
    disassembler/ARMv7Disassembler.cpp \
    disassembler/Disassembler.cpp \
    disassembler/LLVMDisassembler.cpp \
    disassembler/X86Disassembler.cpp \
    interpreter/AbstractPC.cpp \
    interpreter/CallFrame.cpp \
    interpreter/Interpreter.cpp \
    interpreter/JSStack.cpp \
    interpreter/StackVisitor.cpp \
    interpreter/ProtoCallFrame.cpp \
    inspector/ConsoleMessage.cpp \
    inspector/ContentSearchUtilities.cpp \
    inspector/IdentifiersFactory.cpp \
    inspector/InjectedScript.cpp \
    inspector/InjectedScriptBase.cpp \
    inspector/InjectedScriptHost.cpp \
    inspector/InjectedScriptManager.cpp \
    inspector/InjectedScriptModule.cpp \
    inspector/InspectorAgentRegistry.cpp \
    inspector/InspectorBackendDispatcher.cpp \
    inspector/InspectorValues.cpp \
    inspector/JSInjectedScriptHost.cpp \
    inspector/JSInjectedScriptHostPrototype.cpp \
    inspector/JSJavaScriptCallFrame.cpp \
    inspector/JSJavaScriptCallFramePrototype.cpp \
    inspector/JavaScriptCallFrame.cpp \
    inspector/ScriptArguments.cpp \
    inspector/ScriptCallFrame.cpp \
    inspector/ScriptCallStack.cpp \
    inspector/ScriptCallStackFactory.cpp \
    inspector/ScriptDebugServer.cpp \
    inspector/agents/InspectorAgent.cpp \
    inspector/agents/InspectorConsoleAgent.cpp \
    inspector/agents/InspectorDebuggerAgent.cpp \
    inspector/agents/InspectorRuntimeAgent.cpp \
    jit/AssemblyHelpers.cpp \
    jit/ArityCheckFailReturnThunks.cpp \
    jit/ClosureCallStubRoutine.cpp \
    jit/ExecutableAllocatorFixedVMPool.cpp \
    jit/ExecutableAllocator.cpp \
    jit/HostCallReturnValue.cpp \
    jit/GCAwareJITStubRoutine.cpp \
    jit/Reg.cpp \
    jit/RegisterPreservationWrapperGenerator.cpp \
    jit/RegisterSet.cpp \
    jit/Repatch.cpp \
    jit/TempRegisterSet.cpp \
    jit/JITArithmetic.cpp \
    jit/JITArithmetic32_64.cpp \
    jit/JITCall.cpp \
    jit/JITCall32_64.cpp \
    jit/JITCode.cpp \
    jit/JIT.cpp \
    jit/JITInlineCacheGenerator.cpp \
    jit/JITDisassembler.cpp \
    jit/JITExceptions.cpp \
    jit/JITOpcodes.cpp \
    jit/JITOpcodes32_64.cpp \
    jit/JITOperations.cpp \
    jit/JITPropertyAccess.cpp \
    jit/JITPropertyAccess32_64.cpp \
    jit/JITStubRoutine.cpp \
    jit/JITStubs.cpp \
    jit/JITThunks.cpp \
    jit/JITToDFGDeferredCompilationCallback.cpp \
    jit/ThunkGenerators.cpp \
    llint/LLIntEntrypoint.cpp \
    llint/LLIntCLoop.cpp \
    llint/LLIntData.cpp \
    llint/LLIntExceptions.cpp \
    llint/LLIntSlowPaths.cpp \
    llint/LLIntThunks.cpp \
    llint/LowLevelInterpreter.cpp \
    parser/Lexer.cpp \
    parser/Nodes.cpp \
    parser/ParserArena.cpp \
    parser/Parser.cpp \
    parser/SourceCode.cpp \
    parser/SourceProvider.cpp \
    parser/SourceProviderCache.cpp \
    profiler/ProfilerBytecode.cpp \
    profiler/ProfilerBytecode.h \
    profiler/ProfilerBytecodeSequence.cpp \
    profiler/ProfilerBytecodes.cpp \
    profiler/ProfilerBytecodes.h \
    profiler/ProfilerCompilation.cpp \
    profiler/ProfilerCompilation.h \
    profiler/ProfilerCompilationKind.cpp \
    profiler/ProfilerCompilationKind.h \
    profiler/ProfilerCompiledBytecode.cpp \
    profiler/ProfilerCompiledBytecode.h \
    profiler/ProfilerDatabase.cpp \
    profiler/ProfilerDatabase.h \
    profiler/ProfilerExecutionCounter.h \
    profiler/ProfilerJettisonReason.cpp \
    profiler/ProfilerOrigin.cpp \
    profiler/ProfilerOrigin.h \
    profiler/ProfilerOriginStack.cpp \
    profiler/ProfilerOriginStack.h \
    profiler/ProfilerOSRExit.cpp \
    profiler/ProfilerOSRExitSite.cpp \
    profiler/ProfilerProfiledBytecodes.cpp \
    profiler/Profile.cpp \
    profiler/ProfileGenerator.cpp \
    profiler/ProfileNode.cpp \
    profiler/LegacyProfiler.cpp \
    runtime/ArgList.cpp \ 
    runtime/Arguments.cpp \
    runtime/ArgumentsIteratorConstructor.cpp \
    runtime/ArgumentsIteratorPrototype.cpp \
    runtime/ArrayBuffer.cpp \
    runtime/ArrayBufferNeuteringWatchpoint.cpp \
    runtime/ArrayBufferView.cpp \
    runtime/ArrayConstructor.cpp \
    runtime/ArrayIteratorConstructor.cpp \
    runtime/ArrayIteratorPrototype.cpp \
    runtime/ArrayPrototype.cpp \
    runtime/BooleanConstructor.cpp \
    runtime/BooleanObject.cpp \
    runtime/BooleanPrototype.cpp \
    runtime/CallData.cpp \
    runtime/CodeCache.cpp \
    runtime/CodeSpecializationKind.cpp \
    runtime/CommonIdentifiers.cpp \
    runtime/CommonSlowPathsExceptions.cpp \
    runtime/CommonSlowPaths.cpp \
    runtime/CompilationResult.cpp \
    runtime/Completion.cpp \
    runtime/ConstructData.cpp \
    runtime/DataView.cpp \
    runtime/DateConstructor.cpp \
    runtime/DateConversion.cpp \
    runtime/DateInstance.cpp \
    runtime/DatePrototype.cpp \
    runtime/DumpContext.cpp \
    runtime/ErrorConstructor.cpp \
    runtime/Error.cpp \
    runtime/ErrorHandlingScope.cpp \
    runtime/ErrorInstance.cpp \
    runtime/ErrorPrototype.cpp \
    runtime/ExceptionHelpers.cpp \
    runtime/Executable.cpp \
    runtime/FunctionConstructor.cpp \
    runtime/FunctionExecutableDump.cpp \
    runtime/FunctionPrototype.cpp \
    runtime/GCActivityCallback.cpp \
    runtime/GetterSetter.cpp \
    runtime/Identifier.cpp \
    runtime/IndexingType.cpp \
    runtime/InitializeThreading.cpp \
    runtime/IntendedStructureChain.cpp \
    runtime/InternalFunction.cpp \
    runtime/JSActivation.cpp \
    runtime/JSAPIValueWrapper.cpp \
    runtime/JSArgumentsIterator.cpp \
    runtime/JSArray.cpp \
    runtime/JSArrayBuffer.cpp \
    runtime/JSArrayBufferConstructor.cpp \
    runtime/JSArrayBufferPrototype.cpp \
    runtime/JSArrayBufferView.cpp \
    runtime/JSArrayIterator.cpp \
    runtime/JSCell.cpp \
    runtime/JSDataView.cpp \
    runtime/JSDataViewPrototype.cpp \
    runtime/JSDateMath.cpp \
    runtime/JSFunction.cpp \
    runtime/JSBoundFunction.cpp \
    runtime/VM.cpp \
    runtime/VMEntryScope.cpp \
    runtime/JSGlobalObject.cpp \
    runtime/JSGlobalObjectFunctions.cpp \
    runtime/JSProxy.cpp \
    runtime/JSLock.cpp \
    runtime/JSMap.cpp \
    runtime/JSMapIterator.cpp \
    runtime/JSNotAnObject.cpp \
    runtime/JSObject.cpp \
    runtime/JSONObject.cpp \
    runtime/JSPropertyNameIterator.cpp \
    runtime/JSSegmentedVariableObject.cpp \
    runtime/JSSet.cpp \
    runtime/JSSetIterator.cpp \
    runtime/JSTypedArrayConstructors.cpp \
    runtime/JSTypedArrayPrototypes.cpp \
    runtime/JSTypedArrays.cpp \
    runtime/JSWeakMap.cpp \
    runtime/JSWithScope.cpp \
    runtime/JSNameScope.cpp \
    runtime/JSScope.cpp \
    runtime/JSStringJoiner.cpp \
    runtime/JSString.cpp \
    runtime/JSSymbolTableObject.cpp \
    runtime/JSCJSValue.cpp \
    runtime/JSVariableObject.cpp \
    runtime/JSWrapperObject.cpp \
    runtime/LiteralParser.cpp \
    runtime/Lookup.cpp \
    runtime/MapConstructor.cpp \
    runtime/MapData.cpp \
    runtime/MapIteratorConstructor.cpp \
    runtime/MapIteratorPrototype.cpp \
    runtime/MapPrototype.cpp \
    runtime/MathObject.cpp \
    runtime/NameConstructor.cpp \
    runtime/NameInstance.cpp \
    runtime/NamePrototype.cpp \
    runtime/NativeErrorConstructor.cpp \
    runtime/NativeErrorPrototype.cpp \
    runtime/NumberConstructor.cpp \
    runtime/NumberObject.cpp \
    runtime/NumberPrototype.cpp \
    runtime/ObjectConstructor.cpp \
    runtime/ObjectPrototype.cpp \
    runtime/Operations.cpp \
    runtime/Options.cpp \
    runtime/PropertyDescriptor.cpp \
    runtime/PropertyNameArray.cpp \
    runtime/PropertySlot.cpp \
    runtime/PropertyTable.cpp \
    runtime/PrototypeMap.cpp \
    runtime/RegExpCache.cpp \
    runtime/RegExpConstructor.cpp \
    runtime/RegExp.cpp \
    runtime/RegExpMatchesArray.cpp \
    runtime/RegExpCachedResult.cpp \
    runtime/RegExpObject.cpp \
    runtime/RegExpPrototype.cpp \
    runtime/SamplingCounter.cpp \
    runtime/SetConstructor.cpp \
    runtime/SetIteratorConstructor.cpp \
    runtime/SetIteratorPrototype.cpp \
    runtime/SetPrototype.cpp \
    runtime/SimpleTypedArrayController.cpp \
    runtime/SmallStrings.cpp \
    runtime/SparseArrayValueMap.cpp \
    runtime/StrictEvalActivation.cpp \
    runtime/StringConstructor.cpp \
    runtime/StringObject.cpp \
    runtime/StringPrototype.cpp \
    runtime/StringRecursionChecker.cpp \
    runtime/StructureChain.cpp \
    runtime/Structure.cpp \
    runtime/StructureRareData.cpp \
    runtime/SymbolTable.cpp \
    runtime/TestRunnerUtils.cpp \
    runtime/TypedArrayController.cpp \
    runtime/TypedArrayType.cpp \
    runtime/Watchdog.cpp \
    runtime/WatchdogNone.cpp \
    runtime/WatchdogJava.cpp \
    runtime/WeakMapConstructor.cpp \
    runtime/WeakMapData.cpp \
    runtime/WeakMapPrototype.cpp \
    tools/CodeProfile.cpp \
    tools/CodeProfiling.cpp \
    yarr/RegularExpression.cpp \
    yarr/YarrCanonicalizeUCS2.cpp \
    yarr/YarrInterpreter.cpp \
    yarr/YarrJIT.cpp \
    yarr/YarrPattern.cpp \
    yarr/YarrSyntaxChecker.cpp \
    $$PWD/../WTF/wtf/java/MainThreadJava.cpp \
    $$PWD/../WTF/wtf/java/StringJava.cpp

contains(DEFINES, ENABLE_PROMISES=1) {
    SOURCES += \
        runtime/JSPromise.cpp \
    	runtime/JSPromiseConstructor.cpp \
	runtime/JSPromiseDeferred.cpp \
	runtime/JSPromiseFunctions.cpp \
	runtime/JSPromiseReaction.cpp \
	runtime/JSPromisePrototype.cpp
}

contains(DEFINES, ICU_UNICODE=1) {
    SOURCES += $$PWD/../WTF/wtf/unicode/icu/CollatorICU.cpp
} else {
    SOURCES += $$PWD/../WTF/wtf/unicode/java/UnicodeJava.cpp
}
