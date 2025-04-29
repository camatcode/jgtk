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
package com.gitlab.ccook.jna;

import com.gitlab.ccook.jgtk.*;
import com.gitlab.ccook.jgtk.callbacks.GDestroyNotify;
import com.gitlab.ccook.jgtk.callbacks.GtkPrinterFunc;
import com.gitlab.ccook.jgtk.callbacks.GtkTextCharPredicate;
import com.gitlab.ccook.jgtk.callbacks.GtkTextTagTableForeach;
import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

@SuppressWarnings("UnusedReturnValue")
public class GtkLibrary {
    public final static GtkCLib INSTANCE = Native.load("gtk-4", GtkCLib.class);

    static {
        Native.register("gtk-4");
    }

    public native boolean g_application_id_is_valid(String applicationId);

    public native void g_application_quit(Pointer cReference);

    public int g_application_run(Pointer application, int argc, String[] argv) {
        return INSTANCE.g_application_run(application, argc, argv);
    }

    public native String g_date_time_format_iso8601(Pointer cReference);

    public native Pointer g_date_time_new_from_iso8601(String iso, Pointer o);

    public native int g_file_error_from_errno(int code);

    public native String g_file_get_parse_name(Pointer cReference);

    public native String g_file_get_path(Pointer cReference);

    public native Pointer g_file_icon_new(Pointer gfile);

    public native Pointer g_file_new_for_path(String absolutePath);

    public native Pointer g_file_new_for_uri(String s);

    public native boolean g_file_query_exists(Pointer cReference, Pointer aNull);

    public native void g_free(Pointer cReference);

    public native Pointer g_icon_new_for_string(String iconForString);

    public native int g_list_length(Pointer cReference);

    public native Pointer g_list_model_get_item(Pointer cReference, int n);

    public native int g_list_model_get_n_items(Pointer cReference);

    public native Pointer g_list_nth_data(Pointer cReference, int n);

    public native void g_menu_append(Pointer cReference, String label, String detailedAction);


    public native Pointer g_menu_new();

    public native void g_object_get_property(Pointer cReference, String s, PointerByReference p);

    public native void g_object_set_property(Pointer cReference, String s, Pointer b);

    public native int g_quark_from_static_string(String s);

    public native void g_signal_connect_data(Pointer instance, String detailedSignal, Callback toConnect, Pointer data, Pointer nullPointer, int connectFlags0);

    public native void g_signal_emit_by_name(Pointer cReference, String detailedName, PointerByReference returnOut);

    public void g_signal_emit_by_name(Pointer cReference, String detailedName, Pointer[] params) {
        INSTANCE.g_signal_emit_by_name(cReference, detailedName, params);
    }


    public native Pointer g_simple_permission_new(boolean isAllowed);

    public native int g_slist_length(Pointer cReference);

    public native Pointer g_slist_nth(Pointer cReference, int index);

    public native Pointer g_string_new(Pointer aNull);

    public native String g_type_name_from_instance(Pointer cReference);

    public native Pointer g_value_dup_object(Pointer cReference);

    public native boolean g_value_get_boolean(Pointer p);

    public native Pointer g_value_get_object(Pointer cReference);

    public native String g_value_get_string(Pointer cReference);

    public native int g_value_get_uint(Pointer cReference);

    public native void g_value_set_boolean(Pointer p, boolean bool);

    public native void g_value_set_object(Pointer cReference, Pointer value);

    public native void g_value_set_pointer(Pointer cReference, Pointer c);

    public native void g_value_set_string(Pointer gValue, String value);

    public native void g_value_set_uint(Pointer cReference, int value);

    public native Pointer g_variant_new_string(String str);

    public native Pointer gdk_pixbuf_new_from_file(String absolutePath, GError.GErrorStruct error);

    public native boolean gdk_rectangle_equal(Pointer cReference, Pointer cReference1);

    public native Pointer gdk_texture_new_from_file(Pointer gFile, GError.GErrorStruct.ByReference error);

    public native String gsk_transform_to_string(Pointer cReference);

    public native int gtk_accelerator_get_default_mod_mask();

    public native String gtk_accelerator_get_label(int acceleratorKey, int acceleratorMods);

    public native String gtk_accelerator_name(int acceleratorKey, int cValue);

