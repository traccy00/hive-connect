package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.entity.sprint1.Job;
import fpt.edu.capstone.entity.sprint1.Recruiter;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.JobRepository;
import fpt.edu.capstone.service.CategoryService;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RecruiterService recruiterService;

    @Autowired
    JobRepository jobRepository;

    @Override
    public void createJob(CreateJobRequest request) {
        long categoryId = request.getCategoryId();
        long recruiterId = request.getRecruiterId(); // check trạng thái tài khoản của recruiter, recruiter này thuộc công ty nào
        if(!categoryService.existById(categoryId)){
            throw new HiveConnectException("Category not found!");
        }
        if(!recruiterService.existById(recruiterId)){
            throw new HiveConnectException("Recruiter not found!");
        }
        Recruiter recruiter = recruiterService.getRecruiterById(recruiterId);
        Object CreateJobRequest = request;
        Job job = modelMapper.map(CreateJobRequest,Job.class);
        job.setCompanyName(recruiter.getCompanyName());
        job.create();
        jobRepository.save(job);
    }
}
