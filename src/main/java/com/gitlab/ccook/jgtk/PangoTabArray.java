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
package com.gitlab.ccook.jgtk;

import com.gitlab.ccook.jgtk.enums.PangoTabAlign;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class PangoTabArray extends JGTKObject {
    public PangoTabArray(Pointer cReference) {
        super(cReference);
    }

    public PangoTabArray(int initSize, boolean positionsInPixels) {
        super(library.pango_tab_array_new(initSize, positionsInPixels));
    }

    /**
     * Deserializes a PangoTabArray from a string.
     * <p>
     * This is the counterpart to pango_tab_array_to_string().
     * See that functions for details about the format.
     *
     * @param text to deserialize
     * @return PangoTabArray from serialized string
     */
    public static Option<PangoTabArray> fromString(String text) {
        if (text != null) {
            Option<Pointer> p = new Option<>(library.pango_tab_array_from_string(text));
            if (p.isDefined()) {
                return new Option<>(new PangoTabArray(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Returns TRUE if the tab positions are in pixels, FALSE if they are in Pango units.
     *
     * @return Whether positions are in pixels.
     */
    public boolean arePositionsInPixels() {
        return library.pango_tab_array_get_positions_in_pixels(getCReference());
    }

    /**
     * Copies a PangoTabArray.
     *
     * @return The newly allocated PangoTabArray, which should be freed with pango_tab_array_free().
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public PangoTabArray clone() {
        return new PangoTabArray(library.pango_tab_array_copy(getCReference()));
    }

    /**
     * Frees a tab array and associated resources.
     */
    public void free() {
        library.pango_tab_array_free(getCReference());
    }

    /**
     * Gets the Unicode character to use as decimal point.
     * <p>
     * This is only relevant for tabs with PANGO_TAB_DECIMAL alignment, which align content at the first occurrence of
     * the decimal point character.
     * <p>
     * The default value of 0 means that Pango will use the decimal point according to the current locale.
     *
     * @param tabStopIndex The index of a tab stop.
     * @return Decimal point character
     */
    public char getDecimalPoint(int tabStopIndex) {
        return library.pango_tab_array_get_decimal_point(getCReference(), tabStopIndex);
    }

    /**
     * @return tab positions and alignments for each index
     */
    public List<Pair<Integer, PangoTabAlign>> getTabs() {
        List<Pair<Integer, PangoTabAlign>> toReturn = new ArrayList<>();
        for (int i = 0; i < getNumberOfTabStops(); i++) {
            Option<Pair<Integer, PangoTabAlign>> tabAlignmentAndPosition = getTabAlignmentAndPosition(i);
            if (tabAlignmentAndPosition.isDefined()) {
                toReturn.add(tabAlignmentAndPosition.get());
            }
        }
        return toReturn;
    }

    /**
     * Gets the number of tab stops in tab_array.
     *
     * @return The number of tab stops in the array.
     */
    public int getNumberOfTabStops() {
        return library.pango_tab_array_get_size(getCReference());
    }

    /**
     * Gets the alignment and position of a tab stop.
     *
     * @param index Tab stop index.
     * @return First - Location to store tab position, if defined.
     *         Second - PangoTabAlign where the text appears relative to the tab stop position, if defined
     */
    public Option<Pair<Integer, PangoTabAlign>> getTabAlignmentAndPosition(int index) {
        if (index >= 0) {
            PointerByReference alignmentStorage = new PointerByReference();
            PointerByReference locationStorage = new PointerByReference();
            library.pango_tab_array_get_tab(getCReference(), index, alignmentStorage, locationStorage);
            int alignment = alignmentStorage.getPointer().getInt(0);
            int location = locationStorage.getPointer().getInt(0);
            Option<PangoTabAlign> alignOpt = new Option<>(PangoTabAlign.getAlignFromCValue(alignment));
            if (alignOpt.isDefined()) {
                return new Option<>(new Pair<>(location, alignOpt.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Resizes a tab array.
     * <p>
     * You must subsequently initialize any tabs that were added as a result of growing the array.
     *
     * @param newSize New size of the array.
     */
    public void resize(int newSize) {
        if (newSize >= 0) {
            library.pango_tab_array_resize(getCReference(), newSize);
        }
    }

    /**
     * Sets the Unicode character to use as decimal point.
     * <p>
     * This is only relevant for tabs with PANGO_TAB_DECIMAL alignment, which align content at the first occurrence of
     * the decimal point character.
     * <p>
     * By default, Pango uses the decimal point according to the current locale.
     *
     * @param tabIndex         The index of a tab stop.
     * @param decimalPointChar The decimal point to use.
     */
    public void setDecimalPointChar(int tabIndex, char decimalPointChar) {
        library.pango_tab_array_set_decimal_point(getCReference(), tabIndex, decimalPointChar);
    }

    /**
     * Sets the alignment and location of a tab stop.
     *
     * @param tabIndex The index of a tab stop.
     * @param align    Tab alignment.
     * @param position Tab location in Pango units.
     */
    public void setPositionAndAlignment(int tabIndex, PangoTabAlign align, int position) {
        if (align != null) {
            library.pango_tab_array_set_tab(getCReference(), tabIndex, align.getCValue(), position);
        }
    }

    /**
     * Sets whether positions in this array are specified in pixels.
     *
     * @param arePositionsInPixels Whether positions are in pixels.
     */
    public void setPositionsInPixels(boolean arePositionsInPixels) {
        library.pango_tab_array_set_positions_in_pixels(getCReference(), arePositionsInPixels);
    }

    /**
     * Utility function to ensure that the tab stops are in increasing order.
     */
    public void sort() {
        library.pango_tab_array_sort(getCReference());
    }

    /**
     * Serializes a PangoTabArray to a string.
     * <p>
     * No guarantees are made about the format of the string, it may change between Pango versions.
     * <p>
     * The intended use of this function is testing and debugging. The format is not meant as a permanent storage
     * format.
     *
     * @return String representation of PangoTabArray
     */
    @Override
    public String toString() {
        return library.pango_tab_array_to_string(getCReference());
    }
}
