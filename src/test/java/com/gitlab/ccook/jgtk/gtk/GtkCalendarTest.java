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

import com.gitlab.ccook.JGTKJUnitTest;
import com.gitlab.ccook.jgtk.GtkApplication;

import org.joda.time.DateTime;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkCalendarTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkCalendar c = new GtkCalendar();
        w.setChild(c);
        DateTime date = c.getDate();
        assertTrue(date.isBefore(DateTime.now()));
        DateTime toSet = DateTime.now().minusMonths(2);
        c.selectDay(toSet);
        date = c.getDate();
        assertEquals(toSet.toString(), date.toString());
        assertFalse(c.isDayMarked(1));
        c.placeMarkerOnDay(1);
        assertTrue(c.isDayMarked(1));
        AtomicBoolean daySelected = new AtomicBoolean(false);
        c.connect(GtkCalendar.Signals.DAY_SELECTED, (relevantThing, relevantData) -> daySelected.set(true));
        c.selectDay(DateTime.now().minusDays(2));
        assertTrue(daySelected.get());
        AtomicBoolean nextMonth = new AtomicBoolean(false);
        c.connect(GtkCalendar.Signals.NEXT_MONTH, (relevantThing, relevantData) -> nextMonth.set(true));
        c.emitSignal(GtkCalendar.Signals.NEXT_MONTH);
        assertTrue(nextMonth.get());
        AtomicBoolean previousMonth = new AtomicBoolean(false);
        c.connect(GtkCalendar.Signals.PREVIOUS_MONTH, (relevantThing, relevantData) -> previousMonth.set(true));
        c.emitSignal(GtkCalendar.Signals.PREVIOUS_MONTH);
        assertTrue(previousMonth.get());
        AtomicBoolean nextYear = new AtomicBoolean(false);
        c.connect(GtkCalendar.Signals.NEXT_YEAR, (relevantThing, relevantData) -> nextYear.set(true));
        c.emitSignal(GtkCalendar.Signals.NEXT_YEAR);
        assertTrue(nextYear.get());
        AtomicBoolean prevYear = new AtomicBoolean(false);
        c.connect(GtkCalendar.Signals.PREVIOUS_YEAR, (relevantThing, relevantData) -> prevYear.set(true));
        c.emitSignal(GtkCalendar.Signals.PREVIOUS_YEAR);
        assertTrue(prevYear.get());
        c.removeDayMark(1);
        assertFalse(c.doesShowWeekNumbers());
        c.shouldShowWeekNumbers(true);
        assertTrue(c.doesShowWeekNumbers());
        assertTrue(c.doesShowHeading());
        c.shouldShowHeading(false);
        assertFalse(c.doesShowHeading());
        assertTrue(c.doesShowDayNames());
        c.shouldShowDayNames(false);
        assertFalse(c.doesShowDayNames());
        c.clearVisualMarkers();
        w.show();
        gtkApplication.quit();
    }
}
