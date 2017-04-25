package com.purplemagic.spring.web.checker;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.purplemagic.spring.web.controllers.HomeController;
import com.purplemagic.spring.web.dao.Topic;
import com.purplemagic.spring.web.service.TopicsService;

public class MyTopicsChecker {

	private static Logger logger = Logger.getLogger(HomeController.class);

	public static void myTopicsExists(Model model, Principal principal, TopicsService topicsService) {
		logger.info("===================================== MyTopicsChecker.myTopicsExists() =====================================");
		
		if (principal != null) {
			String username = principal.getName();
			if (topicsService.getTopicsByUsername(username).size() > 0) {
				model.addAttribute("topicsexists", true);
			} else {
				model.addAttribute("topicsexists", false);
			}
		} else {
			model.addAttribute("topicsexists", false);
		}		
	}

	public static void recieveMyTopics(Model model, Principal principal, TopicsService topicsService) {
		logger.info("===================================== MyTopicsChecker.recieveMyTopics() =====================================");
		
		if (principal != null) {
			String username = principal.getName();
			List<Topic> userTopics = topicsService.getTopicsByUsername(username);

			if (userTopics.size() > 0) {
				model.addAttribute("userTopics", userTopics);
				model.addAttribute("username", username);
			}

		}
	}
}
