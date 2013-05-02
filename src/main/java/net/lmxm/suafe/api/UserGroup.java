package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.ObjectToStringBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.lmxm.suafe.api.internal.Objects.equal;
import static net.lmxm.suafe.api.internal.Preconditions.checkArgumentNotBlank;

/**
 * User group consisting of one or more users and/or user groups.
 */
public final class UserGroup {
    /**
     * Name of the user group.
     */
    private String name;

    /**
     * Access rules that which apply to this user group.
     */
    private final Set<AccessRule> accessRules = new HashSet<AccessRule>();

    /**
     * User groups in which this user is a member.
     */
    private final Set<UserGroup> userGroups = new HashSet<UserGroup>();

    /**
     * User groups that are members of this user group.
     */
    private final Set<UserGroup> userGroupMembers = new HashSet<UserGroup>();

    /**
     * Users that are members of this user group.
     */
    private final Set<User> userMembers = new HashSet<User>();

    /**
     * Construct a user group with the provided name.
     *
     * @param name Name of the user group
     */
    protected UserGroup(final String name) {
        this.name = checkArgumentNotBlank(name, "Name");
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
     * Adds an access rule to this user group.
     *
     * @param accessRule Access rule to add
     * @return true if the access rule is not already in the set of rules, otherwise false
     */
    protected boolean addAccessRule(final AccessRule accessRule) {
        return accessRules.add(accessRule);
    }

    /**
     * Removes an access rule from this user group.
     *
     * @param accessRule Access rule to remove
     * @return true if the access rule was removed, otherwise false
     */
    protected boolean removeAccessRule(final AccessRule accessRule) {
        return accessRules.remove(accessRule);
    }

    /**
     * Gets set of access rules to which apply to this user group.
     *
     * @return Set of access rules
     */
    public Set<AccessRule> getAccessRules() {
        return Collections.unmodifiableSet(accessRules);
    }

    /**
     * Gets set of user groups in which this group is a member.
     *
     * @return Set of user groups
     */
    public Set<UserGroup> getUserGroups() {
        return Collections.unmodifiableSet(userGroups);
    }

    /**
     * Adds a user group to this group.
     *
     * @param userGroup User group to add
     * @return true of user group is not already in the user, otherwise false
     */
    protected boolean addUserGroup(final UserGroup userGroup) {
        return userGroups.add(userGroup);
    }

    /**
     * Removes a user group from this group.
     *
     * @param userGroup User group to remove
     * @return true if the user group was removed, otherwise false
     */
    protected boolean removeUserGroup(final UserGroup userGroup) {
        return userGroups.remove(userGroup);
    }

    /**
     * Gets an immutable set of user groups that are members of this group.
     *
     * @return Set of user groups that are members of this group
     */
    public Set<UserGroup> getUserGroupMembers() {
        return Collections.unmodifiableSet(userGroupMembers);
    }

    /**
     * Gets an immutable set of users that are members of this group.
     *
     * @return Set of users that are members of this group
     */
    public Set<User> getUserMembers() {
        return Collections.unmodifiableSet(userMembers);
    }

    /**
     * Adds a user group member to this user group.
     *
     * @param userGroup User group member to add
     * @return true if user group is not already in the user group, otherwise false
     */
    protected boolean addUserGroupMember(final UserGroup userGroup) {
        return userGroupMembers.add(userGroup);
    }

    /**
     * Adds a user member to this user group.
     *
     * @param user User member to add
     * @return true if user is not already in the user group, otherwise false
     */
    protected boolean addUserMember(final User user) {
        return userMembers.add(user);
    }

    /**
     * Removes a user group from this user group.
     *
     * @param userGroup User group to remove
     * @return true if user group is removed from the user group, otherwise false
     */
    protected boolean removeUserGroupMember(final UserGroup userGroup) {
        return userGroupMembers.remove(userGroup);
    }

    /**
     * Removes a user from this user group.
     *
     * @param user User to remove
     * @return true if user is removed from the user group, otherwise false
     */
    protected boolean removeUserMember(final User user) {
        return userMembers.remove(user);
    }

    /**
     * Renames this user group.
     *
     * @param name New name for the user group
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

        if (UserGroup.class.isInstance(object)) {
            final UserGroup otherUserGroup = (UserGroup) object;

            return equal(name, otherUserGroup.name);
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
