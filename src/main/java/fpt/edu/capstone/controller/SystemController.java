package fpt.edu.capstone.controller;

import fpt.edu.capstone.utils.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;

public class SystemController {
    @GetMapping("/get-all")
    public ResponseData getAllInformation(){
        //lấy ra tất cả các thông tin trên hộ thống mình như banner, slogan,logo, footer để hiển thị
        return null;
    }
}
