package net.shamansoft.endpoint.directorytree.utils;

import net.shamansoft.endpoint.directorytree.model.Directory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DirectoryLister {

    public static final String INDENT = "  ";

    /**
     * Generate a list of children directories with proper indentation
     *
     * @param root the root to list
     */
    public List<String> listDirectory(Directory root) {
        return listDirectory(root, -1);
    }

    List<String> listDirectory(Directory directory, int depth) {
        return listDirectory(directory, depth, new ArrayList<>());
    }

    private List<String> listDirectory(Directory directory, int depth, List<String> result) {
        if (directory == null) {
            return List.of();
        }
        if (depth >= 0) {
            result.add(INDENT.repeat(depth) + directory.getName());
        }

        var sortedValues = directory.getChildren().values().stream()
                .sorted(Comparator.comparing(Directory::getName)).toList();

        for (Directory child : sortedValues) {
            listDirectory(child, depth + 1, result);
        }
        return result;
    }
}
