package com.bridgeit.controller;


import java.util.List;
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
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/user/addNote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> addNote(@RequestBody Note note) {

		CustomeResponse myResponse = new CustomeResponse();

		try {

			noteService.addNote(note);
			myResponse.setMessage("Note is added");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

		} catch (Exception e) {

			myResponse.setMessage("Note is Notadded");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = "/user/deleteNote/{noteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> deleteNote(@PathVariable("noteId") int noteId) {

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
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);
		    }
	 
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> updateNote(@RequestBody Note note) {
		CustomeResponse myResponse = new CustomeResponse();
		try {
            // note.setNoteId(noteId);
           
			noteService.updateNote(note);
			myResponse.setMessage("Note is updated");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);

		} 
		catch (Exception e) {
			
			e.printStackTrace();
			myResponse.setMessage("Note is notUpdated");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/user/getAllNotes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeResponse> getAllNotes() {
		CustomeResponse myResponse = new CustomeResponse();
		List<Note> allNotes = null;
	 	try {
		    allNotes =  noteService.getallNotes();
		    System.out.println(allNotes);
			myResponse.setMessage("Got all the notes");
			myResponse.setStatus(1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.OK);
		}
	catch (Exception e) {
			
			e.printStackTrace();
			myResponse.setMessage("Didn't got the notes ");
			myResponse.setStatus(-1);
			return new ResponseEntity<CustomeResponse>(myResponse, HttpStatus.BAD_REQUEST);
		}
	}
}
