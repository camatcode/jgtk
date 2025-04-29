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

import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;


public class GtkStackPage extends JGTKObject implements GtkAccessible {
    public GtkStackPage(Pointer ref) {
        super(ref);
    }

    /**
     * Returns whether the page is marked as "needs attention".
     *
     * @return Whether the page requires the user attention.
     *         <p>
     *         This is used by the GtkStackSwitcher to change the appearance of the corresponding button when a page
     *         needs
     *         attention and is not the current one.
     */
    public boolean doesNeedAttention() {
        return library.gtk_stack_page_get_needs_attention(getCReference());
    }

    /**
     * Gets whether underlines in the page title indicate mnemonics.
     *
     * @return If TRUE, an underline in the title indicates a mnemonic.
     */
    public boolean doesTitleUseMnemonics() {
        return library.gtk_stack_page_get_use_underline(getCReference());
    }

    /**
     * Returns the stack child to which self belongs.
     *
     * @return The child to which self belongs.
     */
    public GtkWidget getChild() {
        return (GtkWidget) JGTKObject.newObjectFromType(library.gtk_stack_page_get_child(getCReference()), GtkWidget.class);
    }

    /**
     * Returns the icon name of the page.
     *
     * @return The icon name of the child page, if defined
     */
    public Option<String> getIconName() {
        return new Option<>(library.gtk_stack_page_get_icon_name(getCReference()));
    }

    /**
     * Sets the icon name of the page.
     *
     * @param iconName The icon name of the child page.
     */
    public void setIconName(String iconName) {
        library.gtk_stack_page_set_icon_name(getCReference(), iconName);
    }

    /**
     * Returns the name of the page.
     *
     * @return The name of the child page, if defined
     */
    public Option<String> getName() {
        return new Option<>(library.gtk_stack_page_get_name(getCReference()));
    }

    /**
     * Sets the name of the page.
     *
     * @param name The name of the child page.
     */
    public void setName(String name) {
        library.gtk_stack_page_set_name(getCReference(), name);
    }

    /**
     * Gets the page title.
     *
     * @return The title of the child page, if defined
     */
    public Option<String> getTitle() {
        return new Option<>(library.gtk_stack_page_get_title(getCReference()));
    }

    /**
     * Sets the page title.
     *
     * @param title The title of the child page.
     */
    public void setTitle(String title) {
        library.gtk_stack_page_set_title(getCReference(), title);
    }

    /**
     * Returns whether page is visible in its GtkStack.
     * <p>
     * This is independent to the visible property of its widget
     *
     * @return TRUE if page is visible.
     */
    public boolean isVisible() {
        return library.gtk_stack_page_get_visible(getCReference());
    }

    /**
     * Sets whether page is visible in its GtkStack.
     * This is independent to the visible property of its widget
     *
     * @param isVisible Whether this page is visible.
     */
    public void setVisible(boolean isVisible) {
        library.gtk_stack_page_set_visible(getCReference(), isVisible);
    }

    /**
     * Sets whether the page is marked as "needs attention".
     *
     * @param doesNeedAttention Whether the page requires the user attention.
     *                          <p>
     *                          This is used by the GtkStackSwitcher to change the appearance of the corresponding
     *                          button when a page needs attention and is not the current one.
     */
    public void markNeedsAttention(boolean doesNeedAttention) {
        library.gtk_stack_page_set_needs_attention(getCReference(), doesNeedAttention);
    }

    /**
     * Sets whether underlines in the page title indicate mnemonics.
     *
     * @param doesTitleUseMnemonics If TRUE, an underline in the title indicates a mnemonic.
     */
    public void shouldTitleUseMnemonics(boolean doesTitleUseMnemonics) {
        library.gtk_stack_page_set_use_underline(getCReference(), doesTitleUseMnemonics);
    }
}
