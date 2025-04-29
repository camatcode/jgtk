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
import com.gitlab.ccook.jgtk.enums.GtkBaselinePosition;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkOrientable;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkCenterBox arranges three children in a row, keeping the middle child centered as well as possible.
 * <p>
 * An example GtkCenterBox
 * <p>
 * To add children to GtkCenterBox, use gtk_center_box_set_start_widget(), gtk_center_box_set_center_widget() and
 * gtk_center_box_set_end_widget().
 * <p>
 * The sizing and positioning of children can be influenced with align properties and expand properties of the children.
 */
@SuppressWarnings("unchecked")
public class GtkCenterBox extends GtkWidget implements GtkAccessible, GtkBuildable, GtkOrientable {

    private static final GtkCenterBoxLibrary library = new GtkCenterBoxLibrary();

    /**
     * Creates a new GtkCenterBox.
     */
    public GtkCenterBox() {
        super(library.gtk_center_box_new());
    }

    /**
     * Creates a new GtkCenterBox.
     *
     * @param start  widget at start
     * @param center widget at center
     * @param end    widget at end
     */
    public GtkCenterBox(GtkWidget start, GtkWidget center, GtkWidget end) {
        super(library.gtk_center_box_new());
        setStartWidget(start);
        setCenterWidget(center);
        setEndWidget(end);
    }

    public GtkCenterBox(Pointer ref) {
        super(ref);
    }

    /**
     * Gets the value set by gtk_center_box_set_baseline_position().
     *
     * @return The baseline position.
     */
    public GtkBaselinePosition getBaselinePosition() {
        return GtkBaselinePosition.getPositionFromCValue(library.gtk_center_box_get_baseline_position(cReference));
    }

    /**
     * Sets the baseline position of a center box.
     * <p>
     * This affects only horizontal boxes with at least one baseline aligned child. If there is more vertical space
     * available than requested, and the baseline is not allocated by the parent then position is used to allocate
     * the baseline with regard to the extra space available.
     *
     * @param pos A GtkBaselinePosition
     */
    public void setBaselinePosition(GtkBaselinePosition pos) {
        if (pos != null) {
            library.gtk_center_box_set_baseline_position(getCReference(), pos.getCValue());
        }
    }

    /**
     * Gets the center widget, or NONE
     *
     * @return The center widget.
     */
    public Option<GtkWidget> getCenterWidget() {
        Option<Pointer> p = new Option<>(library.gtk_center_box_get_center_widget(cReference));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the center widget.
     * <p>
     * To remove the existing center widget, pass NULL.
     *
     * @param w The new center widget.
     */
    public void setCenterWidget(GtkWidget w) {
        library.gtk_center_box_set_center_widget(cReference, Pointer.NULL);
        library.gtk_center_box_set_center_widget(cReference, pointerOrNull(w));
    }

    /**
     * Gets the end widget, or NONE
     *
     * @return The end widget.
     */
    public Option<GtkWidget> getEndWidget() {
        Option<Pointer> p = new Option<>(library.gtk_center_box_get_end_widget(cReference));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the end widget.
     * <p>
     * To remove the existing end widget, pass NULL.
     *
     * @param w The new end widget.
     */
    public void setEndWidget(GtkWidget w) {
        library.gtk_center_box_set_end_widget(cReference, Pointer.NULL);
        library.gtk_center_box_set_end_widget(cReference, pointerOrNull(w));
    }

    /**
     * Gets the start widget, or NONE if there is none.
     *
     * @return the start widget
     */
    public Option<GtkWidget> getStartWidget() {
        Option<Pointer> p = new Option<>(library.gtk_center_box_get_start_widget(cReference));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the start widget.
     * <p>
     * To remove the existing start widget, pass NULL.
     *
     * @param w The new start widget.
     */
    public void setStartWidget(GtkWidget w) {
        library.gtk_center_box_set_start_widget(cReference, Pointer.NULL);
        library.gtk_center_box_set_start_widget(cReference, pointerOrNull(w));
    }

    protected static class GtkCenterBoxLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the value set by gtk_center_box_set_baseline_position().
         *
         * @param self self
         * @return The baseline position. Type: GtkBaselinePosition
         */
        public native int gtk_center_box_get_baseline_position(Pointer self);

        /**
         * Gets the center widget, or NULL if there is none.
         *
         * @param self self
         * @return The center widget. Type: GtkWidget
         */
        public native Pointer gtk_center_box_get_center_widget(Pointer self);

        /**
         * Gets the end widget, or NULL if there is none.
         *
         * @param self self
         * @return The end widget. Type: GtkWidget
         */
        public native Pointer gtk_center_box_get_end_widget(Pointer self);

        /**
         * Gets the start widget, or NULL if there is none.
         *
         * @param self self
         * @return The start widget. Type: GtkWidget
         */
        public native Pointer gtk_center_box_get_start_widget(Pointer self);

        /**
         * Creates a new GtkCenterBox.
         *
         * @return The new GtkCenterBox. Type: GtkCenterBox
         */
        public native Pointer gtk_center_box_new();

        /**
         * Sets the baseline position of a center box.
         * <p>
         * This affects only horizontal boxes with at least one baseline aligned child. If there is more vertical space
         * available than requested, and the baseline is not allocated by the parent then position is used to allocate
         * the baseline wrt. the extra space available.
         *
         * @param self     self
         * @param position A GtkBaselinePosition. Type: GtkBaselinePosition
         */
        public native void gtk_center_box_set_baseline_position(Pointer self, int position);

        /**
         * Sets the center widget.
         * <p>
         * To remove the existing center widget, pass NULL.
         *
         * @param self  self
         * @param child The new center widget.
         *              <p>
         *              The argument can be NULL.
         *              Type: GtkWidget
         */
        public native void gtk_center_box_set_center_widget(Pointer self, Pointer child);

        /**
         * Sets the end widget.
         * <p>
         * To remove the existing end widget, pass NULL.
         *
         * @param self  self
         * @param child The new end widget.
         *              <p>
         *              The argument can be NULL.
         *              Type: GtkWidget
         */
        public native void gtk_center_box_set_end_widget(Pointer self, Pointer child);

        /**
         * Sets the start widget.
         * <p>
         * To remove the existing start widget, pass NULL.
         *
         * @param self  self
         * @param child The new start widget.
         *              <p>
         *              The argument can be NULL
         *              Type: GtkWidget
         */
        public native void gtk_center_box_set_start_widget(Pointer self, Pointer child);
    }

}
