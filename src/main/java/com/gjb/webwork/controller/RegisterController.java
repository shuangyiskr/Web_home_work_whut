package com.gjb.webwork.controller;

import com.gjb.webwork.mapper.UserMapper;
import com.gjb.webwork.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RegisterController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // 检查用户名是否已存在
        if (userMapper.getUserByUsername(request.getUsername()) != null) {
            return ResponseEntity.ok().body(new ApiResponse(0, "用户名已存在"));
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setEmail(request.getEmail());
        newUser.setBirthday(request.getBirthday());
        newUser.setAvatar(request.getAvatar());
        newUser.setMoney(0);

        userMapper.insertUser(newUser);

        return ResponseEntity.ok().body(new ApiResponse(1, "注册成功"));
    }

    static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private Date birthday;
        private String avatar;
        private double money;

        // getters and setters

        public double getMoney() {
            return money;
        }
        public void setMoney(double money) {
            this.money = money;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
        public String getAvatar() {
            return avatar;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getUsername() {
            return username;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getPassword() {
            return password;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }
        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
        public Date getBirthday() {
            return birthday;
        }
    }

    static class ApiResponse {
        private int status;
        private String msg;

        public ApiResponse(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }
        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
        public String getMsg() {
            return msg;
        }
        public void setMsg(String msg) {
            this.msg = msg;
        }
        // getters and setters
    }
}
