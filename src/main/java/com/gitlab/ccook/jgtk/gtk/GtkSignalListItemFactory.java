/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 - 2023 JGTK
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
package com.gitlab.ccook.jgtk.gtk;

import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkSignalListItemFactory is a GtkListItemFactory that emits signals to manage list-items.
 * <p>
 * Signals are emitted for every listitem in the same order:
 * <p>
 * GtkSignalListItemFactory::setup is emitted to set up permanent things on the listitem. This usually means
 * constructing the widgets used in the row and adding them to the listitem.
 * <p>
 * GtkSignalListItemFactory::bind is emitted to bind the item passed via GtkListItem:item to the widgets that have been
 * created in step 1 or to add item-specific widgets. Signals are connected to listen to changes - both to changes in
 * the item to update the widgets or to changes in the widgets to update the item. After this signal has been called,
 * the listitem may be shown in a list widget.
 * <p>
 * GtkSignalListItemFactory::unbind is emitted to undo everything done in step 2. Usually this means disconnecting
 * signal handlers. Once this signal has been called, the listitem will no longer be used in a list widget.
 * <p>
 * GtkSignalListItemFactory::bind and GtkSignalListItemFactory::unbind may be emitted multiple times again to bind the
 * listitem for use with new items. By reusing list-items, potentially costly setup can be avoided. However, it means
 * code needs to make sure to properly clean up the listitem in step 3 so that no information from the previous use
 * leaks into the next use.
 * <p>
 * GtkSignalListItemFactory::teardown is emitted to allow undoing the effects of GtkSignalListItemFactory::setup.
 * After this signal was emitted on a listitem, the listitem will be destroyed and not be used again.
 * <p>
 * Note that during the signal emissions, changing properties on the list-items passed will not trigger notify signals
 * as the listitem's notifications are frozen. See g_object_freeze_notify() for details.
 * <p>
 * For tracking changes in other properties in the listitem, the ::notify signal is recommended. The signal can be
 * connected in the GtkSignalListItemFactory::setup signal and removed again during GtkSignalListItemFactory::teardown.
 */
public class GtkSignalListItemFactory extends GtkListItemFactory {
    private static final GtkSignalListItemFactoryLibrary library = new GtkSignalListItemFactoryLibrary();

    public GtkSignalListItemFactory(Pointer cReference) {
        super(cReference);
    }

    public GtkSignalListItemFactory() {
        super(library.gtk_signal_list_item_factory_new());
    }

    /**
     * Connect a signal
     *
     * @param s       Detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to the signal
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef) {
        connect(s.getDetailedName(), fn, dataRef, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s     detailed name of signal
     * @param fn    function to invoke on signal
     * @param flags connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, GConnectFlags... flags) {
        connect(s.getDetailedName(), fn, null, flags);
    }

    /**
     * Connect a signal
     *
     * @param s  detailed name of signal
     * @param fn function to invoke on signal
     */
    public void connect(Signals s, GCallbackFunction fn) {
        connect(s.getDetailedName(), fn, Pointer.NULL, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s       detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
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
                return s.getDetailedName();
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                fn.invoke(relevantThing, relevantData);
            }
        });
    }

    public static class Signals extends JGTKObject.Signals {

        /**
         * Emitted when an object has been bound, for example when a new GtkListItem:item has been set on a listitem
         * and should be bound for use.
         * <p>
         * After this signal was emitted, the object might be shown in a GtkListView or other widget.
         * <p>
         * The GtkSignalListItemFactory::unbind signal is the opposite of this signal and can be used to undo
         * everything done in this signal.
         */
        public static final Signals BIND = new Signals("bind");

        /**
         * Emitted when a new listitem has been created and needs to be setup for use.
         * <p>
         * It is the first signal emitted for every listitem.
         * <p>
         * The GtkSignalListItemFactory::teardown signal is the opposite of this signal and can be used to undo
         * everything done in this signal.
         */
        public static final Signals SETUP = new Signals("setup");

        /**
         * Emitted when an object is about to be destroyed.
         * <p>
         * It is the last signal ever emitted for this object.
         * <p>
         * This signal is the opposite of the GtkSignalListItemFactory::setup signal and should be used to undo
         * everything done in that signal.
         */
        public static final Signals TEAR_DOWN = new Signals("teardown");

        /**
         * Emitted when an object has been unbound from its item, for example when a listitem was removed from use in a
         * list widget and its new GtkListItem:item is about to be unset.
         * <p>
         * This signal is the opposite of the GtkSignalListItemFactory::bind signal and should be used to undo
         * everything done in that signal.
         */
        public static final Signals UNBIND = new Signals("unbind");

        protected Signals(String cValue) {
            super(cValue);
        }
    }

    protected static class GtkSignalListItemFactoryLibrary extends GtkListItemFactoryLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new GtkSignalListItemFactory.
         * <p>
         * You need to connect signal handlers before you use it.
         *
         * @return A new GtkSignalListItemFactory
         */
        public native Pointer gtk_signal_list_item_factory_new();
    }
}
