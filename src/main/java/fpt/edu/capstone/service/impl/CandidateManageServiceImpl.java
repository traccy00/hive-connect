package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.candidate.AppliedJobCandidateResponse;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.service.CandidateManageService;
import fpt.edu.capstone.service.CandidateService;
import fpt.edu.capstone.service.JobService;
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

@Service
@AllArgsConstructor
public class CandidateManageServiceImpl implements CandidateManageService {

    private final AppliedJobService appliedJobService;

    private final CandidateService candidateService;

    private final JobService jobService;

    @Override
    public ResponseDataPagination searchAppliedJobsOfCandidate(Integer pageNo, Integer pageSize, long candidateId, String approvalStatus) {
        if(!candidateService.existsById(candidateId)) {
            throw new HiveConnectException("Người dùng không tồn tại");
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
}
