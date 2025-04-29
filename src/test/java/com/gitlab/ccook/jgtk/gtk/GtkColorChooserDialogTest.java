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
import com.gitlab.ccook.jgtk.GdkRGBA;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class GtkColorChooserDialogTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkColorChooserDialog d = new GtkColorChooserDialog("Choose a color", w);

        GdkRGBA white = new GdkRGBA(1.0f, 1.0f, 1.0f, 1.0f);
        GdkRGBA currentlySelectedColor = d.getCurrentlySelectedColor();
        assertEquals(white, currentlySelectedColor);

        GdkRGBA brown = new GdkRGBA(0.5254902f, 0.36862746f, 0.23529412f, 1.0f);
        d.setColor(brown);
        currentlySelectedColor = d.getCurrentlySelectedColor();
        assertEquals(brown, currentlySelectedColor);

        assertTrue(d.doesUseAlpha());
        d.shouldUseAlpha(false);
        assertFalse(d.doesUseAlpha());
        d.shouldUseAlpha(true);
        assertTrue(d.doesUseAlpha());

        GdkRGBA[] randomColors = generateRandomColors(3);
        d.addPalette(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 3, randomColors);

        d.show();
        w.show();
        d.close();
        w.close();
        gtkApplication.quit();
    }

    @SuppressWarnings("SameParameterValue")
    private GdkRGBA[] generateRandomColors(int multiple) {
        SecureRandom r = new SecureRandom();
        int totalColors = multiple + Math.abs(r.nextInt(5) * multiple);
        GdkRGBA[] toReturn = new GdkRGBA[totalColors];
        for (int i = 0; i < toReturn.length; i++) {
            float red = r.nextFloat();
            float green = r.nextFloat();
            float blue = r.nextFloat();
            float alpha = r.nextFloat();
            toReturn[i] = new GdkRGBA(red, green, blue, alpha);
        }
        return toReturn;
    }
}
