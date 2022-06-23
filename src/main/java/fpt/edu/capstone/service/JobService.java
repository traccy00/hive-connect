package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.job.UpdateJobRequest;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    void createJob(CreateJobRequest request);

    ResponseDataPagination searchListJobFilter(Integer pageNo,Integer pageSize, long fieldName,
                                               String jobName, long fromSalary, long toSalary, String rank,
                                               String workForm, String workPlace);

//    void updateJob(UpdateJobRequest request);

//    void deleteJob(long jobId);

    boolean existsById(long id);

    List<Job> getListJobByWorkForm(String workForm);

    Page<Job> getNewestJobList(Pageable pageable);

    List<Job> getJobByFieldId(long id);

    List<JobResponse> getListSuggestJobByCv(long candidateId);

    Page<Job> getUrgentJobList(Pageable pageable);

    Page<Job> getPopularJobList(Pageable pageable);
}