    public native boolean gtk_accelerator_parse(String acceleratorString, PointerByReference pointerToAcceleratorKey, PointerByReference pointerToAcceleratorMods);

    public native boolean gtk_accelerator_valid(int acceleratorKey, int cValue);

    public native int gtk_accessible_get_accessible_role(Pointer cReference);

    public native void gtk_accessible_reset_property(Pointer cReference, int cValue);

    public native void gtk_accessible_reset_relation(Pointer cReference, int cValue);

    public native void gtk_accessible_reset_state(Pointer cReference, int cValue);

    public void gtk_accessible_update_property_value(Pointer cReference, int i, int prop, Pointer[] pointers) {
        INSTANCE.gtk_accessible_update_property_value(cReference, i, prop, pointers);
    }

    public void gtk_accessible_update_relation_value(Pointer cReference, int i, int cValue, Pointer[] pointers) {
        INSTANCE.gtk_accessible_update_relation_value(cValue, i, cValue, pointers);
    }

    public void gtk_accessible_update_state_value(Pointer cReference, int i, int cValue, Pointer[] pointers) {
        INSTANCE.gtk_accessible_update_state_value(cValue, i, cValue, pointers);
    }


    public native String gtk_actionable_get_action_name(Pointer cReference);

    public native Pointer gtk_actionable_get_action_target_value(Pointer cReference);

    public native void gtk_actionable_set_action_name(Pointer cReference, String actionName);

    public native void gtk_actionable_set_action_target_value(Pointer cReference, Pointer cReference1);

    public native void gtk_actionable_set_detailed_action_name(Pointer cReference, String detailedName);

    public native void gtk_adjustment_clamp_page(Pointer cReference, double lower, double upper);

    public native void gtk_adjustment_configure(Pointer cReference, double value, double lower, double upper, double stepIncrement, double pageIncrement, double pageSize);

    public native double gtk_adjustment_get_lower(Pointer cReference);

    public native double gtk_adjustment_get_minimum_increment(Pointer cReference);

    public native double gtk_adjustment_get_page_increment(Pointer cReference);

    public native double gtk_adjustment_get_page_size(Pointer cReference);

    public native double gtk_adjustment_get_step_increment(Pointer cReference);

    public native double gtk_adjustment_get_upper(Pointer cReference);

    public native double gtk_adjustment_get_value(Pointer cReference);

    public native Pointer gtk_adjustment_new(double value, double lower, double upper, double stepIncrement, double pageIncrement, double pageSize);

    public native void gtk_adjustment_set_lower(Pointer cReference, double lower);

    public native void gtk_adjustment_set_page_increment(Pointer cReference, double pageIncrement);

    public native void gtk_adjustment_set_page_size(Pointer cReference, double pageSize);

    public native void gtk_adjustment_set_step_increment(Pointer cReference, double stepIncrement);

    public native void gtk_adjustment_set_upper(Pointer cReference, double upper);

    public native void gtk_adjustment_set_value(Pointer cReference, double value);

    public native Pointer gtk_app_chooser_get_app_info(Pointer cReference);

    public native String gtk_app_chooser_get_content_type(Pointer cReference);

    public native void gtk_app_chooser_refresh(Pointer cReference);

    public native void gtk_application_add_window(Pointer cReference, Pointer cReference1);

    public String[] gtk_application_get_accels_for_action(Pointer cReference, String detailedActionName) {
        return INSTANCE.gtk_application_get_accels_for_action(cReference, detailedActionName);
    }

    public String[] gtk_application_get_actions_for_accel(Pointer cReference, String accel) {
        return INSTANCE.gtk_application_get_actions_for_accel(cReference, accel);
    }

    public native Pointer gtk_application_get_active_window(Pointer cReference);

    public native Pointer gtk_application_get_menu_by_id(Pointer cReference, String id);

    public native Pointer gtk_application_get_menubar(Pointer cReference);

    public native Pointer gtk_application_get_window_by_id(Pointer cReference, int id);

    public native Pointer gtk_application_get_windows(Pointer cReference);

    public native int gtk_application_inhibit(Pointer cReference, Pointer cReference1, int cValue, String reasonWhy);

