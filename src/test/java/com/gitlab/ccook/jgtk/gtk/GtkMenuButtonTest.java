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
import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.enums.GtkArrowType;
import com.sun.jna.Pointer;


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkMenuButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {

        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkMediaStream stream = new GtkMediaFile(getSampleOGG());
        GtkMenuButton b = new GtkMenuButton();
        w.setChild(b);

        gtkApplication.addWindow(w);
        w.show();

        assertFalse(b.doesAlwaysShowDropdownArrow());
        b.shouldAlwaysShowDropdownArrow(true);
        assertTrue(b.doesAlwaysShowDropdownArrow());

        assertTrue(b.hasFrame());
        b.shouldHaveFrame(false);
        assertFalse(b.hasFrame());

        assertFalse(b.isMnemonic());
        b.setMnemonic(true);
        assertTrue(b.isMnemonic());

        assertFalse(b.getChild().isDefined());
        GtkEntry child = new GtkEntry("Placeholder");
        b.setChild(child);
        assertTrue(b.getChild().isDefined());
        assertEquals(b.getChild().get(), child);

        assertEquals(b.getDirection(), GtkArrowType.GTK_ARROW_DOWN);
        b.setDirection(GtkArrowType.GTK_ARROW_LEFT);
        assertEquals(b.getDirection(), GtkArrowType.GTK_ARROW_LEFT);

        IconName icon = getRandomIcons().get(2);
        assertFalse(b.getIconName().isDefined());
        b.setIconName(icon);
        assertTrue(b.getIconName().isDefined());
        assertEquals(b.getIconName().get(), icon);

        assertFalse(b.getLabel().isDefined());
        b.setLabel("Label");
        assertTrue(b.getLabel().isDefined());
        assertEquals(b.getLabel().get(), "Label");

        assertFalse(b.getMenuModel().isDefined());
        GMenuModel m = makeMainMenu();
        b.setMenuModel(m);
        assertTrue(b.getMenuModel().isDefined());
        assertEquals(b.getMenuModel().get(), m);

        assertTrue(b.getPopover().isDefined());
        AtomicBoolean createPopupRan = new AtomicBoolean(false);
        b.setCreatePopupFunc((menuButton, userData) -> createPopupRan.set(true), Pointer.NULL, null);
        GtkPopoverMenu popoverMenu = new GtkPopoverMenu(m);
        b.setPopover(popoverMenu);
        assertTrue(b.getPopover().isDefined());

        assertFalse(b.isPrimaryMenu());
        b.setPrimaryMenu(true);
        assertTrue(b.isPrimaryMenu());

        b.popupMenu();
        b.dismissMenu();
        assertTrue(createPopupRan.get());
        gtkApplication.quit();
    }
}
