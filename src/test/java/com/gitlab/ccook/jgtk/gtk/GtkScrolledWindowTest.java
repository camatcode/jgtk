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
import com.gitlab.ccook.jgtk.enums.GtkCornerType;
import com.gitlab.ccook.jgtk.enums.GtkPolicyType;
import com.gitlab.ccook.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

public class GtkScrolledWindowTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {

        GtkApplicationWindow ww = new GtkApplicationWindow(gtkApplication);
        ww.setDefaultSize(200, 200);
        ww.setSizeRequest(200, 200);
        GtkScrolledWindow w = new GtkScrolledWindow();

        assertFalse(w.doesPropagateNaturalHeight());
        w.shouldPropagateNaturalHeight(true);
        assertTrue(w.doesPropagateNaturalHeight());

        assertFalse(w.doesPropagateNaturalWidth());
        w.shouldPropagateNaturalWidth(true);
        assertTrue(w.doesPropagateNaturalWidth());

        assertFalse(w.getChild().isDefined());

        GtkTextView textView = new GtkTextView();
        GtkTextBuffer buf = textView.getBuffer();
        buf.setText(makeLongText());
        w.setChild(textView);
        assertTrue(w.getChild().isDefined());

        assertEquals(w.getContentPlacement(), GtkCornerType.GTK_CORNER_TOP_LEFT);
        w.setContentPlacement(GtkCornerType.GTK_CORNER_BOTTOM_RIGHT);
        assertEquals(w.getContentPlacement(), GtkCornerType.GTK_CORNER_BOTTOM_RIGHT);

        //adjustments

        GtkWidget hScrollBar = w.getHorizontalScrollBar();
        assertInstanceOf(GtkScrollBar.class, hScrollBar);

        GtkWidget vScrollBar = w.getVerticalScrollBar();
        assertInstanceOf(GtkScrollBar.class, vScrollBar);

        assertFalse(w.getMaxContentHeight().isDefined());
        w.setMaxContentHeight(100);
        assertTrue(w.getMaxContentHeight().isDefined());
        assertEquals(w.getMaxContentHeight().get(), 100);

        assertFalse(w.getMinContentHeight().isDefined());
        w.setMinContentHeight(50);
        assertTrue(w.getMinContentHeight().isDefined());
        assertEquals(w.getMinContentHeight().get(), 50);

        assertFalse(w.getMaxContentWidth().isDefined());
        w.setMaxContentWidth(100);
        assertTrue(w.getMaxContentWidth().isDefined());
        assertEquals(w.getMaxContentWidth().get(), 100);

        assertFalse(w.getMinContentWidth().isDefined());
        w.setMinContentWidth(50);
        assertTrue(w.getMinContentWidth().isDefined());
        assertEquals(w.getMinContentWidth().get(), 50);

        Pair<GtkPolicyType, GtkPolicyType> policy = w.getPolicy();
        assertEquals(policy.getFirst(), GtkPolicyType.GTK_POLICY_AUTOMATIC);
        assertEquals(policy.getSecond(), GtkPolicyType.GTK_POLICY_AUTOMATIC);

        w.setPolicy(GtkPolicyType.GTK_POLICY_ALWAYS, GtkPolicyType.GTK_POLICY_ALWAYS);
        policy = w.getPolicy();
        assertEquals(policy.getFirst(), GtkPolicyType.GTK_POLICY_ALWAYS);
        assertEquals(policy.getSecond(), GtkPolicyType.GTK_POLICY_ALWAYS);

        assertFalse(w.hasBevel());
        w.useBevel(true);
        assertTrue(w.hasBevel());

        assertTrue(w.isKineticScrollingEnabled());
        w.useKineticScrolling(false);
        assertFalse(w.isKineticScrollingEnabled());

        assertTrue(w.isOverlayScrollingEnabled());
        w.useOverlayScrolling(false);
        assertFalse(w.isOverlayScrollingEnabled());

        assertTrue(w.doesPropagateNaturalHeight());
        w.shouldPropagateNaturalHeight(false);
        assertFalse(w.doesPropagateNaturalHeight());

        assertTrue(w.doesPropagateNaturalWidth());
        w.shouldPropagateNaturalWidth(false);
        assertFalse(w.doesPropagateNaturalWidth());

        w.clearContentPlacement();

        w.show();
        ww.setChild(w);
        ww.show();
        gtkApplication.quit();
    }

    private String makeLongText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append(i);
            if (i % 100 == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
