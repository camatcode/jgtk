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
import com.gitlab.ccook.jgtk.MnemonicLabel;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkActionable;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.AssertionUtils;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * A GtkToggleButton is a button which remains "pressed-in" when clicked.
 * <p>
 * Clicking again will cause the toggle button to return to its normal state.
 * <p>
 * A toggle button is created by calling either gtk_toggle_button_new() or gtk_toggle_button_new_with_label().
 * If using the former, it is advisable to pack a widget, (such as a GtkLabel and/or a GtkImage), into the toggle
 * button's container. (See GtkButton for more information).
 * <p>
 * The state of a GtkToggleButton can be set specifically using gtk_toggle_button_set_active(),
 * and retrieved using gtk_toggle_button_get_active().
 * <p>
 * To simply switch the state of a toggle button, use gtk_toggle_button_toggled().
 */
public class GtkToggleButton extends GtkButton implements GtkAccessible, GtkActionable, GtkBuildable {

    private static final GtkToggleButtonLibrary library = new GtkToggleButtonLibrary();

    /**
     * Creates a new toggle button.
     * <p>
     * A widget should be packed into the button, as in gtk_button_new().
     */
    public GtkToggleButton() {
        super(library.gtk_toggle_button_new());
    }

    /**
     * Creates a new toggle button with a text label.
     * <p>
     * If the label contains '_', it's interpreted as mnemonic
     *
     * @param label A string containing the message to be placed in the toggle button.
     */
    public GtkToggleButton(String label) {
        super(handleCtr(label));
    }

    private static Pointer handleCtr(String label) {
        AssertionUtils.assertNotNull(GtkButton.class, "ctor: Label is null", label);
        if (label.contains("_")) {
            return library.gtk_toggle_button_new_with_mnemonic(label);
        } else {
            return library.gtk_toggle_button_new_with_label(label);
        }
    }

    /**
     * Creates a new GtkToggleButton containing a label.
     * <p>
     * The label will be created using gtk_label_new_with_mnemonic(), so underscores in label indicate the mnemonic
     * for the button.
     *
     * @param label The text of the button, with an underscore in front of the mnemonic character.
     */
    public GtkToggleButton(MnemonicLabel label) {
        super(handleCtr(label));
    }

    private static Pointer handleCtr(MnemonicLabel label) {
        AssertionUtils.assertNotNull(GtkButton.class, "ctor: Label is null", label);
        return handleCtr(label.getMnemonicLabel());
    }

    public GtkToggleButton(Pointer ref) {
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
     * Adds self to the group of group.
     * <p>
     * In a group of multiple toggle buttons, only one button can be active at a time.
     * <p>
     * Setting up groups in a cycle leads to undefined behavior.
     * <p>
     * Note that the same effect can be achieved via the GtkActionable API, by using the same action with parameter type
     * and state type 's' for all buttons in the group, and giving each button its own target value.
     *
     * @param group Another GtkToggleButton to form a group with.(This may be null)
     */
    public void setGroup(GtkToggleButton group) {
        if (group != null) {
            if (group.isPressedIn()) {
                this.setPressedIn(false);
            }
        }
        library.gtk_toggle_button_set_group(cReference, pointerOrNull(group));
    }

    /**
     * Queries a GtkToggleButton and returns its current state.
     * <p>
     * Returns TRUE if the toggle button is pressed in and FALSE if it is raised.
     *
     * @return Whether the button is pressed.
     */
    public boolean isPressedIn() {
        return library.gtk_toggle_button_get_active(cReference);
    }

    /**
     * Sets the status of the toggle button.
     * <p>
     * Set to TRUE if you want the GtkToggleButton to be "pressed in", and FALSE to raise it.
     * <p>
     * If the status of the button changes, this action causes the GtkToggleButton::toggled signal to be emitted.
     *
     * @param isPressedIn Whether the button is pressed.
     */
    public void setPressedIn(boolean isPressedIn) {
        library.gtk_toggle_button_set_active(cReference, isPressedIn);
    }

    public static class Signals extends GtkWidget.Signals {
        public static final Signals TOGGLED = new Signals("toggled");

        @SuppressWarnings("SameParameterValue")
        protected Signals(String cValue) {
            super(cValue);
        }
    }

    protected static class GtkToggleButtonLibrary extends GtkButtonLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Queries a GtkToggleButton and returns its current state.
         * <p>
         * Returns TRUE if the toggle button is pressed in and FALSE if it is raised.
         *
         * @param toggle_button self
         * @return Whether the button is pressed.
         */
        public native boolean gtk_toggle_button_get_active(Pointer toggle_button);

        /**
         * Creates a new toggle button.
         * <p>
         * A widget should be packed into the button, as in gtk_button_new().
         *
         * @return A new toggle button. Type: GtkToggleButton
         */
        public native Pointer gtk_toggle_button_new();

        /**
         * Creates a new toggle button with a text label.
         *
         * @param label A string containing the message to be placed in the toggle button.
         * @return A new toggle button. Type: GtkToggleButton
         */
        public native Pointer gtk_toggle_button_new_with_label(String label);

        /**
         * Creates a new GtkToggleButton containing a label.
         * <p>
         * The label will be created using gtk_label_new_with_mnemonic(), so underscores in label indicate the mnemonic
         * for the button.
         *
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         * @return A new GtkToggleButton
         */
        public native Pointer gtk_toggle_button_new_with_mnemonic(String label);

        /**
         * Sets the status of the toggle button.
         * <p>
         * Set to TRUE if you want the GtkToggleButton to be "pressed in", and FALSE to raise it.
         * <p>
         * If the status of the button changes, this action causes the GtkToggleButton::toggled signal to be emitted.
         *
         * @param toggle_button self
         * @param is_active     TRUE or FALSE.
         */
        public native void gtk_toggle_button_set_active(Pointer toggle_button, boolean is_active);

        /**
         * Adds self to the group of group.
         * <p>
         * In a group of multiple toggle buttons, only one button can be active at a time.
         * <p>
         * Setting up groups in a cycle leads to undefined behavior.
         * <p>
         * Note that the same effect can be achieved via the GtkActionable API, by using the same action with parameter
         * type and state type 's' for all buttons in the group, and giving each button its own target value.
         *
         * @param toggle_button self
         * @param group         Another GtkToggleButton to form a group with.
         *                      <p>
         *                      The argument can be NULL.
         */
        public native void gtk_toggle_button_set_group(Pointer toggle_button, Pointer group);
    }

}
