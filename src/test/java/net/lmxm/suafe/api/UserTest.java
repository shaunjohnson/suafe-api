package net.lmxm.suafe.api;

import org.junit.Test;

import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public final class UserTest {
    @Test
    public void testUser() {
        assertThat(User.class, is(protectedConstructor()));

        assertThat(new User("userName", null).getName(), is(equalTo("userName")));
        assertThat(new User("userName", null).getAlias(), is(nullValue()));

        assertThat(new User("userName", "userAlias").getName(), is(equalTo("userName")));
        assertThat(new User("userName", "userAlias").getAlias(), is(equalTo("userAlias")));

        assertThat(new User("userName", null).getUserGroupsMemberOf(), is(emptySet()));
        assertThat(new User("userName", null).getUserGroupsMemberOf(), is(immutableSet()));
    }

    @Test
    public void testAddUserGroupMemberOf() {
        assertThat(User.class, is(protectedMethod("addUserGroupMemberOf")));

        final UserGroup userGroup = new UserGroup("userGroupName");
        final User user = new User("userName", null);
        assertThat(user.getUserGroupsMemberOf(), is(emptySet()));

        userGroup.addUser(user);
        assertThat(userGroup.getUsers(), is(not(emptySet())));
        assertThat(userGroup.getUsers().iterator().next(), is(sameInstance(user)));
    }

    @Test
    public void testGetUsers() {
        final User user = new User("userName", null);
        assertThat(user.getUserGroupsMemberOf(), is(immutableSet()));
        assertThat(user.getUserGroupsMemberOf(), is(notNullValue()));
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
