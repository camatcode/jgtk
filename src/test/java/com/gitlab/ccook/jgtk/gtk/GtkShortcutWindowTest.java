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
import com.gitlab.ccook.util.Option;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class GtkShortcutWindowTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        File f = new File("src/test/resources/ui/shortcuts-builder.ui");
        GtkBuilder b = new GtkBuilder(f);
        Option<JGTKObject> object = b.getObject("shortcuts-builder");
        assertTrue(object.isDefined());
        GtkShortcutsWindow window = (GtkShortcutsWindow) object.get();
        window.setDefaultSize(200, 200);
        window.setSizeRequest(200, 200);
        gtkApplication.addWindow(window);
        window.show();
        Option<String> sectionName = window.getSectionName();
        assertTrue(sectionName.isDefined());
        assertEquals(sectionName.get(), "editor");
        Option<GtkHeaderBar> header = window.getHeader();
        assertTrue(header.isDefined());
        GtkHeaderBar headerBar = header.get();
        Option<GtkWidget> titleWidget = headerBar.getTitleWidget();
        assertTrue(titleWidget.isDefined());
        GtkStack titleStack = (GtkStack) titleWidget.get();
        GListModel<GtkStackPage> pages = titleStack.getPages();
        assertTrue(pages.size() > 0);
        Option<GtkWidget> title = titleStack.getChild("title");
        assertTrue(title.isDefined());
        GtkLabel titleLabel = (GtkLabel) title.get();
        assertEquals(titleLabel.getLabel(), "Shortcuts");
        Option<GtkWidget> search = titleStack.getChild("search");
        assertTrue(search.isDefined());
        GtkLabel searchLabel = (GtkLabel) search.get();
        assertEquals(searchLabel.getLabel(), "Search Results");
        Option<GtkWidget> sections = titleStack.getChild("sections");
        assertTrue(sections.isDefined());
        GtkMenuButton sectionsButton = (GtkMenuButton) sections.get();
        assertEquals(sectionsButton.getLabel().get(), "Editor Shortcuts");

        window.close();
        f = new File("src/test/resources/ui/shortcuts-gedit.ui");
        b = new GtkBuilder(f);
        object = b.getObject("shortcuts-gedit");
        assertTrue(object.isDefined());
        window = (GtkShortcutsWindow) object.get();
        window.setDefaultSize(200, 200);
        window.setSizeRequest(200, 200);
        gtkApplication.addWindow(window);
        window.show();
        sectionName = window.getSectionName();
        assertTrue(sectionName.isDefined());
        assertEquals(sectionName.get(), "shortcuts");
        header = window.getHeader();
        assertTrue(header.isDefined());
        headerBar = header.get();
        titleWidget = headerBar.getTitleWidget();
        assertTrue(titleWidget.isDefined());
        titleStack = (GtkStack) titleWidget.get();
        pages = titleStack.getPages();
        assertTrue(pages.size() > 0);
        title = titleStack.getChild("title");
        assertTrue(title.isDefined());
        titleLabel = (GtkLabel) title.get();
        assertEquals(titleLabel.getLabel(), "Shortcuts");

        search = titleStack.getChild("search");
        assertTrue(search.isDefined());
        searchLabel = (GtkLabel) search.get();
        assertEquals(searchLabel.getLabel(), "Search Results");
        sections = titleStack.getChild("sections");
        sectionsButton = (GtkMenuButton) sections.get();
        assertFalse(sectionsButton.getLabel().isDefined());

        Option<GtkSearchBar> searchBar = window.getSearchBar();
        assertTrue(searchBar.isDefined());
        assertTrue(searchBar.get().getChild().isDefined());
        assertInstanceOf(GtkSearchEntry.class, searchBar.get().getChild().get());
        GtkSearchEntry ent = (GtkSearchEntry) searchBar.get().getChild().get();
        assertEquals(ent.getPlaceholderText().get(), "Search Shortcuts");

        Option<GtkShortcutsSection> s = window.getSection();
        assertTrue(s.isDefined());
        Option<GtkScrolledWindow> internalSearch = window.getInternalSearch();
        assertTrue(internalSearch.isDefined());
        Option<GtkGrid> noSearchResults = window.getNoSearchResults();
        assertTrue(noSearchResults.isDefined());
        gtkApplication.quit();
    }

}