    public String[] gtk_application_list_action_descriptions(Pointer cReference) {
        return INSTANCE.gtk_application_list_action_descriptions(cReference);
    }

    public native Pointer gtk_application_new(String id, int thing);

    public native void gtk_application_remove_window(Pointer cReference, Pointer cReference1);

    public void gtk_application_set_accels_for_action(Pointer cReference, String detailedActionName, String[] accels) {
        INSTANCE.gtk_application_set_accels_for_action(cReference, detailedActionName, accels);
    }

    public native void gtk_application_set_menubar(Pointer cReference, Pointer cReference1);

    public native void gtk_application_uninhibit(Pointer cReference, int cookie);


    public native boolean gtk_bitset_add(Pointer cReference, int value);

    public native void gtk_bitset_add_range(Pointer cReference, int start, int numberOfItems);

    public native void gtk_bitset_add_range_closed(Pointer cReference, int first, int last);

    public native void gtk_bitset_add_rectangle(Pointer cReference, int start, int width, int height, int stride);

    public native boolean gtk_bitset_contains(Pointer cReference, int value);

    public native Pointer gtk_bitset_copy(Pointer cReference);

    public native void gtk_bitset_difference(Pointer cReference, Pointer cReference1);

    public native boolean gtk_bitset_equals(Pointer cReference, Pointer cReference1);

    public native int gtk_bitset_get_maximum(Pointer cReference);

    public native int gtk_bitset_get_minimum(Pointer cReference);

    public native int gtk_bitset_get_nth(Pointer cReference, int n);

    public native int gtk_bitset_get_size(Pointer cReference);

    public native void gtk_bitset_intersect(Pointer cReference, Pointer cReference1);

    public native boolean gtk_bitset_is_empty(Pointer cReference);

    public native Pointer gtk_bitset_new_empty();

    public native Pointer gtk_bitset_new_range(int startPosition, int numberOfItems);

    public native void gtk_bitset_remove(Pointer cReference, int value);

    public native void gtk_bitset_remove_all(Pointer cReference);

    public native void gtk_bitset_remove_range(Pointer cReference, int start, int nItems);

    public native void gtk_bitset_remove_range_closed(Pointer cReference, int first, int last);

    public native void gtk_bitset_remove_rectangle(Pointer cReference, int start, int width, int height, int stride);

    public native void gtk_bitset_shift_left(Pointer cReference, int amount);

    public native void gtk_bitset_shift_right(Pointer cReference, int amount);

    public native void gtk_bitset_subtract(Pointer cReference, Pointer cReference1);

    public native void gtk_bitset_union(Pointer cReference, Pointer cReference1);

    public native String gtk_buildable_get_buildable_id(Pointer cReference);

    public native String gtk_check_version(int minorVersion, int minorVersion1, int patchVersion);

    public native void gtk_disable_setlocale();

    public native Pointer gtk_emoji_chooser_new();

    public native int gtk_entry_buffer_delete_text(Pointer cReference, int startPosition, int numToDelete);

    public native void gtk_entry_buffer_emit_deleted_text(Pointer cReference, int startPosition, int numToDelete);

    public native void gtk_entry_buffer_emit_inserted_text(Pointer cReference, int startPosition, String textInserted, int length);

    public native int gtk_entry_buffer_get_length(Pointer cReference);

    public native int gtk_entry_buffer_get_max_length(Pointer cReference);

    public native String gtk_entry_buffer_get_text(Pointer cReference);

    public native void gtk_entry_buffer_insert_text(Pointer cReference, int startPosition, String toInsert, int length);

    public native Pointer gtk_entry_buffer_new(String initialCharacters, int i);

    public native void gtk_entry_buffer_set_max_length(Pointer cReference, int maxLength);

    public native Pointer gtk_entry_completion_new();

    public native void gtk_enumerate_printers(GtkPrinterFunc func, Pointer data, GDestroyNotify destroy, boolean wait);

    public native Pointer gtk_event_controller_get_current_event(Pointer cReference);

    public native Pointer gtk_event_controller_get_current_event_device(Pointer cReference);

    public native int gtk_event_controller_get_current_event_state(Pointer cReference);

    public native int gtk_event_controller_get_current_event_time(Pointer cReference);

    public native String gtk_event_controller_get_name(Pointer cReference);

