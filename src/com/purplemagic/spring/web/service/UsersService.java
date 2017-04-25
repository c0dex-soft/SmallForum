package com.purplemagic.spring.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.purplemagic.spring.web.dao.User;
import com.purplemagic.spring.web.dao.UsersDao;

@Service(value = "usersService")
public class UsersService {

	private UsersDao usersDao;

	@Autowired
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Secured(value = "ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}

	public boolean exists(String username) {
		return usersDao.exists(username);
	}

	public void create(User user) {
		usersDao.create(user);
	}

	public String getEmailByUsername(String username) {
		return usersDao.getEmailByUsername(username);
	}

	public boolean statusChange(String admin, String username) {
		return usersDao.statusChange(admin, username);
		
	}

	public boolean roleChange(String admin, String username) {
		return usersDao.roleChange(admin, username);
		
	}

	public boolean deleteUser(String admin, String username) {
		return usersDao.deleteUser(admin, username);
		
	}

}
