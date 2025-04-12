package net.shamansoft.endpoint.directorytree.model;

import net.shamansoft.endpoint.directorytree.utils.DirectoryLister;
import net.shamansoft.endpoint.directorytree.utils.StringUtils;

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
     * Finds a directory at the specified path.
     *
     * @param path the path to find
     * @return the found directory, or null if not found
     */
    public Directory findDirectory(String path) throws IllegalArgumentException {

        if (isNullOrBlank(path)) {
            return root;
        }

        String[] components = path.split("/");
        Directory current = root;

        for (int i = 0; i < components.length; i++) {

            current = current.getChild(components[i]);

            if (current == null) {
                // path to a directory not found
                String pathToCurrent = StringUtils.constructPath(components, i + 1);
                throw new IllegalArgumentException("%s does not exist".formatted(pathToCurrent));
            }
        }

        return current;
    }

    /**
     * Deletes a directory at the specified path.
     *
     * @param path the path to delete
     * @return true if the directory was deleted, false if it wasn't found
     */
    public boolean deleteDirectory(String path) {
        if (isNullOrBlank(path)) {
            return false;
        }

        int lastSlash = path.lastIndexOf('/');
        if (lastSlash == -1) {
            // Top-level directory
            Directory toDelete = root.getChild(path);
            if (toDelete == null) {
                return false;
            }

            root.removeChild(path);
        } else {
            // Nested directory
            String parentPath = path.substring(0, lastSlash);
            String directoryName = path.substring(lastSlash + 1);
            Directory parent = findDirectory(parentPath);
            if (parent == null) {
                return false;
            }

            Directory toDelete = parent.getChild(directoryName);
            if (toDelete == null) {
                return false;
            }

            parent.removeChild(directoryName);
        }
        return true;
    }

    /**
     * Moves a directory from source path to destination path.
     *
     * @param sourcePath the source path
     * @param destinationPath the destination path
     * @return true if the move was successful, false otherwise
     */
    public boolean moveDirectory(String sourcePath, String destinationPath) {
        if (isNullOrBlank(sourcePath) || isNullOrBlank(destinationPath)) {
            return false;
        }

        Directory sourceDir = findDirectory(sourcePath);
        if (sourceDir == null) {
            return false;
        }

        // Find the destination directory
        Directory destDir = findDirectory(destinationPath);
        if (destDir == null) {
            return false;
        }

        // Check if trying to move to a subdirectory of itself
        Directory checkParent = destDir;
        while (checkParent != null) {
            if (checkParent == sourceDir) {
                return false;  // Cannot move a directory to its own subdirectory
            }
            checkParent = checkParent.getParent();
        }

        // Get the source directory name
        String sourceName = sourceDir.getName();

        // Remove from its parent
        Directory sourceParent = sourceDir.getParent();
        if (sourceParent != null) {
            sourceParent.removeChild(sourceName);
        }

        // Add to the destination
        return destDir.addChild(sourceDir);
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