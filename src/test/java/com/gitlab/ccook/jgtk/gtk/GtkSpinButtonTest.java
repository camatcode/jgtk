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

import com.gitlab.ccook.JGTKJUnitTest;
import com.gitlab.ccook.jgtk.GtkAdjustment;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.enums.GtkSpinButtonUpdatePolicy;
import com.gitlab.ccook.util.Pair;


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkSpinButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);

        GtkAdjustment adjustment = new GtkAdjustment(50.0, 0.0, 100.0, 1.0, 5.0, 0.0);
        GtkSpinButton b = new GtkSpinButton(adjustment, 1.0, 0);

        boolean doesSnap = b.doesSnapToNearestStep();
        assertFalse(doesSnap);
        b.shouldSnapToNearestStep(true);
        doesSnap = b.doesSnapToNearestStep();
        assertTrue(doesSnap);

        assertFalse(b.doesWrap());
        b.shouldWrap(true);
        assertTrue(b.doesWrap());

        b.forceUpdate();

        GtkAdjustment adjustment1 = b.getAdjustment();
        assertEquals(adjustment1, adjustment);

        adjustment = new GtkAdjustment(20.0, 0.0, 100.0, 1.0, 5.0, 0.0);
        b.setAdjustment(adjustment);
        adjustment1 = b.getAdjustment();
        assertEquals(adjustment1, adjustment);

        assertEquals(b.getClimbRate(), 1.0);
        b.setClimbRate(2.0);
        assertEquals(b.getClimbRate(), 2.0);

        Pair<Double, Double> increments = b.getIncrements();
        assertEquals(increments.getFirst(), 1.0);
        assertEquals(increments.getSecond(), 5.0);
        b.setIncrements(2.0, 6.0);
        increments = b.getIncrements();
        assertEquals(increments.getFirst(), 2.0);
        assertEquals(increments.getSecond(), 6.0);

        Pair<Double, Double> range = b.getRange();
        assertEquals(range.getFirst(), 0.0);
        assertEquals(range.getSecond(), 100.0);
        b.setRange(1, 101);
        range = b.getRange();
        assertEquals(range.getFirst(), 1.0);
        assertEquals(range.getSecond(), 101.0);

        assertEquals(0, b.getSignificantDigits());
        b.setSignificantDigits(2);
        assertEquals(2, b.getSignificantDigits());

        assertEquals(b.getUpdatePolicy(), GtkSpinButtonUpdatePolicy.GTK_UPDATE_ALWAYS);
        b.setUpdatePolicy(GtkSpinButtonUpdatePolicy.GTK_UPDATE_IF_VALID);
        assertEquals(b.getUpdatePolicy(), GtkSpinButtonUpdatePolicy.GTK_UPDATE_IF_VALID);

        assertEquals(b.getValue(), 20.0);
        b.setValue(19.0);
        assertEquals(b.getValue(), 19.0);
        assertFalse(b.isNumericOnly());
        b.setNumericOnly(true);
        assertTrue(b.isNumericOnly());

        b.reset(adjustment, 1.0, 3);
        assertEquals(b.getClimbRate(), 1.0);
        assertEquals(b.getSignificantDigits(), 3);

        AtomicBoolean signalHappened = new AtomicBoolean(false);
        b.connect(GtkSpinButton.Signals.VALUE_CHANGED, (relevantThing, relevantData) -> signalHappened.set(true));
        b.setValue(1.0);
        assertTrue(signalHappened.get());
        w.setChild(b);
        w.show();
        gtkApplication.quit();
    }
}
