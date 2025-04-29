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
package com.gitlab.ccook.jgtk.gtk;

import com.gitlab.ccook.jgtk.GValue;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkVolumeButton is a GtkScaleButton subclass tailored for volume control.
 *
 * @deprecated Deprecated since: 4.10 This widget will be removed in GTK 5
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class GtkVolumeButton extends GtkScaleButton implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkVolumeButtonLibrary library = new GtkVolumeButtonLibrary();

    public GtkVolumeButton(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a GtkVolumeButton.
     * <p>
     * The button has a range between 0.0 and 1.0, with a stepping of 0.02. Volume values can be obtained and modified
     * using the functions from GtkScaleButton.
     *
     * @deprecated Deprecated since: 4.10 This widget will be removed in GTK 5
     */
    public GtkVolumeButton() {
        super(library.gtk_volume_button_new());
    }

    /**
     * @return Whether to use symbolic icons as the icons.
     *         <p>
     *         Note that if the symbolic icons are not available in your installed theme, then the normal (potentially
     *         colorful)
     *         icons will be used.
     * @deprecated Deprecated since: 4.10 This widget will be removed in GTK 5
     */
    public boolean doesUseSymbolicIcons() {
        Option<GValue> v = getProperty("use-symbolic");
        if (v.isDefined()) {
            return v.get().getBoolean();
        }
        return true;
    }

    /**
     * @param useSymbolic Whether to use symbolic icons as the icons.
     *                    <p>
     *                    Note that if the symbolic icons are not available in your installed theme, then the normal
     *                    (potentially colorful) icons will be used.
     * @deprecated Deprecated since: 4.10 This widget will be removed in GTK 5
     */
    public void shouldUseSymbolicIcons(boolean useSymbolic) {
        setProperty("use-symbolic", getCReference(), useSymbolic);
    }

    protected static class GtkVolumeButtonLibrary extends GtkScaleButtonLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a GtkVolumeButton.
         * <p>
         * The button has a range between 0.0 and 1.0, with a stepping of 0.02. Volume values can be obtained and
         * modified using the functions from GtkScaleButton.
         *
         * @return A new GtkVolumeButton
         */
        public native Pointer gtk_volume_button_new();
    }
}
