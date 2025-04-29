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
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * A widget with two panes, arranged either horizontally or vertically.
 * <p>
 * The division between the two panes is adjustable by the user by dragging a handle.
 * <p>
 * Child widgets are added to the panes of the widget with gtk_paned_set_start_child() and gtk_paned_set_end_child().
 * The division between the two children is set by default from the size requests of the children, but it can be
 * adjusted by the user.
 * <p>
 * A paned widget draws a separator between the two child widgets and a small handle that the user can drag to adjust
 * the division. It does not draw any relief around the children or around the separator. (The space in which the
 * separator is called the gutter.) Often, it is useful to put each child inside a GtkFrame so that the gutter appears
 * as a ridge. No separator is drawn if one of the children is missing.
 * <p>
 * Each child has two options that can be set, "resize" and "shrink". If "resize" is true then, when the GtkPaned is
 * resized, that child will expand or shrink along with the paned widget. If "shrink" is true, then that child can be
 * made smaller than its requisition by the user. Setting "shrink" to false allows the application to set a minimum
 * size. If "resize" is false for both children, then this is treated as if "resize" is true for both children.
 * <p>
 * The application can set the position of the slider as if it were set by the user, by calling
 * gtk_paned_set_position().
 */
@SuppressWarnings("unchecked")
public class GtkPaned extends GtkWidget implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkConstraintTarget, GtkOrientable {
    private static final GtkPanedLibrary library = new GtkPanedLibrary();

