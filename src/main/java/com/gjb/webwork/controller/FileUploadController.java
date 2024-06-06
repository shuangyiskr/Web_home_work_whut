package com.gjb.webwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileUploadController {

    @PostMapping("/uploadAvatar")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件不能为空");
        }

        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 保存文件到服务器的文件系统中
            String path_name = "C:/Users/龚家宝2003/IdeaProjects/webWork/static/" + fileName;
            Path path = Paths.get(path_name);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            // 返回上传成功的消息
            return ResponseEntity.ok().body("{\"status\":1,\"msg\":\"文件上传成功\"}");
        } catch (IOException e) {
            e.printStackTrace();
            // 返回上传失败的消息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"status\":0,\"msg\":\"文件上传失败\"}");
        }
    }
}
