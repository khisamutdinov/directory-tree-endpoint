package net.shamansoft.endpoint.directorytree.model;

import java.util.List;

import static net.shamansoft.endpoint.directorytree.utils.StringUtils.isNullOrBlank;

/**
 * Manages the directory hierarchy.
 * Provides methods for creating, moving, and deleting directories.
 */
public class FileSystem {

    /**
     * Creates a new directory at the specified path.
     * Creates parent directories as needed.
     *
     * @param path the path to create
     * @return true if the directory was created, false validation failed
     */
    public boolean createDirectory(String path) {
        if (isNullOrBlank(path)) {
            return false;
        }
        return true;
    }

    /**
     * Generates a formatted listing of the directory structure.
     *
     * @return a list of strings representing the directory structure
     */
    public List<String> listDirectories() {
        List<String> result = List.of("Stub 1", "Stub 2", "Stub 3");
        return result;
    }
}