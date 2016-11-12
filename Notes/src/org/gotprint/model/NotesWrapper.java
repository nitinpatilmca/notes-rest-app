package org.gotprint.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.gotprint.domain.Notes;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NotesWrapper {
	
	private String userId;
	private List<Notes> notes;
	
	
	public NotesWrapper() {
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<Notes> getNotes() {
		return notes;
	}
	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}
}
