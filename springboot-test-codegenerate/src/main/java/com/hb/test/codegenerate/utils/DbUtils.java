package com.hb.test.codegenerate.utils;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据库工具类
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
public class DbUtils {

    /**
     * 动态创建数据源
     * 
     * @param url
     *            数据库连接
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return 数据源
     */
    public static DataSource dynamicCreateDatasource(String url, String username, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * 执行查询
     * 
     * @param connection
     *            数据库连接
     * @param sql
     *            sql语句
     */
    public static ResultSet executeQuery(Connection connection, String sql) throws Exception {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

}
