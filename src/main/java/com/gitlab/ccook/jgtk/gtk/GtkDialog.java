/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 - 2023 JGTK
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


import com.gitlab.ccook.jgtk.GtkShortcutManager;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkResponseType;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * Dialogs are a convenient way to prompt the user for a small amount of input.
 * <p>
 * Typical uses are to display a message, ask a question, or anything else that does not require extensive effort on the
 * user's part.
 * <p>
 * The main area of a GtkDialog is called the "content area", and is yours to populate with widgets such a GtkLabel or
 * GtkEntry, to present your information, questions, or tasks to the user.
 * <p>
 * In addition, dialogs allow you to add "action widgets". Most commonly, action widgets are buttons. Depending on the
 * platform, action widgets may be presented in the header bar at the top of the window, or at the bottom of the window.
 * To add action widgets, create your GtkDialog using gtk_dialog_new_with_buttons(), or use gtk_dialog_add_button(),
 * gtk_dialog_add_buttons(), or gtk_dialog_add_action_widget().
 * <p>
 * GtkDialogs uses some heuristics to decide whether to add a close button to the window decorations. If any of the
 * action buttons use the response ID GTK_RESPONSE_CLOSE or GTK_RESPONSE_CANCEL, the close button is omitted.
 * <p>
 * Clicking a button that was added as an action widget will emit the GtkDialog::response signal with a response ID
 * that you specified. GTK will never assign a meaning to positive response IDs; these are entirely user-defined. But
 * for convenience, you can use the response IDs in the GtkResponseType enumeration (these all have values less than
 * zero). If a dialog receives a delete event, the GtkDialog::response signal will be emitted with the
 * GTK_RESPONSE_DELETE_EVENT response ID.
 * <p>
 * Dialogs are created with a call to gtk_dialog_new() or gtk_dialog_new_with_buttons(). The latter is recommended;
 * it allows you to set the dialog title, some convenient flags, and add buttons.
 * <p>
 * A "modal" dialog (that is, one which freezes the rest of the application from user input), can be created by calling
 * gtk_window_set_modal() on the dialog. When using gtk_dialog_new_with_buttons(), you can also pass the
 * GTK_DIALOG_MODAL flag to make a dialog modal.
 * <p>
 * For the simple dialog in the following example, a GtkMessageDialog would save some effort. But you'd need to create
 * the dialog contents manually if you had more than a simple message in the dialog.
 *
 * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
 */
