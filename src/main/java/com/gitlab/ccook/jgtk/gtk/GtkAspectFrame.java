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
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkAspectFrame preserves the aspect ratio of its child.
 * <p>
 * The frame can respect the aspect ratio of the child widget, or use its own aspect ratio.
 */
@SuppressWarnings("unchecked")
public class GtkAspectFrame extends GtkWidget implements GtkAccessible, GtkBuildable {

    private static final GtkAspectFrameLibrary library = new GtkAspectFrameLibrary();

    /**
     * Create a new GtkAspectFrame.
     *
     * @param horizontalAlign Horizontal alignment of the child within the parent. Ranges from 0.0 (left aligned) to 1.0
     *                        (right aligned)
     * @param verticalAlign   Vertical alignment of the child within the parent. Ranges from 0.0 (top aligned) to 1.0
     *                        (bottom aligned)
     * @param ratio           The desired aspect ratio.
     * @param obeyChild       If TRUE, ratio is ignored, and the aspect ratio is taken from the requisition of the
     *                        child.
     */
    public GtkAspectFrame(float horizontalAlign, float verticalAlign, float ratio, boolean obeyChild) {
        super(handleCtor(horizontalAlign, verticalAlign, ratio, obeyChild));
    }

    private static Pointer handleCtor(float xAlign, float yAlign, float ratio, boolean obeyChild) {
        if (xAlign < 0.0f) {
            xAlign = 0.0f;
        } else if (xAlign > 1.0f) {
            xAlign = 1.0f;
        }
        if (yAlign < 0.0f) {
            yAlign = 0.0f;
        } else if (yAlign > 1.0f) {
            yAlign = 1.0f;
        }
        return library.gtk_aspect_frame_new(xAlign, yAlign, ratio, obeyChild);
    }

    public GtkAspectFrame(Pointer ref) {
        super(ref);
    }

    /**
     * Returns whether the child's size request should override the set aspect ratio of the GtkAspectFrame.
     *
     * @return Whether to obey the child's size request.
     */
    public boolean doesObeyChild() {
        return library.gtk_aspect_frame_get_obey_child(cReference);
    }

    /**
     * Gets the child widget of self.
     *
     * @return The child widget of self.
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> pointer = new Option<>(library.gtk_aspect_frame_get_child(cReference));
        if (pointer.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(pointer.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of self.
     *
     * @param w The child widget. (This may be NULL)
     */
    public void setChild(GtkWidget w) {
        library.gtk_aspect_frame_set_child(cReference, pointerOrNull(w));
    }

    /**
     * Returns the horizontal alignment of the child within the allocation of the GtkAspectFrame
     *
     * @return The horizontal alignment.
     */
    public float getHorizontalAlignment() {
        return library.gtk_aspect_frame_get_xalign(cReference);
    }

    /**
     * Sets the horizontal alignment of the child within the allocation of the GtkAspectFrame.
     *
     * @param alignment Horizontal alignment, from 0.0 (left aligned) to 1.0 (right aligned)
     */
    public void setHorizontalAlignment(float alignment) {
        if (alignment > 1.0f) {
            alignment = 1.0f;
        } else if (alignment < 0.0f) {
            alignment = 0.0f;
        }
        library.gtk_aspect_frame_set_xalign(cReference, alignment);
    }

    /**
     * Returns the desired aspect ratio of the child.
     *
     * @return The desired aspect ratio.
     */
    public float getRatio() {
        return library.gtk_aspect_frame_get_ratio(cReference);
    }

    /**
     * Returns the vertical alignment of the child within the allocation of the GtkAspectFrame.
     *
     * @return The vertical alignment.
     */
    public float getVerticalAlignment() {
        return library.gtk_aspect_frame_get_yalign(cReference);
    }

    /**
     * Sets the vertical alignment of the child within the allocation of the GtkAspectFrame.
     *
     * @param alignment Horizontal alignment, from 0.0 (top aligned) to 1.0 (bottom aligned)
     */
    public void setVerticalAlignment(float alignment) {
        if (alignment > 1.0f) {
            alignment = 1.0f;
        } else if (alignment < 0.0f) {
            alignment = 0.0f;
        }
        library.gtk_aspect_frame_set_yalign(cReference, alignment);
    }

