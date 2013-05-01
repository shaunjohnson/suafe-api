package net.lmxm.suafe.api;

/**
 * Access rule for a user or user group.
 */
public final class AccessRule {
    /**
     * User to which the rule applies.
     */
    private final User user;

    /**
     * User group to which the rule applies.
     */
    private final UserGroup userGroup;

    /**
     * Indicates if the rule applies to all users that don't match the user/user group.
     */
    private boolean exclusion;

    /**
     * Level of access to apply to the user/user group.
     */
    private AccessLevel accessLevel;

    /**
     * Constructs an access rule for a user.
     *
     * @param user        User to which the rule applies
     * @param accessLevel Level of access to apply to the user
     * @param exclusion   Indicates that the rule applies to all users that is not this user
     */
    protected AccessRule(final User user, final AccessLevel accessLevel, boolean exclusion) {
        this.user = user;
        this.userGroup = null;
        this.accessLevel = accessLevel;
        this.exclusion = exclusion;
    }

    /**
     * Constructs an access rule for a user group.
     *
     * @param userGroup   User group to which the rule applies
     * @param accessLevel Level of access to apply to the user group
     * @param exclusion   Indicates that the rule applies to all users that are not in this user group
     */
    protected AccessRule(final UserGroup userGroup, final AccessLevel accessLevel, final boolean exclusion) {
        this.user = null;
        this.userGroup = userGroup;
        this.accessLevel = accessLevel;
        this.exclusion = exclusion;
    }

    /**
     * Gets the user associated with this access rule.
     *
     * @return User object or null if this rule is for a user group
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the user group associated with this access rule.
     *
     * @return User group object or null if this rule is for a user
     */
    public UserGroup getUserGroup() {
        return userGroup;
    }

    /**
     * Checks if this rule applies to this user/user group or not.
     *
     * @return Boolean value
     */
    public boolean isExclusion() {
        return exclusion;
    }

    /**
     * Changes the rule exclusion value.
     *
     * @param exclusion New exclusion value
     */
    protected void setExclusion(final boolean exclusion) {
        this.exclusion = exclusion;
    }

    /**
     * Gets the current access level for this rule.
     *
     * @return Current access level
     */
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    /**
     * Changes the current access level.
     *
     * @param accessLevel New access level
     */
    protected void setAccessLevel(final AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
