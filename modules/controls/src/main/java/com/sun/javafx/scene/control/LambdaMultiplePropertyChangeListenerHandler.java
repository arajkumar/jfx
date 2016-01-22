/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javafx.scene.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class LambdaMultiplePropertyChangeListenerHandler {

    private final Map<ObservableValue<?>, List<Consumer<ObservableValue<?>>>> propertyReferenceMap;
    private final ChangeListener<Object> propertyChangedListener;
    private final WeakChangeListener<Object> weakPropertyChangedListener;

    public LambdaMultiplePropertyChangeListenerHandler() {
        this.propertyReferenceMap = new HashMap<>();
        this.propertyChangedListener = (observable, oldValue, newValue) -> {
            List<Consumer<ObservableValue<?>>> callbacks = propertyReferenceMap.getOrDefault(observable, Collections.emptyList());
            for (Consumer<ObservableValue<?>> callback : callbacks) {
                callback.accept(observable);
            }
        };
        this.weakPropertyChangedListener = new WeakChangeListener<>(propertyChangedListener);
    }

    /**
     * Subclasses can invoke this method to register that we want to listen to
     * property change events for the given property.
     *
     * @param property
     */
    public final void registerChangeListener(ObservableValue<?> property, Consumer<ObservableValue<?>> consumer) {
        if (consumer == null) return;
        List<Consumer<ObservableValue<?>>> callbacks = propertyReferenceMap.computeIfAbsent(property, p -> new ArrayList<>());
        callbacks.add(consumer);

        // we only add a listener if the callbacks list contains only one element
        // (that is, we've added a consumer to this specific property for the first
        // time).
        if (callbacks.size() == 1) {
            property.addListener(weakPropertyChangedListener);
        }
    }

    // need to be careful here - removing all listeners on the specific property!
    public final void unregisterChangeListener(ObservableValue<?> property) {
        if (propertyReferenceMap.containsKey(property)) {
            propertyReferenceMap.remove(property);
            property.removeListener(weakPropertyChangedListener);
        }
    }

    public void dispose() {
        // unhook listeners
        for (ObservableValue<?> value : propertyReferenceMap.keySet()) {
            value.removeListener(weakPropertyChangedListener);
        }
        propertyReferenceMap.clear();
    }
}
