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
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkActionable;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkSwitch is a "light switch" that has two states: on or off.
 * <p>
 * The user can control which state should be active by clicking the empty area, or by dragging the handle.
 * <p>
 * GtkSwitch can also handle situations where the underlying state changes with a delay. In this case, the slider
 * position indicates the user's recent change (as indicated by the GtkSwitch:active property), and the color
 * indicates whether the underlying state (represented by the GtkSwitch:state property) has been updated yet.
 * <p>
 * See GtkSwitch::state-set for details.
 */
public class GtkSwitch extends GtkWidget implements GtkAccessible, GtkActionable, GtkBuildable, GtkConstraintTarget {

    private static final GtkSwitchLibrary library = new GtkSwitchLibrary();

    /**
     * Creates a new GtkSwitch widget.
     */
    public GtkSwitch() {
        this(library.gtk_switch_new());
    }

    public GtkSwitch(Pointer ref) {
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
     * Gets whether the GtkSwitch is in its "on" state
     *
     * @return TRUE if the GtkSwitch is active, and FALSE otherwise.
     */
    public boolean isOn() {
        return library.gtk_switch_get_active(getCReference());
    }

    /**
     * Sets the switch to 'off'
     */
    public void turnOff() {
        library.gtk_switch_set_active(getCReference(), false);
    }

    /**
     * sets the switch to 'on'
     */
    public void turnOn() {
        library.gtk_switch_set_active(getCReference(), true);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted to animate the switch.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        /**
         * Emitted to change the underlying state.
         */
        public static final Signals STATE_SET = new Signals("state-set");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkSwitchLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets whether the GtkSwitch is in its "on" or "off" state.
         *
         * @param self self
         * @return TRUE if the GtkSwitch is active, and FALSE otherwise.
         */
        public native boolean gtk_switch_get_active(Pointer self);

        /**
         * Creates a new GtkSwitch widget.
         *
         * @return The newly created GtkSwitch instance.
         */
        public native Pointer gtk_switch_new();

        /**
         * Changes the state of self to the desired one.
         *
         * @param self      self
         * @param is_active TRUE if self should be active, and FALSE otherwise.
         */
        public native void gtk_switch_set_active(Pointer self, boolean is_active);
    }
}
