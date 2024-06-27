package com.app.file.upload.exception;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
