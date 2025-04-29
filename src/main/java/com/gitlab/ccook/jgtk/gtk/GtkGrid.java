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

import com.gitlab.ccook.jgtk.GtkWidget;
import com.gitlab.ccook.jgtk.JGTKObject;
import com.gitlab.ccook.jgtk.enums.GtkBaselinePosition;
import com.gitlab.ccook.jgtk.enums.GtkPositionType;
import com.gitlab.ccook.jgtk.interfaces.GtkAccessible;
import com.gitlab.ccook.jgtk.interfaces.GtkBuildable;
import com.gitlab.ccook.jgtk.interfaces.GtkConstraintTarget;
import com.gitlab.ccook.jgtk.interfaces.GtkOrientable;
import com.gitlab.ccook.util.Option;
import com.gitlab.ccook.util.Pair;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;


/**
 * GtkGrid is a container which arranges its child widgets in rows and columns.
 * <p>
 * An example GtkGrid
 * <p>
 * It supports arbitrary positions and horizontal/vertical spans.
 * <p>
 * Children are added using gtk_grid_attach(). They can span multiple rows or columns. It is also possible to add a
 * child next to an existing child, using gtk_grid_attach_next_to(). To remove a child from the grid, use
 * gtk_grid_remove().
 * <p>
 * The behaviour of GtkGrid when several children occupy the same grid cell is undefined.
 */
@SuppressWarnings({"unchecked"})
public class GtkGrid extends GtkWidget implements GtkAccessible, GtkBuildable, GtkConstraintTarget, GtkOrientable {

    private static final GtkGridLibrary library = new GtkGridLibrary();

    public GtkGrid(Pointer ref) {
        super(ref);
    }

    /**
     * Creates a new grid widget.
     */
    public GtkGrid() {
        super(library.gtk_grid_new());
    }

    /**
     * Returns whether all columns of grid have the same width.
     *
     * @return Whether all columns of grid have the same width.
     */
    public boolean areColumnsHomogeneous() {
        return library.gtk_grid_get_column_homogeneous(getCReference());
    }

    /**
     * Returns whether all rows of grid have the same height.
     *
     * @return Whether all rows of grid have the same height.
     */
    public boolean areRowsHomogeneous() {
        return library.gtk_grid_get_row_homogeneous(getCReference());
    }

    /**
     * Adds a widget to the grid.
     * <p>
     * The position of child is determined by column and row. The number of "cells" that child will occupy is
     * determined by width and height.
     *
     * @param w      The widget to add.
     * @param column The column number to attach the left side of child to.
     * @param row    The row number to attach the top side of child to.
     * @param width  The number of columns that child will span
     * @param height The number of rows that child will span.
     */
    public void attach(GtkWidget w, int column, int row, int width, int height) {
        if (w != null) {
            library.gtk_grid_attach(getCReference(), w.getCReference(), column, row, width, height);
        }
    }

    /**
     * Adds a widget to the grid.
     * <p>
     * The widget is placed next to sibling, on the side determined by side. When sibling is NULL, the widget is placed
     * in row (for left or right placement) or column 0 (for top or bottom placement), at the end indicated by side.
     * <p>
     * Attaching widgets labeled [1], [2], [3] with sibling` == `NULL and side` == `GTK_POS_LEFT yields a layout of
     * [3][2][1].
     *
     * @param child   The widget to add.
     * @param sibling The child of grid that child will be placed next to, or NULL to place child at the beginning or
     *                end.
     *                <p>
     *                The argument can be NULL.
     * @param side    The side of sibling that child is positioned next to.
     * @param width   The number of columns that child will span.
     * @param height  The number of rows that child will span.
     */
    public void attachNextTo(GtkWidget child, GtkWidget sibling, GtkPositionType side, int width, int height) {
        if (child != null) {
            library.gtk_grid_attach_next_to(getCReference(), child.getCReference(), pointerOrNull(sibling), GtkPositionType.getCValueFromType(side), width, height);
        }
    }

    /**
     * Returns which row defines the global baseline of grid.
     *
     * @return The row index defining the global baseline.
     */
    public int getBaselineRow() {
        return library.gtk_grid_get_baseline_row(getCReference());
    }

    /**
     * Sets which row defines the global baseline for the entire grid.
     * <p>
     * Each row in the grid can have its own local baseline, but only one of those is global, meaning it will be the
     * baseline in the parent of the grid.
     *
     * @param row The row index.
     */
    public void setBaselineRow(int row) {
        library.gtk_grid_set_baseline_row(getCReference(), row);
    }

