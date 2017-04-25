package com.purplemagic.spring.web.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component("topicsDao")
public class TopicsDao {

	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Topic> getTopics() {
		return jdbc.query(
				"select * from topics t, users u where user_username=username and u.enabled=true order by t.time DESC",
				new TopicRowMapper());
	}

	public boolean create(Topic topic) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(topic);
		return jdbc.update("insert into topics (user_username, subject, text, time) values (:username, :subject, :text, :time)",
				params) == 1;

	}

	public boolean update(Topic topic) {

		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(topic);
		return jdbc.update("update topics set text=:text where id=:id", params) == 1;
	}

	public boolean delete(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.update("delete from topics where id=:id", params) == 1;
	}

	public Topic getTopicById(int id) {

		MapSqlParameterSource params = new MapSqlParameterSource("id", id);
		return jdbc.queryForObject(
				"select * from topics t inner join users u on user_username=username where u.enabled=true and t.id=:id",
				params, new TopicRowMapper());

	}

	public List<Topic> getTopicsByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		return jdbc.query(
				"select * from topics t inner join users u on t.user_username=u.username where u.username=:username and u.enabled=true order by t.time DESC;",
				params, BeanPropertyRowMapper.newInstance(Topic.class));
	}

	public List<Topic> searchTopics(String term) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("term", "%" + term + "%");
		return jdbc.query(
				"select * from topics t inner join users u on user_username=username where subject like :term or text like :term order by t.time DESC",
				params, new TopicRowMapper());
	}

	public List<Topic> getTopicAndPosts(int topicId) {
		MapSqlParameterSource params = new MapSqlParameterSource("topicId", topicId);
		List<Topic> rTopicPosts = new ArrayList<>();
		
		Topic rTopic = jdbc.queryForObject("select u.username, u.email, t.id, t.`subject`, t.text, t.time from users u"
				+ " inner join topics t on u.username=t.user_username where t.id=:topicId and u.enabled=true", params, new TopicRowMapper());	
		List<Topic> rPosts = jdbc.query("select * from posts p where p.topic_id=:topicId order by p.time", params, new PostRowMapper());
		
		rTopicPosts.add(rTopic);
		for (Topic post : rPosts) {
			rTopicPosts.add(post);
		}
		
		return rTopicPosts;
	}

	public boolean createPost(Topic post, Integer topicId) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", post.getUsername());
		params.addValue("email", post.getEmail());
		params.addValue("subject", post.getSubject());
		params.addValue("text", post.getText());
		params.addValue("topic_id", topicId);
		
		return jdbc.update("insert into posts (name, email, subject, text, topic_id) values (:name, :email, :subject, :text, :topic_id)", params) == 1;
		
	}
		
}
