package fpt.edu.capstone.service;

import fpt.edu.capstone.utils.ResponseDataPagination;

public interface RecruiterJobService {

    ResponseDataPagination getJobOfRecruiter(Integer pageNo, Integer pageSize, long recruiterId);
}
