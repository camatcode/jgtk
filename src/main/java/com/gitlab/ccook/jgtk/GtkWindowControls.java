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
package com.gitlab.ccook.jgtk;

import com.gitlab.ccook.jgtk.enums.GtkPackType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


public class GtkWindowControls extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {
    public GtkWindowControls(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkWindowControls.
     *
     * @param type The side.
     */
    public GtkWindowControls(GtkPackType type) {
        super(library.gtk_window_controls_new(GtkPackType.getCValueFromType(type)));
    }

    /**
     * Gets the decoration layout of this GtkWindowControls.
     *
     * @return The decoration layout or NONE if it is unset.
     */
    public Option<String> getDecorationLayout() {
        return new Option<>(library.gtk_window_controls_get_decoration_layout(getCReference()));
    }

    /**
     * Sets the decoration layout for the title buttons.
     * <p>
     * This overrides the GtkSettings:gtk-decoration-layout setting.
     * <p>
     * The format of the string is button names, separated by commas. A colon separates the buttons that should appear
     * on the left from those on the right. Recognized button names are minimize, maximize, close and icon
     * (the window icon).
     * <p>
     * For example, "icon:minimize,maximize,close" specifies an icon on the left, and minimize, maximize and close
     * buttons on the right.
     * <p>
     * If GtkWindowControls:side value is GTK_PACK_START, self will display the part before the colon, otherwise after
     * that.
     *
     * @param decorationLayout A decoration layout, or NULL to unset the layout.
     *                         <p>
     *                         The argument can be NULL.
     */
    public void setDecorationLayout(String decorationLayout) {
        library.gtk_window_controls_set_decoration_layout(getCReference(), decorationLayout);
    }

    /**
     * Gets the side to which this GtkWindowControls instance belongs.
     *
     * @return The side.
     */
    public GtkPackType getSide() {
        return GtkPackType.getTypeFromCValue(library.gtk_window_controls_get_side(getCReference()));
    }

    /**
     * Determines which part of decoration layout the GtkWindowControls uses.
     *
     * @param type A side.
     */
    public void setSide(GtkPackType type) {
        if (type != null) {
            library.gtk_window_controls_set_side(getCReference(), GtkPackType.getCValueFromType(type));
        }
    }

    /**
     * Gets whether the widget has any window buttons.
     *
     * @return TRUE if the widget has window buttons, otherwise FALSE.
     */
    public boolean isEmpty() {
        return library.gtk_window_controls_get_empty(getCReference());
    }
}
