package com.lang2am.util;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class Lang2amTest {

	// L_CODE : code
	// L_NAME : name
	// L_CODENAME : {{L_CODE}} {{L_NAME}}
	// L_00004 : {{L_CODENAME}} is required.
	// L_00005 : ${0} of ${1} has completed.
	// L_00006 : all HTTP connection has close.

	@Test
	public void simple_replace() throws Exception {
		Assert.assertEquals("code", Lang2am.getText("en_US", "L_CODE"));
		Assert.assertEquals("name", Lang2am.getText("en_US", "L_NAME"));
		Assert.assertEquals("all HTTP connection has close.", Lang2am.getText("en_US", "L_00006"));
	}

	@Test
	public void transform_replace() throws Exception {
		Assert.assertEquals("Code", Lang2am.getText("en_US", "L_CODE_F"));
		Assert.assertEquals("CODE", Lang2am.getText("en_US", "L_CODE_U"));
		Assert.assertEquals("name", Lang2am.getText("en_US", "L_NAME_L"));
		Assert.assertEquals("Code name is required.", Lang2am.getText("en_US", "L_00004_F"));
		Assert.assertEquals("Code Name Is Required.", Lang2am.getText("en_US", "L_00004_E"));
		Assert.assertEquals("All HTTP connection has close.", Lang2am.getText("en_US", "L_00006_F"));
		Assert.assertEquals("All HTTP Connection Has Close.", Lang2am.getText("en_US", "L_00006_E"));
		Assert.assertEquals("all http connection has close.", Lang2am.getText("en_US", "L_00006_L"));
		Assert.assertEquals("ALL HTTP CONNECTION HAS CLOSE.", Lang2am.getText("en_US", "L_00006_U"));
	}

	@Test
	public void nested_replace() throws Exception {
		Assert.assertEquals("code name", Lang2am.getText("en_US", "L_CODENAME"));
		Assert.assertEquals("code name is required.", Lang2am.getText("en_US", "L_00004"));
	}

	@Test
	public void param_replace() throws Exception {
		Assert.assertEquals("3 of 5 has completed.", Lang2am.getText("en_US", "L_00005", "3", "5"));
		Assert.assertEquals("3 of 5 has completed.", Lang2am.getText("en_US", "L_00005", 3, 5));
		Assert.assertEquals("3 of 5 has completed.", Lang2am.getText("en_US", "L_00005", BigDecimal.valueOf(3), BigDecimal.valueOf(5)));
		Assert.assertEquals("3000000000 of 5000000000 has completed.", Lang2am.getText("en_US", "L_00005", BigDecimal.valueOf(3000000000L), BigDecimal.valueOf(5000000000L)));
	}

	@Test
	public void param_as_code_replace() throws Exception {
		Assert.assertEquals("code of name has completed.", Lang2am.getText("en_US", "L_00005", "L_CODE", "L_NAME"));
	}

	@Test
	public void locale_replace() throws Exception {
		Assert.assertEquals("코드", Lang2am.getText("ko_KR", "L_CODE"));
		Assert.assertEquals("이름", Lang2am.getText("ko_KR", "L_NAME"));
		Assert.assertEquals("코드이름", Lang2am.getText("ko_KR", "L_CODENAME"));
		Assert.assertEquals("코드이름은 필수입니다.", Lang2am.getText("ko_KR", "L_00004"));
		Assert.assertEquals("5개 중 3개가 완료되었습니다.", Lang2am.getText("ko_KR", "L_00005", "3", "5"));
	}

}
