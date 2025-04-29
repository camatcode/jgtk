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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkHeaderBar is a widget for creating custom title bars for windows.
 * <p>
 * An example GtkHeaderBar
 * <p>
 * GtkHeaderBar is similar to a horizontal GtkCenterBox. It allows children to be placed at the start or the end.
 * In addition, it allows the window title to be displayed. The title will be centered with respect to the width of the
 * box, even if the children at either side take up different amounts of space.
 * <p>
 * GtkHeaderBar can add typical window frame controls, such as minimize, maximize and close buttons, or the window icon.
 * <p>
 * For these reasons, GtkHeaderBar is the natural choice for use as the custom titlebar widget of a GtkWindow (see
 * gtk_window_set_titlebar()), as it gives features typical of titlebars while allowing the addition of child widgets.
 */
@SuppressWarnings("unchecked")
public class GtkHeaderBar extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkHeaderBarLibrary library = new GtkHeaderBarLibrary();

    public GtkHeaderBar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkHeaderBar widget.
     */
    public GtkHeaderBar() {
        super(library.gtk_header_bar_new());
    }

    /**
     * Adds child to bar, packed with reference to the end of the bar.
     *
     * @param w The GtkWidget to be added to bar.
     */
    public void addToEnd(GtkWidget w) {
        if (w != null) {
            library.gtk_header_bar_pack_end(getCReference(), w.getCReference());
        }
    }

    /**
     * Returns whether this header bar shows the standard window title buttons.
     *
     * @return TRUE if title buttons are shown.
     */
    public boolean doesShowTitleButtons() {
        return library.gtk_header_bar_get_show_title_buttons(getCReference());
    }

    /**
     * Gets the decoration layout of the GtkHeaderBar.
     * <p>
     * For example, "icon:minimize,maximize,close" specifies an icon on the left, and minimize, maximize and close
     * buttons on the right.
     *
     * @return The decoration layout.
     */
    public Option<String> getDecorationLayout() {
        return new Option<>(library.gtk_header_bar_get_decoration_layout(getCReference()));
    }

    /**
     * This property overrides the GtkSettings:gtk-decoration-layout setting.
     * <p>
     * There can be valid reasons for overriding the setting, such as a header bar design that does not allow for
     * buttons to take room on the right, or only offers room for a single close button. Split header bars are another
     * example for overriding the setting.
     * <p>
     * The format of the string is button names, separated by commas. A colon separates the buttons that should appear
     * on the left from those on the right. Recognized button names are minimize, maximize, close and icon (the window
     * icon).
     * <p>
     * For example, "icon:minimize,maximize,close" specifies an icon on the left, and minimize, maximize and close
     * buttons on the right.
     *
     * @param layout A decoration layout, or NULL to unset the layout.
     *               <p>
     *               The argument can be NULL.
     */
    public void setDecorationLayout(String layout) {
        library.gtk_header_bar_set_decoration_layout(getCReference(), layout);
    }

    /**
     * Retrieves the title widget of the header.
     * <p>
     * See gtk_header_bar_set_title_widget().
     *
     * @return The title widget of the header, if defined.
     */
    public Option<GtkWidget> getTitleWidget() {
        Option<Pointer> p = new Option<>(library.gtk_header_bar_get_title_widget(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the title for the GtkHeaderBar.
     * <p>
     * When set to NULL, the header-bar will display the title of the window it is contained in.
     * <p>
     * The title should help a user identify the current view. To achieve the same style as the builtin title,
     * use the "title" style class.
     * <p>
     * You should set the title widget to NULL, for the window title label to be visible again.
     *
     * @param w A widget to use for a title.
     *          <p>
     *          The argument can be NULL.
     */
    public void setTitleWidget(GtkWidget w) {
        if (w != null) {
            library.gtk_header_bar_set_title_widget(getCReference(), w.getCReference());
        } else {
            library.gtk_header_bar_set_title_widget(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Adds child to bar, packed with reference to the start of the bar.
     *
     * @param w The GtkWidget to be added to bar.
     */
    public void insertAtBeginning(GtkWidget w) {
        if (w != null) {
            library.gtk_header_bar_pack_start(getCReference(), w.getCReference());
        }
    }

    /**
     * Removes a child from the GtkHeaderBar.
     * <p>
     * The child must have been added with gtk_header_bar_pack_start(), gtk_header_bar_pack_end() or
     * gtk_header_bar_set_title_widget().
     *
     * @param w The child to remove.
     */
    public void remove(GtkWidget w) {
        if (w != null) {
            library.gtk_header_bar_remove(getCReference(), w.getCReference());
        }
    }

    /**
     * Sets whether this header bar shows the standard window title buttons.
     *
     * @param shouldShow TRUE to show standard title buttons.
     */
    public void shouldShowTitleButtons(boolean shouldShow) {
        library.gtk_header_bar_set_show_title_buttons(getCReference(), shouldShow);
    }

    protected static class GtkHeaderBarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the decoration layout of the GtkHeaderBar.
         *
         * @param bar self
         * @return The decoration layout.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_header_bar_get_decoration_layout(Pointer bar);

        /**
         * Returns whether this header bar shows the standard window title buttons.
         *
         * @param bar self
         * @return TRUE if title buttons are shown.
         */
        public native boolean gtk_header_bar_get_show_title_buttons(Pointer bar);

        /**
         * Retrieves the title widget of the header.
         * <p>
         * See gtk_header_bar_set_title_widget().
         *
         * @param bar self
         * @return The title widget of the header. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_header_bar_get_title_widget(Pointer bar);

        /**
         * Creates a new GtkHeaderBar widget.
         *
         * @return A new GtkHeaderBar Type: GtkHeaderBar
         */
        public native Pointer gtk_header_bar_new();

        /**
         * Adds child to bar, packed with reference to the end of the bar.
         *
         * @param bar   self
         * @param child The GtkWidget to be added to bar. Type: GtkWidget
         */
        public native void gtk_header_bar_pack_end(Pointer bar, Pointer child);

        /**
         * Adds child to bar, packed with reference to the start of the bar.
         *
         * @param bar   self
         * @param child The GtkWidget to be added to bar. Type: GtkWidget
         */
        public native void gtk_header_bar_pack_start(Pointer bar, Pointer child);

        /**
         * Removes a child from the GtkHeaderBar.
         * <p>
         * The child must have been added with gtk_header_bar_pack_start(), gtk_header_bar_pack_end() or
         * gtk_header_bar_set_title_widget().
         *
         * @param bar   self
         * @param child The child to remove. Type: GtkWidget
         */
        public native void gtk_header_bar_remove(Pointer bar, Pointer child);

        /**
         * Sets the decoration layout for this header bar.
         * <p>
         * This property overrides the GtkSettings:gtk-decoration-layout setting.
         * <p>
         * There can be valid reasons for overriding the setting, such as a header bar design that does not allow for
         * buttons to take room on the right, or only offers room for a single close button. Split header bars are
         * another example for overriding the setting.
         * <p>
         * The format of the string is button names, separated by commas. A colon separates the buttons that should
         * appear on the left from those on the right. Recognized button names are minimize, maximize, close and
         * icon (the window icon).
         * <p>
         * For example, "icon:minimize,maximize,close" specifies an icon on the left, and minimize, maximize and
         * close buttons on the right.
         *
         * @param bar    self
         * @param layout A decoration layout, or NULL to unset the layout.
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_header_bar_set_decoration_layout(Pointer bar, String layout);

        /**
         * Sets whether this header bar shows the standard window title buttons.
         *
         * @param bar     self
         * @param setting TRUE to show standard title buttons.
         */
        public native void gtk_header_bar_set_show_title_buttons(Pointer bar, boolean setting);

        /**
         * Sets the title for the GtkHeaderBar.
         * <p>
         * When set to NULL, the header-bar will display the title of the window it is contained in.
         * <p>
         * The title should help a user identify the current view. To achieve the same style as the builtin title,
         * use the "title" style class.
         * <p>
         * You should set the title widget to NULL, for the window title label to be visible again.
         *
         * @param bar          self
         * @param title_widget A widget to use for a title. Type: GtkWidget
         *                     <p>
         *                     The argument can be NULL.
         */
        public native void gtk_header_bar_set_title_widget(Pointer bar, Pointer title_widget);
    }
}
