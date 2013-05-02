package net.lmxm.suafe.api;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentNotBlank;
import static net.lmxm.suafe.api.internal.Objects.equal;

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
        this.name = checkArgumentNotBlank(name, "Name");
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
     * Creates a new access rule for the user, with the access level and exclusion value.
     *
     * @param path        Path to which the new rule applies
     * @param user        User to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not the provided user
     * @return True if the access rule is added, otherwise false
     */
    protected boolean createAccessRuleForUser(final String path, final User user, final AccessLevel accessLevel, final boolean exclusion) {
        return TreeNode.createAccessRuleForUser(rootTreeNode, path, user, accessLevel, exclusion);
    }

    /**
     * Creates a new access rule for the user group, with the access level and exclusion value.
     *
     * @param path        Path to which the new rule applies
     * @param userGroup   User group to which this rule applies
     * @param accessLevel Level of access to apply
     * @param exclusion   Indicates if this rule applies to all users that are not in the provided user group
     * @return True if the access rule is added, otherwise false
     */
    protected boolean createAccessRuleForUserGroup(final String path, final UserGroup userGroup, final AccessLevel accessLevel, final boolean exclusion) {
        return TreeNode.createAccessRuleForUserGroup(rootTreeNode, path, userGroup, accessLevel, exclusion);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (Repository.class.isInstance(object)) {
            final Repository otherRepository = (Repository)object;

            return equal(name, otherRepository.name);
        }
        else {
            return false;
        }
    }
}
