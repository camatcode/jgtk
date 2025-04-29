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

public enum GtkIconSize {
    GTK_ICON_SIZE_INHERIT,
    GTK_ICON_SIZE_NORMAL,
    GTK_ICON_SIZE_LARGE;

    public static GtkIconSize getSizeFromCValue(int cValue) {
        return GtkIconSize.values()[cValue];
    }

    public static int getCValueFromSize(GtkIconSize s) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }
}
