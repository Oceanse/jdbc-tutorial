package org.example.pool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库连接池:  其实就是一个容器，存放数据库的连接。
 * 当系统初始化好了以后，容器被创建，容器中会自动申请一些连接对象，
 * 当用户来访问数据库的时候，从容器中获取连接对象，用户访问完以后，将连接归还至连接池中。
 * <p>
 * 好处：
 * 1、节约资源
 * 2、提高了访问效率
 *
 * Druid首先是一个数据库连接池。Druid是目前最好的数据库连接池，在功能、性能、扩展性方面，都超过其他数据库连接池，
 * 包括DBCP、C3P0、BoneCP、Proxool、JBoss DataSource。
 * 另外，它可以监控数据库访问性能，Druid内置提供了一个功能强大的StatFilter插件，能够详细统计SQL的执行性能，这对于线上分析数据库访问性能有帮助。
 */
public class JDBCUtilsWithPool {

    //JavaSE接口：DataSource
    private static DataSource dataSource;

    static {
        try {
            InputStream resourceAsStream = JDBCUtilsWithPool.class.getClassLoader().getResourceAsStream("druid.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            //Druid DataSource
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Connection connection , Statement pstmt, ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
            if(pstmt!=null){
                pstmt.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            rs=null;
            pstmt=null;
            connection=null;
        }
    }
}