    public native int gtk_event_controller_get_propagation_limit(Pointer cReference);

    public native int gtk_event_controller_get_propagation_phase(Pointer cReference);

    public native Pointer gtk_event_controller_get_widget(Pointer cReference);

    public native void gtk_event_controller_reset(Pointer cReference);

    public native void gtk_event_controller_set_name(Pointer cReference, String name);

    public native void gtk_event_controller_set_propagation_limit(Pointer controllerName, int cValue);

    public native void gtk_event_controller_set_propagation_phase(Pointer cReference, int cValue);

    public native int gtk_get_binary_age();

    public native int gtk_get_debug_flags();

    public native Pointer gtk_media_file_get_file(Pointer cReference);

    public native Pointer gtk_media_file_new_for_file(Pointer pointerOrNull);

    public native Pointer gtk_media_file_new_for_filename(String absolutePath);


    public native void gtk_media_stream_gerror(Pointer cReference, Pointer cReference1);

    public native long gtk_media_stream_get_duration(Pointer cReference);

    public native boolean gtk_media_stream_get_ended(Pointer cReference);

    public native GError.GErrorStruct.ByReference gtk_media_stream_get_error(Pointer cReference);

    public native boolean gtk_media_stream_get_loop(Pointer cReference);

    public native boolean gtk_media_stream_get_muted(Pointer cReference);

    public native boolean gtk_media_stream_get_playing(Pointer cReference);

    public native long gtk_media_stream_get_timestamp(Pointer cReference);

    public native double gtk_media_stream_get_volume(Pointer cReference);

    public native boolean gtk_media_stream_has_audio(Pointer cReference);

    public native boolean gtk_media_stream_has_video(Pointer cReference);

    public native boolean gtk_media_stream_is_prepared(Pointer cReference);

    public native boolean gtk_media_stream_is_seekable(Pointer cReference);

    public native boolean gtk_media_stream_is_seeking(Pointer cReference);

    public native void gtk_media_stream_pause(Pointer cReference);

    public native void gtk_media_stream_play(Pointer cReference);

    public native void gtk_media_stream_realize(Pointer cReference, Pointer cReference1);

    public native void gtk_media_stream_seek(Pointer cReference, long timestamp);

    public native void gtk_media_stream_seek_failed(Pointer cReference);

    public native void gtk_media_stream_seek_success(Pointer cReference);

    public native void gtk_media_stream_set_loop(Pointer cReference, boolean canLoop);

    public native void gtk_media_stream_set_muted(Pointer cReference, boolean isMuted);

    public native void gtk_media_stream_set_playing(Pointer cReference, boolean isPlaying);

    public native void gtk_media_stream_set_volume(Pointer cReference, double volume);

    public native void gtk_media_stream_stream_ended(Pointer cReference);

    public native void gtk_media_stream_stream_prepared(Pointer cReference, boolean hasAudio, boolean hasVideo, boolean isSeekable, long duration);

    public native void gtk_media_stream_stream_unprepared(Pointer cReference);

    public native void gtk_media_stream_unrealize(Pointer cReference, Pointer cReference1);

    public native void gtk_media_stream_update(Pointer cReference, long newTimestamp);

    public native Pointer gtk_native_get_for_surface(Pointer cReference);

    public native Pointer gtk_native_get_renderer(Pointer cReference);

    public native Pointer gtk_native_get_surface(Pointer cReference);

    public native void gtk_native_get_surface_transform(Pointer cReference, PointerByReference x, PointerByReference y);

    public native void gtk_native_realize(Pointer cReference);

    public native void gtk_native_unrealize(Pointer cReference);

    public native Pointer gtk_notebook_page_get_child(Pointer cReference);

    public native int gtk_orientable_get_orientation(Pointer cReference);

    public native void gtk_orientable_set_orientation(Pointer cReference, int cValue);

    public native Pointer gtk_password_entry_buffer_new();


    public native void gtk_print_operation_preview_end_preview(Pointer cReference);

    public native boolean gtk_print_operation_preview_is_selected(Pointer cReference, int pageNumber);

    public native void gtk_print_operation_preview_render_page(Pointer cReference, int pageNumber);


    public native Pointer gtk_root_get_display(Pointer cReference);

