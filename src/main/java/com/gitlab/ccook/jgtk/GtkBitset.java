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

import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings({"unchecked", "MethodDoesntCallSuperMethod"})
public class GtkBitset extends JGTKObject {

    public GtkBitset() {
        super(library.gtk_bitset_new_empty());
    }

    public GtkBitset(int start, int numberOfItems) {
        super(library.gtk_bitset_new_range(start, numberOfItems));
    }

    public GtkBitset(Pointer cReference) {
        super(cReference);
    }


    /**
     * Adds value to self if it wasn't part of it before.
     *
     * @param value Value to add.
     * @return TRUE if value was not part of self and self was changed.
     */
    public boolean add(int value) {
        if (value >= 0) {
            return library.gtk_bitset_add(getCReference(), value);
        }
        return false;
    }

    /**
     * Adds the closed range [first, last], so first, last and all values in between. first must be smaller than last.
     *
     * @param first First value to add.
     * @param last  Last value to add.
     */
    public void addClosedRange(int first, int last) {
        if (first >= 0 && last >= 0 && last > first) {
            library.gtk_bitset_add_range_closed(getCReference(), first, last);
        }
    }

    /**
     * Adds all values from start (inclusive) to start + numberOfItems (exclusive) in self.
     *
     * @param start         First value to add.
     * @param numberOfItems Number of consecutive values to add.
     */
    public void addRange(int start, int numberOfItems) {
        if (start >= 0 && numberOfItems >= 0) {
            library.gtk_bitset_add_range(getCReference(), start, numberOfItems);
        }
    }

    /**
     * Interprets the values as a 2-dimensional boolean grid with the given stride and inside that grid, adds a
     * rectangle with the given width and height.
     *
     * @param start  First value to add.
     * @param width  Width of the rectangle.
     * @param height Height of the rectangle.
     * @param stride Row stride of the grid.
     */
    public void addRectangle(int start, int width, int height, int stride) {
        if (start >= 0 && width >= 0 && height >= 0 && stride >= 0) {
            library.gtk_bitset_add_rectangle(getCReference(), start, width, height, stride);
        }
    }

    /**
     * Removes all values from the bitset so that it is empty again.
     */
    public void clear() {
        library.gtk_bitset_remove_all(getCReference());
    }

    /**
     * Removes all values from start (inclusive) to start + n_items (exclusive) in self.
     *
     * @param start  First value to remove.
     * @param nItems Number of consecutive values from start to remove.
     */
    public void clearRange(int start, int nItems) {
        if (start >= 0 && nItems >= 0) {
            library.gtk_bitset_remove_range(getCReference(), start, nItems);
        }
    }

    /**
     * Removes the closed range [first, last], so first, last and all values in between. first must be smaller than la
     *
     * @param first First value to remove.
     * @param last  Last value to remove.
     */
    public void clearRangeClosed(int first, int last) {
        if (first >= 0 && last >= 0 && last > first) {
            library.gtk_bitset_remove_range_closed(getCReference(), first, last);
        }
    }

    /**
     * Interprets the values as a 2-dimensional boolean grid with the given stride and inside that grid, removes a
     * rectangle with the given width and height.
     *
     * @param start  First value to remove.
     * @param width  Width of the rectangle.
     * @param height Height of the rectangle.
     * @param stride Row stride of the grid.
     */
    public void clearRectangle(int start, int width, int height, int stride) {
        if (start >= 0 && width >= 0 && height >= 0 && stride >= 0) {
            library.gtk_bitset_remove_rectangle(getCReference(), start, width, height, stride);
        }
    }

    /**
     * Creates a clone of self.
     *
     * @return A new bitset that contains the same values as self.
     */
    public GtkBitset clone() {
        return new GtkBitset(library.gtk_bitset_copy(getCReference()));
    }

    /**
     * Checks if the given value has been added to self.
     *
     * @param value The value to check.
     * @return TRUE if value is contained in bitset
     */
    public boolean contains(int value) {
        if (value >= 0) {
            return library.gtk_bitset_contains(getCReference(), value);
        }
        return false;
    }

