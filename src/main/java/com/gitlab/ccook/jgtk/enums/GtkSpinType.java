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

public enum GtkSpinType {
    GTK_SPIN_STEP_FORWARD,
    GTK_SPIN_STEP_BACKWARD,
    GTK_SPIN_PAGE_FORWARD,
    GTK_SPIN_PAGE_BACKWARD,
    GTK_SPIN_HOME,
    GTK_SPIN_END,
    GTK_SPIN_USER_DEFINED;

    public static int getCValueFromType(GtkSpinType direction) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].equals(direction)) {
                return i;
            }
        }
        return -1;
    }

    public static GtkSpinType getTypeFromCValue(int cValue) {
        return GtkSpinType.values()[cValue];
    }
}
