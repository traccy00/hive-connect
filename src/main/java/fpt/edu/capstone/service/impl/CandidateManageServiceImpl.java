package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.AppliedJob;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.AppliedJobService;
import fpt.edu.capstone.service.CandidateManageService;
import fpt.edu.capstone.service.CandidateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CandidateManageServiceImpl implements CandidateManageService {

    private final AppliedJobService appliedJobService;

    private final CandidateService candidateService;

    @Override
    public List<AppliedJob> searchAppliedJobsOfCandidate(long candidateId, String approvalStatus) {
        if(!candidateService.existsById(candidateId)) {
            throw new HiveConnectException("Người dùng không tồn tại");
        }
        List<AppliedJob> appliedJobs = appliedJobService.searchAppliedJobsOfCandidate(candidateId, approvalStatus);
        return appliedJobs;
    }
}
