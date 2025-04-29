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
import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.enums.GtkIconSize;
import com.gitlab.ccook.jgtk.enums.GtkImageType;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.jgtk.errors.GErrorException;
import com.gitlab.ccook.util.Option;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GtkImageTest extends JGTKJUnitTest {

    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws GErrorException, IOException {
        GtkBox b = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 5);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(b);
        gtkApplication.addWindow(w);
        w.show();

        GtkImage emptyImage = new GtkImage();
        b.append(emptyImage);
        assertEquals(emptyImage.getStorageType(), GtkImageType.GTK_IMAGE_EMPTY);

        GtkImage imageFromFile = new GtkImage(getIconFile());
        b.append(imageFromFile);
        assertEquals(imageFromFile.getStorageType(), GtkImageType.GTK_IMAGE_PAINTABLE);

        GFileIcon gicon = new GFileIcon(getIconFile());
        GtkImage imageFromGIcon = new GtkImage(gicon);
        b.append(imageFromGIcon);

        Option<GIcon> gIconOption = imageFromGIcon.getGIcon();
        assertTrue(gIconOption.isDefined());
        assertEquals(gIconOption.get(), gicon);
        assertEquals(imageFromGIcon.getStorageType(), GtkImageType.GTK_IMAGE_GICON);

        for (int i = 0; i < 5; i++) {
            IconName iconName = getRandomIcons().get(i);
            GtkImage imageFromIconName = new GtkImage(iconName);
            b.append(imageFromIconName);
            Option<IconName> iconNameOption = imageFromIconName.getIconName();
            assertTrue(iconNameOption.isDefined());
            assertEquals(iconNameOption.get(), iconName);
            assertEquals(imageFromIconName.getIconSize(), GtkIconSize.GTK_ICON_SIZE_INHERIT);
            imageFromIconName.setIconSize(GtkIconSize.GTK_ICON_SIZE_LARGE);
            assertEquals(imageFromIconName.getIconSize(), GtkIconSize.GTK_ICON_SIZE_LARGE);
            assertFalse(imageFromIconName.getCustomPixelSize().isDefined());
            imageFromIconName.setCustomPixelSize(5);
            assertTrue(imageFromIconName.getCustomPixelSize().isDefined());
            assertEquals(imageFromIconName.getCustomPixelSize().get(), 5);
            assertEquals(imageFromIconName.getStorageType(), GtkImageType.GTK_IMAGE_ICON_NAME);
        }

        GdkTexture t = new GdkTexture(getIconFile());
        GtkImage imageFromPaintable = new GtkImage(t);
        b.append(imageFromPaintable);
        assertEquals(imageFromPaintable.getStorageType(), GtkImageType.GTK_IMAGE_PAINTABLE);
        assertTrue(imageFromPaintable.getPaintable().isDefined());
        assertEquals(imageFromPaintable.getPaintable().get(), t);

        GdkPixbuf buf = new GdkPixbuf(getIconFile());
        GtkImage imageFromBuf = new GtkImage(buf);
        b.append(imageFromBuf);
        assertEquals(imageFromBuf.getStorageType(), GtkImageType.GTK_IMAGE_PAINTABLE);
        imageFromBuf.clear();
        assertEquals(imageFromBuf.getStorageType(), GtkImageType.GTK_IMAGE_EMPTY);
        gtkApplication.quit();
    }
}
