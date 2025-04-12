package net.shamansoft.endpoint.directorytree.command;

import net.shamansoft.endpoint.directorytree.model.FileSystem;

/**
 * Command to create a new directory.
 */
public class CreateCommand implements Command {
    private final FileSystem fileSystem;
    private final String path;
    private final String originalCommand;

    public CreateCommand(FileSystem fileSystem, String path, String originalCommand) {
        this.fileSystem = fileSystem;
        this.path = path;
        this.originalCommand = originalCommand;
    }

    @Override
    public String execute() {

        boolean created = fileSystem.createDirectory(path);

        return created ? "%s\n".formatted(originalCommand) : "An error occurred while creation";
    }
}