package net.shamansoft.endpoint.directorytree.utils;

import java.util.Arrays;

public class StringUtils {

    public static boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }

    public static String constructPath(String[] components, int count) {
        if(count < 0) {
            throw new ArrayIndexOutOfBoundsException("Index " + count + " out of bounds for length " + components.length);
        }
        return String.join("/", count >= components.length
                ? components
                : Arrays.copyOf(components, count));
    }
}
