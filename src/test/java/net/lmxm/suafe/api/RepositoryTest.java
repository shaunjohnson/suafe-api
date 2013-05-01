package net.lmxm.suafe.api;

import org.junit.Test;

import static net.lmxm.suafe.api.AccessLevel.READ_ONLY;
import static net.lmxm.suafe.api.AccessLevel.READ_WRITE;
import static net.lmxm.suafe.api.CustomMatchers.emptySet;
import static net.lmxm.suafe.api.CustomMatchers.protectedConstructor;
import static net.lmxm.suafe.api.CustomMatchers.protectedMethod;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for repository objects.
 */
public final class RepositoryTest {
    @Test
    public void testRepository() {
        assertThat(Repository.class, is(protectedConstructor()));

        assertThat(new Repository("repositoryName").getName(), is(equalTo("repositoryName")));
    }

    @Test
    public void testSetName() {
        assertThat(Repository.class, is(protectedMethod("setName")));

        final Repository repository = new Repository("repositoryName");
        assertThat(repository.getName(), is(equalTo("repositoryName")));

        repository.setName("newRepositoryName");
        assertThat(repository.getName(), is(equalTo("newRepositoryName")));
    }

    @Test
    public void testAddAccessRuleForUser() {
        assertThat(Repository.class, is(protectedMethod("addAccessRuleForUser")));

        final Repository repository = new Repository("repositoryName");
        final TreeNode rootTreeNode = repository.getRootTreeNode();
        assertThat(rootTreeNode.getAccessRules(), is(emptySet()));

        final User user  = new User("userName", null);
        repository.addAccessRuleForUser("/", user, READ_WRITE, false);
        assertThat(rootTreeNode.getAccessRules().size(), is(equalTo(1)));

        final AccessRule accessRule = rootTreeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUser(), is(sameInstance(user)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
    }

    @Test
    public void testAddAccessRuleForUserGroup() {
        assertThat(Repository.class, is(protectedMethod("addAccessRuleForUserGroup")));

        final Repository repository = new Repository("repositoryName");
        final TreeNode rootTreeNode = repository.getRootTreeNode();
        assertThat(rootTreeNode.getAccessRules(), is(emptySet()));

        final UserGroup userGroup  = new UserGroup("userGroupName");
        repository.addAccessRuleForUserGroup("/", userGroup, READ_WRITE, false);
        assertThat(rootTreeNode.getAccessRules().size(), is(equalTo(1)));

        final AccessRule accessRule = rootTreeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUserGroup(), is(sameInstance(userGroup)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
    }

    @Test
    public void getRootTreeNode() {
        final Repository repository = new Repository("repositoryName");
        final TreeNode rootTreeNode = repository.getRootTreeNode();

        assertThat(rootTreeNode, is(notNullValue()));
        assertThat(rootTreeNode.getChildren(), is(notNullValue()));
        assertThat(rootTreeNode.getChildren(), is(emptySet()));
        assertThat(rootTreeNode.getAccessRules(), is(notNullValue()));
        assertThat(rootTreeNode.getAccessRules(), is(emptySet()));

        final UserGroup userGroup  = new UserGroup("userGroupName");
        repository.addAccessRuleForUserGroup("/", userGroup, READ_WRITE, false);
        assertThat(rootTreeNode.getChildren(), is(notNullValue()));
        assertThat(rootTreeNode.getChildren(), is(emptySet()));
        assertThat(rootTreeNode.getAccessRules(), is(notNullValue()));
        assertThat(rootTreeNode.getAccessRules().size(), is(equalTo(1)));

        final AccessRule accessRule = rootTreeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUserGroup(), is(sameInstance(userGroup)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
    }
}
