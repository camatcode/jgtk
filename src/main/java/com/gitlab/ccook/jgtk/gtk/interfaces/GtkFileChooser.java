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
package com.gitlab.ccook.jgtk.gtk.interfaces;

import com.gitlab.ccook.jgtk.GError;
import com.gitlab.ccook.jgtk.GFile;
import com.gitlab.ccook.jgtk.GListModel;
import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.enums.GtkFileChooserAction;
import com.gitlab.ccook.jgtk.errors.GErrorException;
import com.gitlab.ccook.jgtk.gtk.GenericGListModel;
import com.gitlab.ccook.jgtk.gtk.GtkFileFilter;
import com.gitlab.ccook.jgtk.interfaces.GtkInterface;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkFileChooser is an interface that can be implemented by file selection widgets.
 * <p>
 * In GTK, the main objects that implement this interface are GtkFileChooserWidget and GtkFileChooserDialog.
 * <p>
 * You do not need to write an object that implements the GtkFileChooser interface unless you are trying to adapt an
 * existing file selector to expose a standard programming interface.
 * <p>
 * GtkFileChooser allows for shortcuts to various places in the filesystem. In the default implementation these are
 * displayed in the left pane. It may be a bit confusing at first that these shortcuts come from various sources and in
 * various flavours, so let's explain the terminology here:
 * <p>
 * Bookmarks: are created by the user, by dragging folders from the right pane to the left pane, or by using the "Add".
 * Bookmarks can be renamed and deleted by the user.
 * <p>
 * Shortcuts: can be provided by the application. For example, a Paint program may want to add a shortcut for a Clipart
 * folder. Shortcuts cannot be modified by the user.
 * <p>
 * Volumes: are provided by the underlying filesystem abstraction. They are the "roots" of the filesystem.
 *
 * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
 */
@SuppressWarnings({"unchecked"})
public interface GtkFileChooser extends GtkInterface {
    GtkFileChooser.GtkFileChooserLibrary fileChooserLibrary = new GtkFileChooser.GtkFileChooserLibrary();

