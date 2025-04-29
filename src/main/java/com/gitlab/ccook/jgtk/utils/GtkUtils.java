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
package com.gitlab.ccook.jgtk.utils;

import com.gitlab.ccook.jgtk.bitfields.GtkDebugFlags;
import com.gitlab.ccook.jgtk.callbacks.GDestroyNotify;
import com.gitlab.ccook.jgtk.callbacks.GtkPrinterFunc;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;

import java.util.List;

public class GtkUtils {
    protected final static GtkLibrary library = new GtkLibrary();

    /**
     * Prevents gtk_init() and gtk_init_check() from automatically calling setlocale (LC_ALL, "").
     * <p>
     * You would want to use this function if you wanted to set the locale for your program to something other than the
     * user's locale, or if you wanted to set different values for different locale categories.
     * <p>
     * Most programs should not need to call this function.
     */
    public void disableLocale() {
        library.gtk_disable_setlocale();
    }

    /**
     * Calls a function for all GtkPrinters.
     * <p>
     * If func returns TRUE, the enumeration is stopped.
     *
     * @param func    A function to call for each printer.
     * @param data    User data to pass to func. (This may be null).
     * @param destroy Function to call if data is no longer needed.
     * @param wait    If TRUE, wait in a recursive main loop until all printers are enumerated; otherwise return early.
     */
    public void forEachGtkPrinter(GtkPrinterFunc func, Pointer data, GDestroyNotify destroy, boolean wait) {
        library.gtk_enumerate_printers(func, data, destroy, wait);
    }

    /**
     * Returns the binary age as passed to libtool.
     * <p>
     * If libtool means nothing to you, don't worry about it.
     *
     * @return The binary age of the GTK library.
     */
    public int getBinaryAge() {
        return library.gtk_get_binary_age();
    }

    /**
     * Returns the GTK debug flags that are currently active.
     * <p>
     * This function is intended for GTK modules that want to adjust their debug output based on GTK debug flags.
     *
     * @return The GTK debug flags.
     */
    public List<GtkDebugFlags> getDebugFlags() {
        return GtkDebugFlags.getFlagsFromCValue(library.gtk_get_debug_flags());
    }

    public Option<String> getVersionMismatchDescription(int majorVersion, int minorVersion, int patchVersion) {
        return new Option<>(library.gtk_check_version(minorVersion, minorVersion, patchVersion));
    }
}
