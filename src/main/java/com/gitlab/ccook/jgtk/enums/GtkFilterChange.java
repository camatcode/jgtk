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

public enum GtkFilterChange {
    GTK_FILTER_CHANGE_DIFFERENT(0),
    GTK_FILTER_CHANGE_LESS_STRICT(1),
    GTK_FILTER_CHANGE_MORE_STRICT(2);

    final int cValue;

    GtkFilterChange(int cValue) {
        this.cValue = cValue;
    }

    public GtkFilterChange getChangeFromCValue(int cValue) {
        for (GtkFilterChange c : GtkFilterChange.values()) {
            if (c.cValue == cValue) {
                return c;
            }
        }
        return null;
    }

    public int getCValue() {
        return cValue;
    }

}
