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
import com.gitlab.ccook.jgtk.enums.*;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

@SuppressWarnings("unchecked")
public class GtkLabel extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkLabelLibrary library = new GtkLabelLibrary();

    public GtkLabel(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new label with the given text inside it.
     * <p>
     * You can pass NULL to get an empty label widget.
     *
     * @param text The text of the label.
     *             <p>
     *             The argument can be NULL.
     */
    public GtkLabel(String text) {
        super(library.gtk_label_new(text));
    }

    /**
     * Creates a new GtkLabel, containing the text in l.
     * <p>
     * If characters in str are preceded by an underscore, they are underlined. If you need a literal underscore
     * character in a label, use '__' (two underscores). The first underlined character represents a keyboard
     * accelerator called a mnemonic. The mnemonic key can be used to activate another widget, chosen automatically,
     * or explicitly using gtk_label_set_mnemonic_widget().
     * <p>
     * If gtk_label_set_mnemonic_widget() is not called, then the first activatable ancestor of the GtkLabel will
     * be chosen as the mnemonic widget. For instance, if the label is inside a button or menu item, the button or
     * menu item will automatically become the mnemonic widget and be activated by the mnemonic.
     *
     * @param l The text of the label, with an underscore in front of the mnemonic character.
     *          <p>
     *          The argument can be NULL.
     */
    public GtkLabel(MnemonicLabel l) {
        super(library.gtk_label_new_with_mnemonic(l.getMnemonicLabel()));
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
     * Returns whether lines in the label are automatically wrapped.
     *
     * @return TRUE if the lines of the label are automatically wrapped.
     */
    public boolean doesWrap() {
        return library.gtk_label_get_wrap(getCReference());
    }

    /**
     * Gets the labels attribute list.
     * <p>
     * This is the PangoAttrList that was set on the label using gtk_label_set_attributes(), if any.
     * This function does not reflect attributes that come from the label's markup (see gtk_label_set_markup()).
     * If you want to get the effective attributes for the label,
     * use pango_layout_get_attributes (gtk_label_get_layout (self)).
     *
     * @return The attribute list, if defined
     */
    public Option<PangoAttrList> getAttributes() {
        Option<Pointer> p = new Option<>(library.gtk_label_get_attributes(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoAttrList(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Apply attributes to the label text.
     * <p>
     * The attributes set with this function will be applied and merged with any other attributes previously effected
     * by way of the GtkLabel:use-underline or GtkLabel:use-markup properties. While it is not recommended to mix
     * markup strings with manually set attributes, if you must; know that the attributes will be applied to the label
     * after the markup string is parsed.
     *
     * @param list A PangoAttrList
     *             <p>
     *             The argument can be NULL.
     */
    public void setAttributes(PangoAttrList list) {
        library.gtk_label_set_attributes(getCReference(), pointerOrNull(list));
    }

    /**
     * Returns the URL for the currently active link in the label.
     * <p>
     * The active link is the one under the mouse pointer or, in a selectable label,
     * the link in which the text cursor is currently positioned.
     * <p>
     * This function is intended for use in a GtkLabel::activate-link handler or for use in a
     * GtkWidget::query-tooltip handler.
     *
     * @return The currently active URL, if defined
     */
    public Option<URL> getCurrentResourceLink() throws MalformedURLException {
        String url = library.gtk_label_get_current_uri(getCReference());
        if (url != null) {
            return new Option<>(URI.create(url).toURL());
        }
        return Option.NONE;
    }

    public Option<PangoAttrList> getEffectiveAttributes() {
        PangoLayout layout = getLayout();
        Option<Pointer> p = new Option<>(library.pango_layout_get_attributes(layout.getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoAttrList(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Gets the PangoLayout used to display the label.
     * <p>
     * The layout is useful to e.g. convert text positions to pixel positions, in combination with
     * gtk_label_get_layout_offsets(). The returned layout is owned by the label so need not be freed by the caller.
     * The label is free to recreate its layout at any time, so it should be considered read-only.
     *
     * @return The PangoLayout for this label.
     */
    public PangoLayout getLayout() {
        return new PangoLayout(library.gtk_label_get_layout(getCReference()));
    }

    /**
     * Returns the ellipsizing (that is, when the text cuts of with a ...) position of the label.
     *
     * @return The ellipsizing position.
     */
    public PangoEllipsizeMode getEllipsizeMode() {
        return PangoEllipsizeMode.getModeFromCValue(library.gtk_label_get_ellipsize(getCReference()));
    }

    /**
     * Sets the mode used to ellipsize the text.
     * <p>
     * The text will be ellipsized if there is not enough space to render the entire string.
     *
     * @param m A PangoEllipsizeMode
     */
    public void setEllipsizeMode(PangoEllipsizeMode m) {
        if (m != null) {
            library.gtk_label_set_ellipsize(getCReference(), PangoEllipsizeMode.getCValueFromMode(m));
        }
    }

    /**
     * Gets the extra menu model of label.
     *
     * @return The menu model, if defined
     */
    public Option<GMenuModel> getExtraMenu() {
        Option<Pointer> p = new Option<>(library.gtk_label_get_extra_menu(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GMenuModel(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a menu model to add when constructing the context menu for label.
     *
     * @param m A GMenuModel
     *          <p>
     *          The argument can be NULL.
     */
    public void setExtraMenu(GMenuModel m) {
        library.gtk_label_set_extra_menu(getCReference(), pointerOrNull(m));
    }

    /**
     * Gets the x-align of the label.
     *
     * @return The x-align property.
     */
    public float getHorizontalAlignment() {
        return library.gtk_label_get_xalign(getCReference());
    }

    /**
     * Sets the horizontal Alignment
     *
     * @param xAlign the horizontal alignment (between 0.0 - 1.0)
     */
    public void setHorizontalAlignment(float xAlign) {
        library.gtk_label_set_xalign(getCReference(), Math.min(1f, Math.max(0.0f, xAlign)));
    }

    /**
     * Returns the justification of the label.
     *
     * @return the justification of the label.
     */
    public GtkJustification getJustification() {
        return GtkJustification.getJustificationFromCValue(library.gtk_label_get_justify(getCReference()));
    }

    /**
     * Sets the alignment of the lines in the text of the label relative to each other.
     * <p>
     * GTK_JUSTIFY_LEFT is the default value when the widget is first created with gtk_label_new(). If you instead want
     * to set the alignment of the label as a whole, use gtk_widget_set_halign() instead. gtk_label_set_justify() has
     * no effect on labels containing only a single line.
     *
     * @param justification A GtkJustification
     */
    public void setJustification(GtkJustification justification) {
        if (justification != null) {
            library.gtk_label_set_justify(getCReference(), GtkJustification.getCValueFromJustification(justification));
        }
    }

    /**
     * Fetches the text from a label.
     * <p>
     * The returned text includes any embedded underlines indicating mnemonics and Pango markup.
     * (See gtk_label_get_text()).
     *
     * @return The text of the label widget.
     */
    public String getLabel() {
        return library.gtk_label_get_label(getCReference());
    }

    /**
     * Sets the text of the label.
     * <p>
     * The label is interpreted as including embedded underlines and/or Pango markup depending on the values of the
     * GtkLabel:use-underline and GtkLabel:use-markup properties.
     *
     * @param label The new text to set for the label.
     */
    public void setLabel(String label) {
        if (label != null) {
            library.gtk_label_set_label(getCReference(), label);
        }
    }

    /**
     * Obtains the coordinates where the label will draw its PangoLayout.
     * <p>
     * The coordinates are useful to convert mouse events into coordinates inside the PangoLayout, e.g. to take some
     * action if some part of the label is clicked. Remember when using the PangoLayout functions you need to convert
     * to and from pixels using PANGO_PIXELS() or PANGO_SCALE.
     *
     * @return first - X offset of layout.
     *         second - Y offset of layout.
     */
    public Pair<Integer, Integer> getLayoutOffsets() {
        PointerByReference x = new PointerByReference();
        PointerByReference y = new PointerByReference();
        library.gtk_label_get_layout_offsets(getCReference(), x, y);
        return new Pair<>(x.getPointer().getInt(0), y.getPointer().getInt(0));
    }

    /**
     * Gets the number of lines to which an ellipsized, wrapping label should be limited.
     *
     * @return The number of lines.
     */
    public Option<Integer> getLineLimit() {
        int limit = library.gtk_label_get_lines(getCReference());
        if (limit >= 0) {
            return new Option<>(limit);
        }
        return Option.NONE;
    }

    /**
     * Sets the number of lines to which an ellipsized, wrapping label should be limited.
     * <p>
     * This has no effect if the label is not wrapping or ellipsized. Set this to -1 if you don't want to limit the
     * number of lines.
     *
     * @param lineLimit The desired number of lines, or -1
     */
    public void setLineLimit(int lineLimit) {
        library.gtk_label_set_lines(getCReference(), Math.max(-1, lineLimit));
    }

    /**
     * Retrieves the desired maximum width of label, in characters.
     *
     * @return The desired maximum width of label, in characters.
     */
    public Option<Integer> getMaxCharacterWidth() {
        int value = library.gtk_label_get_max_width_chars(getCReference());
        if (value >= 0) {
            return new Option<>(value);
        }
        return Option.NONE;

    }

    /**
     * Sets the desired maximum width in characters of label to n_chars.
     * <p>
     * -1 to indicate no max
     *
     * @param max The new desired maximum width, in characters.
     */
    public void setMaxCharacterWidth(int max) {
        library.gtk_label_set_max_width_chars(getCReference(), Math.max(-1, max));
    }

    /**
     * Return the mnemonic accelerator.
     * <p>
     * If the label has been set so that it has a mnemonic key this function returns the key-val used for the mnemonic
     * accelerator. If there is no mnemonic set up it returns GDK_KEY_VoidSymbol.
     *
     * @return GDK key-val usable for accelerators, or GDK_KEY_VoidSymbol
     */
    public GdkKeyVal getMnemonicKeyVal() {
        return GdkKeyVal.getValFromCValue(library.gtk_label_get_mnemonic_keyval(getCReference()));
    }

    /**
     * Retrieves the target of the mnemonic (keyboard shortcut) of this label.
     *
     * @return The target of the label's mnemonic, or NONE if none has been set and the default algorithm will be used.
     */
    public Option<GtkWidget> getMnemonicTarget() {
        Option<Pointer> p = new Option<>(library.gtk_label_get_mnemonic_widget(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Associate the label with its mnemonic target.
     * <p>
     * If the label has been set so that it has a mnemonic key (using i.e. gtk_label_set_markup_with_mnemonic(),
     * gtk_label_set_text_with_mnemonic(), gtk_label_new_with_mnemonic() or the GtkLabel:use-underline property) the
     * label can be associated with a widget that is the target of the mnemonic. When the label is inside a widget
     * (like a GtkButton or a GtkNotebook tab) it is automatically associated with the correct widget, but sometimes
     * (i.e. when the target is a GtkEntry next to the label) you need to set it explicitly using this function.
     * <p>
     * The target widget will be accelerated by emitting the GtkWidget::mnemonic-activate signal on it. The default
     * handler for this signal will activate the widget if there are no mnemonic collisions and toggle focus between
     * the colliding widgets otherwise.
     *
     * @param w The target GtkWidget, or NULL to unset.
     *          <p>
     *          The argument can be NULL.
     */
    public void setMnemonicTarget(GtkWidget w) {
        library.gtk_label_set_mnemonic_widget(getCReference(), pointerOrNull(w));
    }

    /**
     * Returns line wrap mode used by the label.
     * <p>
     *
     * @return The natural line wrap mode.
     * @since 4.5
     */
    public GtkNaturalWrapMode getNaturalWrapMode() {
        return GtkNaturalWrapMode.getModeFromCValue(library.gtk_label_get_natural_wrap_mode(getCReference()));
    }

    /**
     * Select the line wrapping for the natural size request.
     * <p>
     * This only affects the natural size requested, for the actual wrapping used, see the GtkLabel:wrap-mode property.
     *
     * @param m The line wrapping mode.
     * @since 4.6
     */
    public void setNaturalWrapMode(GtkNaturalWrapMode m) {
        if (m != null) {
            library.gtk_label_set_natural_wrap_mode(getCReference(), GtkNaturalWrapMode.getCValueFromMode(m));
        }
    }

    /**
     * Gets the selected range of characters in the label.
     *
     * @return if defined, first - start of selection, as a character offset.
     *         end - end of selection, as a character offset.
     */
    public Option<Pair<Integer, Integer>> getSelectionBounds() {
        PointerByReference start = new PointerByReference();
        PointerByReference end = new PointerByReference();
        boolean hasSelection = library.gtk_label_get_selection_bounds(getCReference(), start, end);
        if (hasSelection) {
            return new Option<>(new Pair<>(start.getPointer().getInt(0), end.getPointer().getInt(0)));
        }
        return Option.NONE;
    }

    /**
     * Fetches the text from a label.
     * <p>
     * The returned text is as it appears on screen. This does not include any embedded underlines indicating
     * mnemonics or Pango markup. (See gtk_label_get_label())
     *
     * @return The text in the label widget
     */
    public String getText() {
        return library.gtk_label_get_text(getCReference());
    }

    /**
     * Sets the text within the GtkLabel widget.
     * <p>
     * It overwrites any text that was there before.
     * <p>
     * This function will clear any previously set mnemonic accelerators, and set the
     * Gtk.Label:use-underline property to FALSE as a side effect.
     * <p>
     * This function will set the GtkLabel:use-markup property to FALSE as a side effect.
     *
     * @param text The text you want to set.
     */
    public void setText(String text) {
        if (text != null) {
            library.gtk_label_set_text(getCReference(), text);
        }
    }

    /**
     * Retrieves the desired width of label, in characters.
     * <p>
     * -1 to indicate unlimited
     *
     * @return The width of the label in characters.
     */
    public Option<Integer> getTextWidth() {
        int value = library.gtk_label_get_width_chars(getCReference());
        if (value > -1) {
            return new Option<>(value);
        }
        return Option.NONE;
    }

    /**
     * Sets the desired width in characters of label to n_chars.
     * or -1 to indicate unlimited
     *
     * @param numChars The new desired width, in characters.
     */
    public void setTextWidth(int numChars) {
        library.gtk_label_set_width_chars(getCReference(), numChars);
    }

    /**
     * Gets the y-align of the label.
     *
     * @return The y-align property.
     */
    public float getVerticalAlignment() {
        return library.gtk_label_get_yalign(getCReference());
    }

    /**
     * Sets the vertical Alignment
     *
     * @param yAlign the vertical alignment (between 0.0 - 1.0)
     */
    public void setVerticalAlignment(float yAlign) {
        library.gtk_label_set_yalign(getCReference(), Math.min(1f, Math.max(0f, yAlign)));
    }

    /**
     * Returns line wrap mode used by the label.
     *
     * @return The line wrap mode.
     */
    public PangoWrapMode getWrapMode() {
        return PangoWrapMode.getModeFromCValue(library.gtk_label_get_wrap_mode(getCReference()));
    }

    /**
     * Controls how line wrapping is done.
     * <p>
     * This only affects the label if line wrapping is on. (See gtk_label_set_wrap())
     * The default is PANGO_WRAP_WORD which means wrap on word boundaries.
     * <p>
     * For sizing behavior, also consider the GtkLabel:natural-wrap-mode property.
     *
     * @param m The line wrapping mode.
     */
    public void setWrapMode(PangoWrapMode m) {
        if (m != null) {
            library.gtk_label_set_wrap_mode(getCReference(), PangoWrapMode.getCValueFromMode(m));
        }
    }

    /**
     * Returns whether an embedded underlines in the label indicate mnemonics.
     *
     * @return TRUE whether an embedded underline in the label indicates the mnemonic accelerator keys.
     */
    public boolean isMnemonic() {
        return library.gtk_label_get_use_underline(getCReference());
    }

    /**
     * Returns whether the label is selectable.
     *
     * @return TRUE if the user can copy text from the label.
     */
    public boolean isSelectable() {
        return library.gtk_label_get_selectable(getCReference());
    }

    /**
     * Makes text in the label selectable.
     * <p>
     * Selectable labels allow the user to select text from the label, for copy-and-paste.
     *
     * @param isSelectable TRUE to allow selecting text in the label.
     */
    public void setSelectable(boolean isSelectable) {
        library.gtk_label_set_selectable(getCReference(), isSelectable);
    }

    /**
     * Returns whether the label is in single line mode.
     *
     * @return TRUE when the label is in single line mode.
     */
    public boolean isSingleLineModeActive() {
        return library.gtk_label_get_single_line_mode(getCReference());
    }

    /**
     * Sets whether the label is in single line mode.
     *
     * @param isActive TRUE if the label should be in single line mode.
     */
    public void setSingleLineModeActive(boolean isActive) {
        library.gtk_label_set_single_line_mode(getCReference(), isActive);
    }

    /**
     * Selects a range of characters in the label, if the label is selectable.
     * <p>
     * See gtk_label_set_selectable(). If the label is not selectable, this function has no effect.
     * If start_offset or end_offset are -1, then the end of the label will be substituted.
     *
     * @param start Start offset (in characters not bytes)
     * @param end   End offset (in characters not bytes)
     */
    public void select(int start, int end) {
        library.gtk_label_select_region(getCReference(), start, end);
    }

    /**
     * Sets whether underlines in the text indicate mnemonics.
     *
     * @param isMnemonic TRUE if underlines in the text indicate mnemonics.
     */
    public void setIsMnemonic(boolean isMnemonic) {
        library.gtk_label_set_use_underline(getCReference(), isMnemonic);
    }

    /**
     * Sets the labels text and attributes from markup.
     * <p>
     * The string must be marked up with Pango markup (see pango_parse_markup()).
     * <p>
     * If the str is external data, you may need to escape it with g_markup_escape_text() or g_markup_printf_escaped():
     * This function will set the GtkLabel:use-markup property to TRUE as a side effect.
     * <p>
     * If you set the label contents using the GtkLabel:label property you should also ensure that you set the
     * GtkLabel:use-markup property accordingly.
     *
     * @param markupText A markup string.
     */
    public void setMarkupText(String markupText) {
        if (markupText != null) {
            library.gtk_label_set_markup(getCReference(), markupText);
        }
    }

    /**
     * Sets the labels text, attributes and mnemonic from markup.
     * <p>
     * Parses str which is marked up with Pango markup (see pango_parse_markup()), setting the label's text and
     * attribute list based on the parse results. If characters in str are preceded by an underscore, they are
     * underlined indicating that they represent a keyboard accelerator called a mnemonic.
     * <p>
     * The mnemonic key can be used to activate another widget, chosen automatically, or explicitly using
     * gtk_label_set_mnemonic_widget().
     *
     * @param markup A markup string.
     */
    public void setMarkupWithMnemonic(String markup) {
        if (markup != null) {
            library.gtk_label_set_markup_with_mnemonic(getCReference(), markup);
        }
    }

    /**
     * Sets the label's text from the string str.
     * <p>
     * If characters in str are preceded by an underscore, they are underlined indicating
     * that they represent a keyboard accelerator called a mnemonic.
     * The mnemonic key can be used to activate another widget, chosen automatically, or explicitly using
     * gtk_label_set_mnemonic_widget().
     *
     * @param mnemonicText text with mnemonic to set
     */
    public void setMnemonicText(String mnemonicText) {
        if (mnemonicText != null) {
            library.gtk_label_set_text_with_mnemonic(getCReference(), mnemonicText);
        }
    }

    /**
     * Toggles line wrapping within the GtkLabel widget.
     * <p>
     * TRUE makes it break lines if text exceeds the widget's size. FALSE lets the text get cut off by the edge of the
     * widget if it exceeds the widget size.
     * <p>
     * Note that setting line wrapping to TRUE does not make the label wrap at its parent container's width, because
     * GTK widgets conceptually can't make their requisition depend on the parent container's size. For a label that
     * wraps at a specific position, set the label's width using gtk_widget_set_size_request().
     *
     * @param shouldWrap TRUE makes it break lines if text exceeds the widget's size.
     *                   FALSE lets the text get cut off by the edge of the widget if it exceeds the widget size.
     */
    public void shouldWrap(boolean shouldWrap) {
        library.gtk_label_set_wrap(getCReference(), shouldWrap);
    }

    /**
     * Sets whether the text of the label contains markup.
     *
     * @param shouldUse TRUE if the label's text should be parsed for markup.
     */
    public void usePangoMarkup(boolean shouldUse) {
        library.gtk_label_set_use_markup(getCReference(), shouldUse);
    }

    /**
     * Returns whether the label's text is interpreted as Pango markup.
     *
     * @return TRUE if the label's text will be parsed for markup.
     */
    public boolean usesPangoMarkup() {
        return library.gtk_label_get_use_markup(getCReference());
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Gets emitted when the user activates a link in the label.
         * <p>
         * The ::activate-current-link is a keybinding signal.
         * <p>
         * Applications may also emit the signal with g_signal_emit_by_name() if they need to control activation of URLs
         * programmatically.
         * <p>
         * The default bindings for this signal are all forms of the Enter key.
         */
        public static final Signals ACTIVATE_CURRENT_LINK = new Signals("activate-current-link");
        /**
         * Gets emitted to activate a URI.
         * <p>
         * Applications may connect to it to override the default behaviour, which is to call
         * gtk_file_launcher_launch().
         */
        public static final Signals ACTIVATE_LINK = new Signals("activate-link");
        /**
         * Gets emitted to copy the selection to the clipboard.
         * <p>
         * The ::copy-clipboard signal is a keybinding signal.
         * <p>
         * The default binding for this signal is Ctrl+c.
         */
        public static final Signals COPY_CLIPBOARD = new Signals("copy-clipboard");
        /**
         * Gets emitted when the user initiates a cursor movement.
         * <p>
         * The ::move-cursor signal is a keybinding signal. If the cursor is not visible in entry, this signal causes
         * the viewport to be moved instead.
         * <p>
         * Applications should not connect to it, but may emit it with g_signal_emit_by_name() if they need to control
         * the cursor programmatically.
         * <p>
         * The default bindings for this signal come in two variants, the variant with the Shift modifier extends the
         * selection, the variant without the Shift modifier does not. There are too many key combinations to list them
         * all here.
         * <p>
         * ←, →, ↑, ↓ move by individual characters/lines
         * Ctrl+←, etc. move by words/paragraphs
         * Home and End move to the ends of the buffer.
         */
        public static final Signals MOVE_CURSOR = new Signals("move-cursor");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkLabelLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the label's attribute list.
         * <p>
         * This is the PangoAttrList that was set on the label using gtk_label_set_attributes(), if any. This
         * function does not reflect attributes that come from the label's markup (see gtk_label_set_markup()).
         * If you want to get the effective attributes for the label, use pango_layout_get_attributes
         * (gtk_label_get_layout (self)).
         *
         * @param self self
         * @return The attribute list.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_label_get_attributes(Pointer self);

        /**
         * Returns the URI for the currently active link in the label.
         * <p>
         * The active link is the one under the mouse pointer or, in a selectable label, the link in which the text
         * cursor is currently positioned.
         * <p>
         * This function is intended for use in a GtkLabel::activate-link handler or for use in a
         * GtkWidget::query-tooltip handler.
         *
         * @param self self
         * @return The currently active URI.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_label_get_current_uri(Pointer self);

        /**
         * Returns the ellipsizing position of the label.
         * <p>
         * See gtk_label_set_ellipsize().
         *
         * @param self self
         * @return The ellipsizing position. Type: PangoEllipsizeMode
         */
        public native int gtk_label_get_ellipsize(Pointer self);

        /**
         * Gets the extra menu model of self.
         * <p>
         * See gtk_label_set_extra_menu().
         *
         * @param self self
         * @return The menu model.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_label_get_extra_menu(Pointer self);

        /**
         * Returns the justification of the label.
         * <p>
         * See gtk_label_set_justify().
         *
         * @param self self
         * @return the justification of the label. Type: GtkJustification
         */
        public native int gtk_label_get_justify(Pointer self);

        /**
         * Fetches the text from a label.
         * <p>
         * The returned text includes any embedded underlines indicating mnemonics and Pango markup.
         * (See gtk_label_get_text()).
         *
         * @param self self
         * @return The text of the label widget. This string is owned by the widget and must not be modified or freed.
         */
        public native String gtk_label_get_label(Pointer self);

        /**
         * Gets the PangoLayout used to display the label.
         * <p>
         * The layout is useful to e.g. convert text positions to pixel positions, in combination with
         * gtk_label_get_layout_offsets(). The returned layout is owned by the label so need not be freed by the
         * caller. The label is free to recreate its layout at any time, so it should be considered read-only.
         *
         * @param self self
         * @return The PangoLayout for this label. Type: PangoLayout
         */
        public native Pointer gtk_label_get_layout(Pointer self);

        /**
         * Obtains the coordinates where the label will draw its PangoLayout.
         * <p>
         * The coordinates are useful to convert mouse events into coordinates inside the PangoLayout, e.g. to take
         * some action if some part of the label is clicked. Remember when using the PangoLayout functions you need to
         * convert to and from pixels using PANGO_PIXELS() or PANGO_SCALE.
         *
         * @param self self
         * @param x    Location to store X offset of layout.
         *             <p>
         *             The argument can be NULL.
         * @param y    Location to store Y offset of layout.
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_label_get_layout_offsets(Pointer self, PointerByReference x, PointerByReference y);

        /**
         * Gets the number of lines to which an ellipsized, wrapping label should be limited.
         * <p>
         * See gtk_label_set_lines().
         *
         * @param self self
         * @return The number of lines.
         */
        public native int gtk_label_get_lines(Pointer self);

        /**
         * Retrieves the desired maximum width of label, in characters.
         * <p>
         * See gtk_label_set_width_chars().
         *
         * @param self self
         * @return The maximum width of the label in characters.
         */
        public native int gtk_label_get_max_width_chars(Pointer self);

        /**
         * Return the mnemonic accelerator.
         * <p>
         * If the label has been set so that it has a mnemonic key this function returns the keyval used for the
         * mnemonic accelerator. If there is no mnemonic set up it returns GDK_KEY_VoidSymbol.
         *
         * @param self self
         * @return GDK keyval usable for accelerators, or GDK_KEY_VoidSymbol
         */
        public native int gtk_label_get_mnemonic_keyval(Pointer self);

        /**
         * Retrieves the target of the mnemonic (keyboard shortcut) of this label.
         * <p>
         * See gtk_label_set_mnemonic_widget().
         *
         * @param self self
         * @return The target of the label's mnemonic, or NULL if none has been set and the default algorithm will be
         *         used. Type: GtkWidget
         *         The return value can be NULL
         */
        public native Pointer gtk_label_get_mnemonic_widget(Pointer self);

        /**
         * Returns line wrap mode used by the label.
         * <p>
         * See gtk_label_set_natural_wrap_mode().
         *
         * @param self self
         * @return The natural line wrap mode. Type: GtkNaturalWrapMode
         * @since 4.6
         */
        public native int gtk_label_get_natural_wrap_mode(Pointer self);

        /**
         * Returns whether the label is selectable.
         *
         * @param self self
         * @return TRUE if the user can copy text from the label.
         */
        public native boolean gtk_label_get_selectable(Pointer self);

        /**
         * Gets the selected range of characters in the label.
         *
         * @param self  self
         * @param start Return location for start of selection, as a character offset.
         *              <p>
         *              The argument can be NULL.
         * @param end   Return location for end of selection, as a character offset.
         *              <p>
         *              The argument can be NULL.
         * @return TRUE if selection is non-empty.
         */
        public native boolean gtk_label_get_selection_bounds(Pointer self, PointerByReference start, PointerByReference end);

        /**
         * Returns whether the label is in single line mode.
         *
         * @param self self
         * @return TRUE when the label is in single line mode.
         */
        public native boolean gtk_label_get_single_line_mode(Pointer self);

        /**
         * Fetches the text from a label.
         * <p>
         * The returned text is as it appears on screen. This does not include any embedded underlines indicating
         * mnemonics or Pango markup. (See gtk_label_get_label())
         *
         * @param self self
         * @return The text in the label widget. This is the internal string used by the label, and must not be
         *         modified.
         */
        public native String gtk_label_get_text(Pointer self);

        /**
         * Returns whether the label's text is interpreted as Pango markup.
         * <p>
         * See gtk_label_set_use_markup().
         *
         * @param self self
         * @return TRUE if the label's text will be parsed for markup.
         */
        public native boolean gtk_label_get_use_markup(Pointer self);

        /**
         * Returns whether an embedded underlines in the label indicate mnemonics.
         * <p>
         * See gtk_label_set_use_underline().
         *
         * @param self self
         * @return TRUE whether an embedded underline in the label indicates the mnemonic accelerator keys.
         */
        public native boolean gtk_label_get_use_underline(Pointer self);

        /**
         * Retrieves the desired width of label, in characters.
         * <p>
         * See gtk_label_set_width_chars().
         *
         * @param self self
         * @return The width of the label in characters.
         */
        public native int gtk_label_get_width_chars(Pointer self);

        /**
         * Returns whether lines in the label are automatically wrapped.
         * <p>
         * See gtk_label_set_wrap().
         *
         * @param self self
         * @return TRUE if the lines of the label are automatically wrapped.
         */
        public native boolean gtk_label_get_wrap(Pointer self);

        /**
         * Returns line wrap mode used by the label.
         * <p>
         * See gtk_label_set_wrap_mode().
         *
         * @param self self
         * @return The line wrap mode.
         */
        public native int gtk_label_get_wrap_mode(Pointer self);

        /**
         * Gets the xalign of the label.
         * <p>
         * See the GtkLabel:xalign property.
         *
         * @param self self
         * @return The xalign property.
         */
        public native float gtk_label_get_xalign(Pointer self);

        /**
         * Gets the yalign of the label.
         * <p>
         * See the GtkLabel:yalign property.
         *
         * @param self self
         * @return The yalign property.
         */
        public native float gtk_label_get_yalign(Pointer self);

        /**
         * Creates a new label with the given text inside it.
         * <p>
         * You can pass NULL to get an empty label widget.
         *
         * @param str The text of the label.
         *            <p>
         *            The argument can be NULL.
         * @return The new GtkLabel. Type: GtkLabel
         */
        public native Pointer gtk_label_new(String str);

        /**
         * Creates a new GtkLabel, containing the text in str.
         * <p>
         * If characters in str are preceded by an underscore, they are underlined. If you need a literal underscore
         * character in a label, use '__' (two underscores). The first underlined character represents a keyboard
         * accelerator called a mnemonic. The mnemonic key can be used to activate another widget, chosen automatically,
         * or explicitly using gtk_label_set_mnemonic_widget().
         * <p>
         * If gtk_label_set_mnemonic_widget() is not called, then the first activatable ancestor of the GtkLabel will
         * be chosen as the mnemonic widget. For instance, if the label is inside a button or menu item, the button or
         * menu item will automatically become the mnemonic widget and be activated by the mnemonic.
         *
         * @param str The text of the label, with an underscore in front of the mnemonic character.
         *            <p>
         *            The argument can be NULL.
         * @return The new GtkLabel. Type: GtkLabel
         */
        public native Pointer gtk_label_new_with_mnemonic(String str);

        /**
         * Selects a range of characters in the label, if the label is selectable.
         * <p>
         * See gtk_label_set_selectable(). If the label is not selectable, this function has no effect.
         * If start_offset or end_offset are -1, then the end of the label will be substituted.
         *
         * @param self         self
         * @param start_offset Start offset (in characters not bytes)
         * @param end_offset   End offset (in characters not bytes)
         */
        public native void gtk_label_select_region(Pointer self, int start_offset, int end_offset);

        /**
         * Apply attributes to the label text.
         * <p>
         * The attributes set with this function will be applied and merged with any other attributes previously
         * effected by way of the GtkLabel:use-underline or GtkLabel:use-markup properties. While it is not recommended
         * to mix markup strings with manually set attributes, if you must; know that the attributes will be applied
         * to the label after the markup string is parsed.
         *
         * @param self  self
         * @param attrs A PangoAttrList
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_label_set_attributes(Pointer self, Pointer attrs);

        /**
         * Sets the mode used to ellipsize the text.
         * <p>
         * The text will be ellipsized if there is not enough space to render the entire string.
         *
         * @param self self
         * @param mode the mode used to ellipsize the text. Type: PangoEllipsizeMode
         */
        public native void gtk_label_set_ellipsize(Pointer self, int mode);

        /**
         * Sets a menu model to add when constructing the context menu for label.
         *
         * @param self  self
         * @param model A GMenuModel. Type: GMenuModel
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_label_set_extra_menu(Pointer self, Pointer model);

        /**
         * Sets the alignment of the lines in the text of the label relative to each other.
         * <p>
         * GTK_JUSTIFY_LEFT is the default value when the widget is first created with gtk_label_new(). If you instead
         * want to set the alignment of the label as a whole, use gtk_widget_set_halign() instead.
         * gtk_label_set_justify() has no effect on labels containing only a single line.
         *
         * @param self  self
         * @param jtype A GtkJustification. Type: GtkJustification
         */
        public native void gtk_label_set_justify(Pointer self, int jtype);

        /**
         * Sets the text of the label.
         * <p>
         * The label is interpreted as including embedded underlines and/or Pango markup depending on the values of the
         * GtkLabel:use-underline and GtkLabel:use-markup properties.
         *
         * @param self self
         * @param str  The new text to set for the label.
         */
        public native void gtk_label_set_label(Pointer self, String str);

        /**
         * Sets the number of lines to which an ellipsized, wrapping label should be limited.
         * <p>
         * This has no effect if the label is not wrapping or ellipsized. Set this to -1 if you don't want to limit the
         * number of lines.
         *
         * @param self  self
         * @param lines The desired number of lines, or -1
         */
        public native void gtk_label_set_lines(Pointer self, int lines);

        /**
         * Sets the labels text and attributes from markup.
         * <p>
         * The string must be marked up with Pango markup (see pango_parse_markup()).
         * <p>
         * If the str is external data, you may need to escape it with g_markup_escape_text() or
         * g_markup_printf_escaped():
         * <p>
         * This function will set the GtkLabel:use-markup property to TRUE as a side effect.
         * <p>
         * If you set the label contents using the GtkLabel:label property you should also ensure that you set the
         * GtkLabel:use-markup property accordingly.
         *
         * @param self self
         * @param str  A markup string.
         */
        public native void gtk_label_set_markup(Pointer self, String str);

        /**
         * Sets the labels text, attributes and mnemonic from markup.
         * <p>
         * Parses str which is marked up with Pango markup (see pango_parse_markup()), setting the label's text and
         * attribute list based on the parse results. If characters in str are preceded by an underscore, they are
         * underlined indicating that they represent a keyboard accelerator called a mnemonic.
         * <p>
         * The mnemonic key can be used to activate another widget, chosen automatically, or explicitly using
         * gtk_label_set_mnemonic_widget().
         *
         * @param self self
         * @param str  A markup string.
         */
        public native void gtk_label_set_markup_with_mnemonic(Pointer self, String str);

        /**
         * Sets the desired maximum width in characters of label to n_chars.
         *
         * @param self    self
         * @param n_chars The new desired maximum width, in characters.
         */
        public native void gtk_label_set_max_width_chars(Pointer self, int n_chars);

        /**
         * Associate the label with its mnemonic target.
         * <p>
         * If the label has been set so that it has a mnemonic key (using i.e. gtk_label_set_markup_with_mnemonic(),
         * gtk_label_set_text_with_mnemonic(), gtk_label_new_with_mnemonic() or the GtkLabel:use-underline property)
         * the label can be associated with a widget that is the target of the mnemonic. When the label is inside a
         * widget (like a GtkButton or a GtkNotebook tab) it is automatically associated with the correct widget, but
         * sometimes (i.e. when the target is a GtkEntry next to the label) you need to set it explicitly using this
         * function.
         * <p>
         * The target widget will be accelerated by emitting the GtkWidget::mnemonic-activate signal on it. The default
         * handler for this signal will activate the widget if there are no mnemonic collisions and toggle focus
         * between the colliding widgets otherwise.
         *
         * @param self   self
         * @param widget The target GtkWidget, or NULL to unset. Type: GtkWidget
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_label_set_mnemonic_widget(Pointer self, Pointer widget);

        /**
         * Select the line wrapping for the natural size request.
         * <p>
         * This only affects the natural size requested, for the actual wrapping used, see the
         * GtkLabel:wrap-mode property.
         *
         * @param self      self
         * @param wrap_mode The line wrapping mode. Type: GtkNaturalWrapMode
         * @since 4.6
         */
        public native void gtk_label_set_natural_wrap_mode(Pointer self, int wrap_mode);

        /**
         * Makes text in the label selectable.
         * <p>
         * Selectable labels allow the user to select text from the label, for copy-and-paste.
         *
         * @param self    self
         * @param setting TRUE to allow selecting text in the label.
         */
        public native void gtk_label_set_selectable(Pointer self, boolean setting);

        /**
         * Sets whether the label is in single line mode.
         *
         * @param self             self
         * @param single_line_mode TRUE if the label should be in single line mode.
         */
        public native void gtk_label_set_single_line_mode(Pointer self, boolean single_line_mode);

        /**
         * Sets the text within the GtkLabel widget.
         * <p>
         * It overwrites any text that was there before.
         * <p>
         * This function will clear any previously set mnemonic accelerators, and set the
         * GtkLabel:use-underline property to FALSE as a side effect.
         * <p>
         * This function will set the GtkLabel:use-markup property to FALSE as a side effect.
         * <p>
         * See also: gtk_label_set_markup()
         *
         * @param self self
         * @param str  The text you want to set.
         */
        public native void gtk_label_set_text(Pointer self, String str);

        /**
         * Sets the label's text from the string str.
         * <p>
         * If characters in str are preceded by an underscore, they are underlined indicating that they represent a
         * keyboard accelerator called a mnemonic. The mnemonic key can be used to activate another widget, chosen
         * automatically, or explicitly using gtk_label_set_mnemonic_widget().
         *
         * @param self self
         * @param str  the label's text
         */
        public native void gtk_label_set_text_with_mnemonic(Pointer self, String str);

        /**
         * Sets whether the text of the label contains markup.
         * <p>
         * See gtk_label_set_markup().
         *
         * @param self    self
         * @param setting TRUE if the label's text should be parsed for markup.
         */
        public native void gtk_label_set_use_markup(Pointer self, boolean setting);

        /**
         * Sets whether underlines in the text indicate mnemonics.
         *
         * @param self    self
         * @param setting TRUE if underlines in the text indicate mnemonics.
         */
        public native void gtk_label_set_use_underline(Pointer self, boolean setting);

        /**
         * Sets the desired width in characters of label to n_chars.
         *
         * @param self    self
         * @param n_chars The new desired width, in characters.
         */
        public native void gtk_label_set_width_chars(Pointer self, int n_chars);

        /**
         * Toggles line wrapping within the GtkLabel widget.
         * <p>
         * TRUE makes it break lines if text exceeds the widget's size. FALSE lets the text get cut off by the edge
         * of the widget if it exceeds the widget size.
         * <p>
         * Note that setting line wrapping to TRUE does not make the label wrap at its parent container's width,
         * because GTK widgets conceptually can't make their requisition depend on the parent container's size. For a
         * label that wraps at a specific position, set the label's width using gtk_widget_set_size_request().
         *
         * @param self se;f
         * @param wrap TRUE if the label text will wrap if it gets too wide.
         */
        public native void gtk_label_set_wrap(Pointer self, boolean wrap);

        /**
         * Controls how line wrapping is done.
         * <p>
         * This only affects the label if line wrapping is on. (See gtk_label_set_wrap())
         * The default is PANGO_WRAP_WORD which means wrap on word boundaries.
         * <p>
         * For sizing behavior, also consider the GtkLabel:natural-wrap-mode property.
         *
         * @param self      self
         * @param wrap_mode The line wrapping mode. Type: PangoWrapMode
         */
        public native void gtk_label_set_wrap_mode(Pointer self, int wrap_mode);

        /**
         * Sets the xalign of the label.
         * <p>
         * See the GtkLabel:xalign property.
         *
         * @param self   self
         * @param xalign The new xalign value, between 0 and 1
         */
        public native void gtk_label_set_xalign(Pointer self, float xalign);

        /**
         * Sets the yalign of the label.
         * <p>
         * See the GtkLabel:yalign property.
         *
         * @param self   self
         * @param yalign The new yalign value, between 0 and 1
         */
        public native void gtk_label_set_yalign(Pointer self, float yalign);
    }
}

