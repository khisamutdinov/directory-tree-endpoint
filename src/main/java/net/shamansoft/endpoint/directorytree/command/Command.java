package net.shamansoft.endpoint.directorytree.command;

import java.util.List;

/**
 * Interface for all directory commands.
 */
public interface Command {

    /**
     * Checks if the command is valid.
     *
     * @return null if the command is valid, array of errors otherwise
     */
    default List<String> validate() {
        return List.of();
    }
    /**
     * Executes the command.
     *
     * @return output of the command execution
     */
    String execute();
}