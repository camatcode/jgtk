/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 - 2023 JGTK
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

import com.gitlab.ccook.jgtk.GFile;
import com.gitlab.ccook.jgtk.GtkMediaStream;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.io.File;


/**
 * GtkVideo is a widget to show a GtkMediaStream with media controls.
 * <p>
 * The controls are available separately as GtkMediaControls. If you just want to display a video without controls,
 * you can treat it like any other paintable and for example put it into a GtkPicture.
 * <p>
 * GtkVideo aims to cover use cases such as previews, embedded animations, etc. It supports autoplay, looping,
 * and simple media controls. It does not have support for video overlays, multichannel audio, device selection, or
 * input. If you are writing a full-fledged video player, you may want to use the GdkPaintable API and a media
 * framework such as Gstreamer directly.
 */
@SuppressWarnings("unchecked")
public class GtkVideo extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkVideoLibrary library = new GtkVideoLibrary();

    public GtkVideo(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new empty GtkVideo.
     */
    public GtkVideo() {
        super(library.gtk_video_new());
    }

    /**
     * Creates a GtkVideo to play back the given file.
     *
     * @param file A GFile
     *             <p>
     *             The argument can be NULL.
     */
    public GtkVideo(GFile file) {
        super(library.gtk_video_new_for_file(pointerOrNull(file)));
    }

    /**
     * Creates a GtkVideo to play back the given file.
     *
     * @param f Video to play
     */
    public GtkVideo(File f) {
        super(library.gtk_video_new_for_filename(f.getAbsolutePath()));
    }

    /**
     * Creates a GtkVideo to play back the given filename.
     * <p>
     * This is a utility function that calls gtk_video_new_for_file(), See that function for details.
     *
     * @param fileName Filename to play back.
     *                 <p>
     *                 The argument can be NULL.
     */
    public GtkVideo(String fileName) {
        super(library.gtk_video_new_for_filename(fileName));
    }


    /**
     * Creates a GtkVideo to play back the given stream.
     *
     * @param stream A GtkMediaStream
     */
    public GtkVideo(GtkMediaStream stream) {
        super(library.gtk_video_new_for_media_stream(pointerOrNull(stream)));
    }

    /**
     * Returns TRUE if videos have been set to loop.
     *
     * @return TRUE if streams should autoplay.
     */
    public boolean doesAutoPlay() {
        return library.gtk_video_get_autoplay(getCReference());
    }

    /**
     * Returns TRUE if videos have been set to loop.
     *
     * @return TRUE if streams should loop.
     */
    public boolean doesLoop() {
        return library.gtk_video_get_loop(getCReference());
    }

    /**
     * Gets the file played by self or NONE if not playing back a file.
     *
     * @return If defined, the file played
     */
    public Option<File> getFile() {
        Option<Pointer> p = new Option<>(library.gtk_video_get_file(getCReference()));
        if (p.isDefined()) {
            GFile f = new GFile(p.get());
            return new Option<>(new File(f.getPath().get()));
        }
        return Option.NONE;
    }

    /**
     * Makes self play the given file.
     *
     * @param f The file to play.
     *          <p>
     *          The argument can be NULL.
     */
    public void setFile(GFile f) {
        if (f != null) {
            library.gtk_video_set_file(getCReference(), f.getCReference());
        } else {
            library.gtk_video_set_file(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Gets the media stream managed by self or NONE if none.
     *
     * @return If defined, the managed media stream
     */
    public Option<GtkMediaStream> getMediaStream() {
        Option<Pointer> p = new Option<>(library.gtk_video_get_media_stream(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkMediaStream) JGTKObject.newObjectFromType(p.get(), GtkMediaStream.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the media stream to be played back.
     * <p>
     * self will take full control of managing the media stream. If you want to manage a media stream yourself,
     * consider using a GtkPicture for display.
     * <p>
     * If you want to display a file, consider using gtk_video_set_file() instead.
     *
     * @param s The media stream to play or NULL to unset.
     *          <p>
     *          The argument can be NULL.
     */
    public void setMediaStream(GtkMediaStream s) {
        library.gtk_video_set_media_stream(getCReference(), pointerOrNull(s));
    }

    /**
     * Makes self play the given filename.
     * <p>
     * This is a utility function that calls gtk_video_set_file(),
     *
     * @param fileName The filename to play.
     *                 <p>
     *                 The argument can be NULL.
     */
    public void setFileName(String fileName) {
        library.gtk_video_set_filename(getCReference(), fileName);
    }


    /**
     * Sets whether self automatically starts playback when it becomes visible or when a new file gets loaded.
     *
     * @param doesAutoPlay Whether media streams should autoplay.
     */
    public void shouldAutoPlay(boolean doesAutoPlay) {
        library.gtk_video_set_autoplay(getCReference(), doesAutoPlay);
    }

    /**
     * Sets whether new files loaded by self should be set to loop.
     *
     * @param doesLoop Whether media streams should loop.
     */
    public void shouldLoop(boolean doesLoop) {
        library.gtk_video_set_loop(getCReference(), doesLoop);
    }

    protected static class GtkVideoLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns TRUE if videos have been set to loop.
         *
         * @param self self
         * @return TRUE if streams should autoplay.
         */
        public native boolean gtk_video_get_autoplay(Pointer self);

        /**
         * Gets the file played by self or NULL if not playing back a file.
         *
         * @param self self
         * @return The file played by self. Type: GFile
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_video_get_file(Pointer self);

        /**
         * Returns TRUE if videos have been set to loop.
         *
         * @param self self
         * @return TRUE if streams should loop.
         */
        public native boolean gtk_video_get_loop(Pointer self);

        /**
         * Gets the media stream managed by self or NULL if none.
         *
         * @param self self
         * @return The media stream managed by self. Type: GtkMediaStream
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_video_get_media_stream(Pointer self);

        /**
         * Creates a new empty GtkVideo.
         *
         * @return A new GtkVideo
         */
        public native Pointer gtk_video_new();

        /**
         * Creates a GtkVideo to play back the given file.
         *
         * @param file A GFile
         *             <p>
         *             The argument can be NULL.
         * @return A new GtkVideo
         */
        public native Pointer gtk_video_new_for_file(Pointer file);

        /**
         * Creates a GtkVideo to play back the given filename.
         * <p>
         * This is a utility function that calls gtk_video_new_for_file(), See that function for details.
         *
         * @param filename Filename to play back.
         *                 <p>
         *                 The argument can be NULL.
         * @return A new GtkVideo
         */
        public native Pointer gtk_video_new_for_filename(String filename);

        /**
         * Creates a GtkVideo to play back the given stream.
         *
         * @param stream A GtkMediaStream
         *               <p>
         *               The argument can be NULL.
         * @return A new GtkVideo
         */
        public native Pointer gtk_video_new_for_media_stream(Pointer stream);

        /**
         * Sets whether self automatically starts playback when it becomes visible or when a new file gets loaded.
         *
         * @param self     self
         * @param autoplay Whether media streams should autoplay.
         */
        public native void gtk_video_set_autoplay(Pointer self, boolean autoplay);

        /**
         * Makes self play the given file.
         *
         * @param self self
         * @param file The file to play. Type: GFile
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_video_set_file(Pointer self, Pointer file);

        /**
         * Makes self play the given filename.
         * <p>
         * This is a utility function that calls gtk_video_set_file(),
         *
         * @param self     self
         * @param filename The filename to play.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_video_set_filename(Pointer self, String filename);

        /**
         * Sets whether new files loaded by self should be set to loop.
         *
         * @param self self
         * @param loop Whether media streams should loop.
         */
        public native void gtk_video_set_loop(Pointer self, boolean loop);

        /**
         * Sets the media stream to be played back.
         * <p>
         * self will take full control of managing the media stream. If you want to manage a media stream yourself,
         * consider using a GtkPicture for display.
         * <p>
         * If you want to display a file, consider using gtk_video_set_file() instead.
         *
         * @param self   self
         * @param stream The media stream to play or NULL to unset. Type: GtkMediaStream
         *               <p>
         *               The argument can be NULL
         */
        public native void gtk_video_set_media_stream(Pointer self, Pointer stream);
    }
}
