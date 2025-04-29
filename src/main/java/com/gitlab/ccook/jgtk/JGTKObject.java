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

import com.gitlab.ccook.jgtk.interfaces.GtkInterface;
import com.gitlab.ccook.util.Option;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings({"unchecked", "EqualsWhichDoesntCheckParameterClass", "rawtypes"})
public class JGTKObject implements GtkInterface {

    final static Reflections reflections = new Reflections(JGTKObject.class.getPackage().getName());
    final static Set subtypes = reflections.getSubTypesOf(JGTKObject.class);
    protected final Set<Object> trackedObjects = new HashSet<>();
    protected Pointer cReference;

    public JGTKObject(Pointer cReference) {
        this.cReference = cReference;

    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static JGTKObject newObjectFromType(Pointer pointer, Class<? extends JGTKObject> fallback) {
        try {
            Class c = getType(pointer);
            if (!Modifier.isAbstract(c.getModifiers())) {
                return (JGTKObject) c.getDeclaredConstructor(Pointer.class).newInstance(pointer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return fallback.getDeclaredConstructor(Pointer.class).newInstance(pointer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Could not create object from type " + pointer + " " + fallback);
    }

    public static Class<? extends JGTKObject> getType(Pointer cReference) {
        String cName = library.g_type_name_from_instance(cReference);
        for (Object obj : subtypes) {
            if (obj instanceof Class) {
                if (((Class) obj).getSimpleName().equalsIgnoreCase(cName)) {
                    return (Class<? extends JGTKObject>) obj;
                }
            }
        }
        System.out.println(cName);
        return JGTKObject.class;
    }

    protected static Pointer pointerOrNull(GtkInterface thing) {
        if (thing != null) {
            return thing.getCReference();
        }
        return Pointer.NULL;
    }

    protected static List<String> toList(String[] things) {
        List<String> thingsList = new ArrayList<>();
        if (things != null) {
            thingsList.addAll(Arrays.asList(things));
        }
        return thingsList;
    }

    protected static String[] toPrimitive(List<String> things) {
        if (things != null) {
            String[] thingsArr = new String[things.size()];
            for (int i = 0; i < thingsArr.length; i++) {
                thingsArr[i] = things.get(i);
            }
            return thingsArr;
        }
        return new String[0];
    }

    public Pointer getCReference() {
        return cReference;
    }

    @Override
    public JGTKObject toJGTKObject(JGTKObject gtkEditable) {
        return this;
    }

    protected void preventGarbageCollection(Object... objects) {
        for (Object o : objects) {
            if (o != null) {
                trackedObjects.add(o);
            }
        }
    }

    protected void setProperty(String propertyName, Pointer cReference, String text) {
        Option<GValue> property = getProperty(propertyName);
        if (property.isDefined()) {
            property.get().setText(text);
            library.g_object_set_property(cReference, propertyName, property.get().cReference);
        }
    }


    public Option<GValue> getProperty(String property) {
        PointerByReference ref = new PointerByReference();
        if (property != null) {
            library.g_object_get_property(cReference, property, ref);
            return new Option<>(new GValue(ref.getPointer()));
        }
        return Option.NONE;
    }

    protected void setProperty(String propertyName, Pointer cRef, Pointer value) {
        Option<GValue> property = getProperty(propertyName);
        if (property.isDefined()) {
            property.get().setObject(value);
            library.g_object_set_property(cReference, propertyName, property.get().cReference);
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected void setProperty(String propertyName, Pointer cReference, boolean bool) {
        Option<GValue> property = getProperty(propertyName);
        if (property.isDefined()) {
            property.get().setBoolean(bool);
            library.g_object_set_property(cReference, propertyName, property.get().cReference);
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected void setPropertyUInt(String propertyName, Pointer cReference, int value) {
        Option<GValue> property = getProperty(propertyName);
        if (property.isDefined()) {
            property.get().setUInt(value);
            library.g_object_set_property(cReference, propertyName, property.get().cReference);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null) {
            return this.hashCode() == o.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cReference);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "(" + cReference + ")";
    }

    public static class Signals {
        private final String detailedName;

        protected Signals(String detailedName) {
            this.detailedName = detailedName;
        }


        public String getDetailedName() {
            return detailedName;
        }
    }


}
