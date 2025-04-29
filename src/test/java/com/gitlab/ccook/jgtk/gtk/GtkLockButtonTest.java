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
import com.gitlab.ccook.jgtk.GSimplePermission;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;


import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class GtkLockButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws Exception {
        GSimplePermission allowPermission = new GSimplePermission(true);
        GSimplePermission denyPermission = new GSimplePermission(false);
        GtkLockButton b = new GtkLockButton();
        b.setPermission(denyPermission);
        assertTrue(b.getPermission().isDefined());
        assertEquals(b.getPermission().get(), denyPermission);
        b.setPermission(allowPermission);
        assertTrue(b.getPermission().isDefined());
        assertEquals(b.getPermission().get(), allowPermission);
        assertEquals("Lock", b.getLockText());
        String lockTextReplace = "LockMe";
        b.setLockText(lockTextReplace);
        assertEquals(lockTextReplace, b.getLockText());
        String lockToolTip = "Prompt to lock";
        String unlockToolTip = "Prompt to lock";
        b.setPromptToLockToolTip(lockToolTip);
        b.setPromptToUnlockToolTip(unlockToolTip);
        assertEquals(lockToolTip, b.getPromptToLockToolTip());
        assertEquals(unlockToolTip, b.getPromptToUnlockToolTip());
        String unauthToolTip = "Unauthorized";
        b.setUnauthorizedToolTip(unauthToolTip);
        assertEquals(unauthToolTip, b.getUnauthorizedToolTip());
        GtkLinkButton b2 = new GtkLinkButton(new URL("https://google.com"));
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0);
        box.append(b);
        box.insertChildAfter(b2, b);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(box);
        gtkApplication.addWindow(w);
        w.show();
        gtkApplication.quit();
    }
}
