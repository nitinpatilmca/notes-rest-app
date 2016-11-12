package org.gotprint.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * The persistent class for the note database table.
 * 
 */
@Entity
@Table(name = "NOTES", schema="gotprint")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Notes implements Serializable, Comparator<Notes> {
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name="NOTE_ID")
	private Integer noteId;

	//@NotBlank
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATE_ON")
	private Date lastUpdateOn;

	private String note;

	private String title;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID")
	private Users users;

	public Notes(String title) {
		this.title = title;
	}

	@Override
	public int compare(Notes o1, Notes o2) {
		return o1.getTitle().compareTo(o2.getTitle());
	}
	
	@Override
	public int hashCode() {

        final int seed = 37;
        int result = 1;
        result = seed * result + ((title == null) ? 0 : title.hashCode());
       
        return result;

	}
	
	@Override
	public boolean equals(Object obj) {
		 if(obj == this){
			 return true;
		 }
		 
		 if(obj == null || obj.getClass() != this.getClass()){
			 return false;
		 }
		 Notes note = (Notes) obj;
		 
		 return this.title == note.title && (this.title == note.getTitle() || this.title.equals(note.getTitle()));
	}
	

	public Notes() {
	}
	
	public Integer getNoteId() {
		return this.noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public Date getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getLastUpdateOn() {
		return this.lastUpdateOn;
	}

	public void setLastUpdateOn(Date lastUpdateOn) {
		this.lastUpdateOn = lastUpdateOn;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Users getUsers() {
		return users;
	}
	
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public static void main(String[] args) {
		
		Notes n1 = new Notes("one");
		Notes n2 = new Notes("two");
		
		Notes n3 = new Notes("one");
		Notes n4 = new Notes("two4");
	
		List<Notes> list = new ArrayList<>();
		System.out.println(n1.hashCode());
		System.out.println(n2.hashCode());
		list.add(n1);
		list.add(n2);
		
		System.out.println(list.indexOf(n3));
		
		
	}

}