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

import com.sun.jna.Pointer;
import org.joda.time.DateTime;

public class GDateTime extends JGTKObject {
    public GDateTime(Pointer cReference) {
        super(cReference);
    }

    public GDateTime(DateTime t) {
        super(handleCtor(t));
    }

    private static Pointer handleCtor(DateTime t) {
        String iso = t.toString();
        return library.g_date_time_new_from_iso8601(iso, null);
    }

    public DateTime toDateTime() {
        return DateTime.parse(library.g_date_time_format_iso8601(cReference));
    }
}
