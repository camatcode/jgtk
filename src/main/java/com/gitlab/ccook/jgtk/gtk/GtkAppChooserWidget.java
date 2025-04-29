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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkAppChooser;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

@SuppressWarnings({"deprecation", "DeprecatedIsStillUsed", "RedundantSuppression"})
@Deprecated
public class GtkAppChooserWidget extends GtkWidget implements GtkAccessible, GtkAppChooser, GtkBuildable, GtkConstraintTarget {

    private static final GtkAppChooserWidget.GtkAppChooserWidgetLibrary library = new GtkAppChooserWidget.GtkAppChooserWidgetLibrary();

    public GtkAppChooserWidget(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkAppChooserWidget for applications that can handle content of the given type.
     *
     * @param contentType The content type to show applications for.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public GtkAppChooserWidget(String contentType) {
        super(library.gtk_app_chooser_widget_new(contentType));
    }

    /**
     * Returns the text that is shown, if there aren't applications that can handle the content type.
     *
     * @return The text that appears in the widget when there are no applications for the given content type.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public Option<String> getDefaultText() {
        return new Option<>(library.gtk_app_chooser_widget_get_default_text(getCReference()));
    }

    /**
     * Sets the text that is shown if there aren't applications that can handle the content type.
     *
     * @param defaultText The text that appears in the widget when there are no applications for the given content
     *                    type.
     */
    public void setDefaultText(String defaultText) {
        library.gtk_app_chooser_widget_set_default_text(getCReference(), defaultText);
    }

    /**
     * Gets whether the app chooser should show all applications in a flat list.
     *
     * @return If TRUE, the app chooser presents all applications in a single list, without subsections for default,
     *         recommended or related applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public boolean doesShowAll() {
        return library.gtk_app_chooser_widget_get_show_all(getCReference());
    }

    /**
     * Sets whether the app chooser should show all applications in a flat list.
     *
     * @param shouldShow If TRUE, the app chooser presents all applications in a single list, without subsections for
     *                   default, recommended or related applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public void shouldShowAll(boolean shouldShow) {
        library.gtk_app_chooser_widget_set_show_all(getCReference(), shouldShow);
    }

    /**
     * Gets whether the app chooser should show the default handler for the content type in a separate section.
     *
     * @return Determines whether the app chooser should show the default handler for the content type in a
     *         separate section.
     *         <p>
     *         If FALSE, the default handler is listed among the recommended applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public boolean doesShowDefault() {
        return library.gtk_app_chooser_widget_get_show_default(getCReference());
    }

    /**
     * Sets whether the app chooser should show the default handler for the content type in a separate section.
     *
     * @param shouldShow Determines whether the app chooser should show the default handler for the content type in a
     *                   separate section.
     *                   <p>
     *                   If FALSE, the default handler is listed among the recommended applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public void shouldShowDefault(boolean shouldShow) {
        library.gtk_app_chooser_widget_set_show_default(getCReference(), shouldShow);
    }

    /**
     * Gets whether the app chooser should show related applications for the content type in a separate section.
     *
     * @return Determines whether the app chooser should show a section for fallback applications.
     *         <p>
     *         If FALSE, the fallback applications are listed among the other applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public boolean doesShowFallback() {
        return library.gtk_app_chooser_widget_get_show_fallback(getCReference());
    }

    /**
     * Sets whether the app chooser should show related applications for the content type in a separate section.
     *
     * @param shouldShow Determines whether the app chooser should show a section for fallback applications.
     *                   <p>
     *                   If FALSE, the fallback applications are listed among the other applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public void shouldShowFallback(boolean shouldShow) {
        library.gtk_app_chooser_widget_set_show_fallback(getCReference(), shouldShow);
    }

    /**
     * Gets whether the app chooser should show applications which are unrelated to the content type.
     *
     * @return Determines whether the app chooser should show a section for other applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public boolean doesShowOther() {
        return library.gtk_app_chooser_widget_get_show_other(getCReference());
    }

    /**
     * Sets whether the app chooser should show applications which are unrelated to the content type.
     *
     * @param shouldShow Determines whether the app chooser should show a section for other applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public void shouldShowOther(boolean shouldShow) {
        library.gtk_app_chooser_widget_set_show_other(getCReference(), shouldShow);
    }

    /**
     * Gets whether the app chooser should show recommended applications for the content type in a separate section.
     *
     * @return Determines whether the app chooser should show a section for recommended applications.
     *         <p>
     *         If FALSE, the recommended applications are listed among the other applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public boolean doesShowRecommended() {
        return library.gtk_app_chooser_widget_get_show_recommended(getCReference());
    }

    /**
     * Sets whether the app chooser should show recommended applications for the content type in a separate section.
     *
     * @param shouldShow Determines whether the app chooser should show a section for recommended applications.
     *                   <p>
     *                   If FALSE, the recommended applications are listed among the other applications.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
     */
    public void shouldShowRecommended(boolean shouldShow) {
        library.gtk_app_chooser_widget_set_show_recommended(getCReference(), shouldShow);
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

    public static class Signals extends GtkWidget.Signals {
        /**
         * Emitted when an application item is activated from the widget's list.
         * <p>
         * This usually happens when the user double-clicks an item, or an item is selected and the user presses one of
         * the keys Space, Shift+Space, Return or Enter.
         */
        public final static Signals APPLICATION_ACTIVATED = new Signals("application-activated");
        /**
         * Emitted when an application item is selected from the widget's list.
         */
        public static Signals APPLICATION_SELECTED = new Signals("application-selected");


        protected Signals(String cValue) {
            super(cValue);
        }
    }

    @SuppressWarnings({"DeprecatedIsStillUsed", "RedundantSuppression"})
    protected static class GtkAppChooserWidgetLibrary extends GtkWidget.GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new GtkAppChooserWidget for applications that can handle content of the given type.
         *
         * @param content_type The content type to show applications for.
         * @return A newly created GtkAppChooserWidget
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native Pointer gtk_app_chooser_widget_new(String content_type);

        /**
         * Returns the text that is shown if there aren't applications that can handle the content type.
         *
         * @param self self
         * @return The value of GtkAppChooserWidget:default-text
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native String gtk_app_chooser_widget_get_default_text(Pointer self);

        /**
         * Gets whether the app chooser should show all applications in a flat list.
         *
         * @param self self
         * @return If TRUE, the app chooser presents all applications in a single list, without subsections for default,
         *         recommended or related applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native boolean gtk_app_chooser_widget_get_show_all(Pointer self);

        /**
         * Gets whether the app chooser should show the default handler for the content type in a separate section.
         *
         * @param self self
         * @return Determines whether the app chooser should show the default handler for the content type in a
         *         separate section.
         *         <p>
         *         If FALSE, the default handler is listed among the recommended applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native boolean gtk_app_chooser_widget_get_show_default(Pointer self);

        /**
         * Gets whether the app chooser should show related applications for the content type in a separate section.
         *
         * @param self self
         * @return Determines whether the app chooser should show a section for fallback applications.
         *         <p>
         *         If FALSE, the fallback applications are listed among the other applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native boolean gtk_app_chooser_widget_get_show_fallback(Pointer self);

        /**
         * Gets whether the app chooser should show applications which are unrelated to the content type.
         *
         * @param self self
         * @return Determines whether the app chooser should show a section for other applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native boolean gtk_app_chooser_widget_get_show_other(Pointer self);

        /**
         * Gets whether the app chooser should show recommended applications for the content type in a separate section.
         *
         * @param self self
         * @return Determines whether the app chooser should show a section for recommended applications.
         *         <p>
         *         If FALSE, the recommended applications are listed among the other applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native boolean gtk_app_chooser_widget_get_show_recommended(Pointer self);

        /**
         * Sets the text that is shown if there aren't applications that can handle the content type.
         *
         * @param self         self
         * @param default_text The text that appears in the widget when there are no applications for the given content
         *                     type.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native void gtk_app_chooser_widget_set_default_text(Pointer self, String default_text);

        /**
         * Sets whether the app chooser should show all applications in a flat list.
         *
         * @param self    self
         * @param setting If TRUE, the app chooser presents all applications in a single list, without subsections for
         *                default, recommended or related applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native void gtk_app_chooser_widget_set_show_all(Pointer self, boolean setting);

        /**
         * Sets whether the app chooser should show the default handler for the content type in a separate section.
         *
         * @param self    self
         * @param setting Determines whether the app chooser should show the default handler for the content type in a
         *                separate section.
         *                <p>
         *                If FALSE, the default handler is listed among the recommended applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native void gtk_app_chooser_widget_set_show_default(Pointer self, boolean setting);

        /**
         * Sets whether the app chooser should show related applications for the content type in a separate section.
         *
         * @param self    self
         * @param setting Determines whether the app chooser should show a section for fallback applications.
         *                <p>
         *                If FALSE, the fallback applications are listed among the other applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native void gtk_app_chooser_widget_set_show_fallback(Pointer self, boolean setting);

        /**
         * Sets whether the app chooser should show applications which are unrelated to the content type.
         *
         * @param self    self
         * @param setting Determines whether the app chooser should show a section for other applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native void gtk_app_chooser_widget_set_show_other(Pointer self, boolean setting);

        /**
         * Sets whether the app chooser should show recommended applications for the content type in a separate section.
         *
         * @param self    self
         * @param setting Determines whether the app chooser should show a section for recommended applications.
         *                <p>
         *                If FALSE, the recommended applications are listed among the other applications.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5.
         */
        public native void gtk_app_chooser_widget_set_show_recommended(Pointer self, boolean setting);
    }
}
