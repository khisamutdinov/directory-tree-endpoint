package net.shamansoft.endpoint.directorytree.command;

import lombok.RequiredArgsConstructor;
import net.shamansoft.endpoint.directorytree.model.FileSystem;
import java.util.List;

/**
 * Command to list the directory structure.
 */
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final FileSystem fileSystem;
    private final String originalCommand;

    @Override
    public String execute() {

        List<String> listing = fileSystem.listDirectories();

        StringBuilder result = new StringBuilder(originalCommand).append("\n");
        for (String line : listing) {
            result.append(line).append("\n");
        }

        return result.toString();
    }
}