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
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkFontChooser;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * The GtkFontChooserDialog widget is a dialog for selecting a font.
 * <p>
 * GtkFontChooserDialog implements the GtkFontChooser interface and does not provide much API of its own.
 *
 * @deprecated Deprecated since: 4.10. Use GtkFontDialog instead.
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class GtkFontChooserDialog extends GtkDialog implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkFontChooser, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkFontChooserDialogLibrary library = new GtkFontChooserDialogLibrary();

    public GtkFontChooserDialog(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkFontChooserDialog.
     *
     * @param parent Transient parent of the dialog.
     *               <p>
     *               The argument can be NULL.
     * @param title  Title of the dialog.
     *               <p>
     *               The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog instead.
     */
    public GtkFontChooserDialog(GtkWindow parent, String title) {
        super(library.gtk_font_chooser_dialog_new(title, pointerOrNull(parent)));
    }

    /**
     * Creates a new GtkFontChooserDialog.
     *
     * @param parent Transient parent of the dialog.
     *               <p>
     *               The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog instead.
     */
    public GtkFontChooserDialog(GtkWindow parent) {
        this(parent, null);
    }

    /**
     * Creates a new GtkFontChooserDialog.
     *
     * @param title Title of the dialog.
     *              <p>
     *              The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog instead.
     */
    public GtkFontChooserDialog(String title) {
        this(null, title);
    }

    protected static class GtkFontChooserDialogLibrary extends GtkDialogLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new GtkFontChooserDialog.
         *
         * @param parent Transient parent of the dialog.
         *               <p>
         *               The argument can be NULL.
         * @param title  Title of the dialog.
         *               <p>
         *               The argument can be NULL.
         * @return A new GtkFontChooserDialog
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog instead.
         */
        public native Pointer gtk_font_chooser_dialog_new(String title, Pointer parent);
    }
}
