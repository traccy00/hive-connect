package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.AppliedJob;

import java.util.List;

public interface CandidateManageService {
    List<AppliedJob> searchAppliedJobsOfCandidate(long candidateId, String approvalStatus);
}
