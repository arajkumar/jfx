/*
    This file is part of the WebKit open source project.
    This file has been generated by generate-bindings.pl. DO NOT MODIFY!

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Library General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Library General Public License for more details.

    You should have received a copy of the GNU Library General Public License
    along with this library; see the file COPYING.LIB.  If not, write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA 02110-1301, USA.
*/

#include "config.h"
#include "JSTestIndexedSetterWithIdentifier.h"

#include "JSDOMBinding.h"
#include "JSDOMConstructorNotConstructable.h"
#include "JSDOMConvertNumbers.h"
#include "JSDOMConvertStrings.h"
#include "JSDOMExceptionHandling.h"
#include "JSDOMOperation.h"
#include "JSDOMWrapperCache.h"
#include "URL.h"
#include <runtime/FunctionPrototype.h>
#include <runtime/JSCInlines.h>
#include <runtime/PropertyNameArray.h>
#include <wtf/GetPtr.h>

using namespace JSC;

namespace WebCore {

// Functions

JSC::EncodedJSValue JSC_HOST_CALL jsTestIndexedSetterWithIdentifierPrototypeFunctionIndexedSetter(JSC::ExecState*);

// Attributes

JSC::EncodedJSValue jsTestIndexedSetterWithIdentifierConstructor(JSC::ExecState*, JSC::EncodedJSValue, JSC::PropertyName);
bool setJSTestIndexedSetterWithIdentifierConstructor(JSC::ExecState*, JSC::EncodedJSValue, JSC::EncodedJSValue);

class JSTestIndexedSetterWithIdentifierPrototype : public JSC::JSNonFinalObject {
public:
    using Base = JSC::JSNonFinalObject;
    static JSTestIndexedSetterWithIdentifierPrototype* create(JSC::VM& vm, JSDOMGlobalObject* globalObject, JSC::Structure* structure)
    {
        JSTestIndexedSetterWithIdentifierPrototype* ptr = new (NotNull, JSC::allocateCell<JSTestIndexedSetterWithIdentifierPrototype>(vm.heap)) JSTestIndexedSetterWithIdentifierPrototype(vm, globalObject, structure);
        ptr->finishCreation(vm);
        return ptr;
    }

    DECLARE_INFO;
    static JSC::Structure* createStructure(JSC::VM& vm, JSC::JSGlobalObject* globalObject, JSC::JSValue prototype)
    {
        return JSC::Structure::create(vm, globalObject, prototype, JSC::TypeInfo(JSC::ObjectType, StructureFlags), info());
    }

private:
    JSTestIndexedSetterWithIdentifierPrototype(JSC::VM& vm, JSC::JSGlobalObject*, JSC::Structure* structure)
        : JSC::JSNonFinalObject(vm, structure)
    {
    }

