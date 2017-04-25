package com.purplemagic.spring.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.purplemagic.spring.web.dao.User;
import com.purplemagic.spring.web.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "classpath:com/purplemagic/spring/web/config/dao-context.xml",
		"classpath:com/purplemagic/spring/web/config/security-context.xml",
		"classpath:com/purplemagic/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private DataSource dataSource;

	@Before 
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from users");
		jdbc.execute("delete from topics");
		jdbc.execute("delete from posts");
	}
	
	@Test
	public void testUsers() {
		User user = new User("markonjero", "Marko Z. Petkovic", "lozinka", "markonjero@facebook.com", true, "ROLE_ADMIN");
		assertTrue("Kreiranje korisnika bi trebalo da vrati TRUE", usersDao.create(user));
		
		List<User> users = usersDao.getAllUsers();
		assertEquals("Broj korisnika bi trebalo da je 1", 1, users.size());
		assertTrue("Korisnik bi trebalo da postoji", usersDao.exists(user.getUsername()));
		assertEquals("Kreirani korisnik bi trebalo da bude isti kao i vraceni korisnik", user, users.get(0));
	}
	
}
