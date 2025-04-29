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
package com.gitlab.ccook.util;


import java.util.Objects;

@SuppressWarnings("rawtypes")
public class Option<T> {

    public final static Option NONE = new Option<>(null);
    private final T thing;

    public Option(T thing) {
        this.thing = thing;
    }

    @Override
    public int hashCode() {
        return Objects.hash(thing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option<?> option = (Option<?>) o;
        return Objects.equals(thing, option.thing);
    }

    @Override
    public String toString() {
        if (isDefined()) {
            return get().toString();
        }
        return "NONE";
    }

    public boolean isDefined() {
        return thing != null;
    }

    public T get() {
        return thing;
    }
}