    void finishCreation(JSC::VM&);
};

using JSTestIndexedSetterWithIdentifierConstructor = JSDOMConstructorNotConstructable<JSTestIndexedSetterWithIdentifier>;

template<> JSValue JSTestIndexedSetterWithIdentifierConstructor::prototypeForStructure(JSC::VM& vm, const JSDOMGlobalObject& globalObject)
{
    UNUSED_PARAM(vm);
    return globalObject.functionPrototype();
}

template<> void JSTestIndexedSetterWithIdentifierConstructor::initializeProperties(VM& vm, JSDOMGlobalObject& globalObject)
{
    putDirect(vm, vm.propertyNames->prototype, JSTestIndexedSetterWithIdentifier::prototype(vm, globalObject), DontDelete | ReadOnly | DontEnum);
    putDirect(vm, vm.propertyNames->name, jsNontrivialString(&vm, String(ASCIILiteral("TestIndexedSetterWithIdentifier"))), ReadOnly | DontEnum);
    putDirect(vm, vm.propertyNames->length, jsNumber(0), ReadOnly | DontEnum);
}

template<> const ClassInfo JSTestIndexedSetterWithIdentifierConstructor::s_info = { "TestIndexedSetterWithIdentifier", &Base::s_info, nullptr, nullptr, CREATE_METHOD_TABLE(JSTestIndexedSetterWithIdentifierConstructor) };

/* Hash table for prototype */

static const HashTableValue JSTestIndexedSetterWithIdentifierPrototypeTableValues[] =
{
    { "constructor", DontEnum, NoIntrinsic, { (intptr_t)static_cast<PropertySlot::GetValueFunc>(jsTestIndexedSetterWithIdentifierConstructor), (intptr_t) static_cast<PutPropertySlot::PutValueFunc>(setJSTestIndexedSetterWithIdentifierConstructor) } },
    { "indexedSetter", JSC::Function, NoIntrinsic, { (intptr_t)static_cast<NativeFunction>(jsTestIndexedSetterWithIdentifierPrototypeFunctionIndexedSetter), (intptr_t) (2) } },
};

const ClassInfo JSTestIndexedSetterWithIdentifierPrototype::s_info = { "TestIndexedSetterWithIdentifierPrototype", &Base::s_info, nullptr, nullptr, CREATE_METHOD_TABLE(JSTestIndexedSetterWithIdentifierPrototype) };

void JSTestIndexedSetterWithIdentifierPrototype::finishCreation(VM& vm)
{
    Base::finishCreation(vm);
    reifyStaticProperties(vm, JSTestIndexedSetterWithIdentifier::info(), JSTestIndexedSetterWithIdentifierPrototypeTableValues, *this);
}

const ClassInfo JSTestIndexedSetterWithIdentifier::s_info = { "TestIndexedSetterWithIdentifier", &Base::s_info, nullptr, nullptr, CREATE_METHOD_TABLE(JSTestIndexedSetterWithIdentifier) };

JSTestIndexedSetterWithIdentifier::JSTestIndexedSetterWithIdentifier(Structure* structure, JSDOMGlobalObject& globalObject, Ref<TestIndexedSetterWithIdentifier>&& impl)
    : JSDOMWrapper<TestIndexedSetterWithIdentifier>(structure, globalObject, WTFMove(impl))
{
}

void JSTestIndexedSetterWithIdentifier::finishCreation(VM& vm)
{
    Base::finishCreation(vm);
    ASSERT(inherits(vm, info()));

}

JSObject* JSTestIndexedSetterWithIdentifier::createPrototype(VM& vm, JSDOMGlobalObject& globalObject)
{
    return JSTestIndexedSetterWithIdentifierPrototype::create(vm, &globalObject, JSTestIndexedSetterWithIdentifierPrototype::createStructure(vm, &globalObject, globalObject.objectPrototype()));
}

JSObject* JSTestIndexedSetterWithIdentifier::prototype(VM& vm, JSDOMGlobalObject& globalObject)
{
    return getDOMPrototype<JSTestIndexedSetterWithIdentifier>(vm, globalObject);
}

JSValue JSTestIndexedSetterWithIdentifier::getConstructor(VM& vm, const JSGlobalObject* globalObject)
{
    return getDOMConstructor<JSTestIndexedSetterWithIdentifierConstructor>(vm, *jsCast<const JSDOMGlobalObject*>(globalObject));
}

void JSTestIndexedSetterWithIdentifier::destroy(JSC::JSCell* cell)
{
    JSTestIndexedSetterWithIdentifier* thisObject = static_cast<JSTestIndexedSetterWithIdentifier*>(cell);
    thisObject->JSTestIndexedSetterWithIdentifier::~JSTestIndexedSetterWithIdentifier();
}

bool JSTestIndexedSetterWithIdentifier::getOwnPropertySlot(JSObject* object, ExecState* state, PropertyName propertyName, PropertySlot& slot)
{
    auto* thisObject = jsCast<JSTestIndexedSetterWithIdentifier*>(object);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    if (auto index = parseIndex(propertyName)) {
        if (index.value() < thisObject->wrapped().length()) {
            auto value = toJS<IDLDOMString>(*state, thisObject->wrapped().item(index.value()));
            slot.setValue(thisObject, 0, value);
            return true;
        }
    }
    return JSObject::getOwnPropertySlot(object, state, propertyName, slot);
}

bool JSTestIndexedSetterWithIdentifier::getOwnPropertySlotByIndex(JSObject* object, ExecState* state, unsigned index, PropertySlot& slot)
{
    auto* thisObject = jsCast<JSTestIndexedSetterWithIdentifier*>(object);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());
    if (LIKELY(index <= MAX_ARRAY_INDEX)) {
        if (index < thisObject->wrapped().length()) {
            auto value = toJS<IDLDOMString>(*state, thisObject->wrapped().item(index));
            slot.setValue(thisObject, 0, value);
            return true;
        }
    }
    return JSObject::getOwnPropertySlotByIndex(object, state, index, slot);
}

void JSTestIndexedSetterWithIdentifier::getOwnPropertyNames(JSObject* object, ExecState* state, PropertyNameArray& propertyNames, EnumerationMode mode)
{
    auto* thisObject = jsCast<JSTestIndexedSetterWithIdentifier*>(object);
    ASSERT_GC_OBJECT_INHERITS(object, info());
    for (unsigned i = 0, count = thisObject->wrapped().length(); i < count; ++i)
        propertyNames.add(Identifier::from(state, i));
    JSObject::getOwnPropertyNames(object, state, propertyNames, mode);
}

