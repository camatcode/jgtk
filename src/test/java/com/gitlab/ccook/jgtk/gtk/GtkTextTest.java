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
import com.gitlab.ccook.jgtk.enums.GtkInputPurpose;
import com.gitlab.ccook.jgtk.enums.GtkOrientation;
import com.gitlab.ccook.util.Option;

import static org.junit.jupiter.api.Assertions.*;

public class GtkTextTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setSizeRequest(500, 500);
        GtkBox b = new GtkBox(GtkOrientation.GTK_ORIENTATION_HORIZONTAL, 5);
        GtkText text1 = new GtkText("hello\nbye");
        GtkText text2 = new GtkText("some initial chars");
        b.append(text1);
        b.append(text2);
        w.setChild(b);

        assertFalse(text1.doesEnterActivateDefault());
        text1.shouldEnterActivateDefault(true);
        assertTrue(text1.doesEnterActivateDefault());

        assertFalse(text1.doesOverwrite());
        text1.shouldOverwrite(true);
        assertTrue(text1.doesOverwrite());

        assertFalse(text1.doesPropagateTextWidth());
        text1.shouldPropagateTextWidth(true);
        assertTrue(text1.doesPropagateTextWidth());

        assertFalse(text1.doesSuggestEmojiReplacements());
        text1.shouldSuggestEmojiReplacements(true);
        assertTrue(text1.doesSuggestEmojiReplacements());

        assertFalse(text1.doesTruncateMultiline());
        text1.shouldTruncateMultiline(true);
        assertTrue(text1.doesTruncateMultiline());

        assertFalse(text1.getAttributes().isDefined());
        GtkLabel label1 = new GtkLabel("<i><b><a href=\"http://google.com\">hello\nbye</a></b></i>");
        label1.usePangoMarkup(true);

        Option<PangoAttrList> effectiveAttributes = label1.getEffectiveAttributes();
        assertTrue(effectiveAttributes.isDefined());
        text1.setAttributes(effectiveAttributes.get());
        assertTrue(text1.getAttributes().isDefined());

        text2.getBuffer().insertText(0, "inserted");
        GtkEntryBuffer override = new GtkEntryBuffer("Override");
        text2.setBuffer(override);
        assertEquals(text2.getBuffer(), override);

        assertFalse(text2.getExtraMenu().isDefined());
        GMenu menu = makeMainMenu();
        text2.setExtraMenu(menu);
        assertTrue(text2.getExtraMenu().isDefined());
        assertEquals(text2.getExtraMenu().get(), menu);

        assertTrue(text1.getInputHints().isEmpty());
        text1.setInputHints(GtkInputHints.GTK_INPUT_HINT_NO_SPELLCHECK, GtkInputHints.GTK_INPUT_HINT_UPPERCASE_CHARS);
        assertFalse(text1.getInputHints().isEmpty());
        assertTrue(text1.getInputHints().contains(GtkInputHints.GTK_INPUT_HINT_NO_SPELLCHECK));
        assertTrue(text1.getInputHints().contains(GtkInputHints.GTK_INPUT_HINT_UPPERCASE_CHARS));

        assertEquals(text1.getInputPurpose(), GtkInputPurpose.GTK_INPUT_PURPOSE_FREE_FORM);
        text1.setInputPurpose(GtkInputPurpose.GTK_INPUT_PURPOSE_DIGITS);
        assertEquals(text1.getInputPurpose(), GtkInputPurpose.GTK_INPUT_PURPOSE_DIGITS);

        assertFalse(text1.getMaxLength().isDefined());
        text1.setMaxLength(5);
        assertTrue(text1.getMaxLength().isDefined());
        assertEquals(text1.getMaxLength().get(), 5);

        assertFalse(text1.getPasswordCharacter().isDefined());
        text1.setPasswordCharacter('X');
        assertTrue(text1.getPasswordCharacter().isDefined());
        assertEquals(text1.getPasswordCharacter().get(), 'X');

        text1.setBuffer(new GtkEntryBuffer());
        text1.setAttributes(null);

        assertFalse(text1.getPlaceholderText().isDefined());
        text1.setPlaceholderText("Placeholder");
        assertTrue(text1.getPlaceholderText().isDefined());
        assertEquals(text1.getPlaceholderText().get(), "Placeholder");

        //TODO tab-stops
        assertEquals(text1.getTextLength(), 0);
        text1.setText("Hello");
        assertEquals(text1.getTextLength(), 5);

        assertTrue(text1.grabFocusWithoutSelect());

        assertTrue(text1.isTextVisible());
        text1.setTextVisible(false);
        assertFalse(text1.isTextVisible());

        text1.resetPasswordCharacter();

        w.show();
        gtkApplication.quit();
    }
}
