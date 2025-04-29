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

public enum GConnectFlags {
    G_CONNECT_DEFAULT(0),
    GOBJECT_AVAILABLE_ENUMERATOR_IN_2_74(0),
    @SuppressWarnings("PointlessBitwiseExpression") G_CONNECT_AFTER(1 << 0),
    G_CONNECT_SWAPPED(1 << 1);

    private final int cValue;

    GConnectFlags(int cValue) {
        this.cValue = cValue;
    }


    public static int getCValueFromFlags(GConnectFlags... flags) {
        int cValue = 0;
        for (GConnectFlags h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GConnectFlags> getFlagsFromCValue(int cValue) {
        List<GConnectFlags> flags = new ArrayList<>();
        if (cValue == G_CONNECT_DEFAULT.getCValue()) {
            flags.add(G_CONNECT_DEFAULT);
            return flags;
        }
        for (GConnectFlags h : GConnectFlags.values()) {
            if (h.cValue != 0) {
                if ((cValue | h.getCValue()) == cValue) {
                    flags.add(h);
                }
            }
        }
        return flags;
    }

    public int getCValue() {
        return cValue;
    }
}
