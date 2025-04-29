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

import com.gitlab.ccook.jgtk.GtkAdjustment;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkScrollable;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkViewport implements scroll-ability for widgets that lack their own scrolling capabilities.
 * <p>
 * Use GtkViewport to scroll child widgets such as GtkGrid, GtkBox, and so on.
 * <p>
 * The GtkViewport will start scrolling content only if allocated less than the child widget's minimum size in a given
 * orientation.
 */
@SuppressWarnings("unchecked")
public class GtkViewport extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkScrollable {

    private static final GtkViewportLibrary library = new GtkViewportLibrary();

    public GtkViewport(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkViewport.
     * <p>
     * The new viewport uses the given adjustments, or default adjustments if none are given.
     *
     * @param hAdjust Horizontal adjustment.
     *                <p>
     *                The argument can be NULL.
     * @param vAdjust Vertical adjustment.
     *                <p>
     *                The argument can be NULL.
     * @param child   The child of this view port
     *                <p>
     *                The argument can be NULL.
     */
    public GtkViewport(GtkWidget child, GtkAdjustment hAdjust, GtkAdjustment vAdjust) {
        this(hAdjust, vAdjust);
        setChild(child);
    }

    /**
     * Creates a new GtkViewport.
     * <p>
     * The new viewport uses the given adjustments, or default adjustments if none are given.
     *
     * @param hAdjust Horizontal adjustment.
     *                <p>
     *                The argument can be NULL.
     * @param vAdjust Vertical adjustment.
     *                <p>
     *                The argument can be NULL.
     */
    public GtkViewport(GtkAdjustment hAdjust, GtkAdjustment vAdjust) {
        super(library.gtk_viewport_new(pointerOrNull(hAdjust), pointerOrNull(vAdjust)));
    }

    /**
     * Creates a new GtkViewport.
     * <p>
     * The new viewport uses default adjustments
     *
     * @param child The child of this view port
     *              <p>
     *              The argument can be NULL.
     */
    public GtkViewport(GtkWidget child) {
        this();
        setChild(child);
    }

    /**
     * Creates a new GtkViewport.
     * <p>
     * The new viewport uses default adjustments
     */
    public GtkViewport() {
        super(library.gtk_viewport_new(Pointer.NULL, Pointer.NULL));
    }

    /**
     * Gets whether the viewport is scrolling to keep the focused child in view.
     *
     * @return TRUE if the viewport keeps the focus child scrolled to view.
     */
    public boolean doesScrollToFocus() {
        return library.gtk_viewport_get_scroll_to_focus(getCReference());
    }

    /**
     * Gets the child widget of viewport.
     *
     * @return The child widget of viewport, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_viewport_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of viewport.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget w) {
        library.gtk_viewport_set_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Sets whether the viewport should automatically scroll to keep the focused child in view.
     *
     * @param doesScroll Whether to keep the focus widget scrolled to view.
     */
    public void shouldScrollToFocus(boolean doesScroll) {
        library.gtk_viewport_set_scroll_to_focus(getCReference(), doesScroll);
    }

    protected static class GtkViewportLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget of viewport.
         *
         * @param viewport self
         * @return The child widget of viewport.
         *         The return value can be NULL.
         */
        public native Pointer gtk_viewport_get_child(Pointer viewport);

        /**
         * Gets whether the viewport is scrolling to keep the focused child in view.
         *
         * @param viewport self
         * @return TRUE if the viewport keeps the focus child scrolled to view.
         */
        public native boolean gtk_viewport_get_scroll_to_focus(Pointer viewport);

        /**
         * Creates a new GtkViewport.
         * <p>
         * The new viewport uses the given adjustments, or default adjustments if none are given.
         *
         * @param hadjustment Horizontal adjustment. Type: GtkAdjustment
         *                    <p>
         *                    The argument can be NULL.
         * @param vadjustment Vertical adjustment. Type: GtkAdjustment
         *                    <p>
         *                    The argument can be NULL.
         * @return A new GtkViewport
         */
        public native Pointer gtk_viewport_new(Pointer hadjustment, Pointer vadjustment);

        /**
         * Sets the child widget of viewport.
         *
         * @param viewport self
         * @param child    The child widget. Type: GtkWidget
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_viewport_set_child(Pointer viewport, Pointer child);

        /**
         * Sets whether the viewport should automatically scroll to keep the focused child in view.
         *
         * @param viewport        self
         * @param scroll_to_focus Whether to keep the focus widget scrolled to view.
         */
        public native void gtk_viewport_set_scroll_to_focus(Pointer viewport, boolean scroll_to_focus);

    }
}
