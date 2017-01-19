package com.lang2am.util;

public class EchoTextSupplier implements TextSupplier {

	@Override
	public String getText(final String locale, final String code) {
		return code;
	}

}
