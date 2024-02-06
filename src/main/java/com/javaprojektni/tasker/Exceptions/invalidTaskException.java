package com.javaprojektni.tasker.Exceptions;

public class invalidTaskException extends RuntimeException {
    public invalidTaskException() {
    }

    public invalidTaskException(String message) {
        super(message);
    }

    public invalidTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public invalidTaskException(Throwable cause) {
        super(cause);
    }

    public invalidTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
