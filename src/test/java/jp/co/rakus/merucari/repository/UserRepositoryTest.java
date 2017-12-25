package jp.co.rakus.merucari.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;
	
//	@Test
//	public void testFindByMaillAddress() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSave() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetMaxId() {
		assertThat(repository.getMaxId(), is(5));
	}

}
