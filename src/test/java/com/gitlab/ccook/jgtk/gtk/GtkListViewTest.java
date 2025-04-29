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
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("rawtypes")
public class GtkListViewTest extends JGTKJUnitTest {
    @SuppressWarnings("unchecked")
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkSignalListItemFactory factory = new GtkSignalListItemFactory();
        factory.connect(GtkSignalListItemFactory.Signals.SETUP.getDetailedName(), new Callback() {
            public void invoke(Pointer factory2, Pointer object, Pointer user_data) {
                GtkListItem item = new GtkListItem(object);
                GtkLabel label = new GtkLabel("label " + item.getPosition());
                item.setChild(label);
            }
        });
        factory.connect(GtkSignalListItemFactory.Signals.BIND.getDetailedName(), new Callback() {
            public void invoke(Pointer factory2, Pointer object, Pointer user_data) {
                GtkListItem item = new GtkListItem(object);
                GtkLabel label = new GtkLabel("label " + item.getPosition() + ":X");
                item.setChild(label);
            }
        });
        GtkStringList list = new GtkStringList("Hello", "World");

        GtkSingleSelection<GtkListItem> selectionModel = new GtkSingleSelection(list);
        GtkListView view = new GtkListView(selectionModel, factory);

        assertFalse(view.doesSelectWithMouseDrag());
        view.shouldSelectWithMouseDrag(true);
        assertTrue(view.doesSelectWithMouseDrag());

        assertFalse(view.doesShowSeparators());
        view.shouldShowSeparators(true);
        assertTrue(view.doesShowSeparators());

        assertFalse(view.doesActivateOnSingleClick());
        view.shouldActivateOnSingleClick(true);
        assertTrue(view.doesActivateOnSingleClick());

        assertEquals(view.getFactory().get(), factory);

        assertEquals(view.getModel().get(), selectionModel);

        GtkApplicationWindow window = new GtkApplicationWindow(gtkApplication);
        window.setDefaultSize(500, 500);
        window.setChild(view);
        window.show();
        gtkApplication.quit();
    }
}
