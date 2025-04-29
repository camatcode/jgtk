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
import com.gitlab.ccook.jgtk.GFileIcon;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.JGTKObject;


import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpellCheckingInspection")
public class GtkAppChooserButtonTest extends JGTKJUnitTest {
    @SuppressWarnings("deprecation")
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws Exception {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        GtkAppChooserButton b = new GtkAppChooserButton("text/plain");
        GtkAppChooserButton inflated = (GtkAppChooserButton) JGTKObject.newObjectFromType(b.getCReference(), GtkAppChooserButton.class);
        assertEquals(b, inflated);
        b.appendSeparator();
        b.shouldShowChooseOtherApplicationDialog(true);
        assertTrue(b.doesShowChooseOtherApplicationDialog());
        b.shouldShowChooseOtherApplicationDialog(false);
        File iconFile = new File("src/test/resources/3d-add-hole.svg");
        assertTrue(iconFile.exists());
        b.appendCustomItem("customitem", "customitemlabel", new GFileIcon(iconFile));
        b.selectCustomItem("customitem");
        assertFalse(b.doesShowChooseOtherApplicationDialog());
        assertTrue(b.isModal());
        b.shouldBeModal(false);
        assertFalse(b.isModal());
        b.shouldBeModal(true);
        String dialogHeading = "Choose one, <i>not</i> two";
        b.setDialogHeading(dialogHeading);
        assertEquals(dialogHeading, b.getHeadingForChooseOtherApplicationDialog().get());
        boolean showDefaultAppAtTop = true;
        b.shouldShowDefaultApplicationAtTop(showDefaultAppAtTop);
        assertEquals(showDefaultAppAtTop, b.doesShowDefaultApplicationAtTop());
        AtomicBoolean changed = new AtomicBoolean(false);
        b.connect(GtkAppChooserButton.Signals.CHANGED, (relevantThing, relevantData) -> {
            assertNotNull(relevantThing);
            Class<? extends JGTKObject> type = JGTKObject.getType(relevantThing);
            assertEquals(b.getCReference(), relevantThing);
            changed.set(true);
        });
        w.setChild(b);
        w.show();
        b.emitSignal(GtkAppChooserButton.Signals.CHANGED);
        Thread.sleep(1000);
        assertTrue(changed.get());
        gtkApplication.quit();
    }
}
