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

import com.gitlab.ccook.jgtk.GIcon;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkAppChooser;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * The GtkAppChooserButton lets the user select an application.
 * <p>
 * An example GtkAppChooserButton
 * <p>
 * Initially, a GtkAppChooserButton selects the first application in its list, which will either be the most-recently
 * used application or, if GtkAppChooserButton:show-default-item is TRUE, the default application.
 * <p>
 * The list of applications shown in a GtkAppChooserButton includes the recommended applications for the given content
 * type. When GtkAppChooserButton:show-default-item is set, the default application is also included. To let the user
 * chooser other applications, you can set the GtkAppChooserButton:show-dialog-item property, which allows to open a
 * full GtkAppChooserDialog.
 * <p>
 * It is possible to add custom items to the list, using gtk_app_chooser_button_append_custom_item(). These items cause
 * the GtkAppChooserButton::custom-item-activated signal to be emitted when they are selected.
 * <p>
 * To track changes in the selected application, use the GtkAppChooserButton::changed signal.
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class GtkAppChooserButton extends GtkWidget implements GtkAccessible, GtkAppChooser, GtkBuildable {

    private static final GtkAppChooserButtonLibrary library = new GtkAppChooserButtonLibrary();

    /**
     * Creates a new GtkAppChooserButton for applications that can handle content of the given type.
     * <p>
     *
     * @param contentType The content type to show applications for.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public GtkAppChooserButton(String contentType) {
        super(library.gtk_app_chooser_button_new(contentType));
    }

    public GtkAppChooserButton(Pointer ref) {
        super(ref);
    }

    /**
     * Appends a custom item to the list of applications that is shown in the popup.
     * <p>
     * The item name must be unique per-widget.
     * Clients can use the provided name as a detail for the GtkAppChooserButton::custom-item-activated signal,
     * to add a callback for the activation of a particular custom item in the list.
     * <p>
     * See also gtk_app_chooser_button_append_separator().
     *
     * @param name  The name of the custom item.
     * @param label The label for the custom item.
     * @param icon  The icon for the custom item.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void appendCustomItem(String name, String label, GIcon icon) {
        if (icon != null && name != null && label != null) {
            library.gtk_app_chooser_button_append_custom_item(cReference, name, label, icon.getCReference());
        }
    }

    /**
     * Appends a separator to the list of applications that is shown in the popup.
     *
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public void appendSeparator() {
        library.gtk_app_chooser_button_append_separator(cReference);
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
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef, GConnectFlags[] flags) {
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
     * Returns whether the dropdown menu shows an item for a GtkAppChooserDialog.
     *
     * @return True if the dropdown menu shows an item for a GtkAppChooserDialog
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public boolean doesShowChooseOtherApplicationDialog() {
        return library.gtk_app_chooser_button_get_show_dialog_item(cReference);
    }

    /**
     * Returns whether the dropdown menu shows the default application at the top.
     *
     * @return True if the dropdown menu shows the default application at top; False otherwise
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public boolean doesShowDefaultApplicationAtTop() {
        return library.gtk_app_chooser_button_get_show_default_item(cReference);
    }

    /**
     * Returns the text to display at the top of the dialog.
     * <p>
     *
     * @return A string containing Pango markup. The text to display at the top of the dialog,
     *         or NONE, in which case a default text is displayed.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public Option<String> getHeadingForChooseOtherApplicationDialog() {
        return new Option<>(library.gtk_app_chooser_button_get_heading(cReference));
    }

    /**
     * Gets whether the dialog is modal.
     *
     * @return TRUE if the dialog is modal.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public boolean isModal() {
        return library.gtk_app_chooser_button_get_modal(cReference);
    }

    /**
     * Selects a custom item.
     * <p>
     * See gtk_app_chooser_button_append_custom_item().
     * <p>
     * Use gtk_app_chooser_refresh() to bring the selection to its initial state.
     *
     * @param customItemName The name of the custom item.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void selectCustomItem(String customItemName) {
        if (customItemName != null) {
            library.gtk_app_chooser_button_set_active_custom_item(cReference, customItemName);
        }
    }

    /**
     * Sets the text to display at the top of the dialog.
     * <p>
     * If the heading is not set, the dialog displays a default text.
     *
     * @param heading A string containing Pango markup.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setDialogHeading(String heading) {
        if (heading != null) {
            library.gtk_app_chooser_button_set_heading(cReference, heading);
        }
    }

    /**
     * Sets whether the dialog should be modal.
     *
     * @param isModal TRUE to make the dialog modal.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void shouldBeModal(boolean isModal) {
        library.gtk_app_chooser_button_set_modal(cReference, isModal);
    }

    /**
     * Sets whether the dropdown menu of this button should show an entry to trigger a GtkAppChooserDialog.
     *
     * @param doesShowDialogItem True if the dropdown menu shows an item for a GtkAppChooserDialog
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void shouldShowChooseOtherApplicationDialog(boolean doesShowDialogItem) {
        library.gtk_app_chooser_button_set_show_dialog_item(cReference, doesShowDialogItem);
    }

    /**
     * Sets whether the dropdown menu of this button should show the default application for the given content type at
     * top.
     *
     * @param doesShowDefaultAtTop True if the dropdown menu shows the default application at top; False otherwise
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void shouldShowDefaultApplicationAtTop(boolean doesShowDefaultAtTop) {
        library.gtk_app_chooser_button_set_show_default_item(cReference, doesShowDefaultAtTop);
    }

    protected static class GtkAppChooserButtonLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }


        /**
         * Appends a custom item to the list of applications that is shown in the popup.
         * <p>
         * The item name must be unique per-widget. Clients can use the provided name as a detail for the
         * GtkAppChooserButton::custom-item-activated signal, to add a callback for the activation of a particular
         * custom item in the list.
         * <p>
         * See also gtk_app_chooser_button_append_separator().
         *
         * @param self  self
         * @param name  The name of the custom item.
         * @param label The label for the custom item.
         * @param icon  The icon for the custom item. Type: GIcon
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_app_chooser_button_append_custom_item(Pointer self, String name, String label, Pointer icon);

        /**
         * Appends a separator to the list of applications that is shown in the popup.
         *
         * @param self self
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_app_chooser_button_append_separator(Pointer self);

        /**
         * Returns the text to display at the top of the dialog.
         *
         * @param self self
         * @return The text to display at the top of the dialog, or NULL, in which case a default text is displayed.
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native String gtk_app_chooser_button_get_heading(Pointer self);

        /**
         * Gets whether the dialog is modal.
         *
         * @param self self
         * @return TRUE if the dialog is modal.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native boolean gtk_app_chooser_button_get_modal(Pointer self);

        /**
         * Returns whether the dropdown menu should show the default application at the top.
         *
         * @param self self
         * @return The value of GtkAppChooserButton:show-default-item
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native boolean gtk_app_chooser_button_get_show_default_item(Pointer self);

        /**
         * Returns whether the dropdown menu shows an item for a GtkAppChooserDialog.
         *
         * @param self self
         * @return The value of GtkAppChooserButton:show-dialog-item
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native boolean gtk_app_chooser_button_get_show_dialog_item(Pointer self);

        /**
         * Creates a new GtkAppChooserButton for applications that can handle content of the given type.
         *
         * @param contentType The content type to show applications for.
         * @return A newly created GtkAppChooserButton
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_app_chooser_button_new(String contentType);

        /**
         * Selects a custom item.
         * <p>
         * See gtk_app_chooser_button_append_custom_item().
         * <p>
         * Use gtk_app_chooser_refresh() to bring the selection to its initial state.
         *
         * @param self self
         * @param name The name of the custom item.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_app_chooser_button_set_active_custom_item(Pointer self, String name);

        /**
         * Sets the text to display at the top of the dialog.
         * <p>
         * If the heading is not set, the dialog displays a default text.
         *
         * @param self    self
         * @param heading A string containing Pango markup.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_app_chooser_button_set_heading(Pointer self, String heading);

        /**
         * Sets whether the dialog should be modal.
         *
         * @param self  self
         * @param modal TRUE to make the dialog modal.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_app_chooser_button_set_modal(Pointer self, boolean modal);

        /**
         * Sets whether the dropdown menu of this button should show the default application for the given content type
         * at top.
         *
         * @param self    self
         * @param setting The new value for GtkAppChooserButton:show-default-item
         */
        public native void gtk_app_chooser_button_set_show_default_item(Pointer self, boolean setting);

        /**
         * Sets whether the dropdown menu of this button should show an entry to trigger a GtkAppChooserDialog.
         *
         * @param self    self
         * @param setting The new value for GtkAppChooserButton:show-dialog-item
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_app_chooser_button_set_show_dialog_item(Pointer self, boolean setting);

    }

    public static class Signals extends GtkWidget.Signals {
        /**
         * Emitted to when the button is activated.
         * <p>
         * The ::activate signal on GtkAppChooserButton is an action signal and emitting it causes the button to pop up
         * its dialog.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        /**
         * Emitted when the active application changes.
         */
        public static final Signals CHANGED = new Signals("changed");

        /**
         * Emitted when a custom item is activated.
         * <p>
         * Use gtk_app_chooser_button_append_custom_item(), to add custom items.
         */
        public static final Signals CUSTOM_ITEM_ACTIVATED = new Signals("custom-item-activated");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }
}
