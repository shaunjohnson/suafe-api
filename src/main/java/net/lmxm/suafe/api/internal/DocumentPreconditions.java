package net.lmxm.suafe.api.internal;

import net.lmxm.suafe.api.*;

public final class DocumentPreconditions {
    /**
     * Checks if the provided argument is blank.
     *
     * @param argument  Argument to check
     * @param fieldName Name of the argument field being checked
     * @throws IllegalArgumentException When argument is blank
     */
    public static void checkArgumentNotBlank(final String argument, final String fieldName) {
        if (isBlank(argument)) {
            throw new IllegalArgumentException(fieldName + " may not be null/blank");
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
