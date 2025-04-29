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
public enum GtkCellRendererState {
    GTK_CELL_RENDERER_SELECTED(1 << 0),
    GTK_CELL_RENDERER_PRELIT(1 << 1),
    GTK_CELL_RENDERER_INSENSITIVE(1 << 2),
    GTK_CELL_RENDERER_SORTED(1 << 3),
    GTK_CELL_RENDERER_FOCUSED(1 << 4),
    GTK_CELL_RENDERER_EXPANDABLE(1 << 5),
    GTK_CELL_RENDERER_EXPANDED(1 << 6);

    private final int cValue;

    GtkCellRendererState(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromState(GtkCellRendererState... flags) {
        int cValue = 0;
        for (GtkCellRendererState h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkCellRendererState> getStateFromCValue(int cValue) {
        List<GtkCellRendererState> flags = new ArrayList<>();
        for (GtkCellRendererState h : GtkCellRendererState.values()) {
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
