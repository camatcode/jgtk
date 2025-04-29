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

import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


public abstract class JGTKConnectableObject extends JGTKObject {

    public JGTKConnectableObject(Pointer ref) {
        super(ref);
    }


    public void connect(String detailedName, GCallbackFunction fn, Pointer dataRef) {
        connect(detailedName, fn, dataRef, GConnectFlags.G_CONNECT_DEFAULT);
    }

    public void connect(String detailedName, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
        super.connect(detailedName, fn, dataRef, flags);
    }

    public void connect(String detailedName, GCallbackFunction fn, GConnectFlags... flags) {
        connect(detailedName, fn, null, flags);
    }

    public void connect(String detailedName, GCallbackFunction fn) {
        connect(detailedName, fn, Pointer.NULL, GConnectFlags.G_CONNECT_DEFAULT);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean emitSignal(String detailedName) {
        PointerByReference pbf = new PointerByReference();
        library.g_signal_emit_by_name(cReference, detailedName, pbf);
        return pbf.getPointer().getInt(0) == 1;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean emitSignal(GtkWidget.Signals detailedName) {
        PointerByReference pbf = new PointerByReference();
        library.g_signal_emit_by_name(cReference, detailedName.getDetailedName(), pbf);
        return pbf.getPointer().getInt(0) == 1;
    }

    @SuppressWarnings("CommentedOutCode")
    public boolean emitSignal(GtkWidget.Signals detailedName, Pointer... params) {
        PointerByReference pbf = new PointerByReference();
        Pointer[] arr = new Pointer[params.length + 1];
        System.arraycopy(params, 0, arr, 0, params.length);
        //System.out.println(arr.length + " " + params.length);
        // arr[params.length] = pbf.getPointer();
        //  for (Pointer r : arr) {
        //  System.out.println("\t" + r);
        //  }
        library.g_signal_emit_by_name(cReference, detailedName.getDetailedName(), arr);
        return pbf.getPointer().getInt(0) == 1;
    }

    @Override
    public String toString() {
        String gtkName = library.g_type_name_from_instance(cReference);
        if (gtkName != null && !this.getClass().getName().contains(gtkName)) {
            return gtkName + "(" + cReference + ")";
        }
        return this.getClass().getName() + "(" + cReference + ")";
    }

}
