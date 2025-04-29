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

public enum GtkTextDirection {
    GTK_TEXT_DIR_NONE(0),
    GTK_TEXT_DIR_LTR(1),
    GTK_TEXT_DIR_RTL(2);

    final private int cValue;

    GtkTextDirection(int cValue) {
        this.cValue = cValue;
    }

    public static GtkTextDirection getTextDirectionFromCValue(int cValue) {
        for (GtkTextDirection dir : GtkTextDirection.values()) {
            if (cValue == dir.cValue) {
                return dir;
            }
        }
        return null;
    }

    public int getCValue() {
        return cValue;
    }
}
