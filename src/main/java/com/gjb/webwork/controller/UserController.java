package com.gjb.webwork.controller;

import com.gjb.webwork.mapper.UserMapper;
import com.gjb.webwork.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {

    private static final String SECRET_KEY = "your_secret_key";

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(@RequestParam String token) {
        try {
            // 解析 token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            // 根据用户名获取用户信息
            User user = userMapper.getUserByUsername(username);
            if (user != null) {
                return ResponseEntity.ok().body(new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getMoney(),
                        user.getBirthday(),
                        user.getAvatar()
                ));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(0, "用户未找到", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse(0, "无效的token", null));
        }
    }

    static class UserResponse {
        private long id;
        private String username;
        private String email;
        private double money;
        private Date birthday;
        private String avatar;

        public UserResponse(long id, String username, String email, double money, Date birthday, String avatar) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.money = money;
            this.birthday = birthday;
            this.avatar = avatar;
        }

        // getters and setters
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
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
    }

    static class ApiResponse {
        private int status;
        private String msg;
        private String token;

        public ApiResponse(int status, String msg, String token) {
            this.status = status;
            this.msg = msg;
            this.token = token;
        }

        // getters and setters
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
