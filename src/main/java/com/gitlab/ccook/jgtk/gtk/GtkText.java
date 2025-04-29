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
import com.gitlab.ccook.jgtk.bitfields.GtkInputHints;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkInputPurpose;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.List;

/**
 * The GtkText widget is a single-line text entry widget.
 * <p>
 * GtkText is the common implementation of single-line text editing that is shared between GtkEntry, GtkPasswordEntry,
 * GtkSpinButton, and other widgets. In all of these, GtkText is used as the delegate for the GtkEditable
 * implementation.
 * <p>
 * A fairly large set of key bindings are supported by default. If the entered text is longer than the allocation of the
 * widget, the widget will scroll so that the cursor position is visible.
 * <p>
 * When using an entry for passwords and other sensitive information, it can be put into "password mode" using
 * gtk_text_set_visibility(). In this mode, entered text is displayed using an "invisible" character. By default,
 * GTK picks the best invisible character that is available in the current font, but it can be changed with
 * gtk_text_set_invisible_char().
 * <p>
 * If you are looking to add icons or progress display in an entry, look at GtkEntry. There other alternatives for more
 * specialized use cases, such as GtkSearchEntry.
 * <p>
 * If you need multi-line editable text, look at GtkTextView.
 */
@SuppressWarnings("unchecked")
public class GtkText extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkEditable {
    private static final GtkTextLibrary library = new GtkTextLibrary();

    /**
     * Creates a new GtkText.
     */
    public GtkText() {
        this(library.gtk_text_new());
    }

