package fpt.edu.capstone.controller;

import fpt.edu.capstone.entity.Candidate;
import fpt.edu.capstone.entity.Users;
import fpt.edu.capstone.service.CandidateService;
import fpt.edu.capstone.service.IOStorageService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/FileUpLoad")
@AllArgsConstructor
public class FileUpLoadController {

    private final IOStorageService storageService;

    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFileName = storageService.storeFile(file);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Tải thư mục thành công");
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "Không thể tải lên thư mục");
        }
    }
}
