package net.shamansoft.endpoint.directorytree.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for validating directory paths.
 */
public class PathValidator {
    // Pattern that allows alphanumeric characters, underscores, and hyphens in directory names

    // Maximum allowed path length
    private static final int MAX_PATH_LENGTH = 500;

    /**
     * Validates a directory path.
     *
     * @param path the path to validate
     * @return List of error message if it's invalid, empty list if it's valid
     */
    public List<String> validatePath(String path) {

        if (StringUtils.isNullOrBlank(path)) {
            return List.of("Path cannot be empty");
        }
        List<String> errors = new ArrayList<>();
        if (path.length() > MAX_PATH_LENGTH) {
            errors.add("Path is too long (maximum " + MAX_PATH_LENGTH + " characters)");
        }

        if (path.startsWith("/") || path.endsWith("/")) {
            errors.add("Path cannot start or end with '/'");
        }

        if (path.contains("//")) {
            errors.add("Path cannot contain consecutive '/' characters");
        }
        return errors;
    }
}