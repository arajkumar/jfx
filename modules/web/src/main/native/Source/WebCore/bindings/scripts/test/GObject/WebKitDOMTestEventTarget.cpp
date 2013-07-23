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
#include "WebKitDOMTestEventTarget.h"

#include "CSSImportRule.h"
#include "DOMObjectCache.h"
#include "ExceptionCode.h"
#include "GObjectEventListener.h"
#include "JSMainThreadExecState.h"
#include "WebKitDOMEventPrivate.h"
#include "WebKitDOMEventTarget.h"
#include "WebKitDOMNodePrivate.h"
#include "WebKitDOMPrivate.h"
#include "WebKitDOMTestEventTargetPrivate.h"
#include "gobject/ConvertToUTF8String.h"
#include <wtf/GetPtr.h>
#include <wtf/RefPtr.h>

#define WEBKIT_DOM_TEST_EVENT_TARGET_GET_PRIVATE(obj) G_TYPE_INSTANCE_GET_PRIVATE(obj, WEBKIT_TYPE_DOM_TEST_EVENT_TARGET, WebKitDOMTestEventTargetPrivate)

typedef struct _WebKitDOMTestEventTargetPrivate {
    RefPtr<WebCore::TestEventTarget> coreObject;
} WebKitDOMTestEventTargetPrivate;

namespace WebKit {

WebKitDOMTestEventTarget* kit(WebCore::TestEventTarget* obj)
{
    if (!obj)
        return 0;

    if (gpointer ret = DOMObjectCache::get(obj))
        return WEBKIT_DOM_TEST_EVENT_TARGET(ret);

    return wrapTestEventTarget(obj);
}

WebCore::TestEventTarget* core(WebKitDOMTestEventTarget* request)
{
    return request ? static_cast<WebCore::TestEventTarget*>(WEBKIT_DOM_OBJECT(request)->coreObject) : 0;
}

WebKitDOMTestEventTarget* wrapTestEventTarget(WebCore::TestEventTarget* coreObject)
{
    ASSERT(coreObject);
    return WEBKIT_DOM_TEST_EVENT_TARGET(g_object_new(WEBKIT_TYPE_DOM_TEST_EVENT_TARGET, "core-object", coreObject, NULL));
}

} // namespace WebKit

static void webkit_dom_test_event_target_dispatch_event(WebKitDOMEventTarget* target, WebKitDOMEvent* event, GError** error)
{
    WebCore::Event* coreEvent = WebKit::core(event);
    WebCore::TestEventTarget* coreTarget = static_cast<WebCore::TestEventTarget*>(WEBKIT_DOM_OBJECT(target)->coreObject);

    WebCore::ExceptionCode ec = 0;
    coreTarget->dispatchEvent(coreEvent, ec);
    if (ec) {
        WebCore::ExceptionCodeDescription description(ec);
        g_set_error_literal(error, g_quark_from_string("WEBKIT_DOM"), description.code, description.name);
    }
}

static gboolean webkit_dom_test_event_target_add_event_listener(WebKitDOMEventTarget* target, const char* eventName, GCallback handler, gboolean bubble, gpointer userData)
{
    WebCore::TestEventTarget* coreTarget = static_cast<WebCore::TestEventTarget*>(WEBKIT_DOM_OBJECT(target)->coreObject);
    return WebCore::GObjectEventListener::addEventListener(G_OBJECT(target), coreTarget, eventName, handler, bubble, userData);
}

static gboolean webkit_dom_test_event_target_remove_event_listener(WebKitDOMEventTarget* target, const char* eventName, GCallback handler, gboolean bubble)
{
    WebCore::TestEventTarget* coreTarget = static_cast<WebCore::TestEventTarget*>(WEBKIT_DOM_OBJECT(target)->coreObject);
    return WebCore::GObjectEventListener::removeEventListener(G_OBJECT(target), coreTarget, eventName, handler, bubble);
}

