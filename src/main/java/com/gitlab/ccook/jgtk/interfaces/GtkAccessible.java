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

import com.gitlab.ccook.jgtk.GValue;
import com.gitlab.ccook.jgtk.enums.GtkAccessibleProperty;
import com.gitlab.ccook.jgtk.enums.GtkAccessibleRelation;
import com.gitlab.ccook.jgtk.enums.GtkAccessibleRole;
import com.gitlab.ccook.jgtk.enums.GtkAccessibleState;
import com.sun.jna.Pointer;


public interface GtkAccessible extends GtkInterface {
    default GtkAccessibleRole getAccessibleRole() {
        return GtkAccessibleRole.getRoleFromCValue(library.gtk_accessible_get_accessible_role(getCReference()));
    }

    /**
     * Resets the accessible property to its default value.
     *
     * @param propToReset A GtkAccessibleProperty to reset
     */
    default void resetAccessibleProperty(GtkAccessibleProperty propToReset) {
        library.gtk_accessible_reset_property(getCReference(), GtkAccessibleProperty.getCValue(propToReset));
    }

    /**
     * Resets the accessible relation to its default value.
     *
     * @param relationToRest A GtkAccessibleRelation to reset
     */
    default void resetGtkAccessibleRelation(GtkAccessibleRelation relationToRest) {
        library.gtk_accessible_reset_relation(getCReference(), GtkAccessibleRelation.getCValue(relationToRest));
    }

    /**
     * Resets the accessible state to its default value.
     *
     * @param state state to reset to default value
     */
    default void resetGtkAccessibleState(GtkAccessibleState state) {
        library.gtk_accessible_reset_state(getCReference(), GtkAccessibleState.getCValue(state));
    }

    /**
     * Updates an array of accessible properties.
     * <p>
     * This function should be called by GtkWidget types whenever an accessible property change must be
     * communicated to assistive technologies.
     *
     * @param prop property to update
     * @param g    value to set property to
     */
    default void updateGtkAccessibleProperty(GtkAccessibleProperty prop, GValue g) {
        library.gtk_accessible_update_property_value(getCReference(), 1, GtkAccessibleProperty.getCValue(prop), new Pointer[]{g.getCReference()});
    }

    /**
     * Updates an array of accessible relations.
     * <p>
     * This function should be called by GtkWidget types whenever an accessible relation change must be communicated
     * to assistive technologies.
     *
     * @param r relation to update
     * @param g value to update the relation with
     */
    default void updateGtkAccessibleRelation(GtkAccessibleRelation r, GValue g) {
        library.gtk_accessible_update_relation_value(getCReference(), 1, GtkAccessibleRelation.getCValue(r), new Pointer[]{g.getCReference()});
    }

    /**
     * Updates an array of accessible states.
     * <p>
     * This function should be called by GtkWidget types whenever an accessible state change must be communicated to
     * assistive technologies.
     *
     * @param s state to update
     * @param g value to update with
     */
    default void updateGtkAccessibleState(GtkAccessibleState s, GValue g) {
        library.gtk_accessible_update_state_value(getCReference(), 1, GtkAccessibleState.getCValue(s), new Pointer[]{g.getCReference()});
    }


}
