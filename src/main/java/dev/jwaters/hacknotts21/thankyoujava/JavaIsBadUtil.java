package dev.jwaters.hacknotts21.thankyoujava;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class JavaIsBadUtil {
    private JavaIsBadUtil() {}

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T>[] getConcreteClasses(Class<T> clazz) {
        List<Class<?>> classes = new ArrayList<>();
        getConcreteClasses(clazz, classes);
        //noinspection SuspiciousToArrayCall
        return classes.toArray((Class<? extends T>[]) new Class<?>[0]);
    }

    private static void getConcreteClasses(Class<?> clazz, List<Class<?>> classes) {
        if (!Modifier.isAbstract(clazz.getModifiers())) {
            classes.add(clazz);
        }
        if (clazz.isSealed()) {
            for (Class<?> permittedSubclass : clazz.getPermittedSubclasses()) {
                getConcreteClasses(permittedSubclass, classes);
            }
        }
    }
}
