package com.intellisense.ebookstorev1.store.exception;

public class APPException extends RuntimeException {

    public APPException() {
        super("Failed to perform the requested action");
    }

    public APPException(Throwable cause) {
        super("Failed to perform the requested action", cause);
    }

    public APPException(String message) {
        super(message);
    }

    public APPException(String message, Throwable cause) {
        super(message, cause);
    }
}
