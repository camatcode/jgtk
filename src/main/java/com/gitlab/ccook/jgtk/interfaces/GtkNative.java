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
package com.gitlab.ccook.jgtk.interfaces;

import com.gitlab.ccook.jgtk.GdkSurface;
import com.gitlab.ccook.jgtk.GskRenderer;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


@SuppressWarnings("unchecked")
public interface GtkNative extends GtkInterface {
    static Option<GtkNative> getNativeForSurface(GdkSurface s) {
        if (s != null) {
            Option<Pointer> p = new Option<>(library.gtk_native_get_for_surface(s.getCReference()));
            if (p.isDefined()) {
                return new Option<>((GtkNative) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
            }
        }
        return Option.NONE;
    }

    /**
     * Returns the renderer that is used for this GtkNative
     *
     * @return The renderer for self.
     */
    default GskRenderer getRenderer() {
        return new GskRenderer(library.gtk_native_get_renderer(getCReference()));
    }

    /**
     * Returns the surface of this GtkNative.
     *
     * @return The surface of self.
     */
    default GdkSurface getSurface() {
        return new GdkSurface(library.gtk_native_get_surface(getCReference()));
    }

    /**
     * Retrieves the surface transform of self.
     * <p>
     * This is the translation from self's surface coordinates into self's widget coordinates.
     *
     * @return translation from self's surface coordinates into self's widget coordinates
     */
    default Pair<Double, Double> getSurfaceTransform() {
        PointerByReference x = new PointerByReference();
        PointerByReference y = new PointerByReference();
        library.gtk_native_get_surface_transform(getCReference(), x, y);
        double xVal = x.getPointer().getDouble(0);
        double yVal = y.getPointer().getDouble(0);
        return new Pair<>(xVal, yVal);
    }

    /**
     * Realizes a GtkNative.
     * <p>
     * This should only be used by subclasses.
     */
    default void realize() {
        library.gtk_native_realize(getCReference());
    }

    /**
     * Unrealizes a GtkNative.
     * <p>
     * This should only be used by subclasses.
     */
    default void unrealize() {
        library.gtk_native_unrealize(getCReference());
    }
}
