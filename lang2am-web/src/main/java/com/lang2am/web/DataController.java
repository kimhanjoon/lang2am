package com.lang2am.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lang2am.domain.TextVO;
import com.lang2am.service.SearchDAO;
import com.lang2am.service.TextDAO;

@RestController
public class DataController {

	private static final String UNCONFIRMED = "UNCONFIRMED";
	private static final String CONFIRMED = "CONFIRMED";

	@Autowired
	private SearchDAO searchDAO;

	@Autowired
	private TextDAO textDAO;

	@RequestMapping(value="/text", method=RequestMethod.GET)
	public String list(@RequestParam(value="q", required=false) String q) {
		return new Gson().toJson(searchDAO.list(q));
	}

	@RequestMapping(value="/text/{code}/{locale}", method=RequestMethod.PUT)
	public void update(@PathVariable(value="code") String code
			, @PathVariable(value="locale") String locale
			, @RequestParam(value="text") String text
			, HttpServletRequest request) {

		TextVO dvo = TextVO.builder()
				.code(code)
				.locale(locale)
				.text(text)
				.comment("modified by lang2am-web")
				.status(CONFIRMED)
				.modifiedIp(request.getRemoteAddr())
				.build();

		int updatecount = textDAO.update(dvo);
		if( updatecount == 0 ) {

			//TODO 해당 언어만 누락된 거면 insert

			dvo.setComment("inserted by lang2am-web");
			dvo.setCreatedIp(request.getRemoteAddr());
			textDAO.insert(dvo);
		}

		// 기준언어인 영어를 변경한 경우에는 나머지 언어들도 다시 검토한다.
		if( "en_US".equals(locale) ) {
			dvo.setStatus(UNCONFIRMED);
			dvo.setComment("unconfirmed by lang2am-web");
			textDAO.updateStatus(dvo);
		}
	}

	@RequestMapping(value="/text", method=RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public TextVO insert(@RequestParam(value="code") String code
			, @RequestParam(value="ko") String ko
			, @RequestParam(value="en") String en
			, @RequestParam(value="zh") String zh
			, HttpServletRequest request) {

		// 5자리의 숫자만 가능
		if( !StringUtils.isBlank(code) ) {
			if( !StringUtils.containsOnly(code, "0123456789") ) {
				throw new IllegalArgumentException("Code must be digits.");
			}
			if( StringUtils.length(code) != 5 ) {
				throw new IllegalArgumentException("Code must be 5-length.");
			}
			if( code.compareTo("10000") < 0 ) {
				throw new IllegalArgumentException("Code must be over 10000.");
			}
		}

		String newcode = code;

		if( StringUtils.isBlank(code) ) {
			newcode = textDAO.newcode();
		}

		newcode = "GEN_SGP_I_" + newcode;

		TextVO dvo = TextVO.builder()
				.code(newcode)
				.status(UNCONFIRMED)
				.comment("inserted by lang2am-web")
				.createdIp(request.getRemoteAddr())
				.modifiedIp(request.getRemoteAddr())
				.build();

		dvo.setLocale("en_US");
		dvo.setText(en);
		textDAO.insert(dvo);

		dvo.setLocale("ko_KR");
		if( StringUtils.isNotBlank(ko) ) {
			dvo.setText(ko);
		}
		else {
			dvo.setText(en);
		}
		textDAO.insert(dvo);

		dvo.setLocale("zh_CN");
		if( StringUtils.isNotBlank(zh) ) {
			dvo.setText(zh);
		}
		else {
			dvo.setText(en);
		}
		textDAO.insert(dvo);

		return TextVO.builder().code(newcode).build();
	}

}