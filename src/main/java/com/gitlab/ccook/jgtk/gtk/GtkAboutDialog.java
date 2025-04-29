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

import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkLicense;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The GtkAboutDialog offers a simple way to display information about a program.
 * <p>
 * The shown information includes the programs' logo, name, copyright, website and license. It is also possible to give
 * credits to the authors, documenters, translators and artists who have worked on the program.
 * <p>
 * An about-dialog is typically opened when the user selects the About option from the Help menu. All parts of the
 * dialog are optional.
 * <p>
 * About dialogs often contain links and email addresses. GtkAboutDialog displays these as clickable links. By default,
 * it calls gtk_file_launcher_launch() when a user clicks one. The behaviour can be overridden with the
 * GtkAboutDialog::activate-link signal.
 * <p>
 * To specify a person with an email address, use a string like Edgar Allan Poe <edgar@poe.com>. To specify a website
 * with a title, use a string like GTK team <a href="https://www.gtk.org">https://www.gtk.org</a>.
 * <p>
 * To make constructing a GtkAboutDialog as convenient as possible, you can use the function gtk_show_about_dialog()
 * which constructs and shows a dialog and keeps it around so that it can be shown again.
 */
@SuppressWarnings("unchecked")
public class GtkAboutDialog extends GtkWindow implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkAboutDialogLibrary library = new GtkAboutDialogLibrary();

    public GtkAboutDialog() {
        super(library.gtk_about_dialog_new());
        setResourceLinkLabel(null);
        setComments(null);
        setCopyright(null);
        setProgramVersion(null);
        setLicense(null);
    }

    public GtkAboutDialog(Pointer ref) {
        super(ref);
    }

    /**
     * Connect a signal
     *
     * @param s       Detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to the signal
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef) {
        connect(s.getDetailedName(), fn, dataRef, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s     detailed name of signal
     * @param fn    function to invoke on signal
     * @param flags connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, GConnectFlags... flags) {
        connect(s.getDetailedName(), fn, null, flags);
    }

    /**
     * Connect a signal
     *
     * @param s  detailed name of signal
     * @param fn function to invoke on signal
     */
    public void connect(Signals s, GCallbackFunction fn) {
        connect(s.getDetailedName(), fn, Pointer.NULL, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s       detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
        connect(new GtkCallbackFunction() {
            @Override
            public GConnectFlags[] getConnectFlag() {
                return flags;
            }

            @Override
            public Pointer getDataReference() {
                return dataRef;
            }

            @Override
            public String getDetailedSignal() {
                return s.getDetailedName();
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                fn.invoke(relevantThing, relevantData);
            }
        });
    }

    /**
     * Returns whether the license text in the about-dialog is automatically wrapped.
     *
     * @return TRUE if the license text is wrapped.
     */
    public boolean doesLicenseTextWrap() {
        return library.gtk_about_dialog_get_wrap_license(getCReference());
    }

    /**
     * Returns the comments string.
     *
     * @return The comments. (This may be NONE)
     */
    public Option<String> getComments() {
        String comments = library.gtk_about_dialog_get_comments(getCReference());
        if (comments != null && !comments.trim().isEmpty()) {
            return new Option<>(comments);
        }
        return Option.NONE;
    }

    /**
     * Sets the comments string to display in the about-dialog.
     * <p>
     * This should be a short string of one or two lines.
     *
     * @param comments A comments string. (This may be NULL)
     */
    public void setComments(String comments) {
        if (comments != null) {
            library.gtk_about_dialog_set_comments(getCReference(), comments);
        } else {
            setComments("");
        }
    }

    /**
     * Returns the copyright string.
     *
     * @return The copyright string.
     */
    public Option<String> getCopyright() {
        String copyRight = library.gtk_about_dialog_get_copyright(getCReference());
        if (copyRight != null && !copyRight.isEmpty()) {
            return new Option<>(copyRight);
        }
        return Option.NONE;
    }

    /**
     * Sets the copyright string to display in the about-dialog.
     * <p>
     * This should be a short string of one or two lines.
     *
     * @param copyright The copyright string. (This may be null)
     */
    public void setCopyright(String copyright) {
        if (copyright != null) {
            library.gtk_about_dialog_set_copyright(getCReference(), copyright);
        } else {
            setCopyright("");
        }
    }

    /**
     * Returns the names of the authors which are displayed in the credits page.
     *
     * @return list containing the authors.
     */
    public List<String> getCreators() {
        return toList(library.gtk_about_dialog_get_authors(getCReference()));
    }

    /**
     * Sets the names of the authors which are displayed in the "Credits" page of the about-dialog.
     *
     * @param authors The authors of the application.
     */
    public void setCreators(String... authors) {
        library.gtk_about_dialog_set_authors(getCReference(), authors);
    }

    /**
     * Returns the names of the artists which are displayed in the credits page.
     *
     * @return string array containing the artists.
     */
    public List<String> getDesigners() {
        return toList(library.gtk_about_dialog_get_artists(getCReference()));
    }

    /**
     * Sets the names of the artists to be displayed in the "Credits" page.
     *
     * @param artists List of artists to display on Credits page
     */
    public void setDesigners(String... artists) {
        library.gtk_about_dialog_set_artists(getCReference(), artists);
    }

    /**
     * Returns the name of the documenters which are displayed in the credits page.
     *
     * @return list containing the documenters.
     */
    public List<String> getDocumenters() {
        return toList(library.gtk_about_dialog_get_documenters(getCReference()));
    }

    /**
     * Sets the names of the documenters which are displayed in the "Credits" page.
     *
     * @param documenters The authors of the documentation of the application.
     */
    public void setDocumenters(String... documenters) {
        library.gtk_about_dialog_set_documenters(getCReference(), documenters);
    }

    /**
     * @return License info
     */
    @SuppressWarnings("rawtypes")
    public Pair<Option<String>, GtkLicense> getLicense() {
        String license = library.gtk_about_dialog_get_license(getCReference());
        if (license != null && !license.isEmpty()) {
            GtkLicense licenseType = getLicenseType();
            return new Pair<>(new Option<>(license), licenseType);
        } else {
            return new Pair<>(Option.NONE, GtkLicense.DEVELOPER_SPECIFIED);
        }
    }

    /**
     * Sets the license information to be displayed in the about-dialog.
     * <p>
     * If license is NULL, the license page is hidden.
     *
     * @param license The license information.(The argument can be NULL.)
     */
    public void setLicense(String license) {
        if (license != null) {
            setProperty("license", getCReference(), license);
            library.gtk_about_dialog_set_license(getCReference(), license);
            setLicenseType(GtkLicense.DEVELOPER_SPECIFIED);
        } else {
            setLicense("");
        }
    }

    /**
     * Retrieves the license type.
     *
     * @return License type
     */
    public GtkLicense getLicenseType() {
        return GtkLicense.getLicenseFromCValue(library.gtk_about_dialog_get_license_type(getCReference()));
    }

    public void setLicenseType(GtkLicense license) {
        if (license != null) {
            library.gtk_about_dialog_set_license_type(getCReference(), license.getCValue());
        } else {
            library.gtk_about_dialog_set_license_type(getCReference(), GtkLicense.DEVELOPER_SPECIFIED.getCValue());
        }
    }

    /**
     * Returns the paintable displayed as logo in the about-dialog.
     *
     * @return The paintable displayed as logo or NULL if the logo is unset or has been set via
     *         gtk_about_dialog_set_logo_icon_name()
     */
    public Option<GdkPaintable> getLogo() {
        Option<Pointer> p = new Option<>(library.gtk_about_dialog_get_logo(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GdkPaintable) JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the logo in the about-dialog.
     *
     * @param logo the logo in the about-dialog.
     */
    public void setLogo(GdkPaintable logo) {
        library.gtk_about_dialog_set_logo(getCReference(), pointerOrNull(logo));
    }

    /**
     * Returns the icon name displayed as logo in the about-dialog.
     *
     * @return The icon name displayed as logo, or NULL if the logo has been set via gtk_about_dialog_set_logo()
     */
    public Option<String> getLogoIconName() {
        return new Option<>(library.gtk_about_dialog_get_logo_icon_name(getCReference()));
    }

    /**
     * Sets the icon name to be displayed as logo in the about-dialog.
     *
     * @param name An icon name.
     *             <p>
     *             The argument can be NULL.
     */
    public void setLogoIconName(String name) {
        library.gtk_about_dialog_set_logo_icon_name(getCReference(), name);
    }

    public void setLogoIconName(IconName name) {
        if (name != null) {
            setLogoIconName(name.getIconName());
        }
    }

    /**
     * Returns the program name displayed in the about-dialog.
     *
     * @return the program name displayed in the about-dialog (This may be NONE)
     */
    public Option<String> getProgramName() {
        return new Option<>(library.gtk_about_dialog_get_program_name(getCReference()));
    }

    /**
     * Sets the name to display in the about-dialog.
     * <p>
     * If name is not set, the string returned by g_get_application_name() is used.
     *
     * @param programName The program name.
     *                    <p>
     *                    The argument can be NULL.
     */
    public void setProgramName(String programName) {
        library.gtk_about_dialog_set_program_name(getCReference(), programName);
    }

    /**
     * @return the program version (This may be NONE)
     */
    public Option<String> getProgramVersion() {
        String version = library.gtk_about_dialog_get_version(getCReference());
        if (version != null && !version.isEmpty()) {
            return new Option<>(version);
        }
        return Option.NONE;
    }

    /**
     * Sets the version string to display in the about-dialog.
     *
     * @param programVersion The version string.
     *                       <p>
     *                       The argument can be NULL.
     */
    public void setProgramVersion(String programVersion) {
        if (programVersion != null) {
            library.gtk_about_dialog_set_version(getCReference(), programVersion);
        } else {
            setProgramVersion("");
        }
    }

    /**
     * @return the website URL. (This may be NONE)
     */
    public Option<URI> getResourceLink() throws URISyntaxException {
        String uriString = library.gtk_about_dialog_get_website(getCReference());
        if (uriString != null) {
            return new Option<>(new URI(uriString));
        } else {
            return Option.NONE;
        }
    }

    /**
     * Sets the URL to use for the website link.
     *
     * @param website A URL string starting with http://
     *                <p>
     *                The argument can be NULL.
     */
    public void setResourceLink(URI website) {
        String websiteString = website != null ? website.toString() : null;
        library.gtk_about_dialog_set_website(getCReference(), websiteString);
        if (!getResourceLinkLabel().isDefined()) {
            setResourceLinkLabel(websiteString);
        }
    }

    /**
     * @return the label used for the website link. (This may be NONE)
     */
    public Option<String> getResourceLinkLabel() {
        String resourceLinkLabel = library.gtk_about_dialog_get_website_label(getCReference());
        if (resourceLinkLabel != null && !resourceLinkLabel.isEmpty()) {
            return new Option<>(resourceLinkLabel);
        }
        return Option.NONE;
    }

    /**
     * Sets the label to be used for the website link.
     *
     * @param label The label used for the website link. (This may be null).
     */
    public void setResourceLinkLabel(String label) {
        if (label != null) {
            library.gtk_about_dialog_set_website_label(getCReference(), label);
        } else {
            setResourceLinkLabel("");
        }
    }

    /**
     * Returns the system information that is shown in the about-dialog.
     *
     * @return the system information that is shown in the about-dialog. (This may be NONE)
     */
    public Option<String> getSystemInformation() {
        return new Option<>(library.gtk_about_dialog_get_system_information(getCReference()));
    }

    /**
     * Sets the system information to be displayed in the about-dialog.
     * <p>
     * If system_information is NULL, the system information page is hidden.
     *
     * @param systemInformation System information.
     *                          <p>
     *                          The argument can be NULL.
     */
    public void setSystemInformation(String systemInformation) {
        library.gtk_about_dialog_set_system_information(getCReference(), systemInformation);
    }

    /**
     * @return the translator credits string which is displayed in the credits page. (This may be NONE)
     */
    public Option<String> getTranslatorCredits() {
        return new Option<>(library.gtk_about_dialog_get_translator_credits(getCReference()));
    }

    /**
     * Sets the translator credits string which is displayed in the credits page.
     * <p>
     * The intended use for this string is to display the translator of the language which is currently used in the
     * user interface. Using gettext(), a simple way to achieve that is to mark the string for translation:
     * <p>
     * It is a good idea to use the customary msgid "translator-credits" for this purpose, since translators will
     * already know the purpose of that msgid, and since GtkAboutDialog will detect if "translator-credits" is
     * untranslated and omit translator credits.
     *
     * @param translatorCredits The translator credits.
     *                          <p>
     *                          The argument can be NULL.
     */
    public void setTranslatorCredits(String translatorCredits) {
        library.gtk_about_dialog_set_translator_credits(getCReference(), translatorCredits);
    }

    /**
     * Creates a new section in the Credits page.
     *
     * @param sectionName The name of the section.
     * @param people      The people who belong to that section.
     */
    public void setCredits(String sectionName, List<String> people) {
        setCredits(sectionName, toPrimitive(people));
    }

    /**
     * Creates a new section in the Credits page.
     *
     * @param sectionName The name of the section.
     * @param people      The people who belong to that section.
     */
    public void setCredits(String sectionName, String... people) {
        if (sectionName != null) {
            library.gtk_about_dialog_add_credit_section(getCReference(), sectionName, people);
        }
    }

    /**
     * Sets whether the license text in the about-dialog should be automatically wrapped.
     *
     * @param shouldWrap Whether to wrap the license.
     */
    public void shouldLicenseTextWrap(boolean shouldWrap) {
        library.gtk_about_dialog_set_wrap_license(getCReference(), shouldWrap);
    }

    protected static class GtkAboutDialogLibrary extends GtkWindowLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new section in the "Credits" page.
         *
         * @param about        self
         * @param section_name The name of the section.
         * @param people       The people who belong to that section.
         */
        public void gtk_about_dialog_add_credit_section(Pointer about, String section_name, String[] people) {
            INSTANCE.gtk_about_dialog_add_credit_section(about, section_name, people);
        }

        /**
         * Returns the names of the artists which are displayed in the credits page.
         *
         * @param about self
         * @return A NULL-terminated string array containing the artists.
         */
        public String[] gtk_about_dialog_get_artists(Pointer about) {
            return INSTANCE.gtk_about_dialog_get_artists(about);
        }

        /**
         * Returns the names of the authors which are displayed in the credits page.
         *
         * @param about self
         * @return A NULL-terminated string array containing the authors.
         */
        public String[] gtk_about_dialog_get_authors(Pointer about) {
            return INSTANCE.gtk_about_dialog_get_authors(about);
        }

        /**
         * Returns the comments string.
         *
         * @param about self
         * @return The comments.
         */
        public native String gtk_about_dialog_get_comments(Pointer about);

        /**
         * Returns the copyright string.
         *
         * @param about self
         * @return The copyright string.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_about_dialog_get_copyright(Pointer about);

        /**
         * Returns the name of the documenters which are displayed in the credits page.
         *
         * @param about self
         * @return A NULL-terminated string array containing the documenters.
         */
        public String[] gtk_about_dialog_get_documenters(Pointer about) {
            return INSTANCE.gtk_about_dialog_get_documenters(about);
        }

        /**
         * Returns the license information.
         *
         * @param about self
         * @return The license information.
         */
        public native String gtk_about_dialog_get_license(Pointer about);

        /**
         * Retrieves the license type.
         *
         * @param about self
         * @return A GtkLicense value.
         */
        public native int gtk_about_dialog_get_license_type(Pointer about);

        /**
         * Returns the paintable displayed as logo in the about-dialog.
         *
         * @param about self
         * @return The paintable displayed as logo or NULL if the logo is unset or has been set via
         *         gtk_about_dialog_set_logo_icon_name(). Type: GdkPaintable
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_about_dialog_get_logo(Pointer about);

        /**
         * Returns the icon name displayed as logo in the about-dialog.
         *
         * @param about self
         * @return The icon name displayed as logo, or NULL if the logo has been set via gtk_about_dialog_set_logo()
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_about_dialog_get_logo_icon_name(Pointer about);

        /**
         * Returns the program name displayed in the about-dialog.
         *
         * @param about self
         * @return The program name.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_about_dialog_get_program_name(Pointer about);

        /**
         * Returns the system information that is shown in the about-dialog.
         *
         * @param about self
         * @return The system information.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_about_dialog_get_system_information(Pointer about);

        /**
         * Returns the translator credits string which is displayed in the credits page.
         *
         * @param about self
         * @return The translator credits string.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_about_dialog_get_translator_credits(Pointer about);

        /**
         * Returns the version string.
         *
         * @param about self
         * @return The version string.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_about_dialog_get_version(Pointer about);

        /**
         * Returns the website URL.
         *
         * @param about self
         * @return The website URL.
         *         <p>
         *         The return value can be NULL
         */
        public native String gtk_about_dialog_get_website(Pointer about);

        /**
         * Returns the label used for the website link.
         *
         * @param about self
         * @return The label used for the website link.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_about_dialog_get_website_label(Pointer about);

        /**
         * Returns whether the license text in the about-dialog is automatically wrapped.
         *
         * @param about self
         * @return TRUE if the license text is wrapped.
         */
        public native boolean gtk_about_dialog_get_wrap_license(Pointer about);

        /**
         * Creates a new GtkAboutDialog.
         *
         * @return A newly created GtkAboutDialog
         */
        public native Pointer gtk_about_dialog_new();

        /**
         * Sets the names of the artists to be displayed in the "Credits" page.
         *
         * @param about   self
         * @param artists The authors of the artwork of the application.
         */
        public void gtk_about_dialog_set_artists(Pointer about, String[] artists) {
            INSTANCE.gtk_about_dialog_set_artists(about, artists);
        }

        /**
         * Sets the names of the authors which are displayed in the "Credits" page of the about-dialog.
         *
         * @param about   self
         * @param authors The authors of the application.
         */
        public void gtk_about_dialog_set_authors(Pointer about, String[] authors) {
            INSTANCE.gtk_about_dialog_set_authors(about, authors);
        }

        /**
         * Sets the comments string to display in the about-dialog.
         * <p>
         * This should be a short string of one or two lines.
         *
         * @param about    self
         * @param comments A comments string.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_about_dialog_set_comments(Pointer about, String comments);

        /**
         * Sets the copyright string to display in the about-dialog.
         * <p>
         * This should be a short string of one or two lines.
         *
         * @param about     self
         * @param copyright The copyright string.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_about_dialog_set_copyright(Pointer about, String copyright);

        /**
         * Sets the names of the documenters which are displayed in the "Credits" page.
         *
         * @param about       self
         * @param documenters The authors of the documentation of the application.
         */
        public void gtk_about_dialog_set_documenters(Pointer about, String[] documenters) {
            INSTANCE.gtk_about_dialog_set_documenters(about, documenters);
        }

        /**
         * Sets the license information to be displayed in the about-dialog.
         * <p>
         * If license is NULL, the license page is hidden.
         *
         * @param about   self
         * @param license The license information.
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_about_dialog_set_license(Pointer about, String license);

        /**
         * Sets the license of the application showing the about-dialog from a list of known licenses.
         * <p>
         * This function overrides the license set using gtk_about_dialog_set_license().
         *
         * @param about        self
         * @param license_type The type of license. Type: GtkLicense
         */
        public native void gtk_about_dialog_set_license_type(Pointer about, int license_type);

        /**
         * Sets the logo in the about-dialog.
         *
         * @param about self
         * @param logo  A GdkPaintable
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_about_dialog_set_logo(Pointer about, Pointer logo);

        /**
         * Sets the icon name to be displayed as logo in the about-dialog.
         *
         * @param about     self
         * @param icon_name An icon name.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_about_dialog_set_logo_icon_name(Pointer about, String icon_name);

        /**
         * Sets the name to display in the about-dialog.
         * <p>
         * If name is not set, the string returned by g_get_application_name() is used.
         *
         * @param about self
         * @param name  The program name.
         *              <p>
         *              The argument can be NULL.
         */
        public native void gtk_about_dialog_set_program_name(Pointer about, String name);

        /**
         * Sets the system information to be displayed in the about-dialog.
         * <p>
         * If system_information is NULL, the system information page is hidden.
         * <p>
         * See GtkAboutDialog:system-information.
         *
         * @param about              self
         * @param system_information System information.
         *                           <p>
         *                           The argument can be NULL.
         */
        public native void gtk_about_dialog_set_system_information(Pointer about, String system_information);

        /**
         * Sets the translator credits string which is displayed in the credits page.
         *
         * @param about              self
         * @param translator_credits The translator credits.
         *                           <p>
         *                           The argument can be NULL.
         */
        public native void gtk_about_dialog_set_translator_credits(Pointer about, String translator_credits);

        /**
         * Sets the version string to display in the about-dialog.
         *
         * @param about   self
         * @param version The version string.
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_about_dialog_set_version(Pointer about, String version);

        /**
         * Sets the URL to use for the website link.
         *
         * @param about   self
         * @param website A URL string starting with, for example, http://,
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_about_dialog_set_website(Pointer about, String website);

        /**
         * Sets the label to be used for the website link.
         *
         * @param about         self
         * @param website_label The label used for the website link.
         */
        public native void gtk_about_dialog_set_website_label(Pointer about, String website_label);

        /**
         * Sets whether the license text in the about-dialog should be automatically wrapped.
         *
         * @param about        self
         * @param wrap_license Whether to wrap the license.
         */
        public native void gtk_about_dialog_set_wrap_license(Pointer about, boolean wrap_license);

    }

    public static class Signals extends GtkWidget.Signals {

        /**
         * Emitted every time a URL is activated.
         * <p>
         * Applications may connect to it to override the default behaviour, which is to call gtk_show_uri().
         */
        public static final Signals ACTIVATE_LINK = new Signals("activate-link");

        @SuppressWarnings("SameParameterValue")
        protected Signals(String detailedName) {
            super(detailedName);
        }
    }
}
