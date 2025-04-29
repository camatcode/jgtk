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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.MnemonicLabel;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


@SuppressWarnings("unchecked")
public class GtkExpander extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkExpanderLibrary library = new GtkExpanderLibrary();

    public GtkExpander(Pointer ref) {
        super(ref);
    }

    public GtkExpander() {
        this("");
    }

    /**
     * Creates a new expander using label as the text of the label.
     *
     * @param label The text of the label.
     *              <p>
     *              The argument can be NULL.
     */
    public GtkExpander(String label) {
        super(library.gtk_expander_new(label));
    }

    /**
     * Creates a new expander using label as the text of the label.
     * <p>
     * If characters in label are preceded by an underscore, they are underlined. If you need a literal underscore
     * character in a label, use "__" (two underscores). The first underlined character represents a keyboard
     * accelerator called a mnemonic.
     * <p>
     * Pressing Alt and that key activates the button.
     *
     * @param label The text of the label with an underscore in front of the mnemonic character.
     *              <p>
     *              The argument can be NULL.
     */
    public GtkExpander(MnemonicLabel label) {
        super(library.gtk_expander_new_with_mnemonic(label.getMnemonicLabel()));
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
     * Gets the child widget of expander.
     *
     * @return the child widget of expander, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_expander_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of expander.
     *
     * @param w The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget w) {
        library.gtk_expander_set_child(getCReference(), pointerOrNull(w));
    }

    /**
     * Fetches the text from a label widget.
     *
     * @return The text of the label widget. This string is owned by the widget and must not be modified or freed.
     */
    public Option<String> getLabel() {
        return new Option<>(library.gtk_expander_get_label(getCReference()));
    }

    /**
     * Sets the text of the label of the expander to label.
     * <p>
     * This will also clear any previously set labels.
     *
     * @param label A string.
     *              <p>
     *              The argument can be NULL.
     */
    public void setLabel(String label) {
        library.gtk_expander_set_label(getCReference(), label);
    }

    /**
     * Retrieves the label widget
     *
     * @return The label widget, if defined
     */
    public Option<GtkWidget> getWidgetLabel() {
        Option<Pointer> p = new Option<>(library.gtk_expander_get_label_widget(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Queries a GtkExpander and returns its current state.
     * <p>
     * Returns TRUE if the child widget is revealed.
     *
     * @return The current state of the expander.
     */
    public boolean isExpanded() {
        return library.gtk_expander_get_expanded(getCReference());
    }

    /**
     * Sets the state of the expander.
     * <p>
     * Set to TRUE, if you want the child widget to be revealed, and FALSE if you want the child widget to be hidden.
     *
     * @param isExpanded Whether the child widget is revealed.
     */
    public void setExpanded(boolean isExpanded) {
        library.gtk_expander_set_expanded(getCReference(), isExpanded);
    }

    /**
     * Returns whether an underline in the text indicates a mnemonic.
     *
     * @return TRUE if an embedded underline in the expander label indicates the mnemonic accelerator keys.
     */
    public boolean isLabelMnemonic() {
        return library.gtk_expander_get_use_underline(getCReference());
    }

    /**
     * If true, an underline in the text indicates a mnemonic.
     *
     * @param isMnemonic TRUE if underlines in the text indicate mnemonics.
     */
    public void setLabelMnemonic(boolean isMnemonic) {
        library.gtk_expander_set_use_underline(getCReference(), isMnemonic);
    }

    /**
     * Returns whether the label's text is interpreted as Pango markup.
     *
     * @return TRUE if the label's text will be parsed for markup.
     */
    public boolean isLabelPangoMarkup() {
        return library.gtk_expander_get_use_markup(getCReference());
    }

    /**
     * Sets whether the text of the label contains Pango markup.
     *
     * @param labelIsPangoMarkup TRUE if the label's text should be parsed for markup.
     */
    public void setIsLabelPangoMarkup(boolean labelIsPangoMarkup) {
        library.gtk_expander_set_use_markup(getCReference(), labelIsPangoMarkup);
    }

    /**
     * Set the label widget for the expander.
     * <p>
     * This is the widget that will appear embedded alongside the expander arrow.
     *
     * @param w The new label widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setLabelWidget(GtkWidget w) {
        library.gtk_expander_set_label_widget(getCReference(), pointerOrNull(w));
    }

    /**
     * Sets whether the expander will resize the top-level widget containing the expander upon resizing and collapsing.
     *
     * @param willResize Whether to resize the top-level.
     */
    public void shouldResize(boolean willResize) {
        library.gtk_expander_set_resize_toplevel(getCReference(), willResize);
    }

    /**
     * Returns whether the expander will resize the top-level widget containing the expander upon resizing and
     * collapsing.
     *
     * @return If TRUE, the expander will resize the toplevel widget containing the expander upon expanding and
     *         collapsing.
     */
    public boolean willResize() {
        return library.gtk_expander_get_resize_toplevel(getCReference());
    }

    @SuppressWarnings("SameParameterValue")
    public static class Signals extends GtkWidget.Signals {
        public static final Signals ACTIVATE = new Signals("activate");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkExpanderLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget of expander.
         *
         * @param expander self
         * @return The child widget of expander.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_expander_get_child(Pointer expander);

        /**
         * Queries a GtkExpander and returns its current state.
         * <p>
         * Returns TRUE if the child widget is revealed.
         *
         * @param expander self
         * @return The current state of the expander.
         */
        public native boolean gtk_expander_get_expanded(Pointer expander);

        /**
         * Fetches the text from a label widget.
         * <p>
         * This is including any embedded underlines indicating mnemonics and Pango markup, as set by
         * gtk_expander_set_label(). If the label text has not been set the return value will be NULL. This will be the
         * case if you create an empty button with gtk_button_new() to use as a container.
         *
         * @param expander self
         * @return The text of the label widget. This string is owned by the widget and must not be modified or freed.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_expander_get_label(Pointer expander);

        /**
         * Retrieves the label widget for the frame.
         *
         * @param expander self
         * @return The label widget. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_expander_get_label_widget(Pointer expander);

        /**
         * Returns whether the expander will resize the toplevel widget containing the expander upon resizing and
         * collapsing.
         *
         * @param expander self
         * @return The "resize toplevel" setting.
         */
        public native boolean gtk_expander_get_resize_toplevel(Pointer expander);

        /**
         * Returns whether the label's text is interpreted as Pango markup.
         *
         * @param expander self
         * @return TRUE if the label's text will be parsed for markup.
         */
        public native boolean gtk_expander_get_use_markup(Pointer expander);

        /**
         * Returns whether an underline in the text indicates a mnemonic.
         *
         * @param expander self
         * @return TRUE if an embedded underline in the expander label indicates the mnemonic accelerator keys.
         */
        public native boolean gtk_expander_get_use_underline(Pointer expander);

        /**
         * Creates a new expander using label as the text of the label.
         *
         * @param label The text of the label.
         *              <p>
         *              The argument can be NULL.
         * @return A new GtkExpander widget. Type: GtkExpander
         */
        public native Pointer gtk_expander_new(String label);

        /**
         * Creates a new expander using label as the text of the label.
         * <p>
         * If characters in label are preceded by an underscore, they are underlined. If you need a literal underscore
         * character in a label, use "__" (two underscores). The first underlined character represents a keyboard
         * accelerator called a mnemonic.
         * <p>
         * Pressing Alt and that key activates the button.
         *
         * @param label The text of the label with an underscore in front of the mnemonic character.
         *              <p>
         *              The argument can be NULL.
         * @return A new GtkExpander widget. Type: GtkExpander
         */
        public native Pointer gtk_expander_new_with_mnemonic(String label);

        /**
         * Sets the child widget of expander.
         *
         * @param expander self
         * @param child    The child widget. Type: GtkWidget
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_expander_set_child(Pointer expander, Pointer child);

        /**
         * Sets the state of the expander.
         * <p>
         * Set to TRUE, if you want the child widget to be revealed, and FALSE if you want the child widget to be
         * hidden.
         *
         * @param expander self
         * @param expanded Whether the child widget is revealed.
         */
        public native void gtk_expander_set_expanded(Pointer expander, boolean expanded);

        /**
         * Sets the text of the label of the expander to label.
         * <p>
         * This will also clear any previously set labels.
         *
         * @param expander self
         * @param label    A string.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_expander_set_label(Pointer expander, String label);

        /**
         * Set the label widget for the expander.
         * <p>
         * This is the widget that will appear embedded alongside the expander arrow.
         *
         * @param expander     self
         * @param label_widget The new label widget.
         *                     <p>
         *                     The argument can be NULL.
         */
        public native void gtk_expander_set_label_widget(Pointer expander, Pointer label_widget);

        /**
         * Sets whether the expander will resize the toplevel widget containing the expander upon resizing and
         * collapsing.
         *
         * @param expander        self
         * @param resize_toplevel Whether to resize the toplevel.
         */
        public native void gtk_expander_set_resize_toplevel(Pointer expander, boolean resize_toplevel);

        /**
         * Sets whether the text of the label contains Pango markup.
         *
         * @param expander   self
         * @param use_markup TRUE if the label's text should be parsed for markup.
         */
        public native void gtk_expander_set_use_markup(Pointer expander, boolean use_markup);

        public native void gtk_expander_set_use_underline(Pointer expander, boolean use_underline);
    }
}
