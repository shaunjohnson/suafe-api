package net.lmxm.suafe.api;

import java.util.*;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentNotBlank;
import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentNotNull;
import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentPathValid;

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
    private final Set<AccessRule> accessRules = new HashSet<>();

    /**
     * Map of all of this node's children.
     */
    private final Map<String, TreeNode> children = new HashMap<>();

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
     * Adds an access rule to this tree at the specified path.
     *
     * @param path        Path to which the new rule applies
     * @param user        User to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not the provided user
     * @return true if the access rule is added, otherwise false
     */
    protected boolean addAccessRuleForUser(final String path, final User user, final AccessLevel accessLevel, final boolean exclusion) {
        checkArgumentPathValid(path, "Path");
        checkArgumentNotNull(user, "User");
        checkArgumentNotNull(accessLevel, "Access level");

        final AccessRule accessRule = new AccessRule(user, accessLevel, exclusion);
        return buildTree(path, this).accessRules.add(accessRule) && user.addAccessRule(accessRule);
    }

    /**
     * Adds an access rule to this tree at the specified path.
     *
     * @param path        Path to which the new rule applies
     * @param userGroup   User group to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not in the provided user group
     * @return true if the access rule is added, otherwise false
     */
    protected boolean addAccessRuleForUserGroup(final String path, final UserGroup userGroup, final AccessLevel accessLevel, final boolean exclusion) {
        checkArgumentPathValid(path, "Path");
        checkArgumentNotNull(userGroup, "User group");
        checkArgumentNotNull(accessLevel, "Access level");

        final AccessRule accessRule = new AccessRule(userGroup, accessLevel, exclusion);
        return buildTree(path, this).accessRules.add(accessRule) && userGroup.addAccessRule(accessRule);
    }

    /**
     * Finds or creates the child node with the specified name. If a child with the name already exists it will be
     * returned, otherwise a new node with the provided name will be created and returned.
     *
     * @param name Name of the child node to find or create
     * @return Matching child node or new child node
     */
    private TreeNode findOrCreateChild(final String name) {
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
        return Collections.unmodifiableSet(new HashSet<>(children.values()));
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
     * Builds a tree of tree node objects using the provided path string.
     *
     * @param path     Path representation of the tree (e.g. one/two/three)
     * @param treeNode Root tree node
     * @return New/Existing tree containing all nodes in the provided path
     */
    protected static TreeNode buildTree(final String path, final TreeNode treeNode) {
        return buildTreeRecursively(splitPath(path), treeNode);
    }

    /**
     * Splits a path string into parts as a linked list.
     *
     * @param path Path string to split
     * @return Linked list containing all path parts
     */
    protected static LinkedList<String> splitPath(final String path) {
        final LinkedList<String> linkedList = new LinkedList<>();
        Collections.addAll(linkedList, path.split("/"));
        return linkedList;
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
            final TreeNode childTreeNode = treeNode.findOrCreateChild(nodeNames.pop());
            return buildTreeRecursively(nodeNames, childTreeNode);
        }
    }
}
