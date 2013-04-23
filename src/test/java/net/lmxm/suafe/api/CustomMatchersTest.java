package net.lmxm.suafe.api;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Unit tests to ensure that the CustomMatchers are working as expected.
 */
public final class CustomMatchersTest {
    private static class HasProtectedConstructor {
        protected HasProtectedConstructor() {
            super();
        }
    }

    private static class HasPublicConstructor {
        public HasPublicConstructor() {
            super();
        }
    }

    private static class HasTestMethods {
        public void publicMethod() {

        }

        protected void protectedMethod() {

        }
    }

    @Test
    public void testContainsSameInstance() {
        // Setup
        final String target = "target";
        final Set<String> set = new HashSet<String>();
        assertThat(set, is(not(containsSameInstance(target))));

        // Test
        set.add(target);
        assertThat(set, is(containsSameInstance(target)));
        assertThat(set, is(not(containsSameInstance("not the target"))));
    }

    @Test
    public void testEmptySet() {
        final Set<String> set = new HashSet<String>();
        assertThat(set, is(emptySet()));

        set.add("foobar");
        assertThat(set, is(not(emptySet())));
    }

    @Test
    public void testImmutableSet() {
        final Set<String> mutableSet = new HashSet<String>();
        assertThat(mutableSet, is(not(immutableSet())));

        final Set<String> immutableSet = Collections.unmodifiableSet(mutableSet);
        assertThat(immutableSet, is(immutableSet()));
    }

    @Test
    public void testProtectedConstructor() {
        assertThat(HasProtectedConstructor.class, is(protectedConstructor()));
        assertThat(HasPublicConstructor.class, is(not(protectedConstructor())));
    }

    @Test
    public void testProtectedMethod() {
        assertThat(HasTestMethods.class, is(protectedMethod("protectedMethod")));
        assertThat(HasTestMethods.class, is(not(protectedMethod("publicMethod"))));
        assertThat(HasTestMethods.class, is(not(protectedMethod("methodThatDoesNotExist"))));
    }
}
