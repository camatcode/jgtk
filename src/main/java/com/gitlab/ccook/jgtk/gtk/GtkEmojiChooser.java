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

import com.gitlab.ccook.jgtk.GtkShortcutManager;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.EmojiPickedCallback;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkNative;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Pointer;


/**
 * The GtkEmojiChooser is used by text widgets such as GtkEntry or GtkTextView to let users insert Emoji characters.
 * <p>
 * GtkEmojiChooser emits the GtkEmojiChooser::emoji-picked signal when an Emoji is selected.
 */
public class GtkEmojiChooser extends GtkPopover implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkShortcutManager {

    public GtkEmojiChooser(Pointer p) {
        super(p);
    }

    /**
     * Creates a new GtkEmojiChooser.
     */
    public GtkEmojiChooser() {
        super(library.gtk_emoji_chooser_new());
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

    public void connect(EmojiPickedCallback callback) {
        callbacks.put(Signals.EMOJI_PICKED.getDetailedName(), callback);//To avoid garbage collection
        library.g_signal_connect_data(
                getCReference(), Signals.EMOJI_PICKED.getDetailedName(), callback, Pointer.NULL, Pointer.NULL, GConnectFlags.getCValueFromFlags(GConnectFlags.G_CONNECT_DEFAULT));

    }

    public static final class Signals extends GtkPopover.Signals {
        /**
         * Emitted when the user selects an Emoji.
         */
        public static final Signals EMOJI_PICKED = new Signals("emoji-picked");

        @SuppressWarnings("SameParameterValue")
        private Signals(String detailedName) {
            super(detailedName);
        }
    }

}
