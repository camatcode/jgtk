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
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.enums.GtkSelectionMode;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkFlowBoxTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        //todo bind model
        GtkFlowBox flowBox = new GtkFlowBox();

        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);

        w.setChild(flowBox);
        gtkApplication.addWindow(w);
        w.show();
        GtkButton button1Child = new GtkButton("Button 1");
        GtkFlowBoxChild button1 = new GtkFlowBoxChild(button1Child);
        assertFalse(button1.isSelected());
        assertTrue(button1.getChild().isDefined());
        assertEquals(button1.getChild().get(), button1Child);
        assertFalse(button1.getFlowBoxIndex().isDefined());
        flowBox.append(button1);
        assertTrue(button1.getFlowBoxIndex().isDefined());
        flowBox.selectChild(button1);
        assertTrue(button1.isSelected());
        AtomicBoolean foundSelected = new AtomicBoolean(false);
        flowBox.forEachSelected((box, child, userData) -> foundSelected.set(true), Pointer.NULL);
        assertTrue(foundSelected.get());

        Option<GtkFlowBoxChild> childAtIndex = flowBox.getChildAtIndex(0);
        assertTrue(childAtIndex.isDefined());
        assertEquals(childAtIndex.get(), button1);

        Option<GtkFlowBoxChild> childAtPosition = flowBox.getChildAtPosition(0, 0);
        assertTrue(childAtPosition.isDefined());
        assertEquals(childAtPosition.get(), button1);

        assertEquals(flowBox.getHorizontalSpacing(), 0);
        flowBox.setHorizontalSpacing(1);
        assertEquals(flowBox.getHorizontalSpacing(), 1);

        assertEquals(flowBox.getVerticalSpacing(), 0);
        flowBox.setVerticalSpacing(1);
        assertEquals(flowBox.getVerticalSpacing(), 1);

        assertEquals(flowBox.getMaxChildrenPerLine(), 7);
        flowBox.setMaxChildrenPerLine(2);
        assertEquals(flowBox.getMaxChildrenPerLine(), 2);

        assertEquals(flowBox.getMinChildrenPerLine(), 0);
        flowBox.setMinChildrenPerLine(1);
        assertEquals(flowBox.getMinChildrenPerLine(), 1);

        GList<GtkWidget> selectedChildren = flowBox.getSelectedChildren();
        assertEquals(selectedChildren.size(), 1);

        assertEquals(flowBox.getSelectionMode(), GtkSelectionMode.GTK_SELECTION_SINGLE);
        flowBox.setSelectionMode(GtkSelectionMode.GTK_SELECTION_MULTIPLE);
        assertEquals(flowBox.getSelectionMode(), GtkSelectionMode.GTK_SELECTION_MULTIPLE);

        GtkFlowBoxChild button2 = new GtkFlowBoxChild(new GtkButton("Button 2"));
        flowBox.insert(button2, -1);
        assertTrue(flowBox.getChildAtIndex(1).isDefined());
        assertEquals(flowBox.getChildAtIndex(1).get(), button2);
        flowBox.invalidateFilter();
        flowBox.invalidateSort();

        assertFalse(flowBox.isHomogeneous());
        flowBox.setHomogeneous(true);
        assertTrue(flowBox.isHomogeneous());

        flowBox.remove(button2);
        assertFalse(flowBox.getChildAtIndex(1).isDefined());

        GtkFlowBoxChild button3 = new GtkFlowBoxChild(new GtkButton("Button 3"));
        flowBox.prepend(button3);
        assertTrue(flowBox.getChildAtIndex(0).isDefined());
        assertEquals(flowBox.getChildAtIndex(0).get(), button3);

        flowBox.selectAll();
        selectedChildren = flowBox.getSelectedChildren();
        assertEquals(selectedChildren.size(), 2);

        assertTrue(flowBox.doChildrenActivateOnSingleClick());
        flowBox.shouldChildrenActivateOnSingleClick(false);
        assertFalse(flowBox.doChildrenActivateOnSingleClick());

        flowBox.setFilterFunc((child, userData) -> child.equals(button1.getCReference()), Pointer.NULL, data -> {
            //do nothing
        });
        flowBox.invalidateFilter();

        flowBox.setFilterFunc((child, userData) -> true, Pointer.NULL, data -> {
            //do nothing
        });

        //TODO use ScrolledWindow flowBox.setHorizontalAdjustment();

        flowBox.setSortFunction((child1, child2, userData) -> {
            GtkFlowBoxChild c1 = new GtkFlowBoxChild(child1);
            GtkButton b1 = new GtkButton(c1.getChild().get().getCReference());
            GtkFlowBoxChild c2 = new GtkFlowBoxChild(child2);
            GtkButton b2 = new GtkButton(c2.getChild().get().getCReference());
            return b1.getLabel().get().compareTo(b2.getLabel().get());
        }, Pointer.NULL, null);

        //TODO scrolled window setVerticalAdjustment

        AtomicBoolean deselected = new AtomicBoolean(true);
        flowBox.connect(GtkFlowBox.Signals.DESELECTED_ALL, (relevantThing, relevantData) -> deselected.set(true));
        flowBox.deselectChild(button1);
        assertFalse(button1.isSelected());
        flowBox.deselectAll();
        assertTrue(deselected.get());
        selectedChildren = flowBox.getSelectedChildren();
        assertEquals(selectedChildren.size(), 0);
        gtkApplication.quit();

    }
}
