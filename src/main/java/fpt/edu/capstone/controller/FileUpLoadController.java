package fpt.edu.capstone.controller;

import fpt.edu.capstone.service.IOStorageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/FileUpLoad")
public class FileUpLoadController {

    @Autowired
    private IOStorageService storageService;

    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFileName = storageService.storeFile(file);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Upload file success");
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), "Can not upload file");
        }
    }
}
//    @RequestMapping(value = "/file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseData uploadFile(@RequestParam("file")MultipartFile file)