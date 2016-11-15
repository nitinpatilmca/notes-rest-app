package org.gotprint.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gotprint.domain.Notes;
import org.gotprint.domain.Users;
import org.gotprint.exceptions.NotesAppBaseException;
import org.gotprint.exceptions.UserException;
import org.gotprint.model.NotesWrapper;
import org.gotprint.persistence.NotesRepository;
import org.gotprint.persistence.UserDetailsRepository;
import org.gotprint.utils.DateUtility;
import org.gotprint.utils.NoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NotesRepository notesRepository;

	@Autowired
	private NoteValidator noteValidator;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private DateUtility dateUtility;
	
	@Override
	@Transactional
	public void addUserNotes(NotesWrapper notesWrapper) throws NotesAppBaseException {
		Users user = getUser(notesWrapper.getUserId());		
		List<Notes> userNotes = notesWrapper.getNotes();
		for(Notes note : userNotes){
			noteValidator.validTitle(note);
			noteValidator.validNote(note);
			note.setLastUpdateOn(dateUtility.getCurrentDate());
			note.setUsers(user);
			
		}
		notesRepository.save(userNotes);
	}
	
	@Override
	@Transactional
	public void updateUserNotes(NotesWrapper notesWrapper) throws NotesAppBaseException  {
		Users user = getUser(notesWrapper.getUserId());
		
		List<Notes> userNotes = notesWrapper.getNotes();
		for(Notes note : userNotes){
			noteValidator.validTitle(note);	// Validate first
			noteValidator.validNote(note);
			//System.out.println("Input : "+note.hashCode());
		}
		List<Notes> persistenUserNotes = notesRepository.getNotesByUserId(notesWrapper.getUserId());
		Map<String, Notes> persistenUserNotesMapping = new HashMap<String, Notes>();

		for(Notes note : persistenUserNotes){
			persistenUserNotesMapping.put(note.getTitle(), note);
		}
		
		for(Notes note : userNotes){
			Notes exisitingNote = persistenUserNotesMapping.get(note.getTitle());
			if(exisitingNote != null){
				note.setNoteId(exisitingNote.getNoteId());			
			}
			note.setLastUpdateOn(dateUtility.getCurrentDate());
			note.setUsers(user);
		}
		persistenUserNotesMapping = null;
		notesRepository.save(userNotes);
	}

	@Override
	@Transactional
	public void deleteNotes(Users userNoteToBeDelete) throws UserException {
		Users user = getUser(userNoteToBeDelete.getUsername());
		notesRepository.delete(user.getNotes());
	}

	@Override
	public List<Notes> getNotes(String userId) throws UserException {
		Users user = getUser(userId);
		return user != null ? user.getNotes() : new ArrayList<Notes>();
	}
	
	
	private Users getUser(String userId) throws UserException {
		Users user = userDetailsRepository.findOne(userId);
		if( user == null){
			throw new UserException("No User found for ID - " + userId);
		}
		return user;
	}




}