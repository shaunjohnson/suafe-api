package net.lmxm.suafe.api;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class CustomMatchers {
    public static Matcher<Set> emptySet() {
        return new BaseMatcher<Set>() {
            @Override
            public boolean matches(Object item) {
                return Set.class.isInstance(item) && ((Set) item).isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("not empty set");
            }
        };
    }

    public static Matcher<Set> immutableSet() {
        return new BaseMatcher<Set>() {
            @Override
            @SuppressWarnings(value = "unchecked")
            public boolean matches(Object item) {
                assertThat(Set.class.isInstance(item), is(true));

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

            @Override
            public void describeTo(Description description) {
                description.appendText("not throwing UnsupportedOperationException when set is modified");
            }
        };
    }

    private CustomMatchers() {
        throw new AssertionError("Cannot be instantiated");
    }
}
