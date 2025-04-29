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
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * A GtkStackSidebar uses a sidebar to switch between GtkStack pages.
 * <p>
 * In order to use a GtkStackSidebar, you simply use a GtkStack to organize your UI flow, and add the sidebar to your
 * sidebar area. You can use gtk_stack_sidebar_set_stack() to connect the GtkStackSidebar to the GtkStack.
 */
@SuppressWarnings("unchecked")
public class GtkStackSidebar extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkStackSidebarLibrary library = new GtkStackSidebarLibrary();

    public GtkStackSidebar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkStackSidebar.
     *
     * @param stack the GtkStack associated with this GtkStackSidebar.
     */
    public GtkStackSidebar(GtkStack stack) {
        this();
        setStack(stack);
    }

    /**
     * Creates a new GtkStackSidebar.
     */
    public GtkStackSidebar() {
        super(library.gtk_stack_sidebar_new());
    }

    /**
     * Retrieves the stack.
     *
     * @return The associated GtkStack or NONE if none has been set explicitly.
     */
    public Option<GtkStack> getStack() {
        Option<Pointer> p = new Option<>(library.gtk_stack_sidebar_get_stack(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkStack) JGTKObject.newObjectFromType(p.get(), GtkStack.class));
        }
        return Option.NONE;
    }

    /**
     * Set the GtkStack associated with this GtkStackSidebar.
     * <p>
     * The sidebar widget will automatically update according to the order and items within the given GtkStack.
     *
     * @param s A GtkStack
     *          <p>
     *          The argument can be NULL.
     */
    public void setStack(GtkStack s) {
        library.gtk_stack_sidebar_set_stack(getCReference(), pointerOrNull(s));
    }

    protected static class GtkStackSidebarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the stack.
         *
         * @param self self
         * @return The associated GtkStack or NULL if none has been set explicitly. Type: GtkStack
         */
        public native Pointer gtk_stack_sidebar_get_stack(Pointer self);

        /**
         * Creates a new GtkStackSidebar.
         *
         * @return The new GtkStackSidebar
         */
        public native Pointer gtk_stack_sidebar_new();

        /**
         * Set the GtkStack associated with this GtkStackSidebar.
         * <p>
         * The sidebar widget will automatically update according to the order and items within the given GtkStack.
         *
         * @param self  self
         * @param stack A GtkStack
         */
        public native void gtk_stack_sidebar_set_stack(Pointer self, Pointer stack);
    }
}
