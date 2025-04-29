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

public enum GtkScrollType {
    GTK_SCROLL_NONE,
    GTK_SCROLL_JUMP,
    GTK_SCROLL_STEP_BACKWARD,
    GTK_SCROLL_STEP_FORWARD,
    GTK_SCROLL_PAGE_BACKWARD,
    GTK_SCROLL_PAGE_FORWARD,
    GTK_SCROLL_STEP_UP,
    GTK_SCROLL_STEP_DOWN,
    GTK_SCROLL_PAGE_UP,
    GTK_SCROLL_PAGE_DOWN,
    GTK_SCROLL_STEP_LEFT,
    GTK_SCROLL_STEP_RIGHT,
    GTK_SCROLL_PAGE_LEFT,
    GTK_SCROLL_PAGE_RIGHT,
    GTK_SCROLL_START,
    GTK_SCROLL_END;

    public static GtkScrollType getTypeFromCValue(int cValue) {
        return GtkScrollType.values()[cValue];
    }
}
