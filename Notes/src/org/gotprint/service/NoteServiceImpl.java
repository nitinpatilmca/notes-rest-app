package org.gotprint.service;

import java.util.ArrayList;
import java.util.List;

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
			noteValidator.validTitle(note);
			noteValidator.validNote(note);
		}
		List<Notes> persistenUserNotes = notesRepository.getNotesByUserId(notesWrapper.getUserId());
		for(Notes note : userNotes){
			int noteIndex = persistenUserNotes.indexOf(note);
			if(noteIndex != -1){
				Notes persistenNote = persistenUserNotes.get(noteIndex);
				note.setNoteId(persistenNote.getNoteId());
				note.setLastUpdateOn(dateUtility.getCurrentDate());
			}
			note.setLastUpdateOn(dateUtility.getCurrentDate());
			note.setUsers(user);
			
		}
		notesRepository.save(userNotes);
	}

	@Override
	@Transactional
	public void deleteNotes(Users userNoteToBeDelete) throws UserException {
		Users user = getUser(userNoteToBeDelete.getUsername());
		notesRepository.delete(user.getNotes());
	}

	@Override
	public List<Notes> getNotes(Users userNotes) throws UserException {
		Users user = getUser(userNotes.getUsername());
		return user != null ? user.getNotes() : new ArrayList<Notes>();
	}
	
	
	private Users getUser(String userId) throws UserException {
		Users user = userDetailsRepository.findOne(userId);
		if( user == null){
			throw new UserException("No User found for ID - " + userId);
		}
		return user;
	}


	
	

	
/*	@RequestMapping(value="/addNote", method = RequestMethod.POST, produces = "application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")	
	public String addNote(@RequestBody List<Notes> userNotes) throws JSONException{
		
		return "";
	}
	
	@RequestMapping(value="/user", method = RequestMethod.POST, produces = "application/json; charset=UTF-8", consumes="application/json; charset=UTF-8")	
	public String getSiteList() throws JSONException{
		JSONArray sitesJosn = new JSONArray();
		JSONObject u1 = new JSONObject();
		JSONObject u2= new JSONObject();
		
		u1.put("id", "1");
		u2.put("id", "2");
		sitesJosn.put(u1);
		sitesJosn.put(u2);
		
		return sitesJosn.toString();
	}
	
	
	 private static final String template = "Hello, %s!";
	@RequestMapping(value="/get")
	public List<Greeting>  getSiteListS(HttpServletRequest request) throws JSONException{
		
		System.out.println(request.isSecure()+"         ");
		
		List glist = new ArrayList<>();
		Greeting g1 = new Greeting(1, "Heloo");
		Greeting g2 = new Greeting(2, "333Heloo");
		glist.add(g1);
		glist.add(g2);
		//return new Greeting(10, String.format(template, "Nitin"));
		return glist;
	}
*/



}
