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

import com.gitlab.ccook.jgtk.GskTransform;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * GtkFixed places its child widgets at fixed positions and with fixed sizes.
 * <p>
 * GtkFixed performs no automatic layout management.
 * <p>
 * For most applications, you should not use this container! It keeps you from having to learn about the other GTK
 * containers, but it results in broken applications. With GtkFixed, the following things will result in truncated text,
 * overlapping widgets, and other display bugs:
 * <p>
 * Themes, which may change widget sizes.
 * <p>
 * Fonts other than the one you used to write the app will of course change the size of widgets containing text; keep in
 * mind that users may use a larger font because of difficulty reading the default, or they may be using a different OS
 * that provides different fonts.
 * <p>
 * Translation of text into other languages changes its size. Also, display of non-English text will use a different
 * font in many cases.
 * <p>
 * In addition, GtkFixed does not pay attention to text direction and thus may produce unwanted results if your app
 * is run under right-to-left languages such as Hebrew or Arabic. That is: normally GTK will order containers
 * appropriately for the text direction, e.g. to put labels to the right of the thing they label when using an RTL
 * language, but it can't do that with GtkFixed. So if you need to reorder widgets depending on the text direction,
 * you would need to manually detect it and adjust child positions accordingly.
 * <p>
 * Finally, fixed positioning makes it kind of annoying to add/remove UI elements, since you have to reposition all
 * the other elements. This is a long-term maintenance problem for your application.
 * <p>
 * If you know none of these things are an issue for your application, and prefer the simplicity of GtkFixed, by all
 * means use the widget. But you should be aware of the tradeoffs.
 */
@SuppressWarnings({"unchecked"})
public class GtkFixed extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkFixedLibrary library = new GtkFixedLibrary();

    public GtkFixed(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkFixed.
     */
    public GtkFixed() {
        super(library.gtk_fixed_new());
    }

    /**
     * Adds a widget to a GtkFixed at the given position.
     *
     * @param child The widget to add.
     * @param x     The horizontal position to place the widget at.
     * @param y     The vertical position to place the widget at.
     */
    public void addChild(GtkWidget child, double x, double y) {
        if (child != null) {
            library.gtk_fixed_put(getCReference(), child.getCReference(), x, y);
        }
    }

    public Option<Pair<Double, Double>> getChildAbsolutePosition(GtkWidget child) {
        if (child != null) {
            PointerByReference xRef = new PointerByReference();
            PointerByReference yRef = new PointerByReference();
            library.gtk_fixed_get_child_position(getCReference(), child.getCReference(), xRef, yRef);
            double x = xRef.getPointer().getDouble(0);
            double y = yRef.getPointer().getDouble(0);
            return new Option<>(new Pair<>(x, y));
        }
        return Option.NONE;
    }

    /**
     * Retrieves the transformation for widget set using gtk_fixed_set_child_transform().
     *
     * @param child A GtkWidget, child of fixed.
     * @return A GskTransform
     */
    public Option<GskTransform> getChildTransform(GtkWidget child) {
        if (child != null) {
            Option<Pointer> p = new Option<>(library.gtk_fixed_get_child_transform(getCReference(), child.getCReference()));
            if (p.isDefined()) {
                return new Option<>(new GskTransform(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Sets a translation transformation to the given x and y coordinates to the child widget of the GtkFixed
     *
     * @param child child widget to move
     * @param x     The horizontal position to move the widget to.
     * @param y     The vertical position to move the widget to.
     */
    public void moveChild(GtkWidget child, double x, double y) {
        if (child != null) {
            library.gtk_fixed_move(getCReference(), child.getCReference(), x, y);
        }
    }

    /**
     * Removes a child from fixed.
     *
     * @param child The child widget to remove.
     */
    public void removeChild(GtkWidget child) {
        if (child != null) {
            library.gtk_fixed_remove(getCReference(), child.getCReference());
        }
    }


    /**
     * Sets the transformation for widget.
     * <p>
     * This is a convenience function that retrieves the GtkFixedLayoutChild instance associated to widget and calls
     * gtk_fixed_layout_child_set_transform().
     *
     * @param child     A GtkWidget, child of fixed.
     * @param transform The transformation assigned to widget to reset widget's transform.
     *                  <p>
     *                  The argument can be NULL.
     */
    public void setChildTransform(GtkWidget child, GskTransform transform) {
        if (child != null) {
            library.gtk_fixed_set_child_transform(getCReference(), child.getCReference(), pointerOrNull(transform));
        }
    }

    protected static class GtkFixedLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the translation transformation of the given child GtkWidget in the GtkFixed.
         * <p>
         * See also: gtk_fixed_get_child_transform().
         *
         * @param fixed  self
         * @param widget A child of fixed. Type: GtkWidget
         * @param x      The horizontal position of the widget.
         * @param y      The vertical position of the widget.
         */
        public native void gtk_fixed_get_child_position(Pointer fixed, Pointer widget, PointerByReference x, PointerByReference y);

        /**
         * Retrieves the transformation for widget set using gtk_fixed_set_child_transform().
         *
         * @param fixed  self
         * @param widget A GtkWidget, child of fixed. Type: GtkWidget
         * @return A GskTransform. Type: GskTransform
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_fixed_get_child_transform(Pointer fixed, Pointer widget);

        /**
         * Sets a translation transformation to the given x and y coordinates to the child widget of the GtkFixed.
         *
         * @param fixed  self
         * @param widget The child widget. Type: GtkWidget
         * @param x      The horizontal position to move the widget to.
         * @param y      The vertical position to move the widget to.
         */
        public native void gtk_fixed_move(Pointer fixed, Pointer widget, double x, double y);

        /**
         * Creates a new GtkFixed.
         *
         * @return A new GtkFixed. Type: GtkFixed
         */
        public native Pointer gtk_fixed_new();

        /**
         * Adds a widget to a GtkFixed at the given position.
         *
         * @param fixed  self
         * @param widget The widget to add. Type: GtkWidget
         * @param x      The horizontal position to place the widget at.
         * @param y      The vertical position to place the widget at.
         */
        public native void gtk_fixed_put(Pointer fixed, Pointer widget, double x, double y);

        /**
         * Removes a child from fixed.
         *
         * @param fixed  self
         * @param widget The child widget to remove. Type: GtkWidget
         */
        public native void gtk_fixed_remove(Pointer fixed, Pointer widget);

        /**
         * Sets the transformation for widget.
         * <p>
         * This is a convenience function that retrieves the GtkFixedLayoutChild instance associated to widget and
         * calls gtk_fixed_layout_child_set_transform().
         *
         * @param fixed     self
         * @param widget    A GtkWidget, child of fixed.
         * @param transform The transformation assigned to widget to reset widget's transform.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_fixed_set_child_transform(Pointer fixed, Pointer widget, Pointer transform);
    }
}
