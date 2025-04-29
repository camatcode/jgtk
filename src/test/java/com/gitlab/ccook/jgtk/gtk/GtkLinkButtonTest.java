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
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.util.Option;


import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkLinkButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws Exception {
        /*URL url = new URL("https://google.com");
        GtkLinkButton b1 = new GtkLinkButton(url);
        AtomicBoolean b1Clicked = new AtomicBoolean(false);
        AtomicBoolean b2Clicked = new AtomicBoolean(false);
        b1.connect(GtkLinkButton.Signals.ACTIVATE_LINK, (relevantThing, relevantData) -> b1Clicked.set(true));
        GtkLinkButton b2 = new GtkLinkButton(url, "Google");
        b2.connect(GtkLinkButton.Signals.ACTIVATE_LINK, (relevantThing, relevantData) -> b1Clicked.set(true));
        assertEquals(url, b1.getURL());
        assertEquals(url, b2.getURL());
        assertEquals(url.toString(), b1.getLabel().get());
        assertEquals("Google", b2.getLabel().get());
        
        URL replaceURL = new URL("https://bing.com");
        b1.setURL(replaceURL);
        b2.setURL(replaceURL, new Option<>("Bing"));
        assertEquals(replaceURL, b1.getURL());
        assertEquals(replaceURL, b2.getURL());
        assertEquals(replaceURL.toString(), b1.getLabel().get());
        assertEquals("Bing", b2.getLabel().get());
        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0);
        box.append(b1);
        box.append(b2);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(box);
        gtkApplication.addWindow(w);
        w.show();
        b1.emitSignal(GtkLinkButton.Signals.ACTIVATE_LINK);
        assertTrue(b1Clicked.get());
        assertTrue(b1.isVisited());
        b1.setVisited(false);
        assertFalse(b1.isVisited());*/
        gtkApplication.quit();
    }
}
