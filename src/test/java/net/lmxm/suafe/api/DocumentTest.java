package net.lmxm.suafe.api;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static net.lmxm.suafe.api.CustomMatchers.emptySet;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public final class DocumentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCloneRepository() {
        final Document document = new Document();

        assertThat(document.findRepositoryByName("repositoryName"), is(nullValue()));
        assertThat(document.findRepositoryByName("cloneRepositoryName"), is(nullValue()));

        document.createRepository("repositoryName");
        assertThat(document.findRepositoryByName("repositoryName"), is(notNullValue()));
        assertThat(document.findRepositoryByName("cloneRepositoryName"), is(nullValue()));

        document.cloneRepository("repositoryName", "cloneRepositoryName");
        assertThat(document.findRepositoryByName("repositoryName"), is(notNullValue()));
        assertThat(document.findRepositoryByName("cloneRepositoryName"), is(notNullValue()));

        thrown.expect(EntityAlreadyExistsException.class);
        document.cloneRepository("cloneRepositoryName", "repositoryName");
    }

    @Test
    public void testCloneUser() {
        final Document document = new Document();

        assertThat(document.findUserByName("userName"), is(nullValue()));
        assertThat(document.findUserByName("cloneUserName"), is(nullValue()));

        document.createUser("userName", null);
        assertThat(document.findUserByName("userName"), is(notNullValue()));
        assertThat(document.findUserByName("userName").getAlias(), is(nullValue()));
        assertThat(document.findUserByName("cloneUserName"), is(nullValue()));

        document.cloneUser("userName", "cloneUserName", "cloneUserAlias");
        assertThat(document.findUserByName("userName"), is(notNullValue()));
        assertThat(document.findUserByName("userName").getAlias(), is(nullValue()));
        assertThat(document.findUserByName("cloneUserName"), is(notNullValue()));
        assertThat(document.findUserByName("cloneUserName").getAlias(), is(equalTo("cloneUserAlias")));

        thrown.expect(EntityAlreadyExistsException.class);
        document.cloneUser("cloneUserName", "userName", null);
    }

    @Test
    public void testCreateRepository() {
        final Document document = new Document();

        assertThat(document.findRepositoryByName("repositoryName"), is(nullValue()));
        document.createRepository("repositoryName");
        assertThat(document.findRepositoryByName("repositoryName"), is(notNullValue()));

        thrown.expect(EntityAlreadyExistsException.class);
        document.createRepository("repositoryName");
    }

    @Test
    public void testCreateUser() {
        final Document document = new Document();

        // Test user name
        assertThat(document.findUserByName("userName"), is(nullValue()));
        document.createUser("userName", null);
        assertThat(document.findUserByName("userName"), is(notNullValue()));

        thrown.expect(EntityAlreadyExistsException.class);
        document.createUser("userName", null);

        // Test user alias
        assertThat(document.findUserByAlias("userAlias"), is(nullValue()));
        document.createUser("userName1", "userAlias");
        assertThat(document.findUserByAlias("userAlias"), is(notNullValue()));

        thrown.expect(EntityAlreadyExistsException.class);
        document.createUser("userName2", "userAlias");
    }

    @Test
    public void testDeleteRepository() {
        final Document document = new Document();

        assertThat(document.findRepositoryByName("repositoryName"), is(nullValue()));

        document.createRepository("repositoryName");
        assertThat(document.findRepositoryByName("repositoryName"), is(notNullValue()));

        document.deleteRepository("repositoryName");
        assertThat(document.findRepositoryByName("repositoryName"), is(nullValue()));

        thrown.expect(EntityDoesNotExistException.class);
        document.deleteRepository("repositoryName");
    }

    @Test
    public void testDeleteUser() {
        final Document document = new Document();

        assertThat(document.findUserByName("userName"), is(nullValue()));

        document.createUser("userName", null);
        assertThat(document.findUserByName("userName"), is(notNullValue()));

        document.deleteUser("userName");
        assertThat(document.findUserByName("userName"), is(nullValue()));

        thrown.expect(EntityDoesNotExistException.class);
        document.deleteUser("userName");
    }

    @Test
    public void testFindRepositoryByName() {
        final Document document = new Document();

        assertThat(document.findRepositoryByName("repositoryName"), is(nullValue()));

        document.createRepository("repositoryName");
        assertThat(document.findRepositoryByName("repositoryName"), is(notNullValue()));
        assertThat(document.findRepositoryByName("repositoryName").getName(), is(equalTo("repositoryName")));
    }

    @Test
    public void testFindUserByAlias() {
        final Document document = new Document();

        assertThat(document.findUserByAlias("userAlias"), is(nullValue()));

        document.createUser("userName", "userAlias");
        assertThat(document.findUserByAlias("userAlias"), is(notNullValue()));
        assertThat(document.findUserByAlias("userAlias").getAlias(), is(equalTo("userAlias")));
    }

    @Test
    public void testFindUserByName() {
        final Document document = new Document();

        assertThat(document.findUserByName("userName"), is(nullValue()));

        document.createUser("userName", null);
        assertThat(document.findUserByName("userName"), is(notNullValue()));
        assertThat(document.findUserByName("userName").getName(), is(equalTo("userName")));
    }

    @Test
    public void testGetRepositories() {
        final Document document = new Document();

        assertThat(document.getRepositories(), is(notNullValue()));
        assertThat(document.getRepositories(), is(emptySet()));

        document.createRepository("repositoryName");

        assertThat(document.getRepositories(), is(notNullValue()));
        assertThat(document.getRepositories(), is(not(emptySet())));
    }

    @Test
    public void testGetUsers() {
        final Document document = new Document();

        assertThat(document.getUsers(), is(notNullValue()));
        assertThat(document.getUsers(), is(emptySet()));

        document.createUser("userName", "userAlias");

        assertThat(document.getUsers(), is(notNullValue()));
        assertThat(document.getUsers(), is(not(emptySet())));
    }

    @Test
    public void testRenameRepository() {
        final Document document = new Document();

        assertThat(document.findRepositoryByName("repositoryName"), is(nullValue()));
        assertThat(document.findRepositoryByName("newRepositoryName"), is(nullValue()));

        document.createRepository("repositoryName");
        assertThat(document.findRepositoryByName("repositoryName"), is(notNullValue()));
        assertThat(document.findRepositoryByName("repositoryName").getName(), is(equalTo("repositoryName")));
        assertThat(document.findRepositoryByName("newRepositoryName"), is(nullValue()));

        document.renameRepository("repositoryName", "newRepositoryName");
        assertThat(document.findRepositoryByName("newRepositoryName"), is(notNullValue()));
        assertThat(document.findRepositoryByName("newRepositoryName").getName(), is(equalTo("newRepositoryName")));
        assertThat(document.findRepositoryByName("repositoryName"), is(nullValue()));
    }

    @Test
    public void testRenameUserAlias() {
        final Document document = new Document();

        assertThat(document.findUserByAlias("userAlias"), is(nullValue()));
        assertThat(document.findUserByAlias("newUserAlias"), is(nullValue()));

        document.createUser("userName", "userAlias");
        assertThat(document.findUserByAlias("userAlias"), is(notNullValue()));
        assertThat(document.findUserByAlias("userAlias").getAlias(), is(equalTo("userAlias")));
        assertThat(document.findUserByAlias("newUserAlias"), is(nullValue()));

        document.renameUser("userName", "userName", "newUserAlias");
        assertThat(document.findUserByAlias("newUserAlias"), is(notNullValue()));
        assertThat(document.findUserByAlias("newUserAlias").getAlias(), is(equalTo("newUserAlias")));
        assertThat(document.findUserByAlias("userAlias"), is(nullValue()));
    }

    @Test
    public void testRenameUserName() {
        final Document document = new Document();

        assertThat(document.findUserByName("userName"), is(nullValue()));
        assertThat(document.findUserByName("newUserName"), is(nullValue()));

        document.createUser("userName", "userAlias");
        assertThat(document.findUserByName("userName"), is(notNullValue()));
        assertThat(document.findUserByName("userName").getName(), is(equalTo("userName")));
        assertThat(document.findUserByName("newUserName"), is(nullValue()));

        document.renameUser("userName", "newUserName", "userAlias");
        assertThat(document.findUserByName("newUserName"), is(notNullValue()));
        assertThat(document.findUserByName("newUserName").getName(), is(equalTo("newUserName")));
        assertThat(document.findUserByName("userName"), is(nullValue()));
    }
}
