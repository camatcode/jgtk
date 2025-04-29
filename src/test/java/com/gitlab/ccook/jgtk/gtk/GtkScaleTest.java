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
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.PangoLayout;
import com.gitlab.ccook.jgtk.enums.GtkBaselinePosition;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Pointer;


import static org.junit.jupiter.api.Assertions.*;

public class GtkScaleTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        GtkScale hScale = new GtkScale(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 0, 100, 1);
        GtkScale vScale = new GtkScale(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0, 100, 1);

        GtkCenterBox box = new GtkCenterBox();
        box.setBaselinePosition(GtkBaselinePosition.GTK_BASELINE_POSITION_CENTER);
        box.setStartWidget(vScale);
        box.setCenterWidget(hScale);

        assertFalse(hScale.doesDisplayValue());
        hScale.shouldDisplayValue(true);
        assertTrue(hScale.doesDisplayValue());

        Option<PangoLayout> layout = hScale.getLayout();
        assertTrue(layout.isDefined());

        Option<Pair<Integer, Integer>> layoutOffsets = hScale.getLayoutOffsets();
        assertTrue(layoutOffsets.isDefined());
        assertEquals(layoutOffsets.get(), new Pair<>(0, 0));

        assertEquals(hScale.getSignificantDigits(), 0);
        hScale.setSignificantDigits(2);
        assertEquals(hScale.getSignificantDigits(), 2);

        assertEquals(hScale.getValuePosition(), GtkPositionType.GTK_POS_TOP);
        hScale.setValuePosition(GtkPositionType.GTK_POS_BOTTOM);
        assertEquals(hScale.getValuePosition(), GtkPositionType.GTK_POS_BOTTOM);

        assertTrue(hScale.hasOrigin());
        hScale.setHasOrigin(false);
        assertFalse(hScale.hasOrigin());
        hScale.setHasOrigin(true);
        hScale.placeMark(50, GtkPositionType.GTK_POS_BOTTOM, "Mark");
        hScale.setValue(50);
        hScale.setValueFormatFunc((scale, value, userData) -> "Value: " + value, Pointer.NULL, null);
        hScale.clearMarks();
        w.setChild(box);
        w.show();
        GtkRangeTest.testRange(hScale);
        gtkApplication.quit();
    }
}
