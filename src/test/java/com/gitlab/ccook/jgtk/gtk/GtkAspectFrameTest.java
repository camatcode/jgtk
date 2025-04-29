/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 JGTK
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
import com.gitlab.ccook.jgtk.JGTKObject;


import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
public class GtkAspectFrameTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        float xAlign = 0.5f;
        float yAlign = 0.5f;
        float ratio = 0.5f;
        boolean obeyChild = false;//TODO
        GtkAspectFrame f = new GtkAspectFrame(xAlign, yAlign, ratio, obeyChild);
        GtkAspectFrame inflated = (GtkAspectFrame) JGTKObject.newObjectFromType(f.getCReference(), GtkAspectFrame.class);
        assertEquals(f, inflated);
        assertEquals(xAlign, f.getHorizontalAlignment(), 0);
        assertEquals(yAlign, f.getVerticalAlignment(), 0);
        assertEquals(ratio, f.getRatio(), 0);
        assertFalse(f.getChild().isDefined());
        GtkButton b = new GtkButton("Button");
        f.setChild(b);
        assertTrue(f.getChild().isDefined());
        assertEquals(b, f.getChild().get());
        assertEquals(obeyChild, f.doesObeyChild());
        obeyChild = true;
        f.shouldObeyChild(obeyChild);
        assertEquals(obeyChild, f.doesObeyChild());
        ratio = 0.6f;
        xAlign = 0.6f;
        yAlign = 0.6f;
        f.setAspectRatio(ratio);
        f.setHorizontalAlignment(xAlign);
        f.setVerticalAlignment(yAlign);
        assertEquals(xAlign, f.getHorizontalAlignment(), 0);
        assertEquals(yAlign, f.getVerticalAlignment(), 0);
        assertEquals(ratio, f.getRatio(), 0);
        w.show();
        gtkApplication.quit();
    }
}
