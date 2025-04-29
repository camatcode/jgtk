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
package com.gitlab.ccook.jgtk.enums;

import com.gitlab.ccook.jgtk.structs.GQuark;


public enum KnownQuark {
    GTK_CSS_PARSER_ERROR_QUARK("gtk-css-parser-error-quark"),
    GTK_CSS_PARSER_WARNING_QUARK("gtk-css-parser-warning-quark"),
    GTK_PANGO_CONTEXT("gtk-pango-context"),
    GTK_MNEMONIC_LABELS("gtk-mnemonic-labels"),
    GTK_WIDGET_SIZE_GROUPS("gtk-widget-size-groups"),
    GTK_WIDGET_AUTO_CHILDREN("gtk-widget-auto-children"),
    GTK_WIDGET_FONT_OPTIONS("gtk-widget-font-options"),
    GTK_WIDGET_FONT_MAP("gtk-widget-font-map"),
    GTK_BUILDER_SET_ID("gtk-builder-set-id"),
    GTK_WINDOW_ICON_INFO("gtk-window-icon-info"),
    GDK_DISPLAY_CURRENT_TOOLTIP("gdk-display-current-tooltip"),
    GTK_LABEL_MNEMONICS_VISIBLE_CONNECTED("gtk-label-mnemonics-visible-connected"),
    GTK_FILE_CHOOSER_DELEGATE("gtk-file-chooser-delegate"),
    GTK_NATIVE_PRIVATE("gtk-native-private"),
    GTK_FILE_CHOOSER_ERROR_QUARK("gtk-file-chooser-error-quark"),
    GTK_ICON_THEME_ERROR_QUARK("gtk-icon-theme-error-quark"),
    GTK_SETTINGS("gtk-settings"),
    GTK_FONT_CHOOSER_DELEGATE("gtk-font-chooser-delegate"),
    GTK_SIZE_REQUEST_IN_PROGRESS("gtk-size-request-in-progress"),
    GTK_PRINT_ERROR_QUARK("gtk-print-error-quark"),
    GTK_TEXT_VIEW_TEXT_SELECTION_DATA("gtk-text-view-text-selection-data"),
    GTK_SIGNAL("gtk-signal"),
    GTK_TEXT_VIEW_CHILD("gtk-text-view-child"),
    GTK_DIALOG_ERROR_QUARK("gtk-dialog-error-quark"),
    GTK_BUILDER_ERROR_QUARK("gtk-builder-error-quark"),
    GTK_ENTRY_PASSWORD_HINT("gtk-entry-password-hint"),
    GTK_CONSTRAINT_VFL_PARSER_ERROR_QUARK("gtk-constraint-vfl-parser-error-quark"),
    GTK_PRINT_BACKEND_ERROR_QUARK("gtk-print-backend-error-quark"),
    GTK_RECENT_MANAGER_ERROR_QUARK("gtk-recent-manager-error-quark"),
    GTK_OVERLAY_CHILD_DATA("gtk-overlay-child-data"),
    GDK_X11_NEEDS_ENTER_AFTER_TOUCH_END("gdk-x11-needs-enter-after-touch-end"),
    GDK_ICON_NAME_SET("gdk-icon-name-set"),
    GDK_SURFACE_MOVE_RESIZE("gdk-surface-moveresize");

    public final String quark;

    KnownQuark(String knownQ) {
        this.quark = knownQ;
    }

    public GQuark getQuark() {
        return GQuark.fromStaticString("gtk-css-parser-error-quark");
    }
}
