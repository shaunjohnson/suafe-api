package net.lmxm.suafe.api;

import org.junit.Test;

import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class UserTest {
    @Test
    public void testUserGroup() {
        assertThat(UserGroup.class, is(protectedConstructor()));

        assertThat(new UserGroup("userGroupName").getName(), is(equalTo("userGroupName")));
        assertThat(new UserGroup("userGroupName").getUsers(), is(emptySet()));
        assertThat(new UserGroup("userGroupName").getUsers(), is(immutableSet()));
    }

    @Test
    public void testSetName() {
        assertThat(UserGroup.class, is(protectedMethod("setName")));

        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(userGroup.getName(), is(equalTo("userGroupName")));
        userGroup.setName("newUserGroupName");
        assertThat(userGroup.getName(), is(equalTo("newUserGroupName")));
    }
}
