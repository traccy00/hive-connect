package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.*;
import fpt.edu.capstone.dto.recruiter.CountTotalCreatedJobResponse;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private final ModelMapper modelMapper;

    private final CompanyService companyService;

    private final RecruiterService recruiterService;

    private final CandidateService candidateService;

    private final JobRepository jobRepository;

    private final CVService cvService;

    private final MajorService majorService;

    private final MajorLevelService majorLevelService;

    private final FieldsService fieldsService;

    private final ImageService imageService;

    @Override
    public void createJob(CreateJobRequest request) {
        long companyId = request.getCompanyId();
        long recruiterId = request.getRecruiterId();
        long fieldId = request.getFieldId();
        if (!companyService.findById(companyId).isPresent()) {
            throw new HiveConnectException("Công ty của nhà tuyển dụng không tồn tại.");
        }
        if (!recruiterService.existById(recruiterId)) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        if (!fieldsService.existById(fieldId)) {
            throw new HiveConnectException("Lĩnh vực kinh doanh không tồn tại.");
        }
//        if ((request.getJobName() == null || request.getJobName().trim().isEmpty())
//                || (request.getWorkPlace() == null || request.getWorkPlace().trim().isEmpty())
//                || (request.getJobDescription() == null || request.getJobDescription().trim().isEmpty())
//                || (request.getJobRequirement() == null || request.getJobRequirement().trim().isEmpty())
//                || (request.getBenefit() == null || request.getBenefit().trim().isEmpty())) {
//        }
        if(request.getFromSalary() < 0 || request.getToSalary() < 0 || (request.getFromSalary() > request.getToSalary())) {
            throw new HiveConnectException(ResponseMessageConstants.SALARY_INVALID);
        }
        Object CreateJobRequest = request;
        Job job = modelMapper.map(CreateJobRequest, Job.class);
        job.create();
        job.setFlag(request.getFlag());
        jobRepository.save(job);
    }

    @Override
    public ResponseDataPagination searchListJobFilter(Integer pageNo, Integer pageSize, long fieldId, long countryId, String jobName,
                                                      long fromSalary, long toSalary, String rank, String workForm, String workPlace) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        Page<Job> jobs = jobRepository.searchListJobFilter(pageable, fieldId, countryId, jobName, fromSalary, toSalary, rank, workForm, workPlace);
        List<JobResponse> jobResponse = new ArrayList<>();
        if (jobs.hasContent()) {
            for (Job j : jobs.getContent()) {
                JobResponse jr = modelMapper.map(j, JobResponse.class);
                jr.setJobId(j.getId());
                Company company = companyService.getCompanyById(j.getCompanyId());
                if (company != null) {
                    jr.setCompanyName(company.getName());
                }
                jr.setCompanyName(company.getName());
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
        if (job == null) {
            throw new HiveConnectException(ResponseMessageConstants.JOB_DOES_NOT_EXIST);
        }
        if (job.getFlag().equals(Enums.Flag.Posted.getStatus())) {
            return;
        }
        Object UpdateJobRequest = request;
        job = modelMapper.map(UpdateJobRequest, Job.class);
        job.update();
        job.setFlag(request.getFlag());
        jobRepository.save(job);
    }

    @Override
    public void saveJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public void deleteJob(long jobId) {
        Job job = jobRepository.getById(jobId);
        if (job == null) {
            throw new HiveConnectException("Công việc không tồn tại");
        }
        jobRepository.deleteJob(jobId);
    }

    @Override
    public boolean existsById(long id) {
        return jobRepository.existsById(id);
    }

//    @Override
//    public ResponseDataPagination getListJobByWorkForm(Integer pageNo, Integer pageSize, String workForm) {
//        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
//        Pageable pageable = PageRequest.of(pageReq, pageSize);
//        Page <Job> listFulltimeJob = jobRepository.getListJobByWorkForm(pageable, workForm);
//        List <DetailJobResponse> response = listFulltimeJob.stream().
//                map(job -> modelMapper.map(job, DetailJobResponse.class)).collect(Collectors.toList());
//        for (DetailJobResponse res: response) {
//            Company company = companyService.getCompanyById(res.getCompanyId());
//            Recruiter recruiter = recruiterService.getRecruiterById(res.getRecruiterId());
//            Fields fields = fieldsService.getById(res.getFieldId());
//
//            res.setCompanyName(company.getName());
//            res.setFieldName(fields.getFieldName());
//            res.setRecruiterName(recruiter.getFullName());
////            res.setAvatar(company.getAvatar());
//        }
//        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
//        Pagination pagination = new Pagination();
//        responseDataPagination.setData(response);
//        pagination.setCurrentPage(pageNo);
//        pagination.setPageSize(pageSize);
//        pagination.setTotalPage(listFulltimeJob.getTotalPages());
//        pagination.setTotalRecords(Integer.parseInt(String.valueOf(listFulltimeJob.getTotalElements())));
//        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
//        responseDataPagination.setPagination(pagination);
//        return responseDataPagination;
//        List<JobResponse> responseList = new ArrayList<>();
//        if(listFulltimeJob.hasContent()) {
//            for(Job job : listFulltimeJob) {
//                JobResponse response = modelMapper.map(job, JobResponse.class);
//            }
//        }
//    }

    @Override
    public Page<Job> getNewestJobList(Pageable pageable) {
        String flag = Enums.Flag.Posted.getStatus();
        return jobRepository.getNewestJob(pageable, true, 0, flag);
    }

    @Override
    public Page<Job> getUrgentJobList(Pageable pageable) {
        String flag = Enums.Flag.Posted.getStatus();
        return jobRepository.getUrgentJob(pageable, true, 0, flag);
    }

    @Override
    public Page<Job> getPopularJobList(Pageable pageable) {
        String flag = Enums.Flag.Posted.getStatus();
        return jobRepository.getPopularJob(pageable, true, 0, flag);
    }

    @Override
    public Page<Job> getJobOfRecruiter(Pageable pageable, long recruiterId) {
        return jobRepository.getAllByRecruiterId(pageable, recruiterId);
    }

    @Override
    public Optional<Job> findById(long id) {
        return jobRepository.findById(id);
    }


    @Override
    public Page<Job> getJobByCompanyId(long pageNo, long quantity, long companyId) {
        int pageReq = (int) (pageNo >= 1 ? pageNo - 1 : pageNo);
        Pageable pageable = PageRequest.of(pageReq, (int) quantity);
        return jobRepository.getJobByCompanyId(companyId, pageable);
    }

    @Override
    public List<Job> getJobByRecruiterId(long recId) {
        return jobRepository.findAllByRecruiterId(recId);
    }

    @Override
    public List<JobResponse> getSameJobsOtherCompanies(long detailJobId) {
//        Job detailJob = jobRepository.getById(detailJobId);
        List<Job> jobs = jobRepository.getSameJobsOtherCompanies(detailJobId);
        List<JobResponse> responseList = new ArrayList<>();
        for (Job job : jobs) {
            JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
            jobResponse.setJobId(job.getId());
            Company company = companyService.getCompanyById(job.getCompanyId());
            if (company != null) {
                jobResponse.setCompanyName(company.getName());
            }
            responseList.add(jobResponse);
        }
        return responseList;
    }

    @Override
    public HomePageData getDataHomePage() {
        HomePageData data = new HomePageData();
        int pageReq = 0 >= 1 ? 0 - 1 : 0;
        Pageable pageable = PageRequest.of(pageReq, 10);
        String flag = Enums.Flag.Posted.getStatus();
        data.setFulltimeJob(jobRepository.getListJobByWorkForm(pageable, "FULLTIME", flag).getContent());
        data.setParttimeJob(jobRepository.getListJobByWorkForm(pageable, "PARTTIME", flag).getContent());
        data.setRemoteJob(jobRepository.getListJobByWorkForm(pageable, "REMOTE", flag).getContent());
        data.setPopularJob(jobRepository.getPopularJob(pageable, true, 0, flag).getContent());
        data.setNewJob(jobRepository.getNewestJob(pageable, true, 0, flag).getContent());
        data.setUrgentJob(jobRepository.getUrgentJob(pageable, true, 0, flag).getContent());
        data.setJobByFields(jobRepository.getListJobByFieldId(pageable, 1, flag).getContent());
        return data;
    }

    @Override
    public Job getJobById(long jobId) {
        Job job = jobRepository.getById(jobId);
        if (job == null || job.getId() == 0) {
            throw new HiveConnectException(ResponseMessageConstants.JOB_DOES_NOT_EXIST);
        }
        return job;
    }

    @Override
    public ResponseDataPagination getJobByFieldId(Integer pageNo, Integer pageSize, long id) {
        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);
        String flag = Enums.Flag.Posted.getStatus();
        Page<Job> listByCareer = jobRepository.getListJobByFieldId(pageable, id, flag);
        List<DetailJobResponse> response = listByCareer.stream().
                map(job -> modelMapper.map(job, DetailJobResponse.class)).collect(Collectors.toList());
        for (DetailJobResponse res : response) {
            Company company = companyService.getCompanyById(res.getCompanyId());
            Recruiter recruiter = recruiterService.getRecruiterById(res.getRecruiterId());
            Fields fields = fieldsService.getById(res.getFieldId());

            res.setCompanyName(company.getName());
            res.setFieldName(fields.getFieldName());
            res.setRecruiterName(recruiter.getFullName());
            Image image = imageService.getImageCompany(company.getId(), true);
            if (image != null) {
                res.setAvatar(image.getUrl());
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(response);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(listByCareer.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(listByCareer.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    @Override
    public List<JobResponse> getListSuggestJobByCv(long candidateId) {
        if (!candidateService.existsById(candidateId)) {
            throw new HiveConnectException(ResponseMessageConstants.CANDIDATE_DOES_NOT_EXIST);
        }
        CV cv = cvService.getCVByCandidateId(candidateId);
        List<MajorLevel> majorLevel = majorLevelService.getListMajorLevelByCvId(cv.getId()); // lấy ra major của candidate để filter
        List<JobResponse> responses = new ArrayList<>();
        for (MajorLevel ml : majorLevel) {
            String majorName = majorService.getNameByMajorId(ml.getMajorId()).toLowerCase();
            List<Job> jobList = jobRepository.getListSuggestJobByCv(majorName);
            for (Job j : jobList) {
                JobResponse jr = modelMapper.map(j, JobResponse.class);
                responses.add(jr);
            }
        }
        return responses;
    }

    @Override
    public CountTotalCreatedJobResponse countTotalCreatedJobOfRecruiter(long recruiterId) {
        return jobRepository.countTotalCreatedJobOfRecruiter(recruiterId);
    }
}
