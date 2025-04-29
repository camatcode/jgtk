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
import com.gitlab.ccook.jgtk.GdkRectangle;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.util.Pair;


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkPopoverTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 5);
        GtkButton dismissButton = new GtkButton("Dismiss");
        GtkButton showButton = new GtkButton("Show");
        box.append(dismissButton);
        box.append(showButton);
        GtkPopover popover = new GtkPopover();
        box.append(popover);

        assertFalse(popover.areMnemonicsVisible());
        popover.setMnemonicsVisible(true);
        assertTrue(popover.areMnemonicsVisible());

        assertFalse(popover.doesDismissWithChild());
        popover.shouldDismissWithChild(true);
        assertTrue(popover.doesDismissWithChild());

        assertTrue(popover.doesShowArrow());
        popover.shouldShowArrow(false);
        assertFalse(popover.doesShowArrow());

        assertFalse(popover.getChild().isDefined());
        GtkLabel l = new GtkLabel("Child");
        popover.setChild(l);
        assertTrue(popover.getChild().isDefined());
        assertEquals(popover.getChild().get(), l);

        assertTrue(popover.getOffsetPosition().isDefined());
        assertEquals(popover.getOffsetPosition().get(), new Pair<>(0, 0));
        popover.setOffset(4, 5);
        assertTrue(popover.getOffsetPosition().isDefined());
        assertEquals(popover.getOffsetPosition().get(), new Pair<>(4, 5));

        assertFalse(popover.getPointingTo().isDefined());
        popover.setPointingTo(new GdkRectangle(4, 5, 2, 3));
        assertTrue(popover.getPointingTo().isDefined());
        assertEquals(popover.getPointingTo().get(), new GdkRectangle(4, 5, 2, 3));

        assertEquals(popover.getPreferredPosition(), GtkPositionType.GTK_POS_BOTTOM);
        popover.setPreferredPosition(GtkPositionType.GTK_POS_RIGHT);
        assertEquals(popover.getPreferredPosition(), GtkPositionType.GTK_POS_RIGHT);

        assertTrue(popover.isModal());
        popover.setModal(false);
        assertFalse(popover.isModal());

        AtomicBoolean didPopDown = new AtomicBoolean(false);
        AtomicBoolean didPopUp = new AtomicBoolean(false);
        dismissButton.connect(GtkButton.Signals.CLICKED, (relevantThing, relevantData) -> {
            popover.popDown();
            didPopDown.set(true);
        });

        showButton.connect(GtkButton.Signals.CLICKED, (relevantThing, relevantData) -> {
            popover.popUp();
            didPopUp.set(true);
        });

        w.setChild(box);
        w.show();

        dismissButton.emitSignal(GtkButton.Signals.CLICKED);
        showButton.emitSignal(GtkButton.Signals.CLICKED);
        assertTrue(didPopUp.get());
        assertTrue(didPopDown.get());
        popover.present();

        assertFalse(popover.getDefaultWidget().isDefined());
        popover.setDefaultWidget(showButton);
        assertTrue(popover.getDefaultWidget().isDefined());
        assertEquals(popover.getDefaultWidget().get(), showButton);
        gtkApplication.quit();
    }
}
