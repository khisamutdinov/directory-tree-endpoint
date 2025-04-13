package net.shamansoft.endpoint.directorytree.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InputProcessorTest {

    @Test
    void splitCommandLinesReturnsEmptyListForNullInput() {
        List<String> result = InputProcessor.splitCommandLines(null);
        assertThat(result).isEmpty();
    }

    @Test
    void splitCommandLinesReturnsEmptyListForEmptyInput() {
        List<String> result = InputProcessor.splitCommandLines("");
        assertThat(result).isEmpty();
    }

    @Test
    void splitCommandLinesReturnsEmptyListForWhitespaceOnlyInput() {
        List<String> result = InputProcessor.splitCommandLines("   ");
        assertThat(result).isEmpty();
    }

    @Test
    void splitCommandLinesSplitsSingleLineInput() {
        List<String> result = InputProcessor.splitCommandLines("command1");
        assertThat(result).containsExactly("command1");
    }

    @Test
    void splitCommandLinesSplitsMultiLineInputWithLF() {
        List<String> result = InputProcessor.splitCommandLines("command1\ncommand2\ncommand3");
        assertThat(result).containsExactly("command1", "command2", "command3");
    }

    @Test
    void splitCommandLinesSplitsMultiLineInputWithCRLF() {
        List<String> result = InputProcessor.splitCommandLines("command1\r\ncommand2\r\ncommand3");
        assertThat(result).containsExactly("command1", "command2", "command3");
    }

    @Test
    void splitCommandLinesFiltersOutEmptyLines() {
        List<String> result = InputProcessor.splitCommandLines("command1\n\ncommand2\r\n\r\ncommand3");
        assertThat(result).containsExactly("command1", "command2", "command3");
    }

    @Test
    void splitCommandLinesTrimsWhitespaceAroundLines() {
        List<String> result = InputProcessor.splitCommandLines("  command1  \n\tcommand2\t\n  command3  ");
        assertThat(result).containsExactly("command1", "command2", "command3");
    }
}