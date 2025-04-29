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

import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.callbacks.GtkTextTagTableForeach;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public class GtkTextTagTable extends JGTKConnectableObject implements GtkBuildable {
    public GtkTextTagTable() {
        this(library.gtk_text_tag_table_new());
    }

    public GtkTextTagTable(Pointer cReference) {
        super(cReference);
    }

    /**
     * Add a tag to the table.
     * <p>
     * The tag is assigned the highest priority in the table.
     * <p>
     * tag must not be in a tag table already, and may not have the same name as an already-added tag.
     *
     * @param tag A GtkTextTag to add
     * @return TRUE on success.
     */
    public boolean addTag(GtkTextTag tag) {
        if (tag != null) {
            return library.gtk_text_tag_table_add(getCReference(), tag.getCReference());
        }
        return false;
    }

    /**
     * Connect a signal
     *
     * @param s       Detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to the signal
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef) {
        connect(s.getDetailedName(), fn, dataRef, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s     detailed name of signal
     * @param fn    function to invoke on signal
     * @param flags connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, GConnectFlags... flags) {
        connect(s.getDetailedName(), fn, null, flags);
    }

    /**
     * Connect a signal
     *
     * @param s  detailed name of signal
     * @param fn function to invoke on signal
     */
    public void connect(Signals s, GCallbackFunction fn) {
        connect(s.getDetailedName(), fn, Pointer.NULL, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s       detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
        connect(new GtkCallbackFunction() {
            @Override
            public GConnectFlags[] getConnectFlag() {
                return flags;
            }

            @Override
            public Pointer getDataReference() {
                return dataRef;
            }

            @Override
            public String getDetailedSignal() {
                return s.getDetailedName();
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                fn.invoke(relevantThing, relevantData);
            }
        });
    }

    /**
     * Calls func on each tag in table, with user data.
     * <p>
     * Note that the table may not be modified while iterating over it (you can't add/remove tags).
     *
     * @param func     A function to call on each tag.
     * @param userData User data.
     *                 <p>
     *                 The argument can be NULL.
     */
    public void forEach(GtkTextTagTableForeach func, Pointer userData) {
        if (func != null) {
            library.gtk_text_tag_table_foreach(getCReference(), func, userData);
        }
    }

    /**
     * Remove a tag from the table.
     * <p>
     * If a GtkTextBuffer has table as its tag table, the tag is removed from the buffer. The table's reference to the
     * tag is removed, so the tag will end up destroyed if you don't have a reference to it.
     *
     * @param name a valid tag name
     */
    public void remove(String name) {
        Option<GtkTextTag> tag = get(name);
        if (tag.isDefined()) {
            remove(tag.get());
        }
    }

    /**
     * Look up a named tag.
     *
     * @param name Name of a tag.
     * @return The tag, if defined
     */
    public Option<GtkTextTag> get(String name) {
        if (name != null) {
            Option<Pointer> p = new Option<>(library.gtk_text_tag_table_lookup(getCReference(), name));
            if (p.isDefined()) {
                return new Option<>(new GtkTextTag(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Remove a tag from the table.
     * <p>
     * If a GtkTextBuffer has table as its tag table, the tag is removed from the buffer. The table's reference to the
     * tag is removed, so the tag will end up destroyed if you don't have a reference to it.
     *
     * @param tag A GtkTextTag
     */
    public void remove(GtkTextTag tag) {
        if (tag != null) {
            library.gtk_text_tag_table_remove(getCReference(), tag.getCReference());
        }
    }

    /**
     * Returns the size of the table (number of tags)
     *
     * @return Number of tags in table.
     */
    public int size() {
        return library.gtk_text_tag_table_get_size(getCReference());
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted every time a new tag is added in the GtkTextTagTable.
         */
        public static final Signals TAG_ADDED = new Signals("tag-added");
        /**
         * Emitted every time a tag in the GtkTextTagTable changes.
         */
        public static final Signals TAG_CHANGED = new Signals("tag-changed");
        /**
         * Emitted every time a tag is removed from the GtkTextTagTable.
         */
        public static final Signals TAG_REMOVED = new Signals("tag-removed");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }
}
