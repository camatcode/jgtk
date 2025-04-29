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
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkOrientable;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * The GtkStackSwitcher shows a row of buttons to switch between GtkStack pages.
 * <p>
 * It acts as a controller for the associated GtkStack.
 * <p>
 * All the content for the buttons comes from the properties of the stacks GtkStackPage objects; the button visibility
 * in a GtkStackSwitcher widget is controlled by the visibility of the child in the GtkStack.
 * <p>
 * It is possible to associate multiple GtkStackSwitcher widgets with the same GtkStack widget.
 */
@SuppressWarnings("unchecked")
public class GtkStackSwitcher extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkStackSwitcherLibrary library = new GtkStackSwitcherLibrary();

    public GtkStackSwitcher(Pointer ref) {
        super(ref);
    }

    /**
     * Create a new GtkStackSwitcher.
     *
     * @param s the stack to control.
     */
    public GtkStackSwitcher(GtkStack s) {
        this();
        setStack(s);
    }

    /**
     * Create a new GtkStackSwitcher.
     */
    public GtkStackSwitcher() {
        super(library.gtk_stack_switcher_new());
    }

    /**
     * Retrieves the stack.
     *
     * @return the stack if defined
     */
    public Option<GtkStack> getStack() {
        Option<Pointer> p = new Option<>(library.gtk_stack_switcher_get_stack(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkStack) JGTKObject.newObjectFromType(p.get(), GtkStack.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the stack to control.
     *
     * @param s A GtkStack
     *          <p>
     *          The argument can be NULL.
     */
    public void setStack(GtkStack s) {
        library.gtk_stack_switcher_set_stack(getCReference(), pointerOrNull(s));
    }

    protected static class GtkStackSwitcherLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the stack.
         *
         * @param switcher self
         * @return The stack.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_stack_switcher_get_stack(Pointer switcher);

        /**
         * Create a new GtkStackSwitcher.
         *
         * @return A new GtkStackSwitcher.
         */
        public native Pointer gtk_stack_switcher_new();

        /**
         * Sets the stack to control.
         *
         * @param switcher self
         * @param stack    A GtkStack
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_stack_switcher_set_stack(Pointer switcher, Pointer stack);
    }
}
