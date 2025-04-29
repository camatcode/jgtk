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

import com.gitlab.ccook.jgtk.GObject;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkFilterChange;
import com.gitlab.ccook.jgtk.enums.GtkFilterMatch;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * A GtkFilter object describes the filtering to be performed by a GtkFilterListModel.
 * <p>
 * The model will use the filter to determine if it should include items or not by calling gtk_filter_match() for each
 * item and only keeping the ones that the function returns TRUE for.
 * <p>
 * Filters may change what items they match through their lifetime. In that case, they will emit the GtkFilter::changed
 * signal to notify that previous filter results are no longer valid and that items should be checked again via
 * gtk_filter_match().
 * <p>
 * GTK provides various pre-made filter implementations for common filtering operations. These filters often include
 * properties that can be linked to various widgets to easily allow searches.
 * <p>
 * However, in particular for large lists or complex search methods, it is also possible to subclass GtkFilter and
 * provide one's own filter.
 */
public class GtkFilter extends GObject {


    private static final GtkFilter.GtkFilterLibrary library = new GtkFilter.GtkFilterLibrary();


    public GtkFilter(Pointer cReference) {
        super(cReference);
    }

    /**
     * Notifies all users of the filter that it has changed.
     * <p>
     * This emits the GtkFilter::changed signal. Users of the filter should then check items again via
     * gtk_filter_match().
     * <p>
     * Depending on the change parameter, not all items need to be changed, but only some. Refer to the
     * GtkFilterChange documentation for details.
     * <p>
     * This function is intended for implementors of GtkFilter subclasses and should not be called from other
     * functions.
     *
     * @param change How the filter changed.
     */
    public void notifyChanged(GtkFilterChange change) {
        if (change != null) {
            library.gtk_filter_changed(getCReference(), change.getCValue());
        }
    }


    /**
     * Gets the known strictness of filters.
     * <p>
     * If the strictness is not known, GTK_FILTER_MATCH_SOME is returned.
     * <p>
     * This value may change after emission of the GtkFilter::changed signal.
     * <p>
     * This function is meant purely for optimization purposes, filters can choose to omit implementing it,
     * but GtkFilterListModel uses it.
     *
     * @return The strictness of self.
     */
    public GtkFilterMatch getStrictness() {
        return GtkFilterMatch.getMatchFromCValue(library.gtk_filter_get_strictness(getCReference()));
    }

    /**
     * Checks if the given item is matched by the filter or not.
     *
     * @param item The item to check.
     * @return TRUE if the filter matches the item and a filter model should keep it, FALSE if not.
     */
    public boolean doesMatch(GObject item) {
        if (item != null) {
            return library.gtk_filter_match(getCReference(), item.getCReference());
        }
        return false;
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

    @SuppressWarnings("SameParameterValue")
    public static class Signals extends JGTKObject.Signals {
        /**
         * Emitted whenever the filter changed.
         * <p>
         * Users of the filter should then check items again via gtk_filter_match().
         * <p>
         * GtkFilterListModel handles this signal automatically.
         * <p>
         * Depending on the change parameter, not all items need to be checked, but only some. Refer to the
         * GtkFilterChange documentation for details.
         */
        public final static Signals CHANGED = new Signals("changed");


        protected Signals(String cValue) {
            super(cValue);
        }
    }

    static class GtkFilterLibrary extends GtkWidget.GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Notifies all users of the filter that it has changed.
         * <p>
         * This emits the GtkFilter::changed signal. Users of the filter should then check items again via
         * gtk_filter_match().
         * <p>
         * Depending on the change parameter, not all items need to be changed, but only some. Refer to the
         * GtkFilterChange documentation for details.
         * <p>
         * This function is intended for implementors of GtkFilter subclasses and should not be called from other
         * functions.
         *
         * @param self   self
         * @param change How the filter changed. Type: GtkFilterChange
         */
        public native void gtk_filter_changed(Pointer self, int change);

        /**
         * Gets the known strictness of filters.
         * <p>
         * If the strictness is not known, GTK_FILTER_MATCH_SOME is returned.
         * <p>
         * This value may change after emission of the GtkFilter::changed signal.
         * <p>
         * This function is meant purely for optimization purposes, filters can choose to omit implementing it,
         * but GtkFilterListModel uses it.
         *
         * @param self self
         * @return The strictness of self. Type: GtkFilterMatch
         */
        public native int gtk_filter_get_strictness(Pointer self);

        /**
         * Checks if the given item is matched by the filter or not.
         *
         * @param self self
         * @param item The item to check. Type: GObject
         * @return TRUE if the filter matches the item and a filter model should keep it, FALSE if not.
         */
        public native boolean gtk_filter_match(Pointer self, Pointer item);
    }
}
