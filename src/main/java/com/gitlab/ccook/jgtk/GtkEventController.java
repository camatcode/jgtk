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

import com.gitlab.ccook.jgtk.enums.GdkModifierType;
import com.gitlab.ccook.jgtk.enums.GtkPropagationLimit;
import com.gitlab.ccook.jgtk.enums.GtkPropagationPhase;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public class GtkEventController extends JGTKConnectableObject {

    public GtkEventController(Pointer p) {
        super(p);
    }

    /**
     * Gets the name of controller.
     *
     * @return The controller name.
     */
    public Option<String> getControllerName() {
        return new Option<>(library.gtk_event_controller_get_name(getCReference()));
    }

    /**
     * Sets a name on the controller that can be used for debugging.
     *
     * @param name A name for controller.
     *             <p>
     *             The argument can be NULL.
     */
    public void setControllerName(String name) {
        library.gtk_event_controller_set_name(getCReference(), name);
    }

    public Option<GdkEvent> getCurrentEvent() {
        Option<Pointer> p = new Option<>(library.gtk_event_controller_get_current_event(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GdkEvent(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Returns the device of the event that is currently being handled by the controller.
     * <p>
     * At other times, NONE is returned.
     *
     * @return Device of the event is currently handled by controller.
     */
    public Option<GdkDevice> getCurrentEventDevice() {
        Option<Pointer> p = new Option<>(library.gtk_event_controller_get_current_event_device(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GdkDevice(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Returns the modifier state of the event that is currently being handled by the controller.
     * <p>
     * At other times, NONE is returned.
     *
     * @return Modifier state of the event is currently handled by controller.
     */
    public Option<GdkModifierType> getCurrentEventState() {
        int cValue = library.gtk_event_controller_get_current_event_state(getCReference());
        if (cValue == 0) {
            return Option.NONE;
        }
        return new Option<>(GdkModifierType.getTypeFromCValue(cValue));
    }

    /**
     * Returns the timestamp of the event that is currently being handled by the controller.
     * <p>
     * At other times, NONE is returned.
     *
     * @return Timestamp of the event is currently handled by controller.
     */
    public Option<Integer> getCurrentEventTime() {
        int timestamp = library.gtk_event_controller_get_current_event_time(getCReference());
        if (timestamp == 0) {
            return Option.NONE;
        }
        return new Option<>(timestamp);
    }

    /**
     * Gets the propagation limit of the event controller.
     *
     * @return The propagation limit.
     */
    public GtkPropagationLimit getPropagationLimit() {
        return GtkPropagationLimit.getLimitFromCValue(library.gtk_event_controller_get_propagation_limit(getCReference()));
    }

    /**
     * Sets the event propagation limit on the event controller.
     * <p>
     * If the limit is set to GTK_LIMIT_SAME_NATIVE, the controller won't handle events that are targeted at widgets
     * on a different surface, such as popovers.
     *
     * @param limit The propagation limit.
     */
    public void setPropagationLimit(GtkPropagationLimit limit) {
        if (limit != null) {
            library.gtk_event_controller_set_propagation_limit(getCReference(), GtkPropagationLimit.getCValue(limit));
        }
    }

    /**
     * Gets the propagation phase at which controller handles events.
     *
     * @return The propagation phase.
     */
    public GtkPropagationPhase getPropagationPhase() {
        return GtkPropagationPhase.getPhaseFromCValue(library.gtk_event_controller_get_propagation_phase(getCReference()));
    }

    /**
     * Sets the propagation phase at which a controller handles events.
     * <p>
     * If phase is GTK_PHASE_NONE, no automatic event handling will be performed, but other additional gesture
     * maintenance will.
     *
     * @param p A propagation phase.
     */
    public void setPropagationPhase(GtkPropagationPhase p) {
        if (p != null) {
            library.gtk_event_controller_set_propagation_phase(getCReference(), GtkPropagationPhase.getCValue(p));
        }
    }

    /**
     * Returns the GtkWidget this controller relates to.
     *
     * @return GtkWidget this controller relates to.
     */
    public GtkWidget getWidget() {
        return (GtkWidget) JGTKObject.newObjectFromType(
                library.gtk_event_controller_get_widget(getCReference()), GtkWidget.class);
    }

    /**
     * Resets the controller to a clean state.
     */
    public void reset() {
        library.gtk_event_controller_reset(getCReference());
    }
}

