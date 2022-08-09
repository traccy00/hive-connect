package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.dto.recruiter.CountCandidateApplyPercentageResponse;
import fpt.edu.capstone.dto.recruiter.CountTotalCreatedJobResponse;
import fpt.edu.capstone.entity.AppliedJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppliedJobService {
    AppliedJob getAppliedJobPendingApproval(long jobId, long candidateId);

    AppliedJob getAppliedJobBefore(long candidateId, long jobId);

    Page<AppliedJob> getCvAppliedJob(Pageable pageable, long jobId, boolean isApplied);

    List<CompanyResponse> getTop12Companies();

    Page<AppliedJob> searchAppliedJobsOfCandidate(Pageable pageable, long candidateId, String approvalStatus);

    List<CountCandidateApplyPercentageResponse> countApplyPercentage(long recruiterId);

    int countAppliedCVOfJob(long jobId);

}
