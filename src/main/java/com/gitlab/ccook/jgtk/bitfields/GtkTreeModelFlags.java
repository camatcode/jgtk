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
public enum GtkTreeModelFlags {
    GTK_TREE_MODEL_ITERS_PERSIST(1 << 0),
    GTK_TREE_MODEL_LIST_ONLY(1 << 1);

    private final int cValue;

    GtkTreeModelFlags(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromFlags(GtkTreeModelFlags... flags) {
        int cValue = 0;
        for (GtkTreeModelFlags h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkTreeModelFlags> getFlagsFromCValue(int cValue) {
        List<GtkTreeModelFlags> flags = new ArrayList<>();
        for (GtkTreeModelFlags h : GtkTreeModelFlags.values()) {
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
