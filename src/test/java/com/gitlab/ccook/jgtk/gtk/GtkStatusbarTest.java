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

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("deprecation")
public class GtkStatusbarTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setSizeRequest(500, 500);
        GtkStatusbar bar = new GtkStatusbar();
        int testing2Id = bar.push("Testing 2...");
        int testingId = bar.push("Testing..");
        bar.pop();
        int testing3Id = bar.push("Testing 3");
        bar.remove(testing2Id);
        AtomicBoolean pushed = new AtomicBoolean(false);
        bar.connect(GtkStatusbar.Signals.TEXT_PUSHED, (relevantThing, relevantData) -> pushed.set(true)
        );
        bar.clear();
        bar.push("Final...");
        assertTrue(pushed.get());
        w.setChild(bar);
        w.show();
        gtkApplication.quit();
    }
}
