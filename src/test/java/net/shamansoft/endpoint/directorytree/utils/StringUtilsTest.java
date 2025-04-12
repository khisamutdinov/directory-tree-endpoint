package net.shamansoft.endpoint.directorytree.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringUtilsTest {

    @Test
    void constructPathReturnsEmptyStringWhenComponentsArrayIsEmpty() {
        String[] components = {};
        String result = StringUtils.constructPath(components, 0);
        assertThat(result).isEqualTo("");
    }

    @Test
    void constructPathReturnsCorrectPathFor1() {
        String[] components = {"home", "user", "documents"};
        String result = StringUtils.constructPath(components, 1);
        assertThat(result).isEqualTo("home");
    }

    @Test
    void constructPathReturnsCorrectPathFor2() {
        String[] components = {"home", "user", "documents"};
        String result = StringUtils.constructPath(components, 2);
        assertThat(result).isEqualTo("home/user");
    }

    @Test
    void constructPathReturnsCorrectPathFor3() {
        String[] components = {"home", "user", "documents"};
        String result = StringUtils.constructPath(components, 3);
        assertThat(result).isEqualTo("home/user/documents");
    }

    @Test
    void constructPathReturnsEmptyStringWhenIndexIsZero() {
        String[] components = {"home", "user", "documents"};
        String result = StringUtils.constructPath(components, 0);
        assertThat(result).isEqualTo("");
    }

    @Test
    void constructPathThrowsExceptionWhenIndexIsNegative() {
        String[] components = {"home", "user", "documents"};
        assertThatThrownBy(() -> StringUtils.constructPath(components, -1))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessageContaining("Index -1 out of bounds for length 3");
    }

    @Test
    void constructPathThrowsExceptionWhenIndexExceedsArrayLength() {
        String[] components = {"home", "user", "documents"};
        assertThat(StringUtils.constructPath(components, 5)).isEqualTo("home/user/documents");
    }
}