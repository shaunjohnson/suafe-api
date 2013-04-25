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

        assertThat(new UserGroup("userGroupName").getUserMembers(), is(emptySet()));
        assertThat(new UserGroup("userGroupName").getUserMembers(), is(immutableSet()));
    }

    @Test
    public void testAddUser() {
        assertThat(UserGroup.class, is(protectedMethod("addUserMember")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final User user = new User("userName", null);
        assertThat(userGroup.getUserMembers(), is(emptySet()));

        // Test
        assertThat(userGroup.addUserMember(user), is(true));
        assertThat(userGroup.addUserMember(user), is(false));
        assertThat(userGroup.getUserMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserMembers(), is(containsSameInstance(user)));
    }

    @Test
    public void testAddUserGroup() {
        assertThat(UserGroup.class, is(protectedMethod("addUserGroup")));

        // Setup
        final UserGroup member = new UserGroup("memberUserGroupName");
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(userGroup.getUserGroups(), is(emptySet()));

        // Test
        assertThat(userGroup.addUserGroup(member), is(true));
        assertThat(userGroup.addUserGroup(member), is(false));
        assertThat(userGroup.getUserGroups(), is(not(emptySet())));
        assertThat(userGroup.getUserGroups(), is(containsSameInstance(member)));
    }

    @Test
    public void testGetUserGroups() {
        // Setup
        final UserGroup member = new UserGroup("memberUserGroupName");
        final UserGroup userGroup = new UserGroup("serGroupName");
        assertThat(userGroup.getUserGroups(), is(emptySet()));

        // Test
        assertThat(userGroup.addUserGroup(member), is(true));
        assertThat(userGroup.getUserGroups(), is(not(emptySet())));
        assertThat(userGroup.getUserGroups(), is(containsSameInstance(member)));
        assertThat(userGroup.removeUserGroup(member), is(true));
        assertThat(userGroup.getUserGroups(), is(emptySet()));
    }

    @Test
    public void testGetUserMembers() {
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(userGroup.getUserMembers(), is(immutableSet()));
        assertThat(userGroup.getUserMembers(), is(notNullValue()));
    }

    @Test
    public void testRemoveUser() {
        assertThat(UserGroup.class, is(protectedMethod("removeUserMember")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final User user = new User("userName", null);
        assertThat(userGroup.getUserMembers(), is(emptySet()));
        assertThat(userGroup.addUserMember(user), is(true));
        assertThat(userGroup.getUserMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserMembers(), is(containsSameInstance(user)));

        // Test
        assertThat(userGroup.removeUserMember(user), is(true));
        assertThat(userGroup.removeUserMember(user), is(false));
        assertThat(userGroup.getUserMembers(), is(emptySet()));
    }

    @Test
    public void testRemoveUserGroup() {
        assertThat(UserGroup.class, is(protectedMethod("removeUserGroup")));

        // Setup
        final UserGroup member = new UserGroup("memberUserGroupName");
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(userGroup.getUserGroups(), is(emptySet()));
        assertThat(userGroup.addUserGroup(member), is(true));
        assertThat(userGroup.getUserGroups(), is(not(emptySet())));
        assertThat(userGroup.getUserGroups(), is(containsSameInstance(member)));

        // Test
        assertThat(userGroup.removeUserGroup(member), is(true));
        assertThat(userGroup.removeUserGroup(member), is(false));
        assertThat(userGroup.getUserGroups(), is(emptySet()));
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
