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
import com.gitlab.ccook.jgtk.enums.GdkDragAction;
import com.gitlab.ccook.jgtk.enums.GtkEntryIconPosition;
import com.gitlab.ccook.jgtk.enums.GtkImageType;
import com.gitlab.ccook.jgtk.enums.GtkInputPurpose;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkCellEditable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.List;

@SuppressWarnings({"unchecked", "DeprecatedIsStillUsed"})
public class GtkEntry extends GtkWidget implements GtkAccessible, GtkBuildable, GtkCellEditable, GtkConstraintTarget, GtkEditable {

    private static final GtkEntryLibrary library = new GtkEntryLibrary();

    public GtkEntry(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new entry with placeholder text
     *
     * @param placeholderText Placeholder text
     */
    public GtkEntry(String placeholderText) {
        this();
        setPlaceholderText(placeholderText);
    }

    /**
     * Creates a new entry.
     */
    public GtkEntry() {
        super(library.gtk_entry_new());
    }

    /**
     * Creates a new entry with the specified text buffer.
     *
     * @param buffer The buffer to use for the new GtkEntry.
     */
    public GtkEntry(GtkEntryBuffer buffer) {
        super(library.gtk_entry_new_with_buffer(buffer.getCReference()));
    }

    /**
     * Returns whether the icon is can be activated.
     *
     * @param iconPosition Icon position to check
     * @return TRUE if the icon can be activated.
     */
    public boolean canIconBeActivated(GtkEntryIconPosition iconPosition) {
        if (iconPosition != null) {
            return library.gtk_entry_get_icon_activatable(getCReference(), GtkEntryIconPosition.getCValue(iconPosition));
        }
        return false;
    }

    /**
     * Gets whether text is overwritten when typing in the GtkEntry.
     *
     * @return Whether the text is overwritten when typing.
     */
    public boolean canOverwrite() {
        return library.gtk_entry_get_overwrite_mode(getCReference());
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
     * Returns Whether to activate the default widget when Enter is pressed.
     *
     * @return Whether to activate the default widget when Enter is pressed.
     */
    public boolean doesEnterActivateDefault() {
        return library.gtk_entry_get_activates_default(getCReference());
    }

    /**
     * Sets whether the text is overwritten when typing in the GtkEntry.
     *
     * @param canOverwrite New value.
     */
    public void doesOverwrite(boolean canOverwrite) {
        library.gtk_entry_set_overwrite_mode(getCReference(), canOverwrite);
    }

    /**
     * Gets the attribute list of the GtkEntry.
     *
     * @return The attribute list.
     */
    public Option<PangoAttrList> getAttributes() {
        Option<Pointer> p = new Option<>(library.gtk_entry_get_attributes(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoAttrList(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a PangoAttrList.
     * <p>
     * The attributes in the list are applied to the entry text.
     * <p>
     * Since the attributes will be applied to text that changes as the user types, it makes most sense to use
     * attributes with unlimited extent.
     *
     * @param list A PangoAttrList
     */
    public void setAttributes(PangoAttrList list) {
        if (list != null) {
            library.gtk_entry_set_attributes(getCReference(), list.getCReference());
        }
    }

    /**
     * Get the GtkEntryBuffer object which holds the text for this widget.
     *
     * @return A GtkEntryBuffer object.
     */
    public GtkEntryBuffer getBuffer() {
        return new GtkEntryBuffer(library.gtk_entry_get_buffer(getCReference()));
    }

    /**
     * Set the GtkEntryBuffer object which holds the text for this widget.
     *
     * @param b A GtkEntryBuffer
     */
    public void setBuffer(GtkEntryBuffer b) {
        if (b != null) {
            library.gtk_entry_set_buffer(getCReference(), b.getCReference());
        }
    }

    /**
     * Returns the auxiliary completion object currently in use by entry.
     *
     * @return The auxiliary completion object currently in use by entry.
     * @deprecated Deprecated since: 4.10. GtkEntryCompletion will be removed in GTK 5.
     */
    @Deprecated
    public Option<GtkEntryCompletion> getCompletion() {
        Option<Pointer> p = new Option<>(library.gtk_entry_get_completion(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkEntryCompletion(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets completion to be the auxiliary completion object to use with entry.
     * <p>
     * All further configuration of the completion mechanism is done on completion using the GtkEntryCompletion API.
     * Completion is disabled if completion is set to NULL.
     *
     * @param c The GtkEntryCompletion
     * @deprecated Deprecated since: 4.10. GtkEntryCompletion will be removed in GTK 5.
     */
    @Deprecated
    public void setCompletion(GtkEntryCompletion c) {
        library.gtk_entry_set_completion(getCReference(), pointerOrNull(c));
    }

    /**
     * Gets the menu model set with gtk_entry_set_extra_menu().
     *
     * @return The menu model.
     */
    public Option<GMenuModel> getExtraMenu() {
        Option<Pointer> p = new Option<>(library.gtk_entry_get_extra_menu(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GMenuModel(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a menu model to add when constructing the context menu for entry.
     *
     * @param m A GMenuModel
     *          <p>
     *          The argument can be NULL.
     */
    public void setExtraMenu(GMenuModel m) {
        library.gtk_entry_set_extra_menu(getCReference(), pointerOrNull(m));
    }

    /**
     * Retrieves the GIcon used for the icon.
     * <p>
     * NONE will be returned if there is no icon or if the icon was set by some other method
     * (e.g., by GdkPaintable or icon name).
     *
     * @param pos Icon position for which to grab
     * @return the GIcon requested, or NONE
     */
    public Option<GIcon> getGIcon(GtkEntryIconPosition pos) {
        if (pos != null) {
            Option<Pointer> p = new Option<>(library.gtk_entry_get_icon_gicon(getCReference(), GtkEntryIconPosition.getCValue(pos)));
            if (p.isDefined()) {
                return new Option<>(new GIcon(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the horizontal alignment, from 0 (left) to 1 (right).
     * <p>
     * Reversed for RTL layouts.
     *
     * @return The horizontal alignment, from 0 (left) to 1 (right).
     */
    public float getHorizontalAlignment() {
        return library.gtk_entry_get_alignment(getCReference());
    }

    /**
     * Gets the area where entry's icon at icon_pos is drawn.
     * <p>
     * This function is useful when drawing something to the entry in a draw callback.
     * <p>
     * If the entry is not realized or has no icon at the given position, icon_area is filled with zeros.
     * Otherwise, icon_area will be filled with the icon's allocation, relative to entry's allocation.
     *
     * @param iconPosition Icon position for which we'll get the area
     * @return Area where the icon is drawn
     */
    public GdkRectangle getIconArea(GtkEntryIconPosition iconPosition) {
        GdkRectangle.GdkRectangleStruct.ByReference ref2 = new GdkRectangle.GdkRectangleStruct.ByReference();
        library.gtk_entry_get_icon_area(getCReference(), GtkEntryIconPosition.getCValue(iconPosition), ref2.getPointer());
        return new GdkRectangle(ref2);
    }

    /**
     * Finds the icon at the given position and return its index.
     * <p>
     * The position's coordinates are relative to the entry's top left corner. If x, y doesn't lie inside an icon,
     * NONE is returned. This function is intended for use in a GtkWidget::query-tooltip signal handler.
     *
     * @param x The x coordinate of the position to find, relative to entry.
     * @param y The y coordinate of the position to find, relative to entry.
     * @return The index of the icon at the given position, or NONE
     */
    public Option<Integer> getIconAtPosition(int x, int y) {
        int pos = library.gtk_entry_get_icon_at_pos(getCReference(), x, y);
        if (pos >= 0) {
            return new Option<>(pos);
        }
        return Option.NONE;
    }

    /**
     * Returns the index of the icon which is the source of the current DND operation, or NONE.
     *
     * @return Index of the icon which is the source of the current DND operation, or NONE.
     */
    public Option<Integer> getIconDragSourceIndex() {
        int index = library.gtk_entry_get_current_icon_drag_source(getCReference());
        if (index >= 0) {
            return new Option<>(index);
        }
        return Option.NONE;
    }

    /**
     * Retrieves the icon name used for the icon.
     * <p>
     * NONE is returned if there is no icon or if the icon was set by some other method (e.g., by GdkPaintable or
     * gicon).
     *
     * @param pos Icon position for which we want the name
     * @return the requested icon's name
     */
    public Option<String> getIconName(GtkEntryIconPosition pos) {
        if (pos != null) {
            return new Option<>(library.gtk_entry_get_icon_name(getCReference(), GtkEntryIconPosition.getCValue(pos)));
        }
        return Option.NONE;
    }

    /**
     * Gets the type of representation being used by the icon to store image data.
     * <p>
     * If the icon has no image data, the return value will be GTK_IMAGE_EMPTY.
     *
     * @param pos Icon position for which to get the icon storage type
     * @return The type of icon
     */
    public GtkImageType getIconStorageType(GtkEntryIconPosition pos) {
        if (pos != null) {
            return GtkImageType.getTypeFromCValue(library.gtk_entry_get_icon_storage_type(getCReference(), GtkEntryIconPosition.getCValue(pos)));
        }
        return GtkImageType.GTK_IMAGE_EMPTY;
    }

    /**
     * Gets the input hints (additional hints that allow input methods to fine-tune their behavior)
     * of this GtkEntry.
     *
     * @return The input hints.
     */
    public List<GtkInputHints> getInputHints() {
        return GtkInputHints.getHintsFromCValue(library.gtk_entry_get_input_hints(getCReference()));
    }

    /**
     * Set additional hints which allow input methods to fine-tune their behavior.
     *
     * @param hints The hints.
     */
    public void setInputHints(GtkInputHints... hints) {
        if (hints != null) {
            library.gtk_entry_set_input_hints(getCReference(), GtkInputHints.getCValueFromHints(hints));
        }
    }

    /**
     * Gets the input purpose (a property can be used by on-screen keyboards and other input methods to adjust their
     * behavior)
     * of the GtkEntry.
     *
     * @return The input purpose.
     */
    public GtkInputPurpose getInputPurpose() {
        return GtkInputPurpose.getPurposeFromCValue(library.gtk_entry_get_input_purpose(getCReference()));
    }

    /**
     * Sets the input purpose which can be used by input methods to adjust their behavior.
     *
     * @param purpose The purpose.
     */
    public void setInputPurpose(GtkInputPurpose purpose) {
        if (purpose != null) {
            library.gtk_entry_set_input_purpose(getCReference(), GtkInputPurpose.getCValueFromPurpose(purpose));
        }
    }

    /**
     * Retrieves the maximum allowed length of the text in entry.
     *
     * @return The maximum allowed number of characters in GtkEntry, or NONE if there is no maximum.
     */
    public Option<Integer> getMaxLength() {
        int max = library.gtk_entry_get_max_length(getCReference());
        if (max > 0) {
            return new Option<>(max);
        }
        return Option.NONE;
    }

    /**
     * Sets the maximum allowed length of the contents of the widget.
     * <p>
     * If the current contents are longer than the given length, then they will be truncated to fit.
     * The length is in characters.
     * <p>
     * This is equivalent to getting entry's GtkEntryBuffer and calling gtk_entry_buffer_set_max_length() on it.
     *
     * @param maxLength The maximum length of the entry, or 0 for no maximum. (other than the maximum length of
     *                  entries.) The value passed in will be clamped to the range 0-65536.
     */
    public void setMaxLength(int maxLength) {
        library.gtk_entry_set_max_length(getCReference(), Math.min(Math.max(0, maxLength), 65536));
    }

    /**
     * Retrieves the GdkPaintable used for the icon.
     * <p>
     * If no GdkPaintable was used for the icon, NONE is returned.
     *
     * @param pos Icon position for which to get the GdkPaintable
     * @return a GdkPaintable if no icon is set for this position or the icon set is not a GdkPaintable - return NONE
     */
    public Option<GdkPaintable> getPaintableFromIcon(GtkEntryIconPosition pos) {
        if (pos != null) {
            Option<Pointer> p = new Option<>(library.gtk_entry_get_icon_paintable(getCReference(), GtkEntryIconPosition.getCValue(pos)));
            if (p.isDefined()) {
                return new Option<>((GdkPaintable) JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
            }
        }
        return Option.NONE;
    }

    /**
     * Retrieves the character displayed in place of the actual text in "password mode".
     *
     * @return The current invisible char, or NONE, if the entry does not show invisible text at all.
     */
    public Option<Character> getPasswordCharacter() {
        char c = library.gtk_entry_get_invisible_char(getCReference());
        if (c != 0) {
            return new Option<>(c);
        }
        return Option.NONE;
    }

    /**
     * Sets the character to use in place of the actual text in "password mode".
     * <p>
     * See gtk_entry_set_visibility() for how to enable "password mode".
     * <p>
     * By default, GTK picks the best invisible char available in the current font. If you set the invisible char to 0,
     * then the user will get no feedback at all; there will be no text on the screen as they type.
     *
     * @param passwordCharacter A Unicode character. If Undefined, reset to default
     */
    public void setPasswordCharacter(Character passwordCharacter) {
        if (passwordCharacter != null) {
            library.gtk_entry_set_invisible_char(getCReference(), passwordCharacter);
        } else {
            library.gtk_entry_unset_invisible_char(getCReference());
        }
    }

    /**
     * Retrieves the text that will be displayed when entry is empty and unfocused.
     *
     * @return A pointer to the placeholder text as a string. This string points to internally allocated storage in the
     *         widget and must not be freed, modified or stored. If no placeholder text has been set, NONE will be
     *         returned.
     */
    public Option<String> getPlaceholderText() {
        return new Option<>(library.gtk_entry_get_placeholder_text(getCReference()));
    }

    /**
     * Sets text to be displayed in entry when it is empty.
     * <p>
     * This can be used to give a visual hint of the expected contents of the GtkEntry.
     *
     * @param text A string to be displayed when entry is empty and unfocused.
     *             <p>
     *             The argument can be NULL.
     */
    public void setPlaceholderText(String text) {
        library.gtk_entry_set_placeholder_text(getCReference(), text);
    }

    /**
     * Returns the current fraction of the task that's been completed.
     *
     * @return A fraction from 0.0 to 1.0
     */
    public double getProgressFraction() {
        return library.gtk_entry_get_progress_fraction(getCReference());
    }

    /**
     * Causes the entry's progress indicator to "fill in" the given fraction of the bar.
     * <p>
     * The fraction should be between 0.0 and 1.0, inclusive.
     *
     * @param fraction Fraction of the task that's been completed.
     */
    public void setProgressFraction(double fraction) {
        library.gtk_entry_set_progress_fraction(getCReference(), Math.min(Math.max(0.0d, fraction), 1.0d));
    }

    /**
     * Retrieves the fraction of total entry width to move the progress bouncing block for each pulse.
     *
     * @return A fraction from 0.0 to 1.0
     */
    public double getProgressPulseStep() {
        return library.gtk_entry_get_progress_pulse_step(getCReference());
    }

    /**
     * Sets the fraction of total entry width to move the progress bouncing block for each pulse.
     * <p>
     * Use gtk_entry_progress_pulse() to pulse the progress.
     *
     * @param step Fraction between 0.0 and 1.0
     */
    public void setProgressPulseStep(double step) {
        library.gtk_entry_set_progress_pulse_step(getCReference(), Math.min(Math.max(0.0d, step), 1.0d));
    }

    /**
     * Gets the tab-stops of the GtkEntry. Each tab stop has an alignment, a position, and optionally a character to
     * use as decimal point.
     *
     * @return The tab-stops.
     */
    public Option<PangoTabArray> getTabStops() {
        Option<Pointer> p = new Option<>(library.gtk_entry_get_tabs(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoTabArray(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a PangoTabArray.
     * <p>
     * The tab-stops in the array are applied to the entry text.
     *
     * @param tabs A PangoTabArray
     *             <p>
     *             The argument can be NULL.
     */
    public void setTabStops(PangoTabArray tabs) {
        library.gtk_entry_set_tabs(getCReference(), pointerOrNull(tabs));
    }

    /**
     * Retrieves the current length of the text in entry.
     * <p>
     * This is equivalent to getting entry's GtkEntryBuffer and calling gtk_entry_buffer_get_length() on it.
     *
     * @return The current number of characters in GtkEntry, or 0 if there are none.
     */
    public int getTextLength() {
        return library.gtk_entry_get_text_length(getCReference());
    }

    /**
     * Gets the contents of the tooltip on the icon at the specified position in entry.
     *
     * @param pos The icon position for which we'll get the tooltip markup
     * @return The tooltip text.
     */
    public Option<String> getTooltipMarkup(GtkEntryIconPosition pos) {
        if (pos != null) {
            return new Option<>(library.gtk_entry_get_icon_tooltip_markup(getCReference(), GtkEntryIconPosition.getCValue(pos)));
        }
        return Option.NONE;
    }

    /**
     * Gets the contents of the tooltip on the icon at the specified position in entry.
     *
     * @param pos The icon position for which we'll get the tooltip text
     * @return The tooltip text.
     */
    public Option<String> getTooltipText(GtkEntryIconPosition pos) {
        if (pos != null) {
            return new Option<>(library.gtk_entry_get_icon_tooltip_text(getCReference(), GtkEntryIconPosition.getCValue(pos)));
        }
        return Option.NONE;
    }

    /**
     * Causes entry to have keyboard focus.
     * <p>
     * It behaves like gtk_widget_grab_focus(), except that it doesn't select the contents of the entry. You only want
     * to call this on some special entries which the user usually doesn't want to replace all text in, such as
     * search-as-you-type entries.
     *
     * @return TRUE if focus is now inside self.
     */
    public boolean grabFocusWithoutSelecting() {
        return library.gtk_entry_grab_focus_without_selecting(getCReference());
    }

    /**
     * Gets whether the entry has a beveled frame
     *
     * @return Whether the entry has a beveled frame.
     */
    public boolean hasBeveledFrame() {
        return library.gtk_entry_get_has_frame(getCReference());
    }

    /**
     * Returns whether the icon appears sensitive or insensitive.
     *
     * @param pos the icon in question
     * @return TRUE if the icon is sensitive.
     */
    public boolean isIconSensitive(GtkEntryIconPosition pos) {
        if (pos != null) {
            return library.gtk_entry_get_icon_sensitive(getCReference(), GtkEntryIconPosition.getCValue(pos));
        }
        return false;
    }

    /**
     * Retrieves whether the text in entry is visible.
     *
     * @return TRUE if the text is currently visible.
     */
    public boolean isTextVisible() {
        return library.gtk_entry_get_visibility(getCReference());
    }

    /**
     * Sets whether the contents of the entry are visible or not.
     * <p>
     * When visibility is set to FALSE, characters are displayed as the invisible char, and will also appear that way
     * when the text in the entry widget is copied elsewhere.
     * <p>
     * By default, GTK picks the best invisible character available in the current font, but it can be changed with
     * gtk_entry_set_invisible_char().
     * <p>
     * Note that you probably want to set GtkEntry:input-purpose to GTK_INPUT_PURPOSE_PASSWORD or GTK_INPUT_PURPOSE_PIN
     * to inform input methods about the purpose of this entry, in addition to setting visibility to FALSE.
     *
     * @param isVisible TRUE if the contents of the entry are displayed as plaintext.
     */
    public void setTextVisible(boolean isVisible) {
        library.gtk_entry_set_visibility(getCReference(), isVisible);
    }

    /**
     * Indicates that some progress is made, but you don't know how much.
     * <p>
     * Causes the entry's progress indicator to enter "activity mode", where a block bounces back and forth. Each call
     * to gtk_entry_progress_pulse() causes the block to move by a little (the amount of movement per pulse is
     * determined by gtk_entry_set_progress_pulse_step()).
     */
    public void pulseProgress() {
        library.gtk_entry_progress_pulse(getCReference());
    }

    /**
     * Reset the input method context of the entry if needed.
     * <p>
     * This can be necessary in the case where modifying the buffer would confuse ongoing input method behavior.
     */
    public void resetInputMethodContext() {
        library.gtk_entry_reset_im_context(getCReference());
    }

    /**
     * Sets the alignment for the contents of the entry.
     * <p>
     * This controls the horizontal positioning of the contents when the displayed text is shorter than the width of
     * the entry.
     *
     * @param xAlign The horizontal alignment, from 0 (left) to 1 (right). Reversed for RTL layouts.
     */
    public void setAlignment(float xAlign) {
        library.gtk_entry_set_alignment(getCReference(), xAlign);
    }

    /**
     * Sets whether the entry has a beveled frame around it.
     *
     * @param hasFrame TRUE if entry has a beveled frame around it
     */
    public void setBeveledFrame(boolean hasFrame) {
        library.gtk_entry_set_has_frame(getCReference(), hasFrame);
    }

    /**
     * Sets the icon shown in the entry at the specified position from the current icon theme.
     * <p>
     * If the icon isn't known, a "broken image" icon will be displayed instead.
     * <p>
     * If icon is NULL, no icon will be shown in the specified position.
     *
     * @param pos  The position at which to set the icon.
     * @param icon The icon to set.
     *             <p>
     *             The argument can be NULL.
     */
    public void setIcon(GtkEntryIconPosition pos, GIcon icon) {
        if (pos != null) {
            library.gtk_entry_set_icon_from_gicon(getCReference(), GtkEntryIconPosition.getCValue(pos), pointerOrNull(icon));
        }
    }

    /**
     * Sets the icon shown in the entry at the specified position from the current icon theme.
     * <p>
     * If the icon name isn't known, a "broken image" icon will be displayed instead.
     * <p>
     * If icon_name is NULL, no icon will be shown in the specified position.
     *
     * @param pos      The position at which to set the icon.
     * @param iconName An icon name.
     *                 <p>
     *                 The argument can be NULL.
     */
    public void setIcon(GtkEntryIconPosition pos, String iconName) {
        if (pos != null) {
            library.gtk_entry_set_icon_from_icon_name(getCReference(), GtkEntryIconPosition.getCValue(pos), iconName);
        }
    }

    /**
     * Sets the icon shown in the specified position using a GdkPaintable.
     * <p>
     * If paintable is NULL, no icon will be shown in the specified position.
     *
     * @param pos       The position at which to set the icon.
     * @param paintable A GdkPaintable
     *                  <p>
     *                  The argument can be NULL.
     */
    public void setIcon(GtkEntryIconPosition pos, GdkPaintable paintable) {
        if (pos != null) {
            library.gtk_entry_set_icon_from_paintable(getCReference(), GtkEntryIconPosition.getCValue(pos), pointerOrNull(paintable));
        }
    }

    /**
     * Sets whether the icon is activatable.
     *
     * @param pos         Icon position.
     * @param activatable TRUE if the icon should be activatable.
     */
    public void setIconBeActivated(GtkEntryIconPosition pos, boolean activatable) {
        if (pos != null) {
            library.gtk_entry_set_icon_activatable(getCReference(), GtkEntryIconPosition.getCValue(pos), activatable);
        }
    }

    /**
     * Sets up the icon at the given position as drag source.
     * <p>
     * This makes it so that GTK will start a drag operation when the user clicks and drags the icon.
     *
     * @param pos      Icon position.
     * @param provider A GdkContentProvider
     * @param actions  A bitmask of the allowed drag actions.
     */
    public void setIconDragSource(GtkEntryIconPosition pos, GdkContentProvider provider, GdkDragAction actions) {
        if (pos != null && provider != null && actions != null) {
            library.gtk_entry_set_icon_drag_source(getCReference(), GtkEntryIconPosition.getCValue(pos), provider.getCReference(), actions.getCValue());
        }
    }

    /**
     * Sets the sensitivity for the specified icon.
     *
     * @param pos         Icon position.
     * @param isSensitive Specifies whether the icon should appear sensitive (TRUE) or insensitive.
     */
    public void setIconSensitive(GtkEntryIconPosition pos, boolean isSensitive) {
        if (pos != null) {
            library.gtk_entry_set_icon_sensitive(getCReference(), GtkEntryIconPosition.getCValue(pos), isSensitive);
        }
    }

    /**
     * Sets tooltip as the contents of the tooltip for the icon at the specified position.
     * <p>
     * tooltip is assumed to be marked up with Pango Markup.
     * <p>
     * Use NULL for tooltip to remove an existing tooltip.
     * <p>
     * See also gtk_widget_set_tooltip_markup() and gtk_entry_set_icon_tooltip_text().
     *
     * @param pos    The icon position.
     * @param markup The contents of the tooltip for the icon.
     *               <p>
     *               The argument can be NULL.
     */
    public void setTooltipMarkup(GtkEntryIconPosition pos, String markup) {
        if (pos != null) {
            library.gtk_entry_set_icon_tooltip_markup(getCReference(), GtkEntryIconPosition.getCValue(pos), markup);
        }
    }

    /**
     * Sets tooltip as the contents of the tooltip for the icon at the specified position.
     * <p>
     * Use NULL for tooltip to remove an existing tooltip.
     * <p>
     * See also gtk_widget_set_tooltip_text() and gtk_entry_set_icon_tooltip_markup().
     * <p>
     * If you unset the widget tooltip via gtk_widget_set_tooltip_text() or gtk_widget_set_tooltip_markup(), this sets
     * GtkWidget:has-tooltip to FALSE, which suppresses icon tooltips too. You can resolve this by then calling
     * gtk_widget_set_has_tooltip() to set GtkWidget:has-tooltip back to TRUE, or setting at least one non-empty
     * tooltip on any icon achieves the same result.
     *
     * @param pos  The icon position.
     * @param text The contents of the tooltip for the icon.
     *             <p>
     *             The argument can be NULL.
     */
    public void setTooltipText(GtkEntryIconPosition pos, String text) {
        if (pos != null) {
            library.gtk_entry_set_icon_tooltip_text(getCReference(), GtkEntryIconPosition.getCValue(pos), text);
        }
    }

    /**
     * Sets whether pressing 'Enter' in the entry will activate the default widget for the window containing the entry.
     * <p>
     * This usually means that the dialog containing the entry will be closed, since the default widget is usually one
     * of the dialog buttons.
     *
     * @param enterActivatesDefault TRUE to activate window's default widget on Enter keypress.
     */
    public void shouldEnterActivateDefault(boolean enterActivatesDefault) {
        library.gtk_entry_set_activates_default(getCReference(), enterActivatesDefault);
    }

    public static class Signals extends GtkWidget.Signals {
        public static final Signals ACTIVATE = new Signals("activate");
        public static final Signals ICON_PRESS = new Signals("icon-press");
        public static final Signals ICON_RELEASE = new Signals("icon-release");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkEntryLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the value set by gtk_entry_set_activates_default().
         *
         * @param entry self
         * @return TRUE if the entry will activate the default widget.
         */
        public native boolean gtk_entry_get_activates_default(Pointer entry);

        /**
         * Gets the horizontal alignment, from 0 (left) to 1 (right).
         * Reversed for RTL layouts.
         *
         * @param entry self
         * @return The horizontal alignment, from 0 (left) to 1 (right).
         */
        public native float gtk_entry_get_alignment(Pointer entry);

        /**
         * Gets the attribute list of the GtkEntry.
         * <p>
         * See gtk_entry_set_attributes().
         *
         * @param entry self
         * @return The attribute list. Type: PangoAttrList
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_entry_get_attributes(Pointer entry);

        /**
         * Get the GtkEntryBuffer object which holds the text for this widget.
         *
         * @param entry self
         * @return A GtkEntryBuffer object. Type: GtkEntryBuffer
         */
        public native Pointer gtk_entry_get_buffer(Pointer entry);

        /**
         * Returns the auxiliary completion object currently in use by entry.
         *
         * @param entry self
         * @return The auxiliary completion object currently in use by entry. Type: GtkEntryCompletion
         * @deprecated Deprecated since: 4.10. GtkEntryCompletion will be removed in GTK 5.
         */
        @Deprecated
        public native Pointer gtk_entry_get_completion(Pointer entry);

        /**
         * Returns the index of the icon which is the source of the current DND operation, or -1.
         *
         * @param entry self
         * @return Index of the icon which is the source of the current DND operation, or -1.
         */
        public native int gtk_entry_get_current_icon_drag_source(Pointer entry);

        /**
         * Gets the menu model set with gtk_entry_set_extra_menu().
         *
         * @param entry self
         * @return The menu model. Type: GMenuModel
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_entry_get_extra_menu(Pointer entry);

        /**
         * Gets the value set by gtk_entry_set_has_frame().
         *
         * @param entry self
         * @return Whether the entry has a beveled frame.
         */
        public native boolean gtk_entry_get_has_frame(Pointer entry);

        /**
         * Returns whether the icon is activatable.
         *
         * @param entry    self
         * @param icon_pos Icon position. Type: GtkEntryIconPosition
         * @return TRUE if the icon is activatable.
         */
        public native boolean gtk_entry_get_icon_activatable(Pointer entry, int icon_pos);

        /**
         * Gets the area where entry's icon at icon_pos is drawn.
         * <p>
         * This function is useful when drawing something to the entry in a draw callback.
         * <p>
         * If the entry is not realized or has no icon at the given position, icon_area is filled with zeros.
         * Otherwise, icon_area will be filled with the icon's allocation, relative to entry's allocation.
         *
         * @param entry     self
         * @param icon_pos  Icon position. Type: GtkEntryIconPosition
         * @param icon_area Return location for the icon's area. Type: GdkRectangle
         */
        public native void gtk_entry_get_icon_area(Pointer entry, int icon_pos, Pointer icon_area);

        /**
         * Finds the icon at the given position and return its index.
         * <p>
         * The position's coordinates are relative to the entry's top left corner.
         * If x, y doesn't lie inside an icon, -1 is returned. This function is intended for use in a
         * GtkWidget::query-tooltip signal handler.
         *
         * @param entry self
         * @param x     The x coordinate of the position to find, relative to entry.
         * @param y     The y coordinate of the position to find, relative to entry.
         * @return The index of the icon at the given position, or -1
         */
        public native int gtk_entry_get_icon_at_pos(Pointer entry, int x, int y);

        /**
         * Retrieves the GIcon used for the icon.
         * <p>
         * NULL will be returned if there is no icon or if the icon was set by some other method (e.g., by
         * GdkPaintable or icon name).
         *
         * @param entry    self
         * @param icon_pos Icon position. Type: GtkEntryIconPosition
         * @return A GIcon. Type: GIcon
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_entry_get_icon_gicon(Pointer entry, int icon_pos);

        /**
         * Retrieves the icon name used for the icon.
         * <p>
         * NULL is returned if there is no icon or if the icon was set by some other method (e.g., by GdkPaintable
         * or gicon).
         *
         * @param entry    self
         * @param icon_pos Icon position. Type: GtkEntryIconPosition
         * @return An icon name.
         *         The return value can be NULL.
         */
        public native String gtk_entry_get_icon_name(Pointer entry, int icon_pos);

        /**
         * Retrieves the GdkPaintable used for the icon.
         * <p>
         * If no GdkPaintable was used for the icon, NULL is returned.
         *
         * @param entry    self
         * @param icon_pos Icon position. Type: GtkEntryIconPosition
         * @return A GdkPaintable if no icon is set for this position or the icon set is not a GdkPaintable.
         *         Type: GdkPaintable
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_entry_get_icon_paintable(Pointer entry, int icon_pos);

        /**
         * Returns whether the icon appears sensitive or insensitive
         *
         * @param entry    self
         * @param icon_pos Icon position. Type: GtkEntryIconPosition
         * @return TRUE if the icon is sensitive.
         */
        public native boolean gtk_entry_get_icon_sensitive(Pointer entry, int icon_pos);

        /**
         * Gets the type of representation being used by the icon to store image data.
         * <p>
         * If the icon has no image data, the return value will be GTK_IMAGE_EMPTY.
         *
         * @param entry    self
         * @param icon_pos Icon position. Type: GtkEntryIconPosition
         * @return Image representation being used. Type: GtkImageType
         */
        public native int gtk_entry_get_icon_storage_type(Pointer entry, int icon_pos);

        /**
         * Gets the contents of the tooltip on the icon at the specified position in entry.
         *
         * @param entry    self
         * @param icon_pos The icon position. Type: GtkEntryIconPosition
         * @return The tooltip text.
         *         The return value can be NULL.
         */
        public native String gtk_entry_get_icon_tooltip_markup(Pointer entry, int icon_pos);

        /**
         * @param entry    self
         * @param icon_pos The icon position. Type: GtkEntryIconPosition
         * @return The tooltip text.
         *         The return value can be NULL.
         */
        public native String gtk_entry_get_icon_tooltip_text(Pointer entry, int icon_pos);

        /**
         * Gets the input hints of this GtkEntry.
         *
         * @param entry self
         * @return The input hints.
         */
        public native int gtk_entry_get_input_hints(Pointer entry);

        /**
         * Gets the input purpose of the GtkEntry.
         *
         * @param entry self
         * @return The input purpose. Type: GtkInputPurpose
         */
        public native int gtk_entry_get_input_purpose(Pointer entry);

        /**
         * Retrieves the character displayed in place of the actual text in "password mode".
         *
         * @param entry self
         * @return The current invisible char, or 0, if the entry does not show invisible text at all.
         */
        public native char gtk_entry_get_invisible_char(Pointer entry);

        /**
         * Retrieves the maximum allowed length of the text in entry.
         * <p>
         * See gtk_entry_set_max_length().
         *
         * @param entry self
         * @return The maximum allowed number of characters in GtkEntry, or 0 if there is no maximum.
         */
        public native int gtk_entry_get_max_length(Pointer entry);

        /**
         * Gets whether the GtkEntry is in overwrite mode.
         *
         * @param entry self
         * @return Whether the text is overwritten when typing.
         */
        public native boolean gtk_entry_get_overwrite_mode(Pointer entry);

        /**
         * Retrieves the text that will be displayed when entry is empty and unfocused.
         *
         * @param entry self
         * @return A pointer to the placeholder text as a string. This string points to internally allocated storage
         *         in the widget and must not be freed, modified or stored. If no placeholder text has been set,
         *         NULL will be returned.
         */
        public native String gtk_entry_get_placeholder_text(Pointer entry);

        /**
         * Returns the current fraction of the task that's been completed.
         * <p>
         * See gtk_entry_set_progress_fraction().
         *
         * @param entry self
         * @return A fraction from 0.0 to 1.0
         */
        public native double gtk_entry_get_progress_fraction(Pointer entry);

        /**
         * Retrieves the pulse step set with gtk_entry_set_progress_pulse_step()
         *
         * @param entry self
         * @return A fraction from 0.0 to 1.0
         */
        public native double gtk_entry_get_progress_pulse_step(Pointer entry);

        /**
         * Gets the tab-stops of the GtkEntry.
         * <p>
         * See gtk_entry_set_tabs().
         *
         * @param entry self
         * @return The tab-stops.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_entry_get_tabs(Pointer entry);

        /**
         * Retrieves the current length of the text in entry.
         * <p>
         * This is equivalent to getting entry's GtkEntryBuffer and calling gtk_entry_buffer_get_length() on it.
         *
         * @param entry self
         * @return The current number of characters in GtkEntry, or 0 if there are none.
         */
        public native int gtk_entry_get_text_length(Pointer entry);

        /**
         * Retrieves whether the text in entry is visible.
         * <p>
         * See gtk_entry_set_visibility().
         *
         * @param entry self
         * @return TRUE if the text is currently visible.
         */
        public native boolean gtk_entry_get_visibility(Pointer entry);

        /**
         * Causes entry to have keyboard focus.
         * <p>
         * It behaves like gtk_widget_grab_focus(), except that it doesn't select the contents of the entry. You only
         * want to call this on some special entries which the user usually doesn't want to replace all text in,
         * such as search-as-you-type entries.
         *
         * @param entry self
         * @return TRUE if focus is now inside self.
         */
        public native boolean gtk_entry_grab_focus_without_selecting(Pointer entry);

        /**
         * Creates a new entry.
         *
         * @return A new GtkEntry. Type: GtkEntry
         */
        public native Pointer gtk_entry_new();

        /**
         * Creates a new entry with the specified text buffer.
         *
         * @param buffer The buffer to use for the new GtkEntry.
         * @return A new GtkEntry. Type: GtkEntry
         */
        public native Pointer gtk_entry_new_with_buffer(Pointer buffer);

        /**
         * Indicates that some progress is made, but you don't know how much.
         * <p>
         * Causes the entry's progress indicator to enter "activity mode", where a block bounces back and forth.
         * Each call to gtk_entry_progress_pulse() causes the block to move by a bit (the amount of movement
         * per pulse is determined by gtk_entry_set_progress_pulse_step()).
         *
         * @param entry self
         */
        public native void gtk_entry_progress_pulse(Pointer entry);

        /**
         * Reset the input method context of the entry if needed.
         * <p>
         * This can be necessary in the case where modifying the buffer would confuse ongoing input method behavior.
         *
         * @param entry self
         */
        public native void gtk_entry_reset_im_context(Pointer entry);

        /**
         * Sets whether pressing 'Enter' in the entry will activate the default widget for the window containing
         * the entry.
         * <p>
         * This usually means that the dialog containing the entry will be closed, since the default widget is usually
         * one of the dialog buttons.
         *
         * @param entry   self
         * @param setting TRUE to activate window's default widget on Enter keypress.
         */
        public native void gtk_entry_set_activates_default(Pointer entry, boolean setting);

        /**
         * Sets the alignment for the contents of the entry.
         * <p>
         * This controls the horizontal positioning of the contents when the displayed text is shorter than the width
         * of the entry.
         *
         * @param entry  self
         * @param xalign The horizontal alignment, from 0 (left) to 1 (right). Reversed for RTL layouts.
         */
        public native void gtk_entry_set_alignment(Pointer entry, float xalign);

        /**
         * Sets a PangoAttrList.
         * <p>
         * The attributes in the list are applied to the entry text.
         * <p>
         * Since the attributes will be applied to text that changes as the user types, it makes most sense to use
         * attributes with unlimited extent.
         *
         * @param entry self
         * @param attrs A PangoAttrList. Type: PangoAttrList
         */
        public native void gtk_entry_set_attributes(Pointer entry, Pointer attrs);

        /**
         * Set the GtkEntryBuffer object which holds the text for this widget.
         *
         * @param entry  self
         * @param buffer A GtkEntryBuffer. Type: GtkEntryBuffer
         */
        public native void gtk_entry_set_buffer(Pointer entry, Pointer buffer);

        /**
         * Sets completion to be the auxiliary completion object to use with entry.
         * <p>
         * All further configuration of the completion mechanism is done on completion using the GtkEntryCompletion API.
         * Completion is disabled if completion is set to NULL.
         *
         * @param entry      self
         * @param completion The GtkEntryCompletion. Type: GtkEntryCompletion
         *                   <p>
         *                   The argument can be NULL.
         * @deprecated Deprecated since: 4.10. GtkEntryCompletion will be removed in GTK 5.
         */
        @Deprecated
        public native void gtk_entry_set_completion(Pointer entry, Pointer completion);

        /**
         * Sets a menu model to add when constructing the context menu for entry.
         *
         * @param entry self
         * @param model A GMenuModel. Type: GMenuModel
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_entry_set_extra_menu(Pointer entry, Pointer model);

        /**
         * Sets whether the entry has a beveled frame around it.
         *
         * @param entry   self
         * @param setting New value.
         */
        public native void gtk_entry_set_has_frame(Pointer entry, boolean setting);

        /**
         * Sets whether the icon is activatable.
         *
         * @param entry       self
         * @param icon_pos    Icon position. Type: GtkEntryIconPosition
         * @param activatable TRUE if the icon should be activatable.
         */
        public native void gtk_entry_set_icon_activatable(Pointer entry, int icon_pos, boolean activatable);

        /**
         * Sets up the icon at the given position as drag source.
         * <p>
         * This makes it so that GTK will start a drag operation when the user clicks and drags the icon.
         *
         * @param entry    self
         * @param icon_pos Icon position. Type: GtkEntryIconPosition
         * @param provider A GdkContentProvider. Type: GdkContentProvider
         * @param actions  A bitmask of the allowed drag actions. Type: GdkDragAction
         */
        public native void gtk_entry_set_icon_drag_source(Pointer entry, int icon_pos, Pointer provider, int actions);

        /**
         * Sets the icon shown in the entry at the specified position from the current icon theme.
         * <p>
         * If the icon isn't known, a "broken image" icon will be displayed instead.
         * <p>
         * If icon is NULL, no icon will be shown in the specified position.
         *
         * @param entry    self
         * @param icon_pos The position at which to set the icon. Type: GtkEntryIconPosition
         * @param icon     The icon to set.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_entry_set_icon_from_gicon(Pointer entry, int icon_pos, Pointer icon);

        /**
         * Sets the icon shown in the entry at the specified position from the current icon theme.
         * <p>
         * If the icon name isn't known, a "broken image" icon will be displayed instead.
         * <p>
         * If icon_name is NULL, no icon will be shown in the specified position.
         *
         * @param entry     self
         * @param icon_pos  The position at which to set the icon. Type: GtkEntryIconPosition
         * @param icon_name An icon name.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_entry_set_icon_from_icon_name(Pointer entry, int icon_pos, String icon_name);

        /**
         * Sets the icon shown in the specified position using a GdkPaintable.
         * <p>
         * If paintable is NULL, no icon will be shown in the specified position.
         *
         * @param entry     self
         * @param icon_pos  Icon position. Type: GtkEntryIconPosition
         * @param paintable A GdkPaintable. Type: GdkPaintable
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_entry_set_icon_from_paintable(Pointer entry, int icon_pos, Pointer paintable);

        /**
         * Sets the sensitivity for the specified icon.
         *
         * @param entry     self
         * @param icon_pos  Icon position. Type: GtkEntryIconPosition
         * @param sensitive Specifies whether the icon should appear sensitive or insensitive.
         */
        public native void gtk_entry_set_icon_sensitive(Pointer entry, int icon_pos, boolean sensitive);

        /**
         * Sets tooltip as the contents of the tooltip for the icon at the specified position.
         * <p>
         * tooltip is assumed to be marked up with Pango Markup.
         * <p>
         * Use NULL for tooltip to remove an existing tooltip.
         * <p>
         * See also gtk_widget_set_tooltip_markup() and gtk_entry_set_icon_tooltip_text().
         *
         * @param entry    self
         * @param icon_pos The icon position. Type: GtkEntryIconPosition
         * @param tooltip  The contents of the tooltip for the icon.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_entry_set_icon_tooltip_markup(Pointer entry, int icon_pos, String tooltip);

        /**
         * Sets tooltip as the contents of the tooltip for the icon at the specified position.
         * <p>
         * Use NULL for tooltip to remove an existing tooltip.
         * <p>
         * See also gtk_widget_set_tooltip_text() and gtk_entry_set_icon_tooltip_markup().
         * <p>
         * If you unset the widget tooltip via gtk_widget_set_tooltip_text() or gtk_widget_set_tooltip_markup(),
         * this sets GtkWidget:has-tooltip to FALSE, which suppresses icon tooltips too. You can resolve this by then
         * calling gtk_widget_set_has_tooltip() to set GtkWidget:has-tooltip back to TRUE, or setting at least one
         * non-empty tooltip on any icon achieves the same result.
         *
         * @param entry    self
         * @param icon_pos The icon position. Type: GtkEntryIconPosition
         * @param tooltip  The contents of the tooltip for the icon.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_entry_set_icon_tooltip_text(Pointer entry, int icon_pos, String tooltip);

        /**
         * Set additional hints which allow input methods to fine-tune their behavior.
         *
         * @param entry self
         * @param hints The hints. Type: GtkInputHints
         */
        public native void gtk_entry_set_input_hints(Pointer entry, int hints);

        /**
         * Sets the input purpose which can be used by input methods to adjust their behavior.
         *
         * @param entry   self
         * @param purpose The purpose. Type: GtkInputPurpose
         */
        public native void gtk_entry_set_input_purpose(Pointer entry, int purpose);

        /**
         * Sets the character to use in place of the actual text in "password mode".
         * <p>
         * See gtk_entry_set_visibility() for how to enable "password mode".
         * <p>
         * By default, GTK picks the best invisible char available in the current font. If you set the invisible char
         * to 0, then the user will get no feedback at all; there will be no text on the screen as they type.
         *
         * @param entry self
         * @param ch    A Unicode character.
         */
        public native void gtk_entry_set_invisible_char(Pointer entry, char ch);

        /**
         * Sets the maximum allowed length of the contents of the widget.
         * <p>
         * If the current contents are longer than the given length, then they will be truncated to fit. The length
         * is in characters.
         * <p>
         * This is equivalent to getting entry's GtkEntryBuffer and calling gtk_entry_buffer_set_max_length() on it.
         *
         * @param entry self
         * @param max   The maximum length of the entry, or 0 for no maximum.
         *              (other than the maximum length of entries.) The value passed in will be clamped to the
         *              range 0-65536.
         */
        public native void gtk_entry_set_max_length(Pointer entry, int max);

        /**
         * Sets whether the text is overwritten when typing in the GtkEntry.
         *
         * @param entry     self
         * @param overwrite New value.
         */
        public native void gtk_entry_set_overwrite_mode(Pointer entry, boolean overwrite);

        /**
         * Sets text to be displayed in entry when it is empty.
         * <p>
         * This can be used to give a visual hint of the expected contents of the GtkEntry.
         *
         * @param entry self
         * @param text  A string to be displayed when entry is empty and unfocused.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_entry_set_placeholder_text(Pointer entry, String text);

        /**
         * Causes the entry's progress indicator to "fill in" the given fraction of the bar.
         * <p>
         * The fraction should be between 0.0 and 1.0, inclusive.
         *
         * @param entry    self
         * @param fraction Fraction of the task that's been completed.
         */
        public native void gtk_entry_set_progress_fraction(Pointer entry, double fraction);

        /**
         * Sets the fraction of total entry width to move the progress bouncing block for each pulse.
         * <p>
         * Use gtk_entry_progress_pulse() to pulse the progress.
         *
         * @param entry    self
         * @param fraction Fraction between 0.0 and 1.0
         */
        public native void gtk_entry_set_progress_pulse_step(Pointer entry, double fraction);

        /**
         * Sets a PangoTabArray.
         * <p>
         * The tab-stops in the array are applied to the entry text.
         *
         * @param entry self
         * @param tabs  A PangoTabArray
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_entry_set_tabs(Pointer entry, Pointer tabs);

        /**
         * Sets whether the contents of the entry are visible or not.
         * <p>
         * When visibility is set to FALSE, characters are displayed as the invisible char, and will also appear that
         * way when the text in the entry widget is copied elsewhere.
         * <p>
         * By default, GTK picks the best invisible character available in the current font, but it can be changed with
         * gtk_entry_set_invisible_char().
         * <p>
         * Note that you probably want to set GtkEntry:input-purpose to GTK_INPUT_PURPOSE_PASSWORD or
         * GTK_INPUT_PURPOSE_PIN to inform input methods about the purpose of this entry, in addition to setting
         * visibility to FALSE.
         *
         * @param entry   self
         * @param visible TRUE if the contents of the entry are displayed as plaintext.
         */
        public native void gtk_entry_set_visibility(Pointer entry, boolean visible);

        /**
         * Unsets the invisible char, so that the default invisible char is used again. See
         * gtk_entry_set_invisible_char().
         *
         * @param entry self
         */
        public native void gtk_entry_unset_invisible_char(Pointer entry);
    }
}