    /**
     * Gets the child of grid whose area covers the grid cell at column, row.
     *
     * @param column The left edge of the cell.
     * @param row    The top edge of the cell.
     * @return The child at the given position, if defined
     */
    public Option<GtkWidget> getChildAt(int column, int row) {
        Option<Pointer> p = new Option<>(library.gtk_grid_get_child_at(getCReference(), column, row));
        if (p.isDefined()) {
            return new Option<>((GtkWidget) JGTKObject.newObjectFromType(p.get(), GtkWidget.class));
        }
        return Option.NONE;
    }

    /**
     * Returns the amount of space between the columns of grid.
     *
     * @return The column spacing of grid.
     */
    public int getColumnSpacing() {
        return library.gtk_grid_get_column_spacing(getCReference());
    }

    /**
     * Sets the amount of space between columns of grid.
     *
     * @param spacing The amount of space to insert between columns.
     */
    public void setColumnSpacing(int spacing) {
        spacing = Math.max(0, spacing);
        library.gtk_grid_set_column_spacing(getCReference(), spacing);
    }

    /**
     * Returns the baseline position of row.
     *
     * @param row A row index.
     * @return The baseline position of row.
     */
    public GtkBaselinePosition getRowBaselinePosition(int row) {
        return GtkBaselinePosition.getPositionFromCValue(library.gtk_grid_get_row_baseline_position(getCReference(), row));
    }

    /**
     * Returns the amount of space between the rows of grid.
     *
     * @return The row spacing of grid.
     */
    public int getRowSpacing() {
        return library.gtk_grid_get_row_spacing(getCReference());
    }

    /**
     * Sets the amount of space between rows of grid.
     *
     * @param spacing The amount of space to insert between rows.
     */
    public void setRowSpacing(int spacing) {
        spacing = Math.max(0, spacing);
        library.gtk_grid_set_row_spacing(getCReference(), spacing);
    }

    /**
     * Inserts a column at the specified position.
     * <p>
     * Children which are attached at or to the right of this position are moved one column to the right.
     * Children which span across this position are grown to span the new column.
     *
     * @param columnPosition The position to insert the column at.
     */
    public void insertColumn(int columnPosition) {
        library.gtk_grid_insert_column(getCReference(), columnPosition);
    }

    /**
     * Inserts a row or column at the specified position.
     * <p>
     * The new row or column is placed next to sibling, on the side determined by side. If side is GTK_POS_TOP or
     * GTK_POS_BOTTOM, a row is inserted. If side is GTK_POS_LEFT of GTK_POS_RIGHT, a column is inserted.
     *
     * @param sibling      The child of grid that the new row or column will be placed next to.
     * @param positionType The side of sibling that child is positioned next to.
     */
    public void insertNextTo(GtkWidget sibling, GtkPositionType positionType) {
        if (sibling != null) {
            library.gtk_grid_insert_next_to(getCReference(), sibling.getCReference(), GtkPositionType.getCValueFromType(positionType));
        }
    }

    /**
     * Inserts a row at the specified position.
     * <p>
     * Children which are attached at or below this position are moved one row down. Children which span across this
     * position are grown to span the new row.
     *
     * @param rowPosition The position to insert the row at.
     */
    public void insertRow(int rowPosition) {
        library.gtk_grid_insert_row(getCReference(), rowPosition);
    }

    /**
     * Queries the attach-points and spans of child inside the given GtkGrid.
     *
     * @param child A GtkWidget child of grid.
     * @return If defined, (Pair(column, row), Pair(width,height)) where
     *         column = The column used to attach the left side of child.
     *         row = The row used to attach the top side of child.
     *         width = The number of columns child spans.
     *         height = The number of rows child spans.
     */
    public Option<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> queryChild(GtkWidget child) {
        if (child != null && getChildren().contains(child)) {
            PointerByReference column = new PointerByReference();
            PointerByReference row = new PointerByReference();
            PointerByReference width = new PointerByReference();
            PointerByReference height = new PointerByReference();
            library.gtk_grid_query_child(getCReference(), child.getCReference(), column, row, width, height);
            return new Option<>(new Pair<>(new Pair<>(column.getPointer().getInt(0), row.getPointer().getInt(0)), new Pair<>(width.getPointer().getInt(0), height.getPointer().getInt(0))));

        }
        return Option.NONE;
    }

    /**
     * Removes a child from grid.
     * <p>
     * The child must have been added with gtk_grid_attach() or gtk_grid_attach_next_to().
     *
     * @param child The child widget to remove.
     */
    public void remove(GtkWidget child) {
        if (child != null) {
            library.gtk_grid_remove(getCReference(), child.getCReference());
        }
    }

    /**
     * Removes a column from the grid.
     * <p>
     * Children that are placed in this column are removed, spanning children that overlap this column have their width
     * reduced by one, and children after the column are moved to the left.
     *
     * @param columnPosition The position of the column to remove.
     */
    public void removeColumn(int columnPosition) {
        library.gtk_grid_remove_column(getCReference(), columnPosition);
    }

