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
import com.gitlab.ccook.jgtk.GObject;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;

@SuppressWarnings("rawtypes")
public class GtkTreeExpanderTest extends JGTKJUnitTest {
    @SuppressWarnings("unchecked")
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkSignalListItemFactory factory = new GtkSignalListItemFactory();
        GtkStringList list = new GtkStringList("Hello", "World", "!");

        GtkTreeListModel m = new GtkTreeListModel(list, true, false, (item, userData) -> {
            JGTKObject jgtkObject = JGTKObject.newObjectFromType(item, GObject.class);
            if (jgtkObject instanceof GtkStringObject) {
                if (jgtkObject.toString().equals("Hello")) {
                    GtkStringList list1 = new GtkStringList("Good Bye");
                    return list1.getCReference();
                }
            }
            return Pointer.NULL;
        });
        factory.connect(GtkSignalListItemFactory.Signals.BIND.getDetailedName(), new Callback() {
            public void invoke(Pointer factory2, Pointer object, Pointer user_data) {
                GtkListItem item = new GtkListItem(object);
                GtkLabel label = new GtkLabel("label " + item.getPosition() + ":X");
                item.setChild(label);
            }
        });
        GtkSingleSelection<GtkListItem> selectionModel = new GtkSingleSelection(m);
        GtkListView view = new GtkListView(selectionModel, factory);


        GtkApplicationWindow window = new GtkApplicationWindow(gtkApplication);
        window.setDefaultSize(500, 500);
        window.setChild(view);
        window.show();
        gtkApplication.quit();
    }
}
