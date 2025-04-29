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

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GtkVideoTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(500, 500);
        File sampleMP4 = getSampleMP4();
        GtkVideo v = new GtkVideo(sampleMP4);
        assertFalse(v.doesLoop());
        v.shouldLoop(true);
        assertTrue(v.doesLoop());

        assertFalse(v.doesAutoPlay());
        v.shouldAutoPlay(true);
        assertTrue(v.doesAutoPlay());

        assertTrue(v.getFile().isDefined());
        assertEquals(v.getFile().get().getAbsolutePath(), sampleMP4.getAbsolutePath());
        w.setChild(v);
        w.show();
        gtkApplication.quit();

    }
}
