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
package com.gitlab.ccook.jgtk.gtk.interfaces;

import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.bitfields.GtkFontChooserLevel;
import com.gitlab.ccook.jgtk.callbacks.GDestroyNotify;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.callbacks.GtkFontFilterFunc;
import com.gitlab.ccook.jgtk.interfaces.GtkInterface;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.List;

/**
 * GtkFontChooser is an interface that can be implemented by widgets for choosing fonts.
 * <p>
 * In GTK, the main objects that implement this interface are GtkFontChooserWidget, GtkFontChooserDialog and
 * GtkFontButton.
 * <p>
 *
 * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
 */
@SuppressWarnings({"unchecked", "DeprecatedIsStillUsed"})
public interface GtkFontChooser extends GtkInterface {
    GtkFontChooserLibrary fontChooserLibrary = new GtkFontChooserLibrary();

    /**
     * Gets the currently-selected font name.
     * <p>
     * Note that this can be a different string than what you set with gtk_font_chooser_set_font(), as the font
     * chooser widget may normalize font names and thus return a string with a different structure. For example,
     * "Helvetica Italic Bold 12" could be normalized to "Helvetica Bold Italic 12".
     * <p>
     * Use pango_font_description_equal() if you want to compare two font descriptions.
     *
     * @return A string with the name of the current font, if defined
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default Option<String> getFont() {
        return new Option<>(fontChooserLibrary.gtk_font_chooser_get_font(getCReference()));
    }

    /**
     * Sets the currently-selected font.
     *
     * @param fontName A font name like "Helvetica 12" or "Times Bold 18"
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void setFont(String fontName) {
        if (fontName != null) {
            fontChooserLibrary.gtk_font_chooser_set_font(getCReference(), fontName);
        }
    }

    /**
     * Gets the currently-selected font.
     * <p>
     * Note that this can be a different string than what you set with gtk_font_chooser_set_font(), as the font
     * chooser widget may normalize font names and thus return a string with a different structure. For example,
     * "Helvetica Italic Bold 12" could be normalized to "Helvetica Bold Italic 12".
     * <p>
     * Use pango_font_description_equal() if you want to compare two font descriptions.
     *
     * @return A PangoFontDescription for the current font, if defined
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default Option<PangoFontDescription> getFontDescription() {
        Option<Pointer> p = new Option<>(fontChooserLibrary.gtk_font_chooser_get_font_desc(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoFontDescription(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the currently-selected font from font_desc.
     *
     * @param desc A PangoFontDescription
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void setFontDescription(PangoFontDescription desc) {
        fontChooserLibrary.gtk_font_chooser_set_font_desc(getCReference(), desc != null ? desc.getCReference() : Pointer.NULL);
    }

    /**
     * Gets the PangoFontFace representing the selected font group details (i.e. family, slant, weight, width, etc.).
     * <p>
     * If the selected font is not installed, returns NONE.
     *
     * @return A PangoFontFace representing the selected font group details, if defined
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default Option<PangoFontFace> getFontFace() {
        Option<Pointer> p = new Option<>(fontChooserLibrary.gtk_font_chooser_get_font_face(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoFontFace(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Gets the currently-selected font features.
     * <p>
     * The format of the returned string is compatible with the CSS font-feature-settings property. It can be
     * passed to pango_attr_font_features_new().
     *
     * @return The currently selected font features.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default String getFontFeatures() {
        return fontChooserLibrary.gtk_font_chooser_get_font_features(getCReference());
    }

    /**
     * Adds a filter function that decides which fonts to display in the font chooser.
     *
     * @param filter   A GtkFontFilterFunc
     *                 <p>
     *                 The argument can be NULL.
     * @param userData Data to pass to filter.
     *                 <p>
     *                 The argument can be NULL.
     * @param destroy  Function to call to free data when it is no longer needed.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void setFilter(GtkFontFilterFunc filter, Pointer userData, GDestroyNotify destroy) {
        fontChooserLibrary.gtk_font_chooser_set_filter_func(getCReference(), filter, userData, destroy);
    }

    /**
     * Gets the PangoFontFamily representing the selected font family.
     * <p>
     * Font families are a collection of font faces.
     * <p>
     * If the selected font is not installed, returns NONE.
     *
     * @return A PangoFontFamily representing the selected font family, if defined
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default Option<PangoFontFamily> getFontFamily() {
        Option<Pointer> p = new Option<>(fontChooserLibrary.gtk_font_chooser_get_font_family(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoFontFamily(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Gets the custom font map of this font chooser widget, or NONE if it does not have one.
     *
     * @return A PangoFontMap, if defined
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default Option<PangoFontMap> getFontMap() {
        Option<Pointer> p = new Option<>(fontChooserLibrary.gtk_font_chooser_get_font_map(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new PangoFontMap(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets a custom font map to use for this font chooser widget.
     * <p>
     * A custom font map can be used to present application-specific fonts instead of or in addition to the normal
     * system fonts.
     *
     * @param map A PangoFontMap
     *            <p>
     *            The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void setFontMap(PangoFontMap map) {
        fontChooserLibrary.gtk_font_chooser_set_font_map(getCReference(), map != null ? map.getCReference() : Pointer.NULL);
    }

    /**
     * The selected font size.
     *
     * @return An integer representing the selected font size, or NONE if no font size is selected.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default Option<Integer> getFontSize() {
        int size = fontChooserLibrary.gtk_font_chooser_get_font_size(getCReference());
        if (size >= 0) {
            return new Option<>(size);
        }
        return Option.NONE;
    }

    /**
     * Gets the language that is used for font features.
     *
     * @return The currently selected language.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default String getLanguage() {
        return fontChooserLibrary.gtk_font_chooser_get_language(getCReference());
    }

    /**
     * Sets the language to use for font features.
     *
     * @param lang A language.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void setLanguage(String lang) {
        fontChooserLibrary.gtk_font_chooser_set_language(getCReference(), lang);
    }

    /**
     * Returns the current level of granularity for selecting fonts.
     *
     * @return The current granularity level.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default List<GtkFontChooserLevel> getLevel() {
        return GtkFontChooserLevel.getFlagsFromCValue(fontChooserLibrary.gtk_font_chooser_get_level(getCReference()));
    }

    /**
     * Sets the desired level of granularity for selecting fonts.
     *
     * @param level The desired level of granularity.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void setLevel(GtkFontChooserLevel... level) {
        fontChooserLibrary.gtk_font_chooser_set_level(getCReference(), GtkFontChooserLevel.getCValueFromFlags(level));
    }

    /**
     * Gets the text displayed in the preview area.
     *
     * @return The text displayed in the preview area.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default String getPreviewText() {
        return fontChooserLibrary.gtk_font_chooser_get_preview_text(getCReference());
    }

    /**
     * Sets the text displayed in the preview area.
     * <p>
     * The text is used to show how the selected font looks.
     *
     * @param previewText The text to display in the preview area.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void setPreviewText(String previewText) {
        fontChooserLibrary.gtk_font_chooser_set_preview_text(getCReference(), previewText);
    }

    /**
     * Returns whether the preview entry is shown.
     *
     * @return TRUE if the preview entry is shown or FALSE if it is hidden.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default boolean doesShowPreviewEntry() {
        return fontChooserLibrary.gtk_font_chooser_get_show_preview_entry(getCReference());
    }

    /**
     * Shows or hides the editable preview entry.
     *
     * @param shouldShow Whether to show the editable preview entry.
     * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
     */
    default void shouldShowPreviewEntry(boolean shouldShow) {
        fontChooserLibrary.gtk_font_chooser_set_show_preview_entry(getCReference(), shouldShow);
    }

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
     * Connect a signal
     *
     * @param s       detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    default void connect(Signals s, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
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

    @SuppressWarnings("SameParameterValue")
    class Signals extends GtkWidget.Signals {
        /**
         * Emitted when a font is activated.
         * <p>
         * This usually happens when the user double-clicks an item, or an item is selected and the user presses one of
         * the keys Space, Shift+Space, Return or Enter.
         */
        public final static Signals FONT_ACTIVATED = new Signals("font-activated");


        protected Signals(String cValue) {
            super(cValue);
        }
    }


    class GtkFontChooserLibrary extends GtkWidget.GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the currently-selected font name.
         * <p>
         * Note that this can be a different string than what you set with gtk_font_chooser_set_font(), as the font
         * chooser widget may normalize font names and thus return a string with a different structure. For example,
         * "Helvetica Italic Bold 12" could be normalized to "Helvetica Bold Italic 12".
         * <p>
         * Use pango_font_description_equal() if you want to compare two font descriptions.
         *
         * @param self self
         * @return A string with the name of the current font.
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native String gtk_font_chooser_get_font(Pointer self);

        /**
         * Gets the currently-selected font.
         * <p>
         * Note that this can be a different string than what you set with gtk_font_chooser_set_font(), as the font
         * chooser widget may normalize font names and thus return a string with a different structure. For example,
         * "Helvetica Italic Bold 12" could be normalized to "Helvetica Bold Italic 12".
         * <p>
         * Use pango_font_description_equal() if you want to compare two font descriptions.
         *
         * @param self self
         * @return A PangoFontDescription for the current font.
         *         <p>
         *         The return value can be NULL
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native Pointer gtk_font_chooser_get_font_desc(Pointer self);

        /**
         * Gets the PangoFontFace representing the selected font group details (i.e. family, slant, weight, width,
         * etc.).
         * <p>
         * If the selected font is not installed, returns NULL.
         *
         * @param self self
         * @return A PangoFontFace representing the selected font group details.
         *         <p>
         *         The return value can be NULL
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native Pointer gtk_font_chooser_get_font_face(Pointer self);

        /**
         * Gets the PangoFontFamily representing the selected font family.
         * <p>
         * Font families are a collection of font faces.
         * <p>
         * If the selected font is not installed, returns NULL.
         *
         * @param self self
         * @return A PangoFontFamily representing the selected font family.
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native Pointer gtk_font_chooser_get_font_family(Pointer self);

        /**
         * Gets the currently-selected font features.
         * <p>
         * The format of the returned string is compatible with the CSS font-feature-settings property. It can be
         * passed to pango_attr_font_features_new().
         *
         * @param self self
         * @return The currently selected font features.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native String gtk_font_chooser_get_font_features(Pointer self);

        /**
         * Gets the custom font map of this font chooser widget, or NULL if it does not have one.
         *
         * @param self self
         * @return A PangoFontMap
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native Pointer gtk_font_chooser_get_font_map(Pointer self);

        /**
         * The selected font size.
         *
         * @param self self
         * @return An integer representing the selected font size, or -1 if no font size is selected.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native int gtk_font_chooser_get_font_size(Pointer self);

        /**
         * Gets the language that is used for font features.
         *
         * @param self self
         * @return The currently selected language.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native String gtk_font_chooser_get_language(Pointer self);

        /**
         * Returns the current level of granularity for selecting fonts.
         *
         * @param self self
         * @return The current granularity level. Type: GtkFontChooserLevel
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native int gtk_font_chooser_get_level(Pointer self);

        /**
         * Gets the text displayed in the preview area.
         *
         * @param self self
         * @return The text displayed in the preview area.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native String gtk_font_chooser_get_preview_text(Pointer self);

        /**
         * Returns whether the preview entry is shown.
         *
         * @param self self
         * @return TRUE if the preview entry is shown or FALSE if it is hidden.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native boolean gtk_font_chooser_get_show_preview_entry(Pointer self);

        /**
         * Adds a filter function that decides which fonts to display in the font chooser.
         *
         * @param self      self
         * @param filter    A GtkFontFilterFunc
         *                  <p>
         *                  The argument can be NULL.
         * @param user_data Data to pass to filter.
         *                  <p>
         *                  The argument can be NULL.
         * @param destroy   Function to call to free data when it is no longer needed.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_filter_func(Pointer self, GtkFontFilterFunc filter, Pointer user_data, GDestroyNotify destroy);

        /**
         * Sets the currently-selected font.
         *
         * @param self      self
         * @param font_name A font name like "Helvetica 12" or "Times Bold 18"
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_font(Pointer self, String font_name);

        /**
         * Sets the currently-selected font from font_desc.
         *
         * @param self      self
         * @param font_desc A PangoFontDescription
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_font_desc(Pointer self, Pointer font_desc);

        /**
         * Sets a custom font map to use for this font chooser widget.
         * <p>
         * A custom font map can be used to present application-specific fonts instead of or in addition to the normal
         * system fonts.
         *
         * @param self     self
         * @param font_map A PangoFontMap
         *                 <p>
         *                 The argument can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_font_map(Pointer self, Pointer font_map);

        /**
         * Sets the language to use for font features.
         *
         * @param self self
         * @param lang A language.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_language(Pointer self, String lang);

        /**
         * Sets the desired level of granularity for selecting fonts.
         *
         * @param self  self
         * @param level The desired level of granularity. Type: GtkFontChooserLevel
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_level(Pointer self, int level);

        /**
         * Sets the text displayed in the preview area.
         * <p>
         * The text is used to show how the selected font looks.
         *
         * @param self self
         * @param text The text to display in the preview area.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_preview_text(Pointer self, String text);

        /**
         * Shows or hides the editable preview entry.
         *
         * @param self               self
         * @param show_preview_entry Whether to show the editable preview entry.
         * @deprecated Deprecated since: 4.10. Use GtkFontDialog and GtkFontDialogButton instead.
         */
        public native void gtk_font_chooser_set_show_preview_entry(Pointer self, boolean show_preview_entry);
    }
}
