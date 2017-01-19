package com.lang2am.util;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class Lang2am {

	private static final TextSupplier textSupplier;
	static {
		if( "JSON".equals(Lang2amProperty.getValue("lang2am.supplier.type")) ) {
			textSupplier = new JsonTextSupplier();
		}
		else if( "JDBC".equals(Lang2amProperty.getValue("lang2am.supplier.type")) ) {
			textSupplier = new DbcpTextSupplier();
		}
		else if( "DBCP2".equals(Lang2amProperty.getValue("lang2am.supplier.type")) ) {
			textSupplier = new Dbcp2TextSupplier();
		}
		else {
			textSupplier = new EchoTextSupplier();
		}
	}

	private static final Cache<String, String> textCache;
	static {
		final String CACHE_NAME = "code:locale";
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache(CACHE_NAME, CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100)))
				.build(true);
		textCache = cacheManager.getCache(CACHE_NAME, String.class, String.class);
	}

	private Lang2am() {
	}

	public static String getText(final String locale, final String code, final Object... param) {

		// remove transform option
		String codeWithoutOption = code;
		if( code.endsWith("__F") || code.endsWith("__E") || code.endsWith("__U") || code.endsWith("__L") ) {
			codeWithoutOption = code.substring(0, code.length() - 1);
		}

		String text = null;

		// check if text is cached
		String key = codeWithoutOption + ":" + locale;
		if( textCache.containsKey(key) ) {
			text = textCache.get(key);
		}

		// retrieve text
		text = textSupplier.getText(locale, codeWithoutOption );
		if( text == null ) {
			return code;
		}

		// cache text
		textCache.put(key, text);

		// nested code
		while( text.contains("{{") && text.contains("}}") ) {
			String nestedCode = text.substring(text.indexOf("{{") + 2, text.indexOf("}}"));
			text = text.replace("{{" + nestedCode + "}}", textSupplier.getText(locale, nestedCode));
		}

		// param
		for (int i = 0; i < param.length; i++) {
			text = text.replace("${" + i + "}", textSupplier.getText(locale, param[i].toString()));
		}

		// transform
		if( code.endsWith("__F") ) {
			text = Character.toTitleCase(text.charAt(0)) + text.substring(1);
		}
		else if( code.endsWith("__E") ) {
			text = capitalize(text);
		}
		else if( code.endsWith("__U") ) {
			text = text.toUpperCase();
		}
		else if( code.endsWith("__L") ) {
			text = text.toLowerCase();
		}

		return text;
	}

	private static String capitalize(final String str) {

		if( str == null ) {
			return str;
		}

		final char[] buffer = str.toCharArray();
		boolean capitalizeNext = true;
		for (int i = 0; i < buffer.length; i++) {
			final char ch = buffer[i];
			if( Character.isWhitespace(ch) ) {
				capitalizeNext = true;
			}
			else if (capitalizeNext) {
				buffer[i] = Character.toTitleCase(ch);
				capitalizeNext = false;
			}
		}
		return new String(buffer);
	}

}
