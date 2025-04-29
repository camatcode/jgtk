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
package com.gitlab.ccook.jgtk.enums;

public enum GtkSizeRequestMode {
    GTK_SIZE_REQUEST_HEIGHT_FOR_WIDTH(0),
    GTK_SIZE_REQUEST_WIDTH_FOR_HEIGHT(1),
    GTK_SIZE_REQUEST_CONSTANT_SIZE(2);

    GtkSizeRequestMode(int cValue) {
    }

    public static GtkSizeRequestMode getModeFromCValue(int cValue) {
        return GtkSizeRequestMode.values()[cValue];
    }

}
