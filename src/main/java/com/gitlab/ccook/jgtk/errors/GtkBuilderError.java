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

public enum GtkBuilderError {
    GTK_BUILDER_ERROR_INVALID_TYPE_FUNCTION,
    GTK_BUILDER_ERROR_UNHANDLED_TAG,
    GTK_BUILDER_ERROR_MISSING_ATTRIBUTE,
    GTK_BUILDER_ERROR_INVALID_ATTRIBUTE,
    GTK_BUILDER_ERROR_INVALID_TAG,
    GTK_BUILDER_ERROR_MISSING_PROPERTY_VALUE,
    GTK_BUILDER_ERROR_INVALID_VALUE,
    GTK_BUILDER_ERROR_VERSION_MISMATCH,
    GTK_BUILDER_ERROR_DUPLICATE_ID,
    GTK_BUILDER_ERROR_OBJECT_TYPE_REFUSED,
    GTK_BUILDER_ERROR_TEMPLATE_MISMATCH,
    GTK_BUILDER_ERROR_INVALID_PROPERTY,
    GTK_BUILDER_ERROR_INVALID_SIGNAL,
    GTK_BUILDER_ERROR_INVALID_ID,
    GTK_BUILDER_ERROR_INVALID_FUNCTION;

    public static GtkBuilderError getErrorFromCValue(int cValue) {
        return GtkBuilderError.values()[cValue];
    }
}
