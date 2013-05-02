package net.lmxm.suafe.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.*;

public final class Document {
    /**
     * Set of all repositories.
     */
    private Set<Repository> repositories = new HashSet<Repository>();

    /**
     * Tree of all server-level (applicable to all repositories) access rules.
     */
    private TreeNode rootTreeNode = new TreeNode();

    /**
     * Set of all users.
     */
    private Set<User> users = new HashSet<User>();

    /**
     * Set of all user groups.
     */
    private Set<UserGroup> userGroups = new HashSet<UserGroup>();

    /**
     * Adds a user to a user group.
     *
     * @param userName            Name of user to add to user group
     * @param targetUserGroupName Name of user group to which the user is added
     * @return true if the user was not already in the user group
     * @throws EntityDoesNotExistException When user or user group with name does not exist
     */
    public boolean addUserToUserGroup(final String userName, final String targetUserGroupName) {
        final User user = checkThatUserWithNameExists(this, userName);
        final UserGroup targetUserGroup = checkThatUserGroupWithNameExists(this, targetUserGroupName);

        return targetUserGroup.addUserMember(user) && user.addUserGroup(targetUserGroup);
    }

    /**
     * Adds a user group to a user group.
     *
     * @param userGroupName       Name of user group to add to user group
     * @param targetUserGroupName Name of user group to which the user group is added
     * @return true if the user group was not already in the user group
     * @throws EntityDoesNotExistException When user group with name does not exist
     */
    public boolean addUserGroupToUserGroup(final String userGroupName, final String targetUserGroupName) {
        final UserGroup userGroup = checkThatUserGroupWithNameExists(this, userGroupName);
        final UserGroup targetUserGroup = checkThatUserGroupWithNameExists(this, targetUserGroupName);

        return targetUserGroup.addUserGroupMember(userGroup) && userGroup.addUserGroup(targetUserGroup);
    }

    /**
     * Clones an existing repository with the provided name.
     *
     * @param repositoryName      Name of the repository to clone
     * @param cloneRepositoryName New name of the repository clone
     * @return Clone repository
     */
    public Repository cloneRepository(final String repositoryName, final String cloneRepositoryName) {
        final Repository existingRepository = checkThatRepositoryExists(this, repositoryName);
        final Repository cloneRepository = createRepository(cloneRepositoryName);

        return cloneRepository;
    }

    /**
     * Clones an existing user with the provided name.
     *
     * @param userName       Name of the user to clone
     * @param cloneUserName  New name of the user clone
     * @param cloneUserAlias New alias of the user clone
     * @return Clone user
     */
    public User cloneUser(final String userName, final String cloneUserName, final String cloneUserAlias) {
        final User existingUser = checkThatUserWithNameExists(this, userName);
        final User cloneUser = createUser(cloneUserName, cloneUserAlias);

        for (final UserGroup userGroup : existingUser.getUserGroups()) {
            addUserToUserGroup(cloneUserName, userGroup.getName());
        }

        for (final AccessRule  accessRule : existingUser.getAccessRules()) {
            accessRule.getTreeNode().createAccessRuleForUser(cloneUser, accessRule.getAccessLevel(), accessRule.isExclusion());
        }

        return cloneUser;
    }

    /**
     * Clones an existing user group with the provided name.
     *
     * @param userGroupName      Name of the user group to clone
     * @param cloneUserGroupName New name of the user group clone
     * @return Clone user
     */
    public UserGroup cloneUserGroup(final String userGroupName, final String cloneUserGroupName) {
        final UserGroup existingUserGroup = checkThatUserGroupWithNameExists(this, userGroupName);
        final UserGroup cloneUserGroup = createUserGroup(cloneUserGroupName);

        for (final User userMember : existingUserGroup.getUserMembers()) {
            addUserToUserGroup(userMember.getName(), cloneUserGroupName);
        }

        for (final UserGroup userGroupMember : existingUserGroup.getUserGroupMembers()) {
            addUserGroupToUserGroup(userGroupMember.getName(), cloneUserGroupName);
        }

        return cloneUserGroup;
    }

    /**
     * Creates a new access rule for the specified repository and user, with the provided access level and exclusion value.
     *
     * @param repositoryName Name of the repository to which the access rule applies. If null the access rule applies to all repositories
     * @param path           Path to which the new rule applies
     * @param userName       Name of user to which this rule applies
     * @param accessLevel    Level of access to apply
     * @param exclusion      Indicates if this rule applies to all users that are not in the provided user group
     * @return True if the access rule is added, otherwise false
     */
    public boolean createAccessRuleForUser(final String repositoryName, final String path, final String userName, final AccessLevel accessLevel, final boolean exclusion) {
        final User user = checkThatUserWithNameExists(this, userName);
        final TreeNode treeNode = getApplicableRootTreeNode(repositoryName);

        return TreeNode.createAccessRuleForUser(treeNode, path, user, accessLevel, exclusion);
    }

