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
package com.gitlab.ccook.jgtk;

import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;

import java.io.File;

public class GFile extends JGTKObject {
    public GFile(Pointer cReference) {
        super(cReference);
    }

    public GFile(File f) {
        super(library.g_file_new_for_path(f.getAbsolutePath()));
    }

    public boolean exists() {
        return library.g_file_query_exists(getCReference(), Pointer.NULL);
    }

    public String getParseName() {
        return library.g_file_get_parse_name(getCReference());
    }

    public Option<String> getPath() {
        return new Option<>(library.g_file_get_path(getCReference()));
    }
}
