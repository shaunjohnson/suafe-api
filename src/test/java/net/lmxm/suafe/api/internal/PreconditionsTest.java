package net.lmxm.suafe.api.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public final class PreconditionsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCheckNotNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("foobar");
        Preconditions.checkNotNull(null, "foobar");

        final String value = "value to test";
        assertThat(Preconditions.checkNotNull(value, "foobar"), is(sameInstance(value)));
    }
}
