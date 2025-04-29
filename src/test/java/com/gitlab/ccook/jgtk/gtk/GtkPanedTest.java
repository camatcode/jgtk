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
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.util.Option;


import static org.junit.jupiter.api.Assertions.*;

public class GtkPanedTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        GtkPaned hPaned = new GtkPaned(GtkOrientation.GTK_ORIENTATION_HORIZONTAL);
        GtkFrame frame1 = new GtkFrame();
        GtkFrame frame2 = new GtkFrame();

        hPaned.setSizeRequest(200, -1);

        hPaned.setStartChild(frame1);
        Option<GtkWidget> startChild = hPaned.getStartChild();
        assertTrue(startChild.isDefined());
        assertEquals(startChild.get(), frame1);

        hPaned.setStartChildResizeable(true);
        assertTrue(hPaned.isStartChildResizable());
        hPaned.setStartChildShrinkable(false);
        assertFalse(hPaned.isStartChildShrinkable());
        frame1.setSizeRequest(50, -1);

        hPaned.setEndChild(frame2);
        Option<GtkWidget> endChild = hPaned.getEndChild();
        assertTrue(endChild.isDefined());
        assertEquals(endChild.get(), frame2);
        hPaned.setEndChildResizeable(false);
        assertFalse(hPaned.isEndChildResizable());
        hPaned.setEndChildShrinkable(false);
        assertFalse(hPaned.isEndChildShrinkable());
        frame2.setSizeRequest(50, -1);

        assertEquals(hPaned.getDividerPosition(), 0);
        hPaned.setDividerPosition(5);
        assertEquals(hPaned.getDividerPosition(), 5);

        assertFalse(hPaned.isSeparatorWide());
        hPaned.setSeparatorWide(true);
        assertTrue(hPaned.isSeparatorWide());

        w.setChild(hPaned);
        w.show();
        gtkApplication.quit();
    }
}
