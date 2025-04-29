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

import com.gitlab.ccook.jgtk.GdkRGBA;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.interfaces.GtkInterface;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkColorChooser is an interface that is implemented by widgets for choosing colors.
 * <p>
 * Depending on the situation, colors may be allowed to have alpha (translucency).
 * <p>
 * In GTK, the main widgets that implement this interface are GtkColorChooserWidget, GtkColorChooserDialog and
 * GtkColorButton.
 *
 * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
 */
@SuppressWarnings("DeprecatedIsStillUsed")
public interface GtkColorChooser extends GtkInterface {


    GtkColorChooserLibrary colorChooserLibrary = new GtkColorChooserLibrary();

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
     * Adds a palette to the color chooser.
     * <p>
     * If orientation is horizontal, the colors are grouped in rows, with colors_per_line colors in each row.
     * If horizontal is FALSE, the colors are grouped in columns instead.
     * <p>
     * The default color palette of GtkColorChooserWidget has 45 colors, organized in columns of 5 colors
     * (this includes some grays).
     * <p>
     * The layout of the color chooser widget works best when the palettes have 9-10 columns.
     * <p>
     * Calling this function for the first time has the side effect of removing the default color palette from the
     * color chooser.
     * <p>
     * If colors is NULL, removes all previously added palettes.
     *
     * @param orientation   GTK_ORIENTATION_HORIZONTAL if the palette should be displayed in rows,
     *                      GTK_ORIENTATION_VERTICAL for columns.
     * @param colorsPerLine The number of colors to show in each row/column.
     * @param colors        The colors of the palette.
     *                      <p>
     *                      The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
     */
    default void addPalette(GtkOrientation orientation, int colorsPerLine, GdkRGBA... colors) {
        int nColors = colors.length;
        GdkRGBA.GdkRGBAStruct[] pointers = new GdkRGBA.GdkRGBAStruct[colors.length];
        for (int i = 0; i < pointers.length; i++) {
            GdkRGBA.GdkRGBAStruct struct = new GdkRGBA.GdkRGBAStruct();
            struct.red = colors[i].red;
            struct.green = colors[i].green;
            struct.blue = colors[i].blue;
            struct.alpha = colors[i].alpha;
            pointers[i] = struct;
        }
        GdkRGBA.GdkRGBAArrayStruct arr = new GdkRGBA.GdkRGBAArrayStruct();
        arr.arr = pointers;
        colorChooserLibrary.gtk_color_chooser_add_palette(getCReference(), orientation.getCValue(), colorsPerLine, nColors, arr);
    }

    /**
     * Gets the currently-selected color.
     *
     * @return A GdkRGBA with the current color.
     * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
     */
    default GdkRGBA getCurrentlySelectedColor() {
        GdkRGBA.GdkRGBAStruct.ByReference rgba = new GdkRGBA.GdkRGBAStruct.ByReference();
        colorChooserLibrary.gtk_color_chooser_get_rgba(getCReference(), rgba);
        return new GdkRGBA(rgba);
    }

    /**
     * Returns whether the color chooser shows the alpha channel.
     *
     * @return TRUE if the color chooser uses the alpha channel, FALSE if not.
     * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
     */
    default boolean doesUseAlpha() {
        return colorChooserLibrary.gtk_color_chooser_get_use_alpha(getCReference());
    }

    /**
     * Sets whether the color chooser should use the alpha channel.
     *
     * @param useAlpha TRUE if color chooser should use alpha channel, FALSE if not.
     * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
     */
    default void shouldUseAlpha(boolean useAlpha) {
        colorChooserLibrary.gtk_color_chooser_set_use_alpha(getCReference(), useAlpha);
    }

    /**
     * Sets the color.
     *
     * @param color The new color.
     * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
     */
    default void setColor(GdkRGBA color) {
        if (color != null) {
            colorChooserLibrary.gtk_color_chooser_set_rgba(getCReference(), color.getCReference());
        }
    }

    @SuppressWarnings("SameParameterValue")
    class Signals extends GtkWidget.Signals {
        /**
         * Emitted when a color is activated from the color chooser.
         * <p>
         * This usually happens when the user clicks a color swatch, or a color is selected and the user presses one of
         * the keys Space, Shift+Space, Return or Enter.
         *
         * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
         */
        public final static Signals COLOR_ACTIVATED = new Signals("color-activated");

        Signals(String detailedName) {
            super(detailedName);
        }
    }

    class GtkColorChooserLibrary extends GtkWidget.GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a palette to the color chooser.
         * <p>
         * If orientation is horizontal, the colors are grouped in rows, with colors_per_line colors in each row.
         * If horizontal is FALSE, the colors are grouped in columns instead.
         * <p>
         * The default color palette of GtkColorChooserWidget has 45 colors, organized in columns of 5 colors
         * (this includes some grays).
         * <p>
         * The layout of the color chooser widget works best when the palettes have 9-10 columns.
         * <p>
         * Calling this function for the first time has the side effect of removing the default color palette from the
         * color chooser.
         * <p>
         * If colors is NULL, removes all previously added palettes.
         *
         * @param chooser         self
         * @param orientation     GTK_ORIENTATION_HORIZONTAL if the palette should be displayed in rows,
         *                        GTK_ORIENTATION_VERTICAL for columns.
         * @param colors_per_line The number of colors to show in each row/column.
         * @param n_colors        The total number of elements in colors.
         * @param colors          The colors of the palette.
         *                        <p>
         *                        The argument can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
         */
        public native void gtk_color_chooser_add_palette(Pointer chooser, int orientation, int colors_per_line, int n_colors, GdkRGBA.GdkRGBAArrayStruct colors);

        /**
         * Gets the currently-selected color.
         *
         * @param chooser self
         * @param rgba    A GdkRGBA to fill in with the current color.
         * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
         */
        public native void gtk_color_chooser_get_rgba(Pointer chooser, GdkRGBA.GdkRGBAStruct.ByReference rgba);

        /**
         * Returns whether the color chooser shows the alpha channel.
         *
         * @param chooser self
         * @return TRUE if the color chooser uses the alpha channel, FALSE if not.
         * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
         */
        public native boolean gtk_color_chooser_get_use_alpha(Pointer chooser);

        /**
         * Sets the color.
         *
         * @param chooser self
         * @param rgba    The new color.
         * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
         */
        public native void gtk_color_chooser_set_rgba(Pointer chooser, Pointer rgba);

        /**
         * Sets whether the color chooser should use the alpha channel.
         *
         * @param chooser   self
         * @param use_alpha TRUE if color chooser should use alpha channel, FALSE if not.
         * @deprecated Deprecated since: 4.10. Use GtkColorDialog instead.
         */
        public native void gtk_color_chooser_set_use_alpha(Pointer chooser, boolean use_alpha);
    }

}
