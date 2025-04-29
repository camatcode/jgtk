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
import com.gitlab.ccook.jgtk.GMenu;
import com.gitlab.ccook.jgtk.GtkApplication;


import static org.junit.jupiter.api.Assertions.*;

public class GtkPasswordEntryTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);

        GtkPasswordEntry ent = new GtkPasswordEntry();
        ent.setSizeRequest(200, 200);
        assertFalse(ent.doesShowPeakIcon());
        ent.shouldShowPeakIcon(true);
        assertTrue(ent.doesShowPeakIcon());

        assertFalse(ent.getExtraMenu().isDefined());
        GMenu m = makeMainMenu();
        m.append("Label 1", "changed");
        ent.setExtraMenu(m);
        //System.out.println(ent.getExtraMenu());

        assertFalse(ent.getPlaceholderText().isDefined());
        ent.setPlaceholderText("Secret here");
        assertTrue(ent.getPlaceholderText().isDefined());
        assertEquals(ent.getPlaceholderText().get(), "Secret here");

        //TODO getExtraMenu doesn't seem to return what i'd like

        w.setChild(ent);
        w.show();
        gtkApplication.quit();
    }
}
