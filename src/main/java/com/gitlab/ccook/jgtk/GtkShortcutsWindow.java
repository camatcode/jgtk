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

import com.gitlab.ccook.jgtk.gtk.*;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkNative;
import com.gitlab.ccook.jgtk.interfaces.GtkRoot;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;

@SuppressWarnings("unchecked")
public class GtkShortcutsWindow extends GtkWindow implements GtkAccessible, GtkBuildable, GtkNative, GtkRoot {
    public GtkShortcutsWindow(Pointer pointer) {
        this.cReference = pointer;
    }

    public Option<GtkHeaderBar> getHeader() {
        for (GtkWidget child : getChildren()) {
            if (child instanceof GtkHeaderBar) {
                return new Option<>((GtkHeaderBar) child);
            }
        }
        return Option.NONE;
    }

    public Option<GtkScrolledWindow> getInternalSearch() {
        Option<GtkStack> stack = getStack();
        GListModel<GtkStackPage> pages1 = stack.get().getPages();
        Option<GtkWidget> internalSearch = stack.get().getChild("internal-search");
        if (internalSearch.isDefined()) {
            return new Option<>((GtkScrolledWindow) internalSearch.get());
        }
        return Option.NONE;
    }

    private Option<GtkStack> getStack() {
        for (GtkWidget child : getChildren()) {
            if (child instanceof GtkBox) {
                GtkBox box = (GtkBox) child;
                for (GtkWidget cc : box.getChildren()) {
                    if (cc instanceof GtkStack) return new Option<>((GtkStack) cc);
                }
            }
        }
        return Option.NONE;
    }

    public Option<GtkGrid> getNoSearchResults() {
        Option<GtkStack> stack = getStack();
        GListModel<GtkStackPage> pages1 = stack.get().getPages();
        Option<GtkWidget> noResults = stack.get().getChild("no-search-results");
        if (noResults.isDefined()) {
            return new Option<>((GtkGrid) noResults.get());
        }
        return Option.NONE;
    }

    public Option<GtkSearchBar> getSearchBar() {
        for (GtkWidget child : getChildren()) {
            if (child instanceof GtkBox) {
                GtkBox box = (GtkBox) child;
                for (GtkWidget cc : box.getChildren()) {
                    if (cc instanceof GtkSearchBar) return new Option<>((GtkSearchBar) cc);
                }
            }
        }
        return Option.NONE;
    }

    public Option<GtkShortcutsSection> getSection() {
        Option<GtkStack> stack = getStack();
        GListModel<GtkStackPage> pages1 = stack.get().getPages();
        Option<GtkWidget> shortcuts = stack.get().getChild("shortcuts");
        if (shortcuts.isDefined()) {
            return new Option<>((GtkShortcutsSection) shortcuts.get());
        }
        return Option.NONE;

    }

    public Option<String> getSectionName() {
        Option<GValue> s = getProperty("section-name");
        if (s.isDefined()) {
            return new Option<>(s.get().getText());
        }
        return Option.NONE;
    }

    public Option<String> getViewName() {
        Option<GValue> s = getProperty("view-name");
        if (s.isDefined()) {
            return new Option<>(s.get().getText());
        }
        return Option.NONE;
    }
}
