package net.shamansoft.endpoint.directorytree.command;

/**
 * Interface for all directory commands.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @return output of the command execution
     */
    String execute();
}