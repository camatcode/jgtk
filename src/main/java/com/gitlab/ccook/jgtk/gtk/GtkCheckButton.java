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
import com.gitlab.ccook.jgtk.MnemonicLabel;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkActionable;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.AssertionUtils;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.Set;

/**
 * Example GtkCheckButtons
 * A GtkCheckButton is created by calling either gtk_check_button_new() or gtk_check_button_new_with_label().
 * <p>
 * The state of a GtkCheckButton can be set specifically using gtk_check_button_set_active(), and retrieved using
 * gtk_check_button_get_active().
 */
@SuppressWarnings({"unchecked", "unused"})
public class GtkCheckButton extends GtkWidget implements GtkAccessible, GtkActionable, GtkBuildable {

    private static final GtkCheckButtonLibrary library = new GtkCheckButtonLibrary();

    public GtkCheckButton(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkCheckButton.
     */
    public GtkCheckButton() {
        super(library.gtk_check_button_new());
    }

    /**
     * Creates a new GtkCheckButton with the given text. If the text contains '_', it's treated mnemonic
     *
     * @param label The text for the check button.
     */
    public GtkCheckButton(String label) {
        super(handleCtor(label));
    }

    private static Pointer handleCtor(String label) {
        AssertionUtils.assertNotNull(GtkCheckButton.class, "ctor: Label is null", label);
        if (label.contains("_")) {
            return library.gtk_check_button_new_with_mnemonic(label);
        } else {
            return library.gtk_check_button_new_with_label(label);
        }
    }

    /**
     * Creates a new GtkCheckButton with the given text and a mnemonic.
     *
     * @param label the text of the button, with an underscore in front of the mnemonic character.
     *              <p>
     *              The argument can be NULL.
     */
    public GtkCheckButton(MnemonicLabel label) {
        super(handleCtor(label));
    }

    private static Pointer handleCtor(MnemonicLabel label) {
        if (label != null) {
            return library.gtk_check_button_new_with_mnemonic(label.getMnemonicLabel());
        }
        return library.gtk_check_button_new_with_mnemonic(null);
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
     * Gets the child widget of button or NULL if GtkCheckButton:label is set.
     *
     * @return The child widget of button.
     */
    public Option<GtkWidget> getChild() {
        Set<GtkWidget> w = getChildren();
        if (!w.isEmpty()) {
            GtkWidget next = w.iterator().next();
            if (next != null) {
                return new Option<>((GtkWidget) JGTKObject.newObjectFromType(next.getCReference(), GtkWidget.class));
            }
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of button.
     * <p>
     * Note that by using this API, you take full responsibility for setting up the proper accessibility label and
     * description information for button. Most likely, you'll either set the accessibility label or description for
     * button explicitly, or you'll set a labelled-by or described-by relations from child to button.
     *
     * @param w The child widget.
     */
    public void setChild(GtkWidget w) {
        w.setParent(this);
        insertWidgetAfter(w, null);
        //4.8 library.gtk_check_button_set_child(cReference, w.cReference);
    }

    @SuppressWarnings("SameParameterValue")
    private void insertWidgetAfter(GtkWidget w, GtkWidget w2) {
        Pointer firstArg = w == null ? null : w.getCReference();
        Pointer secondArg = cReference;
        Pointer thirdArg = w2 == null ? null : w2.getCReference();
        library.gtk_widget_insert_after(firstArg, secondArg, thirdArg);
    }

    /**
     * Returns the label of the check button or NONE if unset
     *
     * @return The label that displays next to the indicator. If no label is shown, NONE will be returned.
     */
    public Option<String> getLabel() {
        return new Option<>(library.gtk_check_button_get_label(cReference));
    }

    /**
     * Sets the text of the Label
     * <p>
     * If GtkCheckButton:use-underline is TRUE, an underscore in label is interpreted as mnemonic indicator,
     * see gtk_check_button_set_use_underline() for details on this behavior.
     *
     * @param label label used by check button
     */
    public void setLabel(String label) {
        library.gtk_check_button_set_label(cReference, label);
    }

    /**
     * Returns whether the check button is active.
     *
     * @return Whether the check button is active.
     */
    public boolean isActive() {
        return library.gtk_check_button_get_active(cReference);
    }

    /**
     * Changes the check buttons active state.
     * Setting active to TRUE will add the :checked: state to both the check button and the indicator CSS node.
     *
     * @param isActive TRUE if is in active state
     */
    public void setActive(boolean isActive) {
        library.gtk_check_button_set_active(cReference, isActive);
    }

    /**
     * Returns whether the check button is in an inconsistent state.
     *
     * @return TRUE if check_button is currently in an inconsistent state.
     */
    public boolean isInInconsistentState() {
        return library.gtk_check_button_get_inconsistent(cReference);
    }

    /**
     * Returns whether underlines in the label indicate mnemonics.
     *
     * @return The value of the GtkCheckButton:use-underline property. See gtk_check_button_set_use_underline() for
     *         details on how to set a new value.
     */
    public boolean isLabelMnemonic() {
        return library.gtk_check_button_get_use_underline(cReference);
    }

    /**
     * Adds button to the group of another button.
     * <p>
     * In a group of multiple check buttons, only one button can be active at a time. The behavior of a check button in
     * a group is also commonly known as a radio button.
     * <p>
     * Setting the group of a check button also changes the css name of the indicator widget's CSS node to 'radio'.
     * <p>
     * Setting up groups in a cycle leads to undefined behavior.
     * <p>
     * Note that the same effect can be achieved via the GtkActionable API, by using the same action with parameter
     * type and state type 's' for all buttons in the group, and giving each button its own target value.
     *
     * @param b Another GtkCheckButton to form a group with.
     *          <p>
     *          The argument can be NULL.
     */
    public void setGroup(GtkCheckButton b) {
        library.gtk_check_button_set_group(cReference, pointerOrNull(b));
    }

    /**
     * Sets the GtkCheckButton to inconsistent state.
     * <p>
     * You should turn off the inconsistent state again if the user checks the check button.
     * This has to be done manually.
     *
     * @param inconsistentState TRUE if state is inconsistent.
     */
    public void setInconsistentState(boolean inconsistentState) {
        library.gtk_check_button_set_inconsistent(getCReference(), inconsistentState);
    }

    /**
     * Sets whether underlines in the label indicate mnemonics.
     * <p>
     * If setting is TRUE, an underscore character in self's label indicates a mnemonic accelerator key.
     * This behavior is similar to GtkLabel:use-underline.
     *
     * @param shouldUse TRUE to interpret underscores as mnemonic
     */
    public void shouldUseMnemonics(boolean shouldUse) {
        library.gtk_check_button_set_use_underline(getCReference(), shouldUse);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted to when the check button is activated.
         * <p>
         * The ::activate signal on GtkCheckButton is an action signal and emitting it causes the button to animate
         * press then release.
         * <p>
         * Applications should never connect to this signal, but use the GtkCheckButton::toggled signal.
         * <p>
         * The default bindings for this signal are all forms of the ␣ and Enter keys.
         */
        public static final Signals ACTIVATE = new Signals("activate");
        public static final Signals TOGGLED = new Signals("toggled");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkCheckButtonLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns whether the check button is active.
         *
         * @param self self
         * @return Whether the check button is active.
         */
        public native boolean gtk_check_button_get_active(Pointer self);

        /**
         * Returns whether the check button is in an inconsistent state.
         *
         * @param check_button self
         * @return TRUE if check_button is currently in an inconsistent state.
         */
        public native boolean gtk_check_button_get_inconsistent(Pointer check_button);

        /**
         * Returns the label of the check button or NULL if GtkCheckButton:child is set.
         *
         * @param self self
         * @return The label self shows next to the indicator. If no label is shown, NULL will be returned.
         */
        public native String gtk_check_button_get_label(Pointer self);

        /**
         * Returns whether underlines in the label indicate mnemonics.
         *
         * @param self self
         * @return The value of the GtkCheckButton:use-underline property. See gtk_check_button_set_use_underline()
         *         for details on how to set a new value.
         */
        public native boolean gtk_check_button_get_use_underline(Pointer self);

        /**
         * Creates a new GtkCheckButton.
         *
         * @return A new GtkCheckButton. Type:GtkCheckButton
         */
        public native Pointer gtk_check_button_new();

        /**
         * Creates a new GtkCheckButton with the given text.
         *
         * @param label The text for the check button.
         *              <p>
         *              The argument can be NULL.
         * @return A new GtkCheckButton. Type: GtkCheckButton
         */
        public native Pointer gtk_check_button_new_with_label(String label);

        /**
         * Creates a new GtkCheckButton with the given text and a mnemonic.
         *
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         *              <p>
         *              The argument can be NULL.
         * @return A new GtkCheckButton. Type: GtkCheckButton
         */
        public native Pointer gtk_check_button_new_with_mnemonic(String label);

        /**
         * Changes the check buttons active state.
         *
         * @param self    self
         * @param setting The new value to set.
         */
        public native void gtk_check_button_set_active(Pointer self, boolean setting);

        /**
         * Adds self to the group of group.
         * <p>
         * In a group of multiple check buttons, only one button can be active at a time. The behavior of a check button
         * in a group is also commonly known as a radio button.
         * <p>
         * Setting the group of a check button also changes the css name of the indicator widget's CSS node to 'radio'.
         * <p>
         * Setting up groups in a cycle leads to undefined behavior.
         * <p>
         * Note that the same effect can be achieved via the GtkActionable API, by using the same action with parameter
         * type and state type 's' for all buttons in the group, and giving each button its own target value.
         *
         * @param self  self
         * @param group Another GtkCheckButton to form a group with.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_check_button_set_group(Pointer self, Pointer group);

        /**
         * Sets the GtkCheckButton to inconsistent state.
         * <p>
         * You should turn off the inconsistent state again if the user checks the check button.
         * This has to be done manually.
         *
         * @param check_button self
         * @param inconsistent TRUE if state is inconsistent.
         */
        public native void gtk_check_button_set_inconsistent(Pointer check_button, boolean inconsistent);

        /**
         * Sets the text of self.
         * <p>
         * If GtkCheckButton:use-underline is TRUE, an underscore in label is interpreted as mnemonic indicator, s
         * ee gtk_check_button_set_use_underline() for details on this behavior.
         *
         * @param self  self
         * @param label The text shown next to the indicator, or NULL to show no text.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_check_button_set_label(Pointer self, String label);

        /**
         * Sets whether underlines in the label indicate mnemonics.
         * <p>
         * If setting is TRUE, an underscore character in self's label indicates a mnemonic accelerator key.
         * This behavior is similar to GtkLabel:use-underline.
         *
         * @param self    self
         * @param setting The new value to set.
         */
        public native void gtk_check_button_set_use_underline(Pointer self, boolean setting);
    }
}
