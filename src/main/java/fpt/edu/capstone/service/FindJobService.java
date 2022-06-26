package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.job.AppliedJobRequest;
import fpt.edu.capstone.dto.job.CvAppliedJobResponse;
import fpt.edu.capstone.dto.job.RecruiterPostResponse;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.List;

public interface FindJobService {

    void appliedJob(AppliedJobRequest request) throws Exception;

    ResponseDataPagination getCvListAppliedJob(Integer pageNo, Integer pageSize, long jobId);

    List<RecruiterPostResponse> getRecruiterPosts(long recruiterId);
}
