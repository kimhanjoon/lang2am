package com.lang2am.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lang2am.domain.Translation;

@Component
public class TranslationDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int insert(Translation dvo) {
		return this.sqlSessionTemplate.insert("insert", dvo);
	}

	public int update(Translation dvo) {
		return this.sqlSessionTemplate.update("update", dvo);
	}

	public String newcode() {
		return this.sqlSessionTemplate.selectOne("selectMaxCode");
	}

	public void updateStatus(Translation dvo) {
		this.sqlSessionTemplate.update("updateStatus", dvo);
	}
}
