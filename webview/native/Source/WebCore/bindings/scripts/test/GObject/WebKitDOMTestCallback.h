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

#ifndef WebKitDOMTestCallback_h
#define WebKitDOMTestCallback_h

#include <glib-object.h>
#include <webkit/WebKitDOMObject.h>
#include <webkit/webkitdefines.h>
#include <webkit/webkitdomdefines.h>

G_BEGIN_DECLS
#define WEBKIT_TYPE_DOM_TEST_CALLBACK            (webkit_dom_test_callback_get_type())
#define WEBKIT_DOM_TEST_CALLBACK(obj)            (G_TYPE_CHECK_INSTANCE_CAST((obj), WEBKIT_TYPE_DOM_TEST_CALLBACK, WebKitDOMTestCallback))
#define WEBKIT_DOM_TEST_CALLBACK_CLASS(klass)    (G_TYPE_CHECK_CLASS_CAST((klass),  WEBKIT_TYPE_DOM_TEST_CALLBACK, WebKitDOMTestCallbackClass)
#define WEBKIT_DOM_IS_TEST_CALLBACK(obj)         (G_TYPE_CHECK_INSTANCE_TYPE((obj), WEBKIT_TYPE_DOM_TEST_CALLBACK))
#define WEBKIT_DOM_IS_TEST_CALLBACK_CLASS(klass) (G_TYPE_CHECK_CLASS_TYPE((klass),  WEBKIT_TYPE_DOM_TEST_CALLBACK))
#define WEBKIT_DOM_TEST_CALLBACK_GET_CLASS(obj)  (G_TYPE_INSTANCE_GET_CLASS((obj),  WEBKIT_TYPE_DOM_TEST_CALLBACK, WebKitDOMTestCallbackClass))

struct _WebKitDOMTestCallback {
    WebKitDOMObject parent_instance;
};

struct _WebKitDOMTestCallbackClass {
    WebKitDOMObjectClass parent_class;
};

WEBKIT_API GType
webkit_dom_test_callback_get_type (void);

/**
 * webkit_dom_test_callback_callback_with_no_param:
 * @self: A #WebKitDOMTestCallback
 *
 * Returns:
 *
**/
WEBKIT_API gboolean
webkit_dom_test_callback_callback_with_no_param(WebKitDOMTestCallback* self);

/**
 * webkit_dom_test_callback_callback_with_class1param:
 * @self: A #WebKitDOMTestCallback
 * @class1Param: A #WebKitDOMClass1
 *
 * Returns:
 *
**/
WEBKIT_API gboolean
webkit_dom_test_callback_callback_with_class1param(WebKitDOMTestCallback* self, WebKitDOMClass1* class1Param);

/**
 * webkit_dom_test_callback_callback_with_class2param:
 * @self: A #WebKitDOMTestCallback
 * @class2Param: A #WebKitDOMClass2
 * @strArg: A #gchar
 *
 * Returns:
 *
**/
WEBKIT_API gboolean
webkit_dom_test_callback_callback_with_class2param(WebKitDOMTestCallback* self, WebKitDOMClass2* class2Param, const gchar* strArg);

/**
 * webkit_dom_test_callback_callback_with_non_bool_return_type:
 * @self: A #WebKitDOMTestCallback
 * @class3Param: A #WebKitDOMClass3
 *
 * Returns:
 *
**/
WEBKIT_API glong
webkit_dom_test_callback_callback_with_non_bool_return_type(WebKitDOMTestCallback* self, WebKitDOMClass3* class3Param);

/**
 * webkit_dom_test_callback_callback_with_string_list:
 * @self: A #WebKitDOMTestCallback
 * @listParam: A #WebKitDOMDOMStringList
 *
 * Returns:
 *
**/
WEBKIT_API gboolean
webkit_dom_test_callback_callback_with_string_list(WebKitDOMTestCallback* self, WebKitDOMDOMStringList* listParam);

/**
 * webkit_dom_test_callback_callback_with_boolean:
 * @self: A #WebKitDOMTestCallback
 * @boolParam: A #gboolean
 *
 * Returns:
 *
**/
WEBKIT_API gboolean
webkit_dom_test_callback_callback_with_boolean(WebKitDOMTestCallback* self, gboolean boolParam);

/**
 * webkit_dom_test_callback_callback_requires_this_to_pass:
 * @self: A #WebKitDOMTestCallback
 * @class8Param: A #WebKitDOMClass8
 * @thisClassParam: A #WebKitDOMThisClass
 *
 * Returns:
 *
**/
WEBKIT_API gboolean
webkit_dom_test_callback_callback_requires_this_to_pass(WebKitDOMTestCallback* self, WebKitDOMClass8* class8Param, WebKitDOMThisClass* thisClassParam);

G_END_DECLS

#endif /* WebKitDOMTestCallback_h */
