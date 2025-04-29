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
import com.gitlab.ccook.jgtk.enums.GtkBaselinePosition;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkOrientable;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * The GtkBox widget arranges child widgets into a single row or column.
 * <p>
 * An example GtkBox
 * <p>
 * Whether it is a row or column depends on the value of its GtkOrientable:orientation property. Within the other
 * dimension, all children are allocated the same size. Of course, the GtkWidget:halign and GtkWidget:valign properties
 * can be used on the children to influence their allocation.
 * <p>
 * Use repeated calls to gtk_box_append() to pack widgets into a GtkBox from start to end. Use gtk_box_remove() to
 * remove widgets from the GtkBox. gtk_box_insert_child_after() can be used to add a child at a particular position.
 * <p>
 * Use gtk_box_set_homogeneous() to specify whether all children of the GtkBox are forced to get the same
 * amount of space.
 * <p>
 * Use gtk_box_set_spacing() to determine how much space will be minimally placed between all children in the GtkBox.
 * Note that spacing is added between the children.
 * <p>
 * Use gtk_box_reorder_child_after() to move a child to a different place in the box.
 */
public class GtkBox extends GtkWidget implements GtkAccessible, GtkBuildable, GtkOrientable {

    private static final GtkBoxLibrary library = new GtkBoxLibrary();

    /**
     * Creates a new GtkBox.
     *
     * @param orientation The box's orientation.
     * @param spacing     The number of pixels to place by default between children.
     */
    public GtkBox(GtkOrientation orientation, int spacing) {
        super(library.gtk_box_new(orientation.getCValue(), spacing));
    }

    /**
     * GtkBox from pointer
     *
     * @param cRef box pointer
     */
    public GtkBox(Pointer cRef) {
        super(cRef);
    }

    /**
     * Adds child as the last child to box.
     *
     * @param toAppend widget to append
     */
    public void append(GtkWidget toAppend) {
        if (toAppend != null) {
            library.gtk_box_append(this.cReference, toAppend.getCReference());
        }
    }

    /**
     * Gets the value set by gtk_box_set_baseline_position().
     *
     * @return the Baseline Position
     */
    public GtkBaselinePosition getBaselinePosition() {
        return GtkBaselinePosition.getPositionFromCValue(library.gtk_box_get_baseline_position(cReference));
    }

    /**
     * Sets the baseline position of a box.
     * <p>
     * This affects only horizontal boxes with at least one baseline aligned child.
     * If there is more vertical space available than requested and the baseline is not allocated by the parent
     * then position is used to allocate the baseline with respect to the extra space available.
     *
     * @param position A GtkBaselinePosition
     */
    public void setBaselinePosition(GtkBaselinePosition position) {
        library.gtk_box_set_baseline_position(cReference, position.getCValue());
    }

    /**
     * Spacing between children.
     *
     * @return Spacing between children.
     */
    public int getSpacing() {
        return library.gtk_box_get_spacing(cReference);
    }

    /**
     * Sets the number of pixels to place between children of box.
     *
     * @param spacing The number of pixels to put between children.
     */
    public void setSpacing(int spacing) {
        library.gtk_box_set_spacing(cReference, spacing);
    }

    /**
     * Inserts child in the position after sibling in the list of box children.
     * <p>
     * If sibling is NULL, insert child at the first position.
     *
     * @param toInsertAfter The GtkWidget to insert.
     * @param sibling       The sibling after which to insert child. (This may be null)
     */
    public void insertChildAfter(GtkWidget toInsertAfter, GtkWidget sibling) {
        if (toInsertAfter != null) {
            library.gtk_box_insert_child_after(cReference, toInsertAfter.getCReference(), pointerOrNull(sibling));
        }
    }

    /**
     * Returns whether the box is homogeneous (all children are the same size).
     *
     * @return TRUE if homogeneous
     */
    public boolean isHomogeneous() {
        return library.gtk_box_get_homogeneous(cReference);
    }

    /**
     * Sets whether all children of box are given equal space in the box.
     *
     * @param isHomogeneous A boolean value, TRUE to create equal allotments, FALSE for variable allotments.
     */
    public void setHomogeneous(boolean isHomogeneous) {
        library.gtk_box_set_homogeneous(cReference, isHomogeneous);
    }

    /**
     * Adds child as the first child to box.
     *
     * @param toPrepend The GtkWidget to prepend.
     */
    public void prepend(GtkWidget toPrepend) {
        if (toPrepend != null) {
            library.gtk_box_prepend(cReference, toPrepend.getCReference());
        }
    }

