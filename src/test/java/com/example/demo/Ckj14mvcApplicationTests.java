package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.MailService;

@SpringBootTest
class Ckj14mvcApplicationTests {
	private MailService mailService;

	@Autowired
	public Ckj14mvcApplicationTests(MailService mailService) {
		this.mailService = mailService;
	}

	@Test
	void passwordEncoderTest() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String str = "admin";
		String encodedStr = passwordEncoder.encode(str);
		System.out.println(encodedStr);
		System.out.println(passwordEncoder.matches(str, encodedStr));
	}

	@Test
	void mailSenderTest() {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setText("Hello, mailtrap!");
		mail.setSubject("My test mail");
		mail.setFrom("sender@mail.com");
		mail.setTo("receiver@mail.com");
		System.out.println("Start sending...");
		for (int i = 0; i < 3; i++) {
			mailService.sendMail(mail);
		}
		System.out.println("Sending complete!");
	}
}
