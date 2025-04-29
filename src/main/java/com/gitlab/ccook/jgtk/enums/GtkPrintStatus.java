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

public enum GtkPrintStatus {
    GTK_PRINT_STATUS_INITIAL,
    GTK_PRINT_STATUS_PREPARING,
    GTK_PRINT_STATUS_GENERATING_DATA,
    GTK_PRINT_STATUS_SENDING_DATA,
    GTK_PRINT_STATUS_PENDING,
    GTK_PRINT_STATUS_PENDING_ISSUE,
    GTK_PRINT_STATUS_PRINTING,
    GTK_PRINT_STATUS_FINISHED,
    GTK_PRINT_STATUS_FINISHED_ABORTED;

    public static GtkPrintStatus getStatusFromCValue(int cValue) {
        return GtkPrintStatus.values()[cValue];
    }
}
