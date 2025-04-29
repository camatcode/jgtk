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
import com.gitlab.ccook.jgtk.enums.GtkFileChooserAction;
import com.gitlab.ccook.jgtk.enums.GtkResponseType;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkFileChooser;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Pointer;

/**
 * GtkFileChooserDialog is a dialog suitable for use with "File Open" or "File Save" commands.
 * This widget works by putting a GtkFileChooserWidget inside a GtkDialog. It exposes the GtkFileChooser interface, so
 * you can use all the GtkFileChooser functions on the file chooser dialog as well as those for GtkDialog.
 * <p>
 * Note that GtkFileChooserDialog does not have any methods of its own. Instead, you should use the functions that work
 * on a GtkFileChooser.
 * <p>
 * If you want to integrate well with the platform you should use the GtkFileChooserNative API, which will use a
 * platform-specific dialog if available and fall back to GtkFileChooserDialog otherwise.
 *
 * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
 */
@SuppressWarnings({"DeprecatedIsStillUsed"})
public class GtkFileChooserDialog extends GtkDialog implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkFileChooser, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkFileChooserDialogLibrary library = new GtkFileChooserDialogLibrary();

    public GtkFileChooserDialog(Pointer cReference) {
        super(cReference);
    }

    /**
     * Creates a new GtkFileChooserDialog.
     * <p>
     * This function is analogous to gtk_dialog_new_with_buttons().
     *
     * @param title   Title of the dialog.
     *                <p>
     *                The argument can be NULL.
     * @param parent  Transient parent of the dialog.
     *                <p>
     *                The argument can be NULL.
     * @param action  Open or save mode for the dialog.
     * @param buttons Pairs of button labels and their associated GtkResponses
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    @SafeVarargs
    public GtkFileChooserDialog(String title, GtkWindow parent, GtkFileChooserAction action, Pair<String, GtkResponseType>... buttons) {
        super(handleCtor(title, parent, action, buttons));
        for (int i = 1; i < buttons.length; i++) {
            this.addButton(buttons[i].getFirst(), buttons[i].getSecond().getCValue());
        }
    }

    @SafeVarargs
    private static Pointer handleCtor(String title, GtkWindow parent, GtkFileChooserAction action, Pair<String, GtkResponseType>... buttons) {
        Pair<String, GtkResponseType> firstButton = new Pair<>("Open", GtkResponseType.GTK_RESPONSE_ACCEPT);
        if (buttons.length != 0) {
            firstButton = buttons[0];
        }
        return library.gtk_file_chooser_dialog_new(title, pointerOrNull(parent), GtkFileChooserAction.getCValue(action), firstButton.getFirst(), firstButton.getSecond().getCValue());
    }

    public static class GtkFileChooserDialogLibrary extends GtkDialogLibrary {

        /**
         * Creates a new GtkFileChooserDialog.
         * <p>
         * This function is analogous to gtk_dialog_new_with_buttons().
         *
         * @param title    Title of the dialog.
         *                 <p>
         *                 The argument can be NULL.
         * @param parent   Transient parent of the dialog.
         *                 <p>
         *                 The argument can be NULL.
         * @param action   Open or save mode for the dialog.
         * @param label    Text to go in the first button.
         *                 <p>
         *                 The argument can be NULL.
         * @param response Response ID for the first button
         * @return A new GtkFileChooserDialog
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public Pointer gtk_file_chooser_dialog_new(String title, Pointer parent, int action, String label, int response) {
            return INSTANCE.gtk_file_chooser_dialog_new(title, parent, action, label, response, Pointer.NULL);
        }
    }
}
