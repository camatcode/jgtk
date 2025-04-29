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

import com.gitlab.ccook.jgtk.GMenuModel;
import com.gitlab.ccook.jgtk.GtkShortcutManager;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GtkPopoverMenuFlags;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkNative;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkPopoverMenu is a subclass of GtkPopover that implements menu behavior.
 * <p>
 * An example GtkPopoverMenu
 * <p>
 * GtkPopoverMenu treats its children like menus and allows switching between them. It can open submenus as traditional,
 * nested submenus, or in a more touch-friendly sliding fashion.
 * <p>
 * GtkPopoverMenu is meant to be used primarily with menu models, using gtk_popover_menu_new_from_model(). If you need
 * to put other widgets such as a GtkSpinButton or a GtkSwitch into a popover, you can use gtk_popover_menu_add_child().
 * <p>
 * For more dialog-like behavior, use a plain GtkPopover.
 */
@SuppressWarnings("unchecked")
public class GtkPopoverMenu extends GtkPopover implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkShortcutManager {

    private static final GtkPopoverMenuLibrary library = new GtkPopoverMenuLibrary();

    public GtkPopoverMenu(Pointer p) {
        super(p);
    }

    /**
     * Creates a GtkPopoverMenu and populates it according to model.
     * <p>
     * The created buttons are connected to actions found in the GtkApplicationWindow to which the popover belongs -
     * typically by means of being attached to a widget that is contained within the GtkApplicationWindows widget
     * hierarchy.
     * <p>
     * Actions can also be added using gtk_widget_insert_action_group() on the menus attach widget or on any of its
     * parent widgets.
     * <p>
     * This function creates menus with sliding submenus. See gtk_popover_menu_new_from_model_full() for a way to
     * control this.
     *
     * @param model A GMenuModel
     *              <p>
     *              The argument can be NULL.
     */
    public GtkPopoverMenu(GMenuModel model) {
        super(library.gtk_popover_menu_new_from_model(pointerOrNull(model)));
    }

    /**
     * Creates a GtkPopoverMenu and populates it according to model.
     * <p>
     * The created buttons are connected to actions found in the action groups that are accessible from the parent
     * widget. This includes the GtkApplicationWindow to which the popover belongs. Actions can also be added using
     * gtk_widget_insert_action_group() on the parent widget or on any of its parent widgets.
     * <p>
     * The only flag that is supported currently is GTK_POPOVER_MENU_NESTED, which makes GTK create traditional,
     * nested submenus instead of the default sliding submenus.
     *
     * @param model A GMenuModel
     * @param flags Flags that affect how the menu is created.
     */
    public GtkPopoverMenu(GMenuModel model, GtkPopoverMenuFlags flags) {
        super(library.gtk_popover_menu_new_from_model_full(model.getCReference(), flags.getCValue()));
    }

    /**
     * Adds a custom widget to a generated menu.
     * <p>
     * For this to work, the menu model of popover must have an item with a custom attribute that matches id.
     *
     * @param child The GtkWidget to add.
     * @param id    The ID to insert child at.
     * @return TRUE if id was found and the widget added.
     */
    public boolean addChild(GtkWidget child, String id) {
        if (child != null) {
            return library.gtk_popover_menu_add_child(getCReference(), child.getCReference(), id);
        }
        return false;
    }

    /**
     * Returns the menu model used to populate the popover.
     *
     * @return The menu model of popover, if defined
     */
    public Option<GMenuModel> getMenuModel() {
        Option<Pointer> p = new Option<>(library.gtk_popover_menu_get_menu_model(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GMenuModel) JGTKObject.newObjectFromType(p.get(), GMenuModel.class));
        }
        return Option.NONE;
    }

    /**
     * Sets a new menu model on popover.
     * <p>
     * The existing contents of popover are removed, and the popover is populated with new contents according to model.
     *
     * @param m A GMenuModel
     *          <p>
     *          The argument can be NULL.
     */
    public void setMenuModel(GMenuModel m) {
        library.gtk_popover_menu_set_menu_model(getCReference(), pointerOrNull(m));
    }

    /**
     * Removes a widget that has previously been added with gtk_popover_menu_add_child().
     *
     * @param child The GtkWidget to remove.
     * @return TRUE if the widget was removed.
     */
    public boolean removeChild(GtkWidget child) {
        if (child != null) {
            return library.gtk_popover_menu_remove_child(getCReference(), child.getCReference());
        }
        return false;
    }

    protected static class GtkPopoverMenuLibrary extends GtkPopoverLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a custom widget to a generated menu.
         * <p>
         * For this to work, the menu model of popover must have an item with a custom attribute that matches id.
         *
         * @param popover self
         * @param child   The GtkWidget to add.
         * @param id      The ID to insert child at.
         * @return TRUE if id was found and the widget added.
         */
        public native boolean gtk_popover_menu_add_child(Pointer popover, Pointer child, String id);

        /**
         * Returns the menu model used to populate the popover.
         *
         * @param popover self
         * @return The menu model of popover. Type: GMenuModel
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_popover_menu_get_menu_model(Pointer popover);

        /**
         * Creates a GtkPopoverMenu and populates it according to model.
         * <p>
         * The created buttons are connected to actions found in the GtkApplicationWindow to which the popover belongs
         * - typically by means of being attached to a widget that is contained within the GtkApplicationWindows widget
         * hierarchy.
         * <p>
         * Actions can also be added using gtk_widget_insert_action_group() on the menus attach widget or on any of its
         * parent widgets.
         * <p>
         * This function creates menus with sliding submenus. See gtk_popover_menu_new_from_model_full() for a way to
         * control this.
         *
         * @param model A GMenuModel. Type: GMenuModel
         *              <p>
         *              The argument can be NULL.
         * @return The new GtkPopoverMenu. Type: GtkPopoverMenu
         */
        public native Pointer gtk_popover_menu_new_from_model(Pointer model);

        /**
         * Creates a GtkPopoverMenu and populates it according to model.
         * <p>
         * The created buttons are connected to actions found in the action groups that are accessible from the parent
         * widget. This includes the GtkApplicationWindow to which the popover belongs. Actions can also be added using
         * gtk_widget_insert_action_group() on the parent widget or on any of its parent widgets.
         * <p>
         * The only flag that is supported currently is GTK_POPOVER_MENU_NESTED, which makes GTK create traditional,
         * nested submenus instead of the default sliding submenus.
         *
         * @param model A GMenuModel. Type: GMenuModel
         * @param flags Flags that affect how the menu is created. Type: GtkPopoverMenuFlags
         * @return The new GtkPopoverMenu. Type: GtkPopoverMenu
         */
        public native Pointer gtk_popover_menu_new_from_model_full(Pointer model, int flags);

        /**
         * Removes a widget that has previously been added with gtk_popover_menu_add_child()
         *
         * @param popover self
         * @param child   The GtkWidget to remove. Type: GtkWidget
         * @return TRUE if the widget was removed.
         */
        public native boolean gtk_popover_menu_remove_child(Pointer popover, Pointer child);

        /**
         * Sets a new menu model on popover.
         * <p>
         * The existing contents of popover are removed, and the popover is populated with new contents according to
         * model.
         *
         * @param popover self
         * @param model   A GMenuModel. Type: GMenuModel
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_popover_menu_set_menu_model(Pointer popover, Pointer model);
    }
}
