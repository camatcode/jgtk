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
import com.gitlab.ccook.jgtk.bitfields.GtkDialogFlags;
import com.gitlab.ccook.jgtk.enums.GtkButtonsType;
import com.gitlab.ccook.jgtk.enums.GtkMessageType;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * tkMessageDialog presents a dialog with some message text.
 * <p>
 * It's simply a convenience widget; you could construct the equivalent of GtkMessageDialog from GtkDialog without too
 * much effort, but GtkMessageDialog saves typing.
 * <p>
 * The easiest way to do a modal message dialog is to use the GTK_DIALOG_MODAL flag, which will call
 * gtk_window_set_modal() internally. The dialog will prevent interaction with the parent window until it's hidden or
 * destroyed. You can use the GtkDialog::response signal to know when the user dismissed the dialog.
 *
 * @deprecated Deprecated since: 4.10. Use GtkAlertDialog instead.
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class GtkMessageDialog extends GtkDialog implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkMessageDialogLibrary library = new GtkMessageDialogLibrary();


    public GtkMessageDialog(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new message dialog.
     * <p>
     * This is a simple dialog with some text the user may want to see. When the user clicks a button a "response"
     * signal is emitted with response IDs from GtkResponseType. See GtkDialog for more details.
     *
     * @param parent      Transient parent.
     *                    <p>
     *                    The argument can be NULL.
     * @param flags       Flags.
     * @param type        Type of message.
     * @param buttons     Set of buttons to use.
     * @param pangoMarkup Printf()-style format string.
     *                    <p>
     *                    The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkAlertDialog instead.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public GtkMessageDialog(GtkWindow parent, GtkMessageType type, GtkButtonsType buttons, String pangoMarkup, GtkDialogFlags... flags) {
        super(handleCtor(parent, type, buttons, flags));
        setMarkup(pangoMarkup);
    }

    /**
     * Creates a new message dialog.
     * <p>
     * This is a simple dialog with some text the user may want to see. When the user clicks a button a "response"
     * signal is emitted with response IDs from GtkResponseType. See GtkDialog for more details.
     *
     * @param flags       Flags.
     * @param type        Type of message.
     * @param buttons     Set of buttons to use.
     * @param pangoMarkup Printf()-style format string.
     *                    <p>
     *                    The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkAlertDialog instead.
     */
    public GtkMessageDialog(GtkMessageType type, GtkButtonsType buttons, String pangoMarkup, GtkDialogFlags... flags) {
        this(null, type, buttons, pangoMarkup, flags);
    }

    private static Pointer handleCtor(GtkWindow parent, GtkMessageType type, GtkButtonsType buttons, GtkDialogFlags[] flags) {
        return library.gtk_message_dialog_new(pointerOrNull(parent), GtkDialogFlags.getCValueFromFlags(flags), GtkMessageType.getCValueFromType(type), GtkButtonsType.getCValueFromType(buttons), null);
    }

    /**
     * Sets the text of the message dialog.
     *
     * @param pangoMarkup String with Pango markup.
     * @deprecated Deprecated since: 4.10. Use GtkAlertDialog instead.
     */
    public void setMarkup(String pangoMarkup) {
        library.gtk_message_dialog_set_markup(getCReference(), pangoMarkup);
    }

    protected static class GtkMessageDialogLibrary extends GtkDialogLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new message dialog.
         * <p>
         * This is a simple dialog with some text the user may want to see. When the user clicks a button a "response"
         * signal is emitted with response IDs from GtkResponseType. See GtkDialog for more details.
         *
         * @param parent  Transient parent. Type: GtkWindow
         *                <p>
         *                The argument can be NULL.
         * @param flags   Flags. Type: GtkDialogFlags
         * @param type    Type of message. Type: GtkMessageType
         * @param buttons Set of buttons to use. Type: GtkButtonsType
         * @param msg     Printf()-style format string.
         *                <p>
         *                The argument can be NULL.
         * @return A new GtkMessageDialog
         * @deprecated Deprecated since: 4.10. Use GtkAlertDialog instead.
         */
        public Pointer gtk_message_dialog_new(Pointer parent, int flags, int type, int buttons, String[] msg) {
            return INSTANCE.gtk_message_dialog_new(parent, flags, type, buttons, msg);
        }

        /**
         * Sets the text of the message dialog.
         *
         * @param self   self
         * @param markup String with Pango markup.
         * @deprecated Deprecated since: 4.10. Use GtkAlertDialog instead.
         */
        public native void gtk_message_dialog_set_markup(Pointer self, String markup);
    }
}
