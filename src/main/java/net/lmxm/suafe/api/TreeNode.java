package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.ObjectToStringBuilder;

import java.util.*;

import static net.lmxm.suafe.api.internal.Preconditions.*;

/**
 * Tree node representing a single node in a tree. Each node contains references to all access rules that apply to that
 * path.
 */
public final class TreeNode {
    /**
     * Name of a root node.
     */
    public static final String ROOT_NODE_NAME = "root";

    /**
     * Set of all access rules that apply to this node.
     */
    private final Set<AccessRule> accessRules = new HashSet<AccessRule>();

    /**
     * Map of all of this node's children.
     */
    private final Map<String, TreeNode> children = new HashMap<String, TreeNode>();

    /**
     * Parent node of this node; will be null for a root node.
     */
    private final TreeNode parent;

    /**
     * Thid node's name.
     */
    private final String name;

    /**
     * Constructs a new root node.
     */
    public TreeNode() {
        this(ROOT_NODE_NAME, null);
    }

    /**
     * Constructs a new non-root node
     *
     * @param name   Name of this node
     * @param parent Parent node of this node
     */
    protected TreeNode(final String name, final TreeNode parent) {
        this.name = checkArgumentNotBlank(name, "Tree node name");
        this.parent = parent;
    }

    /**
     * Creates an access rule to this tree node.
     *
     * @param user        User to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not the provided user
     * @return Newly created access rule
     * @throws EntityAlreadyExistsException When access rule already exists at this path for this user
     */
    protected AccessRule createAccessRuleForUser(final User user, final AccessLevel accessLevel, final boolean exclusion) {
        checkArgumentNotNull(user, "User");
        checkArgumentNotNull(accessLevel, "Access level");
        checkThatAccessRuleForUserDoesNotExist(this, "/", user);

        final AccessRule accessRule = new AccessRule(this, user, accessLevel, exclusion);
        accessRules.add(accessRule);
        user.addAccessRule(accessRule);
        return accessRule;
    }

    /**
     * Creates an access rule to this tree node.
     *
     * @param userGroup   User group to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not in the provided user group
     * @return Newly created access rule
     * @throws EntityAlreadyExistsException When access rule already exists at this path for this user group
     */
    protected AccessRule createAccessRuleForUserGroup(final UserGroup userGroup, final AccessLevel accessLevel, final boolean exclusion) {
        checkArgumentNotNull(userGroup, "User group");
        checkArgumentNotNull(accessLevel, "Access level");
        checkThatAccessRuleForUserGroupDoesNotExist(this, "/", userGroup);

        final AccessRule accessRule = new AccessRule(this, userGroup, accessLevel, exclusion);
        accessRules.add(accessRule);
        userGroup.addAccessRule(accessRule);
        return accessRule;
    }

    /**
     * Deletes an existing access rule for a user from this tree node.
     *
     * @param user User to which this rule applies
     * @return true if the access rule is deleted, otherwise false
     * @throws EntityDoesNotExistException When access rule does not exist at this path for this user
     */
    protected boolean deleteAccessRuleForUser(final User user) {
        checkArgumentNotNull(user, "User");
        final AccessRule accessRule = checkThatAccessRuleForUserExists(this, "/", user);

        return accessRules.remove(accessRule) && user.removeAccessRule(accessRule);
    }

    /**
     * Deletes an existing access rule for a user group from this tree node.
     *
     * @param userGroup User group to which this rule applies
     * @return true if the access rule is deleted, otherwise false
     * @throws EntityDoesNotExistException When access rule does not exist at this path for this user group
     */
    protected boolean deleteAccessRuleForUserGroup(final UserGroup userGroup) {
        checkArgumentNotNull(userGroup, "User group");
        final AccessRule accessRule = checkThatAccessRuleForUserGroupExists(this, "/", userGroup);

        return accessRules.remove(accessRule) && userGroup.removeAccessRule(accessRule);
    }

    /**
     * Deletes all existing access rules for a tree node.
     */
    protected void deleteAllAccessRules() {
        for (final User user : extractUsersFromAccessRules()) {
            deleteAccessRuleForUser(user);
        }

        for (final UserGroup userGroup : extractUserGroupsFromAccessRules()) {
            deleteAccessRuleForUserGroup(userGroup);
        }
    }

    /**
     * Extracts a set of all users that are referenced in access rules for this node.
     *
     * @return Set of user objects
     */
    protected Set<User> extractUsersFromAccessRules() {
        final Set<User> users = new HashSet<User>();

        for (final AccessRule accessRule : accessRules) {
            if (accessRule.getUser() != null) {
                users.add(accessRule.getUser());
            }
        }

        return users;
    }

    /**
     * Extracts a set of all user groups that are referenced in access rules for this node.
     *
     * @return Set of user group objects
     */
    protected Set<UserGroup> extractUserGroupsFromAccessRules() {
        final Set<UserGroup> userGroups = new HashSet<UserGroup>();

        for (final AccessRule accessRule : accessRules) {
            if (accessRule.getUserGroup() != null) {
                userGroups.add(accessRule.getUserGroup());
            }
        }

        return userGroups;
    }

