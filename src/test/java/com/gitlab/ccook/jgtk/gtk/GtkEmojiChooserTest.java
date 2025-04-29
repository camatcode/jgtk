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

import com.gitlab.ccook.JGTKJUnitTest;
import com.gitlab.ccook.jgtk.GtkApplication;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GtkEmojiChooserTest extends JGTKJUnitTest {
    @Override
    protected void testGtkElement(GtkApplication gtkApplication) {
        GtkApplicationWindow w = new GtkApplicationWindow(gtkApplication);
        w.setDefaultSize(200, 200);
        w.setSizeRequest(200, 200);
        GtkTextView view = new GtkTextView();
        w.setChild(view);
        GtkEmojiChooser chooser = new GtkEmojiChooser();
        AtomicBoolean emojiPicked = new AtomicBoolean(false);
        chooser.connect((chooser1, text, userData) -> emojiPicked.set(true));
        view.addOverlay(chooser, 0, 0);
        w.show();
        chooser.emitSignal(GtkEmojiChooser.Signals.EMOJI_PICKED);
        assertTrue(emojiPicked.get());
        gtkApplication.quit();
    }
}
