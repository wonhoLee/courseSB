package net.course.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.course.domain.Question;
import net.course.domain.QuestionRepository;
import net.course.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	@Autowired
	private QuestionRepository questioinRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		return "qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		System.out.println("test");
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questioinRepository.save(newQuestion);
		
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("questions", questioinRepository.findById(id).get());
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {
		model.addAttribute("questions", questioinRepository.findById(id).get());
		return "/qna/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents) {
		Question question = questioinRepository.findById(id).get();
		question.update(title, contents);
		questioinRepository.save(question);
		return String.format("redirect:/questions/%d", id);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		questioinRepository.deleteById(id);
		return "redirect:/";
	}
}
