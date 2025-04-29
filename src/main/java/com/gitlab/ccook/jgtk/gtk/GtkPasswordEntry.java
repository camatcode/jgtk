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

import com.gitlab.ccook.jgtk.GMenuModel;
import com.gitlab.ccook.jgtk.GValue;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkPasswordEntry is an entry that has been tailored for entering secrets.
 * <p>
 * It does not show its contents in clear text, does not allow to copy it to the clipboard, and it shows a warning when
 * Caps Lock is engaged. If the underlying platform allows it, GtkPasswordEntry will also place the text in a
 * non-pageable memory area, to avoid it being written out to disk by the operating system.
 * <p>
 * Optionally, it can offer a way to reveal the contents in clear text.
 * <p>
 * GtkPasswordEntry provides only minimal API and should be used with the GtkEditable API.
 */
@SuppressWarnings("unchecked")
public class GtkPasswordEntry extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkEditable {
    private static final GtkPasswordEntryLibrary library = new GtkPasswordEntryLibrary();


    public GtkPasswordEntry(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a GtkPasswordEntry.
     */
    public GtkPasswordEntry() {
        super(library.gtk_password_entry_new());
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
     * Returns whether the entry is showing an icon to reveal the contents.
     *
     * @return TRUE if an icon is shown.
     */
    public boolean doesShowPeakIcon() {
        return library.gtk_password_entry_get_show_peek_icon(getCReference());
    }

    /**
     * Gets the menu model set with gtk_password_entry_set_extra_menu().
     *
     * @return The menu model, if defined
     */
    public Option<GMenuModel> getExtraMenu() {
        Option<Pointer> p = new Option<>(library.gtk_password_entry_get_extra_menu(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GMenuModel(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a menu model to add when constructing the context menu for entry.
     *
     * @param m A GMenuModel
     *          <p>
     *          The argument can be NULL.
     */
    public void setExtraMenu(GMenuModel m) {
        library.gtk_password_entry_set_extra_menu(getCReference(), pointerOrNull(m));
    }

    /**
     * @return The text that will be displayed in the GtkPasswordEntry when it is empty and unfocused, if defined
     */
    public Option<String> getPlaceholderText() {
        Option<GValue> val = getProperty("placeholder-text");
        if (val.isDefined()) {
            return new Option<>(val.get().getText());
        }
        return Option.NONE;
    }

    /**
     * @param placeholderText The text that will be displayed in the GtkPasswordEntry when it is empty and unfocused
     */
    public void setPlaceholderText(String placeholderText) {
        setProperty("placeholder-text", getCReference(), placeholderText);
    }

    /**
     * Sets whether the entry should have a clickable icon to reveal the contents.
     * <p>
     * Setting this to FALSE also hides the text again
     *
     * @param canShow Whether to show the peek icon.
     */
    public void shouldShowPeakIcon(boolean canShow) {
        library.gtk_password_entry_set_show_peek_icon(getCReference(), canShow);
    }

    @SuppressWarnings("SameParameterValue")
    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted when the entry is activated.
         * <p>
         * The keybindings for this signal are all forms of the Enter key.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkPasswordEntryLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the menu model set with gtk_password_entry_set_extra_menu().
         *
         * @param entry self
         * @return The menu model. Type: GMenuModel
         */
        public native Pointer gtk_password_entry_get_extra_menu(Pointer entry);

        /**
         * Returns whether the entry is showing an icon to reveal the contents.
         *
         * @param entry self
         * @return whether the entry is showing an icon to reveal the contents.
         */
        public native boolean gtk_password_entry_get_show_peek_icon(Pointer entry);

        /**
         * Creates a GtkPasswordEntry.
         *
         * @return A new GtkPasswordEntry. Type GtkPasswordEntry
         */
        public native Pointer gtk_password_entry_new();

        /**
         * Sets a menu model to add when constructing the context menu for entry.
         *
         * @param entry self
         * @param model A GMenuModel. Type: GMenuModel
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_password_entry_set_extra_menu(Pointer entry, Pointer model);

        /**
         * Sets whether the entry should have a clickable icon to reveal the contents.
         * <p>
         * Setting this to FALSE also hides the text again.
         *
         * @param entry          self
         * @param show_peek_icon Whether to show the peek icon.
         */
        public native void gtk_password_entry_set_show_peek_icon(Pointer entry, boolean show_peek_icon);
    }
}
