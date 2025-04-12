package net.shamansoft.endpoint.directorytree.utils;

import net.shamansoft.endpoint.directorytree.model.Directory;

import java.util.ArrayList;
import java.util.List;

public class DirectoryLister {

    public static final String INDENT = "  ";

    /**
     * Helper method to recursively append directory listings with proper indentation.
     *
     * @param directory the directory to list
     * @param depth the current depth for indentation
     */
    public List<String> listDirectory(Directory directory, int depth) {
        String indent = INDENT.repeat(depth);

        List<String> result = new ArrayList<>();

        for (String childName : directory.getChildrenNames()) {
            Directory child = directory.getChild(childName);
            result.add(indent + childName);

            if (child.hasChildren()) {
                result.addAll(listDirectory(child, depth + 1));
            }
        }
        return result;
    }
}
