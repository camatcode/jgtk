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

import com.gitlab.ccook.jgtk.GClosure;
import com.gitlab.ccook.jgtk.GError;
import com.gitlab.ccook.jgtk.GSList;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.bitfields.GtkBuilderClosureFlags;
import com.gitlab.ccook.jgtk.errors.GErrorException;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.io.File;

@SuppressWarnings("unchecked")
public class GtkBuilder extends JGTKObject {
    private static final GtkBuilderLibrary library = new GtkBuilderLibrary();

    public GtkBuilder(Pointer cReference) {
        super(cReference);
    }

    /**
     * Creates a new empty builder object.
     */
    public GtkBuilder() {
        super(library.gtk_builder_new());
    }

    /**
     * Parses the UI definition in the file filename.
     * <p>
     * If there is an error opening the file or parsing the description then the program will be aborted.
     * You should only ever attempt to parse user interface descriptions that are shipped as part of your program.
     *
     * @param uiFile Filename of user interface description file.
     */
    public GtkBuilder(File uiFile) {
        super(library.gtk_builder_new_from_file(uiFile.getAbsolutePath()));
    }

    /**
     * Parses the UI definition in string.
     * <p>
     * If there is an error parsing string then the program will be aborted. You should not attempt to parse user
     * interface description from untrusted sources.
     *
     * @param xml A user interface (XML) description.
     */
    public GtkBuilder(String xml) {
        super(library.gtk_builder_new_from_string(xml, xml.length()));
    }

    /**
     * Parses a file containing a UI definition and merges it with the current contents of builder.
     * <p>
     * This function is useful if you need to call gtk_builder_set_current_object() to add user data to callbacks
     * before loading GtkBuilder UI. Otherwise, you probably want gtk_builder_new_from_file() instead.
     *
     * @param uiToAdd The file to parse.
     * @return TRUE on success, FALSE if an error occurred.
     * @throws GErrorException If an error occurs, 0 will be returned and error will be assigned a GError from the
     *                         GTK_BUILDER_ERROR, G_MARKUP_ERROR or G_FILE_ERROR domains.
     *                         <p>
     *                         It's not really reasonable to attempt to handle failures of this call. You should not
     *                         use this function with untrusted files (ie: files that are not part of your application).
     *                         Broken GtkBuilder files can easily crash your program, and it's possible that memory was
     *                         leaked leading up to the reported failure. The only reasonable thing to do when an error
     *                         is detected is to call g_error().
     */
    public boolean add(File uiToAdd) throws GErrorException {
        if (uiToAdd != null && uiToAdd.exists()) {
            GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
            boolean toReturn = library.gtk_builder_add_from_file(getCReference(), uiToAdd.getAbsolutePath(), error);
            if (error.getPointer() != Pointer.NULL && error.code != 0) {
                throw new GErrorException(new GError(error));
            }
            return toReturn;
        }
        return false;
    }

    /**
     * Parses a string containing a UI definition and merges it with the current contents of builder.
     * <p>
     * This function is useful if you need to call gtk_builder_set_current_object() to add user data to callbacks before
     * loading GtkBuilder UI. Otherwise, you probably want gtk_builder_new_from_string() instead.
     *
     * @param xml The string to parse.
     * @return TRUE on success, FALSE if an error occurred.
     * @throws GErrorException Upon errors FALSE will be returned and error will be assigned a GError from the
     *                         GTK_BUILDER_ERROR, G_MARKUP_ERROR or G_VARIANT_PARSE_ERROR domain.
     *                         <p>
     *                         It's not really reasonable to attempt to handle failures of this call. The only
     *                         reasonable thing to do when an error is detected is to call g_error().
     */
    public boolean add(String xml) throws GErrorException {
        if (xml != null && !xml.isEmpty()) {
            GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
            boolean toReturn = library.gtk_builder_add_from_string(getCReference(), xml, xml.length(), error);
            if (error.getPointer() != Pointer.NULL && error.code != 0) {
                throw new GErrorException(new GError(error));
            }
            return toReturn;
        }
        return false;
    }

