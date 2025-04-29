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
package com.gitlab.ccook.jgtk.interfaces;

import com.gitlab.ccook.jgtk.GdkDisplay;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public interface GtkRoot extends GtkInterface {
    default GdkDisplay getDisplay() {
        return new GdkDisplay(library.gtk_root_get_display(getCReference()));
    }

    /**
     * Retrieves the current focused widget within the root.
     * <p>
     * Note that this is the widget that would have the focus if the root is active; if the root is not focused then
     * gtk_widget_has_focus (widget) will be FALSE for the widget.
     *
     * @return The currently focused widget.
     */
    default Option<GtkWidget> getFocus() {
        Option<Pointer> p = new Option<>(library.gtk_root_get_focus(getCReference()));
        if (p.isDefined()) {
            JGTKObject jgtkObject = JGTKObject.newObjectFromType(p.get(), GtkWidget.class);
            return new Option<>((GtkWidget) jgtkObject);
        }
        return Option.NONE;
    }

    /**
     * If focus is not the current focus widget, and is focusable, sets it as the focus widget for the root.
     * <p>
     * If focus is NULL, unsets the focus widget for the root.
     * <p>
     * To set the focus to a particular widget in the root, it is usually more convenient to use
     * gtk_widget_grab_focus() instead of this function.
     *
     * @param w Widget to be the new focus widget, or NULL to unset the focus widget.
     *          <p>
     *          The argument can be NULL.
     */
    default void setFocus(GtkWidget w) {
        if (w != null) {
            library.gtk_root_set_focus(getCReference(), w.getCReference());
        } else {
            library.gtk_root_set_focus(getCReference(), null);
        }
    }
}
