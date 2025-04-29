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

public class GtkViewportTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(100, 100);
        w.setSizeRequest(100, 100);
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 10);
        GtkVideo v = new GtkVideo(getSampleMP4());
        GtkLabel l = new GtkLabel("Label");
        box.append(v);
        box.append(l);
        for (int i = 0; i < 100; i++) {
            box.append(new GtkLabel("" + i));
        }
        box.setSizeRequest(5, 5);
        GtkViewport viewport = new GtkViewport(box);
        GtkScrolledWindow w2 = new GtkScrolledWindow();
        w2.setChild(viewport);
        w.setChild(w2);

        assertTrue(viewport.doesScrollToFocus());
        viewport.shouldScrollToFocus(false);
        assertFalse(viewport.doesScrollToFocus());

        assertTrue(viewport.getChild().isDefined());
        assertEquals(viewport.getChild().get(), box);
        w.show();
        gtkApplication.quit();
    }
}
