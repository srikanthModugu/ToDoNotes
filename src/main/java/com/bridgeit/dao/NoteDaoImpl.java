package com.bridgeit.dao;

import java.util.Iterator;
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
	public void deleteNote(long noteId) {

		Session session = sessionFactory.getCurrentSession();
		Note persistedNote = session.load(Note.class, noteId);
		if (persistedNote != null) {

			session.delete(persistedNote);
		}
	}

	@Override
	public void updateNote(Note updatedNote) {

		String hql = "UPDATE com.bridgeit.model.Note set note_title=:title,note_description=:description,note_cretedDate=:noteCreatedDate,note_modifiedDate=:noteEditedDate,is_archive=:archive,is_trash=:trash,is_pinned=:pinned WHERE noteId = :noteid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("title", updatedNote.getTitle());
		query.setParameter("description", updatedNote.getDescription());
		query.setParameter("noteCreatedDate", updatedNote.getCreatedDate());
		query.setParameter("noteEditedDate", updatedNote.getModifiedDate());
		query.setParameter("archive", updatedNote.isArchive());
		query.setParameter("trash", updatedNote.isTrash());
		query.setParameter("pinned", updatedNote.isPinned());
		query.setParameter("noteid", updatedNote.getNoteId());
		 query.executeUpdate();
		System.out.println("query executed successfully...");
	}

	@Override
	public List<Note> getallNotes(long id) {

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM com.bridgeit.model.Note where userId = :userid ");
		query.setParameter("userid", id);
		List<Note> ls = query.getResultList();
		return ls;
	}

	public Note getNoteById(long noteId) {
		Session session = sessionFactory.openSession();

		return session.load(Note.class, noteId);

	}

	@Override
	public void updateNoteToArchive(long noteId) {
		
		String hql = "UPDATE com.bridgeit.model.Note set is_archive=:archive WHERE noteId = :noteid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("archive",true);
		query.setParameter("noteid",noteId);
		 query.executeUpdate();
	}

	@Override
	public void updateNotePin(long noteId) {
		
		String hql = "UPDATE com.bridgeit.model.Note set  is_pinned=:pin WHERE noteId = :noteid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("pin",true);
		query.setParameter("noteid",noteId);
		query.executeUpdate();
	}
	
	

	@Override
	public void emptyTrash(int userId) {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM com.bridgeit.model.Note where userId = :userid ");
		query.setParameter("userid", userId);
		List<Note> ls = query.getResultList();
		 Iterator itr = ls.iterator();
		 
		 while (itr.hasNext()) {
		  Note note = (Note) itr.next();
		  if(note.isTrash() == true)
		  {
		  session.delete(note);
		  }	
		}
		
	}

	@Override
	public void UpdateNoteToTrash(long noteId) {
		String hql = "UPDATE com.bridgeit.model.Note set  is_trash=:trash WHERE noteId = :noteid";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("trash",true);
		query.setParameter("noteid",noteId);
		query.executeUpdate();
		
	}

}
