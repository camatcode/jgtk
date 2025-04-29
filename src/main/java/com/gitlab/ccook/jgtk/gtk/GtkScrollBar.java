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

import com.gitlab.ccook.jgtk.GtkAdjustment;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkOrientable;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * The GtkScrollbar widget is a horizontal or vertical scrollbar.
 * Its position and movement are controlled by the adjustment that is passed to or created by gtk_scrollbar_new().
 * See GtkAdjustment for more details. The GtkAdjustment:value field sets the position of the thumb and must be between
 * GtkAdjustment:lower and GtkAdjustment:upper - GtkAdjustment:page-size. The GtkAdjustment:page-size represents the
 * size of the visible scrollable area.
 * <p>
 * The fields GtkAdjustment:step-increment and GtkAdjustment:page-increment fields are added to or subtracted from the
 * GtkAdjustment:value when the user asks to move by a step (using e.g. the cursor arrow keys) or by a page
 * (using e.g. the Page Down/Up keys).
 */
public class GtkScrollBar extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkScrollBarLibrary library = new GtkScrollBarLibrary();

    public GtkScrollBar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new scrollbar with the given orientation.
     *
     * @param orientation The scrollbar's orientation.
     * @param adjustment  The GtkAdjustment to use, or NULL to create a new adjustment.
     *                    <p>
     *                    The argument can be NULL.
     */
    public GtkScrollBar(GtkOrientation orientation, GtkAdjustment adjustment) {
        super(library.gtk_scrollbar_new(GtkOrientation.getCValueFromOrientation(orientation), pointerOrNull(adjustment)));
    }

    /**
     * Creates a new scrollbar with the given orientation.
     *
     * @param orientation The scrollbar's orientation.
     */
    public GtkScrollBar(GtkOrientation orientation) {
        super(library.gtk_scrollbar_new(GtkOrientation.getCValueFromOrientation(orientation), Pointer.NULL));
    }

    /**
     * Returns the scrollbar's adjustment.
     *
     * @return The scrollbar's adjustment.
     */
    public GtkAdjustment getAdjustment() {
        return new GtkAdjustment(library.gtk_scrollbar_get_adjustment(getCReference()));
    }

    /**
     * Makes the scrollbar use the given adjustment.
     *
     * @param adj The adjustment to set.
     *            <p>
     *            The argument can be NULL.
     */
    public void setAdjustment(GtkAdjustment adj) {
        library.gtk_scrollbar_set_adjustment(getCReference(), pointerOrNull(adj));
    }

    protected static class GtkScrollBarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns the scrollbar's adjustment.
         *
         * @param self self
         * @return The scrollbar's adjustment. Type: GtkAdjustment
         */
        public native Pointer gtk_scrollbar_get_adjustment(Pointer self);

        /**
         * Creates a new scrollbar with the given orientation.
         *
         * @param orientation The scrollbar's orientation. Type: GtkOrientation
         * @param adjustment  The GtkAdjustment to use, or NULL to create a new adjustment.
         *                    <p>
         *                    The argument can be NULL.
         * @return The new GtkScrollbar.
         */
        public native Pointer gtk_scrollbar_new(int orientation, Pointer adjustment);

        /**
         * Makes the scrollbar use the given adjustment.
         *
         * @param self       self
         * @param adjustment The adjustment to set. Type: GtkAdjustment
         *                   <p>
         *                   The argument can be NULL.
         */
        public native void gtk_scrollbar_set_adjustment(Pointer self, Pointer adjustment);
    }
}
