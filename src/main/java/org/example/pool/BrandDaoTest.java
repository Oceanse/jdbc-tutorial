package org.example.pool;

import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;

public class BrandDaoTest {
    BrandDao brandDao=new BrandDao();
    @Test
    public void testSelectAll() throws SQLException {
        List<Brand> brands = brandDao.selectAll();
        brands.stream().forEach(brand -> System.out.println(brand));
    }

    @Test
    public void testInsert() throws SQLException {
        int count = brandDao.insert("apple", "apple company", 6, "Change the world", 4);
        if (count > 0) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }


    @Test
    public void testUpdate() throws SQLException {
        int count = brandDao.update("抖音", "字节跳动", 77, "Change the life", 44,1);
        if (count > 0) {
            System.out.println("更新成功");
        } else {
            System.out.println("更新失败");
        }
    }

    @Test
    public void testDelete() throws SQLException {
        int count =brandDao.deleteById(1);
        if (count > 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }
}
