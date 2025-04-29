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
package com.gitlab.ccook.jgtk;

import com.sun.jna.Pointer;

public class GValue extends JGTKObject {

    public GValue(Pointer reference) {
        super(reference);
    }

    public boolean getBoolean() {
        return library.g_value_get_boolean(cReference);
    }

    public void setBoolean(boolean bool) {
        library.g_value_set_boolean(cReference, bool);
    }

    public Pointer getObject() {
        return library.g_value_get_object(getCReference());
    }

    public void setObject(Pointer value) {
        library.g_value_set_object(cReference, value);
    }

    public String getText() {
        return library.g_value_get_string(cReference);
    }

    public void setText(String text) {
        library.g_value_set_string(cReference, text);
    }

    public int getUInt() {
        return library.g_value_get_uint(cReference);
    }

    public void setUInt(int value) {
        library.g_value_set_uint(cReference, value);
    }

    public void setPointer(Pointer c) {
        library.g_value_set_pointer(cReference, c);
    }
}