static void webkit_dom_event_target_init(WebKitDOMEventTargetIface* iface)
{
    iface->dispatch_event = webkit_dom_test_event_target_dispatch_event;
    iface->add_event_listener = webkit_dom_test_event_target_add_event_listener;
    iface->remove_event_listener = webkit_dom_test_event_target_remove_event_listener;
}

G_DEFINE_TYPE_WITH_CODE(WebKitDOMTestEventTarget, webkit_dom_test_event_target, WEBKIT_TYPE_DOM_OBJECT, G_IMPLEMENT_INTERFACE(WEBKIT_TYPE_DOM_EVENT_TARGET, webkit_dom_event_target_init))

static void webkit_dom_test_event_target_finalize(GObject* object)
{
    WebKitDOMTestEventTargetPrivate* priv = WEBKIT_DOM_TEST_EVENT_TARGET_GET_PRIVATE(object);

    WebKit::DOMObjectCache::forget(priv->coreObject.get());

    priv->~WebKitDOMTestEventTargetPrivate();
    G_OBJECT_CLASS(webkit_dom_test_event_target_parent_class)->finalize(object);
}

static GObject* webkit_dom_test_event_target_constructor(GType type, guint constructPropertiesCount, GObjectConstructParam* constructProperties)
{
    GObject* object = G_OBJECT_CLASS(webkit_dom_test_event_target_parent_class)->constructor(type, constructPropertiesCount, constructProperties);

    WebKitDOMTestEventTargetPrivate* priv = WEBKIT_DOM_TEST_EVENT_TARGET_GET_PRIVATE(object);
    priv->coreObject = static_cast<WebCore::TestEventTarget*>(WEBKIT_DOM_OBJECT(object)->coreObject);
    WebKit::DOMObjectCache::put(priv->coreObject.get(), object);

    return object;
}

static void webkit_dom_test_event_target_class_init(WebKitDOMTestEventTargetClass* requestClass)
{
    GObjectClass* gobjectClass = G_OBJECT_CLASS(requestClass);
    g_type_class_add_private(gobjectClass, sizeof(WebKitDOMTestEventTargetPrivate));
    gobjectClass->constructor = webkit_dom_test_event_target_constructor;
    gobjectClass->finalize = webkit_dom_test_event_target_finalize;
}

static void webkit_dom_test_event_target_init(WebKitDOMTestEventTarget* request)
{
    WebKitDOMTestEventTargetPrivate* priv = WEBKIT_DOM_TEST_EVENT_TARGET_GET_PRIVATE(request);
    new (priv) WebKitDOMTestEventTargetPrivate();
}

WebKitDOMNode*
webkit_dom_test_event_target_item(WebKitDOMTestEventTarget* self, gulong index)
{
    WebCore::JSMainThreadNullState state;
    g_return_val_if_fail(WEBKIT_DOM_IS_TEST_EVENT_TARGET(self), 0);
    WebCore::TestEventTarget* item = WebKit::core(self);
    RefPtr<WebCore::Node> gobjectResult = WTF::getPtr(item->item(index));
    return WebKit::kit(gobjectResult.get());
}

gboolean
webkit_dom_test_event_target_dispatch_event(WebKitDOMTestEventTarget* self, WebKitDOMEvent* evt, GError** error)
{
    WebCore::JSMainThreadNullState state;
    g_return_val_if_fail(WEBKIT_DOM_IS_TEST_EVENT_TARGET(self), FALSE);
    g_return_val_if_fail(WEBKIT_DOM_IS_EVENT(evt), FALSE);
    g_return_val_if_fail(!error || !*error, FALSE);
    WebCore::TestEventTarget* item = WebKit::core(self);
    WebCore::Event* convertedEvt = WebKit::core(evt);
    WebCore::ExceptionCode ec = 0;
    gboolean result = item->dispatchEvent(convertedEvt, ec);
    if (ec) {
        WebCore::ExceptionCodeDescription ecdesc(ec);
        g_set_error_literal(error, g_quark_from_string("WEBKIT_DOM"), ecdesc.code, ecdesc.name);
    }
    return result;
}

