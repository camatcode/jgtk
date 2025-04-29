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


public enum GtkTextWindowType {
    GTK_TEXT_WINDOW_WIDGET(1),
    GTK_TEXT_WINDOW_TEXT(2),
    GTK_TEXT_WINDOW_LEFT(3),
    GTK_TEXT_WINDOW_RIGHT(4),
    GTK_TEXT_WINDOW_TOP(5),
    GTK_TEXT_WINDOW_BOTTOM(6);

    private final int cValue;

    GtkTextWindowType(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromType(GtkTextWindowType type) {
        if (type != null) {
            return type.cValue;
        }
        return -1;
    }

    public static GtkTextWindowType getTypeFromCValue(int cValue) {
        for (GtkTextWindowType t : GtkTextWindowType.values()) {
            if (t.cValue == cValue) {
                return t;
            }
        }
        return null;
    }


    public static boolean isSubset(GtkTextWindowType t, GtkTextWindowType... types) {
        for (GtkTextWindowType type : types) {
            if (t != null && t.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
