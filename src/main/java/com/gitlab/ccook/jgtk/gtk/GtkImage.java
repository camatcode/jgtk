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
import com.gitlab.ccook.jgtk.enums.GtkIconSize;
import com.gitlab.ccook.jgtk.enums.GtkImageType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import java.io.File;

/**
 * The GtkImage widget displays an image.
 * <p>
 * Various kinds of object can be displayed as an image; most typically, you would load a GdkTexture from a file,
 * using the convenience function gtk_image_new_from_file(), for instance:
 * <p>
 * GtkWidget *image = gtk_image_new_from_file ("file.png");
 * Copy
 * If the file isn't loaded successfully, the image will contain a "broken image" icon similar to that used in many
 * web browsers.
 * <p>
 * If you want to handle errors in loading the file yourself, for example by displaying an error message, then load
 * the image with gdk_texture_new_from_file(), then create the GtkImage with gtk_image_new_from_paintable().
 * <p>
 * Sometimes an application will want to avoid depending on external data files, such as image files. See the
 * documentation of GResource inside GIO, for details. In this case, GtkImage:resource, gtk_image_new_from_resource(),
 * and gtk_image_set_from_resource() should be used.
 * <p>
 * GtkImage displays its image as an icon, with a size that is determined by the application. See GtkPicture if you
 * want to show an image at is actual size.
 */
@SuppressWarnings("unchecked")
public class GtkImage extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget {

    private static final GtkImageLibrary library = new GtkImageLibrary();

