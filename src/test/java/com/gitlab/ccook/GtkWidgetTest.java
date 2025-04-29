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
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.enums.GtkTextDirection;
import com.gitlab.ccook.jgtk.gtk.GtkButton;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkWidgetTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {

        GtkTextDirection defaultTextDirection = GtkWidget.getDefaultTextDirection();
        // System.out.println(defaultTextDirection);
        GtkWidget.setDefaultTextDirection(GtkTextDirection.GTK_TEXT_DIR_RTL);
        // System.out.println(GtkWidget.getDefaultTextDirection());
        String actionName = "clipboard.paste";
        GtkWidget w = new GtkButton();
        final AtomicBoolean wasActivated = new AtomicBoolean(false);
        w.connect("activate", (relevantThing, relevantData) -> {
            wasActivated.set(true);
            //System.out.println("Was Activated!");
        });
        w.setIsActionEnabled(actionName, true);
        assertTrue(w.activate());
        w.emitSignal("activate");
        assertTrue(wasActivated.get());
        //  System.out.println(w.activateAction("clicked"));
        w.activateDefault();
        //w.addController(null);//TODO
        gtkApplication.quit();
    }
}
