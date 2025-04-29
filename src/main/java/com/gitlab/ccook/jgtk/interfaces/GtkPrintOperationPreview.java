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
package com.gitlab.ccook.jgtk.interfaces;

import com.gitlab.ccook.jgtk.GtkWidget;

public interface GtkPrintOperationPreview extends GtkInterface {

    default void finishPreview() {
        library.gtk_print_operation_preview_end_preview(getCReference());
    }

    /**
     * Returns whether the given page is included in the set of pages that have been selected for printing.
     *
     * @param pageNumber A page number.
     * @return TRUE if the page has been selected for printing.
     */
    default boolean isPageSelectedForPrinting(int pageNumber) {
        return library.gtk_print_operation_preview_is_selected(getCReference(), pageNumber);
    }

    /**
     * Renders a page to the preview.
     * <p>
     * This is using the print context that was passed to the GtkPrintOperation::preview handler together with preview.
     * <p>
     * A custom print preview should use this function to render the currently selected page.
     * <p>
     * Note that this function requires a suitable cairo context to be associated with the print context.
     *
     * @param pageNumber page to render
     */
    default void renderPage(int pageNumber) {
        library.gtk_print_operation_preview_render_page(getCReference(), pageNumber);
    }

    class Signals extends GtkWidget.Signals {
        public static final Signals GOT_PAGE_SIZE = new Signals("got-page-size");
        public static final Signals READY = new Signals("ready");

        Signals(String detailedName) {
            super(detailedName);
        }
    }
}