    /**
     * Removes a child widget from box.
     * <p>
     * The child must have been added before with gtk_box_append(), gtk_box_prepend(), or gtk_box_insert_child_after().
     *
     * @param toRemove child to remove
     */
    public void remove(GtkWidget toRemove) {
        if (toRemove != null) {
            library.gtk_box_remove(cReference, toRemove.getCReference());
        }
    }

    /**
     * Moves child to the position after sibling in the list of box children.
     * <p>
     * If sibling is NULL, move child to the first position.
     *
     * @param toMove                       The GtkWidget to move, must be a child of box.
     * @param siblingThatComesBeforeToMove The sibling to move child after.
     *                                     <p>
     *                                     The argument can be NULL.
     */
    public void reorderChildAfter(GtkWidget toMove, GtkWidget siblingThatComesBeforeToMove) {
        if (toMove != null) {
            library.gtk_box_reorder_child_after(cReference, toMove.getCReference(), pointerOrNull(siblingThatComesBeforeToMove));
        }
    }

    protected static class GtkBoxLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds child as the last child to box.
         *
         * @param box   self
         * @param child The GtkWidget to append. Type: GtkWidget
         */
        public native void gtk_box_append(Pointer box, Pointer child);

        /**
         * Gets the value set by gtk_box_set_baseline_position().
         *
         * @param box self
         * @return The baseline position.
         */
        public native int gtk_box_get_baseline_position(Pointer box);

        /**
         * Returns whether the box is homogeneous (all children are the same size).
         *
         * @param box self
         * @return TRUE if the box is homogeneous.
         */
        public native boolean gtk_box_get_homogeneous(Pointer box);

        /**
         * Gets the value set by gtk_box_set_spacing().
         *
         * @param box self
         * @return Spacing between children.
         */
        public native int gtk_box_get_spacing(Pointer box);

        /**
         * Inserts child in the position after sibling in the list of box children.
         * <p>
         * If sibling is NULL, insert child at the first position.
         *
         * @param box     self
         * @param child   The GtkWidget to insert. Type: GtkWidget
         * @param sibling The sibling after which to insert child. Type: GtkWidget
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_box_insert_child_after(Pointer box, Pointer child, Pointer sibling);

        /**
         * Creates a new GtkBox.
         *
         * @param orientation The box's orientation. Type: GtkOrientation
         * @param spacing     The number of pixels to place by default between children.
         * @return A new GtkBox.
         */
        public native Pointer gtk_box_new(int orientation, int spacing);

        /**
         * Adds child as the first child to box.
         *
         * @param box   box
         * @param child The GtkWidget to prepend. Type: GtkWidget
         */
        public native void gtk_box_prepend(Pointer box, Pointer child);

        /**
         * Removes a child widget from box.
         * <p>
         * The child must have been added before with gtk_box_append(), gtk_box_prepend(), or
         * gtk_box_insert_child_after().
         *
         * @param box   self
         * @param child The child to remove. Type: GtkWidget
         */
        public native void gtk_box_remove(Pointer box, Pointer child);

        /**
         * Moves child to the position after sibling in the list of box children.
         * <p>
         * If sibling is NULL, move child to the first position.
         *
         * @param box     self
         * @param child   The GtkWidget to move, must be a child of box. Type: GtkWidget
         * @param sibling The sibling to move child after. Type: GtkWidget
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_box_reorder_child_after(Pointer box, Pointer child, Pointer sibling);

        /**
         * Sets the baseline position of a box.
         * <p>
         * This affects only horizontal boxes with at least one baseline aligned child. If there is more vertical space
         * available than requested, and the baseline is not allocated by the parent then position is used to allocate
         * the baseline with respect to the extra space available.
         *
         * @param box      self
         * @param position A GtkBaselinePosition. Type: GtkBaselinePosition
         */
        public native void gtk_box_set_baseline_position(Pointer box, int position);

        /**
         * Sets whether all children of box are given equal space in the box.
         *
         * @param box         self
         * @param homogeneous A boolean value, TRUE to create equal allotments, FALSE for variable allotments.
         */
        public native void gtk_box_set_homogeneous(Pointer box, boolean homogeneous);

        /**
         * Sets the number of pixels to place between children of box.
         *
         * @param box     self
         * @param spacing The number of pixels to put between children.
         */
        public native void gtk_box_set_spacing(Pointer box, int spacing);

    }
}
