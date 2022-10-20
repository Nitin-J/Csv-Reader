package com.demo.csvreader.csvreader.service;

import com.demo.csvreader.csvreader.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface FileService {
    void save(MultipartFile file);
    ByteArrayInputStream load();
    List<FileEntity> seeContent();
}