    public GtkImage(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new empty GtkImage widget.
     */
    public GtkImage() {
        super(library.gtk_image_new());
    }

    /**
     * Creates a new GtkImage displaying the file filename.
     * <p>
     * If the file isn't found or can't be loaded, the resulting GtkImage will display a "broken image" icon.
     * This function never returns NULL, it always returns a valid GtkImage widget.
     * <p>
     * If you need to detect failures to load the file, use gdk_texture_new_from_file() to load the file yourself,
     * then create the GtkImage from the texture.
     * <p>
     * The storage type (see gtk_image_get_storage_type()) of the returned image is not defined, it will be whatever is
     * appropriate for displaying the file.
     *
     * @param f A file to use as the GtkImage source
     */
    public GtkImage(File f) {
        super(library.gtk_image_new_from_file(f.getAbsolutePath()));
    }

    /**
     * Creates a GtkImage displaying an icon from the current icon theme.
     * <p>
     * If the icon name isn't known, a "broken image" icon will be displayed instead. If the current icon theme is c
     * hanged, the icon will be updated appropriately.
     *
     * @param icon An icon.
     */
    public GtkImage(GIcon icon) {
        super(library.gtk_image_new_from_gicon(icon.getCReference()));
    }

    /**
     * Creates a GtkImage displaying an icon from the current icon theme.
     * <p>
     * If the icon name isn't known, a "broken image" icon will be displayed instead. If the current icon theme is
     * changed, the icon will be updated appropriately.
     *
     * @param iconName An icon name.
     */
    public GtkImage(IconName iconName) {
        super(library.gtk_image_new_from_icon_name(iconName.getIconName()));
    }

    /**
     * Creates a new GtkImage displaying paintable.
     * <p>
     * The GtkImage does not assume a reference to the paintable; you still need to
     * dereference it if you own references. GtkImage will add its own reference rather than adopting yours.
     * <p>
     * The GtkImage will track changes to the paintable and update its size and contents in response to it.
     *
     * @param paintable A GdkPaintable
     *                  <p>
     *                  The argument can be NULL.
     */
    public GtkImage(GdkPaintable paintable) {
        super(library.gtk_image_new_from_paintable(pointerOrNull(paintable)));
    }

    /**
     * Creates a new GtkImage displaying pixbuf.
     * <p>
     * The GtkImage does not assume a reference to the pixbuf; you still need to dereference it if you own references.
     * GtkImage will add its own reference rather than adopting yours.
     * <p>
     * This is a helper for gtk_image_new_from_paintable(), and you can't get back the exact pixbuf once this is called,
     * only a texture.
     * <p>
     * Note that this function just creates an GtkImage from the pixbuf. The GtkImage created will not react to state
     * changes. Should you want that, you should use gtk_image_new_from_icon_name().
     *
     * @param pixBuf A GdkPixbuf
     *               <p>
     *               The argument can be NULL.
     */
    public GtkImage(GdkPixbuf pixBuf) {
        super(library.gtk_image_new_from_pixbuf(pointerOrNull(pixBuf)));
    }

    /**
     * Resets the image to be empty.
     */
    public void clear() {
        library.gtk_image_clear(getCReference());
    }

    /**
     * Gets the pixel size used for named icons.
     *
     * @return The pixel size used for named icons.
     */
    public Option<Integer> getCustomPixelSize() {
        int val = library.gtk_image_get_pixel_size(getCReference());
        if (val >= 0) {
            return new Option<>(val);
        }
        return Option.NONE;
    }

    /**
     * Sets the pixel size to use for named icons.
     * <p>
     * If the pixel size is set to a value != -1, it is used instead of the icon size set by
     * gtk_image_set_from_icon_name().
     * <p>
     * If pixel size is -1, restore back to defaults
     *
     * @param pixelSize The new pixel size.
     */
    public void setCustomPixelSize(int pixelSize) {
        library.gtk_image_set_pixel_size(getCReference(), Math.max(-1, pixelSize));
    }

    /**
     * Gets the GIcon being displayed by the GtkImage.
     * <p>
     * The storage type of the image must be GTK_IMAGE_EMPTY or GTK_IMAGE_GICON (see gtk_image_get_storage_type()).
     * The caller of this function does not own a reference to the returned GIcon.
     *
     * @return The data is owned by the instance, if defined
     */
    public Option<GIcon> getGIcon() {
        Option<Pointer> p = new Option<>(library.gtk_image_get_gicon(getCReference()));
        if (p.isDefined()) {
            return new Option<>(new GIcon(p.get()));
        }
        return Option.NONE;
    }

    /**
     * Gets the icon name and size being displayed by the GtkImage.
     * <p>
     * The storage type of the image must be GTK_IMAGE_EMPTY or GTK_IMAGE_ICON_NAME (see gtk_image_get_storage_type()).
     * The returned string is owned by the GtkImage and should not be freed.
     *
     * @return The icon name.
     */
    public Option<IconName> getIconName() {
        Option<String> iconName = new Option<>(library.gtk_image_get_icon_name(getCReference()));
        if (iconName.isDefined()) {
            return new Option<>(new IconName(iconName.get()));
        }
        return Option.NONE;
    }

    /**
     * Gets the icon size used by the image when rendering icons.
     *
     * @return The image size used by icons.
     */
    public GtkIconSize getIconSize() {
        return GtkIconSize.getSizeFromCValue(library.gtk_image_get_icon_size(getCReference()));
    }

    /**
     * Suggests an icon size to the theme for named icons.
     *
     * @param s The new icon size.
     */
    public void setIconSize(GtkIconSize s) {
        if (s != null) {
            library.gtk_image_set_icon_size(getCReference(), GtkIconSize.getCValueFromSize(s));
        }
    }

    /**
     * Gets the image GdkPaintable being displayed by the GtkImage.
     * <p>
     * The storage type of the image must be GTK_IMAGE_EMPTY or GTK_IMAGE_PAINTABLE (see gtk_image_get_storage_type()).
     * The caller of this function does not own a reference to the returned paintable.
     *
     * @return The displayed paintable, if defined
     */
    public Option<GdkPaintable> getPaintable() {
        Option<Pointer> p = new Option<>(library.gtk_image_get_paintable(getCReference()));
        if (p.isDefined()) {
            return new Option<>((GdkPaintable) JGTKObject.newObjectFromType(p.get(), JGTKObject.class));
        }
        return Option.NONE;
    }

    /**
     * Gets the type of representation being used by the GtkImage to store image data.
     * <p>
     * If the GtkImage has no image data, the return value will be GTK_IMAGE_EMPTY.
     *
     * @return Image representation being used.
     */
    public GtkImageType getStorageType() {
        return GtkImageType.getTypeFromCValue(library.gtk_image_get_storage_type(getCReference()));
    }

    /**
     * Sets a GtkImage to show a resource.
     *
     * @param f A resource.
     *          <p>
     *          The argument can be NULL.
     */
    public void setFromFile(File f) {
        if (f != null) {
            library.gtk_image_set_from_file(getCReference(), f.getAbsolutePath());
        } else {
            library.gtk_image_set_from_file(getCReference(), null);
        }
    }

    /**
     * Sets a GtkImage to show a GIcon.
     *
     * @param icon An icon.
     */
    public void setFromGIcon(GIcon icon) {
        if (icon != null) {
            library.gtk_image_set_from_gicon(getCReference(), icon.getCReference());
        }
    }

    /**
     * Sets a GtkImage to show a named icon.
     *
     * @param iconName An icon name.
     */
    public void setFromIconName(IconName iconName) {
        if (iconName != null) {
            library.gtk_image_set_from_icon_name(getCReference(), iconName.getIconName());
        }
    }

    /**
     * Sets a GtkImage to show a GdkPaintable.
     *
     * @param p A GdkPaintable
     *          <p>
     *          The argument can be NULL.
     */
    public void setFromPaintable(GdkPaintable p) {
        library.gtk_image_set_from_paintable(getCReference(), pointerOrNull(p));
    }

    /**
     * Sets a GtkImage to show a GdkPixbuf.
     * <p>
     * See gtk_image_new_from_pixbuf() for details.
     * <p>
     * Note: This is a helper for gtk_image_set_from_paintable(), and you can't get back the exact pixbuf once
     * this is called, only a paintable.
     *
     * @param b A GdkPixbuf or NULL
     */
    public void setFromPixbuf(GdkPixbuf b) {
        library.gtk_image_set_from_pixbuf(getCReference(), pointerOrNull(b));
    }

    protected static class GtkImageLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Resets the image to be empty.
         *
         * @param image self
         */
        public native void gtk_image_clear(Pointer image);

        /**
         * Gets the GIcon being displayed by the GtkImage.
         * <p>
         * The storage type of the image must be GTK_IMAGE_EMPTY or GTK_IMAGE_GICON (see gtk_image_get_storage_type()).
         * The caller of this function does not own a reference to the returned GIcon.
         *
         * @param image self
         * @return A GIcon. Type: GIcon
         */
        public native Pointer gtk_image_get_gicon(Pointer image);

        /**
         * Gets the icon name and size being displayed by the GtkImage.
         * <p>
         * The storage type of the image must be GTK_IMAGE_EMPTY or GTK_IMAGE_ICON_NAME
         * (see gtk_image_get_storage_type()). The returned string is owned by the GtkImage and should not be freed.
         *
         * @param image self
         * @return The icon name.
         *         <p>
         *         The return value can be NULL.
         */
        public native String gtk_image_get_icon_name(Pointer image);

        /**
         * Gets the icon size used by the image when rendering icons.
         *
         * @param image self
         * @return The image size used by icons.
         */
        public native int gtk_image_get_icon_size(Pointer image);

        /**
         * Gets the image GdkPaintable being displayed by the GtkImage.
         * <p>
         * The storage type of the image must be GTK_IMAGE_EMPTY or GTK_IMAGE_PAINTABLE (see
         * gtk_image_get_storage_type()). The caller of this function does not own a reference to the returned
         * paintable.
         *
         * @param image se;f
         * @return The displayed paintable. Type: GdkPaintable
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_image_get_paintable(Pointer image);

        /**
         * Gets the pixel size used for named icons.
         *
         * @param image self
         * @return The pixel size used for named icons.
         */
        public native int gtk_image_get_pixel_size(Pointer image);

        /**
         * Gets the type of representation being used by the GtkImage to store image data.
         * <p>
         * If the GtkImage has no image data, the return value will be GTK_IMAGE_EMPTY.
         *
         * @param image self
         * @return Image representation being used. Type: GtkImageType
         */
        public native int gtk_image_get_storage_type(Pointer image);

        /**
         * Creates a new empty GtkImage widget.
         *
         * @return A newly created GtkImage widget. Type: GtkImage
         */
        public native Pointer gtk_image_new();

        /**
         * Creates a new GtkImage displaying the file filename.
         * <p>
         * If the file isn't found or can't be loaded, the resulting GtkImage will display a "broken image" icon.
         * This function never returns NULL, it always returns a valid GtkImage widget.
         * <p>
         * If you need to detect failures to load the file, use gdk_texture_new_from_file() to load the file yourself,
         * then create the GtkImage from the texture.
         * <p>
         * The storage type (see gtk_image_get_storage_type()) of the returned image is not defined, it will be whatever
         * is appropriate for displaying the file.
         *
         * @param filename A filename.
         *                 <p>
         *                 The value is a file system path, using the OS encoding.
         * @return A new GtkImage. Type: GtkImage
         */
        public native Pointer gtk_image_new_from_file(String filename);

        /**
         * Creates a GtkImage displaying an icon from the current icon theme.
         * <p>
         * If the icon name isn't known, a "broken image" icon will be displayed instead. If the current icon theme is
         * changed, the icon will be updated appropriately.
         *
         * @param icon An icon. Type: GIcon
         * @return A new GtkImage displaying the themed icon. Type:GtkImage
         */
        public native Pointer gtk_image_new_from_gicon(Pointer icon);

        /**
         * Creates a GtkImage displaying an icon from the current icon theme.
         * <p>
         * If the icon name isn't known, a "broken image" icon will be displayed instead. If the current icon theme is
         * changed, the icon will be updated appropriately.
         *
         * @param icon_name An icon name.
         *                  <p>
         *                  The argument can be NULL.
         * @return A new GtkImage displaying the themed icon. Type: GtkImage
         */
        public native Pointer gtk_image_new_from_icon_name(String icon_name);

        /**
         * Creates a new GtkImage displaying paintable.
         * <p>
         * The GtkImage does not assume a reference to the paintable; you still need to dereference it if you own
         * references.
         * GtkImage will add its own reference rather than adopting yours.
         * <p>
         * The GtkImage will track changes to the paintable and update its size and contents in response to it.
         *
         * @param paintable A GdkPaintable. Type: GdkPaintable
         *                  <p>
         *                  The argument can be NULL.
         * @return A new GtkImage. Type: GtkImage
         */
        public native Pointer gtk_image_new_from_paintable(Pointer paintable);

        /**
         * Creates a new GtkImage displaying pixbuf.
         * <p>
         * The GtkImage does not assume a reference to the pixbuf; you still need to dereference it if you own
         * references.
         * GtkImage will add its own reference rather than adopting yours.
         * <p>
         * This is a helper for gtk_image_new_from_paintable(), and you can't get back the exact pixbuf once this
         * is called, only a texture.
         * <p>
         * Note that this function just creates an GtkImage from the pixbuf. The GtkImage created will not react to
         * state changes. Should you want that, you should use gtk_image_new_from_icon_name().
         *
         * @param pixbuf A GdkPixbuf. Type: GdkPixbuf
         *               <p>
         *               The argument can be NULL.
         * @return A new GtkImage. Type: GtkImage
         */
        public native Pointer gtk_image_new_from_pixbuf(Pointer pixbuf);

        /**
         * Creates a new GtkImage displaying the resource file resource_path.
         * <p>
         * If the file isn't found or can't be loaded, the resulting GtkImage will display a "broken image" icon.
         * This function never returns NULL, it always returns a valid GtkImage widget.
         * <p>
         * If you need to detect failures to load the file, use gdk_pixbuf_new_from_file() to load the file yourself,
         * then create the GtkImage from the pixbuf.
         * <p>
         * The storage type (see gtk_image_get_storage_type()) of the returned image is not defined, it will be whatever
         * is appropriate for displaying the file.
         *
         * @param resource_path A resource path.
         * @return A new GtkImage. Type: GtkImage
         */
        public native Pointer gtk_image_new_from_resource(String resource_path);

        /**
         * Sets a GtkImage to show a file.
         * <p>
         * See gtk_image_new_from_file() for details.
         *
         * @param image     self
         * @param file_name A filename.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_image_set_from_file(Pointer image, String file_name);

        /**
         * Sets a GtkImage to show a GIcon.
         * <p>
         * See gtk_image_new_from_gicon() for details.
         *
         * @param image self
         * @param icon  An icon.
         */
        public native void gtk_image_set_from_gicon(Pointer image, Pointer icon);

        /**
         * Sets a GtkImage to show a named icon.
         * <p>
         * See gtk_image_new_from_icon_name() for details.
         *
         * @param image     self
         * @param icon_name An icon name.
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_image_set_from_icon_name(Pointer image, String icon_name);

        /**
         * Sets a GtkImage to show a GdkPaintable.
         * <p>
         * See gtk_image_new_from_paintable() for details.
         *
         * @param image     self
         * @param paintable A GdkPaintable. Type: GdkPaintable
         *                  <p>
         *                  The argument can be NULL.
         */
        public native void gtk_image_set_from_paintable(Pointer image, Pointer paintable);

        /**
         * Sets a GtkImage to show a GdkPixbuf.
         * <p>
         * See gtk_image_new_from_pixbuf() for details.
         * <p>
         * Note: This is a helper for gtk_image_set_from_paintable(), and you can't get back the exact pixbuf once
         * this is called, only a paintable.
         *
         * @param image  self
         * @param pixbuf A GdkPixbuf or NULL. Type: GdkPixbuf
         *               <p>
         *               The argument can be NULL.
         */
        public native void gtk_image_set_from_pixbuf(Pointer image, Pointer pixbuf);

        /**
         * Sets a GtkImage to show a resource.
         * <p>
         * See gtk_image_new_from_resource() for details.
         *
         * @param image         self
         * @param resource_path A resource path.
         *                      <p>
         *                      The argument can be NULL.
         */
        public native void gtk_image_set_from_resource(Pointer image, String resource_path);

        /**
         * Suggests an icon size to the theme for named icons.
         *
         * @param image     self
         * @param icon_size The new icon size. Type: GtkIconSize
         */
        public native void gtk_image_set_icon_size(Pointer image, int icon_size);

        /**
         * Sets the pixel size to use for named icons.
         * <p>
         * If the pixel size is set to a value != -1, it is used instead of the icon size set by
         * gtk_image_set_from_icon_name().
         *
         * @param image      self
         * @param pixel_size The new pixel size.
         */
        public native void gtk_image_set_pixel_size(Pointer image, int pixel_size);
    }
}
