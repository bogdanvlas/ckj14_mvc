package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Note;
import com.example.demo.model.User;
import com.example.demo.repository.NoteRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/notes")
public class NoteController {
	private NoteRepository noteRepository;

	private UserRepository userRepository;

	public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
		this.noteRepository = noteRepository;
		this.userRepository = userRepository;
	}

	@GetMapping
	public String findAll(Model model, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		Iterable<Note> notes = user.getNotes();
		model.addAttribute("notes", notes);
		return "notes";
	}

	@PostMapping("/create")
	public String createNote(@ModelAttribute Note note, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		user.addNote(note);
		noteRepository.save(note);
//		userRepository.save(user);
		return "redirect:/notes";
	}

	@GetMapping("/search")
	public String search(@RequestParam("str") String param) {
//		List<Note> note = noteRepository.findByTitle(param);
		return "notes";
	}
}
