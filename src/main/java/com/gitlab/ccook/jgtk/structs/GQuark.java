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
package com.gitlab.ccook.jgtk.structs;

import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;

import java.util.Objects;

@SuppressWarnings({"unchecked", "EqualsWhichDoesntCheckParameterClass"})
public class GQuark {
    protected final static GtkLibrary library = new GtkLibrary();

    private final int cValue;

    public GQuark(int value) {
        this.cValue = value;
    }

    public static GQuark fromStaticString(String s) {
        return new GQuark(library.g_quark_from_static_string(s));
    }

    public boolean isDefined() {
        return getQuark().isDefined();
    }

    public Option<Integer> getQuark() {
        if (cValue > 0) {
            return new Option<>(cValue);
        }
        return Option.NONE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        return hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cValue);
    }
}
