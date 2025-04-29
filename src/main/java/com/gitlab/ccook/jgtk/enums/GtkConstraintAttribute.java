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

public enum GtkConstraintAttribute {
    GTK_CONSTRAINT_ATTRIBUTE_NONE,
    GTK_CONSTRAINT_ATTRIBUTE_LEFT,
    GTK_CONSTRAINT_ATTRIBUTE_RIGHT,
    GTK_CONSTRAINT_ATTRIBUTE_TOP,
    GTK_CONSTRAINT_ATTRIBUTE_BOTTOM,
    GTK_CONSTRAINT_ATTRIBUTE_START,
    GTK_CONSTRAINT_ATTRIBUTE_END,
    GTK_CONSTRAINT_ATTRIBUTE_WIDTH,
    GTK_CONSTRAINT_ATTRIBUTE_HEIGHT,
    GTK_CONSTRAINT_ATTRIBUTE_CENTER_X,
    GTK_CONSTRAINT_ATTRIBUTE_CENTER_Y,
    GTK_CONSTRAINT_ATTRIBUTE_BASELINE;

    public static GtkConstraintAttribute getAttributeFromCValue(int cValue) {
        return GtkConstraintAttribute.values()[cValue];
    }
}
