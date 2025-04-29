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


public enum GtkLicense {
    UNKNOWN(0),
    DEVELOPER_SPECIFIED(1),
    GNU_GENERAL_PUBLIC_LICENSE_V2_0_OR_LATER(2),
    GNU_GENERAL_PUBLIC_LICENSE_V3_0_OR_LATER(3),
    GNU_LESSER_GENERAL_PUBLIC_LICENSE_V2_1(4),
    GNU_LESSER_GENERAL_PUBLIC_LICENSE_V3_0(5),
    BSD_STANDARD_LICENSE(6),
    MIT_OR_X11_LICENSE(7),
    ARTISTIC_LICENSE_V2_0(8),
    GNU_GENERAL_PUBLIC_LICENSE_V2_0_ONLY(9),
    GNU_GENERAL_PUBLIC_LICENSE_V3_0_ONLY(10),
    GNU_LESSER_GENERAL_PUBLIC_LICENSE_V2_1_ONLY(11),
    GNU_LESSER_GENERAL_PUBLIC_LICENSE_V3_0_ONLY(12),
    GNU_AFFERO_GENERAL_PUBLIC_LICENSE_V3_0_OR_LATER(13),
    GNU_AFFERO_GENERAL_PUBLIC_LICENSE_V3_0_ONLY(14),
    BSD_3_CLAUSE_LICENSE(15),
    APACHE_LICENSE_V2_0(16),
    MOZILLA_PUBLIC_LICENSE_V2_0(17);

    final private int cValue;

    GtkLicense(int cValue) {
        this.cValue = cValue;
    }

    public static GtkLicense getLicenseFromCValue(int cValue) {
        for (GtkLicense l : GtkLicense.values()) {
            if (l.cValue == cValue) {
                return l;
            }
        }
        return UNKNOWN;
    }

    public int getCValue() {
        return cValue;
    }
}
