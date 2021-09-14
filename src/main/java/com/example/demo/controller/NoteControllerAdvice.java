package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.exceptions.NoSuchNoteException;

@ControllerAdvice
public class NoteControllerAdvice {

	@ResponseBody
	@ExceptionHandler(NoSuchNoteException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String noteNotFoundHandler(NoSuchNoteException ex) {
		return ex.getMessage();
	}
}
