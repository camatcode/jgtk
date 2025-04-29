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
import com.gitlab.ccook.jgtk.enums.GtkRevealerTransitionType;


import static org.junit.jupiter.api.Assertions.*;

public class GtkRevealerTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws InterruptedException {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);

        GtkRevealer r = new GtkRevealer();
        assertFalse(r.getChild().isDefined());
        GtkLabel revealedLabel = new GtkLabel("Revealed text");
        r.setChild(revealedLabel);
        assertTrue(r.getChild().isDefined());
        assertEquals(r.getChild().get(), revealedLabel);

        assertEquals(r.getTransitionTimeMilliseconds(), 250);
        r.setTransitionTimeMilliseconds(500);
        assertEquals(r.getTransitionTimeMilliseconds(), 500);

        assertEquals(r.getTransitionType(), GtkRevealerTransitionType.GTK_REVEALER_TRANSITION_TYPE_SLIDE_DOWN);
        r.setTransitionType(GtkRevealerTransitionType.GTK_REVEALER_TRANSITION_TYPE_SLIDE_RIGHT);
        assertEquals(r.getTransitionType(), GtkRevealerTransitionType.GTK_REVEALER_TRANSITION_TYPE_SLIDE_RIGHT);

        assertFalse(r.hasRevealingStarted());
        r.reveal();
        assertTrue(r.hasRevealingStarted());
        Thread.sleep(500);
        assertTrue(r.isChildFullyRevealed());
        r.conceal();
        w.setChild(r);
        w.show();
        gtkApplication.quit();
    }
}
