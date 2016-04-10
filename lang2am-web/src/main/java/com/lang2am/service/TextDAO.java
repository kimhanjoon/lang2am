package com.lang2am.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lang2am.domain.TextVO;

@Component
public class TextDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int insert(TextVO dvo) {
		return this.sqlSessionTemplate.insert("insert", dvo);
	}

	public int update(TextVO dvo) {
		return this.sqlSessionTemplate.update("update", dvo);
	}

	public String newcode() {
		return this.sqlSessionTemplate.selectOne("selectMaxCode");
	}

	public void updateStatus(TextVO dvo) {
		this.sqlSessionTemplate.update("updateStatus", dvo);
	}
}
