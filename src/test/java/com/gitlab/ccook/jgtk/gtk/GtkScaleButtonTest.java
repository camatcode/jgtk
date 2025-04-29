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
import com.gitlab.ccook.jgtk.IconName;


import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkScaleButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        List<IconName> randomIcons = getRandomIcons();
        GtkScaleButton b = new GtkScaleButton(0, 100, 2, randomIcons.get(7), randomIcons.get(3));

        GtkAdjustment adjustment = b.getAdjustment();
        assertEquals(adjustment.getLower(), 0);
        assertEquals(adjustment.getUpper(), 100);
        assertEquals(adjustment.getStepIncrement(), 2);

        adjustment = new GtkAdjustment(5, 10, 150, 3, 1, 1);
        b.setAdjustment(adjustment);
        adjustment = b.getAdjustment();
        assertEquals(adjustment.getLower(), 10);
        assertEquals(adjustment.getUpper(), 150);
        assertEquals(adjustment.getStepIncrement(), 3);

        GtkButton minusButton = b.getMinusButton();
        minusButton.setLabel("Minus");
        GtkButton plusButton = b.getPlusButton();
        assertEquals(plusButton.getIconName().get().getIconName(), "list-add-symbolic");

        assertEquals(b.getValue(), 10);
        AtomicBoolean valueChanged = new AtomicBoolean(false);
        b.connect(GtkScaleButton.Signals.VALUE_CHANGED, (relevantThing, relevantData) -> valueChanged.set(true));
        b.setValue(40);
        assertEquals(b.getValue(), 40);
        assertTrue(valueChanged.get());
        w.setChild(b);
        w.show();
        gtkApplication.quit();
    }
}
