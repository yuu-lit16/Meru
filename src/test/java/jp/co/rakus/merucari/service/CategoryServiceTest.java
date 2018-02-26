package jp.co.rakus.merucari.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

	@Autowired
	CategoryService service;
	
	@Test
	public void testFindParent() {
		assertThat(service.findParent(),hasSize(10));
	}

	@Test
	public void testFindChildByParent() {
		assertThat(service.findChildByParent(50),hasSize(8));
	}

}