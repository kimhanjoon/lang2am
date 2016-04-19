package com.lang2am.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Lang2amProperty {

	private static Properties prop = new Properties();
	static {
		try {
			InputStream stream = Lang2amProperty.class.getClassLoader().getResourceAsStream("lang2am.properties");
			InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
			prop.load(inputStreamReader);
		} catch (IOException e) {
			throw new RuntimeException("lang2am.properties is not found in the classpath", e);
		}
	}

	public static String getValue(final String key) {
		return prop.getProperty(key);
	}
}
