package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {
	private UserRepository userRepository;
	
	@GetMapping("/usernames")
	public List<String> getUsernames(){
		List<User>users = (List<User>) userRepository.findAll();
		return users.stream()
				.map(u->u.getUsername())
				.collect(Collectors.toList());
	}
}
