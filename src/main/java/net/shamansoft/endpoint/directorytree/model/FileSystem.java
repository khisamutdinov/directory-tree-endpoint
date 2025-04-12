package net.shamansoft.endpoint.directorytree.model;

import net.shamansoft.endpoint.directorytree.utils.DirectoryLister;

import java.util.ArrayList;
import java.util.List;

import static net.shamansoft.endpoint.directorytree.utils.StringUtils.isNullOrBlank;

/**
 * Manages the directory hierarchy.
 * Provides methods for creating, moving, and deleting directories.
 */
public class FileSystem {

    private final Directory root = new Directory("");
    private final DirectoryLister directoryLister = new DirectoryLister();

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

        String[] parts = path.split("/");
        Directory current = root;

        for (String part : parts) {
            Directory child = current.getChild(part);

            if (child == null) {
                // Create new directory if it doesn't exist
                child = new Directory(part);
                current.addChild(child);
            }

            current = child;
        }

        return true;
    }

    /**
     * Generates a formatted listing of the directory structure.
     *
     * @return a list of strings representing the directory structure
     */
    public List<String> listDirectories() {
        List<String> result = new ArrayList<>();
        for (String dirName : root.getChildrenNames()) {
            Directory dir = root.getChild(dirName);
            result.add(dirName);
            result.addAll(directoryLister.listDirectory(dir, 1));
        }
        return result;
    }

}