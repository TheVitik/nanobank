package com.thevitik.nanobank.service.utils;

import com.thevitik.nanobank.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements AutoCloseable {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = String.format("jdbc:mysql://%s:%s/%s", DatabaseConfig.HOST, DatabaseConfig.PORT, DatabaseConfig.DATABASE);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connection = DriverManager.getConnection(url, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
        }

        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
