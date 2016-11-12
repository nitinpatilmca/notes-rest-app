package org.gotprint.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gotprint.domain.Notes;
import org.gotprint.exceptions.NoteException;
import org.springframework.stereotype.Component;

@Component
public class NoteValidator {
	
	private static final String TITLE_PATTERN =   "^[_a-zA-Z0-9-\\s\\.]{1,50}$";
	private static final String NOTE_PATTERN =   "^[_a-zA-Z0-9-\\s\\.]{0,1000}$";
	//\\.[_A-Za-z0-9-]+
	private Pattern titlePattern = Pattern.compile(TITLE_PATTERN);;
	private Pattern notePattern = Pattern.compile(NOTE_PATTERN);;
	
	/*public boolean validTitle(final String title) {
		matcher = titlePattern.matcher(title);
		return matcher.matches();
	}

	public  boolean validNote(final String note) {
		matcher = notePattern.matcher(note);
		return matcher.matches();
	}*/
	

	public boolean validTitle(Notes note) throws NoteException {
		Matcher matcher = titlePattern.matcher(note.getTitle());
		if(!matcher.matches()){
			throw new NoteException("Invalid title for Note");
		}
		return true; 
	}
	public boolean validNote(Notes note) throws NoteException {
		Matcher matcher = notePattern.matcher(note.getNote());
		if(!matcher.matches()){
			throw new NoteException("Maximum length of Note is 1000");
		}
		return true; 
	}
	
	
}
