package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.AppliedJobByRecruiterResponse;
import fpt.edu.capstone.dto.admin.CommonRecruiterInformationResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.recruiter.RecruiterProfileResponse;
import fpt.edu.capstone.dto.recruiter.RecruiterUpdateProfileRequest;
import fpt.edu.capstone.dto.recruiter.UploadBusinessLicenseRequest;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Notification;
import fpt.edu.capstone.entity.Recruiter;
import fpt.edu.capstone.entity.RequestJoinCompany;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataPagination;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recruiter")
@AllArgsConstructor
public class RecruiterController {

    private static final Logger logger = LoggerFactory.getLogger(RecruiterController.class);

    private final RecruiterService recruiterService;

    private final RecruiterManageService recruiterManageService;

    private final RequestJoinCompanyService requestJoinCompanyService;

    private final CompanyService companyService;

    private final NotificationService notificationService;

    @GetMapping("/recruiter-profile/{userId}")
    public ResponseData getRecruiterProfile(@PathVariable("userId") long userId) {
        try {
            RecruiterProfileResponse recruiterProfile = recruiterManageService.getRecruiterProfile(userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), recruiterProfile);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/insert-recruiter")
    public ResponseData insertRecruiter(@RequestBody long userId) {
        try {
            Optional<Recruiter> recruiter = recruiterService.findRecruiterByUserId(userId);
            if (recruiter.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "That user id is existed", userId);
            }
            Recruiter recruiter1 = recruiterService.insertRecruiter(userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Insert recruiter successful", recruiter1);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @PutMapping("/update-recruiter-profile/{recruiterId}")
    public ResponseData updateProfile(@PathVariable("recruiterId") long recruiterId,
                                      @RequestBody RecruiterUpdateProfileRequest request) {
        try {
            RecruiterProfileResponse profileResponse = recruiterManageService.
                    updateRecruiterInformation(recruiterId, request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.UPDATE_SUCCESSFULLY, profileResponse);
        } catch (Exception ex) {
            String msg = LogUtils.printLogStackTrace(ex);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @GetMapping("/get-detail-cv")
    public ResponseData detailCv() {
        return null;
    }

    @GetMapping("get-list-applied-job")
    public ResponseData getListAppliedJob(long recruiterId) {
        try {
            List<AppliedJobByRecruiterResponse> appliedJobByRecruiterResponses = recruiterService.getListAppliedByForRecruiter(recruiterId);
            System.out.println(appliedJobByRecruiterResponses);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "asd", appliedJobByRecruiterResponses);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    //create request
    @PostMapping("/send-request-join-company") //không cần gửi approval id
    public ResponseData sendRequestJoinCompany(@RequestBody RequestJoinCompany requestJoinCompany) {
        try {
            requestJoinCompanyService.createRequest(requestJoinCompany);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "Send request successful", requestJoinCompany);
        } catch (Exception ex) {
            String msg = LogUtils.printLogStackTrace(ex);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    //fetch request by sender_id
    @GetMapping("/get-sent-request")
    @Operation(summary = "recruiter gửi request xem là nó đã gửi request chưa")
    public ResponseData getSentRequest(@RequestParam long senderId) {
        try {
            Optional<RequestJoinCompany> requestJoinCompanyOp = requestJoinCompanyService.getSentRequest(senderId);
            if (requestJoinCompanyOp.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, requestJoinCompanyOp.get());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.NO_REQUEST_JOIN_COMPANY_SENT);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    //fetch request by creator_id Check xem thang nay co phair creator cua thang nao khong => fetch
    @GetMapping("/get-receive-request")
    @Operation(summary = "recruiter - người tạo company đang có những request nào")
    public ResponseData getReceiveRequest(@RequestParam long approverId) {
        try {
            Optional<List<RequestJoinCompany>> requestJoinCompanyOp = requestJoinCompanyService.getReceiveRequest(approverId);
            if (requestJoinCompanyOp.isPresent()) {
                return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, requestJoinCompanyOp.get());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.NO_REQUEST_JOIN_COMPANY_RECEIVED, null);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @PutMapping("/approve-join-company-request")
    @Operation(summary = "recruiter thực hiện approve/deny request company")
    public ResponseData getReceiveRequest(@RequestBody RequestJoinCompany newRequestJoinCompany) {
        try {

            //Add notification

            String appr = newRequestJoinCompany.getStatus().toLowerCase().equals("deny") ? " đã bị từ chối" : " đã được chấp thuận";
            Optional<Company> company = companyService.findById(newRequestJoinCompany.getCompanyId());
            String content = "Yêu cầu vào công ty "+ company.get().getName() + appr;
            Recruiter r = recruiterService.getRecruiterById(newRequestJoinCompany.getSenderId());
            Notification notification = new Notification(0, r.getUserId(), 3, LocalDateTime.now(), content, false, false, company.get().getId());
            notificationService.insertNotification(notification);



            requestJoinCompanyService.approveRequest(newRequestJoinCompany.getStatus(), newRequestJoinCompany.getId());
            if (!newRequestJoinCompany.getStatus().toLowerCase().equals("deny")) {
                recruiterService.updateCompany(newRequestJoinCompany.getCompanyId(), newRequestJoinCompany.getSenderId());
            }
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), "succesful", newRequestJoinCompany);
        } catch (Exception ex) {
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ex.getMessage());
        }
    }

    @GetMapping("/get-recruiter-by-company")
    public ResponseData getRecruiterByCompany(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        @RequestParam("companyId") long companyId) {
        try {
            ResponseDataPagination pagination = recruiterManageService.getRecruitersOfCompany(pageNo, pageSize, companyId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception ex) {
            String msg = LogUtils.printLogStackTrace(ex);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("find-cv")
    public ResponseData findCv(@RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "6") Integer pageSize,
                               @RequestParam(required = false) String experienceYear,
                               @RequestParam(required = false) String candidateAddress,
                               @RequestParam(required = false) String techStacks) {
        try {
            ResponseDataPagination pagination = recruiterManageService.
                    findCVFilter(pageNo, pageSize, experienceYear, candidateAddress, techStacks);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PutMapping("/upload-business-license")
    public ResponseData uploadBusinessLicense(@ModelAttribute UploadBusinessLicenseRequest request) {
        try {
            Recruiter recruiter = recruiterService.uploadLicense(request.getRecruiterId(), request.getBusinessMultipartFile(), request.getAdditionalMultipartFile());
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, recruiter);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/common-infor/{recruiterId}")
    @Operation(summary = "tên nhà tuyển dụng, đếm tổng số job đã tạo, và xác thực đến bước mấy, tỉ lệ ứng viên apply")
    public ResponseData getCommonInforOfRecruiter(@PathVariable("recruiterId") long recruiterId) {
        try {
            CommonRecruiterInformationResponse response = recruiterManageService.getCommonInforOfRecruiter(recruiterId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, response);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/get-business-license/{id}")
    public ResponseData getBusinessLicense(@PathVariable("id") long recruiterId) {
        try {
            Recruiter recruiter = recruiterService.getRecruiterById(recruiterId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, recruiter);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/total-view-cv/{id}")
    public ResponseData getTotalViewCV(@PathVariable long id){
        try {
            Integer total = recruiterService.getTotalViewCV(id);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, total);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }
}
