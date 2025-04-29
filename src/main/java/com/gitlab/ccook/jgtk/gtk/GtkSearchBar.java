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
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkSearchBar is a container made to have a search entry.
 * It can also contain additional widgets, such as drop-down menus, or buttons. The search bar would appear when a
 * search is started through typing on the keyboard, or the application's search mode is toggled on.
 * <p>
 * For keyboard presses to start a search, the search bar must be told of a widget to capture key events from through
 * gtk_search_bar_set_key_capture_widget(). This widget will typically be the top-level window, or a parent container
 * of the search bar. Common shortcuts such as Ctrl+F should be handled as an application action, or through the menu
 * items.
 * <p>
 * You will also need to tell the search bar about which entry you are using as your search entry using
 * gtk_search_bar_connect_entry().
 */
@SuppressWarnings("unchecked")
public class GtkSearchBar extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkSearchBarLibrary library = new GtkSearchBarLibrary();

    public GtkSearchBar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a GtkSearchBar.
     * <p>
     */
    public GtkSearchBar(GtkEditable entry) {
        super(library.gtk_search_bar_new());
        connectEntry(entry);
    }

    /**
     * Connects the GtkEditable widget passed as the one to be used in this search bar.
     * <p>
     * The entry should be a descendant of the search bar. Calling this function manually is only required if the entry
     * isn't the direct child of the search bar (as in our main example).
     *
     * @param e A GtkEditable
     */
    public void connectEntry(GtkEditable e) {
        if (e != null) {
            library.gtk_search_bar_connect_entry(getCReference(), e.getCReference());
        }
    }

    /**
     * Returns whether the close button is shown.
     *
     * @return Whether the close button is shown.
     */
    public boolean doesShowCloseButton() {
        return library.gtk_search_bar_get_show_close_button(getCReference());
    }

    /**
     * Gets the child widget of bar.
     *
     * @return The child widget of bar, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_search_bar_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of bar.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget w) {
        library.gtk_search_bar_set_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Gets the widget that bar is capturing key events from.
     *
     * @return The key capture widget, if defined
     */
    public Option<GtkWidget> getKeyCaptureWidget() {
        Option<Pointer> p = new Option<>(library.gtk_search_bar_get_key_capture_widget(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets widget as the widget that bar will capture key events from.
     * <p>
     * If key events are handled by the search bar, the bar will be shown, and the entry populated with the entered
     * text.
     * <p>
     * Note that despite the name of this function, the events are only 'captured' in the bubble phase, which means
     * that editable child widgets of widget will receive text input before it gets captured. If that is not desired,
     * you can capture and forward the events yourself with gtk_event_controller_key_forward().
     *
     * @param w A GtkWidget
     *          <p>
     *          The argument can be NULL.
     */
    public void setKeyCaptureWidget(GtkWidget w) {
        library.gtk_search_bar_set_key_capture_widget(getCReference(), pointerOrNull(w));
    }

    /**
     * Returns whether the search mode is on.
     *
     * @return Whether search mode is toggled on.
     */
    public boolean isSearchModeOn() {
        return library.gtk_search_bar_get_search_mode(getCReference());
    }

    /**
     * Switches the search mode on or off.
     *
     * @param isSearchMode `TRUE` if search mode is on. Otherwise, `FALSE`
     */
    public void setSearchModeOn(boolean isSearchMode) {
        library.gtk_search_bar_set_search_mode(getCReference(), isSearchMode);
    }

    /**
     * Shows or hides the close button.
     * <p>
     * Applications that already have a "search" toggle button should not show a close button in their search bar,
     * as it duplicates the role of the toggle button.
     *
     * @param shouldShow Whether the close button will be shown
     */
    public void shouldShowCloseButton(boolean shouldShow) {
        library.gtk_search_bar_set_show_close_button(getCReference(), shouldShow);
    }

    protected static class GtkSearchBarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Connects the GtkEditable widget passed as the one to be used in this search bar.
         * <p>
         * The entry should be a descendant of the search bar. Calling this function manually is only required if the
         * entry isn't the direct child of the search bar (as in our main example).
         *
         * @param bar   self
         * @param entry A GtkEditable
         */
        public native void gtk_search_bar_connect_entry(Pointer bar, Pointer entry);

        /**
         * Gets the child widget of bar.
         *
         * @param bar self
         * @return The child widget of bar. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_search_bar_get_child(Pointer bar);

        /**
         * Gets the widget that bar is capturing key events from.
         *
         * @param bar self
         * @return The key capture widget. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_search_bar_get_key_capture_widget(Pointer bar);

        /**
         * Returns whether the search mode is on or off.
         *
         * @param bar self
         * @return Whether search mode is toggled on.
         */
        public native boolean gtk_search_bar_get_search_mode(Pointer bar);

        /**
         * Returns whether the close button is shown.
         *
         * @param bar self
         * @return Whether the close button is shown.
         */
        public native boolean gtk_search_bar_get_show_close_button(Pointer bar);

        /**
         * Creates a GtkSearchBar.
         * <p>
         * You will need to tell it about which widget is going to be your text entry using
         * gtk_search_bar_connect_entry().
         *
         * @return A new GtkSearchBar
         */
        public native Pointer gtk_search_bar_new();

        /**
         * Sets the child widget of bar.
         *
         * @param bar   self
         * @param child The child widget. Type: GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_search_bar_set_child(Pointer bar, Pointer child);

        /**
         * Sets widget as the widget that bar will capture key events from.
         * <p>
         * If key events are handled by the search bar, the bar will be shown, and the entry populated with the entered
         * text.
         * <p>
         * Note that despite the name of this function, the events are only 'captured' in the bubble phase, which means
         * that editable child widgets of widget will receive text input before it gets captured. If that is not
         * desired, you can capture and forward the events yourself with gtk_event_controller_key_forward().
         *
         * @param bar    self
         * @param widget A GtkWidget. Type: GtkWidget
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_search_bar_set_key_capture_widget(Pointer bar, Pointer widget);

        /**
         * Switches the search mode on or off.
         *
         * @param bar         self
         * @param search_mode The new state of the search mode.
         */
        public native void gtk_search_bar_set_search_mode(Pointer bar, boolean search_mode);

        /**
         * Shows or hides the close button.
         * <p>
         * Applications that already have a "search" toggle button should not show a close button in their search bar,
         * as it duplicates the role of the toggle button.
         *
         * @param bar     self
         * @param visible Whether the close button will be shown
         */
        public native void gtk_search_bar_set_show_close_button(Pointer bar, boolean visible);
    }

}
