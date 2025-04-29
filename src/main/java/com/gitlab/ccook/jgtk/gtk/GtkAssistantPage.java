/*-
 * #%L
 * jgtk
 * %%
 * Copyright (C) 2022 - 2023 JGTK
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

import com.gitlab.ccook.jgtk.GObject;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * GtkAssistantPage is an auxiliary object used by `GtkAssistant.
 *
 * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
 */
@Deprecated
@SuppressWarnings({"unchecked", "DeprecatedIsStillUsed"})
public class GtkAssistantPage extends GObject {
    private static final GtkAssistantPageLibrary library = new GtkAssistantPageLibrary();

    public GtkAssistantPage(Pointer cReference) {
        super(cReference);
    }

    /**
     * Returns the child to which page belongs.
     *
     * @return The child to which page belongs, if defined
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_assistant_page_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    protected static class GtkAssistantPageLibrary implements Library {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns the child to which page belongs.
         *
         * @param page self
         * @return The child to which page belongs. Type: GtkWidget
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_assistant_page_get_child(Pointer page);
    }
}