    public GtkText(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkText with initial text
     *
     * @param text initial text of GtkText
     */
    public GtkText(String text) {
        this(new GtkEntryBuffer(text));
    }

    /**
     * Creates a new GtkText with the specified text buffer.
     *
     * @param buffer The buffer to use for the new GtkText.
     */
    public GtkText(GtkEntryBuffer buffer) {
        this(library.gtk_text_new_with_buffer(buffer.getCReference()));
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
     * Returns whether pressing Enter will activate the default widget for the window containing self.
     *
     * @return TRUE if the GtkText will activate the default widget.
     */
    public boolean doesEnterActivateDefault() {
        return library.gtk_text_get_activates_default(getCReference());
    }

    /**
     * Gets whether text is overwritten when typing in the GtkText.
     *
     * @return Whether the text is overwritten when typing.
     */
    public boolean doesOverwrite() {
        return library.gtk_text_get_overwrite_mode(getCReference());
    }

    /**
     * Returns whether the GtkText will grow and shrink with the content.
     *
     * @return TRUE if self will propagate the text width.
     */
    public boolean doesPropagateTextWidth() {
        return library.gtk_text_get_propagate_text_width(getCReference());
    }

    /**
     * Returns whether Emoji completion is enabled for this GtkText widget.
     *
     * @return TRUE if Emoji completion is enabled.
     */
    public boolean doesSuggestEmojiReplacements() {
        return library.gtk_text_get_enable_emoji_completion(getCReference());
    }

    /**
     * Returns whether the GtkText will truncate multi-line text that is pasted into the widget.
     *
     * @return TRUE if self will truncate multi-line text.
     */
    public boolean doesTruncateMultiline() {
        return library.gtk_text_get_truncate_multiline(getCReference());
    }

    /**
     * Gets the attribute list that was set on the GtkText.
     *
     * @return The attribute list, if defined
     */
    public Option<PangoAttrList> getAttributes() {
        Option<Pointer> p = new Option<>(library.gtk_text_get_attributes(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoAttrList(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets attributes that are applied to the text.
     *
     * @param list A PangoAttrList
     *             <p>
     *             The argument can be NULL.
     */
    public void setAttributes(PangoAttrList list) {
        library.gtk_text_set_attributes(getCReference(), pointerOrNull(list));
    }

    /**
     * Get the GtkEntryBuffer object which holds the text for this widget.
     *
     * @return A GtkEntryBuffer object.
     */
    public GtkEntryBuffer getBuffer() {
        return (GtkEntryBuffer) JGTKObject.newObjectFromType(library.gtk_text_get_buffer(getCReference()), GtkEntryBuffer.class);
    }

    /**
     * Set the GtkEntryBuffer object which holds the text for this widget.
     *
     * @param buf A GtkEntryBuffer
     */
    public void setBuffer(GtkEntryBuffer buf) {
        if (buf != null) {
            library.gtk_text_set_buffer(getCReference(), buf.getCReference());
        }
    }

    /**
     * Gets the menu model for extra items in the context menu.
     *
     * @return The menu model, if defined
     */
    public Option<GMenuModel> getExtraMenu() {
        Option<Pointer> p = new Option<>(library.gtk_text_get_extra_menu(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GMenuModel(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a menu model to add when constructing the context menu for self.
     *
     * @param m A GMenuModel
     *          <p>
     *          The argument can be NULL.
     */
    public void setExtraMenu(GMenuModel m) {
        library.gtk_text_set_extra_menu(getCReference(), pointerOrNull(m));
    }

    /**
     * Gets the input hints of the GtkText.
     *
     * @return the input hints of the GtkText.
     */
    public List<GtkInputHints> getInputHints() {
        return GtkInputHints.getHintsFromCValue(library.gtk_text_get_input_hints(getCReference()));
    }

    /**
     * Sets input hints that allow input methods to fine-tune their behavior.
     *
     * @param hints The hints
     */
    public void setInputHints(GtkInputHints... hints) {
        if (hints != null) {
            library.gtk_text_set_input_hints(getCReference(), GtkInputHints.getCValueFromHints(hints));
        }
    }

    /**
     * Gets the input purpose of the GtkText.
     *
     * @return the input purpose of the GtkText.
     */
    public GtkInputPurpose getInputPurpose() {
        return GtkInputPurpose.getPurposeFromCValue(library.gtk_text_get_input_purpose(getCReference()));
    }

    /**
     * Sets the input purpose of the GtkText.
     * <p>
     * This can be used by on-screen keyboards and other input methods to adjust their behavior.
     *
     * @param purpose The purpose.
     */
    public void setInputPurpose(GtkInputPurpose purpose) {
        library.gtk_text_set_input_purpose(getCReference(), GtkInputPurpose.getCValueFromPurpose(purpose));
    }

    /**
     * Retrieves the maximum allowed length of the text in self.
     * <p>
     * See gtk_text_set_max_length().
     * <p>
     * This is equivalent to getting self's GtkEntryBuffer and calling gtk_entry_buffer_get_max_length() on it.
     *
     * @return The maximum allowed number of characters in GtkText, or NONE if there is no maximum.
     */
    public Option<Integer> getMaxLength() {
        int max = library.gtk_text_get_max_length(getCReference());
        if (max > 0) {
            return new Option<>(max);
        }
        return Option.NONE;
    }

    /**
     * Sets the maximum allowed length of the contents of the widget.
     * <p>
     * If the current contents are longer than the given length, then they will be truncated to fit.
     * <p>
     * This is equivalent to getting self's GtkEntryBuffer and calling gtk_entry_buffer_set_max_length() on it.
     *
     * @param max The maximum length of the GtkText, or 0 for no maximum. (other than the maximum length of entries.)
     *            The value passed in will be clamped to the range 0-65536.
     */
    public void setMaxLength(int max) {
        library.gtk_text_set_max_length(getCReference(), Math.min(Math.max(0, max), 65536));
    }

    /**
     * Retrieves the character displayed when visibility is set to false.
     * <p>
     * Note that GTK does not compute this value unless it needs it, so the value returned by this function is not very
     * useful unless it has been explicitly set with gtk_text_set_invisible_char().
     *
     * @return The current invisible char, if defined; or NONE, if text does not show invisible text at all.
     */
    public Option<Character> getPasswordCharacter() {
        char c = library.gtk_text_get_invisible_char(getCReference());
        if (c != 0) {
            return new Option<>(c);
        }
        return Option.NONE;
    }

    /**
     * Sets the character to use when in "password mode".
     * <p>
     * By default, GTK picks the best invisible char available in the current font.
     * If you set the invisible char to 0, then the user will get no feedback at all; there will be no text on the
     * screen as they type.
     *
     * @param c A Unicode character.
     */
    public void setPasswordCharacter(char c) {
        library.gtk_text_set_invisible_char(getCReference(), c);
    }

    /**
     * Retrieves the text that will be displayed when self is empty and unfocused
     * <p>
     * If no placeholder text has been set, NONE will be returned.
     *
     * @return The placeholder text, if defined
     */
    public Option<String> getPlaceholderText() {
        return new Option<>(library.gtk_text_get_placeholder_text(getCReference()));
    }

    /**
     * Sets text to be displayed in self when it is empty.
     * <p>
     * This can be used to give a visual hint of the expected contents of the GtkText.
     *
     * @param placeholder A string to be displayed when self is empty and unfocused.
     *                    <p>
     *                    The argument can be NULL.
     */
    public void setPlaceholderText(String placeholder) {
        library.gtk_text_set_placeholder_text(getCReference(), placeholder);
    }

    /**
     * Gets the tab-stops that were set on the GtkText.
     *
     * @return The tab-stops, if defined.
     */
    public Option<PangoTabArray> getTabStops() {
        Option<Pointer> p = new Option<>(library.gtk_text_get_tabs(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoTabArray(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets tab-stops that are applied to the text.
     *
     * @param arr A PangoTabArray
     *            <p>
     *            The argument can be NULL.
     */
    public void setTabStops(PangoTabArray arr) {
        library.gtk_text_set_tabs(getCReference(), pointerOrNull(arr));
    }

    /**
     * Retrieves the current length of the text in self.
     * <p>
     * This is equivalent to getting self's GtkEntryBuffer and calling gtk_entry_buffer_get_length() on it.
     *
     * @return The current number of characters in GtkText
     */
    public int getTextLength() {
        return library.gtk_text_get_text_length(getCReference());
    }

    /**
     * Causes self to have keyboard focus.
     * <p>
     * It behaves like gtk_widget_grab_focus(), except that it doesn't select the contents of self.
     * You only want to call this on some special entries which the user usually doesn't want to replace all text in,
     * such as search-as-you-type entries.
     *
     * @return TRUE if focus is now inside self.
     */
    public boolean grabFocusWithoutSelect() {
        return library.gtk_text_grab_focus_without_selecting(getCReference());
    }

    /**
     * Retrieves whether the text in self is visible.
     *
     * @return TRUE if the text is currently visible.
     */
    public boolean isTextVisible() {
        return library.gtk_text_get_visibility(getCReference());
    }

    /**
     * Sets whether the contents of the GtkText are visible or not.
     * <p>
     * When visibility is set to FALSE, characters are displayed as the invisible char, and will also appear that way
     * when the text in the widget is copied to the clipboard.
     * <p>
     * By default, GTK picks the best invisible character available in the current font, but it can be changed with
     * gtk_text_set_invisible_char().
     * <p>
     * Note that you probably want to set GtkText:input-purpose to GTK_INPUT_PURPOSE_PASSWORD or GTK_INPUT_PURPOSE_PIN
     * to inform input methods about the purpose of this self, in addition to setting visibility to FALSE.
     *
     * @param isVis TRUE if text is visible
     */
    public void setTextVisible(boolean isVis) {
        library.gtk_text_set_visibility(getCReference(), isVis);
    }

    /**
     * Unsets the invisible char.
     * <p>
     * After calling this, the default invisible char is used again.
     */
    public void resetPasswordCharacter() {
        library.gtk_text_unset_invisible_char(getCReference());
    }

    /**
     * If activates is TRUE, pressing Enter will activate the default widget for the window containing self.
     * <p>
     * This usually means that the dialog containing the GtkText will be closed, since the default widget is usually one
     * of the dialog buttons.
     *
     * @param doesActivate TRUE to activate window's default widget on Enter keypress.
     */
    public void shouldEnterActivateDefault(boolean doesActivate) {
        library.gtk_text_set_activates_default(getCReference(), doesActivate);
    }

    /**
     * Sets whether the text is overwritten when typing in the GtkText.
     *
     * @param doesOverwrite whether the text is overwritten when typing in the GtkText.
     */
    public void shouldOverwrite(boolean doesOverwrite) {
        library.gtk_text_set_overwrite_mode(getCReference(), doesOverwrite);
    }

    /**
     * Sets whether the GtkText should grow and shrink with the content.
     *
     * @param doesPropagate TRUE to propagate the text width.
     */
    public void shouldPropagateTextWidth(boolean doesPropagate) {
        library.gtk_text_set_propagate_text_width(getCReference(), doesPropagate);
    }

    /**
     * Sets whether Emoji completion is enabled.
     * <p>
     * If it is, typing ':', followed by a recognized keyword, will pop up a window with suggested Emojis matching the
     * keyword.
     *
     * @param doesSuggest TRUE to enable Emoji completion.
     */
    public void shouldSuggestEmojiReplacements(boolean doesSuggest) {
        library.gtk_text_set_enable_emoji_completion(getCReference(), doesSuggest);
    }

    /**
     * Sets whether the GtkText should truncate multi-line text that is pasted into the widget.
     *
     * @param doesTruncate TRUE to truncate multi-line text.
     */
    public void shouldTruncateMultiline(boolean doesTruncate) {
        library.gtk_text_set_truncate_multiline(getCReference(), doesTruncate);
    }

    public static final class Signals extends GtkWidget.Signals {

        /**
         * Emitted when the user hits the Enter key.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        /**
         * Emitted when the user hits the Backspace key.
         */
        public static final Signals BACKSPACE = new Signals("backspace");

        /**
         * Emitted to copy the selection to the clipboard.
         */
        public static final Signals COPY_CLIPBOARD = new Signals("copy-clipboard");

        /**
         * Emitted to cut the selection to the clipboard.
         */
        public static final Signals CUT_CLIPBOARD = new Signals("cut-clipboard");

        /**
         * Emitted when the user initiates a text deletion.
         */
        public static final Signals DELETE_FROM_CURSOR = new Signals("delete-from-cursor");

        /**
         * Emitted when the user initiates the insertion of a fixed string at the cursor.
         */
        public static final Signals INSERT_AT_CURSOR = new Signals("insert-at-cursor");

        /**
         * Emitted to present the Emoji chooser for the widget.
         */
        public static final Signals INSERT_EMOJI = new Signals("insert-emoji");

        /**
         * Emitted when the user initiates a cursor movement.
         */
        public static final Signals MOVE_CURSOR = new Signals("move-cursor");

        /**
         * Emitted to paste the contents of the clipboard.
         */
        public static final Signals PASTE_CLIPBOARD = new Signals("paste-clipboard");

        /**
         * Emitted when the pre-edit text changes.
         */
        public static final Signals PRE_EDIT_CHANGED = new Signals("preedit-changed");

        /**
         * Emitted to toggle the overwrite mode of the GtkText.
         */
        public static final Signals TOGGLE_OVERWRITE = new Signals("toggle-overwrite");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkTextLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns whether pressing Enter will activate the default widget for the window containing self.
         * <p>
         * See gtk_text_set_activates_default().
         *
         * @param self self
         * @return TRUE if the GtkText will activate the default widget.
         */
        public native boolean gtk_text_get_activates_default(Pointer self);

        /**
         * Gets the attribute list that was set on the GtkText.
         * <p>
         * See gtk_text_set_attributes().
         *
         * @param self self
         * @return The attribute list. Type: PangoAttrList
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_text_get_attributes(Pointer self);

        /**
         * Get the GtkEntryBuffer object which holds the text for this widget.
         *
         * @param self self
         * @return A GtkEntryBuffer object.
         */
        public native Pointer gtk_text_get_buffer(Pointer self);

        /**
         * Returns whether Emoji completion is enabled for this GtkText widget.
         *
         * @param self self
         * @return TRUE if Emoji completion is enabled.
         */
        public native boolean gtk_text_get_enable_emoji_completion(Pointer self);

        /**
         * Gets the menu model for extra items in the context menu.
         * <p>
         * See gtk_text_set_extra_menu().
         *
         * @param self self
         * @return The menu model. Type: GMenuModel
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_text_get_extra_menu(Pointer self);

        /**
         * Gets the input hints of the GtkText.
         *
         * @param self self
         * @return Additional hints that allow input methods to fine-tune their behavior.
         */
        public native int gtk_text_get_input_hints(Pointer self);

        /**
         * Gets the input purpose of the GtkText.
         *
         * @param self self
         * @return Describes primary purpose of the input widget. Type: GtkInputPurpose
         *         <p>
         *         This information is useful for on-screen keyboards and similar input methods to decide which keys
         *         should be
         *         presented to the user.
         *         <p>
         *         Note that the purpose is not meant to impose a totally strict rule about allowed characters, and does
         *         not
         *         replace input validation. It is fine for an on-screen keyboard to let the user override the character
         *         set
         *         restriction that is expressed by the purpose. The application is expected to validate the entry
         *         contents,
         *         even if it specified a purpose.
         *         <p>
         *         The difference between GTK_INPUT_PURPOSE_DIGITS and GTK_INPUT_PURPOSE_NUMBER is that the former
         *         accepts only
         *         digits while the latter also some punctuation (like commas or points, plus, minus) and "e" or "E" as
         *         in
         *         3.14E+000.
         *         <p>
         *         This enumeration may be extended in the future; input methods should interpret unknown values as
         *         "free form".
         */
        public native int gtk_text_get_input_purpose(Pointer self);

        /**
         * Retrieves the character displayed when visibility is set to false.
         * <p>
         * Note that GTK does not compute this value unless it needs it, so the value returned by this function is not
         * very useful unless it has been explicitly set with gtk_text_set_invisible_char().
         *
         * @param self self
         * @return The current invisible char, or 0, if text does not show invisible text at all.
         */
        public native char gtk_text_get_invisible_char(Pointer self);

        /**
         * Retrieves the maximum allowed length of the text in self.
         * <p>
         * See gtk_text_set_max_length().
         * <p>
         * This is equivalent to getting self's GtkEntryBuffer and calling gtk_entry_buffer_get_max_length() on it.
         *
         * @param self self
         * @return The maximum allowed number of characters in GtkText, or 0 if there is no maximum.
         */
        public native int gtk_text_get_max_length(Pointer self);

        /**
         * Gets whether text is overwritten when typing in the GtkText.
         * <p>
         * See gtk_text_set_overwrite_mode().
         *
         * @param self self
         * @return Whether the text is overwritten when typing.
         */
        public native boolean gtk_text_get_overwrite_mode(Pointer self);

        /**
         * Retrieves the text that will be displayed when self is empty and unfocused
         * <p>
         * If no placeholder text has been set, NULL will be returned.
         *
         * @param self self
         * @return The placeholder text.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_text_get_placeholder_text(Pointer self);

        /**
         * Returns whether the GtkText will grow and shrink with the content.
         *
         * @param self self
         * @return TRUE if self will propagate the text width.
         */
        public native boolean gtk_text_get_propagate_text_width(Pointer self);

        /**
         * Gets the tab-stops that were set on the GtkText.
         * <p>
         * See gtk_text_set_tabs().
         *
         * @param self self
         * @return The tab-stops. Type: PangoTabArray
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_text_get_tabs(Pointer self);

        /**
         * Retrieves the current length of the text in self.
         * <p>
         * This is equivalent to getting self's GtkEntryBuffer and calling gtk_entry_buffer_get_length() on it.
         *
         * @param self self
         * @return The current number of characters in GtkText, or 0 if there are none.
         */
        public native int gtk_text_get_text_length(Pointer self);

        /**
         * Returns whether the GtkText will truncate multi-line text that is pasted into the widget.
         *
         * @param self self
         * @return TRUE if self will truncate multi-line text.
         */
        public native boolean gtk_text_get_truncate_multiline(Pointer self);

        /**
         * Retrieves whether the text in self is visible.
         *
         * @param self self
         * @return TRUE if the text is currently visible.
         */
        public native boolean gtk_text_get_visibility(Pointer self);

        /**
         * Causes self to have keyboard focus.
         * <p>
         * It behaves like gtk_widget_grab_focus(), except that it doesn't select the contents of self. You only want to
         * call this on some special entries which the user usually doesn't want to replace all text in, such as
         * search-as-you-type entries.
         *
         * @param self se;f
         * @return TRUE if focus is now inside self.
         */
        public native boolean gtk_text_grab_focus_without_selecting(Pointer self);

        /**
         * Creates a new GtkText.
         *
         * @return A new GtkText.
         */
        public native Pointer gtk_text_new();

        /**
         * Creates a new GtkText with the specified text buffer.
         *
         * @param buffer The buffer to use for the new GtkText. Type: GtkEntryBuffer
         * @return A new GtkText
         */
        public native Pointer gtk_text_new_with_buffer(Pointer buffer);

        /**
         * If activates is TRUE, pressing Enter will activate the default widget for the window containing self.
         * <p>
         * This usually means that the dialog containing the GtkText will be closed, since the default widget is
         * usually one of the dialog buttons.
         *
         * @param self      self
         * @param activates TRUE to activate window's default widget on Enter keypress.
         */
        public native void gtk_text_set_activates_default(Pointer self, boolean activates);

        /**
         * Sets attributes that are applied to the text.
         *
         * @param self  self
         * @param attrs A PangoAttrList
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_text_set_attributes(Pointer self, Pointer attrs);

        /**
         * Set the GtkEntryBuffer object which holds the text for this widget.
         *
         * @param self   self
         * @param buffer A GtkEntryBuffer
         */
        public native void gtk_text_set_buffer(Pointer self, Pointer buffer);

        /**
         * Sets whether Emoji completion is enabled.
         * <p>
         * If it is, typing ':', followed by a recognized keyword, will pop up a window with suggested Emojis matching
         * the keyword.
         *
         * @param self                    self
         * @param enable_emoji_completion TRUE to enable Emoji completion.
         */
        public native void gtk_text_set_enable_emoji_completion(Pointer self, boolean enable_emoji_completion);

        /**
         * Sets a menu model to add when constructing the context menu for self.
         *
         * @param self  self
         * @param model A GMenuModel
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_text_set_extra_menu(Pointer self, Pointer model);

        /**
         * Sets input hints that allow input methods to fine-tune their behavior.
         *
         * @param self  self
         * @param hints The hints. Type: GtkInputHints
         */
        public native void gtk_text_set_input_hints(Pointer self, int hints);

        /**
         * Sets the input purpose of the GtkText.
         * <p>
         * This can be used by on-screen keyboards and other input methods to adjust their behaviour.
         *
         * @param self    self
         * @param purpose The purpose. Type: GtkInputPurpose
         */
        public native void gtk_text_set_input_purpose(Pointer self, int purpose);

        /**
         * Sets the character to use when in "password mode".
         * <p>
         * By default, GTK picks the best invisible char available in the current font. If you set the invisible char
         * to 0, then the user will get no feedback at all; there will be no text on the screen as they type.
         *
         * @param self self
         * @param ch   A Unicode character.
         */
        public native void gtk_text_set_invisible_char(Pointer self, char ch);

        /**
         * Sets the maximum allowed length of the contents of the widget.
         * <p>
         * If the current contents are longer than the given length, then they will be truncated to fit.
         * <p>
         * This is equivalent to getting self's GtkEntryBuffer and calling gtk_entry_buffer_set_max_length() on it.
         *
         * @param self   self
         * @param length The maximum length of the GtkText, or 0 for no maximum. (other than the maximum length of
         *               entries.) The value passed in will be clamped to the range 0-65536.
         */
        public native void gtk_text_set_max_length(Pointer self, int length);

        /**
         * Sets whether the text is overwritten when typing in the GtkText.
         *
         * @param self      self
         * @param overwrite New value.
         */
        public native void gtk_text_set_overwrite_mode(Pointer self, boolean overwrite);

        /**
         * Sets text to be displayed in self when it is empty.
         * <p>
         * This can be used to give a visual hint of the expected contents of the GtkText.
         *
         * @param self self
         * @param text A string to be displayed when self is empty and unfocused.
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_text_set_placeholder_text(Pointer self, String text);

        /**
         * Sets whether the GtkText should grow and shrink with the content.
         *
         * @param self                 self
         * @param propagate_text_width TRUE to propagate the text width.
         */
        public native void gtk_text_set_propagate_text_width(Pointer self, boolean propagate_text_width);

        /**
         * Sets tab-stops that are applied to the text.
         *
         * @param self self
         * @param tabs A PangoTabArray
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_text_set_tabs(Pointer self, Pointer tabs);

        /**
         * Sets whether the GtkText should truncate multi-line text that is pasted into the widget.
         *
         * @param self               self
         * @param truncate_multiline TRUE to truncate multi-line text.
         */
        public native void gtk_text_set_truncate_multiline(Pointer self, boolean truncate_multiline);

        /**
         * Sets whether the contents of the GtkText are visible or not.
         * <p>
         * When visibility is set to FALSE, characters are displayed as the invisible char, and will also appear that
         * way when the text in the widget is copied to the clipboard.
         * <p>
         * By default, GTK picks the best invisible character available in the current font, but it can be changed
         * with gtk_text_set_invisible_char().
         * <p>
         * Note that you probably want to set GtkText:input-purpose to GTK_INPUT_PURPOSE_PASSWORD or
         * GTK_INPUT_PURPOSE_PIN to inform input methods about the purpose of this self, in addition to
         * setting visibility to FALSE.
         *
         * @param self    self
         * @param visible TRUE if the contents of the GtkText are displayed as plaintext.
         */
        public native void gtk_text_set_visibility(Pointer self, boolean visible);

        /**
         * Unsets the invisible char.
         * <p>
         * After calling this, the default invisible char is used again.
         *
         * @param self self
         */
        public native void gtk_text_unset_invisible_char(Pointer self);

    }
}
