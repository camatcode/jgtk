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
package com.gitlab.ccook;

import com.gitlab.ccook.jgtk.GMenu;
import com.gitlab.ccook.jgtk.GMenuItem;
import com.gitlab.ccook.jgtk.GtkApplication;
import com.gitlab.ccook.jgtk.IconName;
import com.gitlab.ccook.jgtk.bitfields.GApplicationFlags;
import com.gitlab.ccook.jgtk.bitfields.GConnectFlags;
import com.gitlab.ccook.jgtk.callbacks.GtkCallbackFunction;
import com.gitlab.ccook.jgtk.gtk.GtkWindow;
import com.sun.jna.Pointer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class JGTKJUnitTest {
    private static final Logger log = LoggerFactory.getLogger(JGTKJUnitTest.class);
    protected final AtomicBoolean shouldExit = new AtomicBoolean(true);
    private final List<File> filesToClear = new ArrayList<>();
    protected GtkApplication runningApplication;
    protected Thread runningApplicationThread;
    private GtkCallbackFunction activationFunction;

    public JGTKJUnitTest() {

    }

    @SuppressWarnings("CatchMayIgnoreException")
    @AfterEach
    public void after() {
        if (runningApplicationThread != null) {
            try {
                //noinspection removal
                runningApplicationThread.stop();
            } catch (Throwable e) {

            }
        }
        for (File f : filesToClear) {
            boolean deleted = f.delete();
            if (!deleted) {
                log.warn("Couldn't delete {}", f.getName());
            }
        }
    }

    public File getIconFile() throws IOException {
        File iconFile = new File("src/test/resources/3d-add-hole.png");
        File tmp = File.createTempFile("3d-add-hole", ".png");
        FileInputStream source = new FileInputStream(iconFile);
        FileOutputStream target = new FileOutputStream(tmp);
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) != -1) {
            target.write(buf, 0, length);
        }
        target.flush();
        target.close();
        source.close();
        filesToClear.add(tmp);
        return tmp;
    }

    protected List<IconName> getRandomIcons() {
        List<IconName> buttons = new ArrayList<>();
        File f = new File("/usr/share/icons/gnome/8x8/emblems/");
        if (f.exists()) {
            for (File s : Objects.requireNonNull(f.listFiles())) {
                String name = s.getName().split("\\.")[0];
                buttons.add(new IconName(name));
            }
        }

        return buttons;
    }

    protected File getSampleMP4() {
        return new File("src/test/resources/sample.mp4");
    }

    protected File getSampleOGG() {
        return new File("src/test/resources/sample.ogg");
    }

    public GMenu makeMainMenu() {

        GMenu section1 = new GMenu();
        GMenuItem undoItem = new GMenuItem("Undo", "app.undo");
        undoItem.setActionAndTarget("app.undo", null);
        GMenuItem redoItem = new GMenuItem("Redo", "app.redo");
        redoItem.setActionAndTarget("app.redo", null);
        section1.append(undoItem);
        section1.append(redoItem);
        GMenu section2 = new GMenu();
        GMenuItem cutItem = new GMenuItem("Cut", "app.cut");
        redoItem.setActionAndTarget("app.cut", null);
        GMenuItem copyItem = new GMenuItem("Copy", "app.copy");
        redoItem.setActionAndTarget("app.copy", null);
        GMenuItem pasteItem = new GMenuItem("Paste", "app.paste");
        redoItem.setActionAndTarget("app.paste", null);
        section2.append(cutItem);
        section2.append(copyItem);
        section2.append(pasteItem);

        GMenu mainMenu = new GMenu();
        mainMenu.appendSection("Section 1", section1);
        mainMenu.appendSection("Section 2", section2);
        return mainMenu;
    }

    @Test
    public void test() throws Exception {
        construct(makeApplication(), makeCallbackFunction());
        run();
    }

    public void construct(GtkApplication appToRun, GtkCallbackFunction activationFunction) {
        assertNotNull(appToRun);
        this.runningApplication = appToRun;
        this.activationFunction = activationFunction;
    }

    private GtkApplication makeApplication() {
        GtkApplication app = new GtkApplication("org.gtk.example." + getClass().getSimpleName(), GApplicationFlags.G_APPLICATION_FLAGS_NONE);
        app.shouldRegisterSession(true);
        return app;
    }

    public GtkCallbackFunction makeCallbackFunction() {
        return new GtkCallbackFunction() {
            @Override
            public GConnectFlags[] getConnectFlag() {
                return new GConnectFlags[]{GConnectFlags.G_CONNECT_AFTER};
            }

            @Override
            public Pointer getDataReference() {
                return null;
            }

            @Override
            public String getDetailedSignal() {
                return "activate";
            }

            @Override
            public void invoke(Pointer relevantThing, Pointer relevantData) {
                try {
                    testGtkElement(runningApplication);
                    while (!shouldExit.get()) {
                        Thread.sleep(500);
                    }
                    for (GtkWindow w : runningApplication.getWindows()) {
                        w.close();
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    fail(e.getMessage());
                }
            }
        };
    }

    @SuppressWarnings("BusyWait")
    public void run() throws Exception {
        runningApplicationThread = new Thread(() -> {
            runningApplication.connect(activationFunction);
            runningApplication.run(new String[]{});
        });
        runningApplicationThread.start();
        // System.out.println("Done run " + runningApplicationThread);
        long start = System.currentTimeMillis();
        while (runningApplicationThread.isAlive()) {
            Thread.sleep(500);
        }
        runningApplication.quit();

    }

    protected abstract void testGtkElement(GtkApplication gtkApplication) throws Exception;

}
