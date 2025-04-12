package net.shamansoft.endpoint.directorytree.command;

public class UnknownCommandException extends Exception {

    public UnknownCommandException(String message) {
        super("\"%s\" command is not supported".formatted(message));
    }
}
