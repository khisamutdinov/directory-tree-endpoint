package net.shamansoft.endpoint.directorytree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DirectoryTreeApplication {

    public static void main(String[] args) {

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
                // echo the command
                System.out.printf("echo: %s%n", line);
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}