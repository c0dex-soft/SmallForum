package com.purplemagic.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purplemagic.spring.web.dao.Topic;
import com.purplemagic.spring.web.dao.TopicsDao;

@Service(value = "topicsService")
public class TopicsService {

	private TopicsDao topicsDao;

	@Autowired
	public void setTopicsDao(TopicsDao topicsDao) {
		this.topicsDao = topicsDao;
	}

	public List<Topic> getTopics() {
		return topicsDao.getTopics();
	}

	public Topic getTopicById(int id) {
		return topicsDao.getTopicById(id);
	}

	public boolean delete(int id) {
		return topicsDao.delete(id);
	}

	public boolean create(Topic topic) {
		return topicsDao.create(topic);
	}

	public boolean update(Topic topic) {
		return topicsDao.update(topic);
	}

	public List<Topic> searchTopics(String term) {
		return topicsDao.searchTopics(term);
	}

	public List<Topic> getTopicAndPosts(int topicId) {
		return topicsDao.getTopicAndPosts(topicId);
	}

	public boolean createPost(Topic post, Integer topicId) {
		return topicsDao.createPost(post, topicId);
		
	}

	public List<Topic> getTopicsByUsername(String username) {
		return topicsDao.getTopicsByUsername(username);
	}

}
