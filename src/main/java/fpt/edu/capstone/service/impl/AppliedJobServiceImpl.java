package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.repository.AppliedJobRepository;
import fpt.edu.capstone.service.AppliedJobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppliedJobServiceImpl implements AppliedJobService {

    private final AppliedJobRepository appliedJobRepository;

    @Override
    public boolean appliedJobBefore(long candidateId, long jobId) {
        return appliedJobRepository.existsByCandidateIdAndJobId(candidateId, jobId);
    }

    @Override
    public AppliedJob getAppliedJobBefore(long candidateId, long jobId) {
        return appliedJobRepository.findByCandidateIdAndJobId(candidateId, jobId);
    }

    @Override
    public List<AppliedJob> getListCandidateAppliedJob(long jobId, boolean isApplied) {
        return appliedJobRepository.getListCandidateAppliedJob(jobId, isApplied);
    }
}
