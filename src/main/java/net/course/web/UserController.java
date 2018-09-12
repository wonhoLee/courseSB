package net.course.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.course.domain.User;
import net.course.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			System.out.println("Login Failure! - userId");
			return "redirect:/users/loginForm";
		}
		
		if(!password.equals(user.getPassword())) {
			System.out.println("Login Failure! - password");
			return "redirect:/users/loginForm";
		}
		System.out.println("Login Success!");
		session.setAttribute("sessionedUser", user);
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println("Logout Success!");
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}
	
	@PostMapping("")
	public String create(User user) {
		System.out.println("inputValue : " + user);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String userlist(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	@GetMapping("/form")
	public String userForm() {
		return "/user/form";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if(tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User)tempUser;
		System.out.println(id);
		System.out.println(sessionedUser.getId());
		if(!id.equals(sessionedUser.getId())){
			throw new IllegalStateException("error");
		}
		
		User user = userRepository.findById(sessionedUser.getId()).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	/*@PostMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser) {
		System.out.println("updateValue : " + updateUser);
		User user = userRepository.findById(id).get();
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users";
	}*/
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if(tempUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = (User)tempUser;
		if(!id.equals(sessionedUser.getId())){
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		System.out.println("updateValue : " + updateUser);
		User user = userRepository.findById(id).get();
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
