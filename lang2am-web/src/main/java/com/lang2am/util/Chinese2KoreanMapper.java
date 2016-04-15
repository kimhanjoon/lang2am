package com.lang2am.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class Chinese2KoreanMapper {

	private static final int CHINESE_CHARACTER_CODE_POINT_START = 0x3400;
	private static final int CHINESE_CHARACTER_CODE_POINT_END = 0x9FFF;

	// key range :  0x3400 - 0x9FFF
	Map<Integer, Character> chinese2koreanMap;

    public Chinese2KoreanMapper() {
    	try {
			chinese2koreanMap = new HashMap<>();
			
			String chinese2koreanMapContent = FileUtils.readFileToString(new File("src/main/resources/chinese2koreanMap.txt"));
			
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
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException(e);
		}
    }

    public String toKorean(String chinese) {
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < chinese.length(); i++) {
    		sb.append(this.chinese2koreanMap.get(chinese.codePointAt(i)));
		}
    	return sb.toString();
    }
}
