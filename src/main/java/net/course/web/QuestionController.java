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
			return "/user/login";
		}
		return "qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "/user/login";
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
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		try {
			Question question = questioinRepository.findById(id).get();
			hasPermission(session, question);
			model.addAttribute("questions", question);
			return "/qna/updateForm";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
		try {
			Question question = questioinRepository.findById(id).get();
			hasPermission(session, question);
			question.update(title, contents);
			questioinRepository.save(question);
			return String.format("redirect:/questions/%d", id);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session, Model model) {
		try {
			Question question = questioinRepository.findById(id).get();
			hasPermission(session, question);
			questioinRepository.deleteById(id);
			return "redirect:/";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}
	
	private boolean hasPermission(HttpSession session, Question question) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return true;
	}
}
