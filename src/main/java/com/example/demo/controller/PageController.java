package com.example.demo.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.ConfirmationToken;
import com.example.demo.model.User;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MailService;

@Controller
public class PageController {
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private MailService mailService;

	private TokenRepository tokenRepository;

	public PageController(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService,
			TokenRepository tokenRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailService = mailService;
		this.tokenRepository = tokenRepository;
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
	
	@GetMapping("/notes")
	public String notesPage() {
		return "notes";
	}

	@PostMapping("/signup")
	public String registerNewUser(
			@RequestParam String username, 
			@RequestParam String password,
			@RequestParam String email,
			@RequestParam String url) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return "redirect:/signup?error=username_exists";
		}
		user = new User(0, username, passwordEncoder.encode(password), "USER", email);
		user = userRepository.save(user);
		ConfirmationToken token = new ConfirmationToken(user);
		url = url.replace("signup", "confirm");
		url = url+"?tokenValue=" + token.getValue();
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setText("Visit this link for account activation: " + url);
		mail.setTo(user.getEmail());
		mail.setFrom("ckj14@mail.com");
		mail.setSubject("Account activation");
		mailService.sendMail(mail);
		tokenRepository.save(token);
		return "redirect:/login";
	}
	
	@GetMapping("/confirm")
	public String activateUser(@RequestParam String tokenValue) {
		ConfirmationToken token =tokenRepository.findByValue(tokenValue);
		User user = token.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		tokenRepository.delete(token);
		return "redirect:/login";
	}

}
