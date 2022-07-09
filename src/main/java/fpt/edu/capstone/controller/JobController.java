package fpt.edu.capstone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.*;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/job")
@AllArgsConstructor
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    private final FindJobService findJobService;

    private final CandidateJobService candidateJobService;

    private final RecruiterJobService recruiterJobService;

    private final RecruiterService recruiterService;

    private final JobHashTagService jobHashTagService;

    private final CompanyService companyService;

    private final FieldsService fieldsService;

    private final ModelMapper modelMapper;

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
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "workPlace", required = false) String workPlace){
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

    @GetMapping("/job-detail/{id}")
    public ResponseData getJobDetail(@PathVariable("id") long jobId, @RequestParam("candidateId") long candidateId){
        try {
            JobDetailResponse jobDetailResponse = candidateJobService.getJobDetail(jobId, candidateId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), jobDetailResponse);
        }catch (Exception e){
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

//    @DeleteMapping("/delete-job")
//    public ResponseData deleteJob(@RequestParam(value = "id") long jobId) {
//        try {
//            jobService.deleteJob(jobId);
//            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.UPDATE_CATEGORY_SUCCESS);
//        } catch (Exception e) {
//            String msg = LogUtils.printLogStackTrace(e);
//            logger.error(msg);
//            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
//        }
//    }

    @PostMapping("/apply-job")
    public ResponseData applyJob(@RequestBody AppliedJobRequest request) {
        try {
            findJobService.appliedJob(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS,
                   new ObjectMapper().writeValueAsString(request));
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
        }catch (Exception e) {
            e.printStackTrace();
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
        }catch (Exception e) {
            e.printStackTrace();
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
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/suggest-job/{id}")
    public ResponseData getListSuggestJobByCV(@PathVariable("id")long candidateId) {
        // get list lấy ra công việc gợi ý theo cv của candidate
        //dựa theo major-level của candidate
        try {
            List<JobResponse> listSuggestJob = jobService.getListSuggestJobByCv(candidateId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listSuggestJob);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/remote-job")
    public ResponseData getListRemoteJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            ResponseDataPagination pagination = jobService.getListJobByWorkForm(pageNo,pageSize, "REMOTE");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/fulltime-job")
    public ResponseData getListFulltimeJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            ResponseDataPagination pagination = jobService.getListJobByWorkForm(pageNo,pageSize, "FULLTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/parttime-job")
    public ResponseData getListParttimeJob(@RequestParam(defaultValue = "1") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            ResponseDataPagination pagination = jobService.getListJobByWorkForm(pageNo,pageSize, "PARTTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, pagination);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/job-by-field")
    public ResponseData getJobByCareer(@RequestParam(defaultValue = "1") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "id") long fieldId) {
        try {
            ResponseDataPagination pagination = jobService.getJobByFieldId(pageNo, pageSize, fieldId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS,pagination);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @PutMapping("/approve-job")
    public ResponseData approveJob(@RequestBody ApprovalJobRequest approvalJobRequest) {
        try {
            candidateJobService.approveJob(approvalJobRequest);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS,
                    new ObjectMapper().writeValueAsString(approvalJobRequest));
        } catch (Exception e) {
            logger.error(e.getMessage());
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
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/get-jobs-by-company")
    public ResponseDataPagination getJobByCompany(@RequestParam(defaultValue = "1") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam("companyId") long companyId) {
        try {
            List<JobResponse> responseList = new ArrayList<>();
            Page<Job> jobs = jobService.getJobByCompanyId(pageNo, pageSize, companyId);
            if (jobs.hasContent()) {
                for (Job job : jobs) {
                    JobResponse jobResponse = new JobResponse();
                    jobResponse.setJobId(job.getId());
                    jobResponse.setCompanyId(job.getCompanyId());
                    jobResponse.setRecruiterId(job.getRecruiterId());
                    List<JobHashtag> listJobHashTag = jobHashTagService.getHashTagOfJob(job.getId());
                    if (!(listJobHashTag.isEmpty() && listJobHashTag == null)) {
                        List<String> hashTagNameList = listJobHashTag.stream().map(JobHashtag::getHashTagName).collect(Collectors.toList());
                        jobResponse.setListHashtag(hashTagNameList);
                    }
                    Company company = companyService.getCompanyById(job.getCompanyId());
                    if (company != null) {
                        jobResponse.setCompanyName(company.getName());
                    }
                    jobResponse.setJobName(job.getJobName());
                    jobResponse.setJobDescription(job.getJobDescription());
                    jobResponse.setJobRequirement(job.getJobRequirement());
                    jobResponse.setBenefit(job.getBenefit());
                    jobResponse.setFromSalary(job.getFromSalary());
                    jobResponse.setToSalary(job.getToSalary());
                    jobResponse.setNumberRecruits(job.getNumberRecruits());
                    jobResponse.setRank(job.getRank());
                    jobResponse.setWorkForm(job.getWorkForm());
                    jobResponse.setGender(job.isGender());
                    jobResponse.setStartDate(job.getStartDate());
                    jobResponse.setEndDate(job.getEndDate());
                    jobResponse.setWorkPlace(job.getWorkPlace());
                    jobResponse.setCreatedAt(job.getCreatedAt());
                    jobResponse.setUpdatedAt(job.getUpdatedAt());
                    jobResponse.setPopularJob(job.isPopularJob());
                    jobResponse.setNewJob(job.isNewJob());
                    jobResponse.setUrgentJob(job.isUrgentJob());
                    responseList.add(jobResponse);
                }
            }
            ResponseDataPagination responseDataPagination = new ResponseDataPagination();
            Pagination pagination = new Pagination();
            responseDataPagination.setData(responseList);
            pagination.setCurrentPage(pageNo);
            pagination.setPageSize(pageSize);
            pagination.setTotalPage(jobs.getTotalPages());
            pagination.setTotalRecords(Integer.parseInt(String.valueOf(jobs.getTotalElements())));
            responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
            responseDataPagination.setPagination(pagination);
            return responseDataPagination;
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseDataPagination();
        }
    }
}
