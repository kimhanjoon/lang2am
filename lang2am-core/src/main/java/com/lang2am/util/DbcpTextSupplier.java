package com.lang2am.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import lombok.SneakyThrows;

public class DbcpTextSupplier implements TextSupplier {

	private final DataSource dataSource;
	private final String sql = "SELECT `TEXT` FROM `TB_TEXT` WHERE `CODE` = ? AND `LOCALE` = ?";

	@SneakyThrows
	public DbcpTextSupplier() {
		super();

		String driverClassName = Lang2amProperty.getValue("lang2am.datasource.driverClassName");
		Class.forName(driverClassName);

		String url = Lang2amProperty.getValue("lang2am.datasource.url");
		String username = Lang2amProperty.getValue("lang2am.datasource.username");
		String password = Lang2amProperty.getValue("lang2am.datasource.password");
		
		BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        
		dataSource = ds;
	}

	@SneakyThrows
	@Override
	public String getText(final String locale, final String code) {

		try(
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setString(1, code);
			pstmt.setString(2, locale);

			try(
				ResultSet rs = pstmt.executeQuery();
			) {
				while(rs.next()){
					return rs.getString("TEXT");
				}
			}
		}

		return code;	//TODO fallback ??
	}

}
