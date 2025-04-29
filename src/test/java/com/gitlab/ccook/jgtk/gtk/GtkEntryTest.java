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
import com.gitlab.ccook.jgtk.bitfields.GtkInputHints;
import com.gitlab.ccook.jgtk.enums.GtkEntryIconPosition;
import com.gitlab.ccook.jgtk.enums.GtkImageType;
import com.gitlab.ccook.jgtk.enums.GtkInputPurpose;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.util.Option;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("deprecation")
public class GtkEntryTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws IOException {
        GFileIcon gFileIcon = new GFileIcon(getIconFile());

        GtkEntry e = new GtkEntry();
        Option<GIcon> gIcon = e.getGIcon(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY);
        assertFalse(gIcon.isDefined());
        e.setIcon(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY, gFileIcon);
        gIcon = e.getGIcon(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY);
        e.setIcon(GtkEntryIconPosition.GTK_ENTRY_ICON_SECONDARY, gFileIcon);
        assertTrue(gIcon.isDefined());
        assertEquals(gIcon.get(), gFileIcon);
        GtkEntryBuffer eb = new GtkEntryBuffer();
        eb.insertText(0, "Entry Buffer Text");
        GtkEntry e2 = new GtkEntry(eb);

        assertTrue(e.canIconBeActivated(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY));
        e.setIconBeActivated(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY, false);
        assertFalse(e.canIconBeActivated(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY));
        e.setIconBeActivated(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY, true);

        assertFalse(e2.canOverwrite());
        e2.doesOverwrite(true);
        assertTrue(e2.canOverwrite());

        assertFalse(e.getAttributes().isDefined());
        PangoAttrList attributes = new PangoAttrList();
        e.setAttributes(attributes);
        assertTrue(e.getAttributes().isDefined());
        assertEquals(attributes, e.getAttributes().get());

        GtkEntryBuffer buffer = e2.getBuffer();
        assertEquals(eb, buffer);
        eb = new GtkEntryBuffer("newBuffer");
        e2.setBuffer(eb);
        assertEquals(eb, e2.getBuffer());

        assertFalse(e2.getCompletion().isDefined());
        GtkEntryCompletion completion = new GtkEntryCompletion();
        e2.setCompletion(completion);
        assertTrue(e2.getCompletion().isDefined());
        assertEquals(e2.getCompletion().get(), completion);

        assertFalse(e2.getExtraMenu().isDefined());
        GMenu menu = makeMainMenu();
        e2.setExtraMenu(menu);
        assertTrue(e2.getExtraMenu().isDefined());
        assertEquals(e2.getExtraMenu().get(), menu);

        assertEquals(e2.getHorizontalAlignment(), 0.0f);
        e2.setAlignment(0.5f);
        assertEquals(e2.getHorizontalAlignment(), 0.5f);

        GtkBox box = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 0);
        box.append(e);
        box.append(e2);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(box);
        gtkApplication.addWindow(w);
        w.show();
        GdkRectangle iconArea = e.getIconArea(GtkEntryIconPosition.GTK_ENTRY_ICON_SECONDARY);
        // assertEquals(iconArea, new GdkRectangle(0, 0, 0, 0));

        Option<Integer> iconAtPosition = e.getIconAtPosition(0, 0);
        assertEquals(iconAtPosition.get(), 0);

        assertFalse(e.getIconDragSourceIndex().isDefined());
        //TODO test DND
        //TODO setIconDragSource

        assertFalse(e.getIconName(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY).isDefined());

        assertEquals(GtkImageType.GTK_IMAGE_GICON, e.getIconStorageType(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY));
        e.setInputHints(GtkInputHints.GTK_INPUT_HINT_UPPERCASE_CHARS, GtkInputHints.GTK_INPUT_HINT_NO_SPELLCHECK);
        List<GtkInputHints> hints = e.getInputHints();
        assertNotNull(hints);
        assertEquals(hints.size(), 2);
        assertTrue(hints.contains(GtkInputHints.GTK_INPUT_HINT_UPPERCASE_CHARS));
        assertTrue(hints.contains(GtkInputHints.GTK_INPUT_HINT_NO_SPELLCHECK));

        assertEquals(e.getInputPurpose(), GtkInputPurpose.GTK_INPUT_PURPOSE_FREE_FORM);
        e.setInputPurpose(GtkInputPurpose.GTK_INPUT_PURPOSE_NAME);
        assertEquals(e.getInputPurpose(), GtkInputPurpose.GTK_INPUT_PURPOSE_NAME);

        assertFalse(e.getMaxLength().isDefined());
        e.setMaxLength(20);
        assertTrue(e.getMaxLength().isDefined());
        assertEquals(e.getMaxLength().get(), 20);

        assertFalse(e.getPaintableFromIcon(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY).isDefined());

        e.setInputPurpose(GtkInputPurpose.GTK_INPUT_PURPOSE_PASSWORD);
        assertFalse(e.getPasswordCharacter().isDefined());
        e.setTextVisible(false);
        assertTrue(e.getPasswordCharacter().isDefined());
        e.setPasswordCharacter('c');
        assertEquals(e.getPasswordCharacter().get(), 'c');
        e.setPasswordCharacter(null);

        assertFalse(e.getPlaceholderText().isDefined());
        e.setPlaceholderText("placeholder");
        assertTrue(e.getPlaceholderText().isDefined());

        assertEquals(e.getProgressFraction(), 0.0);
        e.setProgressFraction(0.50);
        assertEquals(e.getProgressFraction(), 0.5);
        assertEquals(e.getProgressPulseStep(), 0.1);
        e.setProgressPulseStep(0.2);
        assertEquals(e.getProgressPulseStep(), 0.2);
        e.pulseProgress();
        e.resetInputMethodContext();

        assertTrue(e.hasBeveledFrame());
        e.setBeveledFrame(false);
        assertFalse(e.hasBeveledFrame());

        assertFalse(e.doesEnterActivateDefault());
        e.shouldEnterActivateDefault(true);
        assertTrue(e.doesEnterActivateDefault());

        assertTrue(e.isIconSensitive(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY));
        e.setIconSensitive(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY, false);
        assertFalse(e.isIconSensitive(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY));

        e.setTooltipMarkup(GtkEntryIconPosition.GTK_ENTRY_ICON_SECONDARY, "<b>tooltip</b>");
        assertEquals(e.getTooltipMarkup(GtkEntryIconPosition.GTK_ENTRY_ICON_SECONDARY).get(), "<b>tooltip</b>");

        e.setTooltipText(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY, "tooltip");
        assertEquals(e.getTooltipText(GtkEntryIconPosition.GTK_ENTRY_ICON_PRIMARY).get(), "tooltip");

        assertFalse(e.getTabStops().isDefined());
        e.setTabStops(new PangoTabArray(5, true));
        assertTrue(e.getTabStops().isDefined());

        AtomicBoolean iconPress = new AtomicBoolean(false);
        e.connect(GtkEntry.Signals.ICON_PRESS, (relevantThing, relevantData) -> iconPress.set(true));
        e.emitSignal(GtkEntry.Signals.ICON_PRESS);
        assertTrue(iconPress.get());
        gtkApplication.quit();

    }
}
