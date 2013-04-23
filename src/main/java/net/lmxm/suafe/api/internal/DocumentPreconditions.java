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
            throw new EntityAlreadyExistsException("Repository with name \"" + repositoryName + "\" already exists");
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
            throw new EntityAlreadyExistsException("User with alias \"" + userAlias + "\" already exists");
        }
    }

    /**
     * Checks that a user with this name does not exist.
     *
     * @param document Document to check
     * @param userName Name of repository to check
     * @throws EntityAlreadyExistsException When user with name already exists
     */
    public static void checkThatUserWithNameDoesNotExist(final Document document, final String userName) {
        if (document.findUserByName(userName) != null) {
            throw new EntityAlreadyExistsException("User with name \"" + userName + "\" already exists");
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
            throw new EntityDoesNotExistException("Repository with name \"" + repositoryName + "\" does not exist");
        }

        return repository;
    }

    /**
     * Checks that a user with name exists.
     *
     * @param document       Document to check
     * @param userName Name of user to check
     * @return Matching user
     * @throws EntityDoesNotExistException When user with name does not exist
     */
    public static User checkThatUserWithNameExists(final Document document, final String userName) {
        final User user = document.findUserByName(userName);
        if (user == null) {
            throw new EntityDoesNotExistException("User with name \"" + userName + "\" does not exist");
        }

        return user;
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
