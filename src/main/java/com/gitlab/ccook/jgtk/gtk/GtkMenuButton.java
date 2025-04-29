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
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.IconName;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GDestroyNotify;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.callbacks.GtkMenuButtonCreatePopupFunc;
import com.gitlab.ccook.jgtk.enums.GtkArrowType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * The GtkMenuButton widget is used to display a popup when clicked.
 * <p>
 * This popup can be provided either as a GtkPopover or as an abstract GMenuModel.
 * <p>
 * The GtkMenuButton widget can show either an icon (set with the GtkMenuButton:icon-name property) or a label (set
 * with the GtkMenuButton:label property). If neither is explicitly set, a GtkImage is automatically created, using an
 * arrow image oriented according to GtkMenuButton:direction or the generic "open-menu-symbolic" icon if the direction
 * is not set.
 * <p>
 * The positioning of the popup is determined by the GtkMenuButton:direction property of the menu button.
 * <p>
 * For menus, the GtkWidget:halign and GtkWidget:valign properties of the menu are also taken into account. For example,
 * when the direction is GTK_ARROW_DOWN and the horizontal alignment is GTK_ALIGN_START, the menu will be positioned
 * below the button, with the starting edge (depending on the text direction) of the menu aligned with the starting
 * edge of the button. If there is not enough space below the button, the menu is popped up above the button instead.
 * If the alignment would otherwise move part of the menu offscreen, it is "pushed in".
 */
@SuppressWarnings("unchecked")
public class GtkMenuButton extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {
    private static final GtkMenuButtonLibrary library = new GtkMenuButtonLibrary();


