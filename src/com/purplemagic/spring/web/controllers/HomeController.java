package com.purplemagic.spring.web.controllers;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.purplemagic.spring.web.checker.MyTopicsChecker;
import com.purplemagic.spring.web.dao.Topic;
import com.purplemagic.spring.web.dao.User;
import com.purplemagic.spring.web.service.TopicsService;
import com.purplemagic.spring.web.service.UsersService;

@Controller
public class HomeController {
	
	private static Logger logger = Logger.getLogger(HomeController.class);

	private TopicsService topicsService;
	
	@Autowired
	public void setTopicsService(TopicsService topicsService) {
		this.topicsService = topicsService;
	}
	
	@RequestMapping("/")
	public String showHome(Model model, Principal principal) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		logger.info("======================================================== Prikaz Home stranice ========================================================");
		
		List<Topic> topics = topicsService.getTopics();
		model.addAttribute("topics", topics);
		return "home";
	}
	
	@RequestMapping("/proba")
	public String proba() {
		System.out.println("Ulazak u metodu /proba");
		Topic topicById = topicsService.getTopicById(187);
		return "proba";
	}
	
}
