package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.company.CompanyResponse;
import fpt.edu.capstone.dto.company.TopCompanyResponse;
import fpt.edu.capstone.dto.recruiter.CountCandidateApplyPercentageResponse;
import fpt.edu.capstone.dto.recruiter.CountTotalCreatedJobResponse;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.entity.Image;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.utils.Enums;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppliedJobServiceImpl implements AppliedJobService {

    private final ModelMapper modelMapper;

    private final AppliedJobRepository appliedJobRepository;

    @Override
    public boolean existsAppliedJob(long candidateId, long jobId) {
        return appliedJobRepository.existsByCandidateIdAndJobId(candidateId, jobId);
    }

    @Override
    public AppliedJob getAppliedJobPendingApproval(long jobId, long candidateId) {
        String approvalStatus = Enums.ApprovalStatus.PENDING.getStatus();
        boolean isApplied = true;
        return appliedJobRepository.getAppliedJobPendingApproval(jobId, candidateId, approvalStatus, isApplied);
    }

    @Override
    public AppliedJob getAppliedJobBefore(long candidateId, long jobId) {
        return appliedJobRepository.getByCandidateIdAndJobId(candidateId, jobId);
    }

    @Override
    public Page<AppliedJob> getCvAppliedJob(Pageable pageable, long jobId, boolean isApplied) {
        return appliedJobRepository.getCvAppliedJob(pageable, jobId, isApplied);
    }

    @Override
    public List<CompanyResponse> getTop12Companies() {
      return appliedJobRepository.getTop12Companies();
    }

    @Override
    public Page<AppliedJob> searchAppliedJobsOfCandidate(Pageable pageable, long candidateId, String approvalStatus) {
        return appliedJobRepository.searchAppliedJobsOfCandidate(pageable, candidateId, approvalStatus);
    }

    @Override
    public CountCandidateApplyPercentageResponse countApplyPercentage(long recruiterId) {
        return appliedJobRepository.countApplyPercentage(recruiterId);
    }

    @Override
    public int countAppliedCVOfJob(long jobId) {
        return appliedJobRepository.countAppliedCVOfAJob(jobId);
    }
}
