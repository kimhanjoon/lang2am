package com.lang2am.util;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class Lang2amTag extends SimpleTagSupport {

	private String code;
	public void setCode(String code) {
		this.code = code;
	}

	private String param;
	public void setParam(String param) {
		this.param = param;
	}

	@Override
	public void doTag() throws JspException, IOException {

		JspContext context = getJspContext();
		JspWriter out = context.getOut();

		String locale = (String) context.getAttribute("lang2am-locale", PageContext.SESSION_SCOPE);

		if( this.param != null ) {
			Object[] paramArray = this.param.split(",");
			out.print(Lang2am.getText(locale, this.code, paramArray));
		}
		else {
			out.print(Lang2am.getText(locale, this.code));
		}
	}


}
