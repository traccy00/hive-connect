package fpt.edu.capstone.service;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
    String uploadImage(long userId, MultipartFile multipartFile) throws Exception;
}
