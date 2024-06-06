package com.gjb.webwork.model;
import java.util.Date;

public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private String avatar;
    private double money;
    public User() {
    }

    public User(Long id, String username, String password, String email, Date birthday, String avatar,double money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.avatar = avatar;
        this.money = money;
    }

    // Getter and setter methods
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }

    // toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", avatar='" + avatar + '\'' +
                '}';
    }


}
