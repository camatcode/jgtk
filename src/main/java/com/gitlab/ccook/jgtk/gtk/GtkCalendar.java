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

import com.gitlab.ccook.jgtk.GDateTime;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.joda.time.DateTime;

public class GtkCalendar extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkCalendarLibrary library = new GtkCalendarLibrary();

    /**
     * Creates a new calendar, with the current date being selected.
     */
    public GtkCalendar() {
        super(library.gtk_calendar_new());
    }

    public GtkCalendar(Pointer ref) {
        super(ref);
    }

    /**
     * Remove all visual markers.
     */
    public void clearVisualMarkers() {
        library.gtk_calendar_clear_marks(cReference);
    }

    /**
     * Connect a signal
     *
     * @param s       Detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to the signal
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef) {
        connect(s.getDetailedName(), fn, dataRef, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s     detailed name of signal
     * @param fn    function to invoke on signal
     * @param flags connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, GConnectFlags... flags) {
        connect(s.getDetailedName(), fn, null, flags);
    }

    /**
     * Connect a signal
     *
     * @param s  detailed name of signal
     * @param fn function to invoke on signal
     */
    public void connect(Signals s, GCallbackFunction fn) {
        connect(s.getDetailedName(), fn, Pointer.NULL, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s       detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
        connect(new GtkCallbackFunction() {
            @Override
            public GConnectFlags[] getConnectFlag() {
                return flags;
            }

            @Override
            public Pointer getDataReference() {
                return dataRef;
            }

            @Override
            public String getDetailedSignal() {
                return s.getDetailedName();
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                fn.invoke(relevantThing, relevantData);
            }
        });
    }

    /**
     * Returns whether calendar is currently showing the names of the week days.
     * <p>
     * This is the value of the GtkCalendar:show-day-names property.
     *
     * @return Whether the calendar shows day names.
     */
    public boolean doesShowDayNames() {
        return library.gtk_calendar_get_show_day_names(cReference);
    }

    /**
     * Returns whether calendar is currently showing the heading.
     * <p>
     * This is the value of the GtkCalendar:show-heading property.
     *
     * @return Whether the calendar is showing a heading.
     */
    public boolean doesShowHeading() {
        return library.gtk_calendar_get_show_heading(cReference);
    }

    /**
     * Returns whether self is showing week numbers right now.
     * <p>
     * This is the value of the GtkCalendar:show-week-numbers property.
     *
     * @return Whether the calendar is showing week numbers.
     */
    public boolean doesShowWeekNumbers() {
        return library.gtk_calendar_get_show_week_numbers(cReference);
    }

    public DateTime getDate() {
        return getGDate().toDateTime();
    }

    /**
     * Returns a GDateTime representing the shown year, month and the selected day.
     * <p>
     * The returned date is in the local time zone.
     *
     * @return The GDate representing the shown date.
     */
    public GDateTime getGDate() {
        Pointer p = library.gtk_calendar_get_date(cReference);
        return new GDateTime(p);
    }

    /**
     * Returns if the day of the calendar is already marked.
     *
     * @param dayOfMonth The day number between 1 and 31.
     * @return Whether the day is marked.
     */
    public boolean isDayMarked(int dayOfMonth) {
        if (dayOfMonth >= 1 && dayOfMonth <= 31) {
            return library.gtk_calendar_get_day_is_marked(cReference, dayOfMonth);
        }
        return false;
    }

    /**
     * Places a visual marker on a particular day.
     *
     * @param day The day number to mark between 1 and 31.
     */
    public void placeMarkerOnDay(int day) {
        if (day >= 1 && day <= 31) {
            library.gtk_calendar_mark_day(cReference, day);
        }
    }

    /**
     * Removes the visual marker from a particular day.
     */
    public void removeDayMark(int day) {
        if (day >= 1 && day <= 31) {
            library.gtk_calendar_unmark_day(cReference, day);
        }
    }

    public void selectDay(DateTime dt) {
        selectDay(new GDateTime(dt));
    }

    /**
     * Switches to date's year and month and select its day.
     *
     * @param time A GDateTime representing the day to select.
     */
    public void selectDay(GDateTime time) {
        if (time != null) {
            library.gtk_calendar_select_day(cReference, time.getCReference());
        }
    }

    /**
     * Sets whether the calendar shows day names.
     *
     * @param shouldShow Whether to show day names above the day numbers.
     */
    public void shouldShowDayNames(boolean shouldShow) {
        library.gtk_calendar_set_show_day_names(cReference, shouldShow);
    }

    /**
     * Sets whether the calendar should show a heading.
     * <p>
     * The heading contains the current year and month as well as buttons for changing both.
     *
     * @param shouldShow Whether to show the heading in the calendar.
     */
    public void shouldShowHeading(boolean shouldShow) {
        library.gtk_calendar_set_show_heading(cReference, shouldShow);
    }

    /**
     * Sets whether week numbers are shown in the calendar.
     *
     * @param shouldShow Whether to show week numbers on the left of the days.
     */
    public void shouldShowWeekNumbers(boolean shouldShow) {
        library.gtk_calendar_set_show_week_numbers(cReference, shouldShow);
    }

    public static final class Signals extends GtkWidget.Signals {
        /**
         * Emitted when the user selects a day.
         */
        public static final Signals DAY_SELECTED = new Signals("day-selected");
        /**
         * Emitted when the user switched to the next month.
         */
        public static final Signals NEXT_MONTH = new Signals("next-month");
        /**
         * Emitted when user switched to the next year.
         */
        public static final Signals NEXT_YEAR = new Signals("next-year");
        /**
         * Emitted when the user switched to the previous month.
         */
        public static final Signals PREVIOUS_MONTH = new Signals("prev-month");

        /**
         * Emitted when user switched to the previous year.
         */
        public static final Signals PREVIOUS_YEAR = new Signals("prev-year");


        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkCalendarLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }


        /**
         * Remove all visual markers.
         *
         * @param calendar self
         */
        public native void gtk_calendar_clear_marks(Pointer calendar);

        /**
         * Returns a GDateTime representing the shown year, month and the selected day.
         * <p>
         * The returned date is in the local time zone.
         *
         * @param self self
         * @return The GDate representing the shown date.
         */
        public native Pointer gtk_calendar_get_date(Pointer self);

        /**
         * Returns if the day of the calendar is already marked.
         *
         * @param calendar self
         * @param day      The day number between 1 and 31.
         * @return Whether the day is marked.
         */
        public native boolean gtk_calendar_get_day_is_marked(Pointer calendar, int day);

        /**
         * Returns whether self is currently showing the names of the week days.
         * <p>
         * This is the value of the GtkCalendar:show-day-names property.
         *
         * @param self self
         * @return Whether the calendar shows day names.
         */
        public native boolean gtk_calendar_get_show_day_names(Pointer self);

        /**
         * Returns whether self is currently showing the heading.
         * <p>
         * This is the value of the GtkCalendar:show-heading property.
         *
         * @param self self
         * @return Whether the calendar is showing a heading.
         */
        public native boolean gtk_calendar_get_show_heading(Pointer self);

        /**
         * Returns whether self is showing week numbers right now.
         * <p>
         * This is the value of the GtkCalendar:show-week-numbers property.
         *
         * @param self self
         * @return Whether the calendar is showing week numbers.
         */
        public native boolean gtk_calendar_get_show_week_numbers(Pointer self);

        /**
         * Places a visual marker on a particular day of the current month.
         *
         * @param calendar self
         * @param day      The day number to mark between 1 and 31.
         */
        public native void gtk_calendar_mark_day(Pointer calendar, int day);

        /**
         * Creates a new calendar, with the current date being selected.
         *
         * @return A newly GtkCalendar widget. Type:GtkCalendar
         */
        public native Pointer gtk_calendar_new();

        /**
         * Switches to date's year and month and select its day.
         *
         * @param self self
         * @param date A GDateTime representing the day to select. Type: GDateTime
         */
        public native void gtk_calendar_select_day(Pointer self, Pointer date);

        public native void gtk_calendar_set_show_day_names(Pointer cReference, boolean shouldShow);

        /**
         * Sets whether the calendar should show a heading.
         * <p>
         * The heading contains the current year and month as well as buttons for changing both.
         *
         * @param self  self
         * @param value Whether to show the heading in the calendar.
         */
        public native void gtk_calendar_set_show_heading(Pointer self, boolean value);

        /**
         * Sets whether week numbers are shown in the calendar.
         *
         * @param self  self
         * @param value Whether to show week numbers on the left of the days.
         */
        public native void gtk_calendar_set_show_week_numbers(Pointer self, boolean value);

        /**
         * Removes the visual marker from a particular day.
         *
         * @param calendar self
         * @param day      The day number to unmark between 1 and 31.
         */
        public native void gtk_calendar_unmark_day(Pointer calendar, int day);

    }


}