bool JSTestIndexedSetterWithIdentifier::put(JSCell* cell, ExecState* state, PropertyName propertyName, JSValue value, PutPropertySlot& putPropertySlot)
{
    auto* thisObject = jsCast<JSTestIndexedSetterWithIdentifier*>(cell);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());

    if (auto index = parseIndex(propertyName)) {
        auto throwScope = DECLARE_THROW_SCOPE(state->vm());
        auto nativeValue = convert<IDLDOMString>(*state, value);
        RETURN_IF_EXCEPTION(throwScope, true);
        thisObject->wrapped().indexedSetter(index.value(), WTFMove(nativeValue));
        return true;
    }

    return JSObject::put(thisObject, state, propertyName, value, putPropertySlot);
}

bool JSTestIndexedSetterWithIdentifier::putByIndex(JSCell* cell, ExecState* state, unsigned index, JSValue value, bool shouldThrow)
{
    auto* thisObject = jsCast<JSTestIndexedSetterWithIdentifier*>(cell);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());

    if (LIKELY(index <= MAX_ARRAY_INDEX)) {
        auto throwScope = DECLARE_THROW_SCOPE(state->vm());
        auto nativeValue = convert<IDLDOMString>(*state, value);
        RETURN_IF_EXCEPTION(throwScope, true);
        thisObject->wrapped().indexedSetter(index, WTFMove(nativeValue));
        return true;
    }

    return JSObject::putByIndex(cell, state, index, value, shouldThrow);
}

bool JSTestIndexedSetterWithIdentifier::defineOwnProperty(JSObject* object, ExecState* state, PropertyName propertyName, const PropertyDescriptor& propertyDescriptor, bool shouldThrow)
{
    auto* thisObject = jsCast<JSTestIndexedSetterWithIdentifier*>(object);
    ASSERT_GC_OBJECT_INHERITS(thisObject, info());

    if (auto index = parseIndex(propertyName)) {
        if (!propertyDescriptor.isDataDescriptor())
            return false;
        auto throwScope = DECLARE_THROW_SCOPE(state->vm());
        auto nativeValue = convert<IDLDOMString>(*state, propertyDescriptor.value());
        RETURN_IF_EXCEPTION(throwScope, true);
        thisObject->wrapped().indexedSetter(index.value(), WTFMove(nativeValue));
        return true;
    }

    PropertyDescriptor newPropertyDescriptor = propertyDescriptor;
    newPropertyDescriptor.setConfigurable(true);
    return JSObject::defineOwnProperty(object, state, propertyName, newPropertyDescriptor, shouldThrow);
}

template<> inline JSTestIndexedSetterWithIdentifier* IDLOperation<JSTestIndexedSetterWithIdentifier>::cast(ExecState& state)
{
    return jsDynamicDowncast<JSTestIndexedSetterWithIdentifier*>(state.vm(), state.thisValue());
}

EncodedJSValue jsTestIndexedSetterWithIdentifierConstructor(ExecState* state, EncodedJSValue thisValue, PropertyName)
{
    VM& vm = state->vm();
    auto throwScope = DECLARE_THROW_SCOPE(vm);
    auto* prototype = jsDynamicDowncast<JSTestIndexedSetterWithIdentifierPrototype*>(vm, JSValue::decode(thisValue));
    if (UNLIKELY(!prototype))
        return throwVMTypeError(state, throwScope);
    return JSValue::encode(JSTestIndexedSetterWithIdentifier::getConstructor(state->vm(), prototype->globalObject()));
}

bool setJSTestIndexedSetterWithIdentifierConstructor(ExecState* state, EncodedJSValue thisValue, EncodedJSValue encodedValue)
{
    VM& vm = state->vm();
    auto throwScope = DECLARE_THROW_SCOPE(vm);
    auto* prototype = jsDynamicDowncast<JSTestIndexedSetterWithIdentifierPrototype*>(vm, JSValue::decode(thisValue));
    if (UNLIKELY(!prototype)) {
        throwVMTypeError(state, throwScope);
        return false;
    }
    // Shadowing a built-in constructor
    return prototype->putDirect(state->vm(), state->propertyNames().constructor, JSValue::decode(encodedValue));
}

