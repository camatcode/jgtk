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
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public class GtkEntryBuffer extends JGTKObject {
    public GtkEntryBuffer(Pointer cReference) {
        super(cReference);
    }

    public GtkEntryBuffer() {
        super(library.gtk_entry_buffer_new("", -1));
    }

    public GtkEntryBuffer(String initialCharacters) {
        super(library.gtk_entry_buffer_new(initialCharacters, initialCharacters.length()));
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
     * @param s       detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    public void connect(String s, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
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
                return s;
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                fn.invoke(relevantThing, relevantData);
            }
        });
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

    public int deleteText(int startPosition, int numToDelete) {
        return library.gtk_entry_buffer_delete_text(getCReference(), startPosition, numToDelete);
    }

    /**
     * Used when subclassing GtkEntryBuffer.
     *
     * @param startPosition Position at which text was deleted.
     * @param numDeleted    Number of characters deleted.
     */
    public void emitDeleteTextSignal(int startPosition, int numDeleted) {
        library.gtk_entry_buffer_emit_deleted_text(getCReference(), startPosition, numDeleted);
    }

    /**
     * Used when subclassing GtkEntryBuffer.
     *
     * @param startPosition Position at which text was inserted.
     * @param textInserted  Text that was inserted.
     */
    public void emitTextInsertedSignal(int startPosition, String textInserted) {
        if (textInserted != null) {
            library.gtk_entry_buffer_emit_inserted_text(getCReference(), startPosition, textInserted, textInserted.length());
        }
    }

    /**
     * Retrieves the maximum allowed length of the text in buffer.
     *
     * @return The maximum allowed number of characters in GtkEntryBuffer, or NONE if there is no maximum.
     */
    public Option<Integer> getMaxLength() {
        int maxLength = library.gtk_entry_buffer_get_max_length(getCReference());
        if (maxLength > 0) {
            return new Option<>(maxLength);
        }
        return Option.NONE;
    }

    /**
     * Sets the maximum allowed length of the contents of the buffer.
     * <p>
     * If the current contents are longer than the given length, then they will be truncated to fit.
     *
     * @param maxLength The maximum length of the entry buffer, or 0 for no maximum. (other than the maximum length of
     *                  entries.) The value passed in will be clamped to the range 0-65536.
     */
    public void setMaxLength(int maxLength) {
        library.gtk_entry_buffer_set_max_length(getCReference(), Math.max(maxLength, 0));
    }

    /**
     * Retrieves the contents of the buffer.
     * <p>
     * The memory pointer returned by this call will not change unless this object emits a signal, or is finalized.
     *
     * @return A pointer to the contents of the widget as a string. This string points to internally allocated storage
     *         in the buffer and must not be freed, modified or stored.
     */
    public String getText() {
        return library.gtk_entry_buffer_get_text(getCReference());
    }

    /**
     * Retrieves the length in characters of the buffer.
     *
     * @return The number of characters in the buffer.
     */
    public int getTextLength() {
        return library.gtk_entry_buffer_get_length(getCReference());
    }

    /**
     * Inserts toInsert at startPosition
     *
     * @param startPosition The position at which to insert text.
     * @param toInsert      The text to insert into the buffer.
     */
    public void insertText(int startPosition, String toInsert) {
        if (toInsert != null) {
            library.gtk_entry_buffer_insert_text(getCReference(), startPosition, toInsert, toInsert.length());
        }
    }

    public static class Signals extends GtkWidget.Signals {
        public static final Signals DELETED_TEXT = new Signals("deleted-text");
        public static final Signals INSERTED_TEXT = new Signals("inserted-text");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }

}
