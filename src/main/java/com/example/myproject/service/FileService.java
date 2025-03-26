package com.example.myproject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.ArrayList;

@Service
public class FileService {

    // 模拟的文件存储集合，实际项目中请集成文件存储（本地或云存储）
    private List<String> files = new ArrayList<>();

    public void uploadFile(MultipartFile file) throws Exception {
        if(file.isEmpty()) {
            throw new Exception("File is empty!");
        }
        files.add(file.getOriginalFilename());
    }

    public void deleteFile(String fileName) throws Exception {
        if (!files.contains(fileName)) {
            throw new Exception("File not found!");
        }
        files.remove(fileName);
    }

    public List<String> listFiles() {
        return files;
    }
}