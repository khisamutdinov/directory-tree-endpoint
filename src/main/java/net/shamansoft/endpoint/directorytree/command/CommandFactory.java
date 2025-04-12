package net.shamansoft.endpoint.directorytree.command;

import lombok.RequiredArgsConstructor;
import net.shamansoft.endpoint.directorytree.model.FileSystem;

import static net.shamansoft.endpoint.directorytree.utils.StringUtils.isNullOrBlank;

/**
 * Factory for creating command objects based on input strings.
 */
@RequiredArgsConstructor
public class CommandFactory {
    private final FileSystem fileSystem;

    /**
     * Creates a command based on the input string.
     *
     * @param input the command input string
     * @return the created command
     * @throws UnknownCommandException if the command is unknown
     */
    public Command createCommand(String input) throws UnknownCommandException {
        if (isNullOrBlank(input)) {
            return null;
        }

        String[] parts = input.trim().split("\\s+", 3);
        String commandType = parts[0].toUpperCase();

        switch (commandType) {
            case "CREATE":
                if (parts.length < 2) {
                    return null;
                }
                return new CreateCommand(fileSystem, parts[1], input);

            case "LIST":
                return new ListCommand(fileSystem, input);

            case "MOVE":
            case "DELETE":
                throw new UnsupportedOperationException("\"%s\" command is not supported yet".formatted(commandType));
            default:
                throw new UnknownCommandException(commandType);
        }
    }
}