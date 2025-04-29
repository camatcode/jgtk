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

import com.gitlab.ccook.jgtk.GValue;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.gtk.interfaces.GtkEditable;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkSearchEntry is an entry widget that has been tailored for use as a search entry.
 * <p>
 * The main API for interacting with a GtkSearchEntry as entry is the GtkEditable interface.
 * It will show an inactive symbolic "find" icon when the search entry is empty, and a symbolic "clear" icon when there
 * is text. Clicking on the "clear" icon will empty the search entry.
 * <p>
 * To make filtering appear more reactive, it is a good idea to not react to every change in the entry text immediately,
 * but only after a short delay. To support this, GtkSearchEntry emits the GtkSearchEntry::search-changed signal which
 * can be used instead of the GtkEditable::changed signal.
 * <p>
 * The GtkSearchEntry::previous-match, GtkSearchEntry::next-match and GtkSearchEntry::stop-search signals can be used
 * to implement moving between search results and ending the search.
 * <p>
 * Often, GtkSearchEntry will be fed events by means of being placed inside a GtkSearchBar. If that is not the case,
 * you can use gtk_search_entry_set_key_capture_widget() to let it capture key input from another widget.
 * <p>
 * GtkSearchEntry provides only minimal API and should be used with the GtkEditable API.
 */
@SuppressWarnings("unchecked")
public class GtkSearchEntry extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkEditable {

    private static final GtkSearchEntryLibrary library = new GtkSearchEntryLibrary();

    public GtkSearchEntry(Pointer ref) {
        super(ref);
    }

    public GtkSearchEntry(String placeholder) {
        this();
        setPlaceholderText(placeholder);
    }

    /**
     * Creates a GtkSearchEntry.
     */
    public GtkSearchEntry() {
        super(library.gtk_search_entry_new());
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
     * Gets the widget that entry is capturing key events from.
     *
     * @return The key capture widget, if defined
     */
    public Option<GtkWidget> getKeyCaptureWidget() {
        Option<Pointer> p = new Option<>(library.gtk_search_entry_get_key_capture_widget(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets widget as the widget that entry will capture key events from.
     * <p>
     * Key events are consumed by the search entry to start or continue a search.
     * <p>
     * If the entry is part of a GtkSearchBar, it is preferable to call gtk_search_bar_set_key_capture_widget() instead,
     * which will reveal the entry in addition to triggering the search entry.
     * <p>
     * Note that despite the name of this function, the events are only 'captured' in the bubble phase, which means that
     * editable child widgets of widget will receive text input before it gets captured. If that is not desired, you can
     * capture and forward the events yourself with gtk_event_controller_key_forward().
     *
     * @param w A GtkWidget
     *          <p>
     *          The argument can be NULL.
     */
    public void setKeyCaptureWidget(GtkWidget w) {
        library.gtk_search_entry_set_key_capture_widget(getCReference(), pointerOrNull(w));
    }

    /**
     * @return The text that will be displayed in the GtkSearchEntry when it is empty and unfocused.
     */
    public Option<String> getPlaceholderText() {
        Option<GValue> property = getProperty("placeholder-text");
        if (property.isDefined()) {
            return new Option<>(property.get().getText());
        }
        return Option.NONE;
    }

    /**
     * @param placeholder The text that will be displayed in the GtkSearchEntry when it is empty and unfocused.
     */
    public void setPlaceholderText(String placeholder) {
        Option<GValue> property = getProperty("placeholder-text");
        if (property.isDefined()) {
            setProperty("placeholder-text", getCReference(), placeholder);
        }
    }

    /**
     * Get the delay to be used between the last keypress and the GtkSearchEntry::search-changed signal being emitted.
     *
     * @return A delay in milliseconds.
     */
    public Option<Integer> getSearchDelayMilliseconds() {
        Option<GValue> property = getProperty("search-delay");
        if (property.isDefined()) {
            return new Option<>(property.get().getUInt());
        }
        return Option.NONE;
    }

    /**
     * Set the delay to be used between the last keypress and the GtkSearchEntry::search-changed signal being emitted.
     *
     * @param milliseconds The delay in milliseconds from last keypress to the search changed signal.
     */
    public void setSearchDelayMilliseconds(int milliseconds) {
        setPropertyUInt("search-delay", getCReference(), milliseconds);
    }

    public static final class Signals extends GtkWidget.Signals {

        /**
         * Emitted when the entry is activated.
         */
        public static final Signals ACTIVATE = new Signals("activate");

        /**
         * Emitted when the user initiates a move to the next match for the current search string
         */
        public static final Signals NEXT_MATCH = new Signals("next-match");

        /**
         * Emitted when the user initiates a move to the previous match for the current search string.
         */
        public static final Signals PREVIOUS_MATCH = new Signals("previous-match");

        /**
         * Emitted with a delay. The length of the delay can be changed with the GtkSearchEntry:search-delay property.
         */
        public static final Signals SEARCH_CHANGED = new Signals("search-changed");

        /**
         * Emitted when the user initiated a search on the entry.
         */
        public static final Signals SEARCH_STARTED = new Signals("search-started");

        /**
         * Emitted when the user stops a search via keyboard input.
         */
        public static final Signals STOP_SEARCH = new Signals("stop-search");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkSearchEntryLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the widget that entry is capturing key events from.
         *
         * @param bar self
         * @return The key capture widget.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_search_entry_get_key_capture_widget(Pointer bar);

        /**
         * Creates a GtkSearchEntry.
         *
         * @return A new GtkSearchEntry
         */
        public native Pointer gtk_search_entry_new();

        /**
         * Sets widget as the widget that entry will capture key events from.
         * <p>
         * Key events are consumed by the search entry to start or continue a search.
         * <p>
         * If the entry is part of a GtkSearchBar, it is preferable to call gtk_search_bar_set_key_capture_widget()
         * instead, which will reveal the entry in addition to triggering the search entry.
         * <p>
         * Note that despite the name of this function, the events are only 'captured' in the bubble phase, which means
         * that editable child widgets of widget will receive text input before it gets captured. If that is not
         * desired, you can capture and forward the events yourself with gtk_event_controller_key_forward().
         *
         * @param bar    self
         * @param widget A GtkWidget
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_search_entry_set_key_capture_widget(Pointer bar, Pointer widget);
    }
}
