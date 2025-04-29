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
import com.gitlab.ccook.jgtk.IconName;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.MnemonicLabel;
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


/**
 * The GtkButton widget is generally used to trigger a callback function that is called when the button is pressed.
 * <p>
 * An example GtkButton
 * <p>
 * The GtkButton widget can hold any valid child widget. That is, it can hold almost any other standard GtkWidget.
 * The most commonly used child is the GtkLabel.
 */
@SuppressWarnings({"unchecked", "unused"})
public class GtkButton extends GtkWidget implements GtkAccessible, GtkActionable, GtkBuildable {

    private static final GtkButtonLibrary library = new GtkButtonLibrary();

    /**
     * Creates a GtkButton widget with a GtkLabel child.
     *
     * @param label The text you want the GtkLabel to hold. If it contains '_', it's to be interpreted as mnemonic.
     */
    public GtkButton(String label) {
        super(handleCtor(label));
    }

    private static Pointer handleCtor(String label) {
        AssertionUtils.assertNotNull(GtkButton.class, "ctor: Label is null", label);
        if (label.contains("_")) {
            return library.gtk_button_new_with_mnemonic(label);
        } else {
            return library.gtk_button_new_with_label(label);
        }
    }

    /**
     * Creates a new GtkButton containing a label.
     * <p>
     * If characters in label are preceded by an underscore, they are underlined.
     * If you need a literal underscore character in a label, use "__" (two underscores).
     * The first underlined character represents a keyboard accelerator called a mnemonic.
     * Pressing Alt and that key activates the button.
     *
     * @param label Mnemonic Label to use
     */
    public GtkButton(MnemonicLabel label) {
        super(handleCtor(label));
    }

    private static Pointer handleCtor(MnemonicLabel label) {
        AssertionUtils.assertNotNull(GtkButton.class, "ctor: Label is null", label);
        return library.gtk_button_new_with_mnemonic(label.getMnemonicLabel());
    }

    /**
     * Creates a new button containing an icon from the current icon theme.
     * <p>
     * If the icon name isn't known, a "broken image" icon will be displayed instead.
     * If the current icon theme is changed, the icon will be updated appropriately.
     *
     * @param iconName An icon name.
     *                 <p>
     *                 The argument can be NULL.
     */
    public GtkButton(IconName iconName) {
        super(handleCtor(iconName));
    }

    private static Pointer handleCtor(IconName iconName) {
        return library.gtk_button_new_from_icon_name(iconName == null ? null : iconName.getIconName());
    }

    /**
     * Creates a new GtkButton widget.
     * <p>
     * To add a child widget to the button, use gtk_button_set_child().
     */
    public GtkButton() {
        super(library.gtk_button_new());
    }