    public native Pointer gtk_root_get_focus(Pointer cReference);

    public native void gtk_root_set_focus(Pointer cReference, Pointer cReference1);

    public native boolean gtk_scrollable_get_border(Pointer cReference, PointerByReference borderPointer);

    public native Pointer gtk_scrollable_get_hadjustment(Pointer cReference);

    public native int gtk_scrollable_get_hscroll_policy(Pointer cReference);

    public native Pointer gtk_scrollable_get_vadjustment(Pointer cReference);

    public native int gtk_scrollable_get_vscroll_policy(Pointer cReference);

    public native void gtk_scrollable_set_hadjustment(Pointer cReference, Pointer cReference1);

    public native void gtk_scrollable_set_hscroll_policy(Pointer cReference, int cValue);

    public native void gtk_scrollable_set_vadjustment(Pointer cReference, Pointer o);

    public native void gtk_scrollable_set_vscroll_policy(Pointer cReference, int cValue);

    public native Pointer gtk_selection_model_get_selection(Pointer cReference);

    public native Pointer gtk_selection_model_get_selection_in_range(Pointer cReference, int startPos, int nItems);

    public native boolean gtk_selection_model_is_selected(Pointer cReference, int position);

    public native boolean gtk_selection_model_select_all(Pointer cReference);

    public native boolean gtk_selection_model_select_item(Pointer cReference, int position, boolean unselectOthers);

    public native boolean gtk_selection_model_select_range(Pointer cReference, int position, int nItems, boolean unselectOthers);

    public native void gtk_selection_model_selection_changed(Pointer cReference, int position, int nItems);

    public native void gtk_selection_model_set_selection(Pointer cReference, Pointer cReference1, Pointer cReference2);

    public native boolean gtk_selection_model_unselect_all(Pointer cReference);

    public native boolean gtk_selection_model_unselect_item(Pointer cReference, int position);

    public native boolean gtk_selection_model_unselect_range(Pointer cReference, int startPosition, int nItems);


    public native Pointer gtk_stack_page_get_child(Pointer cReference);

    public native String gtk_stack_page_get_icon_name(Pointer cReference);

    public native String gtk_stack_page_get_name(Pointer cReference);

    public native boolean gtk_stack_page_get_needs_attention(Pointer cReference);

    public native String gtk_stack_page_get_title(Pointer cReference);

    public native boolean gtk_stack_page_get_use_underline(Pointer cReference);

    public native boolean gtk_stack_page_get_visible(Pointer cReference);

    public native void gtk_stack_page_set_icon_name(Pointer cReference, String iconName);

    public native void gtk_stack_page_set_name(Pointer cReference, String name);

    public native void gtk_stack_page_set_needs_attention(Pointer cReference, boolean doesNeedAttention);

    public native void gtk_stack_page_set_title(Pointer cReference, String title);

    public native void gtk_stack_page_set_use_underline(Pointer cReference, boolean doesTitleUseMnemonics);

    public native void gtk_stack_page_set_visible(Pointer cReference, boolean isVisible);

    public native String gtk_string_object_get_string(Pointer self);

    public native boolean gtk_text_child_anchor_get_deleted(Pointer cReference);

    public Pointer[] gtk_text_child_anchor_get_widgets(Pointer cReference, PointerByReference len) {
        return INSTANCE.gtk_text_child_anchor_get_widgets(cReference, len);
    }

    public native Pointer gtk_text_child_anchor_new();


