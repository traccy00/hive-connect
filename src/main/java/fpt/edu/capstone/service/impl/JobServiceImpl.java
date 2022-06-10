package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.CandidateAppliedJobResponse;
import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.job.UpdateJobRequest;
import fpt.edu.capstone.entity.sprint1.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.JobRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private final ModelMapper modelMapper;

    private final CategoryService categoryService;

    private final RecruiterService recruiterService;

    private final JobRepository jobRepository;

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
        RecruiterPost recruiterPost = modelMapper.map(CreateJobRequest, RecruiterPost.class);
        recruiterPost.setCompanyName(recruiter.getCompanyName());
        recruiterPost.create();
        jobRepository.save(recruiterPost);
    }

    @Override
    public ResponseDataPagination searchListJobFilter(Integer pageNo, Integer pageSize, long category, String companyName,
                                                      String jobName, long fromSalary, long toSalary, String rank,
                                                      String workForm, String workPlace, String techStack) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<RecruiterPost> jobs = jobRepository.searchListJobFilter(pageable,category,companyName,jobName,fromSalary,toSalary,rank,workForm,workPlace,techStack);
        List <JobResponse> jobResponse = new ArrayList<>();
        if(jobs.hasContent()){
            for (RecruiterPost j :jobs.getContent()){
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
        RecruiterPost recruiterPost = jobRepository.getById(request.getJobId());
        if(recruiterPost == null){
            throw new HiveConnectException("Job does not exist");
        }
        Object UpdateJobRequest = request;
        recruiterPost = modelMapper.map(UpdateJobRequest, RecruiterPost.class);
        recruiterPost.update();
        jobRepository.saveAndFlush(recruiterPost);
    }

    @Override
    public void deleteJob(long jobId) {
        RecruiterPost recruiterPost = jobRepository.getById(jobId);
        if(recruiterPost == null){
            throw new HiveConnectException("Job does not exist");
        }
        jobRepository.deleteJob(jobId);
    }

    @Override
    public boolean existsById(long id) {
        return jobRepository.existsById(id);
    }

//    @Override
//    public List<CandidateAppliedJobResponse> getCandidateAppliedJobList(long jobId) {
//        List<CandidateAppliedJobResponse> responseList = new ArrayList<>();
//        CandidateAppliedJobResponse responseObj = new CandidateAppliedJobResponse();
//        List<AppliedJob> appliedJobs = null;//appliedJobService.getListCandidateAppliedJob(jobId);
//        responseObj.setJobId(jobId);
//        for(AppliedJob appliedJob : appliedJobs) {
//            Candidate candidate = candidateService.getById(appliedJob.getCandidateId());
//            responseObj.setCandidateId(appliedJob.getCandidateId());
//            responseObj.setCandidateName(candidate.getFullName());
//            Users user = userService.findById(candidate.getUserId());
//            responseObj.setAvatar(user.getAvatar());
////            responseObj.setExperienceYear();
////            responseObj.setCareerGoal();
//            responseList.add(responseObj);
//        }
//        //responseObj.setRowCount();
//
//        return null;
//    }
}
