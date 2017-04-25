package com.purplemagic.spring.web.controllers;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.purplemagic.spring.web.checker.MyTopicsChecker;
import com.purplemagic.spring.web.dao.Topic;
import com.purplemagic.spring.web.service.TopicsService;
import com.purplemagic.spring.web.service.UsersService;

@Controller
public class TopicController {

	private static Logger logger = Logger.getLogger(TopicController.class);

	private TopicsService topicsService;
	private UsersService usersService;

	@Autowired
	public void setTopicsService(TopicsService topicsService) {
		this.topicsService = topicsService;
	}
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/topics")
	public String showTopics(Model model, Principal principal) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		List<Topic> topics = topicsService.getTopics();
		model.addAttribute("topics", topics);

		return "topics";
	}

	@RequestMapping("/createtopic")
	public String createTopic(Model model, Principal principal) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		Topic topic = new Topic();
		String username = principal.getName();
		topic.setUsername(username);
		topic.setEmail(usersService.getEmailByUsername(username));
		
		model.addAttribute("topic", topic);
		return "createtopic";
	}

	@RequestMapping(value = "/docreate", method = RequestMethod.POST)
	public String doCreate(Model model, @Valid Topic topic, BindingResult result, Principal principal) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		logger.info(
				"======================================================== KREIRANJE NOVE TEME ========================================================");
		logger.info("Korisnik <" + principal.getName() + "> je kreirao novu temu: " + topic);

		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage());
			}

			return "createtopic";
		} else {
			String username = principal.getName();
			topic.setUsername(username);
			topic.setTime(new Timestamp(System.currentTimeMillis()));

			if (topicsService.create(topic)) {
				return "topiccreated";
			} else {
				System.out.println("Tema nije uspesno kreirana");
				return "redirect:/createtopic";
			}
		}

	}
	
	@RequestMapping("/topic")
	public String showTopic(Model model, @RequestParam("id") int topicId, Principal principal) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		List<Topic> topicAndPosts = topicsService.getTopicAndPosts(topicId);
		String title = topicAndPosts.get(0).getSubject();
		model.addAttribute("topicPosts", topicAndPosts);
		model.addAttribute("title", title);
		return "topicpage";
	}
	
	@RequestMapping("/createpost")
	public String createAnswer(Model model, Principal principal, @RequestParam(value="id", required=false) Integer topicId) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		if (topicId == null) {
			return "redirect:/";
		} else if (topicId > 0) {
			Topic post = new Topic();
			
			if (principal != null) {
				String username = principal.getName();
				post.getUser().setUsername(username);
				post.getUser().setEmail(usersService.getEmailByUsername(username));
			}
			
			String subject = topicsService.getTopicById(topicId).getSubject();		
			post.setSubject("RE: " +subject);
			model.addAttribute("topic", post);
			model.addAttribute("topicId", new Integer(topicId));
			model.addAttribute("topicname", subject);
			
			return "createpost";
		} else {
			return "redirect:/";
		}		
	}
	
	@RequestMapping(value = "/doanswer", method = RequestMethod.POST)
	public String doAnswer(Model model, @Valid Topic post, BindingResult result, Principal principal, @RequestParam("id") int topicId) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		if (result.hasErrors()) {
//			for (Object object : result.getAllErrors()) {
//				if (object instanceof FieldError) {
//					System.out.print("FIELD ERROR -> ");
//					FieldError fieldError = (FieldError) object;
//					System.out.println(fieldError.getCode());
//				}
//
//				if (object instanceof ObjectError) {
//					System.out.print("OBJECT ERROR -> ");
//					ObjectError objectError = (ObjectError) object;
//					System.out.println(objectError.getCode());
//				}
//			}
			model.addAttribute("topicId", topicId);
			String subject = topicsService.getTopicById(topicId).getSubject();
			model.addAttribute("topicname", subject);
			return "createpost";
		}
		
		if (principal != null) {
			String username = principal.getName();
			post.setUsername(username);
			post.setEmail(usersService.getEmailByUsername(username));
		}
		
		if (topicsService.createPost(post, topicId)) {
			return "redirect:/topic?id=" +topicId;
		} else {
			return "error";
		}
		
	}
	
	@RequestMapping("/mytopics")
	public String showMyTopics(Model model, Principal principal) {
		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		
		String username = principal.getName();
		List<Topic> myTopics = topicsService.getTopicsByUsername(username);
		
		model.addAttribute("username", username);
		model.addAttribute("mytopics", myTopics);
		return "mytopics";
	}
	
	@RequestMapping("/mytopics/{username}")
	public String myTopicChangeDelete(Model model, Principal principal, @PathVariable("username") String username, 
			@RequestParam(value="ed", required=false) boolean editDelete, @RequestParam(value="id", required=false) Integer topicId) {

		
			// ispitivanje da li su prosledjeni svi neophodni parametri
			if (username.equals(principal.getName()) && editDelete==true && topicId!=null) {
				System.out.println("Parametri su prosledjeni - TopicID: " +topicId);
				
				Topic topicById = topicsService.getTopicById(topicId);
				
				// ispitivanje da li tema zaista pripada ulogovanom korisniku
				// da bi se sprecila mogucnost da ulogovani korisnik promeni temu nekog drugog korisnika
				if (topicById.getUsername().equals(principal.getName())) {
					System.out.println("Tema pripada ulogovanom korisniku <" +topicById.getUsername()+ ">");
//					MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
					MyTopicsChecker.myTopicsExists(model, principal, topicsService);
					model.addAttribute("topic", topicById);
					return "editordeletetopic";
				} else {
					System.out.println("Tema ne pripada ulogovanom korisniku <" +principal.getName()+ "> vec korisniku <" +topicById.getUsername()+ ">");
					return "redirect:/mytopics";
				}			
		} else {
			System.out.println("Parametri nisu prosledjeni u odgovarajucem obliku!");
			return "redirect:/mytopics";
		}
	}
	
	@RequestMapping("/mytopics/edit")
	public String editTopic(Model model, @Valid Topic topic, BindingResult result, Principal principal) {
		
		if (result.hasErrors()) {
			return "editordeletetopic";
		}
		
		if (topicsService.update(topic)) {
			return "topicupdated";
		} else {
			return "topicupdateproblem";
		}
	}
	
	@RequestMapping("/mytopics/delete")
	public String deleteTopic(Model model, Topic topic, Principal principal) {
		if (topicsService.delete(topic.getId())) {
			MyTopicsChecker.myTopicsExists(model, principal, topicsService);
			return "topicdeleted";
		} else {
			return "topicdeleteproblem";
		}
	}

	@RequestMapping("/search")
	public String search(Model model, Principal principal, @RequestParam("term") String term) {
//		MyTopicsChecker.recieveMyTopics(model, principal, topicsService);
		MyTopicsChecker.myTopicsExists(model, principal, topicsService);
		
		List<Topic> searchedTopics = topicsService.searchTopics(term);
		model.addAttribute("searchedTopics", searchedTopics);
		model.addAttribute("term", term);
		return "searchedlist";
	}

}
