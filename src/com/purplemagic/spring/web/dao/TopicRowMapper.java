package com.purplemagic.spring.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TopicRowMapper implements RowMapper<Topic> {

	@Override
	public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
		Topic topic = new Topic();
		User user = new User();
		
		user.setUsername(rs.getString("username"));
//		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
//		user.setEnabled(rs.getBoolean("enabled"));			// posto se vracaju samo teme aktivnih korisnika,
//															// moze stajati user.setEnabled(true);	
//		user.setAuthority(rs.getString("authority"));
		
		topic.setId(rs.getInt("id"));
		topic.setUser(user);
		topic.setSubject(rs.getString("subject"));
		topic.setText(rs.getString("text"));
		topic.setTime(rs.getTimestamp("time"));
		
		return topic;
	}



}
