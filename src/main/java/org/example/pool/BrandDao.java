package org.example.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/weixin_62511863/article/details/126661641
 */
public class BrandDao {

    /**
     * 查询所有
     *
     * @return
     * @throws SQLException
     */
    public List<Brand> selectAll() throws SQLException {
        //获取连接池对象
        Connection connection = JDBCUtilsWithPool.getConnection();
        //获取执行sql的pstmt对象
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tb_brand");
        //执行SQL
        ResultSet rs = preparedStatement.executeQuery();
        List<Brand> brands = new ArrayList<>();
        Brand brand = null;
        //处理结果
        while (rs.next()) {
            //获取数据
            int id = rs.getInt("id");
            String brandName = rs.getString("brand_name");
            String companyName = rs.getString("company_name");
            int ordered = rs.getInt("ordered");
            String description = rs.getString("description");
            int status = rs.getInt("status");
            //封装Brand对象
            brand = new Brand();
            brand.setId(id);
            brand.setBrandName(brandName);
            brand.setCompanyName(companyName);
            brand.setOrdered(ordered);
            brand.setDescription(description);
            brand.setStatus(status);
            //装载集合
            brands.add(brand);
        }
        JDBCUtilsWithPool.close(connection, preparedStatement, rs);
        return brands;
    }


    /**
     * 插入数据
     *
     * @param brandName
     * @param companyName
     * @param ordered
     * @param description
     * @param status
     * @return
     * @throws SQLException
     */
    public int insert(String brandName, String companyName, int ordered, String description, int status) throws SQLException {
        Connection connection = JDBCUtilsWithPool.getConnection();
        String sql = "insert into tb_brand(brand_name, company_name, ordered, description, status) values(?,?,?,?,?);";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, brandName);
        pstmt.setString(2, companyName);
        pstmt.setInt(3, ordered);
        pstmt.setString(4, description);
        pstmt.setInt(5, status);
        int count = pstmt.executeUpdate();  //影响的行数
        JDBCUtilsWithPool.close(connection, pstmt, null);
        return count;
    }

    /**
     * 更新数据
     *
     * @param brandName
     * @param companyName
     * @param ordered
     * @param description
     * @param status
     * @param id
     * @return
     * @throws SQLException
     */
    public int update(String brandName, String companyName, int ordered, String description, int status, int id) throws SQLException {
        Connection connection = JDBCUtilsWithPool.getConnection();

        String sql = " update tb_brand\n" +
                "         set brand_name  = ?,\n" +
                "         company_name= ?,\n" +
                "         ordered     = ?,\n" +
                "         description = ?,\n" +
                "         status      = ?\n" +
                "     where id = ?";

        //预编译
        PreparedStatement pstmt = connection.prepareStatement(sql);

        //设置参数
        pstmt.setString(1, brandName);
        pstmt.setString(2, companyName);
        pstmt.setInt(3, ordered);
        pstmt.setString(4, description);
        pstmt.setInt(5, status);
        pstmt.setInt(6, id);

        //执行SQL
        int count = pstmt.executeUpdate();  //影响的行数

        //释放资源
        JDBCUtilsWithPool.close(connection, pstmt, null);
        return count;
    }


    /**
     * 删除数据
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteById(int id) throws SQLException {
        Connection connection = JDBCUtilsWithPool.getConnection();
        String sql = "delete from tb_brand where id = ?";
        //预编译
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);//执行SQL
        int count = pstmt.executeUpdate();  //影响的行数
        return count;
    }

}
