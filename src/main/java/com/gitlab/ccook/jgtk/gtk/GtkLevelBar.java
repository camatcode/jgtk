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
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkLevelBarMode;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * GtkLevelBar is a widget that can be used as a level indicator.
 * <p>
 * Typical use cases are displaying the strength of a password, or showing the charge level of a battery.
 * <p>
 * Use gtk_level_bar_set_value() to set the current value, and gtk_level_bar_add_offset_value() to set the value
 * offsets at which the bar will be considered in a different state. GTK will add a few offsets by default on the level
 * bar: GTK_LEVEL_BAR_OFFSET_LOW, GTK_LEVEL_BAR_OFFSET_HIGH and GTK_LEVEL_BAR_OFFSET_FULL, with values 0.25, 0.75
 * and 1.0 respectively.
 * <p>
 * Note that it is your responsibility to update preexisting offsets when changing the minimum or maximum value.
 * GTK will simply clamp them to the new range.
 */
@SuppressWarnings("unchecked")
public class GtkLevelBar extends GtkWidget implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkLevelBarLibrary library = new GtkLevelBarLibrary();

    public GtkLevelBar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkLevelBar.
     */
    public GtkLevelBar() {
        super(library.gtk_level_bar_new());
    }

    /**
     * Creates a new GtkLevelBar for the specified interval.
     *
     * @param minValue A positive value.
     * @param maxValue A positive value.
     */
    public GtkLevelBar(double minValue, double maxValue) {
        super(library.gtk_level_bar_new_for_interval(Math.max(0.0d, minValue), Math.min(1.0d, maxValue)));
    }

    /**
     * Adds a new offset marker on self at the position specified by value.
     * <p>
     * When the bar value is in the interval topped by value (or between value and GtkLevelBar:max-value in case the
     * offset is the last one on the bar) a style class named level-[name] will be applied when rendering the level bar
     * fill.
     * <p>
     * If another offset marker named name exists, its value will be replaced by value.
     *
     * @param name  The name of the new offset.
     * @param value The value for the new offset.
     */
    public void addOffsetValue(String name, double value) {
        if (name != null) {
            library.gtk_level_bar_add_offset_value(getCReference(), name, value);
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
     * Returns the max-value of the GtkLevelBar.
     *
     * @return A positive value
     */
    public double getMaxValue() {
        return library.gtk_level_bar_get_max_value(getCReference());
    }

    /**
     * Sets the max-value of the GtkLevelBar.
     * <p>
     * You probably want to update preexisting level offsets after calling this function.
     *
     * @param max A positive value.
     */
    public void setMaxValue(double max) {
        library.gtk_level_bar_set_max_value(getCReference(), Math.max(max, 0));
    }

    /**
     * Returns the min-value of the GtkLevelBar.
     *
     * @return A positive value.
     */
    public double getMinValue() {
        return library.gtk_level_bar_get_min_value(getCReference());
    }

    /**
     * Sets the min-value of the GtkLevelBar.
     * <p>
     * You probably want to update preexisting level offsets after calling this function.
     *
     * @param min A positive value.
     */
    public void setMinValue(double min) {
        library.gtk_level_bar_set_min_value(getCReference(), Math.max(min, 0));
    }

    /**
     * Returns the mode of the GtkLevelBar.
     *
     * @return A GtkLevelBarMode
     */
    public GtkLevelBarMode getMode() {
        return GtkLevelBarMode.getModeFromCValue(library.gtk_level_bar_get_mode(getCReference()));
    }

    /**
     * Sets the mode of the GtkLevelBar.
     *
     * @param m a GtkLevelBarMode
     */
    public void setMode(GtkLevelBarMode m) {
        if (m != null) {
            library.gtk_level_bar_set_mode(getCReference(), GtkLevelBarMode.getCValueFromMode(m));
        }
    }

    /**
     * Fetches the value specified for the offset marker name in self.
     *
     * @param name The name of an offset in the bar.
     * @return the value specified for the offset marker name, if defined
     */
    public Option<Double> getOffsetValue(String name) {
        if (name != null) {
            PointerByReference value = new PointerByReference();
            boolean didFind = library.gtk_level_bar_get_offset_value(getCReference(), name, value);
            if (didFind) {
                return new Option<>(value.getPointer().getDouble(0));
            }
        }
        return Option.NONE;
    }

    /**
     * Returns the value of the GtkLevelBar.
     *
     * @return A value in the interval between Gtk.LevelBar:min-value and GtkLevelBar:max-value
     */
    public double getValue() {
        return library.gtk_level_bar_get_value(getCReference());
    }

    /**
     * Sets the value of the GtkLevelBar.
     *
     * @param v A value in the interval between GtkLevelBar:min-value and GtkLevelBar:max-value
     */
    public void setValue(double v) {
        library.gtk_level_bar_set_value(getCReference(), Math.max(v, 0));
    }

    /**
     * Returns whether the level bar is inverted.
     *
     * @return TRUE if the level bar is inverted.
     */
    public boolean isInverted() {
        return library.gtk_level_bar_get_inverted(getCReference());
    }

    /**
     * Sets whether the GtkLevelBar is inverted.
     *
     * @param isInverted TRUE to invert the level bar.
     */
    public void setInverted(boolean isInverted) {
        library.gtk_level_bar_set_inverted(getCReference(), isInverted);
    }

    /**
     * Removes an offset marker from a GtkLevelBar.
     * <p>
     * The marker must have been previously added with gtk_level_bar_add_offset_value().
     *
     * @param name The name of an offset in the bar to remove.
     */
    public void removeOffsetValue(String name) {
        if (name != null) {
            library.gtk_level_bar_remove_offset_value(getCReference(), name);
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted when an offset specified on the bar changes value.
         * <p>
         * This typically is the result of a gtk_level_bar_add_offset_value() call.
         * <p>
         * The signal supports detailed connections; you can connect to the detailed signal "changed::x" in order
         * to only receive callbacks when the value of offset "x" changes.
         */
        public static final Signals OFFSET_CHANGED = new Signals("offset-changed");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkLevelBarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a new offset marker on self at the position specified by value.
         * <p>
         * When the bar value is in the interval topped by value (or between value and GtkLevelBar:max-value in case
         * the offset is the last one on the bar) a style class named level-``name will be applied when rendering the
         * level bar fill.
         * <p>
         * If another offset marker named name exists, its value will be replaced by value.
         *
         * @param self  self
         * @param name  The name of the new offset.
         * @param value The value for the new offset.
         */
        public native void gtk_level_bar_add_offset_value(Pointer self, String name, double value);

        /**
         * Returns whether the level-bar is inverted.
         *
         * @param self self
         * @return TRUE if the level bar is inverted.
         */
        public native boolean gtk_level_bar_get_inverted(Pointer self);

        /**
         * Returns the max-value of the GtkLevelBar.
         *
         * @param self self
         * @return A positive value.
         */
        public native double gtk_level_bar_get_max_value(Pointer self);

        /**
         * Returns the min-value of the GtkLevelBar.
         *
         * @param self self
         * @return A positive value.
         */
        public native double gtk_level_bar_get_min_value(Pointer self);

        /**
         * Returns the mode of the GtkLevelBar.
         *
         * @param self self
         * @return A GtkLevelBarMode. Type: GtkLevelBarMode
         */
        public native int gtk_level_bar_get_mode(Pointer self);

        /**
         * Fetches the value specified for the offset marker name in self.
         *
         * @param self  self
         * @param name  The name of an offset in the bar.
         *              <p>
         *              The argument can be NULL.
         * @param value Location where to store the value.
         * @return TRUE if the specified offset is found.
         */
        public native boolean gtk_level_bar_get_offset_value(Pointer self, String name, PointerByReference value);

        /**
         * Returns the value of the GtkLevelBar.
         *
         * @param self self
         * @return A value in the interval between GtkLevelBar:min-value and GtkLevelBar:max-value
         */
        public native double gtk_level_bar_get_value(Pointer self);

        /**
         * Creates a new GtkLevelBar.
         *
         * @return A GtkLevelBar. Type: GtkLevelBar
         */
        public native Pointer gtk_level_bar_new();

        /**
         * Creates a new GtkLevelBar for the specified interval.
         *
         * @param min_value A positive value.
         * @param max_value A positive value.
         * @return A GtkLevelBar. Type: GtkLevelBar
         */
        public native Pointer gtk_level_bar_new_for_interval(double min_value, double max_value);

        /**
         * Removes an offset marker from a GtkLevelBar.
         * <p>
         * The marker must have been previously added with gtk_level_bar_add_offset_value().
         *
         * @param self self
         * @param name The name of an offset in the bar.
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_level_bar_remove_offset_value(Pointer self, String name);

        /**
         * Sets whether the GtkLevelBar is inverted.
         *
         * @param self     self
         * @param inverted TRUE to invert the level bar.
         */
        public native void gtk_level_bar_set_inverted(Pointer self, boolean inverted);

        /**
         * Sets the max-value of the GtkLevelBar.
         * <p>
         * You probably want to update preexisting level offsets after calling this function.
         *
         * @param self  self
         * @param value A positive value.
         */
        public native void gtk_level_bar_set_max_value(Pointer self, double value);

        /**
         * Sets the min-value of the GtkLevelBar.
         * <p>
         * You probably want to update preexisting level offsets after calling this function.
         *
         * @param self  self
         * @param value A positive value.
         */
        public native void gtk_level_bar_set_min_value(Pointer self, double value);

        /**
         * Sets the mode of the GtkLevelBar.
         *
         * @param self self
         * @param mode A GtkLevelBarMode. Type: GtkLevelBarMode
         */
        public native void gtk_level_bar_set_mode(Pointer self, int mode);

        /**
         * Sets the value of the GtkLevelBar.
         *
         * @param self  self
         * @param value A value in the interval between GtkLevelBar:min-value and GtkLevelBar:max-value
         */
        public native void gtk_level_bar_set_value(Pointer self, double value);
    }
}
