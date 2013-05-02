package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.DocumentPreconditions;
import net.lmxm.suafe.api.internal.ObjectToStringBuilder;
import net.lmxm.suafe.api.internal.Objects;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentNotBlank;
import static net.lmxm.suafe.api.internal.Objects.equal;

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
     * Access rules that which apply to this user.
     */
    private final Set<AccessRule> accessRules = new HashSet<AccessRule>();

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
        this.name = checkArgumentNotBlank(name, "Name");
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
     * Adds an access rule to this user.
     *
     * @param accessRule Access rule to add
     * @return true if the access rule is not already in the set of rules, otherwise false
     */
    protected boolean addAccessRule(final AccessRule accessRule) {
        return accessRules.add(accessRule);
    }

    /**
     * Removes an access rule from this user.
     *
     * @param accessRule Access rule to remove
     * @return true if the access rule was removed, otherwise false
     */
    protected boolean removeAccessRule(final AccessRule accessRule) {
        return accessRules.remove(accessRule);
    }

    /**
     * Gets set of access rules to which apply to this user.
     *
     * @return Set of access rules
     */
    public Set<AccessRule> getAccessRules() {
        return Collections.unmodifiableSet(accessRules);
    }

    /**
     * Adds a user group to this user.
     *
     * @param userGroup User group to add
     * @return true if user group is not already in the user, otherwise false
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

    @Override
    public int hashCode() {
        return Objects.hashCode(name, alias);
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (User.class.isInstance(object)) {
            final User otherUser = (User)object;

            return equal(name, otherUser.name) && equal(alias, otherUser.alias);
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ObjectToStringBuilder(this.getClass()).append("name", name).append("alias",  alias).build();
    }
}
