package org.gotprint.service;

import java.util.List;

import org.gotprint.domain.Notes;
import org.gotprint.domain.Users;
import org.gotprint.exceptions.NotesAppBaseException;
import org.gotprint.model.NotesWrapper;

public interface NoteService {

	public void addUserNotes(NotesWrapper notesWrapper) throws NotesAppBaseException;

	public void updateUserNotes(NotesWrapper notesWrapper) throws NotesAppBaseException;

	public void deleteNotes(Users user) throws NotesAppBaseException;

	public List<Notes> getNotes(String userId) throws NotesAppBaseException;;

}