    public GtkButton(Pointer cRef) {
        super(cRef);
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
     * Gets the child widget of button.
     *
     * @return The child widget of button, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> pointer = new Option<>(library.gtk_button_get_child(cReference));
        if (pointer.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(pointer.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of button.
     * <p>
     * Note that by using this API, you take full responsibility for setting up the proper accessibility label and
     * description information for button. Most likely, you'll either set the accessibility label or description for
     * button explicitly, or you'll set a labelled-by or described-by relations from child to button.
     *
     * @param widget The child widget.
     */
    public void setChild(GtkWidget widget) {
        library.gtk_button_set_child(cReference, Pointer.NULL);
        library.gtk_button_set_child(cReference, pointerOrNull(widget));
        if (widget instanceof GtkLabel) {
            GtkLabel l = (GtkLabel) widget;
            setLabel(l.getLabel());
        }
    }

    /**
     * Returns the icon name of the button.
     * <p>
     * If the icon name has not been set with gtk_button_set_icon_name() the return value will be NULL.
     * This will be the case if you create an empty button with gtk_button_new() to use as a container.
     *
     * @return Icon name, if defined (This may be NONE)
     */
    public Option<IconName> getIconName() {
        String iconName = library.gtk_button_get_icon_name(cReference);
        if (iconName != null) {
            return new Option<>(new IconName(iconName));
        }
        return Option.NONE;
    }

    /**
     * Adds a GtkImage with the given icon name as a child.
     * <p>
     * If button already contains a child widget, that child widget will be removed and replaced with the image.
     *
     * @param iconName An icon name.
     */
    public void setIconName(IconName iconName) {
        if (iconName != null && iconName.getIconName() != null) {
            library.gtk_button_set_icon_name(cReference, iconName.getIconName());
        }
    }

    /**
     * Fetches the text from the label of the button.
     * <p>
     * If the label text has not been set with gtk_button_set_label() the return value will be NULL.
     * This will be the case if you create an empty button with gtk_button_new() to use as a container.
     *
     * @return label of the button, if defined
     */
    public Option<String> getLabel() {
        return new Option<>(library.gtk_button_get_label(cReference));
    }

    /**
     * Sets the text of the label of the button to label.
     * <p>
     * This will also clear any previously set labels.
     *
     * @param label label to set
     */
    public void setLabel(String label) {
        library.gtk_button_set_label(cReference, label == null ? "" : label);
    }

    /**
     * Returns whether the button has a frame.
     *
     * @return TRUE if the button has a frame.
     */
    public boolean hasFrame() {
        return library.gtk_button_get_has_frame(cReference);
    }

    /**
     * Gets whether underlines are interpreted as mnemonics.
     *
     * @return TRUE if an embedded underline in the button label indicates the mnemonic accelerator keys.
     */
    public boolean isLabelMnemonic() {
        return library.gtk_button_get_use_underline(cReference);
    }

    /**
     * Sets the style of the button.
     * <p>
     * Buttons can have a flat appearance or have a frame drawn around them.
     *
     * @param hasFrame Whether the button should have a visible frame.
     */
    public void setHasFrame(boolean hasFrame) {
        library.gtk_button_set_has_frame(cReference, hasFrame);
    }

    /**
     * Sets whether to use underlines as mnemonics.
     * <p>
     * If true, an underline in the text of the button label indicates the next character should be used for the
     * mnemonic accelerator key.
     *
     * @param canUse TRUE if underlines in the text indicate mnemonics.
     */
    public void setUseMnemonics(boolean canUse) {
        library.gtk_button_set_use_underline(cReference, canUse);
    }

    public static class Signals extends GtkWidget.Signals {
        /**
         * Emitted when the button has been activated (pressed and released).
         */
        public final static Signals CLICKED = new Signals("clicked");
        /**
         * Emitted to animate press then release.
         * <p>
         * This is an action signal. Applications should never connect to this signal,
         * but use the GtkButton::clicked signal.
         */
        public static Signals ACTIVATE = new Signals("activate");


        protected Signals(String cValue) {
            super(cValue);
        }
    }

    protected static class GtkButtonLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the child widget of button.
         *
         * @param button self
         * @return The child widget of button. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_button_get_child(Pointer button);

        /**
         * Returns whether the button has a frame.
         *
         * @param button self
         * @return TRUE if the button has a frame.
         */
        public native boolean gtk_button_get_has_frame(Pointer button);

        /**
         * Returns the icon name of the button.
         * <p>
         * If the icon name has not been set with gtk_button_set_icon_name() the return value will be NULL.
         * This will be the case if you create an empty button with gtk_button_new() to use as a container.
         *
         * @param button self
         * @return The icon name set via gtk_button_set_icon_name()
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_button_get_icon_name(Pointer button);

        /**
         * Fetches the text from the label of the button.
         * <p>
         * If the label text has not been set with gtk_button_set_label() the return value will be NULL.
         * This will be the case if you create an empty button with gtk_button_new() to use as a container.
         *
         * @param button self
         * @return The text of the label widget. This string is owned by the widget and must not be modified or freed.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_button_get_label(Pointer button);

        /**
         * Gets whether underlines are interpreted as mnemonics.
         * <p>
         * See gtk_button_set_use_underline().
         *
         * @param button self
         * @return TRUE if an embedded underline in the button label indicates the mnemonic accelerator keys.
         */
        public native boolean gtk_button_get_use_underline(Pointer button);

        /**
         * Creates a new GtkButton widget.
         * <p>
         * To add a child widget to the button, use gtk_button_set_child().
         *
         * @return The newly created GtkButton widget. Type: GtkButton
         */
        public native Pointer gtk_button_new();

        /**
         * Creates a new button containing an icon from the current icon theme.
         * <p>
         * If the icon name isn't known, a "broken image" icon will be displayed instead. If the current icon theme is
         * changed, the icon will be updated appropriately.
         *
         * @param icon_name An icon name.
         * @return A new GtkButton displaying the themed icon. Type: GtkButton
         */
        public native Pointer gtk_button_new_from_icon_name(String icon_name);

        /**
         * Creates a GtkButton widget with a GtkLabel child.
         *
         * @param label The text you want the GtkLabel to hold.
         * @return The newly created GtkButton widget. Type: GtkButton
         */
        public native Pointer gtk_button_new_with_label(String label);

        /**
         * Creates a new GtkButton containing a label.
         * <p>
         * If characters in label are preceded by an underscore, they are underlined.
         * If you need a literal underscore character in a label, use "__" (two underscores).
         * The first underlined character represents a keyboard accelerator called a mnemonic.
         * Pressing Alt and that key activates the button.
         *
         * @param label The text of the button, with an underscore in front of the mnemonic character.
         */
        public native Pointer gtk_button_new_with_mnemonic(String label);

        /**
         * Sets the child widget of button.
         * <p>
         * Note that by using this API, you take full responsibility for setting up the proper accessibility label
         * and description information for button. Most likely, you'll either set the accessibility label or description
         * for button explicitly, or you'll set a labelled-by or described-by relations from child to button.
         *
         * @param button self
         * @param child  The child widget. Type: GtkWidget
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_button_set_child(Pointer button, Pointer child);

        /**
         * Sets the style of the button.
         * <p>
         * Buttons can have a flat appearance or have a frame drawn around them.
         *
         * @param button    self
         * @param has_frame Whether the button should have a visible frame.
         */
        public native void gtk_button_set_has_frame(Pointer button, boolean has_frame);

        /**
         * Adds a GtkImage with the given icon name as a child.
         * <p>
         * If button already contains a child widget, that child widget will be removed and replaced with the image.
         *
         * @param button    self
         * @param icon_name An icon name.
         */
        public native void gtk_button_set_icon_name(Pointer button, String icon_name);

        /**
         * Sets the text of the label of the button to label.
         * <p>
         * This will also clear any previously set labels.
         *
         * @param button self
         * @param label  A string.
         */
        public native void gtk_button_set_label(Pointer button, String label);

        /**
         * Sets whether to use underlines as mnemonics.
         * <p>
         * If true, an underline in the text of the button label indicates the next character should be used for the
         * mnemonic accelerator key.
         *
         * @param button        self
         * @param use_underline TRUE if underlines in the text indicate mnemonics.
         */
        public native void gtk_button_set_use_underline(Pointer button, boolean use_underline);

    }
}
