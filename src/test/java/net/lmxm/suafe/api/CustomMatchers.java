package net.lmxm.suafe.api;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * Custom unit test matchers.
 */
public final class CustomMatchers {
    /**
     * Matcher that ensures a set contains an object.
     *
     * @return Set contains same instance matcher
     */
    public static <T> Matcher<Set<T>> containsSameInstance(final T target) {
        return new BaseMatcher<Set<T>>() {
            @SuppressWarnings(value = "unchecked")
            public boolean matches(final Object item) {
                for (final T t : ((Set<T>)item)) {
                    if (t == target) {
                        return true;
                    }
                }

                return false;
            }

            public void describeTo(Description description) {
                description.appendText("not contains same instance");
            }
        };
    }

    /**
     * Matcher that ensures a set contains a tree node with name.
     *
     * @return Set contains tree node with name matcher
     */
    public static Matcher<Set<TreeNode>> containsNodeWithName(final String name) {
        return new BaseMatcher<Set<TreeNode>>() {
            @SuppressWarnings(value = "unchecked")
            public boolean matches(final Object item) {
                for (final TreeNode treeNode : ((Set<TreeNode>)item)) {
                    if (treeNode.getName().equals(name)) {
                        return true;
                    }
                }

                return false;
            }

            public void describeTo(Description description) {
                description.appendText("not contains node with name ").appendValue(name);
            }
        };
    }

    /**
     * Matcher that ensures a set is empty.
     *
     * @return Empty set matcher
     */
    public static Matcher<Set> emptySet() {
        return new BaseMatcher<Set>() {
            public boolean matches(final Object item) {
                return Set.class.isInstance(item) && ((Set) item).isEmpty();
            }

            public void describeTo(final Description description) {
                description.appendText("not empty set");
            }
        };
    }

    /**
     * Matcher that ensures a set is immutable.
     *
     * @return Immutable set matcher
     */
    public static Matcher<Set> immutableSet() {
        return new BaseMatcher<Set>() {
            @SuppressWarnings(value = "unchecked")
            public boolean matches(final Object item) {
                if (!Set.class.isInstance(item)) {
                    return false;
                }

                final Set set = (Set) item;

                try {
                    set.add(null);
                    return false;
                }
                catch (final UnsupportedOperationException e) {
                    // Expected
                }

                try {
                    set.addAll(null);
                    return false;
                }
                catch (final UnsupportedOperationException e) {
                    // Expected
                }

                try {
                    set.clear();
                    return false;
                }
                catch (final UnsupportedOperationException e) {
                    // Expected
                }

                try {
                    set.remove(null);
                    return false;
                }
                catch (final UnsupportedOperationException e) {
                    // Expected
                }

                try {
                    set.removeAll(null);
                    return false;
                }
                catch (final UnsupportedOperationException e) {
                    // Expected
                }

                try {
                    set.retainAll(null);
                    return false;
                }
                catch (final UnsupportedOperationException e) {
                    // Expected
                }

                return true;
            }

            public void describeTo(final Description description) {
                description.appendText("not throwing UnsupportedOperationException when set is modified");
            }
        };
    }

    /**
     * Matcher that ensures a class has only protected constructors
     *
     * @return Protected constructor matcher
     */
    public static Matcher<Class> protectedConstructor() {
        return new BaseMatcher<Class>() {
            public boolean matches(final Object item) {
                if (!Class.class.isInstance(item)) {
                    return false;
                }

                for (final Constructor constructor : ((Class) item).getConstructors()) {
                    if (!Modifier.isProtected(constructor.getModifiers())) {
                        return false;
                    }
                }

                return true;
            }

            public void describeTo(final Description description) {
                description.appendText("not a protected constructor");
            }
        };
    }

    /**
     * Matcher that ensures a method is protected.
     *
     * @param methodName Name of method to test
     * @return Protected method matcher
     */
    public static Matcher<Class> protectedMethod(final String methodName) {
        return new BaseMatcher<Class>() {
            public boolean matches(final Object item) {
                if (!Class.class.isInstance(item)) {
                    return false;
                }

                final Method method = findMethodByName((Class) item, methodName);

                return method != null && Modifier.isProtected(method.getModifiers());
            }

            public void describeTo(Description description) {
                description.appendText("not not a protected method");
            }

            /**
             * Finds a declared method with the provided name.
             *
             * @param clazz Class in which to search for the method
             * @param methodName Name of the method to find
             * @return Matching method or null if no match was found
             */
            private Method findMethodByName(final Class clazz, final String methodName) {
                for (final Method method : clazz.getDeclaredMethods()) {
                    if (method.getName().equals(methodName)) {
                        return method;
                    }
                }

                return null;
            }
        };
    }

    /**
     * Prevent instantiation.
     */
    private CustomMatchers() {
        throw new AssertionError("Cannot be instantiated");
    }
}
