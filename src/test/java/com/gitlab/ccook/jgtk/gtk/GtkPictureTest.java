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
import com.gitlab.ccook.jgtk.GdkPixbuf;
import com.gitlab.ccook.jgtk.GdkTexture;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.errors.GErrorException;


import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class GtkPictureTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws IOException, GErrorException {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 5);
        File iconFile = getIconFile();
        GdkTexture paintable = new GdkTexture(getIconFile());
        GdkPixbuf buf = new GdkPixbuf(getIconFile());
        GtkPicture empty = new GtkPicture();
        GtkPicture fromFile = new GtkPicture(iconFile);
        GtkPicture fromPaintable = new GtkPicture(paintable);
        GtkPicture fromBuf = new GtkPicture(buf);
        box.append(empty);
        box.append(fromFile);
        box.append(fromPaintable);
        box.append(fromBuf);

        assertTrue(fromFile.doesKeepAspectRatio());
        fromFile.shouldKeepAspectRatio(false);
        assertFalse(fromFile.doesKeepAspectRatio());

        assertFalse(fromFile.getAlternativeText().isDefined());
        fromFile.setAlternativeText("This is an icon");
        assertTrue(fromFile.getAlternativeText().isDefined());

        assertTrue(fromFile.isShrinkable());
        fromFile.setShrinkable(false);
        assertFalse(fromFile.isShrinkable());

        assertTrue(fromFile.getFile().isDefined());
        assertTrue(fromFile.getGFile().isDefined());

        assertTrue(fromPaintable.getPaintable().isDefined());
        assertTrue(fromBuf.getPaintable().isDefined());

        assertFalse(empty.getFile().isDefined());
        empty.setFile(getIconFile());
        assertTrue(empty.getFile().isDefined());
        empty.setPaintable(paintable);
        assertTrue(empty.getPaintable().isDefined());
        empty.setPixbuf(buf);
        assertTrue(empty.getPaintable().isDefined());
        w.setChild(box);
        w.show();
        gtkApplication.quit();
    }
}
