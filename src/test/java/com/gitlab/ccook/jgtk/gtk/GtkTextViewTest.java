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
import com.gitlab.ccook.jgtk.enums.GtkJustification;
import com.gitlab.ccook.jgtk.enums.GtkTextWindowType;
import com.gitlab.ccook.jgtk.enums.GtkWrapMode;

import static org.junit.jupiter.api.Assertions.*;

public class GtkTextViewTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setSizeRequest(500, 500);
        GtkTextView view1 = new GtkTextView("Text");
        GtkTextBuffer buffer;
        view1.setTextEditable(true);

        GtkButton overlayButton = new GtkButton("Cursor Visible");
        view1.addOverlay(overlayButton, 50, 50);
        overlayButton.connect(GtkButton.Signals.CLICKED, ((relevantThing, relevantData) -> view1.setCursorVisible(!view1.isCursorVisible())));

        assertTrue(view1.doesAcceptTabs());
        view1.shouldAcceptTabs(false);
        assertFalse(view1.doesAcceptTabs());

        assertFalse(view1.doesOverwrite());
        view1.shouldOverwrite(true);
        assertTrue(view1.doesOverwrite());
        view1.shouldOverwrite(false);

        assertEquals(view1.getBottomMarginSize(), 0);
        view1.setBottomMarginSize(5);
        assertEquals(view1.getBottomMarginSize(), 5);

        assertNotNull(view1.getBuffer());
        buffer = new GtkTextBuffer("New Text");
        view1.setBuffer(buffer);
        assertEquals(buffer, view1.getBuffer());

        assertFalse(view1.getExtraMenu().isDefined());
        GMenuModel extraMenu = makeMainMenu();
        view1.setExtraMenu(extraMenu);
        assertTrue(view1.getExtraMenu().isDefined());
        assertEquals(view1.getExtraMenu().get(), extraMenu);

        assertFalse(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_LEFT).isDefined());
        GtkLabel gutterLeft = new GtkLabel("Gutter left");
        view1.setGutter(GtkTextWindowType.GTK_TEXT_WINDOW_LEFT, gutterLeft);
        assertTrue(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_LEFT).isDefined());
        GtkTextViewChild cLeft = (GtkTextViewChild) view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_LEFT).get();
        assertTrue(cLeft.getChild().isDefined());
        assertEquals(gutterLeft, cLeft.getChild().get());

        assertFalse(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_RIGHT).isDefined());
        GtkLabel gutterRight = new GtkLabel("Gutter Right");
        view1.setGutter(GtkTextWindowType.GTK_TEXT_WINDOW_RIGHT, gutterRight);
        assertTrue(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_RIGHT).isDefined());
        GtkTextViewChild cRight = (GtkTextViewChild) view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_RIGHT).get();
        assertTrue(cRight.getChild().isDefined());
        assertEquals(gutterRight, cRight.getChild().get());

        assertFalse(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_TOP).isDefined());
        GtkLabel gutterTop = new GtkLabel("Gutter Top");
        view1.setGutter(GtkTextWindowType.GTK_TEXT_WINDOW_TOP, gutterTop);
        assertTrue(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_TOP).isDefined());
        GtkTextViewChild cTop = (GtkTextViewChild) view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_TOP).get();
        assertTrue(cTop.getChild().isDefined());
        assertEquals(gutterTop, cTop.getChild().get());

        assertFalse(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_BOTTOM).isDefined());
        GtkLabel gutterBottom = new GtkLabel("Gutter Bottom");
        view1.setGutter(GtkTextWindowType.GTK_TEXT_WINDOW_BOTTOM, gutterBottom);
        assertTrue(view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_BOTTOM).isDefined());
        GtkTextViewChild cBottom = (GtkTextViewChild) view1.getGutter(GtkTextWindowType.GTK_TEXT_WINDOW_BOTTOM).get();
        assertTrue(cBottom.getChild().isDefined());
        assertEquals(gutterBottom, cBottom.getChild().get());

        view1.shouldAcceptTabs(true);
        assertEquals(view1.getIndentSize(), 0);
        view1.setIndentSize(5);
        assertEquals(view1.getIndentSize(), 5);

        assertTrue(view1.getInputHints().isEmpty());
        view1.setInputHints(GtkInputHints.GTK_INPUT_HINT_NO_SPELLCHECK, GtkInputHints.GTK_INPUT_HINT_LOWERCASE);
        assertEquals(view1.getInputHints().size(), 2);
        assertTrue(view1.getInputHints().contains(GtkInputHints.GTK_INPUT_HINT_NO_SPELLCHECK) && view1.getInputHints().contains(GtkInputHints.GTK_INPUT_HINT_LOWERCASE));

        assertEquals(view1.getInputPurpose(), GtkInputPurpose.GTK_INPUT_PURPOSE_FREE_FORM);
        view1.setInputPurpose(GtkInputPurpose.GTK_INPUT_PURPOSE_ALPHA);
        assertEquals(view1.getInputPurpose(), GtkInputPurpose.GTK_INPUT_PURPOSE_ALPHA);

        assertEquals(view1.getJustification(), GtkJustification.GTK_JUSTIFY_LEFT);
        view1.setJustification(GtkJustification.GTK_JUSTIFY_CENTER);
        assertEquals(view1.getJustification(), GtkJustification.GTK_JUSTIFY_CENTER);

        assertNotNull(view1.getLTRContext());

        assertEquals(view1.getLeftMarginSize(), 0);
        view1.setLeftMarginSize(5);
        assertEquals(view1.getLeftMarginSize(), 5);

        assertEquals(view1.getPixelsAboveParagraphs(), 0);
        view1.setPixelsAboveParagraphs(5);
        assertEquals(view1.getPixelsAboveParagraphs(), 5);

        assertEquals(view1.getPixelsBelowParagraphs(), 0);
        view1.setPixelsBelowParagraphs(5);
        assertEquals(view1.getPixelsBelowParagraphs(), 5);

        assertEquals(view1.getPixelsInsideWrap(), 0);
        view1.setPixelsInsideWrap(5);
        assertEquals(view1.getPixelsInsideWrap(), 5);

        assertNotNull(view1.getRTLContext());

        assertEquals(view1.getRightMarginSize(), 0);
        view1.setRightMarginSize(5);
        assertEquals(view1.getRightMarginSize(), 5);

        assertFalse(view1.getTabStops().isDefined());
        //TODO setTabStops()

        assertEquals(view1.getTopMarginSize(), 0);
        view1.setTopMarginSize(5);
        assertEquals(view1.getTopMarginSize(), 5);

        assertEquals(view1.getVisibleRectangle(), new GdkRectangle(0, -5, 0, 0));

        assertEquals(view1.getWrapMode(), GtkWrapMode.GTK_WRAP_NONE);
        view1.setWrapMode(GtkWrapMode.GTK_WRAP_WORD);
        assertEquals(view1.getWrapMode(), GtkWrapMode.GTK_WRAP_WORD);

        assertFalse(view1.isMonospace());
        view1.setMonospace(true);
        assertTrue(view1.isMonospace());

        assertTrue(view1.isTextEditable());
        view1.setTextEditable(false);
        assertFalse(view1.isTextEditable());
        view1.setTextEditable(true);

        view1.moveOverlay(overlayButton, 100, 100);

        view1.resetInputMethodContext();

        //TODO addChildAtAnchor
        //TODO removeChild
        //TODO filterKeypress
        //TODO getCursorPositions
        //TODO getIteratorAtBufferCoordinates
        //TODO getIteratorLocation
        //TODO getIteratorPointingToCharAtBufferCoordinates
        //TODO getLineAtY
        //TODO getLineYRange
        //TODO isIteratorAtDisplayLineStart
        //TODO moveBackwardOneDisplayLine
        //TODO moveBackwardToDisplayLineStart
        //TODO moveForwardOneDisplayLine
        //TODO moveForwardToDisplayLineStart
        //TODO moveMarkOnscreen
        //TODO moveVisually
        //TODO scrollMarkOnscreen
        //TODO scrollToIterator
        //TODO scrollToMark
        //TODO translateBufferCoordinatesToWindowCoordinates
        //TODO translateWindowCoordinatesToBufferCoordinates

        w.setChild(view1);
        w.show();
        gtkApplication.quit();


    }
}
