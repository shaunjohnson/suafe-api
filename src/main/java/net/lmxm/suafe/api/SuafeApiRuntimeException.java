package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.MessageKey;
import net.lmxm.suafe.api.internal.MessageResources;

/**
 * A Suafe API runtime exception.
 */
public abstract class SuafeApiRuntimeException extends RuntimeException {
    public SuafeApiRuntimeException(final MessageKey messageKey, final Object... arguments) {
        super(MessageResources.get(messageKey, arguments));
    }
}
