package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.List;

public interface CandidateManageService {
    ResponseDataPagination searchAppliedJobsOfCandidate(Integer pageNo, Integer pageSize, long candidateId, String approvalStatus);

    ResponseDataPagination getJobsOfCompany(Integer pageNo, Integer pageSize, long companyId);
}