    /**
     * Creates a new access rule for the specified repository and user, with the provided access level and exclusion value.
     *
     * @param repositoryName Name of the repository to which the access rule applies. If null the access rule applies to all repositories
     * @param path           Path to which the new rule applies
     * @param userGroupName  Name of user group to which this rule applies
     * @param accessLevel    Level of access to apply
     * @param exclusion      Indicates if this rule applies to all users that are not in the provided user group
     * @return True if the access rule is added, otherwise false
     */
    public boolean createAccessRuleForUserGroup(final String repositoryName, final String path, final String userGroupName, final AccessLevel accessLevel, final boolean exclusion) {
        final UserGroup userGroup = checkThatUserGroupWithNameExists(this, userGroupName);
        final TreeNode treeNode = getApplicableRootTreeNode(repositoryName);

        return TreeNode.createAccessRuleForUserGroup(treeNode, path, userGroup, accessLevel, exclusion);
    }

    /**
     * Creates a new repository with the provided name.
     *
     * @param repositoryName Name of the repository to create
     * @return Newly created repository
     * @throws EntityAlreadyExistsException When repository with the name already exists
     */
    public Repository createRepository(final String repositoryName) {
        checkThatRepositoryDoesNotExist(this, repositoryName);

        final Repository repository = new Repository(repositoryName);
        repositories.add(repository);

        return repository;
    }

    /**
     * Creates a new user with the provided name and optional alias.
     *
     * @param userName  Name of the user to create
     * @param userAlias Optional alias of the user
     * @return Newly created user
     * @throws EntityAlreadyExistsException When user with the name or alias already exists
     */
    public User createUser(final String userName, final String userAlias) {
        checkThatUserWithNameDoesNotExist(this, userName);

        if (isNotBlank(userAlias)) {
            checkThatUserWithAliasDoesNotExist(this, userAlias);
        }

        final User user = new User(userName, userAlias);
        users.add(user);

        return user;
    }

    /**
     * Creates a new user group with the provided name.
     *
     * @param userGroupName Name of the user group to create
     * @return Newly created user group
     * @throws EntityAlreadyExistsException When user group with the name already exists
     */
    public UserGroup createUserGroup(final String userGroupName) {
        checkThatUserGroupWithNameDoesNotExist(this, userGroupName);

        final UserGroup userGroup = new UserGroup(userGroupName);
        userGroups.add(userGroup);

        return userGroup;
    }

    /**
     * Deletes an existing access rule for the specified repository and user.
     *
     * @param repositoryName Name of the repository to which the access rule applies. If null the access rule applies to all repositories
     * @param path           Path to which the rule applies
     * @param userName       Name of user to which the rule applies
     * @return True if the access rule is deleted, otherwise false
     */
    public boolean deleteAccessRuleForUser(final String repositoryName, final String path, final String userName) {
        final User user = checkThatUserWithNameExists(this, userName);
        final TreeNode treeNode = getApplicableRootTreeNode(repositoryName);

        return TreeNode.deleteAccessRuleForUser(treeNode, path, user);
    }

    /**
     * Deletes an existing access rule for the specified repository and user group.
     *
     * @param repositoryName Name of the repository to which the access rule applies. If null the access rule applies to all repositories
     * @param path           Path to which the rule applies
     * @param userGroupName  Name of user group to which this rule applies
     * @return True if the access rule is deleted, otherwise false
     */
    public boolean deleteAccessRuleForUserGroup(final String repositoryName, final String path, final String userGroupName) {
        final UserGroup userGroup = checkThatUserGroupWithNameExists(this, userGroupName);
        final TreeNode treeNode = getApplicableRootTreeNode(repositoryName);

        return TreeNode.deleteAccessRuleForUserGroup(treeNode, path, userGroup);
    }

    /**
     * Deletes an existing repository with the provided name.
     *
     * @param repositoryName Name of the repository to delete
     * @throws EntityDoesNotExistException When repository to delete does not exist
     */
    public void deleteRepository(final String repositoryName) {
        final Repository targetRepository = checkThatRepositoryExists(this, repositoryName);

        repositories.remove(targetRepository);
    }

