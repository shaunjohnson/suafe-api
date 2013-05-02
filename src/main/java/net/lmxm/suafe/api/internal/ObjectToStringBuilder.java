package net.lmxm.suafe.api.internal;

import static net.lmxm.suafe.api.internal.DocumentPreconditions.checkArgumentNotNull;

/**
 * Helper method used to build a toString() value.
 */
public final class ObjectToStringBuilder {
    /**
     * String builder used to build the toString value.
     */
    private final StringBuilder builder;

    /**
     * Indicates if a field separator should be outputted.
     */
    private boolean addFieldSeparator = false;

    /**
     * Constructs a new instance for the provided class.
     *
     * @param theClass The class for which a toString value is being built.
     */
    public ObjectToStringBuilder(final Class theClass) {
        checkArgumentNotNull(theClass, "Class");

        builder = new StringBuilder("[").append(theClass.getSimpleName()).append(": ");
    }

    /**
     * Appends a new field name/value pair to the toString value.
     *
     * @param fieldName Name of the field being outputted
     * @param fieldValue Value of the field to output
     * @return This builder instance
     */
    public ObjectToStringBuilder append(final String fieldName, final Object fieldValue) {
        checkArgumentNotNull(fieldName, "Field name");

        if (addFieldSeparator) {
            builder.append(", ");
        }
        else {
            addFieldSeparator = true;
        }

        builder.append(fieldName).append("=").append(fieldValue == null ? "<null>" : fieldValue);

        return this;
    }

    /**
     * Builds and returns the complete toString value.
     *
     * @return Generated toString value
     */
    public String build() {
        return builder.toString() + "]";
    }
}
