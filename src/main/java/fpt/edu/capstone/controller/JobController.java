package fpt.edu.capstone.controller;

import fpt.edu.capstone.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.LogUtils;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @PostMapping("/create-job")
    public ResponseData createJob(@RequestBody @Valid CreateJobRequest request){
        try {
            if (request == null) {
                return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), ResponseMessageConstants.DATA_INVALID);
            }
            jobService.createJob(request);
            return new ResponseData(Enums.ResponseStatus.SUCCESS.getStatus(), ResponseMessageConstants.CREATE_JOB_SUCCESS);
        } catch (Exception e){
            String msg = LogUtils.printLogStackTrace(e);
            logger.error(msg);
            return new ResponseData(Enums.ResponseStatus.ERROR.getStatus(), e.getMessage());
        }
    }

    @GetMapping("/list-job")
    public ResponseData searchListJobFilter(@RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam String category,
                                            @RequestParam String companyName,
                                            @RequestParam String jobName,
                                            @RequestParam long fromSalary,
                                            @RequestParam long toSalary,
                                            @RequestParam String rank,
                                            @RequestParam String workForm,
                                            @RequestParam String workPlace,
                                            @RequestParam String techStack){
        return null;
    }
}