    /**
     * Parses a file containing a UI definition building only the requested objects and merges them with the current
     * contents of builder.
     * <p>
     * If you are adding an object that depends on an object that is not its child (for instance a GtkTreeView that
     * depends on its GtkTreeModel), you have to explicitly list all of them in object_ids.
     *
     * @param uiFile         The file to parse.
     * @param objectIdsToAdd array of objects to build
     * @return TRUE on success, FALSE if an error occurred.
     * @throws GErrorException Upon errors, 0 will be returned and error will be assigned a GError from the
     *                         GTK_BUILDER_ERROR, G_MARKUP_ERROR or G_FILE_ERROR domain.
     */
    public boolean add(File uiFile, String... objectIdsToAdd) throws GErrorException {
        if (uiFile != null && uiFile.exists() && objectIdsToAdd.length > 0) {
            GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
            boolean toReturn = library.gtk_builder_add_objects_from_file(getCReference(), uiFile.getAbsolutePath(), objectIdsToAdd, error);
            if (error.getPointer() != Pointer.NULL && error.code != 0) {
                throw new GErrorException(new GError(error));
            }
            return toReturn;
        }
        return false;
    }

    /**
     * Parses a string containing a UI definition, building only the requested objects and merges them with the current
     * contents of builder.
     * <p>
     * If you are adding an object that depends on an object that is not its child (for instance a GtkTreeView that
     * depends on its GtkTreeModel), you have to explicitly list all of them in object_ids.
     *
     * @param xml            The string to parse.
     * @param objectIdsToAdd array of objects to build.
     * @return TRUE on success, FALSE if an error occurred.
     * @throws GErrorException Upon errors FALSE will be returned and error will be assigned a GError from the
     *                         GTK_BUILDER_ERROR or G_MARKUP_ERROR domain.
     */
    public boolean add(String xml, String... objectIdsToAdd) throws GErrorException {
        if (xml != null && !xml.isEmpty() && objectIdsToAdd.length != 0) {
            GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
            boolean toReturn = library.gtk_builder_add_objects_from_string(getCReference(), xml, xml.length(), objectIdsToAdd, error);
            if (error.getPointer() != Pointer.NULL && error.code != 0) {
                throw new GErrorException(new GError(error));
            }
            return toReturn;
        }
        return false;
    }

    /**
     * Add object to the builder object pool, so it can be referenced just like any other object built by builder.
     * <p>
     * Only a single object may be added using name. However, it is not an error to expose the same object under
     * multiple names. gtk_builder_get_object() may be used to determine if an object has already been added with name.
     *
     * @param name   The name of the object exposed to the builder.
     * @param object The object to expose.
     */
    public void add(String name, JGTKObject object) {
        if (name != null && object != null) {
            library.gtk_builder_expose_object(getCReference(), name, object.getCReference());
        }
    }

