package net.shamansoft.endpoint.directorytree.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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
}