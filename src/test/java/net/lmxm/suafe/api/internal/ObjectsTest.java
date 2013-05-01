package net.lmxm.suafe.api.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for Objects class.
 */
public final class ObjectsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testHashCode() {
        final int hashCode = Arrays.hashCode(new Object[] { "foo", "bar" });

        assertThat(Objects.hashCode("foo", "bar"), is(equalTo(hashCode)));
    }

    @Test
    public void testHashCodeInvalidValues() {
        thrown.expect(IllegalArgumentException.class);
        Objects.hashCode("one object is not allows");
    }

    @Test
    public void testEqual() {
        assertThat(Objects.equal(null, null), is(true));
        assertThat(Objects.equal("foobar", null), is(false));
        assertThat(Objects.equal(null, "foobar"), is(false));
        assertThat(Objects.equal("foobar", "foobar"), is(true));
    }
}
