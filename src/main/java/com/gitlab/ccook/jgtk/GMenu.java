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
package com.gitlab.ccook.jgtk;

import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GMenu is a simple implementation of GMenuModel. You populate a GMenu by adding GMenuItem instances to it.
 * <p>
 * There are some convenience functions to allow you to directly add items (avoiding GMenuItem) for the common cases.
 * To add a regular item, use g_menu_insert(). To add a section, use g_menu_insert_section(). To add a submenu,
 * use g_menu_insert_submenu().
 */
public class GMenu extends GMenuModel {
    private static final GMenuLibrary library = new GMenuLibrary();

    public GMenu(Pointer reference) {
        super(reference);
    }

    /**
     * Creates a new GMenu.
     * <p>
     * The new menu has no items.
     */
    public GMenu() {
        super(library.g_menu_new());
    }

    /**
     * Convenience function for appending a normal menu item to the end of menu. Combine g_menu_item_new() and
     * g_menu_insert_item() for a more flexible alternative.
     *
     * @param label          The section label, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     * @param detailedAction The detailed action string, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     */
    public void append(String label, String detailedAction) {
        library.g_menu_append(getCReference(), label, detailedAction);
    }

    /**
     * Appends item to the end of menu.
     * <p>
     * See g_menu_insert_item() for more information.
     *
     * @param item A GMenuItem to append.
     */
    public void append(GMenuItem item) {
        if (item != null) {
            library.g_menu_append_item(getCReference(), item.getCReference());
        }
    }

    /**
     * Convenience function for appending a section menu item to the end of menu. Combine g_menu_item_new_section() and
     * g_menu_insert_item() for a more flexible alternative.
     *
     * @param label   The section label, or NULL.
     *                <p>
     *                The argument can be NULL.
     * @param section A GMenuModel with the items of the section.
     */
    public void appendSection(String label, GMenuModel section) {
        if (section != null) {
            library.g_menu_append_section(getCReference(), label, section.getCReference());
        }
    }

    /**
     * Convenience function for appending a submenu menu item to the end of menu. Combine g_menu_item_new_submenu()
     * and g_menu_insert_item() for a more flexible alternative.
     *
     * @param label   The section label, or NULL.
     *                <p>
     *                The argument can be NULL.
     * @param subMenu A GMenuModel with the items of the submenu.
     */
    public void appendSubMenu(String label, GMenuModel subMenu) {
        if (subMenu != null) {
            library.g_menu_append_submenu(getCReference(), label, subMenu.getCReference());
        }
    }

    /**
     * Removes all items in the menu.
     */
    public void clear() {
        library.g_menu_remove_all(getCReference());
    }

    /**
     * Marks menu as frozen.
     * <p>
     * After the menu is frozen, it is an error to attempt to make any changes to it. In effect this means that the
     * GMenu API must no longer be used.
     * <p>
     * This function causes g_menu_model_is_mutable() to begin returning FALSE, which has some positive performance
     * implications.
     */
    public void freeze() {
        library.g_menu_freeze(getCReference());
    }

    /**
     * Convenience function for inserting a normal menu item into menu. Combine g_menu_item_new() and
     * g_menu_insert_item() for a more flexible alternative.
     *
     * @param position       The position at which to insert the item.
     * @param label          The section label, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     * @param detailedAction The detailed action string, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     */
    public void insert(int position, String label, String detailedAction) {
        library.g_menu_insert(getCReference(), position, label, detailedAction);
    }

    /**
     * Inserts item into menu.
     * <p>
     * The "insertion" is actually done by copying all the attribute and link values of item and using them to form
     * a new item within menu. As such, item itself is not really inserted, but rather, a menu item that is exactly the
     * same as the one presently described by item.
     * <p>
     * This means that item is essentially useless after the insertion occurs. Any changes you make to it are ignored
     * unless it is inserted again (at which point its updated values will be copied).
     * <p>
     * You should probably just free item once you're done.
     * <p>
     * There are many convenience functions to take care of common cases. See g_menu_insert(), g_menu_insert_section()
     * and g_menu_insert_submenu() as well as "prepend" and "append" variants of each of these functions.
     *
     * @param position The position at which to insert the item.
     * @param item     The GMenuItem to insert.
     */
    public void insert(int position, GMenuItem item) {
        if (item != null) {
            library.g_menu_insert_item(getCReference(), position, item.getCReference());
        }
    }

