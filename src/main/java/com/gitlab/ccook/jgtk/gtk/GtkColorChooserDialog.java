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
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkColorChooser;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * A dialog for choosing a color.
 * <p>
 * GtkColorChooserDialog implements the GtkColorChooser interface and does not provide much API of its own.
 * <p>
 * To create a GtkColorChooserDialog, use gtk_color_chooser_dialog_new().
 * <p>
 * To change the initially selected color, use gtk_color_chooser_set_rgba(). To get the selected color use
 * gtk_color_chooser_get_rgba().
 * <p>
 * GtkColorChooserDialog has been deprecated in favor of GtkColorDialog.
 *
 * @since 4.0
 * @deprecated Since 4.10. Use GtkColorDialog instead.
 */
@SuppressWarnings("DeprecatedIsStillUsed")
public class GtkColorChooserDialog extends GtkDialog implements GtkAccessible, GtkBuildable, GtkColorChooser, GtkConstraintTarget, GtkNative, GtkRoot, GtkShortcutManager {


    private static final GtkColorChooserDialog.GtkColorChooserDialogLibrary library = new GtkColorChooserDialog.GtkColorChooserDialogLibrary();

    /**
     * Creates a new GtkColorChooserDialog.
     *
     * @param title  Title of the dialog.
     *               <p>
     *               The argument can be NULL.
     * @param parent Transient parent of the dialog.
     *               <p>
     *               The argument can be NULL.
     * @deprecated Since 4.10. Use GtkColorDialog instead.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    public GtkColorChooserDialog(String title, GtkWindow parent) {
        super(library.gtk_color_chooser_dialog_new(title, pointerOrNull(parent)));
    }

    /**
     * Creates a new GtkColorChooserDialog.
     *
     * @param title Title of the dialog.
     *              <p>
     *              The argument can be NULL.
     * @deprecated Since 4.10. Use GtkColorDialog instead.
     */
    public GtkColorChooserDialog(String title) {
        this(title, null);
    }

    /**
     * Creates a new GtkColorChooserDialog.
     *
     * @param parent Transient parent of the dialog.
     *               <p>
     *               The argument can be NULL.
     * @deprecated Since 4.10. Use GtkColorDialog instead.
     */
    public GtkColorChooserDialog(GtkWindow parent) {
        this(null, parent);
    }

    protected static class GtkColorChooserDialogLibrary extends GtkWidget.GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new GtkColorChooserDialog.
         *
         * @param title  Title of the dialog.
         *               <p>
         *               The argument can be NULL.
         * @param parent Transient parent of the dialog.
         *               <p>
         *               The argument can be NULL.
         * @return A new GtkColorChooserDialog
         * @deprecated Since 4.10. Use GtkColorDialog instead.
         */
        public native Pointer gtk_color_chooser_dialog_new(String title, Pointer parent);
    }

}
