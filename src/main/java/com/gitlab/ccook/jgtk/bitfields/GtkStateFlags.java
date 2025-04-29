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
public enum GtkStateFlags {
    GTK_STATE_FLAG_NORMAL(0),
    GTK_STATE_FLAG_ACTIVE(1 << 0),
    GTK_STATE_FLAG_PRELIGHT(1 << 1),
    GTK_STATE_FLAG_SELECTED(1 << 2),
    GTK_STATE_FLAG_INSENSITIVE(1 << 3),
    GTK_STATE_FLAG_INCONSISTENT(1 << 4),
    GTK_STATE_FLAG_FOCUSED(1 << 5),
    GTK_STATE_FLAG_BACKDROP(1 << 6),
    GTK_STATE_FLAG_DIR_LTR(1 << 7),
    GTK_STATE_FLAG_DIR_RTL(1 << 8),
    GTK_STATE_FLAG_LINK(1 << 9),
    GTK_STATE_FLAG_VISITED(1 << 10),
    GTK_STATE_FLAG_CHECKED(1 << 11),
    GTK_STATE_FLAG_DROP_ACTIVE(1 << 12),
    GTK_STATE_FLAG_FOCUS_VISIBLE(1 << 13),
    GTK_STATE_FLAG_FOCUS_WITHIN(1 << 14);

    private final int cValue;

    GtkStateFlags(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromFlags(GtkStateFlags... flags) {
        int cValue = 0;
        for (GtkStateFlags h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkStateFlags> getFlagsFromCValue(int cValue) {
        List<GtkStateFlags> flags = new ArrayList<>();
        for (GtkStateFlags h : GtkStateFlags.values()) {
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
