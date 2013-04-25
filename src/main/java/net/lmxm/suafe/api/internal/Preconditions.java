package net.lmxm.suafe.api.internal;

/**
 * Basic precondition checks, based on the Google Guava Preconditions class.
 */
public final class Preconditions {
    /**
     * Checks a value to determine if it is null or not. If the value is null then a NullPointerException with the
     * provided message text is thrown. Otherwise the value checked is returned to the caller.
     *
     * @param value Value to check for null
     * @param message Message to include in the NullPointerException exception thrown
     * @return Value if not null
     */
    public static <T> T checkNotNull(final T value, final String message) {
        if (value == null) {
            throw new NullPointerException(message);
        }

        return value;
    }

    /**
     * Cannot be instantiated.
     */
    private Preconditions() {
        throw new AssertionError("Cannot be instantiated");
    }
}
