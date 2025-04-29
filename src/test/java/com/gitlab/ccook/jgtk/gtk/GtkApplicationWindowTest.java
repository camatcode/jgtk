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
import com.gitlab.ccook.jgtk.GtkShortcutsWindow;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.util.Option;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GtkApplicationWindowTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(100, 100);
        assertTrue(w.getWindowId().isDefined());

        assertFalse(w.doesShowMenuBar());
        w.shouldShowMenuBar(true);
        assertTrue(w.doesShowMenuBar());

        assertFalse(w.getHelpOverlay().isDefined());
        File f = new File("src/test/resources/ui/shortcuts-builder.ui");
        GtkBuilder b = new GtkBuilder(f);
        Option<JGTKObject> object = b.getObject("shortcuts-builder");
        assertTrue(object.isDefined());
        GtkShortcutsWindow window = (GtkShortcutsWindow) object.get();
        w.setHelpOverlay(window);
        window.setAssociatedApplication(gtkApplication);

        assertTrue(w.getHelpOverlay().isDefined());
        assertEquals(w.getHelpOverlay().get(), window);

        GtkLabel l = new GtkLabel("Label");
        w.setChild(l);
        w.show();
        gtkApplication.quit();
    }
}
