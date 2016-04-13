package com.lang2am.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lang2am.domain.TextVO;

@Component
public class SearchDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map> list() {
		return this.sqlSessionTemplate.selectList("listAllText");
	}

	public List<Map> list(String q) {
		TextVO dvo = TextVO.builder().text(q).build();
		return this.sqlSessionTemplate.selectList("listAllText", dvo);
	}

	public int count(String q) {
		TextVO dvo = TextVO.builder().text(q).build();
		return this.sqlSessionTemplate.selectOne("count", dvo);
	}

}
