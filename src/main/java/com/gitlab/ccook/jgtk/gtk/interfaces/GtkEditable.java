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
package com.gitlab.ccook.jgtk.gtk.interfaces;

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkInterface;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * GtkEditable is an interface for text editing widgets.
 * <p>
 * Typical examples of editable widgets are GtkEntry and GtkSpinButton. It contains functions for generically
 * manipulating an editable widget, a large number of action signals used for key bindings, and several signals that an
 * application can connect to modify the behavior of a widget.
 * <p>
 * As an example of the latter usage, by connecting the following handler to GtkEditable::insert-text, an application
 * can convert all entry into a widget into uppercase.
 */
@SuppressWarnings("unchecked")
public interface GtkEditable extends GtkInterface {


    GtkEditableLibrary editableLibrary = new GtkEditableLibrary();

    /**
     * Connect a signal
     *
     * @param s       Detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to the signal
     */
    default void connect(Signals s, GCallbackFunction fn, Pointer dataRef) {
        connect(s.getDetailedName(), fn, dataRef, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param sigName detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    default void connect(String sigName, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
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
                return sigName;
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
    default void connect(Signals s, GCallbackFunction fn, GConnectFlags... flags) {
        connect(s.getDetailedName(), fn, null, flags);
    }

    /**
     * Connect a signal
     *
     * @param s  detailed name of signal
     * @param fn function to invoke on signal
     */
    default void connect(Signals s, GCallbackFunction fn) {
        connect(s.getDetailedName(), fn, Pointer.NULL, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Deletes the currently selected text of the editable.
     * <p>
     * This call doesn't do anything if there is no selected text.
     */
    default void deleteSelectedText() {
        editableLibrary.gtk_editable_delete_selection(getCReference());
    }

    /**
     * Deletes a sequence of characters.
     * <p>
     * The characters that are deleted are those characters at positions from start_pos up to, but not including
     * end_pos.
     * If end_pos is negative, then the characters deleted are those from start_pos to the end of the text.
     * <p>
     * Note that the positions are specified in characters, not bytes.
     *
     * @param startPos Start position of text to delete
     * @param endPos   End position of text to delete
     */
    default void deleteTextByPosition(int startPos, int endPos) {
        editableLibrary.gtk_editable_delete_text(getCReference(), startPos, endPos);
    }

    /**
     * Gets if undo/redo actions are enabled for editable.
     *
     * @return TRUE if undo/redo actions are enabled
     */
    default boolean doesAllowUndoRedoActions() {
        return editableLibrary.gtk_editable_get_enable_undo(getCReference());
    }

    /**
     * Undoes the setup done by gtk_editable_init_delegate().
     * <p>
     * This is a helper function that should be called from dispose, before removing the delegate object.
     */
    default void finishDelegate() {
        editableLibrary.gtk_editable_finish_delegate(getCReference());
    }

    /**
     * Gets the alignment of the editable.
     *
     * @return the alignment
     */
    default float getAlignment() {
        return editableLibrary.gtk_editable_get_alignment(getCReference());
    }

    /**
     * Sets the alignment for the contents of the editable.
     * <p>
     * This controls the horizontal positioning of the contents when the displayed text is shorter than the width of
     * the editable.
     *
     * @param alignment The horizontal alignment, from 0 (left) to 1 (right). Reversed for RTL layouts.
     */
    default void setAlignment(float alignment) {
        if (alignment >= 0 && alignment <= 1) {
            editableLibrary.gtk_editable_set_alignment(getCReference(), alignment);
        }
    }

    /**
     * Gets the number of characters of space reserved for the contents of the editable
     *
     * @return Number of chars to request space for, or negative if unset.
     */
    default Option<Integer> getCharacterWidth() {
        int value = editableLibrary.gtk_editable_get_width_chars(getCReference());
        if (value >= 0) {
            return new Option<>(value);
        }
        return Option.NONE;
    }

    /**
     * Changes the size request of the editable to be about the right size for n_chars characters.
     * <p>
     * Note that it changes the size request, the size can still be affected by how you pack the widget into containers.
     * If n_chars is -1, the size reverts to the default size.
     *
     * @param width Width in chars.
     */
    default void setCharacterWidth(int width) {
        editableLibrary.gtk_editable_set_width_chars(getCReference(), width);
    }

    /**
     * Retrieves the current position of the cursor relative to the start of the content of the editable.
     * <p>
     * Note that this position is in characters, not in bytes.
     *
     * @return The cursor position relative to the start
     */
    default int getCursorPosition() {
        return editableLibrary.gtk_editable_get_position(getCReference());
    }

    /**
     * Sets the cursor position in the editable to the given value.
     * <p>
     * The cursor is displayed before the character with the given (base 0) index in the contents of the editable.
     * The value must be less than or equal to the number of characters in the editable. A
     * value of -1 indicates that the position should be set after the last character of the editable.
     * Note that position is in characters, not in bytes.
     *
     * @param position cursor position
     */
    default void setCursorPosition(int position) {
        editableLibrary.gtk_editable_set_position(getCReference(), position);
    }

    /**
     * Gets the GtkEditable that editable is delegating its implementation to.
     * <p>
     * Typically, the delegate is a GtkText widget.
     *
     * @return The delegate GtkEditable
     *         <p>
     *         The return value can be NULL.
     */
    default Option<GtkEditable> getDelegate() {
        Option<Pointer> p = new Option<>(editableLibrary.gtk_editable_get_delegate(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkEditable) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Retrieves the desired maximum width of editable, in characters.
     *
     * @return The maximum width of the entry, in characters.
     */
    default Option<Integer> getMaxCharacters() {
        int value = editableLibrary.gtk_editable_get_max_width_chars(getCReference());
        if (value >= 0) {
            return new Option<>(value);
        }
        return Option.NONE;
    }

    /**
     * Sets the desired maximum width in characters of editable.
     *
     * @param width The new desired maximum width, in characters.
     */
    default void setMaxCharacters(int width) {
        editableLibrary.gtk_editable_set_max_width_chars(getCReference(), width);
    }

    default String getText() {
        return editableLibrary.gtk_editable_get_text(getCReference());
    }

    /**
     * Sets the text in the editable to the given value.
     * <p>
     * This is replacing the current contents.
     *
     * @param text The text to set.
     */
    default void setText(String text) {
        if (text != null) {
            editableLibrary.gtk_editable_set_text(getCReference(), text);
        } else {
            editableLibrary.gtk_editable_set_text(getCReference(), "");
        }
    }

    /**
     * Retrieves a sequence of characters.
     * <p>
     * The characters that are retrieved are those characters at positions from start_pos up to, but not
     * including end_pos. If end_pos is negative, then the characters retrieved are those characters from start_pos
     * to the end of the text.
     * <p>
     * Note that positions are specified in characters, not bytes.
     *
     * @param startPos Start of text to grab
     * @param endPos   End of text to return.
     * @return Text from the given positions
     */
    default String getTextFromPosition(int startPos, int endPos) {
        return editableLibrary.gtk_editable_get_chars(getCReference(), startPos, endPos);
    }

    /**
     * Retrieves the selection bound of the editable.
     * <p>
     * startPos will be filled with the start of the selection and endPos with end.
     * If no text was selected both will be identical and NONE will be returned.
     * <p>
     * Note that positions are specified in characters, not bytes.
     *
     * @return If defined, start and end positions for selection
     */
    default Option<Pair<Integer, Integer>> getTextSelectedBounds() {
        PointerByReference startPositionStore = new PointerByReference();
        PointerByReference endPositionStore = new PointerByReference();
        boolean isSelectionNonEmpty = editableLibrary.gtk_editable_get_selection_bounds(getCReference(), startPositionStore, endPositionStore);
        if (isSelectionNonEmpty) {
            int start = startPositionStore.getPointer().getInt(0);
            int end = endPositionStore.getPointer().getInt(0);
            return new Option<>(new Pair<>(start, end));
        }
        return Option.NONE;
    }

    /**
     * Sets up a delegate for GtkEditable.
     * <p>
     * This is assuming that the get_delegate virtual func in the GtkEditable interface has been set up for the
     * editable's type.
     * <p>
     * This is a helper function that should be called in instance init, after creating the delegate object.
     */
    default void initDelegate() {
        editableLibrary.gtk_editable_init_delegate(getCReference());
    }

    /**
     * Inserts length bytes of text into the contents of the widget, at position startPosition.
     * <p>
     * Note that the position is in characters, not in bytes. The function updates position to point after the newly
     * inserted text.
     *
     * @param text             The text to insert
     * @param positionToInsert the index, in characters, to insert text
     * @return updated position after insertion
     */
    default int insertText(String text, int positionToInsert) {
        if (text != null) {
            PointerByReference ref = new PointerByReference();
            ref.getPointer().setInt(0, Math.max(0, positionToInsert));
            editableLibrary.gtk_editable_insert_text(getCReference(), text, text.length(), ref);
            return ref.getPointer().getInt(0);
        }
        return -1;
    }

    /**
     * Retrieves whether this is editable.
     *
     * @return TRUE if editable
     */
    default boolean isEditable() {
        return editableLibrary.gtk_editable_get_editable(getCReference());
    }

    /**
     * Determines if the user can edit the text in the editable widget.
     *
     * @param canEdit TRUE if the user is allowed to edit the text in the widget.
     */
    default void setEditable(boolean canEdit) {
        editableLibrary.gtk_editable_set_editable(getCReference(), canEdit);
    }

    /**
     * Selects a region of text.
     * <p>
     * The characters that are selected are those characters at positions from start_pos up to, but not including
     * end_pos. If end_pos is negative, then the characters selected are those characters from start_pos to the
     * end of the text.
     * <p>
     * Note that positions are specified in characters, not bytes.
     *
     * @param startPos Start of region to select
     * @param endPos   End of region to select
     */
    default void selectRegion(int startPos, int endPos) {
        editableLibrary.gtk_editable_select_region(getCReference(), startPos, endPos);
    }

    /**
     * If enabled, changes to editable will be saved for undo/redo actions.
     * <p>
     * This results in an additional copy of text changes and are not stored in secure memory.
     * As such, undo is forcefully disabled when GtkText:visibility is set to FALSE.
     *
     * @param canUndoRedo TRUE if undo/redo should be enabled.
     */
    default void shouldAllowUndoRedoActions(boolean canUndoRedo) {
        editableLibrary.gtk_editable_set_enable_undo(getCReference(), canUndoRedo);
    }

    class Signals extends GtkWidget.Signals {
        /**
         * Emitted at the end of a single user-visible operation on the contents.
         * <p>
         * E.g., a paste operation that replaces the contents of the selection will cause only one signal emission
         * (even though it is implemented by first deleting the selection, then inserting the new content, and may cause
         * multiple ::notify::text signals to be emitted).
         */
        public final static Signals CHANGED = new Signals("changed");
        /**
         * Emitted when text is deleted from the widget by the user.
         * <p>
         * The default handler for this signal will normally be responsible for deleting the text, so by connecting to
         * this signal and then stopping the signal with g_signal_stop_emission(), it is possible to modify the range
         * of deleted text, or prevent it from being deleted entirely.
         * <p>
         * The start_pos and end_pos parameters are interpreted as for gtk_editable_delete_text().
         */
        public final static Signals DELETE_TEXT = new Signals("delete-text");
        /**
         * Emitted when text is inserted into the widget by the user.
         * <p>
         * The default handler for this signal will normally be responsible for inserting the text, so by connecting to
         * this signal and then stopping the signal with g_signal_stop_emission(), it is possible to modify the inserted
         * text, or prevent it from being inserted entirely.
         */
        public final static Signals INSERT_TEXT = new Signals("insert-text");

        Signals(String detailedName) {
            super(detailedName);
        }
    }

    class GtkEditableLibrary extends GtkWidget.GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Deletes the currently selected text of the editable.
         * <p>
         * This call doesn't do anything if there is no selected text.
         *
         * @param editable self
         */
        public native void gtk_editable_delete_selection(Pointer editable);

        /**
         * Deletes a sequence of characters.
         * <p>
         * The characters that are deleted are those characters at positions from start_pos up to, but not including
         * end_pos. If end_pos is negative, then the characters deleted are those from start_pos to the end of the text.
         * <p>
         * Note that the positions are specified in characters, not bytes.
         *
         * @param editable  self
         * @param start_pos Start position.
         * @param end_pos   End position.
         */
        public native void gtk_editable_delete_text(Pointer editable, int start_pos, int end_pos);

        /**
         * Undoes the setup done by gtk_editable_init_delegate().
         * <p>
         * This is a helper function that should be called from dispose, before removing the delegate object.
         *
         * @param editable self
         */
        public native void gtk_editable_finish_delegate(Pointer editable);

        /**
         * Gets the alignment of the editable.
         *
         * @param editable self
         * @return The alignment.
         */
        public native float gtk_editable_get_alignment(Pointer editable);

        /**
         * Retrieves a sequence of characters.
         * <p>
         * The characters that are retrieved are those characters at positions from start_pos up to, but not including
         * end_pos. If end_pos is negative, then the characters retrieved are those characters from start_pos to the e
         * nd of the text.
         * <p>
         * Note that positions are specified in characters, not bytes.
         *
         * @param editable  self
         * @param start_pos Start of text.
         * @param end_pos   End of text.
         * @return A pointer to the contents of the widget as a string. This string is allocated by the GtkEditable
         *         implementation and should be freed by the caller.
         */
        public native String gtk_editable_get_chars(Pointer editable, int start_pos, int end_pos);

        /**
         * Gets the GtkEditable that editable is delegating its implementation to.
         * <p>
         * Typically, the delegate is a GtkText widget.
         *
         * @param editable self
         * @return The delegate GtkEditable. Type: GtkEditable
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_editable_get_delegate(Pointer editable);

        /**
         * Retrieves whether editable is editable.
         *
         * @param editable self
         * @return TRUE if editable is editable.
         */
        public native boolean gtk_editable_get_editable(Pointer editable);

        /**
         * Gets if undo/redo actions are enabled for editable.
         *
         * @param editable self
         * @return TRUE if undo is enabled.
         */
        public native boolean gtk_editable_get_enable_undo(Pointer editable);

        /**
         * Retrieves the desired maximum width of editable, in characters.
         *
         * @param editable self
         * @return The maximum width of the entry, in characters.
         */
        public native int gtk_editable_get_max_width_chars(Pointer editable);

        /**
         * Retrieves the current position of the cursor relative to the start of the content of the editable.
         * <p>
         * Note that this position is in characters, not in bytes.
         *
         * @param editable self
         * @return The cursor position.
         */
        public native int gtk_editable_get_position(Pointer editable);

        /**
         * Retrieves the selection bound of the editable.
         * <p>
         * start_pos will be filled with the start of the selection and end_pos with end. If no text was selected both
         * will be identical and FALSE will be returned.
         * <p>
         * Note that positions are specified in characters, not bytes.
         *
         * @param editable  self
         * @param start_pos Location to store the starting position.
         *                  <p>
         *                  The argument can be NULL.
         * @param end_pos   Location to store the end position.
         *                  <p>
         *                  The argument can be NULL.
         * @return TRUE if there is a non-empty selection, FALSE otherwise.
         */
        public native boolean gtk_editable_get_selection_bounds(Pointer editable, PointerByReference start_pos, PointerByReference end_pos);

        /**
         * Retrieves the contents of editable.
         *
         * @param editable self
         * @return A pointer to the contents of the editable.
         */
        public native String gtk_editable_get_text(Pointer editable);

        /**
         * Gets the number of characters of space reserved for the contents of the editable.
         *
         * @param editable self
         * @return Number of chars to request space for, or negative if unset.
         */
        public native int gtk_editable_get_width_chars(Pointer editable);

        /**
         * Sets up a delegate for GtkEditable.
         * <p>
         * This is assuming that the get_delegate virtual func in the GtkEditable interface has been set up for the
         * editable's type.
         * <p>
         * This is a helper function that should be called in instance init, after creating the delegate object.
         *
         * @param editable self
         */
        public native void gtk_editable_init_delegate(Pointer editable);

        /**
         * Inserts length bytes of text into the contents of the widget, at position called position.
         * <p>
         * Note that the position is in characters, not in bytes. The function updates position to point after the
         * newly inserted text.
         *
         * @param editable self
         * @param text     The text to insert.
         * @param length   The length of the text in bytes, or -1
         * @param position Location of the position text will be inserted at.
         */
        public native void gtk_editable_insert_text(Pointer editable, String text, int length, PointerByReference position);

        /**
         * Selects a region of text.
         * <p>
         * The characters that are selected are those characters at positions from start_pos up to, but not including
         * end_pos. If end_pos is negative, then the characters selected are those characters from start_pos to the end
         * of the text.
         * <p>
         * Note that positions are specified in characters, not bytes.
         *
         * @param editable  self
         * @param start_pos Start of region.
         * @param end_pos   End of region.
         */
        public native void gtk_editable_select_region(Pointer editable, int start_pos, int end_pos);

        /**
         * Sets the alignment for the contents of the editable.
         * <p>
         * This controls the horizontal positioning of the contents when the displayed text is shorter than the width
         * of the editable.
         *
         * @param editable self
         * @param xalign   The horizontal alignment, from 0 (left) to 1 (right). Reversed for RTL layouts.
         */
        public native void gtk_editable_set_alignment(Pointer editable, float xalign);

        /**
         * Determines if the user can edit the text in the editable widget.
         *
         * @param editable    self
         * @param is_editable TRUE if the user is allowed to edit the text in the widget.
         */
        public native void gtk_editable_set_editable(Pointer editable, boolean is_editable);

        /**
         * If enabled, changes to editable will be saved for undo/redo actions.
         * <p>
         * This results in an additional copy of text changes and are not stored in secure memory. As such, undo is
         * forcefully disabled when GtkText:visibility is set to FALSE.
         *
         * @param editable    self
         * @param enable_undo If undo/redo should be enabled.
         */
        public native void gtk_editable_set_enable_undo(Pointer editable, boolean enable_undo);

        /**
         * Sets the desired maximum width in characters of editable.
         *
         * @param editable self
         * @param n_chars  The new desired maximum width, in characters.
         */
        public native void gtk_editable_set_max_width_chars(Pointer editable, int n_chars);

        /**
         * Sets the cursor position in the editable to the given value.
         * <p>
         * The cursor is displayed before the character with the given (base 0) index in the contents of the editable.
         * The value must be less than or equal to the number of characters in the editable. A value of -1 indicates
         * that the position should be set after the last character of the editable. Note that position is in
         * characters, not in bytes.
         *
         * @param editable self
         * @param position The position of the cursor.
         */
        public native void gtk_editable_set_position(Pointer editable, int position);

        /**
         * Sets the text in the editable to the given value.
         * <p>
         * This replaces the current contents.
         *
         * @param editable self
         * @param text     The text to set.
         */
        public native void gtk_editable_set_text(Pointer editable, String text);

        /**
         * Changes the size request of the editable to be about the right size for n_chars characters.
         * <p>
         * Note that it changes the size request, the size can still be affected by how you pack the widget into
         * containers. If n_chars is -1, the size reverts to the default size.
         *
         * @param editable self
         * @param n_chars  Width in chars.
         */
        public native void gtk_editable_set_width_chars(Pointer editable, int n_chars);
    }
}
