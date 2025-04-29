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


import static org.junit.jupiter.api.Assertions.*;

public class GtkHeaderBarTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkHeaderBar bar = new GtkHeaderBar();
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setTitleBar(bar);

        GtkButton end = new GtkButton("end");
        GtkButton start = new GtkButton("start");
        bar.insertAtBeginning(start);
        bar.addToEnd(end);

        assertTrue(bar.doesShowTitleButtons());
        bar.shouldShowTitleButtons(false);
        assertFalse(bar.doesShowTitleButtons());
        bar.shouldShowTitleButtons(true);

        assertFalse(bar.getDecorationLayout().isDefined());
        bar.setDecorationLayout("icon:close,maximize,minimize");
        assertEquals(bar.getDecorationLayout().get(), "icon:close,maximize,minimize");

        assertFalse(bar.getTitleWidget().isDefined());
        GtkEntry ent = new GtkEntry("Title");
        bar.setTitleWidget(ent);
        assertTrue(bar.getTitleWidget().isDefined());
        assertEquals(bar.getTitleWidget().get(), ent);
        w.show();
        gtkApplication.quit();
    }
}
