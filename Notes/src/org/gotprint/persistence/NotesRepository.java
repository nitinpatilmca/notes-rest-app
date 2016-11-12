package org.gotprint.persistence;

import java.util.List;

import org.gotprint.domain.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {

	@Query("delete FROM Notes notes WHERE notes.users.username = ?1")
	public void deleteNotesOfUser(String userId);
	
	@Query("select notes FROM Notes notes WHERE notes.users.username = ?1")
	public List<Notes> getNotesByUserId(String userId);
	
}