    public GtkMenuButton(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkMenuButton widget with downwards-pointing arrow as the only child.
     * <p>
     * You can replace the child widget with another GtkWidget should you wish to.
     */
    public GtkMenuButton() {
        super(library.gtk_menu_button_new());
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
     * Dismiss the menu.
     */
    public void dismissMenu() {
        library.gtk_menu_button_popdown(getCReference());
    }

    /**
     * Gets whether to show a dropdown arrow even when using an icon
     * <p>
     *
     * @return Whether to show a dropdown arrow even when using an icon
     * @since 4.4
     */
    public boolean doesAlwaysShowDropdownArrow() {
        return library.gtk_menu_button_get_always_show_arrow(getCReference());
    }

    /**
     * Gets the child widget of menu_button.
     *
     * @return The child widget of menu_button, if defined
     * @since 4.6
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_menu_button_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of menu_button.
     * <p>
     * Setting a child resets GtkMenuButton:label and GtkMenuButton:icon-name.
     * <p>
     * If GtkMenuButton:always-show-arrow is set to TRUE and GtkMenuButton:direction is not GTK_ARROW_NONE, a dropdown
     * arrow will be shown next to the child.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     * @since 4.6
     */
    public void setChild(GtkWidget w) {
        library.gtk_menu_button_set_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Returns the direction the popup will be pointing at when popped up.
     *
     * @return A GtkArrowType value.
     */
    public GtkArrowType getDirection() {
        return GtkArrowType.getTypeFromCValue(library.gtk_menu_button_get_direction(getCReference()));
    }

    /**
     * Sets the direction in which the popup will be popped up.
     * <p>
     * If the button is automatically populated with an arrow icon, its direction will be changed to match.
     * <p>
     * If the does not fit in the available space in the given direction, GTK will its best to keep it inside the screen
     * and fully visible.
     * <p>
     * If you pass GTK_ARROW_NONE for a direction, the popup will behave as if you passed GTK_ARROW_DOWN (although you
     * won't see any arrows).
     *
     * @param direction A GtkArrowType
     */
    public void setDirection(GtkArrowType direction) {
        if (direction != null) {
            library.gtk_menu_button_set_direction(getCReference(), GtkArrowType.getCValueFromType(direction));
        }
    }

    /**
     * Gets the name of the icon shown in the button.
     *
     * @return The name of the icon shown in the button.
     */
    public Option<IconName> getIconName() {
        Option<String> val = new Option<>(library.gtk_menu_button_get_icon_name(getCReference()));
        if (val.isDefined()) {
            return new Option<>(new IconName(val.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the name of an icon to show inside the menu button.
     * <p>
     * Setting icon name resets GtkMenuButton:label and GtkMenuButton:child.
     * <p>
     * If GtkMenuButton:always-show-arrow is set to TRUE and GtkMenuButton:direction is not GTK_ARROW_NONE, a dropdown
     * arrow will be shown next to the icon.
     *
     * @param iconName The icon name.
     */
    public void setIconName(IconName iconName) {
        library.gtk_menu_button_set_icon_name(getCReference(), iconName.getIconName());
    }

    /**
     * Gets the label shown in the button.
     *
     * @return The label shown in the button, if defined
     */
    public Option<String> getLabel() {
        return new Option<>(library.gtk_menu_button_get_label(getCReference()));
    }

    /**
     * Sets the label to show inside the menu button.
     * <p>
     * Setting a label resets GtkMenuButton:icon-name and GtkMenuButton:child.
     * <p>
     * If GtkMenuButton:direction is not GTK_ARROW_NONE, a dropdown arrow will be shown next to the label.
     *
     * @param label The label.
     */
    public void setLabel(String label) {
        library.gtk_menu_button_set_label(getCReference(), label);
    }

    /**
     * Returns the GMenuModel used to generate the popup.
     *
     * @return A GMenuModel, if defined
     */
    public Option<GMenuModel> getMenuModel() {
        Option<Pointer> p = new Option<>(library.gtk_menu_button_get_menu_model(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GMenuModel(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the GMenuModel from which the popup will be constructed.
     * <p>
     * If menu_model is NULL, the button is disabled.
     * <p>
     * A GtkPopover will be created from the menu model with gtk_popover_menu_new_from_model(). Actions will be
     * connected as documented for this function.
     * <p>
     * If GtkMenuButton:popover is already set, it will be dissociated from the menu_button, and the property is set to
     * NULL.
     *
     * @param model A GMenuModel, or NULL to unset and disable the button.
     *              <p>
     *              The argument can be NULL
     */
    public void setMenuModel(GMenuModel model) {
        library.gtk_menu_button_set_menu_model(getCReference(), pointerOrNull(model));
    }

    /**
     * Returns the GtkPopover that pops out of the button.
     * <p>
     * If the button is not using a GtkPopover, this function returns NONE.
     *
     * @return A GtkPopover or NONE
     */
    public Option<GtkPopover> getPopover() {
        Option<Pointer> p = new Option<>(library.gtk_menu_button_get_popover(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkPopover) JGTKObject.newObjectFromType(p.get(), GtkPopover.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the GtkPopover that will be popped up when the menu_button is clicked.
     * <p>
     * If popover is NULL, the button is disabled.
     * <p>
     * If GtkMenuButton:menu-model is set, the menu model is dissociated from the menu_button, and the property is set
     * to NULL.
     *
     * @param w A GtkPopover, or NULL to unset and disable the button.
     *          <p>
     *          The argument can be NULL.
     */
    public void setPopover(GtkPopover w) {
        library.gtk_menu_button_set_popover(getCReference(), pointerOrNull(w));
    }

    /**
     * Returns whether the button has a frame.
     *
     * @return TRUE if the button has a frame.
     */
    public boolean hasFrame() {
        return library.gtk_menu_button_get_has_frame(getCReference());
    }

    /**
     * Returns whether an embedded underline in the text indicates a mnemonic.
     *
     * @return TRUE whether an embedded underline in the text indicates the mnemonic accelerator keys.
     */
    public boolean isMnemonic() {
        return library.gtk_menu_button_get_use_underline(getCReference());
    }

    /**
     * If true, an underline in the text indicates a mnemonic.
     *
     * @param doesUse TRUE if underlines in the text indicate mnemonics.
     */
    public void setMnemonic(boolean doesUse) {
        library.gtk_menu_button_set_use_underline(getCReference(), doesUse);
    }

    /**
     * Returns whether the menu button acts as a primary menu.
     * <p>
     * Primary menus can be opened with the F10 key.
     *
     * @return TRUE if the button is a primary menu.
     * @since 4.4
     */
    public boolean isPrimaryMenu() {
        return library.gtk_menu_button_get_primary(getCReference());
    }

    /**
     * Sets whether menu button acts as a primary menu.
     * <p>
     * Primary menus can be opened with the F10 key.
     *
     * @param isPrimary Whether the menu button should act as a primary menu.
     * @since 4.4
     */
    public void setPrimaryMenu(boolean isPrimary) {
        library.gtk_menu_button_set_primary(getCReference(), isPrimary);
    }

    /**
     * Pop up the menu.
     */
    public void popupMenu() {
        library.gtk_menu_button_popup(getCReference());
    }

    /**
     * Sets func to be called when a popup is about to be shown.
     * <p>
     * func should use one of
     * <p>
     * gtk_menu_button_set_popover()
     * gtk_menu_button_set_menu_model()
     * <p>
     * to set a popup for menu_button. If func is non-NULL, menu_button will always be sensitive.
     * <p>
     * Using this function will not reset the menu widget attached to menu_button. Instead, this can be done manually
     * in func.
     *
     * @param func          Function to call when a popup is about to be shown, but none has been provided via other
     *                      means, or NULL to reset to default behavior.
     *                      <p>
     *                      The argument can be NULL.
     * @param userData      User data to pass to func.
     *                      <p>
     *                      The argument can be NULL.
     * @param destroyNotify Destroy notify for user_data.
     *                      <p>
     *                      The argument can be NULL.
     */
    public void setCreatePopupFunc(GtkMenuButtonCreatePopupFunc func, Pointer userData, GDestroyNotify destroyNotify) {
        preventGarbageCollection(func, userData, destroyNotify);
        library.gtk_menu_button_set_create_popup_func(getCReference(), func, userData, destroyNotify);
    }

    /**
     * Sets whether to show a dropdown arrow even when using an icon or a custom child.
     *
     * @param doesShow Whether to show a dropdown arrow even when using an icon.
     * @since 4.4
     */
    public void shouldAlwaysShowDropdownArrow(boolean doesShow) {
        library.gtk_menu_button_set_always_show_arrow(getCReference(), doesShow);
    }

    /**
     * Sets the style of the button.
     *
     * @param hasFrame Whether the button should have a visible frame.
     */
    public void shouldHaveFrame(boolean hasFrame) {
        library.gtk_menu_button_set_has_frame(getCReference(), hasFrame);
    }

    @SuppressWarnings("SameParameterValue")
    public final static class Signals extends GtkWidget.Signals {
        /**
         * Emitted to when the menu button is activated.
         * <p>
         * The ::activate signal on GtkMenuButton is an action signal and emitting it causes the button to pop up its
         * menu.
         *
         * @since 4.4
         */
        public static final Signals ACTIVATE = new Signals("activate");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkMenuButtonLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Gets whether to show a dropdown arrow even when using an icon.
         *
         * @param menu_button self
         * @return Whether to show a dropdown arrow even when using an icon.
         * @since 4.4
         */
        public native boolean gtk_menu_button_get_always_show_arrow(Pointer menu_button);

        /**
         * Gets the child widget of menu_button.
         *
         * @param menu_button self
         * @return The child widget of menu_button. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         * @since 4.6
         */
        public native Pointer gtk_menu_button_get_child(Pointer menu_button);

        /**
         * Returns the direction the popup will be pointing at when popped up.
         *
         * @param menu_button self
         * @return A GtkArrowType value. Type: GtkArrowType
         */
        public native int gtk_menu_button_get_direction(Pointer menu_button);

        /**
         * Returns whether the button has a frame.
         *
         * @param menu_button self
         * @return TRUE if the button has a frame.
         */
        public native boolean gtk_menu_button_get_has_frame(Pointer menu_button);

        /**
         * Gets the name of the icon shown in the button.
         *
         * @param menu_button self
         * @return The name of the icon shown in the button.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_menu_button_get_icon_name(Pointer menu_button);

        /**
         * Gets the label shown in the button.
         *
         * @param menu_button self
         * @return The label shown in the button.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_menu_button_get_label(Pointer menu_button);

        /**
         * Returns the GMenuModel used to generate the popup.
         *
         * @param menu_button self
         * @return A GMenuModel. Type: GMenuModel
         *         <p>
         *         The data is owned by the instance.
         *         The return value can be NULL.
         */
        public native Pointer gtk_menu_button_get_menu_model(Pointer menu_button);

        /**
         * Returns the GtkPopover that pops out of the button.
         * <p>
         * If the button is not using a GtkPopover, this function returns NULL.
         *
         * @param menu_button self
         * @return A GtkPopover or NULL. Type: GtkPopover
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_menu_button_get_popover(Pointer menu_button);

        /**
         * Returns whether the menu button acts as a primary menu.
         * <p>
         * Primary menus can be opened with the F10 key.
         *
         * @param menu_button self
         * @return TRUE if the button is a primary menu.
         * @since 4.4
         */
        public native boolean gtk_menu_button_get_primary(Pointer menu_button);

        /**
         * Returns whether an embedded underline in the text indicates a mnemonic.
         *
         * @param menu_button self
         * @return TRUE whether an embedded underline in the text indicates the mnemonic accelerator keys.
         */
        public native boolean gtk_menu_button_get_use_underline(Pointer menu_button);

        /**
         * Creates a new GtkMenuButton widget with downwards-pointing arrow as the only child.
         * <p>
         * You can replace the child widget with another GtkWidget should you wish to.
         *
         * @return The newly created GtkMenuButton. Type: GtkMenuButton
         */
        public native Pointer gtk_menu_button_new();

        /**
         * Dismiss the menu.
         *
         * @param menu_button self
         */
        public native void gtk_menu_button_popdown(Pointer menu_button);

        /**
         * Pop up the menu.
         *
         * @param menu_button self
         */
        public native void gtk_menu_button_popup(Pointer menu_button);

        /**
         * Sets whether to show a dropdown arrow even when using an icon or a custom child.
         *
         * @param menu_button       self
         * @param always_show_arrow Whether to show a dropdown arrow even when using an icon.
         * @since 4.4
         */
        public native void gtk_menu_button_set_always_show_arrow(Pointer menu_button, boolean always_show_arrow);

        /**
         * Sets the child widget of menu_button.
         * <p>
         * Setting a child resets GtkMenuButton:label and GtkMenuButton:icon-name.
         * <p>
         * If GtkMenuButton:always-show-arrow is set to TRUE and GtkMenuButton:direction is not GTK_ARROW_NONE, a
         * dropdown arrow will be shown next to the child.
         *
         * @param menu_button self
         * @param child       The child widget. Type: GtkWidget
         *                    <p>
         *                    The argument can be NULL.
         * @since 4.6
         */
        public native void gtk_menu_button_set_child(Pointer menu_button, Pointer child);

        /**
         * Sets func to be called when a popup is about to be shown.
         * <p>
         * func should use one of
         * <p>
         * gtk_menu_button_set_popover()
         * gtk_menu_button_set_menu_model()
         * to set a popup for menu_button. If func is non-NULL, menu_button will always be sensitive.
         * <p>
         * Using this function will not reset the menu widget attached to menu_button. Instead, this can be done
         * manually in func.
         *
         * @param menu_button    self
         * @param func           Function to call when a popup is about to be shown, but none has been provided via
         *                       other means, or NULL to reset to default behavior.
         *                       <p>
         *                       The argument can be NULL.
         * @param user_data      User data to pass to func.
         *                       <p>
         *                       The argument can be NULL.
         * @param destroy_notify Destroy notify for user_data.
         *                       <p>
         *                       The argument can be NULL.
         */
        public native void gtk_menu_button_set_create_popup_func(Pointer menu_button, GtkMenuButtonCreatePopupFunc func, Pointer user_data, GDestroyNotify destroy_notify);

        /**
         * Sets the direction in which the popup will be popped up.
         * <p>
         * If the button is automatically populated with an arrow icon, its direction will be changed to match.
         * <p>
         * If the does not fit in the available space in the given direction, GTK will its best to keep it inside the
         * screen and fully visible.
         * <p>
         * If you pass GTK_ARROW_NONE for a direction, the popup will behave as if you passed GTK_ARROW_DOWN (although
         * you won't see any arrows).
         *
         * @param menu_button self
         * @param direction   A GtkArrowType. Type: GtkArrowType
         */
        public native void gtk_menu_button_set_direction(Pointer menu_button, int direction);

        /**
         * Sets the style of the button.
         *
         * @param menu_button self
         * @param has_frame   Whether the button should have a visible frame.
         */
        public native void gtk_menu_button_set_has_frame(Pointer menu_button, boolean has_frame);

        /**
         * Sets the name of an icon to show inside the menu button.
         * <p>
         * Setting icon name resets GtkMenuButton:label and GtkMenuButton:child.
         * <p>
         * If GtkMenuButton:always-show-arrow is set to TRUE and GtkMenuButton:direction is not GTK_ARROW_NONE, a
         * dropdown arrow will be shown next to the icon.
         *
         * @param menu_button self
         * @param icon_name   The icon name.
         */
        public native void gtk_menu_button_set_icon_name(Pointer menu_button, String icon_name);

        /**
         * Sets the label to show inside the menu button.
         * <p>
         * Setting a label resets GtkMenuButton:icon-name and GtkMenuButton:child.
         * <p>
         * If GtkMenuButton:direction is not GTK_ARROW_NONE, a dropdown arrow will be shown next to the label.
         *
         * @param menu_button self
         * @param label       The label.
         */
        public native void gtk_menu_button_set_label(Pointer menu_button, String label);

        /**
         * Sets the GMenuModel from which the popup will be constructed.
         * <p>
         * If menu_model is NULL, the button is disabled.
         * <p>
         * A GtkPopover will be created from the menu model with gtk_popover_menu_new_from_model(). Actions will be
         * connected as documented for this function.
         * <p>
         * If GtkMenuButton:popover is already set, it will be dissociated from the menu_button, and the property
         * is set to NULL.
         *
         * @param menu_button self
         * @param menu_model  A GMenuModel, or NULL to unset and disable the button. Type: GMenuModel
         *                    <p>
         *                    The argument can be NULL.
         */
        public native void gtk_menu_button_set_menu_model(Pointer menu_button, Pointer menu_model);

        /**
         * Sets the GtkPopover that will be popped up when the menu_button is clicked.
         * <p>
         * If popover is NULL, the button is disabled.
         * <p>
         * If GtkMenuButton:menu-model is set, the menu model is dissociated from the menu_button, and the property
         * is set to NULL.
         *
         * @param menu_button self
         * @param popover     A GtkPopover, or NULL to unset and disable the button. Type: GtkWidget
         *                    <p>
         *                    The argument can be NULL.
         */
        public native void gtk_menu_button_set_popover(Pointer menu_button, Pointer popover);

        /**
         * Sets whether menu button acts as a primary menu.
         * <p>
         * Primary menus can be opened with the F10 key.
         *
         * @param menu_button self
         * @param primary     Whether the menu button should act as a primary menu.
         * @since 4.4
         */
        public native void gtk_menu_button_set_primary(Pointer menu_button, boolean primary);

        /**
         * If true, an underline in the text indicates a mnemonic.
         *
         * @param menu_button   self
         * @param use_underline TRUE if underlines in the text indicate mnemonics.
         */
        public native void gtk_menu_button_set_use_underline(Pointer menu_button, boolean use_underline);
    }
}
