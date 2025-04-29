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
package com.gitlab.ccook.jgtk.interfaces;

import com.gitlab.ccook.jgtk.GtkAdjustment;
import com.gitlab.ccook.jgtk.GtkBorder;
import com.gitlab.ccook.jgtk.enums.GtkScrollablePolicy;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


@SuppressWarnings("unchecked")
public interface GtkScrollable extends GtkInterface {
    default Option<GtkBorder> getBorder() {
        PointerByReference borderPointer = new PointerByReference();
        boolean beenSet = library.gtk_scrollable_get_border(getCReference(), borderPointer);
        if (beenSet) {
            return new Option<>(new GtkBorder(borderPointer.getPointer()));
        }
        return Option.NONE;
    }

    /**
     * @return the GtkAdjustment used for horizontal scrolling.
     */
    default Option<GtkAdjustment> getHorizontalAdjustment() {
        Option<Pointer> p = new Option<>(library.gtk_scrollable_get_hadjustment(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkAdjustment(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the horizontal adjustment of the GtkScrollable.
     *
     * @param a A GtkAdjustment
     *          <p>
     *          The argument can be NULL.
     */
    default void setHorizontalAdjustment(GtkAdjustment a) {
        if (a != null) {
            library.gtk_scrollable_set_hadjustment(getCReference(), a.getCReference());
        } else {
            library.gtk_scrollable_set_hadjustment(getCReference(), null);
        }
    }

    /**
     * @return the horizontal GtkScrollablePolicy.
     */
    default GtkScrollablePolicy getHorizontalScrollPolicy() {
        return GtkScrollablePolicy.getPolicyFromCValue(library.gtk_scrollable_get_hscroll_policy(getCReference()));
    }

    /**
     * Sets the GtkScrollablePolicy.
     * <p>
     * The policy determines whether horizontal scrolling should start below the minimum width or below the natural
     * width.
     *
     * @param p The horizontal GtkScrollablePolicy
     */
    default void setHorizontalScrollPolicy(GtkScrollablePolicy p) {
        if (p != null) {
            library.gtk_scrollable_set_hscroll_policy(getCReference(), p.getCValue());
        }
    }

    /**
     * @return the GtkAdjustment used for vertical scrolling.
     */
    default Option<GtkAdjustment> getVerticalAdjustment() {
        Option<Pointer> p = new Option<>(library.gtk_scrollable_get_vadjustment(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkAdjustment(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the vertical adjustment of the GtkScrollable.
     *
     * @param a A GtkAdjustment
     *          <p>
     *          The argument can be NULL.
     */
    default void setVerticalAdjustment(GtkAdjustment a) {
        if (a != null) {
            library.gtk_scrollable_set_vadjustment(getCReference(), a.getCReference());
        } else {
            library.gtk_scrollable_set_vadjustment(getCReference(), null);
        }
    }

    /**
     * @return the vertical GtkScrollablePolicy.
     */
    default GtkScrollablePolicy getVerticalScrollPolicy() {
        return GtkScrollablePolicy.getPolicyFromCValue(library.gtk_scrollable_get_vscroll_policy(getCReference()));
    }

    /**
     * Sets the GtkScrollablePolicy.
     * <p>
     * The policy determines whether vertical scrolling should start below the minimum width or below the natural width.
     *
     * @param p The vertical GtkScrollablePolicy
     */
    default void setVerticalScrollPolicy(GtkScrollablePolicy p) {
        if (p != null) {
            library.gtk_scrollable_set_vscroll_policy(getCReference(), p.getCValue());
        }
    }
}
