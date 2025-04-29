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
import com.gitlab.ccook.jgtk.GdkPaintable;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.GtkMediaFile;
import com.gitlab.ccook.jgtk.enums.GtkLicense;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class GtkAboutDialogTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) throws IOException, URISyntaxException {
        GtkAboutDialog dialog = new GtkAboutDialog();
        dialog.setAssociatedApplication(gtkApplication);

        assertFalse(dialog.doesLicenseTextWrap());
        dialog.shouldLicenseTextWrap(true);
        assertTrue(dialog.doesLicenseTextWrap());

        assertTrue(dialog.getDesigners().isEmpty());
        dialog.setDesigners("Designer A", "Designer B");
        assertEquals(dialog.getDesigners().size(), 2);

        assertTrue(dialog.getCreators().isEmpty());
        dialog.setCreators("Creator 1", "Creator 2");
        assertEquals(dialog.getCreators().size(), 2);

        assertFalse(dialog.getComments().isDefined());
        String newComment = "This is a comment.";
        dialog.setComments(newComment);
        assertTrue(dialog.getComments().isDefined());
        assertEquals(dialog.getComments().get(), newComment);

        assertFalse(dialog.getCopyright().isDefined());
        String newCopyright = "Copyright 1000";
        dialog.setCopyright(newCopyright);
        assertEquals(newCopyright, dialog.getCopyright().get());

        assertTrue(dialog.getDocumenters().isEmpty());
        dialog.setDocumenters("Documenter A", "Documenter B");
        assertEquals(dialog.getDocumenters().size(), 2);

        Pair<Option<String>, GtkLicense> license = dialog.getLicense();
        assertFalse(license.getFirst().isDefined());
        assertEquals(license.getSecond(), GtkLicense.DEVELOPER_SPECIFIED);
        dialog.setLicenseType(GtkLicense.GNU_LESSER_GENERAL_PUBLIC_LICENSE_V2_1);
        license = dialog.getLicense();
        assertTrue(license.getFirst().isDefined());
        assertEquals(license.getSecond(), GtkLicense.GNU_LESSER_GENERAL_PUBLIC_LICENSE_V2_1);
        assertEquals(dialog.getLicenseType(), GtkLicense.GNU_LESSER_GENERAL_PUBLIC_LICENSE_V2_1);

        assertFalse(dialog.getLogoIconName().isDefined());
        dialog.setLogoIconName(getRandomIcons().iterator().next());
        assertTrue(dialog.getLogoIconName().isDefined());

        assertFalse(dialog.getLogo().isDefined());
        GdkPaintable icon = new GtkMediaFile(getIconFile());
        dialog.setLogo(icon);
        assertTrue(dialog.getLogo().isDefined());
        assertEquals(dialog.getLogo().get(), icon);

        String newProgramName = "A new program";
        dialog.setProgramName(newProgramName);
        assertTrue(dialog.getProgramName().isDefined());
        assertEquals(dialog.getProgramName().get(), newProgramName);

        assertFalse(dialog.getProgramVersion().isDefined());
        String newProgramVersion = "1.2.3";
        dialog.setProgramVersion(newProgramVersion);
        assertEquals(newProgramVersion, dialog.getProgramVersion().get());

        assertFalse(dialog.getResourceLink().isDefined());
        URI uri = new URI("http://example.com");
        dialog.setResourceLink(uri);
        assertEquals(dialog.getResourceLink().get(), uri);

        dialog.setResourceLinkLabel("Click Me");
        assertEquals(dialog.getResourceLinkLabel().get(), "Click Me");

        assertFalse(dialog.getSystemInformation().isDefined());
        dialog.setSystemInformation("Linux");
        assertEquals(dialog.getSystemInformation().get(), "Linux");

        assertFalse(dialog.getTranslatorCredits().isDefined());
        String newTranslatorCredits = "translators";
        dialog.setTranslatorCredits(newTranslatorCredits);
        assertTrue(dialog.getTranslatorCredits().isDefined());
        assertEquals(dialog.getTranslatorCredits().get(), newTranslatorCredits);

        dialog.setCredits("Special Thanks", "Thank you A", "Thank you B");

        dialog.show();
        gtkApplication.quit();
    }
}
