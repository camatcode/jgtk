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
package com.gitlab.ccook.jgtk.interfaces;

import com.gitlab.ccook.jgtk.enums.GtkOrientation;

public interface GtkOrientable extends GtkInterface {
    default GtkOrientation getOrientation() {
        return GtkOrientation.getOrientationByValue(library.gtk_orientable_get_orientation(getCReference()));
    }

    /**
     * Sets the orientation of the orientable.
     *
     * @param o The orientable's new orientation.
     */
    default void setOrientation(GtkOrientation o) {
        if (o != null) {
            library.gtk_orientable_set_orientation(getCReference(), o.getCValue());
        }
    }
}
