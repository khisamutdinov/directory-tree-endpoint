package net.shamansoft.endpoint.directorytree.utils;

import net.shamansoft.endpoint.directorytree.model.Directory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DirectoryListerTest {

    private Directory rootDirectory;
    private Directory childDirectory1;
    private Directory childDirectory2;

    @BeforeEach
    void setUp() {
        rootDirectory = mock(Directory.class);
        childDirectory1 = mock(Directory.class);
        childDirectory2 = mock(Directory.class);
    }

    @Test
    void listDirectoryReturnsEmptyListForEmptyDirectory() {
        when(rootDirectory.getName()).thenReturn("root");
        when(rootDirectory.hasChildren()).thenReturn(false);

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory);

        assertThat(result).isEmpty();
    }

    @Test
    void listDirectoryReturnsRootNameWhenDepthIsZero() {
        when(rootDirectory.getName()).thenReturn("root");
        when(rootDirectory.hasChildren()).thenReturn(false);

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertThat(result).containsExactly("root");
    }

    @Test
    void listDirectoryHandlesDirectoryWithNoChildren() {
        when(rootDirectory.getName()).thenReturn("root");
        when(rootDirectory.getChildren()).thenReturn(Map.of());

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertThat(result).containsExactly("root");
    }

    @Test
    void listDirectoryHandlesDirectoryWithMultipleChildren() {

        Directory child3 = mock(Directory.class);
        when(child3.getName()).thenReturn("child3");
        when(child3.getChildren()).thenReturn(Map.of());

        Directory child4 = mock(Directory.class);
        when(child4.getName()).thenReturn("child4");
        when(child4.getChildren()).thenReturn(Map.of());


        when(rootDirectory.getName()).thenReturn("root");
        when(rootDirectory.getChildren()).thenReturn(Map.of(
                "child3", child3,
                "child4", child4,
                "child1", childDirectory1,
                "child2", childDirectory2));
        when(childDirectory1.getName()).thenReturn("child1");
        when(childDirectory1.getChildren()).thenReturn(Map.of());
        when(childDirectory2.getName()).thenReturn("child2");
        when(childDirectory2.getChildren()).thenReturn(Map.of());

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertThat(result).containsExactly("root", "  child1", "  child2", "  child3", "  child4");
    }

    @Test
    void listDirectoryHandlesDeeplyNestedDirectories() {
        when(rootDirectory.getName()).thenReturn("root");
        when(rootDirectory.getChildren()).thenReturn(Map.of("child1", childDirectory1));
        when(childDirectory1.getName()).thenReturn("child1");
        when(childDirectory1.getChildren()).thenReturn(Map.of("child2", childDirectory2));
        when(childDirectory2.getName()).thenReturn("child2");
        when(childDirectory2.getChildren()).thenReturn(Map.of());

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertThat(result).containsExactly("root", "  child1", "    child2");
    }

    @Test
    void listDirectoryHandlesNullDirectoryGracefully() {
        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(null, 0);

        assertThat(result).isEmpty();
    }

}