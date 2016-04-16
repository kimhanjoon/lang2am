package com.lang2am.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lang2am.util.Chinese2KoreanMapper;

@Component
public class SearchService {

	@Autowired
	private SearchDAO searchDAO;

	@Value("${template.javascript}")
	String template_javascript;

	@Value("${template.handlebars}")
	String template_handlebars;

	@Value("${template.java}")
	String template_java;

	@Value("${template.javaexception}")
	String template_javaexception;

	@Value("${template.jsp}")
	String template_jsp;

	private String makeTemplete(String templete, Map map) {
		String template = StringUtils.replace(templete, "{{code}}", (String) map.get("code"));
		template = StringUtils.replace(templete, "{{text}}", (String) map.get("textEn"));
		return template;
	}

	@Autowired
	Chinese2KoreanMapper chinese2KoreanMapper;

	public Map<String, Object> list(String q) {

		List<Map> list = searchDAO.list(q);

		for (Map<String, Object> map : list) {
			map.put("templateJavascript", makeTemplete(template_javascript, map));
			map.put("templateHandlebars", makeTemplete(template_handlebars, map));
			map.put("templateJsp", makeTemplete(template_jsp, map));
			map.put("templateJavaexception", makeTemplete(template_javaexception, map));
			map.put("templateJava", makeTemplete(template_java, map));

			map.put("textZhInKo", chinese2KoreanMapper.toKorean((String) map.get("textZh")));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("textlist", list);
		map.put("total", searchDAO.count(q));

		return map;
	}
}
