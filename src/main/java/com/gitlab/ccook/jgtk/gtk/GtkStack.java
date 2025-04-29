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

import com.gitlab.ccook.jgtk.GListModel;
import com.gitlab.ccook.jgtk.GtkStackPage;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.enums.GtkStackTransitionType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkStack is a container which only shows one of its children at a time.
 * <p>
 * In contrast to GtkNotebook, GtkStack does not provide a means for users to change the visible child. Instead, a
 * separate widget such as GtkStackSwitcher or GtkStackSidebar can be used with GtkStack to provide this functionality.
 * <p>
 * Transitions between pages can be animated as slides or fades. This can be controlled with
 * gtk_stack_set_transition_type(). These animations respect the GtkSettings:gtk-enable-animations setting.
 * <p>
 * GtkStack maintains a GtkStackPage object for each added child, which holds additional per-child properties.
 * You obtain the GtkStackPage for a child with gtk_stack_get_page() and you can obtain a GtkSelectionModel containing
 * all the pages with gtk_stack_get_pages().
 */
@SuppressWarnings("unchecked")
public class GtkStack extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkStackLibrary library = new GtkStackLibrary();

    public GtkStack(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkStack.
     */
    public GtkStack() {
        super(library.gtk_stack_new());
    }

    /**
     * Adds a child to stack.
     *
     * @param child The widget to add.
     * @return The GtkStackPage for child.
     */
    public Option<GtkStackPage> addChild(GtkWidget child) {
        if (child != null) {
            Pointer p = library.gtk_stack_add_child(getCReference(), child.getCReference());
            return new Option<>((GtkStackPage) JGTKObject.newObjectFromType(p, GtkStackPage.class));
        }
        return Option.NONE;
    }

    /**
     * Adds a child to stack.
     * <p>
     * The child is identified by the name.
     *
     * @param child The widget to add.
     * @param name  The name for child.
     *              <p>
     *              The argument can be NULL.
     * @return The GtkStackPage for child.
     */
    public Option<GtkStackPage> addChild(GtkWidget child, String name) {
        if (child != null) {
            Pointer p = library.gtk_stack_add_named(getCReference(), child.getCReference(), name);
            return new Option<>((GtkStackPage) JGTKObject.newObjectFromType(p, GtkStackPage.class));
        }
        return Option.NONE;
    }

    /**
     * Adds a child to stack.
     * <p>
     * The child is identified by the name. The title will be used by GtkStackSwitcher to represent child in a tab bar,
     * so it should be short.
     *
     * @param child The widget to add.
     * @param name  The name for child.
     *              <p>
     *              The argument can be NULL.
     * @param title A human-readable title for child.
     * @return The GtkStackPage for child.
     */
    public Option<GtkStackPage> addChild(GtkWidget child, String name, String title) {
        if (child != null) {
            Pointer p = library.gtk_stack_add_titled(getCReference(), child.getCReference(), name, title);
            return new Option<>((GtkStackPage) JGTKObject.newObjectFromType(p, GtkStackPage.class));
        }
        return Option.NONE;
    }

    /**
     * Returns whether the GtkStack is set up to interpolate between the sizes of children on page switch.
     *
     * @return TRUE if child sizes are interpolated.
     */
    public boolean doesInterpolateSizes() {
        return library.gtk_stack_get_interpolate_size(getCReference());
    }

    /**
     * Finds the child with the name given as the argument.
     *
     * @param name The name of the child to find.
     * @return The requested child of the GtkStack
     */
    public Option<GtkWidget> getChild(String name) {
        Option<Pointer> p = new Option<>(library.gtk_stack_get_child_by_name(getCReference(), name));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Returns the GtkStackPage object for child.
     *
     * @param child A child of stack.
     * @return The GtkStackPage for child.
     */
    public Option<GtkStackPage> getPageForChild(GtkWidget child) {
        if (child != null) {
            Option<Pointer> p = new Option<>(library.gtk_stack_get_page(getCReference(), child.getCReference()));
            if (p.isDefined()) {
                return new Option<>((GtkStackPage) JGTKObject.newObjectFromType(p.get(), GtkStackPage.class));
            }
        }
        return Option.NONE;
    }

    /**
     * Returns a GListModel that contains the pages of the stack.
     * <p>
     * This can be used to keep an up-to-date view. The model also implements GtkSelectionModel and can be used to
     * track and modify the visible page.
     *
     * @return A GtkSelectionModel for the stack's children.
     */
    @SuppressWarnings("rawtypes")
    public GListModel getPages() {
        Pointer p = library.gtk_stack_get_pages(getCReference());
        return new GenericGListModel(GtkWidget.class, p);
    }

    /**
     * Returns the amount of time (in milliseconds) that transitions between pages in stack will take.
     *
     * @return The transition duration.
     */
    public int getTransitionTimeMilliseconds() {
        return library.gtk_stack_get_transition_duration(getCReference());
    }

    /**
     * Sets the duration that transitions between pages in stack will take.
     *
     * @param milliseconds The new duration, in milliseconds.
     */
    public void setTransitionTimeMilliseconds(int milliseconds) {
        library.gtk_stack_set_transition_duration(getCReference(), Math.max(0, milliseconds));
    }

    /**
     * Gets the type of animation that will be used for transitions between pages in stack.
     *
     * @return The current transition type of stack.
     */
    public GtkStackTransitionType getTransitionType() {
        return GtkStackTransitionType.getTypeFromCValue(library.gtk_stack_get_transition_type(getCReference()));
    }

    /**
     * Sets the type of animation that will be used for transitions between pages in stack.
     * <p>
     * Available types include various kinds of fades and slides.
     * <p>
     * The transition type can be changed without problems at runtime, so it is possible to change the animation based
     * on the page that is about to become current.
     *
     * @param type The new transition type.
     */
    public void setTransitionType(GtkStackTransitionType type) {
        if (type != null) {
            library.gtk_stack_set_transition_type(getCReference(), GtkStackTransitionType.getCValueFromType(type));
        }
    }

    /**
     * Gets the currently visible child of stack
     *
     * @return The visible child of the GtkStack, if defined
     */
    public Option<GtkWidget> getVisibleChild() {
        Option<Pointer> p = new Option<>(library.gtk_stack_get_visible_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Makes child the visible child of stack.
     * <p>
     * If child is different from the currently visible child, the transition between the two will be animated with the
     * current transition type of stack.
     * <p>
     * Note that the child widget has to be visible itself (see gtk_widget_show()) in order to become the visible child
     * of stack.
     *
     * @param child A child of stack.
     */
    public void setVisibleChild(GtkWidget child) {
        if (child != null) {
            library.gtk_stack_set_visible_child(getCReference(), child.getCReference());
        }
    }

    /**
     * Makes the child with the given name visible.
     * <p>
     * If child is different from the currently visible child, the transition between the two will be animated with the
     * current transition type of stack.
     * <p>
     * Note that the child widget has to be visible itself (see gtk_widget_show()) in order to become the visible child
     * of stack.
     *
     * @param name The name of the child to make visible.
     */
    public void setVisibleChild(String name) {
        if (name != null) {
            library.gtk_stack_set_visible_child_name(getCReference(), name);
        }
    }

    /**
     * Returns the name of the currently visible child of stack.
     *
     * @return The name of the visible child of the GtkStack, if defined
     */
    public Option<String> getVisibleChildName() {
        return new Option<>(library.gtk_stack_get_visible_child_name(getCReference()));
    }

    /**
     * Gets whether stack is horizontally homogeneous.
     *
     * @return TRUE if stack is horizontally homogeneous.
     */
    public boolean isHorizontallyHomogeneous() {
        return library.gtk_stack_get_hhomogeneous(getCReference());
    }

    /**
     * Sets the GtkStack to be horizontally homogeneous
     * <p>
     * If it is homogeneous, the GtkStack will request the same width for all its children.
     * If it isn't, the stack may change width when a different child becomes visible.
     *
     * @param isHHomogeneous TRUE to make stack horizontally homogeneous.
     */
    public void setHorizontallyHomogeneous(boolean isHHomogeneous) {
        library.gtk_stack_set_hhomogeneous(getCReference(), isHHomogeneous);
    }

    /**
     * Returns whether the stack is currently in a transition from one page to another.
     *
     * @return TRUE if the transition is currently running, FALSE otherwise.
     */
    public boolean isTransitioning() {
        return library.gtk_stack_get_transition_running(getCReference());
    }

    /**
     * Gets whether stack is vertically homogeneous.
     *
     * @return Whether stack is vertically homogeneous.
     */
    public boolean isVerticallyHomogeneous() {
        return library.gtk_stack_get_vhomogeneous(getCReference());
    }

    /**
     * Sets the GtkStack to be vertically homogeneous or not.
     * <p>
     * If it is homogeneous, the GtkStack will request the same height for all its children. If it isn't, the stack may
     * change height when a different child becomes visible.
     *
     * @param isVHomogeneous TRUE to make stack vertically homogeneous.
     */
    public void setVerticallyHomogeneous(boolean isVHomogeneous) {
        library.gtk_stack_set_vhomogeneous(getCReference(), isVHomogeneous);
    }

    /**
     * Removes a child widget from stack.
     *
     * @param w The child to remove.
     */
    public void removeChild(GtkWidget w) {
        if (w != null) {
            library.gtk_stack_remove(getCReference(), w.getCReference());
        }
    }

    /**
     * Makes the child with the given name visible.
     * <p>
     * Note that the child widget has to be visible itself (see gtk_widget_show()) in order to become the visible child
     * of stack.
     *
     * @param name       The name of the child to make visible.
     * @param transition The transition type to use.
     */
    public void setVisibleChild(String name, GtkStackTransitionType transition) {
        if (name != null && transition != null) {
            library.gtk_stack_set_visible_child_full(getCReference(), name, GtkStackTransitionType.getCValueFromType(transition));
        }
    }

    /**
     * Sets whether stack will interpolate its size when changing the visible child.
     * <p>
     * If the GtkStack:interpolate-size property is set to TRUE, stack will interpolate its size between the current one
     * and the one it'll take after changing the visible child, according to the set transition duration.
     *
     * @param doesInterpolate TRUE to make stack interpolate sizes
     */
    public void shouldInterpolateSizes(boolean doesInterpolate) {
        library.gtk_stack_set_interpolate_size(getCReference(), doesInterpolate);
    }

    protected static class GtkStackLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a child to stack.
         *
         * @param stack self
         * @param child The widget to add. Type: GtkWidget
         * @return The GtkStackPage for child.
         */
        public native Pointer gtk_stack_add_child(Pointer stack, Pointer child);

        /**
         * Adds a child to stack.
         * <p>
         * The child is identified by the name.
         *
         * @param stack self
         * @param child The widget to add. Type: GtkWidget
         * @param name  The name for child.
         *              <p>
         *              The argument can be NULL.
         * @return The GtkStackPage for child. Type: GtkStackPage
         */
        public native Pointer gtk_stack_add_named(Pointer stack, Pointer child, String name);

        /**
         * Adds a child to stack.
         * <p>
         * The child is identified by the name. The title will be used by GtkStackSwitcher to represent child in a tab
         * bar, so it should be short.
         *
         * @param stack self
         * @param child The widget to add. Type: GtkWidget
         * @param name  The name for child.
         *              <p>
         *              The argument can be NULL.
         * @param title A human-readable title for child.
         * @return The GtkStackPage for child. Type: GtkStackPage
         */
        public native Pointer gtk_stack_add_titled(Pointer stack, Pointer child, String name, String title);

        /**
         * Finds the child with the name given as the argument.
         * <p>
         * Returns NULL if there is no child with this name.
         *
         * @param stack self
         * @param name  The name of the child to find.
         * @return The requested child of the GtkStack. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_stack_get_child_by_name(Pointer stack, String name);

        /**
         * Gets whether stack is horizontally homogeneous.
         *
         * @param stack self
         * @return Whether stack is horizontally homogeneous.
         */
        public native boolean gtk_stack_get_hhomogeneous(Pointer stack);

        /**
         * Returns whether the GtkStack is set up to interpolate between the sizes of children on page switch.
         *
         * @param stack self
         * @return TRUE if child sizes are interpolated.
         */
        public native boolean gtk_stack_get_interpolate_size(Pointer stack);

        /**
         * Returns the GtkStackPage object for child.
         *
         * @param stack self
         * @param child A child of stack. Type: GtkWidget
         * @return The GtkStackPage for child.
         */
        public native Pointer gtk_stack_get_page(Pointer stack, Pointer child);

        /**
         * Returns a GListModel that contains the pages of the stack.
         * <p>
         * This can be used to keep an up-to-date view. The model also implements GtkSelectionModel and can be used to
         * track and modify the visible page.
         *
         * @param stack self
         * @return A GtkSelectionModel for the stack's children. Type: GtkSelectionModel
         */
        public native Pointer gtk_stack_get_pages(Pointer stack);

        /**
         * Returns the amount of time (in milliseconds) that transitions between pages in stack will take.
         *
         * @param stack self
         * @return The transition duration.
         */
        public native int gtk_stack_get_transition_duration(Pointer stack);

        /**
         * Returns whether the stack is currently in a transition from one page to another.
         *
         * @param stack self
         * @return TRUE if the transition is currently running, FALSE otherwise.
         */
        public native boolean gtk_stack_get_transition_running(Pointer stack);

        /**
         * Gets the type of animation that will be used for transitions between pages in stack.
         *
         * @param stack self
         * @return The current transition type of stack. Type: GtkStackTransitionType
         */
        public native int gtk_stack_get_transition_type(Pointer stack);

        /**
         * Gets whether stack is vertically homogeneous.
         *
         * @param stack self
         * @return Whether stack is vertically homogeneous.
         */
        public native boolean gtk_stack_get_vhomogeneous(Pointer stack);

        /**
         * Gets the currently visible child of stack.
         * <p>
         * Returns NULL if there are no visible children.
         *
         * @param stack self
         * @return The visible child of the GtkStack
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_stack_get_visible_child(Pointer stack);

        /**
         * Returns the name of the currently visible child of stack.
         * <p>
         * Returns NULL if there is no visible child.
         *
         * @param stack self
         * @return The name of the visible child of the GtkStack
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_stack_get_visible_child_name(Pointer stack);

        /**
         * Creates a new GtkStack.
         *
         * @return A new GtkStack
         */
        public native Pointer gtk_stack_new();

        /**
         * Removes a child widget from stack.
         *
         * @param stack self
         * @param child The child to remove. Type: GtkWidget
         */
        public native void gtk_stack_remove(Pointer stack, Pointer child);

        /**
         * Sets the GtkStack to be horizontally homogeneous or not.
         * <p>
         * If it is homogeneous, the GtkStack will request the same width for all its children. If it isn't, the stack
         * may change width when a different child becomes visible.
         *
         * @param stack        self
         * @param hhomogeneous TRUE to make stack horizontally homogeneous.
         */
        public native void gtk_stack_set_hhomogeneous(Pointer stack, boolean hhomogeneous);

        /**
         * Sets whether stack will interpolate its size when changing the visible child.
         * <p>
         * If the GtkStack:interpolate-size property is set to TRUE, stack will interpolate its size between the current
         * one and the one it'll take after changing the visible child, according to the set transition duration.
         *
         * @param stack            self
         * @param interpolate_size The new value.
         */
        public native void gtk_stack_set_interpolate_size(Pointer stack, boolean interpolate_size);

        /**
         * Sets the duration that transitions between pages in stack will take.
         *
         * @param stack    self
         * @param duration The new duration, in milliseconds.
         */
        public native void gtk_stack_set_transition_duration(Pointer stack, int duration);

        /**
         * Sets the type of animation that will be used for transitions between pages in stack.
         * <p>
         * Available types include various kinds of fades and slides.
         * <p>
         * The transition type can be changed without problems at runtime, so it is possible to change the animation
         * based on the page that is about to become current.
         *
         * @param stack      self
         * @param transition The new transition type. Type: GtkStackTransitionType
         */
        public native void gtk_stack_set_transition_type(Pointer stack, int transition);

        /**
         * Sets the GtkStack to be vertically homogeneous or not.
         * <p>
         * If it is homogeneous, the GtkStack will request the same height for all its children. If it isn't,
         * the stack may change height when a different child becomes visible.
         *
         * @param stack        self
         * @param vhomogeneous TRUE to make stack vertically homogeneous.
         */
        public native void gtk_stack_set_vhomogeneous(Pointer stack, boolean vhomogeneous);

        /**
         * Makes child the visible child of stack.
         * <p>
         * If child is different from the currently visible child, the transition between the two will be animated with
         * the current transition type of stack.
         * <p>
         * Note that the child widget has to be visible itself (see gtk_widget_show()) in order to become the visible
         * child of stack.
         *
         * @param stack self
         * @param child A child of stack. Type: GtkWidget
         */
        public native void gtk_stack_set_visible_child(Pointer stack, Pointer child);

        /**
         * Makes the child with the given name visible.
         * <p>
         * Note that the child widget has to be visible itself (see gtk_widget_show()) in order to become the visible
         * child of stack.
         *
         * @param stack      self
         * @param name       The name of the child to make visible.
         * @param transition The transition type to use. Type: GtkStackTransitionType
         */
        public native void gtk_stack_set_visible_child_full(Pointer stack, String name, int transition);

        /**
         * Makes the child with the given name visible.
         * <p>
         * If child is different from the currently visible child, the transition between the two will be animated with
         * the current transition type of stack.
         * <p>
         * Note that the child widget has to be visible itself (see gtk_widget_show()) in order to become the visible
         * child of stack.
         *
         * @param stack self
         * @param name  The name of the child to make visible.
         */
        public native void gtk_stack_set_visible_child_name(Pointer stack, String name);

    }
}
