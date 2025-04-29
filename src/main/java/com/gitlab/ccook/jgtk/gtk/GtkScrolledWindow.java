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

import com.gitlab.ccook.jgtk.GtkAdjustment;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkCornerType;
import com.gitlab.ccook.jgtk.enums.GtkPolicyType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * GtkScrolledWindow is a container that makes its child scrollable.
 * <p>
 * It does so using either internally added scrollbars or externally associated adjustments, and optionally draws a
 * frame around the child.
 * <p>
 * Widgets with native scrolling support, i.e. those whose classes implement the GtkScrollable interface, are added
 * directly. For other types of widget, the class GtkViewport acts as an adaptor, giving scroll-ability to other
 * widgets.
 * gtk_scrolled_window_set_child() intelligently accounts for whether the added child is a GtkScrollable. If it
 * isn't, then it wraps the child in a GtkViewport. Therefore, you can just add any child widget and not worry about
 * the details.
 * <p>
 * If gtk_scrolled_window_set_child() has added a GtkViewport for you, it will be automatically removed when you unset
 * the child. Unless GtkScrolledWindow:hscrollbar-policy and GtkScrolledWindow:vscrollbar-policy are GTK_POLICY_NEVER
 * or GTK_POLICY_EXTERNAL, GtkScrolledWindow adds internal GtkScrollbar widgets around its child. The scroll position
 * of the child, and if applicable the scrollbars, is controlled by the GtkScrolledWindow:hadjustment and
 * GtkScrolledWindow:vadjustment that are associated with the GtkScrolledWindow. See the docs on GtkScrollbar for the
 * details, but note that the "step_increment" and "page_increment" fields are only effective if the policy causes
 * scrollbars to be present.
 * <p>
 * If a GtkScrolledWindow doesn't behave quite as you would like, or doesn't have exactly the right layout, it's very
 * possible to set up your own scrolling with GtkScrollbar and for example a GtkGrid.
 */
