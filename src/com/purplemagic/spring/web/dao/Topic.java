package com.purplemagic.spring.web.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Topic {

	private int id;

	@Valid
	private User user;

	@Size(min = 5, max = 60)
	private String subject;

	@Size(min = 15, max = 1000)
	private String text;

	private Date time;

	public Topic() {
		this.user = new User();
	}

	public Topic(User user, String subject, String text) {
		this.user = user;
		this.subject = subject;
		this.text = text;
	}

	public Topic(int id, User user, String subject, String text) {
		this.id = id;
		this.user = user;
		this.subject = subject;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return user.getUsername();
	}

	public void setUsername(String username) {
		this.user.setUsername(username);
		;
	}

	public String getEmail() {
		return user.getEmail();
	}

	public void setEmail(String email) {
		this.user.setEmail(email);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id != other.id)
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Topic [id=" + id + ", user=" + user + ", subject=" + subject + ", text=" + text + ", time=" + time
				+ "]";
	}

}
