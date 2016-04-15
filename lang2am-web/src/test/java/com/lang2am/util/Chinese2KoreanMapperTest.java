package com.lang2am.util;

import org.junit.Assert;
import org.junit.Test;

public class Chinese2KoreanMapperTest {

	@Test
	public void only_chinese() throws Exception {
		Assert.assertEquals("중국", new Chinese2KoreanMapper().toKorean("中國"));
	}
}
