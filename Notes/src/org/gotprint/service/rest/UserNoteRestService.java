package org.gotprint.service.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gotprint.domain.Notes;
import org.gotprint.domain.Users;
import org.gotprint.exceptions.NotesAppBaseException;
import org.gotprint.exceptions.UserException;
import org.gotprint.model.NotesJson;
import org.gotprint.model.NotesWrapper;
import org.gotprint.service.NoteService;
import org.gotprint.service.UserService;
import org.gotprint.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class UserNoteRestService {

	@Autowired
	private NoteService userNoteService;
	
	@Autowired
	private UserService userService;	

	@Autowired
	private Utility utility;
	
	//------------------Add new user -------------------------------------------------------------------------
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody Users users) throws UserException{
		userService.addUser(users);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	//------------------Add Notes of user -------------------------------------------------------------------------
	@RequestMapping(value = "/addNote", method = RequestMethod.POST)
	public ResponseEntity<String> updateUserNotes(@RequestBody NotesWrapper notesWrapper) throws NotesAppBaseException{
		userNoteService.addUserNotes((notesWrapper));
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	//------------------Update Notes of user if exist, otherwise add ------------------------------------------------
	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	public ResponseEntity<String> addUpdateNote(@RequestBody NotesWrapper notesWrapper) throws NotesAppBaseException{
		userNoteService.updateUserNotes((notesWrapper));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	//------------------Delete Notes of user ------------------------------------------------------------------------
	@RequestMapping(value = "/deleteNote", method = RequestMethod.DELETE)
	public ResponseEntity<String>  deleteNotes(@RequestBody Users user) throws NotesAppBaseException{
		userNoteService.deleteNotes( user);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	//------------------Retrieve Notes list for user id -------------------------------------------------------------
	@RequestMapping(value = "/userNotes/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<NotesJson>>  retrieveUserNotes(@PathVariable("userId") String userId) throws NotesAppBaseException{	
		List<Notes>  sorucelist = userNoteService.getNotes(userId);
		List<NotesJson> listOfNotes =  utility.copyNotes(sorucelist);
		return new ResponseEntity<List<NotesJson>>(listOfNotes, HttpStatus.OK);
	}
	
	//------------------Handle exception at this class -------------------------------------------------------------
	@ExceptionHandler(NotesAppBaseException.class)
	public ResponseEntity<String> rulesForUserNotes(HttpServletRequest request, Exception e)  {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	

}