    /**
     * Finds an access rule for the specified user.
     *
     * @param user User that is used to find a matching access rule
     * @return Access rule that applies to the specified user
     */
    public AccessRule findAccessRuleForUser(final User user) {
        checkArgumentNotNull(user, "User");

        AccessRule foundAccessRule = null;
        for (final AccessRule accessRule : accessRules) {
            if (accessRule.getUser() != null && accessRule.getUser().equals(user)) {
                foundAccessRule = accessRule;
                break;
            }
        }

        return foundAccessRule;
    }

    /**
     * Finds an access rule for the specified user group.
     *
     * @param userGroup group User group that is used to find a matching access rule
     * @return Access rule that applies to the specified user group
     */
    public AccessRule findAccessRuleForUserGroup(final UserGroup userGroup) {
        checkArgumentNotNull(userGroup, "User group");

        AccessRule foundAccessRule = null;
        for (final AccessRule accessRule : accessRules) {
            if (accessRule.getUserGroup() != null && accessRule.getUserGroup().equals(userGroup)) {
                foundAccessRule = accessRule;
                break;
            }
        }

        return foundAccessRule;
    }

    /**
     * Finds or creates the child node with the specified name. If a child with the name already exists it will be
     * returned, otherwise a new node with the provided name will be created and returned.
     *
     * @param name Name of the child node to find or create
     * @return Matching child node or new child node
     */
    private TreeNode findOrCreateChildByName(final String name) {
        if (children.containsKey(name)) {
            return children.get(name);
        }
        else {
            final TreeNode newChild = new TreeNode(name, this);
            children.put(name, newChild);
            return newChild;
        }
    }

    /**
     * Gets this node's access rules.
     *
     * @return Set of this node's access rules
     */
    public Set<AccessRule> getAccessRules() {
        return Collections.unmodifiableSet(accessRules);
    }

    /**
     * Gets this node's immediate children nodes.
     *
     * @return Set of tree node's that are immediate children of this node.
     */
    public Set<TreeNode> getChildren() {
        return Collections.unmodifiableSet(new HashSet<TreeNode>(children.values()));
    }

    /**
     * Gets this nodes name.
     *
     * @return This node's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets this node's parent.
     *
     * @return This node's parent or null if this is a root node
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * Clones all access rules in the source tree into the target tree.
     *
     * @param sourceTreeNode Source tree
     * @param targetTreeNode Target tree
     */
    protected static void cloneAllAccessRulesInTree(final TreeNode sourceTreeNode, final TreeNode targetTreeNode) {
        for (final AccessRule accessRule : sourceTreeNode.getAccessRules()) {
            if (accessRule.getUser() != null) {
                targetTreeNode.createAccessRuleForUser(accessRule.getUser(), accessRule.getAccessLevel(), accessRule.isExclusion());
            }
            else if (accessRule.getUserGroup() != null) {
                targetTreeNode.createAccessRuleForUserGroup(accessRule.getUserGroup(), accessRule.getAccessLevel(), accessRule.isExclusion());
            }
        }

        for (final TreeNode child : sourceTreeNode.getChildren()) {
            cloneAllAccessRulesInTree(child, targetTreeNode.findOrCreateChildByName(child.getName()));
        }
    }

    /**
     * Creates an access rule to the provided tree at the specified path.
     *
     * @param rootTreeNode Root tree node from which to search for the specified path
     * @param path         Path to which the new rule applies
     * @param user         User to which the rule applies
     * @param accessLevel  Level of access to apply
     * @param exclusion    Indicates if this rule applies to all users that are not the provided user
     * @return Newly created access rule
     * @throws EntityAlreadyExistsException When access rule already exists at this path for this user
     */
    protected static AccessRule createAccessRuleForUser(final TreeNode rootTreeNode, final String path, final User user, final AccessLevel accessLevel, final boolean exclusion) {
        checkThatAccessRuleForUserDoesNotExist(rootTreeNode, path, user);

        return buildTree(path, rootTreeNode).createAccessRuleForUser(user, accessLevel, exclusion);
    }

    /**
     * Deletes an existing access rule for a user from the provided tree at the specified path.
     *
     * @param rootTreeNode Root tree node from which to search for the specified path
     * @param path         Path to which the rule applies
     * @param user         User to which the rule applies
     * @return true if the access rule is deleted, otherwise false
     * @throws EntityDoesNotExistException When access rule does not exist at this path for this user
     */
    protected static boolean deleteAccessRuleForUser(final TreeNode rootTreeNode, final String path, final User user) {
        checkThatAccessRuleForUserExists(rootTreeNode, path, user);

        return buildTree(path, rootTreeNode).deleteAccessRuleForUser(user);
    }

