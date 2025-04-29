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
package com.gitlab.ccook.jgtk;

import com.sun.jna.Pointer;

public class GtkAdjustment extends JGTKObject {
    public GtkAdjustment(Pointer cReference) {
        super(cReference);
    }

    public GtkAdjustment(double value, double lower, double upper, double stepIncrement, double pageIncrement, double pageSize) {
        super(handleCtor(value, lower, upper, stepIncrement, pageIncrement, pageSize));
    }

    private static Pointer handleCtor(double value, double lower, double upper, double stepIncrement, double pageIncrement, double pageSize) {
        return library.gtk_adjustment_new(value, lower, upper, stepIncrement, pageIncrement, pageSize);
    }

    /**
     * Updates the value property to ensure that the range between lower and upper is in the current page.
     * <p>
     * The current page goes from value to value + page-size. If the range is larger than the page size, then only the
     * start of it will be in the current page.
     * <p>
     * A GtkAdjustment::value-changed signal will be emitted if the value is changed.
     *
     * @param lower The lower value.
     * @param upper The upper value.
     */
    public void clampPage(double lower, double upper) {
        library.gtk_adjustment_clamp_page(getCReference(), lower, upper);
    }

    /**
     * Sets all properties of the adjustment at once.
     * <p>
     * Use this function to avoid multiple emissions of the GtkAdjustment::changed signal. See
     * gtk_adjustment_set_lower() for an alternative way of compressing multiple emissions of GtkAdjustment::changed
     * into one.
     *
     * @param value         The new value.
     * @param lower         The new minimum value.
     * @param upper         The new maximum value.
     * @param stepIncrement The new step increment.
     * @param pageIncrement The new page increment.
     * @param pageSize      The new page size.
     */
    public void configure(double value, double lower, double upper, double stepIncrement, double pageIncrement, double pageSize) {
        library.gtk_adjustment_configure(getCReference(), value, lower, upper, stepIncrement, pageIncrement, pageSize);
    }

    /**
     * Retrieves the minimum value of the adjustment.
     *
     * @return The current minimum value of the adjustment.
     */
    public double getLower() {
        return library.gtk_adjustment_get_lower(getCReference());
    }

    /**
     * Sets the minimum value of the adjustment.
     * <p>
     * When setting multiple adjustment properties via their individual setters, multiple GtkAdjustment::changed signals
     * will be emitted. However, since the emission of the GtkAdjustment::changed signal is tied to the emission of the
     * ::notify signals of the changed properties, it's possible to compress the GtkAdjustment::changed signals into
     * one by calling g_object_freeze_notify() and g_object_thaw_notify() around the calls to the individual setters.
     * <p>
     * Alternatively, using a single g_object_set() for all the properties to change, or using
     * gtk_adjustment_configure() has the same effect.
     *
     * @param lower The new minimum value.
     */
    public void setLower(double lower) {
        library.gtk_adjustment_set_lower(getCReference(), lower);
    }

    /**
     * Gets the minimum of step increment and page increment; which ever is smallest.
     *
     * @return The minimum increment of adjustment.
     */
    public double getMinimumIncrement() {
        return library.gtk_adjustment_get_minimum_increment(getCReference());
    }

    /**
     * Retrieves the page increment of the adjustment.
     *
     * @return The current page increment of the adjustment.
     */
    public double getPageIncrement() {
        return library.gtk_adjustment_get_page_increment(getCReference());
    }

    /**
     * Sets the page increment of the adjustment.
     * <p>
     * See gtk_adjustment_set_lower() about how to compress multiple emissions of the GtkAdjustment::changed signal
     * when setting multiple adjustment properties.
     *
     * @param pageIncrement The new page increment.
     */
    public void setPageIncrement(double pageIncrement) {
        library.gtk_adjustment_set_page_increment(getCReference(), pageIncrement);
    }

    /**
     * Retrieves the page size of the adjustment.
     *
     * @return The current page size of the adjustment.
     */
    public double getPageSize() {
        return library.gtk_adjustment_get_page_size(getCReference());
    }

    /**
     * Sets the page size of the adjustment.
     * <p>
     * See gtk_adjustment_set_lower() about how to compress multiple emissions of the GtkAdjustment::changed signal
     * when setting multiple adjustment properties.
     *
     * @param pageSize The new page size.
     */
    public void setPageSize(double pageSize) {
        library.gtk_adjustment_set_page_size(getCReference(), pageSize);
    }

    /**
     * Retrieves the step increment of the adjustment.
     *
     * @return The current step increment of the adjustment.
     */
    public double getStepIncrement() {
        return library.gtk_adjustment_get_step_increment(getCReference());
    }

    /**
     * Sets the step increment of the adjustment.
     * <p>
     * See gtk_adjustment_set_lower() about how to compress multiple emissions of the GtkAdjustment::changed signal
     * when setting multiple adjustment properties.
     *
     * @param stepIncrement The new step increment.
     */
    public void setStepIncrement(double stepIncrement) {
        library.gtk_adjustment_set_step_increment(getCReference(), stepIncrement);
    }

    /**
     * Retrieves the maximum value of the adjustment.
     *
     * @return The current maximum value of the adjustment.
     */
    public double getUpper() {
        return library.gtk_adjustment_get_upper(getCReference());
    }

    /**
     * Sets the maximum value of the adjustment.
     * <p>
     * Note that values will be restricted by upper - page-size if the page-size property is nonzero.
     * <p>
     * See gtk_adjustment_set_lower() about how to compress multiple emissions of the GtkAdjustment::changed signal
     * when setting multiple adjustment properties.
     *
     * @param upper The new maximum value.
     */
    public void setUpper(double upper) {
        library.gtk_adjustment_set_upper(getCReference(), upper);
    }

    /**
     * Gets the current value of the adjustment.
     *
     * @return The current value of the adjustment.
     */
    public double getValue() {
        return library.gtk_adjustment_get_value(getCReference());
    }

    /**
     * Sets the GtkAdjustment value.
     * <p>
     * The value is clamped to lie between GtkAdjustment:lower and GtkAdjustment:upper.
     * <p>
     * Note that for adjustments which are used in a GtkScrollbar, the effective range of allowed values goes from
     * GtkAdjustment:lower to GtkAdjustment:upper - GtkAdjustment:page-size.
     *
     * @param value The new value.
     */
    public void setValue(double value) {
        library.gtk_adjustment_set_value(getCReference(), value);
    }

}
