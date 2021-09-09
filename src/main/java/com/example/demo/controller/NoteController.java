package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
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

	public static Function<Note, EntityModel<Note>> getMapper(Principal prl) {
		Function<Note, EntityModel<Note>> mapper = n -> {
			return EntityModel.of(n, linkTo(methodOn(NoteController.class).getNote(n.getId(), prl)).withRel("get"),
					linkTo(methodOn(NoteController.class).changeNote(n, prl)).withRel("put"),
					linkTo(methodOn(NoteController.class).deleteNote(n.getId(), prl)).withRel("delete"));
		};
		return mapper;
	}

	// получение всех - GET
	@GetMapping("/all")
	public List<EntityModel<Note>> getAllNotes(Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		
		return user.getNotes().stream()
				.map(getMapper(prl)).collect(Collectors.toList());
	}

	// получение одной - GET
	@GetMapping("/one/{id}")
	public EntityModel<Note> getNote(@PathVariable int id, Principal prl) {
		String username = prl.getName();
		Note note = noteRepository.findByIdAndUserUsername(id, username);
		EntityModel<Note> result = EntityModel.of(note,
				linkTo(methodOn(NoteController.class).getAllNotes(prl)).withRel("notes"),
				linkTo(methodOn(NoteController.class).deleteNote(note.getId(), prl)).withRel("delete"));
		return result;
	}

	// создание - POST
	@PostMapping("/create")
	public EntityModel<Note> createNote(@RequestBody Note note, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		user.addNote(note);
		note = noteRepository.save(note);
		userRepository.save(user);
		return getMapper(prl).apply(note);
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
	public Note deleteNote(@PathVariable int id, Principal prl) {
		Note note = noteRepository.findByIdAndUserUsername(id, prl.getName());
		if (note != null) {
			noteRepository.delete(note);
			return note;
		}
		return null;
	}

}
