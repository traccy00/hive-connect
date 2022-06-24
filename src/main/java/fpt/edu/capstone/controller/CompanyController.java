package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Fields;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/get-list-company")
    public ResponseData getListCompany(){
        try {
            List<Company> careers = companyService.getAllCompany();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, careers);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/find-company")
    public ResponseData findCompany(@RequestParam String companyName){
        return null;
    }

    @GetMapping("/create-company")
    public ResponseData createCompany(){
        return null;
    }
}
