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
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkNative;
import com.gitlab.ccook.jgtk.interfaces.GtkRoot;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import org.joda.time.DateTime;

import java.util.Set;

/**
 * A GtkWindow is a toplevel window which can contain other widgets.
 * <p>
 * Windows normally have decorations that are under the control of the windowing system and allow the user to
 * manipulate the window (resize it, move it, close it,…).
 */
@SuppressWarnings({"unchecked", "unused", "GrazieInspection"})
public class GtkWindow extends GtkWidget implements GtkAccessible, GtkBuildable, GtkNative, GtkRoot {

    private static final GtkWindowLibrary library = new GtkWindowLibrary();

    public GtkWindow() {
        super(library.gtk_window_new());
    }

    public GtkWindow(Pointer ref) {
        super(ref);
    }

    /**
     * Returns the fallback icon name for windows.
     * <p>
     * The returned string is owned by GTK and should not be modified.
     * It is only valid until the next call to gtk_window_set_default_icon_name().
     *
     * @return The fallback icon name for windows. (This may be NONE)
     */
    public static Option<IconName> getDefaultIconName() {
        String iconName = library.gtk_window_get_default_icon_name();
        if (iconName != null) {
            return new Option<>(new IconName(iconName));
        }
        return Option.NONE;
    }

    /**
     * Sets an icon to be used as fallback.
     * <p>
     * The fallback icon is used for windows that haven't had gtk_window_set_icon_name() called on them.
     *
     * @param iconName The name of the themed icon.
     */
    public static void setDefaultIconName(IconName iconName) {
        if (iconName != null && iconName.getIconName() != null) {
            library.gtk_window_set_default_icon_name(iconName.getIconName());
        }
    }

    /**
     * Returns a list of all existing toplevel windows.
     * <p>
     * If you want to iterate through the list and perform actions involving callbacks that might destroy the widgets or
     * add new ones, be aware that the list of toplevels will change and emit the "items-changed" signal.
     *
     * @return set of top level windows
     */
    public static Set<GtkWindow> getTopLevelWindows() {
        Pointer p = library.gtk_window_get_toplevels();
        return new GListModelSet<>(new GenericGListModel<>(GtkWindow.class, p));
    }

    /**
     * Sets whether the window should request startup notification.
     * <p>
     * By default, after showing the first GtkWindow, GTK calls gdk_display_notify_startup_complete().
     * Call this function to disable the automatic startup notification. You might do this if your first window is a
     * splash screen, and you want to delay notification until after your real main window has been shown, for example.
     * <p>
     * In that example, you would disable startup notification temporarily, show your splash screen, then re-enable it
     * so that showing the main window would automatically result in notification.
     *
     * @param shouldWindowsRequestStartupNotif TRUE to automatically do startup notification.
     */
    public static void setAutoStartupNotification(boolean shouldWindowsRequestStartupNotif) {
        library.gtk_window_set_auto_startup_notification(shouldWindowsRequestStartupNotif);
    }

    /**
     * Opens or closes the interactive debugger.
     * <p>
     * The debugger offers access to the widget hierarchy of the application and to useful debugging tools.
     *
     * @param shouldEnable TRUE to enable interactive debugging.
     */
    public static void setInteractiveDebugger(boolean shouldEnable) {
        library.gtk_window_set_interactive_debugging(shouldEnable);
    }

    /**
     * Gets whether "focus rectangles" are supposed to be visible.
     *
     * @return TRUE if "focus rectangles" are supposed to be visible in this window.
     */
    public boolean areFocusRectanglesVisible() {
        return library.gtk_window_get_focus_visible(cReference);
    }

    /**
     * Gets whether mnemonics are supposed to be visible.
     *
     * @return TRUE if mnemonics are supposed to be visible in this window.
     */
    public boolean areMnemonicsVisible() {
        return library.gtk_window_get_mnemonics_visible(cReference);
    }

