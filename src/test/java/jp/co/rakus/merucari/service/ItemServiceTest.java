package jp.co.rakus.merucari.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.rakus.merucari.domain.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

	@Autowired
	ItemService service;
	

	@Test
	public void testGetMaxId() {
		assertThat(service.getMaxId(), is(1482540));
	}
	
//
//	@Test
//	public void testSave() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdate() {
//		fail("Not yet implemented");
//	}
//
	
	@Test
	public void testFindTo30() {
		assertThat(service.findTo30(), hasSize(30));
	}

	@Test
	public void testGetNumOfTotalData() {
		assertThat(service.getNumOfTotalData(), is(1482541));
	}

	@Test
	public void testFindByLimitAndOffset() {
		List<Item> itemList = service.findByLimitAndOffset(1, 0);
		assertThat(itemList.size(), is(1));
	}

	@Test
	public void testFindById() {
		Item expectedItem = new Item();
		expectedItem.setId(1);		
		Item item = service.findById(1);
		assertThat(expectedItem.getId(),is(item.getId()));
	}

	@Test
	public void testFindBrandByParent() {
		assertThat(service.findBrandByParent(300).size(),is(4));
	}

	@Test
	public void testFindSearchedItem() {
		Item expectedItem = new Item();
		expectedItem.setBrand("Nike");
		
		assertThat(service.findSearchedItem(249, "Nike").get(1).getBrand(),is("Nike"));
	}

//	@Test
//	public void testFindSearchedItemExistName() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testFindSearchedItemOnlyName() {
//	}

}