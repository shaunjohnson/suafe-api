package net.lmxm.suafe.api;

/**
 * Represents a Subversion repository.
 */
public final class Repository {
    /**
     * The current name of this repository.
     */
    private String name;

    /**
     * Tree of paths and access rules within this repository.
     */
    private final TreeNode rootTreeNode = new TreeNode();

    /**
     * Constructs a new repository object with the provided name.
     *
     * @param name Name of the new repository
     */
    protected Repository(final String name) {
        this.name = name;
    }

    /**
     * Gets the current name of this repository.
     *
     * @return Current name of this repository
     */
    public String getName() {
        return name;
    }

    /**
     * Gets this repository's root tree node.
     *
     * @return Repository root tree node
     */
    public TreeNode getRootTreeNode() {
        return rootTreeNode;
    }

    /**
     * Renames this repository.
     *
     * @param name New name for the repository
     */
    protected void setName(final String name) {
        this.name = name;
    }

    /**
     * Adds a new access rule for the user, with the access level and exclusion value.
     *
     * @param path        Path to which the new rule applies
     * @param user        User to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not the provided user
     * @return True if the access rule is added, otherwise false
     */
    protected boolean addAccessRuleForUser(final String path, final User user, final AccessLevel accessLevel, final boolean exclusion) {
        return rootTreeNode.addAccessRuleForUser(path, user, accessLevel, exclusion);
    }

    /**
     * Adds a new access rule for the user group, with the access level and exclusion value.
     *
     * @param path        Path to which the new rule applies
     * @param userGroup   User group to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not in the provided user group
     * @return True if the access rule is added, otherwise false
     */
    protected boolean addAccessRuleForUserGroup(final String path, final UserGroup userGroup, final AccessLevel accessLevel, final boolean exclusion) {
        return rootTreeNode.addAccessRuleForUserGroup(path, userGroup, accessLevel, exclusion);
    }
}
