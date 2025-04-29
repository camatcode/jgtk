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
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkOverlay is a container which contains a single main child, on top of which it can place "overlay" widgets.
 * <p>
 * The position of each overlay widget is determined by its GtkWidget:halign and GtkWidget:valign properties. E.g. a
 * widget with both alignments set to GTK_ALIGN_START will be placed in the top left corner of the GtkOverlay
 * container, whereas an overlay with halign set to GTK_ALIGN_CENTER and valign set to GTK_ALIGN_END will be placed at
 * the bottom edge of the GtkOverlay, horizontally centered. The position can be adjusted by setting the margin
 * properties of the child to non-zero values.
 * <p>
 * More complicated placement of overlays is possible by connecting to the GtkOverlay::get-child-position signal.
 * <p>
 * An overlay's minimum and natural sizes are those of its main child. The sizes of overlay children are not considered
 * when measuring these preferred sizes.
 */
@SuppressWarnings("unchecked")
public class GtkOverlay extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkOverlayLibrary library = new GtkOverlayLibrary();

    public GtkOverlay(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkOverlay.
     */
    public GtkOverlay() {
        super(library.gtk_overlay_new());
    }

    /**
     * Adds widget to overlay.
     * <p>
     * The widget will be stacked on top of the main widget added with gtk_overlay_set_child().
     * <p>
     * The position at which widget is placed is determined from its GtkWidget:halign and GtkWidget:valign properties.
     *
     * @param w A GtkWidget to be added to the container.
     */
    public void addToOverlay(GtkWidget w) {
        if (w != null) {
            library.gtk_overlay_add_overlay(getCReference(), w.getCReference());
        }
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
     * Gets the child widget of overlay.
     *
     * @return The child widget of overlay, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_overlay_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of overlay.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget w) {
        library.gtk_overlay_set_child(getCReference(), pointerOrNull(w));
    }


    /**
     * Gets whether widget should be clipped within the parent.
     *
     * @param w An overlay child of GtkOverlay
     * @return Whether the widget is clipped within the parent.
     */
    public boolean isClippedWithinParent(GtkWidget w) {
        if (w != null) {
            return library.gtk_overlay_get_clip_overlay(getCReference(), w.getCReference());
        }
        return false;
    }

    /**
     * Gets whether widget's size is included in the measurement of overlay.
     *
     * @param w An overlay child of GtkOverlay
     * @return Whether the widget is included in overlay's measurement.
     */
    public boolean isWidgetSizeIncludedInOverlayMeasurement(GtkWidget w) {
        if (w != null) {
            return library.gtk_overlay_get_measure_overlay(getCReference(), w.getCReference());
        }
        return false;
    }

    /**
     * Removes an overlay that was added with gtk_overlay_add_overlay().
     *
     * @param w A GtkWidget to be removed.
     */
    public void removeFromOverlay(GtkWidget w) {
        if (w != null) {
            library.gtk_overlay_remove_overlay(getCReference(), w.getCReference());
        }
    }

    /**
     * Sets whether widget should be clipped within the parent.
     *
     * @param w          An overlay child of GtkOverlay
     * @param shouldClip Whether the child should be clipped within parent
     */
    public void shouldClipWithinParent(GtkWidget w, boolean shouldClip) {
        if (w != null) {
            library.gtk_overlay_set_clip_overlay(getCReference(), w.getCReference(), shouldClip);
        }
    }

    /**
     * Sets whether widget is included in the measured size of overlay.
     * <p>
     * The overlay will request the size of the largest child that has this property set to TRUE. Children who are not
     * included may be drawn outside of overlay's allocation if they are too large.
     *
     * @param w             An overlay child of GtkOverlay
     * @param shouldInclude Whether the child should be measured.
     */
    public void shouldIncludeWidgetSizeInOverlayMeasurement(GtkWidget w, boolean shouldInclude) {
        if (w != null) {
            library.gtk_overlay_set_measure_overlay(getCReference(), w.getCReference(), shouldInclude);
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted to determine the position and size of any overlay child widgets.
         * <p>
         * A handler for this signal should fill allocation with the desired position and size for widget, relative to
         * the 'main' child of overlay.
         * <p>
         * The default handler for this signal uses the widget's halign and valign properties to determine the position
         * and gives the widget its natural size (except that an alignment of GTK_ALIGN_FILL will cause the overlay to
         * be full-width/height). If the main child is a GtkScrolledWindow, the overlays are placed relative to its
         * contents.
         */
        public static final Signals GET_CHILD_POSITION = new Signals("get-child-position");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkOverlayLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Adds widget to overlay.
         * <p>
         * The widget will be stacked on top of the main widget added with gtk_overlay_set_child().
         * <p>
         * The position at which widget is placed is determined from its GtkWidget:halign and GtkWidget:valign
         * properties.
         *
         * @param overlay self
         * @param widget  A GtkWidget to be added to the container. Type: GtkWidget
         */
        public native void gtk_overlay_add_overlay(Pointer overlay, Pointer widget);

        /**
         * Gets the child widget of overlay.
         *
         * @param overlay self
         * @return The child widget of overlay. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_overlay_get_child(Pointer overlay);

        /**
         * Gets whether widget should be clipped within the parent.
         *
         * @param overlay self
         * @param widget  An overlay child of GtkOverlay. Type: GtkWidget
         * @return Whether the widget is clipped within the parent.
         */
        public native boolean gtk_overlay_get_clip_overlay(Pointer overlay, Pointer widget);

        /**
         * Gets whether widget's size is included in the measurement of overlay.
         *
         * @param overlay self
         * @param widget  An overlay child of GtkOverlay. Type: GtkWidget
         * @return Whether the widget is measured.
         */
        public native boolean gtk_overlay_get_measure_overlay(Pointer overlay, Pointer widget);

        /**
         * Creates a new GtkOverlay.
         *
         * @return A new GtkOverlay object. Type: GtkWidget
         */
        public native Pointer gtk_overlay_new();

        /**
         * Removes an overlay that was added with gtk_overlay_add_overlay().
         *
         * @param overlay self
         * @param widget  A GtkWidget to be removed. Type: GtkWidget
         */
        public native void gtk_overlay_remove_overlay(Pointer overlay, Pointer widget);

        /**
         * Sets the child widget of overlay.
         *
         * @param overlay self
         * @param child   The child widget. Type: GtkWidget
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_overlay_set_child(Pointer overlay, Pointer child);

        /**
         * Sets whether widget should be clipped within the parent.
         *
         * @param overlay      self
         * @param widget       An overlay child of GtkOverlay. Type: GtkWidget
         * @param clip_overlay Whether the child should be clipped.
         */
        public native void gtk_overlay_set_clip_overlay(Pointer overlay, Pointer widget, boolean clip_overlay);

        /**
         * Sets whether widget is included in the measured size of overlay.
         * <p>
         * The overlay will request the size of the largest child that has this property set to TRUE. Children who are
         * not included may be drawn outside of overlay's allocation if they are too large.
         *
         * @param overlay self
         * @param widget  An overlay child of GtkOverlay. Type: GtkWidget
         * @param measure Whether the child should be measured.
         */
        public native void gtk_overlay_set_measure_overlay(Pointer overlay, Pointer widget, boolean measure);

    }
}
