package com.lang2am.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map> list(String query, String category, int limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("queryList", query.split(" "));
		map.put("categoryCode", StringUtils.contains(category, "code"));
		map.put("categoryText", StringUtils.contains(category, "text"));
		map.put("limit", limit);
		return this.sqlSessionTemplate.selectList("listAllText", map);
	}

	public int count(String query, String category) {
		Map<String, Object> map = new HashMap<>();
		map.put("queryList", query.split(" "));
		map.put("categoryCode", StringUtils.contains(category, "code"));
		map.put("categoryText", StringUtils.contains(category, "text"));
		return this.sqlSessionTemplate.selectOne("count", map);
	}

	public List<Map> list(String condition, int limit) {
		Map<String, Object> map = new HashMap<>();
		map.put("condition", condition);
		map.put("limit", limit);
		return this.sqlSessionTemplate.selectList("listAllTextCondition", map);
	}

	public int count(String condition) {
		Map<String, Object> map = new HashMap<>();
		map.put("condition", condition);
		return this.sqlSessionTemplate.selectOne("countCondition", map);
	}

}
