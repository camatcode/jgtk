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
import com.gitlab.ccook.jgtk.enums.PangoEllipsizeMode;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkProgressBar is typically used to display the progress of a long-running operation.
 * <p>
 * It provides a visual clue that processing is underway. GtkProgressBar can be used in two different modes: percentage
 * mode and activity mode.
 * <p>
 * When an application can determine how much work needs to take place (e.g. read a fixed number of bytes from a file)
 * and can monitor its progress, it can use the GtkProgressBar in percentage mode and the user sees a growing bar
 * indicating the percentage of the work that has been completed. In this mode, the application is required to call
 * gtk_progress_bar_set_fraction() periodically to update the progress bar.
 * <p>
 * When an application has no accurate way of knowing the amount of work to do, it can use the GtkProgressBar in
 * activity mode, which shows activity by a block moving back and forth within the progress area. In this mode,
 * the application is required to call gtk_progress_bar_pulse() periodically to update the progress bar.
 * <p>
 * There is quite a bit of flexibility provided to control the appearance of the GtkProgressBar. Functions are
 * provided to control the orientation of the bar, optional text can be displayed along with the bar, and the step
 * size used in activity mode can be set.
 */
public class GtkProgressBar extends GtkWidget implements GtkAccessible, GtkAccessibleRange, GtkBuildable, GtkConstraintTarget, GtkOrientable {
    private static final GtkProgressBarLibrary library = new GtkProgressBarLibrary();

