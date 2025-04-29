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

import com.gitlab.ccook.jgtk.bitfields.GtkTextSearchFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkTextCharPredicate;
import com.gitlab.ccook.jgtk.gtk.GtkTextBuffer;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


@SuppressWarnings("unchecked")
public class GtkTextIter extends JGTKObject implements Comparable<GtkTextIter>, Cloneable {
    public GtkTextIter(Pointer cReference) {
        super(cReference);
    }

    /**
     * Assigns the value of other to iter.
     *
     * @param other Another GtkTextIter
     */
    public void assign(GtkTextIter other) {
        if (other != null) {
            library.gtk_text_iter_assign(getCReference(), other.getCReference());
        }
    }

    /**
     * Creates a dynamically-allocated copy of an iterator.
     *
     * @return A copy of the iter, free with gtk_text_iter_free()
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public GtkTextIter clone() {
        return new GtkTextIter(library.gtk_text_iter_copy(getCReference()));
    }

    /**
     * Free an iterator allocated on the heap.
     * <p>
     * This function is intended for use in language bindings, and is not especially useful for applications,
     * because iterators can simply be allocated on the stack.
     *
     * @throws Throwable any issue encountered
     */
    @SuppressWarnings("removal")
    @Override
    protected void finalize() throws Throwable {
        library.gtk_text_iter_free(getCReference());
        super.finalize();
    }


    /**
     * Returns -1 if iter is less than this object
     * Returns 1 if iter is greater than this object
     * Returns 0 if iter is equal to this object
     *
     * @param iter Another GtkTextIter
     * @return -1 if lhs is less than rhs, 1 if lhs is greater, 0 if they are equal.
     */
    @Override
    public int compareTo(GtkTextIter iter) {
        return library.gtk_text_iter_compare(getCReference(), iter.getCReference());
    }

    /**
     * Returns whether the character at iter is within an editable region of text.
     * <p>
     * Non-editable text is "locked" and can't be changed by the user via GtkTextView. If no tags applied to this text
     * affect editability, default_setting will be returned.
     * <p>
     * You don't want to use this function to decide whether text can be inserted at iter, because for insertion you
     * don't want to know whether the char at iter is inside an editable range, you want to know whether a new character
     * inserted at iter would be inside an editable range. Use gtk_text_iter_can_insert() to handle this case.
     *
     * @param defaultEditability TRUE if text is editable by default.
     * @return Whether iter is inside an editable range.
     */
    public boolean couldEdit(boolean defaultEditability) {
        return library.gtk_text_iter_editable(getCReference(), defaultEditability);
    }

    /**
     * Considering the default editability of the buffer, and tags that affect editability, determines whether text
     * inserted at iter would be editable.
     * <p>
     * If text inserted at iter would be editable then the user should be allowed to insert text at iter.
     * gtk_text_buffer_insert_interactive() uses this function to decide whether insertions are allowed at a
     * given position.
     *
     * @param defaultEditability TRUE if text is editable by default.
     * @return Whether text inserted at iter would be editable.
     */
    public boolean couldInsert(boolean defaultEditability) {
        return library.gtk_text_iter_can_insert(getCReference(), defaultEditability);
    }

