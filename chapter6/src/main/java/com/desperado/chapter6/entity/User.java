package com.desperado.chapter6.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "t_user1")
public class User implements Serializable {
    /**
     * TABLE： 使用一个特定的数据库表格来保存主键
     * SEQUENCE： 根据底层数据库的序列来生成主键，条件是数据库支持序列。这个值要与generator一起使用，
     *              generator 指定生成主键使用的生成器（可能是orcale中自己编写的序列）。
     * IDENTITY： 主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
     * AUTO： 主键由程序控制，也是GenerationType的默认值。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;
    //忽略本字段映射
    @Transient
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id,String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
