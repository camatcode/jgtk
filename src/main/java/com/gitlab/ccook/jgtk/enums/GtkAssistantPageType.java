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

public enum GtkAssistantPageType {
    GTK_ASSISTANT_PAGE_CONTENT,
    GTK_ASSISTANT_PAGE_INTRO,
    GTK_ASSISTANT_PAGE_CONFIRM,
    GTK_ASSISTANT_PAGE_SUMMARY,
    GTK_ASSISTANT_PAGE_PROGRESS,
    GTK_ASSISTANT_PAGE_CUSTOM;

    public static int getCValueForType(GtkAssistantPageType type) {
        for (int i = 0; i < values().length; i++) {
            if (type == GtkAssistantPageType.values()[i]) {
                return i;
            }
        }
        return -1;
    }

    public static GtkAssistantPageType getPageTypeForCValue(int cValue) {
        return GtkAssistantPageType.values()[cValue];
    }
}
