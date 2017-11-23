package com.bridgeit.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.model.Note;

public class NoteDaoImpl implements NoteDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addNote(Note note) {

		Session session = sessionFactory.getCurrentSession();
		session.save(note);
	}

	@Override
	public void deleteNote(int noteId) {

		Session session = sessionFactory.getCurrentSession();
		Note persistedNote = session.load(Note.class, noteId);
		if (persistedNote != null) {

			session.delete(persistedNote);
		}
	}

	@Override
	public void updateNote(Note updatedNote) {

		String hql = "UPDATE com.bridgeit.model.Note set note_title=:title,note_description=:description,note_cretedDate=:noteCreatedDate,note_modifiedDate=:noteEditedDate WHERE noteId = :noteid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("title", updatedNote.getTitle());
		query.setParameter("description", updatedNote.getDescription());
		query.setParameter("noteCreatedDate", updatedNote.getCreatedDate());
		query.setParameter("noteEditedDate", updatedNote.getModifiedDate());
		query.setParameter("noteid", updatedNote.getNoteId());

		int result = query.executeUpdate();
		System.out.println("query executed successfully...");
	}

	@Override
	public List<Note> getallNotes() {

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM com.bridgeit.model.Note ");
		List<Note> ls = query.getResultList();
		return ls;
	}

	public Note getNoteById(int noteId) {
		Session session = sessionFactory.openSession();

		return session.load(Note.class, noteId);

	}

}
