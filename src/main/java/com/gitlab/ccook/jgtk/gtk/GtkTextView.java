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
import com.gitlab.ccook.jgtk.enums.GtkJustification;
import com.gitlab.ccook.jgtk.enums.GtkTextWindowType;
import com.gitlab.ccook.jgtk.enums.GtkWrapMode;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkScrollable;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.util.List;

/**
 * A widget that displays the contents of a GtkTextBuffer.
 */
@SuppressWarnings("unchecked")
public class GtkTextView extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkScrollable {
    private static final GtkTextViewLibrary library = new GtkTextViewLibrary();

    /**
     * Creates a new GtkTextView.
     */
    public GtkTextView() {
        this(library.gtk_text_view_new());
    }

    public GtkTextView(Pointer ref) {
        super(ref);
    }

    public GtkTextView(String text) {
        this(new GtkTextBuffer(text));
    }

    /**
     * Creates a new GtkTextView with a given buffer
     *
     * @param buff A GtkTextBuffer to start with
     */
    public GtkTextView(GtkTextBuffer buff) {
        this(library.gtk_text_view_new_with_buffer(buff.getCReference()));
    }

    /**
     * Adds a child widget in the text buffer, at the given anchor.
     *
     * @param child  A GtkWidget
     * @param anchor A GtkTextChildAnchor in the GtkTextBuffer for text_view.
     */
    public void addChildAtAnchor(GtkWidget child, GtkTextChildAnchor anchor) {
        if (child != null && anchor != null) {
            library.gtk_text_view_add_child_at_anchor(getCReference(), child.getCReference(), anchor.getCReference());
        }
    }

