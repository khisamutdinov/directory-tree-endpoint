package net.shamansoft.endpoint.directorytree.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PathValidatorTest {

    @Test
    void validatePathReturnsErrorForEmptyPath() {
        PathValidator pathValidator = new PathValidator();
        List<String> result = pathValidator.validatePath("");
        assertThat(result).containsExactly("Path cannot be empty");
    }

    @Test
    void validatePathReturnsErrorForNullPath() {
        PathValidator pathValidator = new PathValidator();
        List<String> result = pathValidator.validatePath(null);
        assertThat(result).containsExactly("Path cannot be empty");
    }

    @Test
    void validatePathReturnsErrorForPathExceedingMaxLength() {
        PathValidator pathValidator = new PathValidator();
        String longPath = "a".repeat(501);
        List<String> result = pathValidator.validatePath(longPath);
        assertThat(result).containsExactly("Path is too long (maximum 500 characters)");
    }

    @Test
    void validatePathReturnsErrorForPathStartingWithSlash() {
        PathValidator pathValidator = new PathValidator();
        List<String> result = pathValidator.validatePath("/invalidPath");
        assertThat(result).containsExactly("Path cannot start or end with '/'");
    }

    @Test
    void validatePathReturnsErrorForPathEndingWithSlash() {
        PathValidator pathValidator = new PathValidator();
        List<String> result = pathValidator.validatePath("invalidPath/");
        assertThat(result).containsExactly("Path cannot start or end with '/'");
    }

    @Test
    void validatePathReturnsErrorForPathWithConsecutiveSlashes() {
        PathValidator pathValidator = new PathValidator();
        List<String> result = pathValidator.validatePath("invalid//path");
        assertThat(result).containsExactly("Path cannot contain consecutive '/' characters");
    }

    @Test
    void validatePathReturnsMultipleErrorsForInvalidPath() {
        PathValidator pathValidator = new PathValidator();
        String invalidPath = "/invalid//path/";
        List<String> result = pathValidator.validatePath(invalidPath);
        assertThat(result).containsExactlyInAnyOrder(
                "Path cannot start or end with '/'",
                "Path cannot contain consecutive '/' characters"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "validPath",
            "valid/path",
            "valid_path",
            "valid-path",
            "valid.path"
    })
    void validatePathReturnsNoErrorsForValidPath(String path) {
        PathValidator pathValidator = new PathValidator();
        List<String> result = pathValidator.validatePath(path);
        assertThat(result).isEmpty();
    }
}