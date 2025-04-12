package net.shamansoft.endpoint.directorytree.command;

import net.shamansoft.endpoint.directorytree.model.Directory;
import net.shamansoft.endpoint.directorytree.model.FileSystem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteCommandTest {

    @Test
    void executeReturnsSuccessMessageWhenDirectoryIsDeleted() {
        FileSystem fileSystem = mock(FileSystem.class);
        when(fileSystem.deleteDirectory("/valid/path")).thenReturn(true);

        DeleteCommand deleteCommand = new DeleteCommand(fileSystem, "/valid/path", "delete /valid/path");
        String result = deleteCommand.execute();

        assertThat(result).isEqualTo("delete /valid/path\n");
    }

    @Test
    void executeReturnsErrorWhenParentDirectoryDoesNotExist() {
        FileSystem fileSystem = mock(FileSystem.class);
        when(fileSystem.deleteDirectory("/invalid/path")).thenReturn(false);
        when(fileSystem.findDirectory("/invalid")).thenReturn(null);

        DeleteCommand deleteCommand = new DeleteCommand(fileSystem, "/invalid/path", "delete /invalid/path");
        String result = deleteCommand.execute();

        assertThat(result).isEqualTo("delete /invalid/path\nCannot delete /invalid/path - /invalid does not exist\n");
    }

    @Test
    void executeReturnsErrorWhenDirectoryNotFound() {
        FileSystem fileSystem = mock(FileSystem.class);
        when(fileSystem.deleteDirectory("/missing/path")).thenReturn(false);
        when(fileSystem.findDirectory("/missing")).thenReturn(mock(Directory.class));

        DeleteCommand deleteCommand = new DeleteCommand(fileSystem, "/missing/path", "delete /missing/path");
        String result = deleteCommand.execute();

        assertThat(result).isEqualTo("delete /missing/path\nCannot delete /missing/path - directory not found\n");
    }

    @Test
    void executeHandlesRootPathDeletion() {
        FileSystem fileSystem = mock(FileSystem.class);
        when(fileSystem.deleteDirectory("/")).thenReturn(false);
        when(fileSystem.findDirectory("")).thenReturn(null);

        DeleteCommand deleteCommand = new DeleteCommand(fileSystem, "/", "delete /");
        String result = deleteCommand.execute();

        assertThat(result).isEqualTo("delete /\nCannot delete / -  does not exist\n");
    }
}