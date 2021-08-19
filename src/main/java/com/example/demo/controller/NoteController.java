package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {
	private NoteRepository noteRepository;

	@GetMapping
	public String findAll(Model model) {
		Iterable<Note> notes = noteRepository.findAll();
		model.addAttribute("notes", notes);
		return "notes";
	}

	@GetMapping("/search")
	public String search(@RequestParam("str") String param) {
//		List<Note> note = noteRepository.findByTitle(param);
		return "notes";
	}
}
