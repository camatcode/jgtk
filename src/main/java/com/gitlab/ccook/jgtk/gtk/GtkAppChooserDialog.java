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

import com.gitlab.ccook.jgtk.GFile;
import com.gitlab.ccook.jgtk.GtkShortcutManager;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GtkDialogFlags;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkAppChooserDialog shows a GtkAppChooserWidget inside a GtkDialog.
 * <p>
 * Note that GtkAppChooserDialog does not have any interesting methods of its own. Instead, you should get the embedded
 * GtkAppChooserWidget using gtk_app_chooser_dialog_get_widget() and call its methods if the generic GtkAppChooser
 * interface is not sufficient for your needs.
 * <p>
 * To set the heading that is shown above the GtkAppChooserWidget, use gtk_app_chooser_dialog_set_heading().
 */
@SuppressWarnings({"deprecation", "DeprecatedIsStillUsed", "RedundantSuppression"})
@Deprecated
public class GtkAppChooserDialog extends GtkDialog implements GtkAccessible, GtkAppChooser, GtkBuildable, GtkConstraintTarget, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkAppChooserDialog.GtkAppChooserDialogLibrary library = new GtkAppChooserDialog.GtkAppChooserDialogLibrary();

    /**
     * Creates a new GtkAppChooserDialog for the provided GFile.
     * <p>
     * The dialog will show applications that can open the file.
     *
     * @param parent A GtkWindow
     *               <p>
     *               The argument can be NULL.
     * @param flags  Flags for this dialog.
     * @param file   A GFile
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public GtkAppChooserDialog(GtkWindow parent, GFile file, GtkDialogFlags... flags) {
        super(library.gtk_app_chooser_dialog_new(pointerOrNull(parent), GtkDialogFlags.getCValueFromFlags(flags), pointerOrNull(file)));
    }

    /**
     * Creates a new GtkAppChooserDialog for the provided GFile.
     * <p>
     * The dialog will show applications that can open the file.
     *
     * @param flags Flags for this dialog.
     * @param file  A GFile
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public GtkAppChooserDialog(GFile file, GtkDialogFlags... flags) {
        this(null, file, flags);
    }

    /**
     * Creates a new GtkAppChooserDialog for the provided content type.
     * <p>
     * The dialog will show applications that can open the content type.
     *
     * @param parent      A GtkWindow
     *                    <p>
     *                    The argument can be NULL.
     * @param contentType A content type string.
     * @param flags       Flags for this dialog.
     */
    public GtkAppChooserDialog(GtkWindow parent, String contentType, GtkDialogFlags... flags) {
        super(library.gtk_app_chooser_dialog_new_for_content_type(pointerOrNull(parent), GtkDialogFlags.getCValueFromFlags(flags), contentType));
    }

    /**
     * Creates a new GtkAppChooserDialog for the provided content type.
     * <p>
     * The dialog will show applications that can open the content type.
     *
     * @param contentType A content type string.
     * @param flags       Flags for this dialog.
     */
    public GtkAppChooserDialog(String contentType, GtkDialogFlags... flags) {
        this(null, contentType, flags);
    }

    /**
     * Returns the text to display at the top of the dialog.
     *
     * @return The text to display at the top of the dialog, if defined. If not defined, in which case a default text
     *         is displayed.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public Option<String> getHeading() {
        return new Option<>(library.gtk_app_chooser_dialog_get_heading(getCReference()));
    }

    /**
     * Sets the text to display at the top of the dialog.
     * <p>
     * If the heading is not set, the dialog displays a default text.
     *
     * @param headingMarkup A string containing Pango markup.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public void setHeading(String headingMarkup) {
        library.gtk_app_chooser_dialog_set_heading(getCReference(), headingMarkup);
    }

    /**
     * Returns the GtkAppChooserWidget of this dialog.
     *
     * @return The GtkAppChooserWidget of self.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public GtkAppChooserWidget getWidget() {
        return (GtkAppChooserWidget) JGTKObject.newObjectFromType(library.gtk_app_chooser_dialog_get_widget(getCReference()), GtkAppChooserWidget.class);
    }

    @SuppressWarnings({"DeprecatedIsStillUsed", "RedundantSuppression"})
    protected static class GtkAppChooserDialogLibrary extends GtkDialogLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new GtkAppChooserDialog for the provided GFile.
         * <p>
         * The dialog will show applications that can open the file.
         *
         * @param parent A GtkWindow
         *               <p>
         *               The argument can be NULL.
         * @param flags  Flags for this dialog. Type: GtkDialogFlags
         * @param file   A GFile
         * @return A newly created GtkAppChooserDialog
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_app_chooser_dialog_new(Pointer parent, int flags, Pointer file);

        /**
         * Creates a new GtkAppChooserDialog for the provided content type.
         * <p>
         * The dialog will show applications that can open the content type.
         *
         * @param parent       A GtkWindow
         *                     <p>
         *                     The argument can be NULL.
         * @param flags        Flags for this dialog. Type: GtkDialogFlags
         * @param content_type A content type string.
         * @return A newly created GtkAppChooserDialog
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_app_chooser_dialog_new_for_content_type(Pointer parent, int flags, String content_type);

        /**
         * Returns the text to display at the top of the dialog.
         *
         * @param self self
         * @return The text to display at the top of the dialog, or NULL, in which case a default text is displayed.
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native String gtk_app_chooser_dialog_get_heading(Pointer self);

        /**
         * Returns the GtkAppChooserWidget of this dialog.
         *
         * @param self self
         * @return The GtkAppChooserWidget of self.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_app_chooser_dialog_get_widget(Pointer self);

        /**
         * Sets the text to display at the top of the dialog.
         * <p>
         * If the heading is not set, the dialog displays a default text.
         *
         * @param self    self
         * @param heading A string containing Pango markup.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_app_chooser_dialog_set_heading(Pointer self, String heading);
    }
}
