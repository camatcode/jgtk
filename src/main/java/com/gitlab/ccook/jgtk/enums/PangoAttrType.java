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
package com.gitlab.ccook.jgtk.enums;

public enum PangoAttrType {
    PANGO_ATTR_INVALID("INVALID"), /* 0 is an invalid attribute type */
    PANGO_ATTR_LANGUAGE("PangoAttrLanguage"), /* PangoAttrLanguage */
    PANGO_ATTR_FAMILY("PangoAttrString"), /* PangoAttrString */
    PANGO_ATTR_STYLE("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_WEIGHT("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_VARIANT("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_STRETCH("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_SIZE("PangoAttrSize"), /* PangoAttrSize */
    PANGO_ATTR_FONT_DESC("PangoAttrFontDesc"), /* PangoAttrFontDesc */
    PANGO_ATTR_FOREGROUND("PangoAttrColor"), /* PangoAttrColor */
    PANGO_ATTR_BACKGROUND("PangoAttrColor"), /* PangoAttrColor */
    PANGO_ATTR_UNDERLINE("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_STRIKETHROUGH("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_RISE("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_SHAPE("PangoAttrShape"), /* PangoAttrShape */
    PANGO_ATTR_SCALE("PangoAttrFloat"), /* PangoAttrFloat */
    PANGO_ATTR_FALLBACK("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_LETTER_SPACING("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_UNDERLINE_COLOR("PangoAttrColor"), /* PangoAttrColor */
    PANGO_ATTR_STRIKETHROUGH_COLOR("PangoAttrColor"), /* PangoAttrColor */
    PANGO_ATTR_ABSOLUTE_SIZE("PangoAttrSize"), /* PangoAttrSize */
    PANGO_ATTR_GRAVITY("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_GRAVITY_HINT("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_FONT_FEATURES("PangoAttrFontFeatures"), /* PangoAttrFontFeatures */
    PANGO_ATTR_FOREGROUND_ALPHA("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_BACKGROUND_ALPHA("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_ALLOW_BREAKS("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_SHOW("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_INSERT_HYPHENS("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_OVERLINE("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_OVERLINE_COLOR("PangoAttrColor"), /* PangoAttrColor */
    PANGO_ATTR_LINE_HEIGHT("PangoAttrFloat"), /* PangoAttrFloat */
    PANGO_ATTR_ABSOLUTE_LINE_HEIGHT("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_TEXT_TRANSFORM("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_WORD("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_SENTENCE("PangoAttrInt"), /* PangoAttrInt */
    PANGO_ATTR_BASELINE_SHIFT("PangoAttrSize"), /* PangoAttrSize */
    PANGO_ATTR_FONT_SCALE("PangoAttrInt"); /* PangoAttrInt */

    private final String typeKind;

    PangoAttrType(String typeKind) {
        this.typeKind = typeKind;
    }

    public static int getCValueFromType(PangoAttrType type) {
        for (int i = 0; i < PangoAttrType.values().length; i++) {
            if (PangoAttrType.values()[i] == type) {
                return i;
            }
        }
        return -1;
    }

    public static PangoAttrType getTypeFromCValue(int anInt) {
        return PangoAttrType.values()[anInt];
    }

    public boolean isColor() {
        return typeKind.equals("PangoAttrColor");
    }

    public boolean isInt() {
        return typeKind.equals("PangoAttrInt");
    }

}
