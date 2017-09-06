package com.geekbrains;

import com.geekbrains.Annotations.AfterSuite;
import com.geekbrains.Annotations.BeforeSuite;
import com.geekbrains.Annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.TreeSet;

public class MyTest {
    private Method[] methods;
    private Method before = null;
    private Method after = null;
    private Class tClass = null;
    private TreeSet<Method> testMethods = new TreeSet<>(new Comparator<Method>() {
        int testPriority1;
        int testPriority2;

        @Override
        public int compare(Method o1, Method o2) {
            testPriority1 = o1.getAnnotation(Test.class).priority();
            testPriority2 = o2.getAnnotation(Test.class).priority();

            return (testPriority1 < testPriority2) ? -1 : 1;
        }
    });


    public static void start(Class testClass) {
        MyTest myTest = new MyTest();
        myTest.tClass = testClass;
        myTest.analysis(myTest);
        myTest.doBefore(myTest);
        myTest.doTests(myTest);
        myTest.doAfter(myTest);
    }

    private void analysis(MyTest myTest) {
        myTest.methods = tClass.getDeclaredMethods();
        for (Method method : myTest.methods) {

            method.setAccessible(true);

            if (method.getAnnotation(BeforeSuite.class) != null) {
                if (myTest.before == null) {
                    myTest.before = method;
                } else {
                    throw new RuntimeException("Несколько методов BeforeSuite");
                }
            }

            if (method.getAnnotation(AfterSuite.class) != null) {
                if (myTest.after == null) {
                    myTest.after = method;
                } else {
                    throw new RuntimeException("Несколько методов AfterSuite");
                }
            }

            if (method.getAnnotation(Test.class) != null) {
                if (method.getAnnotation(Test.class).priority() >= 1
                        && method.getAnnotation(Test.class).priority() <= 10) {
                    myTest.testMethods.add(method);
                }
            }
        }
    }

    private void doBefore(MyTest myTest) {
        try {
            myTest.before.invoke(tClass.newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void doAfter(MyTest myTest) {
        try {
            myTest.after.invoke(tClass.newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void doTests(MyTest myTest) {
        try {
            for (Method method : myTest.testMethods) {
                method.invoke(tClass.newInstance());
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
