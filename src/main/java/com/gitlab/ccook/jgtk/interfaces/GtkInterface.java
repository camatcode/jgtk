/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 JGTK
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package com.gitlab.ccook.jgtk.interfaces;

import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.jna.GtkLibrary;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
public interface GtkInterface {
    Map<String, Callback> callbacks = new HashMap<>();

    GtkLibrary library = new GtkLibrary();

    default void connect(GtkCallbackFunction callback) {
        GCallbackFunction c = new GCallbackFunction() {
            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                callback.invoke(relevantThing, relevantData);
            }
        };
        callbacks.put(callback.getDetailedSignal(), c);//To avoid garbage collection
        library.g_signal_connect_data(getCReference(), callback.getDetailedSignal(), c, callback.getDataReference(), null, GConnectFlags.getCValueFromFlags(callback.getConnectFlag()));
    }

    default void connect(String detailedName, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
        connect(new GtkCallbackFunction() {
            @Override
            public GConnectFlags[] getConnectFlag() {
                return flags;
            }

            @Override
            public Pointer getDataReference() {
                return dataRef;
            }

            @Override
            public String getDetailedSignal() {
                return detailedName;
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                fn.invoke(relevantThing, relevantData);
            }
        });
    }

    Pointer getCReference();

    default void connect(String signalName, Callback callback) {
        callbacks.put(signalName, callback);//To avoid garbage collection
        library.g_signal_connect_data(getCReference(), signalName, callback, null, null, GConnectFlags.G_CONNECT_DEFAULT.getCValue());

    }

    JGTKObject toJGTKObject(JGTKObject gtkEditable);
}
