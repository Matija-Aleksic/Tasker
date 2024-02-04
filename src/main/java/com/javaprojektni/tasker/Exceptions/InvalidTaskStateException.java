package com.javaprojektni.tasker.Exceptions;

public class InvalidTaskStateException extends RuntimeException {

    public InvalidTaskStateException() {
    }

    public InvalidTaskStateException(String message) {
        super(message);
    }

    public InvalidTaskStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTaskStateException(Throwable cause) {
        super(cause);
    }

    public InvalidTaskStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}