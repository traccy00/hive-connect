package fpt.edu.capstone.service;

import org.springframework.web.multipart.MultipartFile;

public interface IOStorageService {
    public String storeFile(MultipartFile file);
    public byte[] readFileContent(String fileName);
}
