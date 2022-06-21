package fpt.edu.capstone.controller;

import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.*;
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
                                            @RequestParam(defaultValue = "0", value = "categoryId", required = false) long category,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "companyName", required = false) String companyName,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "jobName", required = false) String jobName,
                                            @RequestParam(defaultValue = "0", value = "fromSalary", required = false) long fromSalary,
                                            @RequestParam(defaultValue = "0", value = "toSalary", required = false) long toSalary,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "rank", required = false) String rank,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "workForm", required = false) String workForm,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "workPlace", required = false) String workPlace,
                                            @RequestParam(defaultValue = StringUtils.EMPTY, value = "techStack", required = false) String techStack) {
        try {
            ResponseDataPagination pagination = jobService.searchListJobFilter(pageNo, pageSize, category, companyName,
                    jobName, fromSalary, toSalary, rank, workForm, workPlace, techStack);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), pagination);
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
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.UPDATE_CATEGORY_SUCCESS);
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
    public ResponseData getPopularJob() {
        // get list lấy ra công việc phỏ biến
        return null;
    }

    @GetMapping("/new-job")
    public ResponseData getListNewJob() {
        // get list lấy ra công việc mới nhất
        //sort by created At
        return null;
    }

    @GetMapping("/urgent-job")
    public ResponseData getListUrgentJob() {
        // get list lấy ra công việc cần gấpt
        return null;
    }

    @GetMapping("/suggest-job")
    public ResponseData getListSuggestJobByCV() {
        // get list lấy ra công việc gợi ý theo cv của candidate
        return null;
    }

    @GetMapping("/remote-job")
    public ResponseData getListRemoteJob() {
        try {
            List <JobResponse> listRemoteJob = jobService.getListJobByWorkForm("REMOTE");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listRemoteJob);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/fulltime-job")
    public ResponseData getListFulltimeJob() {
        try {
            List <JobResponse> listFulltimeJob = jobService.getListJobByWorkForm("FULLTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listFulltimeJob);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/parttime-job")
    public ResponseData getListParttimeJob() {
        try {
            List <JobResponse> listParttimeJob = jobService.getListJobByWorkForm("PARTTIME");
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS, listParttimeJob);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }

    @GetMapping("/job-by-career")
    public ResponseData getJobByCareer(@RequestParam(value = "id") long careerId) {
        try {
//           List<JobResponse> listByCareer = jobService.
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.ERROR);
        }
    }
}
