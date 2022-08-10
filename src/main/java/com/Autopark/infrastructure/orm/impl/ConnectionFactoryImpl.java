package com.Autopark.infrastructure.orm.impl;

import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.infrastructure.core.annotations.Property;
import com.Autopark.infrastructure.orm.ConnectionFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;

    @Property("username")
    private String username;

    @Property("password")
    private String password;

    private Connection connection;

    public ConnectionFactoryImpl() {
    }

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        DriverManager.getConnection(url, username, password);
    }


    @SneakyThrows
    @Override
    public Connection getConnection() {
        if (!connection.isClosed()) {
            return connection;
        }

        return DriverManager.getConnection(url, username, password);
    }
}
