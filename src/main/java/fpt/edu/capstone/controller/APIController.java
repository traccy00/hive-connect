package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.OCR;
import fpt.edu.capstone.utils.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cv")
public class APIController {
    private OCR ocr = new OCR();
    @GetMapping("/readcv")
    public ResponseData readCV(){
        return ocr.readCV();
    }
}
