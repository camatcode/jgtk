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
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.util.Option;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GtkOverlayTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws IOException {

        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        GtkOverlay overlay = new GtkOverlay();
        GtkImage imageOnOverlay = new GtkImage(getIconFile());
        overlay.addToOverlay(imageOnOverlay);
        GtkBox b = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 5);
        GtkButton buttonA = new GtkButton("Button A");
        GtkButton buttonB = new GtkButton("Button B");
        GtkEntry ent = new GtkEntry("Placeholder");
        b.append(buttonA);
        b.append(buttonB);
        b.append(ent);
        overlay.setChild(b);
        w.setChild(overlay);
        w.show();

        Option<GtkWidget> child = overlay.getChild();
        assertTrue(child.isDefined());
        assertEquals(child.get(), b);

        assertFalse(overlay.isClippedWithinParent(imageOnOverlay));
        overlay.shouldClipWithinParent(imageOnOverlay, true);
        assertTrue(overlay.isClippedWithinParent(imageOnOverlay));


        assertFalse(overlay.isWidgetSizeIncludedInOverlayMeasurement(imageOnOverlay));
        overlay.shouldIncludeWidgetSizeInOverlayMeasurement(imageOnOverlay, true);
        assertTrue(overlay.isWidgetSizeIncludedInOverlayMeasurement(imageOnOverlay));

        GtkLabel labelOnOverlay = new GtkLabel("Label On overlay");
        overlay.addToOverlay(labelOnOverlay);
        overlay.removeFromOverlay(labelOnOverlay);
        gtkApplication.quit();
    }
}
