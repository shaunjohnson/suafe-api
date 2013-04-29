package net.lmxm.suafe.api.internal;

import java.text.MessageFormat;
import java.util.*;

import static net.lmxm.suafe.api.internal.Preconditions.checkNotNull;

/**
 * Message resource loader, which is responsible for loading resource text from the resource bundle.
 */
public final class MessageResources {

    /**
     * Static reference to the resource bundle.
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("net/lmxm/suafe/api/messages");

    /**
     * Gets a string, formatted if appliable.
     *
     * @param messageKey Message key of the resource to load
     * @param arguments Message format arguments, if needed
     * @return Resource string, formatted if spplicable
     */
    public static String get(final MessageKey messageKey, final Object... arguments) {
        checkNotNull(messageKey, "Message key must not be null");

        String value = null;

        try {
            value = resourceBundle.getString(messageKey.name());
        }
        catch (final MissingResourceException e) {
            // LOGGER.error("Unable to locate resource with key \"" + key + "\"");
            throw new RuntimeException("Unable to locate resource with key \"" + messageKey + "\"");
        }

        return arguments == null ? value : MessageFormat.format(value, arguments);
    }

    /**
     * Gets all resource bundle keys as a set.
     *
     * @return Set of resource bundle keys
     */
    public static Set<String> getKeys() {
        final Set<String> keys = new HashSet<String>();

        final Enumeration<String> keysEnumeration = resourceBundle.getKeys();
        while (keysEnumeration.hasMoreElements()) {
            keys.add(keysEnumeration.nextElement());
        }

        return Collections.unmodifiableSet(keys);
    }
}
