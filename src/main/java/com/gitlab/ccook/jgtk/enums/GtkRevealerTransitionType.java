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

public enum GtkRevealerTransitionType {
    GTK_REVEALER_TRANSITION_TYPE_NONE,
    GTK_REVEALER_TRANSITION_TYPE_CROSSFADE,
    GTK_REVEALER_TRANSITION_TYPE_SLIDE_RIGHT,
    GTK_REVEALER_TRANSITION_TYPE_SLIDE_LEFT,
    GTK_REVEALER_TRANSITION_TYPE_SLIDE_UP,
    GTK_REVEALER_TRANSITION_TYPE_SLIDE_DOWN,
    GTK_REVEALER_TRANSITION_TYPE_SWING_RIGHT,
    GTK_REVEALER_TRANSITION_TYPE_SWING_LEFT,
    GTK_REVEALER_TRANSITION_TYPE_SWING_UP,
    GTK_REVEALER_TRANSITION_TYPE_SWING_DOWN;

    public static int getCValueFromType(GtkRevealerTransitionType type) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].equals(type)) {
                return i;
            }
        }
        return -1;
    }

    public static GtkRevealerTransitionType getTypeFromCValue(int cValue) {
        return GtkRevealerTransitionType.values()[cValue];
    }
}