    /**
     * Convenience function for inserting a section menu item into menu. Combine g_menu_item_new_section() and
     * g_menu_insert_item() for a more flexible alternative.
     *
     * @param position The position at which to insert the item.
     * @param label    The section label, or NULL.
     *                 <p>
     *                 The argument can be NULL.
     * @param section  A GMenuModel with the items of the section.
     */
    public void insertSection(int position, String label, GMenuModel section) {
        if (section != null) {
            library.g_menu_insert_section(getCReference(), position, label, section.getCReference());
        }
    }

    /**
     * Convenience function for inserting a submenu menu item into menu. Combine g_menu_item_new_submenu() and
     * g_menu_insert_item() for a more flexible alternative.
     *
     * @param position The position at which to insert the item.
     * @param label    The section label, or NULL.
     *                 <p>
     *                 The argument can be NULL.
     * @param subMenu  A GMenuModel with the items of the submenu.
     */
    public void insertSubMenu(int position, String label, GMenuModel subMenu) {
        if (subMenu != null) {
            library.g_menu_insert_submenu(getCReference(), position, label, subMenu.getCReference());
        }
    }

    /**
     * Convenience function for prepending a normal menu item to the start of menu. Combine g_menu_item_new() and
     * g_menu_insert_item() for a more flexible alternative.
     *
     * @param label          The section label, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     * @param detailedAction The detailed action string, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     */
    public void prepend(String label, String detailedAction) {
        library.g_menu_prepend(getCReference(), label, detailedAction);
    }

    /**
     * Prepends item to the start of menu.
     * <p>
     * See g_menu_insert_item() for more information.
     *
     * @param item A GMenuItem to prepend.
     */
    public void prepend(GMenuItem item) {
        if (item != null) {
            library.g_menu_prepend_item(getCReference(), item.getCReference());
        }
    }

    /**
     * Convenience function for prepending a section menu item to the start of menu. Combine g_menu_item_new_section()
     * and g_menu_insert_item() for a more flexible alternative.
     *
     * @param label   The section label, or NULL.
     *                <p>
     *                The argument can be NULL.
     * @param section A GMenuModel with the items of the section.
     */
    public void prependSection(String label, GMenuModel section) {
        if (section != null) {
            library.g_menu_prepend_section(getCReference(), label, section.getCReference());
        }
    }

    /**
     * Convenience function for prepending a submenu menu item to the start of menu. Combine g_menu_item_new_submenu()
     * and g_menu_insert_item() for a more flexible alternative.
     *
     * @param label   The section label, or NULL.
     *                <p>
     *                The argument can be NULL.
     * @param submenu A GMenuModel with the items of the submenu. Type: GMenuModel
     */
    public void prependSubMenu(String label, GMenuModel submenu) {
        if (submenu != null) {
            library.g_menu_prepend_submenu(getCReference(), label, submenu.getCReference());
        }
    }

    /**
     * Removes an item from the menu.
     * <p>
     * position gives the index of the item to remove.
     * <p>
     * It is an error if position is not in range the range from 0 to one less than the number of items in the menu.
     * <p>
     * It is not possible to remove items by identity since items are added to the menu simply by copying their links
     * and attributes (ie: identity of the item itself is not preserved).
     *
     * @param position The position of the item to remove.
     */
    public void remove(int position) {
        library.g_menu_remove(getCReference(), position);
    }

    protected static class GMenuLibrary extends GMenuModelLibrary {
        static {
            Native.register("gtk-4");
        }

        public native void g_menu_append(Pointer menu, String label, String detailedAction);

        /**
         * Creates a new GMenu.
         * <p>
         * The new menu has no items.
         *
         * @return A new GMenu. Type: GMenu
         */
        public native Pointer g_menu_new();

        /**
         * Appends item to the end of menu.
         * <p>
         * See g_menu_insert_item() for more information.
         *
         * @param menu self
         * @param item A GMenuItem to append.
         */
        public native void g_menu_append_item(Pointer menu, Pointer item);

        /**
         * Convenience function for appending a section menu item to the end of menu. Combine g_menu_item_new_section()
         * and g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu    self
         * @param label   The section label, or NULL.
         *                <p>
         *                The argument can be NULL.
         * @param section A GMenuModel with the items of the section. Type: GMenuModel
         */
        public native void g_menu_append_section(Pointer menu, String label, Pointer section);

        /**
         * Convenience function for appending a submenu menu item to the end of menu. Combine g_menu_item_new_submenu()
         * and g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu    self
         * @param label   The section label, or NULL.
         *                <p>
         *                The argument can be NULL.
         * @param submenu A GMenuModel with the items of the submenu.
         */
        public native void g_menu_append_submenu(Pointer menu, String label, Pointer submenu);

