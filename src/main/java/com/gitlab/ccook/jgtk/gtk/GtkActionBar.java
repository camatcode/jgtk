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
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkActionBar is designed to present contextual actions.
 * <p>
 * An example GtkActionBar
 * <p>
 * It is expected to be displayed below the content and expand horizontally to fill the area.
 * <p>
 * It allows placing children at the start or the end. In addition, it contains an internal centered box which is
 * centered with respect to the full width of the box, even if the children at either side take up different amounts of
 * space.
 */
@SuppressWarnings("unchecked")
public class GtkActionBar extends GtkWidget implements GtkAccessible, GtkBuildable {

    private static final GtkActionBarLibrary library = new GtkActionBarLibrary();

    public GtkActionBar() {
        super(library.gtk_action_bar_new());
    }

    public GtkActionBar(Pointer ref) {
        super(ref);
    }

    /**
     * Adds child to action_bar, packed with reference to the start of the action_bar.
     *
     * @param w The GtkWidget to be added to action_bar.
     */
    public void addToLeftSide(GtkWidget w) {
        if (w != null) {
            library.gtk_action_bar_pack_start(cReference, w.getCReference());
        }
    }

    /**
     * Adds child to action_bar, packed with reference to the end of the action_bar.
     *
     * @param w end widget
     */
    public void addToRightSide(GtkWidget w) {
        if (w != null) {
            library.gtk_action_bar_pack_end(cReference, w.getCReference());
        }
    }

    /**
     * Retrieves the center bar widget of the bar.
     *
     * @return The center GtkWidget, if defined
     */
    public Option<GtkWidget> getCenterWidget() {
        Option<Pointer> pointer = new Option<>(library.gtk_action_bar_get_center_widget(cReference));
        if (pointer.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(pointer.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the center widget for the GtkActionBar.
     *
     * @param w the center widget for the GtkActionBar.
     */
    public void setCenterWidget(GtkWidget w) {
        library.gtk_action_bar_set_center_widget(cReference, pointerOrNull(w));
    }

    /**
     * Gets whether the contents of the action bar are revealed.
     *
     * @return TRUE if revealed
     */
    public boolean isRevealed() {
        return library.gtk_action_bar_get_revealed(cReference);
    }

    /**
     * Reveals or conceals the content of the action bar.
     * <p>
     * Note: this does not show or hide action_bar in the GtkWidget:visible sense, so revealing has no effect if the
     * action bar is hidden
     *
     * @param b TRUE if revealed
     */
    public void setRevealed(boolean b) {
        library.gtk_action_bar_set_revealed(cReference, b);
    }

    /**
     * Removes a child from action_bar.
     *
     * @param w The GtkWidget to be removed.
     */
    public void remove(GtkWidget w) {
        if (w != null) {
            library.gtk_action_bar_remove(cReference, w.getCReference());
        }
    }

    protected static class GtkActionBarLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the center bar widget of the bar.
         *
         * @param action_bar self
         * @return The center GtkWidget
         *         The return value can be NULL.
         */
        public native Pointer gtk_action_bar_get_center_widget(Pointer action_bar);

        /**
         * Gets whether the contents of the action bar are revealed.
         * <p>
         * Gets property Gtk.ActionBar:revealed
         *
         * @param action_bar self
         * @return The current value of the GtkActionBar:revealed property.
         */
        public native boolean gtk_action_bar_get_revealed(Pointer action_bar);

        /**
         * Creates a new GtkActionBar widget.
         *
         * @return A new GtkActionBar
         */
        public native Pointer gtk_action_bar_new();

        /**
         * Adds child to action_bar, packed with reference to the end of the action_bar.
         *
         * @param action_bar self
         * @param child      The GtkWidget to be added to action_bar. Type: GtkWidget
         */
        public native void gtk_action_bar_pack_end(Pointer action_bar, Pointer child);

        /**
         * Adds child to action_bar, packed with reference to the start of the action_bar.
         *
         * @param action_bar self
         * @param child      The GtkWidget to be added to action_bar.
         */
        public native void gtk_action_bar_pack_start(Pointer action_bar, Pointer child);

        /**
         * Removes a child from action_bar.
         *
         * @param action_bar self
         * @param child      The GtkWidget to be removed. Type: GtkWidget
         */
        public native void gtk_action_bar_remove(Pointer action_bar, Pointer child);

        /**
         * Sets the center widget for the GtkActionBar.
         *
         * @param action_bar    self
         * @param center_widget A widget to use for the center. Type: GtkWidget
         *                      <p>
         *                      The argument can be NULL.
         */
        public native void gtk_action_bar_set_center_widget(Pointer action_bar, Pointer center_widget);

        /**
         * Reveals or conceals the content of the action bar.
         * <p>
         * Note: this does not show or hide action_bar in the GtkWidget:visible sense, so revealing has no effect if
         * the action bar is hidden.
         * <p>
         * Sets property Gtk.ActionBar:revealed
         *
         * @param action_bar self
         * @param revealed   The new value of the property.
         */
        public native void gtk_action_bar_set_revealed(Pointer action_bar, boolean revealed);
    }
}
