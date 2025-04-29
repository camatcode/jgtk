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
import com.gitlab.ccook.jgtk.enums.GtkSelectionMode;


import static org.junit.jupiter.api.Assertions.*;

public class GtkListBoxRowTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkListBox box = new GtkListBox();
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(box);
        gtkApplication.addWindow(w);
        w.show();

        GtkLabel label = new GtkLabel("Label 1");
        box.append(label);
        GtkLabel label2 = new GtkLabel("Label 2");
        box.append(label2);

        assertTrue(box.getRowAtIndex(0).isDefined());
        assertTrue(box.getRowAtIndex(1).isDefined());
        GtkListBoxRow row1 = box.getRowAtIndex(0).get();
        GtkListBoxRow row2 = box.getRowAtIndex(1).get();

        assertTrue(row1.getChild().isDefined());
        assertTrue(row2.getChild().isDefined());

        GtkLabel label3 = new GtkLabel("Label 3");
        GtkListBoxRow row3 = new GtkListBoxRow();
        row3.setChild(label3);
        assertTrue(row3.getChild().isDefined());
        assertEquals(row3.getChild().get(), label3);

        assertEquals(row1.getChild().get(), label);
        assertEquals(row2.getChild().get(), label2);

        assertFalse(row3.getHeader().isDefined());
        GtkLabel header = new GtkLabel("Header");
        row3.setHeader(header);
        assertTrue(row3.getHeader().isDefined());

        assertFalse(row3.getIndex().isDefined());
        box.insert(row3, 2);
        assertTrue(row3.getIndex().isDefined());
        assertEquals(row3.getIndex().get(), 2);

        assertTrue(row3.isActivatable());
        row3.setActivatable(false);
        assertFalse(row3.isActivatable());

        assertTrue(row3.isSelectable());
        row3.setSelectable(false);
        assertFalse(row3.isSelectable());
        box.setSelectionMode(GtkSelectionMode.GTK_SELECTION_MULTIPLE);
        box.selectAll();
        assertFalse(row3.isSelected());
        row3.markChanged();
        gtkApplication.quit();
    }
}
