package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/notes")
public class NoteController {
	private NoteRepository noteRepository;

	private UserRepository userRepository;

	public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
		this.noteRepository = noteRepository;
		this.userRepository = userRepository;
	}

	// получение всех - GET
	@GetMapping("/all")
	public List<Note> getAllNotes(Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		return user.getNotes();
	}

	// получение одной - GET
	@GetMapping("/one/{id}")
	public Note getNote(@PathVariable int id, Principal prl) {
		String username = prl.getName();
		Note note = noteRepository.findByIdAndUserUsername(id, username);
		return note;
	}

	// создание - POST
	@PostMapping("/create")
	public Note createNote(@RequestBody Note note, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		user.addNote(note);
		note = noteRepository.save(note);
		userRepository.save(user);
		return note;
	}

	// редактирование - PUT
	@PutMapping("/change")
	public Note changeNote(@RequestBody Note newNote, Principal prl) {
		String username = prl.getName();
		Note oldNote = noteRepository.findByIdAndUserUsername(newNote.getId(), username);
		if (oldNote != null) {
			oldNote.setTitle(newNote.getTitle());
			oldNote.setDescription(newNote.getDescription());
			return noteRepository.save(oldNote);
		}
		return null;
	}

	// удаление - DELETE
	@DeleteMapping("/delete/{id}")
	public void deleteNote(@PathVariable int id, Principal prl) {
		Note note = noteRepository.findByIdAndUserUsername(id, prl.getName());
		if(note!=null) {
			noteRepository.delete(note);
		}
	}
}
