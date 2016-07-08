package com.lang2am.service;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lang2am.config.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class SearchDAOTest {

	@Autowired
	private SearchDAO searchDAO;

	@Test
	public void list2WithText() throws Exception {

		List<Map> list = searchDAO.list("code", "", 2);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void list30WithText() throws Exception {

		List<Map> list = searchDAO.list("code", "", 30);
		Assert.assertNotNull(list);
		Assert.assertEquals(3, list.size());
	}

	@Test
	public void list30WithTextOver30() throws Exception {

		List<Map> list = searchDAO.list("d", "", 30);
		Assert.assertNotNull(list);
		Assert.assertEquals(30, list.size());
	}

	@Test
	public void list9999WithTextOver30() throws Exception {

		List<Map> list = searchDAO.list("d", "", 9999);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() > 30);
	}

	@Test
	public void listNoresult() throws Exception {

		List<Map> list = searchDAO.list("XXXXXXXXXXXXXXXXX", "", 30);
		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}
}
