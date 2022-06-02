package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.job.UpdateJobRequest;
import fpt.edu.capstone.entity.sprint1.Job;
import fpt.edu.capstone.entity.sprint1.Recruiter;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.JobRepository;
import fpt.edu.capstone.service.CategoryService;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        long recruiterId = request.getRecruiterId();
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

    @Override
    public ResponseDataPagination searchListJobFilter(Integer pageNo, Integer pageSize, long category, String companyName,
                                                      String jobName, long fromSalary, long toSalary, String rank,
                                                      String workForm, String workPlace, String techStack) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<Job> jobs = jobRepository.searchListJobFilter(pageable,category,companyName,jobName,fromSalary,toSalary,rank,workForm,workPlace,techStack);
        List <JobResponse> jobResponse = new ArrayList<>();
        if(jobs.hasContent()){
            for (Job j :jobs.getContent()){
                JobResponse jr = modelMapper.map(j, JobResponse.class);
                jobResponse.add(jr);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(jobResponse);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(jobs.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(jobs.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);

        return responseDataPagination;
    }

    @Override
    //TODO : fix insert for update function
    public void updateJob(UpdateJobRequest request) {
        Job job = jobRepository.getById(request.getJobId());
        if(job == null){
            throw new HiveConnectException("Job does not exist");
        }
        Object UpdateJobRequest = request;
        job = modelMapper.map(UpdateJobRequest, Job.class);
        job.update();
        jobRepository.saveAndFlush(job);
    }

    @Override
    public void deleteJob(long jobId) {
        Job job = jobRepository.getById(jobId);
        if(job == null){
            throw new HiveConnectException("Job does not exist");
        }
        jobRepository.deleteJob(jobId);
    }
}
