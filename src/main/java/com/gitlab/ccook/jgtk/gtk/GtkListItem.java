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
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkListItem is used by list widgets to represent items in a GListModel.
 * <p>
 * The GtkListItems are managed by the list widget (with its factory) and cannot be created by applications, but they
 * need to be populated by application code. This is done by calling gtk_list_item_set_child().
 * <p>
 * GtkListItems exist in 2 stages:
 * <p>
 * The unbound stage where the listitem is not currently connected to an item in the list. In that case, the
 * GtkListItem:item property is set to NULL.
 * <p>
 * The bound stage where the listitem references an item from the list. The GtkListItem:item property is not
 * NULL.
 */
@SuppressWarnings("unchecked")
public class GtkListItem extends GObject {
    private static final GtkListItemLibrary library = new GtkListItemLibrary();

    public GtkListItem(Pointer cReference) {
        super(cReference);
    }

    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_list_item_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    public void setChild(GtkWidget w) {
        library.gtk_list_item_set_child(getCReference(), pointerOrNull(w));
    }

    public Option<GObject> getItem() {
        Option<Pointer> p = new Option<>(library.gtk_list_item_get_item(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GObject) JGTKObject.newObjectFromType(p.get(), GObject.class));
        }
        return Option.NONE;
    }

    public Option<Integer> getPosition() {
        int value = library.gtk_list_item_get_position(getCReference());
        if (value >= 0 && value < Integer.MAX_VALUE) {
            return new Option<>(value);
        }
        return Option.NONE;
    }

    public boolean isActivatable() {
        return library.gtk_list_item_get_activatable(getCReference());
    }

    public void setActivatable(boolean isActivatable) {
        library.gtk_list_item_set_activatable(getCReference(), isActivatable);
    }

    public boolean isSelectable() {
        return library.gtk_list_item_get_selectable(getCReference());
    }

    public void setSelectable(boolean isSelectable) {
        library.gtk_list_item_set_selectable(getCReference(), isSelectable);
    }

    public boolean isSelected() {
        return library.gtk_list_item_get_selected(getCReference());
    }

    protected static class GtkListItemLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Checks if a list item has been set to be activatable via gtk_list_item_set_activatable().
         *
         * @param self self
         * @return TRUE if the item is activatable.
         */
        public native boolean gtk_list_item_get_activatable(Pointer self);

        /**
         * Gets the child previously set via gtk_list_item_set_child() or NULL if none was set.
         *
         * @param self self
         * @return The child. Type: GtkWidget
         *         <p>
         *         The return value can be NULL
         */
        public native Pointer gtk_list_item_get_child(Pointer self);

        /**
         * Gets the model item that associated with self.
         * <p>
         * If self is unbound, this function returns NULL.
         *
         * @param self self
         * @return The item displayed. Type: GObject
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_list_item_get_item(Pointer self);

        /**
         * Gets the position that is in the model that self currently displays.
         * <p>
         * If self is unbound, GTK_INVALID_LIST_POSITION is returned.
         *
         * @param self self
         * @return The position of this item.
         */
        public native int gtk_list_item_get_position(Pointer self);

        /**
         * Checks if a list item has been set to be selectable via gtk_list_item_set_selectable().
         * <p>
         * Do not confuse this function with gtk_list_item_get_selected().
         *
         * @param self self
         * @return TRUE if the item is selectable.
         */
        public native boolean gtk_list_item_get_selectable(Pointer self);

        /**
         * Checks if the item is displayed as selected.
         * <p>
         * The selected state is maintained by the list widget and its model and cannot be set otherwise.
         *
         * @param self self
         * @return TRUE if the item is selected.
         */
        public native boolean gtk_list_item_get_selected(Pointer self);

        /**
         * Sets self to be activatable.
         * <p>
         * If an item is activatable, double-clicking on the item, using the Return key or calling gtk_widget_activate()
         * will activate the item. Activating instructs the containing view to handle activation. GtkListView for
         * example will be emitting the GtkListView::activate signal.
         * <p>
         * By default, list items are activatable.
         *
         * @param self        self
         * @param activatable If the item should be activatable.
         */
        public native void gtk_list_item_set_activatable(Pointer self, boolean activatable);

        /**
         * Sets the child to be used for this listitem.
         * <p>
         * This function is typically called by applications when setting up a listitem so that the widget can be reused
         * when binding it multiple times.
         *
         * @param self  self
         * @param child The list item's child or NULL to unset.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_list_item_set_child(Pointer self, Pointer child);

        /**
         * Sets self to be selectable.
         * <p>
         * If an item is selectable, clicking on the item or using the keyboard will try to select or unselect the item.
         * If this succeeds is up to the model to determine, as it is managing the selected state.
         * <p>
         * Note that this means that making an item non-selectable has no influence on the selected state at all. A
         * non-selectable item may still be selected.
         * <p>
         * By default, list items are selectable. When rebinding them to a new item, they will also be reset to be
         * selectable by GTK.
         *
         * @param self       self
         * @param selectable If the item should be selectable.
         */
        public native void gtk_list_item_set_selectable(Pointer self, boolean selectable);
    }
}
