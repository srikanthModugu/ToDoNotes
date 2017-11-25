package com.bridgeit.service;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgeit.dao.NoteDAO;
import com.bridgeit.model.Note;
import com.bridgeit.model.User;

public class NoteService  {

    @Autowired
	private NoteDAO noteDao;
    
    @Transactional
	public void addNote(Note note,int id) {
	    User user = new User();
	    user.setUserId(id);
	    note.setUser(user);
    	Date toDay= new Date();
    	note.setCreatedDate(toDay);
        note.setModifiedDate(toDay);
		noteDao.addNote(note);
	}

	@Transactional
	public void deleteNote(int noteId) {
		
		noteDao.deleteNote(noteId);
	}

	@Transactional
	public void updateNote(Note updatedNote) {
		  
        Note persistedNote = noteDao.getNoteById(updatedNote.getNoteId());
		Date toDay= new Date();
		updatedNote.setModifiedDate(toDay);
		updatedNote.setCreatedDate(persistedNote.getCreatedDate());
		noteDao.updateNote(updatedNote);
		
	}

	@Transactional
	public List<Note> getallNotes(int id) {
		
		return noteDao.getallNotes(id);
	}
    
	@Transactional
	public Note getNoteById(int noteId)
	{
		return noteDao.getNoteById(noteId);
	}

	
	@Transactional
	public void updateNoteToArchive(long noteId) {
		
		noteDao.updateNoteToArchive(noteId);
	}

	@Transactional
	public void updateNotePin(long noteId) {
	 noteDao.updateNotePin(noteId);
		
	}

	@Transactional
	public void emptyTrash(int userId) {
		
		noteDao.emptyTrash(userId);
	}

	@Transactional
	public void UpdateNoteToTrash(long noteId) {
		
		noteDao.UpdateNoteToTrash(noteId);
		
	}
	
}
