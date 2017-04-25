package com.purplemagic.spring.web.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.purplemagic.spring.web.controllers.LoginController;

@Component("usersDao")
public class UsersDao {
	
	private static Logger logger = Logger.getLogger(LoginController.class);

	private NamedParameterJdbcTemplate jdbc;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> getAllUsers() {
		return jdbc.query("select * from users", BeanPropertyRowMapper.newInstance(User.class));
	}

	public boolean exists(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		return jdbc.queryForObject("select count(*) from users where username=:username", params, Integer.class) == 1;
	}

	@Transactional
	public boolean create(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", user.getUsername());
		params.addValue("password", passwordEncoder.encode(user.getPassword()));
		params.addValue("name", user.getName());
		params.addValue("email", user.getEmail());
		params.addValue("enabled", user.isEnabled());
		params.addValue("authority", user.getAuthority());

		return jdbc.update(
				"insert into users (username, password, name, email, enabled, authority) values (:username, :password, :name, :email, :enabled, :authority)",
				params) == 1;
	}

	public String getEmailByUsername(String username) {
		User user = getUserByUsername(username);
		return user.getEmail();
	}

	private User getUserByUsername(String username) {
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		return jdbc.queryForObject("select * from users where username=:username", params,
				BeanPropertyRowMapper.newInstance(User.class));
	}

	public boolean statusChange(String admin, String username) {
		boolean changed = false;
		
		User currentUser = getUserByUsername(username);
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		
		if (currentUser.isEnabled() == true) {			
			changed = jdbc.update("update users set enabled=false where username=:username", params) == 1;
			if (changed == true) {
				logger.info("Administrator <" + admin + "> je promenio status korisniku #" +username+ " [ENABLED->DISABLED]");
			} else {
				logger.info("Administrator <" + admin + "> nije uspeo da promeni status korisniku #" +username+ " [ENABLED->DISABLED]");
			}			
			return changed;
		} else if (currentUser.isEnabled() == false) {
			changed = jdbc.update("update users set enabled=true where username=:username", params) == 1;
			if (changed == true) {
				logger.info("Administrator <" + admin + "> je promenio status korisniku #" +username+ " [DISABLED->ENABLED]");
			} else {
				logger.info("Administrator <" + admin + "> nije uspeo da promeni status korisniku #" +username+ " [DISABLED->ENABLED]");
			}			
			return changed;
		} else {
			return false;
		}
	}

	public boolean roleChange(String admin, String username) {
		boolean changed = false;
		
		User currentUser = getUserByUsername(username);
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		
		if (currentUser.getAuthority().equals("ROLE_ADMIN")) {
			changed = jdbc.update("update users set authority='ROLE_USER' where username=:username", params) == 1;
			if (changed == true) {
				logger.info("Administrator <" + admin + "> je uspesno promenio privilegiju korisniku #" +username+ " [ADMIN->USER]");
			} else {
				logger.info("Administrator <" + admin + "> nije uspeo da promeni privilegiju korisnika #" +username+ " [ADMIN->USER]");
			}
			return changed;
		} else if (currentUser.getAuthority().equals("ROLE_USER")) {
			changed = jdbc.update("update users set authority='ROLE_ADMIN' where username=:username", params) == 1;
			if (changed == true) {
				logger.info("Administrator <" + admin + "> je uspesno promenio privilegiju korisniku #" +username+ " [USER->ADMIN]");
			} else {
				logger.info("Administrator <" + admin + "> nije uspeo da promeni privilegiju korisnika #" +username+ " [USER->ADMIN]");
			}
			return changed;
		} else {
			return false;
		}
	}

	public boolean deleteUser(String admin, String username) {
		boolean changed = false;
		
		MapSqlParameterSource params = new MapSqlParameterSource("username", username);
		changed = jdbc.update("delete from users where username=:username", params) == 1;
		
		if (changed == true) {
			logger.info("Administrator <" + admin + "> je uspesno izbrisao korisnika #" +username);
		} else {
			logger.info("Administrator <" + admin + "> nije uspeo da izbrise korisnika #" +username);
		}		
		return changed;		
	}

}
