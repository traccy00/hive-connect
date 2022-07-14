package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterBaseOnCompanyResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.service.CompanyService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.service.RequestJoinCompanyService;
import fpt.edu.capstone.service.UserService;
import fpt.edu.capstone.service.impl.CompanyServiceImpl;
import fpt.edu.capstone.service.impl.UserImageService;
import fpt.edu.capstone.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/recruiter")
public class RecruiterController {

    private static final Logger logger = LoggerFactory.getLogger(RecruiterController.class);

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RequestJoinCompanyService requestJoinCompanyService;

    @GetMapping("/recruiter-profile/{userId}")
    public ResponseData getRecruiterProfile(@PathVariable("userId") long userId) {
        try {
            RecruiterProfileResponse recruiterProfile = recruiterService.getRecruiterProfile(userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), recruiterProfile);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
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
    //create request

    @PostMapping("/send-request-join-company") //không cần gửi approval id
    public ResponseData sendRequestJoinCompany(@RequestBody RequestJoinCompany requestJoinCompany) {
        try {
            Optional<Company> company = companyService.findById(requestJoinCompany.getCompanyId());
            if(!company.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Can not find this company to send request", requestJoinCompany.getCompanyId());
            }
            requestJoinCompany.setStatus("Pending");
            requestJoinCompany.setApproverId(company.get().getCreatorId());
            requestJoinCompanyService.createRequest(requestJoinCompany);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Send request successful", requestJoinCompany);
        }catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    //fetch request by sender_id
    @GetMapping("/get-sent-request")
    public ResponseData getSentRequest(@RequestParam long senderId) {
        try {
            Optional<RequestJoinCompany> requestJoinCompanyOp = requestJoinCompanyService.getSentRequest(senderId);
            if(requestJoinCompanyOp.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Successful", requestJoinCompanyOp.get());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "No request was sent", null);
        }catch(Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }
    //fetch request by creator_id Check xem thang nay co phair creator cua thang nao khong => fetch
    @GetMapping("/get-receive-request")
    public ResponseData getReceiveRequest(@RequestParam long approverId) {
        try {
            Optional<List<RequestJoinCompany>> requestJoinCompanyOp = requestJoinCompanyService.getReceiveRequest(approverId);
            if(requestJoinCompanyOp.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Successful", requestJoinCompanyOp.get());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "No request have been received", null);
        }catch(Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @PutMapping("/approve-join-company-request")
    public ResponseData getReceiveRequest(@RequestBody RequestJoinCompany newRequestJoinCompany) {
        try {
            requestJoinCompanyService.approveRequest(newRequestJoinCompany.getStatus(), newRequestJoinCompany.getId());
            if(!newRequestJoinCompany.getStatus().toLowerCase().equals("deny")) {
                recruiterService.updateCompany(newRequestJoinCompany.getCompanyId(), newRequestJoinCompany.getSenderId());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "succesful", newRequestJoinCompany);
        }catch(Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }

    @GetMapping("/get-recruiter-by-company")
    public ResponseDataPagination getRecruiterByCompany(@RequestParam(defaultValue = "1") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam("companyId") long companyId) {
        try {
            List<RecruiterBaseOnCompanyResponse> responseList = new ArrayList<>();
            Page<Recruiter> recruiters = recruiterService.getRecruiterByCompanyId(pageNo, pageSize, companyId);
//            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
//            Page<Job> jobs = jobService.getPopularJobList(pageable);
            if (recruiters.hasContent()) {
                for (Recruiter r : recruiters) {
                    Users user = userService.getUserById(r.getUserId());
                    RecruiterBaseOnCompanyResponse recruiterBaseOnCompanyResponse =
                            new RecruiterBaseOnCompanyResponse(r.getId(), r.getFullName(), r.getAvatarUrl(), r.getFullName(),
                                    r.isGender(), r.getPosition(), r.getLinkedInAccount(), user.getEmail(), user.getPhone());
                    responseList.add(recruiterBaseOnCompanyResponse);
                }
            }
            ResponseDataPagination responseDataPagination = new ResponseDataPagination();
            Pagination pagination = new Pagination();
            responseDataPagination.setData(responseList);
            pagination.setCurrentPage(pageNo);
            pagination.setPageSize(pageSize);
            pagination.setTotalPage(recruiters.getTotalPages());
            pagination.setTotalRecords(Integer.parseInt(String.valueOf(recruiters.getTotalElements())));
            responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
            responseDataPagination.setPagination(pagination);
            return responseDataPagination;
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseDataPagination();
        }
    }

    @GetMapping("/find-cv")
    public ResponseData findCv(@RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam String candidateName,
                               @RequestParam String cvName) {
        try {

            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        }catch(Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage(), null);
        }
    }
}
