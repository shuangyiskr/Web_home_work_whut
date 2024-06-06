package com.gjb.webwork.controller;

import com.gjb.webwork.model.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gjb.webwork.mapper.AuthorMapper;

@RestController
public class DeleteAuthorController {

    @Autowired
    private AuthorMapper authorMapper;
    private static final String SECRET_KEY = "your_secret_key";

    @PostMapping("/deleteAuthor")
    public ResponseEntity<?> deleteAuthor(@RequestBody DeleteAuthorRequest request,@RequestParam("token") String token) {
        try {
            // 解析 token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            // 根据用户名获取用户ID
            Long userId = authorMapper.getUserIdByUsername(username);
            // 获取作者信息
            Author author = authorMapper.getAuthorById(request.getId());
            // 获取Author对应的用户，检查更新权限
            if (!userId.equals(author.getUser_id()))
            {
                return ResponseEntity.status(404).body(new UpdateAuthorController.ApiResponse(0, "用户无权限"));
            }
            // 执行删除作者操作
            authorMapper.deleteAuthor(request.getId());
            return ResponseEntity.ok().body(new ApiResponse(1, "作者删除成功"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(0, "删除作者失败"));
        }
    }

    static class DeleteAuthorRequest {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    static class ApiResponse {
        private int status;
        private String msg;

        public ApiResponse(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        // Getters and setters
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
