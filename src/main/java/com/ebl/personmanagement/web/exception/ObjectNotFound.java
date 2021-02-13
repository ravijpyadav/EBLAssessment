package com.ebl.personmanagement.web.exception;

public class ObjectNotFound extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	public ObjectNotFound() {
	}

	public ObjectNotFound(String string) {
		super(string);
		this.message=string;
	}

	public String getMessage() {
		return message;
	}

}
