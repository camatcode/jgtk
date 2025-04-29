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
import com.sun.jna.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class GdkRectangle extends JGTKObject {
    private static final Logger log = LoggerFactory.getLogger(GdkRectangle.class);
    private final int x;
    private final int y;
    private int height = -1;
    private int width = -1;

    public GdkRectangle(int x, int y, int width, int height) {
        this(handleCtor(x, y, width, height));
    }

    public GdkRectangle(Pointer pointer) {
        super(pointer);
        this.x = pointer.getInt(0);
        this.y = pointer.getInt(1);
        try {
            this.width = pointer.getInt(2);
            this.height = pointer.getInt(3);
        } catch (Throwable e) {
            log.warn("Could not get width/height for GdkRectangle(" + pointer + "). Called before realized?");
        }
    }

    private static Pointer handleCtor(int x, int y, int width, int height) {
        GdkRectangle.GdkRectangleStruct.ByValue r = new GdkRectangle.GdkRectangleStruct.ByValue();
        r.x = x;
        r.y = y;
        r.width = width;
        r.height = height;
        r.write();
        return r.getPointer();
    }

    public GdkRectangle(GdkRectangleStruct.ByReference ref) {
        super(ref.getPointer());
        ref.read();
        this.x = ref.x;
        this.y = ref.y;
        this.width = ref.width;
        this.height = ref.height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GdkRectangle) {
            GdkRectangle r = (GdkRectangle) obj;
            return library.gdk_rectangle_equal(getCReference(), r.getCReference());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ')';
    }

    public static class GdkRectangleStruct extends Structure {
        public int height;
        public int width;
        public int x;
        public int y;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("x", "y", "width", "height");
        }

        public static class ByReference extends GdkRectangleStruct implements Structure.ByReference {
        }

        static class ByValue extends GdkRectangleStruct implements Structure.ByValue {
        }

    }
}
