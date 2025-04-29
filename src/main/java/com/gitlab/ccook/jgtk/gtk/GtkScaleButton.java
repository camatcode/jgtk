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
import com.gitlab.ccook.jgtk.IconName;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkScaleButton provides a button which pops up a scale widget.
 * <p>
 * This kind of widget is commonly used for volume controls in multimedia applications, and GTK provides a
 * GtkVolumeButton subclass that is tailored for this use case.
 */
public class GtkScaleButton extends GtkWidget implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkScaleButtonLibrary library = new GtkScaleButtonLibrary();

    public GtkScaleButton(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a GtkScaleButton.
     * <p>
     * The new scale button has a range between min and max, with a stepping of step.
     *
     * @param min   The minimum value of the scale (usually 0)
     * @param max   The maximum value of the scale (usually 100)
     * @param step  The stepping of value when a scroll-wheel event, or up/down arrow event occurs (usually 2)
     * @param icons The names of the icons to be used by the scale button.
     *              <p>
     *              The first item in the array will be used in the button when the current value is the lowest value,
     *              the second item for the highest value. All the subsequent icons will be used for all the other
     *              values, spread evenly over the range of values.
     *              <p>
     *              If there's only one icon name in the icons array, it will be used for all the values.
     *              If only two icon names are in the icons array, the first one will be used for the bottom
     *              50% of the scale, and the second one for the top 50%.
     *              <p>
     *              It is recommended to use at least 3 icons so that the GtkScaleButton reflects the current value
     *              of the scale better for the users.
     */
    public GtkScaleButton(double min, double max, double step, String... icons) {
        super(library.gtk_scale_button_new(min, max, step, icons));
    }

    /**
     * Creates a GtkScaleButton.
     * <p>
     * The new scale button has a range between min and max, with a stepping of step.
     *
     * @param min   The minimum value of the scale (usually 0)
     * @param max   The maximum value of the scale (usually 100)
     * @param step  The stepping of value when a scroll-wheel event, or up/down arrow event occurs (usually 2)
     * @param icons The names of the icons to be used by the scale button.
     *              <p>
     *              The first item in the array will be used in the button when the current value is the lowest value,
     *              the second item for the highest value. All the subsequent icons will be used for all the other
     *              values, spread evenly over the range of values.
     *              <p>
     *              If there's only one icon name in the icons array, it will be used for all the values.
     *              If only two icon names are in the icons array, the first one will be used for the bottom
     *              50% of the scale, and the second one for the top 50%.
     *              <p>
     *              It is recommended to use at least 3 icons so that the GtkScaleButton reflects the current value
     *              of the scale better for the users.
     */
    public GtkScaleButton(double min, double max, double step, IconName... icons) {
        super(handleCtor(min, max, step, icons));
    }

    private static Pointer handleCtor(double min, double max, double step, IconName[] iconNames) {
        String[] icons = new String[iconNames.length];
        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconNames[i].getIconName();
        }
        return library.gtk_scale_button_new(min, max, step, icons);
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
     * Gets the GtkAdjustment associated with the GtkScaleButton's scale.
     * <p>
     * See gtk_range_get_adjustment() for details.
     *
     * @return The adjustment associated with the scale.
     */
    public GtkAdjustment getAdjustment() {
        return new GtkAdjustment(library.gtk_scale_button_get_adjustment(getCReference()));
    }

    /**
     * Sets the GtkAdjustment to be used as a model for the GtkScaleButton's scale.
     * <p>
     * See gtk_range_set_adjustment() for details.
     *
     * @param adjustment A GtkAdjustment
     */
    public void setAdjustment(GtkAdjustment adjustment) {
        if (adjustment != null) {
            library.gtk_scale_button_set_adjustment(getCReference(), adjustment.getCReference());
        }
    }

    /**
     * Retrieves the minus button of the GtkScaleButton.
     *
     * @return The minus button of the GtkScaleButton
     */
    public GtkButton getMinusButton() {
        return (GtkButton) JGTKObject.newObjectFromType(library.gtk_scale_button_get_minus_button(getCReference()), GtkButton.class);
    }

    /**
     * Retrieves the plus button of the GtkScaleButton.
     *
     * @return The plus button of the GtkScaleButton
     */
    public GtkButton getPlusButton() {
        return (GtkButton) JGTKObject.newObjectFromType(library.gtk_scale_button_get_plus_button(getCReference()), GtkButton.class);
    }

    /**
     * Retrieves the popup of the GtkScaleButton.
     *
     * @return The popup of the GtkScaleButton
     */
    public GtkWidget getPopup() {
        return (GtkWidget) JGTKObject.newObjectFromType(library.gtk_scale_button_get_popup(getCReference()), GtkWidget.class);
    }

    /**
     * Gets the current value of the scale button.
     *
     * @return Current value of the scale button.
     */
    public double getValue() {
        return library.gtk_scale_button_get_value(getCReference());
    }

    /**
     * Sets the current value of the scale.
     * <p>
     * If the value is outside the minimum or maximum range values, it will be clamped to fit inside them.
     * <p>
     * The scale button emits the GtkScaleButton::value-changed signal if the value changes.
     *
     * @param value New value of the scale button.
     */
    public void setValue(double value) {
        library.gtk_scale_button_set_value(getCReference(), value);
    }

    /**
     * Sets the icons to be used by the scale button.
     *
     * @param icons The names of the icons to be used by the scale button.
     *              <p>
     *              The first item in the array will be used in the button when the current value is the lowest value,
     *              the second item for the highest value. All the subsequent icons will be used for all the other
     *              values, spread evenly over the range of values.
     *              <p>
     *              If there's only one icon name in the icons array, it will be used for all the values.
     *              If only two icon names are in the icons array, the first one will be used for the bottom 50% of the
     *              scale, and the second one for the top 50%.
     *              <p>
     *              It is recommended to use at least 3 icons so that the GtkScaleButton reflects the current value of
     *              the scale better for the users.
     */
    public void setIcons(String... icons) {
        library.gtk_scale_button_set_icons(getCReference(), icons);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted to dismiss the popup.
         */
        public static final Signals POP_DOWN = new Signals("popdown");

        /**
         * Emitted to pop up the scale widget.
         */
        public static final Signals POP_UP = new Signals("popup");

        /**
         * Emitted when the value field has changed.
         */
        public static final Signals VALUE_CHANGED = new Signals("value-changed");

        private Signals(String cValue) {
            super(cValue);
        }
    }

    protected static class GtkScaleButtonLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the GtkAdjustment associated with the GtkScaleButton's scale.
         * <p>
         * See gtk_range_get_adjustment() for details.
         *
         * @param button self
         * @return The adjustment associated with the scale. Type: GtkAdjustment
         */
        public native Pointer gtk_scale_button_get_adjustment(Pointer button);

        /**
         * Retrieves the minus button of the GtkScaleButton.
         *
         * @param button self
         * @return The minus button of the GtkScaleButton. Type: GtkButton
         */
        public native Pointer gtk_scale_button_get_minus_button(Pointer button);

        /**
         * Retrieves the plus button of the GtkScaleButton.
         *
         * @param button self
         * @return The plus button of the GtkScaleButton. Type: GtkButton
         */
        public native Pointer gtk_scale_button_get_plus_button(Pointer button);

        /**
         * Retrieves the popup of the GtkScaleButton.
         *
         * @param button self
         * @return The popup of the GtkScaleButton. Type: GtkWidget
         */
        public native Pointer gtk_scale_button_get_popup(Pointer button);

        /**
         * Gets the current value of the scale button.
         *
         * @param button self
         * @return Current value of the scale button.
         */
        public native double gtk_scale_button_get_value(Pointer button);

        /**
         * Creates a GtkScaleButton.
         * <p>
         * The new scale button has a range between min and max, with a stepping of step.
         *
         * @param min   The minimum value of the scale (usually 0)
         * @param max   The maximum value of the scale (usually 100)
         * @param step  The stepping of value when a scroll-wheel event, or up/down arrow event occurs (usually 2)
         * @param icons The names of the icons to be used by the scale button.
         *              <p>
         *              The first item in the array will be used in the button when the current value is the lowest
         *              value,
         *              the second item for the highest value. All the subsequent icons will be used for all the other
         *              values, spread evenly over the range of values.
         *              <p>
         *              If there's only one icon name in the icons array, it will be used for all the values.
         *              If only two icon names are in the icons array, the first one will be used for the bottom
         *              50% of the scale, and the second one for the top 50%.
         *              <p>
         *              It is recommended to use at least 3 icons so that the GtkScaleButton reflects the current value
         *              of the scale better for the users.
         * @return A new GtkScaleButton
         */
        public Pointer gtk_scale_button_new(double min, double max, double step, String... icons) {
            return INSTANCE.gtk_scale_button_new(min, max, step, icons);
        }

        /**
         * Sets the GtkAdjustment to be used as a model for the GtkScaleButton's scale.
         * <p>
         * See gtk_range_set_adjustment() for details.
         *
         * @param button     self
         * @param adjustment A GtkAdjustment
         */
        public native void gtk_scale_button_set_adjustment(Pointer button, Pointer adjustment);

        /**
         * Sets the icons to be used by the scale button.
         *
         * @param button self
         * @param icons  The names of the icons to be used by the scale button.
         *               <p>
         *               The first item in the array will be used in the button when the current value is the lowest
         *               value,
         *               the second item for the highest value. All the subsequent icons will be used for all the other
         *               values, spread evenly over the range of values.
         *               <p>
         *               If there's only one icon name in the icons array, it will be used for all the values.
         *               If only two icon names are in the icons array, the first one will be used for the bottom
         *               50% of the scale, and the second one for the top 50%.
         *               <p>
         *               It is recommended to use at least 3 icons so that the GtkScaleButton reflects the current value
         *               of the scale better for the users.
         */
        public void gtk_scale_button_set_icons(Pointer button, String... icons) {
            INSTANCE.gtk_scale_button_set_icons(button, icons);
        }

        /**
         * Sets the current value of the scale.
         * <p>
         * If the value is outside the minimum or maximum range values, it will be clamped to fit inside them.
         * <p>
         * The scale button emits the GtkScaleButton::value-changed signal if the value changes.
         *
         * @param button self
         * @param value  New value of the scale button.
         */
        public native void gtk_scale_button_set_value(Pointer button, double value);
    }
}
