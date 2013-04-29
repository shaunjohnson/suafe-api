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
    public void testAddUserGroupMember() {
        assertThat(UserGroup.class, is(protectedMethod("addUserGroupMember")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final UserGroup memberUserGroup = new UserGroup("memberUserGroupName");
        assertThat(userGroup.getUserGroupMembers(), is(emptySet()));

        // Test
        assertThat(userGroup.addUserGroupMember(memberUserGroup), is(true));
        assertThat(userGroup.addUserGroupMember(memberUserGroup), is(false));
        assertThat(userGroup.getUserGroupMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserGroupMembers(), is(containsSameInstance(memberUserGroup)));
    }

    @Test
    public void testAddUserMember() {
        assertThat(UserGroup.class, is(protectedMethod("addUserMember")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final User memberUser = new User("memberUserName", null);
        assertThat(userGroup.getUserMembers(), is(emptySet()));

        // Test
        assertThat(userGroup.addUserMember(memberUser), is(true));
        assertThat(userGroup.addUserMember(memberUser), is(false));
        assertThat(userGroup.getUserMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserMembers(), is(containsSameInstance(memberUser)));
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
    public void testGetUserGroupMembers() {
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(userGroup.getUserGroupMembers(), is(immutableSet()));
        assertThat(userGroup.getUserGroupMembers(), is(notNullValue()));
        assertThat(userGroup.getUserGroupMembers(), is(emptySet()));

        final UserGroup memberUserGroup = new UserGroup("memberUserGroupName");
        userGroup.addUserGroupMember(memberUserGroup);
        assertThat(userGroup.getUserGroupMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserGroupMembers(), is(containsSameInstance(memberUserGroup)));
    }

    @Test
    public void testGetUserMembers() {
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(userGroup.getUserMembers(), is(immutableSet()));
        assertThat(userGroup.getUserMembers(), is(notNullValue()));
        assertThat(userGroup.getUserMembers(), is(emptySet()));

        final User memberUser = new User("memberUserName", null);
        userGroup.addUserMember(memberUser);
        assertThat(userGroup.getUserMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserMembers(), is(containsSameInstance(memberUser)));
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
    public void testRemoveUserGroupMember() {
        assertThat(UserGroup.class, is(protectedMethod("removeUserGroupMember")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final UserGroup memberUserGroup = new UserGroup("memberUserGroupName");
        assertThat(userGroup.getUserGroupMembers(), is(emptySet()));
        assertThat(userGroup.addUserGroupMember(memberUserGroup), is(true));
        assertThat(userGroup.getUserGroupMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserGroupMembers(), is(containsSameInstance(memberUserGroup)));

        // Test
        assertThat(userGroup.removeUserGroupMember(memberUserGroup), is(true));
        assertThat(userGroup.removeUserGroupMember(memberUserGroup), is(false));
        assertThat(userGroup.getUserGroupMembers(), is(emptySet()));
    }

    @Test
    public void testRemoveUserMember() {
        assertThat(UserGroup.class, is(protectedMethod("removeUserMember")));

        // Setup
        final UserGroup userGroup = new UserGroup("userGroupName");
        final User memberUser = new User("memberUserName", null);
        assertThat(userGroup.getUserMembers(), is(emptySet()));
        assertThat(userGroup.addUserMember(memberUser), is(true));
        assertThat(userGroup.getUserMembers(), is(not(emptySet())));
        assertThat(userGroup.getUserMembers(), is(containsSameInstance(memberUser)));

        // Test
        assertThat(userGroup.removeUserMember(memberUser), is(true));
        assertThat(userGroup.removeUserMember(memberUser), is(false));
        assertThat(userGroup.getUserMembers(), is(emptySet()));
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
