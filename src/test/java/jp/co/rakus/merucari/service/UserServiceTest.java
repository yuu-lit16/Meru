package jp.co.rakus.merucari.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.rakus.merucari.domain.User;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService service;
	
	@Test
	public void testFindByMaillAddress() {
		User expectedUser = service.findByMaillAddress("a@a");
		assertThat(expectedUser.getId(),is(5));
	}

//	@Test
//	public void testSave() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetMaxId() {
		assertThat(service.getMaxId(), is(5));
	}

}
