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

	private Pattern titlePattern = Pattern.compile(TITLE_PATTERN);;
	private Pattern notePattern = Pattern.compile(NOTE_PATTERN);;
	
	/*
	 * Validate title of note
	 */
	public boolean validTitle(Notes note) throws NoteException {
		Matcher matcher = titlePattern.matcher(note.getTitle());
		if(!matcher.matches()){
			throw new NoteException("Invalid title for Note");
		}
		return true; 
	}
	/*
	 * Validate note description, check length
	 */
	public boolean validNote(Notes note) throws NoteException {
		Matcher matcher = notePattern.matcher(note.getNote());
		if(!matcher.matches()){
			throw new NoteException("Maximum length of Note is 1000");
		}
		return true; 
	}
	
	
}
