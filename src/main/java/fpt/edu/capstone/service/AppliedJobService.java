package fpt.edu.capstone.service;

import fpt.edu.capstone.entity.sprint1.AppliedJob;

import java.util.List;

public interface AppliedJobService {

    boolean appliedJobBefore(long candidateId, long jobId);

    AppliedJob getAppliedJobBefore(long candidateId, long jobId);

    List<AppliedJob> getListCandidateAppliedJob(long jobId, boolean isApplied);

}