static inline JSC::EncodedJSValue jsTestIndexedSetterWithIdentifierPrototypeFunctionIndexedSetterBody(JSC::ExecState* state, typename IDLOperation<JSTestIndexedSetterWithIdentifier>::ClassParameter castedThis, JSC::ThrowScope& throwScope)
{
    UNUSED_PARAM(state);
    UNUSED_PARAM(throwScope);
    auto& impl = castedThis->wrapped();
    if (UNLIKELY(state->argumentCount() < 2))
        return throwVMError(state, throwScope, createNotEnoughArgumentsError(state));
    auto index = convert<IDLUnsignedLong>(*state, state->uncheckedArgument(0));
    RETURN_IF_EXCEPTION(throwScope, encodedJSValue());
    auto value = convert<IDLDOMString>(*state, state->uncheckedArgument(1));
    RETURN_IF_EXCEPTION(throwScope, encodedJSValue());
    impl.indexedSetter(WTFMove(index), WTFMove(value));
    return JSValue::encode(jsUndefined());
}

EncodedJSValue JSC_HOST_CALL jsTestIndexedSetterWithIdentifierPrototypeFunctionIndexedSetter(ExecState* state)
{
    return IDLOperation<JSTestIndexedSetterWithIdentifier>::call<jsTestIndexedSetterWithIdentifierPrototypeFunctionIndexedSetterBody>(*state, "indexedSetter");
}

bool JSTestIndexedSetterWithIdentifierOwner::isReachableFromOpaqueRoots(JSC::Handle<JSC::Unknown> handle, void*, SlotVisitor& visitor)
{
    UNUSED_PARAM(handle);
    UNUSED_PARAM(visitor);
    return false;
}

void JSTestIndexedSetterWithIdentifierOwner::finalize(JSC::Handle<JSC::Unknown> handle, void* context)
{
    auto* jsTestIndexedSetterWithIdentifier = static_cast<JSTestIndexedSetterWithIdentifier*>(handle.slot()->asCell());
    auto& world = *static_cast<DOMWrapperWorld*>(context);
    uncacheWrapper(world, &jsTestIndexedSetterWithIdentifier->wrapped(), jsTestIndexedSetterWithIdentifier);
}

#if ENABLE(BINDING_INTEGRITY)
#if PLATFORM(WIN)
#pragma warning(disable: 4483)
extern "C" { extern void (*const __identifier("??_7TestIndexedSetterWithIdentifier@WebCore@@6B@")[])(); }
#else
extern "C" { extern void* _ZTVN7WebCore31TestIndexedSetterWithIdentifierE[]; }
#endif
#endif

JSC::JSValue toJSNewlyCreated(JSC::ExecState*, JSDOMGlobalObject* globalObject, Ref<TestIndexedSetterWithIdentifier>&& impl)
{

#if ENABLE(BINDING_INTEGRITY)
    void* actualVTablePointer = *(reinterpret_cast<void**>(impl.ptr()));
#if PLATFORM(WIN)
    void* expectedVTablePointer = reinterpret_cast<void*>(__identifier("??_7TestIndexedSetterWithIdentifier@WebCore@@6B@"));
#else
    void* expectedVTablePointer = &_ZTVN7WebCore31TestIndexedSetterWithIdentifierE[2];
#endif

    // If this fails TestIndexedSetterWithIdentifier does not have a vtable, so you need to add the
    // ImplementationLacksVTable attribute to the interface definition
    static_assert(std::is_polymorphic<TestIndexedSetterWithIdentifier>::value, "TestIndexedSetterWithIdentifier is not polymorphic");

    // If you hit this assertion you either have a use after free bug, or
    // TestIndexedSetterWithIdentifier has subclasses. If TestIndexedSetterWithIdentifier has subclasses that get passed
    // to toJS() we currently require TestIndexedSetterWithIdentifier you to opt out of binding hardening
    // by adding the SkipVTableValidation attribute to the interface IDL definition
    RELEASE_ASSERT(actualVTablePointer == expectedVTablePointer);
#endif
    return createWrapper<TestIndexedSetterWithIdentifier>(globalObject, WTFMove(impl));
}

JSC::JSValue toJS(JSC::ExecState* state, JSDOMGlobalObject* globalObject, TestIndexedSetterWithIdentifier& impl)
{
    return wrap(state, globalObject, impl);
}

TestIndexedSetterWithIdentifier* JSTestIndexedSetterWithIdentifier::toWrapped(JSC::VM& vm, JSC::JSValue value)
{
    if (auto* wrapper = jsDynamicDowncast<JSTestIndexedSetterWithIdentifier*>(vm, value))
        return &wrapper->wrapped();
    return nullptr;
}

}
