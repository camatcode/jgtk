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
import com.gitlab.ccook.jgtk.interfaces.GtkOrientable;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.jetbrains.annotations.Nullable;

/**
 * A GtkFlowBox puts child widgets in re-flowing grid.
 * <p>
 * For instance, with the horizontal orientation, the widgets will be arranged from left to right, starting a new row
 * under the previous row when necessary. Reducing the width in this case will require more rows, so a larger height
 * will be requested.
 * <p>
 * Likewise, with the vertical orientation, the widgets will be arranged from top to bottom, starting a new column to
 * the right when necessary. Reducing the height will require more columns, so a larger width will be requested.
 * <p>
 * The size request of a GtkFlowBox alone may not be what you expect; if you need to be able to shrink it along both
 * axes and dynamically reflow its children, you may have to wrap it in a GtkScrolledWindow to enable that.
 * <p>
 * The children of a GtkFlowBox can be dynamically sorted and filtered.
 * <p>
 * Although a GtkFlowBox must have only GtkFlowBoxChild children, you can add any kind of widget to it via
 * gtk_flow_box_insert(), and a GtkFlowBoxChild widget will automatically be inserted between the box and the widget.
 * <p>
 * Also see GtkListBox.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class GtkFlowBox extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkOrientable {
    private static final GtkFlowBoxLibrary library = new GtkFlowBoxLibrary();

    public GtkFlowBox(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a GtkFlowBox.
     */
    public GtkFlowBox() {
        super(library.gtk_flow_box_new());
    }

    /**
     * Adds child to the end of self.
     * <p>
     * If a sort function is set, the widget will actually be inserted at the calculated position.
     * <p>
     * See also: gtk_flow_box_insert().
     *
     * @param child The GtkWidget to add.
     */
    public void append(GtkWidget child) {
        if (child != null) {
            library.gtk_flow_box_append(getCReference(), child.getCReference());
        }
    }

    /**
     * Binds model to box.
     * <p>
     * If box was already bound to a model, that previous binding is destroyed.
     * <p>
     * The contents of box are cleared and then filled with widgets that represent items from model. box is updated
     * whenever model changes. If model is NULL, box is left empty.
     * <p>
     * It is undefined to add or remove widgets directly (for example, with gtk_flow_box_insert()) while box is
     * bound to a model.
     * <p>
     * Note that using a model is incompatible with the filtering and sorting functionality in GtkFlowBox.
     * When using a model, filtering and sorting should be implemented by the model.
     *
     * @param m                The GListModel to be bound to box.
     *                         <p>
     *                         The argument can be NULL.
     * @param func             A function that creates widgets for items.
     * @param userData         User data passed to create_widget_func.
     *                         <p>
     *                         The argument can be NULL.
     * @param userDataFreeFunc Function for freeing user_data.
     */
    @SuppressWarnings("GrazieInspection")
    public void bindModel(GListModel m, GtkFlowBoxCreateWidgetFunc func, Pointer userData, GDestroyNotify userDataFreeFunc) {
        Pointer mPointer = m != null ? m.getCReference() : Pointer.NULL;
        library.gtk_flow_box_bind_model(getCReference(), mPointer, func, userData, userDataFreeFunc);
    }

    /**
     * Connect a signal
     *
     * @param s       Detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to the signal
     */
    public void connect(Signals s, GCallbackFunction fn, @Nullable Pointer dataRef) {
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
     * Deselect all children of box, if the selection mode allows it.
     */
    public void deselectAll() {
        library.gtk_flow_box_unselect_all(getCReference());
    }

    /**
     * Deselects a single child of box, if the selection mode allows it.
     *
     * @param child A child of box.
     */
    public void deselectChild(GtkFlowBoxChild child) {
        if (child != null) {
            library.gtk_flow_box_unselect_child(getCReference(), child.getCReference());
        }
    }

    /**
     * Returns whether children activate on single clicks.
     *
     * @return TRUE if children are activated on single click, FALSE otherwise.
     */
    public boolean doChildrenActivateOnSingleClick() {
        return library.gtk_flow_box_get_activate_on_single_click(getCReference());
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
    public void forEachSelected(GtkFlowBoxForeachFunc func, Pointer data) {
        library.gtk_flow_box_selected_foreach(getCReference(), func, data);
    }

    /**
     * Gets the nth child in the box.
     *
     * @param index The position of the child.
     * @return The child widget, which will always be a GtkFlowBoxChild or NONE in case no child widget with the given
     *         index exists.
     */
    public Option<GtkFlowBoxChild> getChildAtIndex(int index) {
        if (index >= 0) {
            Option<Pointer> p = new Option<>(library.gtk_flow_box_get_child_at_index(getCReference(), index));
            if (p.isDefined()) {
                return new Option<>(new GtkFlowBoxChild(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the child in the (x, y) position.
     * <p>
     * Both x and y are assumed to be relative to the origin of box.
     *
     * @param x The horizontal coordinate of the child.
     * @param y The vertical coordinate of the child.
     * @return The child widget, which will always be a GtkFlowBoxChild or NONE in case no child widget exists for the
     *         given x and y coordinates.
     */
    public Option<GtkFlowBoxChild> getChildAtPosition(int x, int y) {
        Option<Pointer> p = new Option<>(library.gtk_flow_box_get_child_at_pos(getCReference(), x, y));
        if (p.isDefined()) {
            return new Option<>(new GtkFlowBoxChild(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Gets the horizontal spacing.
     *
     * @return The horizontal spacing.
     */
    public int getHorizontalSpacing() {
        return library.gtk_flow_box_get_column_spacing(getCReference());
    }

    /**
     * Sets the horizontal space to add between children.
     *
     * @param spacing The horizontal spacing to use.
     */
    public void setHorizontalSpacing(int spacing) {
        library.gtk_flow_box_set_column_spacing(getCReference(), Math.max(spacing, 0));
    }

    /**
     * Gets the maximum number of children per line.
     *
     * @return The maximum number of children per line.
     */
    public int getMaxChildrenPerLine() {
        return library.gtk_flow_box_get_max_children_per_line(getCReference());
    }

    /**
     * Sets the maximum number of children to request and allocate space for in box's orientation.
     * <p>
     * Setting the maximum number of children per line limits the overall natural size request to be no more than
     * n_children children long in the given orientation.
     *
     * @param maxChildrenPerLine The maximum number of children per line.
     */
    public void setMaxChildrenPerLine(int maxChildrenPerLine) {
        if (maxChildrenPerLine >= 0) {
            library.gtk_flow_box_set_max_children_per_line(getCReference(), maxChildrenPerLine);
        }
    }

    /**
     * Gets the minimum number of children per line.
     *
     * @return The minimum number of children per line.
     */
    public int getMinChildrenPerLine() {
        return library.gtk_flow_box_get_min_children_per_line(getCReference());
    }

    /**
     * Sets the minimum number of children to line up in box's orientation before flowing.
     *
     * @param minChildrenPerLine The minimum number of children per line.
     */
    public void setMinChildrenPerLine(int minChildrenPerLine) {
        library.gtk_flow_box_set_min_children_per_line(getCReference(), Math.max(0, minChildrenPerLine));
    }

    /**
     * Creates a list of all selected children.
     *
     * @return A GList containing the GtkWidget for each selected child. Free with g_list_free() when done.
     */
    public GList<GtkWidget> getSelectedChildren() {
        return new GList<>(GtkWidget.class, library.gtk_flow_box_get_selected_children(getCReference()));
    }

    /**
     * Gets the selection mode of box.
     *
     * @return The GtkSelectionMode
     */
    public GtkSelectionMode getSelectionMode() {
        return GtkSelectionMode.getModeFromCValue(library.gtk_flow_box_get_selection_mode(getCReference()));
    }

    /**
     * Sets how selection works in box.
     *
     * @param mode The new selection mode.
     */
    public void setSelectionMode(GtkSelectionMode mode) {
        if (mode != null) {
            library.gtk_flow_box_set_selection_mode(getCReference(), GtkSelectionMode.getCValueFromMode(mode));
        }
    }

    /**
     * Gets the vertical spacing.
     *
     * @return The vertical spacing.
     */
    public int getVerticalSpacing() {
        return library.gtk_flow_box_get_row_spacing(getCReference());
    }

    /**
     * Sets the vertical space to add between children.
     *
     * @param rowSpacing The vertical spacing to use.
     */
    public void setVerticalSpacing(int rowSpacing) {
        library.gtk_flow_box_set_row_spacing(getCReference(), Math.max(0, rowSpacing));
    }

    /**
     * Inserts the widget into box at position.
     * <p>
     * If a sort function is set, the widget will actually be inserted at the calculated position.
     * <p>
     * If position is -1, or larger than the total number of children in the box, then the widget will be appended
     * to the end.
     *
     * @param child    The GtkWidget to add.
     * @param position The position to insert child in.
     */
    public void insert(GtkWidget child, int position) {
        if (child != null) {
            library.gtk_flow_box_insert(getCReference(), child.getCReference(), Math.max(-1, position));
        }
    }

    /**
     * Updates the filtering for all children.
     * <p>
     * Call this function when the result of the filter function on the box is changed due to an external factor.
     * For instance, this would be used if the filter function just looked for a specific search term, and the entry
     * with the string has changed.
     */
    public void invalidateFilter() {
        library.gtk_flow_box_invalidate_filter(getCReference());
    }

    /**
     * Updates the sorting for all children.
     * <p>
     * Call this when the result of the sort function on box is changed due to an external factor.
     */
    public void invalidateSort() {
        library.gtk_flow_box_invalidate_sort(getCReference());
    }

    /**
     * Returns whether the box is homogeneous.
     *
     * @return TRUE if the box is homogeneous.
     */
    public boolean isHomogeneous() {
        return library.gtk_flow_box_get_homogeneous(getCReference());
    }

    /**
     * Sets whether all children of box are given equal space in the box.
     *
     * @param isHomogeneous TRUE to create equal allotments, FALSE for variable allotments.
     */
    public void setHomogeneous(boolean isHomogeneous) {
        library.gtk_flow_box_set_homogeneous(getCReference(), isHomogeneous);
    }

    /**
     * Adds child to the start of self.
     * <p>
     * If a sort function is set, the widget will actually be inserted at the calculated position.
     * <p>
     * See also: gtk_flow_box_insert().
     *
     * @param child The GtkWidget to add.
     */
    public void prepend(GtkWidget child) {
        if (child != null) {
            library.gtk_flow_box_prepend(getCReference(), child.getCReference());
        }
    }

    /**
     * Removes a child from box.
     *
     * @param child The child widget to remove.
     */
    public void remove(GtkWidget child) {
        if (child != null) {
            library.gtk_flow_box_remove(getCReference(), child.getCReference());
        }
    }

    /**
     * Select all children of box, if the selection mode allows it.
     */
    public void selectAll() {
        library.gtk_flow_box_select_all(getCReference());
    }

    public void selectChild(GtkFlowBoxChild child) {
        if (child != null) {
            library.gtk_flow_box_select_child(getCReference(), child.getCReference());
        }
        //gtk_flow_box_select_child
    }

    /**
     * By setting a filter function on the box one can decide dynamically which of the children to show.
     * <p>
     * For instance, to implement a search function that only shows the children matching the search terms.
     * <p>
     * The filter_func will be called for each child after the call, and it will continue to be called each time a child
     * changes (via gtk_flow_box_child_changed()) or when gtk_flow_box_invalidate_filter() is called.
     * <p>
     * Note that using a filter function is incompatible with using a model (see gtk_flow_box_bind_model()).
     *
     * @param filterFunc Callback that lets you filter which children to show.
     * @param userData   User data passed to filter_func.
     *                   <p>
     *                   The argument can be NULL.
     * @param destroy    Destroy notifier for user_data.
     */
    public void setFilterFunc(GtkFlowBoxFilterFunc filterFunc, Pointer userData, GDestroyNotify destroy) {
        library.gtk_flow_box_set_filter_func(getCReference(), filterFunc, userData, destroy);
    }

    /**
     * Hooks up an adjustment to focus handling in box.
     * <p>
     * The adjustment is also used for auto-scrolling during rubber-band selection.
     * See gtk_scrolled_window_get_hadjustment() for a typical way of obtaining the adjustment, and
     * gtk_flow_box_set_vadjustment() for setting the vertical adjustment.
     * <p>
     * The adjustments have to be in pixel units and in the same coordinate system as the allocation for immediate
     * children of the box.
     *
     * @param adjust An adjustment which should be adjusted when the focus is moved among the descendants of container.
     */
    public void setHorizontalAdjustment(GtkAdjustment adjust) {
        if (adjust != null) {
            library.gtk_flow_box_set_hadjustment(getCReference(), adjust.getCReference());
        }
    }

    /**
     * By setting a sort function on the box, one can dynamically reorder the children of the box, based on the
     * contents of the children.
     * <p>
     * The sort_func will be called for each child after the call, and will continue to be called each time a child
     * changes (via gtk_flow_box_child_changed()) and when gtk_flow_box_invalidate_sort() is called.
     * <p>
     * Note that using a sort function is incompatible with using a model (see gtk_flow_box_bind_model()).
     *
     * @param func          The sort function.
     *                      <p>
     *                      The argument can be NULL.
     * @param userData      User data passed to sort_func.
     *                      <p>
     *                      The argument can be NULL.
     * @param destroyNotify Destroy notifier for user_data.
     */
    public void setSortFunction(GtkFlowBoxSortFunc func, Pointer userData, GDestroyNotify destroyNotify) {
        library.gtk_flow_box_set_sort_func(getCReference(), func, userData, destroyNotify);
    }

    /**
     * Hooks up an adjustment to focus handling in box.
     * <p>
     * The adjustment is also used for auto-scrolling during rubber-band selection.
     * See gtk_scrolled_window_get_vadjustment() for a typical way of obtaining the adjustment,
     * and gtk_flow_box_set_hadjustment() for setting the horizontal adjustment.
     * <p>
     * The adjustments have to be in pixel units and in the same coordinate system as the allocation for immediate
     * children of the box.
     *
     * @param adjust An adjustment which should be adjusted when the focus is moved among the descendants of container.
     */
    public void setVerticalAdjustment(GtkAdjustment adjust) {
        if (adjust != null) {
            library.gtk_flow_box_set_vadjustment(getCReference(), adjust.getCReference());
        }
    }

    /**
     * If single is TRUE, children will be activated when you click on them, otherwise you need to double-click.
     *
     * @param doesActivate TRUE to emit child-activated on a single click.
     */
    public void shouldChildrenActivateOnSingleClick(boolean doesActivate) {
        library.gtk_flow_box_set_activate_on_single_click(getCReference(), doesActivate);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted when the user activates the box.
         */
        public static final Signals ACTIVATE_CURSOR_CHILD = new Signals("activate-cursor-child");

        /**
         * Emitted when a child has been activated by the user.
         */
        public static final Signals CHILD_ACTIVATED = new Signals("child-activated");
        /**
         * Emitted to deselect all children of the box, if the selection mode permits it.
         */
        public static final Signals DESELECTED_ALL = new Signals("unselect-all");
        /**
         * Emitted when the user initiates a cursor movement
         */
        public static final Signals MOVE_CURSOR = new Signals("move-cursor");
        /**
         * Emitted when the set of selected children changes.
         */
        public static final Signals SELECTED_CHILDREN_CHANGED = new Signals("selected-children-changed");
        /**
         * Emitted to select all children of the box, if the selection mode permits it.
         */
        public static final Signals SELECT_ALL = new Signals("select-all");
        /**
         * Emitted to toggle the selection of the child that has the focus.
         */
        public static final Signals TOGGLED_CURSOR_CHILD = new Signals("toggle-cursor-child");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkFlowBoxLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds child to the end of self.
         * <p>
         * If a sort function is set, the widget will actually be inserted at the calculated position.
         * <p>
         * See also: gtk_flow_box_insert().
         *
         * @param self  self
         * @param child The GtkWidget to add.
         */
        public native void gtk_flow_box_append(Pointer self, Pointer child);

        /**
         * Binds model to box.
         * <p>
         * If box was already bound to a model, that previous binding is destroyed.
         * <p>
         * The contents of box are cleared and then filled with widgets that represent items from model. box is updated
         * whenever model changes. If model is NULL, box is left empty.
         * <p>
         * It is undefined to add or remove widgets directly (for example, with gtk_flow_box_insert()) while box is
         * bound to a model.
         * <p>
         * Note that using a model is incompatible with the filtering and sorting functionality in GtkFlowBox. When
         * using a model, filtering and sorting should be implemented by the model.
         *
         * @param box                 self
         * @param model               The GListModel to be bound to box.
         *                            <p>
         *                            The argument can be NULL.
         * @param create_widget_func  A function that creates widgets for items.
         * @param user_data           User data passed to create_widget_func.
         *                            <p>
         *                            The argument can be NULL.
         * @param user_data_free_func Function for freeing user_data.
         */
        @SuppressWarnings("GrazieInspection")
        public native void gtk_flow_box_bind_model(Pointer box, Pointer model, GtkFlowBoxCreateWidgetFunc create_widget_func, Pointer user_data, GDestroyNotify user_data_free_func);

        /**
         * Returns whether children activate on single clicks.
         *
         * @param box self
         * @return TRUE if children are activated on single click, FALSE otherwise.
         */
        public native boolean gtk_flow_box_get_activate_on_single_click(Pointer box);

        /**
         * Gets the nth child in the box.
         *
         * @param box self
         * @param idx The position of the child.
         * @return The child widget, which will always be a GtkFlowBoxChild or NULL in case no child widget with the
         *         given index exists. Type: GtkFlowBoxChild
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_flow_box_get_child_at_index(Pointer box, int idx);

        /**
         * Gets the child in the (x, y) position.
         * <p>
         * Both x and y are assumed to be relative to the origin of box.
         *
         * @param box self
         * @param x   The x coordinate of the child.
         * @param y   The y coordinate of the child.
         * @return The child widget, which will always be a GtkFlowBoxChild or NULL in case no child widget exists for
         *         the given x and y coordinates. Type: GtkFlowBoxChild
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_flow_box_get_child_at_pos(Pointer box, int x, int y);

        /**
         * Gets the horizontal spacing.
         *
         * @param box self
         * @return The horizontal spacing.
         */
        public native int gtk_flow_box_get_column_spacing(Pointer box);

        /**
         * Returns whether the box is homogeneous.
         *
         * @param box self
         * @return TRUE if the box is homogeneous.
         */
        public native boolean gtk_flow_box_get_homogeneous(Pointer box);

        /**
         * Gets the maximum number of children per line.
         *
         * @param box self
         * @return The maximum number of children per line.
         */
        public native int gtk_flow_box_get_max_children_per_line(Pointer box);

        /**
         * Gets the minimum number of children per line.
         *
         * @param box self
         * @return The minimum number of children per line.
         */
        public native int gtk_flow_box_get_min_children_per_line(Pointer box);

        /**
         * Gets the vertical spacing.
         *
         * @param box self
         * @return The vertical spacing.
         */
        public native int gtk_flow_box_get_row_spacing(Pointer box);

        /**
         * Creates a list of all selected children.
         *
         * @param box self
         * @return Type: A list of GtkFlowBoxChild*
         *         <p>
         *         A GList containing the GtkWidget for each selected child. Free with g_list_free() when done.
         */
        public native Pointer gtk_flow_box_get_selected_children(Pointer box);

        /**
         * Gets the selection mode of box.
         *
         * @param box self
         * @return The GtkSelectionMode. Type: GtkSelectionMode
         */
        public native int gtk_flow_box_get_selection_mode(Pointer box);

        /**
         * Inserts the widget into box at position.
         * <p>
         * If a sort function is set, the widget will actually be inserted at the calculated position.
         * <p>
         * If position is -1, or larger than the total number of children in the box, then the widget will be appended
         * to the end.
         *
         * @param box      self
         * @param widget   The GtkWidget to add.
         * @param position The position to insert child in.
         */
        public native void gtk_flow_box_insert(Pointer box, Pointer widget, int position);

        /**
         * Updates the filtering for all children.
         * <p>
         * Call this function when the result of the filter function on the box is changed due to an external factor.
         * For instance, this would be used if the filter function just looked for a specific search term, and the
         * entry with the string has changed.
         *
         * @param box self
         */
        public native void gtk_flow_box_invalidate_filter(Pointer box);

        /**
         * Updates the sorting for all children.
         * <p>
         * Call this when the result of the sort function on box is changed due to an external factor.
         *
         * @param box self
         */
        public native void gtk_flow_box_invalidate_sort(Pointer box);

        /**
         * Creates a GtkFlowBox.
         *
         * @return A new GtkFlowBox. Type: GtkFlowBox
         */
        public native Pointer gtk_flow_box_new();

        /**
         * Adds child to the start of self.
         * <p>
         * If a sort function is set, the widget will actually be inserted at the calculated position.
         * <p>
         * See also: gtk_flow_box_insert().
         *
         * @param box   self
         * @param child The GtkWidget to add. Type: GtkWidget
         */
        public native void gtk_flow_box_prepend(Pointer box, Pointer child);

        /**
         * Removes a child from box.
         *
         * @param box    self
         * @param widget The child widget to remove. Type: GtkWidget
         */
        public native void gtk_flow_box_remove(Pointer box, Pointer widget);

        /**
         * Select all children of box, if the selection mode allows it.
         *
         * @param box self
         */
        public native void gtk_flow_box_select_all(Pointer box);

        /**
         * Selects a single child of box, if the selection mode allows it.
         *
         * @param box   self
         * @param child A child of box. Type: GtkFlowBoxChild
         */
        public native void gtk_flow_box_select_child(Pointer box, Pointer child);

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
        public native void gtk_flow_box_selected_foreach(Pointer box, GtkFlowBoxForeachFunc func, Pointer data);

        /**
         * If single is TRUE, children will be activated when you click on them, otherwise you need to double-click.
         *
         * @param box    self
         * @param single TRUE to emit child-activated on a single click.
         */
        public native void gtk_flow_box_set_activate_on_single_click(Pointer box, boolean single);

        /**
         * Sets the horizontal space to add between children.
         *
         * @param box     self
         * @param spacing The spacing to use.
         */
        public native void gtk_flow_box_set_column_spacing(Pointer box, int spacing);

        /**
         * By setting a filter function on the box one can decide dynamically which of the children to show.
         * <p>
         * For instance, to implement a search function that only shows the children matching the search terms.
         * <p>
         * The filter_func will be called for each child after the call, and it will continue to be called each time a
         * child changes (via gtk_flow_box_child_changed()) or when gtk_flow_box_invalidate_filter() is called.
         * <p>
         * Note that using a filter function is incompatible with using a model (see gtk_flow_box_bind_model()).
         *
         * @param box         self
         * @param filter_func Callback that lets you filter which children to show.
         *                    <p>
         *                    The argument can be NULL.
         * @param user_data   User data passed to filter_func.
         *                    <p>
         *                    The argument can be NULL.
         * @param destroy     Destroy notifier for user_data.
         */
        public native void gtk_flow_box_set_filter_func(Pointer box, GtkFlowBoxFilterFunc filter_func, Pointer user_data, GDestroyNotify destroy);

        /**
         * Hooks up an adjustment to focus handling in box.
         * <p>
         * The adjustment is also used for auto-scrolling during rubberband selection.
         * See gtk_scrolled_window_get_hadjustment() for a typical way of obtaining the adjustment,
         * and gtk_flow_box_set_vadjustment() for setting the vertical adjustment.
         * <p>
         * The adjustments have to be in pixel units and in the same coordinate system as the allocation for immediate
         * children of the box.
         *
         * @param box        self
         * @param adjustment An adjustment which should be adjusted when the focus is moved among the descendants
         *                   of container.
         */
        public native void gtk_flow_box_set_hadjustment(Pointer box, Pointer adjustment);

        /**
         * Sets whether all children of box are given equal space in the box.
         *
         * @param box         self
         * @param homogeneous TRUE to create equal allotments, FALSE for variable allotments.
         */
        public native void gtk_flow_box_set_homogeneous(Pointer box, boolean homogeneous);

        /**
         * Sets the maximum number of children to request and allocate space for in box's orientation.
         * <p>
         * Setting the maximum number of children per line limits the overall natural size request to be no more than
         * n_children children long in the given orientation.
         *
         * @param box        self
         * @param n_children The maximum number of children per line.
         */
        public native void gtk_flow_box_set_max_children_per_line(Pointer box, int n_children);

        /**
         * Sets the minimum number of children to line up in box's orientation before flowing.
         *
         * @param box        self
         * @param n_children The minimum number of children per line.
         */
        public native void gtk_flow_box_set_min_children_per_line(Pointer box, int n_children);

        /**
         * Sets the vertical space to add between children.
         *
         * @param box     self
         * @param spacing The spacing to use.
         */
        public native void gtk_flow_box_set_row_spacing(Pointer box, int spacing);

        /**
         * Sets how selection works in box.
         *
         * @param box  self
         * @param mode The new selection mode. Type: GtkSelectionMode
         */
        public native void gtk_flow_box_set_selection_mode(Pointer box, int mode);

        /**
         * By setting a sort function on the box, one can dynamically reorder the children of the box, based on the
         * contents of the children.
         * <p>
         * The sort_func will be called for each child after the call, and will continue to be called each time a child
         * changes (via gtk_flow_box_child_changed()) and when gtk_flow_box_invalidate_sort() is called.
         * <p>
         * Note that using a sort function is incompatible with using a model (see gtk_flow_box_bind_model()).
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
        public native void gtk_flow_box_set_sort_func(Pointer box, GtkFlowBoxSortFunc sort_func, Pointer user_data, GDestroyNotify destroy);

        /**
         * Hooks up an adjustment to focus handling in box.
         * <p>
         * The adjustment is also used for auto-scrolling during rubberband selection. See
         * gtk_scrolled_window_get_vadjustment() for a typical way of obtaining the adjustment, and
         * gtk_flow_box_set_hadjustment() for setting the horizontal adjustment.
         * <p>
         * The adjustments have to be in pixel units and in the same coordinate system as the allocation for
         * immediate children of the box.
         *
         * @param box        se;f
         * @param adjustment An adjustment which should be adjusted when the focus is moved among the descendants
         *                   of container. Type: GtkAdjustment
         */
        public native void gtk_flow_box_set_vadjustment(Pointer box, Pointer adjustment);

        /**
         * Unselect all children of box, if the selection mode allows it.
         *
         * @param box self
         */
        public native void gtk_flow_box_unselect_all(Pointer box);

        /**
         * Unselects a single child of box, if the selection mode allows it.
         *
         * @param box   self
         * @param child A child of box. Type: GtkFlowBoxChild
         */
        public native void gtk_flow_box_unselect_child(Pointer box, Pointer child);

    }
}
