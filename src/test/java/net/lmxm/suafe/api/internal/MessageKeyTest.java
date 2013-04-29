package net.lmxm.suafe.api.internal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for MessageKey class.
 */
public final class MessageKeyTest {
    @Test
    public void testMessageKey() {
        for (final MessageKey messageKey : MessageKey.values()) {
            assertThat(MessageResources.get(messageKey), is(not(nullValue())));
        }
    }

    @Test
    public void testMessageResources() {
        for (final String key : MessageResources.getKeys()) {
            assertThat(MessageKey.valueOf(key), is(not(nullValue())));
        }
    }
}
