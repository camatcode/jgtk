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
import com.gitlab.ccook.jgtk.GListModel;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.enums.GtkAssistantPageType;
import com.gitlab.ccook.util.Option;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class GtkAssistantTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkAssistant assistant = new GtkAssistant();

        GtkButton actionWidget = new GtkButton("Do Action");
        assistant.addActionWidget(actionWidget);
        assistant.removeActionWidget(actionWidget);
        assistant.setAssociatedApplication(gtkApplication);

        GtkLabel page0 = new GtkLabel("Page 0");
        Option<Integer> index = assistant.appendPage(page0);
        assertTrue(index.isDefined());
        Option<GtkWidget> page0Retrieved = assistant.getPage(index.get());
        assertTrue(page0Retrieved.isDefined());
        assertEquals(page0Retrieved.get(), page0);

        Option<GtkAssistantPage> assistantPage = assistant.getAssistantPage(index.get());
        assertTrue(assistantPage.isDefined());
        assertEquals(assistantPage.get().getChild().get(), page0);

        assertFalse(assistant.getCurrentPageNumber().isDefined());

        assertEquals(assistant.size(), 1);

        GtkLabel page1 = new GtkLabel("Page 1");
        assistant.insertPage(page1, 1);
        //TODO commit
        assistant.show();
        assertTrue(assistant.getCurrentPageNumber().isDefined());
        assertEquals(assistant.getCurrentPageNumber().get(), 0);

        assertFalse(assistant.isPageComplete(0));
        assertEquals(assistant.size(), 2);

        assertEquals(assistant.getPageType(1).get(), GtkAssistantPageType.GTK_ASSISTANT_PAGE_CONTENT);
        assistant.setPageType(1, GtkAssistantPageType.GTK_ASSISTANT_PAGE_CUSTOM);
        assertEquals(assistant.getPageType(1).get(), GtkAssistantPageType.GTK_ASSISTANT_PAGE_CUSTOM);

        GListModel<GtkAssistantPage> pages = assistant.getPages();
        assertEquals(pages.size(), assistant.size());
        for (int i = 0; i < pages.size(); i++) {
            assertNotNull(pages.getNth(i, GtkAssistantPage.class));
        }
        assertFalse(assistant.getPageTitle(0).isDefined());
        assistant.setPageTitle(0, "Title Page 0");
        assertTrue(assistant.getPageTitle(0).isDefined());
        assertEquals(assistant.getPageTitle(0).get(), "Title Page 0");


        assistant.setPageComplete(0, true);
        assertTrue(assistant.isPageComplete(0));
        assistant.setPageTitle(1, "Title Page 1");
        assistant.setPageType(1, GtkAssistantPageType.GTK_ASSISTANT_PAGE_CONFIRM);
        assistant.setPageComplete(1, true);
        assistant.connect(GtkAssistant.Signals.CLOSE, ((relevantThing, relevantData) -> {
            assistant.close();
            gtkApplication.quit();
        }));
        assistant.prependPage(new GtkLabel("Page -1"));
        assistant.removePage(0);
        gtkApplication.quit();

    }
}
