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
import com.gitlab.ccook.jgtk.bitfields.GtkDialogFlags;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("RedundantThrows")
public class GtkAppChooserWidgetTest extends JGTKJUnitTest {
    @SuppressWarnings("deprecation")
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws Exception {
        GtkApplicationWindow parent = new GtkApplicationWindow(gtkApplication);
        GtkAppChooserDialog d = new GtkAppChooserDialog(parent, "text/plain", GtkDialogFlags.GTK_DIALOG_DESTROY_WITH_PARENT);
        assertNotNull(d.getWidget());

        GtkAppChooserWidget w = d.getWidget();

        String defaultTextStart = "No applications found for";
        assertTrue(w.getDefaultText().isDefined());
        assertTrue(w.getDefaultText().get().startsWith(defaultTextStart));

        String newDefault = "Nothing here";
        w.setDefaultText(newDefault);
        assertTrue(w.getDefaultText().isDefined());
        assertEquals(w.getDefaultText().get(), newDefault);

        assertFalse(w.doesShowAll());
        w.shouldShowAll(true);
        assertTrue(w.doesShowAll());
        w.shouldShowAll(false);
        assertFalse(w.doesShowAll());

        assertFalse(w.doesShowDefault());
        w.shouldShowDefault(true);
        assertTrue(w.doesShowDefault());
        w.shouldShowDefault(false);
        assertFalse(w.doesShowDefault());

        assertFalse(w.doesShowFallback());
        w.shouldShowFallback(true);
        assertTrue(w.doesShowFallback());
        w.shouldShowFallback(false);
        assertFalse(w.doesShowFallback());

        assertFalse(w.doesShowOther());
        w.shouldShowOther(true);
        assertTrue(w.doesShowOther());
        w.shouldShowOther(false);
        assertFalse(w.doesShowOther());

        assertTrue(w.doesShowRecommended());
        w.shouldShowRecommended(false);
        assertFalse(w.doesShowRecommended());
        w.shouldShowRecommended(true);
        assertTrue(w.doesShowRecommended());


        d.show();
        parent.show();
        parent.close();
        gtkApplication.quit();
    }
}
