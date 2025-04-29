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
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;


import static com.gitlab.ccook.jgtk.gtk.GtkButton.Signals.CLICKED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkFixedTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkFixed fixed = new GtkFixed();
        GtkButton button = new GtkButton("Fixed Button");
        fixed.addChild(button, 0, 0);
        GtkButton button2 = new GtkButton("Other Button");
        fixed.addChild(button2, 20, 20);
        assertTrue(fixed.getChildTransform(button2).isDefined());
        assertEquals(fixed.getChildTransform(button2).get().toString(), "translate(20, 20)");
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);

        w.setChild(fixed);
        gtkApplication.addWindow(w);
        w.show();

        button2.connect(CLICKED, ((relevantThing, relevantData) -> {
            Option<Pair<Double, Double>> buttonPosition = fixed.getChildAbsolutePosition(button);
            Option<Pair<Double, Double>> button2Position = fixed.getChildAbsolutePosition(button2);
            double relativeX = button2Position.get().getFirst() - buttonPosition.get().getFirst();
            double relativeY = button2Position.get().getSecond() - buttonPosition.get().getSecond();
        }));//TODO position only returns meaningful numbers when size is allocated
        gtkApplication.quit();
    }
}
