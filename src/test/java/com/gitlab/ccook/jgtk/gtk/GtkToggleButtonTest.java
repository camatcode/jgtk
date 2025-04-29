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
import com.gitlab.ccook.jgtk.MnemonicLabel;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkToggleButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        String label = "myLabel";
        MnemonicLabel label2 = new MnemonicLabel("mLabel");
        GtkToggleButton b1 = new GtkToggleButton(label);
        AtomicBoolean b1Togged = new AtomicBoolean(false);
        b1.connect(GtkToggleButton.Signals.TOGGLED, (relevantThing, relevantData) -> b1Togged.set(true));
        GtkToggleButton b2 = new GtkToggleButton(label2);
        AtomicBoolean b2Togged = new AtomicBoolean(false);
        b2.connect(GtkToggleButton.Signals.TOGGLED, (relevantThing, relevantData) -> b2Togged.set(true));
        GtkToggleButton b3 = new GtkToggleButton();
        AtomicBoolean b3Togged = new AtomicBoolean(false);
        b3.connect(GtkToggleButton.Signals.TOGGLED, (relevantThing, relevantData) -> b3Togged.set(true));
        assertFalse(b1.isPressedIn());
        assertFalse(b2.isPressedIn());
        assertFalse(b3.isPressedIn());
        assertFalse(b1Togged.get());
        assertFalse(b2Togged.get());
        assertFalse(b3Togged.get());
        b1.setPressedIn(true);
        b2.setPressedIn(true);
        b3.setPressedIn(true);
        assertTrue(b1.isPressedIn());
        assertTrue(b2.isPressedIn());
        assertTrue(b3.isPressedIn());
        assertTrue(b1Togged.get());
        assertTrue(b2Togged.get());
        assertTrue(b3Togged.get());
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0);
        box.append(b1);
        box.append(b2);
        box.append(b3);
        b2.setGroup(b1);
        b3.setGroup(b1);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(box);
        gtkApplication.addWindow(w);
        assertTrue(b1.isPressedIn());
        assertFalse(b2.isPressedIn());
        assertFalse(b3.isPressedIn());
        b2.setPressedIn(true);
        assertFalse(b1.isPressedIn());
        assertTrue(b2.isPressedIn());
        assertFalse(b3.isPressedIn());
        b3.setPressedIn(true);
        assertFalse(b1.isPressedIn());
        assertFalse(b2.isPressedIn());
        assertTrue(b3.isPressedIn());
        gtkApplication.quit();
    }
}
