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
import com.gitlab.ccook.jgtk.MnemonicLabel;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;


import static org.junit.jupiter.api.Assertions.*;

public class GtkExpanderTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkExpander e = new GtkExpander();
        GtkButton b = new GtkButton("Button");
        GtkEntry ent = new GtkEntry();

        GtkExpander e2 = new GtkExpander(new MnemonicLabel("ABC"));
        e.setChild(b);
        e2.setChild(ent);


        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0);
        box.append(e);
        box.append(e2);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(box);
        gtkApplication.addWindow(w);
        w.show();

        assertTrue(e.getChild().isDefined());
        e.setLabel("label");
        assertTrue(e.getLabel().isDefined());
        assertTrue(e2.getLabel().isDefined());
        GtkLabel l = (GtkLabel) e.getWidgetLabel().get();
        assertNotNull(l);
        GtkCheckButton labelWidget = new GtkCheckButton();
        e.setLabelWidget(labelWidget);
        assertEquals(labelWidget, e.getWidgetLabel().get());
        assertFalse(e.isExpanded());
        e2.setExpanded(true);
        assertTrue(e2.isExpanded());
        assertTrue(e2.isLabelMnemonic());
        e.setLabelMnemonic(true);
        assertTrue(e.isLabelMnemonic());
        assertFalse(e.isLabelPangoMarkup());
        e.setIsLabelPangoMarkup(true);
        assertTrue(e.isLabelPangoMarkup());
        e.setLabel("<b>label</b>");
        //System.out.println(e2.willResize());
        assertFalse(e2.willResize());
        e2.shouldResize(true);
        assertTrue(e2.willResize());
        gtkApplication.quit();
    }
}
