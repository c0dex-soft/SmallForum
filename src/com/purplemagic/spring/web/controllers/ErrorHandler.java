package com.purplemagic.spring.web.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(DataAccessException.class)
	public String handleDatabaseException (DataAccessException ex, Model model) {
		ex.printStackTrace();
		return "error";
	}

	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessException (AccessDeniedException ex) {
		ex.printStackTrace();
		return "denied";
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public String handleEmptyResult(EmptyResultDataAccessException ex) {
		ex.printStackTrace();
		return "emptyresult";
	}
	
}