    /**
     * Removes a row from the grid.
     * <p>
     * Children that are placed in this row are removed, spanning children that overlap this row have their height
     * reduced by one, and children below the row are moved up.
     *
     * @param rowPosition The position of the row to remove.
     */
    public void removeRow(int rowPosition) {
        library.gtk_grid_remove_row(getCReference(), rowPosition);
    }

    /**
     * Sets whether all columns of grid will have the same width.
     *
     * @param areHomogeneous TRUE to make columns have the same width.
     */
    public void setColumnsHomogeneous(boolean areHomogeneous) {
        library.gtk_grid_set_column_homogeneous(getCReference(), areHomogeneous);
    }

    /**
     * Sets how the baseline should be positioned on row of the grid, in case that row is assigned more space than is
     * requested.
     * <p>
     * The default baseline position is GTK_BASELINE_POSITION_CENTER.
     *
     * @param row A row index.
     * @param pos A GtkBaselinePosition
     */
    public void setRowBaselinePosition(int row, GtkBaselinePosition pos) {
        library.gtk_grid_set_row_baseline_position(getCReference(), row, pos.getCValue());
    }

    /**
     * Sets whether all rows of grid will have the same height.
     *
     * @param areHomogeneous TRUE to make rows the same height.
     */
    public void setRowsHomogeneous(boolean areHomogeneous) {
        library.gtk_grid_set_row_homogeneous(getCReference(), areHomogeneous);
    }

    protected static class GtkGridLibrary extends GtkWidgetLibrary {
        static {
            Native.register("gtk-4");
        }

        /**
         * Adds a widget to the grid.
         * <p>
         * The position of child is determined by column and row. The number of "cells" that child will occupy is
         * determined by width and height.
         *
         * @param grid   self
         * @param child  The widget to add. Type: GtkWidget
         * @param column The column number to attach the left side of child to.
         * @param row    The row number to attach the top side of child to.
         * @param width  The number of columns that child will span.
         * @param height The number of rows that child will span.
         */
        public native void gtk_grid_attach(Pointer grid, Pointer child, int column, int row, int width, int height);

        /**
         * Adds a widget to the grid.
         * <p>
         * The widget is placed next to sibling, on the side determined by side. When sibling is NULL, the widget is
         * placed in row (for left or right placement) or column 0 (for top or bottom placement), at the end indicated
         * by side.
         * <p>
         * Attaching widgets labeled [1], [2], [3] with sibling` == `NULL and side` == `GTK_POS_LEFT yields a layout of
         * [3][2][1].
         *
         * @param grid    self
         * @param child   The widget to add. Type: GtkWidget
         * @param sibling The child of grid that child will be placed next to,
         *                or NULL to place child at the beginning or end. Type: GtkWidget
         *                <p>
         *                The argument can be NULL.
         * @param side    The side of sibling that child is positioned next to. Type: GtkPositionType
         * @param width   The number of columns that child will span.
         * @param height  The number of rows that child will span.
         */
        public native void gtk_grid_attach_next_to(Pointer grid, Pointer child, Pointer sibling, int side, int width, int height);

        /**
         * Returns which row defines the global baseline of grid.
         *
         * @param grid self
         * @return The row index defining the global baseline.
         */
        public native int gtk_grid_get_baseline_row(Pointer grid);

        /**
         * Gets the child of grid whose area covers the grid cell at column, row.
         *
         * @param grid   self
         * @param column The left edge of the cell.
         * @param row    The top edge of the cell.
         * @return The child at the given position.
         *         <p>
         *         The return value can be NULL.
         */
        public native Pointer gtk_grid_get_child_at(Pointer grid, int column, int row);

        /**
         * Returns whether all columns of grid have the same width.
         *
         * @param grid self
         * @return Whether all columns of grid have the same width.
         */
        public native boolean gtk_grid_get_column_homogeneous(Pointer grid);

        /**
         * Returns the amount of space between the columns of grid.
         *
         * @param grid self
         * @return The column spacing of grid.
         */
        public native int gtk_grid_get_column_spacing(Pointer grid);

        /**
         * Returns the baseline position of row.
         *
         * @param grid self
         * @param row  A row index.
         * @return The baseline position of row. Type: GtkBaselinePosition
         */
        public native int gtk_grid_get_row_baseline_position(Pointer grid, int row);

        /**
         * Returns whether all rows of grid have the same height.
         *
         * @param grid self
         * @return Whether all rows of grid have the same height.
         */
        public native boolean gtk_grid_get_row_homogeneous(Pointer grid);

        /**
         * Returns the amount of space between the rows of grid.
         *
         * @param grid self
         * @return The row spacing of grid.
         */
        public native int gtk_grid_get_row_spacing(Pointer grid);

