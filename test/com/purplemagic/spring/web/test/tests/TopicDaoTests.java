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

import com.purplemagic.spring.web.dao.Topic;
import com.purplemagic.spring.web.dao.TopicsDao;
import com.purplemagic.spring.web.dao.User;
import com.purplemagic.spring.web.dao.UsersDao;

@ActiveProfiles(value = "dev")
@ContextConfiguration(locations = { "classpath:com/purplemagic/spring/web/config/dao-context.xml",
		"classpath:com/purplemagic/spring/web/config/security-context.xml",
		"classpath:com/purplemagic/spring/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TopicDaoTests {

	private UsersDao usersDao;

	private TopicsDao topicsDao;

	private DataSource dataSource;

	@Autowired
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	@Autowired
	public void setTopicsDao(TopicsDao topicsDao) {
		this.topicsDao = topicsDao;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from users");
		jdbc.execute("delete from topics");
		jdbc.execute("delete from posts");
		jdbc.execute("alter table topics auto_increment = 1");
	}

	@Test
	public void testTopics() {
		User user = new User("markop.018", "Marko Z. Petkovic", "lozinka", "markop.018@codexsoft.com", true, "ROLE_ADMIN");
		assertTrue("Kreiranje korisnika bi trebalo da vrati vrednost TRUE", usersDao.create(user));

		Topic topic = new Topic(user, "Test tema", "Ovo je tema za JUnit testiranje metode testCreateTopic()");
		assertTrue("Kreiranje teme bi trebalo da vrati vrednost TRUE", topicsDao.create(topic));

		List<Topic> topics = topicsDao.getTopics();
		assertEquals("Trebalo bi da postoji samo jedna tema u bazi", 1, topics.size());
		assertEquals("Kreirana tema bi trebalo da se poklapa sa temom koja je vracena iz baze", topic, topics.get(0));

		// Dobijanje Tema na osnovu ID-a
		topic = topics.get(0);

		topic.setText("Ovo je izmenjeni tekst teme.");
		assertTrue("Izmena teme bi trebalo da vrati vrednost TRUE", topicsDao.update(topic));

		Topic updated = topicsDao.getTopicById(topic.getId());
		assertEquals("Izmenjena tema bi trebalo da se poklapa sa vracenom (izmenjenom) temom", topic, updated);

		// Test getById
		Topic topic2 = new Topic(user, "Naslov testirajuce teme2", "Ovo je jedna nova testirajuca tema");
		assertTrue("Kreiranje teme bi trebalo da vrati TRUE", topicsDao.create(topic2));

		List<Topic> secondList = topicsDao.getTopics();

		for (Topic current : secondList) {
			Topic retrieved = topicsDao.getTopicById(current.getId());
			assertEquals("Tema vracena na osnovu ID-a bi trebalo da se poklapa sa temom iz liste", current, retrieved);
		}

		// Test getByUsername
		User user2 = new User("kpb.27", "Kevin Prince Boateng", "kpb27", "kpb27@acmilan.com", true, "ROLE_ADMIN");
		assertTrue("Kreiranje korisnika bi trebalo da vrati vrednost TRUE", usersDao.create(user2));
		Topic kpbTopic = new Topic(user2, "Tema KPB-a", "Ovo je tema Kevina Princa Boatenga");
		assertTrue("Kreiranje teme bi trebalo da vrati vrednost TRUE", topicsDao.create(kpbTopic));

		List<Topic> userTopics = topicsDao.getTopicsByUsername(user.getUsername());
		assertEquals("Korisnik bi trebalo da ima 2 teme u bazi podataka", 2, userTopics.size());

		// Test DeleteTopic
//		topicsDao.delete(topic.getId());
//		List<Topic> finalList = topicsDao.getTopics();
//		assertEquals("U bazi bi trebalo da se nalaze DVE teme", 2, finalList.size());

	}

}
