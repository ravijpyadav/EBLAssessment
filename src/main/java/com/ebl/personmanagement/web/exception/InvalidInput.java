package com.ebl.personmanagement.web.exception;

public class InvalidInput extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	public InvalidInput() {
	}

	public InvalidInput(String string) {
		super(string);
		this.message=string;
	}

	public String getMessage() {
		return message;
	}
}
