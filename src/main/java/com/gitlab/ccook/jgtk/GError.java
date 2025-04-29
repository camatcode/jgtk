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

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class GError extends JGTKObject {

    private final int code;
    private final String message;
    private final int quark;


    public GError(GErrorStruct.ByReference ref) {
        super(ref.getPointer());
        this.quark = ref.quark;
        this.code = ref.code;
        this.message = ref.message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getQuark() {
        return quark;
    }

    public static class GErrorStruct extends Structure {
        public int code;
        public String message;
        public int quark;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("quark", "code", "message");
        }

        public static class ByReference extends GErrorStruct implements Structure.ByReference {
        }

        static class ByValue extends GErrorStruct implements Structure.ByValue {
        }

    }
}
