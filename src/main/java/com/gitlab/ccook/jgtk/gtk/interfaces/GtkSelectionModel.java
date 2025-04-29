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
package com.gitlab.ccook.jgtk.gtk.interfaces;

import com.gitlab.ccook.jgtk.GtkBitset;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.interfaces.GtkInterface;


public interface GtkSelectionModel extends GtkInterface {
    /**
     * Requests to deselect all items in the model.
     *
     * @return TRUE if this action was supported and no fallback should be tried.
     *         This does not mean that all items are now deselected.
     */
    default boolean deselectAll() {
        return library.gtk_selection_model_unselect_all(getCReference());
    }

    /**
     * Requests to deselect an item in the model.
     *
     * @param position The position of the item to deselect.
     * @return TRUE if this action was supported and no fallback should be tried.
     *         This does not mean the item was deselected.
     */
    default boolean deselectItem(int position) {
        return library.gtk_selection_model_unselect_item(getCReference(), position);
    }

    /**
     * Requests to deselect a range of items in the model.
     *
     * @param startPosition The first item to deselect.
     * @param nItems        The number of items to deselect.
     * @return TRUE if this action was supported and no fallback should be tried.
     *         This does not mean the range was deselected.
     */
    default boolean deselectRange(int startPosition, int nItems) {
        return library.gtk_selection_model_unselect_range(getCReference(), startPosition, nItems);
    }

    default GtkBitset getSelection() {
        return new GtkBitset(library.gtk_selection_model_get_selection(getCReference()));
    }

    /**
     * Gets the set of selected items in a range.
     * <p>
     * This function is an optimization for gtk_selection_model_get_selection() when you are only interested in part
     * of the model's selected state. A common use case is in response to the
     * GtkSelectionModel::selection-changed signal.
     *
     * @param startPos Start of the queried range.
     * @param nItems   Number of items in the queried range.
     * @return A GtkBitset that matches the selection state for the given range with all other values being undefined.
     *         The bitset must not be modified.
     */
    default GtkBitset getSelectionInRange(int startPos, int nItems) {
        return new GtkBitset(library.gtk_selection_model_get_selection_in_range(getCReference(), startPos, nItems));
    }

    /**
     * Checks if the given item is selected.
     *
     * @param position The position of the item to query.
     * @return TRUE if the item is selected.
     */
    default boolean isSelected(int position) {
        return library.gtk_selection_model_is_selected(getCReference(), position);
    }

    /**
     * Helper function for implementations of GtkSelectionModel.
     * <p>
     * Call this when a selection changes to emit the GtkSelectionModel::selection-changed signal.
     *
     * @param position The first changed item.
     * @param nItems   The number of changed items.
     */
    default void notifySelectionChanged(int position, int nItems) {
        library.gtk_selection_model_selection_changed(getCReference(), position, nItems);
    }

    /**
     * Make selection changes.
     * <p>
     * This is the most advanced selection updating method that allows the most fine-grained control over selection
     * changes. If you can, you should try the simpler versions, as implementations are more likely to implement
     * support for those.
     * <p>
     * Requests that the selection state of all positions set in mask be updated to the respective value in the
     * selected bitmask.
     * <p>
     * mask and selected must not be modified. They may refer to the same bitset, which would mean that every item in
     * the set should be selected.
     *
     * @param selected Bitmask specifying if items should be selected or unselected.
     * @param mask     Bitmask specifying which items should be updated.
     */
    default void select(GtkBitset selected, GtkBitset mask) {
        if (selected != null && mask != null) {
            library.gtk_selection_model_set_selection(getCReference(), selected.getCReference(), mask.getCReference());
        }
    }

    /**
     * Requests to select all items in the model.
     *
     * @return TRUE if this action was supported and no fallback should be tried.
     *         This does not mean that all items are now selected
     */
    default boolean selectAll() {
        return library.gtk_selection_model_select_all(getCReference());
    }

    /**
     * Requests to select an item in the model.
     *
     * @param position       The position of the item to select.
     * @param deselectOthers Whether previously selected items should be deselected.
     * @return TRUE if this action was supported and no fallback should be tried.
     *         This does not mean the item was selected.
     */
    default boolean selectItem(int position, boolean deselectOthers) {
        return library.gtk_selection_model_select_item(getCReference(), position, deselectOthers);
    }

    /**
     * Requests to select a range of items in the model.
     *
     * @param position       The first item to select.
     * @param nItems         The number of items to select.
     * @param deselectOthers Whether previously selected items should be deselected.
     * @return TRUE if this action was supported and no fallback should be tried.
     *         This does not mean the range was selected.
     */
    default boolean selectItems(int position, int nItems, boolean deselectOthers) {
        return library.gtk_selection_model_select_range(getCReference(), position, nItems, deselectOthers);
    }

    class Signals extends GtkWidget.Signals {
        public static final Signals SELECTION_CHANGED = new Signals("selection-changed");

        @SuppressWarnings("SameParameterValue")
        protected Signals(final String detailedName) {
            super(detailedName);
        }
    }
}
