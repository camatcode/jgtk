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
package com.gitlab.ccook.jgtk;

import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GMenuItem is an opaque structure type.
 */
@SuppressWarnings("unchecked")
public class GMenuItem extends JGTKConnectableObject {
    private static final GMenuItemLibrary library = new GMenuItemLibrary();

    public GMenuItem(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GMenuItem.
     * <p>
     * If label is non-NULL it is used to set the "label" attribute of the new item.
     * <p>
     * If detailed_action is non-NULL it is used to set the "action" and possibly the "target" attribute of the new
     * item. See g_menu_item_set_detailed_action() for more information.
     *
     * @param label          The section label, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     * @param detailedAction The detailed action string, or NULL.
     *                       <p>
     *                       The argument can be NULL.
     */
    public GMenuItem(String label, String detailedAction) {
        super(library.g_menu_item_new(label, detailedAction));
    }

    /**
     * Creates a GMenuItem as an exact copy of an existing menu item in a GMenuModel.
     * <p>
     * item_index must be valid (ie: be sure to call g_menu_model_get_n_items() first).
     *
     * @param model     A GMenuModel.
     * @param itemIndex The index of an item in model.
     */
    public GMenuItem(GMenuModel model, int itemIndex) {
        super(library.g_menu_item_new_from_model(model.getCReference(), itemIndex));
    }

    /**
     * Creates a new GMenuItem representing a section.
     * <p>
     * This is a convenience API around g_menu_item_new() and g_menu_item_set_section().
     * <p>
     * The effect of having one menu appear as a section of another is exactly as it sounds: the items from section
     * become a direct part of the menu that menu_item is added to.
     * <p>
     * Visual separation is typically displayed between two non-empty sections. If label is non-NULL then it will be
     * incorporated into this visual indication. This allows for labeled subsections of a menu.
     * <p>
     * As a simple example, consider a typical "Edit" menu from a simple program. It probably contains an "Undo" and
     * "Redo" item, followed by a separator, followed by "Cut", "Copy" and "Paste".
     *
     * @param label   The section label, or NULL.
     *                <p>
     *                The argument can be NULL.
     * @param section A GMenuModel with the items of the section.
     * @return A new GMenuItem.
     */
    public static Option<GMenuItem> newSection(String label, GMenuModel section) {
        if (section != null) {
            return new Option<>(new GMenuItem(library.g_menu_item_new_section(label, section.getCReference())));
        }
        return Option.NONE;
    }

    /**
     * Creates a new GMenuItem representing a submenu.
     * <p>
     * This is a convenience API around g_menu_item_new() and g_menu_item_set_submenu().
     *
     * @param label   The section label, or NULL.
     *                <p>
     *                The argument can be NULL.
     * @param submenu A GMenuModel with the items of the submenu.
     * @return A new GMenuItem.
     */
    public static Option<GMenuItem> newSubMenu(String label, GMenuModel submenu) {
        if (submenu != null) {
            return new Option<>(new GMenuItem(library.g_menu_item_new_submenu(label, submenu.getCReference())));
        }
        return Option.NONE;
    }

    /**
     * Queries the named attribute on menu_item.
     * <p>
     * If expected_type is specified and the attribute does not have this type, NULL is returned. NULL is also returned
     * if the attribute simply does not exist.
     *
     * @param attribute The attribute name to query.
     * @param type      The expected type of the attribute.
     *                  <p>
     *                  The argument can be NULL.
     * @return The attribute value, or NONE.
     */
    public Option<GVariant> getAttribute(String attribute, GVariantType type) {
        Option<Pointer> p = new Option<>(library.g_menu_item_get_attribute_value(getCReference(), attribute, pointerOrNull(type)));
        if (p.isDefined()) {
            return new Option<>(new GVariant(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Queries the named link on menu_item.
     *
     * @param linkName The link name to query.
     * @return The link, or NONE.
     */
    public Option<GMenuModel> getLink(String linkName) {
        Option<Pointer> p = new Option<>(library.g_menu_item_get_link(getCReference(), linkName));
        if (p.isDefined()) {
            return new Option<>((GMenuModel) JGTKObject.newObjectFromType(p.get(), GMenuModel.class));
        }
        return Option.NONE;
    }

    /**
     * Sets or unsets the "action" and "target" attributes of menu_item.
     * <p>
     * If action is NULL then both the "action" and "target" attributes are unset (and target_value is ignored).
     * <p>
     * If action is non-NULL then the "action" attribute is set. The "target" attribute is then set to the value of
     * target_value if it is non-NULL or unset otherwise.
     * <p>
     * Normal menu items (ie: not submenu, section or other custom item types) are expected to have the "action"
     * attribute set to identify the action that they are associated with. The state type of the action help to
     * determine the disposition of the menu item. See GAction and GActionGroup for an overview of actions.
     * <p>
     * In general, clicking on the menu item will result in activation of the named action with the "target" attribute
     * given as the parameter to the action invocation. If the "target" attribute is not set then the action is invoked
     * with no parameter.
     * <p>
     * If the action has no state then the menu item is usually drawn as a plain menu item (ie: with no additional
     * decoration).
     * <p>
     * If the action has a boolean state then the menu item is usually drawn as a toggle menu item (ie: with a checkmark
     * or equivalent indication). The item should be marked as 'toggled' or 'checked' when the boolean state is TRUE.
     * <p>
     * If the action has a string state then the menu item is usually drawn as a radio menu item (ie: with a radio
     * bullet or equivalent indication). The item should be marked as 'selected' when the string state is equal to the
     * value of the target property.
     * <p>
     * See g_menu_item_set_action_and_target() or g_menu_item_set_detailed_action() for two equivalent calls that are
     * probably more convenient for most uses.
     *
     * @param action      The name of the action for this item.
     *                    <p>
     *                    The argument can be NULL.
     * @param targetValue A GVariant to use as the action target.
     *                    <p>
     *                    The argument can be NULL.
     */
    public void setActionAndTarget(String action, GVariant targetValue) {
        library.g_menu_item_set_action_and_target_value(getCReference(), action, pointerOrNull(targetValue));
    }

    /**
     * Sets or unsets an attribute on menu_item.
     * <p>
     * The attribute to set or unset is specified by attribute. This can be one of the standard attribute names
     * G_MENU_ATTRIBUTE_LABEL, G_MENU_ATTRIBUTE_ACTION, G_MENU_ATTRIBUTE_TARGET, or a custom attribute name.
     * Attribute names are restricted to lowercase characters, numbers and '-'. Furthermore, the names must begin
     * with a lowercase character, must not end with a '-', and must not contain consecutive dashes.
     * <p>
     * must consist only of lowercase ASCII characters, digits and '-'.
     * <p>
     * If value is non-NULL then it is used as the new value for the attribute. If value is NULL then the attribute
     * is unset. If the value GVariant is floating, it is consumed.
     * <p>
     * See also g_menu_item_set_attribute() for a more convenient way to do the same.
     *
     * @param attribute The attribute to set.
     * @param value     A GVariant to use as the value, or NULL.
     *                  <p>
     *                  The argument can be NULL.
     */
    public void setAttribute(String attribute, GVariant value) {
        library.g_menu_item_set_attribute_value(getCReference(), attribute, pointerOrNull(value));
    }

    /**
     * Sets the "action" and possibly the "target" attribute of menu_item.
     * <p>
     * The format of detailed_action is the same format parsed by g_action_parse_detailed_name().
     * <p>
     * See g_menu_item_set_action_and_target() or g_menu_item_set_action_and_target_value() for more flexible
     * (but slightly less convenient) alternatives.
     * <p>
     * See also g_menu_item_set_action_and_target_value() for a description of the semantics of the action and
     * target attributes.
     *
     * @param detailedAction The "detailed" action string.
     */
    public void setDetailedAction(String detailedAction) {
        if (detailedAction != null) {
            library.g_menu_item_set_detailed_action(getCReference(), detailedAction);
        }
    }

    /**
     * Sets (or unsets) the icon on menu_item.
     * <p>
     * This call is the same as calling g_icon_serialize() and using the result as the value to
     * g_menu_item_set_attribute_value() for G_MENU_ATTRIBUTE_ICON.
     * <p>
     * This API is only intended for use with "noun" menu items; things like bookmarks or applications in an
     * "Open With" menu. Don't use it on menu items corresponding to verbs (eg: stock icons for 'Save' or 'Quit').
     * <p>
     * If icon is NULL then the icon is unset.
     *
     * @param icon A GIcon, or NULL.
     */
    public void setIcon(GIcon icon) {
        library.g_menu_item_set_icon(getCReference(), pointerOrNull(icon));
    }

    /**
     * Sets or unsets the "label" attribute of menu_item.
     * <p>
     * If label is non-NULL it is used as the label for the menu item. If it is NULL then the label attribute is
     * unset.
     *
     * @param label The label to set, or NULL to unset.
     *              <p>
     *              The argument can be NULL.
     */
    public void setLabel(String label) {
        library.g_menu_item_set_label(getCReference(), label);
    }

    /**
     * Creates a link from menu_item to model if non-NULL, or unsets it.
     * <p>
     * Links are used to establish a relationship between a particular menu item and another menu. For example,
     * G_MENU_LINK_SUBMENU is used to associate a submenu with a particular menu item, and G_MENU_LINK_SECTION is
     * used to create a section. Other types of link can be used, but there is no guarantee that clients will be
     * able to make sense of them. Link types are restricted to lowercase characters, numbers and '-'. Furthermore,
     * the names must begin with a lowercase character, must not end with a '-', and must not contain consecutive
     * dashes.
     *
     * @param link  Type of link to establish or unset.
     * @param model The GMenuModel to link to (or NULL to unset)
     *              <p>
     *              The argument can be NULL.
     */
    public void setLink(String link, GMenuModel model) {
        library.g_menu_item_set_link(getCReference(), link, pointerOrNull(model));
    }

    /**
     * Sets or unsets the "section" link of menu_item to section.
     * <p>
     * The effect of having one menu appear as a section of another is exactly as it sounds: the items from section
     * become a direct part of the menu that menu_item is added to. See g_menu_item_new_section() for more
     * information about what it means for a menu item to be a section.
     *
     * @param section A GMenuModel, or NULL.
     *                <p>
     *                The argument can be NULL.
     */
    public void setSection(GMenuModel section) {
        library.g_menu_item_set_section(getCReference(), pointerOrNull(section));
    }

    public void setSubMenu(GMenuModel subMenu) {
        library.g_menu_item_set_submenu(getCReference(), pointerOrNull(subMenu));
    }

    protected static class GMenuItemLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Queries the named attribute on menu_item.
         * <p>
         * If expected_type is specified and the attribute does not have this type, NULL is returned. NULL is also
         * returned if the attribute simply does not exist.
         *
         * @param item          self
         * @param attribute     The attribute name to query.
         * @param expected_type The expected type of the attribute.
         *                      <p>
         *                      The argument can be NULL.
         * @return The attribute value, or NULL.
         *         The return value can be NULL.
         */
        public native Pointer g_menu_item_get_attribute_value(Pointer item, String attribute, Pointer expected_type);

        /**
         * Queries the named link on menu_item.
         *
         * @param item self
         * @param link The link name to query.
         * @return The link, or NULL. Type: GMenuModel
         *         The return value can be NULL.
         */
        public native Pointer g_menu_item_get_link(Pointer item, String link);

        /**
         * Creates a new GMenuItem.
         * <p>
         * If label is non-NULL it is used to set the "label" attribute of the new item.
         * <p>
         * If detailed_action is non-NULL it is used to set the "action" and possibly the "target" attribute of the new
         * item. See g_menu_item_set_detailed_action() for more information.
         *
         * @param label           The section label, or NULL.
         *                        <p>
         *                        The argument can be NULL.
         * @param detailed_action The detailed action string, or NULL.
         *                        <p>
         *                        The argument can be NULL.
         * @return A new GMenuItem. Type: GMenuItem
         */
        public native Pointer g_menu_item_new(String label, String detailed_action);

        /**
         * Creates a GMenuItem as an exact copy of an existing menu item in a GMenuModel.
         * <p>
         * item_index must be valid (ie: be sure to call g_menu_model_get_n_items() first).
         *
         * @param model      A GMenuModel. Type: GMenuModel
         * @param item_index The index of an item in model.
         * @return A new GMenuItem. Type: GMenuItem
         */
        public native Pointer g_menu_item_new_from_model(Pointer model, int item_index);

        /**
         * Creates a new GMenuItem representing a section.
         * <p>
         * This is a convenience API around g_menu_item_new() and g_menu_item_set_section().
         * <p>
         * The effect of having one menu appear as a section of another is exactly as it sounds: the items from section
         * become a direct part of the menu that menu_item is added to.
         * <p>
         * Visual separation is typically displayed between two non-empty sections. If label is non-NULL then it will be
         * incorporated into this visual indication. This allows for labeled subsections of a menu.
         * <p>
         * As a simple example, consider a typical "Edit" menu from a simple program. It probably contains an "Undo"
         * and "Redo" item, followed by a separator, followed by "Cut", "Copy" and "Paste".
         *
         * @param label   The section label, or NULL.
         *                <p>
         *                The argument can be NULL.
         * @param section A GMenuModel with the items of the section. Type: GMenuModel
         * @return A new GMenuItem. Type: GMenuItem
         */
        public native Pointer g_menu_item_new_section(String label, Pointer section);

        /**
         * Creates a new GMenuItem representing a submenu.
         * <p>
         * This is a convenience API around g_menu_item_new() and g_menu_item_set_submenu().
         *
         * @param label   The section label, or NULL.
         *                <p>
         *                The argument can be NULL.
         * @param submenu A GMenuModel with the items of the submenu. Type: GMenuModel
         * @return A new GMenuItem. Type: GMenuItem
         */
        public native Pointer g_menu_item_new_submenu(String label, Pointer submenu);

        /**
         * Sets or unsets the "action" and "target" attributes of menu_item.
         * <p>
         * If action is NULL then both the "action" and "target" attributes are unset (and target_value is ignored).
         * <p>
         * If action is non-NULL then the "action" attribute is set. The "target" attribute is then set to the value of
         * target_value if it is non-NULL or unset otherwise.
         * <p>
         * Normal menu items (ie: not submenu, section or other custom item types) are expected to have the "action"
         * attribute set to identify the action that they are associated with. The state type of the action help to
         * determine the disposition of the menu item. See GAction and GActionGroup for an overview of actions.
         * <p>
         * In general, clicking on the menu item will result in activation of the named action with the "target"
         * attribute given as the parameter to the action invocation. If the "target" attribute is not set then the
         * action is invoked with no parameter.
         * <p>
         * If the action has no state then the menu item is usually drawn as a plain menu item (ie: with no additional
         * decoration).
         * <p>
         * If the action has a boolean state then the menu item is usually drawn as a toggle menu item (ie: with a
         * checkmark or equivalent indication). The item should be marked as 'toggled' or 'checked' when the boolean
         * state is TRUE.
         * <p>
         * If the action has a string state then the menu item is usually drawn as a radio menu item (ie: with a radio
         * bullet or equivalent indication). The item should be marked as 'selected' when the string state is equal to
         * the value of the target property.
         * <p>
         * See g_menu_item_set_action_and_target() or g_menu_item_set_detailed_action() for two equivalent calls that
         * are probably more convenient for most uses.
         *
         * @param item         self
         * @param action       The name of the action for this item.
         *                     <p>
         *                     The argument can be NULL.
         * @param target_value A GVariant to use as the action target. Type: GVariant
         *                     <p>
         *                     The argument can be NULL.
         */
        public native void g_menu_item_set_action_and_target_value(Pointer item, String action, Pointer target_value);

        /**
         * Sets or unsets an attribute on menu_item.
         * <p>
         * The attribute to set or unset is specified by attribute. This can be one of the standard attribute names
         * G_MENU_ATTRIBUTE_LABEL, G_MENU_ATTRIBUTE_ACTION, G_MENU_ATTRIBUTE_TARGET, or a custom attribute name.
         * Attribute names are restricted to lowercase characters, numbers and '-'. Furthermore, the names must begin
         * with a lowercase character, must not end with a '-', and must not contain consecutive dashes.
         * <p>
         * must consist only of lowercase ASCII characters, digits and '-'.
         * <p>
         * If value is non-NULL then it is used as the new value for the attribute. If value is NULL then the attribute
         * is unset. If the value GVariant is floating, it is consumed.
         * <p>
         * See also g_menu_item_set_attribute() for a more convenient way to do the same.
         *
         * @param item      self
         * @param attribute The attribute to set.
         * @param value     A GVariant to use as the value, or NULL. Type: GVariant
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void g_menu_item_set_attribute_value(Pointer item, String attribute, Pointer value);

        /**
         * Sets the "action" and possibly the "target" attribute of menu_item.
         * <p>
         * The format of detailed_action is the same format parsed by g_action_parse_detailed_name().
         * <p>
         * See g_menu_item_set_action_and_target() or g_menu_item_set_action_and_target_value() for more flexible
         * (but slightly less convenient) alternatives.
         * <p>
         * See also g_menu_item_set_action_and_target_value() for a description of the semantics of the action and
         * target attributes.
         *
         * @param item            self
         * @param detailed_action The "detailed" action string.
         */
        public native void g_menu_item_set_detailed_action(Pointer item, String detailed_action);

        /**
         * Sets (or unsets) the icon on menu_item.
         * <p>
         * This call is the same as calling g_icon_serialize() and using the result as the value to
         * g_menu_item_set_attribute_value() for G_MENU_ATTRIBUTE_ICON.
         * <p>
         * This API is only intended for use with "noun" menu items; things like bookmarks or applications in an
         * "Open With" menu. Don't use it on menu items corresponding to verbs (eg: stock icons for 'Save' or 'Quit').
         * <p>
         * If icon is NULL then the icon is unset.
         *
         * @param item self
         * @param icon A GIcon, or NULL. Type: GIcon
         */
        public native void g_menu_item_set_icon(Pointer item, Pointer icon);

        /**
         * Sets or unsets the "label" attribute of menu_item.
         * <p>
         * If label is non-NULL it is used as the label for the menu item. If it is NULL then the label attribute is
         * unset.
         *
         * @param item  self
         * @param label The label to set, or NULL to unset.
         *              <p>
         *              The argument can be NULL.
         */
        public native void g_menu_item_set_label(Pointer item, String label);

        /**
         * Creates a link from menu_item to model if non-NULL, or unsets it.
         * <p>
         * Links are used to establish a relationship between a particular menu item and another menu. For example,
         * G_MENU_LINK_SUBMENU is used to associate a submenu with a particular menu item, and G_MENU_LINK_SECTION is
         * used to create a section. Other types of link can be used, but there is no guarantee that clients will be
         * able to make sense of them. Link types are restricted to lowercase characters, numbers and '-'. Furthermore,
         * the names must begin with a lowercase character, must not end with a '-', and must not contain consecutive
         * dashes.
         *
         * @param item  self
         * @param link  Type of link to establish or unset.
         * @param model The GMenuModel to link to (or NULL to unset)
         *              <p>
         *              The argument can be NULL.
         */
        public native void g_menu_item_set_link(Pointer item, String link, Pointer model);

        /**
         * Sets or unsets the "section" link of menu_item to section.
         * <p>
         * The effect of having one menu appear as a section of another is exactly as it sounds: the items from section
         * become a direct part of the menu that menu_item is added to. See g_menu_item_new_section() for more
         * information about what it means for a menu item to be a section.
         *
         * @param item    self
         * @param section A GMenuModel, or NULL.
         *                <p>
         *                The argument can be NULL.
         */
        public native void g_menu_item_set_section(Pointer item, Pointer section);

        /**
         * Sets or unsets the "submenu" link of menu_item to submenu.
         * <p>
         * If submenu is non-NULL, it is linked to. If it is NULL then the link is unset.
         * <p>
         * The effect of having one menu appear as a submenu of another is exactly as it sounds.
         *
         * @param item    self
         * @param submenu A GMenuModel, or NULL.
         *                <p>
         *                The argument can be NULL.
         */
        public native void g_menu_item_set_submenu(Pointer item, Pointer submenu);
    }
}
