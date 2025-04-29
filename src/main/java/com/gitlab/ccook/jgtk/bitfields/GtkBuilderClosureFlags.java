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
package com.gitlab.ccook.jgtk.bitfields;

@SuppressWarnings("PointlessBitwiseExpression")
public enum GtkBuilderClosureFlags {
    GTK_BUILDER_CLOSURE_SWAPPED(1 << 0);

    private final int cValue;

    @SuppressWarnings("SameParameterValue")
    GtkBuilderClosureFlags(int cValue) {
        this.cValue = cValue;
    }

    public static GtkBuilderClosureFlags getFlagsFromCValue(int cValue) {
        for (GtkBuilderClosureFlags flags : GtkBuilderClosureFlags.values()) {
            if (flags.cValue == cValue) {
                return flags;
            }
        }
        return null;
    }

    public int getCValue() {
        return cValue;
    }
}
