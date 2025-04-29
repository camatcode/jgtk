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
import com.gitlab.ccook.jgtk.enums.GtkBaselinePosition;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;


import static org.junit.jupiter.api.Assertions.*;

public class GtkGridTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {

        GtkGrid grid = new GtkGrid();
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkEntry entry = new GtkEntry("entry");
        grid.insertColumn(0);
        grid.insertRow(0);
        grid.insertColumn(1);
        grid.insertRow(1);
        w.setChild(grid);
        gtkApplication.addWindow(w);
        w.show();
        GtkEntry entry2 = new GtkEntry("entry2");

        assertFalse(grid.areColumnsHomogeneous());
        grid.setColumnsHomogeneous(true);
        assertTrue(grid.areColumnsHomogeneous());

        grid.attach(entry2, 0, 0, 1, 1);
        grid.attachNextTo(entry, entry2, GtkPositionType.GTK_POS_RIGHT, 1, 1);

        assertEquals(grid.getBaselineRow(), 0);
        grid.setBaselineRow(-1);
        assertEquals(grid.getBaselineRow(), -1);
        grid.setBaselineRow(0);

        assertTrue(grid.getChildAt(0, 0).isDefined());
        assertEquals(grid.getChildAt(0, 0).get(), entry2);

        assertTrue(grid.getChildAt(1, 0).isDefined());
        assertEquals(grid.getChildAt(1, 0).get(), entry);

        assertEquals(grid.getColumnSpacing(), 0);
        grid.setColumnSpacing(10);
        assertEquals(grid.getColumnSpacing(), 10);

        assertEquals(grid.getRowBaselinePosition(0), GtkBaselinePosition.GTK_BASELINE_POSITION_CENTER);
        grid.setRowBaselinePosition(0, GtkBaselinePosition.GTK_BASELINE_POSITION_TOP);
        assertEquals(grid.getRowBaselinePosition(0), GtkBaselinePosition.GTK_BASELINE_POSITION_TOP);

        assertEquals(grid.getRowSpacing(), 0);
        grid.setRowSpacing(10);
        assertEquals(grid.getRowSpacing(), 10);

        grid.insertColumn(0);
        grid.insertRow(0);

        Option<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> query = grid.queryChild(entry2);
        assertTrue(query.isDefined());
        assertEquals(query.get().getFirst().getFirst(), 1);
        assertEquals(query.get().getFirst().getSecond(), 1);
        assertEquals(query.get().getSecond().getFirst(), 1);
        assertEquals(query.get().getSecond().getSecond(), 1);

        grid.removeRow(0);
        grid.removeColumn(0);

        grid.remove(entry);
        assertFalse(grid.queryChild(entry).isDefined());
        gtkApplication.quit();
    }
}
