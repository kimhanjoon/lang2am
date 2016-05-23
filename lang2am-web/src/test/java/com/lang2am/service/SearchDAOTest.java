package com.lang2am.service;

import java.util.List;
import java.util.Map;

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
	public void testName() throws Exception {

		List<Map> list = searchDAO.list("code");
		System.out.println(list);
		System.out.println(list.size());
	}
}
