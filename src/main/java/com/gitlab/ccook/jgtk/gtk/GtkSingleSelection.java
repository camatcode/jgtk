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

import com.gitlab.ccook.jgtk.GListModel;
import com.gitlab.ccook.jgtk.GObject;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkSelectionModel;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

@SuppressWarnings("unchecked")
public class GtkSingleSelection<T extends JGTKObject> extends GObject implements GListModel<T>, GtkSelectionModel {
    private static final GtkSingleSelectionLibrary library = new GtkSingleSelectionLibrary();

    public GtkSingleSelection(Pointer cRef) {
        super(cRef);
    }

    public GtkSingleSelection(Pointer cReference, Class<? extends JGTKObject> clss) {
        super(cReference);
    }

    public GtkSingleSelection(GListModel<T> model) {
        super(library.gtk_single_selection_new(pointerOrNull(model)));
    }

    public void canAutoSelect(boolean doesAutoSelect) {
        library.gtk_single_selection_set_autoselect(getCReference(), doesAutoSelect);
    }

    /**
     * Returns if the item can be de-selected
     *
     * @return TRUE if item can be de-selected
     */
    public boolean couldDeselect() {
        return library.gtk_single_selection_get_can_unselect(getCReference());
    }

    /**
     * Checks if auto-select has been enabled or disabled
     *
     * @return TRUE if auto-select is enabled.
     */
    public boolean doesAutoSelect() {
        return library.gtk_single_selection_get_autoselect(getCReference());
    }

    /**
     * Gets the model that self is wrapping.
     *
     * @return The model being wrapped.
     */
    public GListModel<GObject> getModel() {
        Pointer ref = library.gtk_single_selection_get_model(getCReference());
        return new GListModel<GObject>() {
            @Override
            public Pointer getCReference() {
                return ref;
            }

            @Override
            public JGTKObject toJGTKObject(JGTKObject obj) {
                return JGTKObject.newObjectFromType(obj.getCReference(), GObject.class);
            }
        };
    }

    public void setModel(GListModel<? extends GObject> m) {
        library.gtk_single_selection_set_model(getCReference(), pointerOrNull(m));
    }

    public Option<GObject> getSelectedItem() {
        Option<Pointer> p = new Option<>(library.gtk_single_selection_get_selected_item(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GObject) JGTKObject.newObjectFromType(p.get(), GObject.class));
        }
        return Option.NONE;
    }

    /**
     * Gets the position of the selected item.
     * <p>
     * If no item is selected, NONE is returned.
     *
     * @return The position of the selected item.
     */
    public Option<Integer> getSelectionPosition() {
        int value = library.gtk_single_selection_get_selected(getCReference());
        if (value > 0 && value != Integer.MAX_VALUE) {
            return new Option<>(value);
        }
        return Option.NONE;
    }

    public void select(int position) {
        library.gtk_single_selection_set_selected(getCReference(), position);
    }

    public void shouldDeselect(boolean couldDeselect) {
        library.gtk_single_selection_set_can_unselect(getCReference(), couldDeselect);
    }

    protected static class GtkSingleSelectionLibrary extends GtkListItemFactory.GtkListItemFactoryLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Checks if auto-select has been enabled or disabled via gtk_single_selection_set_autoselect()
         *
         * @param self self
         * @return TRUE if auto-select is enabled.
         */
        public native boolean gtk_single_selection_get_autoselect(Pointer self);

        /**
         * If TRUE, gtk_selection_model_unselect_item() is supported and allows unselecting the selected item
         *
         * @param self self
         * @return TRUE to support unselecting.
         */
        public native boolean gtk_single_selection_get_can_unselect(Pointer self);

        /**
         * Gets the model that self is wrapping.
         *
         * @param self self
         * @return The model being wrapped. Type: A list model of GObject
         *         The return value can be NULL.
         */
        public native Pointer gtk_single_selection_get_model(Pointer self);

        /**
         * Gets the position of the selected item.
         * <p>
         * If no item is selected, GTK_INVALID_LIST_POSITION is returned.
         *
         * @param self self
         * @return The position of the selected item.
         */
        public native int gtk_single_selection_get_selected(Pointer self);

        /**
         * Gets the selected item.
         * <p>
         * If no item is selected, NULL is returned.
         *
         * @param self self
         * @return The selected item. Type: GObject
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_single_selection_get_selected_item(Pointer self);

        /**
         * Creates a new selection to handle model.
         *
         * @param model The GListModel to manage.
         *              <p>
         *              The argument can be NULL.
         * @return A new GtkSingleSelection
         */
        public native Pointer gtk_single_selection_new(Pointer model);

        /**
         * Enables or disables auto-select.
         * <p>
         * If auto-select is TRUE, self will enforce that an item is always selected. It will select a new item when the
         * currently selected item is deleted, and it will disallow unselecting the current item.
         *
         * @param self       self
         * @param autoselect TRUE to always select an item.
         */
        public native void gtk_single_selection_set_autoselect(Pointer self, boolean autoselect);

        /**
         * If TRUE, unselecting the current item via gtk_selection_model_unselect_item() is supported.
         * <p>
         * Note that setting GtkSingleSelection:autoselect will cause unselecting to not work, so it practically makes
         * no sense to set both at the same time the same time.
         *
         * @param self         self
         * @param can_unselect TRUE to allow unselecting.
         */
        public native void gtk_single_selection_set_can_unselect(Pointer self, boolean can_unselect);

        /**
         * Sets the model that self should wrap.
         * <p>
         * If model is NULL, self will be empty.
         *
         * @param self  self
         * @param model A GListModel to wrap.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_single_selection_set_model(Pointer self, Pointer model);

        /**
         * Selects the item at the given position.
         * <p>
         * If the list does not have an item at position or GTK_INVALID_LIST_POSITION is given, the behavior depends on
         * the value of the GtkSingleSelection:autoselect property: If it is set, no change will occur and the old item
         * will stay selected. If it is unset, the selection will be unset and no item will be selected.
         *
         * @param self     self
         * @param position The item to select or GTK_INVALID_LIST_POSITION.
         */
        public native void gtk_single_selection_set_selected(Pointer self, int position);
    }
}
