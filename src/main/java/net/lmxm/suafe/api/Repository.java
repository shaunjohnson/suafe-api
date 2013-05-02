package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.ObjectToStringBuilder;

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

    @Override
    public String toString() {
        return new ObjectToStringBuilder(this.getClass()).append("name", name).build();
    }
}
