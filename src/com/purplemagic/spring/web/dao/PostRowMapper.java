package com.purplemagic.spring.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostRowMapper implements RowMapper<Topic> {

	@Override
	public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
		Topic topic = new Topic();
		User user = new User();
		
		user.setUsername(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		
		topic.setSubject(rs.getString("subject"));
		topic.setText(rs.getString("text"));
		topic.setUser(user);
		topic.setTime(rs.getTimestamp("time"));
		
		return topic;
	}

	
}
