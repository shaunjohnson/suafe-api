package net.lmxm.suafe.api;

import org.junit.Test;

import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class UserTest {
    @Test
    public void testUser() {
        assertThat(User.class, is(protectedConstructor()));

        assertThat(new User("userName", null).getName(), is(equalTo("userName")));
        assertThat(new User("userName", null).getAlias(), is(nullValue()));

        assertThat(new User("userName", "userAlias").getName(), is(equalTo("userName")));
        assertThat(new User("userName", "userAlias").getAlias(), is(equalTo("userAlias")));

        assertThat(new User("userName", null).getUserGroups(), is(emptySet()));
        assertThat(new User("userName", null).getUserGroups(), is(immutableSet()));
    }

    @Test
    public void testAddUserGroup() {
        assertThat(User.class, is(protectedMethod("addUserGroup")));

        // Setup
        final User user = new User("userName", null);
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(user.getUserGroups(), is(emptySet()));

        // Test
        assertThat(user.addUserGroup(userGroup), is(true));
        assertThat(user.addUserGroup(userGroup), is(false));
        assertThat(user.getUserGroups(), is(not(emptySet())));
        assertThat(user.getUserGroups(), is(containsSameInstance(userGroup)));
    }

    @Test
    public void testGetUserGroups() {
        // Setup
        final User user = new User("userName", null);
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(user.getUserGroups(), is(emptySet()));

        // Test
        assertThat(user.addUserGroup(userGroup), is(true));
        assertThat(user.getUserGroups(), is(not(emptySet())));
        assertThat(user.getUserGroups(), is(containsSameInstance(userGroup)));
        assertThat(user.removeUserGroup(userGroup), is(true));
        assertThat(user.getUserGroups(), is(emptySet()));
    }

    @Test
    public void testGetUsers() {
        final User user = new User("userName", null);
        assertThat(user.getUserGroups(), is(immutableSet()));
        assertThat(user.getUserGroups(), is(notNullValue()));
    }

    @Test
    public void testRemoveUserGroup() {
        assertThat(User.class, is(protectedMethod("removeUserGroup")));

        // Setup
        final User user = new User("userName", null);
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(user.getUserGroups(), is(emptySet()));
        assertThat(user.addUserGroup(userGroup), is(true));
        assertThat(user.getUserGroups(), is(not(emptySet())));
        assertThat(user.getUserGroups(), is(containsSameInstance(userGroup)));

        // Test
        assertThat(user.removeUserGroup(userGroup), is(true));
        assertThat(user.removeUserGroup(userGroup), is(false));
        assertThat(user.getUserGroups(), is(emptySet()));
    }

    @Test
    public void testSetAlias() {
        assertThat(User.class, is(protectedMethod("setAlias")));

        final User user = new User("userName", "userAlias");
        assertThat(user.getAlias(), is(equalTo("userAlias")));

        user.setAlias("newUserAlias");
        assertThat(user.getAlias(), is(equalTo("newUserAlias")));
    }

    @Test
    public void testSetName() {
        assertThat(User.class, is(protectedMethod("setName")));

        final User user = new User("userName", "userAlias");
        assertThat(user.getName(), is(equalTo("userName")));

        user.setName("newUserName");
        assertThat(user.getName(), is(equalTo("newUserName")));
    }
}
