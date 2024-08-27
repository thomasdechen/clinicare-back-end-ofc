package com.example.clinicarebackend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String save(MultipartFile file) throws IOException {
        Path copyLocation = Paths.get(uploadDir + file.getOriginalFilename());
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        return copyLocation.toString();
    }
}