    /**
     * Deletes an existing user with the provided name.
     *
     * @param targetUserName Name of the user to delete
     * @throws EntityDoesNotExistException When user to delete does not exist
     */
    public void deleteUser(final String targetUserName) {
        final User targetUser = checkThatUserWithNameExists(this, targetUserName);

        for (final UserGroup userGroup : targetUser.getUserGroups()) {
            removeUserFromUserGroup(targetUserName, userGroup.getName());
        }

        users.remove(targetUser);
    }

    /**
     * Deletes an existing user group with the provided name.
     *
     * @param targetUserGroupName Name of the user group to delete
     * @throws EntityDoesNotExistException When user group to delete does not exist
     */
    public void deleteUserGroup(final String targetUserGroupName) {
        final UserGroup targetUserGroup = checkThatUserGroupWithNameExists(this, targetUserGroupName);

        for (final User memberUser : targetUserGroup.getUserMembers()) {
            removeUserFromUserGroup(memberUser.getName(), targetUserGroupName);
        }

        for (final UserGroup memberUserGroup : targetUserGroup.getUserGroupMembers()) {
            removeUserGroupFromUserGroup(memberUserGroup.getName(), targetUserGroupName);
        }

        for (final UserGroup userGroup : targetUserGroup.getUserGroups()) {
            removeUserGroupFromUserGroup(targetUserGroupName, userGroup.getName());
        }

        userGroups.remove(targetUserGroup);
    }

    /**
     * Finds an access rule for the specified user at the provided path.
     *
     * @param repositoryName Name of the repository to which the access rule applies
     * @param path           Path of tree node to find
     * @param userName       Name of user that is used to find a matching access rule
     * @return Access that applies to the specified user
     */
    public AccessRule findAccessRuleForUserAtPath(final String repositoryName, final String path, final String userName) {
        final User user = checkThatUserWithNameExists(this, userName);
        final TreeNode treeNode = getApplicableRootTreeNode(repositoryName);

        return TreeNode.findAccessRuleForUserAtPath(treeNode, path, user);
    }

    /**
     * Finds an access rule for the specified user group at the provided path.
     *
     * @param repositoryName Name of the repository to which the access rule applies
     * @param path           Path of tree node to find
     * @param userGroupName       Name of user group that is used to find a matching access rule
     * @return Access that applies to the specified user group
     */
    public AccessRule findAccessRuleForUserGroupAtPath(final String repositoryName, final String path, final String userGroupName) {
        final UserGroup userGroup = checkThatUserGroupWithNameExists(this, userGroupName);
        final TreeNode treeNode = getApplicableRootTreeNode(repositoryName);

        return TreeNode.findAccessRuleForUserGroupAtPath(treeNode, path, userGroup);
    }

    /**
     * Finds an existing repository by name.
     *
     * @param repositoryName Name of repository to find
     * @return Matching repository or null if not found
     */
    public Repository findRepositoryByName(final String repositoryName) {
        checkArgumentNotBlank(repositoryName, "Repository name");

        for (final Repository repository : repositories) {
            if (repository.getName().equals(repositoryName)) {
                return repository;
            }
        }

        return null;
    }

