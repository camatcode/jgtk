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

public enum GtkEditableProperties {
    GTK_EDITABLE_PROP_TEXT,
    GTK_EDITABLE_PROP_CURSOR_POSITION,
    GTK_EDITABLE_PROP_SELECTION_BOUND,
    GTK_EDITABLE_PROP_EDITABLE,
    GTK_EDITABLE_PROP_WIDTH_CHARS,
    GTK_EDITABLE_PROP_MAX_WIDTH_CHARS,
    GTK_EDITABLE_PROP_XALIGN,
    GTK_EDITABLE_PROP_ENABLE_UNDO,
    GTK_EDITABLE_NUM_PROPERTIES;

    public static GtkEditableProperties getPropertiesFromCValue(int cValue) {
        return GtkEditableProperties.values()[cValue];
    }
}
