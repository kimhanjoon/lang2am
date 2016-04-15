package com.lang2am.util;

import static org.junit.Assert.*
import spock.lang.Specification

class Chinese2KoreanMapperSpock extends Specification {

	def "한자를 한글로 변환"(String c, String k) {

		setup:
		def mapper = new Chinese2KoreanMapper()

		expect:
		mapper.toKorean(c) == k

		where:
		c | k
		"中國" | "중국"
		"翻译하다" | "번역하다"
		"after “所有”." | "after “소유”."

	}

}
