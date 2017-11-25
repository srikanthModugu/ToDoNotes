package com.bridgeit.dao;

import java.util.List;
import com.bridgeit.model.Note;


public interface NoteDAO {
	
	public void addNote(Note note);
	public void deleteNote(long noteId);
	public void updateNote(Note updatedNote);
	public List<Note> getallNotes(long id);
	public Note getNoteById(long noteId);
    public void updateNoteToArchive(long noteId);
    public void updateNotePin(long noteId);
    public void  emptyTrash(int userId);
    public void UpdateNoteToTrash(long noteId);
    
}
