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

import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkActionable;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.AssertionUtils;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * A GtkLinkButton is a button with a hyperlink.
 * <p>
 * It is useful to show quick links to resources.
 * <p>
 * A link button is created by calling either gtk_link_button_new() or gtk_link_button_new_with_label().
 * If using the former, the URI you pass to the constructor is used as a label for the widget.
 * <p>
 * The URI bound to a GtkLinkButton can be set specifically using gtk_link_button_set_uri().
 * <p>
 * By default, GtkLinkButton calls gtk_file_launcher_launch() when the button is clicked.
 * This behaviour can be overridden by connecting to the GtkLinkButton::activate-link signal and returning TRUE
 * from the signal handler.
 */
public class GtkLinkButton extends GtkButton implements GtkAccessible, GtkActionable, GtkBuildable {

    private static final GtkLinkButtonLibrary library = new GtkLinkButtonLibrary();

    /**
     * Creates a new GtkLinkButton with the URL as its text.
     *
     * @param url A valid URL
     */
    public GtkLinkButton(URL url) {
        super(handleCtor(url));
    }

    /**
     * Creates a new GtkLinkButton containing a label.
     *
     * @param url        A valid URL.
     * @param buttonText The text of the button.
     */
    public GtkLinkButton(URL url, String buttonText) {
        super(handleCtor(url, buttonText));
    }

    public GtkLinkButton(Pointer p) {
        super(p);
    }

    private static Pointer handleCtor(URL url) {
        AssertionUtils.assertNotNull(GtkLinkButton.class, "Ctor, URL null", url);
        return library.gtk_link_button_new(url.toString());
    }

    private static Pointer handleCtor(URL url, String buttonText) {
        if (buttonText != null) {
            return library.gtk_link_button_new_with_label(url.toString(), buttonText);
        } else {
            return handleCtor(url);
        }
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
     * Retrieves the "visited" state of the GtkLinkButton.
     * <p>
     * The button becomes visited when it is clicked. If the URL is changed on the button,
     * the "visited" state is unset again.
     * <p>
     * The state may also be changed using gtk_link_button_set_visited().
     *
     * @return TRUE if the link has been visited, FALSE otherwise.
     */
    public boolean isVisited() {
        return library.gtk_link_button_get_visited(cReference);
    }

    /**
     * Sets the "visited" state of the GtkLinkButton.
     * <p>
     * See gtk_link_button_get_visited() for more details.
     *
     * @param isVisited The new "visited" state.
     */
    public void setVisited(boolean isVisited) {
        library.gtk_link_button_set_visited(cReference, isVisited);
    }

    /**
     * Sets uri as the URL where the GtkLinkButton points.
     * <p>
     * As a side effect this unsets the "visited" state of the button.
     *
     * @param toSet      url to set
     * @param buttonText button text to set
     * @throws MalformedURLException messed up url
     */
    public void setURL(URL toSet, Option<String> buttonText) throws MalformedURLException {
        if (toSet != null) {
            URL previousURI = getURL();
            if (buttonText != null && buttonText.isDefined()) {
                setLabel(buttonText.get());
                library.gtk_link_button_set_uri(cReference, toSet.toString());
            } else {
                setURL(toSet);
            }
        }
    }

    /**
     * Retrieves the URL of the GtkLinkButton.
     *
     * @return A valid URL. The returned string is owned by the link button and should not be modified or freed.
     * @throws MalformedURLException Messed up URL
     */
    public URL getURL() throws MalformedURLException {
        String url = library.gtk_link_button_get_uri(cReference);
        return URI.create(url).toURL();
    }

    /**
     * Sets uri as the URI where the GtkLinkButton points.
     * <p>
     * As a side effect this unsets the "visited" state of the button.
     *
     * @param toSet A valid URI.
     */
    public void setURL(URL toSet) throws MalformedURLException {
        if (toSet != null) {
            URL previousURI = getURL();
            Option<String> label = super.getLabel();
            if (label.isDefined()) {
                String labelRaw = label.get();
                if (labelRaw.equals(previousURI.toString())) {
                    super.setLabel(toSet.toString());
                }
            }
            library.gtk_link_button_set_uri(cReference, toSet.toString());
        }
    }

    public static class Signals extends GtkButton.Signals {

        /**
         * Emitted each time the GtkLinkButton is clicked.
         * <p>
         * The default handler will call gtk_show_uri() with the URI stored inside the GtkLinkButton:uri property.
         * <p>
         * To override the default behavior, you can connect to the ::activate-link signal and stop the propagation of
         * the signal by returning TRUE from your handler.
         */
        public final static Signals ACTIVATE_LINK = new Signals("activate-link");

        @SuppressWarnings("SameParameterValue")
        protected Signals(String cValue) {
            super(cValue);
        }

    }

    protected static class GtkLinkButtonLibrary extends GtkButtonLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Retrieves the URI of the GtkLinkButton.
         *
         * @param link_button self
         * @return A valid URI. The returned string is owned by the link button and should not be modified or freed.
         */
        public native String gtk_link_button_get_uri(Pointer link_button);

        /**
         * Retrieves the "visited" state of the GtkLinkButton.
         * <p>
         * The button becomes visited when it is clicked.
         * If the URI is changed on the button, the "visited" state is unset again.
         * <p>
         * The state may also be changed using gtk_link_button_set_visited().
         *
         * @param link_button self
         * @return TRUE if the link has been visited, FALSE otherwise.
         */
        public native boolean gtk_link_button_get_visited(Pointer link_button);

        /**
         * Creates a new GtkLinkButton with the URI as its text.
         *
         * @param uri A valid URI.
         * @return A new link button widget.
         */
        public native Pointer gtk_link_button_new(String uri);

        /**
         * Creates a new GtkLinkButton containing a label.
         *
         * @param uri   A valid URI.
         * @param label The text of the button.
         *              <p>
         *              The argument can be NULL.
         * @return A new link button widget. Type:GtkLinkButton
         */
        public native Pointer gtk_link_button_new_with_label(String uri, String label);

        /**
         * Sets uri as the URI where the GtkLinkButton points.
         * <p>
         * As a side effect this unsets the "visited" state of the button.
         *
         * @param link_button self
         * @param uri         A valid URI.
         */
        public native void gtk_link_button_set_uri(Pointer link_button, String uri);

        /**
         * Sets the "visited" state of the GtkLinkButton.
         * <p>
         * See gtk_link_button_get_visited() for more details.
         *
         * @param link_button self
         * @param visited     The new "visited" state.
         */
        public native void gtk_link_button_set_visited(Pointer link_button, boolean visited);
    }
}
