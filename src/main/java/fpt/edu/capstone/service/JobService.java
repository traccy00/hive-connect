package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.job.UpdateJobRequest;
import fpt.edu.capstone.utils.ResponseDataPagination;

import java.util.List;

public interface JobService {
    void createJob(CreateJobRequest request);

    ResponseDataPagination searchListJobFilter(Integer pageNo,Integer pageSize, long category, String companyName,
                                               String jobName, long fromSalary, long toSalary, String rank,
                                               String workForm, String workPlace, String techStack);

    void updateJob(UpdateJobRequest request);

    void deleteJob(long jobId);

    boolean existsById(long id);

    List<JobResponse> getListJobByWorkForm(String workForm);

}
