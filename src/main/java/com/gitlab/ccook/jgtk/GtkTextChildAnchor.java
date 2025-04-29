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
package com.gitlab.ccook.jgtk;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.util.ArrayList;
import java.util.List;

public class GtkTextChildAnchor extends JGTKObject {
    public GtkTextChildAnchor() {
        this(library.gtk_text_child_anchor_new());
    }

    public GtkTextChildAnchor(Pointer cReference) {
        super(cReference);
    }

    /**
     * Gets a list of all widgets anchored at this child anchor.
     * <p>
     * The order in which the widgets are returned is not defined.
     *
     * @return An array of widgets anchored at anchor.
     */
    public List<GtkWidget> getWidgets() {
        PointerByReference len = new PointerByReference();
        Pointer[] pointers = library.gtk_text_child_anchor_get_widgets(getCReference(), len);
        List<GtkWidget> w = new ArrayList<>();
        for (int i = 0; i < len.getPointer().getInt(0); i++) {
            Pointer p = pointers[i];
            if (p != Pointer.NULL) {
                w.add((GtkWidget) JGTKObject.newObjectFromType(p, GtkWidget.class));
            }
        }
        return w;
    }

    /**
     * Determines whether a child anchor has been deleted from the buffer.
     *
     * @return TRUE if the child anchor has been deleted from its buffer.
     */
    public boolean hasBeenDeleted() {
        return library.gtk_text_child_anchor_get_deleted(getCReference());
    }
}
