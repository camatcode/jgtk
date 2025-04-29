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
import com.gitlab.ccook.jgtk.enums.GtkBaselinePosition;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkCenterBoxTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkLabel start = new GtkLabel("start");
        GtkLabel center = new GtkLabel("center");
        GtkLabel end = new GtkLabel("end");
        GtkCenterBox box = new GtkCenterBox();
        box.setStartWidget(start);
        box.setCenterWidget(center);
        box.setEndWidget(end);
        assertEquals(box.getBaselinePosition(), GtkBaselinePosition.GTK_BASELINE_POSITION_CENTER);
        box.setBaselinePosition(GtkBaselinePosition.GTK_BASELINE_POSITION_TOP);
        assertEquals(box.getBaselinePosition(), GtkBaselinePosition.GTK_BASELINE_POSITION_TOP);
        assertTrue(box.getStartWidget().isDefined());
        assertTrue(box.getCenterWidget().isDefined());
        assertTrue(box.getEndWidget().isDefined());
        assertEquals(box.getStartWidget().get(), start);
        assertEquals(box.getCenterWidget().get(), center);
        assertEquals(box.getEndWidget().get(), end);
        w.setChild(box);
        w.show();
        gtkApplication.quit();
    }
}
