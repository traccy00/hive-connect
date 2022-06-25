package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.AppliedJob;

import java.util.List;

public interface AppliedJobService {

    boolean existsAppliedJob(long candidateId, long jobId);

    AppliedJob getAppliedJobPendingApproval(long jobId, long candidateId);

    AppliedJob getAppliedJobBefore(long candidateId, long jobId);

    List<AppliedJob> getListCandidateAppliedJob(long jobId, boolean isApplied);

}
