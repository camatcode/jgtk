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
import com.gitlab.ccook.jgtk.bitfields.GtkDialogFlags;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("RedundantThrows")
public class GtkAppChooserDialogTest extends JGTKJUnitTest {
    @SuppressWarnings("deprecation")
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws Exception {
        GtkApplicationWindow parent = new GtkApplicationWindow(gtkApplication);
        GtkAppChooserDialog d = new GtkAppChooserDialog(parent, "text/plain", GtkDialogFlags.GTK_DIALOG_DESTROY_WITH_PARENT);
        assertFalse(d.getHeading().isDefined());
        d.setHeading("Heading!");
        assertEquals(d.getHeading().get(), "Heading!");

        assertNotNull(d.getWidget());
        assertNotNull(d.getWidget());
        d.show();
        parent.show();
        parent.close();
        gtkApplication.quit();
    }
}
