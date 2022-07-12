package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.job.CreateJobRequest;
import fpt.edu.capstone.dto.job.JobResponse;
import fpt.edu.capstone.dto.job.UpdateJobRequest;
import fpt.edu.capstone.entity.Job;
import fpt.edu.capstone.utils.Pagination;
import fpt.edu.capstone.utils.ResponseDataPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobService {
    void createJob(CreateJobRequest request);

    ResponseDataPagination searchListJobFilter(Integer pageNo,Integer pageSize, long fieldId, long countryId,
                                               String jobName, long fromSalary, long toSalary, String rank,
                                               String workForm, String workPlace);

    void updateJob(UpdateJobRequest request);

//    void deleteJob(long jobId);

    boolean existsById(long id);

    ResponseDataPagination getListJobByWorkForm(Integer pageNo, Integer pageSize, String workForm);

    Page<Job> getNewestJobList(Pageable pageable);

    ResponseDataPagination getJobByFieldId(Integer pageNo, Integer pageSize, long id);

    List<JobResponse> getListSuggestJobByCv(long candidateId);

    Page<Job> getUrgentJobList(Pageable pageable);

    Page<Job> getPopularJobList(Pageable pageable);

    Job getJobById(long jobId);

    Page<Job> getJobOfRecruiter(Pageable pageable, long recruiterId);

    Optional<Job> findById(long id);

    Page<Job> getJobByCompanyId(long pageNo, long quantity, long companyId);

    void updateIsDeleted(long status, long id);
}
