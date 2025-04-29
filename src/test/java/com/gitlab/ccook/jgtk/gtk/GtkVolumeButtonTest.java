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


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class GtkVolumeButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        GtkVolumeButton b = new GtkVolumeButton();

        GtkAdjustment adjustment = b.getAdjustment();
        assertEquals(adjustment.getLower(), 0);
        assertEquals(adjustment.getUpper(), 1);
        assertEquals(adjustment.getStepIncrement(), 0.02);

        GtkButton minusButton = b.getMinusButton();
        minusButton.setLabel("Minus");
        GtkButton plusButton = b.getPlusButton();
        assertEquals(plusButton.getIconName().get().getIconName(), "list-add-symbolic");
        assertEquals(b.getValue(), 0);

        assertTrue(b.doesUseSymbolicIcons());
        b.shouldUseSymbolicIcons(false);
        assertFalse(b.doesUseSymbolicIcons());

        AtomicBoolean valueChanged = new AtomicBoolean(false);
        b.connect(GtkScaleButton.Signals.VALUE_CHANGED, (relevantThing, relevantData) -> valueChanged.set(true));
        b.setValue(0.5);
        assertEquals(b.getValue(), 0.5);
        assertTrue(valueChanged.get());
        w.setChild(b);
        w.show();
        gtkApplication.quit();
    }
}
