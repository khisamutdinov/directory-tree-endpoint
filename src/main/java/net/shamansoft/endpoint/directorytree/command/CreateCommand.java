package net.shamansoft.endpoint.directorytree.command;

import lombok.RequiredArgsConstructor;
import net.shamansoft.endpoint.directorytree.model.FileSystem;
import net.shamansoft.endpoint.directorytree.utils.PathValidator;
import net.shamansoft.endpoint.directorytree.utils.StringUtils;

import java.util.List;

/**
 * Command to create a new directory.
 */
@RequiredArgsConstructor
public class CreateCommand implements Command {
    private final PathValidator pathValidator;
    private final FileSystem fileSystem;
    private final String path;
    private final String originalCommand;

    @Override
    public List<String> validate() {
        return pathValidator.validatePath(path);
    }

    @Override
    public String execute() {

        boolean created = fileSystem.createDirectory(path);

        return created ? "%s\n".formatted(originalCommand) : "An error occurred while creation";
    }
}