    public GtkProgressBar(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkProgressBar.
     */
    public GtkProgressBar() {
        super(library.gtk_progress_bar_new());
    }

    /**
     * Returns whether the GtkProgressBar shows text.
     * <p>
     * See gtk_progress_bar_set_show_text().
     *
     * @return TRUE if text is shown in the progress bar.
     */
    public boolean doesShowText() {
        return library.gtk_progress_bar_get_show_text(getCReference());
    }

    /**
     * Returns the ellipsizing (that is, when the text cuts of with a ...) position of the progress bar.
     *
     * @return PangoEllipsizeMode
     */
    public PangoEllipsizeMode getEllipsizeMode() {
        return PangoEllipsizeMode.getModeFromCValue(library.gtk_progress_bar_get_ellipsize(getCReference()));
    }

    /**
     * Sets the mode used to ellipsize the text.
     * <p>
     * The text is ellipsized if there is not enough space to render the entire string.
     *
     * @param m A PangoEllipsizeMode
     */
    public void setEllipsizeMode(PangoEllipsizeMode m) {
        if (m != null) {
            library.gtk_progress_bar_set_ellipsize(getCReference(), PangoEllipsizeMode.getCValueFromMode(m));
        }
    }

    /**
     * Returns the current fraction of the task that's been completed.
     *
     * @return A fraction from 0.0 to 1.0
     */
    public double getProgress() {
        return library.gtk_progress_bar_get_fraction(getCReference());
    }

    /**
     * Causes the progress bar to "fill in" the given fraction of the bar.
     * <p>
     * The fraction should be between 0.0 and 1.0, inclusive.
     *
     * @param amount Fraction of the task that's been completed.
     */
    public void setProgress(double amount) {
        amount = Math.max(0, amount);
        amount = Math.min(1, amount);
        library.gtk_progress_bar_set_fraction(getCReference(), amount);
    }

    /**
     * Retrieves the pulse step.
     * <p>
     * See gtk_progress_bar_set_pulse_step().
     *
     * @return A fraction from 0.0 to 1.0
     */
    public double getProgressStep() {
        return library.gtk_progress_bar_get_pulse_step(getCReference());
    }

    /**
     * Sets the fraction of total progress bar length to move the bouncing block.
     * <p>
     * The bouncing block is moved when gtk_progress_bar_pulse() is called.
     *
     * @param step Fraction between 0.0 and 1.0
     */
    public void setProgressStep(double step) {
        step = Math.max(0, step);
        step = Math.min(1, step);
        library.gtk_progress_bar_set_pulse_step(getCReference(), step);
    }

    /**
     * Retrieves the text that is displayed with the progress bar.
     * <p>
     * The return value is a reference to the text, not a copy of it, so will become invalid if you change the text in
     * the progress bar.
     *
     * @return The text, if defined
     */
    public Option<String> getText() {
        return new Option<>(library.gtk_progress_bar_get_text(getCReference()));
    }

    /**
     * Causes the given text to appear next to the progress bar.
     * <p>
     * If text is NULL and GtkProgressBar:show-text is TRUE, the current value of GtkProgressBar:fraction will be
     * displayed as a percentage.
     * <p>
     * If text is non-NULL and GtkProgressBar:show-text is TRUE, the text will be displayed. In this case, it will not
     * display the progress percentage. If text is the empty string, the progress bar will still be styled and sized
     * suitably for containing text, as long as GtkProgressBar:show-text is TRUE.
     *
     * @param text A UTF-8 string.
     *             <p>
     *             The argument can be NULL.
     */
    public void setText(String text) {
        library.gtk_progress_bar_set_text(getCReference(), text);
    }

    /**
     * Returns whether the progress bar is inverted.
     *
     * @return TRUE if the progress bar is inverted.
     */
    public boolean isInverted() {
        return library.gtk_progress_bar_get_inverted(getCReference());
    }

    /**
     * Sets whether the progress bar is inverted.
     * <p>
     * Progress bars normally grow from top to bottom or left to right. Inverted progress bars grow in the opposite
     * direction.
     *
     * @param isInverted TRUE to invert the progress bar.
     */
    public void setInverted(boolean isInverted) {
        library.gtk_progress_bar_set_inverted(getCReference(), isInverted);
    }

    /**
     * Indicates that some progress has been made, but you don't know how much.
     * <p>
     * Causes the progress bar to enter "activity mode," where a block bounces back and forth. Each call to
     * gtk_progress_bar_pulse() causes the block to move by a little (the amount of movement per pulse is
     * determined by gtk_progress_bar_set_pulse_step()).
     */
    public void progress() {
        library.gtk_progress_bar_pulse(getCReference());
    }

    /**
     * Sets whether the progress bar will show text next to the bar.
     * <p>
     * The shown text is either the value of the GtkProgressBar:text property or, if that is NULL, the
     * GtkProgressBar:fraction value, as a percentage.
     * <p>
     * To make a progress bar that is styled and sized suitably for containing text (even if the actual text is blank),
     * set GtkProgressBar:show-text to TRUE and GtkProgressBar:text to the empty string (not NULL).
     *
     * @param doesShowText Whether to show text.
     */
    public void shouldShowText(boolean doesShowText) {
        library.gtk_progress_bar_set_show_text(getCReference(), doesShowText);
    }

    protected static class GtkProgressBarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns the ellipsizing position of the progress bar.
         * <p>
         * See gtk_progress_bar_set_ellipsize().
         *
         * @param pbar self
         * @return PangoEllipsizeMode
         */
        public native int gtk_progress_bar_get_ellipsize(Pointer pbar);

        /**
         * Returns the current fraction of the task that's been completed.
         *
         * @param pbar self
         * @return A fraction from 0.0 to 1.0
         */
        public native double gtk_progress_bar_get_fraction(Pointer pbar);

        /**
         * Returns whether the progress bar is inverted.
         *
         * @param pbar self
         * @return TRUE if the progress bar is inverted.
         */
        public native boolean gtk_progress_bar_get_inverted(Pointer pbar);

        /**
         * Retrieves the pulse step.
         * <p>
         * See gtk_progress_bar_set_pulse_step().
         *
         * @param pbar self
         * @return A fraction from 0.0 to 1.0
         */
        public native double gtk_progress_bar_get_pulse_step(Pointer pbar);

        /**
         * Returns whether the GtkProgressBar shows text.
         * <p>
         * See gtk_progress_bar_set_show_text().
         *
         * @param pbar self
         * @return TRUE if text is shown in the progress bar.
         */
        public native boolean gtk_progress_bar_get_show_text(Pointer pbar);

        /**
         * Retrieves the text that is displayed with the progress bar.
         * <p>
         * The return value is a reference to the text, not a copy of it, so will become invalid if you change the text
         * in the progress bar.
         *
         * @param pbar self
         * @return The text.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_progress_bar_get_text(Pointer pbar);

        /**
         * Creates a new GtkProgressBar.
         *
         * @return A GtkProgressBar.
         */
        public native Pointer gtk_progress_bar_new();

        /**
         * Indicates that some progress has been made, but you don't know how much.
         * <p>
         * Causes the progress bar to enter "activity mode," where a block bounces back and forth. Each call to
         * gtk_progress_bar_pulse() causes the block to move by a little (the amount of movement per pulse is
         * determined by gtk_progress_bar_set_pulse_step()).
         *
         * @param pbar self
         */
        public native void gtk_progress_bar_pulse(Pointer pbar);

        /**
         * Sets the mode used to ellipsize the text.
         * <p>
         * The text is ellipsized if there is not enough space to render the entire string.
         *
         * @param pbar self
         * @param mode A PangoEllipsizeMode
         */
        public native void gtk_progress_bar_set_ellipsize(Pointer pbar, int mode);

        /**
         * Causes the progress bar to "fill in" the given fraction of the bar.
         * <p>
         * The fraction should be between 0.0 and 1.0, inclusive.
         *
         * @param pbar     self
         * @param fraction Fraction of the task that's been completed.
         */
        public native void gtk_progress_bar_set_fraction(Pointer pbar, double fraction);

        /**
         * Sets whether the progress bar is inverted.
         * <p>
         * Progress bars normally grow from top to bottom or left to right. Inverted progress bars grow in the opposite
         * direction.
         *
         * @param pbar     self
         * @param inverted TRUE to invert the progress bar.
         */
        public native void gtk_progress_bar_set_inverted(Pointer pbar, boolean inverted);

        /**
         * Sets the fraction of total progress bar length to move the bouncing block.
         * <p>
         * The bouncing block is moved when gtk_progress_bar_pulse() is called.
         *
         * @param pbar     self
         * @param fraction Fraction between 0.0 and 1.0
         */
        public native void gtk_progress_bar_set_pulse_step(Pointer pbar, double fraction);

        /**
         * Sets whether the progress bar will show text next to the bar.
         * <p>
         * The shown text is either the value of the GtkProgressBar:text property or, if that is NULL, the
         * GtkProgressBar:fraction value, as a percentage.
         * <p>
         * To make a progress bar that is styled and sized suitably for containing text (even if the actual text is
         * blank), set GtkProgressBar:show-text to TRUE and GtkProgressBar:text to the empty string (not NULL).
         *
         * @param pbar      self
         * @param show_text Whether to show text.
         */
        public native void gtk_progress_bar_set_show_text(Pointer pbar, boolean show_text);

        /**
         * Causes the given text to appear next to the progress bar.
         * <p>
         * If text is NULL and GtkProgressBar:show-text is TRUE, the current value of GtkProgressBar:fraction will be
         * displayed as a percentage.
         * <p>
         * If text is non-NULL and GtkProgressBar:show-text is TRUE, the text will be displayed. In this case, it will
         * not display the progress percentage. If text is the empty string, the progress bar will still be styled and
         * sized suitably for containing text, as long as GtkProgressBar:show-text is TRUE.
         *
         * @param pbar self
         * @param text A UTF-8 string.
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_progress_bar_set_text(Pointer pbar, String text);
    }
}
