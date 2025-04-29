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

import com.gitlab.ccook.jgtk.GError;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface GtkCLib extends Library {

    int g_application_run(Pointer application, int argc, String[] argv);

    void g_signal_emit_by_name(Pointer cReference, String detailedName, Pointer[] params);

    void gtk_about_dialog_add_credit_section(Pointer cReference, String sectionName, String[] people);

    String[] gtk_about_dialog_get_artists(Pointer cReference);

    String[] gtk_about_dialog_get_authors(Pointer cReference);

    String[] gtk_about_dialog_get_documenters(Pointer cReference);

    void gtk_about_dialog_set_artists(Pointer cReference, String[] arr);

    void gtk_about_dialog_set_authors(Pointer cReference, String[] toPrimitive);

    void gtk_about_dialog_set_documenters(Pointer cReference, String[] toPrimitive);

    void gtk_accessible_update_property_value(Pointer cReference, int i, int prop, Pointer[] pointers);

    void gtk_accessible_update_relation_value(int cValue, int i, int cValue1, Pointer[] pointers);

    void gtk_accessible_update_state_value(int cValue, int i, int cValue1, Pointer[] pointers);

    String[] gtk_application_get_accels_for_action(Pointer cReference, String detailedActionName);

    String[] gtk_application_get_actions_for_accel(Pointer cReference, String accel);

    String[] gtk_application_list_action_descriptions(Pointer cReference);

    void gtk_application_set_accels_for_action(Pointer cReference, String detailedActionName, String[] accels);

    boolean gtk_builder_add_objects_from_file(Pointer builder, String filename, String[] object_ids, GError.GErrorStruct.ByReference error);

    boolean gtk_builder_add_objects_from_string(Pointer builder, String buffer, int length, String[] object_ids, GError.GErrorStruct.ByReference error);

    Pointer gtk_scale_button_new(double min, double max, double step, String[] icons);

    void gtk_scale_button_set_icons(Pointer cReference, String[] icons);

    Pointer gtk_string_list_new(String[] strings);

    Pointer gtk_text_buffer_create_tag(Pointer cReference, String tagName, String[] propertiesPrim);

    void gtk_text_buffer_insert_with_tags(Pointer cReference, Pointer cReference1, String text, int length, Pointer[] tagsToApply);

    void gtk_text_buffer_insert_with_tags_by_name(Pointer cReference, Pointer cReference1, String text, int length, String[] tagNamesToApply);

    Pointer[] gtk_text_child_anchor_get_widgets(Pointer cReference, PointerByReference len);

    boolean gtk_widget_activate_action(Pointer cReference, String actionName, String[] gvariantFormatStrings);

    void gtk_file_chooser_add_choice(Pointer chooser, String id, String label, String[] options, String[] option_labels);

    String[] gtk_file_filter_get_attributes(Pointer self);


    Pointer gtk_file_chooser_dialog_new(String title, Pointer parent, int action, String label, int response, Pointer p);

    Pointer gtk_message_dialog_new(Pointer parent, int flags, int type, int buttons, String[] msg);
}
