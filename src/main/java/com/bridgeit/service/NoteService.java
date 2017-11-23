package com.bridgeit.service;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgeit.dao.NoteDAO;
import com.bridgeit.model.Note;

public class NoteService {

    @Autowired
	private NoteDAO noteDao;
    
    @Transactional
	public void addNote(Note note) {
	
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
	public List<Note> getallNotes() {
		
		return noteDao.getallNotes();
	}
    
	@Transactional
	public Note getNoteById(int noteId)
	{
		return noteDao.getNoteById(noteId);
	}
	
}
