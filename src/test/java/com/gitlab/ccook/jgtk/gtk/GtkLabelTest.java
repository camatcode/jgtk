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
import com.gitlab.ccook.jgtk.enums.*;
import com.gitlab.ccook.util.Option;


import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class GtkLabelTest extends JGTKJUnitTest {

    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws MalformedURLException {
        GtkBox b = new GtkBox(GtkOrientation.GTK_ORIENTATION_VERTICAL, 5);
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setChild(b);
        gtkApplication.addWindow(w);
        w.show();

        GtkLabel label1 = new GtkLabel("<i><b><a href=\"http://google.com\">Label 1</a></b></i>");
        label1.usePangoMarkup(true);
        label1.setSelectable(true);
        b.append(label1);
        GtkLabel mnLabel = new GtkLabel(new MnemonicLabel("_File"));
        b.append(mnLabel);

        assertFalse(label1.doesWrap());
        label1.shouldWrap(true);
        assertTrue(label1.doesWrap());
        Option<PangoAttrList> effectiveAttributes = label1.getEffectiveAttributes();
        assertTrue(effectiveAttributes.isDefined());
        GSList<PangoAttribute> attributes = effectiveAttributes.get().getAttributes();
        for (int i = 0; i < attributes.size(); i++) {
            PangoAttribute attribute = attributes.get(i).get();
            if (attribute.getPangoAttrClass().getType().isInt()) {
                System.out.println(attribute.getPangoAttrClass() + "=" + attribute.getValueAsInt().get());
            }
            if (attribute.getPangoAttrClass().getType().isColor()) {
                System.out.println(attribute.getPangoAttrClass() + "=" + attribute.getValueAsColor().get());
            }
        }
        assertEquals(attributes.size(), 4);
        //TODO set attributes

        assertTrue(label1.getCurrentResourceLink().isDefined());
        assertEquals(label1.getCurrentResourceLink().get(), new URL("http://google.com"));

        assertEquals(label1.getEllipsizeMode(), PangoEllipsizeMode.PANGO_ELLIPSIZE_NONE);
        label1.setEllipsizeMode(PangoEllipsizeMode.PANGO_ELLIPSIZE_MIDDLE);
        assertEquals(label1.getEllipsizeMode(), PangoEllipsizeMode.PANGO_ELLIPSIZE_MIDDLE);

        assertFalse(label1.getExtraMenu().isDefined());
        GMenu m = makeMainMenu();
        label1.setExtraMenu(m);
        assertTrue(label1.getExtraMenu().isDefined());
        assertEquals(label1.getExtraMenu().get(), m);

        assertEquals(label1.getHorizontalAlignment(), 0.5f);
        label1.setHorizontalAlignment(0.7f);
        assertEquals(label1.getHorizontalAlignment(), 0.7f);

        assertEquals(label1.getVerticalAlignment(), 0.5f);
        label1.setVerticalAlignment(0.7f);
        assertEquals(label1.getVerticalAlignment(), 0.7f);

        assertEquals(label1.getJustification(), GtkJustification.GTK_JUSTIFY_LEFT);
        label1.setJustification(GtkJustification.GTK_JUSTIFY_CENTER);
        assertEquals(label1.getJustification(), GtkJustification.GTK_JUSTIFY_CENTER);

        assertEquals(label1.getLabel(), "<i><b><a href=\"http://google.com\">Label 1</a></b></i>");
        label1.setLabel("<u>" + label1.getLabel() + "</u>");

        assertFalse(label1.getLineLimit().isDefined());
        label1.setLineLimit(1);
        assertTrue(label1.getLineLimit().isDefined());
        assertEquals(label1.getLineLimit().get(), 1);

        assertFalse(label1.getMaxCharacterWidth().isDefined());
        label1.setMaxCharacterWidth(2);
        assertEquals(label1.getMaxCharacterWidth().get(), 2);

        assertEquals(mnLabel.getMnemonicKeyVal(), GdkKeyVal.GDK_KEY_F);

        mnLabel.setMnemonicTarget(w);
        assertTrue(mnLabel.getMnemonicTarget().isDefined());
        assertEquals(mnLabel.getMnemonicTarget().get(), w);

        assertEquals(label1.getNaturalWrapMode(), GtkNaturalWrapMode.GTK_NATURAL_WRAP_INHERIT);
        label1.setNaturalWrapMode(GtkNaturalWrapMode.GTK_NATURAL_WRAP_WORD);
        assertEquals(label1.getNaturalWrapMode(), GtkNaturalWrapMode.GTK_NATURAL_WRAP_WORD);

        assertEquals(label1.getText(), "Label 1");
        label1.setText("Label 2");
        assertEquals(label1.getText(), "Label 2");

        assertFalse(label1.getTextWidth().isDefined());
        label1.setTextWidth(5);
        assertTrue(label1.getTextWidth().isDefined());
        assertEquals(label1.getTextWidth().get(), 5);

        assertEquals(label1.getWrapMode(), PangoWrapMode.PANGO_WRAP_WORD);
        label1.setWrapMode(PangoWrapMode.PANGO_WRAP_WORD_CHAR);
        assertEquals(label1.getWrapMode(), PangoWrapMode.PANGO_WRAP_WORD_CHAR);

        assertTrue(mnLabel.isMnemonic());
        assertFalse(label1.isMnemonic());

        assertTrue(label1.isSelectable());
        assertFalse(mnLabel.isSelectable());

        assertFalse(label1.isSingleLineModeActive());
        label1.setSingleLineModeActive(true);
        assertTrue(label1.isSingleLineModeActive());
        gtkApplication.quit();
    }
}
