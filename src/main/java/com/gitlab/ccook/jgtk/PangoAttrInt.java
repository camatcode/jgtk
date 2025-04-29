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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class PangoAttrInt extends Structure {

    public PangoAttribute.PangoAttributeStruct attribute;

    public int value;

    public PangoAttrInt(Pointer p) {
        super();
        useMemory(p); // set pointer
        read(); // initialize fields
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("attribute", "value");
    }

    @Override
    public String toString() {
        return "PangoAttrInt{" + "value=" + value + '}';
    }
}
