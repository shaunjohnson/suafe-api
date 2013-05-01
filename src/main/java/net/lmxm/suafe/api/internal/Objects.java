package net.lmxm.suafe.api.internal;

import java.util.Arrays;

/**
 * Shortcut methods for working with objects.
 */
public final class Objects {
    /**
     * Compares two object values to determine if they are equal.
     *
     * @param left Left hand side object
     * @param right Right hand side object
     * @return True if the objects are equal, otherwise false
     */
    public static boolean equal(final Object left, final Object right) {
        return left == right || (left != null && left.equals(right));
    }

    /**
     * Generates hashCode value of two or more objects. Note that this method cannot be used with zero or one objects.
     *
     * @param objects Objects to be processed
     * @return Hash code generated for the array of objects
     */
    public static int hashCode(final Object... objects) {
        if (objects.length < 2) {
            throw new IllegalArgumentException("hashCode may not be used with less than 2 objects");
        }

        return Arrays.hashCode(objects);
    }

    /**
     * Prevent instantiation.
     */
    private Objects() {
        throw new AssertionError("Cannot be instantiated");
    }
}
