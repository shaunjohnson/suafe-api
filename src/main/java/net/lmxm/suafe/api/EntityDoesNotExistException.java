package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.MessageKey;

public final class EntityDoesNotExistException extends SuafeApiRuntimeException {
    public EntityDoesNotExistException(final MessageKey messageKey, final Object... arguments) {
        super(messageKey, arguments);
    }
}
