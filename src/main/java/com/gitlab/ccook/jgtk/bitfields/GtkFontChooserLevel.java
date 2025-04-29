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
package com.gitlab.ccook.jgtk.bitfields;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("PointlessBitwiseExpression")
public enum GtkFontChooserLevel {
    GTK_FONT_CHOOSER_LEVEL_FAMILY(0),
    GTK_FONT_CHOOSER_LEVEL_STYLE(1 << 0),
    GTK_FONT_CHOOSER_LEVEL_SIZE(1 << 1),
    GTK_FONT_CHOOSER_LEVEL_VARIATIONS(1 << 2),
    GTK_FONT_CHOOSER_LEVEL_FEATURES(1 << 3);

    private final int cValue;

    GtkFontChooserLevel(int cValue) {
        this.cValue = cValue;
    }

    public static int getCValueFromFlags(GtkFontChooserLevel... flags) {
        int cValue = 0;
        for (GtkFontChooserLevel h : flags) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkFontChooserLevel> getFlagsFromCValue(int cValue) {
        List<GtkFontChooserLevel> flags = new ArrayList<>();
        for (GtkFontChooserLevel h : GtkFontChooserLevel.values()) {
            if ((cValue | h.getCValue()) == cValue) {
                flags.add(h);
            }
        }
        return flags;
    }

    public int getCValue() {
        return cValue;
    }

}
