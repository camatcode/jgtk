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
import com.gitlab.ccook.jgtk.enums.GtkOrientation;


public class GtkPopoverMenuTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        GMenu mainMenu = makeMainMenu();
        GtkPopoverMenu m = new GtkPopoverMenu(mainMenu);
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 5);
        GtkButton dismissButton = new GtkButton("Dismiss");
        GtkButton showButton = new GtkButton("Show");
        box.append(dismissButton);
        box.append(showButton);

        box.append(m);
        w.setChild(box);
        w.show();
        gtkApplication.quit();
    }
}
