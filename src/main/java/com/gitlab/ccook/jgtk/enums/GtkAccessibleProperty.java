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

public enum GtkAccessibleProperty {
    GTK_ACCESSIBLE_PROPERTY_AUTOCOMPLETE,
    GTK_ACCESSIBLE_PROPERTY_DESCRIPTION,
    GTK_ACCESSIBLE_PROPERTY_HAS_POPUP,
    GTK_ACCESSIBLE_PROPERTY_KEY_SHORTCUTS,
    GTK_ACCESSIBLE_PROPERTY_LABEL,
    GTK_ACCESSIBLE_PROPERTY_LEVEL,
    GTK_ACCESSIBLE_PROPERTY_MODAL,
    GTK_ACCESSIBLE_PROPERTY_MULTI_LINE,
    GTK_ACCESSIBLE_PROPERTY_MULTI_SELECTABLE,
    GTK_ACCESSIBLE_PROPERTY_ORIENTATION,
    GTK_ACCESSIBLE_PROPERTY_PLACEHOLDER,
    GTK_ACCESSIBLE_PROPERTY_READ_ONLY,
    GTK_ACCESSIBLE_PROPERTY_REQUIRED,
    GTK_ACCESSIBLE_PROPERTY_ROLE_DESCRIPTION,
    GTK_ACCESSIBLE_PROPERTY_SORT,
    GTK_ACCESSIBLE_PROPERTY_VALUE_MAX,
    GTK_ACCESSIBLE_PROPERTY_VALUE_MIN,
    GTK_ACCESSIBLE_PROPERTY_VALUE_NOW,
    GTK_ACCESSIBLE_PROPERTY_VALUE_TEXT;

    public static int getCValue(GtkAccessibleProperty p) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].equals(p)) {
                return i;
            }
        }
        return -1;
    }

    public GtkAccessibleProperty getPropertyFromCValue(int cValue) {
        return GtkAccessibleProperty.values()[cValue];
    }
}
