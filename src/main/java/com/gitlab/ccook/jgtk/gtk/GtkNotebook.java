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

import com.gitlab.ccook.jgtk.GListModelSet;
import com.gitlab.ccook.jgtk.GtkNotebookPage;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.enums.GtkPackType;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.List;

/**
 * GtkNotebook is a container whose children are pages switched between using tabs.
 * <p>
 * There are many configuration options for GtkNotebook. Among other things, you can choose on which edge the tabs
 * appear (see gtk_notebook_set_tab_pos()), whether, if there are too many tabs to fit the notebook should be made
 * bigger or scrolling arrows added (see gtk_notebook_set_scrollable()), and whether there will be a popup menu
 * allowing the users to switch pages. (see gtk_notebook_popup_enable()).
 */
@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public class GtkNotebook extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {
    private static final GtkNotebookLibrary library = new GtkNotebookLibrary();

    public GtkNotebook(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new GtkNotebook widget with no pages.
     */
    public GtkNotebook() {
        super(library.gtk_notebook_new());
    }

    /**
     * Appends a page to notebook.
     *
     * @param child The GtkWidget to use as the contents of the page.
     * @return The index (starting from 0) of the appended page in the notebook, or NONE if function fails.
     */
    public Option<Integer> appendPage(GtkWidget child) {
        return appendPage(child, null);
    }

    /**
     * Appends a page to notebook.
     *
     * @param child The GtkWidget to use as the contents of the page.
     * @param label The GtkWidget to be used as the label for the page, or NULL to use the default label, "page N"
     * @return The index (starting from 0) of the appended page in the notebook, or NONE if function fails.
     */
    public Option<Integer> appendPage(GtkWidget child, GtkWidget label) {
        if (child != null) {
            int index = library.gtk_notebook_append_page(getCReference(), child.getCReference(), pointerOrNull(label));
            if (index >= 0) {
                return new Option<>(index);
            }
        }
        return Option.NONE;
    }

    /**
     * Appends a page to notebook, specifying the widget to use as the label in the popup menu.
     *
     * @param child     The GtkWidget to use as the contents of the page.
     * @param tabLabel  The GtkWidget to be used as the label for the page, or NULL to use the default label, "page N"
     *                  <p>
     *                  The argument can be NULL.
     * @param menuLabel The widget to use as a label for the page-switch menu, if that is enabled. If NULL, and
     *                  tab_label is a GtkLabel or NULL, then the menu label will be a newly created label with the
     *                  same text as tab_label; if tab_label is not a GtkLabel, menu_label must be specified if the
     *                  page-switch menu is to be used.
     *                  <p>
     *                  The argument can be NULL.
     * @return The index (starting from 0) of the appended page in the notebook, or NONE if function fails.
     */
    public Option<Integer> appendPageMenu(GtkWidget child, GtkWidget tabLabel, GtkWidget menuLabel) {
        if (child != null) {
            int index = library.gtk_notebook_append_page_menu(getCReference(), child.getCReference(), pointerOrNull(tabLabel), pointerOrNull(menuLabel));
            if (index >= 0) {
                return new Option<>(index);
            }
        }
        return Option.NONE;
    }

    /**
     * Returns whether the tab label area has arrows for scrolling.
     *
     * @return TRUE if arrows for scrolling are present.
     */
    public boolean areTabsScrollable() {
        return library.gtk_notebook_get_scrollable(getCReference());
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
     * Removes the child from the notebook.
     * <p>
     * This function is very similar to gtk_notebook_remove_page(), but additionally informs the notebook that the
     * removal is happening as part of a tab DND operation, which should not be cancelled.
     *
     * @param child child tab to detach
     */
    public void detachTab(GtkWidget child) {
        if (child != null) {
            library.gtk_notebook_detach_tab(getCReference(), child.getCReference());
        }
    }

    /**
     * Disables the popup menu.
     */
    public void disablePopupMenu() {
        library.gtk_notebook_popup_disable(getCReference());
    }

    /**
     * Returns whether the tabs of the notebook are shown.
     *
     * @return TRUE if the tabs are shown.
     */
    public boolean doesShowTabs() {
        return library.gtk_notebook_get_show_tabs(getCReference());
    }

    /**
     * Enables the popup menu.
     * <p>
     * If the user clicks with the right mouse button on the tab labels, a menu with all the pages will be popped up.
     */
    public void enablePopupMenu() {
        library.gtk_notebook_popup_enable(getCReference());
    }

    /**
     * Gets one of the action widgets.
     *
     * @param type Pack type of the action widget to receive.
     * @return The action widget with the given pack_type or NONE when this action widget has not been set.
     */
    public Option<GtkWidget> getActionWidget(GtkPackType type) {
        if (type != null) {
            Option<Pointer> p = new Option<>(library.gtk_notebook_get_action_widget(getCReference(), GtkPackType.getCValueFromType(type)));
            if (p.isDefined()) {
                return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
            }
        }
        return Option.NONE;
    }

    /**
     * @return Returns all the pages associated with this GtkNotebook
     */
    public List<GtkWidget> getAllPages() {
        List<GtkWidget> list = new ArrayList<>();
        for (int i = 0; i < getNumberOfPages(); i++) {
            Option<GtkWidget> w = getPageAtIndex(i);
            if (w.isDefined()) {
                list.add(w.get());
            }
        }
        return list;
    }

    /**
     * Gets the number of pages in a notebook.
     *
     * @return The number of pages in the notebook.
     */
    public int getNumberOfPages() {
        return library.gtk_notebook_get_n_pages(getCReference());
    }

    /**
     * Returns the child widget contained in page number 'index'
     *
     * @param index The index of a page in the notebook, or -1 to get the last page.
     * @return The child widget, or NONE if page_num is out of bounds.
     */
    public Option<GtkWidget> getPageAtIndex(int index) {
        if (index >= -1) {
            Option<Pointer> p = new Option<>(library.gtk_notebook_get_nth_page(getCReference(), index));
            if (p.isDefined()) {
                return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
            }
        }
        return Option.NONE;
    }

    /**
     * Returns the page number of the current page.
     *
     * @return The index (starting from 0) of the current page in the notebook.
     *         If the notebook has no pages, then NONE will be returned.
     */
    public Option<Integer> getCurrentPageIndex() {
        int index = library.gtk_notebook_get_current_page(getCReference());
        if (index >= 0) {
            return new Option<>(index);
        }
        return Option.NONE;
    }

    /**
     * Gets the current group name for notebook.
     *
     * @return The group name, or NONE if none is set.
     */
    public Option<String> getGroupName() {
        return new Option<>(library.gtk_notebook_get_group_name(getCReference()));
    }

    /**
     * Sets a group name for notebook.
     * <p>
     * Notebooks with the same name will be able to exchange tabs via drag and drop. A notebook with a NULL group name
     * will not be able to exchange tabs with any other notebook.
     *
     * @param groupName The name of the notebook group, or NULL to unset it.
     *                  <p>
     *                  The argument can be NULL.
     */
    public void setGroupName(String groupName) {
        library.gtk_notebook_set_group_name(getCReference(), groupName);
    }

    /**
     * Retrieves the menu label widget of the page containing child.
     *
     * @param child A widget contained in a page of notebook.
     * @return The menu label, or NONE if the notebook page does not have a menu label other than the default
     *         (the tab label).
     */
    public Option<GtkWidget> getMenuLabel(GtkWidget child) {
        if (child != null) {
            Option<Pointer> p = new Option<>(library.gtk_notebook_get_menu_label(getCReference(), child.getCReference()));
            if (p.isDefined()) {
                return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
            }
        }
        return Option.NONE;
    }

    /**
     * Retrieves the text of the menu label for the page containing child.
     *
     * @param child The child widget of a page of the notebook.
     * @return The text of the tab label, or NULL if the widget does not have a menu label other than the default menu
     *         label, or the menu label widget is not a GtkLabel. The string is owned by the widget and must not be
     *         freed.
     */
    public Option<String> getMenuText(GtkWidget child) {
        if (child != null) {
            return new Option<>(library.gtk_notebook_get_menu_label_text(getCReference(), child.getCReference()));
        }
        return Option.NONE;
    }

    /**
     * Returns a GListModel that contains the pages of the notebook.
     * <p>
     * This can be used to keep an up-to-date view. The model also implements GtkSelectionModel and can be used to track
     * and modify the visible page.
     *
     * @return A GListModel for the notebook's children.
     */
    public GListModelSet<GtkNotebookPage> getNotebookPages() {
        Pointer p = library.gtk_notebook_get_pages(cReference);
        return new GListModelSet<>(new GenericGListModel<>(GtkNotebookPage.class, p));
    }

    /**
     * Finds the index of the page which contains the given child widget.
     *
     * @param child A GtkWidget
     * @return The index of the page containing child, or NONE if child is not in the notebook.
     */
    public Option<Integer> getPageIndex(GtkWidget child) {
        if (child != null) {
            int index = library.gtk_notebook_page_num(getCReference(), child.getCReference());
            if (index >= 0) {
                return new Option<>(index);
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the edge at which the tabs are drawn.
     *
     * @return The edge at which the tabs are drawn.
     */
    public GtkPositionType getTabDrawPosition() {
        return GtkPositionType.getTypeFromCValue(library.gtk_notebook_get_tab_pos(getCReference()));
    }

    /**
     * Sets the edge at which the tabs are drawn.
     *
     * @param position The edge to draw where the tabs are drawn.
     */
    public void setTabDrawPosition(GtkPositionType position) {
        if (position != null) {
            library.gtk_notebook_set_tab_pos(getCReference(), GtkPositionType.getCValueFromType(position));
        }
    }

    /**
     * Returns the tab label widget for the page child.
     * <p>
     * NONE is returned if child is not in notebook or if no tab label has specifically been set for child.
     *
     * @param child The page.
     * @return The tab label, if defined
     */
    public Option<GtkWidget> getTabLabel(GtkWidget child) {
        if (child != null) {
            Option<Pointer> p = new Option<>(library.gtk_notebook_get_tab_label(getCReference(), child.getCReference()));
            if (p.isDefined()) {
                return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
            }
        }
        return Option.NONE;
    }

    /**
     * Retrieves the text of the tab label for the page containing child.
     *
     * @param child A widget contained in a page of notebook.
     * @return The text of the tab label, or NONE if the tab label widget is not a GtkLabel.
     *         The string is owned by the widget and must not be freed.
     */
    public Option<String> getTabLabelText(GtkWidget child) {
        if (child != null) {
            return new Option<>(library.gtk_notebook_get_tab_label_text(getCReference(), child.getCReference()));
        }
        return Option.NONE;
    }

    /**
     * Switches to the page number page_num.
     * <p>
     * Note that due to historical reasons, GtkNotebook refuses to switch to a page unless the child widget is visible.
     * Therefore, it is recommended to show child widgets before adding them to a notebook.
     *
     * @param index Index of the page to switch to, starting from 0. If negative, the last page will be used.
     *              If greater than the number of pages in the notebook, nothing will be done.
     */
    public void gotoPage(int index) {
        library.gtk_notebook_set_current_page(getCReference(), index);
    }

    /**
     * Returns whether a bevel will be drawn around the notebook pages.
     *
     * @return TRUE if the bevel is drawn.
     */
    public boolean hasBevel() {
        return library.gtk_notebook_get_show_border(getCReference());
    }

    /**
     * Insert a page into notebook at the given position.
     *
     * @param child    The GtkWidget to use as the contents of the page.
     * @param tabLabel The GtkWidget to be used as the label for the page, or NULL to use the default label, "page N"
     *                 <p>
     *                 The argument can be NULL.
     * @param position The index (starting at 0) at which to insert the page, or -1 to append the page after all other
     *                 pages.
     * @return The index (starting from 0) of the inserted page in the notebook, or NONE if function fails.
     */
    public Option<Integer> insertPage(GtkWidget child, GtkWidget tabLabel, int position) {
        if (child != null) {
            int index = library.gtk_notebook_insert_page(getCReference(), child.getCReference(), pointerOrNull(tabLabel), Math.max(-1, position));
            if (index >= 0) {
                return new Option<>(index);
            }
        }
        return Option.NONE;
    }

    /**
     * Insert a page into notebook at the given position, specifying the widget to use as the label in the popup menu.
     *
     * @param child     The GtkWidget to use as the contents of the page.
     * @param tabLabel  The GtkWidget to be used as the label for the page, or NULL to use the default label, "page N"
     *                  <p>
     *                  The argument can be NULL.
     * @param menuLabel The widget to use as a label for the page-switch menu, if that is enabled. If NULL, and
     *                  tab_label is a GtkLabel or NULL, then the menu label will be a newly created label with the
     *                  same text as tab_label; if tab_label is not a GtkLabel, menu_label must be specified if the
     *                  page-switch menu is to be used.
     *                  <p>
     *                  The argument can be NULL.
     * @param position  The index (starting at 0) at which to insert the page, or -1 to append the page after all
     *                  other pages.
     * @return The index (starting from 0) of the inserted page in the notebook, or NONE if failed
     */
    public Option<Integer> insertPageMenu(GtkWidget child, GtkWidget tabLabel, GtkWidget menuLabel, int position) {
        if (child != null) {
            int index = library.gtk_notebook_insert_page_menu(getCReference(), child.getCReference(), pointerOrNull(tabLabel), pointerOrNull(menuLabel), Math.max(-1, position));
            if (index >= 0) {
                return new Option<>(index);
            }
        }
        return Option.NONE;
    }

    /**
     * Returns whether the tab contents can be detached from notebook.
     *
     * @param w A child GtkWidget
     * @return TRUE if the tab is detachable.
     */
    public boolean isTabDetachable(GtkWidget w) {
        if (w != null) {
            return library.gtk_notebook_get_tab_detachable(getCReference(), w.getCReference());
        }
        return false;
    }

    /**
     * Gets whether the tab can be reordered via drag and drop.
     *
     * @param child A child GtkWidget
     * @return TRUE if the tab is reorder-able.
     */
    public boolean isTabReorderable(GtkWidget child) {
        if (child != null) {
            return library.gtk_notebook_get_tab_reorderable(getCReference(), child.getCReference());
        }
        return false;
    }

    /**
     * Switches to the next page.
     * <p>
     * Nothing happens if the current page is the last page.
     */
    public void nextPage() {
        library.gtk_notebook_next_page(getCReference());
    }

    /**
     * Prepends a page to notebook.
     *
     * @param child    The GtkWidget to use as the contents of the page.
     * @param tabLabel The GtkWidget to be used as the label for the page, or NULL to use the default label, "page N"
     *                 <p>
     *                 The argument can be NULL.
     * @return The index (starting from 0) of the prepended page in the notebook, or NONE if the function fails
     */
    public Option<Integer> prependPage(GtkWidget child, GtkWidget tabLabel) {
        if (child != null) {
            int index;
            if (tabLabel != null) {
                index = library.gtk_notebook_prepend_page(getCReference(), child.getCReference(), tabLabel.getCReference());
            } else {
                index = library.gtk_notebook_prepend_page(getCReference(), child.getCReference(), Pointer.NULL);
            }
            if (index >= 0) {
                return new Option<>(index);
            }
        }
        return Option.NONE;
    }

    /**
     * Prepends a page to notebook, specifying the widget to use as the label in the popup menu.
     *
     * @param child     The GtkWidget to use as the contents of the page.
     * @param tabLabel  The GtkWidget to be used as the label for the page, or NULL to use the default label, "page N"
     *                  <p>
     *                  The argument can be NULL.
     * @param menuLabel The widget to use as a label for the page-switch menu, if that is enabled.
     *                  If NULL, and tab_label is a GtkLabel or NULL, then the menu label will be a newly created label
     *                  with the same text as tab_label; if tab_label is not a GtkLabel, menu_label must be specified
     *                  if the page-switch menu is to be used.
     *                  <p>
     *                  The argument can be NULL.
     * @return The index (starting from 0) of the prepended page in the notebook, or NONE if function fails.
     */
    public Option<Integer> prependPageMenu(GtkWidget child, GtkWidget tabLabel, GtkWidget menuLabel) {
        if (child != null) {
            int index;
            index = library.gtk_notebook_prepend_page_menu(getCReference(), child.getCReference(), pointerOrNull(tabLabel), pointerOrNull(menuLabel));
            if (index >= 0) {
                return new Option<>(index);
            }
        }
        return Option.NONE;
    }

    /**
     * Switches to the previous page.
     * <p>
     * Nothing happens if the current page is the first page.
     */
    public void previousPage() {
        library.gtk_notebook_prev_page(getCReference());
    }

    /**
     * Removes a page from the notebook given its index in the notebook.
     *
     * @param index The index of a notebook page, starting from 0. If -1, the last page will be removed.
     */
    public void removePage(int index) {
        if (index >= -1) {
            library.gtk_notebook_remove_page(getCReference(), index);
        }
    }

    /**
     * Reorders the page containing child, so that it appears in position newIndex.
     * <p>
     * If position is greater than or equal to the number of children in the list or negative, child will be moved to
     * the end of the list.
     *
     * @param child    The child to move.
     * @param newIndex The new position, or -1 to move to the end.
     */
    public void reorderChild(GtkWidget child, int newIndex) {
        if (child != null && newIndex >= -1) {
            library.gtk_notebook_reorder_child(getCReference(), child.getCReference(), newIndex);
        }
    }

    /**
     * Sets widget as one of the action widgets.
     * <p>
     * Depending on the pack type the widget will be placed before or after the tabs. You can use a GtkBox if you need
     * to pack more than one widget on the same side.
     *
     * @param widget   A GtkWidget
     * @param packType Pack type of the action widget.
     */
    public void setActionWidget(GtkWidget widget, GtkPackType packType) {
        if (widget != null && packType != null) {
            library.gtk_notebook_set_action_widget(getCReference(), widget.getCReference(), GtkPackType.getCValueFromType(packType));
        }
    }

    /**
     * Sets whether a bevel will be drawn around the notebook pages.
     * <p>
     * This only has a visual effect when the tabs are not shown.
     *
     * @param hasBevel TRUE if a bevel should be drawn around the notebook.
     */
    public void setBevel(boolean hasBevel) {
        library.gtk_notebook_set_show_border(getCReference(), hasBevel);
    }

    /**
     * Changes the menu label for the page containing child.
     *
     * @param child     The child widget.
     * @param menuLabel The menu label, or NULL for default.
     *                  <p>
     *                  The argument can be NULL.
     */
    public void setMenuLabel(GtkWidget child, GtkWidget menuLabel) {
        if (child != null) {
            library.gtk_notebook_set_menu_label(getCReference(), child.getCReference(), pointerOrNull(menuLabel));
        }
    }

    /**
     * Creates a new label and sets it as the menu label of child.
     *
     * @param child The child widget.
     * @param text  The label text.
     */
    public void setMenuLabelText(GtkWidget child, String text) {
        if (child != null) {
            library.gtk_notebook_set_menu_label_text(getCReference(), child.getCReference(), text);
        }
    }

    /**
     * Sets whether the tab can be detached from notebook to another notebook or widget.
     * <p>
     * Note that two notebooks must share a common group identifier (see gtk_notebook_set_group_name()) to allow
     * automatic tabs interchange between them.
     * <p>
     * If you want a widget to interact with a notebook through DnD (i.e.: accept dragged tabs from it) it must be set
     * as a drop destination and accept the target "GTK_NOTEBOOK_TAB". The notebook will fill the selection with a
     * GtkWidget** pointing to the child widget that corresponds to the dropped tab.
     * <p>
     * Note that you should use gtk_notebook_detach_tab() instead of gtk_notebook_remove_page() if you want to remove
     * the tab from the source notebook as part of accepting a drop. Otherwise, the source notebook will think that the
     * dragged tab was removed from underneath the ongoing drag operation, and will initiate a drag cancel animation.
     *
     * @param tab          A child GtkWidget
     * @param isDetachable Whether the tab is detachable
     */
    public void setTabDetachable(GtkWidget tab, boolean isDetachable) {
        if (tab != null) {
            library.gtk_notebook_set_tab_detachable(getCReference(), tab.getCReference(), isDetachable);
        }
    }

    /**
     * Changes the tab label for child.
     * <p>
     * If NULL is specified for tab_label, then the page will have the label "page N".
     *
     * @param child The page.
     * @param label The tab label widget to use, or NULL for default tab label.
     *              <p>
     *              The argument can be NULL.
     */
    public void setTabLabel(GtkWidget child, GtkWidget label) {
        if (child != null) {
            library.gtk_notebook_set_tab_label(getCReference(), child.getCReference(), pointerOrNull(label));
        }
    }

    /**
     * Creates a new label and sets it as the tab label for the page containing child.
     *
     * @param child The page.
     * @param text  The label text.
     */
    public void setTabLabel(GtkWidget child, String text) {
        if (child != null) {
            library.gtk_notebook_set_tab_label_text(getCReference(), child.getCReference(), text);
        }
    }

    /**
     * Sets whether the notebook tab can be reordered via drag and drop
     *
     * @param child          A child GtkWidget
     * @param canBeReordered Whether the tab can be reordered
     */
    public void setTabReorderable(GtkWidget child, boolean canBeReordered) {
        if (child != null) {
            library.gtk_notebook_set_tab_reorderable(getCReference(), child.getCReference(), canBeReordered);
        }
    }

    /**
     * Sets whether the tab label area will have arrows for scrolling if there are too many tabs to fit in the area.
     * gtk_notebook_set_show_border
     *
     * @param isScrollable TRUE if scroll arrows should be added.
     */
    public void setTabsScrollable(boolean isScrollable) {
        library.gtk_notebook_set_scrollable(getCReference(), isScrollable);
    }

    /**
     * Sets whether to show the tabs for the notebook
     *
     * @param shouldShow TRUE if the tabs should be shown.
     */
    public void shouldShowTabs(boolean shouldShow) {
        library.gtk_notebook_set_show_tabs(getCReference(), shouldShow);
    }

    /**
     * Returns the GtkNotebookPage for child.
     *
     * @param child A child of notebook.
     * @return The GtkNotebookPage for child.
     */
    public Option<GtkNotebookPage> toGtkNotebookPage(GtkWidget child) {
        if (child != null) {
            Option<Pointer> p = new Option<>(library.gtk_notebook_get_page(getCReference(), child.getCReference()));
            if (p.isDefined()) {
                return new Option<>(new GtkNotebookPage(p.get()));
            }
        }
        return Option.NONE;
    }

    public static final class Signals extends GtkWidget.Signals {
        public static final Signals CHANGE_CURRENT_PAGE = new Signals("change-current-page");
        /**
         * The ::create-window signal is emitted when a detachable tab is dropped on the root window.
         */
        public static final Signals CREATE_WINDOW = new Signals("create-window");

        public static final Signals FOCUS_TAB = new Signals("focus-tab");
        public static final Signals MOVE_FOCUS_OUT = new Signals("move-focus-out");
        /**
         * The ::page-added signal is emitted in the notebook right after a page is added to the notebook.
         */
        public static final Signals PAGE_ADDED = new Signals("page-added");

        /**
         * The ::page-removed signal is emitted in the notebook right after a page is removed from the notebook.
         */
        public static final Signals PAGE_REMOVED = new Signals("page-removed");

        /**
         * The ::page-reordered signal is emitted in the notebook right after a page has been reordered.
         */
        public static final Signals PAGE_REORDERED = new Signals("page-reordered");
        public static final Signals REORDER_TAB = new Signals("reorder-tab");
        public static final Signals SELECT_PAGE = new Signals("select-page");
        /**
         * Emitted when the user or a function changes the current page.
         */
        public static final Signals SWITCH_PAGE = new Signals("switch-page");

        private Signals(String detailedName) {
            super(detailedName);
        }
    }

    protected static class GtkNotebookLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Appends a page to notebook.
         *
         * @param notebook  self
         * @param child     The GtkWidget to use as the contents of the page. Type: GtkWidget
         * @param tab_label The GtkWidget to be used as the label for the page, or NULL to use the default label,
         *                  "page N". Type: GtkWidget
         *                  <p>
         *                  The argument can be NULL.
         * @return The index (starting from 0) of the appended page in the notebook, or -1 if function fails.
         */
        public native int gtk_notebook_append_page(Pointer notebook, Pointer child, Pointer tab_label);

        /**
         * Appends a page to notebook, specifying the widget to use as the label in the popup menu.
         *
         * @param notebook   self
         * @param child      The GtkWidget to use as the contents of the page. Type: GtkWidget
         * @param tab_label  The GtkWidget to be used as the label for the page, or NULL to use the default label,
         *                   "page N". Type: GtkWidget
         *                   <p>
         *                   The argument can be NULL.
         * @param menu_label The widget to use as a label for the page-switch menu, if that is enabled.
         *                   If NULL, and tab_label is a GtkLabel or NULL, then the menu label will be a newly created
         *                   label with the same text as tab_label; if tab_label is not a GtkLabel,
         *                   menu_label must be specified if the page-switch menu is to be used. Type: GtkWidget
         *                   <p>
         *                   The argument can be NULL.
         * @return The index (starting from 0) of the appended page in the notebook, or -1 if function fails.
         */
        public native int gtk_notebook_append_page_menu(Pointer notebook, Pointer child, Pointer tab_label, Pointer menu_label);

        /**
         * Removes the child from the notebook.
         * <p>
         * This function is very similar to gtk_notebook_remove_page(), but additionally informs the notebook that the
         * removal is happening as part of a tab DND operation, which should not be cancelled.
         *
         * @param notebook self
         * @param child    A child to detach. Type: GtkWidget
         */
        public native void gtk_notebook_detach_tab(Pointer notebook, Pointer child);

        /**
         * Gets one of the action widgets.
         * <p>
         * See gtk_notebook_set_action_widget().
         *
         * @param notebook  self
         * @param pack_type Pack type of the action widget to receive. Type: GtkPackType
         * @return The action widget with the given pack_type or NULL when this action widget has not been set.
         *         Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_notebook_get_action_widget(Pointer notebook, int pack_type);

        /**
         * Returns the page number of the current page.
         *
         * @param notebook self
         * @return The index (starting from 0) of the current page in the notebook. If the notebook has no pages,
         *         then -1 will be returned.
         */
        public native int gtk_notebook_get_current_page(Pointer notebook);

        /**
         * Gets the current group name for notebook.
         *
         * @param notebook self
         * @return The group name, or NULL if none is set.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_notebook_get_group_name(Pointer notebook);

        /**
         * Retrieves the menu label widget of the page containing child.
         *
         * @param notebook self
         * @param child    A widget contained in a page of notebook. Type: GtkWidget
         * @return The menu label, or NULL if the notebook page does not have a menu label other than the
         *         default (the tab label). Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_notebook_get_menu_label(Pointer notebook, Pointer child);

        /**
         * Retrieves the text of the menu label for the page containing child.
         *
         * @param notebook self
         * @param child    The child widget of a page of the notebook. Type: GtkWidget
         * @return The text of the tab label, or NULL if the widget does not have a menu label other than the default
         *         menu label, or the menu label widget is not a GtkLabel. The string is owned by the widget and must
         *         not be
         *         freed.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_notebook_get_menu_label_text(Pointer notebook, Pointer child);

        /**
         * Gets the number of pages in a notebook.
         *
         * @param notebook self
         * @return The number of pages in the notebook.
         */
        public native int gtk_notebook_get_n_pages(Pointer notebook);

        /**
         * Returns the child widget contained in page number page_num.
         *
         * @param notebook self
         * @param page_num The index of a page in the notebook, or -1 to get the last page.
         * @return The child widget, or NULL if page_num is out of bounds.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_notebook_get_nth_page(Pointer notebook, int page_num);

        /**
         * Returns the GtkNotebookPage for child.
         *
         * @param notebook self
         * @param child    A child of notebook.
         * @return The GtkNotebookPage for child. Type: GtkNotebookPage
         */
        public native Pointer gtk_notebook_get_page(Pointer notebook, Pointer child);

        /**
         * Returns a GListModel that contains the pages of the notebook.
         * <p>
         * This can be used to keep an up-to-date view. The model also implements GtkSelectionModel and can be used to
         * track and modify the visible page.
         *
         * @param notebook self
         * @return A GListModel for the notebook's children. Type: GListModel of GtkNotebookPages
         */
        public native Pointer gtk_notebook_get_pages(Pointer notebook);

        /**
         * Returns whether the tab label area has arrows for scrolling.
         *
         * @param notebook self
         * @return TRUE if arrows for scrolling are present.
         */
        public native boolean gtk_notebook_get_scrollable(Pointer notebook);

        /**
         * Returns whether a bevel will be drawn around the notebook pages.
         *
         * @param notebook self
         * @return TRUE if the bevel is drawn.
         */
        public native boolean gtk_notebook_get_show_border(Pointer notebook);

        /**
         * Returns whether the tabs of the notebook are shown
         *
         * @param notebook self
         * @return TRUE if the tabs are shown.
         */
        public native boolean gtk_notebook_get_show_tabs(Pointer notebook);

        /**
         * Returns whether the tab contents can be detached from notebook.
         *
         * @param notebook self
         * @param child    A child GtkWidget. Type: GtkWidget
         * @return TRUE if the tab is detachable.
         */
        public native boolean gtk_notebook_get_tab_detachable(Pointer notebook, Pointer child);

        /**
         * Returns the tab label widget for the page child.
         * <p>
         * NULL is returned if child is not in notebook or if no tab label has specifically been set for child.
         *
         * @param notebook self
         * @param child    The page. Type: GtkWidget
         * @return The tab label. Type: GtkWidget
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_notebook_get_tab_label(Pointer notebook, Pointer child);

        /**
         * Retrieves the text of the tab label for the page containing child.
         *
         * @param notebook self. Type: GtkWidget
         * @param child    A widget contained in a page of notebook.
         * @return The text of the tab label, or NULL if the tab label widget is not a GtkLabel.
         *         The string is owned by the widget and must not be freed.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_notebook_get_tab_label_text(Pointer notebook, Pointer child);

        /**
         * Gets the edge at which the tabs are drawn.
         *
         * @param notebook self
         * @return The edge at which the tabs are drawn. Type: GtkPositionType
         */
        public native int gtk_notebook_get_tab_pos(Pointer notebook);

        /**
         * Gets whether the tab can be reordered via drag and drop.
         *
         * @param notebook self
         * @param child    A child GtkWidget. Type: GtkWidget
         * @return TRUE if the tab is reorderable.
         */
        public native boolean gtk_notebook_get_tab_reorderable(Pointer notebook, Pointer child);

        /**
         * Insert a page into notebook at the given position.
         *
         * @param notebook  self
         * @param child     The GtkWidget to use as the contents of the page. Type: GtkWidget
         * @param tab_label The GtkWidget to be used as the label for the page, or NULL to use the default label,
         *                  "page N". Type: GtkWidget
         *                  <p>
         *                  The argument can be NULL.
         * @param position  The index (starting at 0) at which to insert the page, or -1 to append the page after all
         *                  other pages.
         * @return The index (starting from 0) of the inserted page in the notebook, or -1 if function fails.
         */
        public native int gtk_notebook_insert_page(Pointer notebook, Pointer child, Pointer tab_label, int position);

        /**
         * Insert a page into notebook at the given position, specifying the widget to use as the label in the popup
         * menu.
         *
         * @param notebook   self
         * @param child      The GtkWidget to use as the contents of the page. Type: GtkWidget
         * @param tab_label  The GtkWidget to be used as the label for the page, or NULL to use the default label,
         *                   "page N". Type: GtkWidget
         *                   <p>
         *                   The argument can be NULL.
         * @param menu_label The widget to use as a label for the page-switch menu, if that is enabled. If NULL, and
         *                   tab_label is a GtkLabel or NULL, then the menu label will be a newly created label with
         *                   the same text as tab_label; if tab_label is not a GtkLabel, menu_label must be specified
         *                   if the page-switch menu is to be used. Type: GtkWidget
         *                   <p>
         *                   The argument can be NULL.
         * @param position   The index (starting at 0) at which to insert the page, or -1 to append the page after
         *                   all other pages.
         * @return The index (starting from 0) of the inserted page in the notebook.
         */
        public native int gtk_notebook_insert_page_menu(Pointer notebook, Pointer child, Pointer tab_label, Pointer menu_label, int position);

        /**
         * Creates a new GtkNotebook widget with no pages.
         *
         * @return The newly created GtkNotebook. Type: GtkNotebook
         */
        public native Pointer gtk_notebook_new();

        /**
         * Switches to the next page.
         * <p>
         * Nothing happens if the current page is the last page.
         *
         * @param notebook self
         */
        public native void gtk_notebook_next_page(Pointer notebook);

        /**
         * Finds the index of the page which contains the given child widget.
         *
         * @param notebook self
         * @param child    A GtkWidget. Type: GtkWidget
         * @return The index of the page containing child, or -1 if child is not in the notebook.
         */
        public native int gtk_notebook_page_num(Pointer notebook, Pointer child);

        /**
         * Disables the popup menu.
         *
         * @param notebook self
         */
        public native void gtk_notebook_popup_disable(Pointer notebook);

        /**
         * Enables the popup menu.
         * <p>
         * If the user clicks with the right mouse button on the tab labels, a menu with all the pages will be popped
         * up.
         *
         * @param notebook self
         */
        public native void gtk_notebook_popup_enable(Pointer notebook);

        /**
         * Prepends a page to notebook.
         *
         * @param notebook  self
         * @param child     The GtkWidget to use as the contents of the page. Type: GtkWidget
         * @param tab_label The GtkWidget to be used as the label for the page, or NULL to use the default label,
         *                  "page N". Type: GtkWidget
         *                  <p>
         *                  The argument can be NULL.
         * @return The index (starting from 0) of the prepended page in the notebook, or -1 if function fails.
         */
        public native int gtk_notebook_prepend_page(Pointer notebook, Pointer child, Pointer tab_label);

        /**
         * Prepends a page to notebook, specifying the widget to use as the label in the popup menu.
         *
         * @param notebook   self
         * @param child      The GtkWidget to use as the contents of the page. Type: GtkWidget
         * @param tab_label  The GtkWidget to be used as the label for the page, or NULL to use the default label,
         *                   "page N". Type: GtkWidget
         *                   <p>
         *                   The argument can be NULL.
         * @param menu_label The widget to use as a label for the page-switch menu, if that is enabled. If NULL, and
         *                   tab_label is a GtkLabel or NULL, then the menu label will be a newly created label with
         *                   the same text as tab_label; if tab_label is not a GtkLabel, menu_label must be
         *                   specified if the page-switch menu is to be used. Type: GtkWidget
         *                   <p>
         *                   The argument can be NULL.
         * @return The index (starting from 0) of the prepended page in the notebook, or -1 if function fails.
         */
        public native int gtk_notebook_prepend_page_menu(Pointer notebook, Pointer child, Pointer tab_label, Pointer menu_label);

        /**
         * Switches to the previous page.
         * <p>
         * Nothing happens if the current page is the first page.
         *
         * @param notebook self
         */
        public native void gtk_notebook_prev_page(Pointer notebook);

        /**
         * Removes a page from the notebook given its index in the notebook.
         *
         * @param notebook self
         * @param index    The index of a notebook page, starting from 0. If -1, the last page will be removed.
         */
        public native void gtk_notebook_remove_page(Pointer notebook, int index);

        /**
         * Reorders the page containing child, so that it appears in position.
         * <p>
         * If position is greater than or equal to the number of children in the list or negative, child will be moved
         * to the end of the list.
         *
         * @param notebook self
         * @param child    The child to move. Type: GtkWidget
         * @param position The new position, or -1 to move to the end.
         */
        public native void gtk_notebook_reorder_child(Pointer notebook, Pointer child, int position);

        /**
         * Sets widget as one of the action widgets.
         * <p>
         * Depending on the pack type the widget will be placed before or after the tabs. You can use a GtkBox
         * if you need to pack more than one widget on the same side.
         *
         * @param notebook  self
         * @param widget    A GtkWidget. Type: GtkWidget
         * @param pack_type Pack type of the action widget. Type: GtkPackType
         */
        public native void gtk_notebook_set_action_widget(Pointer notebook, Pointer widget, int pack_type);

        /**
         * Switches to the page number page_num.
         * <p>
         * Note that due to historical reasons, GtkNotebook refuses to switch to a page unless the child widget is
         * visible. Therefore, it is recommended to show child widgets before adding them to a notebook.
         *
         * @param notebook self
         * @param page_num Index of the page to switch to, starting from 0. If negative, the last page will be used.
         *                 If greater than the number of pages in the notebook, nothing will be done.
         */
        public native void gtk_notebook_set_current_page(Pointer notebook, int page_num);

        /**
         * Sets a group name for notebook.
         * <p>
         * Notebooks with the same name will be able to exchange tabs via drag and drop. A notebook with a NULL group
         * name will not be able to exchange tabs with any other notebook.
         *
         * @param notebook   self
         * @param group_name The name of the notebook group, or NULL to unset it.
         *                   <p>
         *                   The argument can be NULL.
         */
        public native void gtk_notebook_set_group_name(Pointer notebook, String group_name);

        /**
         * Changes the menu label for the page containing child.
         *
         * @param notebook   self
         * @param child      The child widget. Type: GtkWidget
         * @param menu_label The menu label, or NULL for default. Type: GtkWidget
         *                   <p>
         *                   The argument can be NULL.
         */
        public native void gtk_notebook_set_menu_label(Pointer notebook, Pointer child, Pointer menu_label);

        /**
         * Creates a new label and sets it as the menu label of child.
         *
         * @param notebook  self
         * @param child     The child widget. Type: GtkWidget
         * @param menu_text The label text.
         */
        public native void gtk_notebook_set_menu_label_text(Pointer notebook, Pointer child, String menu_text);

        /**
         * Sets whether the tab label area will have arrows for scrolling if there are too many tabs to fit in the area.
         *
         * @param notebook   self
         * @param scrollable TRUE if scroll arrows should be added.
         */
        public native void gtk_notebook_set_scrollable(Pointer notebook, boolean scrollable);

        /**
         * Sets whether a bevel will be drawn around the notebook pages.
         * <p>
         * This only has a visual effect when the tabs are not shown.
         *
         * @param notebook    self
         * @param show_border TRUE if a bevel should be drawn around the notebook.
         */
        public native void gtk_notebook_set_show_border(Pointer notebook, boolean show_border);

        /**
         * Sets whether to show the tabs for the notebook.
         *
         * @param notebook  self
         * @param show_tabs TRUE if the tabs should be shown.
         */
        public native void gtk_notebook_set_show_tabs(Pointer notebook, boolean show_tabs);

        /**
         * Sets whether the tab can be detached from notebook to another notebook or widget.
         * <p>
         * Note that two notebooks must share a common group identifier (see gtk_notebook_set_group_name()) to allow
         * automatic tabs interchange between them.
         * <p>
         * If you want a widget to interact with a notebook through DnD (i.e.: accept dragged tabs from it) it must
         * be set as a drop destination and accept the target "GTK_NOTEBOOK_TAB". The notebook will fill the selection
         * with a GtkWidget** pointing to the child widget that corresponds to the dropped tab.
         * <p>
         * Note that you should use gtk_notebook_detach_tab() instead of gtk_notebook_remove_page() if you want to
         * remove the tab from the source notebook as part of accepting a drop. Otherwise, the source notebook will
         * think that the dragged tab was removed from underneath the ongoing drag operation, and will initiate
         * a drag cancel animation.
         *
         * @param notebook   self
         * @param child      A child GtkWidget. Type: GtkWidget
         * @param detachable Whether the tab is detachable or not.
         */
        public native void gtk_notebook_set_tab_detachable(Pointer notebook, Pointer child, boolean detachable);

        /**
         * Changes the tab label for child.
         * <p>
         * If NULL is specified for tab_label, then the page will have the label "page N".
         *
         * @param notebook  self
         * @param child     The page. Type: GtkWidget
         * @param tab_label The tab label widget to use, or NULL for default tab label. Type: GtkWidget
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_notebook_set_tab_label(Pointer notebook, Pointer child, Pointer tab_label);

        /**
         * Creates a new label and sets it as the tab label for the page containing child.
         *
         * @param notebook self
         * @param child    The page. Type: GtkWidget
         * @param tab_text The label text.
         */
        public native void gtk_notebook_set_tab_label_text(Pointer notebook, Pointer child, String tab_text);

        /**
         * Sets the edge at which the tabs are drawn.
         *
         * @param notebook self
         * @param pos      The edge to draw the tabs at. Type: GtkPositionType
         */
        public native void gtk_notebook_set_tab_pos(Pointer notebook, int pos);

        /**
         * Sets whether the notebook tab can be reordered via drag and drop or not.
         *
         * @param notebook    self
         * @param child       A child GtkWidget. Type: GtkWidget
         * @param reorderable Whether the tab is reorderable or not.
         */
        public native void gtk_notebook_set_tab_reorderable(Pointer notebook, Pointer child, boolean reorderable);

    }
}