    /**
     * Finds an existing user by name.
     *
     * @param userName Name of user to find
     * @return Matching user or null if not found
     */
    public User findUserByName(final String userName) {
        checkArgumentNotBlank(userName, "User name");

        for (final User user : users) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Finds an existing user by alias.
     *
     * @param userAlias Alias of user to find
     * @return Matching user or null if not found
     */
    public User findUserByAlias(final String userAlias) {
        checkArgumentNotBlank(userAlias, "User alias");

        for (final User user : users) {
            if (user.getAlias() != null && user.getAlias().equals(userAlias)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Finds an existing user group by name.
     *
     * @param userGroupName Name of user group to find
     * @return Matching user group or null if not found
     */
    public UserGroup findUserGroupByName(final String userGroupName) {
        checkArgumentNotBlank(userGroupName, "User group name");

        for (final UserGroup userGroup : userGroups) {
            if (userGroup.getName().equals(userGroupName)) {
                return userGroup;
            }
        }

        return null;
    }

    /**
     * Gets the applicable root tree node, depending on whether repository name is provided or not. If repository name
     * is blank then the server wide root tree node is returned, otherwise the repository root tree node is returned.
     *
     * @param repositoryName Name of the repository
     * @return Applicable tree node object
     */
    private TreeNode getApplicableRootTreeNode(final String repositoryName) {
        if (isBlank(repositoryName)) {
            return rootTreeNode;
        }
        else {
            return checkThatRepositoryExists(this, repositoryName).getRootTreeNode();
        }
    }

    /**
     * Gets a copy of all repositories.
     *
     * @return Unmodifiable set of repositories
     */
    public Set<Repository> getRepositories() {
        return Collections.unmodifiableSet(repositories);
    }

    /**
     * Gets the root tree node for access rules/paths that are applicable to all repositories.
     *
     * @return Root tree node for server level paths and access rules
     */
    public TreeNode getRootTreeNode() {
        return rootTreeNode;
    }

    /**
     * Gets a copy of all users.
     *
     * @return Unmodifiable set of users
     */
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    /**
     * Gets a copy of all user groups.
     *
     * @return Unmodifiable set of user groups
     */
    public Set<UserGroup> getUserGroups() {
        return Collections.unmodifiableSet(userGroups);
    }

    /**
     * Removes a user from a user group.
     *
     * @param userName            Name of user to remove
     * @param targetUserGroupName Name of user group from which the user is removed
     * @return True if the user was a member of the group, otherwise false
     * @throws EntityDoesNotExistException When user or user group with name does not exist
     */
    public boolean removeUserFromUserGroup(final String userName, final String targetUserGroupName) {
        final User user = checkThatUserWithNameExists(this, userName);
        final UserGroup targetUserGroup = checkThatUserGroupWithNameExists(this, targetUserGroupName);

        return user.removeUserGroup(targetUserGroup) && targetUserGroup.removeUserMember(user);
    }

    /**
     * Removes a user from a user group.
     *
     * @param userGroupName       Name of user group to remove
     * @param targetUserGroupName Name of user group from which the user group is removed
     * @return True if the user group was a member of the group, otherwise false
     * @throws EntityDoesNotExistException When user group with name does not exist
     */
    public boolean removeUserGroupFromUserGroup(final String userGroupName, final String targetUserGroupName) {
        final UserGroup userGroup = checkThatUserGroupWithNameExists(this, userGroupName);
        final UserGroup targetUserGroup = checkThatUserGroupWithNameExists(this, targetUserGroupName);

        return userGroup.removeUserGroup(targetUserGroup) && targetUserGroup.removeUserGroupMember(userGroup);
    }

    /**
     * Renames an existing repository.
     *
     * @param repositoryName    Name of the repository to rename
     * @param newRepositoryName New name of the repository
     * @return Updated repository
     * @throws EntityDoesNotExistException  When repository to rename does not exist
     * @throws EntityAlreadyExistsException When repository with the new name already exists
     */
    public Repository renameRepository(final String repositoryName, final String newRepositoryName) {
        checkThatRepositoryDoesNotExist(this, newRepositoryName);

        final Repository repository = checkThatRepositoryExists(this, repositoryName);
        repository.setName(newRepositoryName);

        return repository;
    }

    /**
     * Renames an existing user.
     *
     * @param userName     Name of the user to rename
     * @param newUserName  New name of the user
     * @param newUserAlias New alias of the user
     * @return Updated user
     * @throws EntityDoesNotExistException  When user to rename does not exist
     * @throws EntityAlreadyExistsException When user with the new name or alias already exists
     */
    public User renameUser(final String userName, final String newUserName, final String newUserAlias) {
        final User user = checkThatUserWithNameExists(this, userName);

        boolean userNameChanged = !equal(user.getName(), newUserName);
        boolean userAliasChanged = !equal(user.getAlias(), newUserAlias);

        if (userNameChanged && userAliasChanged) {
            checkThatUserWithNameDoesNotExist(this, newUserName);
            checkThatUserWithAliasDoesNotExist(this, newUserAlias);

            user.setName(newUserName);
            user.setAlias(newUserAlias);
        }
        else if (userNameChanged) {
            checkThatUserWithNameDoesNotExist(this, newUserName);

            user.setName(newUserName);
        }
        else if (userAliasChanged) {
            checkThatUserWithAliasDoesNotExist(this, newUserAlias);

            user.setAlias(newUserAlias);
        }

        return user;
    }

    /**
     * Renames an existing user group.
     *
     * @param userGroupName    Name of the user group to rename
     * @param newUserGroupName New name of the user group
     * @return Updated user group
     * @throws EntityDoesNotExistException  When user group to rename does not exist
     * @throws EntityAlreadyExistsException When user group with the new name already exists
     */
    public UserGroup renameUserGroup(final String userGroupName, final String newUserGroupName) {
        checkThatRepositoryDoesNotExist(this, newUserGroupName);

        final UserGroup userGroup = checkThatUserGroupWithNameExists(this, userGroupName);
        userGroup.setName(newUserGroupName);

        return userGroup;
    }
}
