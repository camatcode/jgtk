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

import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkNative;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * GtkPopover is a bubble-like context popup.
 * It is primarily meant to provide context-dependent information or options. Popovers are attached to a parent widget.
 * By default, they point to the whole widget area, although this behavior can be changed with
 * gtk_popover_set_pointing_to().
 * <p>
 * The position of a popover relative to the widget it is attached to can also be changed with
 * gtk_popover_set_position()
 * <p>
 * By default, GtkPopover performs a grab, in order to ensure input events get redirected to it while it is shown, and
 * also so the popover is dismissed in the expected situations (clicks outside the popover, or the Escape key being
 * pressed). If no such modal behavior is desired on a popover, gtk_popover_set_autohide() may be called on it to tweak
 * its behavior.
 */
@SuppressWarnings("unchecked")
public class GtkPopover extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkShortcutManager {

    private static final GtkPopoverLibrary library = new GtkPopoverLibrary();

    public GtkPopover(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkPopover.
     */
    public GtkPopover() {
        super(library.gtk_popover_new());
    }

    /**
     * Gets whether mnemonics are visible.
     *
     * @return TRUE if mnemonics are supposed to be visible in this popover.
     */
    public boolean areMnemonicsVisible() {
        return library.gtk_popover_get_mnemonics_visible(getCReference());
    }

    /**
     * Connect a signal
     *
     * @param s       Detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to the signal
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef) {
        connect(s.getDetailedName(), fn, dataRef, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s     detailed name of signal
     * @param fn    function to invoke on signal
     * @param flags connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, GConnectFlags... flags) {
        connect(s.getDetailedName(), fn, null, flags);
    }

    /**
     * Connect a signal
     *
     * @param s  detailed name of signal
     * @param fn function to invoke on signal
     */
    public void connect(Signals s, GCallbackFunction fn) {
        connect(s.getDetailedName(), fn, Pointer.NULL, GConnectFlags.G_CONNECT_DEFAULT);
    }

    /**
     * Connect a signal
     *
     * @param s       detailed name of signal
     * @param fn      function to invoke on signal
     * @param dataRef data to pass to signal
     * @param flags   connection flags
     */
    public void connect(Signals s, GCallbackFunction fn, Pointer dataRef, GConnectFlags... flags) {
        connect(new GtkCallbackFunction() {
            @Override
            public GConnectFlags[] getConnectFlag() {
                return flags;
            }

            @Override
            public Pointer getDataReference() {
                return dataRef;
            }

            @Override
            public String getDetailedSignal() {
                return s.getDetailedName();
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                fn.invoke(relevantThing, relevantData);
            }
        });
    }

    /**
     * Returns whether the popover will close after a modal child is closed.
     *
     * @return TRUE if popover will close after a modal child.
     */
    public boolean doesDismissWithChild() {
        return library.gtk_popover_get_cascade_popdown(getCReference());
    }

    /**
     * Gets whether this popover is showing an arrow pointing at the widget that it is relative to.
     *
     * @return Whether the popover has an arrow.
     */
    public boolean doesShowArrow() {
        return library.gtk_popover_get_has_arrow(getCReference());
    }

    /**
     * Gets the child widget of popover.
     *
     * @return The child widget of popover, if defined
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> p = new Option<>(library.gtk_popover_get_child(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of popover.
     *
     * @param c The child widget.
     *          <p>
     *          The argument can be NULL.
     */
    public void setChild(GtkWidget c) {
        library.gtk_popover_set_child(getCReference(), pointerOrNull(c));
    }

    /**
     * Gets the default widget of a GtkPopover.
     * <p>
     * The default widget is the widget that's activated when the user presses 'Enter' in a dialog (for example).
     *
     * @return the default widget of the popover, if defined
     */
    public Option<GtkWidget> getDefaultWidget() {
        Option<GValue> property = getProperty("default-widget");
        if (property.isDefined() && property.get().getObject() != Pointer.NULL) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(property.get().getObject(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the default widget of a GtkPopover.
     * <p>
     * The default widget is the widget that's activated when the user presses 'Enter' in a dialog (for example).
     * This function sets or unsets the default widget for a GtkPopover.
     *
     * @param w child widget of popover to set as the default, or NULL to unset the default widget for the popover.
     *          <p>
     *          The argument can be NULL.
     */
    public void setDefaultWidget(GtkWidget w) {
        library.gtk_popover_set_default_widget(getCReference(), pointerOrNull(w));
    }

    /**
     * Gets the offset previous set with gtk_popover_set_offset().
     *
     * @return If defined, first - x_offset, second - y_offset
     */
    public Option<Pair<Integer, Integer>> getOffsetPosition() {
        PointerByReference xOffset = new PointerByReference();
        PointerByReference yOffset = new PointerByReference();
        library.gtk_popover_get_offset(getCReference(), xOffset, yOffset);
        Pointer x = xOffset.getPointer();
        Pointer y = yOffset.getPointer();
        if (x != null && y != null) {
            return new Option<>(new Pair<>(x.getInt(0), y.getInt(0)));
        }
        return Option.NONE;
    }

    /**
     * Gets the rectangle that the popover points to.
     *
     * @return Rectangle in the parent widget that the popover points to.
     */
    public Option<GdkRectangle> getPointingTo() {
        GdkRectangle.GdkRectangleStruct.ByReference r = new GdkRectangle.GdkRectangleStruct.ByReference();
        boolean didFill = library.gtk_popover_get_pointing_to(getCReference(), r);
        if (didFill) {
            return new Option<>(new GdkRectangle(r));
        }
        return Option.NONE;
    }

    /**
     * Sets the rectangle that popover points to.
     * <p>
     * This is in the coordinate space of the popover parent.
     *
     * @param rectangle Rectangle to point to.
     *                  <p>
     *                  The argument can be NULL.
     */
    public void setPointingTo(GdkRectangle rectangle) {
        library.gtk_popover_set_pointing_to(getCReference(), pointerOrNull(rectangle));
    }

    /**
     * Returns the preferred position of popover.
     *
     * @return The preferred position.
     */
    public GtkPositionType getPreferredPosition() {
        return GtkPositionType.getTypeFromCValue(library.gtk_popover_get_position(getCReference()));
    }

    /**
     * Sets the preferred position for popover to appear.
     * <p>
     * If the popover is currently visible, it will be immediately updated.
     * <p>
     * This preference will be respected where possible, although on lack of space (e.g. if close to the window edges),
     * the GtkPopover may choose to appear on the opposite side.
     *
     * @param t Preferred popover position.
     */
    public void setPreferredPosition(GtkPositionType t) {
        if (t != null) {
            library.gtk_popover_set_position(getCReference(), GtkPositionType.getCValueFromType(t));
        }
    }

    /**
     * Returns whether the popover is modal.
     * <p>
     * See gtk_popover_set_autohide() for the implications of this.
     *
     * @return TRUE if popover is modal and outside clicks will dismiss the popover
     */
    public boolean isModal() {
        return library.gtk_popover_get_autohide(getCReference());
    }

    /**
     * Sets whether popover is modal.
     * <p>
     * A modal popover will grab the keyboard focus on it when being displayed. Focus will wrap around within the
     * popover. Clicking outside the popover area or pressing Esc will dismiss the popover.
     * <p>
     * Called this function on an already showing popup with a new auto-hide value different from the current one, will
     * cause the popup to be hidden.
     *
     * @param isModal TRUE to dismiss the popover on outside clicks.
     */
    public void setModal(boolean isModal) {
        library.gtk_popover_set_autohide(getCReference(), isModal);
    }

    /**
     * Pops popover down.
     * <p>
     * This may have the side effect of closing a parent popover as well. See GtkPopover:cascade-popdown.
     */
    public void popDown() {
        library.gtk_popover_popdown(getCReference());
    }

    /**
     * Pops popover up.
     */
    public void popUp() {
        library.gtk_popover_popup(getCReference());
    }

    /**
     * Presents the popover to the user.
     */
    public void present() {
        library.gtk_popover_present(getCReference());
    }

    /**
     * Sets whether mnemonics should be visible.
     *
     * @param areVis TRUE if mnemonics are visible
     */
    public void setMnemonicsVisible(boolean areVis) {
        library.gtk_popover_set_mnemonics_visible(getCReference(), areVis);
    }

    /**
     * Sets the offset to use when calculating the position of the popover.
     * <p>
     * These values are used when preparing the GdkPopupLayout for positioning the popover.
     *
     * @param xOffset The x offset to adjust the position by.
     * @param yOffset The y offset to adjust the position by.
     */
    public void setOffset(int xOffset, int yOffset) {
        library.gtk_popover_set_offset(getCReference(), xOffset, yOffset);
    }

    /**
     * If cascade_popdown is TRUE, the popover will be closed when a child modal popover is closed.
     * <p>
     * If FALSE, popover will stay visible.
     *
     * @param canDismiss TRUE if the popover should follow a child closing.
     */
    public void shouldDismissWithChild(boolean canDismiss) {
        library.gtk_popover_set_cascade_popdown(getCReference(), canDismiss);
    }

    /**
     * Sets whether this popover should draw an arrow pointing at the widget it is relative to.
     *
     * @param canShow TRUE to draw an arrow.
     */
    public void shouldShowArrow(boolean canShow) {
        library.gtk_popover_set_has_arrow(getCReference(), canShow);
    }

    public static class Signals extends GtkWidget.Signals {
        public static final Signals ACTIVATE_DEFAULT = new Signals("activate-default");
        public static final Signals CLOSED = new Signals("closed");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkPopoverLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Returns whether the popover is modal.
         * <p>
         * See gtk_popover_set_autohide() for the implications of this.
         *
         * @param popover self
         * @return TRUE if popover is modal.
         */
        public native boolean gtk_popover_get_autohide(Pointer popover);

        /**
         * Returns whether the popover will close after a modal child is closed.
         *
         * @param popover self
         * @return TRUE if popover will close after a modal child.
         */
        public native boolean gtk_popover_get_cascade_popdown(Pointer popover);

        /**
         * Gets the child widget of popover.
         *
         * @param popover self
         * @return The child widget of popover.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_popover_get_child(Pointer popover);

        /**
         * Gets whether this popover is showing an arrow pointing at the widget that it is relative to.
         *
         * @param popover self
         * @return Whether the popover has an arrow.
         */
        public native boolean gtk_popover_get_has_arrow(Pointer popover);

        /**
         * Gets whether mnemonics are visible.
         *
         * @param popover self
         * @return TRUE if mnemonics are supposed to be visible in this popover.
         */
        public native boolean gtk_popover_get_mnemonics_visible(Pointer popover);

        /**
         * Gets the offset previous set with gtk_popover_set_offset()
         *
         * @param popover  self
         * @param x_offset A location for the x_offset.
         *                 <p>
         *                 The argument can be NULL.
         * @param y_offset A location for the y_offset.
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_popover_get_offset(Pointer popover, PointerByReference x_offset, PointerByReference y_offset);

        /**
         * Gets the rectangle that the popover points to.
         * <p>
         * If a point-to rectangle has been set, this function will return TRUE and fill in rect with such rectangle,
         * otherwise it will return FALSE and fill in rect with the parent widget coordinates.
         *
         * @param popover self
         * @param rect    Location to store the rectangle.
         * @return TRUE if a rectangle to point to was set.
         */
        public native boolean gtk_popover_get_pointing_to(Pointer popover, GdkRectangle.GdkRectangleStruct rect);

        /**
         * Returns the preferred position of popover.
         *
         * @param popover self
         * @return The preferred position. Type: GtkPositionType
         */
        public native int gtk_popover_get_position(Pointer popover);

        /**
         * Creates a new GtkPopover.
         *
         * @return The new GtkPopover. Type: GtkPopover
         */
        public native Pointer gtk_popover_new();

        /**
         * Pops popover down.
         * <p>
         * This may have the side effect of closing a parent popover as well. See GtkPopover:cascade-popdown.
         *
         * @param popover self
         */
        public native void gtk_popover_popdown(Pointer popover);

        /**
         * Pops popover up.
         *
         * @param popover self
         */
        public native void gtk_popover_popup(Pointer popover);

        /**
         * Allocate a size for the GtkPopover.
         * <p>
         * This function needs to be called in size-allocate by widgets who have a GtkPopover as child. When using a
         * layout manager, this is happening automatically.
         * <p>
         * To make a popover appear on screen, use gtk_popover_popup().
         *
         * @param popover self
         */
        public native void gtk_popover_present(Pointer popover);

        /**
         * Sets whether popover is modal.
         * <p>
         * A modal popover will grab the keyboard focus on it when being displayed. Focus will wrap around within the
         * popover. Clicking outside the popover area or pressing Esc will dismiss the popover.
         * <p>
         * Called this function on an already showing popup with a new autohide value different from the current one,
         * will cause the popup to be hidden.
         *
         * @param popover  self
         * @param autohide TRUE to dismiss the popover on outside clicks.
         */
        public native void gtk_popover_set_autohide(Pointer popover, boolean autohide);

        /**
         * If cascade_popdown is TRUE, the popover will be closed when a child modal popover is closed.
         * <p>
         * If FALSE, popover will stay visible.
         *
         * @param popover         self
         * @param cascade_popdown TRUE if the popover should follow a child closing.
         */
        public native void gtk_popover_set_cascade_popdown(Pointer popover, boolean cascade_popdown);

        /**
         * Sets the child widget of popover.
         *
         * @param popover self
         * @param child   The child widget. Type: GtkWidget
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_popover_set_child(Pointer popover, Pointer child);

        /**
         * Sets the default widget of a GtkPopover.
         * <p>
         * The default widget is the widget that's activated when the user presses 'Enter' in a dialog (for example).
         * This function sets or unsets the default widget for a GtkPopover.
         *
         * @param popover self
         * @param widget  A child widget of popover to set as the default, or NULL to unset the default widget for
         *                the popover.
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_popover_set_default_widget(Pointer popover, Pointer widget);

        /**
         * Sets whether this popover should draw an arrow pointing at the widget it is relative to.
         *
         * @param popover   self
         * @param has_arrow TRUE to draw an arrow.
         */
        public native void gtk_popover_set_has_arrow(Pointer popover, boolean has_arrow);

        /**
         * Sets whether mnemonics should be visible.
         *
         * @param popover           self
         * @param mnemonics_visible whether mnemonics should be visible.
         */
        public native void gtk_popover_set_mnemonics_visible(Pointer popover, boolean mnemonics_visible);

        /**
         * Sets the offset to use when calculating the position of the popover.
         * <p>
         * These values are used when preparing the GdkPopupLayout for positioning the popover.
         *
         * @param popover  self
         * @param x_offset The x offset to adjust the position by.
         * @param y_offset The y offset to adjust the position by.
         */
        public native void gtk_popover_set_offset(Pointer popover, int x_offset, int y_offset);

        /**
         * Sets the rectangle that popover points to.
         * <p>
         * This is in the coordinate space of the popover parent.
         *
         * @param popover self
         * @param rect    Rectangle to point to. Type: GdkRectangle
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_popover_set_pointing_to(Pointer popover, Pointer rect);

        /**
         * Sets the preferred position for popover to appear.
         * <p>
         * If the popover is currently visible, it will be immediately updated.
         * <p>
         * This preference will be respected where possible, although on lack of space (e.g. if close to the window
         * edges), the GtkPopover may choose to appear on the opposite side.
         *
         * @param popover  self
         * @param position Preferred popover position. Type: GtkPositionType
         */
        public native void gtk_popover_set_position(Pointer popover, int position);

    }
}
