package com.demo.csvreader.csvreader.repository;

import com.demo.csvreader.csvreader.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepo extends JpaRepository<FileEntity,Long> {
}
