package com.lang2am.util;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class JsonTextSupplier implements TextSupplier {

	private Map<String, Map<String, String>> textMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	public JsonTextSupplier() {
		super();
		textMap = new Gson().fromJson(new InputStreamReader(JsonTextSupplier.class.getClassLoader().getResourceAsStream(Lang2amProperty.getValue("lang2am.json.file"))), Map.class);
	}

	@Override
	public String getText(final String locale, final String code) {
		if( textMap == null ) {
			throw new RuntimeException("TextSupplier has not initialized.");
		}

		// return code itself if there is no text in textMap
		Map<String, String> map = textMap.get(code);
		if( map == null ) {
			return code;
		}
		String text = map.get(locale);
		if( text == null ) {
			return code;	//XXX fallback locale?
		}
		return text;
	}

}
