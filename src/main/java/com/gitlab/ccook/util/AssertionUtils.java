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
package com.gitlab.ccook.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssertionUtils {

    private static final Logger log = LoggerFactory.getLogger(AssertionUtils.class);

    public static void assertNotNull(Class<?> location, String msg, Object toTest) {
        if (toTest == null) {
            throw new RuntimeException(location.getSimpleName() + " " + msg);
        }
    }

    public static void assertTrue(Class<?> location, String msg, boolean toTest) {
        if (!toTest) {
            throw new RuntimeException(location.getSimpleName() + " " + msg);
        }
    }

    public static void warnIsNull(Class<?> location, String msg, Object id) {
        if (id == null) {
            log.warn("{} {}", location.getSimpleName(), msg);
        }
    }
}
