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
package com.gitlab.ccook.jgtk.gtk;

import com.gitlab.ccook.jgtk.GSimplePermission;
import com.gitlab.ccook.jgtk.GValue;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkActionable;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


@SuppressWarnings({"ALL"})
@Deprecated
public class GtkLockButton extends GtkButton implements GtkAccessible, GtkActionable, GtkBuildable {

    private static final GtkLockButtonLibrary library = new GtkLockButtonLibrary();

    public GtkLockButton() {
        super(library.gtk_lock_button_new(Pointer.NULL));
    }

    /**
     * Creates a new lock button which reflects the permission.
     *
     * @param permission A GPermission
     *                   <p>
     *                   The argument can be NULL.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public GtkLockButton(GSimplePermission permission) {
        super(library.gtk_lock_button_new(pointerOrNull(permission)));
    }

    public GtkLockButton(Pointer ref) {
        super(ref);
    }

    /**
     * The text displayed when prompting the user to lock.
     *
     * @return The text displayed when prompting the user to lock.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public String getLockText() {
        return getProperty("text-lock").get().getText();
    }

    /**
     * The text to display when prompting the user to lock.
     *
     * @param lockText The text to display when prompting the user to lock.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setLockText(String lockText) {
        String propertyName = "text-lock";
        Option<GValue> gval = getProperty(propertyName);
        if (gval.isDefined()) {
            setProperty(propertyName, cReference, lockText);
        }
    }

    /**
     * Obtains the GPermission object that controls button.
     *
     * @return The GPermission of button, if defined
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<GSimplePermission> getPermission() {
        Option<Pointer> p = new Option<>(library.gtk_lock_button_get_permission(cReference));
        if (p.isDefined()) {
            return new Option<>(new GSimplePermission(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the GPermission object that controls button.
     *
     * @param permission A GSimplePermission object. (May be null)
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setPermission(GSimplePermission permission) {
        library.gtk_lock_button_set_permission(cReference, pointerOrNull(permission));
    }

    /**
     * The tooltip to display when prompting the user to lock.
     *
     * @return The tooltip displayed when prompting the user to lock.
     */
    public String getPromptToLockToolTip() {
        return getProperty("tooltip-lock").get().getText();
    }

    /**
     * The tooltip to display when prompting the user to lock.
     *
     * @param lockToolTip The tooltip to display when prompting the user to lock.
     */
    public void setPromptToLockToolTip(String lockToolTip) {
        String propertyName = "tooltip-lock";
        Option<GValue> gval = getProperty(propertyName);
        if (gval.isDefined()) {
            setProperty(propertyName, cReference, lockToolTip);
        }
    }

    /**
     * The text displayed when prompting the user to unlock.
     *
     * @return The text displayed when prompting the user to unlock.
     */
    public String getPromptToUnlockToolTip() {
        return getProperty("tooltip-unlock").get().getText();
    }

    /**
     * The text to display when prompting the user to unlock.
     *
     * @param unlockToolTip The text to display when prompting the user to unlock.
     */
    public void setPromptToUnlockToolTip(String unlockToolTip) {
        String propertyName = "tooltip-unlock";
        Option<GValue> gval = getProperty(propertyName);
        if (gval.isDefined()) {
            setProperty(propertyName, cReference, unlockToolTip);
        }
    }

    /**
     * Gets the tooltip to display when the user cannot obtain authorization.
     *
     * @return The tooltip to display when the user cannot obtain authorization.
     */
    public String getUnauthorizedToolTip() {
        return getProperty("tooltip-not-authorized").get().getText();
    }

    /**
     * Sets the tooltip to display when the user cannot obtain authorization.
     *
     * @param unauthToolTip The tooltip to display when the user cannot obtain authorization.
     */
    public void setUnauthorizedToolTip(String unauthToolTip) {
        String propertyName = "tooltip-not-authorized";
        Option<GValue> gval = getProperty(propertyName);
        if (gval.isDefined()) {
            setProperty(propertyName, cReference, unauthToolTip);
        }
    }

    /**
     * The text displayed when prompting the user to unlock.
     *
     * @return The text displayed when prompting the user to unlock.
     */
    public String getUnlockText() {
        return getProperty("text-unlock").get().getText();
    }

    /**
     * The text to display when prompting the user to unlock.
     *
     * @param unlockTest The text to display when prompting the user to unlock.
     */
    public void setUnlockTest(String unlockTest) {
        String propertyName = "text-unlock";
        Option<GValue> gval = getProperty(propertyName);
        if (gval.isDefined()) {
            setProperty(propertyName, cReference, unlockTest);
        }
    }

    @Deprecated
    protected static class GtkLockButtonLibrary extends GtkButtonLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Obtains the GPermission object that controls button.
         *
         * @param button self
         * @return The GPermission of button.
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_lock_button_get_permission(Pointer button);

        /**
         * @param permission A GPermission
         *                   <p>
         *                   The argument can be NULL.
         * @return A new GtkLockButton. Type: GtkLockButton
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_lock_button_new(Pointer permission);

        /**
         * Sets the GPermission object that controls button.
         *
         * @param button     self
         * @param permission A GPermission object.
         *                   <p>
         *                   The argument can be NULL
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_lock_button_set_permission(Pointer button, Pointer permission);
    }


}
