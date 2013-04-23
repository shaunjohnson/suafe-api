package net.lmxm.suafe.api;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Set;

public final class CustomMatchers {
    public static Matcher<Set> emptySet() {
        return new BaseMatcher<Set>() {
            @Override
            public boolean matches(Object item) {
                return Set.class.isInstance(item) && ((Set) item).isEmpty();
            }

            @Override
            public void describeTo(Description description) {
                // Ignore
            }
        };
    }

    private CustomMatchers() {
        throw new AssertionError("Cannot be instantiated");
    }
}
