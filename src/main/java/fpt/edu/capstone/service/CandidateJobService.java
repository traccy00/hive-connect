package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.job.JobResponse;

import java.util.List;

public interface CandidateJobService {
    List<JobResponse> getNewestJob();
}
