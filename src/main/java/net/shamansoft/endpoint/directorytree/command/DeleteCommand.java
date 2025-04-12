package net.shamansoft.endpoint.directorytree.command;

import lombok.RequiredArgsConstructor;
import net.shamansoft.endpoint.directorytree.model.FileSystem;

/**
 * Command to delete a directory.
 */
@RequiredArgsConstructor
public class DeleteCommand implements Command {
    private final FileSystem fileSystem;
    private final String path;
    private final String originalCommand;

    @Override
    public String execute() {

        StringBuilder result = new StringBuilder(originalCommand).append("\n");
        try {
            fileSystem.deleteDirectory(path);
        } catch (IllegalArgumentException e) {
            result.append("Cannot delete %s - %s\n".formatted(path, e.getMessage()));
        }
     return result.toString();
    }
}