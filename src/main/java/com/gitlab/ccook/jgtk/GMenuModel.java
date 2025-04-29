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
package com.gitlab.ccook.jgtk;

import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public class GMenuModel extends JGTKConnectableObject {
    private static final GMenuModelLibrary library = new GMenuModelLibrary();

    public GMenuModel(Pointer ref) {
        super(ref);
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

    /**
     * Queries the item at position item_index in model for the attribute specified by attribute.
     * <p>
     * If expected_type is non-NULL then it specifies the expected type of the attribute. If it is NULL then any
     * type will be accepted.
     * <p>
     * If the attribute exists and matches expected_type (or if the expected type is unspecified) then the value is
     * returned.
     * <p>
     * If the attribute does not exist, or does not match the expected type then NULL is returned.
     *
     * @param itemIndex    The index of the item.
     * @param attribute    The attribute to query.
     * @param expectedType The expected type of the attribute, or NULL.
     *                     <p>
     *                     The argument can be NULL.
     * @return The value of the attribute.
     *         <p>
     *         The return value can be NONE.
     */
    public Option<GVariant> getItemAttribute(int itemIndex, String attribute, GVariantType expectedType) {
        Option<Pointer> p = new Option<>(library.g_menu_model_get_item_attribute_value(getCReference(), itemIndex, attribute, pointerOrNull(expectedType)));
        if (p.isDefined()) {
            return new Option<>((GVariant) JGTKObject.newObjectFromType(p.get(), GVariant.class));
        }
        return Option.NONE;
    }

    /**
     * Queries the item at position item_index in model for the link specified by link.
     * <p>
     * If the link exists, the linked GMenuModel is returned. If the link does not exist, NULL is returned.
     *
     * @param itemIndex The index of the item.
     * @param link      The link to query.
     * @return The linked GMenuModel, or NONE.
     */
    public Option<GMenuModel> getItemLink(int itemIndex, String link) {
        Option<Pointer> p = new Option<>(library.g_menu_model_get_item_link(getCReference(), itemIndex, link));
        if (p.isDefined()) {
            return new Option<>((GMenuModel) JGTKObject.newObjectFromType(p.get(), GMenuModel.class));
        }
        return Option.NONE;
    }

    public boolean isMutable() {
        return library.g_menu_model_is_mutable(getCReference());
    }

    /**
     * Requests emission of the GMenuModel::items-changed signal on model.
     * <p>
     * This function should never be called except by GMenuModel subclasses. Any other calls to this function will very
     * likely lead to a violation of the interface of the model.
     * <p>
     * The implementation should update its internal representation of the menu before emitting the signal. The
     * implementation should further expect to receive queries about the new state of the menu (and particularly added
     * menu items) while signal handlers are running.
     * <p>
     * The implementation must dispatch this call directly from a main-loop entry and not in response to calls —
     * particularly those from the GMenuModel API. Said another way: the menu must not change while user code is running
     * without returning to the main-loop.
     *
     * @param position     The position of the change.
     * @param itemsRemoved The number of items removed.
     * @param itemsAdded   The number of items added.
     */
    public void itemsChanged(int position, int itemsRemoved, int itemsAdded) {
        library.g_menu_model_items_changed(getCReference(), position, itemsRemoved, itemsAdded);
    }

    /**
     * Query the number of items in model.
     *
     * @return The number of items.
     */
    public int size() {
        return library.g_menu_model_get_n_items(getCReference());
    }

    protected static class GMenuModelLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Queries the item at position item_index in model for the attribute specified by attribute.
         * <p>
         * If expected_type is non-NULL then it specifies the expected type of the attribute. If it is NULL then any
         * type will be accepted.
         * <p>
         * If the attribute exists and matches expected_type (or if the expected type is unspecified) then the value is
         * returned.
         * <p>
         * If the attribute does not exist, or does not match the expected type then NULL is returned.
         *
         * @param model         self
         * @param item_index    The index of the item.
         * @param attribute     The attribute to query.
         * @param expected_type The expected type of the attribute, or NULL.
         *                      <p>
         *                      The argument can be NULL.
         * @return The value of the attribute.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer g_menu_model_get_item_attribute_value(Pointer model, int item_index, String attribute, Pointer expected_type);

        /**
         * Queries the item at position item_index in model for the link specified by link.
         * <p>
         * If the link exists, the linked GMenuModel is returned. If the link does not exist, NULL is returned.
         *
         * @param model      self
         * @param item_index The index of the item.
         * @param link       The link to query.
         * @return The linked GMenuModel, or NULL.
         */
        public native Pointer g_menu_model_get_item_link(Pointer model, int item_index, String link);

        /**
         * Query the number of items in model.
         *
         * @param model self
         * @return The number of items.
         */
        public native int g_menu_model_get_n_items(Pointer model);

        /**
         * Queries if model is mutable.
         * <p>
         * An immutable GMenuModel will never emit the GMenuModel::items-changed signal. Consumers of the model may
         * make optimisations accordingly.
         *
         * @param model self
         * @return TRUE if the model is mutable (ie: "items-changed" may be emitted).
         */
        public native boolean g_menu_model_is_mutable(Pointer model);

        /**
         * Requests emission of the GMenuModel::items-changed signal on model.
         * <p>
         * This function should never be called except by GMenuModel subclasses. Any other calls to this function will
         * very likely lead to a violation of the interface of the model.
         * <p>
         * The implementation should update its internal representation of the menu before emitting the signal. The
         * implementation should further expect to receive queries about the new state of the menu (and particularly
         * added menu items) while signal handlers are running.
         * <p>
         * The implementation must dispatch this call directly from a main-loop entry and not in response to calls —
         * particularly those from the GMenuModel API. Said another way: the menu must not change while user code is
         * running without returning to the main-loop.
         *
         * @param model    self
         * @param position The position of the change.
         * @param removed  The number of items removed.
         * @param added    The number of items added.
         */
        public native void g_menu_model_items_changed(Pointer model, int position, int removed, int added);
    }

    @SuppressWarnings("SameParameterValue")
    public static class Signals extends GtkWidget.Signals {
        /**
         * Emitted when a change has occurred to the menu.
         * <p>
         * The only changes that can occur to a menu is that items are removed or added. Items may not change (except by
         * being removed and added back in the same location). This signal is capable of describing both of those
         * changes (at the same time).
         * <p>
         * The signal means that starting at the index position, removed items were removed and added items were added
         * in their place. If removed is zero then only items were added. If added is zero then only items were removed.
         * <p>
         * As an example, if the menu contains items a, b, c, d (in that order) and the signal (2, 1, 3) occurs then
         * the new composition of the menu will be a, b, , , _, d (with each _ representing some new item).
         * <p>
         * Signal handlers may query the model (particularly the added items) and expect to see the results of the
         * modification that is being reported. The signal is emitted after the modification.
         */
        public static final Signals ITEMS_CHANGED = new Signals("items-changed");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }
}
