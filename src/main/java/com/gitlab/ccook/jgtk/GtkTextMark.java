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

import com.gitlab.ccook.jgtk.gtk.GtkTextBuffer;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public class GtkTextMark extends JGTKObject {
    public GtkTextMark(String name, boolean hasLeftGravity) {
        this(library.gtk_text_mark_new(name, hasLeftGravity));
    }

    public GtkTextMark(Pointer cReference) {
        super(cReference);
    }

    /**
     * Gets the buffer this mark is located inside.
     * <p>
     * Returns NONE if the mark is deleted.
     *
     * @return The mark's GtkTextBuffer, if defined
     */
    public Option<GtkTextBuffer> getBuffer() {
        Option<Pointer> p = new Option<>(library.gtk_text_mark_get_buffer(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkTextBuffer(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Returns the mark name.
     * <p>
     * Returns NONE for anonymous marks.
     *
     * @return Mark name, if defined
     */
    public Option<String> getName() {
        return new Option<>(library.gtk_text_mark_get_name(getCReference()));
    }

    /**
     * Returns TRUE if the mark has been removed from its buffer.
     * <p>
     * See gtk_text_buffer_add_mark() for a way to add it to a buffer again.
     *
     * @return Whether the mark is deleted.
     */
    public boolean hasBeenDeleted() {
        return library.gtk_text_mark_get_deleted(getCReference());
    }

    /**
     * Determines whether the mark has left gravity.
     *
     * @return TRUE if the mark has left gravity
     */
    public boolean hasLeftGravity() {
        return library.gtk_text_mark_get_left_gravity(getCReference());
    }

    /**
     * Returns TRUE if the mark is visible.
     * <p>
     * A cursor is displayed for visible marks.
     *
     * @return TRUE if visible.
     */
    public boolean isVisible() {
        return library.gtk_text_mark_get_visible(getCReference());
    }

    /**
     * Sets the visibility of mark.
     * <p>
     * The insertion point is normally visible, i.e. you can see it as
     * a vertical bar. Also, the text widget uses a visible mark to
     * indicate where a drop will occur when dragging-and-dropping text.
     * Most other marks are not visible.
     *
     * @param isVisible visibility of mark
     */
    public void setVisible(boolean isVisible) {
        library.gtk_text_mark_set_visible(getCReference(), isVisible);
    }
}
