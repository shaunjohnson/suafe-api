package net.lmxm.suafe.api;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentNotNull;
import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentPathValid;

public final class TreeNode {
    private Set<AccessRule> accessRules;

    private final Set<TreeNode> children = new HashSet<>();

    private final String name;

    public TreeNode() {
        this.name = "root";
    }

    public TreeNode(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean addAccessRuleForUser(final String path, final User user, final AccessLevel accessLevel, final boolean exclusion) {
        checkArgumentPathValid(path, "Path");
        checkArgumentNotNull(user, "User");
        checkArgumentNotNull(accessLevel, "Access level");

        return buildTree(path, this).addAccessRuleForUser(user, accessLevel, exclusion);
    }

    public boolean addAccessRuleForUserGroup(final String path, final UserGroup userGroup, final AccessLevel accessLevel, final boolean exclusion) {
        checkArgumentPathValid(path, "Path");
        checkArgumentNotNull(userGroup, "User");
        checkArgumentNotNull(accessLevel, "Access level");

        return buildTree(path, this).addAccessRuleForUserGroup(userGroup, accessLevel, exclusion);
    }

    private boolean addAccessRuleForUser(final User user, final AccessLevel accessLevel, boolean exclusion) {
        return accessRules.add(new AccessRule(user, accessLevel, exclusion));
    }

    private boolean addAccessRuleForUserGroup(final UserGroup userGroup, final AccessLevel accessLevel, boolean exclusion) {
        return accessRules.add(new AccessRule(userGroup, accessLevel, exclusion));
    }

    private TreeNode findChild(final String name) {
        TreeNode matchingChild = null;

        for (final TreeNode child : children) {
            if (child.getName().equals(name)) {
                matchingChild = child;
                break;
            }
        }

        return matchingChild;
    }

    private TreeNode findOrCreateChild(final String name) {
        final TreeNode matchingChild = findChild(name);
        if (matchingChild == null) {
            final TreeNode newChild = new TreeNode(name);
            children.add(newChild);
            return newChild;
        }
        else {
            return matchingChild;
        }
    }

    public static TreeNode buildTree(final String path, final TreeNode treeNode) {
        return buildTree(splitPath(path), treeNode);
    }

    private static LinkedList<String> splitPath(final String path) {
        final LinkedList<String> linkedList = new LinkedList<>();

        for (final String part : path.split("/")) {
            linkedList.push(part);
        }

        return linkedList;
    }

    private static TreeNode buildTree(final LinkedList<String> nodeNames, final TreeNode treeNode) {
        final TreeNode childTreeNode = treeNode.findOrCreateChild(nodeNames.pop());
        return nodeNames.size() == 0 ? childTreeNode : buildTree(nodeNames, childTreeNode);
    }
}
