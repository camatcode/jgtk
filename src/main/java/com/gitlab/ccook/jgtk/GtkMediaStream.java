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
package com.gitlab.ccook.jgtk;

import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public class GtkMediaStream extends JGTKObject implements GdkPaintable {
    public GtkMediaStream(Pointer cReference) {
        super(cReference);
    }

    /**
     * Sets whether the stream should loop.
     * <p>
     * In this case, it will attempt to restart playback from the beginning instead of stopping at the end.
     * <p>
     * Not all streams may support looping, in particular non-seekable streams. Those streams will ignore the loop
     * setting and just end.
     *
     * @param canLoop TRUE if the stream should loop.
     */
    public void canLoop(boolean canLoop) {
        library.gtk_media_stream_set_loop(getCReference(), canLoop);
    }

    /**
     * Returns whether the stream is set to loop.
     *
     * @return TRUE if the stream should loop.
     */
    public boolean doesLoop() {
        return library.gtk_media_stream_get_loop(getCReference());
    }

    /**
     * Sets self into an error state.
     * <p>
     * This will pause the stream (you can check for an error via gtk_media_stream_get_error() in your
     * GtkMediaStream.pause() implementation), abort pending seeks and mark the stream as prepared.
     * <p>
     * if the stream is already in an error state, this call will be ignored and the existing error will be retained.
     * <p>
     * To unset an error, the stream must be reset via a call to gtk_media_stream_unprepared().
     *
     * @param e The GError to set.
     */
    public void error(GError e) {
        if (e != null) {
            library.gtk_media_stream_gerror(getCReference(), e.getCReference());
        }
    }

    /**
     * Ends a seek operation started via GtkMediaStream.seek().
     * <p>
     * This function will unset the GtkMediaStream:ended property if it was set.
     * <p>
     *
     * @param success True to label the seek a success, false to label the seek a failure
     */
    public void forceSeekDone(boolean success) {
        if (!success) {
            library.gtk_media_stream_seek_failed(getCReference());
        } else {
            library.gtk_media_stream_seek_success(getCReference());
        }
    }

    /**
     * Gets the duration of the stream.
     * <p>
     * If the duration is not known, NONE will be returned.
     *
     * @return The duration of the stream or NONE if not known.
     */
    public Option<Long> getDuration() {
        long duration = library.gtk_media_stream_get_duration(getCReference());
        if (duration > 0) {
            return new Option<>(duration);
        }
        return Option.NONE;
    }

    /**
     * If the stream is in an error state, returns the GError explaining that state.
     * <p>
     * Any type of error can be reported here depending on the implementation of the media stream.
     * <p>
     * A media stream in an error cannot be operated on, calls like gtk_media_stream_play() or gtk_media_stream_seek()
     * will not have any effect.
     * <p>
     * GtkMediaStream itself does not provide a way to unset an error, but implementations may provide options.
     * For example, a GtkMediaFile will unset errors when a new source is set, e.g. with gtk_media_file_set_file().
     *
     * @return he GError of the stream, if defined
     */
    public Option<GError> getError() {
        Option<GError.GErrorStruct.ByReference> p = new Option<>(library.gtk_media_stream_get_error(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GError(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Returns the current presentation timestamp in microseconds.
     *
     * @return the current presentation timestamp in microseconds.
     */
    public long getPresentationTimestampMicroseconds() {
        return library.gtk_media_stream_get_timestamp(getCReference());
    }

    /**
     * Returns the volume of the audio for the stream.
     * <p>
     * See gtk_media_stream_set_volume() for details.
     *
     * @return Volume of the stream from 0.0 to 1.0
     */
    public double getVolume() {
        return library.gtk_media_stream_get_volume(getCReference());
    }

    /**
     * Sets the volume of the audio stream.
     * <p>
     * This function call will work even if the stream is muted.
     * <p>
     * The given volume should range from 0.0 for silence to 1.0 for as loud as possible. Values outside this range
     * will be clamped to the nearest value.
     * <p>
     * If the stream has no audio or is muted, calling this function will still work; however, it will not have an
     * immediate
     * audible effect. When the stream is not muted, the new volume setting will take effect.
     *
     * @param volume New volume of the stream from 0.0 to 1.0
     */
    public void setVolume(double volume) {
        volume = Math.min(Math.max(0, volume), 1.0);
        library.gtk_media_stream_set_volume(getCReference(), volume);
    }

    /**
     * Returns whether the stream has audio.
     *
     * @return TRUE if the stream has audio.
     */
    public boolean hasAudio() {
        return library.gtk_media_stream_has_audio(getCReference());
    }

    /**
     * Returns whether the streams playback is finished.
     *
     * @return TRUE if playback is finished.
     */
    public boolean hasPlaybackFinished() {
        return library.gtk_media_stream_get_ended(getCReference());
    }

    /**
     * Returns whether the stream has video.
     *
     * @return TRUE if the stream has video.
     */
    public boolean hasVideo() {
        return library.gtk_media_stream_has_video(getCReference());
    }

    /**
     * Returns whether the stream has finished initializing.
     * <p>
     * At this point the existence of audio and video is known.
     *
     * @return TRUE if the stream is prepared.
     */
    public boolean isInitialized() {
        return library.gtk_media_stream_is_prepared(getCReference());
    }

    /**
     * Returns whether the audio for the stream is muted.
     * <p>
     * See gtk_media_stream_set_muted() for details.
     *
     * @return TRUE if the stream is muted.
     */
    public boolean isMuted() {
        return library.gtk_media_stream_get_muted(getCReference());
    }

    /**
     * Return whether the stream is currently playing.
     *
     * @return TRUE if the stream is playing.
     */
    public boolean isPlaying() {
        return library.gtk_media_stream_get_playing(getCReference());
    }

    /**
     * Starts or pauses playback of the stream.
     *
     * @param isPlaying Whether to start (TRUE) or pause (FALSE) playback.
     */
    public void setPlaying(boolean isPlaying) {
        library.gtk_media_stream_set_playing(getCReference(), isPlaying);
    }

    /**
     * Checks if a stream may be seekable.
     * <p>
     * This is meant to be a hint. Streams may not allow seeking even if this function returns TRUE. However, if this
     * function returns FALSE, streams are guaranteed to not be seekable and user interfaces may hide controls that
     * allow seeking.
     * <p>
     * It is allowed to call gtk_media_stream_seek() on a non-seekable stream, though it will not do anything.
     *
     * @return TRUE if the stream may support seeking.
     */
    public boolean isSeekable() {
        return library.gtk_media_stream_is_seekable(getCReference());
    }

    /**
     * Pauses the media stream and marks it as ended.
     * <p>
     * This is a hint only, calls to gtk_media_stream_play() may still happen.
     * <p>
     * The media stream must be prepared when this function is called.
     * <p>
     * Available since: 4.4
     */
    public void markStreamEnded() {
        library.gtk_media_stream_stream_ended(getCReference());
    }

    /**
     * Pauses playback of the stream.
     * <p>
     * If the stream is not playing, do nothing.
     */
    public void pause() {
        library.gtk_media_stream_pause(getCReference());
    }

    /**
     * Starts playing the stream.
     * <p>
     * If the stream is in error or already playing, do nothing.
     */
    public void play() {
        library.gtk_media_stream_play(getCReference());
    }

    /**
     * Called by GtkMediaStream implementations to advertise the stream being ready to play and providing details about
     * the stream.
     * <p>
     * Note that the arguments are hints. If the stream implementation cannot determine the correct values, it is better
     * to err on the side of caution and return TRUE. User interfaces will use those values to determine what controls
     * to show.
     * <p>
     * This function may not be called again until the stream has been reset via gtk_media_stream_stream_unprepared().
     * <p>
     * Available since: 4.4
     *
     * @param hasAudio   TRUE if the stream should advertise audio support.
     * @param hasVideo   TRUE if the stream should advertise video support.
     * @param isSeekable TRUE if the stream should advertise seek-ability.
     * @param duration   The duration of the stream or 0 if unknown.
     */
    public void prepare(boolean hasAudio, boolean hasVideo, boolean isSeekable, long duration) {
        library.gtk_media_stream_stream_prepared(getCReference(), hasAudio, hasVideo, isSeekable, duration);
    }

    /**
     * Called by GtkMediaStream implementations to advertise the stream being ready to play and providing details about
     * the stream.
     * <p>
     * Note that the arguments are hints. If the stream implementation cannot determine the correct values, it is better
     * to err on the side of caution and return TRUE. User interfaces will use those values to determine what controls
     * to show.
     * <p>
     * This function may not be called again until the stream has been reset via gtk_media_stream_stream_unprepared().
     * <p>
     * Available since: 4.4
     * <p>
     * This helper function assumes a duration of 0. If the duration is known, provide it to the other prepare function
     *
     * @param hasAudio   TRUE if the stream should advertise audio support.
     * @param hasVideo   TRUE if the stream should advertise video support.
     * @param isSeekable TRUE if the stream should advertise seek-ability.
     */
    public void prepare(boolean hasAudio, boolean hasVideo, boolean isSeekable) {
        library.gtk_media_stream_stream_prepared(getCReference(), hasAudio, hasVideo, isSeekable, 0);
    }

    /**
     * Called by users to attach the media stream to a GdkSurface they manage.
     * <p>
     * The stream can then access the resources of surface for its rendering purposes. In particular, media streams
     * might want to create a GdkGLContext or sync to the GdkFrameClock.
     * <p>
     * Whoever calls this function is responsible for calling gtk_media_stream_unrealize() before either the stream or
     * surface get destroyed.
     * <p>
     * Multiple calls to this function may happen from different users of the video, even with the same surface.
     * Each of these calls must be followed by its own call to gtk_media_stream_unrealize().
     * <p>
     * It is not required to call this function to make a media stream work.
     *
     * @param surfaceToAttach A GdkSurface
     */
    public void realize(GdkSurface surfaceToAttach) {
        if (surfaceToAttach != null) {
            library.gtk_media_stream_realize(getCReference(), surfaceToAttach.getCReference());
        }
    }

    /**
     * Resets a given media stream implementation.
     * <p>
     * gtk_media_stream_stream_prepared() can then be called again.
     * <p>
     * This function will also reset any error state the stream was in.
     * <p>
     * Available since: 4.4
     */
    public void resetAll() {
        library.gtk_media_stream_stream_unprepared(getCReference());
    }

    /**
     * Seek that blocks until done seeking
     *
     * @param timestamp Timestamp to seek to.
     * @throws InterruptedException If the thread containing this seek is interrupted
     */
    public void seekBlocking(long timestamp) throws InterruptedException {
        seek(timestamp);
        while (isSeeking()) {
            //noinspection BusyWait
            Thread.sleep(2);
        }
    }

    /**
     * Start a seek operation on self to timestamp.
     * <p>
     * If timestamp is out of range, it will be clamped.
     * <p>
     * Seek operations may not finish instantly. While a seek operation is in process, the GtkMediaStream:seeking
     * property will be set.
     * <p>
     * When calling gtk_media_stream_seek() during an ongoing seek operation, the new seek will override any
     * pending seek.
     *
     * @param timestamp Timestamp to seek to.
     */
    public void seek(long timestamp) {
        if (timestamp > 0) {
            library.gtk_media_stream_seek(getCReference(), timestamp);
        }
    }

    /**
     * Checks if there is currently a seek operation going on.
     *
     * @return TRUE if a seek operation is ongoing.
     */
    public boolean isSeeking() {
        return library.gtk_media_stream_is_seeking(getCReference());
    }

    /**
     * Sets whether the audio stream should be muted.
     * <p>
     * Muting a stream will cause no audio to be played, but it does not modify the volume. This means that muting and
     * then removing mute from the stream will restore the volume settings.
     * <p>
     * If the stream has no audio, calling this function will still work, but it will not have an audible effect.
     *
     * @param isMuted TRUE if the stream should be muted.
     */
    public void setMute(boolean isMuted) {
        library.gtk_media_stream_set_muted(getCReference(), isMuted);
    }

    /**
     * Undoes a previous call to gtk_media_stream_realize().
     * <p>
     * This causes the stream to release all resources it had allocated from surface.
     *
     * @param surfaceToRelease The GdkSurface the stream was realized with.
     */
    public void unrealize(GdkSurface surfaceToRelease) {
        if (surfaceToRelease != null) {
            library.gtk_media_stream_unrealize(getCReference(), surfaceToRelease.getCReference());
        }
    }

    /**
     * Media stream implementations should regularly call this function to update the timestamp reported by the stream.
     * <p>
     * It is up to implementations to call this at the frequency they deem appropriate.
     * <p>
     * The media stream must be prepared when this function is called.
     *
     * @param newTimestamp The new timestamp.
     */
    public void updateTimestamp(long newTimestamp) {
        if (newTimestamp >= 0) {
            library.gtk_media_stream_update(getCReference(), newTimestamp);
        }
    }
}
