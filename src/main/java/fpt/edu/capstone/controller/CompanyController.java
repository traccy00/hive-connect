package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.TopCompanyResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.company.CompanyInformationResponse;
import fpt.edu.capstone.dto.company.CreateCompanyRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.impl.ImageService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/company")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;

    private final ImageService imageService;

    private final AppliedJobService appliedJobService;

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

    @PostMapping("/create-company")
    public ResponseData createCompany(@RequestBody CreateCompanyRequest request){
        try {
            Company company = companyService.createCompany(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, company);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/company-information") //get information of company by id
    public ResponseData getCompanyInformation(@RequestParam long companyId) {
        try{
            Optional<Company> companyOp = companyService.findById(companyId);
            if(companyOp.isPresent()) {
                Company tmp = companyOp.get();
                CompanyInformationResponse company = new CompanyInformationResponse();
                company.setAddress(tmp.getAddress());
//                company.setAvatar(tmp.getAvatar());
                company.setDescription(tmp.getDescription());
                company.setEmail(tmp.getEmail());
                company.setFieldWork(tmp.getFieldWork());
                company.setId(tmp.getId());
                company.setName(tmp.getName());
                company.setMapUrl(tmp.getMapUrl());
                company.setPhone(tmp.getPhone());
                company.setNumberEmployees(tmp.getNumberEmployees());
                company.setWebsite(tmp.getWebsite());
                company.setTaxCode(tmp.getTaxCode());
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Successful", company);
            }else {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Can not find this company", companyId);
            }

        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @GetMapping("/get-top-12-recruitment-companies")
    public ResponseData getTop12Companies() {
        try {
            List<TopCompanyResponse> topCompanies = appliedJobService.getTop12Companies();
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, topCompanies);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }
}
