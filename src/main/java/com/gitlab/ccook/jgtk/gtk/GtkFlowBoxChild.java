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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkFlowBoxChild is the kind of widget that can be added to a GtkFlowBox.
 */
@SuppressWarnings("unchecked")
public class GtkFlowBoxChild extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkFlowBoxChildLibrary library = new GtkFlowBoxChildLibrary();

    public GtkFlowBoxChild(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkFlowBoxChild, with a given child
     * <p>
     * This should only be used as a child of a GtkFlowBox.
     *
     * @param w the child to turn into a GtkFlowBoxChild
     */
    public GtkFlowBoxChild(GtkWidget w) {
        this();
        setChild(w);
    }

    /**
     * Creates a new GtkFlowBoxChild.
     * <p>
     * This should only be used as a child of a GtkFlowBox.
     */
    public GtkFlowBoxChild() {
        super(library.gtk_flow_box_child_new());
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
     * Gets the child widget of self.
     *
     * @return the child widget of self, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_flow_box_child_get_child(getCReference()));
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
        if (w != null) {
            library.gtk_flow_box_child_set_child(getCReference(), w.getCReference());
        } else {
            library.gtk_flow_box_child_set_child(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Gets the current index of the child in its GtkFlowBox container.
     *
     * @return The index of the child, or NONE if the child is not in a flow box.
     */
    public Option<Integer> getFlowBoxIndex() {
        int index = library.gtk_flow_box_child_get_index(getCReference());
        if (index >= 0) {
            return new Option<>(index);
        }
        return Option.NONE;
    }

    /**
     * Returns whether the child is currently selected in its GtkFlowBox container.
     *
     * @return TRUE if child is selected.
     */
    public boolean isSelected() {
        return library.gtk_flow_box_child_is_selected(getCReference());
    }

    /**
     * Marks child as changed, causing any state that depends on this to be updated.
     * <p>
     * This affects sorting and filtering.
     * <p>
     * Note that calls to this method must be in sync with the data used for the sorting and filtering functions.
     * For instance, if the list is mirroring some external data set, and two children changed in the external data set
     * when you call gtk_flow_box_child_changed() on the first child, the sort function must only read the new data for
     * the first of the two changed children, otherwise the resorting of the children will be wrong.
     * <p>
     * This generally means that if you don't fully control the data model, you have to duplicate the data that affects
     * the sorting and filtering functions into the widgets themselves.
     * <p>
     * Another alternative is to call gtk_flow_box_invalidate_sort() on any model change, but that is more expensive.
     */
    public void markChanged() {
        library.gtk_flow_box_child_changed(getCReference());
    }

    @SuppressWarnings("SameParameterValue")
    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted when the user activates a child widget in a GtkFlowBox.
         * <p>
         * This can happen either by clicking or double-clicking, or via a keybinding.
         * <p>
         * This is a keybinding signal, but it can be used by applications for their own purposes.
         * <p>
         * The default bindings are Space and Enter.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkFlowBoxChildLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Marks child as changed, causing any state that depends on this to be updated.
         * <p>
         * This affects sorting and filtering.
         * <p>
         * Note that calls to this method must be in sync with the data used for the sorting and filtering functions.
         * For instance, if the list is mirroring some external data set, and two children changed in the external data
         * set when you call gtk_flow_box_child_changed() on the first child, the sort function must only read the new
         * data for the first of the two changed children, otherwise the resorting of the children will be wrong.
         * <p>
         * This generally means that if you don't fully control the data model, you have to duplicate the data that
         * affects the sorting and filtering functions into the widgets themselves.
         * <p>
         * Another alternative is to call gtk_flow_box_invalidate_sort() on any model change, but that is more
         * expensive.
         *
         * @param child self
         */
        public native void gtk_flow_box_child_changed(Pointer child);

        /**
         * Gets the child widget of self.
         *
         * @param self self
         * @return The child widget of self. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_flow_box_child_get_child(Pointer self);

        /**
         * Gets the current index of the child in its GtkFlowBox container.
         *
         * @param child self
         * @return The index of the child, or -1 if the child is not in a flow box.
         */
        public native int gtk_flow_box_child_get_index(Pointer child);

        /**
         * Returns whether the child is currently selected in its GtkFlowBox container.
         *
         * @param child self
         * @return TRUE if child is selected.
         */
        public native boolean gtk_flow_box_child_is_selected(Pointer child);

        /**
         * Creates a new GtkFlowBoxChild.
         * <p>
         * This should only be used as a child of a GtkFlowBox.
         *
         * @return A new GtkFlowBoxChild. Type: GtkFlowBoxChild
         */
        public native Pointer gtk_flow_box_child_new();

        /**
         * Sets the child widget of self.
         *
         * @param self  self
         * @param child The child widget. Type: GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_flow_box_child_set_child(Pointer self, Pointer child);
    }
}