    /**
     * Creates a closure to invoke the function called function_name.
     * <p>
     * This is using the create_closure() implementation of builder's GtkBuilderScope.
     * <p>
     * If no closure could be created, NULL will be returned and error will be set.
     *
     * @param functionName Name of the function to look up.
     * @param object       Object to create the closure with.
     *                     <p>
     *                     The argument can be NULL.
     * @param flags        Closure creation flags.
     * @return A new closure for invoking function_name.
     * @throws GErrorException If a closure could not be created
     */
    public Option<GClosure> createClosure(String functionName, JGTKObject object, GtkBuilderClosureFlags flags) throws GErrorException {
        if (functionName != null) {
            GError.GErrorStruct.ByReference error = new GError.GErrorStruct.ByReference();
            Option<Pointer> p = new Option<>(library.gtk_builder_create_closure(getCReference(), functionName, flags.getCValue(), pointerOrNull(object), error));
            if (error.getPointer() != Pointer.NULL && error.code != 0) {
                throw new GErrorException(new GError(error));
            }
            if (p.isDefined()) {
                return new Option<>(new GClosure(p.get()));
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the current object set via gtk_builder_set_current_object().
     *
     * @return The current object, if defined
     */
    public Option<JGTKObject> getCurrentObject() {
        Option<Pointer> p = new Option<>(library.gtk_builder_get_current_object(getCReference()));
        if (p.isDefined()) {
            return new Option<>(JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
        }
        return Option.NONE;
    }

    /**
     * Sets the current object for the builder.
     * <p>
     * The current object can be thought of as the 'this' object that the builder is working for and will often be used
     * as
     * the default object when an object is optional.
     * <p>
     * gtk_widget_init_template() for example will set the current object to the widget the template is initialized for.
     * For functions like gtk_builder_new_from_resource(), the current object will be NULL.
     *
     * @param object The new current object.
     *               <p>
     *               The argument can be NULL.
     */
    public void setCurrentObject(JGTKObject object) {
        library.gtk_builder_set_current_object(getCReference(), pointerOrNull(object));
    }

    /**
     * Gets the object named name.
     * <p>
     * Note that this function does not increment the reference count of the returned object.
     *
     * @param name Name of object to get.
     * @return The object named name, if defined.
     */
    public Option<JGTKObject> getObject(String name) {
        Option<Pointer> p = new Option<>(library.gtk_builder_get_object(getCReference(), name));
        if (p.isDefined()) {
            return new Option<>(JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
        }
        return Option.NONE;
    }

    /**
     * Gets all objects that have been constructed by builder.
     * <p>
     * Note that this function does not increment the reference counts of the returned objects.
     *
     * @return A newly-allocated GSList containing all the objects constructed by the GtkBuilder instance.
     */
    public GSList<JGTKObject> getObjects() {
        return new GSList<>(library.gtk_builder_get_objects(getCReference()), JGTKObject.class);
    }

    /**
     * Gets the scope in use that was set via gtk_builder_set_scope().
     *
     * @return The current scope.
     */
    public GtkBuilderScope getScope() {
        return new GtkBuilderCScope(library.gtk_builder_get_scope(getCReference()));
    }

    public void setScope(GtkBuilderScope scope) {
        library.gtk_builder_set_scope(getCReference(), pointerOrNull(scope));
    }

    /**
     * @return The translation domain used when translating property values that have been marked as translatable.
     *         <p>
     *         If the translation domain is NULL, GtkBuilder uses gettext(), otherwise g_dgettext().
     */
    public Option<String> getTranslationDomain() {
        return new Option<>(library.gtk_builder_get_translation_domain(getCReference()));
    }

    public void setTranslationDomain(String translationDomain) {
        library.gtk_builder_set_translation_domain(getCReference(), translationDomain);
    }

    /**
     * Looks up a type by name.
     * <p>
     * This is using the virtual function that GtkBuilder has for that purpose. This is mainly used when implementing
     * the GtkBuildable interface on a type.
     *
     * @param typeName Type name to lookup.
     * @return The GType found for type_name or G_TYPE_INVALID if no type was found.
     */
    public int getType(String typeName) {
        return library.gtk_builder_get_type_from_name(getCReference(), typeName);
    }

    protected static class GtkBuilderLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Parses a file containing a UI definition and merges it with the current contents of builder.
         * <p>
         * This function is useful if you need to call gtk_builder_set_current_object() to add user data to callbacks
         * before loading GtkBuilder UI. Otherwise, you probably want gtk_builder_new_from_file() instead.
         * <p>
         * If an error occurs, 0 will be returned and error will be assigned a GError from the GTK_BUILDER_ERROR,
         * G_MARKUP_ERROR or G_FILE_ERROR domains.
         * <p>
         * It's not really reasonable to attempt to handle failures of this call. You should not use this function with
         * untrusted files (ie: files that are not part of your application). Broken GtkBuilder files can easily crash
         * your program, and it's possible that memory was leaked leading up to the reported failure. The only
         * reasonable thing to do when an error is detected is to call g_error().
         *
         * @param builder  self
         * @param filename The name of the file to parse.
         * @param error    The argument can be NULL.
         * @return TRUE on success, FALSE if an error occurred.
         */
        public native boolean gtk_builder_add_from_file(Pointer builder, String filename, GError.GErrorStruct.ByReference error);

        /**
         * Parses a string containing a UI definition and merges it with the current contents of builder.
         * <p>
         * This function is useful if you need to call gtk_builder_set_current_object() to add user data to callbacks
         * before loading GtkBuilder UI. Otherwise, you probably want gtk_builder_new_from_string() instead.
         * <p>
         * Upon errors FALSE will be returned and error will be assigned a GError from the GTK_BUILDER_ERROR,
         * G_MARKUP_ERROR or G_VARIANT_PARSE_ERROR domain.
         * <p>
         * It's not really reasonable to attempt to handle failures of this call. The only reasonable thing to do when
         * an error is detected is to call g_error().
         *
         * @param builder self
         * @param buffer  The string to parse.
         * @param length  The length of buffer (this may be -1 if buffer is nul-terminated)
         * @param error   The return location for a recoverable error.
         *                <p>
         *                The argument can be NULL.
         * @return TRUE on success, FALSE if an error occurred.
         */
        public native boolean gtk_builder_add_from_string(Pointer builder, String buffer, int length, GError.GErrorStruct.ByReference error);

        /**
         * Parses a file containing a UI definition building only the requested objects and merges them with the
         * current contents of builder.
         * <p>
         * Upon errors, 0 will be returned and error will be assigned a GError from the GTK_BUILDER_ERROR,
         * G_MARKUP_ERROR or G_FILE_ERROR domain.
         * <p>
         * If you are adding an object that depends on an object that is not its child (for instance a GtkTreeView
         * that depends on its GtkTreeModel), you have to explicitly list all of them in object_ids.
         *
         * @param builder    self
         * @param filename   The name of the file to parse.
         * @param object_ids Nul-terminated array of objects to build.
         * @param error      The return location for a recoverable error.
         * @return TRUE on success, FALSE if an error occurred.
         */
        public boolean gtk_builder_add_objects_from_file(Pointer builder, String filename, String[] object_ids, GError.GErrorStruct.ByReference error) {
            return INSTANCE.gtk_builder_add_objects_from_file(builder, filename, object_ids, error);
        }

        /**
         * Parses a string containing a UI definition, building only the requested objects and merges them with the
         * current contents of builder.
         * <p>
         * Upon errors FALSE will be returned and error will be assigned a GError from the GTK_BUILDER_ERROR or
         * G_MARKUP_ERROR domain.
         * <p>
         * If you are adding an object that depends on an object that is not its child (for instance a GtkTreeView that
         * depends on its GtkTreeModel), you have to explicitly list all of them in object_ids.
         *
         * @param builder    self
         * @param buffer     The string to parse.
         * @param length     The length of buffer (this may be -1 if buffer is nul-terminated)
         * @param object_ids Null-terminated array of objects to build.
         * @param error      The return location for a recoverable error.
         * @return TRUE on success, FALSE if an error occurred.
         */
        public boolean gtk_builder_add_objects_from_string(Pointer builder, String buffer, int length, String[] object_ids, GError.GErrorStruct.ByReference error) {
            return INSTANCE.gtk_builder_add_objects_from_string(builder, buffer, length, object_ids, error);
        }

        /**
         * Creates a closure to invoke the function called function_name.
         * <p>
         * This is using the create_closure() implementation of builder's GtkBuilderScope.
         * <p>
         * If no closure could be created, NULL will be returned and error will be set.
         *
         * @param builder       self
         * @param function_name Name of the function to look up.
         * @param flags         Closure creation flags. Type: GtkBuilderClosureFlags
         * @param object        Object to create the closure with. Type: GObject
         *                      <p>
         *                      The argument can be NULL.
         * @param error         The return location for a recoverable error.
         * @return A new closure for invoking function_name.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_builder_create_closure(Pointer builder, String function_name, int flags, Pointer object, GError.GErrorStruct.ByReference error);

        /**
         * Add object to the builder object pool, so it can be referenced just like any other object built by builder.
         * <p>
         * Only a single object may be added using name. However, it is not an error to expose the same object under
         * multiple names. gtk_builder_get_object() may be used to determine if an object has already been added with
         * name.
         *
         * @param builder self
         * @param name    The name of the object exposed to the builder
         * @param object  The object to expose.
         */
        public native void gtk_builder_expose_object(Pointer builder, String name, Pointer object);

        /**
         * Gets the current object set via gtk_builder_set_current_object().
         *
         * @param builder self
         * @return The current object. Type: GObject
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_builder_get_current_object(Pointer builder);

        /**
         * Gets the object named name.
         * <p>
         * Note that this function does not increment the reference count of the returned object.
         *
         * @param builder self
         * @param name    Name of object to get.
         * @return The object named name.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_builder_get_object(Pointer builder, String name);

        /**
         * Gets all objects that have been constructed by builder.
         * <p>
         * Note that this function does not increment the reference counts of the returned objects.
         *
         * @param builder self
         * @return A newly-allocated GSList containing all the objects constructed by the GtkBuilder instance.
         *         It should be freed by g_slist_free()
         */
        public native Pointer gtk_builder_get_objects(Pointer builder);

        /**
         * Gets the scope in use that was set via gtk_builder_set_scope().
         *
         * @param builder self
         * @return The current scope. Type: GtkBuilderScope
         */
        public native Pointer gtk_builder_get_scope(Pointer builder);

        /**
         * Gets the translation domain of builder.
         *
         * @param builder self
         * @return The translation domain.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_builder_get_translation_domain(Pointer builder);

        /**
         * Looks up a type by name.
         * <p>
         * This is using the virtual function that GtkBuilder has for that purpose. This is mainly used when
         * implementing
         * the GtkBuildable interface on a type.
         *
         * @param builder   self
         * @param type_name Type name to lookup.
         * @return The GType found for type_name or G_TYPE_INVALID if no type was found.
         */
        public native int gtk_builder_get_type_from_name(Pointer builder, String type_name);

        /**
         * Creates a new empty builder object.
         * <p>
         * This function is only useful if you intend to make multiple calls to gtk_builder_add_from_file(),
         * gtk_builder_add_from_resource() or gtk_builder_add_from_string() in order to merge multiple UI descriptions
         * into a single builder.
         *
         * @return A new (empty) GtkBuilder object.
         */
        public native Pointer gtk_builder_new();

        /**
         * Parses the UI definition in the file filename.
         * <p>
         * If there is an error opening the file or parsing the description then the program will be aborted.
         * You should only ever attempt to parse user interface descriptions that are shipped as part of your program.
         *
         * @param filename Filename of user interface description file.
         * @return A GtkBuilder containing the described interface.
         */
        public native Pointer gtk_builder_new_from_file(String filename);

        /**
         * Parses the UI definition in string.
         * <p>
         * If string is NULL-terminated, then length should be -1. If length is not -1, then it is the length of string.
         * <p>
         * If there is an error parsing string then the program will be aborted. You should not attempt to parse user
         * interface description from untrusted sources.
         *
         * @param string A user interface (XML) description.
         * @param length The length of string, or -1
         * @return A GtkBuilder containing the interface described by string.
         */
        public native Pointer gtk_builder_new_from_string(String string, int length);

        /**
         * Sets the current object for the builder.
         * <p>
         * The current object can be thought of as the 'this' object that the builder is working for and will often be
         * used as the default object when an object is optional.
         * <p>
         * gtk_widget_init_template() for example will set the current object to the widget the template is initialized
         * for.
         * For functions like gtk_builder_new_from_resource(), the current object will be NULL.
         *
         * @param builder self
         * @param object  The new current object. Type: GObject
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_builder_set_current_object(Pointer builder, Pointer object);

        /**
         * Sets the scope the builder should operate in.
         * <p>
         * If scope is NULL, a new GtkBuilderCScope will be created.
         *
         * @param builder self
         * @param scope   The scope to use. Type: GtkBuilderScope
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_builder_set_scope(Pointer builder, Pointer scope);

        /**
         * Sets the translation domain of builder.
         *
         * @param builder self
         * @param domain  The translation domain.
         *                <p>
         *                The argument can be NULL.
         */
        public native void gtk_builder_set_translation_domain(Pointer builder, String domain);
    }
}
