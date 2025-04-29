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
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkOrientable;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkSeparator is a horizontal or vertical separator widget.
 * <p>
 * A GtkSeparator can be used to group the widgets within a window. It displays a line with a shadow to make it appear
 * sunken into the interface.
 */
public class GtkSeparator extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkSeparatorLibrary library = new GtkSeparatorLibrary();

    public GtkSeparator(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkSeparator with the given orientation.
     *
     * @param orientation The separator's orientation.
     */
    public GtkSeparator(GtkOrientation orientation) {
        super(library.gtk_separator_new(GtkOrientation.getCValueFromOrientation(orientation)));
    }

    protected static class GtkSeparatorLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new GtkSeparator with the given orientation.
         *
         * @param orientation The separator's orientation. Type: GtkOrientation
         * @return A new GtkSeparator.
         */
        public native Pointer gtk_separator_new(int orientation);
    }
}
