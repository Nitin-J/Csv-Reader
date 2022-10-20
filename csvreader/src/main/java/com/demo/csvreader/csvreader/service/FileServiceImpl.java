package com.demo.csvreader.csvreader.service;

import com.demo.csvreader.csvreader.entity.FileEntity;
import com.demo.csvreader.csvreader.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepo repository;

    @Override
    public void save(MultipartFile file) {
        try {
            List<FileEntity> tutorials = CSVHelper.csvToData(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream load() {
        List<FileEntity> saveFile = repository.findAll();

        ByteArrayInputStream in = CSVHelper.dataToCsv(saveFile);
        return in;
    }

    @Override
    public List<FileEntity> seeContent() {
        return repository.findAll();
    }
}
