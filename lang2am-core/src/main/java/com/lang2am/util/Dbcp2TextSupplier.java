package com.lang2am.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import lombok.SneakyThrows;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class Dbcp2TextSupplier implements TextSupplier {

	private final DataSource dataSource;
	private final String sql = "SELECT `TEXT` FROM `TB_TEXT` WHERE `CODE` = ? AND `LOCALE` = ?";

	@SneakyThrows
	public Dbcp2TextSupplier() {
		super();

		String driverClassName = Lang2amProperty.getValue("lang2am.datasource.driverClassName");
		Class.forName(driverClassName);

		String url = Lang2amProperty.getValue("lang2am.datasource.url");
		String username = Lang2amProperty.getValue("lang2am.datasource.username");
		String password = Lang2amProperty.getValue("lang2am.datasource.password");
		dataSource = setupDataSource(url, username, password);

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

	private static DataSource setupDataSource(String connectURI, String username, String password) {

		// a ConnectionFactory that the pool will use to create Connections.
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI, username, password);

		// the PoolableConnectionFactory, which wraps the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);

		// a ObjectPool that serves as the actual pool of connections.
		// We'll use a GenericObjectPool instance, although any ObjectPool implementation will suffice.
		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);

		// Set the factory's pool property to the owning pool
		poolableConnectionFactory.setPool(connectionPool);

		// Finally, we create the PoolingDriver itself, passing in the object pool we created.
		PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(connectionPool);

		return dataSource;
	}
}
