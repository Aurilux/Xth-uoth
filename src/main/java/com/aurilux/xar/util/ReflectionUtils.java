package com.aurilux.xar.util;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtils {
    public static Field getField(Class clazz, String ... fieldNames) {
        try {
            Field field;
            if (fieldNames.length > 1) {
                field = ReflectionHelper.findField(clazz,
                    ObfuscationReflectionHelper.remapFieldNames(clazz.getName(), fieldNames));
            }
            else {
                field = clazz.getField(fieldNames[0]);
            }

            field.setAccessible(true);
            Field modfield = Field.class.getDeclaredField("modifiers");
            modfield.setAccessible(true);
            modfield.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            return field;
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static <T> T getProtectedValue(Class clazz, String ... fieldNames) {
        try {
            Field field = getField(clazz, fieldNames);
            return (T) field.get(null);
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static void setProtectedValue(Class clazz, Object classObject, Object newValue, String ... fieldNames) {
        try {
            Field field = getField(clazz, fieldNames);
            field.set(classObject, newValue);
        }
        catch(Exception ex) {}
    }

    public static Object invokeMethod(Class clazz, Object classObject, String methodName, Class[] paramTypes, Object ... args) {
        try {
            if (paramTypes == null) {
                paramTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Integer) {
                        paramTypes[i] = int.class;
                    } else {
                        paramTypes[i] = args[i].getClass();
                    }
                }
            }
            Method m = clazz.getDeclaredMethod(methodName, paramTypes);
            m.setAccessible(true);
            return m.invoke(classObject, args);
        }
        catch(Exception ex) {
            System.out.println(ex.getCause() + ", ");
            ex.printStackTrace();
            return null;
        }
    }
}
