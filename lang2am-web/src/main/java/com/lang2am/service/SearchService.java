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

	private String makeCodeSnippet(String template, Map map) {
		String codeSnippet = StringUtils.replace(template, "{{code}}", (String) map.get("code"));
		codeSnippet = StringUtils.replace(codeSnippet, "{{text}}", (String) map.get("textEn"));
		return codeSnippet;
	}

	@Autowired
	Chinese2KoreanMapper chinese2KoreanMapper;

	public Map<String, Object> list(String q) {

		List<Map> list = searchDAO.list(q);

		for (Map<String, Object> map : list) {
			map.put("codeSnippetJavascript", makeCodeSnippet(template_javascript, map));
			map.put("codeSnippetHandlebars", makeCodeSnippet(template_handlebars, map));
			map.put("codeSnippetJsp", makeCodeSnippet(template_jsp, map));
			map.put("codeSnippetJavaexception", makeCodeSnippet(template_javaexception, map));
			map.put("codeSnippetJava", makeCodeSnippet(template_java, map));

			map.put("textZhInKo", chinese2KoreanMapper.toKorean((String) map.get("textZh")));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("textlist", list);
		map.put("total", searchDAO.count(q));

		return map;
	}
}
