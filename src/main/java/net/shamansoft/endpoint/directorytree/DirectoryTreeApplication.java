package net.shamansoft.endpoint.directorytree;

import net.shamansoft.endpoint.directorytree.command.Command;
import net.shamansoft.endpoint.directorytree.command.CommandFactory;
import net.shamansoft.endpoint.directorytree.command.CommandException;
import net.shamansoft.endpoint.directorytree.model.FileSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DirectoryTreeApplication {

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        CommandFactory commandFactory = new CommandFactory(fileSystem);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                if (trimmed.equalsIgnoreCase("EXIT")) {
                    break;
                }
                try {
                    Command command = commandFactory.createCommand(line);
                    List<String> validationErrors = command.validate();
                    if(validationErrors != null && !validationErrors.isEmpty()) {
                        System.out.println("Invalid command: " + line);
                        for (String error : validationErrors) {
                            System.out.println(error);
                        }
                        continue;
                    }
                    String output = command.execute();
                    System.out.print(output);
                } catch (CommandException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}