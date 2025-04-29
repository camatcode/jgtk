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
public enum GtkEventControllerScrollFlags {
    GTK_EVENT_CONTROLLER_SCROLL_NONE(0),
    GTK_EVENT_CONTROLLER_SCROLL_VERTICAL(1 << 0),
    GTK_EVENT_CONTROLLER_SCROLL_HORIZONTAL(1 << 1),
    GTK_EVENT_CONTROLLER_SCROLL_DISCRETE(1 << 2),
    GTK_EVENT_CONTROLLER_SCROLL_KINETIC(1 << 3),
    GTK_EVENT_CONTROLLER_SCROLL_BOTH_AXES((1 << 0) | (1 << 1));

    private final int cValue;

    GtkEventControllerScrollFlags(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromFlags(GtkEventControllerScrollFlags... flags) {
        int cValue = 0;
        for (GtkEventControllerScrollFlags h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkEventControllerScrollFlags> getFlagsFromCValue(int cValue) {
        List<GtkEventControllerScrollFlags> flags = new ArrayList<>();
        for (GtkEventControllerScrollFlags h : GtkEventControllerScrollFlags.values()) {
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
