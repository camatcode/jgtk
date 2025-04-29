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


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkCheckButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkCheckButton button = new GtkCheckButton();
        GtkCheckButton buttonLabel = new GtkCheckButton("Label");
        GtkCheckButton buttonMn = new GtkCheckButton(new MnemonicLabel("Label"));
        buttonMn.setGroup(button);
        buttonLabel.setGroup(button);
        assertFalse(button.getLabel().isDefined());
        assertTrue(buttonLabel.getLabel().isDefined());
        assertTrue(buttonMn.getLabel().isDefined());
        assertFalse(button.isLabelMnemonic());
        assertFalse(buttonLabel.isLabelMnemonic());
        assertTrue(buttonMn.isLabelMnemonic());
        button.activate();
        assertTrue(button.isActive());
        assertFalse(buttonLabel.isActive());
        buttonLabel.activate();
        assertTrue(buttonLabel.isActive());
        buttonMn.activate();
        assertTrue(buttonMn.isActive());
        assertTrue(buttonLabel.getChild().isDefined());
        assertTrue(buttonMn.getChild().isDefined());
        assertTrue(button.getChild().isDefined()); // an empty check button has GtkBuiltinIcon as a child
        assertFalse(button.getLabel().isDefined());
        assertTrue(buttonLabel.getLabel().isDefined());
        assertTrue(buttonMn.getLabel().isDefined());
        button.setLabel("NewLabel");
        assertTrue(button.getLabel().isDefined());
        AtomicBoolean buttonToggled = new AtomicBoolean(false);
        AtomicBoolean buttonLabelToggled = new AtomicBoolean(false);
        AtomicBoolean buttonMnToggled = new AtomicBoolean(false);
        button.connect(GtkCheckButton.Signals.TOGGLED, (relevantThing, relevantData) -> buttonToggled.set(true));
        buttonLabel.connect(GtkCheckButton.Signals.TOGGLED, (relevantThing, relevantData) -> buttonLabelToggled.set(true));
        buttonMn.connect(GtkCheckButton.Signals.TOGGLED, (relevantThing, relevantData) -> buttonMnToggled.set(true));
        button.emitSignal(GtkCheckButton.Signals.TOGGLED);
        buttonLabel.emitSignal(GtkCheckButton.Signals.TOGGLED);
        buttonMn.emitSignal(GtkCheckButton.Signals.TOGGLED);
        assertTrue(buttonToggled.get());
        assertTrue(buttonLabelToggled.get());
        assertTrue(buttonMnToggled.get());
        assertFalse(button.isActive());
        assertFalse(buttonLabel.isActive());
        assertTrue(buttonMn.isActive());
        button.setActive(false);
        assertFalse(button.isActive());
        assertFalse(button.isInInconsistentState());
        assertFalse(buttonLabel.isInInconsistentState());
        assertFalse(buttonMn.isInInconsistentState());
        buttonLabel.setInconsistentState(true);
        assertTrue(buttonLabel.isInInconsistentState());
        buttonLabel.setInconsistentState(false);
        assertFalse(button.isLabelMnemonic());
        assertTrue(buttonMn.isLabelMnemonic());
        assertFalse(buttonLabel.isLabelMnemonic());
        buttonLabel.shouldUseMnemonics(true);
        assertTrue(buttonLabel.isLabelMnemonic());
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkCenterBox box = new GtkCenterBox(button, buttonLabel, buttonMn);
        w.setChild(box);
        w.show();
        gtkApplication.quit();
    }
}

