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
import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.util.Option;


import static org.junit.jupiter.api.Assertions.*;


public class GtkActionBarTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkActionBar bar = new GtkActionBar();
        GtkActionBar inflated = (GtkActionBar) JGTKObject.newObjectFromType(bar.getCReference(), GtkActionBar.class);
        assertEquals(bar, inflated);
        GtkButton first = new GtkButton("First");
        GtkButton firstNext = new GtkButton("FirstNext");
        GtkButton last = new GtkButton("Last");
        GtkButton lastNext = new GtkButton("Last Next");
        GtkButton center = new GtkButton("Center");
        bar.addToLeftSide(first);
        bar.addToLeftSide(firstNext);
        bar.addToRightSide(last);
        bar.addToRightSide(lastNext);
        bar.setRevealed(false);
        assertFalse(bar.isRevealed());
        GtkApplicationWindow window = new GtkApplicationWindow(gtkApplication);
        window.setChild(bar);
        bar.setRevealed(true);
        assertTrue(bar.isRevealed());
        Option<GtkWidget> centerW = bar.getCenterWidget();
        assertFalse(centerW.isDefined());
        bar.setCenterWidget(center);
        centerW = bar.getCenterWidget();
        assertTrue(centerW.isDefined());
        assertEquals(centerW.get(), center);
        window.show();
        bar.remove(center);
        gtkApplication.quit();
    }
}
