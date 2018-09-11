package net.course.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.course.domain.User;

@Controller
public class UserController {
	private List<User> users = new ArrayList<>();
	
	@PostMapping("/create")
	public String create(User user) {
		System.out.println("inputValue : " + user.toString());
		users.add(user);
		
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", users);
		return "list";
	}
}
