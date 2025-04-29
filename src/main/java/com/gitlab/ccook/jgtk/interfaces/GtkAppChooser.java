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

import com.gitlab.ccook.jgtk.GAppInfo;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


@SuppressWarnings({"unchecked", "DeprecatedIsStillUsed", "RedundantSuppression"})
@Deprecated
public interface GtkAppChooser extends GtkInterface {

    /**
     * Returns the currently selected application.
     * <p>
     *
     * @return A GAppInfo for the currently selected application.
     * @deprecated since: 4.10. This widget will be removed in GTK 5
     */
    default Option<GAppInfo> getAppInfo() {
        Option<Pointer> p = new Option<>(library.gtk_app_chooser_get_app_info(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GAppInfo(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Returns the content type for which the GtkAppChooser shows applications.
     *
     * @return The content type
     * @deprecated since: 4.10. This widget will be removed in GTK 5
     */
    default String getContentType() {
        return library.gtk_app_chooser_get_content_type(getCReference());
    }

    /**
     * Reloads the list of applications.
     *
     * @deprecated since: 4.10. This widget will be removed in GTK 5
     */
    default void refreshApplicationList() {
        library.gtk_app_chooser_refresh(getCReference());
    }
}
