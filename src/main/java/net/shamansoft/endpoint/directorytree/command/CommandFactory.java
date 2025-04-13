package net.shamansoft.endpoint.directorytree.command;

import lombok.RequiredArgsConstructor;
import net.shamansoft.endpoint.directorytree.model.FileSystem;
import net.shamansoft.endpoint.directorytree.utils.PathValidator;

import static net.shamansoft.endpoint.directorytree.utils.StringUtils.isNullOrBlank;

/**
 * Factory for creating command objects based on input strings.
 */
@RequiredArgsConstructor
public class CommandFactory {
    private final FileSystem fileSystem;
    private final PathValidator pathValidator = new PathValidator();

    /**
     * Creates a command based on the input string.
     *
     * @param input the command input string
     * @return the created command
     * @throws CommandException if the command is unknown
     */
    public Command createCommand(String input) throws CommandException {
        if (isNullOrBlank(input)) {
            return null;
        }

        String[] parts = input.trim().split("\\s+", 3);
        String commandType = parts[0].toUpperCase();

        switch (commandType) {
            case "CREATE":
                if (parts.length < 2) {
                    throw new CommandException("CREATE command requires a path parameter");
                }
                return new CreateCommand(pathValidator, fileSystem, parts[1], input);

            case "LIST":
                return new ListCommand(fileSystem, input);

            case "MOVE":
                if (parts.length < 3) {
                    throw new CommandException("MOVE command requires a source and a destination path parameters");
                }
                return new MoveCommand(pathValidator, fileSystem, parts[1], parts[2], input);

            case "DELETE":
                if (parts.length < 2) {
                    throw new CommandException("DELETE command requires a path parameter");
                }
                return new DeleteCommand(pathValidator, fileSystem, parts[1], input);

            default:
                throw new CommandException("\"%s\" command is not supported".formatted(commandType));
        }
    }
}