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

import com.gitlab.ccook.jgtk.GBytes;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * GtkBuilderListItemFactory is a GtkListItemFactory that creates widgets by instantiating GtkBuilder UI templates.
 * <p>
 * The templates must be extending GtkListItem, and typically use GtkExpressions to obtain data from the items in the
 * model.
 */
@SuppressWarnings("unchecked")
public class GtkBuilderListItemFactory extends GtkListItemFactory {

    private static final GtkBuilderListItemFactoryLibrary library = new GtkBuilderListItemFactoryLibrary();

    public GtkBuilderListItemFactory(Pointer cReference) {
        super(cReference);
    }

    public GtkBuilderListItemFactory(GtkBuilderScope scope, GBytes bytes) {
        super(library.gtk_builder_list_item_factory_new_from_bytes(pointerOrNull(scope), bytes.getCReference()));
    }

    public GtkBuilderListItemFactory(GtkBuilderScope scope, String resourcePath) {
        super(library.gtk_builder_list_item_factory_new_from_resource(pointerOrNull(scope), resourcePath));
    }

    /**
     * Gets the data used as the GtkBuilder UI template for constructing list-items
     *
     * @return The GtkBuilder data.
     */
    public GBytes getBytes() {
        return new GBytes(library.gtk_builder_list_item_factory_get_bytes(getCReference()));
    }

    /**
     * If the data references a resource, gets the path of that resource.
     *
     * @return The path to the resource, if defined
     */
    public Option<String> getResourcePath() {
        return new Option<>(library.gtk_builder_list_item_factory_get_resource(getCReference()));
    }

    /**
     * Gets the scope used when constructing list-items.
     *
     * @return The scope used when constructing list-items.
     */
    public Option<GtkBuilderScope> getScope() {
        Option<Pointer> p = new Option<>(library.gtk_builder_list_item_factory_get_scope(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GtkBuilderCScope(p.get()));
        }
        return Option.NONE;
    }

    protected static class GtkBuilderListItemFactoryLibrary extends GtkListItemFactoryLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the data used as the GtkBuilder UI template for constructing list-items
         *
         * @param self self
         * @return The GtkBuilder data.
         */
        public native Pointer gtk_builder_list_item_factory_get_bytes(Pointer self);

        /**
         * If the data references a resource, gets the path of that resource.
         *
         * @param self self
         * @return The path to the resource.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_builder_list_item_factory_get_resource(Pointer self);

        /**
         * Gets the scope used when constructing list-items.
         *
         * @param self self
         * @return The scope used when constructing list-items. Type: GtkBuilderScope
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_builder_list_item_factory_get_scope(Pointer self);

        /**
         * Creates a new GtkBuilderListItemFactory that instantiates widgets using bytes as the data to pass to
         * GtkBuilder.
         *
         * @param scope A scope to use when instantiating. Type: GtkBuilderScope
         *              <p>
         *              The argument can be NULL.
         * @param bytes The GBytes containing the ui file to instantiate.
         * @return A new GtkBuilderListItemFactory
         */
        public native Pointer gtk_builder_list_item_factory_new_from_bytes(Pointer scope, Pointer bytes);

        /**
         * Creates a new GtkBuilderListItemFactory that instantiates widgets using data read from the given
         * resource_path to pass to GtkBuilder.
         *
         * @param scope         A scope to use when instantiating. Type: GtkBuilderScope
         *                      <p>
         *                      The argument can be NULL.
         * @param resource_path Valid path to a resource that contains the data.
         * @return A new GtkBuilderListItemFactory
         */
        public native Pointer gtk_builder_list_item_factory_new_from_resource(Pointer scope, String resource_path);
    }
}
