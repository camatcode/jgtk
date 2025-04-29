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
import com.gitlab.ccook.jgtk.GList;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.enums.GtkSelectionMode;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class GtkListBoxTest extends JGTKJUnitTest {
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

        //TODO bind model

        assertFalse(box.doesShowSeparators());
        box.shouldShowSeparators(true);
        assertTrue(box.doesShowSeparators());

        Option<GtkListBoxRow> rowAtIndex = box.getRowAtIndex(1);
        assertTrue(rowAtIndex.isDefined());
        box.select(rowAtIndex.get());
        assertTrue(rowAtIndex.get().isSelected());
        box.deselect(rowAtIndex.get());
        assertFalse(rowAtIndex.get().isSelected());
        assertEquals(box.getSelectionMode(), GtkSelectionMode.GTK_SELECTION_SINGLE);
        box.setSelectionMode(GtkSelectionMode.GTK_SELECTION_MULTIPLE);
        assertEquals(box.getSelectionMode(), GtkSelectionMode.GTK_SELECTION_MULTIPLE);
        box.selectAll();

        GList<GtkListBoxRow> selectedRows = box.getSelectedRows();
        assertEquals(selectedRows.size(), 2);
        AtomicInteger count = new AtomicInteger(0);
        box.forEachSelected((box1, row, userData) -> count.set(count.get() + 1), Pointer.NULL);
        assertEquals(count.get(), 2);

        assertTrue(box.doesActivateOnSingleClick());
        box.shouldActivateOnSingleClick(false);
        assertFalse(box.doesActivateOnSingleClick());

        assertFalse(box.getAdjustment().isDefined());
        box.setAdjustment(null);


        GtkLabel label3 = new GtkLabel("Label 3");
        box.insert(label3, 2);

        GtkLabel label4 = new GtkLabel("Label 4");
        box.prepend(label4);

        box.remove(box.getRowAtIndex(2).get());

        box.removeHighlight();
        box.deselectAll();
        box.setSort((row1, row2, userData) -> {
            GtkListBoxRow one = new GtkListBoxRow(row1);
            GtkListBoxRow two = new GtkListBoxRow(row2);
            GtkLabel l = (GtkLabel) one.getChild().get();
            GtkLabel l2 = (GtkLabel) two.getChild().get();
            return l2.getLabel().compareTo(l.getLabel());
        }, Pointer.NULL, data -> {

        });
        box.invalidateSort();

        box.setUpdateHeader((row, before, userData) -> {
            GtkListBoxRow one = new GtkListBoxRow(row);
            if (before != Pointer.NULL && row != Pointer.NULL) {
                GtkListBoxRow two = new GtkListBoxRow(before);
                GtkLabel l = new GtkLabel(one.getChild().get().getCReference());
                GtkLabel beforeL = new GtkLabel(two.getChild().get().getCReference());
                l.setText("... before " + l.getText() + " is " + beforeL.getText());

            }
        }, Pointer.NULL, data -> {

        });

        box.setFilter((row, userData) -> {
            GtkListBoxRow one = new GtkListBoxRow(row);
            GtkLabel l = new GtkLabel(one.getChild().get().getCReference());
            return l.getText().contains("1");
        }, Pointer.NULL, data -> {

        });

        box.invalidateFilter();
        box.invalidateHeaders();
        GtkLabel placeholder = new GtkLabel("Place Holder");
        box.setPlaceholder(placeholder);
        box.setFilter((row, userData) -> false, Pointer.NULL, data -> {

        });
        gtkApplication.quit();
    }
}