    public native void gtk_text_iter_assign(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_backward_char(Pointer cReference);

    public native boolean gtk_text_iter_backward_chars(Pointer cReference, int max);

    public native boolean gtk_text_iter_backward_cursor_position(Pointer cReference);

    public native boolean gtk_text_iter_backward_cursor_positions(Pointer cReference, int numCursorPositions);

    public native boolean gtk_text_iter_backward_find_char(Pointer cReference, GtkTextCharPredicate pred, Pointer userData, Pointer limitPointer);

    public native boolean gtk_text_iter_backward_line(Pointer cReference);

    public native boolean gtk_text_iter_backward_lines(Pointer cReference, int numLines);

    public native boolean gtk_text_iter_backward_search(Pointer cReference, String search, int cValueFromFlags, PointerByReference matchStart, PointerByReference matchEnd, Pointer limitPointer);

    public native boolean gtk_text_iter_backward_sentence_start(Pointer cReference);

    public native boolean gtk_text_iter_backward_sentence_starts(Pointer cReference, int numOfSentenceStartsToMoveBack);

    public native boolean gtk_text_iter_backward_to_tag_toggle(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_backward_visible_cursor_position(Pointer cReference);

    public native boolean gtk_text_iter_backward_visible_cursor_positions(Pointer cReference, int numPositions);

    public native boolean gtk_text_iter_backward_visible_line(Pointer cReference);

    public native boolean gtk_text_iter_backward_visible_lines(Pointer cReference, int numVisLines);

    public native boolean gtk_text_iter_backward_visible_word_start(Pointer cReference);

    public native boolean gtk_text_iter_backward_visible_word_starts(Pointer cReference, int count);

    public native boolean gtk_text_iter_backward_word_start(Pointer cReference);

    public native boolean gtk_text_iter_backward_word_starts(Pointer cReference, int count);

    public native boolean gtk_text_iter_can_insert(Pointer cReference, boolean defaultEditability);

    public native int gtk_text_iter_compare(Pointer cReference, Pointer cReference1);

    public native Pointer gtk_text_iter_copy(Pointer cReference);

    public native boolean gtk_text_iter_editable(Pointer cReference, boolean defaultEditability);

    public native boolean gtk_text_iter_ends_line(Pointer cReference);

    public native boolean gtk_text_iter_ends_sentence(Pointer cReference);

    public native boolean gtk_text_iter_ends_tag(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_ends_word(Pointer cReference);

    public native boolean gtk_text_iter_equal(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_forward_char(Pointer cReference);

    public native boolean gtk_text_iter_forward_chars(Pointer cReference, int numCharacters);

    public native boolean gtk_text_iter_forward_cursor_position(Pointer cReference);

    public native boolean gtk_text_iter_forward_cursor_positions(Pointer cReference, int numCursorPositions);

    public native boolean gtk_text_iter_forward_find_char(Pointer cReference, GtkTextCharPredicate pred, Pointer userData, Pointer limitPointer);

    public native boolean gtk_text_iter_forward_line(Pointer cReference);

    public native boolean gtk_text_iter_forward_lines(Pointer cReference, int numLines);

    public native boolean gtk_text_iter_forward_search(Pointer cReference, String search, int cValueFromFlags, PointerByReference matchStart, PointerByReference matchEnd, Pointer limitPointer);

    public native boolean gtk_text_iter_forward_sentence_end(Pointer cReference);

    public native boolean gtk_text_iter_forward_sentence_ends(Pointer cReference, int numOfSentenceEndsToMoveForward);

    public native void gtk_text_iter_forward_to_end(Pointer cReference);

    public native boolean gtk_text_iter_forward_to_line_end(Pointer cReference);

    public native boolean gtk_text_iter_forward_to_tag_toggle(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_forward_visible_cursor_position(Pointer cReference);

    public native boolean gtk_text_iter_forward_visible_cursor_positions(Pointer cReference, int numCursorPositions);

    public native boolean gtk_text_iter_forward_visible_line(Pointer cReference);

    public native boolean gtk_text_iter_forward_visible_lines(Pointer cReference, int numVisLines);

    public native boolean gtk_text_iter_forward_visible_word_end(Pointer cReference);

    public native boolean gtk_text_iter_forward_visible_word_ends(Pointer cReference, int numWords);

    public native boolean gtk_text_iter_forward_word_end(Pointer cReference);

    public native boolean gtk_text_iter_forward_word_ends(Pointer cReference, int numWords);

    public native void gtk_text_iter_free(Pointer cReference);

    public native Pointer gtk_text_iter_get_buffer(Pointer cReference);

    public native int gtk_text_iter_get_bytes_in_line(Pointer cReference);

    public native char gtk_text_iter_get_char(Pointer cReference);

    public native int gtk_text_iter_get_chars_in_line(Pointer cReference);

    public native Pointer gtk_text_iter_get_child_anchor(Pointer cReference);

    public native Pointer gtk_text_iter_get_language(Pointer cReference);

    public native int gtk_text_iter_get_line(Pointer cReference);

    public native int gtk_text_iter_get_line_index(Pointer cReference);

    public native int gtk_text_iter_get_line_offset(Pointer cReference);

    public native Pointer gtk_text_iter_get_marks(Pointer cReference);

    public native int gtk_text_iter_get_offset(Pointer cReference);

    public native Pointer gtk_text_iter_get_paintable(Pointer cReference);

    public native String gtk_text_iter_get_slice(Pointer cReference, Pointer cReference1);

    public native Pointer gtk_text_iter_get_tags(Pointer cReference);

    public native String gtk_text_iter_get_text(Pointer cReference, Pointer cReference1);

    public native Pointer gtk_text_iter_get_toggled_tags(Pointer cReference, boolean toggledOn);

    public native int gtk_text_iter_get_visible_line_index(Pointer cReference);

    public native int gtk_text_iter_get_visible_line_offset(Pointer cReference);

    public native String gtk_text_iter_get_visible_slice(Pointer cReference, Pointer cReference1);

    public native String gtk_text_iter_get_visible_text(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_has_tag(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_in_range(Pointer cReference, Pointer cReference1, Pointer cReference2);

    public native boolean gtk_text_iter_inside_sentence(Pointer cReference);

    public native boolean gtk_text_iter_inside_word(Pointer cReference);

    public native boolean gtk_text_iter_is_cursor_position(Pointer cReference);

    public native boolean gtk_text_iter_is_end(Pointer cReference);

    public native boolean gtk_text_iter_is_start(Pointer cReference);

    public native void gtk_text_iter_order(Pointer cReference, Pointer cReference1);

    public native void gtk_text_iter_set_line(Pointer cReference, int lineNumber);

    public native void gtk_text_iter_set_line_index(Pointer cReference, int lineByteOffset);

    public native void gtk_text_iter_set_line_offset(Pointer cReference, int lineCharacterOffset);

    public native void gtk_text_iter_set_offset(Pointer cReference, int characterOffset);

    public native void gtk_text_iter_set_visible_line_index(Pointer cReference, int visLineByteOffset);

    public native void gtk_text_iter_set_visible_line_offset(Pointer cReference, int visLineCharOffset);

    public native boolean gtk_text_iter_starts_line(Pointer cReference);

    public native boolean gtk_text_iter_starts_sentence(Pointer cReference);

    public native boolean gtk_text_iter_starts_tag(Pointer cReference, Pointer cReference1);

    public native boolean gtk_text_iter_starts_word(Pointer cReference);

    public native boolean gtk_text_iter_toggles_tag(Pointer cReference, Pointer cReference1);

    public native Pointer gtk_text_mark_get_buffer(Pointer cReference);

    public native boolean gtk_text_mark_get_deleted(Pointer cReference);

    public native boolean gtk_text_mark_get_left_gravity(Pointer cReference);

    public native String gtk_text_mark_get_name(Pointer cReference);

    public native boolean gtk_text_mark_get_visible(Pointer cReference);

    public native Pointer gtk_text_mark_new(String name, boolean hasLeftGravity);

    public native void gtk_text_mark_set_visible(Pointer cReference, boolean isVisible);

    public native boolean gtk_text_tag_table_add(Pointer cReference, Pointer cReference1);

    public native void gtk_text_tag_table_foreach(Pointer cReference, GtkTextTagTableForeach func, Pointer userData);

    public native int gtk_text_tag_table_get_size(Pointer cReference);

    public native Pointer gtk_text_tag_table_lookup(Pointer cReference, String name);

    public native Pointer gtk_text_tag_table_new();

    public native void gtk_text_tag_table_remove(Pointer cReference, Pointer cReference1);

    public native void gtk_widget_action_set_enabled(Pointer cReference, String actionName, boolean isEnabled);

    public native boolean gtk_widget_activate(Pointer cReference);

    public boolean gtk_widget_activate_action(Pointer cReference, String actionName, String[] gvariantFormatStrings) {
        return INSTANCE.gtk_widget_activate_action(cReference, actionName, gvariantFormatStrings);
    }

    public native void gtk_widget_activate_default(Pointer cReference);

    public native void gtk_widget_add_controller(Pointer cReference, Pointer cReference1);

    public native void gtk_widget_add_css_class(Pointer cReference, String cssClass);

    public native void gtk_widget_add_mnemonic_label(Pointer cReference, Pointer cReference1);

    public native int gtk_widget_get_allocated_height(Pointer cReference);

    public native int gtk_widget_get_allocated_width(Pointer cReference);

    public native void gtk_widget_get_allocation(Pointer cReference, GdkRectangle.GdkRectangleStruct.ByReference pbr);

    public native int gtk_widget_get_default_direction();

    public native void gtk_widget_insert_after(Pointer cReference, Pointer cReference1, Pointer cReference2);

    public native Pointer gtk_widget_observe_children(Pointer cReference);

    public native void gtk_widget_set_default_direction(int cValue);

    public native void gtk_widget_set_parent(Pointer cReference, Pointer cReference1);

    public native void gtk_widget_set_size_request(Pointer cReference, int width, int height);

    public native void gtk_widget_show(Pointer cReference);

    public native String gtk_window_controls_get_decoration_layout(Pointer cReference);

    public native boolean gtk_window_controls_get_empty(Pointer cReference);

    public native int gtk_window_controls_get_side(Pointer cReference);

    public native Pointer gtk_window_controls_new(int cValueFromType);

    public native void gtk_window_controls_set_decoration_layout(Pointer cReference, String decorationLayout);

    public native void gtk_window_controls_set_side(Pointer cReference, int cValueFromType);


    public native Pointer gtk_window_handle_get_child(Pointer cReference);

    public native Pointer gtk_window_handle_new();

    public native void gtk_window_handle_set_child(Pointer cReference, Pointer pointerOrNull);

    public native Pointer pango_attr_list_get_attributes(Pointer cReference);

    public native void pango_attr_list_insert(Pointer cReference, Pointer cReference1);

    public native Pointer pango_attr_list_new();

    public native PangoAttrColor pango_attribute_as_color(Pointer cReference);

    public native Pointer pango_attribute_as_float(Pointer cReference);

    public native Pointer pango_attribute_as_font_desc(Pointer cReference);

    public native Pointer pango_attribute_as_font_features(Pointer cReference);

    public native PangoAttrInt pango_attribute_as_int(Pointer cReference);

    public native Pointer pango_attribute_as_language(Pointer cReference);

    public native Pointer pango_attribute_as_shape(Pointer cReference);

    public native Pointer pango_attribute_as_size(Pointer cReference);

    public native Pointer pango_attribute_as_string(Pointer cReference);

    public native boolean pango_attribute_equal(Pointer cReference, Pointer cReference1);

    public native Pointer pango_attribute_init(Pointer cReference);

    public native String pango_color_to_string(Pointer pointer);

    public native Pointer pango_layout_get_attributes(Pointer layout);

    public native Pointer pango_tab_array_copy(Pointer cReference);

    public native void pango_tab_array_free(Pointer cReference);

    public native Pointer pango_tab_array_from_string(String text);

    public native char pango_tab_array_get_decimal_point(Pointer cReference, int tabStopIndex);

    public native boolean pango_tab_array_get_positions_in_pixels(Pointer cReference);

    public native int pango_tab_array_get_size(Pointer cReference);

    public native void pango_tab_array_get_tab(Pointer cReference, int index, PointerByReference alignmentStorage, PointerByReference locationStorage);

    public native Pointer pango_tab_array_new(int initSize, boolean positionsInPixels);

    public native void pango_tab_array_resize(Pointer cReference, int newSize);

    public native void pango_tab_array_set_decimal_point(Pointer cReference, int tabIndex, char decimalPointChar);

    public native void pango_tab_array_set_positions_in_pixels(Pointer cReference, boolean arePositionsInPixels);

    public native void pango_tab_array_set_tab(Pointer cReference, int tabIndex, int cValue, int position);

    public native void pango_tab_array_sort(Pointer cReference);

    public native String pango_tab_array_to_string(Pointer cReference);

    public native boolean gdk_rgba_equal(Pointer cReference, Pointer cReference1);

    public native void gdk_rgba_parse(GdkRGBA.GdkRGBAStruct.ByReference rgba, String parsable_string);

    public native Pointer gdk_rgba_copy(Pointer cReference);
}
