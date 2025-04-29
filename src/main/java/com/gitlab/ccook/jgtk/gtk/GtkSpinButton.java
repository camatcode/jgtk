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
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkSpinButtonUpdatePolicy;
import com.gitlab.ccook.jgtk.enums.GtkSpinType;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * A GtkSpinButton is an ideal way to allow the user to set the value of some attribute.
 * <p>
 * Rather than having to directly type a number into a GtkEntry, GtkSpinButton allows the user to click on one of two
 * arrows to increment or decrement the displayed value. A value can still be typed in, with the bonus that it can be
 * checked to ensure it is in a given range.
 * <p>
 * The main properties of a GtkSpinButton are through an adjustment. See the GtkAdjustment documentation for more
 * details about an adjustment's properties.
 * <p>
 * Note that GtkSpinButton will by default make its entry large enough to accommodate the lower and upper bounds of
 * the adjustment. If this is not desired, the automatic sizing can be turned off by explicitly setting
 * GtkEditable:width-chars to a value != -1.
 */
public class GtkSpinButton extends GtkWidget implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkCellEditable, GtkConstraintTarget, GtkEditable, GtkOrientable {

    private static final GtkSpinButtonLibrary library = new GtkSpinButtonLibrary();

    public GtkSpinButton(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkSpinButton.
     *
     * @param adjustment The GtkAdjustment that this spin button should use.
     *                   <p>
     *                   The argument can be NULL.
     * @param climbRate  Specifies by how much the rate of change in the value will accelerate if you continue to hold
     *                   down an up/down button or arrow key.
     * @param sigDig     The number of decimal places to display.
     */
    public GtkSpinButton(GtkAdjustment adjustment, double climbRate, int sigDig) {
        super(library.gtk_spin_button_new(adjustment.getCReference(), climbRate, sigDig));
    }

    /**
     * @param climbRate Specifies by how much the rate of change in the value will accelerate if you continue to hold
     *                  down an up/down button or arrow key.
     * @param sigDig    The number of decimal places to display.
     */
    public GtkSpinButton(double climbRate, int sigDig) {
        super(library.gtk_spin_button_new(Pointer.NULL, climbRate, sigDig));
    }

    /**
     * Creates a new GtkSpinButton with the given properties.
     * <p>
     * This is a convenience constructor that allows creation of a numeric GtkSpinButton without manually creating an
     * adjustment. The value is initially set to the minimum value and a page increment of 10 * step is the default.
     * The precision of the spin button is equivalent to the precision of step.
     * <p>
     * Note that the way in which the precision is derived works best if step is a power of ten. If the resulting
     * precision is not suitable for your needs, use gtk_spin_button_set_digits() to correct it.
     *
     * @param min  Minimum allowable value.
     * @param max  Maximum allowable value.
     * @param step Increment added or subtracted by spinning the widget.
     */
    public GtkSpinButton(double min, double max, double step) {
        super(library.gtk_spin_button_new_with_range(min, max, step));
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
     * Returns whether the values are corrected to the nearest step.
     *
     * @return TRUE if values are snapped to the nearest step.
     */
    public boolean doesSnapToNearestStep() {
        return library.gtk_spin_button_get_snap_to_ticks(getCReference());
    }

    /**
     * Returns whether the spin button's value wraps around to the opposite limit when the upper or lower limit of the
     * range is exceeded.
     *
     * @return TRUE if the spin button wraps around.
     */
    public boolean doesWrap() {
        return library.gtk_spin_button_get_wrap(getCReference());
    }

    /**
     * Manually force an update of the spin button.
     */
    public void forceUpdate() {
        library.gtk_spin_button_update(getCReference());
    }

    /**
     * Get the adjustment associated with a GtkSpinButton.
     *
     * @return The GtkAdjustment of spin_button.
     */
    public GtkAdjustment getAdjustment() {
        return new GtkAdjustment(library.gtk_spin_button_get_adjustment(getCReference()));
    }

    /**
     * Replaces the GtkAdjustment associated with spin_button.
     *
     * @param adjustment A GtkAdjustment to replace the existing adjustment.
     */
    public void setAdjustment(GtkAdjustment adjustment) {
        if (adjustment != null) {
            library.gtk_spin_button_set_adjustment(getCReference(), adjustment.getCReference());
        }
    }

    /**
     * Returns the acceleration rate for repeated changes.
     *
     * @return The acceleration rate.
     */
    public double getClimbRate() {
        return library.gtk_spin_button_get_climb_rate(getCReference());
    }

    /**
     * Sets the acceleration rate for repeated changes when you hold down a button or key.
     *
     * @param climbRate The rate of acceleration, must be >= 0
     */
    public void setClimbRate(double climbRate) {
        if (climbRate >= 0) {
            library.gtk_spin_button_set_climb_rate(getCReference(), climbRate);
        }
    }

    /**
     * Gets the current step and page the increments used by spin_button.
     *
     * @return first - step increment. second - page increment.
     */
    public Pair<Double, Double> getIncrements() {
        PointerByReference step = new PointerByReference();
        PointerByReference page = new PointerByReference();
        library.gtk_spin_button_get_increments(getCReference(), step, page);
        return new Pair<>(step.getPointer().getDouble(0), page.getPointer().getDouble(0));
    }

    /**
     * Gets the range allowed for spin_button.
     *
     * @return first - minimum allowed value. second - maximum allowed value.
     */
    public Pair<Double, Double> getRange() {
        PointerByReference min = new PointerByReference();
        PointerByReference max = new PointerByReference();
        library.gtk_spin_button_get_range(getCReference(), min, max);
        return new Pair<>(min.getPointer().getDouble(0), max.getPointer().getDouble(0));
    }

    /**
     * Fetches the number of significant digits of spin_button.
     *
     * @return the current number of significant digits
     */
    public int getSignificantDigits() {
        return library.gtk_spin_button_get_digits(getCReference());
    }

    /**
     * Set the precision to be displayed by spin_button.
     * <p>
     * Up to 20 digit precision is allowed.
     *
     * @param numSigDig The number of digits after the decimal point to be displayed for the spin button's value.
     */
    public void setSignificantDigits(int numSigDig) {
        library.gtk_spin_button_set_digits(getCReference(), Math.min(Math.max(0, numSigDig), 20));
    }

    /**
     * Gets the update behavior of a spin button.
     *
     * @return The current update policy.
     */
    public GtkSpinButtonUpdatePolicy getUpdatePolicy() {
        return GtkSpinButtonUpdatePolicy.getPolicyFromCValue(library.gtk_spin_button_get_update_policy(getCReference()));
    }

    /**
     * Sets the update behavior of a spin button.
     * <p>
     * This determines whether the spin button is always updated or only when a valid value is set.
     *
     * @param policy A GtkSpinButtonUpdatePolicy value.
     */
    public void setUpdatePolicy(GtkSpinButtonUpdatePolicy policy) {
        if (policy != null) {
            library.gtk_spin_button_set_update_policy(getCReference(), GtkSpinButtonUpdatePolicy.getCValueFromPolicy(policy));
        }
    }

    /**
     * Get the value in the spin_button.
     *
     * @return The value of spin_button.
     */
    public double getValue() {
        return library.gtk_spin_button_get_value(getCReference());
    }

    /**
     * Sets the value of spin_button.
     *
     * @param value The new value
     */
    public void setValue(double value) {
        library.gtk_spin_button_set_value(getCReference(), value);
    }

    /**
     * Returns whether non-numeric text can be typed into the spin button.
     *
     * @return TRUE if only numeric text can be entered.
     */
    public boolean isNumericOnly() {
        return library.gtk_spin_button_get_numeric(getCReference());
    }

    /**
     * Sets the flag that determines if non-numeric text can be typed into the spin button.
     *
     * @param isNumericOnly TRUE if only numeric entry is allowed.
     */
    public void setNumericOnly(boolean isNumericOnly) {
        library.gtk_spin_button_set_numeric(getCReference(), isNumericOnly);
    }

    /**
     * Changes the properties of an existing spin button.
     * <p>
     * The adjustment, climb rate, and number of decimal places are updated accordingly.
     *
     * @param adjustment A GtkAdjustment to replace the spin button's existing adjustment,
     *                   or NULL to leave its current adjustment unchanged.
     *                   <p>
     *                   The argument can be NULL.
     * @param climbRate  Specifies by how much the rate of change in the value will accelerate if you continue to hold
     *                   down an up/down button or arrow key.
     * @param sigDig     The number of decimal places to display.
     */
    public void reset(GtkAdjustment adjustment, double climbRate, int sigDig) {
        sigDig = Math.min(Math.max(0, sigDig), 20);
        library.gtk_spin_button_configure(getCReference(), pointerOrNull(adjustment), climbRate, sigDig);

    }

    /**
     * Sets the step and page increments for spin_button.
     * <p>
     * This affects how quickly the value changes when the spin button's arrows are activated.
     *
     * @param step Increment applied for a button 1 press.
     * @param page Increment applied for a button 2 press.
     */
    public void setIncrements(double step, double page) {
        library.gtk_spin_button_set_increments(getCReference(), step, page);
    }

    /**
     * Sets the minimum and maximum allowable values for spin_button.
     * <p>
     * If the current value is outside this range, it will be adjusted to fit within the range, otherwise it will remain
     * unchanged.
     *
     * @param min Minimum allowable value.
     * @param max Maximum allowable value.
     */
    public void setRange(double min, double max) {
        min = Math.min(min, max);
        max = Math.max(max, min);
        library.gtk_spin_button_set_range(getCReference(), min, max);
    }

    /**
     * Sets the policy whether values are corrected to the nearest step increment when a spin button is activated
     * after providing an invalid value.
     *
     * @param doesSnap TRUE to snap values to the nearest step
     */
    public void shouldSnapToNearestStep(boolean doesSnap) {
        library.gtk_spin_button_set_snap_to_ticks(getCReference(), doesSnap);
    }

    /**
     * Sets the flag that determines if a spin button value wraps around to the opposite limit when the upper or lower
     * limit of the range is exceeded.
     *
     * @param doesWrap TRUE if wrapping behavior is performed.
     */
    public void shouldWrap(boolean doesWrap) {
        library.gtk_spin_button_set_wrap(getCReference(), doesWrap);
    }

    /**
     * Increment or decrement a spin button's value in a specified direction by a specified amount.
     *
     * @param direction A GtkSpinType indicating the direction to spin.
     * @param increment Step increment to apply in the specified direction.
     */
    public void spin(GtkSpinType direction, double increment) {
        library.gtk_spin_button_spin(getCReference(), GtkSpinType.getCValueFromType(direction), increment);
    }

    public static final class Signals extends GtkWidget.Signals {

        /**
         * Emitted when the user initiates a value change.
         */
        public static final Signals CHANGE_VALUE = new Signals("change-value");

        /**
         * Emitted to convert the users input into a double value.
         */
        public static final Signals INPUT = new Signals("input");

        /**
         * Emitted to tweak the formatting of the value for display.
         */
        public static final Signals OUTPUT = new Signals("output");

        /**
         * Emitted when the value is changed.
         */
        public static final Signals VALUE_CHANGED = new Signals("value-changed");

        /**
         * Emitted right after the spin button wraps from its maximum to its minimum value or vice-versa.
         */
        public static final Signals WRAPPED = new Signals("wrapped");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkSpinButtonLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Changes the properties of an existing spin button.
         * <p>
         * The adjustment, climb rate, and number of decimal places are updated accordingly.
         *
         * @param spin_button self
         * @param adjustment  A GtkAdjustment to replace the spin button's existing adjustment,
         *                    or NULL to leave its current adjustment unchanged. Type: GtkAdjustment
         *                    <p>
         *                    The argument can be NULL.
         * @param climb_rate  The new climb rate.
         * @param digits      The number of decimal places to display in the spin button.
         */
        public native void gtk_spin_button_configure(Pointer spin_button, Pointer adjustment, double climb_rate, int digits);

        /**
         * Get the adjustment associated with a GtkSpinButton
         *
         * @param spin_button self
         * @return The GtkAdjustment of spin_button.
         */
        public native Pointer gtk_spin_button_get_adjustment(Pointer spin_button);

        /**
         * Returns the acceleration rate for repeated changes.
         *
         * @param spin_button self
         * @return The acceleration rate.
         */
        public native double gtk_spin_button_get_climb_rate(Pointer spin_button);

        /**
         * Fetches the precision of spin_button.
         *
         * @param spin_button self
         * @return The current precision.
         */
        public native int gtk_spin_button_get_digits(Pointer spin_button);

        /**
         * Gets the current step and page the increments used by spin_button.
         * <p>
         * See gtk_spin_button_set_increments().
         *
         * @param spin_button self
         * @param step        Location to store step increment.
         *                    <p>
         *                    The argument can be NULL.
         * @param page        Location to store page increment.
         *                    <p>
         *                    The argument can be NULL.
         */
        public native void gtk_spin_button_get_increments(Pointer spin_button, PointerByReference step, PointerByReference page);

        /**
         * Returns whether non-numeric text can be typed into the spin button.
         *
         * @param spin_button self
         * @return TRUE if only numeric text can be entered.
         */
        public native boolean gtk_spin_button_get_numeric(Pointer spin_button);

        /**
         * Gets the range allowed for spin_button.
         *
         * @param spin_button self
         * @param min         Location to store minimum allowed value.
         *                    <p>
         *                    The argument can be NULL.
         * @param max         Location to store maximum allowed value.
         *                    <p>
         *                    The argument can be NULL.
         */
        public native void gtk_spin_button_get_range(Pointer spin_button, PointerByReference min, PointerByReference max);

        /**
         * Returns whether the values are corrected to the nearest step.
         *
         * @param spin_button self
         * @return TRUE if values are snapped to the nearest step.
         */
        public native boolean gtk_spin_button_get_snap_to_ticks(Pointer spin_button);

        /**
         * Gets the update behavior of a spin button.
         * <p>
         * See gtk_spin_button_set_update_policy().
         *
         * @param spin_button self
         * @return The current update policy. Type: GtkSpinButtonUpdatePolicy
         */
        public native int gtk_spin_button_get_update_policy(Pointer spin_button);

        /**
         * Get the value in the spin_button.
         *
         * @param spin_button self
         * @return The value of spin_button.
         */
        public native double gtk_spin_button_get_value(Pointer spin_button);

        /**
         * Returns whether the spin button's value wraps around to the opposite limit when the upper or lower limit of
         * the range is exceeded.
         *
         * @param spin_button self
         * @return TRUE if the spin button wraps around.
         */
        public native boolean gtk_spin_button_get_wrap(Pointer spin_button);

        /**
         * Creates a new GtkSpinButton.
         *
         * @param adjustment The GtkAdjustment that this spin button should use. Type: GtkAdjustment
         *                   <p>
         *                   The argument can be NULL.
         * @param climb_rate Specifies by how much the rate of change in the value will accelerate if you continue to
         *                   hold down an up/down button or arrow key.
         * @param digits     The number of decimal places to display.
         * @return The new GtkSpinButton
         */
        public native Pointer gtk_spin_button_new(Pointer adjustment, double climb_rate, int digits);

        /**
         * Creates a new GtkSpinButton with the given properties.
         * <p>
         * This is a convenience constructor that allows creation of a numeric GtkSpinButton without manually creating
         * an adjustment. The value is initially set to the minimum value and a page increment of 10 * step is the
         * default. The precision of the spin button is equivalent to the precision of step.
         * <p>
         * Note that the way in which the precision is derived works best if step is a power of ten. If the resulting
         * precision is not suitable for your needs, use gtk_spin_button_set_digits() to correct it.
         *
         * @param min  Minimum allowable value.
         * @param max  Maximum allowable value.
         * @param step Increment added or subtracted by spinning the widget.
         * @return The new GtkSpinButton
         */
        public native Pointer gtk_spin_button_new_with_range(double min, double max, double step);

        /**
         * Replaces the GtkAdjustment associated with spin_button.
         *
         * @param spin_button self
         * @param adjustment  A GtkAdjustment to replace the existing adjustment. Type: GtkAdjustment
         */
        public native void gtk_spin_button_set_adjustment(Pointer spin_button, Pointer adjustment);

        /**
         * Sets the acceleration rate for repeated changes when you hold down a button or key.
         *
         * @param spin_button self
         * @param climb_rate  The rate of acceleration, must be >= 0
         */
        public native void gtk_spin_button_set_climb_rate(Pointer spin_button, double climb_rate);

        /**
         * Set the precision to be displayed by spin_button.
         * <p>
         * Up to 20 digit precision is allowed.
         *
         * @param spin_button self
         * @param digits      The number of digits after the decimal point to be displayed for the spin button's value.
         */
        public native void gtk_spin_button_set_digits(Pointer spin_button, int digits);

        /**
         * Sets the step and page increments for spin_button.
         * <p>
         * This affects how quickly the value changes when the spin button's arrows are activated.
         *
         * @param spin_button self
         * @param step        Increment applied for a button 1 press.
         * @param page        Increment applied for a button 2 press.
         */
        public native void gtk_spin_button_set_increments(Pointer spin_button, double step, double page);

        /**
         * Sets the flag that determines if non-numeric text can be typed into the spin button.
         *
         * @param spin_button self
         * @param numeric     Flag indicating if only numeric entry is allowed.
         */
        public native void gtk_spin_button_set_numeric(Pointer spin_button, boolean numeric);

        /**
         * Sets the minimum and maximum allowable values for spin_button.
         * <p>
         * If the current value is outside this range, it will be adjusted to fit within the range, otherwise it will
         * remain unchanged
         *
         * @param spin_button self
         * @param min         Minimum allowable value.
         * @param max         Maximum allowable value.
         */
        public native void gtk_spin_button_set_range(Pointer spin_button, double min, double max);

        /**
         * Sets the policy whether values are corrected to the nearest step increment when a spin button is
         * activated after providing an invalid value.
         *
         * @param spin_button   self
         * @param snap_to_ticks A flag indicating if invalid values should be corrected.
         */
        public native void gtk_spin_button_set_snap_to_ticks(Pointer spin_button, boolean snap_to_ticks);

        /**
         * Sets the update behavior of a spin button.
         * <p>
         * This determines whether the spin button is always updated or only when a valid value is set.
         *
         * @param spin_button self
         * @param policy      A GtkSpinButtonUpdatePolicy value. Type: GtkSpinButtonUpdatePolicy
         */
        public native void gtk_spin_button_set_update_policy(Pointer spin_button, int policy);

        /**
         * Sets the value of spin_button.
         *
         * @param spin_button self
         * @param value       The new value.
         */
        public native void gtk_spin_button_set_value(Pointer spin_button, double value);

        /**
         * Sets the flag that determines if a spin button value wraps around to the opposite limit when the upper or
         * lower limit of the range is exceeded.
         *
         * @param spin_button self
         * @param wrap        A flag indicating if wrapping behavior is performed.
         */
        public native void gtk_spin_button_set_wrap(Pointer spin_button, boolean wrap);

        /**
         * Increment or decrement a spin button's value in a specified direction by a specified amount.
         *
         * @param spin_button self
         * @param direction   A GtkSpinType indicating the direction to spin. Type: GtkSpinType
         * @param increment   Step increment to apply in the specified direction.
         */
        public native void gtk_spin_button_spin(Pointer spin_button, int direction, double increment);

        /**
         * Manually force an update of the spin button.
         *
         * @param spin_button self
         */
        public native void gtk_spin_button_update(Pointer spin_button);
    }

}
