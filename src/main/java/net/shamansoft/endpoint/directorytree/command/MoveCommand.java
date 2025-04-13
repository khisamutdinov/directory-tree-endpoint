package net.shamansoft.endpoint.directorytree.command;

import lombok.RequiredArgsConstructor;
import net.shamansoft.endpoint.directorytree.model.FileSystem;
import net.shamansoft.endpoint.directorytree.utils.PathValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to move a directory to a new location.
 */
@RequiredArgsConstructor
public class MoveCommand implements Command {
    private final PathValidator pathValidator;
    private final FileSystem fileSystem;
    private final String sourcePath;
    private final String destinationPath;
    private final String originalCommand;

    @Override
    public List<String> validate() {
        List<String> validationErrors = new ArrayList<>();
        validationErrors.addAll(pathValidator.validatePath(sourcePath));
        validationErrors.addAll(pathValidator.validatePath(destinationPath));
        return validationErrors;
    }

    @Override
    public String execute() {
        try {
            fileSystem.moveDirectory(sourcePath, destinationPath);
        } catch (IllegalArgumentException e) {
            return "Cannot move %s to %s - %s\n".formatted(sourcePath, destinationPath, e.getMessage());
        }
        return originalCommand + "\n";
    }
}