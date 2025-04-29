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
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.IconName;
import com.gitlab.ccook.jgtk.MnemonicLabel;
import com.gitlab.ccook.util.Option;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GtkButtonTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        //TODO setIconName
        String labelA = "labelA";
        MnemonicLabel labelB = new MnemonicLabel("LabelB");
        GtkButton buttonA = new GtkButton(labelA);
        GtkButton buttonB = new GtkButton(labelB);
        GtkButton buttonC = new GtkButton();
        assertTrue(buttonA.getLabel().isDefined());
        assertTrue(buttonB.getLabel().isDefined());
        assertFalse(buttonC.getLabel().isDefined());
        assertEquals(buttonA.getLabel().get(), labelA);
        assertEquals(buttonB.getLabel().get(), labelB.getMnemonicLabel());
        assertFalse(buttonA.getIconName().isDefined());
        assertTrue(buttonA.getChild().isDefined());
        assertFalse(buttonC.getChild().isDefined());
        assertTrue(buttonA.hasFrame());
        buttonA.setHasFrame(false);
        assertFalse(buttonA.hasFrame());
        assertTrue(buttonB.isLabelMnemonic());
        assertFalse(buttonA.isLabelMnemonic());
        buttonA.setUseMnemonics(true);
        assertTrue(buttonA.isLabelMnemonic());
        String newLabel = "newLabel";
        buttonA.setLabel(newLabel);
        assertEquals(buttonA.getLabel().get(), newLabel);
        newLabel = "myLabel2";
        GtkLabel gtkWidget = new GtkLabel(newLabel);
        buttonA.setChild(gtkWidget);
        assertEquals(buttonA.getLabel().get(), newLabel);

        List<GtkButton> iconButtons = getRandomIconButtons();
        for (GtkButton b : iconButtons) {
            Option<IconName> iconName1 = b.getIconName();
            assertTrue(iconName1.isDefined());
            b.setIconName(iconName1.get());
            Option<IconName> iconName2 = b.getIconName();
            assertTrue(iconName2.isDefined());
            assertEquals(iconName1, iconName2);
        }
        gtkApplication.quit();
    }

    private List<GtkButton> getRandomIconButtons() {
        List<GtkButton> buttons = new ArrayList<>();
        File f = new File("/usr/share/icons/gnome/8x8/emblems/");
        if (f.exists()) {
            for (File s : Objects.requireNonNull(f.listFiles())) {
                GtkButton b = new GtkButton(new IconName(s.getName()));
                buttons.add(b);
            }
        }

        return buttons;
    }
}
