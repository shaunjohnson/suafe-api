package net.lmxm.suafe.api;

import org.junit.Test;

import static net.lmxm.suafe.api.AccessLevel.READ_WRITE;
import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

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
    public void testAddAccessRule() {
        assertThat(User.class, is(protectedMethod("addAccessRule")));

        // Setup
        final User user = new User("userName", null);
        final AccessRule accessRule = new AccessRule(user, READ_WRITE, false);
        assertThat(user.getAccessRules(), is(emptySet()));

        // Test
        assertThat(user.addAccessRule(accessRule), is(true));
        assertThat(user.addAccessRule(accessRule), is(false));
        assertThat(user.getAccessRules(), is(not(emptySet())));
        assertThat(user.getAccessRules(), is(containsSameInstance(accessRule)));
    }

    @Test
    public void testGetAccessRules() {
        // Setup
        final User user = new User("userName", null);
        final AccessRule accessRule = new AccessRule(user, READ_WRITE, false);
        assertThat(user.getAccessRules(), is(emptySet()));

        // Test
        assertThat(user.addAccessRule(accessRule), is(true));
        assertThat(user.getAccessRules(), is(not(emptySet())));
        assertThat(user.getAccessRules(), is(containsSameInstance(accessRule)));
        assertThat(user.removeAccessRule(accessRule), is(true));
        assertThat(user.getAccessRules(), is(emptySet()));
    }

    @Test
    public void testRemoveAccessRule() {
        assertThat(User.class, is(protectedMethod("removeAccessRule")));

        // Setup
        final User user = new User("userName", null);
        final AccessRule accessRule = new AccessRule(user, READ_WRITE, false);
        assertThat(user.getAccessRules(), is(emptySet()));
        assertThat(user.addAccessRule(accessRule), is(true));
        assertThat(user.getAccessRules(), is(not(emptySet())));
        assertThat(user.getAccessRules(), is(containsSameInstance(accessRule)));

        // Test
        assertThat(user.removeAccessRule(accessRule), is(true));
        assertThat(user.removeAccessRule(accessRule), is(false));
        assertThat(user.getAccessRules(), is(emptySet()));
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
