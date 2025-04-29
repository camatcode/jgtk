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


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PointlessBitwiseExpression")
public enum GtkDebugFlags {
    GTK_DEBUG_TEXT(1 << 0),
    GTK_DEBUG_TREE(1 << 1),
    GTK_DEBUG_KEYBINDINGS(1 << 2),
    GTK_DEBUG_MODULES(1 << 3),
    GTK_DEBUG_GEOMETRY(1 << 4),
    GTK_DEBUG_ICONTHEME(1 << 5),
    GTK_DEBUG_PRINTING(1 << 6),
    GTK_DEBUG_BUILDER(1 << 7),
    GTK_DEBUG_SIZE_REQUEST(1 << 8),
    GTK_DEBUG_NO_CSS_CACHE(1 << 9),
    GTK_DEBUG_INTERACTIVE(1 << 10),
    GTK_DEBUG_TOUCHSCREEN(1 << 11),
    GTK_DEBUG_ACTIONS(1 << 12),
    GTK_DEBUG_LAYOUT(1 << 13),
    GTK_DEBUG_SNAPSHOT(1 << 14),
    GTK_DEBUG_CONSTRAINTS(1 << 15),
    GTK_DEBUG_BUILDER_OBJECTS(1 << 16),
    GTK_DEBUG_A11Y(1 << 17),
    GTK_DEBUG_ICONFALLBACK(1 << 18),
    GTK_DEBUG_INVERT_TEXT_DIR(1 << 19);

    private final int cValue;

    GtkDebugFlags(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromFlags(GtkDebugFlags... flags) {
        int cValue = 0;
        for (GtkDebugFlags h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkDebugFlags> getFlagsFromCValue(int cValue) {
        List<GtkDebugFlags> flags = new ArrayList<>();
        for (GtkDebugFlags h : GtkDebugFlags.values()) {
            if ((cValue | h.getCValue()) == cValue) {
                flags.add(h);
            }
        }
        return flags;
    }

    public int getCValue() {
        return cValue;
    }
}