    public GtkPaned(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkPaned widget.
     *
     * @param orientation The paned's orientation.
     */
    public GtkPaned(GtkOrientation orientation) {
        super(library.gtk_paned_new(GtkOrientation.getCValueFromOrientation(orientation)));
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
     * Obtains the position of the divider between the two panes.
     *
     * @return The position of the divider, in pixels.
     */
    public int getDividerPosition() {
        return library.gtk_paned_get_position(getCReference());
    }

    /**
     * Sets the position of the divider between the two panes.
     *
     * @param pixelPosition Pixel position of divider, a negative value means that the position is unset.
     */
    public void setDividerPosition(int pixelPosition) {
        library.gtk_paned_set_position(getCReference(), pixelPosition);
    }

    /**
     * Retrieves the end child of the given GtkPaned.
     *
     * @return The end child widget, if defined
     */
    public Option<GtkWidget> getEndChild() {
        Option<Pointer> p = new Option<>(library.gtk_paned_get_end_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the end child of paned to child.
     * <p>
     * If child is NULL, the existing child will be removed.
     *
     * @param w The widget to add.
     *          <p>
     *          The argument can be NULL.
     */
    public void setEndChild(GtkWidget w) {
        library.gtk_paned_set_end_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Retrieves the start child of the given GtkPaned.
     *
     * @return The start child widget, if defined.
     */
    public Option<GtkWidget> getStartChild() {
        Option<Pointer> p = new Option<>(library.gtk_paned_get_start_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the start child of paned to child.
     * <p>
     * If child is NULL, the existing child will be removed.
     *
     * @param w The widget to add.
     *          <p>
     *          The argument can be NULL.
     */
    public void setStartChild(GtkWidget w) {
        library.gtk_paned_set_start_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Returns whether the GtkPaned:end-child can be resized.
     *
     * @return TRUE if the end child is resizable.
     */
    public boolean isEndChildResizable() {
        return library.gtk_paned_get_resize_end_child(getCReference());
    }

    /**
     * Returns whether the GtkPaned:end-child can shrink.
     *
     * @return TRUE if the end child is shrinkable.
     */
    public boolean isEndChildShrinkable() {
        return library.gtk_paned_get_shrink_end_child(getCReference());
    }

    /**
     * Sets whether the GtkPaned:end-child can shrink.
     *
     * @param canShrink TRUE to let the end child be shrunk.
     */
    public void setEndChildShrinkable(boolean canShrink) {
        library.gtk_paned_set_shrink_end_child(getCReference(), canShrink);
    }

    /**
     * Gets whether the separator should be wide.
     *
     * @return TRUE if the paned should have a wide handle.
     */
    public boolean isSeparatorWide() {
        return library.gtk_paned_get_wide_handle(getCReference());
    }

    /**
     * Sets whether the separator should be wide.
     *
     * @param isWide The new value for the GtkPaned:wide-handle property.
     */
    public void setSeparatorWide(boolean isWide) {
        library.gtk_paned_set_wide_handle(getCReference(), isWide);
    }

    /**
     * Returns whether the GtkPaned:start-child can be resized.
     *
     * @return TRUE if the start child is resizable.
     */
    public boolean isStartChildResizable() {
        return library.gtk_paned_get_resize_start_child(getCReference());
    }

    /**
     * Returns whether the GtkPaned:start-child can shrink.
     *
     * @return TRUE if the start child is shrinkable.
     */
    public boolean isStartChildShrinkable() {
        return library.gtk_paned_get_shrink_start_child(getCReference());
    }

    /**
     * Sets whether the GtkPaned:start-child can shrink.
     *
     * @param canShrink TRUE to let the start child be shrunk.
     */
    public void setStartChildShrinkable(boolean canShrink) {
        library.gtk_paned_set_shrink_start_child(getCReference(), canShrink);
    }

    /**
     * Sets whether the GtkPaned:end-child can be resized.
     *
     * @param canResize TRUE to let the end child be resized.
     */
    public void setEndChildResizeable(boolean canResize) {
        library.gtk_paned_set_resize_end_child(getCReference(), canResize);
    }

    /**
     * Sets whether the GtkPaned:start-child can be resized.
     *
     * @param canResize TRUE to let the start child be resized.
     */
    public void setStartChildResizeable(boolean canResize) {
        library.gtk_paned_set_resize_start_child(getCReference(), canResize);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted to accept the current position of the handle when moving it using key bindings.
         */
        public static final Signals ACCEPT_POSITION = new Signals("accept-position");

        /**
         * Emitted to cancel moving the position of the handle using key bindings.
         */
        public static final Signals CANCEL_POSITION = new Signals("cancel-position");

        /**
         * Emitted to cycle the focus between the children of the paned.
         */
        public static final Signals CYCLE_CHILD_FOCUS = new Signals("cycle-child-focus");

        /**
         * Emitted to cycle whether the paned should grab focus to allow the user to change position of the handle by
         * using key bindings.
         */
        public static final Signals CYCLE_HANDLE_FOCUS = new Signals("cycle-handle-focus");

        /**
         * Emitted to move the handle with key bindings.
         */
        public static final Signals MOVE_HANDLE = new Signals("move-handle");

        /**
         * Emitted to accept the current position of the handle and then move focus to the next widget in the focus
         * chain.
         */
        public static final Signals TOGGLE_HANDLE_FOCUS = new Signals("toggle-handle-focus");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkPanedLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the end child of the given GtkPaned.
         *
         * @param paned self
         * @return The end child widget. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_paned_get_end_child(Pointer paned);

        /**
         * Obtains the position of the divider between the two panes.
         *
         * @param paned self
         * @return The position of the divider, in pixels.
         */
        public native int gtk_paned_get_position(Pointer paned);

        /**
         * Returns whether the GtkPaned:end-child can be resized.
         *
         * @param paned self
         * @return True if the end child is resizable.
         */
        public native boolean gtk_paned_get_resize_end_child(Pointer paned);

        /**
         * Returns whether the GtkPaned:start-child can be resized.
         *
         * @param paned self
         * @return True if the start child is resizable.
         */
        public native boolean gtk_paned_get_resize_start_child(Pointer paned);

        /**
         * Returns whether the GtkPaned:end-child can shrink.
         *
         * @param paned self
         * @return True if the end child is shrinkable.
         */
        public native boolean gtk_paned_get_shrink_end_child(Pointer paned);

        /**
         * Returns whether the GtkPaned:start-child can shrink.
         *
         * @param paned self
         * @return True if the start child is shrinkable.
         */
        public native boolean gtk_paned_get_shrink_start_child(Pointer paned);

        /**
         * Retrieves the start child of the given GtkPaned.
         *
         * @param paned self
         * @return The start child widget. Type: GtkWidget
         */
        public native Pointer gtk_paned_get_start_child(Pointer paned);

        /**
         * Gets whether the separator should be wide.
         *
         * @param paned self
         * @return TRUE if the paned should have a wide handle.
         */
        public native boolean gtk_paned_get_wide_handle(Pointer paned);

        /**
         * Creates a new GtkPaned widget.
         *
         * @param orientation The GtkPaned's orientation. Type: GtkOrientation
         * @return The newly created paned widget. Type: GtkPaned
         */
        public native Pointer gtk_paned_new(int orientation);

        /**
         * Sets the end child of paned to child.
         * <p>
         * If child is NULL, the existing child will be removed.
         *
         * @param paned self
         * @param child The widget to add. Type: GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_paned_set_end_child(Pointer paned, Pointer child);

        /**
         * Sets the position of the divider between the two panes.
         *
         * @param paned    self
         * @param position Pixel position of divider, a negative value means that the position is unset.
         */
        public native void gtk_paned_set_position(Pointer paned, int position);

        /**
         * Sets whether the GtkPaned:end-child can be resized.
         *
         * @param paned  self
         * @param resize True to let the end child be resized.
         */
        public native void gtk_paned_set_resize_end_child(Pointer paned, boolean resize);

        /**
         * Sets whether the GtkPaned:start-child can be resized.
         *
         * @param paned  self
         * @param resize True to let the start child be resized.
         */
        public native void gtk_paned_set_resize_start_child(Pointer paned, boolean resize);

        /**
         * Sets whether the GtkPaned:end-child can shrink.
         *
         * @param paned  self
         * @param resize True to let the end child be shrunk.
         */
        public native void gtk_paned_set_shrink_end_child(Pointer paned, boolean resize);

        /**
         * Sets whether the GtkPaned:start-child can shrink.
         *
         * @param paned  self
         * @param resize True to let the start child be shrunk.
         */
        public native void gtk_paned_set_shrink_start_child(Pointer paned, boolean resize);

        /**
         * Sets the start child of paned to child.
         * <p>
         * If child is NULL, the existing child will be removed.
         *
         * @param paned self
         * @param child The widget to add. Type: GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_paned_set_start_child(Pointer paned, Pointer child);

        /**
         * Sets whether the separator should be wide.
         *
         * @param paned self
         * @param wide  whether the separator should be wide.
         */
        public native void gtk_paned_set_wide_handle(Pointer paned, boolean wide);
    }
}
