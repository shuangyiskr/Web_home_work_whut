package com.gjb.webwork.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gjb.webwork.mapper.AuthorMapper;
import com.gjb.webwork.model.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class addAuthorController {

    private static final String SECRET_KEY = "your_secret_key";

    @Autowired
    private AuthorMapper authorMapper;

    @PostMapping("/addAuthor")
    public ResponseEntity<?> addAuthor(@RequestBody AddAuthorRequest request, @RequestParam("token") String token) {
        try {
            // 解析 token
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            // 根据用户名获取用户ID
            Long userId = authorMapper.getUserIdByUsername(username);

            if (userId == null) {
                return ResponseEntity.status(401).body(new ApiResponse(0, "无效的用户"));
            }
            System.out.println(userId);

            // 创建作者对象
            Author author = new Author();
            author.setUser_id(userId);
            author.setName(request.getName());
            author.setProvince(request.getProvince());
            author.setCity(request.getCity());
            author.setAddress(request.getAddress());
            author.setZip_code(request.getZipCode());
            author.setCreatedAt(new Date());
            author.setUpdatedAt(request.getAtime());
            // 插入数据库
            authorMapper.insertAuthor(author);

            return ResponseEntity.ok().body(new ApiResponse(1, "添加成功"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse(0, "出错"));
        }
    }

    static class AddAuthorRequest {
        private String name;
        private String province;
        private String city;
        private String address;
        @JsonProperty("zip_code")
        private String zipCode;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date atime;

        public Date getAtime() {
            return atime;
        }
        public void setAtime(Date atime) {
            this.atime = atime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }

    static class ApiResponse {
        private int status;
        private String msg;

        public ApiResponse(int status, String msg) {
            this.status = status;
            this.msg = msg;
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
    }
}
