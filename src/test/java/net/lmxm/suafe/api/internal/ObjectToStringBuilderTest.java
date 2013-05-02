package net.lmxm.suafe.api.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for ObjectToStringBuilder.
 */
public final class ObjectToStringBuilderTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testObjectToStringBuilder() {
        new ObjectToStringBuilder(ObjectToStringBuilderTest.class);

        thrown.expect(IllegalArgumentException.class);
        new ObjectToStringBuilder(null);
    }

    @Test
    public void testAppend() {
        final ObjectToStringBuilder builder = new ObjectToStringBuilder(ObjectToStringBuilderTest.class);

        assertThat(builder.append("name", "value"), is(sameInstance(builder)));

        thrown.expect(IllegalArgumentException.class);
        new ObjectToStringBuilder(ObjectToStringBuilderTest.class).append(null, "value");
    }

    @Test
    public void testBuildNullValue() {
        final ObjectToStringBuilder builder = new ObjectToStringBuilder(ObjectToStringBuilderTest.class);
        builder.append("name", null);
        assertThat(builder.build(), is(equalTo("[ObjectToStringBuilderTest: name=<null>]")));
    }

    @Test
    public void testBuild() {
        final ObjectToStringBuilder builder = new ObjectToStringBuilder(ObjectToStringBuilderTest.class);
        builder.append("name", "value");
        assertThat(builder.build(), is(equalTo("[ObjectToStringBuilderTest: name=value]")));
    }
}
