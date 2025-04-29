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

import com.gitlab.ccook.jgtk.GdkRectangle;
import com.gitlab.ccook.jgtk.GtkAdjustment;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * GtkRange is the common base class for widgets which visualize an adjustment.
 * <p>
 * Widgets that are derived from GtkRange include GtkScale
 * <p>
 * Apart from signals for monitoring the parameters of the adjustment, GtkRange provides properties and methods for
 * setting a "fill level" on range widgets. See gtk_range_set_fill_level().
 */
@SuppressWarnings("unchecked")
public class GtkRange extends GtkWidget implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkRangeLibrary library = new GtkRangeLibrary();

    public GtkRange(Pointer ref) {
        super(ref);
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
     * Gets whether the GtkRange respects text direction.
     *
     * @return TRUE if the range is flippable
     */
    public boolean doesRespectTextDirection() {
        return library.gtk_range_get_flippable(getCReference());
    }

    /**
     * Gets whether the range displays the fill level graphically.
     *
     * @return TRUE if range shows the fill level.
     */
    public boolean doesShowFillLevel() {
        return library.gtk_range_get_show_fill_level(getCReference());
    }

    /**
     * Get the adjustment which is the "model" object for GtkRange.
     *
     * @return A GtkAdjustment
     */
    public Option<GtkAdjustment> getAdjustment() {
        Option<Pointer> p = new Option<>(library.gtk_range_get_adjustment(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkAdjustment) JGTKObject.newObjectFromType(p.get(), GtkAdjustment.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the adjustment to be used as the "model" object for the GtkRange
     * <p>
     * The adjustment indicates the current range value, the minimum and maximum range values, the step/page increments
     * used for keybindings and scrolling, and the page size.
     * <p>
     * The page size is normally 0 for GtkScale and nonzero for GtkScrollbar, and indicates the size of the visible
     * area of the widget being scrolled. The page size affects the size of the scrollbar slider.
     *
     * @param adj A GtkAdjustment
     */
    public void setAdjustment(GtkAdjustment adj) {
        if (adj != null) {
            library.gtk_range_set_adjustment(getCReference(), adj.getCReference());
        }
    }

    /**
     * Gets the current position of the fill level indicator.
     *
     * @return The current fill level.
     */
    public double getFillLevel() {
        return library.gtk_range_get_fill_level(getCReference());
    }

    /**
     * Set the new position of the fill level indicator.
     * <p>
     * The "fill level" is probably best described by its most prominent use case, which is an indicator for the amount
     * of pre-buffering in a streaming media player. In that use case, the value of the range would indicate the current
     * play position, and the fill level would be the position up to which the file/stream has been downloaded.
     * <p>
     * This amount of pre-buffering can be displayed on the range's trough and is theme-able separately from the trough.
     * To enable fill level display, use gtk_range_set_show_fill_level(). The range defaults to not showing the fill
     * level.
     * <p>
     * Additionally, it's possible to restrict the range's slider position to values which are smaller than the fill
     * level. This is controlled by gtk_range_set_restrict_to_fill_level() and is by default enabled.
     *
     * @param fillLevel The new position of the fill level indicator.
     */
    public void setFillLevel(double fillLevel) {
        library.gtk_range_set_fill_level(getCReference(), fillLevel);
    }

    /**
     * This function returns the area that contains the range's trough, in coordinates relative to range's origin.
     * <p>
     * This function is useful mainly for GtkRange subclasses.
     *
     * @return the area that contains the range's trough, in coordinates relative to range's origin.
     */
    public GdkRectangle getRangeRectangle() {
        GdkRectangle.GdkRectangleStruct.ByReference rectPointer = new GdkRectangle.GdkRectangleStruct.ByReference();
        library.gtk_range_get_range_rect(getCReference(), rectPointer);
        return new GdkRectangle(rectPointer);
    }

    /**
     * Gets the number of digits to round the value to when it changes.
     *
     * @return The number of digits to round to. (or -1 for none)
     */
    public int getSignificantDigits() {
        return library.gtk_range_get_round_digits(getCReference());
    }

    /**
     * Sets the number of digits to round the value to when it changes.
     * <p>
     * See GtkRange::change-value.
     *
     * @param numSigDig The precision in digits, or -1
     */
    public void setSignificantDigits(int numSigDig) {
        numSigDig = Math.max(-1, numSigDig);
        library.gtk_range_set_round_digits(getCReference(), numSigDig);
    }

    /**
     * This function returns sliders range along the long dimension, in widget->window coordinates.
     * <p>
     * This function is useful mainly for GtkRange subclasses.
     *
     * @return start and end coordinates
     */
    public Pair<Integer, Integer> getSliderRange() {
        PointerByReference start = new PointerByReference();
        PointerByReference end = new PointerByReference();
        library.gtk_range_get_slider_range(getCReference(), start, end);
        return new Pair<>(start.getPointer().getInt(0), end.getPointer().getInt(0));
    }

    /**
     * Gets the current value of the range.
     *
     * @return Current value of the range.
     */
    public double getValue() {
        return library.gtk_range_get_value(getCReference());
    }

    /**
     * Sets the current value of the range.
     * <p>
     * If the value is outside the minimum or maximum range values, it will be clamped to fit inside them.
     * The range emits the GtkRange::value-changed signal if the value changes.
     *
     * @param value New value of the range.
     */
    public void setValue(double value) {
        library.gtk_range_set_value(getCReference(), value);
    }

    /**
     * Gets whether the range is inverted.
     *
     * @return TRUE if the range is inverted.
     */
    public boolean isInverted() {
        return library.gtk_range_get_inverted(getCReference());
    }

    /**
     * Sets whether to invert the range.
     * <p>
     * Ranges normally move from lower to higher values as the slider moves from top to bottom or left to right.
     * Inverted ranges have higher values at the top or on the right rather than on the bottom or left.
     *
     * @param isInverted TRUE to invert the range.
     */
    public void setInverted(boolean isInverted) {
        library.gtk_range_set_inverted(getCReference(), isInverted);
    }

    /**
     * Gets whether the range is restricted to the fill level.
     *
     * @return TRUE if range is restricted to the fill level.
     */
    public boolean isRangeRestricted() {
        return library.gtk_range_get_restrict_to_fill_level(getCReference());
    }

    /**
     * Sets whether the slider is restricted to the fill level.
     * <p>
     * See gtk_range_set_fill_level() for a general description of the fill level concept.
     *
     * @param isRestricted Whether the fill level restricts slider movement.
     */
    public void setRangeRestricted(boolean isRestricted) {
        library.gtk_range_set_restrict_to_fill_level(getCReference(), isRestricted);
    }

    /**
     * This function is useful mainly for GtkRange subclasses.
     *
     * @return Whether the range's slider has a fixed size.
     */
    public boolean isSizeFixed() {
        return library.gtk_range_get_slider_size_fixed(getCReference());
    }

    /**
     * Sets whether the range's slider has a fixed size, or a size that depends on its adjustment's page size.
     * <p>
     * This function is useful mainly for GtkRange subclasses.
     *
     * @param isFixed TRUE to make the slider size constant.
     */
    public void setSizeFixed(boolean isFixed) {
        library.gtk_range_set_slider_size_fixed(getCReference(), isFixed);
    }

    /**
     * Sets the allowable values in the GtkRange.
     * <p>
     * The range value is clamped to be between min and max.
     * (If the range has a non-zero page size, it is clamped between min and max page size.)
     *
     * @param min Minimum range value.
     * @param max Maximum range value.
     */
    public void setAllowableRange(double min, double max) {
        library.gtk_range_set_range(getCReference(), min, max);
    }

    /**
     * Sets the step and page sizes for the range.
     * <p>
     * The step size is used when the user clicks the GtkScrollbar arrows or moves a GtkScale via arrow keys.
     * The page size is used for example when moving via Page Up or Page Down keys.
     *
     * @param step Step size.
     * @param page Page size.
     */
    public void setIncrements(double step, double page) {
        library.gtk_range_set_increments(getCReference(), step, page);
    }

    /**
     * Sets whether the GtkRange respects text direction.
     * <p>
     * If a range is flippable, it will switch its direction if it is horizontal and its direction is GTK_TEXT_DIR_RTL.
     *
     * @param shouldRespect TRUE to make the range flippable.
     */
    public void shouldRespectTextDirection(boolean shouldRespect) {
        library.gtk_range_set_flippable(getCReference(), shouldRespect);
    }

    /**
     * Sets whether a graphical fill level is show on the trough.
     * <p>
     * See gtk_range_set_fill_level() for a general description of the fill level concept.
     *
     * @param doesShow Whether a fill level indicator graphics is shown.
     */
    public void shouldShowFillLevel(boolean doesShow) {
        library.gtk_range_set_show_fill_level(getCReference(), doesShow);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted before clamping a value, to give the application a chance to adjust the bounds.
         */
        public static final Signals ADJUST_BOUNDS = new Signals("adjust-bounds");
        /**
         * Virtual function that moves the slider.
         */
        public static final Signals MOVE_SLIDER = new Signals("move-slider");
        /**
         * Emitted when a scroll action is performed on a range.
         */
        public static final Signals SCROLL_ACTION_PERFORMED = new Signals("change-value");
        /**
         * Emitted when the range value changes.
         */
        public static final Signals VALUE_CHANGED = new Signals("value-changed");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkRangeLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Get the adjustment which is the "model" object for GtkRange.
         *
         * @param range self
         * @return A GtkAdjustment
         */
        public native Pointer gtk_range_get_adjustment(Pointer range);

        /**
         * Gets the current position of the fill level indicator.
         *
         * @param range self
         * @return The current fill level.
         */
        public native double gtk_range_get_fill_level(Pointer range);

        /**
         * Gets whether the GtkRange respects text direction.
         *
         * @param range self
         * @return TRUE if the range is flippable.
         */
        public native boolean gtk_range_get_flippable(Pointer range);

        /**
         * Gets whether the range is inverted.
         * <p>
         * See gtk_range_set_inverted().
         *
         * @param range self
         * @return TRUE if the range is inverted.
         */
        public native boolean gtk_range_get_inverted(Pointer range);

        /**
         * This function returns the area that contains the range's trough, in coordinates relative to range's origin.
         * <p>
         * This function is useful mainly for GtkRange subclasses.
         *
         * @param range      self
         * @param range_rect Return location for the range rectangle.
         */
        public native void gtk_range_get_range_rect(Pointer range, GdkRectangle.GdkRectangleStruct.ByReference range_rect);

        /**
         * Gets whether the range is restricted to the fill level.
         *
         * @param range self
         * @return TRUE if range is restricted to the fill level.
         */
        public native boolean gtk_range_get_restrict_to_fill_level(Pointer range);

        /**
         * Gets the number of digits to round the value to when it changes.
         *
         * @param range self
         * @return The number of digits to round to.
         */
        public native int gtk_range_get_round_digits(Pointer range);

        /**
         * Gets whether the range displays the fill level graphically.
         *
         * @param range self
         * @return TRUE if range shows the fill level.
         */
        public native boolean gtk_range_get_show_fill_level(Pointer range);

        /**
         * This function returns sliders range along the long dimension, in widget->window coordinates.
         * <p>
         * This function is useful mainly for GtkRange subclasses.
         *
         * @param range        self
         * @param slider_start Return location for the slider's start.
         *                     <p>
         *                     The argument can be NULL.
         * @param slider_end   Return location for the slider's end.
         *                     <p>
         *                     The argument can be NULL.
         */
        public native void gtk_range_get_slider_range(Pointer range, PointerByReference slider_start, PointerByReference slider_end);

        /**
         * This function is useful mainly for GtkRange subclasses.
         *
         * @param range self
         * @return Whether the range's slider has a fixed size.
         */
        public native boolean gtk_range_get_slider_size_fixed(Pointer range);

        /**
         * Gets the current value of the range.
         *
         * @param range self
         * @return Current value of the range.
         */
        public native double gtk_range_get_value(Pointer range);

        /**
         * Sets the adjustment to be used as the "model" object for the GtkRange
         * <p>
         * The adjustment indicates the current range value, the minimum and maximum range values, the step/page
         * increments used for keybindings and scrolling, and the page size.
         * <p>
         * The page size is normally 0 for GtkScale and nonzero for GtkScrollbar, and indicates the size of the
         * visible area of the widget being scrolled. The page size affects the size of the scrollbar slider.
         *
         * @param range      self
         * @param adjustment A GtkAdjustment
         */
        public native void gtk_range_set_adjustment(Pointer range, Pointer adjustment);

        /**
         * Set the new position of the fill level indicator.
         * <p>
         * The "fill level" is probably best described by its most prominent use case, which is an indicator for the
         * amount of pre-buffering in a streaming media player. In that use case, the value of the range would indicate
         * the current play position, and the fill level would be the position up to which the file/stream has been
         * downloaded.
         * <p>
         * This amount of pre-buffering can be displayed on the range's trough and is theme-able separately from the
         * trough. To enable fill level display, use gtk_range_set_show_fill_level(). The range defaults to not showing
         * the fill level.
         * <p>
         * Additionally, it's possible to restrict the range's slider position to values which are smaller than the
         * fill level. This is controlled by gtk_range_set_restrict_to_fill_level() and is by default enabled.
         *
         * @param range      self
         * @param fill_level The new position of the fill level indicator.
         */
        public native void gtk_range_set_fill_level(Pointer range, double fill_level);

        /**
         * Sets whether the GtkRange respects text direction.
         * <p>
         * If a range is flippable, it will switch its direction if it is horizontal and its direction is
         * GTK_TEXT_DIR_RTL.
         *
         * @param range     self
         * @param flippable TRUE to make the range flippable.
         */
        public native void gtk_range_set_flippable(Pointer range, boolean flippable);

        /**
         * Sets the step and page sizes for the range.
         * <p>
         * The step size is used when the user clicks the GtkScrollbar arrows or moves a GtkScale via arrow keys.
         * The page size is used for example when moving via Page Up or Page Down keys.
         *
         * @param range self
         * @param step  Step size.
         * @param page  Page size.
         */
        public native void gtk_range_set_increments(Pointer range, double step, double page);

        /**
         * Sets whether to invert the range.
         * <p>
         * Ranges normally move from lower to higher values as the slider moves from top to bottom or left to right.
         * Inverted ranges have higher values at the top or on the right rather than on the bottom or left.
         *
         * @param range   self
         * @param setting TRUE to invert the range.
         */
        public native void gtk_range_set_inverted(Pointer range, boolean setting);

        /**
         * Sets the allowable values in the GtkRange.
         * <p>
         * The range value is clamped to be between min and max. (If the range has a non-zero page size, it is clamped
         * between min and max - page-size.)
         *
         * @param range self
         * @param min   Minimum range value.
         * @param max   Maximum range value.
         */
        public native void gtk_range_set_range(Pointer range, double min, double max);

        /**
         * Sets whether the slider is restricted to the fill level.
         * <p>
         * See gtk_range_set_fill_level() for a general description of the fill level concept.
         *
         * @param range                  self
         * @param restrict_to_fill_level Whether the fill level restricts slider movement.
         */
        public native void gtk_range_set_restrict_to_fill_level(Pointer range, boolean restrict_to_fill_level);

        /**
         * Sets the number of digits to round the value to when it changes.
         * <p>
         * See GtkRange::change-value.
         *
         * @param range        self
         * @param round_digits The precision in digits, or -1
         */
        public native void gtk_range_set_round_digits(Pointer range, int round_digits);

        /**
         * Sets whether a graphical fill level is show on the trough.
         * <p>
         * See gtk_range_set_fill_level() for a general description of the fill level concept.
         *
         * @param range           self
         * @param show_fill_level Whether a fill level indicator graphics is shown.
         */
        public native void gtk_range_set_show_fill_level(Pointer range, boolean show_fill_level);

        /**
         * Sets whether the range's slider has a fixed size, or a size that depends on its adjustment's page size.
         * <p>
         * This function is useful mainly for GtkRange subclasses.
         *
         * @param range      self
         * @param size_fixed TRUE to make the slider size constant.
         */
        public native void gtk_range_set_slider_size_fixed(Pointer range, boolean size_fixed);

        /**
         * Sets the current value of the range.
         * <p>
         * If the value is outside the minimum or maximum range values, it will be clamped to fit inside them.
         * The range emits the GtkRange::value-changed signal if the value changes.
         *
         * @param range self
         * @param value New value of the range.
         */
        public native void gtk_range_set_value(Pointer range, double value);
    }
}
