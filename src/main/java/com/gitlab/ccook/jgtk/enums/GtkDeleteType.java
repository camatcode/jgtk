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

public enum GtkDeleteType {
    GTK_DELETE_CHARS,
    GTK_DELETE_WORD_ENDS,
    GTK_DELETE_WORDS,
    GTK_DELETE_DISPLAY_LINES,
    GTK_DELETE_DISPLAY_LINE_ENDS,
    GTK_DELETE_PARAGRAPH_ENDS,
    GTK_DELETE_PARAGRAPHS,
    GTK_DELETE_WHITESPACE;

    public GtkDeleteType getTypeFromCValue(int cValue) {
        return GtkDeleteType.values()[cValue];
    }
}
