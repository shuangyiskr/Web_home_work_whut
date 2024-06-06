package com.gjb.webwork.controller;

import com.gjb.webwork.mapper.UserMapper;
import com.gjb.webwork.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/changepwd")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            return ResponseEntity.ok(new ApiResponse(0, "新密码与确认密码不匹配"));
        }

        int result = userMapper.updatePassword(request.getUsername(), request.getPassword(), request.getNewPassword());
        if (result > 0) {
            return ResponseEntity.ok(new ApiResponse(1, "密码修改成功"));
        } else {
            return ResponseEntity.ok(new ApiResponse(0, "用户名或密码错误"));
        }
    }

    static class ChangePasswordRequest {
        private String username;
        private String password;
        private String newPassword;
        private String confirmNewPassword;

        // Getters and Setters
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

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmNewPassword() {
            return confirmNewPassword;
        }

        public void setConfirmNewPassword(String confirmNewPassword) {
            this.confirmNewPassword = confirmNewPassword;
        }
    }

    static class ApiResponse {
        private int status;
        private String msg;

        public ApiResponse(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        // Getters and Setters
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
    }
}