    /**
     * Adds child at a fixed coordinate in the GtkTextView's text window.
     * <p>
     * The xPosition and yPosition must be in buffer coordinates (see gtk_text_view_get_iter_location() to convert to
     * buffer coordinates).
     * <p>
     * child will scroll with the text view.
     * <p>
     * If instead you want a widget that will not move with the GtkTextView contents see GtkOverlay.
     *
     * @param child     A GtkWidget
     * @param xPosition X position of child in window coordinates.
     * @param yPosition Y position of child in window coordinates.
     */
    public void addOverlay(GtkWidget child, int xPosition, int yPosition) {
        if (child != null) {
            library.gtk_text_view_add_overlay(getCReference(), child.getCReference(), xPosition, yPosition);
        }
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
     * Returns whether pressing the Tab key inserts a tab characters.
     *
     * @return TRUE if pressing the Tab key inserts a tab character,
     *         FALSE if pressing the Tab key moves the keyboard focus.
     */
    public boolean doesAcceptTabs() {
        return library.gtk_text_view_get_accepts_tab(getCReference());
    }

    /**
     * Returns whether the GtkTextView is in overwrite mode
     *
     * @return Whether text_view is in overwrite mode
     */
    public boolean doesOverwrite() {
        return library.gtk_text_view_get_overwrite(getCReference());
    }

    /**
     * Allow the GtkTextView input method to internally handle key press and release events.
     * <p>
     * If this function returns TRUE, then no further processing should be done for this key event.
     * See gtk_im_context_filter_keypress().
     * <p>
     * Note that you are expected to call this function from your handler when overriding key event handling.
     * This is needed in the case when you need to insert your own key handling between the input method and the default
     * key event handling of the GtkTextView.
     *
     * @param e The key event.
     * @return TRUE if the input method handled the key event.
     */
    public boolean filterKeypress(GdkEvent e) {
        if (e != null) {
            return library.gtk_text_view_im_context_filter_keypress(getCReference(), e.getCReference());
        }
        return false;
    }

    /**
     * Gets the bottom margin for text in the text_view.
     *
     * @return Bottom margin in pixels.
     */
    public int getBottomMarginSize() {
        return library.gtk_text_view_get_bottom_margin(getCReference());
    }

    /**
     * Sets the bottom margin for text in text_view.
     * <p>
     * Note that this function is confusingly named. In CSS terms, the value set here is padding.
     *
     * @param size Bottom margin in pixels.
     */
    public void setBottomMarginSize(int size) {
        library.gtk_text_view_set_bottom_margin(getCReference(), size);
    }

    /**
     * Returns the GtkTextBuffer being displayed by this text view.
     * <p>
     * The reference count on the buffer is not incremented; the caller of this function won't own a new reference.
     *
     * @return A GtkTextBuffer
     */
    public GtkTextBuffer getBuffer() {
        return new GtkTextBuffer(library.gtk_text_view_get_buffer(getCReference()));
    }

    /**
     * Sets buffer as the buffer being displayed by text_view.
     * <p>
     * The previous buffer displayed by the text view is unreferenced, and a reference is added to buffer. If you owned
     * a reference to buffer before passing it to this function, you must remove that reference yourself;
     * GtkTextView will not "adopt" it.
     *
     * @param buff A GtkTextBuffer
     *             <p>
     *             The argument can be NULL.
     */
    public void setBuffer(GtkTextBuffer buff) {
        library.gtk_text_view_set_buffer(getCReference(), pointerOrNull(buff));
    }

    /**
     * Determine the positions of the strong and weak cursors if the insertion point is at iter.
     * <p>
     * The position of each cursor is stored as a zero-width rectangle. The strong cursor location is the location where
     * characters of the directionality equal to the base direction of the paragraph are inserted. The weak cursor
     * location is the location where characters of the directionality opposite to the base direction of the paragraph
     * are inserted.
     * <p>
     * If iter is NULL, the actual cursor position is used.
     * <p>
     * Note that if iter happens to be the actual cursor position, and there is currently an IM preedit sequence being
     * entered, the returned locations will be adjusted to account for the preedit cursor's offset within the preedit
     * sequence.
     * <p>
     * The rectangle position is in buffer coordinates; use gtk_text_view_buffer_to_window_coords() to convert these
     * coordinates to coordinates for one of the windows in the text view.
     *
     * @param iter A GtkTextIter
     *             <p>
     *             The argument can be NULL.
     * @return first - the strong cursor position, second - the weak cursor position.
     */
    public Pair<GdkRectangle, GdkRectangle> getCursorPositions(GtkTextIter iter) {
        GdkRectangle.GdkRectangleStruct.ByReference strong = new GdkRectangle.GdkRectangleStruct.ByReference();
        GdkRectangle.GdkRectangleStruct.ByReference weak = new GdkRectangle.GdkRectangleStruct.ByReference();
        library.gtk_text_view_get_cursor_locations(getCReference(), pointerOrNull(iter), strong, weak);
        return new Pair<>(new GdkRectangle(strong), new GdkRectangle(weak));
    }

    /**
     * Gets the menu model that gets added to the context menu or NONE if none has been set.
     *
     * @return The menu model, if defined
     */
    public Option<GMenuModel> getExtraMenu() {
        Option<Pointer> p = new Option<>(library.gtk_text_view_get_extra_menu(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GMenuModel(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a menu model to add when constructing the context menu for text_view.
     * <p>
     * You can pass NULL to remove a previously set extra menu.
     *
     * @param m A GMenuModel
     *          <p>
     *          The argument can be NULL.
     */
    public void setExtraMenu(GMenuModel m) {
        library.gtk_text_view_set_extra_menu(getCReference(), pointerOrNull(m));
    }

    /**
     * Gets a GtkWidget that has previously been set as gutter.
     * <p>
     * See gtk_text_view_set_gutter().
     * <p>
     *
     * @param win A GtkTextWindowType (must be one of GTK_TEXT_WINDOW_LEFT, GTK_TEXT_WINDOW_RIGHT, GTK_TEXT_WINDOW_TOP,
     *            or GTK_TEXT_WINDOW_BOTTOM.)
     * @return A GtkWidget, if defined
     */
    public Option<GtkWidget> getGutter(GtkTextWindowType win) {
        if (win != null) {
            if (GtkTextWindowType.isSubset(win, GtkTextWindowType.GTK_TEXT_WINDOW_LEFT, GtkTextWindowType.GTK_TEXT_WINDOW_RIGHT, GtkTextWindowType.GTK_TEXT_WINDOW_TOP, GtkTextWindowType.GTK_TEXT_WINDOW_BOTTOM)) {
                Option<Pointer> p = new Option<>(library.gtk_text_view_get_gutter(getCReference(), GtkTextWindowType.getCValueFromType(win)));
                if (p.isDefined()) {
                    return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
                }
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the default indentation of paragraphs in text_view.
     * <p>
     * Tags in the view's buffer may override the default. The indentation may be negative.
     *
     * @return Number of pixels of indentation.
     */
    public int getIndentSize() {
        return library.gtk_text_view_get_indent(getCReference());
    }

    /**
     * Sets the default indentation for paragraphs in text_view.
     * <p>
     * Tags in the buffer may override the default.
     *
     * @param size Indentation in pixels.
     */
    public void setIndentSize(int size) {
        library.gtk_text_view_set_indent(getCReference(), size);
    }

    /**
     * Gets the input-hints of the GtkTextView.
     *
     * @return Additional hints (beyond GtkTextView:input-purpose) that allow input methods to fine-tune their
     *         behavior.
     */
    public List<GtkInputHints> getInputHints() {
        return GtkInputHints.getHintsFromCValue(library.gtk_text_view_get_input_hints(getCReference()));
    }

    /**
     * Sets the input-hints of the GtkTextView.
     * <p>
     * The input-hints allow input methods to fine-tune their behaviour.
     *
     * @param hints The hints.
     */
    public void setInputHints(GtkInputHints... hints) {
        if (hints != null) {
            library.gtk_text_view_set_input_hints(getCReference(), GtkInputHints.getCValueFromHints(hints));
        }
    }

    /**
     * Gets the input-purpose of the GtkTextView.
     * The purpose of this text field.
     * <p>
     * This property can be used by on-screen keyboards and other input methods to adjust their behavior.
     *
     * @return The purpose of this text field.
     *         This property can be used by on-screen keyboards and other input methods to adjust their behavior.
     */
    public GtkInputPurpose getInputPurpose() {
        return GtkInputPurpose.getPurposeFromCValue(library.gtk_text_view_get_input_purpose(getCReference()));
    }

    /**
     * Sets the input-purpose of the GtkTextView.
     * <p>
     * The input-purpose can be used by on-screen keyboards and other input methods to adjust their behaviour.
     *
     * @param purpose The purpose.
     */
    public void setInputPurpose(GtkInputPurpose purpose) {
        if (purpose != null) {
            library.gtk_text_view_set_input_purpose(getCReference(), GtkInputPurpose.getCValueFromPurpose(purpose));
        }
    }

    /**
     * Retrieves the iterator at buffer coordinates x and y.
     * <p>
     * Buffer coordinates are coordinates for the entire buffer, not just the currently-displayed portion.
     * If you have coordinates from an event, you have to convert those to buffer coordinates with
     * gtk_text_view_window_to_buffer_coords().
     *
     * @param xBufferCoordinate X position, in buffer coordinates.
     * @param yBufferCoordinate Y position, in buffer coordinates.
     * @return A GtkTextIter at provided coordinates, if defined
     */
    public Option<GtkTextIter> getIteratorAtBufferCoordinates(int xBufferCoordinate, int yBufferCoordinate) {
        PointerByReference iter = new PointerByReference();
        boolean posOverText = library.gtk_text_view_get_iter_at_location(getCReference(), iter, xBufferCoordinate, yBufferCoordinate);
        if (posOverText && iter.getPointer() != Pointer.NULL) {
            return new Option<>(new GtkTextIter(iter.getPointer()));
        }
        return Option.NONE;
    }

    /**
     * Gets a rectangle which roughly contains the character at iter.
     * <p>
     * The rectangle position is in buffer coordinates; use gtk_text_view_buffer_to_window_coords() to convert these
     * coordinates to coordinates for one of the windows in the text view.
     *
     * @param iter A GtkTextIter
     * @return Bounds of the character at iter, if defined
     */
    public Option<GdkRectangle> getIteratorLocation(GtkTextIter iter) {
        if (iter != null) {
            GdkRectangle.GdkRectangleStruct.ByReference location = new GdkRectangle.GdkRectangleStruct.ByReference();
            library.gtk_text_view_get_iter_location(getCReference(), iter.getCReference(), location);
            if (location.getPointer() != Pointer.NULL) {
                return new Option<>(new GdkRectangle(location));
            }
        }
        return Option.NONE;
    }

    /**
     * Retrieves the iterator pointing to the character at buffer coordinates x and y.
     * <p>
     * Buffer coordinates are coordinates for the entire buffer, not just the currently-displayed portion.
     * If you have coordinates from an event, you have to convert those to buffer coordinates with
     * gtk_text_view_window_to_buffer_coords().
     * <p>
     * Note that this is different from gtk_text_view_get_iter_at_location(), which returns cursor locations,
     * i.e. positions between characters.
     *
     * @param xBufferCoordinate X position, in buffer coordinates.
     * @param yBufferCoordinate Y position, in buffer coordinates.
     * @return A GtkTextIter at provided coordinates, if defined
     */
    public Option<GtkTextIter> getIteratorPointingToCharAtBufferCoordinates(int xBufferCoordinate, int yBufferCoordinate) {
        PointerByReference iter = new PointerByReference();
        boolean posOverText = library.gtk_text_view_get_iter_at_position(getCReference(), iter, Pointer.NULL, xBufferCoordinate, yBufferCoordinate);
        if (posOverText && iter.getPointer() != Pointer.NULL) {
            return new Option<>(new GtkTextIter(iter.getPointer()));
        }
        return Option.NONE;
    }

    /**
     * Gets the default justification of paragraphs in text_view.
     * <p>
     * Tags in the buffer may override the default.
     *
     * @return Default justification.
     */
    public GtkJustification getJustification() {
        return GtkJustification.getJustificationFromCValue(library.gtk_text_view_get_justification(getCReference()));
    }

    /**
     * Sets the default justification of text in text_view.
     * <p>
     * Tags in the view's buffer may override the default.
     *
     * @param justification Justification.
     */
    public void setJustification(GtkJustification justification) {
        if (justification != null) {
            library.gtk_text_view_set_justification(getCReference(), GtkJustification.getCValueFromJustification(justification));
        }
    }

    /**
     * Gets the PangoContext that is used for rendering LTR directed text layouts.
     * <p>
     * The context may be replaced when CSS changes occur.
     *
     * @return A PangoContext
     */
    public PangoContext getLTRContext() {
        return new PangoContext(library.gtk_text_view_get_ltr_context(getCReference()));
    }

    /**
     * Gets the default left margin size of paragraphs in the text_view.
     * <p>
     * Tags in the buffer may override the default.
     *
     * @return Left margin in pixels.
     */
    public int getLeftMarginSize() {
        return library.gtk_text_view_get_left_margin(getCReference());
    }

    /**
     * Sets the default left margin for text in text_view.
     * <p>
     * Tags in the buffer may override the default.
     * <p>
     * Note that this function is confusingly named. In CSS terms, the value set here is padding.
     *
     * @param leftMarginSize Left margin in pixels.
     */
    public void setLeftMarginSize(int leftMarginSize) {
        library.gtk_text_view_set_left_margin(getCReference(), leftMarginSize);
    }

    /**
     * Gts the GtkTextIter at the start of the line containing the coordinate y; with the coordinate of the top edge of
     * the line
     * <p>
     * y is in buffer coordinates, convert from window coordinates with gtk_text_view_window_to_buffer_coords().
     *
     * @param yBufferCoordinate A y coordinate in buffer coordinates
     * @return If defined,
     *         first - the GtkTextIter at the start of the line containing the coordinate y
     *         second - top coordinate of the line.
     */
    public Option<Pair<GtkTextIter, Integer>> getLineAtY(int yBufferCoordinate) {
        PointerByReference targetIter = new PointerByReference();
        PointerByReference lineTop = new PointerByReference();
        library.gtk_text_view_get_line_at_y(getCReference(), targetIter, yBufferCoordinate, lineTop);
        if (targetIter.getPointer() != Pointer.NULL && lineTop.getPointer() != Pointer.NULL) {
            return new Option<>(new Pair<>(new GtkTextIter(targetIter.getPointer()), lineTop.getPointer().getInt(0)));
        }
        return Option.NONE;
    }

    /**
     * Gets the y coordinate of the top of the line containing iter, and the height of the line.
     * <p>
     * The coordinate is a buffer coordinate; convert to window coordinates with
     * gtk_text_view_buffer_to_window_coords().
     *
     * @param iter A GtkTextIter
     * @return If defined, first - y buffer coordinate second - height of the line
     */
    public Option<Pair<Integer, Integer>> getLineYRange(GtkTextIter iter) {
        if (iter != null) {
            PointerByReference y = new PointerByReference();
            PointerByReference height = new PointerByReference();
            library.gtk_text_view_get_line_yrange(getCReference(), iter.getCReference(), y, height);
            if (y.getPointer() != Pointer.NULL && height.getPointer() != Pointer.NULL) {
                return new Option<>(new Pair<>(y.getPointer().getInt(0), height.getPointer().getInt(0)));
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the default number of pixels to put above paragraphs.
     * <p>
     * Adding this function with gtk_text_view_get_pixels_below_lines() is equal to the line space between each
     * paragraph.
     *
     * @return Default number of pixels above paragraphs.
     */
    public int getPixelsAboveParagraphs() {
        return library.gtk_text_view_get_pixels_above_lines(getCReference());
    }

    /**
     * Sets the default number of blank pixels above paragraphs in text_view.
     * <p>
     * Tags in the buffer for text_view may override the defaults.
     *
     * @param pixelsAboveLines Pixels above paragraphs.
     */
    public void setPixelsAboveParagraphs(int pixelsAboveLines) {
        library.gtk_text_view_set_pixels_above_lines(getCReference(), pixelsAboveLines);
    }

    /**
     * Gets the default number of pixels to put below paragraphs.
     * <p>
     * The line space is the sum of the value returned by this function and the value returned by
     * gtk_text_view_get_pixels_above_lines().
     *
     * @return Default number of blank pixels below paragraphs.
     */
    public int getPixelsBelowParagraphs() {
        return library.gtk_text_view_get_pixels_below_lines(getCReference());
    }

    /**
     * Sets the default number of pixels of blank space to put below paragraphs in text_view.
     * <p>
     * May be overridden by tags applied to text_view's buffer.
     *
     * @param belowParagraphs Pixels below paragraphs.
     */
    public void setPixelsBelowParagraphs(int belowParagraphs) {
        library.gtk_text_view_set_pixels_below_lines(getCReference(), belowParagraphs);
    }

    /**
     * Gets the default number of pixels to put between wrapped lines inside a paragraph.
     *
     * @return Default number of pixels of blank space between wrapped lines.
     */
    public int getPixelsInsideWrap() {
        return library.gtk_text_view_get_pixels_inside_wrap(getCReference());
    }

    /**
     * Sets the default number of pixels of blank space to leave between display/wrapped lines within a paragraph.
     * <p>
     * May be overridden by tags in text_view's buffer.
     *
     * @param pixelsInsideWrap Default number of pixels between wrapped lines.
     */
    public void setPixelsInsideWrap(int pixelsInsideWrap) {
        library.gtk_text_view_set_pixels_inside_wrap(getCReference(), pixelsInsideWrap);
    }

    /**
     * Gets the PangoContext that is used for rendering RTL directed text layouts.
     * <p>
     * The context may be replaced when CSS changes occur.
     *
     * @return A PangoContext
     */
    public PangoContext getRTLContext() {
        return new PangoContext(library.gtk_text_view_get_rtl_context(getCReference()));
    }

    /**
     * Gets the default right margin for text in text_view.
     * <p>
     * Tags in the buffer may override the default.
     *
     * @return Right margin in pixels.
     */
    public int getRightMarginSize() {
        return library.gtk_text_view_get_right_margin(getCReference());
    }

    /**
     * Sets the default right margin for text in the text view.
     * <p>
     * Tags in the buffer may override the default.
     * <p>
     * Note that this function is confusingly named. In CSS terms, the value set here is padding.
     *
     * @param size Right margin in pixels.
     */
    public void setRightMarginSize(int size) {
        library.gtk_text_view_set_right_margin(getCReference(), size);
    }

    /**
     * Gets the default tabs for text_view.
     * <p>
     * Tags in the buffer may override the defaults. The returned array will be NULL if "standard" (8-space) tabs are
     * used. Free the return value with pango_tab_array_free().
     *
     * @return Copy of default tab array, or NONE if standard tabs are used; must be freed with pango_tab_array_free().
     */
    public Option<PangoTabArray> getTabStops() {
        Option<Pointer> p = new Option<>(library.gtk_text_view_get_tabs(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoTabArray(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the default tab stops for paragraphs in text_view.
     * <p>
     * Tags in the buffer may override the default.
     *
     * @param arr Tabs as a PangoTabArray
     */
    public void setTabStops(PangoTabArray arr) {
        if (arr != null) {
            library.gtk_text_view_set_tabs(getCReference(), arr.getCReference());
        }
    }

    /**
     * Gets the top margin for text in the text_view.
     *
     * @return Top margin in pixels.
     */
    public int getTopMarginSize() {
        return library.gtk_text_view_get_top_margin(getCReference());
    }

    /**
     * Sets the top margin for text in text_view.
     * <p>
     * Note that this function is confusingly named. In CSS terms, the value set here is padding.
     *
     * @param size Top margin in pixels.
     */
    public void setTopMarginSize(int size) {
        library.gtk_text_view_set_top_margin(getCReference(), size);
    }

    /**
     * Fills visible_rect with the currently-visible region of the buffer, in buffer coordinates.
     * <p>
     * Convert to window coordinates with gtk_text_view_buffer_to_window_coords().
     *
     * @return the currently visible region of the buffer, in buffer coordinates.
     */
    public GdkRectangle getVisibleRectangle() {
        GdkRectangle.GdkRectangleStruct.ByReference gdkRect = new GdkRectangle.GdkRectangleStruct.ByReference();
        library.gtk_text_view_get_visible_rect(getCReference(), gdkRect);
        return new GdkRectangle(gdkRect);
    }

    /**
     * Gets the line wrapping for the view.
     *
     * @return The line wrap setting.
     */
    public GtkWrapMode getWrapMode() {
        return GtkWrapMode.getModeFromCValue(library.gtk_text_view_get_wrap_mode(getCReference()));
    }

    /**
     * Sets the line wrapping for the view.
     *
     * @param m A GtkWrapMode
     */
    public void setWrapMode(GtkWrapMode m) {
        if (m != null) {
            library.gtk_text_view_set_wrap_mode(getCReference(), GtkWrapMode.getCValueFromMode(m));
        }
    }

    /**
     * Find out whether the cursor should be displayed.
     *
     * @return Whether the insertion mark is visible.
     */
    public boolean isCursorVisible() {
        return library.gtk_text_view_get_cursor_visible(getCReference());
    }

    /**
     * Toggles whether the insertion point should be displayed.
     * <p>
     * A buffer with no editable text probably shouldn't have a visible cursor, so you may want to turn the cursor off.
     * <p>
     * Note that this property may be overridden by the GtkSettings:gtk-keynav-use-caret setting.
     *
     * @param isVisible Whether to show the insertion cursor.
     */
    public void setCursorVisible(boolean isVisible) {
        library.gtk_text_view_set_cursor_visible(getCReference(), isVisible);
        resetCursorBlink();
        if (isVisible) {
            moveCursorOnscreen();
        }
    }

    /**
     * Ensures that the cursor is shown.
     * <p>
     * This also resets the time that it will stay blinking (or visible, in case blinking is disabled).
     * <p>
     * This function should be called in response to user input (e.g. from derived classes that override the text view's
     * event handlers).
     */
    public void resetCursorBlink() {
        library.gtk_text_view_reset_cursor_blink(getCReference());
    }

    /**
     * Moves the cursor to the currently visible region of the buffer.
     *
     * @return TRUE if the cursor had to be moved.
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean moveCursorOnscreen() {
        return library.gtk_text_view_place_cursor_onscreen(getCReference());
    }

    /**
     * Determines whether iter is at the start of a display line.
     * <p>
     * See gtk_text_view_forward_display_line() for an explanation of display lines vs. paragraphs.
     *
     * @param iter A GtkTextIter
     * @return TRUE if iter begins a wrapped line.
     */
    public boolean isIteratorAtDisplayLineStart(GtkTextIter iter) {
        if (iter != null) {
            return library.gtk_text_view_starts_display_line(getCReference(), iter.getCReference());
        }
        return false;
    }

    /**
     * Gets whether the GtkTextView uses monospace styling.
     *
     * @return TRUE if monospace fonts are desired.
     */
    public boolean isMonospace() {
        return library.gtk_text_view_get_monospace(getCReference());
    }

    /**
     * Sets whether the GtkTextView should display text in monospace styling.
     *
     * @param monospace TRUE to request monospace styling.
     */
    public void setMonospace(boolean monospace) {
        library.gtk_text_view_set_monospace(getCReference(), monospace);
    }

    /**
     * Returns the default editability of the GtkTextView.
     * <p>
     * Tags in the buffer may override this setting for some ranges of text.
     *
     * @return Whether text is editable by default.
     */
    public boolean isTextEditable() {
        return library.gtk_text_view_get_editable(getCReference());
    }

    /**
     * Sets the default editability of the GtkTextView.
     * <p>
     * You can override this default setting with tags in the buffer, using the "editable" attribute of tags.
     *
     * @param isEditable Whether text is editable.
     */
    public void setTextEditable(boolean isEditable) {
        library.gtk_text_view_set_editable(getCReference(), isEditable);
    }

    /**
     * Moves the given iter backward by one display (wrapped) line.
     * <p>
     * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph separator
     * characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off, display lines and
     * paragraphs will be the same. Display lines are divided differently for each view, since they depend on the view's
     * width; paragraphs are the same in all views, since they depend on the contents of the GtkTextBuffer.
     *
     * @param iter A GtkTextIter
     * @return TRUE if iter was moved and is not on the end iterator.
     */
    public boolean moveBackwardOneDisplayLine(GtkTextIter iter) {
        if (iter != null) {
            return library.gtk_text_view_backward_display_line(getCReference(), iter.getCReference());
        }
        return false;
    }

    /**
     * Moves the given iter backward to the next display line start.
     * <p>
     * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph separator
     * characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off, display lines and
     * paragraphs will be the same. Display lines are divided differently for each view, since they depend on the view's
     * width; paragraphs are the same in all views, since they depend on the contents of the GtkTextBuffer.
     *
     * @param iter A GtkTextIter
     * @return TRUE if iter was moved and is not on the end iterator.
     */
    public boolean moveBackwardToDisplayLineStart(GtkTextIter iter) {
        if (iter != null) {
            return library.gtk_text_view_backward_display_line_start(getCReference(), iter.getCReference());
        }
        return false;
    }

    /**
     * Moves the given iter forward by one display (wrapped) line.
     * <p>
     * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph separator
     * characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off, display lines and
     * paragraphs will be the same. Display lines are divided differently for each view, since they depend on the view's
     * width; paragraphs are the same in all views, since they depend on the contents of the GtkTextBuffer.
     *
     * @param iter A GtkTextIter
     * @return TRUE if iter was moved and is not on the end iterator.
     */
    public boolean moveForwardOneDisplayLine(GtkTextIter iter) {
        if (iter != null) {
            return library.gtk_text_view_forward_display_line(getCReference(), iter.getCReference());
        }
        return false;
    }

    /**
     * Moves the given iter forward to the next display line end.
     * <p>
     * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph separator
     * characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off, display lines and
     * paragraphs will be the same. Display lines are divided differently for each view, since they depend on the view's
     * width; paragraphs are the same in all views, since they depend on the contents of the GtkTextBuffer.
     *
     * @param iter A GtkTextIter
     * @return TRUE if iter was moved and is not on the end iterator.
     */
    public boolean moveForwardToDisplayLineStart(GtkTextIter iter) {
        if (iter != null) {
            return library.gtk_text_view_forward_display_line_end(getCReference(), iter.getCReference());
        }
        return false;
    }

    /**
     * Moves a mark within the buffer so that it's located within the currently visible text area.
     *
     * @param mark A GtkTextMark
     * @return TRUE if the mark moved (wasn't already onscreen)
     */
    public boolean moveMarkOnscreen(GtkTextMark mark) {
        if (mark != null) {
            return library.gtk_text_view_move_mark_onscreen(getCReference(), mark.getCReference());
        }
        return false;
    }

    /**
     * Updates the position of a child.
     *
     * @param overlay           A widget already added with gtk_text_view_add_overlay()
     * @param xBufferCoordinate New X position in buffer coordinates.
     * @param yBufferCoordinate New Y position in buffer coordinates.
     */
    public void moveOverlay(GtkWidget overlay, int xBufferCoordinate, int yBufferCoordinate) {
        if (overlay != null) {
            library.gtk_text_view_move_overlay(getCReference(), overlay.getCReference(), xBufferCoordinate, yBufferCoordinate);
        }
    }

    /**
     * Move the iterator a given number of characters visually, treating it as the strong cursor position.
     * <p>
     * If count is positive, then the new strong cursor position will be count positions to the right of the old cursor
     * position. If count is negative then the new strong cursor position will be count positions to the left of the
     * old cursor position.
     * <p>
     * In the presence of bidirectional text, the correspondence between logical and visual order will depend on the
     * direction of the current run, and there may be jumps when the cursor is moved off of the end of a run.
     *
     * @param iter  A GtkTextIter
     * @param count Number of characters to move (negative moves left, positive moves right)
     * @return TRUE if iter moved and is not on the end iterator.
     */
    public boolean moveVisually(GtkTextIter iter, int count) {
        if (iter != null) {
            return library.gtk_text_view_move_visually(getCReference(), iter.getCReference(), count);
        }
        return false;
    }

    /**
     * Removes a child widget from text_view.
     *
     * @param w The child to remove.
     */
    public void removeChild(GtkWidget w) {
        if (w != null) {
            library.gtk_text_view_remove(getCReference(), w.getCReference());
        }
    }

    /**
     * Reset the input method context of the text view if needed.
     * <p>
     * This can be necessary in the case where modifying the buffer would confuse ongoing input method behavior.
     */
    public void resetInputMethodContext() {
        library.gtk_text_view_reset_im_context(getCReference());
    }

    /**
     * Scrolls text_view the minimum distance such that mark is contained within the visible area of the widget.
     *
     * @param mark A mark in the buffer for text_view.
     */
    public void scrollMarkOnscreen(GtkTextMark mark) {
        if (mark != null) {
            library.gtk_text_view_scroll_mark_onscreen(getCReference(), mark.getCReference());
        }
    }

    /**
     * Scrolls text_view so that iter is on the screen in the position indicated by xalign and yalign.
     * <p>
     * The text scrolls the minimal distance to get the mark onscreen, possibly not scrolling at all.
     * The effective screen for purposes of this function is reduced by a margin of size within_margin.
     * <p>
     * Note that this function uses the currently-computed height of the lines in the text buffer
     * . Line heights are computed in an idle handler; so this function may not have the desired effect if it's
     * called before the height computations. To avoid oddness, consider using gtk_text_view_scroll_to_mark()
     * which saves a point to be scrolled to after line validation.
     *
     * @param iter         A GtkTextIter
     * @param withinMargin Margin as a [0.0,0.5) fraction of screen size.
     * @return TRUE if scrolling occurred.
     */
    public boolean scrollToIterator(GtkTextIter iter, double withinMargin) {
        if (iter != null) {
            return library.gtk_text_view_scroll_to_iter(getCReference(), iter.getCReference(), Math.max(0.001, Math.min(withinMargin, 0.5)), false, 0, 0);
        }
        return false;
    }

    /**
     * Scrolls text_view so that iter is on the screen in the position indicated by xalign and yalign.
     * <p>
     * An alignment of 0.0 indicates left or top, 1.0 indicates right or bottom, 0.5 means center.
     * The effective screen for purposes of this function is reduced by a margin of size within_margin.
     * <p>
     * Note that this function uses the currently-computed height of the lines in the text buffer. Line heights are
     * computed in an idle handler; so this function may not have the desired effect if it's called before the height
     * computations. To avoid oddness, consider using gtk_text_view_scroll_to_mark() which saves a point to be scrolled
     * to after line validation.
     *
     * @param iter         A GtkTextIter
     * @param withinMargin Margin as a [0.0,0.5) fraction of screen size.
     * @param xAlign       Horizontal alignment of mark within visible area.
     * @param yAlign       Vertical alignment of mark within visible area.
     * @return TRUE if scrolling occurred.
     */
    public boolean scrollToIterator(GtkTextIter iter, double withinMargin, double xAlign, double yAlign) {
        if (iter != null) {
            return library.gtk_text_view_scroll_to_iter(getCReference(), iter.getCReference(), withinMargin, true, xAlign, yAlign);
        }
        return false;
    }

    /**
     * Scrolls text_view so that mark is on the screen in the position indicated by xalign and yalign.
     * <p>
     * An alignment of 0.0 indicates left or top, 1.0 indicates right or bottom, 0.5 means center.
     * The effective screen for purposes of this function is reduced by a margin of size within_margin.
     *
     * @param mark         A GtkTextMark
     * @param withinMargin Margin as a [0.0,0.5) fraction of screen size.
     * @param xAlign       Horizontal alignment of mark within visible area.
     * @param yAlign       Vertical alignment of mark within visible area.
     */
    public void scrollToMark(GtkTextMark mark, double withinMargin, double xAlign, double yAlign) {
        if (mark != null) {
            library.gtk_text_view_scroll_to_mark(getCReference(), mark.getCReference(), withinMargin, true, xAlign, yAlign);
        }
    }

    /**
     * Scrolls text_view so that mark is on the screen in the position indicated by xalign and yalign.
     * <p>
     * An alignment of 0.0 indicates left or top, 1.0 indicates right or bottom, 0.5 means center.
     * The text scrolls the minimal distance to get the mark onscreen, possibly not scrolling at all.
     * The effective screen for purposes of this function is reduced by a margin of size within_margin.
     *
     * @param mark         A GtkTextMark
     * @param withinMargin Margin as a [0.0,0.5) fraction of screen size.
     */
    public void scrollToMark(GtkTextMark mark, double withinMargin) {
        if (mark != null) {
            library.gtk_text_view_scroll_to_mark(getCReference(), mark.getCReference(), Math.max(0.001, Math.min(withinMargin, 0.5)), false, 0, 0);
        }
    }

    /**
     * Places widget into the gutter specified by win.
     * <p>
     * win must be one of GTK_TEXT_WINDOW_LEFT, GTK_TEXT_WINDOW_RIGHT, GTK_TEXT_WINDOW_TOP, or GTK_TEXT_WINDOW_BOTTOM.
     *
     * @param win    A GtkTextWindowType
     * @param gutter A GtkWidget
     *               <p>
     *               The argument can be NULL.
     */
    public void setGutter(GtkTextWindowType win, GtkWidget gutter) {
        if (win != null && GtkTextWindowType.isSubset(win, GtkTextWindowType.GTK_TEXT_WINDOW_LEFT, GtkTextWindowType.GTK_TEXT_WINDOW_RIGHT, GtkTextWindowType.GTK_TEXT_WINDOW_TOP, GtkTextWindowType.GTK_TEXT_WINDOW_BOTTOM)) {
            library.gtk_text_view_set_gutter(getCReference(), GtkTextWindowType.getCValueFromType(win), pointerOrNull(gutter));
        }
    }

    /**
     * Sets the behavior of the text widget when the Tab key is pressed.
     * <p>
     * If accepts_tab is TRUE, a tab character is inserted. If accepts_tab is FALSE the keyboard focus is moved to the
     * next widget in the focus chain.
     *
     * @param doesAcceptTabs TRUE if pressing the Tab key should insert a tab character, FALSE, if pressing the Tab key
     *                       should move the keyboard focus.
     */
    public void shouldAcceptTabs(boolean doesAcceptTabs) {
        library.gtk_text_view_set_accepts_tab(getCReference(), doesAcceptTabs);
    }

    /**
     * Changes the GtkTextView overwrite mode.
     *
     * @param doesOverwrite TRUE to turn on overwrite mode, FALSE to turn it off.
     */
    public void shouldOverwrite(boolean doesOverwrite) {
        library.gtk_text_view_set_overwrite(getCReference(), doesOverwrite);
    }

    /**
     * Converts buffer coordinates to window coordinates.
     *
     * @param type    A GtkTextWindowType
     * @param bufferX Buffer x coordinate.
     * @param bufferY Buffer y coordinate
     * @return If defined, first - window x coordinate, second - window y coordinate
     */
    public Option<Pair<Integer, Integer>> translateBufferCoordinatesToWindowCoordinates(GtkTextWindowType type, int bufferX, int bufferY) {
        if (type != null) {
            PointerByReference windowX = new PointerByReference();
            PointerByReference windowY = new PointerByReference();
            library.gtk_text_view_buffer_to_window_coords(getCReference(), GtkTextWindowType.getCValueFromType(type), bufferX, bufferY, windowX, windowY);
            return new Option<>(new Pair<>(windowX.getPointer().getInt(0), windowY.getPointer().getInt(0)));
        }
        return Option.NONE;
    }

    /**
     * Converts coordinates on the window identified by win to buffer coordinates.
     *
     * @param type    A GtkTextWindowType
     * @param windowX Window x coordinate.
     * @param windowY Window y coordinate.
     * @return if defined, first - buffer x coordinate, second - buffer y coordinate
     */
    public Option<Pair<Integer, Integer>> translateWindowCoordinatesToBufferCoordinates(GtkTextWindowType type, int windowX, int windowY) {
        if (type != null) {
            PointerByReference bufferX = new PointerByReference();
            PointerByReference bufferY = new PointerByReference();
            library.gtk_text_view_window_to_buffer_coords(getCReference(), GtkTextWindowType.getCValueFromType(type), windowX, windowY, bufferX, bufferY);
            return new Option<>(new Pair<>(bufferX.getPointer().getInt(0), bufferY.getPointer().getInt(0)));
        }
        return Option.NONE;
    }

    public static final class Signals extends GtkWidget.Signals {

        /**
         * Gets emitted when the user hits the backspace button
         */
        public static final Signals BACKSPACE = new Signals("backspace");

        /**
         * Gets emitted to copy the selection to the clipboard.
         */
        public static final Signals COPY_CLIPBOARD = new Signals("copy-clipboard");

        /**
         * Gets emitted to cut the selection to the clipboard.
         */
        public static final Signals CUT_CLIPBOARD = new Signals("cut-clipboard");

        /**
         * Gets emitted when the user initiates a text deletion.
         */
        public static final Signals DELETE_FROM_CURSOR = new Signals("delete-from-cursor");

        /**
         * Emitted when the selection needs to be extended at location.
         */
        public static final Signals EXTEND_SELECTION = new Signals("extend-selection");

        /**
         * Gets emitted when the user initiates the insertion of a fixed string at the cursor.
         */
        public static final Signals INSERT_AT_CURSOR = new Signals("insert-at-cursor");

        /**
         * Gets emitted to present the Emoji chooser for the text_view.
         */
        public static final Signals INSERT_EMOJI = new Signals("insert-emoji");

        /**
         * Gets emitted when the user initiates a cursor movement.
         */
        public static final Signals MOVE_CURSOR = new Signals("move-cursor");

        /**
         * Gets emitted to move the viewport.
         */
        public static final Signals MOVE_VIEWPORT = new Signals("move-viewport");

        /**
         * Gets emitted to paste the contents of the clipboard into the text view.
         */
        public static final Signals PASTE_CLIPBOARD = new Signals("paste-clipboard");

        /**
         * Emitted when preedit text of the active input method changes.
         */
        public static final Signals PRE_EDIT_CHANGED = new Signals("preedit-changed");

        /**
         * Gets emitted to select or unselect the complete contents of the text view.
         */
        public static final Signals SELECT_ALL = new Signals("select-all");

        /**
         * Gets emitted when the user initiates settings the "anchor" mark.
         */
        public static final Signals SET_ANCHOR = new Signals("set-anchor");

        /**
         * Gets emitted to toggle the cursor-visible property.
         */
        public static final Signals TOGGLE_CURSOR_VISIBLE = new Signals("toggle-cursor-visible");

        /**
         * Gets emitted to toggle the overwrite mode of the text view.
         */
        public static final Signals TOGGLE_OVERWRITE = new Signals("toggle-overwrite");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkTextViewLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a child widget in the text buffer, at the given anchor.
         *
         * @param text_view self
         * @param child     A GtkWidget
         * @param anchor    A GtkTextChildAnchor in the GtkTextBuffer for text_view.
         */
        public native void gtk_text_view_add_child_at_anchor(Pointer text_view, Pointer child, Pointer anchor);

        /**
         * Adds child at a fixed coordinate in the GtkTextView's text window.
         * <p>
         * The xpos and ypos must be in buffer coordinates (see gtk_text_view_get_iter_location() to convert to buffer
         * coordinates).
         * <p>
         * child will scroll with the text view.
         * <p>
         * If instead you want a widget that will not move with the GtkTextView contents see GtkOverlay.
         *
         * @param text_view self
         * @param child     A GtkWidget
         * @param xpos      X position of child in window coordinates.
         * @param ypos      Y position of child in window coordinates.
         */
        public native void gtk_text_view_add_overlay(Pointer text_view, Pointer child, int xpos, int ypos);

        /**
         * Moves the given iter backward by one display (wrapped) line.
         * <p>
         * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph
         * separator characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off,
         * display lines and paragraphs will be the same. Display lines are divided differently for each view, since
         * they depend on the view's width; paragraphs are the same in all views, since they depend on the contents of
         * the GtkTextBuffer
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @return TRUE if iter was moved and is not on the end iterator.
         */
        public native boolean gtk_text_view_backward_display_line(Pointer text_view, Pointer iter);

        /**
         * Moves the given iter backward to the next display line start.
         * <p>
         * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph
         * separator characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off,
         * display lines and paragraphs will be the same. Display lines are divided differently for each view, since
         * they depend on the view's width; paragraphs are the same in all views, since they depend on the contents of
         * the GtkTextBuffer.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @return TRUE if iter was moved and is not on the end iterator.
         */
        public native boolean gtk_text_view_backward_display_line_start(Pointer text_view, Pointer iter);

        /**
         * Converts buffer coordinates to window coordinates.
         *
         * @param text_view self
         * @param win       A GtkTextWindowType
         * @param buffer_x  Buffer x coordinate.
         * @param buffer_y  Buffer y coordinate.
         * @param window_x  Window x coordinate return location.
         *                  <p>
         *                  The argument can be NULL.
         * @param window_y  Window y coordinate return location.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_text_view_buffer_to_window_coords(Pointer text_view, int win, int buffer_x, int buffer_y, PointerByReference window_x, PointerByReference window_y);

        /**
         * Moves the given iter forward by one display (wrapped) line.
         * <p>
         * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph
         * separator characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off,
         * display lines and paragraphs will be the same. Display lines are divided differently for each view, since
         * they depend on the view's width; paragraphs are the same in all views, since they depend on the contents of
         * the GtkTextBuffer.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @return TRUE if iter was moved and is not on the end iterator.
         */
        public native boolean gtk_text_view_forward_display_line(Pointer text_view, Pointer iter);

        /**
         * Moves the given iter forward to the next display line end.
         * <p>
         * A display line is different from a paragraph. Paragraphs are separated by newlines or other paragraph
         * separator characters. Display lines are created by line-wrapping a paragraph. If wrapping is turned off,
         * display lines and paragraphs will be the same. Display lines are divided differently for each view, since
         * they depend on the view's width; paragraphs are the same in all views, since they depend on the contents of
         * the GtkTextBuffer.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @return TRUE if iter was moved and is not on the end iterator.
         */
        public native boolean gtk_text_view_forward_display_line_end(Pointer text_view, Pointer iter);

        /**
         * Returns whether pressing the Tab key inserts a tab characters.
         * <p>
         * See gtk_text_view_set_accepts_tab().
         *
         * @param text_view self
         * @return TRUE if pressing the Tab key inserts a tab character, FALSE if pressing the Tab key moves the
         *         keyboard focus.
         */
        public native boolean gtk_text_view_get_accepts_tab(Pointer text_view);

        /**
         * Gets the bottom margin for text in the text_view.
         *
         * @param text_view self
         * @return Bottom margin in pixels.
         */
        public native int gtk_text_view_get_bottom_margin(Pointer text_view);

        /**
         * Returns the GtkTextBuffer being displayed by this text view.
         * <p>
         * The reference count on the buffer is not incremented; the caller of this function won't own a new reference.
         *
         * @param text_view self
         * @return A GtkTextBuffer
         */
        public native Pointer gtk_text_view_get_buffer(Pointer text_view);

        /**
         * Determine the positions of the strong and weak cursors if the insertion point is at iter.
         * <p>
         * The position of each cursor is stored as a zero-width rectangle. The strong cursor location is the location
         * where characters of the directionality equal to the base direction of the paragraph are inserted. The weak
         * cursor location is the location where characters of the directionality opposite to the base direction of the
         * paragraph are inserted.
         * <p>
         * If iter is NULL, the actual cursor position is used.
         * <p>
         * Note that if iter happens to be the actual cursor position, and there is currently an IM preedit sequence
         * being entered, the returned locations will be adjusted to account for the preedit cursor's offset within the
         * preedit sequence.
         * <p>
         * The rectangle position is in buffer coordinates; use gtk_text_view_buffer_to_window_coords() to convert these
         * coordinates to coordinates for one of the windows in the text view.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         *                  <p>
         *                  The argument can be NULL.
         * @param strong    Location to store the strong cursor position.
         *                  <p>
         *                  The argument can be NULL.
         * @param weak      Location to store the weak cursor position.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_text_view_get_cursor_locations(Pointer text_view, Pointer iter, GdkRectangle.GdkRectangleStruct.ByReference strong, GdkRectangle.GdkRectangleStruct.ByReference weak);

        /**
         * Find out whether the cursor should be displayed.
         *
         * @param text_view self
         * @return Whether the insertion mark is visible.
         */
        public native boolean gtk_text_view_get_cursor_visible(Pointer text_view);

        /**
         * Returns the default editability of the GtkTextView.
         * <p>
         * Tags in the buffer may override this setting for some ranges of text.
         *
         * @param text_view self
         * @return Whether text is editable by default.
         */
        public native boolean gtk_text_view_get_editable(Pointer text_view);

        /**
         * Gets the menu model that gets added to the context menu or NULL if none has been set.
         *
         * @param text_view self
         * @return The menu model.
         */
        public native Pointer gtk_text_view_get_extra_menu(Pointer text_view);

        /**
         * Gets a GtkWidget that has previously been set as gutter.
         * <p>
         * See gtk_text_view_set_gutter().
         * <p>
         * win must be one of GTK_TEXT_WINDOW_LEFT, GTK_TEXT_WINDOW_RIGHT, GTK_TEXT_WINDOW_TOP, or
         * GTK_TEXT_WINDOW_BOTTOM.
         *
         * @param text_view self
         * @param win       A GtkTextWindowType
         * @return A GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_text_view_get_gutter(Pointer text_view, int win);

        /**
         * Gets the default indentation of paragraphs in text_view.
         * <p>
         * Tags in the view's buffer may override the default. The indentation may be negative.
         *
         * @param text_view self
         * @return Number of pixels of indentation.
         */
        public native int gtk_text_view_get_indent(Pointer text_view);

        /**
         * Gets the input-hints of the GtkTextView.
         *
         * @param text_view self
         * @return Type: GtkInputHints. Which describes hints that might be taken into account by input methods or
         *         applications.
         *         <p>
         *         Note that input methods may already tailor their behaviour according to the GtkInputPurpose of the
         *         entry.
         *         <p>
         *         Some common sense is expected when using these flags - mixing GTK_INPUT_HINT_LOWERCASE with any of
         *         the
         *         uppercase hints makes no sense.
         */
        public native int gtk_text_view_get_input_hints(Pointer text_view);

        /**
         * Gets the input-purpose of the GtkTextView.
         *
         * @param text_view self
         * @return Type: GtkInputPurpose. Describes primary purpose of the input widget.
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
         *         accepts
         *         only digits while the latter also some punctuation (like commas or points, plus, minus) and "e" or
         *         "E" as in
         *         3.14E+000.
         */
        public native int gtk_text_view_get_input_purpose(Pointer text_view);

        /**
         * Retrieves the iterator at buffer coordinates x and y.
         * <p>
         * Buffer coordinates are coordinates for the entire buffer, not just the currently-displayed portion.
         * If you have coordinates from an event, you have to convert those to buffer coordinates with
         * gtk_text_view_window_to_buffer_coords().
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @param x         X position, in buffer coordinates.
         * @param y         Y position, in buffer coordinates.
         * @return TRUE if the position is over text.
         */
        public native boolean gtk_text_view_get_iter_at_location(Pointer text_view, PointerByReference iter, int x, int y);

        /**
         * Retrieves the iterator pointing to the character at buffer coordinates x and y.
         * <p>
         * Buffer coordinates are coordinates for the entire buffer, not just the currently-displayed portion.
         * If you have coordinates from an event, you have to convert those to buffer coordinates with
         * gtk_text_view_window_to_buffer_coords().
         * <p>
         * Note that this is different from gtk_text_view_get_iter_at_location(), which returns cursor locations,
         * i.e. positions between characters.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @param trailing  If non-NULL, location to store an integer indicating where in the grapheme the user clicked.
         *                  It will either be zero, or the number of characters in the grapheme. 0 represents the
         *                  trailing edge of the grapheme.
         *                  <p>
         *                  The argument can be NULL.
         * @param x         X position, in buffer coordinates.
         * @param y         Y position, in buffer coordinates.
         * @return TRUE if the position is over text.
         */
        public native boolean gtk_text_view_get_iter_at_position(Pointer text_view, PointerByReference iter, Pointer trailing, int x, int y);

        /**
         * Gets a rectangle which roughly contains the character at iter.
         * <p>
         * The rectangle position is in buffer coordinates; use gtk_text_view_buffer_to_window_coords() to convert
         * these coordinates to coordinates for one of the windows in the text view.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @param location  Bounds of the character at iter.
         */
        public native void gtk_text_view_get_iter_location(Pointer text_view, Pointer iter, GdkRectangle.GdkRectangleStruct.ByReference location);

        /**
         * Gets the default justification of paragraphs in text_view.
         * <p>
         * Tags in the buffer may override the default.
         *
         * @param text_view self
         * @return Default justification. Type: GtkJustification
         */
        public native int gtk_text_view_get_justification(Pointer text_view);

        /**
         * Gets the default left margin size of paragraphs in the text_view.
         * <p>
         * Tags in the buffer may override the default.
         *
         * @param text_view self
         * @return Left margin in pixels.
         */
        public native int gtk_text_view_get_left_margin(Pointer text_view);

        /**
         * Gets the GtkTextIter at the start of the line containing the coordinate y.
         * <p>
         * y is in buffer coordinates, convert from window coordinates with gtk_text_view_window_to_buffer_coords().
         * If non-NULL, line_top will be filled with the coordinate of the top edge of the line.
         *
         * @param text_view   self
         * @param target_iter A GtkTextIter
         * @param y           A y coordinate.
         * @param line_top    Return location for top coordinate of the line.
         */
        public native void gtk_text_view_get_line_at_y(Pointer text_view, PointerByReference target_iter, int y, PointerByReference line_top);

        /**
         * Gets the y coordinate of the top of the line containing iter, and the height of the line.
         * <p>
         * The coordinate is a buffer coordinate; convert to window coordinates with
         * gtk_text_view_buffer_to_window_coords().
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @param y         Return location for a y coordinate.
         * @param height    Return location for a height.
         */
        public native void gtk_text_view_get_line_yrange(Pointer text_view, Pointer iter, PointerByReference y, PointerByReference height);

        /**
         * Gets the PangoContext that is used for rendering LTR directed text layouts.
         * <p>
         * The context may be replaced when CSS changes occur.
         *
         * @param text_view self
         * @return A PangoContext
         * @since 4.4
         */
        public native Pointer gtk_text_view_get_ltr_context(Pointer text_view);

        /**
         * Gets whether the GtkTextView uses monospace styling.
         *
         * @param text_view self
         * @return TRUE if monospace fonts are desired.
         */
        public native boolean gtk_text_view_get_monospace(Pointer text_view);

        /**
         * Returns whether the GtkTextView is in overwrite mode
         *
         * @param text_view self
         * @return Whether text_view is in overwrite mode or not.
         */
        public native boolean gtk_text_view_get_overwrite(Pointer text_view);

        /**
         * Gets the default number of pixels to put above paragraphs.
         * <p>
         * Adding this function with gtk_text_view_get_pixels_below_lines() is equal to the line space between each
         * paragraph.
         *
         * @param text_view self
         * @return Default number of pixels above paragraphs.
         */
        public native int gtk_text_view_get_pixels_above_lines(Pointer text_view);

        /**
         * Gets the default number of pixels to put below paragraphs.
         * <p>
         * The line space is the sum of the value returned by this function and the value returned by
         * gtk_text_view_get_pixels_above_lines().
         *
         * @param text_view self
         * @return Default number of blank pixels below paragraphs.
         */
        public native int gtk_text_view_get_pixels_below_lines(Pointer text_view);

        /**
         * Gets the default number of pixels to put between wrapped lines inside a paragraph.
         *
         * @param text_view self
         * @return Default number of pixels of blank space between wrapped lines.
         */
        public native int gtk_text_view_get_pixels_inside_wrap(Pointer text_view);

        /**
         * Gets the default right margin for text in text_view.
         * <p>
         * Tags in the buffer may override the default.
         *
         * @param text_view self
         * @return Right margin in pixels.
         */
        public native int gtk_text_view_get_right_margin(Pointer text_view);

        /**
         * Gets the PangoContext that is used for rendering RTL directed text layouts.
         * <p>
         * The context may be replaced when CSS changes occur.
         *
         * @param text_view self
         * @return A PangoContext
         * @since 4.4
         */
        public native Pointer gtk_text_view_get_rtl_context(Pointer text_view);

        /**
         * Gets the default tabs for text_view.
         * <p>
         * Tags in the buffer may override the defaults. The returned array will be NULL if "standard" (8-space)
         * tabs are used. Free the return value with pango_tab_array_free().
         *
         * @param text_view self
         * @return Copy of default tab array, or NULL if standard tabs are used; must be freed with
         *         pango_tab_array_free(). Type: PangoTabArray
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_text_view_get_tabs(Pointer text_view);

        /**
         * Gets the top margin for text in the text_view.
         *
         * @param text_view self
         * @return Top margin in pixels.
         */
        public native int gtk_text_view_get_top_margin(Pointer text_view);

        /**
         * Fills visible_rect with the currently-visible region of the buffer, in buffer coordinates.
         * <p>
         * Convert to window coordinates with gtk_text_view_buffer_to_window_coords().
         *
         * @param text_view    self
         * @param visible_rect Rectangle to fill.
         */
        public native void gtk_text_view_get_visible_rect(Pointer text_view, GdkRectangle.GdkRectangleStruct.ByReference visible_rect);

        /**
         * Gets the line wrapping for the view.
         *
         * @param text_view self
         * @return The line wrap setting. Type: GtkWrapMode
         */
        public native int gtk_text_view_get_wrap_mode(Pointer text_view);

        /**
         * Allow the GtkTextView input method to internally handle key press and release events.
         * <p>
         * If this function returns TRUE, then no further processing should be done for this key event.
         * See gtk_im_context_filter_keypress().
         * <p>
         * Note that you are expected to call this function from your handler when overriding key event handling.
         * This is needed in the case when you need to insert your own key handling between the input method and the
         * default key event handling of the GtkTextView.
         *
         * @param text_view self
         * @param event     The key event. Type: GdkEvent
         * @return TRUE if the input method handled the key event.
         */
        public native boolean gtk_text_view_im_context_filter_keypress(Pointer text_view, Pointer event);

        /**
         * Moves a mark within the buffer so that it's located within the currently-visible text area.
         *
         * @param text_view self
         * @param mark      A GtkTextMark
         * @return TRUE if the mark moved (wasn't already onscreen)
         */
        public native boolean gtk_text_view_move_mark_onscreen(Pointer text_view, Pointer mark);

        /**
         * Updates the position of a child.
         * <p>
         * See gtk_text_view_add_overlay().
         *
         * @param text_view self
         * @param child     A widget already added with gtk_text_view_add_overlay(). Type: GtkWidget
         * @param xpos      New X position in buffer coordinates.
         * @param ypos      New Y position in buffer coordinates.
         */
        public native void gtk_text_view_move_overlay(Pointer text_view, Pointer child, int xpos, int ypos);

        /**
         * Move the iterator a given number of characters visually, treating it as the strong cursor position.
         * <p>
         * If count is positive, then the new strong cursor position will be count positions to the right of the old
         * cursor position. If count is negative then the new strong cursor position will be count positions to the
         * left of the old cursor position.
         * <p>
         * In the presence of bidirectional text, the correspondence between logical and visual order will depend on
         * the direction of the current run, and there may be jumps when the cursor is moved off of the end of a run.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @param count     Number of characters to move (negative moves left, positive moves right)
         * @return TRUE if iter moved and is not on the end iterator.
         */
        public native boolean gtk_text_view_move_visually(Pointer text_view, Pointer iter, int count);

        /**
         * Creates a new GtkTextView.
         * <p>
         * If you don't call gtk_text_view_set_buffer() before using the text view, an empty default buffer will be
         * created for you. Get the buffer with gtk_text_view_get_buffer(). If you want to specify your own buffer,
         * consider gtk_text_view_new_with_buffer().
         *
         * @return A new GtkTextView
         */
        public native Pointer gtk_text_view_new();

        /**
         * Creates a new GtkTextView widget displaying the buffer 'buffer'.
         * <p>
         * One buffer can be shared among many widgets. buffer may be NULL to create a default buffer, in which case
         * this function is equivalent to gtk_text_view_new(). The text view adds its own reference count to the
         * buffer; it does not take over an existing reference.
         *
         * @param buffer A GtkTextBuffer
         * @return A new GtkTextView.
         */
        public native Pointer gtk_text_view_new_with_buffer(Pointer buffer);

        /**
         * Moves the cursor to the currently visible region of the buffer.
         *
         * @param text_view self
         * @return TRUE if the cursor had to be moved.
         */
        public native boolean gtk_text_view_place_cursor_onscreen(Pointer text_view);

        /**
         * Removes a child widget from text_view.
         *
         * @param text_view self
         * @param child     The child to remove. Type: GtkWidget
         */
        public native void gtk_text_view_remove(Pointer text_view, Pointer child);

        /**
         * Ensures that the cursor is shown.
         * <p>
         * This also resets the time that it will stay blinking (or visible, in case blinking is disabled).
         * <p>
         * This function should be called in response to user input (e.g. from derived classes that override the
         * textview's event handlers).
         *
         * @param text_view self
         */
        public native void gtk_text_view_reset_cursor_blink(Pointer text_view);

        /**
         * Reset the input method context of the text view if needed.
         * <p>
         * This can be necessary in the case where modifying the buffer would confuse ongoing input method behavior.
         *
         * @param text_view self
         */
        public native void gtk_text_view_reset_im_context(Pointer text_view);

        /**
         * Scrolls text_view the minimum distance such that mark is contained within the visible area of the widget.
         *
         * @param text_view self
         * @param mark      A mark in the buffer for text_view. Type: GtkTextMark
         */
        public native void gtk_text_view_scroll_mark_onscreen(Pointer text_view, Pointer mark);

        /**
         * Scrolls text_view so that iter is on the screen in the position indicated by xalign and yalign.
         * <p>
         * An alignment of 0.0 indicates left or top, 1.0 indicates right or bottom, 0.5 means center. If 'use_align' is
         * FALSE, the text scrolls the minimal distance to get the mark onscreen, possibly not scrolling at all.
         * The effective screen for purposes of this function is reduced by a margin of size within_margin.
         * <p>
         * Note that this function uses the currently-computed height of the lines in the text buffer. Line heights are
         * computed in an idle handler; so this function may not have the desired effect if it's called before the
         * height computations. To avoid oddness, consider using gtk_text_view_scroll_to_mark() which saves a point
         * to be scrolled to after line validation.
         *
         * @param text_view     self
         * @param iter          A GtkTextIter
         * @param within_margin Margin as a [0.0,0.5) fraction of screen size.
         * @param use_align     Whether to use alignment arguments (if FALSE, just get the mark onscreen)
         * @param xalign        Horizontal alignment of mark within visible area.
         * @param yalign        Vertical alignment of mark within visible area.
         * @return TRUE if scrolling occurred.
         */
        public native boolean gtk_text_view_scroll_to_iter(Pointer text_view, Pointer iter, double within_margin, boolean use_align, double xalign, double yalign);

        /**
         * Scrolls text_view so that mark is on the screen in the position indicated by xalign and yalign.
         * <p>
         * An alignment of 0.0 indicates left or top, 1.0 indicates right or bottom, 0.5 means center. If 'use_align' is
         * FALSE, the text scrolls the minimal distance to get the mark onscreen, possibly not scrolling at all. The
         * effective screen for purposes of this function is reduced by a margin of size within_margin.
         *
         * @param text_view     self
         * @param mark          A GtkTextMark
         * @param within_margin Margin as a [0.0,0.5) fraction of screen size.
         * @param use_align     Whether to use alignment arguments (if FALSE, just get the mark onscreen)
         * @param xalign        Horizontal alignment of mark within visible area.
         * @param yalign        Vertical alignment of mark within visible area.
         */
        public native void gtk_text_view_scroll_to_mark(Pointer text_view, Pointer mark, double within_margin, boolean use_align, double xalign, double yalign);

        /**
         * Sets the behavior of the text widget when the Tab key is pressed.
         * <p>
         * If accepts_tab is TRUE, a tab character is inserted. If accepts_tab is FALSE the keyboard focus is moved to
         * the next widget in the focus chain.
         * <p>
         * Focus can always be moved using Ctrl+Tab.
         *
         * @param text_view   self
         * @param accepts_tab TRUE if pressing the Tab key should insert a tab character, FALSE, if pressing the Tab
         *                    key should move the keyboard focus.
         */
        public native void gtk_text_view_set_accepts_tab(Pointer text_view, boolean accepts_tab);

        /**
         * Sets the bottom margin for text in text_view.
         * <p>
         * Note that this function is confusingly named. In CSS terms, the value set here is padding.
         *
         * @param text_view     self
         * @param bottom_margin Bottom margin in pixels.
         */
        public native void gtk_text_view_set_bottom_margin(Pointer text_view, int bottom_margin);

        /**
         * Sets buffer as the buffer being displayed by text_view.
         * <p>
         * The previous buffer displayed by the text view is unreferenced, and a reference is added to buffer. If you
         * owned a reference to buffer before passing it to this function, you must remove that reference yourself;
         * GtkTextView will not "adopt" it.
         *
         * @param text_view self
         * @param buffer    A GtkTextBuffer
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_text_view_set_buffer(Pointer text_view, Pointer buffer);

        /**
         * Toggles whether the insertion point should be displayed.
         * <p>
         * A buffer with no editable text probably shouldn't have a visible cursor, so you may want to turn the cursor
         * off.
         * <p>
         * Note that this property may be overridden by the GtkSettings:gtk-keynav-use-caret setting.
         *
         * @param text_view self
         * @param setting   Whether to show the insertion cursor.
         */
        public native void gtk_text_view_set_cursor_visible(Pointer text_view, boolean setting);

        /**
         * Sets the default editability of the GtkTextView.
         * <p>
         * You can override this default setting with tags in the buffer, using the "editable" attribute of tags.
         *
         * @param text_view self
         * @param setting   Whether it's editable.
         */
        public native void gtk_text_view_set_editable(Pointer text_view, boolean setting);

        /**
         * Sets a menu model to add when constructing the context menu for text_view.
         * <p>
         * You can pass NULL to remove a previously set extra menu.
         *
         * @param text_view self
         * @param model     A GMenuModel
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_text_view_set_extra_menu(Pointer text_view, Pointer model);

        /**
         * Places widget into the gutter specified by win.
         * <p>
         * win must be one of GTK_TEXT_WINDOW_LEFT, GTK_TEXT_WINDOW_RIGHT, GTK_TEXT_WINDOW_TOP, or
         * GTK_TEXT_WINDOW_BOTTOM.
         *
         * @param text_view self
         * @param win       A GtkTextWindowType
         * @param widget    A GtkWidget
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_text_view_set_gutter(Pointer text_view, int win, Pointer widget);

        /**
         * Sets the default indentation for paragraphs in text_view.
         * <p>
         * Tags in the buffer may override the default.
         *
         * @param text_view self
         * @param indent    Indentation in pixels.
         */
        public native void gtk_text_view_set_indent(Pointer text_view, int indent);

        /**
         * Sets the input-hints of the GtkTextView.
         * <p>
         * The input-hints allow input methods to fine-tune their behaviour.
         *
         * @param text_view self
         * @param hints     The hints. Type: GtkInputHints
         */
        public native void gtk_text_view_set_input_hints(Pointer text_view, int hints);

        /**
         * Sets the input-purpose of the GtkTextView.
         * <p>
         * The input-purpose can be used by on-screen keyboards and other input methods to adjust their behaviour.
         *
         * @param text_view self
         * @param purpose   The purpose. Type: GtkInputPurpose
         */
        public native void gtk_text_view_set_input_purpose(Pointer text_view, int purpose);

        /**
         * Sets the default justification of text in text_view.
         * <p>
         * Tags in the view's buffer may override the default.
         *
         * @param text_view     self
         * @param justification Justification. Type: GtkJustification
         */
        public native void gtk_text_view_set_justification(Pointer text_view, int justification);

        /**
         * Sets the default left margin for text in text_view.
         * <p>
         * Tags in the buffer may override the default.
         * <p>
         * Note that this function is confusingly named. In CSS terms, the value set here is padding.
         *
         * @param text_view   self
         * @param left_margin Left margin in pixels.
         */
        public native void gtk_text_view_set_left_margin(Pointer text_view, int left_margin);

        /**
         * Sets whether the GtkTextView should display text in monospace styling.
         *
         * @param text_view self
         * @param monospace TRUE to request monospace styling.
         */
        public native void gtk_text_view_set_monospace(Pointer text_view, boolean monospace);

        /**
         * Changes the GtkTextView overwrite mode.
         *
         * @param text_view self
         * @param overwrite TRUE to turn on overwrite mode, FALSE to turn it off.
         */
        public native void gtk_text_view_set_overwrite(Pointer text_view, boolean overwrite);

        /**
         * Sets the default number of blank pixels above paragraphs in text_view.
         * <p>
         * Tags in the buffer for text_view may override the defaults.
         *
         * @param text_view          self
         * @param pixels_above_lines Pixels above paragraphs.
         */
        public native void gtk_text_view_set_pixels_above_lines(Pointer text_view, int pixels_above_lines);

        /**
         * Sets the default number of pixels of blank space to put below paragraphs in text_view.
         * <p>
         * May be overridden by tags applied to text_view's buffer.
         *
         * @param text_view          self
         * @param pixels_below_lines Pixels below paragraphs.
         */
        public native void gtk_text_view_set_pixels_below_lines(Pointer text_view, int pixels_below_lines);

        /**
         * Sets the default number of pixels of blank space to leave between display/wrapped lines within a paragraph.
         * <p>
         * May be overridden by tags in text_view's buffer.
         *
         * @param text_view          self
         * @param pixels_inside_wrap Default number of pixels between wrapped lines.
         */
        public native void gtk_text_view_set_pixels_inside_wrap(Pointer text_view, int pixels_inside_wrap);

        /**
         * Sets the default right margin for text in the text view.
         * <p>
         * Tags in the buffer may override the default.
         * <p>
         * Note that this function is confusingly named. In CSS terms, the value set here is padding.
         *
         * @param text_view    self
         * @param right_margin Right margin in pixels.
         */
        public native void gtk_text_view_set_right_margin(Pointer text_view, int right_margin);

        /**
         * Sets the default tab stops for paragraphs in text_view.
         * <p>
         * Tags in the buffer may override the default.
         *
         * @param text_view self
         * @param tabs      Tabs as a PangoTabArray
         */
        public native void gtk_text_view_set_tabs(Pointer text_view, Pointer tabs);

        /**
         * Sets the top margin for text in text_view.
         * <p>
         * Note that this function is confusingly named. In CSS terms, the value set here is padding.
         *
         * @param text_view  self
         * @param top_margin Top margin in pixels.
         */
        public native void gtk_text_view_set_top_margin(Pointer text_view, int top_margin);

        /**
         * Sets the line wrapping for the view.
         *
         * @param text_view self
         * @param wrap_mode A GtkWrapMode
         */
        public native void gtk_text_view_set_wrap_mode(Pointer text_view, int wrap_mode);

        /**
         * Determines whether iter is at the start of a display line.
         * <p>
         * See gtk_text_view_forward_display_line() for an explanation of display lines vs. paragraphs.
         *
         * @param text_view self
         * @param iter      A GtkTextIter
         * @return TRUE if iter begins a wrapped line.
         */
        public native boolean gtk_text_view_starts_display_line(Pointer text_view, Pointer iter);

        /**
         * Converts coordinates on the window identified by win to buffer coordinates.
         *
         * @param text_view self
         * @param win       A GtkTextWindowType
         * @param window_x  Window x coordinate.
         * @param window_y  Window y coordinate.
         * @param buffer_x  Buffer x coordinate return location.
         *                  <p>
         *                  The argument can be NULL.
         * @param buffer_y  Buffer y coordinate return location.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_text_view_window_to_buffer_coords(Pointer text_view, int win, int window_x, int window_y, PointerByReference buffer_x, PointerByReference buffer_y);

    }

}
