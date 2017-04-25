package com.purplemagic.spring.web.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.purplemagic.spring.web.dao.User;
import com.purplemagic.spring.web.service.TopicsService;
import com.purplemagic.spring.web.service.UsersService;

@Controller
public class LoginController {
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	private UsersService usersService;
	
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@RequestMapping("/login")
	public String showLogin(Model model, Principal principal) {
		
		if (principal != null) {
			return "redirect:/";
		} else {
			return "login";			
		}
	}
	
	@RequestMapping("/loggedout")
	public String showLoggedout() {
		return "loggedout";
	}
	
	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}
	
	@RequestMapping("/newaccount")
	public String showNewAccount(Model model) {
		
		model.addAttribute("user", new User());
		return "newaccount";
	}
	
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(@Valid User user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "newaccount";
		}
		
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		// Duplikat username (part1)
		if (usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		// Duplikat username (part2)
		try {
			usersService.create(user);
		} catch (DuplicateKeyException ex) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		return "accountcreated";
	}

}
