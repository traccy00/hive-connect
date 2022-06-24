package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.job.ApprovalJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.JobHashtag;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.repository.AppliedJobRepository;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidateJobServiceImpl implements CandidateJobService {

    private final ModelMapper modelMapper;

    private final JobService jobService;

    private final JobRepository jobRepository;

    private final JobHashTagService jobHashTagService;

    private final CompanyService companyService;

    private final AppliedJobService appliedJobService;

    private final AppliedJobRepository appliedJobRepository;

    @Override
    public ResponseDataPagination getNewestJob(Integer pageNo, Integer pageSize) {
        List<JobResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<Job> jobs = jobService.getNewestJobList(pageable);
        JobResponse jobResponse = new JobResponse();
        if (jobs.hasContent()) {
            for (Job job : jobs) {
//            JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
//            responseList.add(jobResponse);
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
                jobResponse.setBenefit(job.getBenifit());
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
    }

    @Override
    public ResponseDataPagination getUrgentJob(Integer pageNo, Integer pageSize) {
        List<JobResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<Job> jobs = jobService.getUrgentJobList(pageable);
        JobResponse jobResponse = new JobResponse();
        if (jobs.hasContent()) {
            for (Job job : jobs) {
//            JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
//            responseList.add(jobResponse);
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
                jobResponse.setBenefit(job.getBenifit());
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
    }

    @Override
    public ResponseDataPagination getPopularJob(Integer pageNo, Integer pageSize) {
        List<JobResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<Job> jobs = jobService.getPopularJobList(pageable);
        JobResponse jobResponse = new JobResponse();
        if (jobs.hasContent()) {
            for (Job job : jobs) {
//            JobResponse jobResponse = modelMapper.map(job, JobResponse.class);
//            responseList.add(jobResponse);
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
                jobResponse.setBenefit(job.getBenifit());
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
    }

    @Override
    public void approveJob(ApprovalJobRequest request) {
        AppliedJob appliedJob = appliedJobService.getAppliedJobPendingApproval(request.getJobId(), request.getCandidateId());
        if(appliedJob == null) {
            throw new HiveConnectException("This CV does not exist");
        }
        if (appliedJob.getApprovalStatus().equals(Enums.ApprovalStatus.PENDING.getStatus()) &&
                request.getApprovalStatus().equals("Approved")) {
            appliedJob.setApprovalStatus(Enums.ApprovalStatus.APPROVED.getStatus());
        } else if (appliedJob.getApprovalStatus().equals(Enums.ApprovalStatus.PENDING.getStatus()) &&
                request.getApprovalStatus().equals("Reject")) {
            appliedJob.setApprovalStatus(Enums.ApprovalStatus.REJECT.getStatus());
        }
        appliedJob.update();
        appliedJobRepository.save(appliedJob);
    }
}