    /**
     * Adds a 'choice' to the file chooser.
     * <p>
     * This is typically implemented as a combobox or, for boolean choices, as a checkbutton. You can select a value
     * using gtk_file_chooser_set_choice() before the dialog is shown, and you can obtain the user-selected value
     * in the GtkDialog::response signal handler using gtk_file_chooser_get_choice().
     *
     * @param id           ID for the added choice.
     * @param label        User-visible label for the added choice.
     * @param optionIds    Ids for the options of the choice, or NULL for a boolean choice.
     *                     <p>
     *                     The argument can be NULL.
     * @param optionLabels User-visible labels for the options, must be the same length as options.
     *                     <p>
     *                     The argument can be NULL.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void addChoice(String id, String label, String[] optionIds, String[] optionLabels) {
        fileChooserLibrary.gtk_file_chooser_add_choice(getCReference(), id, label, optionIds, optionLabels);
    }

    /**
     * Adds filter to the list of filters that the user can select between.
     * <p>
     * When a filter is selected, only files that are passed by that filter are displayed.
     * <p>
     * Note that the chooser takes ownership of the filter if it is floating, so you have to ref and sink it if you
     * want to keep a reference.
     *
     * @param filter A GtkFileFilter
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void addFilter(GtkFileFilter filter) {
        if (filter != null) {
            fileChooserLibrary.gtk_file_chooser_add_filter(getCReference(), filter.getCReference());
        }
    }

    /**
     * Adds a folder to be displayed with the shortcut folders in a file chooser.
     *
     * @param folder A GFile for the folder to add.
     * @return TRUE if the folder could be added successfully, FALSE otherwise.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default boolean addShortcutFolder(GFile folder) throws GErrorException {
        if (folder != null) {
            GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
            boolean toReturn = fileChooserLibrary.gtk_file_chooser_add_shortcut_folder(getCReference(), folder.getCReference(), error);
            if (error.getPointer() != Pointer.NULL && error.code != 0) {
                throw new GErrorException(new GError(error));
            }
            return toReturn;
        }
        return false;
    }

    /**
     * Gets the type of operation that the file chooser is performing.
     *
     * @return The action that the file selector is performing.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default GtkFileChooserAction getAction() {
        return GtkFileChooserAction.getActionFromCValue(fileChooserLibrary.gtk_file_chooser_get_action(getCReference()));
    }

    /**
     * Sets the type of operation that the chooser is performing.
     * <p>
     * The user interface is adapted to suit the selected action.
     * <p>
     * For example, an option to create a new folder might be shown if the action is GTK_FILE_CHOOSER_ACTION_SAVE
     * but not if the action is GTK_FILE_CHOOSER_ACTION_OPEN.
     *
     * @param action The action that the file selector is performing.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void setAction(GtkFileChooserAction action) {
        if (action != null) {
            fileChooserLibrary.gtk_file_chooser_set_action(getCReference(), GtkFileChooserAction.getCValue(action));
        }
    }

    /**
     * Gets the currently selected option in the 'choice' with the given ID.
     *
     * @param id The ID of the choice to get.
     * @return The ID of the currently selected option, if defined
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default Option<String> getChoice(String id) {
        return new Option<>(fileChooserLibrary.gtk_file_chooser_get_choice(getCReference(), id));
    }

    /**
     * Selects an option in a 'choice' that has been added with gtk_file_chooser_add_choice().
     * <p>
     * For a boolean choice, the possible options are "true" and "false".
     *
     * @param id     The ID of the choice to set.
     * @param option The ID of the option to select.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void setChoice(String id, String option) {
        if (id != null) {
            fileChooserLibrary.gtk_file_chooser_set_choice(getCReference(), id, option);
        }
    }

    /**
     * Gets whether file chooser will offer to create new folders.
     *
     * @return TRUE if the Create Folder button should be displayed.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default boolean canCreateFolders() {
        return fileChooserLibrary.gtk_file_chooser_get_create_folders(getCReference());
    }

    /**
     * Sets whether file chooser will offer to create new folders.
     * <p>
     * This is only relevant if the action is not set to be GTK_FILE_CHOOSER_ACTION_OPEN.
     *
     * @param doesCreate TRUE if the Create Folder button should be displayed.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void couldCreateFolders(boolean doesCreate) {
        fileChooserLibrary.gtk_file_chooser_set_create_folders(getCReference(), doesCreate);
    }

    /**
     * Gets the current folder of chooser as GFile.
     *
     * @return The GFile for the current folder, if defined
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default Option<GFile> getCurrentFolder() {
        Option<Pointer> p = new Option<>(fileChooserLibrary.gtk_file_chooser_get_current_folder(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GFile(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the current folder for chooser from a GFile.
     *
     * @param file The GFile for the new folder.
     *             <p>
     *             The argument can be NULL.
     * @return TRUE if the folder could be changed successfully, FALSE otherwise.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default boolean setCurrentFolder(GFile file) throws GErrorException {
        GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
        Pointer fPointer = file != null ? file.getCReference() : Pointer.NULL;
        boolean toReturn = fileChooserLibrary.gtk_file_chooser_set_current_folder(getCReference(), fPointer, error);
        if (error.getPointer() != Pointer.NULL && error.code != 0) {
            throw new GErrorException(new GError(error));
        }
        return toReturn;
    }

    /**
     * Gets the current name in the file selector, as entered by the user.
     * <p>
     * This is meant to be used in save dialogs, to get the currently typed filename when the file itself does
     * not exist yet.
     *
     * @return The raw text from the file chooser's "Name" entry. Free with g_free(). Note that this string is
     *         not a full pathname or URI; it is whatever the contents of the entry are. Note also that this string is
     *         in
     *         UTF-8 encoding, which is not necessarily the system's encoding for filenames.The raw text from the file
     *         chooser's "Name" entry. Free with g_free(). Note that this string is not a full pathname or URI; it is
     *         whatever the contents of the entry are. Note also that this string is in UTF-8 encoding, which is not
     *         necessarily the system's encoding for filenames.
     *         <p>
     *         The return value can be Option.NONE.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default Option<String> getCurrentName() {
        return new Option<>(fileChooserLibrary.gtk_file_chooser_get_current_name(getCReference()));
    }

    /**
     * Sets the current name in the file selector, as if entered by the user.
     * <p>
     * Note that the name passed in here is a UTF-8 string rather than a filename. This function is meant for such
     * uses as a suggested name in a "Save As…" dialog. You can pass "Untitled.doc" or a similarly suitable
     * suggestion for the name.
     * <p>
     * If you want to preselect a particular existing file, you should use gtk_file_chooser_set_file() instead.
     * <p>
     * Please see the documentation for those functions for an example of using gtk_file_chooser_set_current_name()
     * as well.
     *
     * @param name The filename to use, as a UTF-8 string.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void setCurrentName(String name) {
        fileChooserLibrary.gtk_file_chooser_set_current_name(getCReference(), name);
    }

    /**
     * Gets the GFile for the currently selected file in the file selector, if defined.
     * <p>
     * If multiple files are selected, one of the files will be returned at random.
     * <p>
     * If the file chooser is in folder mode, this function returns the selected folder.
     *
     * @return A selected GFile. You own the returned file; use g_object_unref() to release it.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default Option<GFile> getFile() {
        Option<Pointer> p = new Option<>(fileChooserLibrary.gtk_file_chooser_get_file(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GFile(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets file as the current filename for the file chooser.
     * <p>
     * This includes changing to the file's parent folder and actually selecting the file in list. If the chooser
     * is in GTK_FILE_CHOOSER_ACTION_SAVE mode, the file's base name will also appear in the dialog's file name
     * entry.
     * <p>
     * If the file name isn't in the current folder of chooser, then the current folder of chooser will be
     * changed to the folder containing file.
     * <p>
     * Note that the file must exist, or nothing will be done except for the directory change.
     * <p>
     * If you are implementing a save dialog, you should use this function if you already have a file name to
     * which the user may save; for example, when the user opens an existing file and then does "Save As…". I
     * f you don't have a file name already — for example, if the user just created a new file and is saving it
     * for the first time, do not call this function.
     *
     * @param file The GFile to set as current.
     * @return TRUE if file selected
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default boolean setFile(GFile file) throws GErrorException {
        GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
        Pointer fPointer = file != null ? file.getCReference() : Pointer.NULL;
        boolean toReturn = fileChooserLibrary.gtk_file_chooser_set_file(getCReference(), fPointer, error);
        if (error.getPointer() != Pointer.NULL && error.code != 0) {
            throw new GErrorException(new GError(error));
        }
        return toReturn;
    }

    /**
     * Lists all the selected files and sub-folders in the current folder of chooser as GFile.
     *
     * @return A list model containing a GFile for each selected file and subfolder in the current folder.
     *         Free the returned list with g_object_unref().
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default GListModel<GFile> getFiles() {
        Pointer p = fileChooserLibrary.gtk_file_chooser_get_files(getCReference());
        return new GenericGListModel<>(GFile.class, p);
    }

    /**
     * Gets the current filter.
     *
     * @return The current filter, if defined.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default Option<GtkFileFilter> getFilter() {
        Option<Pointer> p = new Option<>(fileChooserLibrary.gtk_file_chooser_get_filter(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkFileFilter(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Sets the current filter.
     * <p>
     * Only the files that pass the filter will be displayed. If the user-selectable list of filters is non-empty,
     * then the filter should be one of the filters in that list.
     * <p>
     * Setting the current filter when the list of filters is empty is useful if you want to restrict the displayed
     * set of files without letting the user change it.
     *
     * @param filter A GtkFileFilter
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void setFilter(GtkFileFilter filter) {
        fileChooserLibrary.gtk_file_chooser_set_filter(getCReference(), filter != null ? filter.getCReference() : Pointer.NULL);
    }

    /**
     * Gets the current set of user-selectable filters, as a list model.
     * <p>
     * See gtk_file_chooser_add_filter() and gtk_file_chooser_remove_filter() for changing individual filters.
     * <p>
     * You should not modify the returned list model. Future changes to chooser may or may not affect the returned
     * model.
     *
     * @return A GListModel containing the current set of user-selectable filters.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default GListModel<GtkFileFilter> getFilters() {
        Pointer p = fileChooserLibrary.gtk_file_chooser_get_filters(getCReference());
        return new GenericGListModel<>(GtkFileFilter.class, p);
    }

    /**
     * Gets whether multiple files can be selected in the file chooser.
     *
     * @return TRUE if multiple files can be selected.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default boolean canSelectMultiple() {
        return fileChooserLibrary.gtk_file_chooser_get_select_multiple(getCReference());
    }

    /**
     * Sets whether multiple files can be selected in the file chooser.
     * <p>
     * This is only relevant if the action is set to be GTK_FILE_CHOOSER_ACTION_OPEN or
     * GTK_FILE_CHOOSER_ACTION_SELECT_FOLDER.
     *
     * @param couldSelect TRUE if multiple files can be selected.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void couldSelectMultiple(boolean couldSelect) {
        fileChooserLibrary.gtk_file_chooser_set_select_multiple(getCReference(), couldSelect);
    }


    /**
     * Queries the list of shortcut folders in the file chooser.
     * <p>
     * You should not modify the returned list model. Future changes to chooser may or may not affect the returned
     * model.
     *
     * @return A list model of GFiles.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default GListModel<GFile> getShortcutFolders() {
        Pointer p = fileChooserLibrary.gtk_file_chooser_get_shortcut_folders(getCReference());
        return new GenericGListModel<>(GFile.class, p);
    }

    /**
     * Removes a 'choice' that has been added with gtk_file_chooser_add_choice().
     *
     * @param id The ID of the choice to remove.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void removeChoice(String id) {
        fileChooserLibrary.gtk_file_chooser_remove_choice(getCReference(), id);
    }

    /**
     * Removes filter from the list of filters that the user can select between.
     *
     * @param filter A GtkFileFilter
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default void removeFilter(GtkFileFilter filter) {
        if (filter != null) {
            fileChooserLibrary.gtk_file_chooser_remove_filter(getCReference(), filter.getCReference());
        }
    }

    /**
     * Removes a folder from the shortcut folders in a file chooser.
     *
     * @param folder A GFile for the folder to remove.
     * @return TRUE if the folder could be removed successfully, FALSE otherwise.
     * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
     */
    default boolean removeShortcutFolder(GFile folder) throws GErrorException {
        if (folder != null) {
            GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
            boolean toReturn = fileChooserLibrary.gtk_file_chooser_remove_shortcut_folder(getCReference(), folder.getCReference(), error);
            if (error.getPointer() != Pointer.NULL && error.code != 0) {
                throw new GErrorException(new GError(error));
            }
            return toReturn;
        }
        return false;
    }

