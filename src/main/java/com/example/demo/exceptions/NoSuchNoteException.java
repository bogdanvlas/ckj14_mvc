package com.example.demo.exceptions;

public class NoSuchNoteException extends RuntimeException {
	public NoSuchNoteException(int id) {
		super("Note with id " + id + " doesn't exist");
	}
}
