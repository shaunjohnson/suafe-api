package net.lmxm.suafe.api.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit tests for MessageResources.
 */
public final class MessageResourcesTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGet() {
        thrown.expect(NullPointerException.class);
        MessageResources.get(null);
    }
}
