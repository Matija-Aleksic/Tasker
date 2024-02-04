package com.javaprojektni.tasker.Exceptions;

public class TaskUpdateFailedException extends Exception {
    public TaskUpdateFailedException() {
    }

    public TaskUpdateFailedException(String message) {
        super(message);
    }

    public TaskUpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskUpdateFailedException(Throwable cause) {
        super(cause);
    }

    public TaskUpdateFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}