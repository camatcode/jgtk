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
public enum GApplicationFlags {
    G_APPLICATION_FLAGS_NONE(0),
    G_APPLICATION_DEFAULT_FLAGS(0),
    GIO_AVAILABLE_ENUMERATOR_IN_2_74(0),
    G_APPLICATION_IS_SERVICE(1 << 0),
    G_APPLICATION_IS_LAUNCHER(1 << 1),
    G_APPLICATION_HANDLES_OPEN(1 << 2),
    G_APPLICATION_HANDLES_COMMAND_LINE(1 << 3),
    G_APPLICATION_SEND_ENVIRONMENT(1 << 4),
    G_APPLICATION_NON_UNIQUE(1 << 5),
    G_APPLICATION_CAN_OVERRIDE_APP_ID(1 << 6),
    G_APPLICATION_ALLOW_REPLACEMENT(1 << 7),
    G_APPLICATION_REPLACE(1 << 8);

    private final int cValue;

    GApplicationFlags(int i) {
        this.cValue = i;
    }

    public static int getCValueFromFlags(GApplicationFlags... flags) {
        int cValue = 0;
        for (GApplicationFlags h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GApplicationFlags> getFlagsFromCValue(int cValue) {
        List<GApplicationFlags> flags = new ArrayList<>();
        if (cValue == G_APPLICATION_FLAGS_NONE.getCValue()) {
            flags.add(G_APPLICATION_FLAGS_NONE);
            flags.add(G_APPLICATION_DEFAULT_FLAGS);
            return flags;
        }
        for (GApplicationFlags h : GApplicationFlags.values()) {
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