        /**
         * Marks menu as frozen.
         * <p>
         * After the menu is frozen, it is an error to attempt to make any changes to it. In effect this means that the
         * GMenu API must no longer be used.
         * <p>
         * This function causes g_menu_model_is_mutable() to begin returning FALSE, which has some positive performance
         * implications.
         *
         * @param menu self
         */
        public native void g_menu_freeze(Pointer menu);

        /**
         * Convenience function for inserting a normal menu item into menu. Combine g_menu_item_new() and
         * g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu            self
         * @param position        The position at which to insert the item.
         * @param label           The section label, or NULL.
         *                        <p>
         *                        The argument can be NULL.
         * @param detailed_action The detailed action string, or NULL.
         *                        <p>
         *                        The argument can be NULL.
         */
        public native void g_menu_insert(Pointer menu, int position, String label, String detailed_action);

        /**
         * Inserts item into menu.
         * <p>
         * The "insertion" is actually done by copying all the attribute and link values of item and using them to
         * form a new item within menu. As such, item itself is not really inserted, but rather, a menu item that is
         * exactly the same as the one presently described by item.
         * <p>
         * This means that item is essentially useless after the insertion occurs. Any changes you make to it are
         * ignored unless it is inserted again (at which point its updated values will be copied).
         * <p>
         * You should probably just free item once you're done.
         * <p>
         * There are many convenience functions to take care of common cases. See g_menu_insert(),
         * g_menu_insert_section() and g_menu_insert_submenu() as well as "prepend" and "append" variants of each of
         * these functions.
         *
         * @param menu     self
         * @param position The position at which to insert the item.
         * @param item     The GMenuItem to insert. Type: GMenuItem
         */
        public native void g_menu_insert_item(Pointer menu, int position, Pointer item);

        /**
         * Convenience function for inserting a section menu item into menu. Combine g_menu_item_new_section() and
         * g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu     self
         * @param position The position at which to insert the item.
         * @param label    The section label, or NULL.
         *                 <p>
         *                 The argument can be NULL.
         * @param section  A GMenuModel with the items of the section. Type: GMenuModel
         */
        public native void g_menu_insert_section(Pointer menu, int position, String label, Pointer section);

        /**
         * Convenience function for inserting a submenu menu item into menu. Combine g_menu_item_new_submenu() and
         * g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu     self
         * @param position The position at which to insert the item.
         * @param label    The section label, or NULL.
         *                 <p>
         *                 The argument can be NULL.
         * @param submenu  A GMenuModel with the items of the submenu. Type: GMenuModel
         */
        public native void g_menu_insert_submenu(Pointer menu, int position, String label, Pointer submenu);

        /**
         * Convenience function for prepending a normal menu item to the start of menu. Combine g_menu_item_new() and
         * g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu            self
         * @param label           The section label, or NULL.
         *                        <p>
         *                        The argument can be NULL.
         * @param detailed_action The detailed action string, or NULL.
         *                        <p>
         *                        The argument can be NULL.
         */
        public native void g_menu_prepend(Pointer menu, String label, String detailed_action);

        /**
         * Prepends item to the start of menu.
         * <p>
         * See g_menu_insert_item() for more information.
         *
         * @param menu self
         * @param item A GMenuItem to prepend. Type: GMenuItem
         */
        public native void g_menu_prepend_item(Pointer menu, Pointer item);

        /**
         * Convenience function for prepending a section menu item to the start of menu. Combine
         * g_menu_item_new_section() and g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu    self
         * @param label   The section label, or NULL.
         *                <p>
         *                The argument can be NULL.
         * @param section A GMenuModel with the items of the section. Type: GMenuModel
         */
        public native void g_menu_prepend_section(Pointer menu, String label, Pointer section);

        /**
         * Convenience function for prepending a submenu menu item to the start of menu. Combine
         * g_menu_item_new_submenu() and g_menu_insert_item() for a more flexible alternative.
         *
         * @param menu    self
         * @param label   The section label, or NULL.
         *                <p>
         *                The argument can be NULL.
         * @param submenu A GMenuModel with the items of the submenu. Type: GMenuModel
         */
        public native void g_menu_prepend_submenu(Pointer menu, String label, Pointer submenu);

        /**
         * Removes an item from the menu.
         * <p>
         * position gives the index of the item to remove.
         * <p>
         * It is an error if position is not in range the range from 0 to one less than the number of items in the menu.
         * <p>
         * It is not possible to remove items by identity since items are added to the menu simply by copying their
         * links and attributes (ie: identity of the item itself is not preserved).
         *
         * @param menu     self
         * @param position The position of the item to remove.
         */
        public native void g_menu_remove(Pointer menu, int position);

        /**
         * Removes all items in the menu.
         *
         * @param menu self
         */
        public native void g_menu_remove_all(Pointer menu);
    }

}
