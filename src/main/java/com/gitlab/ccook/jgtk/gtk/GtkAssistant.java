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
package com.gitlab.ccook.jgtk.gtk;

import com.gitlab.ccook.jgtk.GListModel;
import com.gitlab.ccook.jgtk.GtkShortcutManager;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GDestroyNotify;
import com.gitlab.ccook.jgtk.callbacks.GtkAssistantPageFunc;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkAssistantPageType;
import com.gitlab.ccook.jgtk.interfaces.*;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkAssistant is used to represent a complex as a series of steps.
 * <p>
 * Each step consists of one or more pages. GtkAssistant guides the user through the pages, and controls the page flow
 * to collect the data needed for the operation.
 * <p>
 * GtkAssistant handles which buttons to show and to make sensitive based on page sequence knowledge and the
 * GtkAssistantPageType of each page in addition to state information like the completed and committed page statuses.
 * <p>
 * If you have a case that doesn't quite fit in GtkAssistants way of handling buttons, you can use the
 * GTK_ASSISTANT_PAGE_CUSTOM page type and handle buttons yourself.
 * <p>
 * GtkAssistant maintains a GtkAssistantPage object for each added child, which holds additional per-child properties.
 * You obtain the GtkAssistantPage for a child with gtk_assistant_get_page().
 *
 * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
 */
@Deprecated
@SuppressWarnings({"unchecked", "DeprecatedIsStillUsed"})
public class GtkAssistant extends GtkWindow implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkNative, GtkRoot, GtkShortcutManager {

    private static final GtkAssistantLibrary library = new GtkAssistantLibrary();

    public GtkAssistant(Pointer p) {
        super(p);
    }

