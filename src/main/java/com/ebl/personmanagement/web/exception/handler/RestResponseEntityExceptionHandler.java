package com.ebl.personmanagement.web.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ebl.personmanagement.web.exception.AlreadyExists;
import com.ebl.personmanagement.web.exception.ErrorResponse;
import com.ebl.personmanagement.web.exception.InvalidInput;
import com.ebl.personmanagement.web.exception.ObjectNotFound;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class })
	protected ResponseEntity<Object> handleConflict(IllegalArgumentException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Illegal Argument.", details);
		return new ResponseEntity(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = { IllegalStateException.class })
	protected ResponseEntity<Object> handleException(IllegalStateException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Illegal state or access.", details);
		return new ResponseEntity(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = { AlreadyExists.class })
	protected ResponseEntity<Object> handleException(AlreadyExists ex, WebRequest request) {
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Entity already exists.", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ObjectNotFound.class })
	protected ResponseEntity<Object> handleException(ObjectNotFound ex, WebRequest request) {
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Entity not found.", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { InvalidInput.class })
	protected ResponseEntity<Object> handleException(InvalidInput ex, WebRequest request) {
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Invalid Input.", details);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server side error.", details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}