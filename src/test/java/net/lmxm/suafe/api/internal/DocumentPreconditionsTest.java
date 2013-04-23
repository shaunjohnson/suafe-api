package net.lmxm.suafe.api.internal;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.*;

import net.lmxm.suafe.api.Document;
import net.lmxm.suafe.api.EntityAlreadyExistsException;
import net.lmxm.suafe.api.EntityDoesNotExistException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public final class DocumentPreconditionsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCheckArgumentNotBlank() {
        // Test null
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString("field"));
        checkArgumentNotBlank(null, "field");

        // Test empty
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString("field"));
        checkArgumentNotBlank("", "field");

        // Test blank
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString("field"));
        checkArgumentNotBlank("    ", "field");

        // Test valid values
        checkArgumentNotBlank("a", "field");
        checkArgumentNotBlank("abc", "field");
    }

    @Test
    public void testCheckThatRepositoryDoesNotExist() {
        final Document document = new Document();

        // Test adding first repository
        checkThatRepositoryDoesNotExist(document, "repositoryName");
        document.createRepository("repositoryName");

        // Test duplicate check
        thrown.expect(EntityAlreadyExistsException.class);
        thrown.expectMessage(containsString("repositoryName"));
        thrown.expectMessage(containsString("already exists"));
        checkThatRepositoryDoesNotExist(document, "repositoryName");
    }

    @Test
    public void testCheckThatRepositoryAlreadyExists() {
        final Document document = new Document();

        // Test clean document check
        thrown.expect(EntityDoesNotExistException.class);
        thrown.expectMessage(containsString("repositoryName"));
        thrown.expectMessage(containsString("does not exist"));
        checkThatRepositoryExists(document, "repositoryName");

        // Test after adding first repository
        document.createRepository("repositoryName");
        checkThatRepositoryExists(document, "repositoryName");
    }

    @Test
    public void testCheckThatUserWithAliasDoesNotExist() {
        final Document document = new Document();

        // Test adding first user
        checkThatUserWithAliasDoesNotExist(document, "userAlias");
        document.createUser("userName", "userAlias");

        // Test duplicate check
        thrown.expect(EntityAlreadyExistsException.class);
        thrown.expectMessage(containsString("userAlias"));
        thrown.expectMessage(containsString("already exists"));
        checkThatUserWithAliasDoesNotExist(document, "userAlias");
    }

    @Test
    public void testCheckThatUserWithNameDoesNotExist() {
        final Document document = new Document();

        // Test adding first user
        checkThatUserWithNameDoesNotExist(document, "userName");
        document.createUser("userName", null);

        // Test duplicate check
        thrown.expect(EntityAlreadyExistsException.class);
        thrown.expectMessage(containsString("userName"));
        thrown.expectMessage(containsString("already exists"));
        checkThatUserWithNameDoesNotExist(document, "userName");
    }

    @Test
    public void testCheckThatUserGroupWithNameDoesNotExist() {
        final Document document = new Document();

        // Test adding first user group
        checkThatUserGroupWithNameDoesNotExist(document, "userGroupName");
        document.createUserGroup("userGroupName");

        // Test duplicate check
        thrown.expect(EntityAlreadyExistsException.class);
        thrown.expectMessage(containsString("userGroupName"));
        thrown.expectMessage(containsString("already exists"));
        checkThatUserGroupWithNameDoesNotExist(document, "userGroupName");
    }

    @Test
    public void testCheckThatUserWithNameExists() {
        final Document document = new Document();

        // Test clean document check
        thrown.expect(EntityDoesNotExistException.class);
        thrown.expectMessage(containsString("userName"));
        thrown.expectMessage(containsString("does not exist"));
        checkThatUserWithNameExists(document, "userName");

        // Test after adding first user
        document.createUser("userName", null);
        checkThatUserWithNameExists(document, "userName");
    }

    @Test
    public void testCheckThatUserGroupWithNameExists() {
        final Document document = new Document();

        // Test clean document check
        thrown.expect(EntityDoesNotExistException.class);
        thrown.expectMessage(containsString("userGroupName"));
        thrown.expectMessage(containsString("does not exist"));
        checkThatUserGroupWithNameExists(document, "userGroupName");

        // Test after adding first user group
        document.createUserGroup("userGroupName");
        checkThatUserGroupWithNameExists(document, "userGroupName");
    }

    @Test
    public void testEqual() {
        assertThat(equal(null, null), is(true));
        assertThat(equal("foobar", "foobar"), is(true));

        assertThat(equal(null, "foobar"), is(false));
        assertThat(equal("foobar", null), is(false));
        assertThat(equal("abcde", "qwerty"), is(false));
    }

    @Test
    public void testIsBlank() {
        assertThat(isBlank(null), is(true));
        assertThat(isBlank(""), is(true));
        assertThat(isBlank("    "), is(true));

        assertThat(isBlank("abc"), is(false));
        assertThat(isBlank("  abc  "), is(false));
    }

    @Test
    public void testIsNotBlank() {
        assertThat(isNotBlank(null), is(false));
        assertThat(isNotBlank(""), is(false));
        assertThat(isNotBlank("    "), is(false));

        assertThat(isNotBlank("abc"), is(true));
        assertThat(isNotBlank("  abc  "), is(true));
    }
}
