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
import com.gitlab.ccook.jgtk.enums.GtkPackType;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.util.Option;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkNotebookTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws IOException {

        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.show();
        GtkNotebook notebook = new GtkNotebook();
        w.setChild(notebook);
        AtomicBoolean pageAdded = new AtomicBoolean(false);
        notebook.connect(GtkNotebook.Signals.PAGE_ADDED, (relevantThing, relevantData) -> pageAdded.set(true));

        GtkEntry tab1 = new GtkEntry("Placeholder");
        Option<Integer> child1Position = notebook.appendPage(tab1, new GtkLabel("Page A"));
        assertTrue(child1Position.isDefined());
        assertEquals(child1Position.get(), notebook.getPageIndex(tab1).get());

        GtkEntry tab2 = new GtkEntry("Placeholder 2");
        GtkImage child2PageMenu = new GtkImage(getIconFile());
        GtkLabel child2TabLabel = new GtkLabel("Page B");
        notebook.enablePopupMenu();
        Option<Integer> child2Position = notebook.appendPageMenu(tab2, child2TabLabel, child2PageMenu);
        assertTrue(child2Position.isDefined());
        assertEquals(child2Position.get(), notebook.getPageIndex(tab2).get());

        assertTrue(notebook.doesShowTabs());
        notebook.shouldShowTabs(false);
        assertFalse(notebook.doesShowTabs());
        notebook.shouldShowTabs(true);

        assertFalse(notebook.isTabReorderable(tab1));
        assertFalse(notebook.isTabReorderable(tab2));
        notebook.setTabReorderable(tab1, true);
        notebook.setTabReorderable(tab2, true);
        assertTrue(notebook.isTabReorderable(tab1));
        assertTrue(notebook.isTabReorderable(tab2));


        assertFalse(notebook.isTabDetachable(tab1));
        notebook.setTabDetachable(tab1, true);
        assertTrue(notebook.isTabDetachable(tab1));

        Option<GtkWidget> actionWidget = notebook.getActionWidget(GtkPackType.GTK_PACK_START);
        assertFalse(actionWidget.isDefined());

        GtkButton actionButton = new GtkButton("Detach Tab A");
        notebook.setActionWidget(actionButton, GtkPackType.GTK_PACK_START);
        actionButton.connect(GtkButton.Signals.CLICKED, (relevantThing, relevantData) -> notebook.detachTab(tab1));
        actionWidget = notebook.getActionWidget(GtkPackType.GTK_PACK_START);
        assertTrue(actionWidget.isDefined());
        assertEquals(actionWidget.get(), actionButton);

        List<GtkWidget> allPages = notebook.getAllPages();
        assertEquals(allPages.size(), 2);
        assertEquals(notebook.getNumberOfPages(), 2);
        assertTrue(allPages.contains(tab1));
        assertTrue(allPages.contains(tab2));

        Option<GtkWidget> index0 = notebook.getPageAtIndex(0);
        assertTrue(index0.isDefined());
        assertEquals(index0.get(), tab1);
        Option<GtkWidget> index1 = notebook.getPageAtIndex(1);
        assertTrue(index1.isDefined());
        assertEquals(index1.get(), tab2);

        Option<Integer> currentPage = notebook.getCurrentPageIndex();
        assertTrue(currentPage.isDefined());
        assertEquals(currentPage.get(), 0);

        assertFalse(notebook.getGroupName().isDefined());
        notebook.setGroupName("Group1");
        assertTrue(notebook.getGroupName().isDefined());
        assertEquals(notebook.getGroupName().get(), "Group1");

        assertFalse(notebook.getMenuLabel(tab1).isDefined());
        notebook.setMenuLabel(tab1, new GtkLabel("Go to Page A"));
        assertTrue(notebook.getMenuLabel(tab1).isDefined());

        assertTrue(notebook.getMenuText(tab1).isDefined());
        assertEquals(notebook.getMenuText(tab1).get(), "Go to Page A");
        notebook.setMenuLabelText(tab1, "GOTO Page A");
        assertTrue(notebook.getMenuText(tab1).isDefined());
        assertEquals(notebook.getMenuText(tab1).get(), "GOTO Page A");


        GListModelSet<GtkNotebookPage> pages = notebook.getNotebookPages();
        assertFalse(pages.isEmpty());
        for (GtkNotebookPage page : pages) {
            assertTrue(page.getChild().isDefined());
        }

        assertEquals(notebook.getTabDrawPosition(), GtkPositionType.GTK_POS_TOP);
        notebook.setTabDrawPosition(GtkPositionType.GTK_POS_BOTTOM);
        assertEquals(notebook.getTabDrawPosition(), GtkPositionType.GTK_POS_BOTTOM);

        Option<GtkWidget> tab1Label = notebook.getTabLabel(tab1);
        assertTrue(tab1Label.isDefined());
        assertEquals(tab1Label.get().getClass(), GtkLabel.class);

        assertTrue(notebook.getTabLabelText(tab2).isDefined());
        assertEquals(notebook.getTabLabelText(tab2).get(), "Page B");

        notebook.gotoPage(1);
        assertEquals(notebook.getCurrentPageIndex().get(), 1);

        assertTrue(notebook.hasBevel());
        notebook.setBevel(false);
        assertFalse(notebook.hasBevel());

        GtkWidget tab3 = new GtkEntry("Placeholder 3");
        notebook.insertPage(tab3, new GtkLabel("Page C"), 0);
        notebook.removePage(0);
        tab3 = new GtkEntry("Placeholder 3");
        notebook.insertPageMenu(tab3, new GtkLabel("Page c"), new GtkLabel("Page C?"), 2);

        assertFalse(notebook.areTabsScrollable());
        notebook.setTabsScrollable(true);
        assertTrue(notebook.areTabsScrollable());

        notebook.nextPage();
        notebook.previousPage();

        tab3 = new GtkEntry("Placeholder 3");
        notebook.prependPage(tab3, new GtkLabel("Page c"));
        notebook.removePage(0);
        tab3 = new GtkEntry("Placeholder 3");
        notebook.prependPageMenu(tab3, new GtkLabel("Page c"), new GtkLabel("Page C?"));
        notebook.setTabLabel(tab3, "PAGE C");

        notebook.reorderChild(tab1, 1);
        notebook.disablePopupMenu();
        assertTrue(pageAdded.get());
        //TODO detachTab
        gtkApplication.quit();
    }
}

