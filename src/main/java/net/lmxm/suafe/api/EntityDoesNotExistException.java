package net.lmxm.suafe.api;

public final class EntityDoesNotExistException extends RuntimeException {
    public EntityDoesNotExistException(final String message) {
        super(message);
    }
}
