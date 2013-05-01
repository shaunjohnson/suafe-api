package net.lmxm.suafe.api.internal;

import net.lmxm.suafe.api.*;

import java.util.regex.Pattern;

public final class DocumentPreconditions {
    /**
     * Regular expression pattern representing a valid path value.
     */
    private static final Pattern VALID_PATH_PATTERN = Pattern.compile("^(/)|([^/=]+(/[^/=]+)*)$");

    /**
     * Checks if the provided argument is blank.
     *
     * @param argument  Argument to check
     * @param fieldName Name of the argument field being checked
     * @return The argument passed in, if not blank
     * @throws IllegalArgumentException When argument is blank
     */
    public static String checkArgumentNotBlank(final String argument, final String fieldName) {
        if (isBlank(argument)) {
            throw new IllegalArgumentException(fieldName + " may not be null/blank");
        }

        return argument;
    }

    /**
     * Checks if the provided argument is null.
     *
     * @param argument  Argument to check
     * @param fieldName Name of the argument field being checked
     * @throws IllegalArgumentException When argument is null
     */
    public static void checkArgumentNotNull(final Object argument, final String fieldName) {
        if (argument == null) {
            throw new IllegalArgumentException(fieldName + " may not be null");
        }
    }

    /**
     * Checks if the provided argument is null.
     *
     * @param argument  Argument to check
     * @param fieldName Name of the argument field being checked
     * @throws IllegalArgumentException When argument is null
     */
    public static void checkArgumentPathValid(final String argument, final String fieldName) {
        if (isBlank(argument)) {
            throw new IllegalArgumentException(fieldName + " may not be null/blank");
        }

        if (!VALID_PATH_PATTERN.matcher(argument).matches()) {
            throw new IllegalArgumentException(fieldName + " is not a valid path");
        }
    }

    /**
     * Checks that an access rule at the provided path for the provided user does not exist.
     *
     * @param treeNode Tree node to traverse
     * @param path     Path of node to check
     * @param user     User that is used to find a matching access rule
     * @throws EntityAlreadyExistsException When access rule at path for user already exists
     */
    public static void checkThatAccessRuleForUserDoesNotExist(final TreeNode treeNode, final String path, final User user) {
        if (treeNode.findAccessRuleForUserAtPath(path, user) != null) {
            throw new EntityAlreadyExistsException(MessageKey.accessRuleForUserAlreadyExists, path, user.getName());
        }
    }

    /**
     * Checks that an access rule at the provided path for the provided user group does not exist.
     *
     * @param treeNode  Tree node to traverse
     * @param path      Path of node to check
     * @param userGroup User group that is used to find a matching access rule
     * @throws EntityAlreadyExistsException When access rule at path for user group already exists
     */
    public static void checkThatAccessRuleForUserGroupDoesNotExist(final TreeNode treeNode, final String path, final UserGroup userGroup) {
        if (treeNode.findAccessRuleForUserGroupAtPath(path, userGroup) != null) {
            throw new EntityAlreadyExistsException(MessageKey.accessRuleForUserGroupAlreadyExists, path, userGroup.getName());
        }
    }

    /**
     * Checks that a repository with this name does not exist.
     *
     * @param document       Document to check
     * @param repositoryName Name of repository to check
     * @throws EntityAlreadyExistsException When repository with name already exists
     */
    public static void checkThatRepositoryDoesNotExist(final Document document, final String repositoryName) {
        if (document.findRepositoryByName(repositoryName) != null) {
            throw new EntityAlreadyExistsException(MessageKey.repositoryWithNameAlreadyExists, repositoryName);
        }
    }

    /**
     * Checks that a user with this alias does not exist.
     *
     * @param document  Document to check
     * @param userAlias Alias of user to check
     * @throws EntityAlreadyExistsException When user with alias already exists
     */
    public static void checkThatUserWithAliasDoesNotExist(final Document document, final String userAlias) {
        if (document.findUserByAlias(userAlias) != null) {
            throw new EntityAlreadyExistsException(MessageKey.userWithAliasAlreadyExists, userAlias);
        }
    }

    /**
     * Checks that a user with this name does not exist.
     *
     * @param document Document to check
     * @param userName Name of user to check
     * @throws EntityAlreadyExistsException When user with name already exists
     */
    public static void checkThatUserWithNameDoesNotExist(final Document document, final String userName) {
        if (document.findUserByName(userName) != null) {
            throw new EntityAlreadyExistsException(MessageKey.userWithNameAlreadyExists, userName);
        }
    }

    /**
     * Checks that a user group with this name does not exist.
     *
     * @param document      Document to check
     * @param userGroupName Name of user group to check
     * @throws EntityAlreadyExistsException When user group with name already exists
     */
    public static void checkThatUserGroupWithNameDoesNotExist(final Document document, final String userGroupName) {
        if (document.findUserGroupByName(userGroupName) != null) {
            throw new EntityAlreadyExistsException(MessageKey.userGroupWithNameAlreadyExists, userGroupName);
        }
    }

    /**
     * Checks that a repository exists.
     *
     * @param document       Document to check
     * @param repositoryName Name of repository to check
     * @return Matching repository
     * @throws EntityDoesNotExistException When repository with name does not exist
     */
    public static Repository checkThatRepositoryExists(final Document document, final String repositoryName) {
        final Repository repository = document.findRepositoryByName(repositoryName);
        if (repository == null) {
            throw new EntityDoesNotExistException(MessageKey.repositoryWithNameDoesNotExist, repositoryName);
        }

        return repository;
    }

    /**
     * Checks that a user with name exists.
     *
     * @param document Document to check
     * @param userName Name of user to check
     * @return Matching user
     * @throws EntityDoesNotExistException When user with name does not exist
     */
    public static User checkThatUserWithNameExists(final Document document, final String userName) {
        final User user = document.findUserByName(userName);
        if (user == null) {
            throw new EntityDoesNotExistException(MessageKey.userWithNameDoesNotExist, userName);
        }

        return user;
    }

    /**
     * Checks that a user group with name exists.
     *
     * @param document      Document to check
     * @param userGroupName Name of user group to check
     * @return Matching user group
     * @throws EntityDoesNotExistException When user group with name does not exist
     */
    public static UserGroup checkThatUserGroupWithNameExists(final Document document, final String userGroupName) {
        final UserGroup userGroup = document.findUserGroupByName(userGroupName);
        if (userGroup == null) {
            throw new EntityDoesNotExistException(MessageKey.userGroupWithNameDoesNotExist, userGroupName);
        }

        return userGroup;
    }

    /**
     * Checks if the provided values are equal or not.
     *
     * @param a First object to compare
     * @param b Second object to compare
     * @return True if the objects are equal, otherwise false
     */
    public static boolean equal(final Object a, final Object b) {
        return a == b || (a != null && a.equals(b));
    }

    /**
     * Checks if the provided String value is blank (i.e. null, empty or blank).
     *
     * @param value String value to test
     * @return True if the value is blank, otherwise false
     */
    public static boolean isBlank(final String value) {
        return value == null || value.trim().length() == 0;
    }

    /**
     * Checks if the provided String value is not blank (i.e. not null, empty or blank).
     *
     * @param value String value to test
     * @return True if the value is not blank, otherwise false
     */
    public static boolean isNotBlank(final String value) {
        return !isBlank(value);
    }

    private DocumentPreconditions() {
        throw new AssertionError("Cannot be instantiated");
    }
}
