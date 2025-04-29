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
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkShortcutLabel displays a single keyboard shortcut or gesture.
 * <p>
 * The main use case for GtkShortcutLabel is inside a GtkShortcutsWindow.
 */
public class GtkShortcutLabel extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {
    private static final GtkShortcutLabelLibrary library = new GtkShortcutLabelLibrary();

    public GtkShortcutLabel(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkShortcutLabel with accelerator set.
     *
     * @param accelerator The initial accelerator.
     */
    public GtkShortcutLabel(String accelerator) {
        super(library.gtk_shortcut_label_new(accelerator));
    }

    /**
     * Retrieves the current accelerator of self.
     *
     * @return The current accelerator, if defined
     */
    public Option<String> getAccelerator() {
        return new Option<>(library.gtk_shortcut_label_get_accelerator(getCReference()));
    }

    /**
     * Sets the accelerator to be displayed by self.
     *
     * @param accelerator The new accelerator.
     */
    public void setAccelerator(String accelerator) {
        library.gtk_shortcut_label_set_accelerator(getCReference(), accelerator);
    }

    /**
     * Retrieves the text that is displayed when no accelerator is set.
     *
     * @return The current text displayed when no accelerator is set, if defined
     */
    public Option<String> getDisabledText() {
        return new Option<>(library.gtk_shortcut_label_get_disabled_text(getCReference()));
    }

    /**
     * Sets the text to be displayed by self when no accelerator is set.
     *
     * @param text The text to be displayed when no accelerator is set.
     */
    public void setDisabledText(String text) {
        library.gtk_shortcut_label_set_disabled_text(getCReference(), text);
    }

    protected static class GtkShortcutLabelLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the current accelerator of self.
         *
         * @param self self
         * @return The current accelerator.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_shortcut_label_get_accelerator(Pointer self);

        /**
         * Retrieves the text that is displayed when no accelerator is set.
         *
         * @param self self
         * @return The current text displayed when no accelerator is set.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_shortcut_label_get_disabled_text(Pointer self);

        /**
         * Creates a new GtkShortcutLabel with accelerator set.
         *
         * @param accelerator The initial accelerator.
         * @return A newly-allocated GtkShortcutLabel
         */
        public native Pointer gtk_shortcut_label_new(String accelerator);

        /**
         * Sets the accelerator to be displayed by self.
         *
         * @param self        self
         * @param accelerator The new accelerator.
         */
        public native void gtk_shortcut_label_set_accelerator(Pointer self, String accelerator);

        /**
         * Sets the text to be displayed by self when no accelerator is set.
         *
         * @param self          self
         * @param disabled_text The text to be displayed when no accelerator is set.
         */
        public native void gtk_shortcut_label_set_disabled_text(Pointer self, String disabled_text);
    }
}
