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
import com.gitlab.ccook.jgtk.callbacks.GtkTreeListModelCreateModelFunc;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

@SuppressWarnings("unchecked")
public class GtkTreeListModel<T extends JGTKObject> extends GObject implements GListModel<T> {
    private static final GtkTreeListModelLibrary library = new GtkTreeListModelLibrary();

    public GtkTreeListModel(Pointer cReference) {
        super(cReference);
    }

    public GtkTreeListModel(GListModel<T> root, boolean passThrough, boolean autoExpand, GtkTreeListModelCreateModelFunc createFunc) {
        super(library.gtk_tree_list_model_new(root.getCReference(), passThrough, autoExpand, createFunc, Pointer.NULL, null));
    }

    public boolean doesAutoExpand() {
        return library.gtk_tree_list_model_get_autoexpand(getCReference());
    }

    public boolean doesPassThrough() {
        return library.gtk_tree_list_model_get_passthrough(getCReference());
    }

    public Option<GtkTreeListRow> getChildRow(int position) {
        Option<Pointer> p = new Option<>(library.gtk_tree_list_model_get_child_row(getCReference(), position));
        if (p.isDefined()) {
            return new Option<>(new GtkTreeListRow(p.get()));
        }
        return Option.NONE;
    }

    public GListModel<T> getModel() {
        return new GenericGListModel<>(GObject.class, library.gtk_tree_list_model_get_model(getCReference()));
    }

    public Option<GtkTreeListRow> getRow(int position) {
        Option<Pointer> p = new Option<>(library.gtk_tree_list_model_get_row(getCReference(), position));
        if (p.isDefined()) {
            return new Option<>(new GtkTreeListRow(p.get()));
        }
        return Option.NONE;
    }

    public void shouldAutoExpand(boolean doesAutoExpand) {
        library.gtk_tree_list_model_set_autoexpand(getCReference(), doesAutoExpand);
    }

    protected static class GtkTreeListModelLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets whether the model is set to automatically expand new rows that get added.
         * <p>
         * This can be either rows added by changes to the underlying models or via gtk_tree_list_row_set_expanded().
         *
         * @param self self
         * @return TRUE if the model is set to auto-expand.
         */
        public native boolean gtk_tree_list_model_get_autoexpand(Pointer self);

        /**
         * Gets the row item corresponding to the child at index position for self's root model.
         * <p>
         * If position is greater than the number of children in the root model, NULL is returned.
         * <p>
         * Do not confuse this function with gtk_tree_list_model_get_row().
         *
         * @param self     self
         * @param position Position of the child to get.
         * @return The child in position. Type: GtkTreeListRow
         */
        public native Pointer gtk_tree_list_model_get_child_row(Pointer self, int position);

        /**
         * Gets the root model that self was created with.
         *
         * @param self self
         * @return The root model.
         */
        public native Pointer gtk_tree_list_model_get_model(Pointer self);

        /**
         * Gets whether the model is passing through original row items.
         * <p>
         * If this function returns FALSE, the GListModel functions for self return custom GtkTreeListRow objects. You
         * need to call gtk_tree_list_row_get_item() on these objects to get the original item.
         * <p>
         * If TRUE, the values of the child models are passed through in their original state. You then need to call
         * gtk_tree_list_model_get_row() to get the custom GtkTreeListRows.
         *
         * @param self self
         * @return TRUE if the model is passing through original row items.
         */
        public native boolean gtk_tree_list_model_get_passthrough(Pointer self);

        /**
         * Gets the row object for the given row.
         * <p>
         * If position is greater than the number of items in self, NULL is returned.
         * <p>
         * The row object can be used to expand and collapse rows as well as to inspect its position in the tree. See
         * its documentation for details.
         * <p>
         * This row object is persistent and will refer to the current item as long as the row is present in self,
         * independent of other rows being added or removed.
         * <p>
         * If self is set to not be passthrough, this function is equivalent to calling g_list_model_get_item().
         * <p>
         * Do not confuse this function with gtk_tree_list_model_get_child_row().
         *
         * @param self     self
         * @param position The position of the row to fetch.
         * @return The row item. Type: GtkTreeListRow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_list_model_get_row(Pointer self, int position);

        /**
         * Creates a new empty GtkTreeListModel displaying root with all rows collapsed.
         *
         * @param root         The GListModel to use as root.
         * @param passthrough  TRUE to pass through items from the models.
         * @param autoexpand   TRUE to set the auto-expand property and expand the root model.
         * @param create_func  Function to call to create the GListModel for the children of an item.
         * @param user_data    Data to pass to create_func.
         *                     <p>
         *                     The argument can be NULL.
         * @param user_destroy Function to call to free user_data.
         * @return A newly created GtkTreeListModel.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_list_model_new(Pointer root, boolean passthrough, boolean autoexpand, GtkTreeListModelCreateModelFunc create_func, Pointer user_data, Pointer user_destroy);

        /**
         * Sets whether the model should auto-expand.
         * <p>
         * If set to TRUE, the model will recursively expand all rows that get added to the model. This can be either
         * rows added by changes to the underlying models or via gtk_tree_list_row_set_expanded().
         *
         * @param self       self
         * @param autoexpand TRUE to make the model auto-expand its rows.
         */
        public native void gtk_tree_list_model_set_autoexpand(Pointer self, boolean autoexpand);
    }
}
