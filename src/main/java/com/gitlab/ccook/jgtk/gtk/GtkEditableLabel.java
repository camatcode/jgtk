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
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * A GtkEditableLabel is a label that allows users to edit the text by switching to an "edit mode".
 * <p>
 * GtkEditableLabel does not have API of its own, but it implements the GtkEditable interface.
 * <p>
 * The default bindings for activating the edit mode is to click or press the Enter key. The default bindings for
 * leaving the edit mode are the Enter key (to save the results) or the Escape key (to cancel the editing).
 */
public class GtkEditableLabel extends GtkWidget implements GtkAccessible, GtkBuildable, GtkEditable {

    private static final GtkEditableLabelLibrary library = new GtkEditableLabelLibrary();

    /**
     * Creates a new GtkEditableLabel widget.
     */
    public GtkEditableLabel() {
        super(handleCtor(""));
    }

    private static Pointer handleCtor(String label) {
        if (label == null) {
            label = "";
        }
        return library.gtk_editable_label_new(label);
    }

    /**
     * Creates a new GtkEditableLabel widget.
     *
     * @param label The text for the label.
     */
    public GtkEditableLabel(String label) {
        super(handleCtor(label));
    }

    public GtkEditableLabel(Pointer ref) {
        super(ref);
    }

    /**
     * Returns whether the label is currently in "editing mode".
     * <p>
     * The default bindings for activating the edit mode is to click or press the Enter key.
     * The default bindings for leaving the edit mode are the Enter key (to save the results) or
     * the Escape key (to cancel the editing).
     *
     * @return TRUE if label is currently in editing mode.
     */
    public boolean getCurrentEditingMode() {
        return library.gtk_editable_label_get_editing(getCReference());
    }

    /**
     * Switches the label into "editing mode".
     * <p>
     * The default bindings for activating the edit mode is to click or press the Enter key.
     * The default bindings for leaving the edit mode are the Enter key (to save the results) or
     * the Escape key (to cancel the editing).
     */
    public void startEditing() {
        library.gtk_editable_label_start_editing(getCReference());
    }

    /**
     * Switches the label out of "editing mode".
     * <p>
     * If commit is TRUE, the resulting text is kept as the GtkEditable:text property value, otherwise the resulting
     * text is discarded and the label will keep its previous GtkEditable:text property value.
     *
     * @param shouldCommitChanges Whether to set the edited text on the label.
     */
    public void stopEditing(boolean shouldCommitChanges) {
        library.gtk_editable_label_stop_editing(getCReference(), shouldCommitChanges);
    }

    protected static class GtkEditableLabelLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns whether the label is currently in "editing mode".
         *
         * @param self self
         * @return TRUE if self is currently in editing mode.
         */
        public native boolean gtk_editable_label_get_editing(Pointer self);

        /**
         * Creates a new GtkEditableLabel widget.
         *
         * @param str The text for the label.
         * @return The new GtkEditableLabel. Type:GtkEditableLabel
         */
        public native Pointer gtk_editable_label_new(String str);

        /**
         * Switches the label into "editing mode".
         *
         * @param self self
         */
        public native void gtk_editable_label_start_editing(Pointer self);

        /**
         * Switches the label out of "editing mode".
         * <p>
         * If commit is TRUE, the resulting text is kept as the GtkEditable:text property value, otherwise the
         * resulting text is discarded and the label will keep its previous GtkEditable:text property value.
         *
         * @param self   self
         * @param commit Whether to set the edited text on the label.
         */
        public native void gtk_editable_label_stop_editing(Pointer self, boolean commit);
    }


}
