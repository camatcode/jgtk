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

import com.gitlab.ccook.jgtk.GtkAdjustment;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("CommentedOutCode")
public class GtkRangeTest {

    public static void testRange(GtkRange r) {
        assertTrue(r.doesRespectTextDirection());
        r.shouldRespectTextDirection(false);
        assertFalse(r.doesRespectTextDirection());

        assertFalse(r.doesShowFillLevel());
        r.shouldShowFillLevel(true);
        assertTrue(r.doesShowFillLevel());

        assertTrue(r.getAdjustment().isDefined());

        GtkAdjustment adjustment = new GtkAdjustment(50.0, 1, 150, 1, 1, 1);
        r.setAdjustment(adjustment);
        assertTrue(r.getAdjustment().isDefined());
        assertEquals(r.getAdjustment().get(), adjustment);

        r.setFillLevel(100);
        assertEquals(r.getFillLevel(), 100);

        // assertEquals(r.getRangeRectangle(), new GdkRectangle(-1, -1, 2, 2));
        //assertEquals(r.getSliderRange(), new Pair<>(-1, 1));

        assertFalse(r.isInverted());
        r.setInverted(true);
        assertTrue(r.isInverted());

        assertTrue(r.isRangeRestricted());
        r.setRangeRestricted(false);
        assertFalse(r.isRangeRestricted());

        assertTrue(r.isSizeFixed());
        r.setSizeFixed(false);
        assertFalse(r.isSizeFixed());

        r.setAllowableRange(0, 175);
        r.setIncrements(1, 1);

        AtomicBoolean valueChanged = new AtomicBoolean(false);
        r.connect(GtkRange.Signals.VALUE_CHANGED, (relevantThing, relevantData) -> valueChanged.set(true));
        r.setValue(51);
        assertTrue(valueChanged.get());

    }
}
