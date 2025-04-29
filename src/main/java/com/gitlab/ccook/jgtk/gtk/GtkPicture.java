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

import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.io.File;
import java.nio.file.Paths;

/**
 * The GtkPicture widget displays a GdkPaintable.
 */
@SuppressWarnings({"unchecked", "DeprecatedIsStillUsed"})
public class GtkPicture extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {
    private static final GtkPictureLibrary library = new GtkPictureLibrary();

    public GtkPicture(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new empty GtkPicture widget.
     */
    public GtkPicture() {
        super(library.gtk_picture_new());
    }

    /**
     * Creates a new GtkPicture displaying the given file.
     * <p>
     * If the file isn't found or can't be loaded, the resulting GtkPicture is empty.
     * <p>
     * If you need to detect failures to load the file, use gdk_texture_new_from_file() to load the file yourself,
     * then create the GtkPicture from the texture.
     *
     * @param file A GFile
     *             <p>
     *             The argument can be NULL.
     */
    public GtkPicture(GFile file) {
        super(library.gtk_picture_new_for_file(pointerOrNull(file)));
    }

    /**
     * Creates a new GtkPicture displaying the file filename.
     * <p>
     * This is a utility function that calls gtk_picture_new_for_file(). See that function for details.
     *
     * @param file File representing the Picture
     */
    public GtkPicture(File file) {
        super(library.gtk_picture_new_for_filename(file.getAbsolutePath()));
    }

    /**
     * Creates a new GtkPicture displaying paintable.
     * <p>
     * The GtkPicture will track changes to the paintable and update its size and contents in response to it.
     *
     * @param paintable A GdkPaintable
     *                  <p>
     *                  The argument can be NULL.
     */
    public GtkPicture(GdkPaintable paintable) {
        super(library.gtk_picture_new_for_paintable(pointerOrNull(paintable)));
    }

    /**
     * Creates a new GtkPicture displaying pixbuf.
     * <p>
     * This is a utility function that calls gtk_picture_new_for_paintable(), See that function for details.
     * <p>
     * The pixbuf must not be modified after passing it to this function.
     *
     * @param pixbuf A GdkPixbuf
     *               <p>
     *               The argument can be NULL.
     */
    public GtkPicture(GdkPixbuf pixbuf) {
        super(library.gtk_picture_new_for_pixbuf(pointerOrNull(pixbuf)));
    }

    /**
     * Returns whether the GtkPicture preserves its contents aspect ratio.
     *
     * @return TRUE if the self tries to keep the contents' aspect ratio.
     * @deprecated Deprecated since: 4.8. Use gtk_picture_get_content_fit() instead.
     */
    @Deprecated
    public boolean doesKeepAspectRatio() {
        return library.gtk_picture_get_keep_aspect_ratio(getCReference());
    }

    /**
     * Gets the alternative textual description of the picture.
     * <p>
     * The returned string will be NONE if the picture cannot be described textually.
     *
     * @return The alternative textual description of self, if defined
     */
    public Option<String> getAlternativeText() {
        return new Option<>(library.gtk_picture_get_alternative_text(getCReference()));
    }

    /**
     * Sets an alternative textual description for the picture contents.
     * <p>
     * It is equivalent to the "alt" attribute for images on websites.
     * <p>
     * This text will be made available to accessibility tools.
     * <p>
     * If the picture cannot be described textually, set this property to NULL.
     *
     * @param altText A textual description of the contents.
     *                <p>
     *                The argument can be NULL.
     */
    public void setAlternativeText(String altText) {
        library.gtk_picture_set_alternative_text(getCReference(), altText);
    }

    /**
     * Gets the GFile currently displayed if self is displaying a file.
     * <p>
     * If self is not displaying a file, for example when gtk_picture_set_paintable() was used, then NONE is returned.
     *
     * @return The picture resource as a File
     */
    public Option<File> getFile() {
        Option<GFile> gFile = getGFile();
        if (gFile.isDefined()) {
            Option<String> path = gFile.get().getPath();
            if (path.isDefined()) {
                return new Option<>(Paths.get(path.get()).toFile());
            }
        }
        return Option.NONE;
    }

    /**
     * Gets the GFile currently displayed if self is displaying a file.
     * <p>
     * If self is not displaying a file, for example when gtk_picture_set_paintable() was used, then NONE is returned.
     *
     * @return The picture resource as a GFile
     */
    public Option<GFile> getGFile() {
        Option<Pointer> p = new Option<>(library.gtk_picture_get_file(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GFile(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Makes self load and display file.
     * <p>
     * See gtk_picture_new_for_file() for details.
     *
     * @param f A GFile
     *          <p>
     *          The argument can be NULL.
     */
    public void setFile(GFile f) {
        library.gtk_picture_set_file(getCReference(), pointerOrNull(f));
    }

    /**
     * Makes self load and display file.
     * <p>
     * See gtk_picture_new_for_file() for details.
     *
     * @param f A File
     *          <p>
     *          The argument can be NULL.
     */
    public void setFile(File f) {
        if (f != null) {
            library.gtk_picture_set_file(getCReference(), new GFile(f).getCReference());
        } else {
            library.gtk_picture_set_file(getCReference(), Pointer.NULL);
        }
    }

    /**
     * Gets the GdkPaintable being displayed by the GtkPicture.
     *
     * @return The displayed paintable, if defined
     */
    public Option<GdkPaintable> getPaintable() {
        Option<Pointer> p = new Option<>(library.gtk_picture_get_paintable(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GdkPaintable) JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
        }
        return Option.NONE;
    }

    /**
     * Makes self display the given paintable.
     * <p>
     * If paintable is NULL, nothing will be displayed.
     * <p>
     * See gtk_picture_new_for_paintable() for details.
     *
     * @param paintable A GdkPaintable
     *                  <p>
     *                  The argument can be NULL.
     */
    public void setPaintable(GdkPaintable paintable) {
        library.gtk_picture_set_paintable(getCReference(), pointerOrNull(paintable));
    }

    /**
     * Returns whether the GtkPicture respects its contents size.
     *
     * @return TRUE if the picture can be made smaller than its contents.
     */
    public boolean isShrinkable() {
        return library.gtk_picture_get_can_shrink(getCReference());
    }

    /**
     * If set to TRUE, the self can be made smaller than its contents.
     * <p>
     * The contents will then be scaled down when rendering.
     * <p>
     * If you want to still force a minimum size manually, consider using gtk_widget_set_size_request().
     * <p>
     * Also, of note, is that a similar function for growing does not exist because the grow-behavior can be controlled
     * via gtk_widget_set_halign() and gtk_widget_set_valign().
     *
     * @param shrinkable If self can be made smaller than its contents.
     */
    public void setShrinkable(boolean shrinkable) {
        library.gtk_picture_set_can_shrink(getCReference(), shrinkable);
    }

    /**
     * Sets a GtkPicture to show a GdkPixbuf.
     * <p>
     * See gtk_picture_new_for_pixbuf() for details.
     * <p>
     * This is a utility function that calls gtk_picture_set_paintable().
     *
     * @param pixbuf A GdkPixbuf
     *               <p>
     *               The argument can be NULL.
     */
    public void setPixbuf(GdkPixbuf pixbuf) {
        library.gtk_picture_set_pixbuf(getCReference(), pointerOrNull(pixbuf));
    }

    /**
     * If set to TRUE, the self will render its contents according to their aspect ratio.
     * <p>
     * That means that empty space may show up at the top/bottom or left/right of self.
     * <p>
     * If set to FALSE or if the contents provide no aspect ratio, the contents will be stretched over the picture's
     * whole area.
     *
     * @param keep Whether to keep aspect ratio.
     * @deprecated Deprecated since: 4.8
     */
    @Deprecated
    public void shouldKeepAspectRatio(boolean keep) {
        library.gtk_picture_set_keep_aspect_ratio(getCReference(), keep);
    }

    protected static class GtkPictureLibrary extends GtkWidgetLibrary {

        static {
            Native.register("gtk-4");
        }

        /**
         * Gets the alternative textual description of the picture.
         * <p>
         * The returned string will be NULL if the picture cannot be described textually.
         *
         * @param self self
         * @return The alternative textual description of self.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_picture_get_alternative_text(Pointer self);

        /**
         * Returns whether the GtkPicture respects its contents size.
         *
         * @param self self
         * @return TRUE if the picture can be made smaller than its contents.
         */
        public native boolean gtk_picture_get_can_shrink(Pointer self);

        /**
         * Gets the GFile currently displayed if self is displaying a file.
         * <p>
         * If self is not displaying a file, for example when gtk_picture_set_paintable() was used, then NULL is
         * returned.
         *
         * @param self self
         * @return The GFile displayed by self. Type: GFile
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_picture_get_file(Pointer self);

        /**
         * Returns whether the GtkPicture preserves its contents aspect ratio.
         * <p>
         * Use gtk_picture_get_content_fit() instead. This will now return FALSE only if GtkPicture:content-fit is
         * GTK_CONTENT_FIT_FILL. Returns TRUE otherwise.
         *
         * @param self self
         * @return TRUE if the self tries to keep the contents' aspect ratio.
         * @deprecated Deprecated since: 4.8. Use gtk_picture_get_content_fit() instead.
         */
        @Deprecated
        public native boolean gtk_picture_get_keep_aspect_ratio(Pointer self);

        /**
         * Gets the GdkPaintable being displayed by the GtkPicture.
         *
         * @param self self
         * @return The displayed paintable. Type: GdkPaintable
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_picture_get_paintable(Pointer self);

        /**
         * Creates a new empty GtkPicture widget.
         *
         * @return A newly created GtkPicture widget. Type: GtkPicture
         */
        public native Pointer gtk_picture_new();

        /**
         * Creates a new GtkPicture displaying the given file.
         * <p>
         * If the file isn't found or can't be loaded, the resulting GtkPicture is empty.
         * <p>
         * If you need to detect failures to load the file, use gdk_texture_new_from_file() to load the file yourself,
         * then create the GtkPicture from the texture.
         *
         * @param file A GFile. Type: GFile
         *             <p>
         *             The argument can be NULL.
         * @return A new GtkPicture. Type: GtkPicture
         */
        public native Pointer gtk_picture_new_for_file(Pointer file);

        /**
         * Creates a new GtkPicture displaying the file filename.
         * <p>
         * This is a utility function that calls gtk_picture_new_for_file(). See that function for details.
         *
         * @param filename A filename.
         *                 <p>
         *                 The argument can be NULL.
         * @return A new GtkPicture. Type: GtkPicture
         */
        public native Pointer gtk_picture_new_for_filename(String filename);

        /**
         * Creates a new GtkPicture displaying paintable.
         * <p>
         * The GtkPicture will track changes to the paintable and update its size and contents in response to it.
         *
         * @param paintable A GdkPaintable. Type: GdkPaintable
         *                  <p>
         *                  The argument can be NULL.
         * @return A new GtkPicture. Type: GtkPicture
         */
        public native Pointer gtk_picture_new_for_paintable(Pointer paintable);

        /**
         * Creates a new GtkPicture displaying pixbuf.
         * <p>
         * This is a utility function that calls gtk_picture_new_for_paintable(), See that function for details.
         * <p>
         * The pixbuf must not be modified after passing it to this function.
         *
         * @param pixbuf A GdkPixbuf. Type: GdkPixbuf
         *               <p>
         *               The argument can be NULL.
         * @return A new GtkPicture. Type: GtkPicture
         */
        public native Pointer gtk_picture_new_for_pixbuf(Pointer pixbuf);

        /**
         * Sets an alternative textual description for the picture contents.
         * <p>
         * It is equivalent to the "alt" attribute for images on websites.
         * <p>
         * This text will be made available to accessibility tools.
         * <p>
         * If the picture cannot be described textually, set this property to NULL.
         *
         * @param self             self
         * @param alternative_text A textual description of the contents.
         *                         <p>
         *                         The argument can be NULL.
         */
        public native void gtk_picture_set_alternative_text(Pointer self, String alternative_text);

        /**
         * If set to TRUE, the self can be made smaller than its contents.
         * <p>
         * The contents will then be scaled down when rendering.
         * <p>
         * If you want to still force a minimum size manually, consider using gtk_widget_set_size_request().
         * <p>
         * Also, of note, is that a similar function for growing does not exist because the grow-behavior can be
         * controlled via gtk_widget_set_halign() and gtk_widget_set_valign().
         *
         * @param self       self
         * @param can_shrink If self can be made smaller than its contents.
         */
        public native void gtk_picture_set_can_shrink(Pointer self, boolean can_shrink);

        /**
         * Makes self load and display file.
         * <p>
         * See gtk_picture_new_for_file() for details.
         *
         * @param self self
         * @param file A GFile. Type: GFile
         *             <p>
         *             The argument can be NULL.
         */
        public native void gtk_picture_set_file(Pointer self, Pointer file);

        /**
         * If set to TRUE, the self will render its contents according to their aspect ratio.
         * <p>
         * That means that empty space may show up at the top/bottom or left/right of self.
         * <p>
         * If set to FALSE or if the contents provide no aspect ratio, the contents will be stretched over the picture's
         * whole area.
         * <p>
         * Deprecated since: 4.8
         * <p>
         * Use gtk_picture_set_content_fit() instead. If still used, this method will always set the G
         * tkPicture:content-fit property to GTK_CONTENT_FIT_CONTAIN if keep_aspect_ratio is true, otherwise it will
         * set it to GTK_CONTENT_FIT_FILL.
         *
         * @param self              self
         * @param keep_aspect_ratio Whether to keep aspect ratio.
         * @deprecated Deprecated since: 4.8. Use gtk_picture_set_content_fit() instead.
         */
        @Deprecated
        public native void gtk_picture_set_keep_aspect_ratio(Pointer self, boolean keep_aspect_ratio);

        /**
         * Makes self display the given paintable.
         * <p>
         * If paintable is NULL, nothing will be displayed.
         * <p>
         * See gtk_picture_new_for_paintable() for details.
         *
         * @param self      self
         * @param paintable A GdkPaintable. Type: GdkPaintable
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_picture_set_paintable(Pointer self, Pointer paintable);

        /**
         * Sets a GtkPicture to show a GdkPixbuf.
         * <p>
         * See gtk_picture_new_for_pixbuf() for details.
         * <p>
         * This is a utility function that calls gtk_picture_set_paintable().
         *
         * @param self   self
         * @param pixbuf A GdkPixbuf. Type: GdkPixbuf
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_picture_set_pixbuf(Pointer self, Pointer pixbuf);
    }

}
