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

import com.gitlab.ccook.jgtk.GList;
import com.gitlab.ccook.jgtk.GObject;
import com.gitlab.ccook.jna.GtkLibrary;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkWindowGroup makes group of windows behave like separate applications.
 * <p>
 * It achieves this by limiting the effect of GTK grabs and modality to windows in the same group.
 * <p>
 * A window can be a member in at most one window group at a time. Windows that have not been explicitly assigned to a
 * group are implicitly treated like windows of the default window group.
 * <p>
 * GtkWindowGroup objects are referenced by each window in the group, so once you have added all windows to a
 * GtkWindowGroup, you can drop the initial reference to the window group with g_object_unref(). If the windows in the
 * window group are subsequently destroyed, then they will be removed from the window group and drop their references
 * on the window group; when all window have been removed, the window group will be freed.
 */
public class GtkWindowGroup extends GObject {

    private static final GtkWindowGroupLibrary library = new GtkWindowGroupLibrary();

    public GtkWindowGroup(Pointer p) {
        super(p);
    }

    public GtkWindowGroup() {
        super(library.gtk_window_group_new());
    }

    public void addWindow(GtkWindow w) {
        if (w != null) {
            library.gtk_window_group_add_window(getCReference(), w.getCReference());
        }
    }

    public GList<GtkWindow> getWindows() {
        return new GList<>(GtkWindow.class, library.gtk_window_group_list_windows(getCReference()));
    }

    public void removeWindow(GtkWindow w) {
        if (w != null) {
            library.gtk_window_group_remove_window(getCReference(), w.getCReference());
        }
    }

    protected static class GtkWindowGroupLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a window to a GtkWindowGroup.
         *
         * @param window_group self
         * @param window       The GtkWindow to add.
         */
        public native void gtk_window_group_add_window(Pointer window_group, Pointer window);

        /**
         * Returns a list of the GtkWindows that belong to window_group.
         *
         * @param window_group self
         * @return A newly-allocated list of windows inside the group.
         */
        public native Pointer gtk_window_group_list_windows(Pointer window_group);

        /**
         * Creates a new GtkWindowGroup object.
         * <p>
         * Modality of windows only affects windows within the same GtkWindowGroup.
         *
         * @return A new GtkWindowGroup.
         */
        public native Pointer gtk_window_group_new();

        /**
         * Removes a window from a GtkWindowGroup.
         *
         * @param window_group self
         * @param window       The GtkWindow to remove.
         */
        public native void gtk_window_group_remove_window(Pointer window_group, Pointer window);
    }
}
