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

import com.gitlab.ccook.jgtk.GVariant;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class GtkFileFilter extends GtkFilter implements GtkBuildable {

    private static final GtkFileFilter.GtkFileFilterLibrary library = new GtkFileFilter.GtkFileFilterLibrary();

    public GtkFileFilter(Pointer cReference) {
        super(cReference);
    }

    /**
     * Creates a new GtkFileFilter with no rules added to it.
     * <p>
     * Such a filter doesn't accept any files, so is not particularly useful until you add rules with
     * gtk_file_filter_add_mime_type(), gtk_file_filter_add_pattern(), gtk_file_filter_add_suffix() or
     * gtk_file_filter_add_pixbuf_formats().
     */
    public GtkFileFilter() {
        super(library.gtk_file_filter_new());
    }

    /**
     * Deserialize a file filter from a GVariant.
     * <p>
     * The variant must be in the format produced by gtk_file_filter_to_gvariant().
     *
     * @param variant An a{sv} GVariant.
     */
    public GtkFileFilter(GVariant variant) {
        super(library.gtk_file_filter_new_from_gvariant(variant.getCReference()));
    }

    /**
     * Adds a rule allowing a given mime type to filter.
     *
     * @param mimeType Name of a MIME type.
     */
    public void addMimeType(String mimeType) {
        library.gtk_file_filter_add_mime_type(getCReference(), mimeType);
    }

    /**
     * Adds a rule allowing a shell style glob to a filter.
     * <p>
     * Note that it depends on the platform whether pattern matching ignores case or not. On Windows,
     * it does, on other platforms, it doesn't.
     *
     * @param glob A shell style glob.
     */
    public void addPattern(String glob) {
        library.gtk_file_filter_add_pattern(getCReference(), glob);
    }

    /**
     * Adds a rule allowing image files in the formats supported by GdkPixbuf.
     * <p>
     * This is equivalent to calling gtk_file_filter_add_mime_type() for all the supported mime types.
     */
    public void addPixbufFormats() {
        library.gtk_file_filter_add_pixbuf_formats(getCReference());
    }

    /**
     * Adds a suffix match rule to a filter.
     * <p>
     * This is similar to adding a match for the pattern "*.suffix".
     * <p>
     * In contrast to pattern matches, suffix matches are always case-insensitive.
     *
     * @param suffix Filename suffix to match.
     */
    public void addSuffix(String suffix) {
        if (suffix != null) {
            library.gtk_file_filter_add_suffix(getCReference(), suffix);
        }
    }

    /**
     * Gets the human-readable name for the filter.
     * <p>
     * See gtk_file_filter_set_name().
     *
     * @return The human-readable name of the filter, if defined
     */
    public Option<String> getName() {
        return new Option<>(library.gtk_file_filter_get_name(getCReference()));
    }

    /**
     * Sets a human-readable name of the filter.
     * <p>
     * This is the string that will be displayed in the file chooser if there is a selectable list of filters.
     *
     * @param name The human-readable-name for the filter, or NULL to remove any existing name.
     *             <p>
     *             The argument can be NULL.
     */
    public void setName(String name) {
        library.gtk_file_filter_set_name(getCReference(), name);
    }

    /**
     * Gets the attributes that need to be filled in for the GFileInfo passed to this filter.
     * <p>
     * This function will not typically be used by applications; it is intended principally for use in the
     * implementation of GtkFileChooser.
     *
     * @return The attributes.
     */
    public String[] getAttributes() {
        return library.gtk_file_filter_get_attributes(getCReference());
    }

    /**
     * Serialize a file filter to an a{sv} variant.
     *
     * @return A new, floating, GVariant
     */
    public GVariant toVariant() {
        return new GVariant(library.gtk_file_filter_to_gvariant(getCReference()));
    }

    static class GtkFileFilterLibrary extends GtkFilter.GtkFilterLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Creates a new GtkFileFilter with no rules added to it.
         * <p>
         * Such a filter doesn't accept any files, so is not particularly useful until you add rules with
         * gtk_file_filter_add_mime_type(), gtk_file_filter_add_pattern(), gtk_file_filter_add_suffix() or
         * gtk_file_filter_add_pixbuf_formats().
         *
         * @return A new GtkFileFilter
         */
        public native Pointer gtk_file_filter_new();

        /**
         * Deserialize a file filter from a GVariant.
         * <p>
         * The variant must be in the format produced by gtk_file_filter_to_gvariant().
         *
         * @param variant An a{sv} GVariant. Type: GVariant
         */
        public native Pointer gtk_file_filter_new_from_gvariant(Pointer variant);

        /**
         * Adds a rule allowing a given mime type to filter.
         *
         * @param self      self
         * @param mime_type Name of a MIME type.
         */
        public native void gtk_file_filter_add_mime_type(Pointer self, String mime_type);

        /**
         * Adds a rule allowing a shell style glob to a filter.
         * <p>
         * Note that it depends on the platform whether pattern matching ignores case or not. On Windows,
         * it does, on other platforms, it doesn't.
         *
         * @param self    self
         * @param pattern A shell style glob.
         */
        public native void gtk_file_filter_add_pattern(Pointer self, String pattern);

        /**
         * Adds a rule allowing image files in the formats supported by GdkPixbuf.
         * <p>
         * This is equivalent to calling gtk_file_filter_add_mime_type() for all the supported mime types.
         *
         * @param self self
         */
        public native void gtk_file_filter_add_pixbuf_formats(Pointer self);

        /**
         * Adds a suffix match rule to a filter.
         * <p>
         * This is similar to adding a match for the pattern "*.suffix".
         * <p>
         * In contrast to pattern matches, suffix matches are always case-insensitive.
         *
         * @param self   self
         * @param suffix Filename suffix to match.
         */
        public native void gtk_file_filter_add_suffix(Pointer self, String suffix);

        /**
         * Gets the attributes that need to be filled in for the GFileInfo passed to this filter.
         * <p>
         * This function will not typically be used by applications; it is intended principally for use in the
         * implementation of GtkFileChooser.
         *
         * @param self self
         * @return The attributes.
         */
        public String[] gtk_file_filter_get_attributes(Pointer self) {
            return INSTANCE.gtk_file_filter_get_attributes(self);
        }

        /**
         * Gets the human-readable name for the filter.
         * <p>
         * See gtk_file_filter_set_name().
         *
         * @param self self
         * @return The human-readable name of the filter.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_file_filter_get_name(Pointer self);

        /**
         * Sets a human-readable name of the filter.
         * <p>
         * This is the string that will be displayed in the file chooser if there is a selectable list of filters.
         *
         * @param self self
         * @param name The human-readable-name for the filter, or NULL to remove any existing name.
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_file_filter_set_name(Pointer self, String name);

        /**
         * Serialize a file filter to an a{sv} variant.
         *
         * @param self self
         * @return A new, floating, GVariant
         */
        public native Pointer gtk_file_filter_to_gvariant(Pointer self);
    }
}
