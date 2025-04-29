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
import com.gitlab.ccook.jgtk.enums.PangoEllipsizeMode;


import static org.junit.jupiter.api.Assertions.*;

public class GtkProgressBarTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        GtkCenterBox box = new GtkCenterBox();

        GtkProgressBar bar = new GtkProgressBar();

        assertFalse(bar.doesShowText());
        bar.shouldShowText(true);
        assertTrue(bar.doesShowText());

        assertEquals(bar.getEllipsizeMode(), PangoEllipsizeMode.PANGO_ELLIPSIZE_NONE);
        bar.setEllipsizeMode(PangoEllipsizeMode.PANGO_ELLIPSIZE_MIDDLE);
        assertEquals(bar.getEllipsizeMode(), PangoEllipsizeMode.PANGO_ELLIPSIZE_MIDDLE);

        assertEquals(bar.getProgress(), 0.0d);
        bar.setProgress(.5);
        assertEquals(bar.getProgress(), 0.5d);

        assertEquals(bar.getProgressStep(), 0.1d);
        bar.setProgressStep(0.2);
        assertEquals(bar.getProgressStep(), 0.2d);

        assertFalse(bar.getText().isDefined());
        bar.setText("New Text");
        assertTrue(bar.getText().isDefined());
        assertEquals(bar.getText().get(), "New Text");

        assertFalse(bar.isInverted());
        bar.setInverted(true);
        assertTrue(bar.isInverted());

        for (int i = 0; i < 100; i++) {
            bar.progress();
        }

        box.setCenterWidget(bar);
        w.setChild(box);
        w.show();
        gtkApplication.quit();
    }
}
