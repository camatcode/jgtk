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
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkActionable;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkListBoxRow is the kind of widget that can be added to a GtkListBox.
 */
@SuppressWarnings("unchecked")
public class GtkListBoxRow extends GtkWidget implements GtkAccessible, GtkActionable, GtkBuildable, GtkConstraintTarget {
    private static final GtkListBoxRowLibrary library = new GtkListBoxRowLibrary();

    public GtkListBoxRow(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkListBoxRow.
     */
    public GtkListBoxRow() {
        super(library.gtk_list_box_row_new());
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
     * Gets the child widget of row.
     *
     * @return The child widget of row, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_list_box_row_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of self.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget w) {
        library.gtk_list_box_row_set_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Returns the current header of the row.
     * <p>
     * This can be used in a GtkListBoxUpdateHeaderFunc to see if there is a header set already, and if so to
     * update the state of it.
     *
     * @return The current header, if defined
     */
    public Option<GtkWidget> getHeader() {
        Option<Pointer> p = new Option<>(library.gtk_list_box_row_get_header(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the current header of the row.
     * <p>
     * This is only allowed to be called from a GtkListBoxUpdateHeaderFunc. It will replace any existing header in the
     * row, and be shown in front of the row in the list box.
     *
     * @param w The header.
     *          <p>
     *          The argument can be NULL.
     */
    public void setHeader(GtkWidget w) {
        library.gtk_list_box_row_set_header(getCReference(), pointerOrNull(w));
    }

    /**
     * Gets the current index of the row in its GtkListBox container.
     *
     * @return The index of the row, or NONE if the row is not in a list box.
     */
    public Option<Integer> getIndex() {
        int index = library.gtk_list_box_row_get_index(getCReference());
        if (index >= 0) {
            return new Option<>(index);
        }
        return Option.NONE;
    }

    /**
     * Gets whether the row is activatable.
     *
     * @return TRUE if the row is activatable.
     */
    public boolean isActivatable() {
        return library.gtk_list_box_row_get_activatable(getCReference());
    }

    /**
     * Set whether the row is activatable.
     *
     * @param isActivatable TRUE to mark the row as activatable.
     */
    public void setActivatable(boolean isActivatable) {
        library.gtk_list_box_row_set_activatable(getCReference(), isActivatable);
    }

    /**
     * Gets whether the row can be selected.
     *
     * @return TRUE if the row is selectable.
     */
    public boolean isSelectable() {
        return library.gtk_list_box_row_get_selectable(getCReference());
    }

    /**
     * Set whether the row can be selected.
     *
     * @param isSelectable TRUE to mark the row as selectable.
     */
    public void setSelectable(boolean isSelectable) {
        library.gtk_list_box_row_set_selectable(getCReference(), isSelectable);
    }

    /**
     * Returns whether the child is currently selected in its GtkListBox container.
     *
     * @return TRUE if row is selected.
     */
    public boolean isSelected() {
        return library.gtk_list_box_row_is_selected(getCReference());
    }

    /**
     * Marks row as changed, causing any state that depends on this to be updated.
     * <p>
     * This affects sorting, filtering and headers.
     * <p>
     * Note that calls to this method must be in sync with the data used for the row functions. For instance, if the
     * list is mirroring some external data set, and two rows changed in the external data set then when you call
     * gtk_list_box_row_changed() on the first row the sort function must only read the new data for the first of the
     * two changed rows, otherwise the resorting of the rows will be wrong.
     * <p>
     * This generally means that if you don't fully control the data model you have to duplicate the data that affects
     * the list box row functions into the row widgets themselves. Another alternative is to call
     * gtk_list_box_invalidate_sort() on any model change, but that is more expensive.
     */
    public void markChanged() {
        library.gtk_list_box_row_changed(getCReference());
    }

    @SuppressWarnings("SameParameterValue")
    public static final class Signals extends GtkWidget.Signals {
        /**
         * This is a keybinding signal, which will cause this row to be activated.
         * <p>
         * If you want to be notified when the user activates a row (by key or not), use the GtkListBox::row-activated
         * signal on the row's parent GtkListBox.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkListBoxRowLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Marks row as changed, causing any state that depends on this to be updated.
         * <p>
         * This affects sorting, filtering and headers.
         * <p>
         * Note that calls to this method must be in sync with the data used for the row functions. For instance,
         * if the list is mirroring some external data set, and two rows changed in the external data set then when
         * you call gtk_list_box_row_changed() on the first row the sort function must only read the new data for the
         * first of the two changed rows, otherwise the resorting of the rows will be wrong.
         * <p>
         * This generally means that if you don't fully control the data model you have to duplicate the data that
         * affects the list box row functions into the row widgets themselves. Another alternative is to call
         * gtk_list_box_invalidate_sort() on any model change, but that is more expensive.
         *
         * @param row self
         */
        public native void gtk_list_box_row_changed(Pointer row);

        /**
         * Gets whether the row is activatable.
         *
         * @param row self
         * @return TRUE if the row is activatable.
         */
        public native boolean gtk_list_box_row_get_activatable(Pointer row);

        /**
         * Gets the child widget of row.
         *
         * @param row self
         * @return The child widget of row. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_list_box_row_get_child(Pointer row);

        /**
         * Returns the current header of the row.
         * <p>
         * This can be used in a GtkListBoxUpdateHeaderFunc to see if there is a header set already, and if so to
         * update the state of it.
         *
         * @param row self
         * @return The current header. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_list_box_row_get_header(Pointer row);

        /**
         * Gets the current index of the row in its GtkListBox container.
         *
         * @param row self
         * @return The index of the row, or -1 if the row is not in a list box.
         */
        public native int gtk_list_box_row_get_index(Pointer row);

        /**
         * Gets whether the row can be selected.
         *
         * @param row self
         * @return TRUE if the row is selectable.
         */
        public native boolean gtk_list_box_row_get_selectable(Pointer row);

        /**
         * Returns whether the child is currently selected in its GtkListBox container.
         *
         * @param row self
         * @return TRUE if row is selected.
         */
        public native boolean gtk_list_box_row_is_selected(Pointer row);

        /**
         * Creates a new GtkListBoxRow.
         *
         * @return A new GtkListBoxRow. Type: GtkListBoxRow
         */
        public native Pointer gtk_list_box_row_new();

        /**
         * Set whether the row is activatable.
         *
         * @param row         self
         * @param activatable TRUE to mark the row as activatable.
         */
        public native void gtk_list_box_row_set_activatable(Pointer row, boolean activatable);

        /**
         * Sets the child widget of self.
         *
         * @param row   self
         * @param child The child widget. Type: GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_list_box_row_set_child(Pointer row, Pointer child);

        /**
         * Sets the current header of the row.
         * <p>
         * This is only allowed to be called from a GtkListBoxUpdateHeaderFunc. It will replace any existing header
         * in the row, and be shown in front of the row in the list box.
         *
         * @param row    self
         * @param header The header. Type: GtkWidget
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_list_box_row_set_header(Pointer row, Pointer header);

        /**
         * Set whether the row can be selected.
         *
         * @param row        self
         * @param selectable TRUE to mark the row as selectable.
         */
        public native void gtk_list_box_row_set_selectable(Pointer row, boolean selectable);

    }
}
