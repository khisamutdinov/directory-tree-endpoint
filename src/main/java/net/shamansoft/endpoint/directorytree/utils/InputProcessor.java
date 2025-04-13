package net.shamansoft.endpoint.directorytree.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for processing multi-line inputs.
 */
public class InputProcessor {

    /**
     * Splits a possibly multi-line input into individual command lines.
     * Handles different line ending types (CR, LF, CRLF) and filters empty lines.
     *
     * @param input the possibly multi-line input string
     * @return a list of individual command lines
     */
    public static List<String> splitCommandLines(String input) {
        if (input == null || input.trim().isEmpty()) {
            return List.of();
        }

        // Split on any kind of line ending
        String[] lines = input.split("\\r?\\n");
        List<String> commands = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                commands.add(trimmed);
            }
        }

        return commands;
    }
}