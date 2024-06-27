package com.app.file.upload.exception;

public class MaxUploadSizeExceededException extends RuntimeException{

	public MaxUploadSizeExceededException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public MaxUploadSizeExceededException(String message) {
		super(message);
		
	}

	
}
