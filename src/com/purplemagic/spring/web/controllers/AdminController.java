package com.purplemagic.spring.web.controllers;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.purplemagic.spring.web.checker.MyTopicsChecker;
import com.purplemagic.spring.web.dao.User;
import com.purplemagic.spring.web.service.TopicsService;
import com.purplemagic.spring.web.service.UsersService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private UsersService usersService;
	private TopicsService topicsService;

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@Autowired
	public void setTopicsService(TopicsService topicsService) {
		this.topicsService = topicsService;
	}

	@RequestMapping("")
	public String showAdmin(Model model, Principal principal) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		List<User> users = usersService.getAllUsers();

		model.addAttribute("users", users);
		return "admin";
	}

	@RequestMapping("/user")
	public String statusChange(Model model, Principal principal, @RequestParam("uid") String username,
			@RequestParam(value = "sc", required = false) boolean statusChange,
			@RequestParam(value = "rc", required = false) boolean roleChange,
			@RequestParam(value = "del", required = false) boolean toDelete) {

		String admin = principal.getName();
		
		if (statusChange == true) {
			if (usersService.statusChange(admin, username)) {
				return "redirect:/admin";
			} else {
				model.addAttribute("actionerror", "PROMENE STATUSA KORISNIKA");
				return "adminerror";
			}
		} else if (roleChange == true) {
			if (usersService.roleChange(admin, username)) {
				return "redirect:/admin";
			} else {
				model.addAttribute("actionerror", "PROMENE PRIVILEGIJE KORISNIKA");
				return "adminerror";
			}

		} else if (toDelete == true) {
			if (usersService.deleteUser(admin, username)) {
				return "redirect:/admin";
			} else {
				model.addAttribute("actionerror", "BRISANJA KORISNIKA");
				return "adminerror";
			}

		} else {
			model.addAttribute("action", "NEPOZNATA AKCIJA");
			return "proba";
		}

	}

}
