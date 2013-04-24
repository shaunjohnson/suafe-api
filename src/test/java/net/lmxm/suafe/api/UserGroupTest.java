package net.lmxm.suafe.api;

import org.junit.Test;

import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class UserGroupTest {
    @Test
    public void testUserGroup() {
        assertThat(UserGroup.class, is(protectedConstructor()));

        assertThat(new UserGroup("userGroupName").getName(), is(equalTo("userGroupName")));

        assertThat(new UserGroup("userGroupName").getUsers(), is(emptySet()));
        assertThat(new UserGroup("userGroupName").getUsers(), is(immutableSet()));
    }

    @Test
    public void testAddUser() {
        assertThat(UserGroup.class, is(protectedMethod("addUser")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final User user = new User("userName", null);
        assertThat(userGroup.getUsers(), is(emptySet()));

        // Test
        assertThat(userGroup.addUser(user), is(true));
        assertThat(userGroup.addUser(user), is(false));
        assertThat(userGroup.getUsers(), is(not(emptySet())));
        assertThat(userGroup.getUsers(), is(containsSameInstance(user)));
    }

    @Test
    public void testGetUsers() {
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(userGroup.getUsers(), is(immutableSet()));
        assertThat(userGroup.getUsers(), is(notNullValue()));
    }

    @Test
    public void testRemoveUser() {
        assertThat(UserGroup.class, is(protectedMethod("removeUser")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final User user = new User("userName", null);
        assertThat(userGroup.getUsers(), is(emptySet()));
        assertThat(userGroup.addUser(user), is(true));
        assertThat(userGroup.getUsers(), is(not(emptySet())));
        assertThat(userGroup.getUsers(), is(containsSameInstance(user)));

        // Test
        assertThat(userGroup.removeUser(user), is(true));
        assertThat(userGroup.removeUser(user), is(false));
        assertThat(userGroup.getUsers(), is(emptySet()));
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