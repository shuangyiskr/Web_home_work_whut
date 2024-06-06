package com.gjb.webwork.controller;

import com.gjb.webwork.mapper.AuthorMapper;
import com.gjb.webwork.model.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthorController {

    private static final String SECRET_KEY = "your_secret_key";

    @Autowired
    private AuthorMapper authorMapper;

    @GetMapping("/getAuthor")
    public ResponseEntity<?> getWriter(
            @RequestParam(required = false) Integer current,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String token) {
        try {
            // 解析 token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            // 根据用户名获取用户信息
            Long userId = authorMapper.getUserIdByUsername(username);
            System.out.println(userId);

            // 设置分页参数
            int page = current == null ? 1 : current;
            int size = pageSize == null ? 10 : pageSize;
            int offset = (page - 1) * size;

            // 获取作者列表
            List<Author> authors = authorMapper.getAuthors(userId, name, size, offset);
            System.out.println(authors);
            int total = authorMapper.getAuthorCount(userId, name);
            System.out.println(total);
            return ResponseEntity.ok().body(new ApiResponse(1, "获取成功", authors,total));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse(0, "无效的token", null,0));
        }
    }

    static class ApiResponse {
        private int status;
        private String msg;
        private List<Author> data;
        private int total;

        public ApiResponse(int status, String msg, List<Author> data,int total) {
            this.status = status;
            this.msg = msg;
            this.data = data;
            this.total = total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
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

        public List<Author> getData() {
            return data;
        }

        public void setData(List<Author> data) {
            this.data = data;
        }
    }
}
