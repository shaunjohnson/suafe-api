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
    public void testContainsNodeWithName() {
        // Setup
        final Set<TreeNode> set = new HashSet<>();
        set.add(new TreeNode("foobar", new TreeNode()));

        // Test
        assertThat(set, is(containsNodeWithName("foobar")));
        assertThat(set, is(not(containsNodeWithName("does not exist"))));
    }

    @Test
    public void testContainsSameInstance() {
        // Setup
        final String target = "target";
        final Set<String> set = new HashSet<>();
        assertThat(set, is(not(containsSameInstance(target))));

        // Test
        set.add(target);
        assertThat(set, is(containsSameInstance(target)));
        assertThat(set, is(not(containsSameInstance("not the target"))));
    }

    @Test
    public void testEmptySet() {
        final Set<String> set = new HashSet<>();
        assertThat(set, is(emptySet()));

        set.add("foobar");
        assertThat(set, is(not(emptySet())));
    }

    @Test
    public void testImmutableSet() {
        final Set<String> mutableSet = new HashSet<>();
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
