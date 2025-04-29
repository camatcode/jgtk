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

import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jna.GCallbackFunction;
import com.gitlab.ccook.jna.GtkLibrary;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * A GtkBuilderScope implementation for the C language.
 * <p>
 * GtkBuilderCScope instances use symbols explicitly added to builder with prior calls to
 * gtk_builder_cscope_add_callback_symbol(). If developers want to do that, they are encouraged to create their own
 * scopes for that purpose.
 * <p>
 * In the case that symbols are not explicitly added; GTK will use GModule's introspective features (by opening the
 * module NULL) to look at the application's symbol table. From here it tries to match the signal function names given
 * in the interface description with symbols in the application.
 * <p>
 * Note that unless gtk_builder_cscope_add_callback_symbol() is called for all signal callbacks which are referenced
 * by the loaded XML, this functionality will require that GModule be supported on the platform.
 */
@SuppressWarnings("unchecked")
public class GtkBuilderCScope extends JGTKObject implements GtkBuilderScope {

    private static final GtkBuilderCScopeLibrary library = new GtkBuilderCScopeLibrary();

    public GtkBuilderCScope(Pointer pointer) {
        super(pointer);
    }

    /**
     * Creates a new GtkBuilderCScope object to use with future GtkBuilder instances.
     * <p>
     * Calling this function is only necessary if you want to add custom callbacks via
     * gtk_builder_cscope_add_callback_symbol().
     */
    public GtkBuilderCScope() {
        super(library.gtk_builder_cscope_new());
    }

    /**
     * Adds the callback_symbol to the scope of builder under the given callback_name.
     * <p>
     * Using this function overrides the behavior of gtk_builder_create_closure() for any callback symbols that are
     * added. Using this method allows for better encapsulation as it does not require that callback symbols be
     * declared in the global namespace.
     *
     * @param callbackName   The name of the callback, as expected in the XML.
     * @param callbackSymbol The callback pointer.
     */
    public void addCallbackSymbol(String callbackName, GCallbackFunction callbackSymbol) {
        trackedObjects.add(callbackSymbol);
        library.gtk_builder_cscope_add_callback_symbol(getCReference(), callbackName, callbackSymbol);
    }

    /**
     * Fetches a symbol previously added with gtk_builder_cscope_add_callback_symbol().
     *
     * @param callbackName The name of the callback.
     * @return The callback symbol in builder for callback_name; if defined
     */
    public Option<GCallbackFunction> getCallbackSymbol(String callbackName) {
        if (callbackName != null) {
            GCallbackFunction f = library.gtk_builder_cscope_lookup_callback_symbol(getCReference(), callbackName);
            return new Option<>(f);
        }
        return Option.NONE;
    }

    /**
     * void
     * gtk_builder_cscope_add_callback_symbol (
     * GtkBuilderCScope* self,
     * const char* callback_name,
     * GCallback callback_symbol
     * )
     */

    protected static class GtkBuilderCScopeLibrary extends GtkLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds the callback_symbol to the scope of builder under the given callback_name.
         * <p>
         * Using this function overrides the behavior of gtk_builder_create_closure() for any callback symbols that are
         * added. Using this method allows for better encapsulation as it does not require that callback symbols be
         * declared in the global namespace.
         *
         * @param builder         self
         * @param callback_name   The name of the callback, as expected in the XML.
         * @param callback_symbol The callback pointer.
         */
        public native void gtk_builder_cscope_add_callback_symbol(Pointer builder, String callback_name, GCallbackFunction callback_symbol);

        /**
         * Fetches a symbol previously added with gtk_builder_cscope_add_callback_symbol().
         *
         * @param builder       self
         * @param callback_name The name of the callback.
         * @return The callback symbol in builder for callback_name.
         */
        public native GCallbackFunction gtk_builder_cscope_lookup_callback_symbol(Pointer builder, String callback_name);

        /**
         * Creates a new GtkBuilderCScope object to use with future GtkBuilder instances.
         * <p>
         * Calling this function is only necessary if you want to add custom callbacks via
         * gtk_builder_cscope_add_callback_symbol().
         *
         * @return A new GtkBuilderCScope
         */
        public native Pointer gtk_builder_cscope_new();
    }
}
