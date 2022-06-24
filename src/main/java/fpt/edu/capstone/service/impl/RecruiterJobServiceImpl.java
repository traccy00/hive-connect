package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.dto.common.ResponseMessageConstants;
import fpt.edu.capstone.dto.job.JobForRecruiterResponse;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.exception.HiveConnectException;
import fpt.edu.capstone.service.JobService;
import fpt.edu.capstone.service.RecruiterJobService;
import fpt.edu.capstone.service.RecruiterService;
import fpt.edu.capstone.utils.Enums;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecruiterJobServiceImpl implements RecruiterJobService {

    private final ModelMapper modelMapper;

    private final JobService jobService;

    private final RecruiterService recruiterService;

    @Override
    public ResponseDataPagination getJobOfRecruiter(Integer pageNo, Integer pageSize, long recruiterId) {
        List<JobForRecruiterResponse> responseList = new ArrayList<>();

        int pageReq = pageNo >= 1 ? pageNo - 1 : pageNo;
        Pageable pageable = PageRequest.of(pageReq, pageSize);

        if(!recruiterService.existById(recruiterId)) {
            throw new HiveConnectException("Recruiter doesn't exist");
        }
        Page<Job> jobsListOfRecruiter = jobService.getJobOfRecruiter(pageable, recruiterId);
        JobForRecruiterResponse response;
        if(jobsListOfRecruiter.hasContent()) {
            for(Job job : jobsListOfRecruiter) {
                response = modelMapper.map(job, JobForRecruiterResponse.class);
                responseList.add(response);
            }
        }
        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        responseDataPagination.setData(responseList);
        pagination.setCurrentPage(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setTotalPage(jobsListOfRecruiter.getTotalPages());
        pagination.setTotalRecords(Integer.parseInt(String.valueOf(jobsListOfRecruiter.getTotalElements())));
        responseDataPagination.setStatus(Enums.ResponseStatus.SUCCESS.getStatus());
        responseDataPagination.setPagination(pagination);
        return responseDataPagination;
    }
}
