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
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.enums.GtkStackTransitionType;
import com.gitlab.ccook.util.Option;


import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class GtkStackTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkStack stack = new GtkStack();
        GtkStackSwitcher s = new GtkStackSwitcher(stack);
        GtkBox b = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 4);
        b.append(stack);
        b.append(s);

        GtkLabel child1 = new GtkLabel("Hello World");
        GtkLabel child2 = new GtkLabel("Hello World 2");
        GtkLabel child3 = new GtkLabel("Hello World 3");
        Option<GtkStackPage> page1 = stack.addChild(child1);
        Option<GtkStackPage> page2 = stack.addChild(child2, "second");
        Option<GtkStackPage> page3 = stack.addChild(child3, "third", "Third Label");
        assertTrue(page1.isDefined());
        assertTrue(page2.isDefined());
        assertTrue(page3.isDefined());
        page1 = stack.getPageForChild(child1);
        page2 = stack.getPageForChild(child2);
        page3 = stack.getPageForChild(child3);
        assertTrue(page1.isDefined());
        assertEquals(page1.get().getChild(), child1);
        assertTrue(page2.isDefined());
        assertEquals(page2.get().getChild(), child2);
        assertTrue(page3.isDefined());
        assertEquals(page3.get().getChild(), child3);
        Option<GtkWidget> second = stack.getChild("second");
        assertTrue(second.isDefined());
        assertEquals(second.get(), child2);
        Option<GtkWidget> third = stack.getChild("third");
        assertTrue(third.isDefined());
        assertEquals(third.get(), child3);

        assertFalse(stack.doesInterpolateSizes());
        stack.shouldInterpolateSizes(true);
        assertTrue(stack.doesInterpolateSizes());

        GListModel<GtkStackPage> pages = stack.getPages();
        assertTrue(pages.size() > 0);

        assertEquals(stack.getTransitionTimeMilliseconds(), 200);
        stack.setTransitionTimeMilliseconds(500);
        assertEquals(stack.getTransitionTimeMilliseconds(), 500);

        assertEquals(stack.getTransitionType(), GtkStackTransitionType.GTK_STACK_TRANSITION_TYPE_NONE);
        stack.setTransitionType(GtkStackTransitionType.GTK_STACK_TRANSITION_TYPE_CROSSFADE);
        assertEquals(stack.getTransitionType(), GtkStackTransitionType.GTK_STACK_TRANSITION_TYPE_CROSSFADE);

        assertEquals(child1, stack.getVisibleChild().get());
        stack.setVisibleChild("second");
        assertEquals(child2, stack.getVisibleChild().get());
        assertEquals(stack.getVisibleChildName().get(), "second");

        assertTrue(stack.isHorizontallyHomogeneous());
        stack.setHorizontallyHomogeneous(false);
        assertFalse(stack.isHorizontallyHomogeneous());

        assertFalse(stack.isTransitioning());
        assertTrue(stack.isVerticallyHomogeneous());
        stack.setVerticallyHomogeneous(false);
        assertFalse(stack.isVerticallyHomogeneous());

        stack.removeChild(child1);
        pages = stack.getPages();
        assertEquals(pages.size(), 2);

        w.setChild(b);
        w.show();
        gtkApplication.quit();
    }
}
