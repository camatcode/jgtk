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
package com.gitlab.ccook.jgtk.errors;

public enum GtkPrintError {
    GTK_PRINT_ERROR_GENERAL,
    GTK_PRINT_ERROR_INTERNAL_ERROR,
    GTK_PRINT_ERROR_NOMEM,
    GTK_PRINT_ERROR_INVALID_FILE;

    public GtkPrintError getErrorFromCValue(int cValue) {
        return GtkPrintError.values()[cValue];
    }
}
