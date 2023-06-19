package com.RP.user.service.exception;

public class ResourceNotFound extends RuntimeException {

	public ResourceNotFound() {
		super("Resourse Not Found !!");
	}
	
	public ResourceNotFound(String message) {
		super(message);
	}
	
}
