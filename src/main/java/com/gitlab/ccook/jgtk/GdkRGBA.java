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
import java.util.Collections;
import java.util.List;

public class GdkRGBA extends JGTKObject {
    public final float red;
    public final float green;
    public final float blue;
    public final float alpha;

    public GdkRGBA(Pointer pointer) {
        super(pointer);
        float[] arr = pointer.getFloatArray(0, 4);
        this.red = arr[0];
        this.green = arr[1];
        this.blue = arr[2];
        this.alpha = arr[3];
    }

    public GdkRGBA(String parsable_string) {
        this(handleCtor(parsable_string));
    }


    public GdkRGBA(float red, float green, float blue, float alpha) {
        this(handleCtor(red, green, blue, alpha));
    }

    public GdkRGBA(GdkRGBA.GdkRGBAStruct.ByReference ref) {
        super(ref.getPointer());
        ref.read();
        this.red = ref.red;
        this.green = ref.green;
        this.blue = ref.blue;
        this.alpha = ref.alpha;
    }

    private static Pointer handleCtor(float red, float green, float blue, float alpha) {
        GdkRGBAStruct.ByValue r = new GdkRGBAStruct.ByValue();
        r.red = red;
        r.green = green;
        r.blue = blue;
        r.alpha = alpha;
        r.write();
        return r.getPointer();
    }


    private static Pointer handleCtor(String parsable_string) {
        GdkRGBA.GdkRGBAStruct.ByReference rgba = new GdkRGBA.GdkRGBAStruct.ByReference();
        library.gdk_rgba_parse(rgba, parsable_string);
        return rgba.getPointer();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GdkRGBA) {
            GdkRGBA r = (GdkRGBA) obj;
            return library.gdk_rgba_equal(getCReference(), r.getCReference());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + "r=" + red + ", g=" + green + ", b=" + blue + ", a=" + alpha + ')';
    }

    public GdkRGBA copy() {
        return new GdkRGBA(library.gdk_rgba_copy(getCReference()));
    }

    public static class GdkRGBAArrayStruct extends Structure {
        public GdkRGBAStruct[] arr;

        @Override
        protected List<String> getFieldOrder() {
            return Collections.singletonList("arr");
        }

        public static class ByReference extends GdkRGBAArrayStruct implements Structure.ByReference {
        }

        public static class ByValue extends GdkRGBAArrayStruct implements Structure.ByValue {
        }
    }

    public static class GdkRGBAStruct extends Structure {
        public float red;
        public float green;
        public float blue;
        public float alpha;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("red", "green", "blue", "alpha");
        }

        public static class ByReference extends GdkRGBAStruct implements Structure.ByReference {
        }

        public static class ByValue extends GdkRGBAStruct implements Structure.ByValue {
        }

    }
}
