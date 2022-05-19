package fpt.edu.capstone.controller;

import fpt.edu.capstone.entity.sprint1.Candidate;
import fpt.edu.capstone.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    /*
    tạo job, liên quan đến bảng techstack, khi job chọn techstack đồng thời insert vào bảng n - n JobTechstack
    Tức là insert 2 bảng cùng lúc
    sử dụng transactional
     */
    public ResponseData createJob(){
        return null;
    }
}
