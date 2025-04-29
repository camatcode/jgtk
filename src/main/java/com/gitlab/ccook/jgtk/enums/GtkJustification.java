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

public enum GtkJustification {
    GTK_JUSTIFY_LEFT,
    GTK_JUSTIFY_RIGHT,
    GTK_JUSTIFY_CENTER,
    GTK_JUSTIFY_FILL;

    public static GtkJustification getJustificationFromCValue(int cValue) {
        return GtkJustification.values()[cValue];
    }

    public static int getCValueFromJustification(GtkJustification justification) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].equals(justification)) {
                return i;
            }
        }
        return -1;
    }
}
