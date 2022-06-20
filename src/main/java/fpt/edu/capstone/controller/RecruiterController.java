package fpt.edu.capstone.controller;

import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recruiter")
public class RecruiterController {

    private static final Logger logger = LoggerFactory.getLogger(RecruiterController.class);

    @Autowired
    private RecruiterService recruiterService;

    @GetMapping("/recruiter-profile/{userId}")
    public ResponseData getRecruiterProfile(@PathVariable("userId") long userId) {
        try {
            RecruiterProfileResponse recruiterProfile = recruiterService.getRecruiterProfile(userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), recruiterProfile);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @PutMapping("/account-authen-level")
    public ResponseData changeLevelAuthenAccount(){
        //cấp độ xác thực account của recruiter ( update trong db khi đã xác thực xong 1 cấp độ)
        return null;
    }

    @PutMapping("/update-recruiter-profile")
    public ResponseData updateProfile(){
        //cập nhật thông tin profile của rec
        return null;
    }

    @PostMapping("/prove-recruiter")
    public ResponseData insertProveOfRecruiter(){
        //thêm giấy phép kinh doanh or thẻ nhân viên... chứng minh là nhà tuyển dụng
        return null;
    }

    //TODO : Dashboard
    @PostMapping("/create-campaign")
    public ResponseData createCampaign(){
        return null;
    }

    @PutMapping("/update-campaign")
    public ResponseData updateCampaign(){
        return null;
    }

    @DeleteMapping("/delete-campaign")
    public ResponseData deleteCampaign(){
        return null;
    }

    @GetMapping("/get-total-campaign-open")
    public ResponseData totalCampaign(){
        //lấy ra total count các chiến dịch
        return null;
    }

    @GetMapping("/get-detail-campaign")
    public ResponseData detailCampaign(){
        //lấy ra chi tiết chiến dịch
        return null;
    }

    @GetMapping("/get-total-cv-applied")
    public ResponseData totalCVApplied(){
        return null;
    }

    @GetMapping("/get-detail-cv")
    public ResponseData detailCv(){
        return null;
    }
}
