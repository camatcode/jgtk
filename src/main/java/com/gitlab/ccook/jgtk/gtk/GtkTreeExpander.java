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
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkTreeExpander is a widget that provides an expander for a list.
 * <p>
 * It is typically placed as a bottommost child into a GtkListView to allow users to expand and collapse children in a
 * list with a GtkTreeListModel. GtkTreeExpander provides the common UI elements, gestures and keybindings for this
 * purpose.
 * <p>
 * On top of this, the "listitem.expand", "listitem.collapse" and "listitem.toggle-expand" actions are provided to allow
 * adding custom UI for managing expanded state.
 * <p>
 * It is important to mention that you want to set the GtkListItem:focusable property to FALSE when using this widget,
 * as you want the keyboard focus to be in the tree expander, and not inside the list to make use of the keybindings.
 * <p>
 * The GtkTreeListModel must be set to not be pass-through. Then it will provide GtkTreeListRow items which can be set
 * via gtk_tree_expander_set_list_row() on the expander. The expander will then watch that row item automatically.
 * gtk_tree_expander_set_child() sets the widget that displays the actual row contents.
 * <p>
 * GtkTreeExpander can be modified with properties such as GtkTreeExpander:indent-for-icon,
 * GtkTreeExpander:indent-for-depth, and GtkTreeExpander:hide-expander to achieve a different appearance. This can even
 * be done to influence individual rows, for example by binding the GtkTreeExpander:hide-expander property to the item
 * count of the model of the tree lis trow, to hide the expander for rows without children, even if the row is
 * expandable.
 */
@SuppressWarnings("unchecked")
public class GtkTreeExpander extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {
    private static final GtkTreeExpanderLibrary library = new GtkTreeExpanderLibrary();

    public GtkTreeExpander(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkTreeExpander
     */
    public GtkTreeExpander() {
        super(library.gtk_tree_expander_new());
    }

    /**
     * TRUE if the child should be indented when not expandable. Otherwise, FALSE.
     *
     * @return TRUE if the child should be indented when not expandable. Otherwise, FALSE.
     * @since 4.6
     */
    public boolean doesIndentForIcon() {
        return library.gtk_tree_expander_get_indent_for_icon(getCReference());
    }

    /**
     * Gets the child widget displayed by self.
     *
     * @return The child displayed by self, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_tree_expander_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the content widget to display.
     *
     * @param child A GtkWidget
     *              <p>
     *              The argument can be NULL.
     */
    public void setChild(GtkWidget child) {
        library.gtk_tree_expander_set_child(getCReference(), pointerOrNull(child));
    }

    /**
     * Forwards the item set on the GtkTreeListRow that self is managing.
     * <p>
     * This call is essentially equivalent to calling:
     * <p>
     * gtk_tree_list_row_get_item (gtk_tree_expander_get_list_row (@self));
     *
     * @return The item of the row, if defined
     */
    public Option<GObject> getItem() {
        Option<Pointer> p = new Option<>(library.gtk_tree_expander_get_item(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GObject) JGTKObject.newObjectFromType(p.get(), GObject.class));
        }
        return Option.NONE;
    }

    /**
     * Gets the list row managed by self.
     *
     * @return The list row displayed by self, if defined
     */
    public Option<GtkTreeListRow> getListRow() {
        Option<Pointer> p = new Option<>(library.gtk_tree_expander_get_list_row(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkTreeListRow) JGTKObject.newObjectFromType(p.get(), GtkTreeListRow.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the tree list row that this expander should manage.
     *
     * @param row A GtkTreeListRow
     *            <p>
     *            The argument can be NULL.
     */
    public void setListRow(GtkTreeListRow row) {
        library.gtk_tree_expander_set_list_row(getCReference(), pointerOrNull(row));
    }

    /**
     * Sets if the TreeExpander should indent the child by the width of an expander-icon when it is not expandable.
     *
     * @param shouldIndent TRUE if the child should be indented without expander. Otherwise, FALSE.
     * @since 4.6
     */
    public void shouldIndentForIcon(boolean shouldIndent) {
        library.gtk_tree_expander_set_indent_for_icon(getCReference(), shouldIndent);
    }

    protected static class GtkTreeExpanderLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget displayed by self.
         *
         * @param self self
         * @return The child displayed by self. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_expander_get_child(Pointer self);

        /**
         * TRUE if the child should be indented when not expandable. Otherwise, FALSE.
         *
         * @param self self
         * @return TRUE if the child should be indented when not expandable. Otherwise, FALSE.
         * @since 4.6
         */
        public native boolean gtk_tree_expander_get_indent_for_icon(Pointer self);

        /**
         * Forwards the item set on the GtkTreeListRow that self is managing.
         * <p>
         * This call is essentially equivalent to calling:
         * <p>
         * gtk_tree_list_row_get_item (gtk_tree_expander_get_list_row (@self));
         *
         * @param self self
         * @return The item of the row. Type: GObject
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_expander_get_item(Pointer self);

        /**
         * Gets the list row managed by self.
         *
         * @param self self
         * @return The list row displayed by self. Type: GtkTreeListRow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_tree_expander_get_list_row(Pointer self);

        /**
         * Creates a new GtkTreeExpander
         *
         * @return A new GtkTreeExpander
         */
        public native Pointer gtk_tree_expander_new();

        /**
         * Sets the content widget to display.
         *
         * @param self  self
         * @param child A GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_tree_expander_set_child(Pointer self, Pointer child);

        /**
         * Sets if the TreeExpander should indent the child by the width of an expander-icon when it is not expandable.
         *
         * @param self            self
         * @param indent_for_icon TRUE if the child should be indented without expander. Otherwise, FALSE.
         * @since 4.6
         */
        public native void gtk_tree_expander_set_indent_for_icon(Pointer self, boolean indent_for_icon);

        /**
         * Sets the tree list row that this expander should manage.
         *
         * @param self     self
         * @param list_row A GtkTreeListRow
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_tree_expander_set_list_row(Pointer self, Pointer list_row);
    }
}
