package net.shamansoft.endpoint.directorytree.utils;

import net.shamansoft.endpoint.directorytree.model.Directory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(rootDirectory.getChildrenNames()).thenReturn(Set.of());
        when(rootDirectory.hasChildren()).thenReturn(false);

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertTrue(result.isEmpty());
    }

    @Test
    void listDirectoryHandlesSingleLevelDirectory() {
        when(rootDirectory.getChildrenNames()).thenReturn(Set.of("child1", "child2"));
        when(rootDirectory.getChild("child1")).thenReturn(childDirectory1);
        when(rootDirectory.getChild("child2")).thenReturn(childDirectory2);
        when(childDirectory1.hasChildren()).thenReturn(false);
        when(childDirectory2.hasChildren()).thenReturn(false);

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertEquals(List.of("child1", "child2"), result);
    }

    @Test
    void listDirectoryHandlesNestedDirectories() {
        when(rootDirectory.getChildrenNames()).thenReturn(Set.of("child1"));
        when(rootDirectory.getChild("child1")).thenReturn(childDirectory1);
        when(childDirectory1.hasChildren()).thenReturn(true);
        when(childDirectory1.getChildrenNames()).thenReturn(Set.of("child2"));
        when(childDirectory1.getChild("child2")).thenReturn(childDirectory2);
        when(childDirectory2.hasChildren()).thenReturn(false);

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertEquals(List.of("child1", "  child2"), result);
    }

    @Test
    void listDirectoryHandlesDeeplyNestedDirectories() {
        when(rootDirectory.getChildrenNames()).thenReturn(Set.of("child1"));
        when(rootDirectory.getChild("child1")).thenReturn(childDirectory1);
        when(childDirectory1.hasChildren()).thenReturn(true);
        when(childDirectory1.getChildrenNames()).thenReturn(Set.of("child2"));
        when(childDirectory1.getChild("child2")).thenReturn(childDirectory2);
        when(childDirectory2.hasChildren()).thenReturn(true);
        when(childDirectory2.getChildrenNames()).thenReturn(Set.of("child3"));
        Directory childDirectory3 = mock(Directory.class);
        when(childDirectory2.getChild("child3")).thenReturn(childDirectory3);
        when(childDirectory3.hasChildren()).thenReturn(false);

        DirectoryLister directoryLister = new DirectoryLister();
        List<String> result = directoryLister.listDirectory(rootDirectory, 0);

        assertEquals(List.of("child1", "  child2", "    child3"), result);
    }
}