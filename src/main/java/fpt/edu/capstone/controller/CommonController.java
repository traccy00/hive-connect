package fpt.edu.capstone.controller;

import fpt.edu.capstone.utils.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {
    @GetMapping("/get-list-career")
    public ResponseData getListCareer(){
        //lấy ra được ngành nghề hiển thị trong thanh combobox
        return null;
    }

    @GetMapping("/get-list-hashtag")
    public ResponseData getListHashtag(){
        //lấy ra được ngành nghề hiển thị trong thanh combobox
        return null;
    }
}
