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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * A GtkSpinner widget displays an icon-size spinning animation.
 * <p>
 * It is often used as an alternative to a GtkProgressBar for displaying indefinite activity, instead of actual
 * progress.
 * <p>
 * To start the animation, use gtk_spinner_start(), to stop it use gtk_spinner_stop().
 */
public class GtkSpinner extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkSpinnerLibrary library = new GtkSpinnerLibrary();

    public GtkSpinner(Pointer ref) {
        super(ref);
    }

    /**
     * Returns a new spinner widget. Not yet started.
     */
    public GtkSpinner() {
        super(library.gtk_spinner_new());
    }

    /**
     * Returns whether the spinner is spinning.
     *
     * @return TRUE if the spinner is active.
     */
    public boolean isSpinning() {
        return library.gtk_spinner_get_spinning(getCReference());
    }

    /**
     * Starts the animation of the spinner.
     */
    public void start() {
        library.gtk_spinner_start(getCReference());
    }

    /**
     * Stops the animation of the spinner.
     */
    public void stop() {
        library.gtk_spinner_stop(getCReference());
    }

    protected static class GtkSpinnerLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns whether the spinner is spinning.
         *
         * @param spinner self
         * @return TRUE if the spinner is active.
         */
        public native boolean gtk_spinner_get_spinning(Pointer spinner);

        /**
         * Returns a new spinner widget. Not yet started.
         *
         * @return A new GtkSpinner
         */
        public native Pointer gtk_spinner_new();

        /**
         * Starts the animation of the spinner.
         *
         * @param spinner self
         */
        public native void gtk_spinner_start(Pointer spinner);

        /**
         * Stops the animation of the spinner.
         *
         * @param spinner self
         */
        public native void gtk_spinner_stop(Pointer spinner);
    }
}