    /**
     * Gets whether a range with tag applied to it begins or ends at iter.
     * <p>
     * This is equivalent to (gtk_text_iter_starts_tag() || gtk_text_iter_ends_tag())
     *
     * @param t A GtkTextTag
     *          <p>
     *          The argument can be NULL
     * @return Whether tag is toggled on or off at iter.
     */
    public boolean doesToggleTag(GtkTextTag t) {
        if (t != null) {
            return library.gtk_text_iter_toggles_tag(getCReference(), t.getCReference());
        } else {
            return library.gtk_text_iter_toggles_tag(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Tests whether two iterators are equal, using the fastest possible mechanism.
     * <p>
     * This function is very fast; you can expect it to perform better than e.g. getting the character offset for each
     * iterator and comparing the offsets yourself. Also, it's a bit faster than gtk_text_iter_compare().
     *
     * @param o Another GtkTextIter
     * @return TRUE if the iterators point to the same place in the buffer.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof GtkTextIter) {
            return library.gtk_text_iter_equal(getCReference(), ((GtkTextIter) o).getCReference());
        }
        return super.equals(o);
    }

    /**
     * Returns the GtkTextBuffer this iterator is associated with.
     *
     * @return The buffer.
     */
    public GtkTextBuffer getBuffer() {
        return new GtkTextBuffer(library.gtk_text_iter_get_buffer(getCReference()));
    }

    /**
     * The Unicode character at this iterator is returned.
     * <p>
     * Equivalent to operator* on a C++ iterator. If the element at this iterator is a non-character element, such as
     * an image embedded in the buffer, the Unicode "unknown" character 0xFFFC is returned. If invoked on the end
     * iterator, zero is returned; zero is not a valid Unicode character.
     * <p>
     * So you can write a loop which ends when this function returns 0.
     *
     * @return A Unicode character, or NONE if iter is not de-referenceable.
     */
    public Option<Character> getChar() {
        char c = library.gtk_text_iter_get_char(getCReference());
        if (c == 0) {
            return Option.NONE;
        }
        return new Option<>(c);
    }

    /**
     * Returns the character offset of an iterator.
     * <p>
     * Each character in a GtkTextBuffer has an offset, starting with 0 for the first character in the buffer.
     * Use get_iter_at_offset to convert an offset back into an iterator.
     *
     * @return A character offset.
     */
    public int getCharacterOffset() {
        return library.gtk_text_iter_get_offset(getCReference());
    }

    /**
     * Sets iter to point to char_offset.
     * <p>
     * char_offset counts from the start of the entire text buffer, starting with 0.
     *
     * @param characterOffset A character number.
     */
    public void setCharacterOffset(int characterOffset) {
        library.gtk_text_iter_set_offset(getCReference(), characterOffset);
    }

    /**
     * If the location at iter contains a child anchor, the anchor is returned.
     * <p>
     * Otherwise, NONE is returned.
     *
     * @return If defined, The anchor at iter.
     */
    public Option<GtkTextChildAnchor> getChildAnchor() {
        Pointer p = library.gtk_text_iter_get_child_anchor(getCReference());
        if (p != null) {
            return new Option<>(new GtkTextChildAnchor(p));
        }
        return Option.NONE;
    }

    /**
     * Returns the language in effect at iter.
     * <p>
     * If no tags affecting language apply to iter, the return value is identical to that of gtk_get_default_language().
     *
     * @return Language in effect at iter.
     */
    public PangoLanguage getLanguage() {
        return new PangoLanguage(library.gtk_text_iter_get_language(getCReference()));
    }

    /**
     * Returns the byte index of the iterator, counting from the start of a newline-terminated line.
     * <p>
     * Remember that GtkTextBuffer encodes text in UTF-8, and that characters can require a variable number of bytes
     * to represent.
     *
     * @return Distance from start of line, in bytes.
     */
    public int getLineByteOffset() {
        return library.gtk_text_iter_get_line_index(getCReference());
    }

    /**
     * Same as gtk_text_iter_set_line_offset(), but works with a byte index. The given byte index must be at the start
     * of a character, it can't be in the middle of a UTF-8 encoded character.
     *
     * @param lineByteOffset A byte index relative to the start of iter current line.
     */
    public void setLineByteOffset(int lineByteOffset) {
        library.gtk_text_iter_set_line_index(getCReference(), lineByteOffset);
    }

    /**
     * Returns the number of bytes in the line containing iter, including the paragraph delimiters.
     *
     * @return Number of bytes in the line.
     */
    public int getLineByteSize() {
        return library.gtk_text_iter_get_bytes_in_line(getCReference());
    }

    /**
     * Returns the character offset of the iterator, counting from the start of a newline-terminated line.
     * <p>
     * The first character on the line has offset 0.
     *
     * @return Offset from start of line.
     */
    public int getLineCharacterOffset() {
        return library.gtk_text_iter_get_line_offset(getCReference());
    }

    /**
     * Moves iter within a line, to a new character (not byte) offset.
     * <p>
     * The given character offset must be less than or equal to the number of characters in the line; if equal, iter
     * moves to the start of the next line. See gtk_text_iter_set_line_index() if you have a byte index rather than a
     * character offset.
     *
     * @param lineCharacterOffset A character offset relative to the start of iter current line.
     */
    public void setLineCharacterOffset(int lineCharacterOffset) {
        library.gtk_text_iter_set_line_offset(getCReference(), lineCharacterOffset);
    }

    /**
     * Returns the number of characters in the line containing iter, including the paragraph delimiters.
     *
     * @return Number of characters in the line.
     */
    public int getLineCharacterSize() {
        return library.gtk_text_iter_get_chars_in_line(getCReference());
    }

    /**
     * Returns the line number containing the iterator.
     * <p>
     * Lines in a GtkTextBuffer are numbered beginning with 0 for the first line in the buffer.
     *
     * @return A line number.
     */
    public int getLineNumber() {
        return library.gtk_text_iter_get_line(getCReference());
    }

    /**
     * Moves iterator iter to the start of the line line_number.
     * <p>
     * If line_number is negative or larger than or equal to the number of lines in the buffer, moves iter to the start
     * of the last line in the buffer.
     *
     * @param lineNumber Line number (counted from 0)
     */
    public void setLineNumber(int lineNumber) {
        library.gtk_text_iter_set_line(getCReference(), lineNumber);
    }

    /**
     * Returns a list of all GtkTextMark at this location.
     * <p>
     * Because marks are not iterable (they don't take up any "space" in the buffer, they are just marks in between
     * iterable locations), multiple marks can exist in the same place.
     * <p>
     * The returned list is not in any meaningful order.
     *
     * @return list of GtkTextMark
     */
    public GSList<GtkTextMark> getMarks() {
        return new GSList<>(library.gtk_text_iter_get_marks(getCReference()), GtkTextMark.class);
    }

    /**
     * If the element at iter is a paintable, the paintable is returned.
     * <p>
     * Otherwise, NONE is returned.
     *
     * @return If defined, the paintable at iter.
     */
    public Option<GdkPaintable> getPaintable() {
        Option<Pointer> p = new Option<>(library.gtk_text_iter_get_paintable(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GdkPaintable) JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
        }
        return Option.NONE;
    }

    /**
     * Returns the text in the given range.
     * <p>
     * A "slice" is an array of characters encoded in UTF-8 format, including the Unicode "unknown" character 0xFFFC
     * for iterable non-character elements in the buffer, such as images. Because images are encoded in the slice,
     * byte and character offsets in the returned array will correspond to byte offsets in the text buffer.
     * Note that 0xFFFC can occur in normal text as well, so it is not a reliable indicator that a paintable or
     * widget is in the buffer.
     *
     * @param end Iterator at end of a range.
     * @return Slice of text from the buffer.
     */
    public String getSlice(GtkTextIter end) {
        if (end != null) {
            return library.gtk_text_iter_get_slice(getCReference(), end.getCReference());
        }
        return "";
    }

    /**
     * Returns a list of tags that apply to iter, in ascending order of priority.
     * <p>
     * The highest-priority tags are last.
     * <p>
     * The GtkTextTags in the list don't have a reference added, but you have to free the list itself.
     *
     * @return List of GtkTextTag
     */
    public GSList<GtkTextTag> getTags() {
        return new GSList<>(library.gtk_text_iter_get_tags(getCReference()), GtkTextTag.class);
    }

    /**
     * Returns text in the given range.
     * <p>
     * If the range contains non-text elements such as images, the character and byte offsets in the returned string
     * will not correspond to character and byte offsets in the buffer. If you want offsets to correspond,
     * see gtk_text_iter_get_slice().
     *
     * @param end Iterator at end of a range.
     * @return Array of characters from the buffer.
     */
    public String getText(GtkTextIter end) {
        if (end != null) {
            return library.gtk_text_iter_get_text(getCReference(), end.getCReference());
        }
        return "";
    }

    /**
     * Returns a list of GtkTextTag that are toggled on or off at this point.
     * <p>
     * If toggled_on is TRUE, the list contains tags that are toggled on. If a tag is toggled on at iter, then some
     * non-empty range of characters following iter has that tag applied to it. If a tag is toggled off, then some
     * non-empty range following iter does not have the tag applied to it.
     *
     * @param toggledOn TRUE to get toggled-on tags.
     * @return Tags toggled at this point.
     */
    public GSList<GtkTextTag> getToggledTags(boolean toggledOn) {
        return new GSList<>(library.gtk_text_iter_get_toggled_tags(getCReference(), toggledOn), GtkTextTag.class);
    }

    /**
     * Returns the number of bytes from the start of the line to the given iter, not counting bytes that are invisible
     * due to tags with the "invisible" flag toggled on.
     *
     * @return Byte index of iter with respect to the start of the line.
     */
    public int getVisibleLineByteOffset() {
        return library.gtk_text_iter_get_visible_line_index(getCReference());
    }

    /**
     * Like gtk_text_iter_set_line_index(), but the index is in visible bytes, i.e. text with a tag making it invisible
     * is not counted in the index.
     *
     * @param visLineByteOffset A byte index.
     */
    public void setVisibleLineByteOffset(int visLineByteOffset) {
        library.gtk_text_iter_set_visible_line_index(getCReference(), visLineByteOffset);
    }

    /**
     * Returns the offset in characters from the start of the line to the given iter, not counting characters that are
     * invisible due to tags with the "invisible" flag toggled on.
     *
     * @return Offset in visible characters from the start of the line.
     */
    public int getVisibleLineCharacterOffset() {
        return library.gtk_text_iter_get_visible_line_offset(getCReference());
    }

    /**
     * Like gtk_text_iter_set_line_offset(), but the offset is in visible characters, i.e. text with a tag making it
     * invisible is not counted in the offset.
     *
     * @param visLineCharOffset A character offset.
     */
    public void setVisibleLineCharacterOffset(int visLineCharOffset) {
        library.gtk_text_iter_set_visible_line_offset(getCReference(), visLineCharOffset);
    }

    /**
     * Returns visible text in the given range.
     * <p>
     * Like gtk_text_iter_get_slice(), but invisible text is not included. Invisible text is usually invisible because
     * a GtkTextTag with the "invisible" attribute turned on has been applied to it.
     *
     * @param end Iterator at end of range.
     * @return Slice of text from the buffer.
     */
    public String getVisibleSlice(GtkTextIter end) {
        if (end != null) {
            return library.gtk_text_iter_get_visible_slice(getCReference(), end.getCReference());
        }
        return "";
    }

    /**
     * Returns visible text in the given range.
     * <p>
     * Like gtk_text_iter_get_text(), but invisible text is not included. Invisible text is usually invisible because a
     * GtkTextTag with the "invisible" attribute turned on has been applied to it.
     *
     * @param end Iterator at end of range.
     * @return String containing visible text in the range.
     */
    public String getVisibleText(GtkTextIter end) {
        if (end != null) {
            return library.gtk_text_iter_get_visible_text(getCReference(), end.getCReference());
        }
        return "";
    }

    /**
     * Returns TRUE if iter points to a character that is part of a range tagged with tag.
     * <p>
     * See also gtk_text_iter_starts_tag() and gtk_text_iter_ends_tag().
     *
     * @param t A GtkTextTag
     * @return Whether iter is tagged with tag.
     */
    public boolean hasTag(GtkTextTag t) {
        if (t != null) {
            return library.gtk_text_iter_has_tag(getCReference(), t.getCReference());
        }
        return false;
    }

    /**
     * Determine if iter is at a cursor position.
     * <p>
     * See gtk_text_iter_forward_cursor_position() or PangoLogAttr or pango_break() for details on what a cursor
     * position is.
     *
     * @return TRUE if the cursor can be placed at iter.
     */
    public boolean isAtCursorPosition() {
        return library.gtk_text_iter_is_cursor_position(getCReference());
    }

    /**
     * Returns TRUE if iter is the end iterator.
     * <p>
     * This means it is one past the last de-referenceable iterator in the buffer. gtk_text_iter_is_end() is the most
     * efficient way to check whether an iterator is the end iterator.
     *
     * @return Whether iter is the end iterator.
     */
    public boolean isAtEnd() {
        return library.gtk_text_iter_is_end(getCReference());
    }

    /**
     * Returns TRUE if iter points to the start of the paragraph delimiter characters for a line.
     * <p>
     * Delimiters will be either a newline, a carriage return, a carriage return followed by a newline, or a Unicode
     * paragraph separator character.
     * <p>
     * Note that an iterator pointing to the \n of a \r\n pair will not be counted as the end of a line, the line ends
     * before the \r. The end iterator is considered to be at the end of a line, even though there are no paragraph
     * delimiter chars there.
     *
     * @return Whether iter is at the end of a line.
     */
    public boolean isAtEndOfLine() {
        return library.gtk_text_iter_ends_line(getCReference());
    }

    /**
     * Determines whether iter ends a sentence.
     * <p>
     * Sentence boundaries are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter is at the end of a sentence.
     */
    public boolean isAtEndOfSentence() {
        return library.gtk_text_iter_ends_sentence(getCReference());
    }

    /**
     * Returns TRUE if tag is toggled off at exactly this point.
     * <p>
     * If tag is NULL, returns TRUE if any tag is toggled off at this point.
     * <p>
     * Note that if this function returns TRUE, it means that iter is at the end of the tagged range, but that the
     * character at iter is outside the tagged range. In other words, unlike gtk_text_iter_starts_tag(), if this
     * function returns TRUE, gtk_text_iter_has_tag() will return FALSE for the same parameters.
     *
     * @param t A GtkTextTag
     *          <p>
     *          The argument can be NULL.
     * @return Whether iter is the end of a range tagged with tag.
     */
    public boolean isAtEndOfTag(GtkTextTag t) {
        if (t != null) {
            return library.gtk_text_iter_ends_tag(getCReference(), t.getCReference());
        }
        return library.gtk_text_iter_ends_tag(getCReference(), Pointer.NULL);
    }

    /**
     * Determines whether iter ends a natural-language word.
     * <p>
     * Word breaks are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter is at the end of a word.
     */
    public boolean isAtEndOfWord() {
        return library.gtk_text_iter_ends_word(getCReference());
    }

    /**
     * Returns TRUE if iter begins a paragraph.
     * <p>
     * This is the case if gtk_text_iter_get_line_offset() would return 0. However, this function is potentially more
     * efficient than gtk_text_iter_get_line_offset(), because it doesn't have to compute the offset, it just has to
     * see whether it's 0.
     *
     * @return Whether iter begins a line.
     */
    public boolean isAtLineStart() {
        return library.gtk_text_iter_starts_line(getCReference());
    }

    /**
     * Determines whether iter begins a sentence.
     * <p>
     * Sentence boundaries are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter is at the start of a sentence.
     */
    public boolean isAtSentenceStart() {
        return library.gtk_text_iter_starts_sentence(getCReference());
    }

    /**
     * Returns TRUE if iter is the first iterator in the buffer.
     *
     * @return Whether iter is the first in the buffer.
     */
    public boolean isAtStart() {
        return library.gtk_text_iter_is_start(getCReference());
    }

    /**
     * Returns TRUE if tag is toggled on at exactly this point.
     * <p>
     * If tag is NULL, returns TRUE if any tag is toggled on at this point.
     * <p>
     * Note that if this function returns TRUE, it means that iter is at the beginning of the tagged range, and that
     * the character at iter is inside the tagged range. In other words, unlike gtk_text_iter_ends_tag(),
     * if this function returns TRUE, gtk_text_iter_has_tag will also return TRUE for the same parameters.
     *
     * @param t A GtkTextTag
     *          <p>
     *          The argument can be NULL.
     * @return Whether iter is the start of a range tagged with tag.
     */
    public boolean isAtTagStart(GtkTextTag t) {
        if (t != null) {
            return library.gtk_text_iter_starts_tag(getCReference(), t.getCReference());
        } else {
            return library.gtk_text_iter_starts_tag(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Determines whether iter begins a natural-language word.
     * <p>
     * Word breaks are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter is at the start of a word.
     */
    public boolean isAtWordStart() {
        return library.gtk_text_iter_starts_word(getCReference());
    }

    /**
     * Checks whether iter falls in the range [start, end).
     * <p>
     * start and end must be in ascending order.
     *
     * @param start Start of range.
     * @param end   End of range.
     * @return TRUE if iter is in the range.
     */
    public boolean isInRange(GtkTextIter start, GtkTextIter end) {
        if (start != null && end != null) {
            return library.gtk_text_iter_in_range(getCReference(), start.getCReference(), end.getCReference());
        }
        return false;
    }

    /**
     * Determines whether iter is inside a sentence (as opposed to in between two sentences, e.g. after a period and
     * before the first letter of the next sentence).
     * <p>
     * Sentence boundaries are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter is inside a sentence.
     */
    public boolean isInsideSentence() {
        return library.gtk_text_iter_inside_sentence(getCReference());
    }

    /**
     * Determines whether the character pointed by iter is part of a natural-language word (as opposed to say inside
     * some whitespace).
     * <p>
     * Word breaks are determined by Pango and should be correct for nearly any language.
     * <p>
     * Note that if gtk_text_iter_starts_word() returns TRUE, then this function returns TRUE too, since iter points to
     * the first character of the word.
     *
     * @return TRUE if iter is inside a word.
     */
    public boolean isInsideWord() {
        return library.gtk_text_iter_inside_word(getCReference());
    }

    /**
     * Moves count characters backward, if possible.
     * <p>
     * If count would move past the start or end of the buffer, moves to the start or end of the buffer.
     * <p>
     * The return value indicates whether the iterator moved onto a de-reference-able position; if the iterator didn't
     * move, or moved onto the end iterator, then FALSE is returned. If count is 0, the function does nothing and
     * returns FALSE.
     *
     * @param numCharacters Number of characters to move.
     * @return Whether iter moved and is dereference-able.
     */
    public boolean moveBackCharacters(int numCharacters) {
        return library.gtk_text_iter_backward_chars(getCReference(), Math.max(0, numCharacters));
    }

    /**
     * Moves up to numCursorPositions cursor positions backward.
     *
     * @param numCursorPositions Number of positions to move.
     * @return TRUE if we moved and the new position is dereference-able.
     */
    public boolean moveBackCursorPositions(int numCursorPositions) {
        return library.gtk_text_iter_backward_cursor_positions(getCReference(), numCursorPositions);
    }

    /**
     * Moves backward by one character offset.
     * <p>
     * Returns TRUE if movement was possible; if iter was the first in the buffer (character offset 0), this function
     * returns FALSE for convenience when writing loops.
     *
     * @return Whether movement was possible.
     */
    public boolean moveBackOneCharacter() {
        return library.gtk_text_iter_backward_char(getCReference());
    }

    /**
     * Moves iter backward by a single cursor position.
     * <p>
     * Cursor positions are (unsurprisingly) positions where the cursor can appear. Perhaps surprisingly, there may not
     * be a cursor position between all characters. The most common example for European languages would be a
     * carriage return/newline sequence.
     * <p>
     * For some Unicode characters, the equivalent of say the letter "a" with an accent mark will be represented as two
     * characters, first the letter then a "combining mark" that causes the accent to be rendered; so the cursor can't
     * go between those two characters.
     * <p>
     * See also the PangoLogAttr struct and the pango_break() function.
     *
     * @return TRUE if we moved and the new position is de-reference-able.
     */
    public boolean moveBackOneCursorPosition() {
        return library.gtk_text_iter_backward_cursor_position(getCReference());
    }

    /**
     * Moves iter backward to the previous visible cursor position.
     * <p>
     * See gtk_text_iter_backward_cursor_position() for details.
     *
     * @return TRUE if we moved and the new position is dereference-able
     */
    public boolean moveBackOneVisibleCursorPosition() {
        return library.gtk_text_iter_backward_visible_cursor_position(getCReference());
    }

    /**
     * Moves iter to the start of the previous visible line.
     * <p>
     * Returns TRUE if iter could be moved; i.e. if iter was at character offset 0, this function returns FALSE.
     * Therefore, if iter was already on line 0, but not at the start of the line, iter is snapped to the start of the
     * line and the function returns TRUE. (Note that this implies that in a loop calling this function, the line number
     * may not change on every iteration, if your first iteration is on line 0.)
     *
     * @return Whether iter moved.
     */
    public boolean moveBackOneVisibleLine() {
        return library.gtk_text_iter_backward_visible_line(getCReference());
    }

    /**
     * Moves backward to the previous sentence start.
     * <p>
     * If iter is already at the start of a sentence, moves backward to the next one.
     * <p>
     * Sentence boundaries are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveBackToPreviousSentenceStart() {
        return library.gtk_text_iter_backward_sentence_start(getCReference());
    }

    /**
     * Calls gtk_text_iter_backward_sentence_start() up to count times.
     * <p>
     * If count is negative, moves forward instead of backward.
     *
     * @param numOfSentenceStartsToMoveBack Number of sentences to move.
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveBackToSentenceStartMultiple(int numOfSentenceStartsToMoveBack) {
        return library.gtk_text_iter_backward_sentence_starts(getCReference(), numOfSentenceStartsToMoveBack);
    }

    /**
     * Moves backward to the next toggle (on or off) of the tag, or to the next toggle of any tag if tag is NULL.
     * <p>
     * If no matching tag toggles are found, returns FALSE, otherwise TRUE. Does not return toggles located at iter,
     * only toggles before iter. Sets iter to the location of the toggle, or the start of the buffer if no toggle is
     * found.
     *
     * @param tag A GtkTextTag
     *            <p>
     *            The argument can be NULL.
     * @return Whether we found a tag toggle before iter.
     */
    public boolean moveBackToTagToggle(GtkTextTag tag) {
        if (tag != null) {
            return library.gtk_text_iter_backward_to_tag_toggle(getCReference(), tag.getCReference());
        } else {
            return library.gtk_text_iter_backward_to_tag_toggle(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Moves up to count visible cursor positions.
     * <p>
     * See gtk_text_iter_backward_cursor_position() for details.
     *
     * @param numPositions Number of positions to move.
     * @return TRUE if we moved and the new position is dereference-able.
     */
    public boolean moveBackVisibleCursorPositions(int numPositions) {
        return library.gtk_text_iter_backward_visible_cursor_positions(getCReference(), numPositions);
    }

    /**
     * Moves count visible lines backward, if possible.
     * <p>
     * If count would move past the start or end of the buffer, moves to the start or end of the buffer.
     * <p>
     * The return value indicates whether the iterator moved onto a dereference-able position; if the iterator didn't
     * move, or moved onto the end iterator, then FALSE is returned. If count is 0, the function does nothing and
     * returns FALSE. If count is negative, moves forward by abs(count) lines.
     *
     * @param numVisLines Number of lines to move backward.
     * @return Whether iter moved and is dereference-able.
     */
    public boolean moveBackVisibleLines(int numVisLines) {
        return library.gtk_text_iter_backward_visible_lines(getCReference(), numVisLines);
    }

    /**
     * Moves count lines backward, if possible.
     * <p>
     * If count would move past the start or end of the buffer, moves to the start or end of the buffer.
     * <p>
     * The return value indicates whether the iterator moved onto a dereference-able position; if the iterator didn't
     * move, or moved onto the end iterator, then FALSE is returned. If count is 0, the function does nothing and
     * returns FALSE. If count is negative, moves forward by abs(count) lines.
     *
     * @param numLines Number of lines to move backward.
     * @return Whether iter moved and is dereference-able.
     */
    public boolean moveBackwardLines(int numLines) {
        return library.gtk_text_iter_backward_lines(getCReference(), numLines);
    }

    /**
     * Moves iter to the start of the previous line.
     * <p>
     * Returns TRUE if iter could be moved; i.e. if iter was at character offset 0, this function returns FALSE.
     * Therefore, if iter was already on line 0, but not at the start of the line, iter is snapped to the start of the
     * line and the function returns TRUE. (Note that this implies that in a loop calling this function, the line number
     * may not change on every iteration, if your first iteration is on line 0.)
     *
     * @return Whether iter moved.
     */
    public boolean moveBackwardOneLine() {
        return library.gtk_text_iter_backward_line(getCReference());
    }

    /**
     * Moves backward to the previous visible word start.
     * <p>
     * If iter is currently on a word start, moves backward to the next one after that.
     * <p>
     * Word breaks are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveBackwardToPreviousVisibleWordStart() {
        return library.gtk_text_iter_backward_visible_word_start(getCReference());
    }

    /**
     * Calls gtk_text_iter_backward_visible_word_start() up to count times.
     *
     * @param count Number of times to move.
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveBackwardToPreviousVisibleWordStarts(int count) {
        return library.gtk_text_iter_backward_visible_word_starts(getCReference(), count);
    }

    /**
     * Moves backward to the previous word start.
     * <p>
     * If iter is currently on a word start, moves backward to the next one after that.
     * <p>
     * Word breaks are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveBackwardToPreviousWordStart() {
        return library.gtk_text_iter_backward_word_start(getCReference());
    }

    /**
     * Calls gtk_text_iter_backward_word_start() up to count times.
     *
     * @param count Number of times to move.
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveBackwardToPreviousWordStarts(int count) {
        return library.gtk_text_iter_backward_word_starts(getCReference(), count);
    }

    /**
     * Moves iter backward, calling pred on each character.
     * <p>
     * If pred returns TRUE, this method returns TRUE and stops scanning.
     * If pred never returns TRUE, iter is set to limit if limit is non-NULL, otherwise to the beginning iterator.
     *
     * @param pred     A function to be called on each character.
     * @param userData User data for pred.
     *                 <p>
     *                 The argument can be NULL.
     * @param limit    Search limit.
     *                 <p>
     *                 The argument can be NULL.
     * @return Whether a match was found.
     */
    public boolean moveBackwardUntilPredicate(GtkTextCharPredicate pred, Pointer userData, GtkTextIter limit) {
        Pointer limitPointer = limit != null ? limit.getCReference() : Pointer.NULL;
        return library.gtk_text_iter_backward_find_char(getCReference(), pred, userData, limitPointer);
    }

    /**
     * Moves count characters if possible.
     * <p>
     * If count would move past the start or end of the buffer, moves to the start or end of the buffer.
     * <p>
     * The return value indicates whether the new position of iter is different from its original position, and
     * de-referenceable (the last iterator in the buffer is not de-referenceable).
     * If count is 0, the function does nothing and returns FALSE.
     *
     * @param numCharacters Number of characters to move, may be negative.
     * @return Whether iter moved and is de-referenceable.
     */
    public boolean moveForwardCharacters(int numCharacters) {
        return library.gtk_text_iter_forward_chars(getCReference(), numCharacters);
    }

    /**
     * Moves up to count cursor positions.
     * <p>
     * See gtk_text_iter_forward_cursor_position() for details.
     *
     * @param numCursorPositions Number of positions to move.
     * @return TRUE if we moved and the new position is de-referenceable.
     */
    public boolean moveForwardCursorPositions(int numCursorPositions) {
        return library.gtk_text_iter_forward_cursor_positions(getCReference(), numCursorPositions);
    }

    /**
     * Moves count lines forward, if possible.
     * <p>
     * If count would move past the start or end of the buffer, moves to the start or end of the buffer.
     * <p>
     * The return value indicates whether the iterator moved onto a de-referenceable position; if the iterator didn't
     * move, or moved onto the end iterator, then FALSE is returned. If count is 0, the function does nothing and
     * returns FALSE. If count is negative, moves backward by 0 - count lines.
     *
     * @param numLines Number of lines to move forward.
     * @return Whether iter moved and is de-referenceable.
     */
    public boolean moveForwardLines(int numLines) {
        return library.gtk_text_iter_forward_lines(getCReference(), numLines);
    }

    /**
     * Moves iter forward by one character offset.
     * <p>
     * Note that images embedded in the buffer occupy 1 character slot, so this function may actually move onto an image
     * instead of a character, if you have images in your buffer. If iter is the end iterator or one character before
     * it, iter will now point at the end iterator, and this function returns FALSE for convenience when writing loops.
     *
     * @return Whether iter moved and is de-referenceable.
     */
    public boolean moveForwardOneCharacter() {
        return library.gtk_text_iter_forward_char(getCReference());
    }

    /**
     * Moves iter forward by a single cursor position.
     * <p>
     * Cursor positions are (unsurprisingly) positions where the cursor can appear. Perhaps surprisingly, there may not
     * be a cursor position between all characters. The most common example for European languages would be a
     * carriage return/newline sequence.
     * <p>
     * For some Unicode characters, the equivalent of say the letter "a" with an accent mark will be represented as
     * two characters, first the letter then a "combining mark" that causes the accent to be rendered; so the cursor
     * can't go between those two characters.
     * <p>
     * See also the PangoLogAttr struct and the pango_break() function.
     *
     * @return TRUE if we moved and the new position is de-referenceable.
     */
    public boolean moveForwardOneCursorPosition() {
        return library.gtk_text_iter_forward_cursor_position(getCReference());
    }

    /**
     * Moves iter to the start of the next line.
     * <p>
     * If the iter is already on the last line of the buffer, moves the iter to the end of the current line.
     * If after the operation, the iter is at the end of the buffer and not de-referenceable, returns FALSE. Otherwise,
     * returns TRUE.
     *
     * @return Whether iter can be de-referenced.
     */
    public boolean moveForwardOneLine() {
        return library.gtk_text_iter_forward_line(getCReference());
    }

    /**
     * Moves iter forward to the "end iterator", which points one past the last valid character in the buffer.
     * <p>
     * gtk_text_iter_get_char() called on the end iterator returns 0, which is convenient for writing loops.
     */
    public void moveForwardToEndIterator() {
        library.gtk_text_iter_forward_to_end(getCReference());
    }

    /**
     * Moves the iterator to point to the paragraph delimiter characters.
     * <p>
     * The possible characters are either a newline, a carriage return, a carriage return/newline in sequence,
     * or the Unicode paragraph separator character.
     * <p>
     * If the iterator is already at the paragraph delimiter characters, moves to the paragraph delimiter characters
     * for the next line. If iter is on the last line in the buffer, which does not end in paragraph delimiters,
     * moves to the end iterator (end of the last line), and returns FALSE.
     *
     * @return TRUE if we moved and the new location is not the end iterator.
     */
    public boolean moveForwardToLineEnd() {
        return library.gtk_text_iter_forward_to_line_end(getCReference());
    }

    /**
     * Moves iter forward to the next visible cursor position.
     * <p>
     * See gtk_text_iter_forward_cursor_position() for details.
     *
     * @return TRUE if we moved and the new position is de-referenceable.
     */
    public boolean moveForwardToNextVisibleCursorPosition() {
        return library.gtk_text_iter_forward_visible_cursor_position(getCReference());
    }

    /**
     * Moves iter to the start of the next visible line.
     * <p>
     * Returns TRUE if there was a next line to move to, and FALSE if iter was simply moved to the end of the buffer
     * and is now not de-referenceable, or if iter was already at the end of the buffer.
     *
     * @return Whether iter can be de-referenced.
     */
    public boolean moveForwardToNextVisibleLine() {
        return library.gtk_text_iter_forward_visible_line(getCReference());
    }

    /**
     * Moves forward to the next visible word end.
     * <p>
     * If iter is currently on a word end, moves forward to the next one after that.
     * <p>
     * Word breaks are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveForwardToNextVisibleWordEnd() {
        return library.gtk_text_iter_forward_visible_word_end(getCReference());
    }

    /**
     * Calls gtk_text_iter_forward_visible_word_end() up to count times.
     *
     * @param numWords Number of times to move.
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveForwardToNextVisibleWordStarts(int numWords) {
        return library.gtk_text_iter_forward_visible_word_ends(getCReference(), numWords);
    }

    /**
     * Moves forward to the next word end.
     * <p>
     * If iter is currently on a word end, moves forward to the next one after that.
     * <p>
     * Word breaks are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveForwardToNextWordEnd() {
        return library.gtk_text_iter_forward_word_end(getCReference());
    }

    /**
     * Calls gtk_text_iter_forward_word_end() up to count times.
     *
     * @param numWords Number of times to move.
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveForwardToNextWordEnds(int numWords) {
        return library.gtk_text_iter_forward_word_ends(getCReference(), numWords);
    }

    /**
     * Moves forward to the next sentence end.
     * <p>
     * If iter is at the end of a sentence, moves to the next end of sentence.
     * <p>
     * Sentence boundaries are determined by Pango and should be correct for nearly any language.
     *
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveForwardToSentenceEnd() {
        return library.gtk_text_iter_forward_sentence_end(getCReference());
    }

    /**
     * Calls gtk_text_iter_forward_sentence_end() count times.
     * <p>
     * If count is negative, moves backward instead of forward.
     *
     * @param numOfSentenceEndsToMoveForward Number of sentences to move.
     * @return TRUE if iter moved and is not the end iterator.
     */
    public boolean moveForwardToSentenceEndMultiple(int numOfSentenceEndsToMoveForward) {
        return library.gtk_text_iter_forward_sentence_ends(getCReference(), numOfSentenceEndsToMoveForward);
    }

    /**
     * Moves forward to the next toggle (on or off) of the tag, or to the next toggle of any tag if tag is NULL.
     * <p>
     * If no matching tag toggles are found, returns FALSE, otherwise TRUE. Does not return toggles located at iter,
     * only toggles after iter. Sets iter to the location of the toggle, or to the end of the buffer if no toggle is
     * found.
     *
     * @param tag A GtkTextTag
     *            <p>
     *            The argument can be NULL.
     * @return Whether we found a tag toggle after iter.
     */
    public boolean moveForwardToTagToggle(GtkTextTag tag) {
        if (tag != null) {
            return library.gtk_text_iter_forward_to_tag_toggle(getCReference(), tag.getCReference());
        } else {
            return library.gtk_text_iter_forward_to_tag_toggle(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Advances iter, calling pred on each character.
     * <p>
     * If pred returns TRUE, returns TRUE and stops scanning. If pred never returns TRUE, iter is set to limit if limit
     * is non-NULL, otherwise to the end iterator.
     *
     * @param pred     A function to be called on each character.
     * @param userData User data for pred.
     *                 <p>
     *                 The argument can be NULL.
     * @param limit    Search limit.
     *                 <p>
     *                 The argument can be NULL.
     * @return Whether a match was found.
     */
    public boolean moveForwardUntilPredicate(GtkTextCharPredicate pred, Pointer userData, GtkTextIter limit) {
        Pointer limitPointer = limit != null ? limit.getCReference() : Pointer.NULL;
        return library.gtk_text_iter_forward_find_char(getCReference(), pred, userData, limitPointer);
    }

    /**
     * Moves up to count visible cursor positions.
     * <p>
     * See gtk_text_iter_forward_cursor_position() for details.
     *
     * @param numCursorPositions Number of positions to move.
     * @return TRUE if we moved and the new position is de-referenceable.
     */
    public boolean moveForwardVisibleCursorPositions(int numCursorPositions) {
        return library.gtk_text_iter_forward_visible_cursor_positions(getCReference(), numCursorPositions);
    }

    /**
     * Moves count visible lines forward, if possible.
     * <p>
     * If count would move past the start or end of the buffer, moves to the start or end of the buffer.
     * <p>
     * The return value indicates whether the iterator moved onto a de-referenceable position; if the iterator
     * didn't move, or moved onto the end iterator, then FALSE is returned. If count is 0, the function does nothing
     * and returns FALSE. If count is negative, moves backward by 0 - count lines.
     *
     * @param numVisLines Number of lines to move forward.
     * @return Whether iter moved and is de-referenceable.
     */
    public boolean moveForwardVisibleLines(int numVisLines) {
        return library.gtk_text_iter_forward_visible_lines(getCReference(), numVisLines);
    }

    /**
     * Swaps the value of this and iter if iter comes before this in the buffer.
     * <p>
     * That is, ensures that first and second are in sequence. Most text buffer functions that take a range call this
     * automatically on your behalf, so there's no real reason to call it yourself in those cases. There are some
     * exceptions, such as gtk_text_iter_in_range(), that expect a pre-sorted range.
     *
     * @param iter Another GtkTextIter
     */
    public void orderAfterIterator(GtkTextIter iter) {
        if (iter != null) {
            library.gtk_text_iter_order(getCReference(), iter.getCReference());
        }
    }

    /**
     * Searches backward for str.
     * <p>
     * Any match is returned by setting match_start to the first character of the match and match_end to the first
     * character after the match. The search will not continue past limit. Note that a search is a linear or O(n)
     * operation, so you may wish to use limit to avoid locking up your UI on large buffers.
     * <p>
     * match_start will never be set to a GtkTextIter located before iter, even if there is a possible match_end after
     * or at iter.
     *
     * @param search Search string.
     * @param flags  Bitmask of flags affecting the search.
     * @param limit  Location of last possible match_start, or NULL for start of buffer.
     *               <p>
     *               The argument can be NULL.
     * @return If defined, match start, match end
     */
    public Option<Pair<GtkTextIter, GtkTextIter>> searchBackward(String search, GtkTextSearchFlags flags, GtkTextIter limit) {
        if (search != null) {
            Pointer limitPointer = limit != null ? limit.getCReference() : Pointer.NULL;
            PointerByReference matchStart = new PointerByReference();
            PointerByReference matchEnd = new PointerByReference();
            boolean didFind = library.gtk_text_iter_backward_search(getCReference(), search, GtkTextSearchFlags.getCValueFromFlags(flags), matchStart, matchEnd, limitPointer);
            if (didFind) {
                return new Option<>(new Pair<>(new GtkTextIter(matchStart.getPointer()), new GtkTextIter(matchEnd.getPointer())));
            }
        }
        return Option.NONE;
    }

    /**
     * Searches forward for str.
     * <p>
     * Any match is returned by setting match_start to the first character of the match and match_end to the first
     * character after the match. The search will not continue past limit. Note that a search is a linear or O(n)
     * operation, so you may wish to use limit to avoid locking up your UI on large buffers.
     * <p>
     * match_start will never be set to a GtkTextIter located before iter, even if there is a possible match_end
     * after or at iter.
     *
     * @param search A search string.
     * @param flags  Flags affecting how the search is done.
     * @param limit  Location of last possible match_end, or NULL for the end of the buffer.
     *               <p>
     *               The argument can be NULL.
     * @return If defined, match start, match end
     */
    public Option<Pair<GtkTextIter, GtkTextIter>> searchForward(String search, GtkTextSearchFlags flags, GtkTextIter limit) {
        if (search != null) {
            Pointer limitPointer = limit != null ? limit.getCReference() : Pointer.NULL;
            PointerByReference matchStart = new PointerByReference();
            PointerByReference matchEnd = new PointerByReference();
            boolean didFind = library.gtk_text_iter_forward_search(getCReference(), search, GtkTextSearchFlags.getCValueFromFlags(flags), matchStart, matchEnd, limitPointer);
            if (didFind) {
                return new Option<>(new Pair<>(new GtkTextIter(matchStart.getPointer()), new GtkTextIter(matchEnd.getPointer())));
            }
        }
        return Option.NONE;
    }
}
