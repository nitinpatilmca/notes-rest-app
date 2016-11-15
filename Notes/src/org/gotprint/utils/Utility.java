package org.gotprint.utils;

import java.util.ArrayList;
import java.util.List;

import org.gotprint.domain.Notes;
import org.gotprint.model.NotesJson;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	/*
	 * This method is to return the List of Notes required to display in json format.
	 * @List<Notes> sourceNotes - list return by  Persistent bag a specific collection of Hibernet/JPA implementation 
	 */
	public List<NotesJson> copyNotes(List<Notes> sourceNotes){
		
		List<NotesJson> notesList = new ArrayList<NotesJson>();
		if(sourceNotes != null && sourceNotes.size() > 0){
			for(Notes note : sourceNotes){
				NotesJson json = new NotesJson();
				json.setTitle(note.getTitle());
				json.setNote(note.getNote());
				json.setLastUpdateOn(note.getLastUpdateOn().toString());
				notesList.add(json);
			}
		}
		return notesList;
	}
	
		
}
