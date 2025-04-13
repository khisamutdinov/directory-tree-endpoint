package net.shamansoft.endpoint.directorytree.command;

import net.shamansoft.endpoint.directorytree.model.FileSystem;
import net.shamansoft.endpoint.directorytree.utils.PathValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteCommandTest {

    @Test
    void executeReturnsSuccessMessageWhenDirectoryIsDeleted() {
        PathValidator pathValidator = mock(PathValidator.class);
        FileSystem fileSystem = mock(FileSystem.class);
        when(fileSystem.deleteDirectory("/valid/path")).thenReturn(true);

        DeleteCommand deleteCommand = new DeleteCommand(pathValidator, fileSystem, "/valid/path", "delete /valid/path");
        String result = deleteCommand.execute();

        assertThat(result).isEqualTo("delete /valid/path\n");
    }

    @Test
    void executeReturnsErrorMessageWhenDeletionFails() {
        PathValidator pathValidator = mock(PathValidator.class);
        FileSystem fileSystem = mock(FileSystem.class);
        doThrow(new IllegalArgumentException("Directory not found")).when(fileSystem).deleteDirectory("/invalid/path");

        DeleteCommand deleteCommand = new DeleteCommand(pathValidator, fileSystem, "/invalid/path", "delete /invalid/path");
        String result = deleteCommand.execute();

        assertThat(result).isEqualTo("delete /invalid/path\nCannot delete /invalid/path - Directory not found\n");
    }

    @Test
    void executeHandlesEmptyPathGracefully() {
        PathValidator pathValidator = mock(PathValidator.class);
        FileSystem fileSystem = mock(FileSystem.class);
        doThrow(new IllegalArgumentException("Path cannot be empty")).when(fileSystem).deleteDirectory("");

        DeleteCommand deleteCommand = new DeleteCommand(pathValidator, fileSystem, "", "delete ");
        String result = deleteCommand.execute();

        assertThat(result).isEqualTo("delete \nCannot delete  - Path cannot be empty\n");
    }
}