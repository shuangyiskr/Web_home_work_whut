package com.gjb.webwork.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gjb.webwork.mapper.AuthorMapper;
import com.gjb.webwork.model.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UpdateAuthorController {

    @Autowired
    private AuthorMapper authorMapper;

    private static final String SECRET_KEY = "your_secret_key";

    @PostMapping("/updateAuthor")
    public ResponseEntity<?> updateAuthor(@RequestBody UpdateAuthorRequest request,@RequestParam("token") String token) {
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
                return ResponseEntity.status(401).body(new addAuthorController.ApiResponse(0, "无效的用户"));
            }
            System.out.println(userId);

            // 获取作者信息
            Author author = authorMapper.getAuthorById(request.getId());
            if (author == null) {
                return ResponseEntity.status(404).body(new ApiResponse(0, "作者不存在"));
            }

            // 获取Author对应的用户，检查更新权限
            if (!userId.equals(author.getUser_id()))
            {
                return ResponseEntity.status(404).body(new ApiResponse(0, "用户无权限"));
            }
            // 更新作者信息
            if (request.getName() != null) {
                author.setName(request.getName());
                System.out.println(request.getName());
            }
            if (request.getProvince() != null) {
                author.setProvince(request.getProvince());
                System.out.println(request.getProvince());
            }
            if (request.getCity() != null) {
                author.setCity(request.getCity());
                System.out.println(request.getCity());
            }
            if (request.getAddress() != null) {
                author.setAddress(request.getAddress());
                System.out.println(request.getAddress());
            }
            if (request.getZip_code() != null) {
                author.setZip_code(request.getZip_code());
                System.out.println(request.getZip_code());
            }
            if (request.getAtime() != null) {
                author.setCreatedAt(request.getAtime());
                System.out.println(request.getAtime());
            }
            author.setUpdatedAt(new Date());
            // 更新数据库
            authorMapper.updateAuthor(author);
            return ResponseEntity.ok().body(new ApiResponse(1, "更新成功"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(0, "更新出错"));
        }
    }

    static class UpdateAuthorRequest {
        private int id;
        private String name;
        private String province;
        private String city;
        private String address;
        private String zip_code;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private Date atime;


        // getters and setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

        public Date getAtime() {
            return atime;
        }

        public void setAtime(Date atime) {
            this.atime = atime;
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
