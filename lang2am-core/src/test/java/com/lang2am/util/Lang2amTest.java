package com.lang2am.util;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class Lang2amTest {

	// L__CODE__ : code
	// L__NAME__ : name
	// L__CODENAME__ : {{L__CODE__}} {{L__NAME__}}
	// L__00004__ : {{L__CODENAME__}} is required.
	// L__00005__ : ${0} of ${1} has completed.
	// L__00006__ : all HTTP connection has close.

	@Test
	public void simple_replace() throws Exception {
		Assert.assertEquals("code", Lang2am.getText("en_US", "L__CODE__"));
		Assert.assertEquals("name", Lang2am.getText("en_US", "L__NAME__"));
		Assert.assertEquals("all HTTP connection has close.", Lang2am.getText("en_US", "L__00006__"));
	}

	@Test
	public void transform_replace() throws Exception {
		Assert.assertEquals("Code", Lang2am.getText("en_US", "L__CODE__F"));
		Assert.assertEquals("CODE", Lang2am.getText("en_US", "L__CODE__U"));
		Assert.assertEquals("name", Lang2am.getText("en_US", "L__NAME__L"));
		Assert.assertEquals("Code name is required.", Lang2am.getText("en_US", "L__00004__F"));
		Assert.assertEquals("Code Name Is Required.", Lang2am.getText("en_US", "L__00004__E"));
		Assert.assertEquals("All HTTP connection has close.", Lang2am.getText("en_US", "L__00006__F"));
		Assert.assertEquals("All HTTP Connection Has Close.", Lang2am.getText("en_US", "L__00006__E"));
		Assert.assertEquals("all http connection has close.", Lang2am.getText("en_US", "L__00006__L"));
		Assert.assertEquals("ALL HTTP CONNECTION HAS CLOSE.", Lang2am.getText("en_US", "L__00006__U"));
	}

	@Test
	public void nested_replace() throws Exception {
		Assert.assertEquals("code name", Lang2am.getText("en_US", "L__CODENAME__"));
		Assert.assertEquals("code name is required.", Lang2am.getText("en_US", "L__00004__"));
	}

	@Test
	public void param_replace() throws Exception {
		Assert.assertEquals("3 of 5 has completed.", Lang2am.getText("en_US", "L__00005__", "3", "5"));
		Assert.assertEquals("3 of 5 has completed.", Lang2am.getText("en_US", "L__00005__", 3, 5));
		Assert.assertEquals("3 of 5 has completed.", Lang2am.getText("en_US", "L__00005__", BigDecimal.valueOf(3), BigDecimal.valueOf(5)));
		Assert.assertEquals("3000000000 of 5000000000 has completed.", Lang2am.getText("en_US", "L__00005__", BigDecimal.valueOf(3000000000L), BigDecimal.valueOf(5000000000L)));
	}

	@Test
	public void param_as_code_replace() throws Exception {
		Assert.assertEquals("code of name has completed.", Lang2am.getText("en_US", "L__00005__", "L__CODE__", "L__NAME__"));
	}

	@Test
	public void locale_replace() throws Exception {
		Assert.assertEquals("코드", Lang2am.getText("ko_KR", "L__CODE__"));
		Assert.assertEquals("이름", Lang2am.getText("ko_KR", "L__NAME__"));
		Assert.assertEquals("코드이름", Lang2am.getText("ko_KR", "L__CODENAME__"));
		Assert.assertEquals("코드이름은 필수입니다.", Lang2am.getText("ko_KR", "L__00004__"));
		Assert.assertEquals("5개 중 3개가 완료되었습니다.", Lang2am.getText("ko_KR", "L__00005__", "3", "5"));
	}

}
