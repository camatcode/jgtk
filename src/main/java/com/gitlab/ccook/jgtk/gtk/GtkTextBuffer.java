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

import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Stores text and attributes for display in a GtkTextView.
 * <p>
 * GtkTextBuffer can support undoing changes to the buffer content, see gtk_text_buffer_set_enable_undo()
 */
@SuppressWarnings({"unchecked", "GrazieInspection"})
public class GtkTextBuffer extends JGTKConnectableObject {
    private static final GtkTextBufferLibrary library = new GtkTextBufferLibrary();

    /**
     * Creates a new text buffer.
     *
     * @param t A tag table, or NULL to create a new one.
     *          <p>
     *          The argument can be NULL.
     */
    public GtkTextBuffer(GtkTextTagTable t) {
        this(library.gtk_text_buffer_new(t != null ? t.getCReference() : Pointer.NULL));
    }

    public GtkTextBuffer(Pointer buffer) {
        super(buffer);
    }

    /**
     * Creates a new text buffer.
     */
    public GtkTextBuffer() {
        this(library.gtk_text_buffer_new(Pointer.NULL));
    }

    /**
     * Creates a new text buffer and sets with starting text
     *
     * @param text starting text
     */
    public GtkTextBuffer(String text) {
        this(library.gtk_text_buffer_new(Pointer.NULL));
        setText(text);
    }

    /**
     * Deletes current contents of buffer, and inserts text instead. This is automatically marked as an irreversible
     * action in the undo stack. If you wish to mark this action as part of a larger undo operation, call
     * gtk_text_buffer_delete() and gtk_text_buffer_insert() directly instead.
     *
     * @param text UTF-8 text to set.
     */
    public void setText(String text) {
        if (text != null) {
            text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            library.gtk_text_buffer_set_text(getCReference(), text, text.length());
        }
    }

    /**
     * Adds the mark at iterator position.
     * <p>
     * The mark must not be added to another buffer, and if its name is not NULL then there must not be another mark in
     * the buffer with the same name.
     * <p>
     * Emits the GtkTextBuffer::mark-set signal as notification of the mark's initial placement.
     *
     * @param mark      The mark to add.
     * @param placeHere Location to place mark.
     */
    public void addMark(GtkTextMark mark, GtkTextIter placeHere) {
        if (mark != null && placeHere != null) {
            library.gtk_text_buffer_add_mark(getCReference(), mark.getCReference(), placeHere.getCReference());
        }
    }

    /**
     * Adds clipboard to the list of clipboards in which the selection contents of buffer are available.
     * <p>
     * In most cases, clipboard will be the GdkClipboard returned by gtk_widget_get_primary_clipboard() for a view of
     * buffer.
     * <p>
     *
     * @param clipboard A GdkClipboard
     */
    public void addSelectionClipboard(GdkClipboard clipboard) {
        if (clipboard != null) {
            library.gtk_text_buffer_add_selection_clipboard(getCReference(), clipboard.getCReference());
        }
    }

    /**
     * Performs the appropriate action as if the user hit the delete key with the cursor at the position specified by
     * iter.
     * <p>
     * In the normal case a single character will be deleted, but when combining accents are involved, more than one
     * character can be deleted, and when pre-composed character and accent combinations are involved, less than one
     * character will be deleted.
     * <p>
     * Because the buffer is modified, all outstanding iterators become invalid after calling this function; however,
     * the iter will be re-initialized to point to the location where text was deleted.
     *
     * @param iter            A position in buffer.
     * @param interactive     Whether the deletion is caused by user interaction.
     * @param defaultEditable Whether the buffer is editable by default.
     * @return TRUE if the buffer was modified.
     */
    public boolean applyBackspace(GtkTextIter iter, boolean interactive, boolean defaultEditable) {
        if (iter != null) {
            return library.gtk_text_buffer_backspace(getCReference(), iter.getCReference(), interactive, defaultEditable);
        }
        return false;
    }

    /**
     * Emits the "apply-tag" signal on buffer.
     * <p>
     * Calls gtk_text_tag_table_lookup() on the buffer's tag table to get a GtkTextTag, then calls
     * gtk_text_buffer_apply_tag().
     *
     * @param name  Name of a named GtkTextTag
     * @param start One bound of range to be tagged.
     * @param end   Other bound of range to be tagged.
     */
    public void applyTag(String name, GtkTextIter start, GtkTextIter end) {
        if (name != null && start != null && end != null) {
            library.gtk_text_buffer_apply_tag_by_name(getCReference(), name, start.getCReference(), end.getCReference());
        }
    }

    /**
     * Emits the "apply-tag" signal on buffer.
     * <p>
     * The default handler for the signal applies tag to the given range. start and end do not have to be in order.
     *
     * @param tag   A GtkTextTag
     * @param start One bound of range to be tagged.
     * @param end   Other bound of range to be tagged.
     */
    public void applyTag(GtkTextTag tag, GtkTextIter start, GtkTextIter end) {
        if (tag != null && start != null && end != null) {
            library.gtk_text_buffer_apply_tag(getCReference(), tag.getCReference(), start.getCReference(), end.getCReference());
        }
    }

    /**
     * Denotes the beginning of an action that may not be undone.
     * <p>
     * This will cause any previous operations in the undo/redo queue to be cleared.
     * <p>
     * This should be paired with a call to gtk_text_buffer_end_irreversible_action() after the irreversible action
     * has completed.
     * <p>
     * You may nest calls to gtk_text_buffer_begin_irreversible_action() and gtk_text_buffer_end_irreversible_action()
     * pairs.
     */
    public void beginIrreversibleAction() {
        library.gtk_text_buffer_begin_irreversible_action(getCReference());
    }

    /**
     * Called to indicate that the buffer operations between here and a call to gtk_text_buffer_end_user_action() are
     * part of a single user-visible operation.
     * <p>
     * The operations between gtk_text_buffer_begin_user_action() and gtk_text_buffer_end_user_action() can then be
     * grouped when creating an undo stack. GtkTextBuffer maintains a count of calls to
     * gtk_text_buffer_begin_user_action() that have not been closed with a call to gtk_text_buffer_end_user_action(),
     * and emits the "begin-user-action" and "end-user-action" signals only for the outermost pair of calls.
     * This allows you to build user actions from other user actions.
     * <p>
     * The "interactive" buffer mutation functions, such as gtk_text_buffer_insert_interactive(), automatically call
     * begin/end user action around the buffer operations they perform, so there's no need to add extra calls if you
     * user action consists solely of a single call to one of those functions.
     */
    public void beginUserAction() {
        library.gtk_text_buffer_begin_user_action(getCReference());
    }

    /**
     * Gets whether there is a redo-able action in the history.
     *
     * @return TRUE if there is a redo-able action.
     */
    public boolean canRedo() {
        return library.gtk_text_buffer_get_can_redo(getCReference());
    }

