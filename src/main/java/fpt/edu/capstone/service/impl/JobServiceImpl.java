package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.job.UpdateJobRequest;
import fpt.edu.capstone.entity.*;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.JobRepository;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final CompanyService companyService;

    private final RecruiterService recruiterService;

    private final JobRepository jobRepository;

    private final CVService cvService;

    private final MajorService majorService;

    private final MajorLevelService majorLevelService;

    private final FieldsService fieldsService;

    @Override
    public void createJob(CreateJobRequest request) {
        long companyId = request.getCompanyId();
        long recruiterId = request.getRecruiterId();
        long fieldId = request.getFieldId();
        if(!companyService.existById(companyId)){
            throw new HiveConnectException("Company not found!");
        }
        if(!recruiterService.existById(recruiterId)){
            throw new HiveConnectException("Recruiter not found!");
        }
        if(!fieldsService.existById(fieldId)){
            throw new HiveConnectException("Field not found!");
        }
        Recruiter recruiter = recruiterService.getRecruiterById(recruiterId);
        Object CreateJobRequest = request;
        Job job = modelMapper.map(CreateJobRequest, Job.class);
//        job.setCompanyName(recruiter.getCompanyName());
        job.create();
        jobRepository.save(job);
    }

//    @Override
//    public ResponseDataPagination searchListJobFilter(Integer pageNo, Integer pageSize, long category, String companyName,
//                                                      String jobName, long fromSalary, long toSalary, String rank,
//                                                      String workForm, String workPlace, String techStack) {
//        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
//        Pageable pageable = PageRequest.of(pageReq, pageSize);
//        Page<RecruiterPost> jobs = jobRepository.searchListJobFilter(pageable,category,companyName,jobName,fromSalary,toSalary,rank,workForm,workPlace,techStack);
//        List <JobResponse> jobResponse = new ArrayList<>();
//        if(jobs.hasContent()){
//            for (RecruiterPost j :jobs.getContent()){
//                JobResponse jr = modelMapper.map(j, JobResponse.class);
//                jobResponse.add(jr);
//            }
//        }
//        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
//        Pagination pagination = new Pagination();
//        responseDataPagination.setData(jobResponse);
//        pagination.setCurrentPage(pageNo);
//        pagination.setPageSize(pageSize);
//        pagination.setTotalPage(jobs.getTotalPages());
//        pagination.setTotalRecords(Integer.parseInt(String.valueOf(jobs.getTotalElements())));
//        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
//        responseDataPagination.setPagination(pagination);
//
//        return responseDataPagination;
//    }

//    @Override
//    //TODO : fix insert for update function
//    public void updateJob(UpdateJobRequest request) {
//        RecruiterPost recruiterPost = jobRepository.getById(request.getJobId());
//        if(recruiterPost == null){
//            throw new HiveConnectException("Job does not exist");
//        }
//        Object UpdateJobRequest = request;
//        recruiterPost = modelMapper.map(UpdateJobRequest, RecruiterPost.class);
//        recruiterPost.update();
//        jobRepository.saveAndFlush(recruiterPost);
//    }

//    @Override
//    public void deleteJob(long jobId) {
//        RecruiterPost recruiterPost = jobRepository.getById(jobId);
//        if(recruiterPost == null){
//            throw new HiveConnectException("Job does not exist");
//        }
//        jobRepository.deleteJob(jobId);
//    }

    @Override
    public boolean existsById(long id) {
        return jobRepository.existsById(id);
    }

    @Override
    public List<JobResponse> getListJobByWorkForm(String workForm) {
        return jobRepository.getListJobByWorkForm(workForm);
    }

    @Override
    public Page<Job> getNewestJobList(Pageable pageable) {
        return jobRepository.getNewestJob(pageable, true, 0);
    }

    @Override
    public Page<Job> getUrgentJobList(Pageable pageable) {
        return jobRepository.getUrgentJob(pageable, true, 0);
    }

    @Override
    public Page<Job> getPopularJobList(Pageable pageable) {
        return jobRepository.getPopularJob(pageable, true, 0);
    }

    @Override
    public List<JobResponse> getJobByFieldId(long id) {
        return jobRepository.getListJobByFieldId(id);
    }

    @Override
    public List<JobResponse> getListSuggestJobByCv(long candidateId) {
        CV cv = cvService.getCVByCandidateId(candidateId);
//cần lấy ra được chuyên môn của thằng candidate đó,
        //sau đó đề xuất những công việc theo lĩnh vực và chuyên môn đó
        MajorLevel majorLevel = majorLevelService.getByCvId(cv.getId());
        String majorName = majorService.getNameByMajorId(majorLevel.getMajorId());
        //khi đã có được name rồi thì cầm vào để query search like "java" trong bảng job
        List <Job> jobList = jobRepository.getListSuggestJobByCv(majorName);
        List<JobResponse> responses = new ArrayList<>();

        for (Job j : jobList){
            JobResponse jr = modelMapper.map(j, JobResponse.class);
            responses.add(jr);
        }
        return responses;
    }
}
