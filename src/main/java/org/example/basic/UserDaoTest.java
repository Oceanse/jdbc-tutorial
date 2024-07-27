package org.example.basic;

import org.example.pojo.User;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Scanner;

public class UserDaoTest {
    public static void main(String[] args) {
        testCreatable();
        testFindByNameAndPwdWithSQLInjection();
//        testFindByNameAndPwd();
        //testInsert();
    }


    static UserDao usersDao = new UserDaoImpl();


    public static void testCreatable(){
        usersDao.creatable();
    }

    /**
     * 插入数据
     */
    public static void testInsert() {
        Scanner input = new Scanner(System.in);

        System.out.println("请输入uid:");
        int uid = input.nextInt();

        System.out.println("请输入用户名:");
        String name = input.next();

        System.out.println("请输入密码:");
        String pwd = input.next();

        int count = usersDao.insert(uid, name, pwd);
        if (count > 0) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }


    /**
     * 根据用户名和密码查询
     */
    public static void testFindByNameAndPwd() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String name = input.next();
        System.out.println("请输入密码:");
        String pwd = input.next();
        User user = usersDao.findByNameAndPwd(name, pwd);
        if (user == null) {
            System.out.println("登录失败");
        } else {
            System.out.println("欢迎您" + user.getUid() + "--" + user.getUname());
        }
    }


    /**
     * 根据用户名和密码查询
     */
    public static void testFindByNameAndPwdWithSQLInjection() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String name = input.next();
        System.out.println("请输入密码:");
        String pwd = input.next();
        User user = usersDao.findByNameAndPwdWithSQLInjection(name, pwd);
        if (user == null) {
            System.out.println("登录失败");
        } else {
            System.out.println("欢迎您" + user.getUid() + "--" + user.getUname());
        }
    }


    /**
     * 查询所有
     */
    @Test
    public static void testFindAll() {
        List<User> users = usersDao.findAll();
        System.out.println("用户数量：" + users.size());
        System.out.println(users);
    }

    /**
     * 插入数据
     */
    @Test
    public static void testUpdate() {
        int count = usersDao.updatePassword(1, "333");
        if (count > 0) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
    }


    /**
     * 删除数据
     */
    @Test
    public static void testDeleteById() {
        int uid = 3;
        int count = usersDao.deleteById(uid);
        if (count > 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

}