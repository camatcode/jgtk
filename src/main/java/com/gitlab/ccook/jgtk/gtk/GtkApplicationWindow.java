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

import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.GtkShortcutManager;
import com.gitlab.ccook.jgtk.GtkShortcutsWindow;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkApplicationWindow is a GtkWindow subclass that integrates with GtkApplication.
 * <p>
 * Notably, GtkApplicationWindow can handle an application menubar.
 * <p>
 * This class implements the GActionGroup and GActionMap interfaces, to let you add window-specific actions that will be
 * exported by the associated GtkApplication, together with its application-wide actions. Window-specific actions are
 * prefixed with the "win." prefix and application-wide actions are prefixed with the "app." prefix. Actions must be
 * addressed with the prefixed name when referring to them from a GMenuModel.
 * <p>
 * Note that widgets that are placed inside a GtkApplicationWindow can also activate these actions, if they implement
 * the GtkActionable interface.
 * <p>
 * The settings GtkSettings:gtk-shell-shows-app-menu and GtkSettings:gtk-shell-shows-menubar tell GTK whether the
 * desktop environment is showing the application menu and menubar models outside the application as part of the
 * desktop shell. For instance, on OS X, both menus will be displayed remotely; on Windows neither will be.
 * <p>
 * If the desktop environment does not display the menubar, then GtkApplicationWindow will automatically show a menubar
 * for it. This behaviour can be overridden with the GtkApplicationWindow:show-menubar property. If the desktop
 * environment does not display the application menu, then it will automatically be included in the menubar or in the
 * windows client-side decorations.
 * <p>
 * See GtkPopoverMenu for information about the XML language used by GtkBuilder for menu models.
 * <p>
 * See also: gtk_application_set_menubar().
 */
@SuppressWarnings({"unchecked", "unused"})
public class GtkApplicationWindow extends GtkWindow implements GActionGroup, GActionMap, GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkApplicationWindowLibrary library = new GtkApplicationWindowLibrary();

    /**
     * Creates a new GtkApplicationWindow.
     *
     * @param associatedApplication A GtkApplication
     */
    public GtkApplicationWindow(GtkApplication associatedApplication) {
        super(library.gtk_application_window_new(associatedApplication.getCReference()));
    }

    /**
     * GtkApplicationWindow from Reference
     *
     * @param gtkAppWindowPointer pointer to application associated with window
     */
    public GtkApplicationWindow(Pointer gtkAppWindowPointer) {
        super(gtkAppWindowPointer);
    }

    /**
     * Returns whether the window will display a menubar for the app menu and menubar as needed.
     *
     * @return TRUE if window will display a menubar when needed.
     */
    public boolean doesShowMenuBar() {
        return library.gtk_application_window_get_show_menubar(getCReference());
    }

    /**
     * @return returns associated help overlay
     */
    public Option<GtkShortcutsWindow> getHelpOverlay() {
        Option<Pointer> pointer = new Option<>(library.gtk_application_window_get_help_overlay(getCReference()));
        if (pointer.isDefined()) {
            return new Option<>((GtkShortcutsWindow) JGTKObject.newObjectFromType(pointer.get(), GtkShortcutsWindow.class));
        }
        return Option.NONE;
    }

    /**
     * Associates a shortcuts window with the application window.
     * <p>
     * Additionally, sets up an action with the name win.show-help-overlay to present it.
     * <p>
     * window takes responsibility for destroying help_overlay.
     *
     * @param helpOverlay overlay to associate; Null will unset
     */
    public void setHelpOverlay(GtkShortcutsWindow helpOverlay) {
        library.gtk_application_window_set_help_overlay(getCReference(), pointerOrNull(helpOverlay));
    }

    /**
     * Returns the unique ID of the window.
     *
     * @return the id of the window, if it's been added to the GtkApplication
     */
    public Option<Integer> getWindowId() {
        int id = library.gtk_application_window_get_id(getCReference());
        if (id > 0) {
            return new Option<>(id);
        }
        return Option.NONE;
    }

    /**
     * Sets whether the window will display a menubar for the app menu and menubar as needed.
     *
     * @param willShowWhenNeeded Whether to show a menubar when needed.
     */
    public void shouldShowMenuBar(boolean willShowWhenNeeded) {
        library.gtk_application_window_set_show_menubar(getCReference(), willShowWhenNeeded);
    }

    protected static class GtkApplicationWindowLibrary extends GtkWindowLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the GtkShortcutsWindow that is associated with window.
         * <p>
         * See gtk_application_window_set_help_overlay().
         *
         * @param window self
         * @return The help overlay associated with window. Type: GtkShortcutsWindow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_application_window_get_help_overlay(Pointer window);

        /**
         * Returns the unique ID of the window.
         * <p>
         * If the window has not yet been added to a GtkApplication, returns 0.
         *
         * @param window self
         * @return The unique ID for window, or 0 if the window has not yet been added to a GtkApplication
         */
        public native int gtk_application_window_get_id(Pointer window);

        /**
         * Returns whether the window will display a menubar for the app menu and menubar as needed.
         *
         * @param window self
         * @return TRUE if window will display a menubar when needed.
         */
        public native boolean gtk_application_window_get_show_menubar(Pointer window);

        /**
         * Creates a new GtkApplicationWindow.
         *
         * @param application A GtkApplication
         * @return A newly created GtkApplicationWindow
         */
        public native Pointer gtk_application_window_new(Pointer application);

        /**
         * Associates a shortcuts window with the application window.
         * <p>
         * Additionally, sets up an action with the name win.show-help-overlay to present it.
         * <p>
         * window takes responsibility for destroying help_overlay.
         *
         * @param window       self
         * @param help_overlay A GtkShortcutsWindow
         *                     <p>
         *                     The argument can be NULL.
         */
        public native void gtk_application_window_set_help_overlay(Pointer window, Pointer help_overlay);

        /**
         * Sets whether the window will display a menubar for the app menu and menubar as needed.
         *
         * @param window       self
         * @param show_menubar Whether to show a menubar when needed.
         */
        public native void gtk_application_window_set_show_menubar(Pointer window, boolean show_menubar);
    }

}
