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
public enum GtkDialogFlags {
    GTK_DIALOG_MODAL(1 << 0),
    GTK_DIALOG_DESTROY_WITH_PARENT(1 << 1),
    GTK_DIALOG_USE_HEADER_BAR(1 << 2);

    private final int cValue;

    GtkDialogFlags(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromFlags(GtkDialogFlags... flags) {
        int cValue = 0;
        for (GtkDialogFlags h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkDialogFlags> getFlagsFromCValue(int cValue) {
        List<GtkDialogFlags> flags = new ArrayList<>();
        for (GtkDialogFlags h : GtkDialogFlags.values()) {
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
