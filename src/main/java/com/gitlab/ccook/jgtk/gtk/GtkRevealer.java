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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.enums.GtkRevealerTransitionType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * A GtkRevealer animates the transition of its child from invisible to visible.
 * <p>
 * The style of transition can be controlled with gtk_revealer_set_transition_type().
 * <p>
 * These animations respect the GtkSettings:gtk-enable-animations setting.
 */
@SuppressWarnings("unchecked")
public class GtkRevealer extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkRevealerLibrary library = new GtkRevealerLibrary();

    public GtkRevealer(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkRevealer.
     */
    public GtkRevealer() {
        super(library.gtk_revealer_new());
    }

    /**
     * Tells the GtkRevealer to conceal its child.
     * <p>
     * The transition will be animated with the current transition type of revealer.
     */
    public void conceal() {
        library.gtk_revealer_set_reveal_child(getCReference(), false);
    }

    /**
     * Gets the child widget of revealer.
     *
     * @return The child widget of revealer, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_revealer_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of revealer.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget w) {
        library.gtk_revealer_set_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Returns the amount of time (in milliseconds) that transitions will take.
     *
     * @return The transition duration.
     */
    public int getTransitionTimeMilliseconds() {
        return library.gtk_revealer_get_transition_duration(getCReference());
    }

    /**
     * Sets the duration that transitions will take.
     *
     * @param milliseconds The new duration, in milliseconds.
     */
    public void setTransitionTimeMilliseconds(int milliseconds) {
        library.gtk_revealer_set_transition_duration(getCReference(), Math.max(0, milliseconds));
    }

    /**
     * Gets the type of animation that will be used for transitions in revealer.
     *
     * @return The current transition type of revealer.
     */
    public GtkRevealerTransitionType getTransitionType() {
        return GtkRevealerTransitionType.getTypeFromCValue(library.gtk_revealer_get_transition_type(getCReference()));
    }

    /**
     * Sets the type of animation that will be used for transitions in revealer.
     * <p>
     * Available types include various kinds of fades and slides.
     *
     * @param type The new transition type.
     */
    public void setTransitionType(GtkRevealerTransitionType type) {
        if (type != null) {
            library.gtk_revealer_set_transition_type(getCReference(), GtkRevealerTransitionType.getCValueFromType(type));
        }
    }

    /**
     * Returns whether the child is currently revealed.
     * <p>
     * This function returns TRUE as soon as the transition is to the revealed state is started. To learn whether the
     * child is fully revealed (ie the transition is completed), use gtk_revealer_get_child_revealed().
     *
     * @return TRUE if the child is revealed.
     */
    public boolean hasRevealingStarted() {
        return library.gtk_revealer_get_reveal_child(getCReference());
    }

    /**
     * Returns whether the child is fully revealed.
     * <p>
     * In other words, this returns whether the transition to the revealed state is completed.
     *
     * @return TRUE if the child is fully revealed.
     */
    public boolean isChildFullyRevealed() {
        return library.gtk_revealer_get_child_revealed(getCReference());
    }

    /**
     * Tells the GtkRevealer to reveal its child.
     * <p>
     * The transition will be animated with the current transition type of revealer.
     */
    public void reveal() {
        library.gtk_revealer_set_reveal_child(getCReference(), true);
    }

    protected static class GtkRevealerLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget of revealer.
         *
         * @param revealer self
         * @return The child widget of revealer.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_revealer_get_child(Pointer revealer);

        /**
         * Returns whether the child is fully revealed.
         * <p>
         * In other words, this returns whether the transition to the revealed state is completed.
         *
         * @param revealer self
         * @return TRUE if the child is fully revealed.
         */
        public native boolean gtk_revealer_get_child_revealed(Pointer revealer);

        /**
         * Returns whether the child is currently revealed.
         * <p>
         * This function returns TRUE as soon as the transition is to the revealed state is started. To learn whether
         * the child is fully revealed (ie the transition is completed), use gtk_revealer_get_child_revealed().
         *
         * @param revealer self
         * @return TRUE if the child is revealed.
         */
        public native boolean gtk_revealer_get_reveal_child(Pointer revealer);

        /**
         * Returns the amount of time (in milliseconds) that transitions will take.
         *
         * @param revealer self
         * @return The transition duration.
         */
        public native int gtk_revealer_get_transition_duration(Pointer revealer);

        /**
         * Gets the type of animation that will be used for transitions in revealer.
         *
         * @param revealer self
         * @return The current transition type of revealer. Type: GtkRevealerTransitionType
         */
        public native int gtk_revealer_get_transition_type(Pointer revealer);

        /**
         * Creates a new GtkRevealer.
         *
         * @return A newly created GtkRevealer
         */
        public native Pointer gtk_revealer_new();

        /**
         * Sets the child widget of revealer.
         *
         * @param revealer self
         * @param child    The child widget. Type: GtkWidget
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_revealer_set_child(Pointer revealer, Pointer child);

        /**
         * Tells the GtkRevealer to reveal or conceal its child.
         * <p>
         * The transition will be animated with the current transition type of revealer.
         *
         * @param revealer     self
         * @param reveal_child TRUE to reveal the child.
         */
        public native void gtk_revealer_set_reveal_child(Pointer revealer, boolean reveal_child);

        /**
         * Sets the duration that transitions will take.
         *
         * @param revealer self
         * @param duration The new duration, in milliseconds.
         */
        public native void gtk_revealer_set_transition_duration(Pointer revealer, int duration);

        /**
         * Sets the type of animation that will be used for transitions in revealer.
         * <p>
         * Available types include various kinds of fades and slides.
         *
         * @param revealer   self
         * @param transition The new transition type. Type: GtkRevealerTransitionType
         */
        public native void gtk_revealer_set_transition_type(Pointer revealer, int transition);
    }
}
