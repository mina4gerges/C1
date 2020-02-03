package com.yaps.petstore.exception;

/**
 * This exception is thrown when some checking validation error is found.
 */
public final class CheckException extends ApplicationException {

    public CheckException(final String message) {
        super(message);
    }
}
