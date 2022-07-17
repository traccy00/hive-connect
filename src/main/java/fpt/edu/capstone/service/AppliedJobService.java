package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.dto.recruiter.CountCandidateApplyPercentageResponse;
import fpt.edu.capstone.dto.recruiter.CountTotalCreatedJobResponse;
import fpt.edu.capstone.entity.AppliedJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppliedJobService {

    boolean existsAppliedJob(long candidateId, long jobId);

    AppliedJob getAppliedJobPendingApproval(long jobId, long candidateId);

    AppliedJob getAppliedJobBefore(long candidateId, long jobId);

    Page<AppliedJob> getCvAppliedJob(Pageable pageable, long jobId, boolean isApplied);

    List<CompanyResponse> getTop12Companies();

    List<AppliedJob> searchAppliedJobsOfCandidate(long candidateId, String approvalStatus);

    CountCandidateApplyPercentageResponse countApplyPercentage(long recruiterId);

}
