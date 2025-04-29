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
import com.gitlab.ccook.jgtk.enums.GtkLevelBarMode;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkLevelBarTest extends JGTKJUnitTest {

    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkLevelBar b = new GtkLevelBar();
        b.setMaxValue(1);
        b.setMinValue(0);
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 5);
        GtkLabel l = new GtkLabel("Level Bar");
        box.append(b);
        box.append(l);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(box);
        gtkApplication.addWindow(w);
        w.show();

        b.addOffsetValue("Half", 0.5);

        assertEquals(b.getMaxValue(), 1.0);
        assertEquals(b.getMinValue(), 0.0);

        assertEquals(b.getMode(), GtkLevelBarMode.GTK_LEVEL_BAR_MODE_CONTINUOUS);
        b.setMode(GtkLevelBarMode.GTK_LEVEL_BAR_MODE_DISCRETE);
        assertEquals(b.getMode(), GtkLevelBarMode.GTK_LEVEL_BAR_MODE_DISCRETE);
        b.setMode(GtkLevelBarMode.GTK_LEVEL_BAR_MODE_CONTINUOUS);

        assertTrue(b.getOffsetValue("Half").isDefined());
        assertEquals(b.getOffsetValue("Half").get(), 0.5);

        assertEquals(b.getValue(), 0.0);
        b.setValue(0.5);
        assertEquals(b.getValue(), 0.5);

        assertFalse(b.isInverted());
        b.setInverted(true);
        assertTrue(b.isInverted());

        b.addOffsetValue("Three Fourths", 0.75);
        b.removeOffsetValue("Three Fourths");
        assertFalse(b.getOffsetValue("Three Fourths").isDefined());

        AtomicBoolean changed = new AtomicBoolean(false);
        b.connect(GtkLevelBar.Signals.OFFSET_CHANGED, ((relevantThing, relevantData) -> changed.set(true)));
        b.addOffsetValue("Half", 0.051);
        assertTrue(changed.get());
        gtkApplication.quit();
    }
}