    /**
     * Creates a new GtkAssistant.
     *
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public GtkAssistant() {
        super(library.gtk_assistant_new());
    }

    /**
     * Adds a widget to the action area of a GtkAssistant.
     *
     * @param w A GtkWidget
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void addActionWidget(GtkWidget w) {
        if (w != null) {
            library.gtk_assistant_add_action_widget(getCReference(), w.getCReference());
        }
    }

    /**
     * Appends a page to the assistant.
     *
     * @param page A GtkWidget
     * @return The index (starting at 0) of the inserted page, if defined
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<Integer> appendPage(GtkWidget page) {
        if (page != null) {
            int num = library.gtk_assistant_append_page(getCReference(), page.getCReference());
            if (num >= 0) {
                return new Option<>(num);
            }
        }
        return Option.NONE;
    }

    /**
     * Erases the visited page history.
     * <p>
     * GTK will then hide the back button on the current page, and removes the cancel button from subsequent pages.
     * <p>
     * Use this when the information provided up to the current page is hereafter deemed permanent and cannot be
     * modified or undone. For example, showing a progress page to track a long-running, un-reversible operation
     * after the user has clicked apply on a confirmation page.
     *
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void commit() {
        library.gtk_assistant_commit(getCReference());
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
     * Gets the title for page.
     *
     * @param index The index of a page in the assistant, or -1 to get the last page.
     * @return The title for page.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<String> getPageTitle(int index) {
        Option<GtkAssistantPage> assistantPage = getAssistantPage(index);
        if (assistantPage.isDefined()) {
            return new Option<>(library.gtk_assistant_get_page_title(getCReference(), assistantPage.get().getChild().get().getCReference()));
        }
        return Option.NONE;
    }

    /**
     * Returns the GtkAssistantPage object for child.
     *
     * @param index The index of a page in the assistant, or -1 to get the last page.
     * @return The GtkAssistantPage for child.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<GtkAssistantPage> getAssistantPage(int index) {
        Option<GtkWidget> page = getPage(index);
        if (page.isDefined()) {
            Option<Pointer> p = new Option<>(library.gtk_assistant_get_page(getCReference(), page.get().getCReference()));
            if (p.isDefined()) {
                return new Option<>(new GtkAssistantPage(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Returns the child widget contained in page number page_num.
     *
     * @param index The index of a page in the assistant, or -1 to get the last page.
     * @return The child widget, or NONE if page_num is out of bounds.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<GtkWidget> getPage(int index) {
        Option<Pointer> p = new Option<>(library.gtk_assistant_get_nth_page(getCReference(), index));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Gets the page type of page.
     *
     * @param index The index of a page in the assistant, or -1 to get the last page.
     * @return The page type of index.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<GtkAssistantPageType> getPageType(int index) {
        Option<GtkAssistantPage> assistantPage = getAssistantPage(index);
        if (assistantPage.isDefined()) {
            return new Option<>(GtkAssistantPageType.getPageTypeForCValue(library.gtk_assistant_get_page_type(getCReference(), assistantPage.get().getChild().get().getCReference())));
        }
        return Option.NONE;
    }

    /**
     * Gets a list model of the assistant pages.
     *
     * @return A list model of the pages.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public GListModel<GtkAssistantPage> getPages() {
        return new GenericGListModel<>(GtkAssistantPage.class, library.gtk_assistant_get_pages(getCReference()));
    }

    /**
     * Inserts a page in the assistant at a given position.
     *
     * @param page     A GtkWidget
     * @param position The index (starting at 0) at which to insert the page, or -1 to append the page to the assistant.
     * @return The index (starting from 0) of the inserted page, if defined
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<Integer> insertPage(GtkWidget page, int position) {
        if (page != null) {
            int val = library.gtk_assistant_insert_page(getCReference(), page.getCReference(), position);
            if (val >= 0) {
                return new Option<>(val);
            }
        }
        return Option.NONE;
    }

    /**
     * Gets whether page is complete.
     *
     * @param index The index of a page in the assistant, or -1 to get the last page.
     * @return TRUE if page is complete.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public boolean isPageComplete(int index) {
        Option<Pointer> p = new Option<>(library.gtk_assistant_get_nth_page(getCReference(), index));
        if (p.isDefined()) {
            return library.gtk_assistant_get_page_complete(getCReference(), p.get());
        }
        return false;
    }

    /**
     * Navigate to the next page.
     * <p>
     * It is a programming error to call this function when there is no next page.
     * <p>
     * This function is for use when creating pages of the GTK_ASSISTANT_PAGE_CUSTOM type.
     *
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void nextPage() {
        Option<Integer> currentPageNumber = getCurrentPageNumber();
        if (currentPageNumber.isDefined()) {
            if (currentPageNumber.get() + 1 < size()) {
                library.gtk_assistant_next_page(getCReference());
            }
        }
    }

    /**
     * Returns the page number of the current page.
     *
     * @return The index (starting from 0) of the current page in the assistant, or -1 if the assistant has no
     *         pages, or no current page.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<Integer> getCurrentPageNumber() {
        int num = library.gtk_assistant_get_current_page(getCReference());
        if (num >= 0) {
            return new Option<>(num);
        }
        return Option.NONE;
    }

    /**
     * Returns the number of pages in the assistant.
     *
     * @return The number of pages in the assistant.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public int size() {
        return library.gtk_assistant_get_n_pages(getCReference());
    }

    /**
     * Switches the page to pageNum.
     * <p>
     * Note that this will only be necessary in custom buttons, as the assistant flow can be set with
     * gtk_assistant_set_forward_page_func().
     *
     * @param pageNum Index of the page to switch to, starting from 0. If negative, the last page will be used.
     *                If greater than the number of pages in the assistant, nothing will be done.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setCurrentPageNumber(int pageNum) {
        library.gtk_assistant_set_current_page(getCReference(), pageNum);
    }

    /**
     * Prepends a page to the assistant.
     *
     * @param page A GtkWidget
     * @return The index (starting at 0) of the inserted page.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public Option<Integer> prependPage(GtkWidget page) {
        if (page != null) {
            int num = library.gtk_assistant_prepend_page(getCReference(), page.getCReference());
            if (num >= 0) {
                return new Option<>(num);
            }
        }
        return Option.NONE;
    }

    /**
     * Navigate to the previous visited page.
     * <p>
     * It is a programming error to call this function when no previous page is available.
     * <p>
     * This function is for use when creating pages of the GTK_ASSISTANT_PAGE_CUSTOM type.
     *
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void previousPage() {
        Option<Integer> currentPage = getCurrentPageNumber();
        if (currentPage.isDefined()) {
            if (currentPage.get() - 1 >= 0) {
                library.gtk_assistant_previous_page(getCReference());
            }
        }
    }

    /**
     * Removes a widget from the action area of a GtkAssistant.
     *
     * @param widgetToRemove A GtkWidget
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void removeActionWidget(GtkWidget widgetToRemove) {
        if (widgetToRemove != null) {
            library.gtk_assistant_remove_action_widget(getCReference(), widgetToRemove.getCReference());
        }
    }

    /**
     * Removes the position's page from assistant.
     *
     * @param position The index of a page in the assistant, or -1 to remove the last page.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void removePage(int position) {
        library.gtk_assistant_remove_page(getCReference(), position);
    }

    /**
     * Sets the page forwarding function to be page_func.
     * <p>
     * This function will be used to determine what will be the next page when the user presses the forward button.
     * Setting page_func to NULL will make the assistant to use the default forward function, which just goes to the
     * next visible page.
     *
     * @param nextPageFunction The GtkAssistantPageFunc, or NULL to use the default one.
     *                         <p>
     *                         The argument can be NULL.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setNextPageFunction(GtkAssistantPageFunc nextPageFunction) {
        library.gtk_assistant_set_forward_page_func(getCReference(), nextPageFunction, Pointer.NULL, null);
    }

    /**
     * Sets whether page contents are complete.
     * <p>
     * This will make assistant update the buttons state to be able to continue the task.
     *
     * @param index      The index of a page in the assistant, or -1 to get the last page.
     * @param isComplete The completeness status of the page.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setPageComplete(int index, boolean isComplete) {
        Option<GtkAssistantPage> page = getAssistantPage(index);
        if (page.isDefined()) {
            library.gtk_assistant_set_page_complete(getCReference(), page.get().getChild().get().getCReference(), isComplete);
        }
    }

    /**
     * Sets a title for page.
     * <p>
     * The title is displayed in the header area of the assistant when page is the current page.
     *
     * @param title The new title for page.
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setPageTitle(int index, String title) {
        Option<GtkAssistantPage> page = getAssistantPage(index);
        if (page.isDefined()) {
            library.gtk_assistant_set_page_title(getCReference(), page.get().getChild().get().getCReference(), title);
        }
    }

    /**
     * Sets the page type for page.
     * <p>
     * The page type determines the page behavior in the assistant.
     *
     * @param index The index of a page in the assistant, or -1 to get the last page.
     * @param type  The new type for page. Type: GtkAssistantPageType
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void setPageType(int index, GtkAssistantPageType type) {
        Option<GtkAssistantPage> page = getAssistantPage(index);
        if (page.isDefined() && type != null) {
            library.gtk_assistant_set_page_type(getCReference(), page.get().getChild().get().getCReference(), GtkAssistantPageType.getCValueForType(type));
        }
    }

    /**
     * Forces assistant to recompute the buttons state.
     * <p>
     * GTK automatically takes care of this in most situations, e.g. when the user goes to a different page, or when
     * the visibility or completeness of a page changes.
     * <p>
     * One situation where it can be necessary to call this function is when changing a value on the current page
     * affects the future page flow of the assistant.
     *
     * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
     */
    public void updateButtonState() {
        library.gtk_assistant_update_buttons_state(getCReference());
    }

