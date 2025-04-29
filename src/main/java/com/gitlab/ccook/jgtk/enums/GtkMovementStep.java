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

public enum GtkMovementStep {
    GTK_MOVEMENT_LOGICAL_POSITIONS,
    GTK_MOVEMENT_VISUAL_POSITIONS,
    GTK_MOVEMENT_WORDS,
    GTK_MOVEMENT_DISPLAY_LINES,
    GTK_MOVEMENT_DISPLAY_LINE_ENDS,
    GTK_MOVEMENT_PARAGRAPHS,
    GTK_MOVEMENT_PARAGRAPH_ENDS,
    GTK_MOVEMENT_PAGES,
    GTK_MOVEMENT_BUFFER_ENDS,
    GTK_MOVEMENT_HORIZONTAL_PAGES;

    public static GtkMovementStep getStepFromCValue(int cValue) {
        return GtkMovementStep.values()[cValue];
    }
}
