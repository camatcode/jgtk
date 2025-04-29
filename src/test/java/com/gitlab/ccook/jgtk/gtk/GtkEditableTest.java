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

import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.util.Pair;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class GtkEditableTest {
    public static void testEditable(GtkEditable editable) {
        assertTrue(editable.doesAllowUndoRedoActions());
        editable.shouldAllowUndoRedoActions(false);
        assertFalse(editable.doesAllowUndoRedoActions());

        String currentText = "012345678901234567890123456789";
        editable.setText(currentText);
        assertEquals(currentText, editable.getText());

        editable.deleteTextByPosition(0, 4);
        currentText = currentText.substring(4);
        assertEquals(currentText, editable.getText());

        assertEquals(editable.getAlignment(), 0.0f);
        editable.setAlignment(0.1f);
        assertEquals(editable.getAlignment(), 0.1f);

        assertEquals(editable.getCursorPosition(), 0);
        editable.setCursorPosition(1);
        assertEquals(editable.getCursorPosition(), 1);

        assertTrue(editable.getDelegate().isDefined());

        assertFalse(editable.getMaxCharacters().isDefined());
        editable.setMaxCharacters(50);
        assertTrue(editable.getMaxCharacters().isDefined());
        assertEquals(editable.getMaxCharacters().get(), 50);

        assertFalse(editable.getCharacterWidth().isDefined());
        editable.setCharacterWidth(10);
        assertTrue(editable.getCharacterWidth().isDefined());
        assertEquals(editable.getCharacterWidth().get(), 10);

        assertEquals(currentText.substring(3, 6), editable.getTextFromPosition(3, 6));

        editable.insertText("01234", 0);
        currentText = "01234" + currentText;
        assertEquals(currentText, editable.getText());

        assertTrue(editable.isEditable());
        editable.setEditable(false);
        assertFalse(editable.isEditable());
        editable.setEditable(true);

        editable.selectRegion(0, 5);
        assertTrue(editable.getTextSelectedBounds().isDefined());
        assertEquals(editable.getTextSelectedBounds().get(), new Pair<>(0, 5));
        editable.deleteSelectedText();
        currentText = currentText.substring(5);
        assertEquals(currentText, editable.getText());

        AtomicBoolean changed = new AtomicBoolean(false);
        editable.connect(GtkEditable.Signals.CHANGED, (relevantThing, relevantData) -> changed.set(true));
        editable.setText("Search");
        assertTrue(changed.get());
    }


}
