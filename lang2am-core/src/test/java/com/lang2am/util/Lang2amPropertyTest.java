package com.lang2am.util;

import org.junit.Assert;
import org.junit.Test;

public class Lang2amPropertyTest {

	@Test
	public void testName() throws Exception {

		Assert.assertEquals("JSON", Lang2amProperty.getValue("lang2am.supplier.type"));
	}
}
