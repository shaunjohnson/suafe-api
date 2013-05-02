package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.MessageKey;

public final class InvalidEntityNameException extends SuafeApiRuntimeException {
    public InvalidEntityNameException(final MessageKey messageKey, final Object... arguments) {
        super(messageKey, arguments);
    }
}
