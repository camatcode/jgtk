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

public enum GtkAccessibleRelation {
    GTK_ACCESSIBLE_RELATION_ACTIVE_DESCENDANT,
    GTK_ACCESSIBLE_RELATION_COL_COUNT,
    GTK_ACCESSIBLE_RELATION_COL_INDEX,
    GTK_ACCESSIBLE_RELATION_COL_INDEX_TEXT,
    GTK_ACCESSIBLE_RELATION_COL_SPAN,
    GTK_ACCESSIBLE_RELATION_CONTROLS,
    GTK_ACCESSIBLE_RELATION_DESCRIBED_BY,
    GTK_ACCESSIBLE_RELATION_DETAILS,
    GTK_ACCESSIBLE_RELATION_ERROR_MESSAGE,
    GTK_ACCESSIBLE_RELATION_FLOW_TO,
    GTK_ACCESSIBLE_RELATION_LABELLED_BY,
    GTK_ACCESSIBLE_RELATION_OWNS,
    GTK_ACCESSIBLE_RELATION_POS_IN_SET,
    GTK_ACCESSIBLE_RELATION_ROW_COUNT,
    GTK_ACCESSIBLE_RELATION_ROW_INDEX,
    GTK_ACCESSIBLE_RELATION_ROW_INDEX_TEXT,
    GTK_ACCESSIBLE_RELATION_ROW_SPAN,
    GTK_ACCESSIBLE_RELATION_SET_SIZE;

    public static int getCValue(GtkAccessibleRelation r) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].equals(r)) {
                return i;
            }
        }
        return -1;
    }

    public GtkAccessibleRelation getRelationFromCValue(int cValue) {
        return GtkAccessibleRelation.values()[cValue];
    }
}
