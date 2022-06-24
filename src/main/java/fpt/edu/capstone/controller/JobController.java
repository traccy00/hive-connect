package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.*;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.service.CandidateJobService;
import fpt.edu.capstone.service.FindJobService;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    public ResponseData getJobDetail(@PathVariable("id") long jobId){
        try {
            Job job = jobService.getJobById(jobId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), job);
        }catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

//    @PutMapping("update-job")
//    public ResponseData updateJob(@RequestBody UpdateJobRequest request) {
//        try {
//            jobService.updateJob(request);
//            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.UPDATE_JOB_SUCCESS);
//        } catch (Exception e) {
//            String msg = LogUtils.printLogStackTrace(e);
//            logger.error(msg);
//            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
//        }
//    }

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
                    request.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/list-candidate-applied-job")
    public ResponseData getListCandidateAppliedJob(@RequestParam long jobId) {
        try {
            List<CandidateAppliedJobResponse> listCandidateAppliedJob = findJobService.getCandidateAppliedJobList(jobId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listCandidateAppliedJob);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/get-recruiter-posts")
    public ResponseData getRecruiterPosts(@RequestParam long recruiterId) {
        try {
            List<RecruiterPostResponse> responseList = findJobService.getRecruiterPosts(recruiterId);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

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
    public ResponseData getListRemoteJob() {
        try {
            List <Job> listRemoteJob = jobService.getListJobByWorkForm("REMOTE");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listRemoteJob);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/fulltime-job")
    public ResponseData getListFulltimeJob() {
        try {
            List <Job> listFulltimeJob = jobService.getListJobByWorkForm("FULLTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listFulltimeJob);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/parttime-job")
    public ResponseData getListParttimeJob() {
        try {
            List <Job> listParttimeJob = jobService.getListJobByWorkForm("PARTTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listParttimeJob);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/job-by-field")
    public ResponseData getJobByCareer(@RequestParam(value = "id") long fieldId) {
        try {
            List<Job> listByCareer = jobService.getJobByFieldId(fieldId);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS,listByCareer);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }
}
