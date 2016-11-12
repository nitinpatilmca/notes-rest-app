package org.gotprint.exceptions;

public class NotesAppBaseException extends Exception {
	private static final long serialVersionUID = -3972518743278074496L;

	public NotesAppBaseException() {

	}

	public NotesAppBaseException(String messsage) {
		super(messsage);
	}
}
