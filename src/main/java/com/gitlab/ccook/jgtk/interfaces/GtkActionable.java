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

import com.gitlab.ccook.jgtk.GVariant;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public interface GtkActionable extends GtkInterface {

    /**
     * Gets the action name for actionable.
     *
     * @return The action name.
     */
    default Option<String> getActionName() {
        return new Option<>(library.gtk_actionable_get_action_name(getCReference()));
    }

    /**
     * Specifies the name of the action with which this widget should be associated.
     * <p>
     * If action_name is NULL then the widget will be unassociated from any previous action.
     * <p>
     * Usually this function is used when the widget is located (or will be located) within the hierarchy of a
     * GtkApplicationWindow.
     * <p>
     * Names are of the form "win.save" or "app.quit" for actions on the containing GtkApplicationWindow or its
     * associated GtkApplication, respectively. This is the same form used for actions in the GMenu associated with
     * the window.
     *
     * @param actionName An action name.
     *                   <p>
     *                   The argument can be NULL.
     */
    default void setActionName(String actionName) {
        library.gtk_actionable_set_action_name(getCReference(), actionName);
    }

    /**
     * Gets the current target value of actionable.
     *
     * @return The current target value.
     */
    default Option<GVariant> getActionTargetValue() {
        Option<Pointer> pointer = new Option<>(library.gtk_actionable_get_action_target_value(getCReference()));
        if (pointer.isDefined()) {
            return new Option<>(new GVariant(pointer.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the target value of an actionable widget.
     * <p>
     * If target_value is NULL then the target value is unset.
     * <p>
     * The target value has two purposes. First, it is used as the parameter to activation of the action associated
     * with the GtkActionable widget. Second, it is used to determine if the widget should be rendered as "active"
     * — the widget is active if the state is equal to the given target.
     * <p>
     * Consider the example of associating a set of buttons with a GAction with string state in a typical
     * "radio button" situation. Each button will be associated with the same action, but with a different target value
     * for that action. Clicking on a particular button will activate the action with the target of that button,
     * which will typically cause the action's state to change to that value. Since the action's state is now equal to
     * the target value of the button, the button will now be rendered as active (and the other buttons, with different
     * targets, rendered inactive).
     *
     * @param targetValue A GVariant to set as the target value.
     *                    <p>
     *                    The argument can be NULL.
     */
    default void setActionTargetValue(GVariant targetValue) {
        library.gtk_actionable_set_action_target_value(getCReference(), targetValue.getCReference());
    }

    /**
     * Sets the action-name and associated string target value of an actionable widget.
     * <p>
     * detailed_action_name is a string in the format accepted by g_action_parse_detailed_name().
     *
     * @param detailedName The detailed action name.
     */
    default void setActionDetailedName(String detailedName) {
        if (detailedName != null) {
            library.gtk_actionable_set_detailed_action_name(getCReference(), detailedName);
        }
    }
}
