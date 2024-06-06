package com.gjb.webwork.controller;

import com.gjb.webwork.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.gjb.webwork.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    private static final String SECRET_KEY = "your_secret_key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 根据用户名和密码进行登录验证
        // 这里可以调用你的登录逻辑，比如验证用户名和密码是否匹配
        if (isValidLogin(request.getUsername(), request.getPassword())) {
            // 登录成功，生成 token
            String token = Jwts.builder()
                    .setSubject(request.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
            System.out.println(token);
            // 返回 token
            return ResponseEntity.ok().body(new ApiResponse(1, "登录成功", token));
        } else {
            // 登录失败
            return ResponseEntity.ok().body(new ApiResponse(0, "用户名或密码错误",""));
        }
    }

    private boolean isValidLogin(String username, String password) {
        // 在这里实现登录验证逻辑，可以根据实际情况连接数据库或者其他方式验证
        // 用户名为空
        if (username == null) {
            return false;
        }
        else
        {
            System.out.println(username);
            // 首先数据库取出的user不为空
            if (userMapper.getUserByUsername(username)==null)
            {
                return false;
            }
            else
                // 在不为空的情况下比较
                return userMapper.getUserByUsername(username).getPassword().equals(password);
        }
    }

    static class LoginRequest {
        private String username;
        private String password;

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
