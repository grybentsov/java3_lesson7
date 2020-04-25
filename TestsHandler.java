package ru.geekbrains.java3_lesson7;

import java.lang.reflect.*;
import java.util.*;

public class TestsHandler {
    private static Object object;

    public static void start(Class someClass) {
        if (!withAnnotations(someClass)) {
            throw new RuntimeException();
        }

        try {
            object = someClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Method beforeMethod = null;
        Method afterMethod = null;
        ArrayList<Method> testsMethods = new ArrayList<>();
        Method[] objectMethods = someClass.getDeclaredMethods();

        for (Method method : objectMethods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeMethod = method;
            } else if (method.getAnnotation(AfterSuite.class) != null) {
                afterMethod = method;
            } else if (method.getAnnotation(Test.class) != null) {
                testsMethods.add(method);
            }
        }

        testsMethods.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()));

        if (beforeMethod != null) {
            testsMethods.add(0, beforeMethod);
        }

        if (afterMethod != null) {
            testsMethods.add(afterMethod);
        }

        try {
            for (Method testMethod : testsMethods) {
                if (Modifier.isPrivate(testMethod.getModifiers())) {
                    testMethod.setAccessible(true);
                }
                testMethod.invoke(object);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static boolean withAnnotations(Class someClass) {
        int beforeAnnotationCount = 0;
        int afterAnnotationCount = 0;

        for (Method method : someClass.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeAnnotationCount++;
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                afterAnnotationCount++;
            }
        }

        return (beforeAnnotationCount < 2) && (afterAnnotationCount < 2);
    }
}