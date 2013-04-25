package net.lmxm.suafe.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User information.
 */
public final class User {
    /**
     * Name of the user
     */
    private String name;

    /**
     * Optional alias of the user.
     */
    private String alias;

    /**
     * User groups in which this user is a member.
     */
    private final Set<UserGroup> userGroups = new HashSet<UserGroup>();

    /**
     * Construct a user with the provided name and optional alias.
     *
     * @param name Name of the user
     * @param alias Optional alias of the user
     */
    protected User(final String name, final String alias) {
        this.name = name;
        this.alias = alias;
    }

    /**
     * Gets the current alias of the user.
     *
     * @return Alias of the user
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Gets the current name of the user.
     *
     * @return Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets set of user groups in which this user is a member.
     *
     * @return Set of user groups
     */
    public Set<UserGroup> getUserGroups() {
        return Collections.unmodifiableSet(userGroups);
    }

    /**
     * Adds a user group to this user.
     *
     * @param userGroup User group to add
     * @return true of user group is not already in the user, otherwise false
     */
    protected boolean addUserGroup(final UserGroup userGroup) {
        return userGroups.add(userGroup);
    }

    /**
     * Removes a user group from this user.
     *
     * @param userGroup User group to remove
     * @return true if the user group was removed, otherwise false
     */
    protected  boolean removeUserGroup(final UserGroup userGroup) {
        return userGroups.remove(userGroup);
    }

    /**
     * Changes alias for this user.
     *
     * @param alias New alias for the user
     */
    protected void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * Renames this user.
     *
     * @param name New name for the user
     */
    protected void setName(final String name) {
        this.name = name;
    }
}