    @SuppressWarnings("CanBeFinal")
    public static class Signals extends GtkWindow.Signals {
        /**
         * Emitted when the apply button is clicked.
         * <p>
         * The default behavior of the GtkAssistant is to switch to the page after the current page, unless the current
         * page is the last one.
         * <p>
         * A handler for the ::apply signal should carry out the actions for which the wizard has collected data. If the
         * action takes a long time to complete, you might consider putting a page of type GTK_ASSISTANT_PAGE_PROGRESS
         * after the confirmation page and handle this operation within the GtkAssistant::prepare signal of the progress
         * page.
         *
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public static Signals APPLY = new Signals("apply");

        /**
         * Emitted when then the cancel button is clicked.
         *
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public static Signals CANCEL = new Signals("cancel");

        /**
         * Emitted either when the close button of a summary page is clicked, or when the apply button in the last page
         * in the flow (of type GTK_ASSISTANT_PAGE_CONFIRM) is clicked.
         *
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public static Signals CLOSE = new Signals("close");

        /**
         * The action signal for the Escape binding.
         *
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public static Signals ESCAPE = new Signals("escape");

        /**
         * Emitted when a new page is set as the assistant's current page, before making the new page visible.
         * <p>
         * A handler for this signal can do any preparations which are necessary before showing page.
         *
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public static Signals PREPARE = new Signals("prepare");

        protected Signals(String cValue) {
            super(cValue);
        }
    }

    protected static class GtkAssistantLibrary extends GtkWindowLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a widget to the action area of a GtkAssistant.
         *
         * @param assistant self
         * @param child     A GtkWidget
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_add_action_widget(Pointer assistant, Pointer child);

        /**
         * Appends a page to the assistant.
         *
         * @param assistant self
         * @param page      A GtkWidget
         * @return The index (starting at 0) of the inserted page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native int gtk_assistant_append_page(Pointer assistant, Pointer page);

        /**
         * Erases the visited page history.
         * <p>
         * GTK will then hide the back button on the current page, and removes the cancel button from subsequent pages.
         * <p>
         * Use this when the information provided up to the current page is hereafter deemed permanent and cannot be
         * modified or undone. For example, showing a progress page to track a long-running, un-reversible operation
         * after the user has clicked apply on a confirmation page.
         *
         * @param assistant self
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_commit(Pointer assistant);

        /**
         * Returns the page number of the current page.
         *
         * @param assistant self
         * @return The index (starting from 0) of the current page in the assistant, or -1 if the assistant has no
         *         pages, or no current page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native int gtk_assistant_get_current_page(Pointer assistant);

        /**
         * Returns the number of pages in the assistant.
         *
         * @param assistant self
         * @return The number of pages in the assistant.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native int gtk_assistant_get_n_pages(Pointer assistant);

        /**
         * Returns the child widget contained in page number page_num.
         *
         * @param assistant self
         * @param page_num  The index of a page in the assistant, or -1 to get the last page.
         * @return The child widget, or NULL if page_num is out of bounds.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_assistant_get_nth_page(Pointer assistant, int page_num);

        /**
         * Returns the GtkAssistantPage object for child.
         *
         * @param assistant self
         * @param child     A child of assistant.
         * @return The GtkAssistantPage for child.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_assistant_get_page(Pointer assistant, Pointer child);

        /**
         * Gets whether page is complete.
         *
         * @param assistant self
         * @param page      A page of assistant.
         * @return TRUE if page is complete.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native boolean gtk_assistant_get_page_complete(Pointer assistant, Pointer page);

        /**
         * Gets the title for page.
         *
         * @param assistant self
         * @param page      A page of assistant.
         * @return The title for page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native String gtk_assistant_get_page_title(Pointer assistant, Pointer page);

        /**
         * Gets the page type of page.
         *
         * @param assistant self
         * @param page      A page of assistant.
         * @return The page type of page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native int gtk_assistant_get_page_type(Pointer assistant, Pointer page);

        /**
         * Gets a list model of the assistant pages.
         *
         * @param assistant self
         * @return A list model of the pages. Type: A list model of GtkAssistantPage
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_assistant_get_pages(Pointer assistant);

        /**
         * Inserts a page in the assistant at a given position.
         *
         * @param assistant self
         * @param page      A GtkWidget
         * @param position  The index (starting at 0) at which to insert the page, or -1 to append the page to the
         *                  assistant.
         * @return The index (starting from 0) of the inserted page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native int gtk_assistant_insert_page(Pointer assistant, Pointer page, int position);

        /**
         * Creates a new GtkAssistant.
         *
         * @return A newly created GtkAssistant
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native Pointer gtk_assistant_new();

        /**
         * Navigate to the next page.
         * <p>
         * It is a programming error to call this function when there is no next page.
         * <p>
         * This function is for use when creating pages of the GTK_ASSISTANT_PAGE_CUSTOM type.
         *
         * @param assistant self
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_next_page(Pointer assistant);

        /**
         * Prepends a page to the assistant.
         *
         * @param assistant self
         * @param page      A GtkWidget
         * @return The index (starting at 0) of the inserted page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native int gtk_assistant_prepend_page(Pointer assistant, Pointer page);

        /**
         * Navigate to the previous visited page.
         * <p>
         * It is a programming error to call this function when no previous page is available.
         * <p>
         * This function is for use when creating pages of the GTK_ASSISTANT_PAGE_CUSTOM type.
         *
         * @param assistant self
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_previous_page(Pointer assistant);

        /**
         * Removes a widget from the action area of a GtkAssistant.
         *
         * @param assistant self
         * @param child     A GtkWidget
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_remove_action_widget(Pointer assistant, Pointer child);

        /**
         * Removes the page_num's page from assistant.
         *
         * @param assistant self
         * @param page_num  The index of a page in the assistant, or -1 to remove the last page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_remove_page(Pointer assistant, int page_num);

        /**
         * Switches the page to page_num.
         * <p>
         * Note that this will only be necessary in custom buttons, as the assistant flow can be set with
         * gtk_assistant_set_forward_page_func().
         *
         * @param assistant self
         * @param page_num  Index of the page to switch to, starting from 0. If negative, the last page will be used.
         *                  If greater than the number of pages in the assistant, nothing will be done.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_set_current_page(Pointer assistant, int page_num);

        /**
         * Sets the page forwarding function to be page_func.
         * <p>
         * This function will be used to determine what will be the next page when the user presses the forward button.
         * Setting page_func to NULL will make the assistant to use the default forward function, which just goes to
         * the next visible page.
         *
         * @param assistant self
         * @param page_func The GtkAssistantPageFunc, or NULL to use the default one.
         *                  <p>
         *                  The argument can be NULL.
         * @param data      User data for page_func.
         *                  <p>
         *                  The argument can be NULL.
         * @param destroy   Destroy notifier for data. Type: GDestroyNotify
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_set_forward_page_func(Pointer assistant, GtkAssistantPageFunc page_func, Pointer data, GDestroyNotify destroy);

        /**
         * Sets whether page contents are complete.
         * <p>
         * This will make assistant update the buttons state to be able to continue the task.
         *
         * @param assistant self
         * @param page      A page of assistant.
         * @param complete  The completeness status of the page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_set_page_complete(Pointer assistant, Pointer page, boolean complete);

        /**
         * Sets a title for page.
         * <p>
         * The title is displayed in the header area of the assistant when page is the current page.
         *
         * @param assistant self
         * @param page      A page of assistant.
         * @param title     The new title for page.
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_set_page_title(Pointer assistant, Pointer page, String title);

        /**
         * Sets the page type for page.
         * <p>
         * The page type determines the page behavior in the assistant.
         *
         * @param assistant self
         * @param page      A page of assistant.
         * @param type      The new type for page. Type: GtkAssistantPageType
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_set_page_type(Pointer assistant, Pointer page, int type);

        /**
         * Forces assistant to recompute the buttons state.
         * <p>
         * GTK automatically takes care of this in most situations, e.g. when the user goes to a different page, or when
         * the visibility or completeness of a page changes.
         * <p>
         * One situation where it can be necessary to call this function is when changing a value on the current page
         * affects the future page flow of the assistant.
         *
         * @param assistant self
         * @deprecated Deprecated since: 4.10. This widget will be removed in GTK 5
         */
        public native void gtk_assistant_update_buttons_state(Pointer assistant);
    }

}