    class GtkFileChooserLibrary extends GtkWidget.GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a 'choice' to the file chooser.
         * <p>
         * This is typically implemented as a combobox or, for boolean choices, as a checkbutton. You can select a value
         * using gtk_file_chooser_set_choice() before the dialog is shown, and you can obtain the user-selected value
         * in the GtkDialog::response signal handler using gtk_file_chooser_get_choice().
         *
         * @param chooser       self
         * @param id            ID for the added choice.
         * @param label         User-visible label for the added choice.
         * @param options       Ids for the options of the choice, or NULL for a boolean choice.
         *                      <p>
         *                      The argument can be NULL.
         * @param option_labels User-visible labels for the options, must be the same length as options.
         *                      <p>
         *                      The argument can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public void gtk_file_chooser_add_choice(Pointer chooser, String id, String label, String[] options, String[] option_labels) {
            INSTANCE.gtk_file_chooser_add_choice(chooser, id, label, options, option_labels);
        }

        /**
         * Adds filter to the list of filters that the user can select between.
         * <p>
         * When a filter is selected, only files that are passed by that filter are displayed.
         * <p>
         * Note that the chooser takes ownership of the filter if it is floating, so you have to ref and sink it if you
         * want to keep a reference.
         *
         * @param self   self
         * @param filter A GtkFileFilter
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_add_filter(Pointer self, Pointer filter);

        /**
         * Adds a folder to be displayed with the shortcut folders in a file chooser.
         *
         * @param self   self
         * @param folder A GFile for the folder to add.
         * @param error  The return location for a recoverable error.
         *               The argument can be NULL.
         * @return TRUE if the folder could be added successfully, FALSE otherwise.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native boolean gtk_file_chooser_add_shortcut_folder(Pointer self, Pointer folder, GError.GErrorStruct.ByReference error);

        /**
         * Gets the type of operation that the file chooser is performing.
         *
         * @param self self
         * @return The action that the file selector is performing. Type: GtkFileChooserAction
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native int gtk_file_chooser_get_action(Pointer self);

        /**
         * Gets the currently selected option in the 'choice' with the given ID.
         *
         * @param self self
         * @param id   The ID of the choice to get.
         * @return The ID of the currently selected option.
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native String gtk_file_chooser_get_choice(Pointer self, String id);

        /**
         * Gets whether file chooser will offer to create new folders.
         *
         * @param self self
         * @return TRUE if the Create Folder button should be displayed.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native boolean gtk_file_chooser_get_create_folders(Pointer self);

        /**
         * Gets the current folder of chooser as GFile.
         *
         * @param self self
         * @return The GFile for the current folder.
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native Pointer gtk_file_chooser_get_current_folder(Pointer self);

        /**
         * Gets the current name in the file selector, as entered by the user.
         * <p>
         * This is meant to be used in save dialogs, to get the currently typed filename when the file itself does
         * not exist yet.
         *
         * @param self self
         * @return The raw text from the file chooser's "Name" entry. Free with g_free(). Note that this string is
         *         not a full pathname or URI; it is whatever the contents of the entry are. Note also that this string
         *         is in
         *         UTF-8 encoding, which is not necessarily the system's encoding for filenames.The raw text from the
         *         file
         *         chooser's "Name" entry. Free with g_free(). Note that this string is not a full pathname or URI; it
         *         is
         *         whatever the contents of the entry are. Note also that this string is in UTF-8 encoding, which is not
         *         necessarily the system's encoding for filenames.
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native String gtk_file_chooser_get_current_name(Pointer self);

        /**
         * Gets the GFile for the currently selected file in the file selector.
         * <p>
         * If multiple files are selected, one of the files will be returned at random.
         * <p>
         * If the file chooser is in folder mode, this function returns the selected folder.
         *
         * @param self self
         * @return A selected GFile. You own the returned file; use g_object_unref() to release it.
         *         <p>
         *         The return value can be NULL
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native Pointer gtk_file_chooser_get_file(Pointer self);

        /**
         * Lists all the selected files and sub-folders in the current folder of chooser as GFile.
         *
         * @param self self
         * @return A list model containing a GFile for each selected file and subfolder in the current folder.
         *         Free the returned list with g_object_unref().
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native Pointer gtk_file_chooser_get_files(Pointer self);

        /**
         * Gets the current filter.
         *
         * @param self self
         * @return The current filter.
         *         <p>
         *         The return value can be NULL.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native Pointer gtk_file_chooser_get_filter(Pointer self);

        /**
         * Gets the current set of user-selectable filters, as a list model.
         * <p>
         * See gtk_file_chooser_add_filter() and gtk_file_chooser_remove_filter() for changing individual filters.
         * <p>
         * You should not modify the returned list model. Future changes to chooser may or may not affect the returned
         * model.
         *
         * @param self self
         * @return A GListModel containing the current set of user-selectable filters.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native Pointer gtk_file_chooser_get_filters(Pointer self);

        /**
         * Gets whether multiple files can be selected in the file chooser.
         *
         * @param self self
         * @return TRUE if multiple files can be selected.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native boolean gtk_file_chooser_get_select_multiple(Pointer self);

        /**
         * Queries the list of shortcut folders in the file chooser.
         * <p>
         * You should not modify the returned list model. Future changes to chooser may or may not affect the returned
         * model.
         *
         * @param self self
         * @return A list model of GFiles.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native Pointer gtk_file_chooser_get_shortcut_folders(Pointer self);

        /**
         * Removes a 'choice' that has been added with gtk_file_chooser_add_choice().
         *
         * @param self self
         * @param id   The ID of the choice to remove.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_remove_choice(Pointer self, String id);

        /**
         * Removes filter from the list of filters that the user can select between.
         *
         * @param self   self
         * @param filter A GtkFileFilter
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_remove_filter(Pointer self, Pointer filter);

        /**
         * Removes a folder from the shortcut folders in a file chooser.
         *
         * @param self   self
         * @param folder A GFile for the folder to remove.
         * @param error  The return location for a recoverable error.
         * @return TRUE if the folder could be removed successfully, FALSE otherwise.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native boolean gtk_file_chooser_remove_shortcut_folder(Pointer self, Pointer folder, GError.GErrorStruct.ByReference error);

        /**
         * Sets the type of operation that the chooser is performing.
         * <p>
         * The user interface is adapted to suit the selected action.
         * <p>
         * For example, an option to create a new folder might be shown if the action is GTK_FILE_CHOOSER_ACTION_SAVE
         * but not if the action is GTK_FILE_CHOOSER_ACTION_OPEN.
         *
         * @param self   self
         * @param action The action that the file selector is performing. Type: GtkFileChooserAction
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_set_action(Pointer self, int action);

        /**
         * Selects an option in a 'choice' that has been added with gtk_file_chooser_add_choice().
         * <p>
         * For a boolean choice, the possible options are "true" and "false".
         *
         * @param self   self
         * @param id     The ID of the choice to set.
         * @param option The ID of the option to select.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_set_choice(Pointer self, String id, String option);

        /**
         * Sets whether file chooser will offer to create new folders.
         * <p>
         * This is only relevant if the action is not set to be GTK_FILE_CHOOSER_ACTION_OPEN.
         *
         * @param self           self
         * @param create_folders TRUE if the Create Folder button should be displayed.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_set_create_folders(Pointer self, boolean create_folders);

        /**
         * Sets the current folder for chooser from a GFile.
         *
         * @param self  self
         * @param file  The GFile for the new folder.
         *              <p>
         *              The argument can be NULL.
         * @param error The return location for a recoverable error.
         * @return TRUE if the folder could be changed successfully, FALSE otherwise.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native boolean gtk_file_chooser_set_current_folder(Pointer self, Pointer file, GError.GErrorStruct.ByReference error);

        /**
         * Sets the current name in the file selector, as if entered by the user.
         * <p>
         * Note that the name passed in here is a UTF-8 string rather than a filename. This function is meant for such
         * uses as a suggested name in a "Save As…" dialog. You can pass "Untitled.doc" or a similarly suitable
         * suggestion for the name.
         * <p>
         * If you want to preselect a particular existing file, you should use gtk_file_chooser_set_file() instead.
         * <p>
         * Please see the documentation for those functions for an example of using gtk_file_chooser_set_current_name()
         * as well.
         *
         * @param self self
         * @param name The filename to use, as a UTF-8 string.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_set_current_name(Pointer self, String name);

        /**
         * Sets file as the current filename for the file chooser.
         * <p>
         * This includes changing to the file's parent folder and actually selecting the file in list. If the chooser
         * is in GTK_FILE_CHOOSER_ACTION_SAVE mode, the file's base name will also appear in the dialog's file name
         * entry.
         * <p>
         * If the file name isn't in the current folder of chooser, then the current folder of chooser will be
         * changed to the folder containing file.
         * <p>
         * Note that the file must exist, or nothing will be done except for the directory change.
         * <p>
         * If you are implementing a save dialog, you should use this function if you already have a file name to
         * which the user may save; for example, when the user opens an existing file and then does "Save As…". I
         * f you don't have a file name already — for example, if the user just created a new file and is saving it
         * for the first time, do not call this function.
         *
         * @param self  self
         * @param file  The GFile to set as current.
         * @param error The return location for a recoverable error.
         * @return TRUE if file selected
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native boolean gtk_file_chooser_set_file(Pointer self, Pointer file, GError.GErrorStruct.ByReference error);

        /**
         * Sets the current filter.
         * <p>
         * Only the files that pass the filter will be displayed. If the user-selectable list of filters is non-empty,
         * then the filter should be one of the filters in that list.
         * <p>
         * Setting the current filter when the list of filters is empty is useful if you want to restrict the displayed
         * set of files without letting the user change it.
         *
         * @param self   self
         * @param filter A GtkFileFilter
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_set_filter(Pointer self, Pointer filter);

        /**
         * Sets whether multiple files can be selected in the file chooser.
         * <p>
         * This is only relevant if the action is set to be GTK_FILE_CHOOSER_ACTION_OPEN or
         * GTK_FILE_CHOOSER_ACTION_SELECT_FOLDER.
         *
         * @param self            self
         * @param select_multiple TRUE if multiple files can be selected.
         * @deprecated Deprecated since: 4.10. Use GtkFileDialog instead.
         */
        public native void gtk_file_chooser_set_select_multiple(Pointer self, boolean select_multiple);
    }
}
