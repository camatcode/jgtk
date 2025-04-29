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
import com.gitlab.ccook.jgtk.enums.GtkOrientation;


import static org.junit.jupiter.api.Assertions.*;

public class GtkSearchBarTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 5);
        GtkSearchEntry entry = new GtkSearchEntry();
        box.append(entry);
        GtkSearchBar bar = new GtkSearchBar(entry);
        GtkMenuButton button = new GtkMenuButton();
        box.append(button);

        bar.setKeyCaptureWidget(w);
        bar.setChild(box);

        assertFalse(bar.doesShowCloseButton());
        bar.shouldShowCloseButton(true);
        assertTrue(bar.doesShowCloseButton());

        assertTrue(bar.getChild().isDefined());
        assertEquals(bar.getChild().get(), box);

        assertTrue(bar.getKeyCaptureWidget().isDefined());
        assertEquals(bar.getKeyCaptureWidget().get(), w);

        assertFalse(bar.isSearchModeOn());
        bar.setSearchModeOn(true);
        assertTrue(bar.isSearchModeOn());

        w.setChild(bar);
        w.show();
        gtkApplication.quit();
    }
}
