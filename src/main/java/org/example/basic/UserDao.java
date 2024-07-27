package org.example.basic;

import org.example.pojo.User;

import java.util.List;

public interface UserDao {
     List<User> findAll();
     User findByNameAndPwd(String name, String pwd);
     User findByNameAndPwdWithSQLInjection(String name, String pwd);
     int insert(int uid, String name, String pwd);
     int updatePassword(int uid, String newPassword);
     int deleteById(int uid);
     void creatable();
}
