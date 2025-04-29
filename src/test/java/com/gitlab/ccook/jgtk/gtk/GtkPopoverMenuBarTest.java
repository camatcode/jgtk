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
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkPopoverMenuBarTest extends JGTKJUnitTest {
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        GMenu mainMenu = new GMenu();
        GMenu fileMenu = new GMenu();
        GMenuItem undoItem = new GMenuItem("Undo", null);
        undoItem.setAttribute("custom", new GVariant(new GtkLibrary().g_variant_new_string("undoid")));
        fileMenu.append(undoItem);
        mainMenu.appendSubMenu("File", fileMenu);
        GtkPopoverMenuBar bar = new GtkPopoverMenuBar(mainMenu);
        GtkCenterBox box = new GtkCenterBox();
        box.setCenterWidget(bar);

        Option<GMenuModel> menuModel = bar.getMenuModel();
        assertTrue(menuModel.isDefined());
        assertEquals(menuModel.get(), mainMenu);

        GtkLabel l = new GtkLabel("Undo child added");
        boolean added = bar.addChild("undoid", l);
        assertTrue(added);

        boolean removed = bar.removeChild(l);
        assertTrue(removed);
        w.setChild(box);
        w.show();
        gtkApplication.quit();
    }
}
