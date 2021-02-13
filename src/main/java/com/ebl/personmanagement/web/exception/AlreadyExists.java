package com.ebl.personmanagement.web.exception;

public class AlreadyExists extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	public AlreadyExists() {
	}

	public AlreadyExists(String string) {
		super(string);
		this.message=string;
	}

	public String getMessage() {
		return message;
	}

}
