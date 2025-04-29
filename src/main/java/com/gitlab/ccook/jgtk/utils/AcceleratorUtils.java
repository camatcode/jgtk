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
package com.gitlab.ccook.jgtk.utils;

import com.gitlab.ccook.jgtk.enums.GdkKeyVal;
import com.gitlab.ccook.jgtk.enums.GdkModifierType;
import com.gitlab.ccook.jna.GtkLibrary;
import com.sun.jna.ptr.PointerByReference;


@SuppressWarnings("GrazieInspection")
public class AcceleratorUtils {

    protected final static GtkLibrary library = new GtkLibrary();

    /**
     * gtk_accelerator_parse:
     * <p>
     * modifier mask
     * <p>
     * Parses a string representing an accelerator.
     * <p>
     * The format looks like "`<Control>a`" or "`<Shift><Alt>F1`".
     * <p>
     * The parser is fairly liberal and allows lower or upper case, and also
     * abbreviations such as "`<Ctl>`" and "`<Ctrl>`".
     * <p>
     * Key names are parsed using [func@Gdk.keyval_from_name]. For character keys
     * the name is not the symbol, but the lowercase name, e.g. one would use
     * "`<Ctrl>minus`" instead of "`<Ctrl>-`".
     * <p>
     * Modifiers are enclosed in angular brackets `<>`, and match the
     * [flags@Gdk.ModifierType] mask:
     * <p>
     * - `<Shift>` for `GDK_SHIFT_MASK`
     * - `<Ctrl>` for `GDK_CONTROL_MASK`
     * - `<Alt>` for `GDK_ALT_MASK`
     * - `<Meta>` for `GDK_META_MASK`
     * - `<Super>` for `GDK_SUPER_MASK`
     * - `<Hyper>` for `GDK_HYPER_MASK`
     * <p>
     *
     * @param acceleratorString The String to check
     * @return TRUE if string can be parsed; false, gtk encountered an error parsing
     */
    public boolean canParseAcceleratorString(String acceleratorString) {
        PointerByReference pointerToAcceleratorKey = new PointerByReference();// will be set to 0 if failure
        PointerByReference pointerToAcceleratorMods = new PointerByReference();// will be set to 0 if failure
        return library.gtk_accelerator_parse(acceleratorString, pointerToAcceleratorKey, pointerToAcceleratorMods);
    }

    /**
     * Converts an accelerator key-val and modifier mask into a string which can be used to represent the accelerator to
     * the user.
     *
     * @param acceleratorKey  Accelerator key to convert.
     * @param acceleratorMods Accelerator modifier mask.
     * @return A newly-allocated string representing the accelerator for displaying.
     */
    public String convertAccelKeyToStringForDisplay(GdkKeyVal acceleratorKey, GdkModifierType acceleratorMods) {
        return library.gtk_accelerator_get_label(acceleratorKey.getCValue(), acceleratorMods.getCValue());
    }

    /**
     * Converts an accelerator key-val and modifier mask into a string parse-ready by gtk_accelerator_parse().
     * <p>
     * For example, if you pass in %GDK_KEY_q and GDK_CONTROL_MASK, this function returns <Control>q.
     * <p>
     * If you need to display accelerators in the user interface, see convertAccelKeyToStringForDisplay().
     *
     * @param acceleratorKey  Accelerator key to convert.
     * @param acceleratorMods Accelerator modifier mask.
     * @return A newly-allocated accelerator name.
     */
    public String convertAcceleratorKeyToParsableString(GdkKeyVal acceleratorKey, GdkModifierType acceleratorMods) {
        return library.gtk_accelerator_name(acceleratorKey.getCValue(), acceleratorMods.getCValue());
    }

    public GdkModifierType getDefaultModifierMask() {
        return GdkModifierType.getTypeFromCValue(library.gtk_accelerator_get_default_mod_mask());
    }

    /**
     * Determines whether a given keyval and modifier mask constitute a valid keyboard accelerator.
     * <p>
     * For example, the %GDK_KEY_a keyval plus GDK_CONTROL_MASK mark is valid, and matches the "Ctrl+a" accelerator.
     * But, you can't, for instance, use the %GDK_KEY_Control_L keyval as an accelerator.
     *
     * @param acceleratorKey A GDKKeyVal to determine if valid.
     * @param modifiers      Modifiers of AcceleratorKey
     * @return TRUE if valid Accelerator, False if invalid
     */
    public boolean isAcceleratorKeyValid(GdkKeyVal acceleratorKey, GdkModifierType modifiers) {
        return library.gtk_accelerator_valid(acceleratorKey.getCValue(), modifiers.getCValue());
    }
}
