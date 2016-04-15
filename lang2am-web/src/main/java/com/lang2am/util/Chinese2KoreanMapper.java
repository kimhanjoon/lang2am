package com.lang2am.util;

import java.util.HashMap;
import java.util.Map;

import lombok.SneakyThrows;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class Chinese2KoreanMapper {

	private static final int CHINESE_CHARACTER_CODE_POINT_START = 0x3400;
	private static final int CHINESE_CHARACTER_CODE_POINT_END = 0x9FFF;

	// key range :  0x3400 - 0x9FFF
	private Map<Integer, Character> chinese2koreanMap;

	@SneakyThrows
    public Chinese2KoreanMapper() {
		this.chinese2koreanMap = new HashMap<>(CHINESE_CHARACTER_CODE_POINT_END - CHINESE_CHARACTER_CODE_POINT_START);

		String chinese2koreanMapContent = FileUtils.readFileToString(new ClassPathResource("chinese2koreanMap.txt").getFile());

		int currentCodePoint = CHINESE_CHARACTER_CODE_POINT_START;
		for(String string : chinese2koreanMapContent.split(",|\\n")) {

			if( StringUtils.isBlank(string) ) {
				continue;
			}
			if( string.startsWith("#") ) {
				continue;
			}

			chinese2koreanMap.put(currentCodePoint, (char)(Integer.decode(string.trim()).intValue()));
			currentCodePoint++;
		}
    }

    /**
     * change each Chinese character to Korean character
     * ex) 中國 -> 중국
     * @param String that include Chinese character (or not include)
     * @return String that changed to Korean character
     */
    public String toKorean(String chinese) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < chinese.length(); i++) {
    		int codePointAt = chinese.codePointAt(i);
    		if( CHINESE_CHARACTER_CODE_POINT_START <= codePointAt && codePointAt <= CHINESE_CHARACTER_CODE_POINT_END ) {
    			sb.append(this.chinese2koreanMap.get(codePointAt));
    		}
    		else {
    			sb.append(chinese.charAt(i));
    		}
		}
    	return sb.toString();
    }
}
