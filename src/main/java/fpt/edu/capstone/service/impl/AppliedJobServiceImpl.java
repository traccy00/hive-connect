package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.TopCompanyResponse;
import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.entity.Company;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.utils.Enums;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppliedJobServiceImpl implements AppliedJobService {

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
    public List<TopCompanyResponse> getTop12Companies() {
        //thêm sort
        return appliedJobRepository.getTop12Companies();
    }
}
