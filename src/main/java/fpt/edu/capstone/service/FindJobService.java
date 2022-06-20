package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.job.AppliedJobRequest;
import fpt.edu.capstone.dto.job.CandidateAppliedJobResponse;
import fpt.edu.capstone.dto.job.RecruiterPostResponse;
import fpt.edu.capstone.entity.sprint1.AppliedJob;

import java.util.List;

public interface FindJobService {

    void appliedJob(AppliedJobRequest request) throws Exception;

    List<CandidateAppliedJobResponse> getCandidateAppliedJobList(long jobId);

    List<RecruiterPostResponse> getRecruiterPosts(long recruiterId);
}
