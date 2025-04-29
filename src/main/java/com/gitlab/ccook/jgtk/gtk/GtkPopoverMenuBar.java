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
package com.gitlab.ccook.jgtk.gtk;

import com.gitlab.ccook.jgtk.GMenuModel;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkPopoverMenuBar presents a horizontal bar of items that pop up popover menus when clicked.
 * <p>
 * The only way to create instances of GtkPopoverMenuBar is from a GMenuModel.
 */
@SuppressWarnings("unchecked")
public class GtkPopoverMenuBar extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {
    private static final GtkPopoverMenuBarLibrary library = new GtkPopoverMenuBarLibrary();

    public GtkPopoverMenuBar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a GtkPopoverMenuBar from a GMenuModel.
     *
     * @param model A GMenuModel
     *              <p>
     *              The argument can be NULL.
     */
    public GtkPopoverMenuBar(GMenuModel model) {
        super(library.gtk_popover_menu_bar_new_from_model(pointerOrNull(model)));
    }

    /**
     * Adds a custom widget to a generated menubar.
     * <p>
     * For this to work, the menu model of bar must have an item with a custom attribute that matches id.
     *
     * @param child     The GtkWidget to add.
     * @param idInModel The ID to insert child at.
     * @return TRUE if id was found and the widget added.
     */
    public boolean addChild(String idInModel, GtkWidget child) {
        if (child != null) {
            return library.gtk_popover_menu_bar_add_child(getCReference(), child.getCReference(), idInModel);
        }
        return false;
    }

    /**
     * Returns the model from which the contents of bar are taken.
     *
     * @return A GMenuModel
     *         <p>
     *         The return value can be NULL.
     */
    public Option<GMenuModel> getMenuModel() {
        Option<Pointer> p = new Option<>(library.gtk_popover_menu_bar_get_menu_model(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GMenuModel) JGTKObject.newObjectFromType(p.get(), GMenuModel.class));
        }
        return Option.NONE;
    }

    /**
     * Sets a menu model from which bar should take its contents.
     *
     * @param model A GMenuModel
     *              <p>
     *              The argument can be NULL.
     */
    public void setMenuModel(GMenuModel model) {
        library.gtk_popover_menu_bar_set_menu_model(getCReference(), pointerOrNull(model));
    }

    /**
     * Removes a widget that has previously been added with gtk_popover_menu_bar_add_child().
     *
     * @param toRemove The GtkWidget to remove.
     * @return TRUE if the widget was removed.
     */
    public boolean removeChild(GtkWidget toRemove) {
        if (toRemove != null) {
            return library.gtk_popover_menu_bar_remove_child(getCReference(), toRemove.getCReference());
        }
        return false;
    }

    protected static class GtkPopoverMenuBarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a custom widget to a generated menubar.
         * <p>
         * For this to work, the menu model of bar must have an item with a custom attribute that matches id.
         *
         * @param bar   self
         * @param child The GtkWidget to add.
         * @param id    The ID to insert child at.
         * @return TRUE if id was found and the widget added.
         */
        public native boolean gtk_popover_menu_bar_add_child(Pointer bar, Pointer child, String id);

        /**
         * Returns the model from which the contents of bar are taken.
         *
         * @param bar self
         * @return A GMenuModel
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_popover_menu_bar_get_menu_model(Pointer bar);

        /**
         * Creates a GtkPopoverMenuBar from a GMenuModel.
         *
         * @param model A GMenuModel
         *              <p>
         *              The argument can be NULL.
         * @return A new GtkPopoverMenuBar
         */
        public native Pointer gtk_popover_menu_bar_new_from_model(Pointer model);

        /**
         * Removes a widget that has previously been added with gtk_popover_menu_bar_add_child().
         *
         * @param bar   self
         * @param child The GtkWidget to remove.
         * @return TRUE if the widget was removed.
         */
        public native boolean gtk_popover_menu_bar_remove_child(Pointer bar, Pointer child);

        /**
         * Sets a menu model from which bar should take its contents.
         *
         * @param bar   self
         * @param model A GMenuModel
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_popover_menu_bar_set_menu_model(Pointer bar, Pointer model);
    }
}
