package net.shamansoft.endpoint.directorytree.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileSystemTest {


    @Test
    void listDirectoriesReturnsEmptyListWhenRootHasNoChildren() {
        FileSystem fileSystem = new FileSystem();
        List<String> result = fileSystem.listDirectories();
        assertThat(result).isEmpty();
    }

    @Test
    void listDirectoriesReturnsSingleDirectoryWhenRootHasOneChild() {
        FileSystem fileSystem = new FileSystem();
        fileSystem.createDirectory("child");
        List<String> result = fileSystem.listDirectories();
        assertThat(result).containsExactly("child");
    }

    @Test
    void listDirectoriesReturnsNestedDirectoriesInCorrectOrder() {
        FileSystem fileSystem = new FileSystem();
        fileSystem.createDirectory("parent/child");
        List<String> result = fileSystem.listDirectories();
        assertThat(result).containsExactly("parent", "  child");
    }

    @Test
    void listDirectoriesHandlesMultipleTopLevelDirectories() {
        FileSystem fileSystem = new FileSystem();
        fileSystem.createDirectory("dir1");
        fileSystem.createDirectory("dir2");
        List<String> result = fileSystem.listDirectories();
        assertThat(result).containsExactly("dir1", "dir2");
    }

    @Test
    void listDirectoriesHandlesDeeplyNestedDirectories() {
        FileSystem fileSystem = new FileSystem();
        fileSystem.createDirectory("a/b/c/d");
        List<String> result = fileSystem.listDirectories();
        assertThat(result).containsExactly("a", "  b", "    c", "      d");
    }
}