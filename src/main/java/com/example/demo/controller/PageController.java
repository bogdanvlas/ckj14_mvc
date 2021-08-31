package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
public class PageController {
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	public PageController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}

	@PostMapping("/signup")
	public String registerNewUser(@RequestParam String username, @RequestParam String password) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return "redirect:/signup?error=username_exists";
		}
		user = new User(0, username, passwordEncoder.encode(password), "USER");
		userRepository.save(user);
		return "redirect:/login";
	}

}