    /**
     * Creates an access rule to the provided tree at the specified path.
     *
     * @param rootTreeNode Root tree node from which to search for the specified path
     * @param path         Path to which the new rule applies
     * @param userGroup    User group to which the rule applies
     * @param accessLevel  Level of access to apply
     * @param exclusion    Indicates if this rule applies to all users that are not in the provided user group
     * @return Newly created access rule
     * @throws EntityAlreadyExistsException When access rule already exists at this path for this user
     */
    protected static AccessRule createAccessRuleForUserGroup(final TreeNode rootTreeNode, final String path, final UserGroup userGroup, final AccessLevel accessLevel, final boolean exclusion) {
        checkThatAccessRuleForUserGroupDoesNotExist(rootTreeNode, path, userGroup);

        return buildTree(path, rootTreeNode).createAccessRuleForUserGroup(userGroup, accessLevel, exclusion);
    }

    /**
     * Deletes an existing access rule for a user group from the provided tree at the specified path.
     *
     * @param rootTreeNode Root tree node from which to search for the specified path
     * @param path         Path to which the rule applies
     * @param userGroup    User group to which the rule applies
     * @return true if the access rule is deleted, otherwise false
     * @throws EntityDoesNotExistException When access rule does not exist at this path for this user group
     */
    protected static boolean deleteAccessRuleForUserGroup(final TreeNode rootTreeNode, final String path, final UserGroup userGroup) {
        checkThatAccessRuleForUserGroupExists(rootTreeNode, path, userGroup);

        return buildTree(path, rootTreeNode).deleteAccessRuleForUserGroup(userGroup);
    }

    /**
     * Builds a tree of tree node objects using the provided path string.
     *
     * @param path     Path representation of the tree (e.g. one/two/three)
     * @param treeNode Root tree node
     * @return New/Existing tree containing all nodes in the provided path
     */
    protected static TreeNode buildTree(final String path, final TreeNode treeNode) {
        checkArgumentPathValid(path, "Path");

        return buildTreeRecursively(splitPath(path), treeNode);
    }

    /**
     * Builds a tree recursively using the provided node names and root tree node.
     *
     * @param nodeNames List of node names to create/find
     * @param treeNode  Root tree node
     * @return New/Existing tree containing all nodes in the list
     */
    private static TreeNode buildTreeRecursively(final LinkedList<String> nodeNames, final TreeNode treeNode) {
        if (nodeNames.size() == 0) {
            return treeNode;
        }
        else {
            final TreeNode childTreeNode = treeNode.findOrCreateChildByName(nodeNames.pop());
            return buildTreeRecursively(nodeNames, childTreeNode);
        }
    }

    /**
     * Deletes all access rules that appear within a tree.
     *
     * @param treeNode Tree that is traversed to delete access rules
     */
    public static void deleteAllAccessRulesInTree(final TreeNode treeNode) {
        treeNode.deleteAllAccessRules();

        for (final TreeNode child : treeNode.getChildren()) {
            deleteAllAccessRulesInTree(child);
        }
    }

    /**
     * Finds an access rule for the specified user at the provided path.
     *
     * @param treeNode Tree node from which to start search
     * @param path     Path of tree node to find
     * @param user     User that is used to find a matching access rule
     * @return Access that applies to the specified user
     */
    public static AccessRule findAccessRuleForUserAtPath(final TreeNode treeNode, final String path, final User user) {
        final TreeNode targetTreeNode = findByPath(treeNode, path);
        return targetTreeNode == null ? null : targetTreeNode.findAccessRuleForUser(user);
    }

    /**
     * Finds an access rule for the specified user group at the provided path.
     *
     * @param treeNode  Tree node from which to start search
     * @param path      Path of tree node to find
     * @param userGroup User group that is used to find a matching access rule
     * @return Access that applies to the specified user group
     */
    public static AccessRule findAccessRuleForUserGroupAtPath(final TreeNode treeNode, final String path, final UserGroup userGroup) {
        final TreeNode targetTreeNode = findByPath(treeNode, path);
        return targetTreeNode == null ? null : targetTreeNode.findAccessRuleForUserGroup(userGroup);
    }

    /**
     * Finds an ancestor node at the provided path.
     *
     * @param treeNode Tree node from which to start search
     * @param path     Path of ancestor node to find
     * @return Matching node or null if no nodes exist at the path
     */
    protected static TreeNode findByPath(final TreeNode treeNode, final String path) {
        checkArgumentPathValid(path, "Path");

        TreeNode matchingNode = treeNode;
        final LinkedList<String> nodeNames = splitPath(path);
        if (nodeNames.size() > 0) {
            for (final String name : nodeNames) {
                matchingNode = matchingNode.children.get(name);

                if (matchingNode == null) {
                    break;
                }
            }
        }

        return matchingNode;
    }

    /**
     * Splits a path string into parts as a linked list.
     *
     * @param path Path string to split
     * @return Linked list containing all path parts
     */
    protected static LinkedList<String> splitPath(final String path) {
        final LinkedList<String> linkedList = new LinkedList<String>();
        Collections.addAll(linkedList, path.split("/"));
        return linkedList;
    }

    @Override
    public String toString() {
        return new ObjectToStringBuilder(this.getClass()).append("name", name).append("isRoot", getParent() == null).build();
    }
}
