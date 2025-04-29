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

public enum GtkStackTransitionType {
    GTK_STACK_TRANSITION_TYPE_NONE,
    GTK_STACK_TRANSITION_TYPE_CROSSFADE,
    GTK_STACK_TRANSITION_TYPE_SLIDE_RIGHT,
    GTK_STACK_TRANSITION_TYPE_SLIDE_LEFT,
    GTK_STACK_TRANSITION_TYPE_SLIDE_UP,
    GTK_STACK_TRANSITION_TYPE_SLIDE_DOWN,
    GTK_STACK_TRANSITION_TYPE_SLIDE_LEFT_RIGHT,
    GTK_STACK_TRANSITION_TYPE_SLIDE_UP_DOWN,
    GTK_STACK_TRANSITION_TYPE_OVER_UP,
    GTK_STACK_TRANSITION_TYPE_OVER_DOWN,
    GTK_STACK_TRANSITION_TYPE_OVER_LEFT,
    GTK_STACK_TRANSITION_TYPE_OVER_RIGHT,
    GTK_STACK_TRANSITION_TYPE_UNDER_UP,
    GTK_STACK_TRANSITION_TYPE_UNDER_DOWN,
    GTK_STACK_TRANSITION_TYPE_UNDER_LEFT,
    GTK_STACK_TRANSITION_TYPE_UNDER_RIGHT,
    GTK_STACK_TRANSITION_TYPE_OVER_UP_DOWN,
    GTK_STACK_TRANSITION_TYPE_OVER_DOWN_UP,
    GTK_STACK_TRANSITION_TYPE_OVER_LEFT_RIGHT,
    GTK_STACK_TRANSITION_TYPE_OVER_RIGHT_LEFT,
    GTK_STACK_TRANSITION_TYPE_ROTATE_LEFT,
    GTK_STACK_TRANSITION_TYPE_ROTATE_RIGHT,
    GTK_STACK_TRANSITION_TYPE_ROTATE_LEFT_RIGHT;

    public static int getCValueFromType(GtkStackTransitionType type) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].equals(type)) {
                return i;
            }
        }
        return -1;
    }

    public static GtkStackTransitionType getTypeFromCValue(int cValue) {
        return GtkStackTransitionType.values()[cValue];
    }
}