    /**
     * Requests that the window is closed.
     * <p>
     * This is similar to what happens when a window manager close button is clicked.
     * <p>
     * This function can be used with close buttons in custom titlebars.
     */
    public void close() {
        library.gtk_window_close(cReference);
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
     * Drop the internal reference GTK holds on toplevel windows
     */
    public void destroy() {
        library.gtk_window_destroy(this.cReference);
    }

    /**
     * Returns whether the window will be destroyed with its transient parent.
     *
     * @return TRUE if the window will be destroyed with its transient parent.
     */
    public boolean doesDestroyWithParent() {
        return library.gtk_window_get_destroy_with_parent(getCReference());
    }

    /**
     * Returns whether this window reacts to F10 key presses by activating a menubar it contains.
     * <p>
     * Available since: 4.2
     *
     * @return TRUE if the window handles F10
     */
    public boolean doesF10ActivateMenuBar() {
        return library.gtk_window_get_handle_menubar_accel(cReference);
    }

    /**
     * Sets the focus widget.
     * <p>
     * If focus is not the current focus widget, and is focusable, sets it as the focus widget for the window.
     * If focus is NULL, unsets the focus widget for this window.
     * To set the focus to a particular widget in the toplevel, it is usually more convenient to
     * use gtk_widget_grab_focus() instead of this function.
     *
     * @param widgetToFocus Widget to be the new focus widget, or NULL to unset any focus widget for the toplevel
     *                      window.
     */
    public void focus(GtkWidget widgetToFocus) {
        library.gtk_window_set_focus(getCReference(), pointerOrNull(widgetToFocus));
    }

    /**
     * Asks to place window in the fullscreen state.
     * <p>
     * Note that you shouldn't assume the window is definitely fullscreen afterward, because other entities
     * (e.g. the user or window manager unfullscreen it again, and not all window managers honor requests
     * to fullscreen windows.)
     * <p>
     * You can track the result of this operation via the GdkToplevel:state property, or by listening to notifications
     * of the GtkWindow:fullscreened property.
     */
    public void fullscreen() {
        library.gtk_window_fullscreen(cReference);
    }

    /**
     * Asks to place window in the fullscreen state on the given monitor.
     * <p>
     * Note that you shouldn't assume the window is definitely fullscreen afterward, or that the windowing system allows
     * fullscreen windows on any given monitor.
     * <p>
     * You can track the result of this operation via the GdkToplevel:state property, or by listening to notifications
     * of the GtkWindow:fullscreened property.
     *
     * @param monitor Which monitor to go fullscreen on.
     */
    public void fullscreenOnMonitor(GdkMonitor monitor) {
        if (monitor != null) {
            library.gtk_window_fullscreen_on_monitor(cReference, monitor.getCReference());
        }
    }

    /**
     * Gets the GtkApplication associated with the window.
     *
     * @return A GtkApplication (may be NONE)
     */
    public Option<GtkApplication> getAssociatedApplication() {
        Option<Pointer> pointer = new Option<>(library.gtk_window_get_application(cReference));
        if (pointer.isDefined()) {
            return new Option<>(new GtkApplication(pointer.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets or unsets the GtkApplication associated with the window.
     * <p>
     * The application will be kept alive for at least as long as it has any windows associated with it
     * (see g_application_hold() for a way to keep it alive without windows).
     * <p>
     * Normally, the connection between the application and the window will remain until the window is destroyed,
     * but you can explicitly remove it by setting the application to NULL.
     * <p>
     * This is equivalent to calling gtk_application_remove_window() and/or gtk_application_add_window() on the
     * old/new applications as relevant.
     *
     * @param app A GtkApplication, or NULL to unset.
     */
    public void setAssociatedApplication(GtkApplication app) {
        library.gtk_window_set_application(cReference, pointerOrNull(app));
    }

    /**
     * Gets the child widget of window.
     *
     * @return The child widget of window. (may be NONE)
     */
    public Option<GtkWidget> getChild() {
        Option<Pointer> pointer = new Option<>(library.gtk_window_get_child(cReference));
        if (pointer.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(pointer.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the child widget of window.
     *
     * @param child The child widget. (may be null)
     */
    public void setChild(GtkWidget child) {
        library.gtk_window_set_child(cReference, pointerOrNull(child));
    }

    /**
     * Gets the default size of the window.
     * <p>
     * A value of 0 for the width or height indicates that a default size has not been explicitly set for that
     * dimension, so the "natural" size of the window will be used.
     *
     * @return Default Width, Height
     */
    public Pair<Integer, Integer> getDefaultSize() {
        PointerByReference width = new PointerByReference();
        PointerByReference height = new PointerByReference();
        library.gtk_window_get_default_size(cReference, width, height);
        int w = width.getPointer().getInt(0);
        int h = height.getPointer().getInt(0);
        return new Pair<>(w, h);
    }

    /**
     * Returns the default widget for window.
     *
     * @return The default widget. (May be NONE)
     */
    public Option<GtkWidget> getDefaultWidget() {
        Option<Pointer> pointer = new Option<>(library.gtk_window_get_default_widget(cReference));
        if (pointer.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(pointer.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the default widget.
     * <p>
     * The default widget is the widget that is activated when the user presses Enter in a dialog (for example).
     *
     * @param defaultWidget Widget to be the default to unset the default widget for the toplevel. (may be null)
     */
    public void setDefaultWidget(GtkWidget defaultWidget) {
        library.gtk_window_set_default_widget(cReference, pointerOrNull(defaultWidget));
    }

    /**
     * Retrieves the current focused widget within the window.
     * <p>
     * Note that this is the widget that would have the focus if the toplevel window focused; if the toplevel window is
     * not focused then gtk_widget_has_focus (widget) will not be TRUE for the widget.
     *
     * @return The currently focused widget. (May be NONE)
     */
    public Option<GtkWidget> getFocusedWidget() {
        Option<Pointer> pointer = new Option<>(library.gtk_window_get_focus(cReference));
        if (pointer.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(pointer.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Returns the name of the themed icon for the window.
     *
     * @return The icon name. (may be NONE)
     */
    public Option<IconName> getIconName() {
        Option<String> p = new Option<>(library.gtk_window_get_icon_name(cReference));
        if (p.isDefined()) {
            return new Option<>(new IconName(p.get()));
        }
        return Option.NONE;
    }

    /**
     * n for the window from a named themed icon.
     * <p>
     * See the docs for GtkIconTheme for more details. On some platforms, the window icon is not used at all.
     * <p>
     * Note that this has nothing to do with the WM_ICON_NAME property which is mentioned in the ICCCM.
     *
     * @param name The name of the themed icon. (may be null)
     */
    public void setIconName(IconName name) {
        String nameString = name != null ? name.getIconName() : null;
        library.gtk_window_set_icon_name(cReference, nameString);
    }

    /**
     * Retrieves the title of the window.
     *
     * @return The title of the window. (May be NONE)
     */
    public Option<String> getTitle() {
        return new Option<>(library.gtk_window_get_title(cReference));
    }

    /**
     * Sets the title of the GtkWindow.
     * <p>
     * The title of a window will be displayed in its title bar; on the X Window System, the title bar is rendered
     * by the window manager so exactly how the title appears to users may vary according to a user's exact
     * configuration. The title should help a user distinguish this window from other windows they may have open.
     * A good title might include the application name and current document filename, for example.
     * <p>
     * Passing NULL does the same as setting the title to an empty string.
     *
     * @param title Title of the window
     */
    public void setTitle(String title) {
        library.gtk_window_set_title(cReference, title);
    }

    /**
     * Returns the custom titlebar that has been set with gtk_window_set_titlebar().
     *
     * @return The custom titlebar. (May be NONE)
     */
    public Option<GtkWidget> getTitleBar() {
        Option<Pointer> pointer = new Option<>(library.gtk_window_get_titlebar(cReference));
        if (pointer.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(pointer.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Sets a custom titlebar for window.
     * <p>
     * A typical widget used here is GtkHeaderBar, as it provides various features expected of a titlebar while
     * allowing the addition of child widgets to it.
     * <p>
     * If you set a custom titlebar, GTK will do its best to convince the window manager not to put its own
     * titlebar on the window. Depending on the system, this function may not work for a window that is already visible,
     * so you set the titlebar before calling gtk_widget_show().
     *
     * @param titleBar The widget to use as titlebar. (may be null)
     */
    public void setTitleBar(GtkWidget titleBar) {
        library.gtk_window_set_titlebar(cReference, pointerOrNull(titleBar));
    }

    /**
     * Fetches the transient parent for this window.
     *
     * @return The transient parent for this window. (May be NONE)
     */
    public Option<GtkWindow> getTransientFor() {
        Option<Pointer> p = new Option<>(library.gtk_window_get_transient_for(cReference));
        if (p.isDefined()) {
            return new Option<>(new GtkWindow(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Dialog windows should be set transient for the main application window they were spawned from.
     * This allows window managers to e.g. keep the dialog on top of the main window, or center the dialog over the
     * main window. gtk_dialog_new_with_buttons() and other convenience functions in GTK will sometimes call
     * gtk_window_set_transient_for() on your behalf.
     * <p>
     * Passing NULL for parent unsets the current transient window.
     * <p>
     * On Windows, this function puts the child window on top of the parent, much as the window manager would
     * have done on X.
     *
     * @param transientForWindow Parent window (may be null)
     */
    public void setTransientFor(GtkWindow transientForWindow) {
        library.gtk_window_set_transient_for(cReference, pointerOrNull(transientForWindow));
    }

    /**
     * Returns the group for window.
     * <p>
     * If the window has no group, then the default group is returned.
     *
     * @return The GtkWindowGroup for a window or the default group
     */
    public GtkWindowGroup getWindowGroup() {
        return new GtkWindowGroup(library.gtk_window_get_group(cReference));
    }

    /**
     * Returns whether window has an explicit window group.
     *
     * @return TRUE if window has an explicit window group.
     */
    public boolean hasWindowGroup() {
        return library.gtk_window_has_group(cReference);
    }

    /**
     * Returns whether the window will be hidden when the close button is clicked.
     *
     * @return TRUE if the window will be hidden.
     */
    public boolean hidesOnClose() {
        return library.gtk_window_get_hide_on_close(cReference);
    }

    /**
     * Returns whether the window is part of the current active toplevel.
     * <p>
     * The active toplevel is the window receiving keystrokes.
     * <p>
     * The return value is TRUE if the window is active toplevel itself. You might use this function if you wanted to
     * draw a widget differently in an active window from a widget in an inactive window.
     *
     * @return TRUE if the window part of the current active window.
     */
    public boolean isActive() {
        return library.gtk_window_is_active(cReference);
    }

    /**
     * Returns whether the window has been set to have decorations.
     *
     * @return TRUE if the window has been set to have decorations.
     */
    public boolean isDecorated() {
        return library.gtk_window_get_decorated(cReference);
    }

    /**
     * Sets whether the window should be decorated.
     * <p>
     * By default, windows are decorated with a title bar, resize controls, etc. Some window managers allow GTK to
     * disable these decorations, creating a borderless window. If you set the decorated property to FALSE using this
     * function, GTK will do its best to convince the window manager not to decorate the window.
     * Depending on the system, this function may not have any effect when called on a window that is already visible,
     * so you should call it before calling gtk_widget_show().
     * <p>
     * On Windows, this function always works, since there's no window manager policy involved.
     *
     * @param isDecorated TRUE to decorate the window.
     */
    public void setDecorated(boolean isDecorated) {
        library.gtk_window_set_decorated(cReference, isDecorated);
    }

    /**
     * Returns whether the window has been set to have a close button.
     *
     * @return TRUE if the window has been set to have a close button.
     */
    public boolean isDeletable() {
        return library.gtk_window_get_deletable(cReference);
    }

    /**
     * Sets whether the window should be deletable.
     * <p>
     * By default, windows have a close button in the window frame. Some window managers allow GTK to disable
     * this button. If you set the deletable property to FALSE using this function,
     * GTK will do its best to convince the window manager not to show a close button. Depending on the system,
     * this function may not have any effect when called on a window that is already visible,
     * so you should call it before calling gtk_widget_show().
     * <p>
     * On Windows, this function always works, since there's no window manager policy involved.
     *
     * @param isDeletable TRUE to decorate the window as deletable.
     */
    public void setDeletable(boolean isDeletable) {
        library.gtk_window_set_deletable(cReference, isDeletable);
    }

    /**
     * Retrieves the current fullscreen state of window.
     * <p>
     * Note that since fullscreening is ultimately handled by the window manager and happens asynchronously to an
     * application request, you shouldn't assume the return value of this function changing immediately (or at all),
     * as an effect of calling gtk_window_fullscreen() or gtk_window_unfullscreen().
     * <p>
     * If the window isn't yet mapped, the value returned will whether the initial requested state is fullscreen.
     *
     * @return Whether the window has a fullscreen state.
     */
    public boolean isFullscreen() {
        return library.gtk_window_is_fullscreen(cReference);
    }

    /**
     * Retrieves the current maximized state of window.
     * <p>
     * Note that since maximization is ultimately handled by the window manager and happens asynchronously to an
     * application request, you shouldn't assume the return value of this function changing immediately (or at all),
     * as an effect of calling gtk_window_maximize() or gtk_window_unmaximize().
     * <p>
     * If the window isn't yet mapped, the value returned will whether the initial requested state is maximized.
     *
     * @return Whether the window has a maximized state.
     */
    public boolean isMaximized() {
        return library.gtk_window_is_maximized(cReference);
    }

    /**
     * Returns whether the window is modal.
     *
     * @return TRUE if the window is set to be modal and establishes a grab when shown.
     */
    public boolean isModal() {
        return library.gtk_window_get_modal(cReference);
    }

    /**
     * Sets a window modal or non-modal.
     * <p>
     * Modal windows prevent interaction with other windows in the same application. T
     * o keep modal dialogs on top of main application windows,
     * use gtk_window_set_transient_for() to make the dialog transient for the parent;
     * most window managers will then disallow lowering the dialog below the parent.
     *
     * @param isWindowModal Whether the window is modal.
     */
    public void setModal(boolean isWindowModal) {
        library.gtk_window_set_modal(cReference, isWindowModal);

    }

    /**
     * Gets the value set by gtk_window_set_resizable().
     *
     * @return TRUE if the user can resize the window.
     */
    public boolean isResizable() {
        return library.gtk_window_get_resizable(cReference);
    }

    /**
     * Sets whether the user can resize a window.
     * <p>
     * Windows are user resizable by default.
     *
     * @param isResizable TRUE if the user can resize this window.
     */
    public void setResizable(boolean isResizable) {
        library.gtk_window_set_resizable(cReference, isResizable);
    }

    /**
     * Asks to maximize window, so that it fills the screen.
     * <p>
     * Note that you shouldn't assume the window is definitely maximized afterward, because other entities
     * (e.g. the user or window manager) could unmaximize it again, and not all window managers support maximization.
     * <p>
     * It's permitted to call this function before showing a window, in which case the window will be maximized when
     * it appears onscreen initially.
     * <p>
     * You can track the result of this operation via the GdkToplevel:state property, or by listening to
     * notificationsreturn
     * on the GtkWindow:maximized property.
     */
    public void maximize() {
        library.gtk_window_maximize(cReference);
    }

    /**
     * Asks to minimize the specified window.
     * <p>
     * Note that you shouldn't assume the window is definitely minimized afterward, because the windowing system might
     * not support this functionality; other entities (e.g. the user or the window manager) could unminimize it again,
     * or there may not be a window manager in which case minimization isn't possible, etc.
     * <p>
     * It's permitted to call this function before showing a window, in which case the window will be minimized before
     * it ever appears onscreen.
     * <p>
     * You can track result of this operation via the GdkToplevel:state property.
     */
    public void minimize() {
        library.gtk_window_minimize(cReference);
    }

    /**
     * Presents a window to the user.
     * <p>
     * This may mean raising the window in the stacking order, unminimizing it, moving it to the current desktop
     * and/or giving it the keyboard focus (possibly dependent on the user's platform, window manager and preferences).
     * <p>
     * If window is hidden, this function also makes it visible.
     */
    public void present() {
        library.gtk_window_present(getCReference());
    }

    /**
     * Presents a window to the user.
     * <p>
     * This may mean raising the window in the stacking order, unminimizing it, moving it to the current desktop,
     * and/or giving it the keyboard focus, possibly dependent on the user's platform, window manager, and preferences.
     * <p>
     * If window is hidden, this function calls gtk_widget_show() as well.
     * <p>
     * This function should be used when the user tries to open a window that's already open. Say for example the
     * preferences dialog is currently open, and the user chooses Preferences from the menu a second time;
     * <p>
     * Presents a window to the user in response to a user interaction. The timestamp should be gathered when
     * the window was requested to be shown (when clicking a link for example), rather than once the window
     * is ready to be shown.
     * <p>
     * See: <a href="https://stackoverflow.com/a/27448501">StackOverflow Q1</a>
     * See: <a href="https://stackoverflow.com/q/29338115">StackOverflow Q2</a>
     *
     * @param timestamp The timestamp of the user interaction (typically a button or key press event) which
     *                  triggered this call.
     */
    public void presentWithTimestamp(DateTime timestamp) {
        if (timestamp == null) {
            timestamp = DateTime.now();
        }
        int unixTimestamp = (int) (timestamp.getMillis() / 1000);
        library.gtk_window_present_with_time(cReference, unixTimestamp);
    }

    /**
     * Sets the default size of a window.
     * <p>
     * If the window's "natural" size (its size request) is larger than the default, the default will be ignored.
     * <p>
     * Unlike gtk_widget_set_size_request(), which sets a size request for a widget and thus would keep users from
     * shrinking the window, this function only sets the initial size, just as if the user had resized the window
     * themselves. Users can still shrink the window again as they normally would. Setting a default size of -1 means
     * to use the "natural" default size (the size request of the window).
     * <p>
     * The default size of a window only affects the first time a window is shown; if a window is hidden and re-shown,
     * it will remember the size it had prior to hiding, rather than using the default size.
     * <p>
     * Windows can't actually be 0x0 in size, they must be at least 1x1, but passing 0 for width and height is OK,
     * resulting in a 1x1 default size.
     * <p>
     * If you use this function to reestablish a previously saved window size, note that the appropriate size to save
     * is the one returned by gtk_window_get_default_size(). Using the window allocation directly will not work in all
     * circumstances and can lead to growing or shrinking windows.
     *
     * @param width  Width in pixels, or -1 to unset the default width.
     * @param height Height in pixels, or -1 to unset the default height.
     */
    public void setDefaultSize(int width, int height) {
        if (width >= -1 && height >= -1) {
            library.gtk_window_set_default_size(cReference, width, height);
        }
    }

    /**
     * Sets the GdkDisplay where the window is displayed.
     * <p>
     * If the window is already mapped, it will be unmapped, and then remapped on the new display.
     *
     * @param display A GdkDisplay
     */
    public void setDisplay(GdkDisplay display) {
        if (display != null) {
            library.gtk_window_set_display(cReference, display.getCReference());
        }
    }

    /**
     * Sets whether "focus rectangles" are supposed to be visible.
     *
     * @param areFocusRectanglesVis TRUE if focus rectangles are visible
     */
    public void setFocusRectanglesVisible(boolean areFocusRectanglesVis) {
        library.gtk_window_set_focus_visible(cReference, areFocusRectanglesVis);
    }

    /**
     * If setting is TRUE, then clicking the close button on the window will not destroy it, but only hide it.
     *
     * @param hideOnClose Whether to hide the window when it is closed.
     */
    public void setHideOnClose(boolean hideOnClose) {
        library.gtk_window_set_hide_on_close(cReference, hideOnClose);
    }

    /**
     * Sets whether mnemonics are supposed to be visible.
     *
     * @param areMnemonicsVis If true, underlined Mnemonics are visible
     */
    public void setMnemonicsVisible(boolean areMnemonicsVis) {
        library.gtk_window_set_mnemonics_visible(cReference, areMnemonicsVis);
    }

    /**
     * Sets the startup notification ID.
     * <p>
     * Startup notification identifiers are used by desktop environment to track application startup, to provide user
     * feedback and other features. This function changes the corresponding property on the underlying GdkSurface.
     * <p>
     * Normally, startup identifier is managed automatically, and you should only use this function in special cases
     * like transferring focus from other processes. You should use this function before calling gtk_window_present()
     * or any equivalent function generating a window map event.
     * <p>
     * This function is only useful on X11, not with other GTK targets.
     *
     * @param startupId A string with startup-notification identifier.
     */
    public void setStartupNotificationId(String startupId) {
        if (startupId != null) {
            library.gtk_window_set_startup_id(cReference, startupId);
        }
    }

    /**
     * If setting is TRUE, then destroying the transient parent of window will also destroy window itself.
     * <p>
     * This is useful for dialogs that shouldn't persist beyond the lifetime of the main window they are associated
     * with, for example.
     *
     * @param shouldDestroy Whether to destroy window with its transient parent.
     */
    public void shouldDestroyWithParent(boolean shouldDestroy) {
        library.gtk_window_set_destroy_with_parent(cReference, shouldDestroy);
    }

    /**
     * Sets whether this window should react to F10 key presses by activating a menubar it contains.
     * <p>
     * Available since: 4.2
     *
     * @param shouldF10ActivateMenuBar TRUE to make window handle F10
     */
    public void shouldF10KeyActivatesMenuBar(boolean shouldF10ActivateMenuBar) {
        library.gtk_window_set_handle_menubar_accel(cReference, shouldF10ActivateMenuBar);
    }

    /**
     * Asks to remove the fullscreen state for window, and return to its previous state.
     * <p>
     * Note that you shouldn't assume the window is definitely not fullscreen afterward, because other entities
     * (e.g. the user or window manager could fullscreen it again, and not all window managers honor requests to
     * unfullscreen windows); normally the window will end up restored to its normal state. J
     * ust don't write code that crashes if not.
     * <p>
     * You can track the result of this operation via the GdkToplevel:state property, or by listening to
     * notifications of the GtkWindow:fullscreened property.
     */
    public void unfullscreen() {
        library.gtk_window_unfullscreen(cReference);
    }

    /**
     * Asks to unmaximize window.
     * <p>
     * Note that you shouldn't assume the window is definitely unmaximized afterward, because other entities
     * (e.g. the user or window manager) maximize it again, and not all window managers honor requests to unmaximize.
     * <p>
     * You can track the result of this operation via the GdkToplevel:state property, or by listening to notifications
     * on the GtkWindow:maximized property.
     */
    public void unmaximize() {
        library.gtk_window_unmaximize(cReference);
    }

    /**
     * Asks to unminimize the specified window.
     * <p>
     * Note that you shouldn't assume the window is definitely unminimized afterward, because the windowing system might
     * not support this functionality; other entities (e.g. the user or the window manager) could minimize it again, or
     * there may not be a window manager in which case minimization isn't possible, etc.
     * <p>
     * You can track result of this operation via the GdkToplevel:state property.
     */
    public void unminimize() {
        library.gtk_window_unminimize(cReference);
    }

    public static class Signals extends GtkWidget.Signals {
        /**
         * Emitted when the user activates the default widget of window.
         * <p>
         * This is a keybinding signal.
         */
        public static final Signals ACTIVATE_DEFAULT = new Signals("activate-default");
        /**
         * Emitted when the user activates the currently focused widget of window.
         * <p>
         * This is a keybinding signal.
         */
        public static final Signals ACTIVATE_FOCUS = new Signals("activate-focus");
        /**
         * Emitted when the user clicks on the close button of the window.
         */
        public static final Signals CLOSE_REQUEST = new Signals("close-request");
        /**
         * Emitted when the user enables or disables interactive debugging.
         * <p>
         * When toggle is TRUE, interactive debugging is toggled on or off, when it is FALSE,
         * the debugger will be pointed at the widget under the pointer.
         * <p>
         * This is a keybinding signal.
         * <p>
         * The default bindings for this signal are Ctrl-Shift-I and Ctrl-Shift-D.
         */
        public static final Signals ENABLE_DEBUGGING = new Signals("enable-debugging");
        /**
         * Emitted when the set of accelerators or mnemonics that are associated with window changes.
         */
        public static final Signals KEYS_CHANGED = new Signals("keys-changed");

        protected Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkWindowLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Requests that the window is closed.
         * <p>
         * This is similar to what happens when a window manager close button is clicked.
         * <p>
         * This function can be used with close buttons in custom titlebars.
         *
         * @param window self
         */
        public native void gtk_window_close(Pointer window);

        /**
         * Drop the internal reference GTK holds on toplevel windows.
         *
         * @param window self
         */
        public native void gtk_window_destroy(Pointer window);

        /**
         * Asks to place window in the fullscreen state.
         * <p>
         * Note that you shouldn't assume the window is definitely fullscreen afterward, because other entities
         * (e.g. the user or window manager) unfullscreen it again, and not all window managers honor requests to
         * fullscreen windows.
         * <p>
         * You can track the result of this operation via the GdkToplevel:state property, or by listening to
         * notifications of the GtkWindow:fullscreened property.
         *
         * @param window self
         */
        public native void gtk_window_fullscreen(Pointer window);

        /**
         * Asks to place window in the fullscreen state on the given monitor.
         * <p>
         * Note that you shouldn't assume the window is definitely fullscreen afterward, or that the windowing system
         * allows fullscreen windows on any given monitor.
         * <p>
         * You can track the result of this operation via the GdkToplevel:state property, or by listening to
         * notifications of the GtkWindow:fullscreened property.
         *
         * @param window  self
         * @param monitor Which monitor to go fullscreen on. Type: GdkMonitor
         */
        public native void gtk_window_fullscreen_on_monitor(Pointer window, Pointer monitor);

        /**
         * Gets the GtkApplication associated with the window.
         *
         * @param window self
         * @return A GtkApplication
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_window_get_application(Pointer window);

        /**
         * Gets the child widget of window.
         *
         * @param window self
         * @return The child widget of window. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_window_get_child(Pointer window);

        /**
         * Returns whether the window has been set to have decorations.
         *
         * @param window self
         * @return TRUE if the window has been set to have decorations.
         */
        public native boolean gtk_window_get_decorated(Pointer window);

        /**
         * Returns the fallback icon name for windows.
         * <p>
         * The returned string is owned by GTK and should not be modified. It is only valid until the next call to
         * gtk_window_set_default_icon_name().
         *
         * @return The fallback icon name for windows.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_window_get_default_icon_name();

        /**
         * Gets the default size of the window.
         * <p>
         * A value of 0 for the width or height indicates that a default size has not been explicitly set for that
         * dimension, so the "natural" size of the window will be used.
         * <p>
         * This function is the recommended way for saving window state across restarts of applications.
         *
         * @param window self
         * @param width  Location to store the default width.
         *               <p>
         *               The argument can be NULL.
         * @param height Location to store the default height.
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_window_get_default_size(Pointer window, PointerByReference width, PointerByReference height);

        /**
         * Returns the default widget for window.
         *
         * @param window self
         * @return The default widget. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_window_get_default_widget(Pointer window);

        /**
         * Returns whether the window has been set to have a close button.
         *
         * @param window self
         * @return TRUE if the window has been set to have a close button.
         */
        public native boolean gtk_window_get_deletable(Pointer window);

        /**
         * Returns whether the window will be destroyed with its transient parent.
         *
         * @param window self
         * @return TRUE if the window will be destroyed with its transient parent.
         */
        public native boolean gtk_window_get_destroy_with_parent(Pointer window);

        /**
         * Retrieves the current focused widget within the window.
         * <p>
         * Note that this is the widget that would have the focus if the toplevel window focused; if the toplevel window
         * is not focused then gtk_widget_has_focus (widget) will not be TRUE for the widget.
         *
         * @param window self
         * @return The currently focused widget. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_window_get_focus(Pointer window);

        /**
         * Gets whether "focus rectangles" are supposed to be visible.
         *
         * @param window self
         * @return TRUE if "focus rectangles" are supposed to be visible in this window.
         */
        public native boolean gtk_window_get_focus_visible(Pointer window);

        /**
         * Returns the group for window.
         * <p>
         * If the window has no group, then the default group is returned.
         *
         * @param window self
         * @return The GtkWindowGroup for a window or the default group.
         */
        public native Pointer gtk_window_get_group(Pointer window);

        /**
         * Returns whether this window reacts to F10 key presses by activating a menubar it contains.
         *
         * @param window self
         * @return TRUE if the window handles F10
         * @since 4.2
         */
        public native boolean gtk_window_get_handle_menubar_accel(Pointer window);

        /**
         * Returns whether the window will be hidden when the close button is clicked.
         *
         * @param window self
         * @return TRUE if the window will be hidden.
         */
        public native boolean gtk_window_get_hide_on_close(Pointer window);

        /**
         * Returns the name of the themed icon for the window.
         *
         * @param window self
         * @return The icon name.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_window_get_icon_name(Pointer window);

        /**
         * Gets whether mnemonics are supposed to be visible.
         *
         * @param window self
         * @return TRUE if mnemonics are supposed to be visible in this window.
         */
        public native boolean gtk_window_get_mnemonics_visible(Pointer window);

        /**
         * Returns whether the window is modal.
         *
         * @param window self
         * @return TRUE if the window is set to be modal and establishes a grab when shown.
         */
        public native boolean gtk_window_get_modal(Pointer window);

        /**
         * Gets the value set by gtk_window_set_resizable().
         *
         * @param window self
         * @return TRUE if the user can resize the window.
         */
        public native boolean gtk_window_get_resizable(Pointer window);

        /**
         * Retrieves the title of the window.
         *
         * @param window self
         * @return The title of the window.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_window_get_title(Pointer window);

        /**
         * Returns the custom titlebar that has been set with gtk_window_set_titlebar().
         *
         * @param window self
         * @return The custom titlebar.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_window_get_titlebar(Pointer window);

        /**
         * Returns a list of all existing toplevel windows.
         * <p>
         * If you want to iterate through the list and perform actions involving callbacks that might destroy the
         * widgets or add new ones, be aware that the list of toplevels will change and emit the "items-changed" signal.
         *
         * @return The list of toplevel widgets. Type: A list model of GtkWindow
         */
        public native Pointer gtk_window_get_toplevels();

        /**
         * Fetches the transient parent for this window.
         *
         * @param window self
         * @return The transient parent for this window. Type: GtkWindow
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_window_get_transient_for(Pointer window);

        /**
         * Returns whether window has an explicit window group.
         *
         * @param window self
         * @return TRUE if window has an explicit window group.
         */
        public native boolean gtk_window_has_group(Pointer window);

        /**
         * Returns whether the window is part of the current active toplevel.
         * <p>
         * The active toplevel is the window receiving keystrokes.
         * <p>
         * The return value is TRUE if the window is active toplevel itself. You might use this function if you wanted
         * to draw a widget differently in an active window from a widget in an inactive window.
         *
         * @param window self
         * @return TRUE if the window part of the current active window.
         */
        public native boolean gtk_window_is_active(Pointer window);

        /**
         * Retrieves the current fullscreen state of window.
         * <p>
         * Note that since fullscreening is ultimately handled by the window manager and happens asynchronously to an
         * application request, you shouldn't assume the return value of this function changing immediately (or at all),
         * as an effect of calling gtk_window_fullscreen() or gtk_window_unfullscreen().
         * <p>
         * If the window isn't yet mapped, the value returned will whether the initial requested state is fullscreen.
         *
         * @param window self
         * @return Whether the window has a fullscreen state.
         */
        public native boolean gtk_window_is_fullscreen(Pointer window);

        /**
         * Retrieves the current maximized state of window.
         * <p>
         * Note that since maximization is ultimately handled by the window manager and happens asynchronously to an
         * application request, you shouldn't assume the return value of this function changing immediately (or at all),
         * as an effect of calling gtk_window_maximize() or gtk_window_unmaximize().
         * <p>
         * If the window isn't yet mapped, the value returned will whether the initial requested state is maximized.
         *
         * @param window self
         * @return Whether the window has a maximized state.
         */
        public native boolean gtk_window_is_maximized(Pointer window);

        /**
         * Asks to maximize window, so that it fills the screen.
         * <p>
         * Note that you shouldn't assume the window is definitely maximized afterward, because other entities (e.g.
         * the user or window manager) could unmaximize it again, and not all window managers support maximization.
         * <p>
         * It's permitted to call this function before showing a window, in which case the window will be maximized when
         * it appears onscreen initially.
         * <p>
         * You can track the result of this operation via the GdkToplevel:state property, or by listening to
         * notifications on the GtkWindow:maximized property.
         *
         * @param window self
         */
        public native void gtk_window_maximize(Pointer window);

        /**
         * Asks to minimize the specified window.
         * <p>
         * Note that you shouldn't assume the window is definitely minimized afterward, because the windowing system
         * might not support this functionality; other entities (e.g. the user or the window manager) could unminimize
         * it again, or there may not be a window manager in which case minimization isn't possible, etc.
         * <p>
         * It's permitted to call this function before showing a window, in which case the window will be minimized
         * before it ever appears onscreen.
         * <p>
         * You can track result of this operation via the GdkToplevel:state property.
         *
         * @param window self
         */
        public native void gtk_window_minimize(Pointer window);

        /**
         * Creates a new GtkWindow.
         * <p>
         * To get an undecorated window (no window borders), use gtk_window_set_decorated().
         * <p>
         * All top-level windows created by gtk_window_new() are stored in an internal top-level window list.
         * This list can be obtained from gtk_window_list_toplevels(). Due to GTK keeping a reference to the window
         * internally, gtk_window_new() does not return a reference to the caller.
         * <p>
         * To delete a GtkWindow, call gtk_window_destroy().
         *
         * @return A new GtkWindow.
         */
        public native Pointer gtk_window_new();

        /**
         * Presents a window to the user.
         * <p>
         * This may mean raising the window in the stacking order, unminimizing it, moving it to the current desktop
         * and/or giving it the keyboard focus (possibly dependent on the user's platform, window manager and
         * preferences).
         * <p>
         * If window is hidden, this function also makes it visible.
         *
         * @param window self
         */
        public native void gtk_window_present(Pointer window);

        /**
         * Presents a window to the user in response to an user interaction.
         * <p>
         * See gtk_window_present() for more details.
         * <p>
         * The timestamp should be gathered when the window was requested to be shown (when clicking a link for
         * example), rather than once the window is ready to be shown.
         *
         * @param window    self
         * @param timestamp The timestamp of the user interaction (typically a button or key press event) which
         *                  triggered this call.
         */
        public native void gtk_window_present_with_time(Pointer window, int timestamp);

        /**
         * Sets or unsets the GtkApplication associated with the window.
         * <p>
         * The application will be kept alive for at least as long as it has any windows associated with it (see
         * g_application_hold() for a way to keep it alive without windows).
         * <p>
         * Normally, the connection between the application and the window will remain until the window is destroyed,
         * but you can explicitly remove it by setting the application to NULL.
         * <p>
         * This is equivalent to calling gtk_application_remove_window() and/or gtk_application_add_window() on the
         * old/new applications as relevant.
         *
         * @param window      self
         * @param application A GtkApplication, or NULL to unset. Type: GtkApplication
         *                    <p>
         *                    The argument can be NULL.
         */
        public native void gtk_window_set_application(Pointer window, Pointer application);

        /**
         * Sets whether the window should request startup notification.
         * <p>
         * By default, after showing the first GtkWindow, GTK calls gdk_toplevel_set_startup_id(). Call this function to
         * disable the automatic startup notification. You might do this if your first window is a splash screen, and
         * you want to delay notification until after your real main window has been shown, for example.
         * <p>
         * In that example, you would disable startup notification temporarily, show your splash screen, then re-enable
         * it so that showing the main window would automatically result in notification.
         *
         * @param setting TRUE to automatically do startup notification.
         */
        public native void gtk_window_set_auto_startup_notification(boolean setting);

        /**
         * Sets the child widget of window.
         *
         * @param window self
         * @param child  The child widget. Type: GtkWidget
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_window_set_child(Pointer window, Pointer child);

        /**
         * Sets whether the window should be decorated.
         * <p>
         * By default, windows are decorated with a title bar, resize controls, etc. Some window managers allow GTK to
         * disable these decorations, creating a borderless window. If you set the decorated property to FALSE using
         * this function, GTK will do its best to convince the window manager not to decorate the window. Depending on
         * the system, this function may not have any effect when called on a window that is already visible, so you
         * should call it before calling gtk_widget_show().
         * <p>
         * On Windows, this function always works, since there's no window manager policy involved.
         *
         * @param window  self
         * @param setting TRUE to decorate the window.
         */
        public native void gtk_window_set_decorated(Pointer window, boolean setting);

        /**
         * Sets an icon to be used as fallback.
         * <p>
         * The fallback icon is used for windows that haven't had gtk_window_set_icon_name() called on them.
         *
         * @param name The name of the themed icon.
         */
        public native void gtk_window_set_default_icon_name(String name);

        /**
         * Sets the default size of a window.
         * <p>
         * The default size of a window is the size that will be used if no other constraints apply.
         * <p>
         * The default size will be updated whenever the window is resized to reflect the new size, unless the window is
         * forced to a size, like when it is maximized or fullscreened.
         * <p>
         * If the window's minimum size request is larger than the default, the default will be ignored.
         * <p>
         * Setting the default size to a value <= 0 will cause it to be ignored and the natural size request will be
         * used instead. It is possible to do this while the window is showing to "reset" it to its initial size.
         * <p>
         * Unlike gtk_widget_set_size_request(), which sets a size request for a widget and thus would keep users from
         * shrinking the window, this function only sets the initial size, just as if the user had resized the window
         * themselves. Users can still shrink the window again as they normally would. Setting a default size of -1
         * means to use the "natural" default size (the size request of the window).
         * <p>
         * If you use this function to reestablish a previously saved window size, note that the appropriate size to
         * save is the one returned by gtk_window_get_default_size(). Using the window allocation directly will not
         * work in all circumstances and can lead to growing or shrinking windows.
         *
         * @param window self
         * @param width  Width in pixels, or -1 to unset the default width.
         * @param height Height in pixels, or -1 to unset the default height.
         */
        public native void gtk_window_set_default_size(Pointer window, int width, int height);

        /**
         * Sets the default widget.
         * <p>
         * The default widget is the widget that is activated when the user presses Enter in a dialog (for example).
         *
         * @param window         self
         * @param default_widget Widget to be the default to unset the default widget for the toplevel. Type: GtkWidget
         *                       <p>
         *                       The argument can be NULL.
         */
        public native void gtk_window_set_default_widget(Pointer window, Pointer default_widget);

        /**
         * Sets whether the window should be deletable.
         * <p>
         * By default, windows have a close button in the window frame. Some window managers allow GTK to disable this
         * button. If you set the deletable property to FALSE using this function, GTK will do its best to convince the
         * window manager not to show a close button. Depending on the system, this function may not have any effect
         * when called on a window that is already visible, so you should call it before calling gtk_widget_show().
         * <p>
         * On Windows, this function always works, since there's no window manager policy involved.
         *
         * @param window  self
         * @param setting TRUE to decorate the window as deletable.
         */
        public native void gtk_window_set_deletable(Pointer window, boolean setting);

        /**
         * If setting is TRUE, then destroying the transient parent of window will also destroy window itself.
         * <p>
         * This is useful for dialogs that shouldn't persist beyond the lifetime of the main window they are associated
         * with, for example.
         *
         * @param window  self
         * @param setting Whether to destroy window with its transient parent.
         */
        public native void gtk_window_set_destroy_with_parent(Pointer window, boolean setting);

        /**
         * Sets the GdkDisplay where the window is displayed.
         * <p>
         * If the window is already mapped, it will be unmapped, and then remapped on the new display.
         *
         * @param window  self
         * @param display A GdkDisplay. Type: GdkDisplay
         */
        public native void gtk_window_set_display(Pointer window, Pointer display);

        /**
         * Sets the focus widget.
         * <p>
         * If focus is not the current focus widget, and is focusable, sets it as the focus widget for the window.
         * If focus is NULL, unsets the focus widget for this window. To set the focus to a particular widget in the
         * toplevel, it is usually more convenient to use gtk_widget_grab_focus() instead of this function.
         *
         * @param window self
         * @param focus  Widget to be the new focus widget, or NULL to unset any focus widget for the toplevel
         *               window.
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_window_set_focus(Pointer window, Pointer focus);

        /**
         * Sets whether "focus rectangles" are supposed to be visible.
         * <p>
         * This property is maintained by GTK based on user input, and should not be set by applications.
         *
         * @param window  self
         * @param setting The new value.
         */
        public native void gtk_window_set_focus_visible(Pointer window, boolean setting);

        /**
         * Sets whether this window should react to F10 key presses by activating a menubar it contains.
         *
         * @param window               self
         * @param handle_menubar_accel TRUE to make window handle F10
         * @since 4.2
         */
        public native void gtk_window_set_handle_menubar_accel(Pointer window, boolean handle_menubar_accel);

        /**
         * If setting is TRUE, then clicking the close button on the window will not destroy it, but only hide it.
         *
         * @param window  self
         * @param setting Whether to hide the window when it is closed.
         */
        public native void gtk_window_set_hide_on_close(Pointer window, boolean setting);

        /**
         * Sets the icon for the window from a named themed icon.
         * <p>
         * See the docs for GtkIconTheme for more details. On some platforms, the window icon is not used at all.
         * <p>
         * Note that this has nothing to do with the WM_ICON_NAME property which is mentioned in the ICCCM.
         *
         * @param window self
         * @param name   The name of the themed icon.
         *               <p>
         *               The argument can be NULL
         */
        public native void gtk_window_set_icon_name(Pointer window, String name);

        /**
         * Opens or closes the interactive debugger.
         * <p>
         * The debugger offers access to the widget hierarchy of the application and to useful debugging tools.
         *
         * @param enable TRUE to enable interactive debugging.
         */
        public native void gtk_window_set_interactive_debugging(boolean enable);

        /**
         * Sets whether mnemonics are supposed to be visible.
         * <p>
         * This property is maintained by GTK based on user input, and should not be set by applications.
         *
         * @param window  self
         * @param setting The new value.
         */
        public native void gtk_window_set_mnemonics_visible(Pointer window, boolean setting);

        /**
         * Sets a window modal or non-modal.
         * <p>
         * Modal windows prevent interaction with other windows in the same application. To keep modal dialogs on top of
         * main application windows, use gtk_window_set_transient_for() to make the dialog transient for the parent;
         * most window managers will then disallow lowering the dialog below the parent.
         *
         * @param window self
         * @param modal  Whether the window is modal.
         */
        public native void gtk_window_set_modal(Pointer window, boolean modal);

        /**
         * Sets whether the user can resize a window.
         * <p>
         * Windows are user resizable by default.
         *
         * @param window    self
         * @param resizable TRUE if the user can resize this window.
         */
        public native void gtk_window_set_resizable(Pointer window, boolean resizable);

        /**
         * Sets the startup notification ID.
         * <p>
         * Startup notification identifiers are used by desktop environment to track application startup, to provide
         * user feedback and other features. This function changes the corresponding property on the underlying
         * GdkSurface.
         * <p>
         * Normally, startup identifier is managed automatically and you should only use this function in special
         * cases like transferring focus from other processes. You should use this function before calling
         * gtk_window_present() or any equivalent function generating a window map event.
         * <p>
         * This function is only useful on X11, not with other GTK targets.
         *
         * @param window     self
         * @param startup_id A string with startup-notification identifier.
         */
        public native void gtk_window_set_startup_id(Pointer window, String startup_id);

        /**
         * Sets the title of the GtkWindow.
         * <p>
         * The title of a window will be displayed in its title bar; on the X Window System, the title bar is rendered
         * by the window manager so exactly how the title appears to users may vary according to a user's exact
         * configuration. The title should help a user distinguish this window from other windows they may have open.
         * A good title might include the application name and current document filename, for example.
         * <p>
         * Passing NULL does the same as setting the title to an empty string.
         *
         * @param window self
         * @param title  Title of the window.
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_window_set_title(Pointer window, String title);

        /**
         * Sets a custom titlebar for window.
         * <p>
         * A typical widget used here is GtkHeaderBar, as it provides various features expected of a titlebar while
         * allowing the addition of child widgets to it.
         * <p>
         * If you set a custom titlebar, GTK will do its best to convince the window manager not to put its own titlebar
         * on the window. Depending on the system, this function may not work for a window that is already visible,
         * so you set the titlebar before calling gtk_widget_show().
         *
         * @param window   self
         * @param titlebar The widget to use as titlebar. Type: GtkWidget
         *                 <p>
         *                 The argument can be NULL.
         */
        public native void gtk_window_set_titlebar(Pointer window, Pointer titlebar);

        /**
         * Dialog windows should be set transient for the main application window they were spawned from. This allows
         * window managers to e.g. keep the dialog on top of the main window, or center the dialog over the main window.
         * gtk_dialog_new_with_buttons() and other convenience functions in GTK will sometimes call
         * gtk_window_set_transient_for() on your behalf.
         * <p>
         * Passing NULL for parent unsets the current transient window.
         * <p>
         * On Windows, this function puts the child window on top of the parent, much as the window manager would have
         * done on X.
         *
         * @param window self
         * @param parent Parent window. Type: GtkWindow
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_window_set_transient_for(Pointer window, Pointer parent);

        /**
         * Asks to remove the fullscreen state for window, and return to its previous state.
         * <p>
         * Note that you shouldn't assume the window is definitely not fullscreen afterward, because other entities
         * (e.g. the user or window manager) could fullscreen it again, and not all window managers honor requests to
         * unfullscreen windows; normally the window will end up restored to its normal state. Just don't write code
         * that crashes if not.
         * <p>
         * You can track the result of this operation via the GdkToplevel:state property, or by listening to
         * notifications of the GtkWindow:fullscreened property.
         *
         * @param window self
         */
        public native void gtk_window_unfullscreen(Pointer window);

        /**
         * Asks to unmaximize window.
         * <p>
         * Note that you shouldn't assume the window is definitely unmaximized afterward, because other entities (e.g.
         * the user or window manager) maximize it again, and not all window managers honor requests to unmaximize.
         * <p>
         * You can track the result of this operation via the GdkToplevel:state property, or by listening to
         * notifications on the GtkWindow:maximized property.
         *
         * @param window self
         */
        public native void gtk_window_unmaximize(Pointer window);

        /**
         * Asks to unminimize the specified window.
         * <p>
         * Note that you shouldn't assume the window is definitely unminimized afterward, because the windowing system
         * might not support this functionality; other entities (e.g. the user or the window manager) could minimize it
         * again, or there may not be a window manager in which case minimization isn't possible, etc.
         * <p>
         * You can track result of this operation via the GdkToplevel:state property.
         *
         * @param window self
         */
        public native void gtk_window_unminimize(Pointer window);

    }

}
