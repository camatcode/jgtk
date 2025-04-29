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
 * GtkFrame is a widget that surrounds its child with a decorative frame and an optional label.
 * <p>
 * An example GtkFrame
 * <p>
 * If present, the label is drawn inside the top edge of the frame. The horizontal position of the label can be
 * controlled with gtk_frame_set_label_align().
 * <p>
 * GtkFrame clips its child. You can use this to add rounded corners to widgets, but be aware that it also cuts off
 * shadows.
 */
@SuppressWarnings("unchecked")
public class GtkFrame extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkFrameLibrary library = new GtkFrameLibrary();

    public GtkFrame(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkFrame
     */
    public GtkFrame() {
        super((library.gtk_frame_new(null)));
    }

    /**
     * Creates a new GtkFrame, with optional label.
     * <p>
     * If label is NULL, the label is omitted.
     *
     * @param label The text to use as the label of the frame.
     *              <p>
     *              The argument can be NULL.
     */
    public GtkFrame(String label) {
        super(library.gtk_frame_new(label));
    }

    /**
     * Gets the child widget of frame.
     *
     * @return The child widget of frame, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_frame_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of frame.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget w) {
        if (w != null) {
            library.gtk_frame_set_child(getCReference(), w.getCReference());
        } else {
            library.gtk_frame_set_child(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Returns the frame label's text.
     * <p>
     * If the frame's label widget is not a GtkLabel, NONE is returned.
     *
     * @return The text in the label, or NULL if there was no label widget or the label widget was not a GtkLabel.
     *         This string is owned by GTK and must not be modified or freed.
     */
    public Option<String> getLabel() {
        return new Option<>(library.gtk_frame_get_label(getCReference()));
    }

    /**
     * Creates a new GtkLabel with the label and sets it as the frame's label widget.
     *
     * @param text The text to use as the label of the frame.
     *             <p>
     *             The argument can be NULL.
     */
    public void setLabel(String text) {
        library.gtk_frame_set_label(getCReference(), text);
    }

    /**
     * Retrieves the X alignment of the frame's label.
     *
     * @return The frame's label's X alignment.
     */
    public float getLabelHorizontalAlignment() {
        return library.gtk_frame_get_label_align(getCReference());
    }

    /**
     * Sets the X alignment of the frame widget's label.
     * <p>
     * The default value for a newly created frame is 0.0.
     *
     * @param xAlign The position of the label along the top edge of the widget. A value of 0.0 represents left
     *               alignment; 1.0 represents right alignment.
     */
    public void setLabelHorizontalAlignment(float xAlign) {
        library.gtk_frame_set_label_align(getCReference(), Math.min(1, Math.max(0, xAlign)));
    }

    /**
     * Retrieves the label widget for the frame.
     *
     * @return The label widget, if defined
     */
    public Option<GtkWidget> getLabelWidget() {
        Option<Pointer> p = new Option<>(library.gtk_frame_get_label_widget(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the label widget for the frame.
     * <p>
     * This is the widget that will appear embedded in the top edge of the frame as a title.
     *
     * @param w The new label widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setLabelWidget(GtkWidget w) {
        library.gtk_frame_set_label_widget(getCReference(), pointerOrNull(w));
    }

    protected static class GtkFrameLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget of frame.
         *
         * @param frame self
         * @return The child widget of frame. Type: GtkWidget
         */
        public native Pointer gtk_frame_get_child(Pointer frame);

        /**
         * Returns the frame labels text.
         * <p>
         * If the frame's label widget is not a GtkLabel, NULL is returned.
         *
         * @param frame self
         * @return The text in the label, or NULL if there was no label widget or the label widget was not a GtkLabel.
         *         This string is owned by GTK and must not be modified or freed.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_frame_get_label(Pointer frame);

        /**
         * Retrieves the X alignment of the frame's label.
         *
         * @param frame self
         * @return The frames X alignment.
         */
        public native float gtk_frame_get_label_align(Pointer frame);

        /**
         * Retrieves the label widget for the frame.
         *
         * @param frame self
         * @return The label widget. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_frame_get_label_widget(Pointer frame);

        /**
         * Creates a new GtkFrame, with optional label.
         * <p>
         * If label is NULL, the label is omitted.
         *
         * @param label The text to use as the label of the frame.
         *              <p>
         *              The argument can be NULL.
         * @return A new GtkFrame widget. Type: GtkFrame
         */
        public native Pointer gtk_frame_new(String label);

        /**
         * Sets the child widget of frame.
         *
         * @param frame self
         * @param child The child widget. Type: GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_frame_set_child(Pointer frame, Pointer child);

        /**
         * Creates a new GtkLabel with the label and sets it as the frame's label widget.
         *
         * @param frame self
         * @param label The text to use as the label of the frame.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_frame_set_label(Pointer frame, String label);

        /**
         * Sets the X alignment of the frame widget's label.
         * <p>
         * The default value for a newly created frame is 0.0.
         *
         * @param frame  self
         * @param xalign The position of the label along the top edge of the widget. A value of 0.0 represents
         *               left alignment; 1.0 represents right alignment.
         */
        public native void gtk_frame_set_label_align(Pointer frame, float xalign);

        /**
         * Sets the label widget for the frame.
         * <p>
         * This is the widget that will appear embedded in the top edge of the frame as a title.
         *
         * @param frame        self
         * @param label_widget The new label widget. Type: GtkWidget
         *                     <p>
         *                     The argument can be NULL.
         */
        public native void gtk_frame_set_label_widget(Pointer frame, Pointer label_widget);
    }
}
