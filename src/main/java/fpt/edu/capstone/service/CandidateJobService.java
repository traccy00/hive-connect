package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.List;

public interface CandidateJobService {
    ResponseDataPagination getNewestJob(Integer pageNo, Integer pageSize);

    ResponseDataPagination getUrgentJob(Integer pageNo, Integer pageSize);

    ResponseDataPagination getPopularJob(Integer pageNo, Integer pageSize);
}
