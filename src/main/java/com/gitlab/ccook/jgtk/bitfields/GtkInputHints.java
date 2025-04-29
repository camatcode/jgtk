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
public enum GtkInputHints {
    GTK_INPUT_HINT_NONE(0),
    GTK_INPUT_HINT_SPELLCHECK(1 << 0),
    GTK_INPUT_HINT_NO_SPELLCHECK(1 << 1),
    GTK_INPUT_HINT_WORD_COMPLETION(1 << 2),
    GTK_INPUT_HINT_LOWERCASE(1 << 3),
    GTK_INPUT_HINT_UPPERCASE_CHARS(1 << 4),
    GTK_INPUT_HINT_UPPERCASE_WORDS(1 << 5),
    GTK_INPUT_HINT_UPPERCASE_SENTENCES(1 << 6),
    GTK_INPUT_HINT_INHIBIT_OSK(1 << 7),
    GTK_INPUT_HINT_VERTICAL_WRITING(1 << 8),
    GTK_INPUT_HINT_EMOJI(1 << 9),
    GTK_INPUT_HINT_NO_EMOJI(1 << 10),
    GTK_INPUT_HINT_PRIVATE(1 << 11);


    private final int cValue;

    GtkInputHints(int cValue) {
        this.cValue = cValue;
    }


    public static int getCValueFromHints(GtkInputHints... hints) {
        int cValue = 0;
        for (GtkInputHints h : hints) {
            cValue |= h.cValue;
        }
        return cValue;
    }

    public static List<GtkInputHints> getHintsFromCValue(int cValue) {
        List<GtkInputHints> hints = new ArrayList<>();
        if (cValue == GTK_INPUT_HINT_NONE.getCValue()) {
            // hints.add(GTK_INPUT_HINT_NONE);
            return hints;
        }
        for (GtkInputHints h : GtkInputHints.values()) {
            if (h.cValue != 0) {
                if ((cValue | h.getCValue()) == cValue) {
                    hints.add(h);
                }
            }
        }
        return hints;
    }


    public int getCValue() {
        return cValue;
    }
}
