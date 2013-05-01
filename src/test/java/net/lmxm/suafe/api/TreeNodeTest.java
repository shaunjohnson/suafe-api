package net.lmxm.suafe.api;

import org.junit.Test;

import java.util.LinkedList;

import static net.lmxm.suafe.api.AccessLevel.READ_ONLY;
import static net.lmxm.suafe.api.AccessLevel.READ_WRITE;
import static net.lmxm.suafe.api.CustomMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for TreeNode.
 */
public final class TreeNodeTest {
    @Test
    public void testAddAccessRuleForUserShallow() {
        final TreeNode treeNode = new TreeNode();
        assertThat(treeNode.getAccessRules(), is(emptySet()));

        final User user = new User("userName", null);
        treeNode.addAccessRuleForUser("/", user, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));

        final AccessRule shallowAccessRule = treeNode.getAccessRules().iterator().next();
        assertThat(shallowAccessRule.getUser(), is(sameInstance(user)));
        assertThat(shallowAccessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(shallowAccessRule.isExclusion(), is(false));
    }

    @Test
    public void testAddAccessRuleForUserDeep() {
        final TreeNode treeNode = new TreeNode();
        assertThat(treeNode.getAccessRules(), is(emptySet()));

        final User user = new User("userName", null);
        treeNode.addAccessRuleForUser("foo/bar", user, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(emptySet()));

        final TreeNode fooTreeNode = treeNode.getChildren().iterator().next();
        assertThat(fooTreeNode.getAccessRules(), is(emptySet()));

        final TreeNode barTreeNode = fooTreeNode.getChildren().iterator().next();
        assertThat(barTreeNode.getAccessRules(), is(not(emptySet())));

        final AccessRule deepAccessRule = barTreeNode.getAccessRules().iterator().next();
        assertThat(deepAccessRule.getUser(), is(sameInstance(user)));
        assertThat(deepAccessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(deepAccessRule.isExclusion(), is(false));
    }

    @Test
    public void testAddAccessRuleForUserGroupShallow() {
        final TreeNode treeNode = new TreeNode();
        assertThat(treeNode.getAccessRules(), is(emptySet()));

        final UserGroup userGroup = new UserGroup("userGroupName");
        treeNode.addAccessRuleForUserGroup("/", userGroup, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(not(emptySet())));

        final AccessRule shallowAccessRule = treeNode.getAccessRules().iterator().next();
        assertThat(shallowAccessRule.getUserGroup(), is(sameInstance(userGroup)));
        assertThat(shallowAccessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(shallowAccessRule.isExclusion(), is(false));
    }

    @Test
    public void testAddAccessRuleForUserGroupDeep() {
        final TreeNode treeNode = new TreeNode();
        assertThat(treeNode.getAccessRules(), is(emptySet()));

        final UserGroup userGroup = new UserGroup("userGroupName");
        treeNode.addAccessRuleForUserGroup("foo/bar", userGroup, READ_WRITE, false);
        assertThat(treeNode.getAccessRules(), is(emptySet()));

        final TreeNode fooTreeNode = treeNode.getChildren().iterator().next();
        assertThat(fooTreeNode.getAccessRules(), is(emptySet()));

        final TreeNode barTreeNode = fooTreeNode.getChildren().iterator().next();
        assertThat(barTreeNode.getAccessRules(), is(not(emptySet())));

        final AccessRule deepAccessRule = barTreeNode.getAccessRules().iterator().next();
        assertThat(deepAccessRule.getUserGroup(), is(sameInstance(userGroup)));
        assertThat(deepAccessRule.getAccessLevel(), is(equalTo(READ_WRITE)));
        assertThat(deepAccessRule.isExclusion(), is(false));
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
        TreeNode.buildTree("/", rootNode);
        assertThat(rootNode.getChildren(), is(emptySet()));
    }

    @Test
    public void testGetChildrenBasicPath() {
        final TreeNode rootNode = new TreeNode();
        TreeNode.buildTree("foobar", rootNode);
        assertThat(rootNode.getChildren().size(), is(equalTo(1)));
        assertThat(rootNode.getChildren(), is(containsNodeWithName("foobar")));
    }

    @Test
    public void testGetChildrenMultipleChildren() {
        final TreeNode rootNode = new TreeNode();
        TreeNode.buildTree("foo", rootNode);
        TreeNode.buildTree("bar", rootNode);
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