        /**
         * Inserts a column at the specified position.
         * <p>
         * Children which are attached at or to the right of this position are moved one column to the right.
         * Children which span across this position are grown to span the new column.
         *
         * @param grid     self
         * @param position The position to insert the column at.
         */
        public native void gtk_grid_insert_column(Pointer grid, int position);

        /**
         * Inserts a row or column at the specified position.
         * <p>
         * The new row or column is placed next to sibling, on the side determined by side. If side is GTK_POS_TOP or
         * GTK_POS_BOTTOM, a row is inserted. If side is GTK_POS_LEFT of GTK_POS_RIGHT, a column is inserted.
         *
         * @param grid    self
         * @param sibling The child of grid that the new row or column will be placed next to. Type: GtkWidget
         * @param side    The side of sibling that child is positioned next to. Type: GtkPositionType
         */
        public native void gtk_grid_insert_next_to(Pointer grid, Pointer sibling, int side);

        /**
         * Inserts a row at the specified position.
         * <p>
         * Children which are attached at or below this position are moved one row down. Children which span across
         * this position are grown to span the new row.
         *
         * @param grid     self
         * @param position The position to insert the row at.
         */
        public native void gtk_grid_insert_row(Pointer grid, int position);

        /**
         * Creates a new grid widget.
         *
         * @return The new GtkGrid. Type: GtkGrid
         */
        public native Pointer gtk_grid_new();

        /**
         * Queries the attach-points and spans of child inside the given GtkGrid.
         *
         * @param grid   self
         * @param child  A GtkWidget child of grid.
         * @param column The column used to attach the left side of child.
         *               <p>
         *               The argument will be set by the function.
         *               The argument can be NULL.
         * @param row    The row used to attach the top side of child.
         *               <p>
         *               The argument will be set by the function.
         *               The argument can be NULL.
         * @param width  The number of columns child spans.
         *               <p>
         *               The argument will be set by the function.
         *               The argument can be NULL.
         * @param height The number of rows child spans.
         *               <p>
         *               The argument will be set by the function.
         *               The argument can be NULL
         */
        public native void gtk_grid_query_child(Pointer grid, Pointer child, PointerByReference column, PointerByReference row, PointerByReference width, PointerByReference height);

        /**
         * Removes a child from grid.
         * <p>
         * The child must have been added with gtk_grid_attach() or gtk_grid_attach_next_to().
         *
         * @param grid  self
         * @param child The child widget to remove. Type: GtkWidget
         */
        public native void gtk_grid_remove(Pointer grid, Pointer child);

        /**
         * Removes a column from the grid.
         * <p>
         * Children that are placed in this column are removed, spanning children that overlap this column have their
         * width reduced by one, and children after the column are moved to the left.
         *
         * @param grid     self
         * @param position The position of the column to remove.
         */
        public native void gtk_grid_remove_column(Pointer grid, int position);

        /**
         * Removes a row from the grid.
         * <p>
         * Children that are placed in this row are removed, spanning children that overlap this row have their height
         * reduced by one, and children below the row are moved up.
         *
         * @param grid     self
         * @param position The position of the row to remove.
         */
        public native void gtk_grid_remove_row(Pointer grid, int position);

        /**
         * Sets which row defines the global baseline for the entire grid.
         * <p>
         * Each row in the grid can have its own local baseline, but only one of those is global, meaning it will be
         * the baseline in the parent of the grid.
         *
         * @param grid self
         * @param row  The row index.
         */
        public native void gtk_grid_set_baseline_row(Pointer grid, int row);

        /**
         * Sets whether all columns of grid will have the same width.
         *
         * @param grid        self
         * @param homogeneous TRUE to make columns homogeneous.
         */
        public native void gtk_grid_set_column_homogeneous(Pointer grid, boolean homogeneous);

        /**
         * Sets the amount of space between columns of grid.
         *
         * @param grid    self
         * @param spacing The amount of space to insert between columns.
         */
        public native void gtk_grid_set_column_spacing(Pointer grid, int spacing);

        /**
         * Sets how the baseline should be positioned on row of the grid, in case that row is assigned more space than
         * is requested.
         * <p>
         * The default baseline position is GTK_BASELINE_POSITION_CENTER.
         *
         * @param grid self
         * @param row  A row index.
         * @param pos  A GtkBaselinePosition. Type: GtkBaselinePosition
         */
        public native void gtk_grid_set_row_baseline_position(Pointer grid, int row, int pos);

        /**
         * Sets whether all rows of grid will have the same height.
         *
         * @param grid        self
         * @param homogeneous TRUE to make rows homogeneous.
         */
        public native void gtk_grid_set_row_homogeneous(Pointer grid, boolean homogeneous);

        /**
         * Sets the amount of space between rows of grid.
         *
         * @param grid    self
         * @param spacing The amount of space to insert between rows.
         */
        public native void gtk_grid_set_row_spacing(Pointer grid, int spacing);

    }
}
