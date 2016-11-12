package org.gotprint.exceptions;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This User is not found in the application")
public class UserException extends NotesAppBaseException {
	private static final long serialVersionUID = 8499293575845977234L;

	public UserException(String messsage) {
		super(messsage);
	}
}
