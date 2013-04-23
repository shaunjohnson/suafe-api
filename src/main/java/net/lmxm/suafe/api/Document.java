package net.lmxm.suafe.api;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Document {
    /**
     * Set of all repositories.
     */
    private Set<Repository> repositories = new HashSet<Repository>();

    /**
     * Set of all users.
     */
    private Set<User> users = new HashSet<User>();

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

        return cloneUser;
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
     * Deletes an existing repository with the provided name.
     *
     * @param repositoryName Name of the repository to delete
     * @throws EntityDoesNotExistException When repository to delete does not exist
     */
    public void deleteRepository(final String repositoryName) {
        final Repository repository = checkThatRepositoryExists(this, repositoryName);

        repositories.remove(repository);
    }

    /**
     * Deletes an existing user with the provided name.
     *
     * @param userName Name of the user to delete
     * @throws EntityDoesNotExistException When user to delete does not exist
     */
    public void deleteUser(final String userName) {
        final User user = checkThatUserWithNameExists(this, userName);

        users.remove(user);
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
     * Gets a copy of all repositories.
     *
     * @return Unmodifiable set of repositories
     */
    public Set<Repository> getRepositories() {
        return Collections.unmodifiableSet(repositories);
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
}
