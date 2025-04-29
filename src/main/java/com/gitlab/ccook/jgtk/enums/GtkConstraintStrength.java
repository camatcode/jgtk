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

public enum GtkConstraintStrength {
    GTK_CONSTRAINT_STRENGTH_REQUIRED(1001001000),
    GTK_CONSTRAINT_STRENGTH_STRONG(1000000000),
    GTK_CONSTRAINT_STRENGTH_MEDIUM(1000),
    GTK_CONSTRAINT_STRENGTH_WEAK(1);


    private final int cValue;

    GtkConstraintStrength(int cValue) {
        this.cValue = cValue;
    }

    public static GtkConstraintStrength getStrengthFromCValue(int cValue) {
        for (GtkConstraintStrength s : GtkConstraintStrength.values()) {
            if (s.cValue == cValue) {
                return s;
            }
        }
        return null;
    }
}
