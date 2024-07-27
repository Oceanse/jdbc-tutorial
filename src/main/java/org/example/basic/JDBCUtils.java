package org.example.basic;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    static String url = null;
    static String username = null;
    static String password = null;

    static {
        try {
            Properties properties = new Properties();
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(is);

            //读取属性
            Class.forName(properties.getProperty("driverClass"));
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取连接对象
     *
     * @return
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, username, password);
    }


    /**
     * 释放资源
     * Jdbc中，数据库连接资源是非常珍贵的，数据库允许的并发访问连接数量有限。因此，当数据库资源使用完毕后，一定要记得释放资源。
     * 哪怕程序出现异常，也需要释放资源。对于这样一个情况，我们需要将资源释放的操作放在finally代码块中。
     * 无论怎样，只要开辟了数据库资源，即使报了异常也要把数据库资源还给系统
     *
     * @param connection
     * @param pstmt
     * @param rs
     */
    public static void close(Connection connection, Statement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            rs = null;
            pstmt = null;
            connection = null;
        }
    }

}