    /**
     * Gets whether there is an undoable action in the history.
     *
     * @return TRUE if there is an undoable action.
     */
    public boolean canUndo() {
        return library.gtk_text_buffer_get_can_undo(getCReference());
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
     * Copies the currently-selected text to a clipboard.
     *
     * @param clipboard The GdkClipboard object to copy to.
     */
    public void copyTextToClipboard(GdkClipboard clipboard) {
        if (clipboard != null) {
            library.gtk_text_buffer_copy_clipboard(getCReference(), clipboard.getCReference());
        }
    }

    /**
     * Creates and inserts a child anchor.
     * <p>
     * This is a convenience function which simply creates a child anchor with gtk_text_child_anchor_new() and inserts
     * it into the buffer with gtk_text_buffer_insert_child_anchor().
     * <p>
     * The new anchor is owned by the buffer; no reference count is returned to the caller of this function.
     *
     * @param iter Location in the buffer.
     * @return The created child anchor, if defined
     */
    public Option<GtkTextChildAnchor> createChildAnchor(GtkTextIter iter) {
        if (iter != null) {
            return new Option<>(new GtkTextChildAnchor(library.gtk_text_buffer_create_child_anchor(getCReference(), iter.getCReference())));
        }
        return Option.NONE;
    }

    /**
     * Creates a mark at position toPlace.
     * <p>
     * If mark_name is NULL, the mark is anonymous; otherwise, the mark can be retrieved by name using
     * gtk_text_buffer_get_mark(). If a mark has left gravity, and text is inserted at the mark's current location,
     * the mark will be moved to the left of the newly-inserted text. If the mark has right gravity
     * (left_gravity = FALSE), the mark will end up on the right of newly-inserted text.
     * The standard left-to-right cursor is a mark with right gravity (when you type, the cursor stays on the right
     * side of the text you're typing).
     * <p>
     * The caller of this function does not own a reference to the returned GtkTextMark, so you can ignore the return
     * value if you like. Marks are owned by the buffer and go away when the buffer does.
     * <p>
     * Emits the GtkTextBuffer::mark-set signal as notification of the mark's initial placement.
     *
     * @param markName       Name for mark.
     *                       <p>
     *                       The argument can be NULL.
     * @param toPlace        Location to place mark.
     * @param hasLeftGravity Whether the mark has left gravity.
     * @return The new GtkTextMark object.
     */
    public Option<GtkTextMark> createMark(String markName, GtkTextIter toPlace, boolean hasLeftGravity) {
        if (toPlace != null) {
            return new Option<>(new GtkTextMark(library.gtk_text_buffer_create_mark(getCReference(), markName, toPlace.getCReference(), hasLeftGravity)));
        }
        return Option.NONE;
    }

    /**
     * Creates a tag and adds it to the tag table for buffer.
     * <p>
     * Equivalent to calling gtk_text_tag_new() and then adding the tag to the buffer's tag table.
     * The returned tag is owned by the buffer's tag table, so the ref count will be equal to one.
     * <p>
     * If tag_name is NULL, the tag is anonymous.
     * <p>
     * If tag_name is non-NULL, a tag called tag_name must not already exist in the tag table for this buffer.
     * <p>
     * The first_property_name argument and subsequent arguments are a list of properties to set on the tag, as with
     * g_object_set().
     * <p>
     *
     * @param tagName    Name of the new tag.
     *                   <p>
     *                   The argument can be NULL.
     * @param properties list of property names and values.
     *                   <p>
     *                   The argument can be NULL.
     * @return A new tag.
     */
    public GtkTextTag createTag(String tagName, Map<String, String> properties) {
        List<String> propertiesList = new ArrayList<>();
        if (properties != null) {
            for (Map.Entry<String, String> ent : properties.entrySet()) {
                if (ent.getKey() != null && ent.getValue() != null) {
                    propertiesList.add(ent.getKey().trim());
                    propertiesList.add(ent.getValue());
                }
            }
        }
        String[] propertiesPrim = new String[propertiesList.size()];
        for (int i = 0; i < propertiesPrim.length; i++) {
            propertiesPrim[i] = propertiesList.get(i);
        }
        return new GtkTextTag(library.gtk_text_buffer_create_tag(getCReference(), tagName, propertiesPrim));
    }

    /**
     * Copies the currently-selected text to a clipboard, then deletes said text if it's editable.
     *
     * @param clipboard       The GdkClipboard object to cut to.
     * @param defaultEditable Default editability of the buffer.
     */
    public void cutTextToClipboard(GdkClipboard clipboard, boolean defaultEditable) {
        if (clipboard != null) {
            library.gtk_text_buffer_cut_clipboard(getCReference(), clipboard.getCReference(), defaultEditable);
        }
    }

    /**
     * Deletes all editable text in the given range.
     * <p>
     * Calls gtk_text_buffer_delete() for each editable sub-range of [start,end). start and end are revalidated to point
     * to the location of the last deleted range, or left untouched if no text was deleted.
     *
     * @param start           Start of range to delete.
     * @param end             End of range.
     * @param defaultEditable Whether the buffer is editable by default.
     * @return Whether some text was actually deleted.
     */
    public boolean deleteEditableText(GtkTextIter start, GtkTextIter end, boolean defaultEditable) {
        if (start != null && end != null) {
            return library.gtk_text_buffer_delete_interactive(getCReference(), start.getCReference(), end.getCReference(), defaultEditable);
        }
        return false;
    }

    /**
     * Deletes mark, so that it's no longer located anywhere in the buffer.
     * <p>
     * Removes the reference the buffer holds to the mark, so if you haven't called g_object_ref() on the mark, it will
     * be freed. Even if the mark isn't freed, most operations on mark become invalid, until it gets added to a buffer
     * again with gtk_text_buffer_add_mark(). Use gtk_text_mark_get_deleted() to find out if a mark has been removed
     * from its buffer.
     * <p>
     * The GtkTextBuffer::mark-deleted signal will be emitted as notification after the mark is deleted.
     *
     * @param mark A GtkTextMark in buffer.
     */
    public void deleteMark(GtkTextMark mark) {
        if (mark != null) {
            library.gtk_text_buffer_delete_mark(getCReference(), mark.getCReference());
        }
    }

    /**
     * Deletes the mark named markName;
     * <p>
     * See gtk_text_buffer_delete_mark() for details.
     *
     * @param markName Name of a mark in buffer.
     */
    public void deleteMark(String markName) {
        if (getMark(markName).isDefined()) {
            library.gtk_text_buffer_delete_mark_by_name(getCReference(), markName);
        }
    }

    /**
     * Returns the mark named name in buffer, or NONE if no such mark exists in the buffer.
     *
     * @param markName A mark name.
     * @return A GtkTextMark, if defined
     */
    public Option<GtkTextMark> getMark(String markName) {
        if (markName != null) {
            Option<Pointer> p = new Option<>(library.gtk_text_buffer_get_mark(getCReference(), markName));
            if (p.isDefined()) {
                return new Option<>(new GtkTextMark(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Deletes the range between the "insert" and "selection_bound" marks, that is, the currently-selected text.
     * <p>
     * If interactive is TRUE, the editability of the selection will be considered (users can't delete uneditable text).
     *
     * @param interactive     Whether the deletion is caused by user interaction.
     * @param defaultEditable Whether the buffer is editable by default.
     * @return Whether there was a non-empty selection to delete.
     */
    public boolean deleteSelection(boolean interactive, boolean defaultEditable) {
        return library.gtk_text_buffer_delete_selection(getCReference(), interactive, defaultEditable);
    }

    /**
     * Deletes text between start and end.
     * <p>
     * The order of start and end is not actually relevant; gtk_text_buffer_delete() will reorder them.
     * <p>
     * This function actually emits the "delete-range" signal, and the default handler of that signal deletes the text.
     * Because the buffer is modified, all outstanding iterators become invalid after calling this function; however,
     * the start and end will be re-initialized to point to the location where text was deleted.
     *
     * @param start A position in buffer.
     * @param end   Another position in buffer.
     */
    public void deleteText(GtkTextIter start, GtkTextIter end) {
        if (start != null && end != null) {
            library.gtk_text_buffer_delete(getCReference(), start.getCReference(), end.getCReference());
        }
    }

    /**
     * Sets whether to enable undoable actions in the text buffer.
     * <p>
     * Undoable actions in this context are changes to the text content of the buffer. Changes to tags and marks are
     * not tracked.
     * <p>
     * If enabled, the user will be able to undo the last number of actions up to gtk_text_buffer_get_max_undo_levels().
     * <p>
     * See gtk_text_buffer_begin_irreversible_action() and gtk_text_buffer_end_irreversible_action() to create changes
     * to the buffer that cannot be undone.
     *
     * @param isUndoEnabled TRUE to enable undo.
     */
    public void enableUndo(boolean isUndoEnabled) {
        library.gtk_text_buffer_set_enable_undo(getCReference(), isUndoEnabled);
    }

    /**
     * Denotes the end of an action that may not be undone.
     * <p>
     * This will cause any previous operations in the undo/redo queue to be cleared.
     * <p>
     * This should be called after completing modifications to the text buffer after
     * gtk_text_buffer_begin_irreversible_action() was called.
     * <p>
     * You may nest calls to gtk_text_buffer_begin_irreversible_action() and gtk_text_buffer_end_irreversible_action()
     * pairs.
     */
    public void endIrreversibleAction() {
        library.gtk_text_buffer_end_irreversible_action(getCReference());
    }

    /**
     * Ends a user-visible operation.
     * <p>
     * Should be paired with a call to gtk_text_buffer_begin_user_action(). See that function for a full explanation.
     */
    public void endUserAction() {
        library.gtk_text_buffer_end_user_action(getCReference());
    }

    /**
     * Gets the number of characters in the buffer.
     * <p>
     * Note that characters and bytes are not the same, you can't e.g. expect the contents of the buffer in string form
     * to be this many bytes long.
     * <p>
     * The character count is cached, so this function is very fast.
     *
     * @return Number of characters in the buffer.
     */
    public int getCharacterCount() {
        return library.gtk_text_buffer_get_char_count(getCReference());
    }

    /**
     * Initializes iter with the "end iterator," one past the last valid character in the text buffer.
     * <p>
     * If de-referenced with gtk_text_iter_get_char(), the end iterator has a character value of 0. The entire buffer
     * lies in the range from the first position in the buffer (call gtk_text_buffer_get_start_iter() to get character
     * position 0) to the end iterator.
     *
     * @return the "end iterator," one past the last valid character in the text buffer.
     */
    public GtkTextIter getEndIterator() {
        PointerByReference endIter = new PointerByReference();
        library.gtk_text_buffer_get_end_iter(getCReference(), endIter);
        return new GtkTextIter(endIter.getPointer());
    }

    /**
     * Returns the mark that represents the cursor (insertion point).
     * <p>
     * Equivalent to calling gtk_text_buffer_get_mark() to get the mark named "insert", but very slightly more
     * efficient, and involves less typing.
     *
     * @return Insertion point mark.
     */
    public GtkTextMark getInsertionPoint() {
        return new GtkTextMark(library.gtk_text_buffer_get_insert(getCReference()));
    }

    /**
     * Obtains an iterator pointing to byte_index within the given line.
     * <p>
     * byte_index must be the start of a UTF-8 character. Note bytes, not characters; UTF-8 may encode one character as
     * multiple bytes.
     * <p>
     * If line_number is greater than or equal to the number of lines in the buffer, the end iterator is returned. And
     * if byte_index is off the end of the line, the iterator at the end of the line is returned.
     *
     * @param lineNumber Line number counting from 0
     * @param byteIndex  Byte index from start of line.
     * @return first - Whether the exact position has been found. second - iterator at byte index within a line
     */
    public Pair<Boolean, GtkTextIter> getIteratorAtByteIndex(int lineNumber, int byteIndex) {
        PointerByReference iter = new PointerByReference();
        boolean exactPositionFound = library.gtk_text_buffer_get_iter_at_line_index(getCReference(), iter, Math.max(0, lineNumber), Math.max(0, byteIndex));
        return new Pair<>(exactPositionFound, new GtkTextIter(iter.getPointer()));
    }

    /**
     * Obtains an iterator pointing to char_offset within the given line.
     * <p>
     * Note characters, not bytes; UTF-8 may encode one character as multiple bytes.
     * <p>
     * If line_number is greater than or equal to the number of lines in the buffer, the end iterator is returned. And
     * if char_offset is off the end of the line, the iterator at the end of the line is returned.
     *
     * @param lineNumber                Line number counting from 0
     * @param characterOffsetWithinLine Char offset from start of line.
     * @return first - Whether the exact position has been found. second -iterator at character offset at line number
     */
    public Pair<Boolean, GtkTextIter> getIteratorAtCharacterOffset(int lineNumber, int characterOffsetWithinLine) {
        PointerByReference iter = new PointerByReference();
        boolean exactPositionFound = library.gtk_text_buffer_get_iter_at_line_offset(getCReference(), iter, Math.max(0, lineNumber), Math.max(0, characterOffsetWithinLine));
        return new Pair<>(exactPositionFound, new GtkTextIter(iter.getPointer()));
    }

    /**
     * Returns iter to a position char_offset chars from the start of the entire buffer.
     * <p>
     * If char_offset is -1 or greater than the number of characters in the buffer, iter is initialized to the end
     * iterator, the iterator one past the last valid character in the buffer.
     *
     * @param characterOffset Char offset from start of buffer, counting from 0, or -1
     * @return iter to a position char_offset chars from the start of the entire buffer.
     */
    public GtkTextIter getIteratorAtCharacterOffset(int characterOffset) {
        PointerByReference iter = new PointerByReference();
        library.gtk_text_buffer_get_iter_at_offset(getCReference(), iter, Math.max(-1, characterOffset));
        return new GtkTextIter(iter.getPointer());
    }

    /**
     * Obtains the location of anchor within buffer.
     *
     * @param anchor A child anchor that appears in buffer.
     * @return An iterator indicating the location of anchor within buffer.
     */
    public Option<GtkTextIter> getIteratorAtChildAnchor(GtkTextChildAnchor anchor) {
        if (anchor != null) {
            PointerByReference iter = new PointerByReference();
            library.gtk_text_buffer_get_iter_at_child_anchor(getCReference(), iter, anchor.getCReference());
            return new Option<>(new GtkTextIter(iter.getPointer()));
        }
        return Option.NONE;
    }

    /**
     * Initializes iter to the start of the given line.
     * <p>
     * If line_number is greater than or equal to the number of lines in the buffer, the end iterator is returned.
     *
     * @param lineNumber Line number counting from 0 (negative values clamped)
     * @return first - Whether the exact position has been found. second - iter to the start of the given line
     */
    public Pair<Boolean, GtkTextIter> getIteratorAtLine(int lineNumber) {
        PointerByReference iter = new PointerByReference();
        boolean exactPositionFound = library.gtk_text_buffer_get_iter_at_line(getCReference(), iter, Math.max(0, lineNumber));
        return new Pair<>(exactPositionFound, new GtkTextIter(iter.getPointer()));
    }

    /**
     * Retrieves iter with the current position of mark.
     *
     * @param m A GtkTextMark in buffer.
     * @return iter with the current position of mark.
     */
    public Option<GtkTextIter> getIteratorAtMark(GtkTextMark m) {
        if (m != null) {
            PointerByReference iter = new PointerByReference();
            library.gtk_text_buffer_get_iter_at_mark(getCReference(), iter, m.getCReference());
            return new Option<>(new GtkTextIter(iter.getPointer()));
        }
        return Option.NONE;
    }

    /**
     * Obtains the number of lines in the buffer.
     * <p>
     * This value is cached, so the function is very fast.
     *
     * @return Number of lines in the buffer.
     */
    public int getLineCount() {
        return library.gtk_text_buffer_get_line_count(getCReference());
    }

    /**
     * Gets the maximum number of undo levels to perform.
     * <p>
     * If 0, unlimited undo actions may be performed. Note that this may have a memory usage impact as it requires
     * storing an additional copy of the inserted or removed text within the text buffer.
     *
     * @return The max number of undo levels allowed, or 0 indicate unlimited
     */
    public Option<Integer> getMaximumUndoLevels() {
        int maxUndo = library.gtk_text_buffer_get_max_undo_levels(getCReference());
        if (maxUndo > 0) {
            return new Option<>(maxUndo);
        }
        return Option.NONE;
    }

    /**
     * Sets the maximum number of undo levels to perform.
     * <p>
     * If NONE, unlimited undo actions may be performed. Note that this may have a memory usage impact as it requires
     * storing an additional copy of the inserted or removed text within the text buffer.
     *
     * @param maxUndoLevels The maximum number of undo actions to perform, NONE for unlimited
     */
    public void setMaximumUndoLevels(Option<Integer> maxUndoLevels) {
        if (maxUndoLevels.isDefined()) {
            library.gtk_text_buffer_set_max_undo_levels(getCReference(), Math.max(0, maxUndoLevels.get()));
        } else {
            library.gtk_text_buffer_set_max_undo_levels(getCReference(), 0);
        }
    }

    /**
     * Sets the maximum number of undo levels to perform.
     * <p>
     * If 0, unlimited undo actions may be performed. Note that this may have a memory usage impact as it requires
     * storing an additional copy of the inserted or removed text within the text buffer.
     *
     * @param maximumUndoLevels The maximum number of undo actions to perform, 0 for unlimited
     */
    public void setMaximumUndoLevels(int maximumUndoLevels) {
        setMaximumUndoLevels(new Option<>(maximumUndoLevels));
    }

    /**
     * Get a content provider for this buffer.
     * <p>
     * It can be used to make the content of buffer available in a GdkClipboard, see gdk_clipboard_set_content().
     *
     * @param includeHiddenCharacters Whether to include invisible text.
     * @return content of text selected
     */
    public Option<String> getSelectedText(boolean includeHiddenCharacters) {
        Pair<GtkTextIter, GtkTextIter> selection = getSelectionBounds();
        return getText(selection.getFirst(), selection.getSecond(), includeHiddenCharacters);
    }

    /**
     * If the selection has length 0, then start and end are filled in with the same value. start and end will be in
     * ascending order.
     *
     * @return first - start of selection, second - end of selection
     */
    public Pair<GtkTextIter, GtkTextIter> getSelectionBounds() {
        PointerByReference start = new PointerByReference();
        PointerByReference end = new PointerByReference();
        boolean selectionNonZero = library.gtk_text_buffer_get_selection_bounds(getCReference(), start, end);
        return new Pair<>(new GtkTextIter(start.getPointer()), new GtkTextIter(end.getPointer()));
    }

    /**
     * Returns the text in the range [start,end).
     * <p>
     * Excludes undisplayed text (text marked with tags that set the invisibility attribute) if include_hidden_chars is
     * FALSE. Does not include characters representing embedded images, so byte and character indexes into the returned
     * string do not correspond to byte and character indexes into the buffer.
     * <p>
     * Contrast with gtk_text_buffer_get_slice().
     *
     * @param start                   Start of a range.
     * @param end                     End of a range.
     * @param includeHiddenCharacters Whether to include invisible text.
     * @return An allocated UTF-8 string.
     */
    public Option<String> getText(GtkTextIter start, GtkTextIter end, boolean includeHiddenCharacters) {
        if (start != null && end != null) {
            return new Option<>(library.gtk_text_buffer_get_text(getCReference(), start.getCReference(), end.getCReference(), includeHiddenCharacters));
        }
        return Option.NONE;
    }

    /**
     * Returns the mark that represents the selection bound.
     * <p>
     * Equivalent to calling gtk_text_buffer_get_mark() to get the mark named "selection_bound", but very slightly more
     * efficient, and involves less typing.
     * <p>
     * The currently-selected text in buffer is the region between the "selection_bound" and "insert" marks. If
     * "selection_bound" and "insert" are in the same place, then there is no current selection.
     * gtk_text_buffer_get_selection_bounds() is another convenient function for handling the selection, if you just
     * want to know whether there's a selection and what its bounds are.
     *
     * @return Selection bound mark.
     */
    public GtkTextMark getSelectionBoundsAsMark() {
        return new GtkTextMark(library.gtk_text_buffer_get_selection_bound(getCReference()));
    }

    /**
     * Returns the text in the range [start,end).
     * <p>
     * Excludes undisplayed text (text marked with tags that set the invisibility attribute) if include_hidden_chars is
     * FALSE. The returned string includes a 0xFFFC character whenever the buffer contains embedded images, so byte and
     * character indexes into the returned string do correspond to byte and character indexes into the buffer.
     * Contrast with gtk_text_buffer_get_text(). Note that 0xFFFC can occur in normal text as well, so it is not a
     * reliable indicator that a paintable or widget is in the buffer.
     *
     * @param start                   Start of a range.
     * @param end                     End of a range.
     * @param includeHiddenCharacters Whether to include invisible text.
     * @return An allocated UTF-8 string.
     */
    public Option<String> getSlice(GtkTextIter start, GtkTextIter end, boolean includeHiddenCharacters) {
        if (start != null && end != null) {
            return new Option<>(library.gtk_text_buffer_get_slice(getCReference(), start.getCReference(), end.getCReference(), includeHiddenCharacters));
        }
        return Option.NONE;
    }

    /**
     * Initialized iter with the first position in the text buffer.
     * <p>
     * This is the same as using gtk_text_buffer_get_iter_at_offset() to get the iter at character offset 0.
     *
     * @return iter with the first position in the text buffer.
     */
    public GtkTextIter getStartIterator() {
        PointerByReference start = new PointerByReference();
        library.gtk_text_buffer_get_start_iter(getCReference(), start);
        return new GtkTextIter(start.getPointer());
    }

    /**
     * Get the GtkTextTagTable associated with this buffer.
     *
     * @return The buffer's tag table.
     */
    public GtkTextTagTable getTagTable() {
        return new GtkTextTagTable(library.gtk_text_buffer_get_tag_table(getCReference()));
    }

    /**
     * Retrieves the first and last iterators in the buffer, i.e. the entire buffer lies within the range [start,end).
     *
     * @return first - start iterator, second - end iterator
     */
    public Pair<GtkTextIter, GtkTextIter> getTextBounds() {
        PointerByReference startIter = new PointerByReference();
        PointerByReference endIter = new PointerByReference();
        library.gtk_text_buffer_get_bounds(getCReference(), startIter, endIter);
        return new Pair<>(new GtkTextIter(startIter.getPointer()), new GtkTextIter(endIter.getPointer()));
    }

    /**
     * Indicates whether the buffer has been modified since the last call to gtk_text_buffer_set_modified() set the
     * modification flag to FALSE.
     * <p>
     * Used for example to enable a "save" function in a text editor.
     *
     * @return TRUE if the buffer has been modified since setModified = false called
     */
    public boolean hasBeenModified() {
        return library.gtk_text_buffer_get_modified(getCReference());
    }

    /**
     * Indicates whether the buffer has some text currently selected.
     *
     * @return TRUE if the there is text selected.
     */
    public boolean hasSelection() {
        return library.gtk_text_buffer_get_has_selection(getCReference());
    }

    /**
     * Inserts a child widget anchor into the text buffer at iter.
     * <p>
     * The anchor will be counted as one character in character counts, and when obtaining the buffer contents as a
     * string, will be represented by the Unicode "object replacement character" 0xFFFC. Note that the "slice" variants
     * for obtaining portions of the buffer as a string include this character for child anchors, but the "text"
     * variants do not. E.g. see gtk_text_buffer_get_slice() and gtk_text_buffer_get_text().
     * <p>
     * Consider gtk_text_buffer_create_child_anchor() as a more convenient alternative to this function.
     * The buffer will add a reference to the anchor, so you can un-reference it after insertion.
     *
     * @param iter   Location to insert the anchor.
     * @param anchor A GtkTextChildAnchor to insert
     */
    public void insertChildAnchor(GtkTextIter iter, GtkTextChildAnchor anchor) {
        if (iter != null && anchor != null) {
            library.gtk_text_buffer_insert_child_anchor(getCReference(), iter.getCReference(), anchor.getCReference());
        }
    }

    /**
     * Inserts the text in markup at position iter.
     * <p>
     * markup will be inserted in its entirety and must be nul-terminated and valid UTF-8.
     * Emits the GtkTextBuffer::insert-text signal, possibly multiple times; insertion actually occurs in the default
     * handler for the signal. iter will point to the end of the inserted text on return.
     *
     * @param iter   Location to insert the markup.
     * @param markup A null-terminated UTF-8 string containing Pango markup.
     */
    public void insertMarkup(GtkTextIter iter, String markup) {
        if (iter != null && markup != null) {
            markup = new String(markup.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            library.gtk_text_buffer_insert_markup(getCReference(), iter.getCReference(), markup, markup.length());
        }
    }

    /**
     * Inserts an image into the text buffer at iter.
     * <p>
     * The image will be counted as one character in character counts, and when obtaining the buffer contents as a
     * string, will be represented by the Unicode "object replacement character" 0xFFFC. Note that the "slice"
     * variants for obtaining portions of the buffer as a string include this character for paintable, but the "text"
     * variants do not. e.g. see gtk_text_buffer_get_slice() and gtk_text_buffer_get_text().
     *
     * @param iter      Location to insert the paintable.
     * @param paintable A GdkPaintable
     */
    public void insertPaintable(GtkTextIter iter, GdkPaintable paintable) {
        if (iter != null && paintable != null) {
            library.gtk_text_buffer_insert_paintable(getCReference(), iter.getCReference(), paintable.getCReference());
        }
    }

    /**
     * Copies text, tags, and paintables between start and end and inserts the copy at iter.
     * <p>
     * The order of start and end doesn't matter.
     * <p>
     * Used instead of simply getting/inserting text because it preserves images and tags. If start and end are in a
     * different buffer from buffer, the two buffers must share the same tag table.
     * <p>
     * Implemented via emissions of the ::insert-text and ::apply-tag signals, so expect those.
     *
     * @param iter  A position in buffer.
     * @param start A position in a GtkTextBuffer
     * @param end   Another position in start's buffer
     */
    public void insertRange(GtkTextIter iter, GtkTextIter start, GtkTextIter end) {
        if (iter != null && start != null && end != null) {
            library.gtk_text_buffer_insert_range(getCReference(), iter.getCReference(), start.getCReference(), end.getCReference());
        }
    }

    /**
     * Copies text, tags, and paintables between start and end and inserts the copy at iter.
     * <p>
     * Same as gtk_text_buffer_insert_range(), but does nothing if the insertion point isn't editable. The
     * default_editable parameter indicates whether the text is editable at iter if no tags enclosing iter affect
     * editability. Typically, the result of gtk_text_view_get_editable() is appropriate here.
     *
     * @param iter            A position in buffer.
     * @param start           A position in a GtkTextBuffer
     * @param end             Another position in start's buffer
     * @param defaultEditable Default editability of the buffer.
     * @return Whether text was actually inserted.
     */
    public boolean insertRange(GtkTextIter iter, GtkTextIter start, GtkTextIter end, boolean defaultEditable) {
        if (iter != null && start != null && end != null) {
            return library.gtk_text_buffer_insert_range_interactive(getCReference(), iter.getCReference(), start.getCReference(), end.getCReference(), defaultEditable);
        }
        return false;
    }

    /**
     * Inserts text into buffer at iter, applying the list of tags to the newly-inserted text.
     * <p>
     * The last tag specified must be NULL to terminate the list. Equivalent to calling gtk_text_buffer_insert(),
     * then gtk_text_buffer_apply_tag() on the inserted text; this is just a convenience function.
     *
     * @param iter        An iterator in buffer.
     * @param text        UTF-8 text.
     * @param tagsToApply Tags to apply to text.
     */
    public void insertText(GtkTextIter iter, String text, GtkTextTag... tagsToApply) {
        if (text != null && iter != null) {
            text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            Pointer[] pointers = new Pointer[tagsToApply.length];
            for (int i = 0; i < pointers.length; i++) {
                pointers[i] = tagsToApply[i].getCReference();
            }
            library.gtk_text_buffer_insert_with_tags(getCReference(), iter.getCReference(), text, text.length(), pointers);
        }
    }

    /**
     * Inserts text into buffer at iter, applying the list of tags to the newly-inserted text.
     * <p>
     * Same as gtk_text_buffer_insert_with_tags(), but allows you to pass in tag names instead of tag objects.
     *
     * @param iter            Position in buffer.
     * @param text            UTF-8 text.
     * @param tagNamesToApply Names of tags to apply to text.
     */
    public void insertText(GtkTextIter iter, String text, String... tagNamesToApply) {
        if (text != null && iter != null) {
            text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            library.gtk_text_buffer_insert_with_tags_by_name(getCReference(), iter.getCReference(), text, text.length(), tagNamesToApply);
        }
    }

    /**
     * Inserts text at position iter.
     * <p>
     * Emits the "insert-text" signal; insertion actually occurs in the default handler for the signal. iter is
     * invalidated when insertion occurs (because the buffer contents change), but the default signal handler
     * revalidates it to point to the end of the inserted text.
     *
     * @param text Text in UTF-8 format.
     * @param iter A position in the buffer.
     */
    public void insertText(String text, GtkTextIter iter) {
        if (text != null && iter != null) {
            text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            library.gtk_text_buffer_insert(getCReference(), iter.getCReference(), text, text.length());
        }
    }

    /**
     * Inserts text in buffer.
     * <p>
     * Like gtk_text_buffer_insert(), but the insertion will not occur if iter is at a non-editable location in the
     * buffer. Usually you want to prevent insertions at uneditable locations if the insertion results from a user
     * action (is interactive).
     * <p>
     * default_editable indicates the editability of text that doesn't have a tag affecting editability applied to it.
     * Typically, the result of gtk_text_view_get_editable() is appropriate here.
     *
     * @param iter            A position in buffer.
     * @param text            Some UTF-8 text.
     * @param defaultEditable Default editability of buffer.
     * @return Whether text was actually inserted.
     */
    public boolean insertText(GtkTextIter iter, String text, boolean defaultEditable) {
        if (iter != null && text != null) {
            text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            return library.gtk_text_buffer_insert_interactive(getCReference(), iter.getCReference(), text, text.length(), defaultEditable);
        }
        return false;
    }

    /**
     * Inserts text in buffer.
     * <p>
     * Simply calls gtk_text_buffer_insert(), using the current cursor position as the insertion point.
     *
     * @param text Text in UTF-8 format.
     */
    public void insertTextAtCursor(String text) {
        if (text != null) {
            text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            library.gtk_text_buffer_insert_at_cursor(getCReference(), text, text.length());
        }
    }

    /**
     * Inserts text in buffer.
     * <p>
     * Calls gtk_text_buffer_insert_interactive() at the cursor position.
     * <p>
     * default_editable indicates the editability of text that doesn't have a tag affecting editability applied to it.
     * Typically, the result of gtk_text_view_get_editable() is appropriate here.
     *
     * @param text            Text in UTF-8 format.
     * @param defaultEditable Default editability of buffer.
     * @return Whether text was actually inserted.
     */
    public boolean insertTextAtCursor(String text, boolean defaultEditable) {
        if (text != null) {
            text = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            return library.gtk_text_buffer_insert_interactive_at_cursor(getCReference(), text, text.length(), defaultEditable);
        }
        return false;
    }

    /**
     * Gets whether the buffer is saving modifications to the buffer to allow for undo and redo actions.
     * <p>
     * See gtk_text_buffer_begin_irreversible_action() and gtk_text_buffer_end_irreversible_action() to create changes
     * to the buffer that cannot be undone.
     *
     * @return TRUE if support for undoing and redoing changes to the buffer is allowed.
     */
    public boolean isUndoEnabled() {
        return library.gtk_text_buffer_get_enable_undo(getCReference());
    }

    /**
     * This function moves the "insert" and "selection_bound" marks simultaneously.
     * <p>
     * If you move them to the same place in two steps with gtk_text_buffer_move_mark(), you will temporarily select a
     * region in between their old and new locations, which can be pretty inefficient since the temporarily-selected
     * region will force stuff to be recalculated. This function moves them as a unit, which can be optimized.
     *
     * @param where Where to put the cursor.
     */
    public void moveCursor(GtkTextIter where) {
        if (where != null) {
            library.gtk_text_buffer_place_cursor(getCReference(), where.getCReference());
        }
    }

    /**
     * Moves mark to the new location where.
     * <p>
     * Emits the GtkTextBuffer::mark-set signal as notification of the move.
     *
     * @param mark  A GtkTextMark
     * @param where New location for mark in buffer.
     */
    public void moveMark(GtkTextMark mark, GtkTextIter where) {
        if (mark != null && where != null) {
            library.gtk_text_buffer_move_mark(getCReference(), mark.getCReference(), where.getCReference());
        }
    }

    /**
     * Moves the mark named name (which must exist) to location where.
     * <p>
     * See gtk_text_buffer_move_mark() for details.
     *
     * @param name  Name of a mark.
     * @param where New location for mark.
     */
    public void moveMark(String name, GtkTextIter where) {
        if (name != null && where != null) {
            library.gtk_text_buffer_move_mark_by_name(getCReference(), name, where.getCReference());
        }
    }

    /**
     * Pastes the contents of a clipboard.
     * <p>
     * the pasted text will be inserted at the cursor position, or the buffer selection
     * will be replaced if the selection is non-empty.
     * <p>
     * Note: pasting is asynchronous, that is, we'll ask for the paste data and return, and at some point later after
     * the main loop runs, the paste data will be inserted.
     *
     * @param clipboard       The GdkClipboard to paste from.
     * @param defaultEditable Whether the buffer is editable by default.
     */
    public void pasteClipboard(GdkClipboard clipboard, boolean defaultEditable) {
        pasteClipboard(clipboard, null, defaultEditable);
    }

    /**
     * Pastes the contents of a clipboard.
     * <p>
     * If override_location is NULL, the pasted text will be inserted at the cursor position, or the buffer selection
     * will be replaced if the selection is non-empty.
     * <p>
     * Note: pasting is asynchronous, that is, we'll ask for the paste data and return, and at some point later after
     * the main loop runs, the paste data will be inserted.
     *
     * @param clipboard       The GdkClipboard to paste from.
     * @param location        Location to insert pasted text.
     *                        <p>
     *                        The argument can be NULL.
     * @param defaultEditable Whether the buffer is editable by default.
     */
    public void pasteClipboard(GdkClipboard clipboard, GtkTextIter location, boolean defaultEditable) {
        if (clipboard != null) {
            library.gtk_text_buffer_paste_clipboard(getCReference(), clipboard.getCReference(), pointerOrNull(location), defaultEditable);
        }
    }

    /**
     * Redoes the next re-doable action on the buffer, if there is one.
     */
    public void redo() {
        library.gtk_text_buffer_redo(getCReference());
    }

    /**
     * Removes all tags in the range between start and end.
     * <p>
     * Be careful with this function; it could remove tags added in code unrelated to the code you're currently writing.
     * That is, using this function is probably a bad idea if you have two or more unrelated code sections that add
     * tags.
     *
     * @param start One bound of range to be untagged.
     * @param end   Other bound of range to be untagged.
     */
    public void removeAllTags(GtkTextIter start, GtkTextIter end) {
        if (start != null && end != null) {
            library.gtk_text_buffer_remove_all_tags(getCReference(), start.getCReference(), end.getCReference());
        }
    }

    /**
     * Removes a GdkClipboard added with gtk_text_buffer_add_selection_clipboard().
     *
     * @param clipboard A GdkClipboard added to buffer by gtk_text_buffer_add_selection_clipboard()
     */
    public void removeSelectionClipboard(GdkClipboard clipboard) {
        if (clipboard != null) {
            library.gtk_text_buffer_remove_selection_clipboard(getCReference(), clipboard.getCReference());
        }
    }

    /**
     * Emits the "remove-tag" signal.
     * <p>
     * The default handler for the signal removes all occurrences of tag from the given range. start and end don't have
     * to be in order.
     *
     * @param tag   A GtkTextTag
     * @param start One bound of range to be untagged.
     * @param end   Other bound of range to be untagged.
     */
    public void removeTag(GtkTextTag tag, GtkTextIter start, GtkTextIter end) {
        if (tag != null && start != null && end != null) {
            library.gtk_text_buffer_remove_tag(getCReference(), tag.getCReference(), start.getCReference(), end.getCReference());
        }
    }

    /**
     * Emits the "remove-tag" signal.
     * <p>
     * Calls gtk_text_tag_table_lookup() on the buffer's tag table to get a GtkTextTag, then calls
     * gtk_text_buffer_remove_tag().
     *
     * @param tagName Name of a GtkTextTag
     * @param start   One bound of range to be untagged.
     * @param end     Other bound of range to be untagged.
     */
    public void removeTag(String tagName, GtkTextIter start, GtkTextIter end) {
        if (tagName != null && start != null && end != null) {
            library.gtk_text_buffer_remove_tag_by_name(getCReference(), tagName, start.getCReference(), end.getCReference());
        }
    }

    /**
     * [src]
     * This function moves the "insert" and "selection_bound" marks simultaneously.
     * <p>
     * If you move them in two steps with gtk_text_buffer_move_mark(), you will temporarily select a region in between
     * their old and new locations, which can be pretty inefficient since the temporarily-selected region will force
     * stuff to be recalculated. This function moves them as a unit, which can be optimized.
     *
     * @param insertMark Where to put the "insert" mark.
     * @param boundMark  Where to put the "selection_bound" mark.
     */
    public void selectRange(GtkTextIter insertMark, GtkTextIter boundMark) {
        if (insertMark != null && boundMark != null) {
            library.gtk_text_buffer_select_range(getCReference(), insertMark.getCReference(), boundMark.getCReference());
        }
    }

    /**
     * Used to keep track of whether the buffer has been modified since the last time it was saved.
     * <p>
     * Whenever the buffer is saved to disk, call gtk_text_buffer_set_modified (buffer, FALSE). When the buffer is
     * modified, it will automatically toggle on the modified bit again. When the modified bit flips, the buffer emits
     * the GtkTextBuffer::modified-changed signal.
     *
     * @param isModified Modification flag setting.
     */
    public void setModified(boolean isModified) {
        library.gtk_text_buffer_set_modified(getCReference(), isModified);
    }

    /**
     * Undoes the last undoable action on the buffer, if there is one.
     */
    public void undo() {
        library.gtk_text_buffer_undo(getCReference());
    }

    public static class Signals extends GtkWidget.Signals {
        /**
         * Emitted to apply a tag to a range of text in a GtkTextBuffer.
         */
        public static final Signals APPLY_TAG = new Signals("apply-tag");

        /**
         * Emitted at the beginning of a single user-visible operation on a GtkTextBuffer.
         */
        public static final Signals BEGIN_USER_ACTION = new Signals("begin-user-action");

        /**
         * Emitted when the content of a GtkTextBuffer has changed.
         */
        public static final Signals CHANGED = new Signals("changed");

        /**
         * Emitted to delete a range from a GtkTextBuffer.
         */
        public static final Signals DELETE_RANGE = new Signals("delete-range");

        /**
         * Emitted at the end of a single user-visible operation on the GtkTextBuffer.
         */
        public static final Signals END_USER_ACTION = new Signals("end-user-action");

        /**
         * Emitted to insert a GtkTextChildAnchor in a GtkTextBuffer.
         */
        public static final Signals INSERT_CHILD_ANCHOR = new Signals("insert-child-anchor");

        /**
         * Emitted to insert a GdkPaintable in a GtkTextBuffer.
         */
        public static final Signals INSERT_PAINTABLE = new Signals("insert-paintable");

        /**
         * Emitted to insert text in a GtkTextBuffer.
         */
        public static final Signals INSERT_TEXT = new Signals("insert-text");

        /**
         * Emitted as notification after a GtkTextMark is deleted.
         */
        public static final Signals MARK_DELETED = new Signals("mark-deleted");

        /**
         * Emitted as notification after a GtkTextMark is set.
         */
        public static final Signals MARK_SET = new Signals("mark-set");

        /**
         * Emitted when the modified bit of a GtkTextBuffer flips.
         */
        public static final Signals MODIFIED_CHANGED = new Signals("modified-changed");

        /**
         * Emitted after paste operation has been completed.
         */
        public static final Signals PASTE_DONE = new Signals("paste-done");

        /**
         * Emitted when a request has been made to redo the previously undone operation.
         */
        public static final Signals REDO = new Signals("redo");

        /**
         * Emitted to remove all occurrences of tag from a range of text in a GtkTextBuffer.
         */
        public static final Signals REMOVE_TAG = new Signals("remove-tag");

        /**
         * Emitted when a request has been made to undo the previous operation or set of operations that have been
         * grouped together.
         */
        public static final Signals UNDO = new Signals("undo");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkTextBufferLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds the mark at position where.
         * <p>
         * The mark must not be added to another buffer, and if its name is not NULL then there must not be another mark
         * in the buffer with the same name.
         * <p>
         * Emits the GtkTextBuffer::mark-set signal as notification of the mark's initial placement.
         *
         * @param buffer self
         * @param mark   The mark to add. Type: GtkTextMark
         * @param where  Location to place mark. Type: GtkTextIter
         */
        public native void gtk_text_buffer_add_mark(Pointer buffer, Pointer mark, Pointer where);

        /**
         * Adds clipboard to the list of clipboards in which the selection contents of buffer are available.
         * <p>
         * In most cases, clipboard will be the GdkClipboard returned by gtk_widget_get_primary_clipboard() for a view
         * of buffer.
         *
         * @param buffer    self
         * @param clipboard A GdkClipboard
         */
        public native void gtk_text_buffer_add_selection_clipboard(Pointer buffer, Pointer clipboard);

        /**
         * Emits the "apply-tag" signal on buffer.
         * <p>
         * The default handler for the signal applies tag to the given range. start and end do not have to be in order.
         *
         * @param buffer self
         * @param tag    A GtkTextTag
         * @param start  One bound of range to be tagged. Type: GtkTextIter
         * @param end    Other bound of range to be tagged. Type: GtkTextIter
         */
        public native void gtk_text_buffer_apply_tag(Pointer buffer, Pointer tag, Pointer start, Pointer end);

        /**
         * Emits the "apply-tag" signal on buffer.
         * <p>
         * Calls gtk_text_tag_table_lookup() on the buffer's tag table to get a GtkTextTag, then calls
         * gtk_text_buffer_apply_tag().
         *
         * @param buffer self
         * @param name   Name of a named GtkTextTag
         * @param start  One bound of range to be tagged. Type: GtkTextIter
         * @param end    Other bound of range to be tagged. Type: GtkTextIter
         */
        public native void gtk_text_buffer_apply_tag_by_name(Pointer buffer, String name, Pointer start, Pointer end);

        /**
         * Performs the appropriate action as if the user hit the delete key with the cursor at the position specified
         * by iter.
         * <p>
         * In the normal case a single character will be deleted, but when combining accents are involved, more than
         * one character can be deleted, and when pre-composed character and accent combinations are involved, less than
         * one character will be deleted.
         * <p>
         * Because the buffer is modified, all outstanding iterators become invalid after calling this function;
         * however, the iter will be re-initialized to point to the location where text was deleted.
         *
         * @param buffer           self
         * @param iter             A position in buffer. Type: GtkTextIter
         * @param interactive      Whether the deletion is caused by user interaction.
         * @param default_editable Whether the buffer is editable by default.
         * @return TRUE if the buffer was modified.
         */
        public native boolean gtk_text_buffer_backspace(Pointer buffer, Pointer iter, boolean interactive, boolean default_editable);

        /**
         * Denotes the beginning of an action that may not be undone.
         * <p>
         * This will cause any previous operations in the undo/redo queue to be cleared.
         * <p>
         * This should be paired with a call to gtk_text_buffer_end_irreversible_action() after the irreversible action
         * has completed.
         * <p>
         * You may nest calls to gtk_text_buffer_begin_irreversible_action() and
         * gtk_text_buffer_end_irreversible_action() pairs.
         *
         * @param buffer self
         */
        public native void gtk_text_buffer_begin_irreversible_action(Pointer buffer);

        /**
         * Called to indicate that the buffer operations between here and a call to gtk_text_buffer_end_user_action()
         * are part of a single user-visible operation.
         * <p>
         * The operations between gtk_text_buffer_begin_user_action() and gtk_text_buffer_end_user_action() can then be
         * grouped when creating an undo stack. GtkTextBuffer maintains a count of calls to
         * gtk_text_buffer_begin_user_action() that have not been closed with a call to
         * gtk_text_buffer_end_user_action(), and emits the "begin-user-action" and "end-user-action" signals only for
         * the outermost pair of calls. This allows you to build user actions from other user actions.
         * <p>
         * The "interactive" buffer mutation functions, such as gtk_text_buffer_insert_interactive(), automatically call
         * begin/end user action around the buffer operations they perform, so there's no need to add extra calls if you
         * user action consists solely of a single call to one of those functions.
         *
         * @param buffer self
         */
        public native void gtk_text_buffer_begin_user_action(Pointer buffer);

        /**
         * Copies the currently-selected text to a clipboard.
         *
         * @param buffer    self
         * @param clipboard The GdkClipboard object to copy to.
         */
        public native void gtk_text_buffer_copy_clipboard(Pointer buffer, Pointer clipboard);

        /**
         * Creates and inserts a child anchor.
         * <p>
         * This is a convenience function which simply creates a child anchor with gtk_text_child_anchor_new() and
         * inserts it into the buffer with gtk_text_buffer_insert_child_anchor().
         * <p>
         * The new anchor is owned by the buffer; no reference count is returned to the caller of this function.
         *
         * @param buffer self
         * @param iter   Location in the buffer. Type: GtkTextIter
         * @return The created child anchor. Type: GtkTextChildAnchor
         */
        public native Pointer gtk_text_buffer_create_child_anchor(Pointer buffer, Pointer iter);

        /**
         * Creates a mark at position where.
         * <p>
         * If mark_name is NULL, the mark is anonymous; otherwise, the mark can be retrieved by name using
         * gtk_text_buffer_get_mark(). If a mark has left gravity, and text is inserted at the mark's current location,
         * the mark will be moved to the left of the newly-inserted text. If the mark has right gravity
         * (left_gravity = FALSE), the mark will end up on the right of newly-inserted text. The standard left-to-right
         * cursor is a mark with right gravity (when you type, the cursor stays on the right side of the text you're
         * typing).
         * <p>
         * The caller of this function does not own a reference to the returned GtkTextMark, so you can ignore the
         * return value if you like. Marks are owned by the buffer and go away when the buffer does.
         * <p>
         * Emits the GtkTextBuffer::mark-set signal as notification of the mark's initial placement.
         *
         * @param buffer       self
         * @param mark_name    Name for mark.
         *                     <p>
         *                     The argument can be NULL.
         * @param where        Location to place mark. Type: GtkTextIter
         * @param left_gravity Whether the mark has left gravity.
         * @return The new GtkTextMark object.
         */
        public native Pointer gtk_text_buffer_create_mark(Pointer buffer, String mark_name, Pointer where, boolean left_gravity);

        /**
         * Creates a tag and adds it to the tag table for buffer.
         * <p>
         * Equivalent to calling gtk_text_tag_new() and then adding the tag to the buffer's tag table. The returned tag
         * is owned by the buffer's tag table, so the ref count will be equal to one.
         * <p>
         * If tag_name is NULL, the tag is anonymous.
         * <p>
         * If tag_name is non-NULL, a tag called tag_name must not already exist in the tag table for this buffer.
         * <p>
         * The first_property_name argument and subsequent arguments are a list of properties to set on the tag, as
         * with g_object_set().
         *
         * @param buffer             self
         * @param tag_name           Name of the new tag.
         *                           <p>
         *                           The argument can be NULL.
         * @param keyValueProperties List of property names and values.
         * @return A new tag. Type: GtkTextTag
         */
        public Pointer gtk_text_buffer_create_tag(Pointer buffer, String tag_name, String[] keyValueProperties) {
            return INSTANCE.gtk_text_buffer_create_tag(buffer, tag_name, keyValueProperties);
        }

        /**
         * Copies the currently-selected text to a clipboard, then deletes said text if it's editable.
         *
         * @param buffer           self
         * @param clipboard        The GdkClipboard object to cut to. Type: GdkClipboard
         * @param default_editable Default editability of the buffer.
         */
        public native void gtk_text_buffer_cut_clipboard(Pointer buffer, Pointer clipboard, boolean default_editable);

        /**
         * Deletes text between start and end.
         * <p>
         * The order of start and end is not actually relevant; gtk_text_buffer_delete() will reorder them.
         * <p>
         * This function actually emits the "delete-range" signal, and the default handler of that signal deletes the
         * text. Because the buffer is modified, all outstanding iterators become invalid after calling this function;
         * however, the start and end will be re-initialized to point to the location where text was deleted.
         *
         * @param buffer self
         * @param start  A position in buffer. Type: GtkTextIter
         * @param end    Another position in buffer. Type: GtkTextIter
         */
        public native void gtk_text_buffer_delete(Pointer buffer, Pointer start, Pointer end);

        /**
         * Deletes all editable text in the given range.
         * <p>
         * Calls gtk_text_buffer_delete() for each editable sub-range of [start,end). start and end are revalidated to
         * point to the location of the last deleted range, or left untouched if no text was deleted.
         *
         * @param buffer           self
         * @param start_iter       Start of range to delete. Type: GtkTextIter
         * @param end_iter         End of range. Type: GtkTextIter
         * @param default_editable Whether the buffer is editable by default.
         * @return Whether some text was actually deleted.
         */
        public native boolean gtk_text_buffer_delete_interactive(Pointer buffer, Pointer start_iter, Pointer end_iter, boolean default_editable);

        /**
         * Deletes mark, so that it's no longer located anywhere in the buffer.
         * <p>
         * Removes the reference the buffer holds to the mark, so if you haven't called g_object_ref() on the mark,
         * it will be freed. Even if the mark isn't freed, most operations on mark become invalid, until it gets added
         * to a buffer again with gtk_text_buffer_add_mark(). Use gtk_text_mark_get_deleted() to find out if a mark has
         * been removed from its buffer.
         * <p>
         * The GtkTextBuffer::mark-deleted signal will be emitted as notification after the mark is deleted.
         *
         * @param buffer self
         * @param mark   A GtkTextMark in buffer.
         */
        public native void gtk_text_buffer_delete_mark(Pointer buffer, Pointer mark);

        /**
         * Deletes the mark named name; the mark must exist.
         * <p>
         * See gtk_text_buffer_delete_mark() for details.
         *
         * @param buffer self
         * @param name   Name of a mark in buffer.
         */
        public native void gtk_text_buffer_delete_mark_by_name(Pointer buffer, String name);

        /**
         * Deletes the range between the "insert" and "selection_bound" marks, that is, the currently-selected text.
         * <p>
         * If interactive is TRUE, the editability of the selection will be considered (users can't delete uneditable
         * text).
         *
         * @param buffer           self
         * @param interactive      Whether the deletion is caused by user interaction.
         * @param default_editable Whether the buffer is editable by default.
         * @return Whether there was a non-empty selection to delete.
         */
        public native boolean gtk_text_buffer_delete_selection(Pointer buffer, boolean interactive, boolean default_editable);

        /**
         * Denotes the end of an action that may not be undone.
         * <p>
         * This will cause any previous operations in the undo/redo queue to be cleared.
         * <p>
         * This should be called after completing modifications to the text buffer after
         * gtk_text_buffer_begin_irreversible_action() was called.
         * <p>
         * You may nest calls to gtk_text_buffer_begin_irreversible_action() and
         * gtk_text_buffer_end_irreversible_action() pairs.
         *
         * @param buffer self
         */
        public native void gtk_text_buffer_end_irreversible_action(Pointer buffer);

        /**
         * Ends a user-visible operation.
         * <p>
         * Should be paired with a call to gtk_text_buffer_begin_user_action(). See that function for a full
         * explanation.
         *
         * @param buffer self
         */
        public native void gtk_text_buffer_end_user_action(Pointer buffer);

        /**
         * Retrieves the first and last iterators in the buffer, i.e. the entire buffer lies within the range
         * [start,end).
         *
         * @param buffer self
         * @param start  Iterator to initialize with first position in the buffer.
         * @param end    Iterator to initialize with the end iterator.
         */
        public native void gtk_text_buffer_get_bounds(Pointer buffer, PointerByReference start, PointerByReference end);

        /**
         * Gets whether there is a redo-able action in the history.
         *
         * @param buffer self
         * @return TRUE if there is a redo-able action.
         */
        public native boolean gtk_text_buffer_get_can_redo(Pointer buffer);

        /**
         * Gets whether there is an undoable action in the history.
         *
         * @param buffer self
         * @return TRUE if there is an undoable action.
         */
        public native boolean gtk_text_buffer_get_can_undo(Pointer buffer);

        /**
         * Gets the number of characters in the buffer.
         * <p>
         * Note that characters and bytes are not the same, you can't e.g. expect the contents of the buffer in string
         * form to be this many bytes long.
         * <p>
         * The character count is cached, so this function is very fast.
         *
         * @param buffer self
         * @return Number of characters in the buffer.
         */
        public native int gtk_text_buffer_get_char_count(Pointer buffer);

        /**
         * Gets whether the buffer is saving modifications to the buffer to allow for undo and redo actions.
         * <p>
         * See gtk_text_buffer_begin_irreversible_action() and gtk_text_buffer_end_irreversible_action() to create
         * changes to the buffer that cannot be undone.
         *
         * @param buffer self
         * @return TRUE if undoing and redoing changes to the buffer is allowed.
         */
        public native boolean gtk_text_buffer_get_enable_undo(Pointer buffer);

        /**
         * Initializes iter with the "end iterator," one past the last valid character in the text buffer.
         * <p>
         * If de-referenced with gtk_text_iter_get_char(), the end iterator has a character value of 0. The entire
         * buffer
         * lies in the range from the first position in the buffer (call gtk_text_buffer_get_start_iter() to get
         * character position 0) to the end iterator.
         *
         * @param buffer  self
         * @param endIter Iterator to initialize. Type: GtkTextIter
         */
        public native void gtk_text_buffer_get_end_iter(Pointer buffer, PointerByReference endIter);

        /**
         * Indicates whether the buffer has some text currently selected.
         *
         * @param buffer self
         * @return TRUE if the there is text selected.
         */
        public native boolean gtk_text_buffer_get_has_selection(Pointer buffer);

        /**
         * Returns the mark that represents the cursor (insertion point).
         * <p>
         * Equivalent to calling gtk_text_buffer_get_mark() to get the mark named "insert", but very slightly more
         * efficient, and involves less typing.
         *
         * @param buffer self
         * @return Insertion point mark. Type: GtkTextMark
         */
        public native Pointer gtk_text_buffer_get_insert(Pointer buffer);

        /**
         * Obtains the location of anchor within buffer.
         *
         * @param buffer self
         * @param iter   An iterator to be initialized. Type: GtkTextIter
         * @param anchor A child anchor that appears in buffer. Type: GtkTextChildAnchor
         */
        public native void gtk_text_buffer_get_iter_at_child_anchor(Pointer buffer, PointerByReference iter, Pointer anchor);

        /**
         * Initializes iter to the start of the given line.
         * <p>
         * If line_number is greater than or equal to the number of lines in the buffer, the end iterator is returned.
         *
         * @param buffer      self
         * @param iter        Iterator to initialize. Type: GtkTextIter
         * @param line_number Line number counting from 0
         * @return Whether the exact position has been found.
         */
        public native boolean gtk_text_buffer_get_iter_at_line(Pointer buffer, PointerByReference iter, int line_number);

        /**
         * Obtains an iterator pointing to byte_index within the given line.
         * <p>
         * byte_index must be the start of a UTF-8 character. Note bytes, not characters; UTF-8 may encode one character
         * as multiple bytes.
         * <p>
         * If line_number is greater than or equal to the number of lines in the buffer, the end iterator is returned.
         * And if byte_index is off the end of the line, the iterator at the end of the line is returned.
         *
         * @param buffer      self
         * @param iter        Iterator to initialize. Type: GtkTextIter
         * @param line_number Line number counting from 0
         * @param byte_index  Byte index from start of line.
         * @return Whether the exact position has been found.
         */
        public native boolean gtk_text_buffer_get_iter_at_line_index(Pointer buffer, PointerByReference iter, int line_number, int byte_index);

        /**
         * Obtains an iterator pointing to char_offset within the given line.
         * <p>
         * Note characters, not bytes; UTF-8 may encode one character as multiple bytes.
         * <p>
         * If line_number is greater than or equal to the number of lines in the buffer, the end iterator is returned.
         * And if char_offset is off the end of the line, the iterator at the end of the line is returned.
         *
         * @param buffer      self
         * @param iter        Iterator to initialize. Type: GtkTextIter
         * @param line_number Line number counting from 0
         * @param char_offset Char offset from start of line.
         * @return Whether the exact position has been found.
         */
        public native boolean gtk_text_buffer_get_iter_at_line_offset(Pointer buffer, PointerByReference iter, int line_number, int char_offset);

        /**
         * Initializes iter with the current position of mark.
         *
         * @param buffer self
         * @param iter   Iterator to initialize. Type: GtkTextIter
         * @param mark   A GtkTextMark in buffer.
         */
        public native void gtk_text_buffer_get_iter_at_mark(Pointer buffer, PointerByReference iter, Pointer mark);

        /**
         * Initializes iter to a position char_offset chars from the start of the entire buffer.
         * <p>
         * If char_offset is -1 or greater than the number of characters in the buffer, iter is initialized to the end
         * iterator, the iterator one past the last valid character in the buffer.
         *
         * @param buffer      self
         * @param iter        Iterator to initialize. Type: GtkTextIter
         * @param char_offset Char offset from start of buffer, counting from 0, or -1
         */
        public native void gtk_text_buffer_get_iter_at_offset(Pointer buffer, PointerByReference iter, int char_offset);

        /**
         * Obtains the number of lines in the buffer.
         * <p>
         * This value is cached, so the function is very fast.
         *
         * @param buffer self
         * @return Number of lines in the buffer.
         */
        public native int gtk_text_buffer_get_line_count(Pointer buffer);

        /**
         * Returns the mark named name in buffer 'buffer', or NULL if no such mark exists in the buffer.
         *
         * @param buffer self
         * @param name   A mark name.
         * @return A GtkTextMark
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_text_buffer_get_mark(Pointer buffer, String name);

        /**
         * Gets the maximum number of undo levels to perform.
         * <p>
         * If 0, unlimited undo actions may be performed. Note that this may have a memory usage impact as it requires
         * storing an additional copy of the inserted or removed text within the text buffer.
         *
         * @param buffer self
         * @return The max number of undo levels allowed (0 indicates unlimited).
         */
        public native int gtk_text_buffer_get_max_undo_levels(Pointer buffer);

        /**
         * Indicates whether the buffer has been modified since the last call to gtk_text_buffer_set_modified() set the
         * modification flag to FALSE.
         * <p>
         * Used for example to enable a "save" function in a text editor.
         *
         * @param buffer self
         * @return TRUE if the buffer has been modified.
         */
        public native boolean gtk_text_buffer_get_modified(Pointer buffer);

        /**
         * Returns the mark that represents the selection bound.
         * <p>
         * Equivalent to calling gtk_text_buffer_get_mark() to get the mark named "selection_bound", but very slightly
         * more efficient, and involves less typing.
         * <p>
         * The currently-selected text in buffer is the region between the "selection_bound" and "insert" marks.
         * If "selection_bound" and "insert" are in the same place, then there is no current selection.
         * gtk_text_buffer_get_selection_bounds() is another convenient function for handling the selection, if you just
         * want to know whether there's a selection and what its bounds are.
         *
         * @param buffer self
         * @return Selection bound mark. Type: GtkTextMark
         */
        public native Pointer gtk_text_buffer_get_selection_bound(Pointer buffer);

        /**
         * Returns TRUE if some text is selected; places the bounds of the selection in start and end.
         * <p>
         * If the selection has length 0, then start and end are filled in with the same value. start and end will be in
         * ascending order. If start and end are NULL, then they are not filled in, but the return value still indicates
         * whether text is selected.
         *
         * @param buffer self
         * @param start  Iterator to initialize with selection start.
         * @param end    Iterator to initialize with selection end.
         * @return Whether the selection has nonzero length.
         */
        public native boolean gtk_text_buffer_get_selection_bounds(Pointer buffer, PointerByReference start, PointerByReference end);

        /**
         * Get a content provider for this buffer.
         * <p>
         * It can be used to make the content of buffer available in a GdkClipboard, see gdk_clipboard_set_content().
         *
         * @param buffer self
         * @return A new GdkContentProvider.
         */
        public native Pointer gtk_text_buffer_get_selection_content(Pointer buffer);

        /**
         * Returns the text in the range [start,end).
         * <p>
         * Excludes undisplayed text (text marked with tags that set the invisibility attribute) if
         * include_hidden_chars is FALSE. The returned string includes a 0xFFFC character whenever the buffer
         * contains embedded images, so byte and character indexes into the returned string do correspond to byte and
         * character indexes into the buffer. Contrast with gtk_text_buffer_get_text(). Note that 0xFFFC can occur in
         * normal text as well, so it is not a reliable indicator that a paintable or widget is in the buffer.
         *
         * @param buffer               self
         * @param start                Start of a range. Type: GtkTextIter
         * @param end                  End of a range. Type: GtkTextIter
         * @param include_hidden_chars Whether to include invisible text.
         * @return An allocated UTF-8 string.
         */
        public native String gtk_text_buffer_get_slice(Pointer buffer, Pointer start, Pointer end, boolean include_hidden_chars);

        /**
         * Initialized iter with the first position in the text buffer.
         * <p>
         * This is the same as using gtk_text_buffer_get_iter_at_offset() to get the iter at character offset 0.
         *
         * @param buffer self
         * @param iter   Iterator to initialize. Type: GtkTextIter
         */
        public native void gtk_text_buffer_get_start_iter(Pointer buffer, PointerByReference iter);

        /**
         * Get the GtkTextTagTable associated with this buffer.
         *
         * @param buffer self
         * @return The buffer's tag table. Type: GtkTextTagTable
         */
        public native Pointer gtk_text_buffer_get_tag_table(Pointer buffer);

        /**
         * Returns the text in the range [start,end).
         * <p>
         * Excludes undisplayed text (text marked with tags that set the invisibility attribute) if
         * include_hidden_chars is FALSE. Does not include characters representing embedded images, so byte and
         * character indexes into the returned string do not correspond to byte and character indexes into the buffer.
         * Contrast with gtk_text_buffer_get_slice().
         *
         * @param buffer               self
         * @param start                Start of a range. Type: GtkTextIter
         * @param end                  End of a range. Type: GtkTextIter
         * @param include_hidden_chars Whether to include invisible text.
         * @return An allocated UTF-8 string.
         */
        public native String gtk_text_buffer_get_text(Pointer buffer, Pointer start, Pointer end, boolean include_hidden_chars);

        /**
         * Inserts len bytes of text at position iter.
         * <p>
         * If len is -1, text must be nul-terminated and will be inserted in its entirety. Emits the "insert-text"
         * signal; insertion actually occurs in the default handler for the signal. iter is invalidated when insertion
         * occurs (because the buffer contents change), but the default signal handler revalidates it to point to the
         * end of the inserted text.
         *
         * @param buffer self
         * @param iter   A position in the buffer. Type: GtkTextIter
         * @param text   Text in UTF-8 format.
         * @param len    Length of text in bytes, or -1
         */
        public native void gtk_text_buffer_insert(Pointer buffer, Pointer iter, String text, int len);

        /**
         * Inserts text in buffer.
         * <p>
         * Simply calls gtk_text_buffer_insert(), using the current cursor position as the insertion point.
         *
         * @param buffer self
         * @param text   Text in UTF-8 format.
         * @param len    Length of text, in bytes.
         */
        public native void gtk_text_buffer_insert_at_cursor(Pointer buffer, String text, int len);

        /**
         * Inserts a child widget anchor into the text buffer at iter.
         * <p>
         * The anchor will be counted as one character in character counts, and when obtaining the buffer contents as a
         * string, will be represented by the Unicode "object replacement character" 0xFFFC. Note that the "slice"
         * variants for obtaining portions of the buffer as a string include this character for child anchors, but the
         * "text" variants do not. E.g. see gtk_text_buffer_get_slice() and gtk_text_buffer_get_text().
         * <p>
         * Consider gtk_text_buffer_create_child_anchor() as a more convenient alternative to this function. The buffer
         * will add a reference to the anchor, so you can de-ref it after insertion.
         *
         * @param buffer self
         * @param iter   Location to insert the anchor. Type: GtkTextIter
         * @param anchor A GtkTextChildAnchor
         */
        public native void gtk_text_buffer_insert_child_anchor(Pointer buffer, Pointer iter, Pointer anchor);

        /**
         * Inserts text in buffer.
         * <p>
         * Like gtk_text_buffer_insert(), but the insertion will not occur if iter is at a non-editable location in the
         * buffer. Usually you want to prevent insertions at uneditable locations if the insertion results from a user
         * action (is interactive).
         * <p>
         * default_editable indicates the editability of text that doesn't have a tag affecting editability applied to
         * it. Typically, the result of gtk_text_view_get_editable() is appropriate here.
         *
         * @param buffer           self
         * @param iter             A position in buffer. Type: GtkTextIter
         * @param text             Some UTF-8 text.
         * @param len              Length of text in bytes, or -1
         * @param default_editable Default editability of buffer.
         * @return Whether text was actually inserted.
         */
        public native boolean gtk_text_buffer_insert_interactive(Pointer buffer, Pointer iter, String text, int len, boolean default_editable);

        /**
         * Inserts text in buffer.
         * <p>
         * Calls gtk_text_buffer_insert_interactive() at the cursor position.
         * <p>
         * default_editable indicates the editability of text that doesn't have a tag affecting editability applied to
         * it. Typically, the result of gtk_text_view_get_editable() is appropriate here.
         *
         * @param buffer           self
         * @param text             Text in UTF-8 format.
         * @param len              Length of text in bytes, or -1
         * @param default_editable Default editability of buffer.
         * @return Whether text was actually inserted.
         */
        public native boolean gtk_text_buffer_insert_interactive_at_cursor(Pointer buffer, String text, int len, boolean default_editable);

        /**
         * Inserts the text in markup at position iter.
         * <p>
         * markup will be inserted in its entirety and must be nul-terminated and valid UTF-8. Emits the
         * GtkTextBuffer::insert-text signal, possibly multiple times; insertion actually occurs in the default handler
         * for the signal. iter will point to the end of the inserted text on return.
         *
         * @param buffer self
         * @param iter   Location to insert the markup. Type: GtkTextIter
         * @param markup A null-terminated UTF-8 string containing Pango markup.
         * @param len    Length of markup in bytes, or -1
         */
        public native void gtk_text_buffer_insert_markup(Pointer buffer, Pointer iter, String markup, int len);

        /**
         * Inserts an image into the text buffer at iter.
         * <p>
         * The image will be counted as one character in character counts, and when obtaining the buffer contents as a
         * string, will be represented by the Unicode "object replacement character" 0xFFFC. Note that the "slice"
         * variants for obtaining portions of the buffer as a string include this character for paintable, but the
         * "text" variants do not. e.g. see gtk_text_buffer_get_slice() and gtk_text_buffer_get_text().
         *
         * @param buffer    self
         * @param iter      Location to insert the paintable. Type: GtkTextIter
         * @param paintable A GdkPaintable
         */
        public native void gtk_text_buffer_insert_paintable(Pointer buffer, Pointer iter, Pointer paintable);

        /**
         * Copies text, tags, and paintables between start and end and inserts the copy at iter.
         * <p>
         * The order of start and end doesn't matter.
         * <p>
         * Used instead of simply getting/inserting text because it preserves images and tags. If start and end are in
         * a different buffer from buffer, the two buffers must share the same tag table.
         * <p>
         * Implemented via emissions of the ::insert-text and ::apply-tag signals, so expect those.
         *
         * @param buffer self
         * @param iter   A position in buffer. Type: GtkTextIter
         * @param start  A position in a GtkTextBuffer. Type: GtkTextIter
         * @param end    Another position in start's buffer Type: GtkTextIter
         */
        public native void gtk_text_buffer_insert_range(Pointer buffer, Pointer iter, Pointer start, Pointer end);

        /**
         * Copies text, tags, and paintables between start and end and inserts the copy at iter.
         * <p>
         * Same as gtk_text_buffer_insert_range(), but does nothing if the insertion point isn't editable. The
         * default_editable parameter indicates whether the text is editable at iter if no tags enclosing iter affect
         * editability. Typically, the result of gtk_text_view_get_editable() is appropriate here.
         *
         * @param buffer           self
         * @param iter             A position buffer. Type: GtkTextIter
         * @param start            A position in a GtkTextBuffer. Type: GtkTextIter
         * @param end              Another position in start's buffer Type: GtkTextIter
         * @param default_editable Default editability of the buffer.
         * @return Whether an insertion was possible at iter.
         */
        public native boolean gtk_text_buffer_insert_range_interactive(Pointer buffer, Pointer iter, Pointer start, Pointer end, boolean default_editable);

        /**
         * Inserts text into buffer at iter, applying the list of tags to the newly-inserted text.
         * <p>
         * The last tag specified must be NULL to terminate the list. Equivalent to calling gtk_text_buffer_insert(),
         * then gtk_text_buffer_apply_tag() on the inserted text; this is just a convenience function.
         *
         * @param buffer      self
         * @param iter        An iterator in buffer. Type: GtkTextIter
         * @param text        UTF-8 text.
         * @param len         Length of text, or -1
         * @param tagsToApply tag to apply to text.
         */
        public void gtk_text_buffer_insert_with_tags(Pointer buffer, Pointer iter, String text, int len, Pointer[] tagsToApply) {
            INSTANCE.gtk_text_buffer_insert_with_tags(buffer, iter, text, len, tagsToApply);
        }

        /**
         * Inserts text into buffer at iter, applying the list of tags to the newly-inserted text.
         * <p>
         * Same as gtk_text_buffer_insert_with_tags(), but allows you to pass in tag names instead of tag objects.
         *
         * @param buffer          self
         * @param iter            Position in buffer. Type: GtkTextIter
         * @param text            UTF-8 text.
         * @param len             Length of text, or -1
         * @param tagNamesToApply Names of a tag to apply to text.
         */
        public void gtk_text_buffer_insert_with_tags_by_name(Pointer buffer, Pointer iter, String text, int len, String[] tagNamesToApply) {
            INSTANCE.gtk_text_buffer_insert_with_tags_by_name(buffer, iter, text, len, tagNamesToApply);
        }

        /**
         * Moves mark to the new location where.
         * <p>
         * Emits the GtkTextBuffer::mark-set signal as notification of the move.
         *
         * @param buffer self
         * @param mark   A GtkTextMark
         * @param where  New location for mark in buffer. Type: GtkTextIter
         */
        public native void gtk_text_buffer_move_mark(Pointer buffer, Pointer mark, Pointer where);

        /**
         * Moves the mark named name (which must exist) to location where.
         * <p>
         * See gtk_text_buffer_move_mark() for details.
         *
         * @param buffer self
         * @param name   Name of a mark.
         * @param where  New location for mark. Type: GtkTextIter
         */
        public native void gtk_text_buffer_move_mark_by_name(Pointer buffer, String name, Pointer where);

        /**
         * Creates a new text buffer.
         *
         * @param table A tag table, or NULL to create a new one. Type: GtkTextTagTable
         *              <p>
         *              The argument can be NULL.
         * @return A new text buffer. Type: GtkTextBuffer
         */
        public native Pointer gtk_text_buffer_new(Pointer table);

        /**
         * Pastes the contents of a clipboard.
         * <p>
         * If override_location is NULL, the pasted text will be inserted at the cursor position, or the buffer
         * selection will be replaced if the selection is non-empty.
         * <p>
         * Note: pasting is asynchronous, that is, we'll ask for the paste data and return, and at some point later
         * after the main loop runs, the paste data will be inserted.
         *
         * @param buffer            self
         * @param clipboard         The GdkClipboard to paste from.
         * @param override_location Location to insert pasted text.
         *                          <p>
         *                          The argument can be NULL.
         * @param default_editable  Whether the buffer is editable by default.
         */
        public native void gtk_text_buffer_paste_clipboard(Pointer buffer, Pointer clipboard, Pointer override_location, boolean default_editable);

        /**
         * This function moves the "insert" and "selection_bound" marks simultaneously.
         * <p>
         * If you move them to the same place in two steps with gtk_text_buffer_move_mark(), you will temporarily
         * select a region in between their old and new locations, which can be pretty inefficient since the
         * temporarily-selected region will force stuff to be recalculated. This function moves them as a unit, which
         * can be optimized.
         *
         * @param buffer self
         * @param where  Where to put the cursor. Type: GtkTextIter
         */
        public native void gtk_text_buffer_place_cursor(Pointer buffer, Pointer where);

        /**
         * Redoes the next redo-able action on the buffer, if there is one.
         *
         * @param buffer self
         */
        public native void gtk_text_buffer_redo(Pointer buffer);

        /**
         * Removes all tags in the range between start and end.
         * <p>
         * Be careful with this function; it could remove tags added in code unrelated to the code you're currently
         * writing. That is, using this function is probably a bad idea if you have two or more unrelated code sections
         * that add tags.
         *
         * @param buffer self
         * @param start  One bound of range to be untagged. Type: GtkTextIter
         * @param end    Other bound of range to be untagged. Type: GtkTextIter
         */
        public native void gtk_text_buffer_remove_all_tags(Pointer buffer, Pointer start, Pointer end);

        /**
         * Removes a GdkClipboard added with gtk_text_buffer_add_selection_clipboard()
         *
         * @param buffer    self
         * @param clipboard A GdkClipboard added to buffer by gtk_text_buffer_add_selection_clipboard()
         */
        public native void gtk_text_buffer_remove_selection_clipboard(Pointer buffer, Pointer clipboard);

        /**
         * Emits the "remove-tag" signal.
         * <p>
         * The default handler for the signal removes all occurrences of tag from the given range. start and end don't
         * have to be in order.
         *
         * @param buffer self
         * @param tag    A GtkTextTag
         * @param start  One bound of range to be untagged. Type: GtkTextIter
         * @param end    Other bound of range to be untagged. Type: GtkTextIter
         */
        public native void gtk_text_buffer_remove_tag(Pointer buffer, Pointer tag, Pointer start, Pointer end);

        /**
         * Emits the "remove-tag" signal.
         * <p>
         * Calls gtk_text_tag_table_lookup() on the buffer's tag table to get a GtkTextTag, then calls
         * gtk_text_buffer_remove_tag().
         *
         * @param buffer self
         * @param name   Name of a GtkTextTag
         * @param start  One bound of range to be untagged. Type: GtkTextIter
         * @param end    Other bound of range to be untagged. Type: GtkTextIter
         */
        public native void gtk_text_buffer_remove_tag_by_name(Pointer buffer, String name, Pointer start, Pointer end);

        /**
         * This function moves the "insert" and "selection_bound" marks simultaneously.
         * <p>
         * If you move them in two steps with gtk_text_buffer_move_mark(), you will temporarily select a region in
         * between their old and new locations, which can be pretty inefficient since the temporarily-selected region
         * will force stuff to be recalculated. This function moves them as a unit, which can be optimized.
         *
         * @param buffer self
         * @param ins    Where to put the "insert" mark. Type: GtkTextIter
         * @param bound  Where to put the "selection_bound" mark. Type: GtkTextIter
         */
        public native void gtk_text_buffer_select_range(Pointer buffer, Pointer ins, Pointer bound);

        /**
         * Sets whether to enable undoable actions in the text buffer.
         * <p>
         * Undoable actions in this context are changes to the text content of the buffer. Changes to tags and marks
         * are not tracked.
         * <p>
         * If enabled, the user will be able to undo the last number of actions up to
         * gtk_text_buffer_get_max_undo_levels().
         * <p>
         * See gtk_text_buffer_begin_irreversible_action() and gtk_text_buffer_end_irreversible_action() to create
         * changes to the buffer that cannot be undone.
         *
         * @param buffer       self
         * @param undo_enabled TRUE to enable undo.
         */
        public native void gtk_text_buffer_set_enable_undo(Pointer buffer, boolean undo_enabled);

        /**
         * Sets the maximum number of undo levels to perform.
         * <p>
         * If 0, unlimited undo actions may be performed. Note that this may have a memory usage impact as it requires
         * storing an additional copy of the inserted or removed text within the text buffer.
         *
         * @param buffer          self
         * @param max_undo_levels The maximum number of undo actions to perform.
         */
        public native void gtk_text_buffer_set_max_undo_levels(Pointer buffer, int max_undo_levels);

        /**
         * Used to keep track of whether the buffer has been modified since the last time it was saved.
         * <p>
         * Whenever the buffer is saved to disk, call gtk_text_buffer_set_modified (buffer, FALSE). When the buffer is
         * modified, it will automatically toggle on the modified bit again. When the modified bit flips, the buffer
         * emits the GtkTextBuffer::modified-changed signal.
         *
         * @param buffer  self
         * @param setting Modification flag setting.
         */
        public native void gtk_text_buffer_set_modified(Pointer buffer, boolean setting);

        /**
         * Deletes current contents of buffer, and inserts text instead. This is automatically marked as an
         * irreversible action in the undo stack. If you wish to mark this action as part of a larger undo operation,
         * call gtk_text_buffer_delete() and gtk_text_buffer_insert() directly instead.
         * <p>
         * If len is -1, text must be nul-terminated. text must be valid UTF-8.
         *
         * @param buffer self
         * @param text   UTF-8 text to insert.
         * @param len    Length of text in bytes.
         */
        public native void gtk_text_buffer_set_text(Pointer buffer, String text, int len);

        /**
         * Undoes the last undoable action on the buffer, if there is one.
         *
         * @param buffer self
         */
        public native void gtk_text_buffer_undo(Pointer buffer);
    }
}