@SuppressWarnings("unchecked")
public class GtkScrolledWindow extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkScrolledWindowLibrary library = new GtkScrolledWindowLibrary();

    public GtkScrolledWindow(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new scrolled window.
     */
    public GtkScrolledWindow() {
        super(library.gtk_scrolled_window_new());
    }

    /**
     * Unsets the placement of the contents with respect to the scrollbars.
     * <p>
     * If no window placement is set for a scrolled window, it defaults to GTK_CORNER_TOP_LEFT.
     */
    public void clearContentPlacement() {
        library.gtk_scrolled_window_unset_placement(getCReference());
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
     * Reports whether the natural height of the child will be calculated and propagated through the scrolled window's
     * requested natural height.
     *
     * @return Whether natural height propagation is enabled.
     */
    public boolean doesPropagateNaturalHeight() {
        return library.gtk_scrolled_window_get_propagate_natural_height(getCReference());
    }

    /**
     * Reports whether the natural width of the child will be calculated and propagated through the scrolled window's
     * requested natural width.
     *
     * @return Whether natural width propagation is enabled.
     */
    public boolean doesPropagateNaturalWidth() {
        return library.gtk_scrolled_window_get_propagate_natural_width(getCReference());
    }

    /**
     * Gets the child widget of scrolled_window.
     *
     * @return The child widget of scrolled_window, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_scrolled_window_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of scrolled_window.
     *
     * @param child The child widget.
     *              <p>
     *              The argument can be NULL
     */
    public void setChild(GtkWidget child) {
        library.gtk_scrolled_window_set_child(getCReference(), pointerOrNull(child));
    }

    /**
     * Gets the placement of the contents with respect to the scrollbars.
     *
     * @return The current placement value.
     */
    public GtkCornerType getContentPlacement() {
        return GtkCornerType.getTypeFromCValue(library.gtk_scrolled_window_get_placement(getCReference()));
    }

    /**
     * Sets the placement of the contents with respect to the scrollbars for the scrolled window.
     * <p>
     * The default is GTK_CORNER_TOP_LEFT, meaning the child is in the top left, with the scrollbars underneath and to
     * the right. Other values in GtkCornerType are GTK_CORNER_TOP_RIGHT, GTK_CORNER_BOTTOM_LEFT, and
     * GTK_CORNER_BOTTOM_RIGHT.
     * <p>
     * See also gtk_scrolled_window_get_placement() and gtk_scrolled_window_unset_placement().
     *
     * @param placement Position of the child window.
     */
    public void setContentPlacement(GtkCornerType placement) {
        if (placement != null) {
            library.gtk_scrolled_window_set_placement(getCReference(), GtkCornerType.getCValueFromType(placement));
        }
    }

    /**
     * Returns the horizontal scrollbar's adjustment.
     * <p>
     * This is the adjustment used to connect the horizontal scrollbar to the child widget's horizontal scroll
     * functionality.
     *
     * @return The horizontal GtkAdjustment
     */
    public GtkAdjustment getHorizontalAdjustment() {
        return new GtkAdjustment(library.gtk_scrolled_window_get_hadjustment(getCReference()));
    }

    /**
     * Sets the GtkAdjustment for the horizontal scrollbar.
     *
     * @param adjustment The GtkAdjustment to use, or NULL to create a new one.
     *                   <p>
     *                   The argument can be NULL.
     */
    public void setHorizontalAdjustment(GtkAdjustment adjustment) {
        library.gtk_scrolled_window_set_hadjustment(getCReference(), pointerOrNull(adjustment));
    }

    /**
     * Returns the horizontal scrollbar of scrolled_window.
     *
     * @return The horizontal scrollbar of the scrolled window.
     */
    public GtkWidget getHorizontalScrollBar() {
        return (GtkWidget) JGTKObject.newObjectFromType(library.gtk_scrolled_window_get_hscrollbar(getCReference()), GtkWidget.class);
    }

    /**
     * Returns the maximum content height set, if defined
     *
     * @return The maximum content height, or NONE
     */
    public Option<Integer> getMaxContentHeight() {
        int max = library.gtk_scrolled_window_get_max_content_height(getCReference());
        if (max >= 0) {
            return new Option<>(max);
        }
        return Option.NONE;
    }

    /**
     * Sets the maximum height that scrolled_window should keep visible.
     * <p>
     * The scrolled_window will grow up to this height before it starts scrolling the content.
     *
     * @param maxContentHeight The maximum content height; should be greater than minContentHeight
     */
    public void setMaxContentHeight(int maxContentHeight) {
        Option<Integer> min = getMinContentHeight();
        if (min.isDefined()) {
            maxContentHeight = Math.max(min.get() + 1, maxContentHeight);
        }
        library.gtk_scrolled_window_set_max_content_height(getCReference(), maxContentHeight);
    }

    /**
     * Gets the minimal content height of scrolled_window.
     *
     * @return The minimal content height.
     */
    public Option<Integer> getMinContentHeight() {
        int value = library.gtk_scrolled_window_get_min_content_height(getCReference());
        if (value >= 0) {
            return new Option<>(value);
        }
        return Option.NONE;
    }

    /**
     * Sets the minimum height that scrolled_window should keep visible.
     * <p>
     * Note that this can and (usually will) be smaller than the minimum size of the content.
     * <p>
     * It is a programming error to set the minimum content height to a value greater than
     * GtkScrolledWindow:max-content-height.
     *
     * @param minHeight The minimal content height.
     */
    public void setMinContentHeight(int minHeight) {
        library.gtk_scrolled_window_set_min_content_height(getCReference(), minHeight);
    }

    /**
     * Returns the maximum content width set.
     *
     * @return The maximum content width, or NONE
     */
    public Option<Integer> getMaxContentWidth() {
        int max = library.gtk_scrolled_window_get_max_content_width(getCReference());
        if (max >= 0) {
            return new Option<>(max);
        }
        return Option.NONE;
    }

    /**
     * Sets the maximum width that scrolled_window should keep visible.
     * <p>
     * The scrolled_window will grow up to this width before it starts scrolling the content.
     *
     * @param maxContentWidth The maximum content width. should be greater than minContentWidth
     */
    public void setMaxContentWidth(int maxContentWidth) {
        Option<Integer> min = getMinContentWidth();
        if (min.isDefined()) {
            maxContentWidth = Math.max(min.get() + 1, maxContentWidth);
        }
        library.gtk_scrolled_window_set_max_content_width(getCReference(), maxContentWidth);
    }

    /**
     * Gets the minimum content width of scrolled_window.
     *
     * @return The minimum content width.
     */
    public Option<Integer> getMinContentWidth() {
        int value = library.gtk_scrolled_window_get_min_content_width(getCReference());
        if (value >= 0) {
            return new Option<>(value);
        }
        return Option.NONE;
    }

    /**
     * Sets the minimum width that scrolled_window should keep visible.
     * <p>
     * Note that this can and (usually will) be smaller than the minimum size of the content.
     * <p>
     * It is a programming error to set the minimum content width to a value greater than
     * GtkScrolledWindow:max-content-width.
     *
     * @param min The minimal content width.
     */
    public void setMinContentWidth(int min) {
        library.gtk_scrolled_window_set_min_content_width(getCReference(), min);
    }

    /**
     * Retrieves the current policy values for the horizontal and vertical scrollbars.
     *
     * @return first - the policy for the horizontal scrollbar. second - the policy for the vertical scrollbar.
     */
    public Pair<GtkPolicyType, GtkPolicyType> getPolicy() {
        PointerByReference hPolicy = new PointerByReference();
        PointerByReference vPolicy = new PointerByReference();
        library.gtk_scrolled_window_get_policy(getCReference(), hPolicy, vPolicy);
        return new Pair<>(GtkPolicyType.getTypeFromCValue(hPolicy.getPointer().getInt(0)), GtkPolicyType.getTypeFromCValue(vPolicy.getPointer().getInt(0)));
    }

    /**
     * Returns the vertical scrollbar's adjustment.
     * <p>
     * This is the adjustment used to connect the vertical scrollbar to the child widget's vertical scroll
     * functionality.
     *
     * @return The vertical GtkAdjustment
     */
    public GtkAdjustment getVerticalAdjustment() {
        return new GtkAdjustment(library.gtk_scrolled_window_get_vadjustment(getCReference()));
    }

    /**
     * Sets the GtkAdjustment for the vertical scrollbar.
     *
     * @param adjustment The GtkAdjustment to use, or NULL to create a new one.
     *                   <p>
     *                   The argument can be NULL.
     */
    public void setVerticalAdjustment(GtkAdjustment adjustment) {
        library.gtk_scrolled_window_set_vadjustment(getCReference(), pointerOrNull(adjustment));
    }

    /**
     * Returns the vertical scrollbar of scrolled_window.
     *
     * @return The vertical scrollbar of the scrolled window.
     */
    public GtkWidget getVerticalScrollBar() {
        return (GtkWidget) JGTKObject.newObjectFromType(library.gtk_scrolled_window_get_vscrollbar(getCReference()), GtkWidget.class);
    }

    /**
     * Gets whether the scrolled window draws a frame.
     *
     * @return TRUE if the scrolled_window has a frame.
     */
    public boolean hasBevel() {
        return library.gtk_scrolled_window_get_has_frame(getCReference());
    }

    /**
     * Whether kinetic scrolling is enabled or not.
     * <p>
     * Kinetic scrolling only applies to devices with source GDK_SOURCE_TOUCHSCREEN.
     *
     * @return TRUE if kinetic scrolling enabled
     */
    public boolean isKineticScrollingEnabled() {
        return library.gtk_scrolled_window_get_kinetic_scrolling(getCReference());
    }

    /**
     * Returns whether overlay scrolling is enabled for this scrolled window.
     *
     * @return TRUE if overlay scrolling is enabled.
     */
    public boolean isOverlayScrollingEnabled() {
        return library.gtk_scrolled_window_get_overlay_scrolling(getCReference());
    }

    /**
     * Sets the scrollbar policy for the horizontal and vertical scrollbars.
     * <p>
     * The policy determines when the scrollbar should appear; it is a value from the GtkPolicyType enumeration.
     * If GTK_POLICY_ALWAYS, the scrollbar is always present; if GTK_POLICY_NEVER, the scrollbar is never present;
     * if GTK_POLICY_AUTOMATIC, the scrollbar is present only if needed (that is, if the slider part of the bar would
     * be smaller than the trough — the display is larger than the page size).
     *
     * @param hPolicy Policy for horizontal bar.
     * @param vPolicy Policy for vertical bar
     */
    public void setPolicy(GtkPolicyType hPolicy, GtkPolicyType vPolicy) {
        if (hPolicy != null && vPolicy != null) {
            library.gtk_scrolled_window_set_policy(getCReference(), GtkPolicyType.getCValueFromType(hPolicy), GtkPolicyType.getCValueFromType(vPolicy));
        }
    }

    /**
     * Sets whether the natural height of the child should be calculated and propagated through the scrolled window's
     * requested natural height.
     *
     * @param doesProp Whether to propagate natural height.
     */
    public void shouldPropagateNaturalHeight(boolean doesProp) {
        library.gtk_scrolled_window_set_propagate_natural_height(getCReference(), doesProp);
    }

    /**
     * Sets whether the natural width of the child should be calculated and propagated through the scrolled window's
     * requested natural width.
     *
     * @param doesProp Whether to propagate natural width.
     */
    public void shouldPropagateNaturalWidth(boolean doesProp) {
        library.gtk_scrolled_window_set_propagate_natural_width(getCReference(), doesProp);
    }

    /**
     * Changes the frame drawn around the contents of scrolled_window.
     *
     * @param hasBevel Whether to draw a frame around scrolled window contents.
     */
    public void useBevel(boolean hasBevel) {
        library.gtk_scrolled_window_set_has_frame(getCReference(), hasBevel);
    }

    /**
     * Turns kinetic scrolling on or off.
     * <p>
     * Kinetic scrolling only applies to devices with source GDK_SOURCE_TOUCHSCREEN.
     *
     * @param hasKineticScrolling TRUE to enable kinetic scrolling.
     */
    public void useKineticScrolling(boolean hasKineticScrolling) {
        library.gtk_scrolled_window_set_kinetic_scrolling(getCReference(), hasKineticScrolling);
    }

    /**
     * Enables or disables overlay scrolling for this scrolled window.
     *
     * @param hasOverlayScrolling Whether to enable overlay scrolling.
     */
    public void useOverlayScrolling(boolean hasOverlayScrolling) {
        library.gtk_scrolled_window_set_overlay_scrolling(getCReference(), hasOverlayScrolling);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted whenever user initiated scrolling makes the scrolled window firmly surpass the limits defined by the
         * adjustment in that orientation.
         */
        public static final Signals EDGE_OVERSHOT = new Signals("edge-overshot");

        /**
         * Emitted whenever user-initiated scrolling makes the scrolled window exactly reach the lower or upper limits
         * defined by the adjustment in that orientation.
         */
        public static final Signals EDGE_REACHED = new Signals("edge-reached");

        /**
         * Emitted when focus is moved away from the scrolled window by a keybinding.
         */
        public static final Signals MOVE_FOCUS_OUT = new Signals("move-focus-out");

        /**
         * Emitted when a keybinding that scrolls is pressed.
         */
        public static final Signals SCROLL_CHILD = new Signals("scroll-child");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkScrolledWindowLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget of scrolled_window.
         *
         * @param scrolled_window self
         * @return The child widget of scrolled_window. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_scrolled_window_get_child(Pointer scrolled_window);

        /**
         * Returns the horizontal scrollbar's adjustment.
         * <p>
         * This is the adjustment used to connect the horizontal scrollbar to the child widget's horizontal scroll
         * functionality.
         *
         * @param scrolled_window self
         * @return The horizontal GtkAdjustment
         */
        public native Pointer gtk_scrolled_window_get_hadjustment(Pointer scrolled_window);

        /**
         * Gets whether the scrolled window draws a frame.
         *
         * @param scrolled_window self
         * @return TRUE if the scrolled_window has a frame.
         */
        public native boolean gtk_scrolled_window_get_has_frame(Pointer scrolled_window);

        /**
         * Returns the horizontal scrollbar of scrolled_window.
         *
         * @param scrolled_window self
         * @return The horizontal scrollbar of the scrolled window.
         */
        public native Pointer gtk_scrolled_window_get_hscrollbar(Pointer scrolled_window);

        /**
         * Returns the specified kinetic scrolling behavior.
         *
         * @param scrolled_window self
         * @return The scrolling behavior flags.
         */
        public native boolean gtk_scrolled_window_get_kinetic_scrolling(Pointer scrolled_window);

        /**
         * Returns the maximum content height set.
         *
         * @param scrolled_window self
         * @return The maximum content height, or -1
         */
        public native int gtk_scrolled_window_get_max_content_height(Pointer scrolled_window);

        /**
         * Returns the maximum content width set.
         *
         * @param scrolled_window self
         * @return The maximum content width, or -1
         */
        public native int gtk_scrolled_window_get_max_content_width(Pointer scrolled_window);

        /**
         * Gets the minimal content height of scrolled_window.
         *
         * @param scrolled_window self
         * @return The minimal content height.
         */
        public native int gtk_scrolled_window_get_min_content_height(Pointer scrolled_window);

        /**
         * Gets the minimum content width of scrolled_window.
         *
         * @param scrolled_window self
         * @return The minimum content width.
         */
        public native int gtk_scrolled_window_get_min_content_width(Pointer scrolled_window);

        /**
         * Returns whether overlay scrolling is enabled for this scrolled window.
         *
         * @param scrolled_window self
         * @return TRUE if overlay scrolling is enabled.
         */
        public native boolean gtk_scrolled_window_get_overlay_scrolling(Pointer scrolled_window);

        /**
         * Gets the placement of the contents with respect to the scrollbars.
         *
         * @param scrolled_window self
         * @return The current placement value. Type: GtkCornerType
         */
        public native int gtk_scrolled_window_get_placement(Pointer scrolled_window);

        /**
         * Retrieves the current policy values for the horizontal and vertical scrollbars.
         * <p>
         * See gtk_scrolled_window_set_policy().
         *
         * @param scrolled_window   self
         * @param hscrollbar_policy Location to store the policy for the horizontal scrollbar. Type: GtkPolicyType
         *                          <p>
         *                          The argument can be NULL.
         * @param vscrollbar_policy Location to store the policy for the vertical scrollbar. Type: GtkPolicyType
         *                          <p>
         *                          The argument can be NULL.
         */
        public native void gtk_scrolled_window_get_policy(Pointer scrolled_window, PointerByReference hscrollbar_policy, PointerByReference vscrollbar_policy);

        /**
         * Reports whether the natural height of the child will be calculated and propagated through the scrolled
         * window's requested natural height.
         *
         * @param scrolled_window self
         * @return Whether natural height propagation is enabled.
         */
        public native boolean gtk_scrolled_window_get_propagate_natural_height(Pointer scrolled_window);

        /**
         * Reports whether the natural width of the child will be calculated and propagated through the scrolled
         * window's requested natural width.
         *
         * @param scrolled_window self
         * @return Whether natural width propagation is enabled.
         */
        public native boolean gtk_scrolled_window_get_propagate_natural_width(Pointer scrolled_window);

        /**
         * Returns the vertical scrollbar's adjustment.
         * <p>
         * This is the adjustment used to connect the vertical scrollbar to the child widget's vertical scroll
         * functionality.
         *
         * @param scrolled_window self
         * @return The vertical GtkAdjustment
         */
        public native Pointer gtk_scrolled_window_get_vadjustment(Pointer scrolled_window);

        /**
         * Returns the vertical scrollbar of scrolled_window.
         *
         * @param scrolled_window self
         * @return The vertical scrollbar of the scrolled window.
         */
        public native Pointer gtk_scrolled_window_get_vscrollbar(Pointer scrolled_window);

        /**
         * Creates a new scrolled window.
         *
         * @return A new scrolled window. Type: GtkScrolledWindow
         */
        public native Pointer gtk_scrolled_window_new();

        /**
         * Sets the child widget of scrolled_window.
         *
         * @param scrolled_window self
         * @param child           The child widget.
         *                        <p>
         *                        The argument can be NULL.
         */
        public native void gtk_scrolled_window_set_child(Pointer scrolled_window, Pointer child);

        /**
         * Sets the GtkAdjustment for the horizontal scrollbar.
         *
         * @param scrolled_window self
         * @param hadjustment     The GtkAdjustment to use, or NULL to create a new one.
         *                        <p>
         *                        The argument can be NULL.
         */
        public native void gtk_scrolled_window_set_hadjustment(Pointer scrolled_window, Pointer hadjustment);

        /**
         * Changes the frame drawn around the contents of scrolled_window.
         *
         * @param scrolled_window self
         * @param has_frame       Whether to draw a frame around scrolled window contents.
         */
        public native void gtk_scrolled_window_set_has_frame(Pointer scrolled_window, boolean has_frame);

        /**
         * Turns kinetic scrolling on or off.
         * <p>
         * Kinetic scrolling only applies to devices with source GDK_SOURCE_TOUCHSCREEN.
         *
         * @param scrolled_window   self
         * @param kinetic_scrolling TRUE to enable kinetic scrolling.
         */
        public native void gtk_scrolled_window_set_kinetic_scrolling(Pointer scrolled_window, boolean kinetic_scrolling);

        /**
         * Sets the maximum height that scrolled_window should keep visible.
         * <p>
         * The scrolled_window will grow up to this height before it starts scrolling the content.
         * <p>
         * It is a programming error to set the maximum content height to a value smaller than
         * GtkScrolledWindow:min-content-height.
         *
         * @param scrolled_window self
         * @param height          The maximum content height.
         */
        public native void gtk_scrolled_window_set_max_content_height(Pointer scrolled_window, int height);

        /**
         * Sets the maximum width that scrolled_window should keep visible.
         * <p>
         * The scrolled_window will grow up to this width before it starts scrolling the content.
         * <p>
         * It is a programming error to set the maximum content width to a value smaller than
         * GtkScrolledWindow:min-content-width.
         *
         * @param scrolled_window self
         * @param width           The maximum content width.
         */
        public native void gtk_scrolled_window_set_max_content_width(Pointer scrolled_window, int width);

        /**
         * Sets the minimum height that scrolled_window should keep visible.
         * <p>
         * Note that this can and (usually will) be smaller than the minimum size of the content.
         * <p>
         * It is a programming error to set the minimum content height to a value greater than
         * GtkScrolledWindow:max-content-height.
         *
         * @param scrolled_window self
         * @param height          The minimal content height.
         */
        public native void gtk_scrolled_window_set_min_content_height(Pointer scrolled_window, int height);

        /**
         * Sets the minimum width that scrolled_window should keep visible.
         * <p>
         * Note that this can and (usually will) be smaller than the minimum size of the content.
         * <p>
         * It is a programming error to set the minimum content width to a value greater than
         * GtkScrolledWindow:max-content-width.
         *
         * @param scrolled_window self
         * @param width           The minimal content width.
         */
        public native void gtk_scrolled_window_set_min_content_width(Pointer scrolled_window, int width);

        /**
         * Enables or disables overlay scrolling for this scrolled window.
         *
         * @param scrolled_window   self
         * @param overlay_scrolling Whether to enable overlay scrolling.
         */
        public native void gtk_scrolled_window_set_overlay_scrolling(Pointer scrolled_window, boolean overlay_scrolling);

        /**
         * Sets the placement of the contents with respect to the scrollbars for the scrolled window.
         * <p>
         * The default is GTK_CORNER_TOP_LEFT, meaning the child is in the top left, with the scrollbars underneath and
         * to the right. Other values in GtkCornerType are GTK_CORNER_TOP_RIGHT, GTK_CORNER_BOTTOM_LEFT, and
         * GTK_CORNER_BOTTOM_RIGHT.
         * <p>
         * See also gtk_scrolled_window_get_placement() and gtk_scrolled_window_unset_placement().
         *
         * @param scrolled_window  self
         * @param window_placement Position of the child window. Type: GtkCornerType
         */
        public native void gtk_scrolled_window_set_placement(Pointer scrolled_window, int window_placement);

        /**
         * Sets the scrollbar policy for the horizontal and vertical scrollbars.
         * <p>
         * The policy determines when the scrollbar should appear; it is a value from the GtkPolicyType enumeration.
         * If GTK_POLICY_ALWAYS, the scrollbar is always present; if GTK_POLICY_NEVER, the scrollbar is never present;
         * if GTK_POLICY_AUTOMATIC, the scrollbar is present only if needed (that is, if the slider part of the bar
         * would be smaller than the trough — the display is larger than the page size).
         *
         * @param scrolled_window   self
         * @param hscrollbar_policy Policy for horizontal bar. Type: GtkPolicyType
         * @param vscrollbar_policy Policy for vertical bar. Type: GtkPolicyType
         */
        public native void gtk_scrolled_window_set_policy(Pointer scrolled_window, int hscrollbar_policy, int vscrollbar_policy);

        /**
         * Sets whether the natural height of the child should be calculated and propagated through the scrolled
         * window's requested natural height.
         *
         * @param scrolled_window self
         * @param propagate       Whether to propagate natural height.
         */
        public native void gtk_scrolled_window_set_propagate_natural_height(Pointer scrolled_window, boolean propagate);

        /**
         * Sets whether the natural width of the child should be calculated and propagated through the scrolled
         * window's requested natural width.
         *
         * @param scrolled_window self
         * @param propagate       Whether to propagate natural width.
         */
        public native void gtk_scrolled_window_set_propagate_natural_width(Pointer scrolled_window, boolean propagate);

        /**
         * Sets the GtkAdjustment for the vertical scrollbar.
         *
         * @param scrolled_window self
         * @param vadjustment     The GtkAdjustment to use, or NULL to create a new one.
         *                        <p>
         *                        The argument can be NULL.
         */
        public native void gtk_scrolled_window_set_vadjustment(Pointer scrolled_window, Pointer vadjustment);

        /**
         * Unsets the placement of the contents with respect to the scrollbars.
         * <p>
         * If no window placement is set for a scrolled window, it defaults to GTK_CORNER_TOP_LEFT.
         *
         * @param scrolled_window self
         */
        public native void gtk_scrolled_window_unset_placement(Pointer scrolled_window);
    }
}
