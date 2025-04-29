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
import com.gitlab.ccook.jgtk.GtkStackPage;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.util.Option;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkStackSidebarTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setSizeRequest(500, 500);
        GtkStack stack = new GtkStack();
        GtkStackSidebar s = new GtkStackSidebar(stack);
        GtkBox b = new GtkBox(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 4);
        b.append(stack);
        b.append(s);
        GtkLabel child1 = new GtkLabel("Hello World");
        GtkLabel child2 = new GtkLabel("Hello World 2");
        GtkLabel child3 = new GtkLabel("Hello World 3");
        Option<GtkStackPage> page1 = stack.addChild(child1, "first", "First Label");
        Option<GtkStackPage> page2 = stack.addChild(child2, "second", "Second Label");
        Option<GtkStackPage> page3 = stack.addChild(child3, "third", "Third Label");
        assertTrue(page1.isDefined());
        assertTrue(page2.isDefined());
        assertTrue(page3.isDefined());
        w.setChild(b);
        w.show();
        Option<GtkStack> stack1 = s.getStack();
        assertTrue(stack1.isDefined());
        assertEquals(stack1.get(), stack);
        gtkApplication.quit();
    }
}
