package com.yaps.petstore.exception;

/**
 * This exception is thrown when an object cannot be deleted.
 */
public final class RemoveException extends ApplicationException {

    public RemoveException(final String message) {
        super(message);
    }
}
