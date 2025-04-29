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

import com.gitlab.ccook.jgtk.errors.GErrorException;
import com.sun.jna.Pointer;

import java.io.File;

public class GdkPixbuf extends JGTKObject {
    public GdkPixbuf(Pointer cReference) {
        super(cReference);
    }

    public GdkPixbuf(File f) throws GErrorException {
        super(handleCtr(f));
    }

    private static Pointer handleCtr(File f) throws GErrorException {
        GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
        Pointer p = library.gdk_pixbuf_new_from_file(f.getAbsolutePath(), error);
        if (error.getPointer() != Pointer.NULL && error.code != 0) {
            throw new GErrorException(new GError(error));
        }
        return p;
    }
}
