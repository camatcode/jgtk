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
package com.gitlab.ccook.jgtk.gtk;

import com.gitlab.ccook.jgtk.GList;
import com.gitlab.ccook.jgtk.GListModel;
import com.gitlab.ccook.jgtk.GtkAdjustment;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.*;
import com.gitlab.ccook.jgtk.enums.GtkSelectionMode;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkListBox is a vertical list.
 * <p>
 * A GtkListBox only contains GtkListBoxRow children. These rows can be dynamically sorted and filtered, and headers
 * can be added dynamically depending on the row content. It also allows keyboard and mouse navigation and selection
 * like a typical list.
 * <p>
 * Using GtkListBox is often an alternative to GtkTreeView, especially when the list-contents has a more complicated
 * layout than what is allowed by a GtkCellRenderer, or when the list-contents is interactive (i.e. has a button in it).
 * <p>
 * Although a GtkListBox must have only GtkListBoxRow children, you can add any kind of widget to it via
 * gtk_list_box_prepend(), gtk_list_box_append() and gtk_list_box_insert() and a GtkListBoxRow widget will
 * automatically be inserted between the list and the widget.
 * <p>
 * GtkListBoxRows can be marked as activatable or selectable. If a row is activatable, GtkListBox::row-activated
 * will be emitted for it when the user tries to activate it. If it is selectable, the row will be marked as selected
 * when the user tries to select it.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class GtkListBox extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {


    private static final GtkListBoxLibrary library = new GtkListBoxLibrary();

    public GtkListBox(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkListBox container.
     */
    public GtkListBox() {
        super(library.gtk_list_box_new());
    }

    /**
     * Add a drag highlight to a row.
     * <p>
     * This is a helper function for implementing DnD onto a GtkListBox. The passed in row will be highlighted by
     * setting the GTK_STATE_FLAG_DROP_ACTIVE state and any previously highlighted row will have its highlight removed.
     * <p>
     * The row will also have its highlight removed when the widget gets a drag leave event.
     *
     * @param c A GtkListBoxRow
     */
    public void addDragHighlight(GtkListBoxRow c) {
        if (c != null) {
            library.gtk_list_box_drag_highlight_row(getCReference(), c.getCReference());
        }
    }

    /**
     * Append a widget to the list.
     * <p>
     * If a sort function is set, the widget will actually be inserted at the calculated position.
     *
     * @param child The GtkWidget to append.
     */
    public void append(GtkWidget child) {
        if (child != null) {
            library.gtk_list_box_append(getCReference(), child.getCReference());
        }
    }

    /**
     * Binds model to box.
     * <p>
     * If box was already bound to a model, that previous binding is destroyed.
     * <p>
     * The contents of box are cleared and then filled with widgets that represent items from model.
     * box is updated whenever model changes. If model is NULL, box is left empty.
     * <p>
     * It is undefined to add or remove widgets directly (for example, with gtk_list_box_insert()) while box is bound
     * to a model.
     * <p>
     * Note that using a model is incompatible with the filtering and sorting functionality in GtkListBox. When using
     * a model, filtering and sorting should be implemented by the model.
     *
     * @param m        The GListModel to be bound to box.
     *                 <p>
     *                 The argument can be NULL.
     * @param create   A function that creates widgets for items or NULL in case you also passed NULL as model.
     *                 <p>
     *                 The argument can be NULL.
     * @param userData User data passed to create_widget_func.
     *                 <p>
     *                 The argument can be NULL.
     * @param notify   Function for freeing user_data.
     */
    @SuppressWarnings("GrazieInspection")
    public void bindModel(GListModel m, GtkListBoxCreateWidgetFunc create, Pointer userData, GDestroyNotify notify) {
        preventGarbageCollection(create, userData, notify);
        library.gtk_list_box_bind_model(getCReference(), pointerOrNull(m), create, userData, notify);
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
     * Unselects a single row of box, if the selection mode allows it.
     *
     * @param r The row to deselect.
     */
    public void deselect(GtkListBoxRow r) {
        if (r != null) {
            library.gtk_list_box_unselect_row(getCReference(), r.getCReference());
        }
    }

    /**
     * Deselect all children of box, if the selection mode allows it.
     */
    public void deselectAll() {
        library.gtk_list_box_unselect_all(getCReference());
    }

    /**
     * Returns whether rows activate on single clicks.
     *
     * @return TRUE if rows are activated on single click, FALSE otherwise.
     */
    public boolean doesActivateOnSingleClick() {
        return library.gtk_list_box_get_activate_on_single_click(getCReference());
    }

    /**
     * Returns whether the list box should show separators between rows
     *
     * @return TRUE if the list box shows separators.
     */
    public boolean doesShowSeparators() {
        return library.gtk_list_box_get_show_separators(getCReference());
    }

    /**
     * Calls a function for each selected child.
     * <p>
     * Note that the selection cannot be modified from within this function.
     *
     * @param func The function to call for each selected child.
     * @param data User data to pass to the function.
     *             <p>
     *             The argument can be NULL.
     */
    public void forEachSelected(GtkListBoxForeachFunc func, Pointer data) {
        preventGarbageCollection(func, data);
        library.gtk_list_box_selected_foreach(getCReference(), func, data);
    }

    /**
     * Gets the adjustment (if any) that the widget uses to for vertical scrolling.
     *
     * @return The adjustment, if defined
     */
    public Option<GtkAdjustment> getAdjustment() {
        Option<Pointer> p = new Option<>(library.gtk_list_box_get_adjustment(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkAdjustment(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the adjustment (if any) that the widget uses to for vertical scrolling.
     * <p>
     * For instance, this is used to get the page size for PageUp/Down key handling.
     * <p>
     * In the normal case when the box is packed inside a GtkScrolledWindow the adjustment from that will be picked up
     * automatically, so there is no need to manually do that.
     *
     * @param a The adjustment.
     *          <p>
     *          The argument can be NULL.
     */
    public void setAdjustment(GtkAdjustment a) {
        library.gtk_list_box_set_adjustment(getCReference(), pointerOrNull(a));
    }

    /**
     * Gets the nth child in the list (not counting headers).
     *
     * @param index The index of the row.
     * @return The child GtkWidget
     */
    public Option<GtkListBoxRow> getRowAtIndex(int index) {
        if (index >= 0) {
            Option<Pointer> p = new Option<>(library.gtk_list_box_get_row_at_index(getCReference(), index));
            if (p.isDefined()) {
                return new Option<>(new GtkListBoxRow(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the row at the y position (in pixels)
     *
     * @param yPosition Y position to query
     * @return The row at Y, if defined
     */
    public Option<GtkListBoxRow> getRowAtY(int yPosition) {
        Option<Pointer> p = new Option<>(library.gtk_list_box_get_row_at_y(getCReference(), yPosition));
        if (p.isDefined()) {
            return new Option<>(new GtkListBoxRow(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Gets the selected row, or NULL if no rows are selected.
     * <p>
     * Note that the box may allow multiple selection, in which case you should use gtk_list_box_selected_foreach() to
     * find all selected rows.
     *
     * @return The selected row, if defined
     */
    public Option<GtkListBoxRow> getSelectedRow() {
        Option<Pointer> p = new Option<>(library.gtk_list_box_get_selected_row(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkListBoxRow(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Creates a list of all selected children.
     *
     * @return A GList containing the GtkWidget for each selected child. Free with g_list_free() when done.
     */
    public GList<GtkListBoxRow> getSelectedRows() {
        return new GList<>(GtkListBoxRow.class, library.gtk_list_box_get_selected_rows(getCReference()));
    }

    /**
     * Gets the selection mode of the list box.
     *
     * @return A GtkSelectionMode
     */
    public GtkSelectionMode getSelectionMode() {
        return GtkSelectionMode.getModeFromCValue(library.gtk_list_box_get_selection_mode(getCReference()));
    }

    /**
     * Sets how selection works in the list box.
     *
     * @param m The GtkSelectionMode
     */
    public void setSelectionMode(GtkSelectionMode m) {
        if (m != null) {
            library.gtk_list_box_set_selection_mode(getCReference(), GtkSelectionMode.getCValueFromMode(m));
        }
    }

    /**
     * Insert the child into the box at position.
     * <p>
     * If a sort function is set, the widget will actually be inserted at the calculated position.
     * <p>
     * If position is -1, or larger than the total number of items in the box, then the child will be appended to the
     * end.
     *
     * @param child    The GtkWidget to add.
     * @param position The position to insert child in.
     */
    public void insert(GtkWidget child, int position) {
        if (child != null) {
            library.gtk_list_box_insert(getCReference(), child.getCReference(), Math.max(-1, position));
        }
    }

    /**
     * Update the filtering for all rows.
     * <p>
     * Call this when result of the filter function on the box is changed due to an external factor.
     * For instance, this would be used if the filter function just looked for a specific search string and the entry
     * with the search string has changed.
     */
    public void invalidateFilter() {
        library.gtk_list_box_invalidate_filter(getCReference());
    }

    /**
     * Update the separators for all rows.
     * <p>
     * Call this when result of the header function on the box is changed due to an external factor.
     */
    public void invalidateHeaders() {
        library.gtk_list_box_invalidate_headers(getCReference());
    }

    /**
     * Update the sorting for all rows.
     * <p>
     * Call this when result of the sort function on the box is changed due to an external factor.
     */
    public void invalidateSort() {
        library.gtk_list_box_invalidate_sort(getCReference());
    }

    /**
     * Prepend a widget to the list.
     * <p>
     * If a sort function is set, the widget will actually be inserted at the calculated position.
     *
     * @param w The GtkWidget to prepend.
     */
    public void prepend(GtkWidget w) {
        if (w != null) {
            library.gtk_list_box_prepend(getCReference(), w.getCReference());
        }
    }

    /**
     * Removes a child from box.
     *
     * @param w The child to remove.
     */
    public void remove(GtkListBoxRow w) {
        if (w != null) {
            library.gtk_list_box_remove(getCReference(), w.getCReference());
        }
    }

    /**
     * If any row has previously been highlighted via gtk_list_box_drag_highlight_row(), it will have the highlight
     * removed.
     */
    public void removeHighlight() {
        library.gtk_list_box_drag_unhighlight_row(getCReference());
    }

    /**
     * Make row the currently selected row.
     *
     * @param r The row to select.
     *          <p>
     *          The argument can be NULL.
     */
    public void select(GtkListBoxRow r) {
        library.gtk_list_box_select_row(getCReference(), pointerOrNull(r));
    }

    /**
     * Select all children of box, if the selection mode allows it.
     */
    public void selectAll() {
        library.gtk_list_box_select_all(getCReference());
    }

    /**
     * By setting a filter function on the box one can decide dynamically which of the rows to show.
     * <p>
     * For instance, to implement a search function on a list that filters the original list to only show the matching
     * rows.
     * <p>
     * The filter_func will be called for each row after the call, and it will continue to be called each time a row
     * changes (via gtk_list_box_row_changed()) or when gtk_list_box_invalidate_filter() is called.
     * <p>
     * Note that using a filter function is incompatible with using a model (see gtk_list_box_bind_model()).
     *
     * @param filterFunc    Callback that lets you filter which rows to show.
     *                      <p>
     *                      The argument can be NULL.
     * @param userData      User data passed to filter_func.
     *                      <p>
     *                      The argument can be NULL.
     * @param destroyNotify Destroy notifier for user_data.
     */
    public void setFilter(GtkListBoxFilterFunc filterFunc, Pointer userData, GDestroyNotify destroyNotify) {
        preventGarbageCollection(filterFunc, userData, destroyNotify);
        library.gtk_list_box_set_filter_func(getCReference(), filterFunc, userData, destroyNotify);
    }

    /**
     * Sets the placeholder widget that is shown in the list when it doesn't display any visible children.
     *
     * @param placeholder A GtkWidget
     *                    <p>
     *                    The argument can be NULL.
     */
    public void setPlaceholder(GtkWidget placeholder) {
        library.gtk_list_box_set_placeholder(getCReference(), pointerOrNull(placeholder));
    }

    /**
     * Sets a sort function.
     * <p>
     * By setting a sort function on the box one can dynamically reorder the rows of the list, based on the contents
     * of the rows.
     * <p>
     * The sort_func will be called for each row after the call, and will continue to be called each time a row changes
     * (via gtk_list_box_row_changed()) and when gtk_list_box_invalidate_sort() is called.
     * <p>
     * Note that using a sort function is incompatible with using a model (see gtk_list_box_bind_model()).
     *
     * @param sortFunc The sort function.
     *                 <p>
     *                 The argument can be NULL.
     * @param userData User data passed to sort_func.
     *                 <p>
     *                 The argument can be NULL.
     * @param notify   Destroy notifier for user_data.
     */
    public void setSort(GtkListBoxSortFunc sortFunc, Pointer userData, GDestroyNotify notify) {
        preventGarbageCollection(sortFunc, userData, notify);
        library.gtk_list_box_set_sort_func(getCReference(), sortFunc, userData, notify);
    }

    /**
     * Sets a header function.
     * <p>
     * By setting a header function on the box one can dynamically add headers in front of rows, depending on the
     * contents of the row and its position in the list.
     * <p>
     * For instance, one could use it to add headers in front of the first item of a new kind, in a list sorted by the
     * kind.
     * <p>
     * The update_header can look at the current header widget using gtk_list_box_row_get_header() and either update
     * the state of the widget as needed, or set a new one using gtk_list_box_row_set_header(). If no header is needed,
     * set the header to NULL.
     * <p>
     * Note that you may get many calls update_header to this for a particular row when e.g. changing things that don't
     * affect the header. In this case it is important for performance to not blindly replace an existing header
     * with an identical one.
     * <p>
     * The update_header function will be called for each row after the call, and it will continue to be called each
     * time a row changes (via gtk_list_box_row_changed()) and when the row before changes
     * (either by gtk_list_box_row_changed() on the previous row, or when the previous row becomes a different row).
     * It is also called for all rows when gtk_list_box_invalidate_headers() is called.
     *
     * @param updateHeader  Callback that lets you add row headers.
     *                      <p>
     *                      The argument can be NULL.
     * @param userData      User data passed to update_header.
     *                      <p>
     *                      The argument can be NULL.
     * @param destroyNotify Destroy notifier for user_data.
     */
    public void setUpdateHeader(GtkListBoxUpdateHeaderFunc updateHeader, Pointer userData, GDestroyNotify destroyNotify) {
        preventGarbageCollection(updateHeader, userData, destroyNotify);
        library.gtk_list_box_set_header_func(getCReference(), updateHeader, userData, destroyNotify);
    }

    /**
     * Sets whether items should be activated on single click and selected on hover.
     *
     * @param doesActivate whether children can be activated with a single click, or require a double click.
     */
    public void shouldActivateOnSingleClick(boolean doesActivate) {
        library.gtk_list_box_set_activate_on_single_click(getCReference(), doesActivate);
    }

    /**
     * Sets whether the list box should show separators between rows.
     *
     * @param doesShow TRUE to show separators.
     */
    public void shouldShowSeparators(boolean doesShow) {
        library.gtk_list_box_set_show_separators(getCReference(), doesShow);
    }

    public static final class Signals extends GtkWidget.Signals {

        /**
         * Emitted when a row is selected and activated by cursor
         */
        public static final Signals ACTIVATE_CURSOR_ROW = new Signals("activate-cursor-row");
        /**
         * Emitted to deselect all children of the box, if the selection mode permits it.
         * <p>
         * This is a keybinding signal.
         * <p>
         * The default binding for this signal is Ctrl-Shift-a.
         */
        public static final Signals DESELECT_ALL = new Signals("unselect-all");
        /**
         * Emitted when the cursor is moved
         */
        public static final Signals MOVE_CURSOR = new Signals("move-cursor");
        /**
         * Emitted when a row has been activated by the user.
         */
        public static final Signals ROW_ACTIVATED = new Signals("row-activated");
        /**
         * Emitted when a new row is selected, or (with a NULL row) when the selection is cleared.
         * <p>
         * When the box is using GTK_SELECTION_MULTIPLE, this signal will not give you the full picture of selection
         * changes, and you should use the GtkListBox::selected-rows-changed signal instead.
         */
        public static final Signals ROW_SELECTED = new Signals("row-selected");
        /**
         * Emitted when the set of selected rows changes.
         */
        public static final Signals SELECTED_ROWS_CHANGED = new Signals("selected-rows-changed");
        /**
         * Emitted to select all children of the box, if the selection mode permits it.
         * <p>
         * This is a keybinding signal.
         * <p>
         * The default binding for this signal is Ctrl-a.
         */
        public static final Signals SELECT_ALL = new Signals("select-all");
        /**
         * Emitted when a row is toggled by pressing space key
         */
        public static final Signals TOGGLE_CURSOR_ROW = new Signals("toggle-cursor-row");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkListBoxLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Append a widget to the list.
         * <p>
         * If a sort function is set, the widget will actually be inserted at the calculated position.
         *
         * @param box   self
         * @param child The GtkWidget to add. Type: GtkWidget
         */
        public native void gtk_list_box_append(Pointer box, Pointer child);

        /**
         * Binds model to box.
         * <p>
         * If box was already bound to a model, that previous binding is destroyed.
         * <p>
         * The contents of box are cleared and then filled with widgets that represent items from model. box is updated
         * whenever model changes. If model is NULL, box is left empty.
         * <p>
         * It is undefined to add or remove widgets directly (for example, with gtk_list_box_insert()) while box is
         * bound to a model.
         * <p>
         * Note that using a model is incompatible with the filtering and sorting functionality in GtkListBox. When
         * using a model, filtering and sorting should be implemented by the model.
         *
         * @param box                 self
         * @param model               The GListModel to be bound to box. Type: GObject
         *                            <p>
         *                            The argument can be NULL.
         * @param create_widget_func  A function that creates widgets for items or NULL in case you also passed NULL as
         *                            model.
         *                            <p>
         *                            The argument can be NULL.
         * @param user_data           User data passed to create_widget_func.
         *                            <p>
         *                            The argument can be NULL.
         * @param user_data_free_func Function for freeing user_data.
         */
        @SuppressWarnings("GrazieInspection")
        public native void gtk_list_box_bind_model(Pointer box, Pointer model, GtkListBoxCreateWidgetFunc create_widget_func, Pointer user_data, GDestroyNotify user_data_free_func);

        /**
         * Add a drag highlight to a row.
         * <p>
         * This is a helper function for implementing DnD onto a GtkListBox. The passed in row will be highlighted by
         * setting the GTK_STATE_FLAG_DROP_ACTIVE state and any previously highlighted row will have its highlight
         * removed.
         * <p>
         * The row will have its highlight removed when the widget gets a drag leave event.
         *
         * @param box self
         * @param row A GtkListBoxRow. Type: GtkListBoxRow
         */
        public native void gtk_list_box_drag_highlight_row(Pointer box, Pointer row);

        /**
         * If a row has previously been highlighted via gtk_list_box_drag_highlight_row(), it will have the highlight
         * removed.
         *
         * @param box self
         */
        public native void gtk_list_box_drag_unhighlight_row(Pointer box);

        /**
         * Returns whether rows activate on single clicks.
         *
         * @param box self
         * @return TRUE if rows are activated on single click, FALSE otherwise.
         */
        public native boolean gtk_list_box_get_activate_on_single_click(Pointer box);

        /**
         * Gets the adjustment (if any) that the widget uses to for vertical scrolling.
         *
         * @param box self
         * @return The adjustment. Type: GtkAdjustment
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_list_box_get_adjustment(Pointer box);

        /**
         * Gets the n-th child in the list (not counting headers).
         * <p>
         * If index_ is negative or larger than the number of items in the list, NULL is returned.
         *
         * @param box    self
         * @param index_ The index of the row.
         * @return The child GtkWidget. Type: GtkListBoxRow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_list_box_get_row_at_index(Pointer box, int index_);

        /**
         * Gets the row at the y position.
         *
         * @param box self
         * @param y   Position.
         * @return The row. Type: GtkListBoxRow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_list_box_get_row_at_y(Pointer box, int y);

        /**
         * Gets the selected row, or NULL if no rows are selected.
         * <p>
         * Note that the box may allow multiple selection, in which case you should use gtk_list_box_selected_foreach()
         * to find all selected rows.
         *
         * @param box self
         * @return The selected row. Type: GtkListBoxRow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_list_box_get_selected_row(Pointer box);

        /**
         * Creates a list of all selected children.
         *
         * @param box box
         * @return A GList containing the GtkWidget for each selected child. Free with g_list_free() when done.
         *         Type: GList of GtkListBoxRow
         */
        public native Pointer gtk_list_box_get_selected_rows(Pointer box);

        /**
         * Gets the selection mode of the list box.
         *
         * @param box self
         * @return A GtkSelectionMode. Type: GtkSelectionMode
         */
        public native int gtk_list_box_get_selection_mode(Pointer box);

        /**
         * Returns whether the list box should show separators between rows.
         *
         * @param box self
         * @return TRUE if the list box shows separators.
         */
        public native boolean gtk_list_box_get_show_separators(Pointer box);

        /**
         * Insert the child into the box at position.
         * <p>
         * If a sort function is set, the widget will actually be inserted at the calculated position.
         * <p>
         * If position is -1, or larger than the total number of items in the box, then the child will be appended to
         * the end.
         *
         * @param box      self
         * @param child    The GtkWidget to add. Type: GtkWidget
         * @param position The position to insert child in.
         */
        public native void gtk_list_box_insert(Pointer box, Pointer child, int position);

        /**
         * Update the filtering for all rows.
         * <p>
         * Call this when result of the filter function on the box is changed due to an external factor. For instance,
         * this would be used if the filter function just looked for a specific search string and the entry with the
         * search string has changed.
         *
         * @param box self
         */
        public native void gtk_list_box_invalidate_filter(Pointer box);

        /**
         * Update the separators for all rows.
         * <p>
         * Call this when result of the header function on the box is changed due to an external factor.
         *
         * @param box self
         */
        public native void gtk_list_box_invalidate_headers(Pointer box);

        /**
         * Update the sorting for all rows.
         * <p>
         * Call this when result of the sort function on the box is changed due to an external factor.
         *
         * @param box self
         */
        public native void gtk_list_box_invalidate_sort(Pointer box);

        /**
         * Creates a new GtkListBox container.
         *
         * @return A new GtkListBox. Type: GtkListBox
         */
        public native Pointer gtk_list_box_new();

        /**
         * Prepend a widget to the list.
         * <p>
         * If a sort function is set, the widget will actually be inserted at the calculated position.
         *
         * @param box   self
         * @param child The GtkWidget to add. Type: GtkWidget
         */
        public native void gtk_list_box_prepend(Pointer box, Pointer child);

        /**
         * Removes a child from box.
         *
         * @param box   self
         * @param child The child to remove. Type: GtkWidget
         */
        public native void gtk_list_box_remove(Pointer box, Pointer child);

        /**
         * Select all children of box, if the selection mode allows it.
         *
         * @param box self
         */
        public native void gtk_list_box_select_all(Pointer box);

        /**
         * Make row the currently selected row.
         *
         * @param box self
         * @param row The row to select. Type: GtkListBoxRow
         *            <p>
         *            The argument can be NULL.
         */
        public native void gtk_list_box_select_row(Pointer box, Pointer row);

        /**
         * Calls a function for each selected child.
         * <p>
         * Note that the selection cannot be modified from within this function.
         *
         * @param box  self
         * @param func The function to call for each selected child.
         * @param data User data to pass to the function.
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_list_box_selected_foreach(Pointer box, GtkListBoxForeachFunc func, Pointer data);

        /**
         * If single is TRUE, rows will be activated when you click on them, otherwise you need to double-click.
         *
         * @param box    self
         * @param single whether children can be activated with a single click, or require a double click.
         */
        public native void gtk_list_box_set_activate_on_single_click(Pointer box, boolean single);

        /**
         * Sets the adjustment (if any) that the widget uses to for vertical scrolling.
         * <p>
         * For instance, this is used to get the page size for PageUp/Down key handling.
         * <p>
         * In the normal case when the box is packed inside a GtkScrolledWindow the adjustment from that will be picked
         * up automatically, so there is no need to manually do that.
         *
         * @param box        self
         * @param adjustment The adjustment.
         *                   <p>
         *                   The argument can be NULL.
         */
        public native void gtk_list_box_set_adjustment(Pointer box, Pointer adjustment);

        /**
         * By setting a filter function on the box one can decide dynamically which of the rows to show.
         * <p>
         * For instance, to implement a search function on a list that filters the original list to only show the
         * matching rows.
         * <p>
         * The filter_func will be called for each row after the call, and it will continue to be called each time a
         * row changes (via gtk_list_box_row_changed()) or when gtk_list_box_invalidate_filter() is called.
         * <p>
         * Note that using a filter function is incompatible with using a model (see gtk_list_box_bind_model()).
         *
         * @param box         self
         * @param filter_func Callback that lets you filter which rows to show.
         *                    <p>
         *                    The argument can be NULL.
         * @param user_data   User data passed to filter_func.
         *                    <p>
         *                    The argument can be NULL.
         * @param destroy     Destroy notifier for user_data.
         */
        public native void gtk_list_box_set_filter_func(Pointer box, GtkListBoxFilterFunc filter_func, Pointer user_data, GDestroyNotify destroy);

        /**
         * Sets a header function.
         * <p>
         * By setting a header function on the box one can dynamically add headers in front of rows, depending on the
         * contents of the row and its position in the list.
         * <p>
         * For instance, one could use it to add headers in front of the first item of a new kind, in a list sorted by
         * the kind.
         * <p>
         * The update_header can look at the current header widget using gtk_list_box_row_get_header() and either update
         * the state of the widget as needed, or set a new one using gtk_list_box_row_set_header(). If no header is
         * needed, set the header to NULL.
         * <p>
         * Note that you may get many calls update_header to this for a particular row when e.g. changing things
         * that don't affect the header. In this case it is important for performance to not blindly replace an
         * existing header with an identical one.
         * <p>
         * The update_header function will be called for each row after the call, and it will continue to be called
         * each time a row changes (via gtk_list_box_row_changed()) and when the row before changes (either by
         * gtk_list_box_row_changed() on the previous row, or when the previous row becomes a different row). It is
         * also called for all rows when gtk_list_box_invalidate_headers() is called.
         *
         * @param box           self
         * @param update_header Callback that lets you add row headers.
         *                      <p>
         *                      The argument can be NULL.
         * @param user_data     User data passed to update_header.
         *                      <p>
         *                      The argument can be NULL.
         * @param destroyNotify Destroy notifier for user_data.
         */
        public native void gtk_list_box_set_header_func(Pointer box, GtkListBoxUpdateHeaderFunc update_header, Pointer user_data, GDestroyNotify destroyNotify);

        /**
         * Sets the placeholder widget that is shown in the list when it doesn't display any visible children.
         *
         * @param box         self
         * @param placeholder A GtkWidget. Type: GtkWidget
         *                    <p>
         *                    The argument can be NULL.
         */
        public native void gtk_list_box_set_placeholder(Pointer box, Pointer placeholder);

        /**
         * Sets how selection works in the list box.
         *
         * @param box  box
         * @param mode The GtkSelectionMode. Type: GtkSelectionMode
         */
        public native void gtk_list_box_set_selection_mode(Pointer box, int mode);

        /**
         * Sets whether the list box should show separators between rows.
         *
         * @param box             self
         * @param show_separators TRUE to show separators.
         */
        public native void gtk_list_box_set_show_separators(Pointer box, boolean show_separators);

        /**
         * Sets a sort function.
         * <p>
         * By setting a sort function on the box one can dynamically reorder the rows of the list, based on the contents
         * of the rows.
         * <p>
         * The sort_func will be called for each row after the call, and will continue to be called each time a row
         * changes (via gtk_list_box_row_changed()) and when gtk_list_box_invalidate_sort() is called.
         * <p>
         * Note that using a sort function is incompatible with using a model (see gtk_list_box_bind_model()).
         *
         * @param box       self
         * @param sort_func The sort function.
         *                  <p>
         *                  The argument can be NULL.
         * @param user_data User data passed to sort_func.
         *                  <p>
         *                  The argument can be NULL.
         * @param destroy   Destroy notifier for user_data.
         */
        public native void gtk_list_box_set_sort_func(Pointer box, GtkListBoxSortFunc sort_func, Pointer user_data, GDestroyNotify destroy);

        /**
         * Unselect all children of box, if the selection mode allows it.
         *
         * @param box self
         */
        public native void gtk_list_box_unselect_all(Pointer box);

        /**
         * Unselects a single row of box, if the selection mode allows it.
         *
         * @param box self
         * @param row The row to unselect. Type: GtkListBoxRow
         */
        public native void gtk_list_box_unselect_row(Pointer box, Pointer row);

    }
}
