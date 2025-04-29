/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 - 2023 JGTK
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

import com.gitlab.ccook.jgtk.GListModel;
import com.gitlab.ccook.jgtk.GObject;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

@SuppressWarnings("unchecked")
public class GtkStringList extends GObject implements GListModel<String>, GtkBuildable {

    private static final GtkStringListLibrary library = new GtkStringListLibrary();
    int size = -1;

    public GtkStringList(Pointer cReference) {
        super(cReference);
        determineSize();
    }

    private void determineSize() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!getNth(i).isDefined()) {
                size = i;
            }
        }
    }

    public Option<String> getNth(int position) {
        if (position < size) {
            return new Option<>(library.gtk_string_list_get_string(getCReference(), position));
        }
        return Option.NONE;
    }

    public GtkStringList(String... strings) {
        super(library.gtk_string_list_new(strings));
        this.size = strings.length;
    }

    public void append(String s) {
        if (s != null) {
            library.gtk_string_list_append(getCReference(), s);
            size++;
        }
    }

    public void remove(int position) {
        if (position < size) {
            library.gtk_string_list_remove(getCReference(), position);
            size--;
        }
    }

    protected static class GtkStringListLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Appends string to self.
         *
         * @param self   self
         * @param string The string to insert.
         */
        public native void gtk_string_list_append(Pointer self, String string);

        /**
         * Gets the string that is at position in self.
         * <p>
         * If self does not contain position items, NULL is returned.
         *
         * @param self     self
         * @param position The position to get the string for.
         * @return The string at the given position.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_string_list_get_string(Pointer self, int position);

        /**
         * Creates a new GtkStringList with the given strings.
         *
         * @param strings The strings to put in the model.
         *                <p>
         *                The argument can be NULL.
         * @return A new GtkStringList
         */
        public Pointer gtk_string_list_new(String[] strings) {
            return INSTANCE.gtk_string_list_new(strings);
        }

        /**
         * Removes the string at position from self.
         * <p>
         * position must be smaller than the current length of the list.
         *
         * @param self     self
         * @param position The position of the string that is to be removed.
         */
        public native void gtk_string_list_remove(Pointer self, int position);
    }
}
