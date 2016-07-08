package com.lang2am.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map> list(String query, String category, int limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("query", query);
		map.put("limit", limit);
		return this.sqlSessionTemplate.selectList("listAllText", map);
	}

	public int count(String query, String category) {
		Map<String, Object> map = new HashMap<>();
		map.put("query", query);
		return this.sqlSessionTemplate.selectOne("count", map);
	}


}
