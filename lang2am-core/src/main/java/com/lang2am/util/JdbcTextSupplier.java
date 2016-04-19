package com.lang2am.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import lombok.SneakyThrows;

public class JdbcTextSupplier implements TextSupplier {

	private final Connection conn;
	private final PreparedStatement pstmt;
	private final String sql = "SELECT `TEXT` FROM `TB_TEXT` WHERE `CODE` = ? AND `LOCALE` = ?";

	@SneakyThrows
	public JdbcTextSupplier() {
		super();

		String driverClassName = Lang2amProperty.getValue("lang2am.datasource.driverClassName");
		String url = Lang2amProperty.getValue("lang2am.datasource.url");
		String username = Lang2amProperty.getValue("lang2am.datasource.username");
		String password = Lang2amProperty.getValue("lang2am.datasource.password");

		Class.forName(driverClassName);
		conn = DriverManager.getConnection(url, username, password);
		pstmt = conn.prepareStatement(sql);
	}

	@SneakyThrows
	@Override
	public String getText(final String locale, final String code) {

		pstmt.clearParameters();
		pstmt.setString(1, code);
		pstmt.setString(2, locale);

		try(ResultSet rs = pstmt.executeQuery();) {
			while(rs.next()){
				return rs.getString("TEXT");
			}
		}
		return code;	//TODO fallback ??
	}

}
