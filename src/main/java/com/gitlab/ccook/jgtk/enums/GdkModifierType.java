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

@SuppressWarnings("PointlessBitwiseExpression")
public enum GdkModifierType {
    GDK_SHIFT_MASK(1 << 0),
    GDK_LOCK_MASK(1 << 1),
    GDK_CONTROL_MASK(1 << 2),
    GDK_ALT_MASK(1 << 3),
    GDK_BUTTON1_MASK(1 << 8),
    GDK_BUTTON2_MASK(1 << 9),
    GDK_BUTTON3_MASK(1 << 10),
    GDK_BUTTON4_MASK(1 << 11),
    GDK_BUTTON5_MASK(1 << 12),
    GDK_SUPER_MASK(1 << 26),
    GDK_HYPER_MASK(1 << 27),
    GDK_META_MASK(1 << 28);

    private final int cValue;

    GdkModifierType(int cValue) {
        this.cValue = cValue;
    }

    public static GdkModifierType getTypeFromCValue(int cValue) {
        for (GdkModifierType t : GdkModifierType.values()) {
            if (t.cValue == cValue) {
                return t;
            }
        }
        return null;
    }

    public int getCValue() {
        return cValue;
    }

}
