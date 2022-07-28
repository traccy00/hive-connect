package fpt.edu.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.*;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.Report;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataPagination;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/job")
@AllArgsConstructor
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    private final FindJobService findJobService;

    private final CandidateJobService candidateJobService;

    private final RecruiterJobService recruiterJobService;

    private final ModelMapper modelMapper;

    private final CandidateManageService candidateManageService;

    private final AdminManageService adminManageService;

    private final PaymentService paymentService;

    @PostMapping("/create-job")
    public ResponseData createJob(@RequestBody @Valid CreateJobRequest request) {
        try {
            if (request == null) {
                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.DATA_INVALID);
            }
            jobService.createJob(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.CREATE_JOB_SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/find-job")
    public ResponseData searchListJobFilter(@RequestParam(defaultValue = "1") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(defaultValue = "0", value = "fieldId", required = false) long fieldId,
                                            @RequestParam(defaultValue = "0", value = "countryId", required = false) long countryId,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "jobName", required = false) String jobName,
                                            @RequestParam(defaultValue = "0", value = "fromSalary", required = false) long fromSalary,
                                            @RequestParam(defaultValue = "0", value = "toSalary", required = false) long toSalary,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "rank", required = false) String rank,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "workForm", required = false) String workForm,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "workPlace", required = false) String workPlace) {
        try {
            ResponseDataPagination pagination = jobService.searchListJobFilter(pageNo, pageSize, fieldId, countryId,
                    jobName, fromSalary, toSalary, rank, workForm, workPlace);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/list-job-by-recruiter")
    @Operation(summary = "list job theo recruiterId, để hiển thị trên màn job của ứng viên")
    public ResponseData recruiterBuyPackage(@RequestParam(value = "recruiterId") long recruiterId) {
        try {
            List<Job> jobs = jobService.getJobByRecruiterId(recruiterId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS, ResponseMessageConstants.SUCCESS, jobs);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/job-detail/{id}")
    public ResponseData getJobDetail(@PathVariable("id") long jobId, @RequestParam("candidateId") long candidateId) {
        try {
            JobDetailResponse jobDetailResponse = candidateJobService.getJobDetail(jobId, candidateId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), jobDetailResponse);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PutMapping("update-job")
    public ResponseData updateJob(@RequestBody UpdateJobRequest request) {
        try {
            jobService.updateJob(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.UPDATE_JOB_SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @DeleteMapping("/delete-job")
    public ResponseData deleteJob(@RequestParam(value = "id") long jobId) {
        try {
            jobService.deleteJob(jobId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @PostMapping("/apply-job")
    public ResponseData applyJob(@RequestBody AppliedJobRequest request) {
        try {
            findJobService.appliedJob(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS,
                    new ObjectMapper().writeValueAsString(request));
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    //Recruiter: Get list CV applied a job
    @GetMapping("/list-cv-applied-a-job/{jobId}")
    public ResponseData getListCandidateAppliedJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @PathVariable long jobId) {
        try {
            ResponseDataPagination pagination = findJobService.getCvListAppliedJob(pageNo, pageSize, jobId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(),
                    ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

//    @GetMapping("/get-recruiter-posts")
//    public ResponseData getRecruiterPosts(@RequestParam long recruiterId) {
//        try {
//            List<RecruiterPostResponse> responseList = findJobService.getRecruiterPosts(recruiterId);
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
//        }
//    }

    @GetMapping("/popular-job")
    public ResponseData getPopularJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        // get list lấy ra công việc phỏ biến
        try {
            ResponseDataPagination pagination = candidateJobService.getPopularJob(pageNo, pageSize);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/new-job")
    public ResponseData getListNewJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        // get list lấy ra công việc mới nhất
        //sort by created At
        try {
            ResponseDataPagination pagination = candidateJobService.getNewestJob(pageNo, pageSize);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/urgent-job")
    public ResponseData getListUrgentJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        // get list lấy ra công việc cần tuyển gấp
        try {
            ResponseDataPagination pagination = candidateJobService.getUrgentJob(pageNo, pageSize);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/suggest-job/{id}")
    public ResponseData getListSuggestJobByCV(@PathVariable("id") long candidateId) {
        // get list lấy ra công việc gợi ý theo cv của candidate
        //dựa theo major-level của candidate
        try {
            List<JobResponse> listSuggestJob = jobService.getListSuggestJobByCv(candidateId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listSuggestJob);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/remote-job")
    public ResponseData getListRemoteJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            ResponseDataPagination pagination = candidateJobService.getListJobByWorkForm(pageNo, pageSize, "REMOTE");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/fulltime-job")
    public ResponseData getListFulltimeJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            ResponseDataPagination pagination = candidateJobService.getListJobByWorkForm(pageNo, pageSize, "FULLTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/parttime-job")
    public ResponseData getListParttimeJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            ResponseDataPagination pagination = candidateJobService.getListJobByWorkForm(pageNo, pageSize, "PARTTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/job-by-field")
    public ResponseData getJobByCareer(@RequestParam(defaultValue = "1") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "id") long fieldId) {
        try {
            ResponseDataPagination pagination = jobService.getJobByFieldId(pageNo, pageSize, fieldId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @PutMapping("/approve-job")
    public ResponseData approveJob(@RequestBody ApprovalJobRequest approvalJobRequest) {
        try {
            candidateJobService.approveJob(approvalJobRequest);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/get-jobs-of-recruiter")
    public ResponseData getJobListOfRecruiter(@RequestParam(defaultValue = "1") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam("recruiterId") long recruiterId) {
        try {
            ResponseDataPagination pagination = recruiterJobService.getJobOfRecruiter(pageNo, pageSize, recruiterId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/get-jobs-by-company")
    public ResponseData getJobByCompany(@RequestParam(defaultValue = "1") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam("companyId") long companyId) {
        try {
            ResponseDataPagination pagination = candidateManageService.getJobsOfCompany(pageNo, pageSize, companyId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/same-jobs-of-other-companies")
    public ResponseData getSameJobsOfOtherCompanies(@RequestParam long detailJobId) {
        try {
            List<JobResponse> responseList = jobService.getSameJobsOtherCompanies(detailJobId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, responseList);
        } catch (HiveConnectException e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @PostMapping("/report-job/{userId}")
    public ResponseData reportJob(@PathVariable("userId") long userId,
                                  @RequestBody ReportJobRequest request) {
        try {
            Report report = adminManageService.reportJob(request, userId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, report);
        } catch (Exception e) {
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

}
