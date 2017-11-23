package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.model.Note;


public interface NoteDAO {
	
	public void addNote(Note note);
	public void deleteNote(int noteId);
	public void updateNote(Note updatedNote);
	public List<Note> getallNotes();
	public Note getNoteById(int noteId);

}
