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
package com.gitlab.ccook;

import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.gtk.GtkApplicationWindow;
import com.gitlab.ccook.jgtk.bitfields.GtkApplicationInhibitFlags;
import com.gitlab.ccook.jgtk.gtk.GtkWindow;
import com.gitlab.ccook.util.Option;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"unchecked", "unused"})
public class GtkApplicationTest extends JGTKJUnitTest {

    public void testGtkElement(GtkApplication app) {
        testAddRemoveWindow(app);
        testAccels(app);
        //TODO MenuBar/GMenu
        Option<GtkApplication.InhibitCookie> inhibitCookie = app.inhibitWindow(Option.NONE, "No Idle!", GtkApplicationInhibitFlags.GTK_APPLICATION_INHIBIT_IDLE);
        app.uninhibit(inhibitCookie.get());
        app.quit();
    }

    private void testAddRemoveWindow(GtkApplication app) {
        List<GtkWindow> windows = app.getWindows();
        assertEquals(0, windows.size());
        assertEquals(Option.NONE, app.getActiveWindow());
        GtkApplicationWindow window = new GtkApplicationWindow(app);
        assertTrue(app.getActiveWindow().isDefined());
        assertEquals(window, app.getActiveWindow().get());
        app.addWindow(window);
        windows = app.getWindows();
        assertEquals(window, app.getActiveWindow().get());
        assertEquals(window, app.getWindowById(window.getWindowId().get()).get());
        assertEquals(1, windows.size());
        app.removeWindow(windows.get(0));
        windows = app.getWindows();
        assertEquals(0, windows.size());
        assertEquals(Option.NONE, app.getActiveWindow());
        assertEquals(Option.NONE, app.getWindowById(window.getWindowId().get()));
    }

    private void testAccels(GtkApplication app) {
        String actionName = "app.quit";
        String actionName2 = "app.quit2";
        List<String> accelerators = app.getKeyboardAcceleratorsForAction(actionName);
        assertEquals(0, accelerators.size());
        accelerators = new ArrayList<>();
        accelerators.add("<Ctrl>Q");
        accelerators.add("<Ctrl>W");
        app.setKeyboardAccelerators(actionName, accelerators);
        app.setKeyboardAccelerators(actionName2, accelerators);
        accelerators = app.getKeyboardAcceleratorsForAction(actionName);
        assertEquals(2, accelerators.size());
        assertTrue(accelerators.contains("<Control>q"));
        assertTrue(accelerators.contains("<Control>w"));
        List<String> actions = app.getActionsForKeyboardAccelerator("<Ctrl>Q");
        assertEquals(2, actions.size());
        assertTrue(actions.contains(actionName));
        assertTrue(actions.contains(actionName2));

    }
}
