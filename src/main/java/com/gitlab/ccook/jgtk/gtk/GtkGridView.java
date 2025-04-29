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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkSelectionModel;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkGridView presents a large dynamic grid of items.
 * <p>
 * GtkGridView uses its factory to generate one child widget for each visible item and shows them in a grid.
 * The orientation of the grid view determines if the grid re-flows vertically or horizontally.
 * <p>
 * GtkGridView allows the user to select items according to the selection characteristics of the model. For models that
 * allow multiple selected items, it is possible to turn on rubberband selection, using GtkGridView:enable-rubberband.
 */
@SuppressWarnings("unchecked")
public class GtkGridView extends GtkListBase implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkOrientable, GtkScrollable {

    private static final GtkGridViewLibrary library = new GtkGridViewLibrary();

    public GtkGridView(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkGridView that uses the given factory for mapping items to widgets.
     *
     * @param model   The model to use.
     *                <p>
     *                The argument can be NULL.
     * @param factory The factory to populate items with.
     *                <p>
     *                The argument can be NULL.
     */
    public GtkGridView(GtkSelectionModel model, GtkListItemFactory factory) {
        super(handleCtor(model, factory));
    }

    private static Pointer handleCtor(GtkSelectionModel model, GtkListItemFactory factory) {
        return library.gtk_grid_view_new(pointerOrNull(model), pointerOrNull(factory));
    }

    /**
     * Returns whether rows can be selected by dragging with the mouse.
     *
     * @return TRUE if rubber-band selection is enabled.
     */
    public boolean canRubberBandSelection() {
        return library.gtk_grid_view_get_enable_rubberband(getCReference());
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
     * Returns whether items will be activated on single click and selected on hover
     *
     * @return TRUE if items are activated on single click.
     */
    public boolean doesActivateOnSingleClick() {
        return library.gtk_grid_view_get_single_click_activate(getCReference());
    }

    /**
     * Sets whether items should be activated on single click and selected on hover.
     *
     * @param doesActivate TRUE to activate items on single click.
     */
    public void doesActivateOnSingleClick(boolean doesActivate) {
        library.gtk_grid_view_set_single_click_activate(getCReference(), doesActivate);
    }

    /**
     * Sets whether selections can be changed by dragging with the mouse.
     *
     * @param canRubberBand TRUE to enable rubber band selection.
     */
    public void enableRubberBand(boolean canRubberBand) {
        library.gtk_grid_view_set_enable_rubberband(getCReference(), canRubberBand);
    }

    /**
     * Gets the factory that's currently used to populate list items.
     *
     * @return The factory in use, if defined
     */
    public Option<GtkListItemFactory> getFactory() {
        Option<Pointer> p = new Option<>(library.gtk_grid_view_get_factory(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkListItemFactory(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the GtkListItemFactory to use for populating list items.
     *
     * @param f The factory to use.
     *          <p>
     *          The argument can be NULL.
     */
    public void setFactory(GtkListItemFactory f) {
        if (f != null) {
            library.gtk_grid_view_set_factory(getCReference(), f.getCReference());
        } else {
            library.gtk_grid_view_set_factory(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Gets the maximum number of columns that the grid will use.
     *
     * @return The maximum number of columns.
     */
    public int getMaxColumns() {
        return library.gtk_grid_view_get_max_columns(getCReference());
    }

    /**
     * Sets the maximum number of columns to use.
     * <p>
     * This number must be at least 1.
     * <p>
     * If max_columns is smaller than the minimum set via gtk_grid_view_set_min_columns(), that value is used instead.
     *
     * @param maxColumns The maximum number of columns.
     */
    public void setMaxColumns(int maxColumns) {
        library.gtk_grid_view_set_max_columns(getCReference(), Math.max(1, maxColumns));
    }

    /**
     * Gets the minimum number of columns that the grid will use.
     *
     * @return The minimum number of columns.
     */
    public int getMinColumns() {
        return library.gtk_grid_view_get_min_columns(getCReference());
    }

    /**
     * Sets the minimum number of columns to use.
     * <p>
     * This number must be at least 1.
     * <p>
     * If min_columns is smaller than the minimum set via gtk_grid_view_set_max_columns(), that value is ignored.
     *
     * @param minColumns The minimum number of columns.
     */
    public void setMinColumns(int minColumns) {
        library.gtk_grid_view_set_min_columns(getCReference(), Math.max(1, minColumns));
    }

    /**
     * Gets the model that's currently used to read the items displayed.
     *
     * @return The model in use, if defined
     */
    public Option<GtkSelectionModel> getModel() {
        Option<Pointer> p = new Option<>(library.gtk_grid_view_get_model(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkSelectionModel) JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the model to use.
     * <p>
     * This must be a GtkSelectionModel.
     *
     * @param m The model to use.
     *          <p>
     *          The argument can be NULL.
     */
    public void setModel(GtkSelectionModel m) {
        if (m != null) {
            library.gtk_grid_view_set_model(getCReference(), m.getCReference());
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static final class Signals extends GtkWidget.Signals {

        /**
         * Emitted when a cell has been activated by the user, usually via activating the
         * GtkGridView|list.activate-item action.
         * <p>
         * This allows for a convenient way to handle activation in a gridview. See GtkListItem:activatable for
         * details on how to use this signal.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkGridViewLibrary extends GtkListBaseLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns whether rows can be selected by dragging with the mouse.
         *
         * @param self self
         * @return TRUE if rubberband selection is enabled.
         */
        public native boolean gtk_grid_view_get_enable_rubberband(Pointer self);

        /**
         * Gets the factory that's currently used to populate list items.
         *
         * @param self self
         * @return The factory in use. Type: GtkListItemFactory
         */
        public native Pointer gtk_grid_view_get_factory(Pointer self);

        /**
         * Gets the maximum number of columns that the grid will use.
         *
         * @param self self
         * @return The maximum number of columns.
         */
        public native int gtk_grid_view_get_max_columns(Pointer self);

        /**
         * Gets the minimum number of columns that the grid will use.
         *
         * @param self self
         * @return The minimum number of columns.
         */
        public native int gtk_grid_view_get_min_columns(Pointer self);

        /**
         * Gets the model that's currently used to read the items displayed.
         *
         * @param self self
         * @return The model in use. Type: GtkSelectionModel
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_grid_view_get_model(Pointer self);

        /**
         * Returns whether items will be activated on single click and selected on hover.
         *
         * @param self self
         * @return TRUE if items are activated on single click.
         */
        public native boolean gtk_grid_view_get_single_click_activate(Pointer self);

        /**
         * Creates a new GtkGridView that uses the given factory for mapping items to widgets.
         *
         * @param model   The model to use.
         *                <p>
         *                The argument can be NULL.
         * @param factory The factory to populate items with.
         *                <p>
         *                The argument can be NULL.
         * @return A new GtkGridView using the given model and factory. TypeL GtkGridView
         */
        public native Pointer gtk_grid_view_new(Pointer model, Pointer factory);

        /**
         * Sets whether selections can be changed by dragging with the mouse.
         *
         * @param self              self
         * @param enable_rubberband TRUE to enable rubberband selection.
         */
        public native void gtk_grid_view_set_enable_rubberband(Pointer self, boolean enable_rubberband);

        /**
         * Sets the GtkListItemFactory to use for populating list items.
         *
         * @param self    self
         * @param factory The factory to use. Type: GtkListItemFactory
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_grid_view_set_factory(Pointer self, Pointer factory);

        /**
         * Sets the maximum number of columns to use.
         * <p>
         * This number must be at least 1.
         * <p>
         * If max_columns is smaller than the minimum set via gtk_grid_view_set_min_columns(), that value is used
         * instead.
         *
         * @param self        self
         * @param max_columns The maximum number of columns.
         */
        public native void gtk_grid_view_set_max_columns(Pointer self, int max_columns);

        /**
         * Sets the minimum number of columns to use.
         * <p>
         * This number must be at least 1.
         * <p>
         * If min_columns is smaller than the minimum set via gtk_grid_view_set_max_columns(), that value is ignored.
         *
         * @param self        self
         * @param min_columns The minimum number of columns.
         */
        public native void gtk_grid_view_set_min_columns(Pointer self, int min_columns);

        /**
         * Sets the model to use.
         * <p>
         * This must be a GtkSelectionModel.
         *
         * @param self  self
         * @param model The model to use. Type: GtkSelectionModel
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_grid_view_set_model(Pointer self, Pointer model);

        /**
         * Sets whether items should be activated on single click and selected on hover.
         *
         * @param self                  self
         * @param single_click_activate TRUE to activate items on single click.
         */
        public native void gtk_grid_view_set_single_click_activate(Pointer self, boolean single_click_activate);
    }
}
