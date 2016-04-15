package com.lang2am.util;

import org.junit.Assert;
import org.junit.Test;

import com.lang2am.util.Chinese2KoreanMapper;

public class Chinese2KoreanMapperTest {
	
	@Test
	public void testName() throws Exception {
		Assert.assertEquals("중국", new Chinese2KoreanMapper().toKorean("中國"));
	}
}
