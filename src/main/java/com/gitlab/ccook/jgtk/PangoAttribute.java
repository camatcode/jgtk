/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 - 2023 JGTK
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

import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class PangoAttribute extends JGTKObject {

    final int endIndex;
    final PangoAttrClass klass;
    final int startIndex;

    public PangoAttribute(Pointer cReference) {
        super(cReference);
        PangoAttributeStruct struct = new PangoAttributeStruct(cReference);
        klass = new PangoAttrClass(struct.klass);
        startIndex = struct.start_index;
        endIndex = struct.end_index;
    }


    @Override
    public boolean equals(Object other) {
        if (other instanceof PangoAttribute) {
            PangoAttribute attr = (PangoAttribute) other;
            return library.pango_attribute_equal(getCReference(), attr.getCReference());
        }
        return false;
    }

    @Override
    public String toString() {
        return "PangoAttribute{" + "klass=" + klass + ", endIndex=" + endIndex + ", startIndex=" + startIndex + '}';
    }

    public PangoAttrClass getPangoAttrClass() {
        return klass;
    }

    public Option<PangoColor> getValueAsColor() {
        if (klass.getType().isColor()) {
            return new Option<>(library.pango_attribute_as_color(klass.getCReference()).color);
        }
        return Option.NONE;
    }

    public Option<Integer> getValueAsInt() {
        if (klass.getType().isInt()) {
            return new Option<>(library.pango_attribute_as_int(klass.getCReference()).value);
        }
        return Option.NONE;
    }

    public static class PangoAttributeStruct extends Structure {
        public int end_index;
        public Pointer klass;
        public int start_index;

        public PangoAttributeStruct(Pointer p) {
            super();
            useMemory(p); // set pointer
            read(); // initialize fields
        }


        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("klass", "start_index", "end_index");
        }


    }

}
