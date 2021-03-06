package com.bridgeit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.model.CustomeResponse;
import com.bridgeit.model.Note;
import com.bridgeit.service.NoteService;

@RestController
@RequestMapping(value="/user")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/addNote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> addNote(@RequestBody Note note, HttpServletRequest request) {
         
		CustomeResponse myResponse = new CustomeResponse();

		try {

			String id = (String) request.getAttribute("userid");
			noteService.addNote(note, Integer.valueOf(id));
			myResponse.setMessage("Note is added");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			myResponse.setMessage("Note is Notadded");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/deleteNote/{noteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> deleteNote(@PathVariable("noteId") long noteId) {

		CustomeResponse myResponse = new CustomeResponse();
		try {

			noteService.deleteNote(noteId);
			myResponse.setMessage("Note is deleted");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

		}

		catch (Exception e) {
			e.printStackTrace();
			myResponse.setMessage("Note is Notdeleted");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> updateNote(@RequestBody Note note) {
		CustomeResponse myResponse = new CustomeResponse();
		try {
			// note.setNoteId(noteId);
            System.out.println("in update controller");
			noteService.updateNote(note);
			myResponse.setMessage("Note is updated");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

		} catch (Exception e) {

			e.printStackTrace();
			myResponse.setMessage("Note is notUpdated");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getAllNotes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List getAllNotes(HttpServletRequest request) {
	
		List<Note> allNotes = null;
		try {
			String id = (String) request.getAttribute("userid");
			allNotes = noteService.getallNotes(Integer.valueOf(id));
			System.out.println(allNotes);
		/*	myResponse.setMessage("Got all the notes");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);*/
			return allNotes;
		} catch (Exception e) {

			e.printStackTrace();
			return allNotes;
			/*myResponse.setMessage("Didn't got the notes ");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);*/
		}
	}

	@RequestMapping(value = "/updateToArchive/{noteId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> updateToArchive(@PathVariable("noteId") long noteId) {
		CustomeResponse myResponse = new CustomeResponse();
		try {
			System.out.println("url is working fine");
			noteService.updateNoteToArchive(noteId);
			myResponse.setMessage("updated to archive sucessFully");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			myResponse.setMessage("some error occured ");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updteToPin/{noteId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> updteToPin(@PathVariable("noteId") long noteId) {
		CustomeResponse myResponse = new CustomeResponse();
		try {
			noteService.updateNotePin(noteId);
			myResponse.setMessage("note pin is upadted");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			myResponse.setMessage("note pin is not updated ");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateTotrash/{noteId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> updateTotrash(@PathVariable("noteId") long noteId) {
		CustomeResponse myResponse = new CustomeResponse();
		try {
			noteService.UpdateNoteToTrash(noteId);
			myResponse.setMessage("note is updated to trash");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			myResponse.setMessage("note is not updated to trash");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/emptyTrash", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> emptyTrash(HttpServletRequest request) {
		CustomeResponse myResponse = new CustomeResponse();
		try {
			String id = (String) request.getAttribute("userid");
			noteService.emptyTrash(Integer.valueOf(id));
			myResponse.setMessage("sucessFully cleared Trash");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
		} catch (Exception e) {													

			e.printStackTrace();
			myResponse.setMessage("some error occured ");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
