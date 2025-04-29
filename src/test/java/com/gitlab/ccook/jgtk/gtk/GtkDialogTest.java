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
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.util.Option;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"deprecation", "RedundantThrows"})
public class GtkDialogTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws IOException, URISyntaxException {
        GtkDialog dialog = new GtkDialog();
        dialog.setAssociatedApplication(gtkApplication);
        GtkButton b = new GtkButton("Action!");
        dialog.addActionWidget(b, 1);
        assertEquals(dialog.getWidgetForResponseId(1).get(), b);
        assertEquals(dialog.getResponseIdForWidget(b).get(), 1);

        Option<GtkWidget> button2 = dialog.addButton("Button 2", 2);
        assertEquals(dialog.getWidgetForResponseId(2).get(), button2.get());
        assertEquals(dialog.getResponseIdForWidget(button2.get()).get(), 2);

        GtkBox contentArea = (GtkBox) dialog.getContentArea();

        Option<GtkHeaderBar> headerBar = dialog.getHeaderBar();
        assertFalse(headerBar.isDefined());

        dialog.setDefaultResponse(1);

        dialog.setResponseSensitive(1, true);

        AtomicBoolean responseEmitted = new AtomicBoolean(false);
        dialog.connect(GtkDialog.Signals.RESPONSE, (relevantThing, relevantData) -> responseEmitted.set(true));
        dialog.emitResponse(1);
        assertTrue(responseEmitted.get());
        dialog.show();
        gtkApplication.quit();
    }
}
