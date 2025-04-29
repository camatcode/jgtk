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
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkTreeListRow is used by GtkTreeListModel to represent items.
 * <p>
 * It allows navigating the model as a tree and modify the state of rows.
 * <p>
 * GtkTreeListRow instances are created by a GtkTreeListModel only when the GtkTreeListModel:passthrough property is not
 * set.
 * <p>
 * There are various support objects that can make use of GtkTreeListRow objects, such as the GtkTreeExpander widget
 * that allows displaying an icon to expand or collapse a row or GtkTreeListRowSorter that makes it possible to sort
 * trees properly.
 */
@SuppressWarnings("unchecked")
public class GtkTreeListRow extends JGTKObject {
    private static final GtkTreeListRowLibrary library = new GtkTreeListRowLibrary();

    public GtkTreeListRow(Pointer cReference) {
        super(cReference);
    }

    /**
     * Checks if a row can be expanded.
     * <p>
     * This does not mean that the row is actually expanded, this can be checked with
     * gtk_tree_list_row_get_expanded().
     * <p>
     * If a row is expandable never changes until the row is removed from its model at which point it will forever
     * return FALSE.
     *
     * @return TRUE if the row is expandable.
     */
    public boolean canExpand() {
        return library.gtk_tree_list_row_is_expandable(getCReference());
    }

    /**
     * Gets Child Row at position
     * If self is not expanded or position is greater than the number of children, NONE is returned.
     *
     * @param position Position of the child to get.
     * @return The child in position.
     */
    public Option<GtkTreeListRow> getChildRow(int position) {
        Option<Pointer> p = new Option<>(library.gtk_tree_list_row_get_child_row(getCReference(), Math.max(0, position)));
        if (p.isDefined()) {
            return new Option<>((GtkTreeListRow) JGTKObject.newObjectFromType(p.get(), GtkTreeListRow.class));
        }
        return Option.NONE;
    }

    /**
     * If the row is expanded, gets the model holding the children of self.
     * <p>
     * This model is the model created by the GtkTreeListModelCreateModelFunc and contains the original items, no matter
     * what value GtkTreeListModel:passthrough is set to.
     *
     * @return The model containing the children.
     */
    public GListModel<GObject> getChildren() {
        Pointer p = library.gtk_tree_list_row_get_children(getCReference());
        return new GenericGListModel<>(GObject.class, p);
    }

    /**
     * Gets the depth of this row.
     * <p>
     * Rows that correspond to items in the root model have a depth of zero, rows corresponding to items of models
     * of direct children of the root model have a depth of 1 and so on.
     * <p>
     * The depth of a row never changes until the row is removed from its model at which point it will forever
     * return 0.
     *
     * @return The depth of this row.
     */
    public int getDepth() {
        return library.gtk_tree_list_row_get_depth(getCReference());
    }

    /**
     * Gets the item corresponding to this row,
     *
     * @return The item of this row.
     */
    public Option<GObject> getItem() {
        Option<Pointer> p = new Option<>(library.gtk_tree_list_row_get_item(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GObject) JGTKObject.newObjectFromType(p.get(), GObject.class));
        }
        return Option.NONE;
    }

    /**
     * Gets the row representing the parent for self.
     * <p>
     * That is the row that would need to be collapsed to make this row disappear.
     * <p>
     * If self is a row corresponding to the root model, NONE is returned.
     * <p>
     * The value returned by this function never changes until the row is removed from its model at which point it will
     * forever return NONE.
     *
     * @return The parent of self, if defined
     */
    public Option<GtkTreeListRow> getParent() {
        Option<Pointer> p = new Option<>(library.gtk_tree_list_row_get_parent(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkTreeListRow) JGTKObject.newObjectFromType(p.get(), GtkTreeListRow.class));
        }
        return Option.NONE;
    }

    public int getPosition() {
        return library.gtk_tree_list_row_get_position(getCReference());
    }

    /**
     * Gets if a row is currently expanded.
     *
     * @return TRUE if the row is expanded.
     */
    public boolean isExpanded() {
        return library.gtk_tree_list_row_get_expanded(getCReference());
    }

    public void setExpanded(boolean isExpanded) {
        library.gtk_tree_list_row_set_expanded(getCReference(), isExpanded);
    }

    protected static class GtkTreeListRowLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * If self is not expanded or position is greater than the number of children, NULL is returned.
         *
         * @param self     self
         * @param position Position of the child to get.
         * @return The child in position. Type: GtkTreeListRow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_list_row_get_child_row(Pointer self, int position);

        /**
         * If the row is expanded, gets the model holding the children of self.
         * <p>
         * This model is the model created by the GtkTreeListModelCreateModelFunc and contains the original items,
         * no matter what value GtkTreeListModel:passthrough is set to.
         *
         * @param self self
         * @return The model containing the children. Type: A list model of GObject
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_list_row_get_children(Pointer self);

        /**
         * Gets the depth of this row.
         * <p>
         * Rows that correspond to items in the root model have a depth of zero, rows corresponding to items of models
         * of direct children of the root model have a depth of 1 and so on.
         * <p>
         * The depth of a row never changes until the row is removed from its model at which point it will forever
         * return 0.
         *
         * @param self self
         * @return The depth of this row.
         */
        public native int gtk_tree_list_row_get_depth(Pointer self);

        /**
         * Gets if a row is currently expanded.
         *
         * @param self self
         * @return TRUE if the row is expanded.
         */
        public native boolean gtk_tree_list_row_get_expanded(Pointer self);

        /**
         * Gets the item corresponding to this row,
         *
         * @param self self
         * @return The item of this row.
         *         <p>
         *         The return value can be NULL (for backwards compatibility reasons).
         */
        public native Pointer gtk_tree_list_row_get_item(Pointer self);

        /**
         * Gets the row representing the parent for self.
         * <p>
         * That is the row that would need to be collapsed to make this row disappear.
         * <p>
         * If self is a row corresponding to the root model, NULL is returned.
         * <p>
         * The value returned by this function never changes until the row is removed from its model at which point it
         * will forever return NULL.
         *
         * @param self self
         * @return The parent of self. Type: GtkTreeListRow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_list_row_get_parent(Pointer self);

        /**
         * Returns the position in the GtkTreeListModel that self occupies at the moment.
         *
         * @param self self
         * @return The position in the model.
         */
        public native int gtk_tree_list_row_get_position(Pointer self);

        /**
         * Checks if a row can be expanded.
         * <p>
         * This does not mean that the row is actually expanded, this can be checked with
         * gtk_tree_list_row_get_expanded().
         * <p>
         * If a row is expandable never changes until the row is removed from its model at which point it will forever
         * return FALSE.
         *
         * @param self self
         * @return TRUE if the row is expandable.
         */
        public native boolean gtk_tree_list_row_is_expandable(Pointer self);

        /**
         * Expands or collapses a row.
         * <p>
         * If a row is expanded, the model of calling the GtkTreeListModelCreateModelFunc for the row's item will be
         * inserted after this row. If a row is collapsed, those items will be removed from the model.
         * <p>
         * If the row is not expandable, this function does nothing.
         *
         * @param self     self
         * @param expanded TRUE if the row should be expanded.
         */
        public native void gtk_tree_list_row_set_expanded(Pointer self, boolean expanded);
    }
}
