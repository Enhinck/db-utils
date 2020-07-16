package com.enhinck.db.util;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Data
@Builder
@Slf4j
public class Database {
    private String driver;
    private String url;
    private String username;
    private String password;

    public Database(){}
    public Database(String driver, String url, String username, String password) {
        super();
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Database(String url, String username, String password) {
        super();
        this.driver = MYSQL_DRIVER;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    public Connection getConnection() {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username,
                    password);
            return conn;
        } catch (Exception e) {
            log.info("getConnection error:{}", e);
            return null;
        }
    }
}
