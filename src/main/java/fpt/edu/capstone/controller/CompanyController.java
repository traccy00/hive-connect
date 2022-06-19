package fpt.edu.capstone.controller;

import fpt.edu.capstone.utils.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {

    @GetMapping("get-list-company")
    public ResponseData getListCompany(){
        return null;
    }
}
