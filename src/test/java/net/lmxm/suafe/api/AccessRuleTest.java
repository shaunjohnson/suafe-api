package net.lmxm.suafe.api;

import org.junit.Test;

import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static net.lmxm.suafe.api.AccessLevel.*;

/**
 * Unit tests for access rule objects.
 */
public final class AccessRuleTest {
    @Test
    public void testAccessRule() {
        assertThat(AccessRule.class, is(protectedConstructor()));

        final User user = new User("userName", null);
        assertThat(new AccessRule(user, READ_WRITE, false).getUser(), is(sameInstance(user)));
        assertThat(new AccessRule(user, READ_WRITE, false).getUserGroup(), is(nullValue()));
        assertThat(new AccessRule(user, READ_WRITE, false).getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(new AccessRule(user, READ_WRITE, false).isExclusion(), is(false));

        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(new AccessRule(userGroup, READ_WRITE, false).getUser(), is(nullValue()));
        assertThat(new AccessRule(userGroup, READ_WRITE, false).getUserGroup(), is(sameInstance(userGroup)));
        assertThat(new AccessRule(userGroup, READ_WRITE, false).getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(new AccessRule(userGroup, READ_WRITE, false).isExclusion(), is(false));
    }

    @Test
    public void testSetAccessLevel() {
        assertThat(AccessRule.class, is(protectedMethod("setAccessLevel")));

        final AccessRule accessRule = new AccessRule(new User("userName", null), READ_WRITE, false);
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));

        accessRule.setAccessLevel(READ_ONLY);
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_ONLY)));
    }

    @Test
    public void testSetExclusion() {
        assertThat(AccessRule.class, is(protectedMethod("setExclusion")));

        final AccessRule accessRule = new AccessRule(new User("userName", null), READ_WRITE, false);
        assertThat(accessRule.isExclusion(), is(false));

        accessRule.setExclusion(true);
        assertThat(accessRule.isExclusion(), is(true));
    }
}