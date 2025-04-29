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

import com.gitlab.ccook.jgtk.gtk.GenericGListModel;
import com.gitlab.ccook.jna.GtkLibrary;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class GdkDisplay extends JGTKObject {
    private static final GdkDisplayLibrary library = new GdkDisplayLibrary();


    public GdkDisplay(Pointer p) {
        super(p);
    }

    //gdk_display_get_default

    public GdkDisplay() {
        super(library.gdk_display_get_default());
    }

    public GListModel<GdkMonitor> getMonitors() {
        return new GenericGListModel<>(GdkMonitor.class, library.gdk_display_get_monitors(getCReference()));
    }

    protected static class GdkDisplayLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the default GdkDisplay.
         *
         * @return The data is owned by the called function.
         *         The return value can be NULL.
         */
        public native Pointer gdk_display_get_default();

        public native Pointer gdk_display_get_monitors(Pointer display);
    }

}
