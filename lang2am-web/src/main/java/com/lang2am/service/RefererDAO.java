package com.lang2am.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lang2am.domain.RefererVO;

@Component
public class RefererDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public int insert(RefererVO dvo) {
		return this.sqlSessionTemplate.insert("com.lang2am.service.RefererDAO.insert", dvo);
	}

	public int delete() {
		return this.sqlSessionTemplate.delete("com.lang2am.service.RefererDAO.delete");
	}

}
