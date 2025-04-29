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

import com.gitlab.ccook.jgtk.enums.GtkTextDirection;
import com.gitlab.ccook.jgtk.gtk.GenericGListModel;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;

import java.util.Set;

@SuppressWarnings("unchecked")
public class GtkWidget extends JGTKConnectableObject implements GtkAccessible, GtkBuildable {


    public GtkWidget(Pointer ref) {
        super(ref);
    }

    public static GtkTextDirection getDefaultTextDirection() {
        return GtkTextDirection.getTextDirectionFromCValue(library.gtk_widget_get_default_direction());
    }

    /**
     * Sets the default reading direction for widgets.
     *
     * @param dir The new default direction. This cannot be GTK_TEXT_DIR_NONE.
     */
    public static void setDefaultTextDirection(GtkTextDirection dir) {
        if (dir != null && !dir.equals(GtkTextDirection.GTK_TEXT_DIR_NONE)) {
            library.gtk_widget_set_default_direction(dir.getCValue());
        }
    }

    /**
     * For widgets that can be "activated" (buttons, menu items, etc.), this function activates them.
     * <p>
     * The activation will emit the signal set using gtk_widget_class_set_activate_signal() during class initialization.
     * <p>
     * Activation is what happens when you press Enter on a widget during key navigation.
     * <p>
     * If you wish to handle the activation keybinding yourself, it is recommended to use
     * gtk_widget_class_add_shortcut() with an action created with gtk_signal_action_new().
     * <p>
     * If widget isn't activatable, the function returns FALSE.
     *
     * @return TRUE if the widget was activatable.
     */
    public boolean activate() {
        return library.gtk_widget_activate(cReference);
    }

    public boolean activateAction(String actionName, String... gvariantFormatStrings) {
        if (actionName != null) {
            if (gvariantFormatStrings.length == 0) {
                return library.gtk_widget_activate_action(cReference, actionName, null);
            } else {
                return library.gtk_widget_activate_action(cReference, actionName, gvariantFormatStrings);
            }
        }
        return false;
    }

    public void activateDefault() {
        library.gtk_widget_activate_default(cReference);
    }

    /**
     * Adds a style class to widget.
     * <p>
     * After calling this function, the widgets style will match for css_class, according to CSS matching rules.
     * <p>
     * Use gtk_widget_remove_css_class() to remove the style again.
     *
     * @param cssClass The style class to add to widget, without the leading '.' used for notation of style classes.
     */
    public void addCSSClass(String cssClass) {
        if (cssClass != null) {
            library.gtk_widget_add_css_class(cReference, cssClass);//TODO Test
        }
    }

    /**
     * Adds controller to widget so that it will receive events.
     * <p>
     * You will usually want to call this function right after creating any kind of GtkEventController.
     *
     * @param gtkController A GtkEventController that hasn't been added to a widget yet.
     */
    public void addController(GtkEventController gtkController) {
        if (gtkController != null) {
            library.gtk_widget_add_controller(cReference, gtkController.cReference);//TODO Test
        }
    }

    /**
     * Adds a widget to the list of mnemonic labels for this widget.
     * <p>
     * See gtk_widget_list_mnemonic_labels(). Note the list of mnemonic labels for the widget is cleared when the
     * widget is destroyed, so the caller must make sure to update its internal state at this point as well.
     *
     * @param mnemonicLabel A GtkWidget that acts as a mnemonic label for widget.
     */
    public void addMnemonicLabel(GtkWidget mnemonicLabel) {
        if (mnemonicLabel != null) {
            library.gtk_widget_add_mnemonic_label(cReference, mnemonicLabel.cReference);//TODO Test
        }
    }

    public int getAllocatedHeight() {
        return library.gtk_widget_get_allocated_height(getCReference());
    }

    public int getAllocatedWidth() {
        return library.gtk_widget_get_allocated_width(getCReference());
    }

    public GdkRectangle getAllocation() {
        GdkRectangle.GdkRectangleStruct.ByReference allocP = new GdkRectangle.GdkRectangleStruct.ByReference();
        library.gtk_widget_get_allocation(getCReference(), allocP);
        return new GdkRectangle(allocP);
    }

    public Set<GtkWidget> getChildren() {
        Pointer p = library.gtk_widget_observe_children(cReference);
        return new GListModelSet<>(new GenericGListModel<>(GtkWidget.class, p));
    }

    /**
     * Enable or disable an action installed with gtk_widget_class_install_action().
     *
     * @param actionName Action name, such as "clipboard.paste"
     * @param isEnabled  Whether the action is now enabled.
     */
    public void setIsActionEnabled(String actionName, boolean isEnabled) {
        if (actionName != null) {
            library.gtk_widget_action_set_enabled(cReference, actionName, isEnabled);
        }
    }

    public void setParent(GtkWidget w) {
        if (w != null) {
            library.gtk_widget_set_parent(cReference, w.cReference);
        } else {
            library.gtk_widget_set_parent(cReference, Pointer.NULL);
        }
    }

    public void setSizeRequest(int width, int height) {
        library.gtk_widget_set_size_request(getCReference(), width, height);
    }

    //TODO gtk_widget_add_tick_callback

    public void show() {
        show(false);
    }

    public void show(boolean showChildren) {
        if (showChildren) {
            for (GtkWidget w : getChildren()) {
                if (!w.equals(this)) {
                    w.show(true);
                }
            }
        }
        library.gtk_widget_show(this.cReference);
    }

    public Option<GtkWidget> getChild(Class<? extends GtkWidget> widgetCls) {
        for (GtkWidget w : getChildren()) {
            if (w.getClass().equals(widgetCls)) {
                return new Option<>(w);
            }
        }
        return Option.NONE;
    }

    public static class Signals extends JGTKObject.Signals {
        protected Signals(String detailedName) {
            super(detailedName);
        }
    }


    public static class GtkWidgetLibrary extends GtkLibrary {

    }
}