    /**
     * Sets the desired aspect ratio of the child.
     *
     * @param ratio Aspect ratio of the child.
     */
    public void setAspectRatio(float ratio) {
        library.gtk_aspect_frame_set_ratio(cReference, ratio);
    }

    /**
     * Sets whether the aspect ratio of the child's size request should override the set aspect ratio of the
     * GtkAspectFrame.
     *
     * @param obey If TRUE, ratio is ignored, and the aspect ratio is taken from the requisition of the child.
     */
    public void shouldObeyChild(boolean obey) {
        library.gtk_aspect_frame_set_obey_child(cReference, obey);
    }

    protected static class GtkAspectFrameLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget of self.
         *
         * @param self self
         * @return The child widget of self.
         */
        public native Pointer gtk_aspect_frame_get_child(Pointer self);

        /**
         * Returns whether the child's size request should override the set aspect ratio of the GtkAspectFrame.
         *
         * @param self self
         * @return Whether to obey the child's size request.
         */
        public native boolean gtk_aspect_frame_get_obey_child(Pointer self);

        /**
         * Returns the desired aspect ratio of the child.
         *
         * @param self self
         * @return The desired aspect ratio.
         */
        public native float gtk_aspect_frame_get_ratio(Pointer self);

        /**
         * Returns the horizontal alignment of the child within the allocation of the GtkAspectFrame
         *
         * @param self self
         * @return The horizontal alignment.
         */
        public native float gtk_aspect_frame_get_xalign(Pointer self);

        /**
         * Returns the vertical alignment of the child within the allocation of the GtkAspectFrame.
         *
         * @param self self
         * @return The vertical alignment.
         */
        public native float gtk_aspect_frame_get_yalign(Pointer self);

        /**
         * Create a new GtkAspectFrame.
         *
         * @param xAlign    Horizontal alignment of the child within the parent. Ranges from 0.0 (left aligned) to 1.0
         *                  (right aligned)
         * @param yAlign    Vertical alignment of the child within the parent. Ranges from 0.0 (top aligned) to 1.0
         *                  (bottom aligned)
         * @param ratio     The desired aspect ratio.
         * @param obeyChild If TRUE, ratio is ignored, and the aspect ratio is taken from the requisition of the child.
         * @return The new GtkAspectFrame.
         */
        public native Pointer gtk_aspect_frame_new(float xAlign, float yAlign, float ratio, boolean obeyChild);

        /**
         * Sets the child widget of self.
         *
         * @param self  self
         * @param child The child widget. Type: GtkWidget
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_aspect_frame_set_child(Pointer self, Pointer child);

        /**
         * Sets whether the aspect ratio of the child's size request should override the set aspect ratio of the
         * GtkAspectFrame.
         *
         * @param self       self
         * @param obey_child If TRUE, ratio is ignored, and the aspect ratio is taken from the requisition of the child.
         */
        public native void gtk_aspect_frame_set_obey_child(Pointer self, boolean obey_child);

        /**
         * Sets the desired aspect ratio of the child.
         *
         * @param self  self
         * @param ratio Aspect ratio of the child.
         */
        public native void gtk_aspect_frame_set_ratio(Pointer self, float ratio);

        /**
         * Sets the horizontal alignment of the child within the allocation of the GtkAspectFrame.
         *
         * @param self   self
         * @param xalign Horizontal alignment, from 0.0 (left aligned) to 1.0 (right aligned)
         */
        public native void gtk_aspect_frame_set_xalign(Pointer self, float xalign);

        /**
         * Sets the vertical alignment of the child within the allocation of the GtkAspectFrame.
         *
         * @param self   self
         * @param yalign Horizontal alignment, from 0.0 (top aligned) to 1.0 (bottom aligned)
         */
        public native void gtk_aspect_frame_set_yalign(Pointer self, float yalign);

    }


}
