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


public enum GtkOrientation {
    GTK_ORIENTATION_HORIZONTAL(0),
    GTK_ORIENTATION_VERTICAL(1);

    private final int cValue;

    GtkOrientation(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromOrientation(GtkOrientation orientation) {
        for (GtkOrientation o : values()) {
            if (o.equals(orientation)) {
                return orientation.cValue;
            }
        }
        return -1;
    }

    public static GtkOrientation getOrientationByValue(int cValue) {
        for (GtkOrientation o : GtkOrientation.values()) {
            if (o.cValue == cValue) {
                return o;
            }
        }
        return null;
    }

    public int getCValue() {
        return cValue;
    }
}
