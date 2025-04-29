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
import com.gitlab.ccook.jgtk.enums.GtkMessageType;
import com.gitlab.ccook.jgtk.enums.GtkResponseType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkInfoBar can be show messages to the user without a dialog.
 * <p>
 * It is often temporarily shown at the top or bottom of a document. In contrast to GtkDialog, which has an action area
 * at the bottom, GtkInfoBar has an action area at the side.
 * <p>
 * The API of GtkInfoBar is very similar to GtkDialog, allowing you to add buttons to the action area with
 * gtk_info_bar_add_button() or gtk_info_bar_new_with_buttons(). The sensitivity of action widgets can be controlled
 * with gtk_info_bar_set_response_sensitive().
 * <p>
 * To add widgets to the main content area of a GtkInfoBar, use gtk_info_bar_add_child().
 * <p>
 * Similar to GtkMessageDialog, the contents of a GtkInfoBar can be classified as error message, warning,
 * informational message, etc., by using gtk_info_bar_set_message_type(). GTK may use the message type to determine how
 * the message is displayed.
 *
 * @deprecated Deprecated since: 4.10
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class GtkInfoBar extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkInfoBarLibrary library = new GtkInfoBarLibrary();

    public GtkInfoBar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkInfoBar object.
     *
     * @deprecated Deprecated since: 4.10
     */
    public GtkInfoBar() {
        super(library.gtk_info_bar_new());
    }

    /**
     * Add an activatable widget to the action area of a GtkInfoBar.
     * <p>
     * This also connects a signal handler that will emit the GtkInfoBar::response signal on the message area when the
     * widget is activated. The widget is appended to the end of the message areas action area.
     *
     * @param w          An activatable widget.
     * @param responseId Response ID for child.
     * @deprecated Deprecated since: 4.10
     */
    public void addActionWidget(GtkWidget w, int responseId) {
        if (w != null) {
            library.gtk_info_bar_add_action_widget(getCReference(), w.getCReference(), responseId);
        }
    }

    /**
     * Adds a button with the given text.
     * <p>
     * Clicking the button will emit the GtkInfoBar::response signal with the given response_id. The button is appended
     * to the end of the info bar's action area. The button widget is returned, but usually you don't need it.
     *
     * @param buttonText Text of button.
     * @param responseId Response ID for the button.
     * @deprecated Deprecated since: 4.10
     */
    public GtkButton addButton(String buttonText, int responseId) {
        return new GtkButton(library.gtk_info_bar_add_button(getCReference(), buttonText, responseId));
    }

    /**
     * Adds a button with the given text.
     * <p>
     * Clicking the button will emit the GtkInfoBar::response signal with the given response_id. The button is appended
     * to the end of the info bar's action area. The button widget is returned, but usually you don't need it.
     *
     * @param buttonText Text of button.
     * @param responseId Response ID for the button.
     * @deprecated Deprecated since: 4.10
     */
    public GtkButton addButton(String buttonText, GtkResponseType responseId) {
        return new GtkButton(library.gtk_info_bar_add_button(getCReference(), buttonText, responseId.getCValue()));
    }

    /**
     * Adds a widget to the content area of the info bar.
     *
     * @param w The child to be added.
     * @deprecated Deprecated since: 4.10
     */
    public void addChild(GtkWidget w) {
        if (w != null) {
            library.gtk_info_bar_add_child(getCReference(), w.getCReference());
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
     * Returns whether the widget will display a standard close button.
     *
     * @return TRUE if the widget displays standard close button.
     * @deprecated Deprecated since: 4.10
     */
    public boolean doesShowCloseButton() {
        return library.gtk_info_bar_get_show_close_button(getCReference());
    }

    /**
     * Emits the "response" signal with the given response_id.
     *
     * @param responseId A response ID.
     * @deprecated Deprecated since: 4.10
     */
    public void emitResponse(int responseId) {
        library.gtk_info_bar_response(getCReference(), responseId);
    }

    /**
     * Returns the message type of the message area.
     *
     * @return The message type of the message area.
     * @deprecated Deprecated since: 4.10
     */
    public GtkMessageType getMessageType() {
        return GtkMessageType.getTypeFromCValue(library.gtk_info_bar_get_message_type(getCReference()));
    }

    /**
     * Sets the message type of the message area.
     * <p>
     * GTK uses this type to determine how the message is displayed.
     *
     * @param type A GtkMessageType
     * @deprecated Deprecated since: 4.10
     */
    public void setMessageType(GtkMessageType type) {
        if (type != null) {
            library.gtk_info_bar_set_message_type(getCReference(), GtkMessageType.getCValueFromType(type));
        }
    }

    /**
     * Returns whether the info bar is currently revealed.
     *
     * @return whether the info bar is currently revealed.
     * @deprecated Deprecated since: 4.10
     */
    public boolean isRevealed() {
        return library.gtk_info_bar_get_revealed(getCReference());
    }

    /**
     * Sets whether the GtkInfoBar is revealed.
     * <p>
     * Changing this will make info_bar reveal or conceal itself via a sliding transition.
     * <p>
     * Note: this does not show or hide info_bar in the GtkWidget:visible sense, so revealing has no effect if
     * GtkWidget:visible is FALSE.
     *
     * @param isRevealed The new value of the property.
     * @deprecated Deprecated since: 4.10
     */
    public void setRevealed(boolean isRevealed) {
        library.gtk_info_bar_set_revealed(getCReference(), isRevealed);
    }

    /**
     * Removes a widget from the content area of the info bar.
     *
     * @param w A child that has been added to the content area.
     * @deprecated Deprecated since: 4.10
     */
    public void remove(GtkWidget w) {
        if (w != null) {
            library.gtk_info_bar_remove_child(getCReference(), w.getCReference());
        }
    }

    /**
     * Removes a widget from the action area of info_bar.
     * <p>
     * The widget must have been put there by a call to gtk_info_bar_add_action_widget() or gtk_info_bar_add_button().
     *
     * @param w An action widget to remove.
     * @deprecated Deprecated since: 4.10
     */
    public void removeActionWidget(GtkWidget w) {
        if (w != null) {
            library.gtk_info_bar_remove_action_widget(getCReference(), w.getCReference());
        }
    }

    /**
     * Sets the last widget in the info bar's action area with the given response_id as the default widget for the
     * dialog.
     * <p>
     * Pressing "Enter" normally activates the default widget.
     * <p>
     * Note that this function currently requires info_bar to be added to a widget hierarchy.
     *
     * @param responseId A response ID.
     * @deprecated Deprecated since: 4.10
     */
    public void setDefaultResponse(int responseId) {
        library.gtk_info_bar_set_default_response(getCReference(), responseId);
    }

    /**
     * Sets the sensitivity of action widgets for response_id.
     * <p>
     * Calls gtk_widget_set_sensitive (widget, setting) for each widget in the info bar's action area with the given
     * response_id. A convenient way to sensitize/desensitize buttons.
     *
     * @param isSensitive TRUE for sensitive.
     * @param responseId  A response ID.
     * @deprecated Deprecated since: 4.10
     */
    public void setResponseSensitive(int responseId, boolean isSensitive) {
        library.gtk_info_bar_set_response_sensitive(getCReference(), responseId, isSensitive);
    }

    /**
     * If true, a standard close button is shown.
     * <p>
     * When clicked it emits the response GTK_RESPONSE_CLOSE.
     *
     * @param shouldShow TRUE to include a close button.
     * @deprecated Deprecated since: 4.10
     */
    public void shouldShowCloseButton(boolean shouldShow) {
        library.gtk_info_bar_set_show_close_button(getCReference(), shouldShow);
    }

    /**
     * @deprecated Deprecated since: 4.10
     */
    @Deprecated
    public static class Signals extends GtkWidget.Signals {
        /**
         * Gets emitted when the user uses a keybinding to dismiss the info bar.
         * The default binding for this signal is the Escape key.
         */
        public static final Signals CLOSE = new Signals("close");
        /**
         * Emitted when an action widget is clicked.
         * <p>
         * The signal is also emitted when the application programmer calls gtk_info_bar_response().
         * The response_id depends on which action widget was clicked.
         */
        public static final Signals RESPONSE = new Signals("response");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }

    /**
     * @deprecated Deprecated since: 4.10
     */
    @Deprecated
    protected static class GtkInfoBarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Add an activatable widget to the action area of a GtkInfoBar.
         * <p>
         * This also connects a signal handler that will emit the GtkInfoBar::response signal on the message area when
         * the widget is activated. The widget is appended to the end of the message areas action area.
         *
         * @param info_bar    self
         * @param child       An activatable widget. Type: GtkWidget
         * @param response_id Response ID for child.
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_add_action_widget(Pointer info_bar, Pointer child, int response_id);

        /**
         * Adds a button with the given text.
         * <p>
         * Clicking the button will emit the GtkInfoBar::response signal with the given response_id. The button is
         * appended to the end of the info bar's action area. The button widget is returned, but usually you don't
         * need it.
         *
         * @param info_bar    self
         * @param button_text Text of button.
         * @param response_id Response ID for the button.
         * @return The GtkButton widget that was added. Type: GtkButton
         * @deprecated Deprecated since: 4.10
         */
        public native Pointer gtk_info_bar_add_button(Pointer info_bar, String button_text, int response_id);

        /**
         * Adds a widget to the content area of the info bar.
         *
         * @param info_bar self
         * @param widget   The child to be added. Type: GtkWidget
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_add_child(Pointer info_bar, Pointer widget);

        /**
         * Returns the message type of the message area.
         *
         * @param info_bar self
         * @return The message type of the message area. Type: GtkMessageType
         * @deprecated Deprecated since: 4.10
         */
        public native int gtk_info_bar_get_message_type(Pointer info_bar);

        /**
         * Returns whether the info bar is currently revealed.
         *
         * @param info_bar self
         * @return The current value of the GtkInfoBar:revealed property.
         * @deprecated Deprecated since: 4.10
         */
        public native boolean gtk_info_bar_get_revealed(Pointer info_bar);

        /**
         * Returns whether the widget will display a standard close button.
         *
         * @param info_bar self
         * @return TRUE if the widget displays standard close button.
         * @deprecated Deprecated since: 4.10
         */
        public native boolean gtk_info_bar_get_show_close_button(Pointer info_bar);

        /**
         * Creates a new GtkInfoBar object.
         *
         * @return A new GtkInfoBar object. Type: GtkInfoBar
         * @deprecated Deprecated since: 4.10
         */
        public native Pointer gtk_info_bar_new();

        /**
         * Removes a widget from the action area of info_bar.
         * <p>
         * The widget must have been put there by a call to gtk_info_bar_add_action_widget() or
         * gtk_info_bar_add_button().
         *
         * @param info_bar self
         * @param widget   An action widget to remove.
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_remove_action_widget(Pointer info_bar, Pointer widget);

        /**
         * Removes a widget from the content area of the info bar.
         *
         * @param info_bar self
         * @param widget   A child that has been added to the content area. Type: GtkWidget
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_remove_child(Pointer info_bar, Pointer widget);

        /**
         * Emits the "response" signal with the given response_id.
         *
         * @param info_bar    self
         * @param response_id A response ID.
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_response(Pointer info_bar, int response_id);

        /**
         * Sets the last widget in the info bar's action area with the given response_id as the default widget for the
         * dialog.
         * <p>
         * Pressing "Enter" normally activates the default widget.
         * <p>
         * Note that this function currently requires info_bar to be added to a widget hierarchy.
         *
         * @param info_bar    self
         * @param response_id A response ID.
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_set_default_response(Pointer info_bar, int response_id);

        /**
         * Sets the message type of the message area.
         * <p>
         * GTK uses this type to determine how the message is displayed.
         *
         * @param info_bar     self
         * @param message_type A GtkMessageType. Type: GtkMessageType
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_set_message_type(Pointer info_bar, int message_type);

        /**
         * Sets the sensitivity of action widgets for response_id.
         * <p>
         * Calls gtk_widget_set_sensitive (widget, setting) for each widget in the info bar's action area with the
         * given response_id. A convenient way to sensitize/desensitize buttons.
         *
         * @param info_bar    self
         * @param response_id A response ID.
         * @param setting     TRUE for sensitive.
         * @deprecated Deprecated since: 4.10
         */
        public native void gtk_info_bar_set_response_sensitive(Pointer info_bar, int response_id, boolean setting);

        /**
         * Sets whether the GtkInfoBar is revealed.
         * <p>
         * Changing this will make info_bar reveal or conceal itself via a sliding transition.
         * <p>
         * Note: this does not show or hide info_bar in the GtkWidget:visible sense, so revealing has no effect if
         * GtkWidget:visible is FALSE.
         *
         * @param info_bar   self
         * @param isRevealed The new value of the property.
         */
        public native void gtk_info_bar_set_revealed(Pointer info_bar, boolean isRevealed);

        /**
         * If true, a standard close button is shown.
         * <p>
         * When clicked it emits the response GTK_RESPONSE_CLOSE.
         *
         * @param info_bar self
         * @param setting  TRUE to include a close button.
         */
        public native void gtk_info_bar_set_show_close_button(Pointer info_bar, boolean setting);

    }
}
