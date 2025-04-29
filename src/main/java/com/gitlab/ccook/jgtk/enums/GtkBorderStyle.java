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

public enum GtkBorderStyle {
    GTK_BORDER_STYLE_NONE,
    GTK_BORDER_STYLE_HIDDEN,
    GTK_BORDER_STYLE_SOLID,
    GTK_BORDER_STYLE_INSET,
    GTK_BORDER_STYLE_OUTSET,
    GTK_BORDER_STYLE_DOTTED,
    GTK_BORDER_STYLE_DASHED,
    GTK_BORDER_STYLE_DOUBLE,
    GTK_BORDER_STYLE_GROOVE,
    GTK_BORDER_STYLE_RIDGE;

    public static GtkBorderStyle getStyleFromCValue(int cValue) {
        return GtkBorderStyle.values()[cValue];
    }
}
