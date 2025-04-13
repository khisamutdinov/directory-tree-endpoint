package net.shamansoft.endpoint.directorytree;

import net.shamansoft.endpoint.directorytree.command.Command;
import net.shamansoft.endpoint.directorytree.command.CommandFactory;
import net.shamansoft.endpoint.directorytree.command.CommandException;
import net.shamansoft.endpoint.directorytree.model.FileSystem;
import net.shamansoft.endpoint.directorytree.utils.InputProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DirectoryTreeApplication {

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        CommandFactory commandFactory = new CommandFactory(fileSystem);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Directory Tree Application");
            System.out.println("Enter commands (CREATE, LIST, MOVE, DELETE) or type EXIT to quit");
            System.out.println("You can also paste multiple commands at once - each command on a new line");
            System.out.println("----------------------------------------------------------");

            StringBuilder inputBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if this is an empty line and we have accumulated input
                if (line.trim().isEmpty() && !inputBuffer.isEmpty()) {
                    // Process the accumulated multi-line input
                    processMultilineInput(inputBuffer.toString(), commandFactory);
                    inputBuffer.setLength(0); // Clear the buffer
                    continue;
                }

                // Check for EXIT command
                if (line.trim().equalsIgnoreCase("EXIT")) {
                    System.out.println("Exiting application. Goodbye!");
                    break;
                }

                // Accumulate the input
                inputBuffer.append(line).append("\n");

                // If the line looks like a complete command (doesn't end with a backslash),
                // process it immediately rather than waiting for an empty line
                if (!line.trim().endsWith("\\")) {
                    processMultilineInput(inputBuffer.toString(), commandFactory);
                    inputBuffer.setLength(0); // Clear the buffer
                }
            }

            // Process any remaining input in the buffer
            if (inputBuffer.length() > 0) {
                processMultilineInput(inputBuffer.toString(), commandFactory);
            }

        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    /**
     * Processes a potentially multi-line input by splitting it into individual commands
     * and executing each one.
     *
     * @param input the multi-line input
     * @param commandFactory the command factory
     */
    private static void processMultilineInput(String input, CommandFactory commandFactory) {
        List<String> commands = InputProcessor.splitCommandLines(input);

        for (String commandLine : commands) {
            try {
                Command command = commandFactory.createCommand(commandLine);
                List<String> validationErrors = command.validate();
                if(validationErrors != null && !validationErrors.isEmpty()) {
                    System.out.println("Invalid command: " + commandLine);
                    for (String error : validationErrors) {
                        System.out.println(error);
                    }
                    continue;
                }
                String output = command.execute();
                System.out.print(output);
            } catch (CommandException e) {
                System.out.println(commandLine);
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}