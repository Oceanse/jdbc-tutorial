package org.example.basic;

import org.example.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public void creatable() {
        try {

            Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();

            // Check if the table exists and create it if not
            String checkTableExistsQuery = "SHOW TABLES LIKE 'user'";
            var resultSet = statement.executeQuery(checkTableExistsQuery);

            if (!resultSet.next()) {
                // Table does not exist, create it
                String createTableQuery = "CREATE TABLE user (" +
                        "uid INT AUTO_INCREMENT PRIMARY KEY, " +
                        "uname VARCHAR(255), " +
                        "upwd VARCHAR(255)" +
                        ")";
                statement.executeUpdate(createTableQuery);
                System.out.println("Table 'user' created successfully.");
            } else {
                System.out.println("Table 'user' already exists.");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("SQL error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public int insert(int uid, String name, String pwd) {
        int count = 0;
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "INSERT INTO user VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, pwd);
            count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    /**
     * sql注入：先传参，再编译，传入的参数可以通过添加关键字改变原有sql的语义
     * 当用户传入的数据中包含SQL关键字时，就有可能通过这些关键字改变SQL语句的语义，从而执行一些特殊的操作，这样的攻击方式就叫做SQL注入攻击。
     * SQL注入产生的原因就是在于用户先对SQL整合，然后再发送到数据库进行编译运行,编译前进行sql拼凑就存在添加关键字改变SQL语句的语义
     * 的机会
     *
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public User findByNameAndPwdWithSQLInjection(String name, String pwd) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = JDBCUtils.getConnection();
            stmt = connection.createStatement();//获取发送SQL语句的对象
            String sql = "SELECT * FROM user WHERE uname='" + name + "' AND upwd = '" + pwd + "' or '1' = '1';";
            rs = stmt.executeQuery(sql);//获取结果集
            if (rs.next()) {
                user = new User();
                int uid = rs.getInt("uid");
                String uname = rs.getString("uname");
                String upwd = rs.getString("upwd");
                user.setUid(uid);
                user.setUname(uname);
                user.setUpwd(upwd);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(connection, stmt, rs);
        }
        return user;
    }


    /**
     * sql注入：先传参，再编译，传入的参数可以通过添加关键字改变原有sql的语义
     * 当用户传入的数据中包含SQL关键字时，就有可能通过这些关键字改变SQL语句的语义，从而执行一些特殊的操作，这样的攻击方式就叫做SQL注入攻击。
     * SQL注入产生的原因就是在于用户先对SQL整合，然后再发送到数据库进行编译运行,编译前进行sql拼凑就存在添加关键字改变SQL语句的语义
     * 的机会
     * <p>
     * 先编译，再传参
     * 编译就是sql的语义结构会被确定，查询时参数仅仅是数据，而不是SQL代码的一部分。
     * SQL语句在用户输入参数前就进行编译，然后再进行数据填充
     * 使用对象关系映射（ORM）框架（如 Hibernate 或 JPA）可以帮助自动处理 SQL 注入问题。ORM 框架通常使用预编译语句来与数据库交互。
     *
     * 如果使用预编译语句（Prepared Statements），即使用户尝试进行SQL拼接注入，如' OR '1' = '1'，这种注入攻击不会影响到最终执行的查询，
     * 因为参数值被当作数据处理，而不是SQL代码的一部分,这样，即使用户输入了恶意内容，也不会导致SQL注入问题。
     *
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public User findByNameAndPwd(String name, String pwd) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = JDBCUtils.getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM user WHERE uname=? AND upwd=?");
            pstmt.setString(1, name);//1代表第一个参数
            pstmt.setString(2, pwd);//2代表第二个参数
            rs = pstmt.executeQuery();//获取结果集
            if (rs.next()) {
                user = new User();
                int uid = rs.getInt("uid");
                String uname = rs.getString("uname");
                String upwd = rs.getString("upwd");
                user.setUid(uid);
                user.setUname(uname);
                user.setUpwd(upwd);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(connection, pstmt, rs);
        }
        return user;
    }


    @Override
    public List<User> findAll() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            connection = JDBCUtils.getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM user");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int uid = rs.getInt("uid");
                String uname = rs.getString("uname");
                String upwd = rs.getString("upwd");
                users.add(new User(uid, uname, upwd));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(connection, pstmt, rs);
        }
        return users;
    }


    @Override
    public int updatePassword(int uid, String newPassword) {
        int count = 0;
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "UPDATE user SET upwd = ? WHERE uid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, uid);
            count = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int deleteById(int uid) {
        int count;
        Connection connection;
        PreparedStatement pstmt = null;
        try {
            connection = JDBCUtils.getConnection();
            pstmt = connection.prepareStatement("DELETE FROM user WHERE uid=?");
            pstmt.setInt(1, uid);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