@Deprecated
@SuppressWarnings({"unchecked", "DeprecatedIsStillUsed"})
public class GtkDialog extends GtkWindow implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkDialogLibrary library = new GtkDialogLibrary();

    public GtkDialog(Pointer p) {
        super(p);
    }

    /**
     * Creates a new dialog box.
     * <p>
     * Widgets should not be packed into the GtkWindow directly, but into the content_area and action_area.
     * <p>
     *
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public GtkDialog() {
        super(library.gtk_dialog_new());
    }

    /**
     * Adds an activate-able widget to the action area of a GtkDialog.
     * <p>
     * GTK connects a signal handler that will emit the GtkDialog::response signal on the dialog when the widget is
     * activated. The widget is appended to the end of the dialog's action area.
     * <p>
     * If you want to add a non-activate-able widget, simply pack it into the action_area field of the GtkDialog struct.
     *
     * @param w          An activate-able widget.
     * @param responseId Response ID for child.
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public void addActionWidget(GtkWidget w, int responseId) {
        if (w != null) {
            library.gtk_dialog_add_action_widget(getCReference(), w.getCReference(), responseId);
        }
    }

    /**
     * Adds a button with the given text.
     * <p>
     * GTK arranges things so that clicking the button will emit the GtkDialog::response signal with the given
     * response_id. The button is appended to the end of the dialog's action area. The button widget is returned,
     * but usually you don't need it.
     *
     * @param buttonText Text of button.
     * @param responseId Response ID for the button.
     * @return The GtkButton widget that was added.
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public Option<GtkWidget> addButton(String buttonText, int responseId) {
        Option<Pointer> p = new Option<>(library.gtk_dialog_add_button(getCReference(), buttonText, responseId));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) GtkWidget.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Returns the content area of dialog.
     *
     * @return The content area GtkBox.
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public GtkWidget getContentArea() {
        Pointer p = library.gtk_dialog_get_content_area(getCReference());
        return (GtkWidget) GtkWidget.newObjectFromType(p, GtkWidget.class);
    }

    /**
     * Returns the header bar of dialog.
     * <p>
     * Note that the header bar is only used by the dialog if the GtkDialog:use-header-bar property is TRUE.
     *
     * @return The header bar.
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public Option<GtkHeaderBar> getHeaderBar() {
        Option<Pointer> p = new Option<>(library.gtk_dialog_get_header_bar(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkHeaderBar) GtkWidget.newObjectFromType(p.get(), GtkHeaderBar.class));
        }
        return Option.NONE;
    }

    /**
     * Gets the response id of a widget in the action area of a dialog.
     *
     * @param child A widget in the action area of dialog.
     * @return The response id of widget, if defined
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public Option<Integer> getResponseIdForWidget(GtkWidget child) {
        int response = library.gtk_dialog_get_response_for_widget(getCReference(), pointerOrNull(child));
        if (response != GtkResponseType.GTK_RESPONSE_NONE.getCValue()) {
            return new Option<>(response);
        }
        return Option.NONE;
    }

    /**
     * Gets the widget button that uses the given response ID in the action area of a dialog.
     *
     * @param responseId The response ID used by the dialog widget.
     * @return The widget button that uses the given response_id.
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public Option<GtkWidget> getWidgetForResponseId(int responseId) {
        Option<Pointer> p = new Option<>(library.gtk_dialog_get_widget_for_response(getCReference(), responseId));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Emits the ::response signal with the given response ID.
     * <p>
     * Used to indicate that the user has responded to the dialog in some way.
     *
     * @param responseId Response ID.
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public void emitResponse(int responseId) {
        library.gtk_dialog_response(getCReference(), responseId);
    }

    /**
     * Sets the default widget for the dialog based on the response ID.
     * <p>
     * Pressing "Enter" normally activates the default widget.
     *
     * @param responseId A response ID.
     * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
     */
    public void setDefaultResponse(int responseId) {
        library.gtk_dialog_set_default_response(getCReference(), responseId);
    }

    /**
     * A convenient way to sensitize/desensitize dialog buttons.
     * <p>
     * Calls gtk_widget_set_sensitive (widget,setting) for each widget in the dialog's action area with the given
     * response_id.
     *
     * @param responseId  A response ID.
     * @param isSensitive TRUE for sensitive.
     */
    public void setResponseSensitive(int responseId, boolean isSensitive) {
        library.gtk_dialog_set_response_sensitive(getCReference(), responseId, isSensitive);
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

    protected static class GtkDialogLibrary extends GtkWindowLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new dialog box.
         * <p>
         * Widgets should not be packed into the GtkWindow directly, but into the content_area and action_area.
         * <p>
         *
         * @return The new dialog as a GtkWidget. Type: GtkDialog
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native Pointer gtk_dialog_new();

        /**
         * Adds an activate-able widget to the action area of a GtkDialog.
         * <p>
         * GTK connects a signal handler that will emit the GtkDialog::response signal on the dialog when the widget is
         * activated. The widget is appended to the end of the dialog's action area.
         * <p>
         * If you want to add a non-activate-able widget, simply pack it into the action_area field of the GtkDialog
         * struct.
         *
         * @param dialog      self
         * @param widget      An activate-able widget.
         * @param response_id Response ID for child.
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native void gtk_dialog_add_action_widget(Pointer dialog, Pointer widget, int response_id);

        /**
         * Adds a button with the given text.
         * <p>
         * GTK arranges things so that clicking the button will emit the GtkDialog::response signal with the given
         * response_id. The button is appended to the end of the dialog's action area. The button widget is returned,
         * but usually you don't need it.
         *
         * @param dialog      self
         * @param button_text Text of button.
         * @param response_id Response ID for the button.
         * @return The GtkButton widget that was added.
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native Pointer gtk_dialog_add_button(Pointer dialog, String button_text, int response_id);

        /**
         * Returns the content area of dialog.
         *
         * @param dialog self
         * @return The content area GtkBox.
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native Pointer gtk_dialog_get_content_area(Pointer dialog);

        /**
         * Returns the header bar of dialog.
         * <p>
         * Note that the header bar is only used by the dialog if the GtkDialog:use-header-bar property is TRUE.
         *
         * @param dialog self
         * @return The header bar. Type: GtkHeaderBar
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native Pointer gtk_dialog_get_header_bar(Pointer dialog);

        /**
         * Gets the response id of a widget in the action area of a dialog.
         *
         * @param dialog self
         * @param widget A widget in the action area of dialog.
         * @return The response id of widget, or GTK_RESPONSE_NONE if widget doesn't have a response id set.
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native int gtk_dialog_get_response_for_widget(Pointer dialog, Pointer widget);

        /**
         * @param dialog      self
         * @param response_id The response ID used by the dialog widget.
         * @return The widget button that uses the given response_id.
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native Pointer gtk_dialog_get_widget_for_response(Pointer dialog, int response_id);

        /**
         * Emits the ::response signal with the given response ID.
         * <p>
         * Used to indicate that the user has responded to the dialog in some way.
         *
         * @param dialog      self
         * @param response_id Response ID.
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native void gtk_dialog_response(Pointer dialog, int response_id);

        /**
         * Sets the default widget for the dialog based on the response ID.
         * <p>
         * Pressing "Enter" normally activates the default widget.
         *
         * @param dialog      self
         * @param response_id A response ID.
         * @deprecated Deprecated since: 4.10. Use GtkWindow instead.
         */
        public native void gtk_dialog_set_default_response(Pointer dialog, int response_id);

        /**
         * A convenient way to sensitize/desensitize dialog buttons.
         * <p>
         * Calls gtk_widget_set_sensitive (widget,setting) for each widget in the dialog's action area with the given
         * response_id.
         *
         * @param dialog      self
         * @param response_id A response ID.
         * @param setting     TRUE for sensitive.
         */
        public native void gtk_dialog_set_response_sensitive(Pointer dialog, int response_id, boolean setting);
    }

    public static class Signals extends GtkWindow.Signals {

        /**
         * Emitted when the user uses a keybinding to close the dialog.
         * <p>
         * This is a keybinding signal.
         * <p>
         * The default binding for this signal is the Escape key.
         */
        public static final Signals CLOSE = new Signals("close");
        /**
         * Emitted when an action widget is clicked.
         * <p>
         * The signal is also emitted when the dialog receives a delete event, and when gtk_dialog_response() is called.
         * On a delete event, the response ID is GTK_RESPONSE_DELETE_EVENT. Otherwise, it depends on which action widget
         * was clicked.
         */
        public static final Signals RESPONSE = new Signals("response");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }
}

