package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	private UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping
	public String findAll(Model model) {
		Iterable<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "usersPage";
	}

	@GetMapping("/create")
	public String getForm() {
		return "userForm";
	}

	@PostMapping("/add")
	public String saveUser(@ModelAttribute User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/search")
	public String search(@RequestParam("param") String str, @RequestParam("age") int age) {
//		List<User> users= userRepository.findByUsernameContaining(param);
//		List<User> users = userRepository.findByUsernameContainingAndAgeGreaterThan(str, age);
		List<User> users = userRepository.search("%" + str + "%", age);
		return "usersPage";
	}

	// перехожу на страницу с формой
	// ввожу в поля формы данные пользователя
	// нажимаю кнопку "создать"
	// отправляется Пост запрос в контроллер
	// обрабатываю параметры запроса и создаю нового пользователя
	// вызываю метод репозитория для сохранения пользователя в БД
	// выполняю редирект на страницу пользователей
}
