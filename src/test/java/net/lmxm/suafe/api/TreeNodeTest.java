package net.lmxm.suafe.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedList;

import static net.lmxm.suafe.api.TreeNode.*;
import static net.lmxm.suafe.api.AccessLevel.READ_WRITE;
import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for TreeNode.
 */
public final class TreeNodeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateAccessRuleForUser() {
        final TreeNode treeNode = new TreeNode();
        final User user = new User("userName", null);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(emptySet()));

        treeNode.createAccessRuleForUser(user, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));
        assertThat(user.getAccessRules(), is(not(emptySet())));

        final AccessRule accessRule = treeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUser(), is(sameInstance(user)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
        assertThat(user.getAccessRules(), is(containsSameInstance(accessRule)));
    }

    @Test
    public void testCreateAccessRuleForUserShallow() {
        final TreeNode treeNode = new TreeNode();
        final User user = new User("userName", null);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(emptySet()));

        createAccessRuleForUser(treeNode, "/", user, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));
        assertThat(user.getAccessRules(), is(not(emptySet())));

        final AccessRule accessRule = treeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUser(), is(sameInstance(user)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
        assertThat(user.getAccessRules(), is(containsSameInstance(accessRule)));
    }

    @Test
    public void testCreateAccessRuleForUserDeep() {
        final TreeNode treeNode = new TreeNode();
        final User user = new User("userName", null);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(emptySet()));

        createAccessRuleForUser(treeNode, "foo/bar", user, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(not(emptySet())));

        final TreeNode fooTreeNode = treeNode.getChildren().iterator().next();
        assertThat(fooTreeNode.getAccessRules(), is(emptySet()));

        final TreeNode barTreeNode = fooTreeNode.getChildren().iterator().next();
        assertThat(barTreeNode.getAccessRules(), is(not(emptySet())));

        final AccessRule accessRule = barTreeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUser(), is(sameInstance(user)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
        assertThat(user.getAccessRules(), is(containsSameInstance(accessRule)));
    }

    @Test
    public void testCreateAccessRuleForUserGroup() {
        final TreeNode treeNode = new TreeNode();
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(emptySet()));

        treeNode.createAccessRuleForUserGroup(userGroup, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));
        assertThat(userGroup.getAccessRules(), is(not(emptySet())));

        final AccessRule accessRule = treeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUserGroup(), is(sameInstance(userGroup)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
        assertThat(userGroup.getAccessRules(), is(containsSameInstance(accessRule)));
    }

    @Test
    public void testCreateAccessRuleForUserGroupShallow() {
        final TreeNode treeNode = new TreeNode();
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(emptySet()));

        createAccessRuleForUserGroup(treeNode, "/", userGroup, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));
        assertThat(userGroup.getAccessRules(), is(not(emptySet())));

        final AccessRule accessRule = treeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUserGroup(), is(sameInstance(userGroup)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
        assertThat(userGroup.getAccessRules(), is(containsSameInstance(accessRule)));
    }

    @Test
    public void testCreateAccessRuleForUserGroupDeep() {
        final TreeNode treeNode = new TreeNode();
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(emptySet()));

        createAccessRuleForUserGroup(treeNode, "foo/bar", userGroup, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(not(emptySet())));

        final TreeNode fooTreeNode = treeNode.getChildren().iterator().next();
        assertThat(fooTreeNode.getAccessRules(), is(emptySet()));

        final TreeNode barTreeNode = fooTreeNode.getChildren().iterator().next();
        assertThat(barTreeNode.getAccessRules(), is(not(emptySet())));

        final AccessRule accessRule = barTreeNode.getAccessRules().iterator().next();
        assertThat(accessRule.getUserGroup(), is(sameInstance(userGroup)));
        assertThat(accessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(accessRule.isExclusion(), is(false));
        assertThat(userGroup.getAccessRules(), is(containsSameInstance(accessRule)));
    }

    @Test
    public void testDeleteAccessRuleForUser() {
        final TreeNode treeNode = new TreeNode();
        final User user = new User("userName", null);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(emptySet()));

        treeNode.createAccessRuleForUser(user, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));
        assertThat(user.getAccessRules(), is(not(emptySet())));

        treeNode.deleteAccessRuleForUser(user);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(emptySet()));
    }

    @Test
    public void testDeleteAccessRuleForUserAtPath() {
        final TreeNode treeNode = new TreeNode();
        final User user = new User("userName", null);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(emptySet()));

        createAccessRuleForUser(treeNode, "foo/bar", user, READ_WRITE, false);
        final TreeNode foobarTreeNode = findByPath(treeNode, "foo/bar");
        assertThat(foobarTreeNode.getAccessRules(), is(not(emptySet())));
        assertThat(user.getAccessRules(), is(not(emptySet())));

        deleteAccessRuleForUser(treeNode, "foo/bar", user);
        assertThat(foobarTreeNode.getAccessRules(), is(emptySet()));
        assertThat(user.getAccessRules(), is(emptySet()));
    }

    @Test
    public void testDeleteAccessRuleForUserGroup() {
        final TreeNode treeNode = new TreeNode();
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(emptySet()));

        treeNode.createAccessRuleForUserGroup(userGroup, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));
        assertThat(userGroup.getAccessRules(), is(not(emptySet())));

        treeNode.deleteAccessRuleForUserGroup(userGroup);
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(emptySet()));
    }

    @Test
    public void testDeleteAccessRuleForUserGroupAtPath() {
        final TreeNode treeNode = new TreeNode();
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(treeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(emptySet()));

        createAccessRuleForUserGroup(treeNode, "foo/bar", userGroup, READ_WRITE, false);
        final TreeNode foobarTreeNode = findByPath(treeNode, "foo/bar");
        assertThat(foobarTreeNode.getAccessRules(), is(not(emptySet())));
        assertThat(userGroup.getAccessRules(), is(not(emptySet())));

        deleteAccessRuleForUserGroup(treeNode, "foo/bar", userGroup);
        assertThat(foobarTreeNode.getAccessRules(), is(emptySet()));
        assertThat(userGroup.getAccessRules(), is(emptySet()));
    }

    @Test
    public void testFindAccessRuleForUser() {
        // Setup
        final TreeNode rootNode = new TreeNode();
        final User user = new User("userName", null);
        final User otherUser = new User("otherUserName", null);
        assertThat(rootNode.findAccessRuleForUser(user), is(nullValue()));
        assertThat(rootNode.findAccessRuleForUser(otherUser), is(nullValue()));

        // Test
        createAccessRuleForUser(rootNode, "/", user, READ_WRITE, false);
        assertThat(rootNode.findAccessRuleForUser(user), is(notNullValue()));
        assertThat(rootNode.findAccessRuleForUser(user).getUser(), is(sameInstance(user)));
        assertThat(rootNode.findAccessRuleForUser(otherUser), is(nullValue()));

        thrown.expect(IllegalArgumentException.class);
        assertThat(rootNode.findAccessRuleForUser(null), is(notNullValue()));
    }

    @Test
    public void testFindAccessRuleForUserAtPath() {
        // Setup
        final TreeNode rootNode = new TreeNode();
        buildTree("foo/bar", rootNode);
        final User user = new User("userName", null);
        assertThat(findAccessRuleForUserAtPath(rootNode, "foo/bar", user), is(nullValue()));

        // Test
        createAccessRuleForUser(rootNode, "foo/bar", user, READ_WRITE, false);
        assertThat(findAccessRuleForUserAtPath(rootNode, "foo/bar", user), is(notNullValue()));
        assertThat(findAccessRuleForUserAtPath(rootNode, "foo/bar", user).getUser(), is(sameInstance(user)));

        thrown.expect(IllegalArgumentException.class);
        assertThat(findAccessRuleForUserAtPath(rootNode, null, null), is(notNullValue()));
    }

    @Test
    public void testFindAccessRuleForUserGroup() {
        // Setup
        final TreeNode rootNode = new TreeNode();
        final UserGroup userGroup = new UserGroup("userGroupName");
        final UserGroup otherUserGroup = new UserGroup("otherUserGroupName");
        assertThat(rootNode.findAccessRuleForUserGroup(userGroup), is(nullValue()));
        assertThat(rootNode.findAccessRuleForUserGroup(otherUserGroup), is(nullValue()));

        // Test
        createAccessRuleForUserGroup(rootNode, "/", userGroup, READ_WRITE, false);
        assertThat(rootNode.findAccessRuleForUserGroup(userGroup), is(notNullValue()));
        assertThat(rootNode.findAccessRuleForUserGroup(userGroup).getUserGroup(), is(sameInstance(userGroup)));
        assertThat(rootNode.findAccessRuleForUserGroup(otherUserGroup), is(nullValue()));

        thrown.expect(IllegalArgumentException.class);
        assertThat(rootNode.findAccessRuleForUser(null), is(notNullValue()));
    }

    @Test
    public void testFindAccessRuleForUserGroupAtPath() {
        // Setup
        final TreeNode rootNode = new TreeNode();
        buildTree("foo/bar", rootNode);
        final UserGroup userGroup = new UserGroup("userGroupName");
        assertThat(findAccessRuleForUserGroupAtPath(rootNode, "foo/bar", userGroup), is(nullValue()));

        // Test
        createAccessRuleForUserGroup(rootNode, "foo/bar", userGroup, READ_WRITE, false);
        assertThat(findAccessRuleForUserGroupAtPath(rootNode, "foo/bar", userGroup), is(notNullValue()));
        assertThat(findAccessRuleForUserGroupAtPath(rootNode, "foo/bar", userGroup).getUserGroup(), is(sameInstance(userGroup)));

        thrown.expect(IllegalArgumentException.class);
        assertThat(findAccessRuleForUserGroupAtPath(rootNode, null, null), is(notNullValue()));
    }

    @Test
    public void testFindByPath() {
        final TreeNode rootNode = new TreeNode();
        assertThat(findByPath(rootNode, "/"), is(notNullValue()));
        assertThat(findByPath(rootNode, "/"), is(sameInstance(rootNode)));

        buildTree("foo/bar", rootNode);
        assertThat(findByPath(rootNode, "foo"), is(notNullValue()));
        assertThat(findByPath(rootNode, "foo").getName(), is(equalTo("foo")));
        assertThat(findByPath(rootNode, "foo/bar"), is(notNullValue()));
        assertThat(findByPath(rootNode, "foo/bar").getName(), is(equalTo("bar")));

        assertThat(findByPath(rootNode, "does-not-exist"), is(nullValue()));
    }

    @Test
    public void testGetAccessRulesNewNode() {
        assertThat(new TreeNode().getChildren(), is(notNullValue()));
        assertThat(new TreeNode().getChildren(), is(emptySet()));
        assertThat(new TreeNode().getAccessRules(), is(immutableSet()));
    }

    @Test
    public void testGetChildrenNewNode() {
        assertThat(new TreeNode().getChildren(), is(notNullValue()));
        assertThat(new TreeNode().getChildren(), is(emptySet()));
        assertThat(new TreeNode().getChildren(), is(immutableSet()));
    }

    @Test
    public void testGetChildrenRootPath() {
        final TreeNode rootNode = new TreeNode();
        buildTree("/", rootNode);
        assertThat(rootNode.getChildren(), is(emptySet()));
    }

    @Test
    public void testGetChildrenBasicPath() {
        final TreeNode rootNode = new TreeNode();
        buildTree("foobar", rootNode);
        assertThat(rootNode.getChildren().size(), is(equalTo(1)));
        assertThat(rootNode.getChildren(), is(containsNodeWithName("foobar")));
    }

    @Test
    public void testGetChildrenMultipleChildren() {
        final TreeNode rootNode = new TreeNode();
        buildTree("foo", rootNode);
        buildTree("bar", rootNode);
        assertThat(rootNode.getChildren().size(), is(equalTo(2)));

        assertThat(rootNode.getChildren(), is(containsNodeWithName("foo")));
        assertThat(rootNode.getChildren(), is(containsNodeWithName("bar")));
    }

    @Test
    public void testGetName() {
        assertThat(new TreeNode().getName(), is(equalTo(TreeNode.ROOT_NODE_NAME)));
        assertThat(new TreeNode("treeNodeName", new TreeNode()).getName(), is(equalTo("treeNodeName")));
    }

    @Test
    public void testGetParent() {
        assertThat(new TreeNode().getParent(), is(nullValue()));

        final TreeNode rootNode = new TreeNode();
        assertThat(TreeNode.buildTree("foobar", rootNode).getParent(), is(notNullValue()));
        assertThat(TreeNode.buildTree("foobar", rootNode).getParent(), is(sameInstance(rootNode)));
    }

    @Test
    public void testSplitPath() {
        assertThat(TreeNode.splitPath("/"), is(notNullValue()));
        assertThat(TreeNode.splitPath("/").size(), is(equalTo(0)));

        assertThat(TreeNode.splitPath("foobar"), is(notNullValue()));
        assertThat(TreeNode.splitPath("foobar").size(), is(equalTo(1)));
        assertThat(TreeNode.splitPath("foobar").pop(), is(equalTo("foobar")));

        final LinkedList<String> parts = TreeNode.splitPath("foo/bar");
        assertThat(parts, is(notNullValue()));
        assertThat(parts.size(), is(equalTo(2)));
        assertThat(parts.pop(), is(equalTo("foo")));
        assertThat(parts.pop(), is(equalTo("bar")));
    }
}
