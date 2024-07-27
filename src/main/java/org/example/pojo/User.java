package org.example.pojo;

public class User {
    private String uname;
    private String upwd;
    private int uid;

    public User() {
    }

    public User(int uid, String uname, String upwd) {
        this.uname = uname;
        this.upwd = upwd;
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "uname='" + uname + '\'' +
                ", upwd='" + upwd + '\'' +
                ", uid=" + uid +
                '}';
    }
}
