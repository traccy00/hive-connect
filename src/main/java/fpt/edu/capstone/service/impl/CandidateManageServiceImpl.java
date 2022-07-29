package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.candidate.AppliedJobCandidateResponse;
import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.entity.JobHashtag;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.*;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidateManageServiceImpl implements CandidateManageService {

    private final AppliedJobService appliedJobService;

    private final CandidateService candidateService;

    private final JobService jobService;

    private final CompanyService companyService;

    private final JobHashTagService jobHashTagService;

    @Override
    public ResponseDataPagination searchAppliedJobsOfCandidate(Integer pageNo, Integer pageSize, long candidateId, String approvalStatus) {
        if(!candidateService.existsById(candidateId)) {
            throw new HiveConnectException(ResponseMessageConstants.USER_DOES_NOT_EXIST);
        }
        List<AppliedJobCandidateResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        Page<AppliedJob> appliedJobs = appliedJobService.searchAppliedJobsOfCandidate(pageable, candidateId, approvalStatus);
        if(appliedJobs.hasContent()) {
            for(AppliedJob appliedJob : appliedJobs) {
                AppliedJobCandidateResponse response = new AppliedJobCandidateResponse();
                response.setAppliedJobId(appliedJob.getId());
                response.setJob(jobService.getJobById(appliedJob.getJobId()));
                response.setApprovalStatus(appliedJob.getApprovalStatus());
                response.setAppliedJobDate(appliedJob.getCreatedAt());
                if(appliedJob.getApprovalStatus().equals(Enums.ApprovalStatus.APPROVED.getStatus())) {
                    response.setApprovalDate(appliedJob.getUpdatedAt());
                }
                responseList.add(response);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(appliedJobs.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(appliedJobs.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }

    public ResponseDataPagination getJobsOfCompany(Integer pageNo, Integer pageSize, long companyId) {
        List<JobResponse> responseList = new ArrayList<>();
        Page<Job> jobs = jobService.getJobByCompanyId(pageNo, pageSize, companyId);
        if (jobs.hasContent()) {
            for (Job job : jobs) {
                JobResponse jobResponse = new JobResponse();
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
                jobResponse.setBenefit(job.getBenefit());
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
}
