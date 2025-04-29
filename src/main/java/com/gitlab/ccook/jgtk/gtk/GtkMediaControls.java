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

import com.gitlab.ccook.jgtk.GtkMediaStream;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkMediaControls is a widget to show controls for a video.
 * <p>
 * Usually, GtkMediaControls is used as part of GtkVideo.
 */
@SuppressWarnings("unchecked")
public class GtkMediaControls extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkMediaControlsLibrary library = new GtkMediaControlsLibrary();

    public GtkMediaControls(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkMediaControls managing the stream passed to it.
     *
     * @param stream A GtkMediaStream to manage.
     *               <p>
     *               The argument can be NULL.
     */
    public GtkMediaControls(GtkMediaStream stream) {
        super(library.gtk_media_controls_new(pointerOrNull(stream)));
    }

    /**
     * Gets the media stream managed by controls, if defined.
     *
     * @return The media stream managed by controls, if defined
     */
    public Option<GtkMediaStream> getMediaStream() {
        Option<Pointer> p = new Option<>(library.gtk_media_controls_get_media_stream(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkMediaStream) JGTKObject.newObjectFromType(p.get(), GtkMediaStream.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the stream that is controlled by controls.
     *
     * @param s A GtkMediaStream
     *          <p>
     *          The argument can be NULL.
     */
    public void setMediaStream(GtkMediaStream s) {
        library.gtk_media_controls_set_media_stream(getCReference(), pointerOrNull(s));
    }

    protected static class GtkMediaControlsLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the media stream managed by controls or NULL if none.
         *
         * @param controls self
         * @return The media stream managed by controls. Type: GtkMediaStream
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_media_controls_get_media_stream(Pointer controls);

        /**
         * Creates a new GtkMediaControls managing the stream passed to it.
         *
         * @param stream A GtkMediaStream to manage. Type: GtkMediaStream
         *               <p>
         *               The argument can be NULL
         * @return A new GtkMediaControls. Type: GtkMediaControls
         */
        public native Pointer gtk_media_controls_new(Pointer stream);

        /**
         * Sets the stream that is controlled by controls.
         *
         * @param controls self
         * @param stream   A GtkMediaStream. Type: GtkMediaStream
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_media_controls_set_media_stream(Pointer controls, Pointer stream);
    }
}
