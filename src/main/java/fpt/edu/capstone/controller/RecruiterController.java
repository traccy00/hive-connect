package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/insert-recruiter")
    public ResponseData insertRecruiter(@RequestBody long userId) {
        try{
            Optional<Recruiter> recruiter = recruiterService.findRecruiterByUserId(userId);
            if(recruiter.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "That user id is existed", userId);
            }
            Recruiter recruiter1 = recruiterService.insertRecruiter(userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Insert recruiter successful", recruiter1);
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PutMapping("/account-authen-level")
    public ResponseData changeLevelAuthenAccount(){
        //cấp độ xác thực account của recruiter ( update trong db khi đã xác thực xong 1 cấp độ)
        return null;
    }


    @PutMapping("/update-recruiter-profile")
    public ResponseData updateProfile(@RequestBody RecruiterUpdateProfileRequest recruiterUpdateProfileRequest){
        try{
            Optional<Recruiter> recruiter = recruiterService.findById(recruiterUpdateProfileRequest.getId());
            if(!recruiter.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS,"Can not find this recruiter", recruiterUpdateProfileRequest.getId());
            }
            recruiterService.updateRecruiterInformation(recruiterUpdateProfileRequest);
            return new ResponseData(Enums.ResponseStatus.SUCCESS,"update recruiter successfull", recruiterService.findById(recruiterUpdateProfileRequest.getId()).get());
        }catch (Exception ex){
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
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

    @GetMapping("get-list-applied-job")
    public ResponseData getListAppliedJob(long recruiterId) {
        List<AppliedJobByRecruiterResponse> appliedJobByRecruiterResponses = recruiterService.getListAppliedByForRecruiter(recruiterId);
        System.out.println(appliedJobByRecruiterResponses);
        return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "asd", appliedJobByRecruiterResponses);
    }
}
