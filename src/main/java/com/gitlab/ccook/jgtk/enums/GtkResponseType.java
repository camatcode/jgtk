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

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public enum GtkResponseType {
    GTK_RESPONSE_NONE(-1),
    GTK_RESPONSE_REJECT(-2),
    GTK_RESPONSE_ACCEPT(-3),
    GTK_RESPONSE_DELETE_EVENT(-4),
    GTK_RESPONSE_OK(-5),
    GTK_RESPONSE_CANCEL(-6),
    GTK_RESPONSE_CLOSE(-7),
    GTK_RESPONSE_YES(-8),
    GTK_RESPONSE_NO(-9),
    GTK_RESPONSE_APPLY(-10),
    GTK_RESPONSE_HELP(-11);

    private final int cValue;

    GtkResponseType(int cValue) {
        this.cValue = cValue;
    }

    public static GtkResponseType getTypeFromCValue(int cValue) {
        for (GtkResponseType t : GtkResponseType.values()) {
            if (t.cValue == cValue) {
                return t;
            }
        }
        return null;
    }

    public int getCValue() {
        return cValue;
    }
}
