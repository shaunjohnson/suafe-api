package net.lmxm.suafe.api;

import net.lmxm.suafe.api.internal.MessageKey;

public final class EntityAlreadyExistsException extends SuafeApiRuntimeException {
    public EntityAlreadyExistsException(final MessageKey messageKey, final Object... arguments) {
        super(messageKey, arguments);
    }
}
