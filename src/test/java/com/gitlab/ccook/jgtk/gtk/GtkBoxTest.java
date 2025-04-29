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
import com.gitlab.ccook.jgtk.enums.GtkBaselinePosition;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;


import static org.junit.jupiter.api.Assertions.*;

public class GtkBoxTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow window = new GtkApplicationWindow(gtkApplication);
        GtkBox b = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0);
        window.setChild(b);
        assertEquals(b.getBaselinePosition(), GtkBaselinePosition.GTK_BASELINE_POSITION_CENTER);
        b.setBaselinePosition(GtkBaselinePosition.GTK_BASELINE_POSITION_BOTTOM);
        assertEquals(b.getBaselinePosition(), GtkBaselinePosition.GTK_BASELINE_POSITION_BOTTOM);
        b.setBaselinePosition(GtkBaselinePosition.GTK_BASELINE_POSITION_TOP);
        assertEquals(b.getBaselinePosition(), GtkBaselinePosition.GTK_BASELINE_POSITION_TOP);
        assertEquals(b.getSpacing(), 0);
        b.setSpacing(1);
        assertEquals(b.getSpacing(), 1);
        assertFalse(b.isHomogeneous());
        b.setHomogeneous(true);
        assertTrue(b.isHomogeneous());
        GtkButton w = new GtkButton("label");
        b.append(w);
        b.remove(w);
        w = new GtkButton("label");
        b.prepend(w);
        b.remove(w);
        w = new GtkButton("label");
        b.insertChildAfter(w, null);
        b.remove(w);
        w = new GtkButton("label");
        b.insertChildAfter(w, null);
        b.reorderChildAfter(w, null);
        GtkBox inflated = (GtkBox) JGTKObject.newObjectFromType(b.getCReference(), GtkBox.class);
        assertEquals(b, inflated);
        window.close();
        gtkApplication.quit();
    }
}