    /**
     * Sets self to be the symmetric difference of self and other.
     * <p>
     * The symmetric difference is set self to contain all values that were either contained in self or in other,
     * but not in both. This operation is also called an XOR.
     * <p>
     * It is allowed for self and other to be the same bitset. The bitset will be emptied in that case.
     *
     * @param other The GtkBitset to compute the difference from.
     */
    public void disjoint(GtkBitset other) {
        if (other != null) {
            library.gtk_bitset_difference(getCReference(), other.getCReference());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other != null) {
            if (other instanceof GtkBitset) {
                GtkBitset s = (GtkBitset) other;
                return library.gtk_bitset_equals(getCReference(), s.getCReference());
            }
        }
        return false;
    }

    /**
     * If within bounds, returns the value of the nth item in self.
     *
     * @param n Index of the item to get.
     * @return If defined, The value of the nth item in self. Otherwise, NONE
     */
    public Option<Integer> getNth(int n) {
        if (!isEmpty() && n < size()) {
            return new Option<>(library.gtk_bitset_get_nth(getCReference(), n));
        }
        return Option.NONE;
    }

    /**
     * Check if no value is contained in bitset.
     *
     * @return true if no elements are in bitset
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isEmpty() {
        return library.gtk_bitset_is_empty(getCReference());
    }

    /**
     * Gets the number of values that were added to the set.
     * <p>
     * For example, if the set is empty, 0 is returned.
     *
     * @return number of elements in bitset
     */
    public int size() {
        return library.gtk_bitset_get_size(getCReference());
    }

    /**
     * Sets self to be the intersection of self and other.
     * <p>
     * In other words, remove all values from self that are not part of other.
     * <p>
     * It is allowed for self and other to be the same bitset. Nothing will happen in that case.
     *
     * @param b The GtkBitset to intersect with
     */
    @SuppressWarnings("GrazieInspection")
    public void intersect(GtkBitset b) {
        if (b != null) {
            library.gtk_bitset_intersect(getCReference(), b.getCReference());
        }
    }

    /**
     * Returns the largest value in self - if not empty
     *
     * @return Defined if not empty, with the largest value. Otherwise NONE
     */
    public Option<Integer> maximum() {
        if (!isEmpty()) {
            return new Option<>(library.gtk_bitset_get_maximum(getCReference()));
        }
        return Option.NONE;
    }

    /**
     * Returns the smallest value in self if not empty
     *
     * @return Defined if not empty, with the smallest value. Otherwise, NONE.
     */
    public Option<Integer> minimum() {
        if (!isEmpty()) {
            return new Option<>(library.gtk_bitset_get_minimum(getCReference()));
        }
        return Option.NONE;
    }

    /**
     * If the bitset currently contains value, remove value from the bitset
     *
     * @param value value to remove
     */
    public void remove(int value) {
        if (value >= 0) {
            library.gtk_bitset_remove(getCReference(), value);
        }
    }

    /**
     * Shifts all values in self to the left by amount.
     * <p>
     * Values smaller than amount are discarded.
     *
     * @param amount Amount to shift all values to the left.
     */
    public void shiftValuesLeft(int amount) {
        if (amount >= 0) {
            library.gtk_bitset_shift_left(getCReference(), amount);
        }
    }

    /**
     * Shifts all values in self to the right by amount.
     * <p>
     * Values that end up too large to be held in a #guint are discarded.
     *
     * @param amount Amount to shift all values to the right.
     */
    public void shiftValuesRight(int amount) {
        if (amount >= 0) {
            library.gtk_bitset_shift_right(getCReference(), amount);
        }
    }

    /**
     * Sets self to be the subtraction of other from self.
     * <p>
     * In other words, remove all values from self that are part of other.
     * <p>
     * It is allowed for self and other to be the same bitset. The bitset will be emptied in that case.
     *
     * @param s The GtkBitset to subtract.
     */
    @SuppressWarnings("GrazieInspection")
    public void subtract(GtkBitset s) {
        if (s != null) {
            library.gtk_bitset_subtract(getCReference(), s.getCReference());
        }
    }

    /**
     * Sets self to be the union of self and other.
     * <p>
     * That is, add all values from other into self that weren't part of it.
     * <p>
     * It is allowed for self and other to be the same bitset. Nothing will happen in that case.
     *
     * @param s The GtkBitset to union with.
     */
    public void union(GtkBitset s) {
        if (s != null) {
            library.gtk_bitset_union(getCReference(), s.getCReference());
        }
    }
}
