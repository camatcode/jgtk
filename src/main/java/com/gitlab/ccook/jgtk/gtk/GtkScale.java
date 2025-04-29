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
import com.gitlab.ccook.jgtk.PangoLayout;
import com.gitlab.ccook.jgtk.callbacks.GDestroyNotify;
import com.gitlab.ccook.jgtk.callbacks.GtkScaleFormatValueFunc;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * A GtkScale is a slider control used to select a numeric value.
 * <p>
 * To use it, you'll probably want to investigate the methods on its base class, GtkRange, in addition to the methods
 * for GtkScale itself. To set the value of a scale, you would normally use gtk_range_set_value(). To detect changes to
 * the value, you would normally use the GtkRange::value-changed signal.
 * <p>
 * Note that using the same upper and lower bounds for the GtkScale (through the GtkRange methods) will hide the slider
 * itself. This is useful for applications that want to show an indeterminate value on the scale, without changing the
 * layout of the application (such as movie or music players).
 */
@SuppressWarnings("unchecked")
public class GtkScale extends GtkRange implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkScaleLibrary library = new GtkScaleLibrary();

    public GtkScale(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkScale.
     *
     * @param orientation The scale's orientation.
     */
    public GtkScale(GtkOrientation orientation) {
        super(library.gtk_scale_new(GtkOrientation.getCValueFromOrientation(orientation), Pointer.NULL));
    }

    /**
     * Creates a new GtkScale.
     *
     * @param orientation The scale's orientation.
     * @param adjustment  The GtkAdjustment which sets the range of the scale, or NULL to create a new adjustment.
     *                    <p>
     *                    The argument can be NULL.
     */
    public GtkScale(GtkOrientation orientation, GtkAdjustment adjustment) {
        super(library.gtk_scale_new(GtkOrientation.getCValueFromOrientation(orientation), pointerOrNull(adjustment)));
    }

    /**
     * Creates a new scale widget with a range from min to max.
     * <p>
     * The returns scale will have the given orientation and will let the user input a number between min and max
     * (including min and max) with the increment step. step must be nonzero; it's the distance the slider moves when
     * using the arrow keys to adjust the scale value.
     * <p>
     * Note that the way in which the precision is derived works best if step is a power of ten. If the resulting
     * precision is not suitable for your needs, use gtk_scale_set_digits() to correct it.
     *
     * @param orientation The scale's orientation.
     * @param min         Minimum value.
     * @param max         Maximum value.
     * @param step        Step increment (tick size) used with keyboard shortcuts.
     */
    public GtkScale(GtkOrientation orientation, double min, double max, double step) {
        super(library.gtk_scale_new_with_range(GtkOrientation.getCValueFromOrientation(orientation), min, max, step));
    }

    /**
     * Removes any marks that have been added.
     */
    public void clearMarks() {
        library.gtk_scale_clear_marks(getCReference());
    }

    /**
     * Returns whether the current value is displayed as a string next to the slider.
     *
     * @return Whether the current value is displayed as a string.
     */
    public boolean doesDisplayValue() {
        return library.gtk_scale_get_draw_value(getCReference());
    }

    /**
     * Gets the PangoLayout used to display the scale.
     * <p>
     * The returned object is owned by the scale so does not need to be freed by the caller.
     *
     * @return The PangoLayout for this scale, or NONE if the GtkScale:draw-value property is FALSE.
     */
    public Option<PangoLayout> getLayout() {
        Option<Pointer> p = new Option<>(library.gtk_scale_get_layout(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoLayout(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Obtains the coordinates where the scale will draw the PangoLayout representing the text in the scale.
     * <p>
     * Remember when using the PangoLayout function you need to convert to and from pixels using PANGO_PIXELS() or
     * PANGO_SCALE.
     * <p>
     * If the GtkScale:draw-value property is FALSE, the return values are undefined.
     *
     * @return If defined, first - X offset of layout, second - Y offset of layout
     */
    public Option<Pair<Integer, Integer>> getLayoutOffsets() {
        PointerByReference x = new PointerByReference();
        PointerByReference y = new PointerByReference();
        library.gtk_scale_get_layout_offsets(getCReference(), x, y);
        Pointer xPointer = x.getPointer();
        Pointer yPointer = y.getPointer();
        if (xPointer != Pointer.NULL && yPointer != Pointer.NULL) {
            return new Option<>(new Pair<>(xPointer.getInt(0), yPointer.getInt(0)));
        }
        return Option.NONE;
    }

    /**
     * Gets the number of decimal places that are displayed in the value.
     *
     * @return The number of decimal places that are displayed.
     */
    @Override
    public int getSignificantDigits() {
        return library.gtk_scale_get_digits(getCReference());
    }

    /**
     * Sets the number of decimal places that are displayed in the value.
     * <p>
     * Also causes the value of the adjustment to be rounded to this number of digits, so the retrieved value matches
     * the displayed one, if GtkScale:draw-value is TRUE when the value changes. If you want to enforce rounding the
     * value when GtkScale:draw-value is FALSE, you can set GtkRange:round-digits instead.
     * <p>
     * Note that rounding to a small number of digits can interfere with the smooth auto-scrolling that is built into
     * GtkScale. As an alternative, you can use gtk_scale_set_format_value_func() to format the displayed value
     * yourself.
     *
     * @param numSigDig The number of decimal places to display, e.g. use 1 to display 1.0, 2 to display 1.00, etc.
     */
    @Override
    public void setSignificantDigits(int numSigDig) {
        library.gtk_scale_set_digits(getCReference(), numSigDig);
    }

    /**
     * Gets the position in which the current value is displayed.
     *
     * @return The position in which the current value is displayed.
     */
    public GtkPositionType getValuePosition() {
        return GtkPositionType.getTypeFromCValue(library.gtk_scale_get_value_pos(getCReference()));
    }

    /**
     * Sets the position in which the current value is displayed.
     *
     * @param pos The position in which the current value is displayed.
     */
    public void setValuePosition(GtkPositionType pos) {
        if (pos != null) {
            library.gtk_scale_set_value_pos(getCReference(), GtkPositionType.getCValueFromType(pos));
        }
    }

    /**
     * Returns whether the scale has an origin.
     *
     * @return TRUE if the scale has an origin.
     */
    public boolean hasOrigin() {
        return library.gtk_scale_get_has_origin(getCReference());
    }

    /**
     * Adds a mark at value.
     * <p>
     * A mark is indicated visually by drawing a tick mark next to the scale, and GTK makes it easy for the user to
     * position the scale exactly at the marks value.
     * <p>
     * If markup is not NULL, text is shown next to the tick mark.
     * <p>
     * To remove marks from a scale, use gtk_scale_clear_marks().
     *
     * @param value      The value at which the mark is placed, must be between the lower and upper limits of the
     *                   scales' adjustment.
     * @param position   Where to draw the mark. For a horizontal scale, GTK_POS_TOP and GTK_POS_LEFT are drawn above
     *                   the scale, anything else below. For a vertical scale, GTK_POS_LEFT and GTK_POS_TOP are drawn
     *                   to the left of the scale, anything else to the right.
     * @param markupText Text to be shown at the mark, using Pango markup.
     *                   <p>
     *                   The argument can be NULL.
     */
    public void placeMark(double value, GtkPositionType position, String markupText) {
        library.gtk_scale_add_mark(getCReference(), value, GtkPositionType.getCValueFromType(position), markupText);
    }

    /**
     * Sets whether the scale has an origin.
     * <p>
     * If GtkScale:has-origin is set to TRUE (the default), the scale will highlight the part of the trough between the
     * origin (bottom or left side) and the current value.
     *
     * @param hasOrigin TRUE if the scale has an origin.
     */
    public void setHasOrigin(boolean hasOrigin) {
        library.gtk_scale_set_has_origin(getCReference(), hasOrigin);
    }

    /**
     * func allows you to change how the scale value is displayed.
     * <p>
     * The given function will return an allocated string representing value. That string will then be used to display
     * the scale's value.
     * <p>
     * If NULL is passed as func, the value will be displayed on its own, rounded according to the value of the
     * GtkScale:digits property.
     *
     * @param func          Function that formats the value.
     *                      <p>
     *                      The argument can be NULL.
     * @param userData      User data to pass to func.
     *                      <p>
     *                      The argument can be NULL.
     * @param destroyNotify Destroy function for user_data.
     *                      <p>
     *                      The argument can be NULL.
     */
    public void setValueFormatFunc(GtkScaleFormatValueFunc func, Pointer userData, GDestroyNotify destroyNotify) {
        preventGarbageCollection(func, userData, destroyNotify);
        library.gtk_scale_set_format_value_func(getCReference(), func, userData, destroyNotify);
    }

    /**
     * Specifies whether the current value is displayed as a string next to the slider.
     *
     * @param doesDisplay TRUE to draw the value.
     */
    public void shouldDisplayValue(boolean doesDisplay) {
        library.gtk_scale_set_draw_value(getCReference(), doesDisplay);
    }

    protected static class GtkScaleLibrary extends GtkRangeLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a mark at value.
         * <p>
         * A mark is indicated visually by drawing a tick mark next to the scale, and GTK makes it easy for the user to
         * position the scale exactly at the marks value.
         * <p>
         * If markup is not NULL, text is shown next to the tick mark.
         * <p>
         * To remove marks from a scale, use gtk_scale_clear_marks().
         *
         * @param scale    self
         * @param value    The value at which the mark is placed, must be between the lower and upper limits of the
         *                 scale's adjustment.
         * @param position Where to draw the mark. For a horizontal scale, GTK_POS_TOP and GTK_POS_LEFT are drawn above
         *                 the scale, anything else below. For a vertical scale, GTK_POS_LEFT and GTK_POS_TOP are drawn
         *                 to the left of the scale, anything else to the right.
         * @param markup   Text to be shown at the mark, using Pango markup.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_scale_add_mark(Pointer scale, double value, int position, String markup);

        /**
         * Removes any marks that have been added.
         *
         * @param scale self
         */
        public native void gtk_scale_clear_marks(Pointer scale);

        /**
         * Gets the number of decimal places that are displayed in the value.
         *
         * @param scale self
         * @return The number of decimal places that are displayed.
         */
        public native int gtk_scale_get_digits(Pointer scale);

        /**
         * Returns whether the current value is displayed as a string next to the slider.
         *
         * @param scale self
         * @return Whether the current value is displayed as a string.
         */
        public native boolean gtk_scale_get_draw_value(Pointer scale);

        /**
         * Returns whether the scale has an origin.
         *
         * @param scale self
         * @return TRUE if the scale has an origin.
         */
        public native boolean gtk_scale_get_has_origin(Pointer scale);

        /**
         * Gets the PangoLayout used to display the scale.
         * <p>
         * The returned object is owned by the scale so does not need to be freed by the caller.
         *
         * @param scale self
         * @return The PangoLayout for this scale, or NULL if the GtkScale:draw-value property is FALSE.
         *         Type: PangoLayout
         */
        public native Pointer gtk_scale_get_layout(Pointer scale);

        /**
         * Obtains the coordinates where the scale will draw the PangoLayout representing the text in the scale.
         * <p>
         * Remember when using the PangoLayout function you need to convert to and from pixels using PANGO_PIXELS() or
         * PANGO_SCALE.
         * <p>
         * If the GtkScale:draw-value property is FALSE, the return values are undefined.
         *
         * @param scale self
         * @param x     Location to store X offset of layout.
         *              <p>
         *              The argument can be NULL.
         * @param y     Location to store Y offset of layout.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_scale_get_layout_offsets(Pointer scale, PointerByReference x, PointerByReference y);

        /**
         * Gets the position in which the current value is displayed.
         *
         * @param scale self
         * @return The position in which the current value is displayed.
         */
        public native int gtk_scale_get_value_pos(Pointer scale);

        /**
         * Creates a new GtkScale.
         *
         * @param orientation The scale's orientation. Type: GtkOrientation
         * @param adjustment  The GtkAdjustment which sets the range of the scale, or NULL to create a new adjustment.
         *                    <p>
         *                    The argument can be NULL.
         * @return A new GtkScale
         */
        public native Pointer gtk_scale_new(int orientation, Pointer adjustment);

        /**
         * Creates a new scale widget with a range from min to max.
         * <p>
         * The returns scale will have the given orientation and will let the user input a number between min and max
         * (including min and max) with the increment step. step must be nonzero; it's the distance the slider moves
         * when using the arrow keys to adjust the scale value.
         * <p>
         * Note that the way in which the precision is derived works best if step is a power of ten. If the resulting
         * precision is not suitable for your needs, use gtk_scale_set_digits() to correct it.
         *
         * @param orientation The scale's orientation. Type: GtkOrientation
         * @param min         Minimum value.
         * @param max         Maximum value.
         * @param step        Step increment (tick size) used with keyboard shortcuts.
         * @return A new GtkScale
         */
        public native Pointer gtk_scale_new_with_range(int orientation, double min, double max, double step);

        /**
         * Sets the number of decimal places that are displayed in the value.
         * <p>
         * Also causes the value of the adjustment to be rounded to this number of digits, so the retrieved value
         * matches the displayed one, if GtkScale:draw-value is TRUE when the value changes. If you want to enforce
         * rounding the value when GtkScale:draw-value is FALSE, you can set GtkRange:round-digits instead.
         * <p>
         * Note that rounding to a small number of digits can interfere with the smooth auto-scrolling that is built
         * into GtkScale. As an alternative, you can use gtk_scale_set_format_value_func() to format the displayed
         * value yourself.
         *
         * @param scale  self
         * @param digits The number of decimal places to display, e.g. use 1 to display 1.0, 2 to display 1.00, etc.
         */
        public native void gtk_scale_set_digits(Pointer scale, int digits);

        /**
         * Specifies whether the current value is displayed as a string next to the slider.
         *
         * @param scale      self
         * @param draw_value TRUE to draw the value.
         */
        public native void gtk_scale_set_draw_value(Pointer scale, boolean draw_value);

        /**
         * func allows you to change how the scale value is displayed.
         * <p>
         * The given function will return an allocated string representing value. That string will then be used to
         * display the scale's value.
         * <p>
         * If NULL is passed as func, the value will be displayed on its own, rounded according to the value of the
         * GtkScale:digits property.
         *
         * @param scale          self
         * @param func           Function that formats the value.
         *                       <p>
         *                       The argument can be NULL.
         * @param user_data      User data to pass to func.
         *                       <p>
         *                       The argument can be NULL.
         * @param destroy_notify Destroy function for user_data.
         *                       <p>
         *                       The argument can be NULL.
         */
        public native void gtk_scale_set_format_value_func(Pointer scale, GtkScaleFormatValueFunc func, Pointer user_data, GDestroyNotify destroy_notify);

        /**
         * Sets whether the scale has an origin.
         * <p>
         * If GtkScale:has-origin is set to TRUE (the default), the scale will highlight the part of the trough between
         * the origin (bottom or left side) and the current value.
         *
         * @param scale      self
         * @param has_origin TRUE if the scale has an origin.
         */
        public native void gtk_scale_set_has_origin(Pointer scale, boolean has_origin);

        /**
         * Sets the position in which the current value is displayed.
         *
         * @param scale self
         * @param pos   The position in which the current value is displayed. Type: GtkPositionType
         */
        public native void gtk_scale_set_value_pos(Pointer scale, int pos);
    }
}
