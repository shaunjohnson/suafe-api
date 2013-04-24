package net.lmxm.suafe.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User group consisting of one or more users and/or user groups.
 */
public final class UserGroup {
    /**
     * Name of the user group.
     */
    private String name;

    /**
     * Users that are part of this user group.
     */
    private final Set<User> users = new HashSet<User>();

    /**
     * Construct a user group with the provided name.
     *
     * @param name Name of the user group
     */
    protected UserGroup(final String name) {
        this.name = name;
    }

    /**
     * Gets the current name of the user group.
     *
     * @return Name of the user group
     */
    public String getName() {
        return name;
    }

    /**
     * Gets an immutable set of users that are part of this group.
     *
     * @return Set of users that are part of this group.
     */
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    /**
     * Adds a user to this user group.
     *
     * @param user User to add
     * @return true if user is not already in the user group, otherwise false
     */
    protected boolean addUser(final User user) {
        return users.add(user);
    }

    /**
     * Removes a user from this user group.
     *
     * @param user User to remove
     * @return true if user is removed from the user group, otherwise false
     */
    protected boolean removeUser(final User user) {
        return users.remove(user);
    }

    /**
     * Renames this user group.
     *
     * @param name New name for the user group
     */
    protected void setName(final String name) {
        this.name = name;
    }
}
