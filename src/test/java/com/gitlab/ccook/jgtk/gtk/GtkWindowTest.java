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
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import org.joda.time.DateTime;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class GtkWindowTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        Pair<Integer, Integer> defaultSize = w.getDefaultSize();
        assertEquals(defaultSize, new Pair<>(200, 200));
        w.setSizeRequest(200, 200);

        assertFalse(w.getTitleBar().isDefined());
        GtkLabel titleBar = new GtkLabel("TitleBar");
        w.setTitleBar(titleBar);
        assertTrue(w.getTitleBar().isDefined());
        assertEquals(w.getTitleBar().get(), titleBar);
        w.fullscreen();
        assertTrue(w.isFullscreen());
        w.maximize();
        assertTrue(w.isMaximized());
        w.show();
        Option<IconName> defaultIconName = GtkWindow.getDefaultIconName();
        assertFalse(defaultIconName.isDefined());
        IconName defaultIcon = getRandomIcons().iterator().next();
        GtkWindow.setDefaultIconName(defaultIcon);
        defaultIconName = GtkWindow.getDefaultIconName();
        assertTrue(defaultIconName.isDefined());
        assertEquals(defaultIconName.get(), defaultIcon);

        Set<GtkWindow> topLevelWindows = GtkWindow.getTopLevelWindows();
        assertFalse(topLevelWindows.isEmpty());

        GtkWindow.setInteractiveDebugger(true);
        GtkWindow.setInteractiveDebugger(false);

        assertFalse(w.areFocusRectanglesVisible());
        w.setFocusRectanglesVisible(true);
        assertTrue(w.areFocusRectanglesVisible());

        assertFalse(w.areMnemonicsVisible());
        w.setMnemonicsVisible(true);
        assertTrue(w.areMnemonicsVisible());

        assertFalse(w.doesDestroyWithParent());
        w.shouldDestroyWithParent(true);
        assertTrue(w.doesDestroyWithParent());

        assertTrue(w.doesF10ActivateMenuBar());
        w.shouldF10KeyActivatesMenuBar(false);
        assertFalse(w.doesF10ActivateMenuBar());

        GtkLabel label = new GtkLabel("Label");
        w.setChild(label);
        assertTrue(w.isFullscreen());

        GdkDisplay display = new GdkDisplay();
        GListModel<GdkMonitor> monitors = display.getMonitors();
        GListModelSet<GdkMonitor> gdkMonitors = new GListModelSet<>(monitors);
        assertFalse(gdkMonitors.isEmpty());
        w.fullscreenOnMonitor(gdkMonitors.iterator().next());

        assertTrue(w.getAssociatedApplication().isDefined());
        assertEquals(w.getAssociatedApplication().get(), gtkApplication);

        assertTrue(w.getChild().isDefined());
        assertEquals(w.getChild().get(), label);

        assertFalse(w.getDefaultWidget().isDefined());
        w.setDefaultWidget(label);
        assertTrue(w.getDefaultWidget().isDefined());
        assertEquals(w.getDefaultWidget().get(), label);

        assertFalse(w.getFocusedWidget().isDefined());

        assertFalse(w.getIconName().isDefined());
        IconName iconName = getRandomIcons().iterator().next();
        w.setIconName(iconName);
        assertTrue(w.getIconName().isDefined());
        assertEquals(w.getIconName().get(), iconName);

        assertFalse(w.getTransientFor().isDefined());
        GtkWindow parent = new GtkWindow();
        w.setTransientFor(parent);
        assertTrue(w.getTransientFor().isDefined());
        assertEquals(w.getTransientFor().get(), parent);

        GtkWindowGroup windowGroup = w.getWindowGroup();

        assertFalse(w.hasWindowGroup());
        GList<GtkWindow> windows = windowGroup.getWindows();
        assertTrue(windows.size() > 0);

        assertTrue(w.isDeletable());
        w.setDeletable(false);
        assertFalse(w.isDeletable());
        assertFalse(w.hidesOnClose());
        w.setHideOnClose(false);
        w.setStartupNotificationId("notificationId");
        assertFalse(w.isActive());

        assertTrue(w.doesDestroyWithParent());


        assertTrue(w.isDecorated());
        w.setDecorated(false);
        assertFalse(w.isDecorated());

        assertTrue(w.isMaximized());
        assertFalse(w.isModal());
        w.setModal(true);
        assertTrue(w.isModal());

        assertTrue(w.isResizable());
        w.setResizable(false);
        assertFalse(w.isResizable());
        w.unfullscreen();
        w.unmaximize();
        w.minimize();
        w.present();
        w.presentWithTimestamp(DateTime.now());
        w.close();
        w.destroy();
        gtkApplication.quit();
    }
}
