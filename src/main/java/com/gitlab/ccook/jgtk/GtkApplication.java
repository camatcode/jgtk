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

import com.gitlab.ccook.jgtk.bitfields.GApplicationFlags;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.bitfields.GtkApplicationInhibitFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.gtk.GtkWindow;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.AssertionUtils;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class GtkApplication extends JGTKConnectableObject {
    private static final Logger log = LoggerFactory.getLogger(GtkApplication.class);
    private String appId;
    private GApplicationFlags flags;

    /**
     * Creates a new GtkApplication instance.
     *
     * @param applicationId The application ID.
     * @param flags         The application flags.
     */
    public GtkApplication(String applicationId, GApplicationFlags flags) {
        super(handleCtor(applicationId, flags));
        this.appId = applicationId;
        this.flags = flags;
    }

    private static Pointer handleCtor(String applicationId, GApplicationFlags... flags) {
        AssertionUtils.assertNotNull(GtkApplication.class, "ctor: applicationId null", applicationId);
        AssertionUtils.assertTrue(GtkApplication.class, "ctr: applicationId invalid: " + applicationId, library.g_application_id_is_valid(applicationId));
        Pointer cReference = library.gtk_application_new(applicationId, GApplicationFlags.getCValueFromFlags(flags));
        AssertionUtils.assertNotNull(GtkApplication.class, "ctor: call to GTK did not make a GTKApplication", cReference);
        return cReference;
    }

    /**
     * References an already created GtkApplication
     *
     * @param gtkApplicationPointer the internal reference to the gtk application
     */
    public GtkApplication(Pointer gtkApplicationPointer) {
        super(gtkApplicationPointer);
    }

    /**
     * Adds a window to application.
     *
     * @param toAdd window to add
     */
    public void addWindow(GtkWindow toAdd) {
        if (toAdd.cReference != null) {
            library.gtk_application_add_window(cReference, toAdd.cReference);
        }
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
     * Lists the detailed action names which have associated accelerators.
     *
     * @return Action names with associated accelerators
     */
    public String[] getActionDescriptions() {
        return library.gtk_application_list_action_descriptions(cReference);
    }

    /**
     * Returns the list of actions (possibly empty) that accelerator maps to.
     *
     * @param accel accelerator associated with returned actions
     * @return actions associated with given accelerator
     */
    public List<String> getActionsForKeyboardAccelerator(String accel) {
        if (accel != null) {
            return Arrays.asList(library.gtk_application_get_actions_for_accel(cReference, accel));
        }
        return new ArrayList<>();
    }

    /**
     * Gets the "active" window for the application.
     *
     * @return the Active window (may be NONE)
     */
    public Option<GtkWindow> getActiveWindow() {
        Option<Pointer> pointer = new Option<>(library.gtk_application_get_active_window(cReference));
        if (pointer.isDefined()) {
            return new Option<>(new GtkWindow(pointer.get()));
        }
        return Option.NONE;
    }

    /**
     * @return the build app id
     */
    public String getAppId() {
        if (appId == null) {
            return "UNKNOWN";
        }
        return appId;
    }

    /**
     * @return application flags
     */
    public GApplicationFlags getFlags() {
        if (flags == null) {
            return GApplicationFlags.G_APPLICATION_DEFAULT_FLAGS;
        }
        return flags;
    }

    /**
     * Gets the accelerators that are currently associated with the given action.
     *
     * @param detailedActionName action name associated with returned accelerators
     * @return the accelerators associated with the action name
     */
    public List<String> getKeyboardAcceleratorsForAction(String detailedActionName) {
        if (detailedActionName != null) {
            return Arrays.asList(library.gtk_application_get_accels_for_action(cReference, detailedActionName));
        }
        return new ArrayList<>();
    }

    /**
     * Returns the menu model that has been set with getMenuBar()
     *
     * @return The Menu Bar (may be NONE)
     */
    public Option<GMenuModel> getMenuBar() {
        Option<Pointer> pointer = new Option<>(library.gtk_application_get_menubar(cReference));
        if (pointer.isDefined()) {
            return new Option<>(new GMenuModel(pointer.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets or unsets the menubar for windows of application.
     * <p>
     * This is a menubar in the traditional sense.
     * <p>
     * This can only be done in the primary instance of the application, after it has been registered.
     * GApplication::startup is a good place to call this.
     * <p>
     * Depending on the desktop environment, this may appear at the top of each window, or at the top of the screen.
     * In some environments, if both the application menu and the menubar are set,
     * the application menu will be presented as if it were the first item of the menubar.
     * Other environments treat the two as completely separate - for example, the application menu may be rendered by
     * the desktop shell while the menubar (if set) remains in each individual window.
     * <p>
     * Use the base GActionMap interface to add actions, to respond to the user selecting these menu items.
     *
     * @param menuBar Menu bar to set (NONE to unset)
     */
    public void setMenuBar(Option<GMenuModel> menuBar) {
        if (menuBar.isDefined()) {
            library.gtk_application_set_menubar(cReference, menuBar.get().cReference);
        } else {
            library.gtk_application_set_menubar(cReference, Pointer.NULL);
        }
    }

    /**
     * Gets a menu from automatically loaded resources.
     *
     * @param id The id of the menu to look up.
     * @return Gets the menu with the given id from the automatically loaded resources. (may be NONE)
     */
    public Option<GMenu> getMenuById(String id) {
        AssertionUtils.assertNotNull(GtkApplication.class, "getMenuById(id). id is null", id);
        Option<Pointer> p = new Option<>(library.gtk_application_get_menu_by_id(cReference, id));
        if (p.isDefined()) {
            return new Option<>(new GMenu(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Returns the GtkApplicationWindow with the given ID.
     *
     * @param id id of the window to get
     * @return the window associated with that id (may be NONE)
     */
    public Option<GtkWindow> getWindowById(int id) {
        if (id >= 0) {
            Option<Pointer> pointer = new Option<>(library.gtk_application_get_window_by_id(cReference, id));
            if (pointer.isDefined()) {
                return new Option<>(new GtkWindow(pointer.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Gets a list of the GtkWindow instances associated with application.
     * <p>
     * The list is sorted by most recently focused window, such that the first element is the currently focused window.
     * (Useful for choosing a parent for a transient window.)
     *
     * @return list of associated windows
     */
    public List<GtkWindow> getWindows() {
        return new GListArrayList<>(new GList<>(GtkWindow.class, library.gtk_application_get_windows(cReference)));
    }

    /**
     * Inform the session manager that certain types of actions should be inhibited.
     * <p>
     * This is not guaranteed to work on all platforms and for all types of actions.
     * <p>
     * Applications should invoke this method when they begin an operation that should not be interrupted,
     * such as creating a CD or DVD. The types of actions that may be blocked are specified by the flag's parameter.
     * When the application completes the operation it should call uninhibit() to remove the inhibitor.
     * Note that an application can have multiple inhibitors, and all of them must be individually removed.
     * Inhibitors are also cleared when the application exits.
     * <p>
     * Applications should not expect that they will always be able to block the action.
     * In most cases, users will be given the option to force the action to take place.
     * <p>
     * The reason message should be short and to the point.
     * <p>
     * If window is given, the session manager may point the user to this window to find out more about why the
     * action is inhibited.
     *
     * @param windowToInhibit the window the session manager will point to, if defined
     * @param whatToInhibit   action to prevent
     * @param reasonWhy       text reason why to inhibit
     * @return A non-zero cookie that is used to uniquely identify this request.
     *         It should be used as an argument to uninhibit() in order to remove the request.
     *         If the platform does not support inhibiting or the request failed for some reason, 0 is returned.
     */
    public Option<InhibitCookie> inhibitWindow(Option<GtkWindow> windowToInhibit, String reasonWhy, GtkApplicationInhibitFlags... whatToInhibit) {
        if (reasonWhy == null) {
            reasonWhy = "UNKNOWN";
        }
        int cookie;
        if (windowToInhibit.isDefined()) {
            cookie = library.gtk_application_inhibit(cReference, windowToInhibit.get().cReference, GtkApplicationInhibitFlags.getCValueFromFlags(whatToInhibit), reasonWhy);
        } else {
            cookie = library.gtk_application_inhibit(cReference, Pointer.NULL, GtkApplicationInhibitFlags.getCValueFromFlags(whatToInhibit), reasonWhy);
        }
        if (cookie > 0) {
            return new Option<>(new InhibitCookie(cookie));
        }
        return Option.NONE;
    }

    /**
     * This property is TRUE if GTK believes that the screensaver is currently active.
     * <p>
     * GTK only tracks session state (including this) when GtkApplication:register-session is set to TRUE.
     * <p>
     * Tracking the screensaver state is currently only supported on Linux.
     *
     * @return if screen save is active
     */
    public boolean isScreenSaverActive() {
        if (isSessionRegistered()) {
            Option<GValue> p = getProperty("screensaver-active");
            if (p.isDefined()) {
                return p.get().getBoolean();
            }
        }
        log.warn("isScreenSaverActive = false because isSessionRegistered() is false. Use registerSession()");
        return false;
    }

    /**
     * @return TRUE if the session manager is tracking this application
     */
    public boolean isSessionRegistered() {
        Option<GValue> value = getProperty("register-session");
        if (value.isDefined()) {
            return value.get().getBoolean();
        }
        return false;
    }

    public void quit() {
        library.g_application_quit(cReference);
    }

    /**
     * Remove a window from application.
     * <p>
     * If window belongs to application then this call is equivalent to setting the GtkWindow:application property of
     * window to NULL.
     * <p>
     * The application may stop running as a result of a call to this function, if window was the last window of the
     * application.
     *
     * @param toRemove windowToRemove
     */
    public void removeWindow(GtkWindow toRemove) {
        if (toRemove != null) {
            library.gtk_application_remove_window(cReference, toRemove.cReference);
        }
    }

    /**
     * Runs the application.
     *
     * @param args args to send to application
     * @return exit code
     */
    public int run(String[] args) {
        if (args == null) {
            args = new String[]{};
        }
        return library.g_application_run(cReference, args.length, args);
    }

    /**
     * Sets zero or more keyboard accelerators that will trigger the given action.
     * <p>
     * The first item in accels will be the primary accelerator, which may be displayed in the UI.
     * <p>
     * To remove all accelerators for an action, use an empty, zero-terminated array for accels.
     * <p>
     * For the detailed_action_name, see g_action_parse_detailed_name() and g_action_print_detailed_name().
     *
     * @param detailedActionName A detailed action name, specifying an action and target to associate accelerators with.
     * @param accelerators       A list of accelerators in the format understood by gtk_accelerator_parse()
     */
    public void setKeyboardAccelerators(String detailedActionName, List<String> accelerators) {
        if (detailedActionName != null && accelerators != null) {
            String[] accels = new String[accelerators.size()];
            for (int i = 0; i < accels.length; i++) {
                accels[i] = accelerators.get(i);
            }
            library.gtk_application_set_accels_for_action(cReference, detailedActionName, accels);
        }
    }

    /**
     * Set this property to TRUE to register with the session manager.
     * <p>
     * This will make GTK track the session state (such as the GtkApplication:screensaver-active property).
     *
     * @param shouldRegister TRUE if the session manager should track the session state
     */
    public void shouldRegisterSession(boolean shouldRegister) {
        setProperty("register-session", cReference, shouldRegister);
    }

    /**
     * Removes an inhibitor that has been previously established.
     * <p>
     * See inhibit().
     * <p>
     * Inhibitors are also cleared when the application exits.
     *
     * @param cookie A cookie that was returned by gtk_application_inhibit()
     */
    public void uninhibit(InhibitCookie cookie) {
        if (cookie != null && cookie.getCookie() >= 0) {
            library.gtk_application_uninhibit(cReference, cookie.getCookie());
        }
    }

    public static class InhibitCookie {
        private final int cookie;

        public InhibitCookie(int cookie) {
            this.cookie = cookie;
        }

        public int getCookie() {
            return cookie;
        }
    }

    /**
     * Signals specific to GtkApplication
     */
    public static class Signals extends GtkWidget.Signals {
        /**
         * Emitted when the session manager is about to end the session.
         * <p>
         * This signal is only emitted if GtkApplication:register-session is TRUE. Applications can connect to this
         * signal and call gtk_application_inhibit() with GTK_APPLICATION_INHIBIT_LOGOUT to delay the end of the session
         * until state has been saved.
         */
        public static final Signals QUERY_END = new Signals("query-end");
        /**
         * Emitted when a GtkWindow is added to application through gtk_application_add_window().
         */
        public static final Signals WINDOW_ADDED = new Signals("window-added");
        /**
         * Emitted when a GtkWindow is removed from application.
         * <p>
         * This can happen as a side effect of the window being destroyed or explicitly through
         * gtk_application_remove_window().
         */
        public static final Signals WINDOW_REMOVED = new Signals("window-removed");

        public Signals(String s) {
            super(s);
        }
    }
}
