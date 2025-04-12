package net.shamansoft.endpoint.directorytree.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a directory in the file system.
 * Each directory can have multiple children directories.
 */
@Data
@RequiredArgsConstructor
public class Directory {
    private final String name;
    private Directory parent;
    private final Map<String, Directory> children = new HashMap<>();

    /**
     * Adds a child directory.
     *
     * @param directory the directory to add as a child
     * @return true if the directory was added, false if it already existed
     */
    public boolean addChild(Directory directory) {
        if (children.containsKey(directory.getName())) {
            return false;
        }

        children.put(directory.getName(), directory);
        directory.setParent(this);
        return true;
    }

    /**
     * Removes a child directory.
     *
     * @param name the name of the directory to remove
     * @return the removed directory, or null if it wasn't found
     */
    public Directory removeChild(String name) {
        Directory removed = children.remove(name);
        if (removed != null) {
            removed.setParent(null);
        }
        return removed;
    }

    /**
     * Returns a child directory by name.
     *
     * @param name the name of the child directory
     * @return the child directory, or null if not found
     */
    public Directory getChild(String name) {
        return children.get(name);
    }

    /**
     * Returns all children names.
     *
     * @return a set of all children names
     */
    public Set<String> getChildrenNames() {
        return children.keySet();
    }

    /**
     * Checks if this directory has any children.
     *
     * @return true if this directory has children, false otherwise
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }
}