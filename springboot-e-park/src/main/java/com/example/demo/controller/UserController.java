package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.model.User;

import com.example.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/user-registration-form")
	public String viewUserRegistration(Model model) {
		User user = new User();
		model.addAttribute("user", user);
				
		return "user-registration";		
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user) {
		// save employee to database
		userService.saveUser(user);
		return "redirect:/user-sign-in-form";
	}

	@GetMapping("/user-sign-in-form")
	public String viewUserSignInForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
				
		return "user-sign-in";		
	}
}